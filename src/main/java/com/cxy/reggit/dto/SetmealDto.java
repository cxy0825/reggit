package com.cxy.reggit.dto;

import com.cxy.reggit.entity.Setmeal;
import com.cxy.reggit.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

  private List<SetmealDish> setmealDishes;

  private String categoryName;
}
