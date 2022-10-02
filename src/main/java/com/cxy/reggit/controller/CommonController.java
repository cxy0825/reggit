package com.cxy.reggit.controller;

import com.cxy.reggit.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
  @Value("${reggie.path}")
  private String basePath;

  @PostMapping("upload")
  public R<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
    //    String fileName = file.getOriginalFilename();
    String suffix =
        file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    // 使用uuid重新生成文件名
    String fileName = UUID.randomUUID().toString();
    // 创建一个目录对象
    File dir = new File(basePath);
    if (!dir.exists()) {
      // 不存在目录就创建
      dir.mkdirs();
    }
    // 将临时文件转存到D盘
    file.transferTo(new File(basePath + fileName + suffix));
    return R.success(fileName + suffix);
  }

  @GetMapping("/download")
  public void download(String name, HttpServletResponse response) {
    // 输入流，通过输入流读取文件内容
    try {
      FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
      // 输出流，通过输出流将文件写回到浏览器
      ServletOutputStream outputStream = response.getOutputStream();
      response.setContentType("image/jpeg");
      int len = 0;
      byte[] bytes = new byte[1024];
      while ((len = fileInputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, len);
        outputStream.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
