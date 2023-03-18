package com.yumi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.*;
import com.yumi.mapper.AccountMapper;
import com.yumi.mapper.SaleSheetMapper;
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
public class SaleSheetServiceImpl extends ServiceImpl<SaleSheetMapper, SaleSheetPO> implements SaleSheetService {

    @Autowired
    private SaleSheetContentService saleSheetContentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SaleSheet> getAllSheet() {
        String source  = (String) redisTemplate.opsForValue().get("saleSheetList");

        if(source == null){
            QueryWrapper<SaleSheetPO> wrapper1 = new QueryWrapper<SaleSheetPO>();
            wrapper1.orderByDesc("id");
            List<SaleSheetPO> saleSheetPOList = this.list(wrapper1);
            List<SaleSheet> result = new ArrayList<>();

            for (SaleSheetPO saleSheetPO : saleSheetPOList) {
                SaleSheet saleSheet = new SaleSheet();

                BeanUtils.copyProperties(saleSheetPO, saleSheet);

                QueryWrapper<SaleSheetContent> wrapper = new QueryWrapper<SaleSheetContent>();
                wrapper.eq("sale_sheet_id", saleSheet.getId());

                List<SaleSheetContent> list = saleSheetContentService.list(wrapper);

                saleSheet.setSaleSheetContent(list);
                result.add(saleSheet);
            }

            source = JSON.toJSONString(result);
            redisTemplate.opsForValue().set("saleSheetList", source, 10, TimeUnit.MINUTES );

            return result;
        }else {
            List<SaleSheet> result = JSONArray.parseArray(source, SaleSheet.class);

            return result;
        }


    }

    @Override
    public void approval(String saleSheetId, Integer state) {
        SaleSheetPO saleSheetPO = this.getById(saleSheetId);
        saleSheetPO.setState(state);
        if(state == 1){
            saleSheetPO.setWarehouseState(0);
        }
        this.updateById(saleSheetPO);
    }

    @Override
    public void sheetMake(SaleSheet saleSheet) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new Date());

        QueryWrapper<SaleSheetPO> wrapper = new QueryWrapper<SaleSheetPO>();
        wrapper.orderByDesc("id").last("limit 1");
        SaleSheetPO one = this.getOne(wrapper);
        String lastId = one.getId();

        String newId = null;
        if (lastId.split("-")[1].equals(today)){
            newId = "XSD-" + today + "-" + SpaceGenerator.generate(Integer.parseInt(lastId.split("-")[2])+1,5);
        }else {
            newId = "XSD-" + today + "-00001";
        }

        BigDecimal amount = BigDecimal.valueOf(0);
        List<SaleSheetContent> saleSheetContents = saleSheet.getSaleSheetContent();
        for (SaleSheetContent saleSheetContent : saleSheetContents) {
            saleSheetContent.setSaleSheetId(newId);

            saleSheetContentService.save(saleSheetContent);

            amount  = amount.add(saleSheetContent.getTotalPrice());
        }

        saleSheet.setId(newId);
        saleSheet.setTotalAmount(amount);
        saleSheet.setCreateTime(new Date());
        saleSheet.setState(0);
        saleSheet.setFinancialState(-1);
        saleSheet.setWarehouseState(-1);

        SaleSheetPO saleSheetPO = new SaleSheetPO();

        BeanUtils.copyProperties(saleSheet, saleSheetPO);

        this.save(saleSheetPO);
    }

    @Override
    public void warehouseApproval(String saleSheetId, Integer state) {
        SaleSheetPO saleSheetPO = this.getById(saleSheetId);
        saleSheetPO.setWarehouseState(state);
        if (state == 1){
            QueryWrapper<SaleSheetContent> wrapper = new QueryWrapper<SaleSheetContent>();
            wrapper.eq("sale_sheet_id", saleSheetId);

            List<SaleSheetContent> list = saleSheetContentService.list(wrapper);

            for (SaleSheetContent saleSheetContent : list) {
                QueryWrapper<Product> wrapper2 = new QueryWrapper<Product>();
                wrapper2.eq("id", saleSheetContent.getPid());

                Product product = productService.getOne(wrapper2);

                product.setQuantity(product.getQuantity() - saleSheetContent.getQuantity());

                productService.updateById(product);

                Inventory inventory = new Inventory();
                inventory.setCreatTime(new Date());
                inventory.setPid(product.getId());
                inventory.setQuantity(-saleSheetContent.getQuantity());
                inventory.setSheetId(saleSheetId);

                inventoryService.save(inventory);
            }

            saleSheetPO.setFinancialState(0);
        }

        this.updateById(saleSheetPO);
    }

    @Override
    public void financialApproval(String saleSheetId, Integer state) {
        SaleSheetPO saleSheetPO = this.getById(saleSheetId);
        saleSheetPO.setFinancialState(state);

        if (state == 1){
            Customer customer = customerService.getById(saleSheetPO.getSupplier());

            customer.setReceivable(customer.getReceivable().add(saleSheetPO.getTotalAmount()));

            customerService.updateById(customer);

            saleSheetPO.setState(3);
        }

        this.updateById(saleSheetPO);
    }

    @Override
    public void updateRedis() {
        QueryWrapper<SaleSheetPO> wrapper1 = new QueryWrapper<SaleSheetPO>();
        wrapper1.orderByDesc("id");
        List<SaleSheetPO> saleSheetPOList = this.list(wrapper1);
        List<SaleSheet> result = new ArrayList<>();

        for (SaleSheetPO saleSheetPO : saleSheetPOList) {
            SaleSheet saleSheet = new SaleSheet();

            BeanUtils.copyProperties(saleSheetPO, saleSheet);

            QueryWrapper<SaleSheetContent> wrapper = new QueryWrapper<SaleSheetContent>();
            wrapper.eq("sale_sheet_id", saleSheet.getId());

            List<SaleSheetContent> list = saleSheetContentService.list(wrapper);

            saleSheet.setSaleSheetContent(list);
            result.add(saleSheet);
        }

        String source = JSON.toJSONString(result);
        redisTemplate.opsForValue().set("saleSheetList", source, 10, TimeUnit.MINUTES );

        return;
    }
}
