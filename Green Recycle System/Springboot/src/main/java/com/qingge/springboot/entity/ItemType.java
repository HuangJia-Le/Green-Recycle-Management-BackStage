package com.qingge.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-04-18
 */
@Getter
@Setter
  @TableName("item_type")
@ApiModel(value = "ItemType对象", description = "")
public class ItemType implements Serializable {


    @TableId(type = IdType.AUTO)
      private Integer id;

    private String item;

      @ApiModelProperty("每斤物品所能兑换的公益积分")
      private Integer grade;

      @ApiModelProperty("种类Id")
      private Integer tid;


}
