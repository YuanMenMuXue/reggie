package com.wcq.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcq.reggie.common.R;
import com.wcq.reggie.entity.Category;

import com.wcq.reggie.entity.Employee;
import com.wcq.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//分类管理
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody Category category){

        log.info("新增分类:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");

    }

    @DeleteMapping
    public R<String> delete(Long id){

        log.info("删除分类 id 为{}",id);
        categoryService.remove(id);
        return R.success("删除分类成功");
    }

    //Page是mybatisplus提供的类
    //员工信息分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器 name
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Category::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Category::getUpdateTime);

        //执行查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }



    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null , Category::getType , category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list=categoryService.list(queryWrapper);
        return R.success(list);
    }
}
