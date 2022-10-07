package com.cxy.reggit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.reggit.dto.SetmealDto;
import com.cxy.reggit.entity.Setmeal;
import com.cxy.reggit.entity.SetmealDish;
import com.cxy.reggit.mapper.SetmealMapper;
import com.cxy.reggit.service.SetmealDishService;
import com.cxy.reggit.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService {
  @Autowired private SetmealDishService setmealDishService;
  @Autowired private SetmealMapper setmealMapper;

  @Override
  @Transactional
  public void saveWithDish(SetmealDto setmealDto) {
    // 保存套餐的基本信息。操作的是setmeal ，执行insert
    this.save(setmealDto);
    List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
    setmealDishes.stream()
        .map(
            (item) -> {
              item.setSetmealId(setmealDto.getId());
              return item;
            })
        .collect(Collectors.toList());
    // 保存套餐和菜品的关联信息，操作setmeal_dish执行insert
    setmealDishService.saveBatch(setmealDishes);
  }

  @Override
  public Page<SetmealDto> selectWithSetmeal(Page<Setmeal> page, String name) {
    return setmealMapper.selectWithSetmeal(page, name);
  }
}
