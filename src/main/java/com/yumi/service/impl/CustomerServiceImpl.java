package com.yumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.Account;
import com.yumi.entity.Customer;
import com.yumi.mapper.AccountMapper;
import com.yumi.mapper.CusotmerMapper;
import com.yumi.service.AccountService;
import com.yumi.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl extends ServiceImpl<CusotmerMapper, Customer> implements CustomerService {

    @Override
    public List<Customer> getByType(String type) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<Customer>();

        wrapper.eq("type", type);

        return this.list(wrapper);
    }
}
