package com.cxy.reggit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxy.reggit.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {}
