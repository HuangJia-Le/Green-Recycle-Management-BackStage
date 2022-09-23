package com.qingge.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2022-04-23
 */
@Getter
@Setter
  @TableName("t_paper")
@ApiModel(value = "Paper对象", description = "")
public class Paper implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "pId", type = IdType.AUTO)
      private Integer pId;

      @ApiModelProperty("标题")
      private String title;

      @ApiModelProperty("1慈善文摘2文化中国3绿色回收")
      private Integer type;

      @ApiModelProperty("1社会新闻 2 热点聚焦 3新春走基层 4人物访谈 5关注民生 6社会观察 7生态环境 8绿色交通 9绿色食品 ")
      private Integer typeid;

      @ApiModelProperty("文摘内容")
      private String content;

      @ApiModelProperty("浏览量")
      private Integer view;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
      @ApiModelProperty("发布时间")
      private Date createtime;

      @ApiModelProperty("排序")
      private Integer sort;

      @ApiModelProperty("作者")
      private String author;


}
