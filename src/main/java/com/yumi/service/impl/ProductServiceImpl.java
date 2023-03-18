package com.yumi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yumi.entity.Account;
import com.yumi.entity.Category;
import com.yumi.entity.Product;
import com.yumi.mapper.AccountMapper;
import com.yumi.mapper.ProductMapper;
import com.yumi.service.AccountService;
import com.yumi.service.CategoryService;
import com.yumi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void create(Product product) {
        Integer categoryId = product.getCategoryId();

        Category category = categoryService.getById(categoryId);

        Integer itemCount = category.getItemCount() + 1;

         String temp1 = String.valueOf(categoryId);
         while (temp1.length() < 11){
             temp1 = "0" + temp1;
         }
        String temp2 = String.valueOf(itemCount);
         while (temp2.length() < 5){
             temp2 = "0" + temp2;
         }

         product.setId(temp1+temp2);
         product.setQuantity(0);

         this.save(product);

         category.setItemCount(itemCount);
         categoryService.updateById(category);
    }
}
