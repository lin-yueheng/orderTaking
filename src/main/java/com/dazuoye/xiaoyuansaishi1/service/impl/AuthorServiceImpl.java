package com.dazuoye.xiaoyuansaishi1.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dazuoye.xiaoyuansaishi1.dto.AuthorDTO;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UpdatePwdDTO;
import com.dazuoye.xiaoyuansaishi1.dto.UserDTO;
import com.dazuoye.xiaoyuansaishi1.entity.Author;
import com.dazuoye.xiaoyuansaishi1.entity.User;
import com.dazuoye.xiaoyuansaishi1.mapper.AuthorMapper;
import com.dazuoye.xiaoyuansaishi1.service.AuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazuoye.xiaoyuansaishi1.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dazuoye.xiaoyuansaishi1.utils.RedisConstants.*;
import static com.dazuoye.xiaoyuansaishi1.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * <p>
 * 主办方表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-04-29
 */
@Service
@Slf4j
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone) {
        // TODO 1.校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // TODO 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }
        // TODO 3.符合，生成验证码4位数
        String code = RandomUtil.randomNumbers(4);

        // TODO 4.保存验证码到reids
        stringRedisTemplate.opsForValue().set(REG_CODE_KEY + phone, code, REG_CODE_TTL, TimeUnit.MINUTES);

        // TODO 5.发送验证码
        log.debug("发送短信验证码成功，验证码：{}", code);
        // TODO 返回ok
        return Result.ok(code);
    }

    @Override
    public Result authorLogin(AuthorDTO authorDTO) {
        // TODO 1.获取表单输入的用户名和密码
        String authorname = authorDTO.getAuthorname();
        String password = authorDTO.getPassword();

        // TODO 2.判断用户是否存在
        //Author author = query().eq("authorname", authorname).eq("password",password).one();

        LambdaQueryWrapper<Author> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(Author::getId,Author::getAuthorname,Author::getPassword,Author::getCompanyName,Author::getPhone,Author::getStatus)
                .eq(Author::getPassword,password)
                .eq(Author::getAuthorname,authorname);

        Author author = getOne(lambdaQueryWrapper);

        if(author == null){
            return Result.fail("用户不存在或输入有误");
        }else if(author.getStatus()==0 || author.getStatus()==-1){
            return Result.fail("账号未审核，无法登录");
        }

        // TODO 3.保存用户信息到 redis中

        // TODO 随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);//不带中划线-

        // TODO 将user对象转换成hashmap储存

        Map<String, Object> authormap = BeanUtil.beanToMap(author, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setIgnoreProperties("proof")
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // TODO 存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, authormap);

        // TODO 设置token有效期 分钟
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // TODO 4.返回
        return Result.ok(token);
    }

    @Override
    public Result checkPhone(String phone) {
        // TODO 1.查询数据库是否存在手机号
        Author author = query().eq("phone", phone).one();

        if(author==null){
            return Result.fail("手机号未注册账号");
        }

        // TODO 2.验证成功则从redis中删除使用的验证码
        Boolean delete = stringRedisTemplate.opsForValue().getOperations().delete(REG_CODE_KEY + phone);
        System.out.println("删除redis验证码"+delete);

        return Result.ok();
    }

    @Override
    public Result updatePwd(UpdatePwdDTO updatePwdDTO) {
        String phone = updatePwdDTO.getPhone();
        String password = updatePwdDTO.getPassword();
        String confirmPwd = updatePwdDTO.getConfirmPwd();

        if(!confirmPwd.equals(password)){
            return Result.fail("密码不一致");
        }

        Author author = query().eq("phone", phone).one();
        String sqlPWD = author.getPassword();

        if(password.equals(sqlPWD) && confirmPwd.equals(sqlPWD)){
            return Result.fail("新密码不能与原密码相同");
        }

        UpdateWrapper<Author> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password",password).eq("phone",phone);

        Boolean update =update(updateWrapper);
        System.out.println("修改密码"+update);

        return Result.ok();
    }

    @Override
    public Result authorReg(AuthorDTO authorDTO) {

        // TODO 1.校验手机号
        String phone = authorDTO.getPhone();
//        if (RegexUtils.isPhoneInvalid(phone)) {
//            // TODO 2.如果不符合，返回错误信息
//            return Result.fail("手机号格式错误！");
//        }
        // TODO 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(REG_CODE_KEY + phone);
        System.out.println(cacheCode);
        String code = authorDTO.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // TODO 不一致，报错
            return Result.fail("验证码错误");
        }

        // TODO 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        Author author = query().eq("phone", phone).one();

        // TODO 5.判断用户是否存在
        if (author == null) {
            // TODO 6.不存在，创建新用户并保存
            createAuthor(authorDTO);
        }else{
            return Result.fail("该手机号已注册");
        }

        // TODO 7.注册成功则从redis中删除使用的验证码
        Boolean delete = stringRedisTemplate.opsForValue().getOperations().delete(REG_CODE_KEY + phone);
        System.out.println("删除redis验证码"+delete);

        return Result.ok();
    }

    private Author createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setAuthorname(authorDTO.getAuthorname());
        author.setPassword(authorDTO.getPassword());
        author.setCompanyName(authorDTO.getCompanyName());
        author.setPhone(authorDTO.getPhone());
        author.setStatus(1);
        author.setProof(authorDTO.getProof());
        System.out.println("=================="+authorDTO.getProof());
        save(author);
        return author;
    }

    @Override
    public Result authorInfor(HttpServletRequest request) {

        // TODO 获取当前登录主办方的token
        String token = request.getHeader("token");
        String tokenKey = LOGIN_USER_KEY + token;
        String authorname = String.valueOf(stringRedisTemplate.opsForHash().get(tokenKey, "authorname"));

        // TODO 根据用户名查询数据
        LambdaQueryWrapper<Author> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Author::getAuthorname,authorname);

        List<Author> list = list(lambdaQueryWrapper);

        return Result.ok(list);
    }
}
