package com.cxy.reggit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.reggit.dto.DishDto;
import com.cxy.reggit.entity.Dish;
import com.cxy.reggit.entity.DishFlavor;
import com.cxy.reggit.mapper.DishMapper;
import com.cxy.reggit.service.DishFlavorService;
import com.cxy.reggit.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
  /**
   * 新增菜品，同时保存对应的口味数据
   *
   * @param: dishDto
   */
  @Autowired private DishFlavorService dishFlavorService;

  @Autowired private DishMapper dishMapper;

  @Override
  @Transactional
  public void saveWithFlavor(DishDto dishDto) {
    //      保存菜品
    this.save(dishDto);
    //    获取菜品id
    Long id = dishDto.getId();
    List<DishFlavor> flavors = dishDto.getFlavors();
    flavors =
        flavors.stream()
            .map(
                (item) -> {
                  item.setDishId(id);
                  return item;
                })
            .collect(Collectors.toList());
    dishFlavorService.saveBatch(flavors);
  }

  @Override
  public IPage<DishDto> selectDishWithCategoryName(IPage<DishDto> page, String name) {
    return dishMapper.selectDishWithCategoryName(page, name);
  }

  @Override
  public DishDto selectByIdWithFlavor(Long id) {
    return dishMapper.selectByIdWithFlavor(id);
  }
  // 更新口味表和菜品表
  @Override
  @Transactional
  public void updateWithFlavor(DishDto dishDto) {
    // 更新菜品表(dish)的基本信息
    this.updateById(dishDto);
    // 清理口味表中当前菜品的信息
    LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
    dishFlavorService.remove(queryWrapper);
    // 添加传递过来口味表的数据
    List<DishFlavor> flavors = dishDto.getFlavors();
    flavors =
        flavors.stream()
            .map(
                (item) -> {
                  item.setDishId(dishDto.getId());
                  return item;
                })
            .collect(Collectors.toList());
    //    log.info(String.valueOf(flavors));
    dishFlavorService.saveBatch(flavors);
  }
}
