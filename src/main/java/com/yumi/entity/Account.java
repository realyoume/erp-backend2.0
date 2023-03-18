package com.yumi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class Account {
    /**
     * 账户id
     */
    @TableId(type = IdType.AUTO)
    Integer id;

    /**
     * 账户名称
     */
    String name;

    /**
     * 账户金额
     */
    Long amount;
}
