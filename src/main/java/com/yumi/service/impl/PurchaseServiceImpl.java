package com.yumi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.*;
import com.yumi.mapper.AccountMapper;
import com.yumi.mapper.PurchaseMapper;
import com.yumi.service.*;
import com.yumi.utils.SpaceGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, PurchaseSheetPO> implements PurchaseService {
    @Autowired
    private PurchaseContentService purchaseContentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<PurchaseSheet> getAll() {
        String source  = (String) redisTemplate.opsForValue().get("purchaseSheetList");

        if(source == null){
            QueryWrapper<PurchaseSheetPO> wrapper1 = new QueryWrapper<>();

            wrapper1.orderByDesc("id");
            List<PurchaseSheetPO> purchaseSheetList = this.list(wrapper1);
            List<PurchaseSheet> result = new ArrayList<>();

            for (PurchaseSheetPO purchaseSheetPO : purchaseSheetList) {
                QueryWrapper<PurchaseSheetContent> wrapper = new QueryWrapper<>();

                wrapper.eq("purchase_sheet_id", purchaseSheetPO.getId() );

                List<PurchaseSheetContent> list = purchaseContentService.list(wrapper);

                PurchaseSheet purchaseSheet = new PurchaseSheet();

                BeanUtils.copyProperties(purchaseSheetPO, purchaseSheet);

                purchaseSheet.setPurchaseSheetContent(list);

                result.add(purchaseSheet);

            }

            source = JSON.toJSONString(result);
            redisTemplate.opsForValue().set("purchaseSheetList", source, 10, TimeUnit.MINUTES );

            return result;
        }else {
            List<PurchaseSheet> result = JSONArray.parseArray(source, PurchaseSheet.class);
            return result;
        }

    }

    @Override
    public void makePurchaseSheet(PurchaseSheet purchaseSheet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());

        QueryWrapper<PurchaseSheetPO> wrapper = new QueryWrapper<PurchaseSheetPO>();
        wrapper.orderByDesc("id").last("limit 1");
        PurchaseSheetPO one = this.getOne(wrapper);
        String lastId = one.getId();

        String newId = null;
        if (lastId.split("-")[1].equals(today)){
            newId = "JHD-" + today + "-" + SpaceGenerator.generate(Integer.parseInt(lastId.split("-")[2])+1,5);
        }else {
            newId = "JHD-" + today + "-00001";
        }

        BigDecimal amount = BigDecimal.valueOf(0);
        List<PurchaseSheetContent> purchaseSheetContent = purchaseSheet.getPurchaseSheetContent();
        for (PurchaseSheetContent sheetContent : purchaseSheetContent) {
            sheetContent.setPurchaseSheetId(newId);

            purchaseContentService.save(sheetContent);

            amount = amount.add(sheetContent.getTotalPrice());
        }

        purchaseSheet.setId(newId);
        purchaseSheet.setTotalAmount(amount);
        purchaseSheet.setState(0);
        purchaseSheet.setFinancialState(-1);
        purchaseSheet.setWarehouseState(-1);
        purchaseSheet.setCreateTime(new Date());

        PurchaseSheetPO purchaseSheetPO = new PurchaseSheetPO();

        BeanUtils.copyProperties(purchaseSheet, purchaseSheetPO);

        this.save(purchaseSheetPO);
    }

    @Override
    public void approval(String purchaseSheetId, Integer state) {
        PurchaseSheetPO purchaseSheetPO = this.getById(purchaseSheetId);
        purchaseSheetPO.setState(state);
        if (state == 1){
            purchaseSheetPO.setWarehouseState(0);
        }
        this.updateById(purchaseSheetPO);
    }

    @Override
    public void warehouseApproval(String purchaseSheetId, Integer state) {
        PurchaseSheetPO purchaseSheetPO = this.getById(purchaseSheetId);
        purchaseSheetPO.setWarehouseState(state);
        if (state == 1){
            QueryWrapper<PurchaseSheetContent> wrapper = new QueryWrapper<PurchaseSheetContent>();
            wrapper.eq("purchase_sheet_id", purchaseSheetId);
            List<PurchaseSheetContent> list = purchaseContentService.list(wrapper);

            for (PurchaseSheetContent purchaseSheetContent : list) {
                QueryWrapper<Product> wrapper2 = new QueryWrapper<Product>();
                wrapper2.eq("id", purchaseSheetContent.getPid());

                Product product = productService.getOne(wrapper2);

                product.setQuantity(product.getQuantity() + purchaseSheetContent.getQuantity());

                productService.updateById(product);

                Inventory inventory = new Inventory();
                inventory.setCreatTime(new Date());
                inventory.setPid(product.getId());
                inventory.setQuantity(purchaseSheetContent.getQuantity());
                inventory.setSheetId(purchaseSheetId);

                inventoryService.save(inventory);
            }

            purchaseSheetPO.setFinancialState(0);
        }
        this.updateById(purchaseSheetPO);
    }

    @Override
    public void financialApproval(String purchaseSheetId, Integer state) {
        PurchaseSheetPO purchaseSheetPO = this.getById(purchaseSheetId);
        purchaseSheetPO.setFinancialState(state);

        if (state == 1){
            Customer customer = customerService.getById(purchaseSheetPO.getSupplier());

            customer.setPayable(customer.getPayable().add(purchaseSheetPO.getTotalAmount()));

            customerService.updateById(customer);

            purchaseSheetPO.setState(3);
        }

        this.updateById(purchaseSheetPO);
    }

    @Override
    public void updateRedis() {
        QueryWrapper<PurchaseSheetPO> wrapper1 = new QueryWrapper<>();

        wrapper1.orderByDesc("id");
        List<PurchaseSheetPO> purchaseSheetList = this.list(wrapper1);
        List<PurchaseSheet> result = new ArrayList<>();

        for (PurchaseSheetPO purchaseSheetPO : purchaseSheetList) {
            QueryWrapper<PurchaseSheetContent> wrapper = new QueryWrapper<>();

            wrapper.eq("purchase_sheet_id", purchaseSheetPO.getId() );

            List<PurchaseSheetContent> list = purchaseContentService.list(wrapper);

            PurchaseSheet purchaseSheet = new PurchaseSheet();

            BeanUtils.copyProperties(purchaseSheetPO, purchaseSheet);

            purchaseSheet.setPurchaseSheetContent(list);

            result.add(purchaseSheet);

        }

        String source = JSON.toJSONString(result);
        redisTemplate.opsForValue().set("purchaseSheetList", source,10, TimeUnit.MINUTES);

        return;
    }
}
