package com.yumi.auth;

import com.yumi.entity.User;
import com.yumi.enums.Role;
import com.yumi.exception.MyServiceException;
import com.yumi.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
@Order(1)
public class AuthAspect {
    @Autowired
    private UserService userService;

    @Before(value = "@annotation(authorized)")
    public void authCheck(JoinPoint joinPoint, Authorized authorized){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String token = request.getHeader("Authorization");

            User user = userService.auth(token);

            List<Role> list = Arrays.asList(authorized.roles());

            if (! (list.contains(user.getRole()) || user.getRole().equals(Role.ADMIN))){
                throw new MyServiceException("A0003", "无权限");
            }

        }catch (MyServiceException e){
            throw new MyServiceException("A0004", "无权限");
        }
    }
}
