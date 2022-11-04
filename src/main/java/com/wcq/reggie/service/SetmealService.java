package com.wcq.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.wcq.reggie.dto.DishDto;
import com.wcq.reggie.dto.SetmealDto;
import com.wcq.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    //新增套餐 同时保存套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(Long id);

    public SetmealDto getByIdWithDish(Long id);

    public void updateWithDish(SetmealDto setmealDto);
}
