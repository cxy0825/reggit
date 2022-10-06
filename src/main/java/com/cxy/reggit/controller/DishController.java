package com.cxy.reggit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.reggit.common.R;
import com.cxy.reggit.dto.DishDto;
import com.cxy.reggit.entity.Dish;
import com.cxy.reggit.service.CategoryService;
import com.cxy.reggit.service.DishFlavorService;
import com.cxy.reggit.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/dish")
@RestController
@Slf4j
public class DishController {
  @Autowired private DishFlavorService dishFlavorService;
  @Autowired private DishService dishService;
  @Autowired private CategoryService categoryService;
  // 新增菜品
  @PostMapping
  private R<String> save(@RequestBody DishDto dishDto) {
    //    log.info(dishDto.toString());
    dishService.saveWithFlavor(dishDto);
    return R.success("新增菜品成功");
  }
  // 更新菜品
  @PutMapping
  private R<String> update(@RequestBody DishDto dishDto) {
    log.info(dishDto.toString());
    //    dishService.updateWithFlavor(dishDto);
    //    return R.success("新增菜品成功");
    return null;
  }

  // 根据菜品id查询菜品信息和对应的口味
  @GetMapping("/{id}")
  private R<DishDto> get(@PathVariable("id") Long id) {
    DishDto dishDto = dishService.selectByIdWithFlavor(id);

    return R.success(dishDto);
  }

  @GetMapping("page")
  public R<Page> page(
      @RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name) {

    Page<DishDto> dishDtoPage = new Page<>(page, pageSize);
    dishService.selectDishWithCategoryName(dishDtoPage, name);
    return R.success(dishDtoPage);

    //    Page<Dish> pageInfo = new Page<Dish>(page, pageSize);
    //    // 因为Dish这个类中没有所需要的东西，所以创建了一个DishDto去继承Dish拓展属性
    //    Page<DishDto> dishDtoPage = new Page<DishDto>();
    //
    //    LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    //    queryWrapper.like(!StringUtils.isEmpty(name), Dish::getName, name);
    //    // 排序
    //    queryWrapper.orderByDesc(Dish::getUpdateTime);
    //    dishService.page(pageInfo, queryWrapper);
    //    // 对象拷贝
    //    BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
    //    List<Dish> records = pageInfo.getRecords();
    //    List<DishDto> list =
    //        records.stream()
    //            .map(
    //                (item) -> {
    //                  DishDto dishDto = new DishDto();
    //                  BeanUtils.copyProperties(item, dishDto);
    //                  Long categoryId = item.getCategoryId();
    //                  Category category = categoryService.getById(categoryId);
    //                  String categoryName = category.getName();
    //                  dishDto.setCategoryName(categoryName);
    //                  return dishDto;
    //                })
    //            .collect(Collectors.toList());
    //    dishDtoPage.setRecords(list);
    //    return R.success(dishDtoPage);
  }

  // 根据菜品分类id获取分类下面有什么菜品
  @GetMapping("list")
  private R<List<Dish>> list(Dish dish) {
    // 根据分类id去查询菜品
    LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dishLambdaQueryWrapper.eq(
        dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
    dishLambdaQueryWrapper.eq(Dish::getStatus, 1);
    dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime);
    List<Dish> list = dishService.list(dishLambdaQueryWrapper);
    return R.success(list);
  }
}
