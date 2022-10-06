package com.cxy.reggit.controller;

// 套餐管理

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxy.reggit.common.CustomException;
import com.cxy.reggit.common.R;
import com.cxy.reggit.dto.SetmealDto;
import com.cxy.reggit.entity.Setmeal;
import com.cxy.reggit.entity.SetmealDish;
import com.cxy.reggit.service.SetmealDishService;
import com.cxy.reggit.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
  @Autowired private SetmealService setmealService;
  @Autowired private SetmealDishService setmealDishService;

  // 新增套餐
  @PostMapping
  public R<String> save(@RequestBody SetmealDto setmealDto) {
    //    log.info(JSON.toJSONString(setmealDto, JSONWriter.Feature.PrettyFormat));
    setmealService.saveWithDish(setmealDto);
    return R.success("新增套餐成功");
  }
  /**
   * 分页查询
   *
   * @param: page
   * @param: pageSize
   * @param: name
   * @return: com.cxy.reggit.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
   */
  @GetMapping("/page")
  public R<Page> page(
      @RequestParam("page") int page, @RequestParam("pageSize") int pageSize, String name) {
    Page<Setmeal> setmealPage = new Page<>(page, pageSize);
    //    LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
    //    queryWrapper.eq(!StringUtils.isEmpty(name), Setmeal::getName, name);
    //    setmealService.page(setmealPage, queryWrapper);
    setmealService.selectWithSetmeal(setmealPage, name);
    return R.success(setmealPage);
  }
  // 根据id获取套餐信息
  @GetMapping("/{id}")
  public R<List<Setmeal>> setmealDetail(@PathVariable("id") Long id) {
    LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<Setmeal>();
    queryWrapper.eq(Setmeal::getId, id);
    List<Setmeal> list = setmealService.list(queryWrapper);
    return R.success(list);
  }
  // 删除套餐 （逻辑删除）
  @DeleteMapping
  public R<String> delete(@RequestParam("ids") List<Long> ids) {
    // 1.先判断该套餐是不是停售状态，如果是在售就不能删除
    // 2.如果不满足这些条件就删除

    // 1.先判断该套餐是不是停售状态
    LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
    setmealLambdaQueryWrapper.in(Setmeal::getId, ids);
    setmealLambdaQueryWrapper.eq(Setmeal::getStatus, 1);
    int count = setmealService.count(setmealLambdaQueryWrapper);
    if (count > 0) {
      throw new CustomException("有套餐正在出售，先停售才能删除");
    }
    // 2.获取ids在订单表的情况
    setmealService.removeByIds(ids);
    // 删除Setmeal_dish，根据dishid进行删除
    LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
    setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
    setmealDishService.remove(setmealDishLambdaQueryWrapper);
    return R.success("删除成功");
  }

  // 停售某个套餐
  @PostMapping("/status/{code}")
  public R<String> status(@PathVariable("code") int code, @RequestParam("ids") List<Long> ids) {

    LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.in(Setmeal::getId, ids).set(Setmeal::getStatus, code);
    setmealService.update(updateWrapper);
    return R.success("操作成功");
  }
}
