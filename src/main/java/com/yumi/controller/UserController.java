package com.yumi.controller;

import com.yumi.entity.User;
import com.yumi.entity.Worker;
import com.yumi.service.UserService;
import com.yumi.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response userLogin(@RequestBody User user) {
        return Response.buildSuccess(userService.login(user));
    }

    @GetMapping("/auth")
    public Response userAuth(@RequestParam(name = "token") String token) {
        return Response.buildSuccess(userService.auth(token));
    }

    @PostMapping("/register")
    public Response userRegister(@RequestBody User user) {
        userService.register(user);
        return Response.buildSuccess();
    }
}
