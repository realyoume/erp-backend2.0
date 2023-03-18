package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Customer;
import com.yumi.enums.Role;
import com.yumi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Authorized(roles = {Role.GM, Role.SALE_STAFF, Role.SALE_MANAGER})
    @GetMapping("/findByType")
    public Response getByType(@RequestParam("type") String type){
        List<Customer> customerList = customerService.getByType(type);
        return Response.buildSuccess(customerList);
    }

    @Authorized(roles = {Role.GM, Role.SALE_STAFF, Role.SALE_MANAGER})
    @PostMapping("/create")
    public Response getByType(@RequestBody Customer customer){
        customerService.save(customer);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.SALE_STAFF, Role.SALE_MANAGER})
    @PostMapping("/delete")
    public Response delete(@RequestBody Customer customer){
        customerService.removeById(customer.getId());
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.GM, Role.SALE_STAFF, Role.SALE_MANAGER})
    @PostMapping("/update")
    public Response update(@RequestBody Customer customer){
        customerService.updateById(customer);
        return Response.buildSuccess();
    }


}
