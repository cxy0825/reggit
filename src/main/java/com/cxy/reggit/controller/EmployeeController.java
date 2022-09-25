package com.cxy.reggit.controller;

import com.cxy.reggit.common.R;
import com.cxy.reggit.entity.Employee;
import com.cxy.reggit.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired private EmployeeService employeeService;
  /**
   * 员工登录
   *
   * @param: request
   * @param: employee
   * @return: com.cxy.reggit.common.R<com.cxy.reggit.entity.Employee>
   */
  @PostMapping("/login")
  public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
    return null;
  }
}
