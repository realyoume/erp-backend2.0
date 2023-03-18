package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Account;
import com.yumi.entity.PurchaseSheet;
import com.yumi.enums.Role;
import com.yumi.service.AccountService;
import com.yumi.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/sheet-show")
    public Response getAll()  {
        return Response.buildSuccess(purchaseService.getAll());
    }

    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER})
    @PostMapping(value = "/sheet-make")
    public Response makePurchaseOrder(@RequestBody PurchaseSheet purchaseSheet)  {
        purchaseService.makePurchaseSheet(purchaseSheet);
        purchaseService.updateRedis();
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.SALE_MANAGER})
    @GetMapping("/approval")
    public Response approval(@RequestParam("purchaseSheetId") String purchaseSheetId, @RequestParam("state") Integer state){
        purchaseService.approval(purchaseSheetId, state);
        purchaseService.updateRedis();
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.INVENTORY_MANAGER})
    @GetMapping("/warehouse/approval")
    public Response warehouseApproval(@RequestParam("purchaseSheetId") String purchaseSheetId, @RequestParam("state") Integer state){
        purchaseService.warehouseApproval(purchaseSheetId, state);
        purchaseService.updateRedis();
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.FINANCIAL_STAFF})
    @GetMapping("/financial/approval")
    public Response financialApproval(@RequestParam("purchaseSheetId") String purchaseSheetId, @RequestParam("state") Integer state){
        purchaseService.financialApproval(purchaseSheetId, state);
        purchaseService.updateRedis();
        return Response.buildSuccess();
    }
}
