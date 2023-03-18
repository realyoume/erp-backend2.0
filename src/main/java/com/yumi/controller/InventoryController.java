package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Account;
import com.yumi.entity.Inventory;
import com.yumi.enums.Role;
import com.yumi.service.AccountService;
import com.yumi.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

   @Autowired
    private InventoryService inventoryService;

   @GetMapping("query-all")
    public Response getAll(@RequestParam("beginDate") String beginDate, @RequestParam("endDate")  String endDate,@RequestParam("pid")  String pid) throws ParseException {
       List<Inventory> list = inventoryService.getByCondition(beginDate, endDate, pid);
       return Response.buildSuccess(list);
   }
}
