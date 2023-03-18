package com.yumi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yumi.entity.Account;
import com.yumi.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
