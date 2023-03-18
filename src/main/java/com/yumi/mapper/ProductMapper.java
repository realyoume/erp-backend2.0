package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.Account;
import com.yumi.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}
