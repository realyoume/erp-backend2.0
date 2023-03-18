package com.yumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumi.entity.User;

import java.util.Map;


public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param user
     * @return
     */
    Map<String, String> login(User user);

    /**
     * 用户认证
     * @param token
     */
    User auth(String token);

    /**
     * 用户注册
     * @param user
     */
    void register(User user);
}
