package com.cxy.reggit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.reggit.common.R;
import com.cxy.reggit.entity.Category;
import com.cxy.reggit.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
  @Autowired private CategoryService categoryService;
  // 查询菜品分类
  @PostMapping
  public R<String> save(@RequestBody Category category) {

    boolean hasSave = categoryService.save(category);
    if (hasSave) {
      return R.success("增加成功");
    }
    return R.error("新增菜品失败");
  }

  // 菜品分页
  @GetMapping("/page")
  public R<Object> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
    Page<Category> pageInfo = new Page<Category>(page, pageSize);
    LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>();
    queryWrapper.orderByAsc(Category::getType);
    queryWrapper.orderByAsc(Category::getSort);

    categoryService.page(pageInfo, queryWrapper);
    return R.success(pageInfo);
  }

  @DeleteMapping
  public R<String> delete(@RequestParam("ids") Long id) {
    log.info("删除分类，id为" + id);
    //
    categoryService.remove(id);
    return R.success("分类信息删除成功");
  }
}
