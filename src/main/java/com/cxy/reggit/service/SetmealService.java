package com.cxy.reggit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.reggit.dto.SetmealDto;
import com.cxy.reggit.entity.Setmeal;
import org.apache.ibatis.annotations.Param;

public interface SetmealService extends IService<Setmeal> {
  /** 新增套餐，同时需要保存套餐和菜品的关联 */
  public void saveWithDish(SetmealDto setmealDto);

  Page<SetmealDto> selectWithSetmeal(Page<Setmeal> page, @Param("name") String name);
}
