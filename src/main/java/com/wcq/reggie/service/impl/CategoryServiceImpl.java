package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.common.CustomException;
import com.wcq.reggie.entity.Category;
import com.wcq.reggie.entity.Dish;
import com.wcq.reggie.entity.Setmeal;
import com.wcq.reggie.mapper.CategoryMapper;
import com.wcq.reggie.service.CategoryService;
import com.wcq.reggie.service.DishService;
import com.wcq.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements
        CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    //根据id删除分类 删除之前进行判断
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联菜品 如果已经关联 抛出一个业务异常
        if (count1 > 0){
            //已经关联菜品 抛出异常
            throw new CustomException("当前分类下关联了菜品 不能删除");
        }
        //查询当前分类是否关联套餐 如果已经关联 抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0){
            //已经关联套餐 抛出异常
            throw new CustomException("当前套餐下关联了菜品 不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
