package com.cxy.reggit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public R<String> exceptionHandler() {
    return R.error("失败了");
  }
}
