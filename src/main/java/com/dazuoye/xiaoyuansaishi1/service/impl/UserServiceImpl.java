package com.dazuoye.xiaoyuansaishi1.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UserDTO;
import com.dazuoye.xiaoyuansaishi1.dto.UserLoginDTO;
import com.dazuoye.xiaoyuansaishi1.entity.User;
import com.dazuoye.xiaoyuansaishi1.mapper.UserMapper;
import com.dazuoye.xiaoyuansaishi1.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dazuoye.xiaoyuansaishi1.utils.RegexUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.dazuoye.xiaoyuansaishi1.utils.RedisConstants.*;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-04-24
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result userReg(UserDTO userdto){
        // TODO 1.校验手机号
        String phone = userdto.getPhone();
//        if (RegexUtils.isPhoneInvalid(phone)) {
//            // TODO 2.如果不符合，返回错误信息
//            return Result.fail("手机号格式错误！");
//        }
        // TODO 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(REG_CODE_KEY + phone);
        System.out.println(cacheCode);
        String code = userdto.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // TODO 不一致，报错
            return Result.fail("验证码错误");
        }

        // TODO 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one();

        // TODO 5.判断用户是否存在
        if (user == null) {
            // TODO 6.不存在，创建新用户并保存
            createUser(userdto);
        }else{
            return Result.fail("该手机号已注册");
        }

        // TODO 7.注册成功则从redis中删除使用的验证码
        Boolean delete = stringRedisTemplate.opsForValue().getOperations().delete(REG_CODE_KEY + phone);
        System.out.println("删除redis验证码"+delete);

        return Result.ok();
    }

    private User createUser(UserDTO userdto) {
        User user = new User();
        user.setUsername(userdto.getUsername());
        user.setPassword(userdto.getPassword());
        user.setSchool(userdto.getSchool());
        user.setUserclass(userdto.getUserclass());
        user.setNumber(userdto.getNumber());
        user.setPhone(userdto.getPhone());
        user.setSex(userdto.getSex());
        save(user);
        return user;
    }

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
    public Result userLogin(UserLoginDTO userLoginDTO){

        // TODO 1.获取表单输入的用户名和密码
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        // TODO 2.判断用户是否存在
        User user = query().eq("username", username).eq("password",password).one();

        if(user == null){
            return Result.fail("用户不存在或输入有误");
        }

        // TODO 3.保存用户信息到 redis中

        // TODO 随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);//不带中划线-

        // TODO 将user对象转换成hashmap储存
        Map<String, Object> userMap = BeanUtil.beanToMap(user, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // TODO 存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);

        // TODO 设置token有效期 分钟
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // TODO 4.返回
        return Result.ok(token);

    }

}
