package com.yumi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.SaleSheetContent;
import com.yumi.entity.SaleSheetPO;
import com.yumi.mapper.SaleSheetContentMapper;
import com.yumi.mapper.SaleSheetMapper;
import com.yumi.service.SaleSheetContentService;
import com.yumi.service.SaleSheetService;
import org.springframework.stereotype.Service;

@Service
public class SaleSheetContentServiceImpl extends ServiceImpl<SaleSheetContentMapper, SaleSheetContent> implements SaleSheetContentService {

}
