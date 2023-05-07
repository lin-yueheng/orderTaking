package com.dazuoye.xiaoyuansaishi1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主办方表
 * </p>
 *
 * @author ${author}
 * @since 2023-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Author对象", description="主办方表")
public class Author implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String authorname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "主办方名称")
    private String companyName;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "1:已审核;0:待审核;-1:未通过")
    private int status;

    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @ApiModelProperty(value = "相关材料")
    private String proof;


}
