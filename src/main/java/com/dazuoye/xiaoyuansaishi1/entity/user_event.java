package com.dazuoye.xiaoyuansaishi1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class user_event {
    @TableId(type = IdType.ASSIGN_UUID)     //生成唯一id
    private Long id;
    private Long user_id;
    private Long event_id;
}
