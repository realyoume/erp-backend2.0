package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.Account;
import com.yumi.entity.PurchaseSheet;
import com.yumi.entity.PurchaseSheetPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseMapper extends BaseMapper<PurchaseSheetPO> {

}
