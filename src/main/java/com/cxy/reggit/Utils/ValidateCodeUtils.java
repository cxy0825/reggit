package com.cxy.reggit.Utils;

import java.util.Random;

/*
 *
 * 验证码工具类
 * @param: null
 * @return: null
 **/
public class ValidateCodeUtils {
  private static final int length = 6;

  public static String getValidateCode() {
    String validateCode = "";
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      validateCode += String.valueOf(random.nextInt(8) + 1);
    }

    return validateCode;
  }
}
