package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Account;
import com.yumi.entity.Category;
import com.yumi.enums.Role;
import com.yumi.service.AccountService;
import com.yumi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Authorized(roles = {Role.GM, Role.INVENTORY_MANAGER})
    @GetMapping("/query-all")
    public Response getAll(){
        List<Category> list = categoryService.list();
        return Response.buildSuccess(list);
    }

    @Authorized(roles = {Role.GM, Role.INVENTORY_MANAGER})
    @PostMapping("/create")
    public Response create(@RequestBody Category category){
        System.out.println(category);
        categoryService.save(category);
        return Response.buildSuccess();
    }


}
