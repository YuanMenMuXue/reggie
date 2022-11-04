package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.dto.DishDto;
import com.wcq.reggie.dto.SetmealDto;
import com.wcq.reggie.entity.Dish;
import com.wcq.reggie.entity.DishFlavor;
import com.wcq.reggie.entity.Setmeal;
import com.wcq.reggie.entity.SetmealDish;
import com.wcq.reggie.mapper.SetmealMapper;
import com.wcq.reggie.service.SetmealDishService;
import com.wcq.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        //保存套餐的基本信息 执行insert操作
        this.save(setmealDto);//操作setmeal表

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联关系 操作setmeal_dish  执行insert操作
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    public void removeWithDish(Long id) {

        if(this.getById(id).getStatus()==0) {
            //删除改套餐
            this.removeById(id);
            //删除表 SetmealDish中的关联数据
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(queryWrapper);
        }
    }

    @Override
    public SetmealDto getByIdWithDish(Long id) {

        Setmeal setmeal = this.getById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> dishes = setmealDishService.list(queryWrapper);

        setmealDto.setSetmealDishes(dishes);


        return setmealDto;
    }

    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        //更新dish表基本信息
        this.updateById(setmealDto);

        //清理当前菜品对应菜品
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);

        //添加当前提交的菜品数据
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        dishes = dishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(dishes);
    }
}
