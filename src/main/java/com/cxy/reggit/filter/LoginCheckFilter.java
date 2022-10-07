package com.cxy.reggit.filter;

import com.alibaba.fastjson.JSON;
import com.cxy.reggit.common.BaseContext;
import com.cxy.reggit.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 用户登录检查
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
  // 路径匹配器，支持通配符
  public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    // 获得本次请求的ur
    String requestURL = request.getRequestURI();
    log.info("拦截请求：{}", request.getRequestURI());
    // 不被拦截的请求列表
    String[] urls =
        new String[] {
          "/employee/login",
          "/employee/logout",
          "/backend/**",
          "/front/**",
          "/user/sendMsg",
          "/user/login"
        };
    boolean check = check(urls, requestURL);
    // 如果本次请求不需要处理就返回
    if (check) {
      filterChain.doFilter(request, response);
      return;
    }
    // 已经登录过了就放行
    Long userId = (Long) request.getSession().getAttribute("employee");
    Long userId2 = (Long) request.getSession().getAttribute("user");
    if (userId != null || userId2 != null) {
      BaseContext.setCurrentId(userId);
      filterChain.doFilter(request, response);
      return;
    }

    // 跳转到登录页面
    response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
  }

  public boolean check(String[] urls, String requestURL) {
    for (String url : urls) {
      boolean match = PATH_MATCHER.match(url, requestURL);
      if (match) {
        return true;
      }
    }
    return false;
  }
}
