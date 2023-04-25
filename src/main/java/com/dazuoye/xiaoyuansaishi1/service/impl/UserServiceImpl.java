package com.dazuoye.xiaoyuansaishi1.service.impl;

import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.User;
import com.dazuoye.xiaoyuansaishi1.mapper.UserMapper;
import com.dazuoye.xiaoyuansaishi1.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Result userReg(User user){
        save(user);
        return Result.ok();
    }
}
