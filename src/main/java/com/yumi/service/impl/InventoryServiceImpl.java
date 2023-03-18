package com.yumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.Account;
import com.yumi.entity.Customer;
import com.yumi.entity.Inventory;
import com.yumi.mapper.AccountMapper;
import com.yumi.mapper.InventoryMapper;
import com.yumi.service.AccountService;
import com.yumi.service.InventoryService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
    @Override
    public List<Inventory> getByCondition(String beginDate, String endDate, String pid) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        QueryWrapper<Inventory> wrapper = new QueryWrapper<Inventory>();
        if(! beginDate.equals("")){
            Date begin = sdf.parse(beginDate);
            wrapper.gt("creat_time", begin);
        }

        if(! endDate.equals("")){
            Date end = sdf.parse(endDate);
            wrapper.lt("creat_time", end);
        }

        if(! pid.equals("0")){
            wrapper.eq("pid", pid);
        }


        List<Inventory> list = this.list(wrapper);

        return list;
    }
}
