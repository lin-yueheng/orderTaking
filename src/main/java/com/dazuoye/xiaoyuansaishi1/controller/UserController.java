package com.dazuoye.xiaoyuansaishi1.controller;


import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UpdatePwdDTO;
import com.dazuoye.xiaoyuansaishi1.dto.UserDTO;
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
    @PostMapping("/register")
    public Result userReg(@RequestBody UserDTO userdto){
        return userService.userReg(userdto);
    }

    /**
     * 发送验证码
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
     * @param userDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        // TODO 实现登录功能
        return userService.userLogin(userDTO);
    }

    /**
     * 找回密码 步骤一 检验手机号
     * @param phone
     * @return
     */
    @GetMapping("/checkPhone/{phone}")
    public Result checkPhone(@PathVariable("phone") String phone){
        // TODO 检验手机号是否已注册
        return userService.checkPhone(phone);
    }

    /**
     * 更新密码
     * @param updatePwdDTO
     * @return
     */
    @PutMapping("/updatePWD")
    public Result updatePWD(@RequestBody UpdatePwdDTO updatePwdDTO){
        return userService.updatePwd(updatePwdDTO);
    }
}

