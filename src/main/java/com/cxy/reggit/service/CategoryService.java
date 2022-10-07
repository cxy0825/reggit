package com.cxy.reggit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxy.reggit.entity.Category;

public interface CategoryService extends IService<Category> {
  public void remove(Long id);
}
