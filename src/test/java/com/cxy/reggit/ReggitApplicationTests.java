package com.cxy.reggit;

import com.cxy.reggit.Utils.ValidateCodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReggitApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  void dingding() {
    //    DingDingUtils.sendMsg("测试一下");
    System.out.println(ValidateCodeUtils.getValidateCode());
  }
}
