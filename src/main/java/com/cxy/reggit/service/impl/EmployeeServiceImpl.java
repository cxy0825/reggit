package com.cxy.reggit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.reggit.entity.Employee;
import com.cxy.reggit.mapper.EmployeeMapper;
import com.cxy.reggit.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService {}
