package com.wcq.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcq.reggie.entity.AddressBook;
import com.wcq.reggie.mapper.AddressBookMapper;
import com.wcq.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
implements AddressBookService {
}
