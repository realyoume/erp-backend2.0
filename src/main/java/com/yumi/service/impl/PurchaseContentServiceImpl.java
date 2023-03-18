package com.yumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.Account;
import com.yumi.entity.PurchaseSheet;
import com.yumi.entity.PurchaseSheetContent;
import com.yumi.mapper.PurchaseContentMapper;
import com.yumi.mapper.PurchaseMapper;
import com.yumi.service.PurchaseContentService;
import com.yumi.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseContentServiceImpl extends ServiceImpl<PurchaseContentMapper, PurchaseSheetContent> implements PurchaseContentService {

}
