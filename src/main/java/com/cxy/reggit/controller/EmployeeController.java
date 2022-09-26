package com.cxy.reggit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cxy.reggit.common.R;
import com.cxy.reggit.entity.Employee;
import com.cxy.reggit.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

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
    // md5加密处理密码

    String password = employee.getPassword();
    password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    // 查询数据库
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Employee::getUsername, employee.getUsername());
    queryWrapper.eq(Employee::getPassword, password);
    Employee emp = employeeService.getOne(queryWrapper);
    if (emp == null) {
      return R.error("登录失败");
    }
    if (emp.getStatus() == 0) {
      return R.error("账号被禁用");
    }
    // 放到session中
    request.getSession().setAttribute("employee", emp.getId());
    return R.success(emp);
  }

  @PostMapping("/logout")
  public R<String> logout(HttpServletRequest request) {
    // 清理session中保存的id
    request.getSession().removeAttribute("employee");
    return R.success("退出成功");
  }

  @PostMapping
  public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
    //    log.info(employee.toString());
    // 验证账号有没有存在数据库
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Employee::getUsername, employee.getUsername());
    Employee emp = employeeService.getOne(queryWrapper);
    // 说明这个账号在数据库中已经存在跳过
    if (emp != null) {
      return R.error("账号已经存在");
    }
    // 初始密码
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
    //
    employee.setCreateTime(LocalDateTime.now());
    employee.setUpdateTime(LocalDateTime.now());
    employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
    employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
    // 添加到数据库
    employeeService.save(employee);
    return R.success("新增员工成功");
  }
}
