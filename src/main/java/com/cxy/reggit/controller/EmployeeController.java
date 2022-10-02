package com.cxy.reggit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.reggit.common.R;
import com.cxy.reggit.entity.Employee;
import com.cxy.reggit.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

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
    //    employee.setCreateTime(LocalDateTime.now());
    //    employee.setUpdateTime(LocalDateTime.now());
    //    employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
    //    employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
    // 添加到数据库
    employeeService.save(employee);
    return R.success("新增员工成功");
  }

  /**
   * 分页请求+查询用户
   *
   * @param: page
   * @param: pageSize
   * @param: name
   * @return: com.cxy.reggit.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
   */
  @GetMapping("/page")
  public R<Object> page(
      @RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name) {
    //    log.info("{},{},{}", page, pageSize, name);
    Page<Employee> pageInfo = new Page<>(page, pageSize);
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<Employee>();

    queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
    queryWrapper.orderByDesc(Employee::getUpdateTime);
    Page<Employee> page1 = employeeService.page(pageInfo, queryWrapper);

    return R.success(page1);
  }
  /**
   * 根据id修改员工信息
   *
   * @param: employee
   * @return: com.cxy.reggit.common.R<java.lang.String>
   */
  @PutMapping
  public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
    log.info(employee.toString());
    //    Long employeeId = (Long) request.getSession().getAttribute("employee");
    //    employee.setUpdateTime(LocalDateTime.now());
    //    employee.setUpdateUser(employeeId);
    employeeService.updateById(employee);
    return R.success("员工信息修改成功");
  }

  @GetMapping("/{id}")
  public R<Employee> getById(@PathVariable("id") Long id) {
    Employee employee = employeeService.getById(id);
    if (employee != null) {
      return R.success(employee);
    }
    return R.error("没有找到员工信息");
  }
}
