package com.dazuoye.xiaoyuansaishi1.service;

import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UpdatePwdDTO;
import com.dazuoye.xiaoyuansaishi1.dto.UserDTO;
import com.dazuoye.xiaoyuansaishi1.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-04-24
 */
public interface UserService extends IService<User> {

    Result userReg(UserDTO userdto);

    Result sendCode(String phone);

    Result userLogin(UserDTO userDTO);

    Result checkPhone(String phone);

    Result updatePwd(UpdatePwdDTO updatePwdDTO);
}
