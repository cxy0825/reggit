package com.cxy.reggit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.reggit.common.CustomException;
import com.cxy.reggit.entity.Category;
import com.cxy.reggit.entity.Dish;
import com.cxy.reggit.entity.Setmeal;
import com.cxy.reggit.mapper.CategoryMapper;
import com.cxy.reggit.service.CategoryService;
import com.cxy.reggit.service.DishService;
import com.cxy.reggit.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServerImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {
  @Autowired private DishService dishService;

  @Autowired private SetmealService setmealService;
  /** 根据id删除分类，删除前需要判断是否关联了其他菜品 */
  @Override
  public void remove(Long id) {
    // 查询当前分类是否关联菜品
    LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
    dishQueryWrapper.eq(Dish::getCategoryId, id);
    int count1 = dishService.count(dishQueryWrapper);
    if (count1 > 0) {
      // 已经关联了菜品
      throw new CustomException("当前分类下关联了菜品不能删除");
    }
    // 查询售后关联套餐
    LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
    setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
    int count2 = setmealService.count(setmealLambdaQueryWrapper);

    if (count2 > 0) {
      // 已经关联了套餐
      throw new CustomException("当前分类下关联了套餐不能删除");
    }
    // 正常删除
    super.removeById(id);
  }
}
