package com.dazuoye.xiaoyuansaishi1.controller;


import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UserDTO;
import com.dazuoye.xiaoyuansaishi1.dto.UserLoginDTO;
import com.dazuoye.xiaoyuansaishi1.entity.User;
import com.dazuoye.xiaoyuansaishi1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 学生注册
     * @param userdto
     * @return
     */
    @PostMapping("/userRegister")
    public Result userReg(@RequestBody UserDTO userdto){
        return userService.userReg(userdto);
    }

    /**
     * 注册发送验证码
     * @param phone
     * @return
     */
    @GetMapping("/code/{phone}")
    public Result sendCode(@PathVariable("phone") String phone) {
        // TODO 发送短信验证码并保存验证码
        return userService.sendCode(phone);
    }

    /**
     * 学生登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        // TODO 实现登录功能
        return userService.userLogin(userLoginDTO);
    }
}

