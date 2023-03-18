package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Account;
import com.yumi.entity.User;
import com.yumi.entity.Worker;
import com.yumi.enums.Role;
import com.yumi.service.AccountService;
import com.yumi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Authorized(roles = {Role.GM, Role.FINANCIAL_STAFF})
    @GetMapping()
    public Response getAll(){
        List<Account> list = accountService.list();
        return Response.buildSuccess(list);
    }

    @Authorized(roles = {Role.GM, Role.FINANCIAL_STAFF})
    @PostMapping()
    public Response add(@RequestBody Account account){
        accountService.save(account);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.FINANCIAL_STAFF})
    @PostMapping("/delete")
    public Response delete(@RequestBody Account account){
        accountService.delete(account);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.FINANCIAL_STAFF})
    @PostMapping("/update")
    public Response update(@RequestBody Account account){

        accountService.updateById(account);
        return Response.buildSuccess();
    }
}
