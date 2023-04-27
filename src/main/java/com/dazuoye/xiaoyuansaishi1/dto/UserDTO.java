package com.dazuoye.xiaoyuansaishi1.dto;

import com.dazuoye.xiaoyuansaishi1.entity.User;
import lombok.Data;

@Data
public class UserDTO extends User {
    private String code;
}
