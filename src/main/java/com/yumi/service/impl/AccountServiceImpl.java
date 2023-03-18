package com.yumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.Account;
import com.yumi.entity.User;
import com.yumi.entity.Worker;
import com.yumi.mapper.AccountMapper;
import com.yumi.mapper.WorkerMapper;
import com.yumi.service.AccountService;
import com.yumi.service.WorkerService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Override
    public void delete(Account account) {
        QueryWrapper<Account> wrapper = new QueryWrapper<Account>();

        wrapper.eq("id", account.getId());

        this.remove(wrapper);
    }
}
