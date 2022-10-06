package com.cxy.reggit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.reggit.dto.DishDto;
import com.cxy.reggit.entity.Dish;

public interface DishService extends IService<Dish> {
  // 新增菜品，同时插入菜品对应的口味数据，需要插座两张表 dish,dish_flavor

  void saveWithFlavor(DishDto dishDto);

  IPage<DishDto> selectDishWithCategoryName(IPage<DishDto> page, String name);

  DishDto selectByIdWithFlavor(Long id);

  void updateWithFlavor(DishDto dishDto);
}
