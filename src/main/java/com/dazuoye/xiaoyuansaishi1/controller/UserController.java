package com.dazuoye.xiaoyuansaishi1.controller;


import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.entity.User;
import com.dazuoye.xiaoyuansaishi1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-04-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/userRegister")
    public Result userReg(@RequestBody User user){
        return userService.userReg(user);
    }

}

