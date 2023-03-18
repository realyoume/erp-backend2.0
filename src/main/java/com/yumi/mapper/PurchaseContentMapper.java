package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.PurchaseSheet;
import com.yumi.entity.PurchaseSheetContent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseContentMapper extends BaseMapper<PurchaseSheetContent> {

}
