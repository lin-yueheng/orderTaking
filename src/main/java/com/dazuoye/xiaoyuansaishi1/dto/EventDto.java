package com.dazuoye.xiaoyuansaishi1.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    @ApiModelProperty(value = "赛事名称")
    private String name;

    @ApiModelProperty(value = "赛事类型")
    private String type;

    @ApiModelProperty(value = "赛事赛制")
    private String format;

    @ApiModelProperty(value = "赛场")
    private String competitionName;

    @ApiModelProperty(value = "安排")
    private String schedule;

    @ApiModelProperty(value = "比赛审核状态：-1：审核未通过；0：待审核；1：审核已通过")
    private int status;

    @ApiModelProperty(value = "报名开始时间")
    private Date registration1;

    @ApiModelProperty(value = "报名截止时间")
    private Date registration2;

    @ApiModelProperty(value = "举办开办时间")
    private Date holding1;

    @ApiModelProperty(value = "举办结束时间")
    private Date holding2;
}
