package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.Account;
import com.yumi.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

}
