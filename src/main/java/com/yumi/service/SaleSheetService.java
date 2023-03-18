package com.yumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumi.entity.Product;
import com.yumi.entity.SaleSheet;
import com.yumi.entity.SaleSheetPO;

import java.util.List;


public interface SaleSheetService extends IService<SaleSheetPO> {
    List<SaleSheet> getAllSheet();

    void approval(String saleSheetId, Integer state);

    void sheetMake(SaleSheet saleSheet);

    void warehouseApproval(String saleSheetId, Integer state);

    void financialApproval(String saleSheetId, Integer state);

    void updateRedis();
}
