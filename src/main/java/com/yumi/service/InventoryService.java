package com.yumi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yumi.entity.Account;
import com.yumi.entity.Inventory;

import java.text.ParseException;
import java.util.List;


public interface InventoryService extends IService<Inventory> {

    List<Inventory> getByCondition(String beginDate, String endDate, String pid) throws ParseException;
}
