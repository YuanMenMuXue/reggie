package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.entity.DishFlavor;
import com.wcq.reggie.mapper.DishFlavorMapper;
import com.wcq.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService{
}
