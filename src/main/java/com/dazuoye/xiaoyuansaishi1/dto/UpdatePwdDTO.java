package com.dazuoye.xiaoyuansaishi1.dto;

import lombok.Data;

@Data
public class UpdatePwdDTO {
    private String phone;
    private String password;
    private String confirmPwd;
}
