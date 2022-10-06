package com.cxy.reggit.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/** 菜品口味 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DishFlavor implements Serializable {
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;

  @TableId private Long df_id;

  // 菜品id
  private Long dishId;

  // 口味名称
  private String name;

  // 口味数据list
  private String value;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

  @TableField(fill = FieldFill.INSERT)
  private Long createUser;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Long updateUser;

  // 是否删除
  private Integer isDeleted;

  //  private boolean showOption;
}