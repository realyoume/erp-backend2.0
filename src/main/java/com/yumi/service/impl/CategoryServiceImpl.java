package com.yumi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.Category;
import com.yumi.entity.Worker;
import com.yumi.mapper.CategoryMapper;
import com.yumi.mapper.WorkerMapper;
import com.yumi.service.CategoryService;
import com.yumi.service.WorkerService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
