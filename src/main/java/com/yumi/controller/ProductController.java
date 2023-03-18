package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Account;
import com.yumi.entity.Product;
import com.yumi.enums.Role;
import com.yumi.service.AccountService;
import com.yumi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/query-all")
    public Response getAll(){
        List<Product> list = productService.list();
        return Response.buildSuccess(list);
    }

    @Authorized(roles = {Role.GM, Role.INVENTORY_MANAGER})
    @PostMapping("/create")
    public Response create(@RequestBody Product product){
        productService.create(product);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.INVENTORY_MANAGER})
    @PostMapping("/delete")
    public Response delete(@RequestBody Product product){
        productService.removeById(product.getId());
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.INVENTORY_MANAGER})
    @PostMapping("/update")
    public Response update(@RequestBody Product product){
        productService.updateById(product);
        return Response.buildSuccess();
    }

}
