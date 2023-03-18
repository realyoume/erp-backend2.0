package com.yumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumi.entity.Account;
import com.yumi.entity.User;

import java.util.Map;


public interface AccountService extends IService<Account> {
    /**
     * 删除账户
     */
    void delete(Account account);

}
