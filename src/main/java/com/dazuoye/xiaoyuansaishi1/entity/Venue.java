package com.dazuoye.xiaoyuansaishi1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 比赛场馆
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Venue对象", description="比赛场馆")
public class Venue implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "场馆名字")
    private String name;

    @ApiModelProperty(value = "所属大学")
    private String university;

    @ApiModelProperty(value = "可用状态：0：不可用；1：可用；")
    private int status;


}
