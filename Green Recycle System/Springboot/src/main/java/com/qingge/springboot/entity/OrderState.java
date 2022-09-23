package com.qingge.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 小黄同学
 * @since 2022-04-15
 */
@Getter
@Setter
  @TableName("order_state")
@ApiModel(value = "OrderState对象", description = "")
public class OrderState implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String state;


}
