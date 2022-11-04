package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.entity.User;
import com.wcq.reggie.mapper.UserMapper;
import com.wcq.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
}
