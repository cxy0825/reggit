package com.cxy.reggit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.reggit.dto.SetmealDto;
import com.cxy.reggit.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
  Page<SetmealDto> selectWithSetmeal(Page<Setmeal> page, @Param("name") String name);
}
