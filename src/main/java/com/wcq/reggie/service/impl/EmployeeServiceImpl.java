package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.entity.Employee;
import com.wcq.reggie.mapper.EmployeeMapper;
import com.wcq.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
implements EmployeeService {

}
