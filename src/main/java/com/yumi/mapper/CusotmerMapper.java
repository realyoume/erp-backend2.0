package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.Account;
import com.yumi.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CusotmerMapper extends BaseMapper<Customer> {

}
