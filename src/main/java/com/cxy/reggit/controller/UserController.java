package com.cxy.reggit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cxy.reggit.Utils.DingDingUtils;
import com.cxy.reggit.Utils.ValidateCodeUtils;
import com.cxy.reggit.common.R;
import com.cxy.reggit.entity.User;
import com.cxy.reggit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
  @Autowired UserService userService;
  /*
   * 发送短信
   *
   * @param: user
   * @return: com.cxy.reggit.common.R<java.lang.String>
   **/
  @PostMapping("/sendMsg")
  public R<String> sendMsg(@RequestBody User user, HttpSession session) {
    // 获取手机号
    String phone = user.getPhone();
    if (StringUtils.isEmpty(phone)) {
      return R.error("手机号不正确");
    }

    // 生成验证码
    String validateCode = ValidateCodeUtils.getValidateCode();
    // 发送验证码短信
    DingDingUtils.sendMsg(validateCode + "\n5分钟内有效");
    // 保存到session
    session.setAttribute(phone, validateCode);
    session.setMaxInactiveInterval(5 * 60);
    return R.success("验证码发送成功");
  }

  // 登录功能 移动端用户登录
  @PostMapping("/login")
  public R<User> login(@RequestBody HashMap<String, String> map, HttpSession session) {
    // 获取手机号
    String phone = map.get("phone");
    // 获取验证码
    String code = map.get("code");
    if (StringUtils.isEmpty(code)) {
      return R.error("登录失败,验证码为空");
    }
    // 从session中获取保存的验证码
    Object attribute = session.getAttribute(phone);
    if (attribute == null || !attribute.equals(code)) {
      return R.error("登录失败,验证码错误");
    }

    // 与验证码进行比对
    if (attribute.equals(code)) {
      // 查询数据库，数据库中有没有这个用户，没有就是新用户，保存到数据库中
      LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
      userLambdaQueryWrapper.eq(User::getPhone, phone);
      User user = userService.getOne(userLambdaQueryWrapper);
      // 数量小于1就是新用户
      if (user == null) {
        user = new User();
        user.setPhone(phone);
        user.setStatus(1);
        userService.save(user);
      }
      session.setAttribute("user", user.getId());
      return R.success(user);
    }

    return R.error("登录失败");
  }
}
