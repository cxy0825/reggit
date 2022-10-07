package com.cxy.reggit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.reggit.entity.DishFlavor;
import com.cxy.reggit.mapper.DishFlavorMapper;
import com.cxy.reggit.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService {}
