package com.cxy.reggit;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.cxy.reggit")
public class ReggitApplication {

  public static void main(String[] args) {

    SpringApplication.run(ReggitApplication.class, args);
    log.info("项目启动成功...");
  }
}