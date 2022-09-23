package com.qingge.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-18
 */
@Getter
@Setter
  @TableName("charity_item")
@ApiModel(value = "CharityItem对象", description = "")
public class CharityItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer grade;

    private String cover;

    private Integer num;

    private String overview;

}
