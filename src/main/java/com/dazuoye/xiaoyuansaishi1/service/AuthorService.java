package com.dazuoye.xiaoyuansaishi1.service;

import com.dazuoye.xiaoyuansaishi1.dto.AuthorDTO;
import com.dazuoye.xiaoyuansaishi1.dto.Result;
import com.dazuoye.xiaoyuansaishi1.dto.UpdatePwdDTO;
import com.dazuoye.xiaoyuansaishi1.entity.Author;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 主办方表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-04-29
 */
public interface AuthorService extends IService<Author> {

    Result sendCode(String phone);

    Result authorLogin(AuthorDTO authorDTO);

    Result checkPhone(String phone);

    Result updatePwd(UpdatePwdDTO updatePwdDTO);

    Result authorReg(AuthorDTO authorDTO);

    Result authorInfor(HttpServletRequest request);
}
