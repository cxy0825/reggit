package com.cxy.reggit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

  // 处理数据库主键数据重复
  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public R<String> SQLExceptionHandler(SQLIntegrityConstraintViolationException ex) {
    log.info(ex.getMessage().toString());
    if (ex.getMessage().contains("Duplicate entry")) {
      return R.error("数据库中已经存在这条数据");
    }
    return R.error("未知数据库错误");
  }

  @ExceptionHandler(CustomException.class)
  public R<String> exceptionHandler(CustomException ex) {
    log.error(ex.getMessage());
    return R.error(ex.getMessage());
  }
  //  @ExceptionHandler(RuntimeException.class)
  //  public R<String> exceptionHandler(Exception e) {
  //    //    log.info(e.getMessage().toString());
  //    return R.error("失败了");
  //  }
}
