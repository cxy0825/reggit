package com.cxy.reggit.dto;

import com.cxy.reggit.entity.Dish;
import com.cxy.reggit.entity.DishFlavor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
// @EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties
public class DishDto extends Dish implements Serializable {
  private List<DishFlavor> flavors = new ArrayList<>();
  private String categoryName;
  private Integer copies;

  @Override
  public String toString() {
    return "DishDto{"
        + "flavors="
        + flavors
        + ", categoryName='"
        + categoryName
        + '\''
        + ", copies="
        + copies
        + '}';
  }
}
