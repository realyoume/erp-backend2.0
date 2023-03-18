package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.Account;
import com.yumi.entity.SaleSheetPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SaleSheetMapper extends BaseMapper<SaleSheetPO> {

}
