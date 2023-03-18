package com.yumi.controller;

import com.yumi.auth.Authorized;
import com.yumi.entity.Account;
import com.yumi.entity.SaleSheet;
import com.yumi.enums.Role;
import com.yumi.service.AccountService;
import com.yumi.service.SaleSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleSheetController {

    @Autowired
    private SaleSheetService saleSheetService;

    @GetMapping("/sheet-show")
    public Response getAll(){
       List<SaleSheet> list = saleSheetService.getAllSheet();
       return Response.buildSuccess(list);
   }

    @Authorized(roles = {Role.SALE_MANAGER})
    @GetMapping("/approval")
    public Response approval(@RequestParam("saleSheetId") String saleSheetId, @RequestParam("state") Integer state){
        saleSheetService.approval(saleSheetId, state);
        saleSheetService.updateRedis();
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.SALE_STAFF, Role.SALE_MANAGER})
    @PostMapping("/sheet-make")
    public Response SheetMake(@RequestBody SaleSheet saleSheet){
        saleSheetService.sheetMake(saleSheet);
        saleSheetService.updateRedis();
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.INVENTORY_MANAGER})
    @GetMapping("/warehouse/approval")
    public Response warehouseApproval(@RequestParam("saleSheetId") String saleSheetId, @RequestParam("state") Integer state){
        saleSheetService.warehouseApproval(saleSheetId, state);
        saleSheetService.updateRedis();
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.FINANCIAL_STAFF})
    @GetMapping("/financial/approval")
    public Response financialApproval(@RequestParam("saleSheetId") String saleSheetId, @RequestParam("state") Integer state){
        saleSheetService.financialApproval(saleSheetId, state);
        saleSheetService.updateRedis();
        return Response.buildSuccess();
    }
}
