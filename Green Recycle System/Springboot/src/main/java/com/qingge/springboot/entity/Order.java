package com.qingge.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-14
 */
@Getter
@ToString
@Setter
@TableName("Allorder")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    private String address;

    private String user;

    private String company;

    private String orderItem;

    private String state;

    private String charityItem;

    private Integer orderNum;

    private Integer grade;

    private String type;

    private String telephone;

    private String description;


}
