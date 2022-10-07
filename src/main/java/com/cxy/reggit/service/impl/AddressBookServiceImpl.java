package com.cxy.reggit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxy.reggit.entity.AddressBook;
import com.cxy.reggit.mapper.AddressBookMapper;
import com.cxy.reggit.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService {}
