package com.dazuoye.xiaoyuansaishi1.controller;


import com.dazuoye.xiaoyuansaishi1.dto.AuthorDTO;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UpdatePwdDTO;
import com.dazuoye.xiaoyuansaishi1.dto.UserDTO;
import com.dazuoye.xiaoyuansaishi1.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>
 * 主办方表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-04-29
 */
@RestController
@RequestMapping("/author")
@CrossOrigin
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    /**
     * 主办方注册
     * @param authorDTO
     * @return
     */
    @PostMapping("/register")
    public Result authorReg(@RequestBody AuthorDTO authorDTO){
        return authorService.authorReg(authorDTO);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @GetMapping("/code/{phone}")
    public Result sendCode(@PathVariable("phone") String phone) {
        // TODO 发送短信验证码并保存验证码
        return authorService.sendCode(phone);
    }

    /**
     * 主办方登录
     * @param authorDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody AuthorDTO authorDTO){
        // TODO 实现登录功能
        return authorService.authorLogin(authorDTO);
    }

    /**
     * 找回密码 步骤一 检验手机号
     * @param phone
     * @return
     */
    @GetMapping("/checkPhone/{phone}")
    public Result checkPhone(@PathVariable("phone") String phone){
        // TODO 检验手机号是否已注册
        return authorService.checkPhone(phone);
    }

    /**
     * 更新密码
     * @param updatePwdDTO
     * @return
     */
    @PutMapping("/updatePWD")
    public Result updatePWD(@RequestBody UpdatePwdDTO updatePwdDTO){
        return authorService.updatePwd(updatePwdDTO);
    }

}

