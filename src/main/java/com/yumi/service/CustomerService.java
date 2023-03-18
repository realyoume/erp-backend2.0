package com.yumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumi.entity.Account;
import com.yumi.entity.Customer;

import java.util.List;


public interface CustomerService extends IService<Customer> {
    List<Customer> getByType(String type);
}
