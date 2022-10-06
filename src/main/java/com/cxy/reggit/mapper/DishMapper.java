package com.cxy.reggit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cxy.reggit.dto.DishDto;
import com.cxy.reggit.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

  IPage<DishDto> selectDishWithCategoryName(
      IPage<DishDto> page, @Param("typeName") String typeName);

  DishDto selectByIdWithFlavor(@Param("id") Long id);
}
