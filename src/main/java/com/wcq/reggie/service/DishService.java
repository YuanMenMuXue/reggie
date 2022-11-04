package com.wcq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcq.reggie.dto.DishDto;
import com.wcq.reggie.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    //新增菜品 同时插入菜品对应的口味数据 需要操作两张表 dish dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void remove(Long id);

    List<Dish> getByName(String name);

}
