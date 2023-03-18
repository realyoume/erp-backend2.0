package com.yumi.service.impl;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.config.JwtConfig;
import com.yumi.entity.User;
import com.yumi.enums.Role;
import com.yumi.exception.MyServiceException;
import com.yumi.mapper.UserMapper;
import com.yumi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public Map<String, String> login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        wrapper.eq("name", user.getName() ).eq("password", user.getPassword());

        User one = this.getOne(wrapper);

        if(one == null){
            throw new MyServiceException("A0000", "用户名或密码错误");
        }


        Map<String, String> authToken = new HashMap<>();
        String token = jwtConfig.createJWT(one);
        authToken.put("token", token);

        return authToken;
    }

    @Override
    public User auth(String token) {
        Map<String, Claim> claims = jwtConfig.parseJwt(token);
        User user = User.builder()
                .name(claims.get("name").as(String.class))
                .role(Role.valueOf(claims.get("role").as(String.class)))
                .build();

        return user;
    }

    @Override
    public void register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        wrapper.eq("name", user.getName() );

        User one = this.getOne(wrapper);

        if (one != null) {
            throw new MyServiceException("A0001", "用户名已存在");
        }

        this.save(user);
    }
}
