package com.cxy.reggit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@Slf4j
@ServletComponentScan
public class ReggitApplication {

  public static void main(String[] args) {

    SpringApplication.run(ReggitApplication.class, args);
    log.info("项目启动成功...");
  }
}
