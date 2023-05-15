package com.dazuoye.xiaoyuansaishi1.entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 比赛
 * </p>
 *
 * @author ${author}
 * @since 2023-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Event对象", description="比赛")
public class Event implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "赛事名称")
    private String name;

    @ApiModelProperty(value = "赛事类型")
    private String type;

    @ApiModelProperty(value = "赛事赛制")
    private String format;

    @ApiModelProperty(value = "报名开始时间")
    private Date registration1;

    @ApiModelProperty(value = "报名截止时间")
    private Date registration2;

    @ApiModelProperty(value = "举办开办时间")
    private Date holding1;

    @ApiModelProperty(value = "举办结束时间")
    private Date holding2;

    @ApiModelProperty(value = "场馆id")
    private Long venueId;

    @ApiModelProperty(value = "赛事介绍")
    private String introduction;

    @ApiModelProperty(value = "赛事规则")
    private String rule;

    @ApiModelProperty(value = "报名要求")
    private String demand;

    @ApiModelProperty(value = "赛事奖项")
    private String awards;

    @ApiModelProperty(value = "图片")
    private String picture;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "比赛审核状态：-1：审核未通过；0：待审核；1：审核已通过")
    private int status;

    private String createUser;

    private String updateUser;

}
