package com.wcq.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcq.reggie.common.R;
import com.wcq.reggie.dto.DishDto;
import com.wcq.reggie.dto.SetmealDto;
import com.wcq.reggie.entity.Category;
import com.wcq.reggie.entity.Dish;
import com.wcq.reggie.entity.Setmeal;
import com.wcq.reggie.service.CategoryService;
import com.wcq.reggie.service.SetmealDishService;
import com.wcq.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");

    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        //构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dishDtoPage = new Page<>();

        //构造条件构造器 name
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        //执行查询
        setmealService.page(pageInfo, queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto SetmealDto = new SetmealDto();

            BeanUtils.copyProperties(item, SetmealDto);

            Long categoryId = item.getCategoryId();//分类id

            Category category = categoryService.getById(categoryId);//根据分类id查询分类对象
            if (category != null) {
                String categoryName = category.getName();
                SetmealDto.setCategoryName(categoryName);
            }

            return SetmealDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @DeleteMapping
    public R<String> delete(Long[] ids) {

        for (Long id : ids) {
            setmealService.removeWithDish(id);
            return R.success("删除分类成功");
        }
        return R.error("删除失败");
    }

    @PostMapping("/status/0")
    public R<String> update0(Long[] ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(0);
            setmealService.updateById(setmeal);
        }
        return R.success("停售成功");
    }

    @PostMapping("/status/1")
    public R<String> update1(Long[] ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(1);
            setmealService.updateById(setmeal);
        }
        return R.success("启售成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        log.info("根据id查询套餐");
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);

        return R.success(setmealDto );

    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());

        setmealService.updateWithDish(setmealDto);


        return R.success("修改套餐成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(setmeal.getCategoryId()!= null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lambdaQueryWrapper.eq(setmeal.getStatus()!= null,Setmeal::getStatus,setmeal.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(lambdaQueryWrapper);
        return  R.success(list);
    }
}
