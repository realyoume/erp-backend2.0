package com.yumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumi.entity.Account;
import com.yumi.entity.PurchaseSheet;
import com.yumi.entity.PurchaseSheetPO;

import java.util.List;


public interface PurchaseService extends IService<PurchaseSheetPO> {

    List<PurchaseSheet> getAll();

    void makePurchaseSheet(PurchaseSheet purchaseSheet);

    void approval(String purchaseSheetId, Integer state);

    void warehouseApproval(String purchaseSheetId, Integer state);

    void financialApproval(String purchaseSheetId, Integer state);

    void updateRedis();
}
