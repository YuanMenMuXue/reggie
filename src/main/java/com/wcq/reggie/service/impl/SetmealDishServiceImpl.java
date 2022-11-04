package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.entity.SetmealDish;
import com.wcq.reggie.mapper.SetmealDishMapper;
import com.wcq.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish>
implements SetmealDishService {
}
