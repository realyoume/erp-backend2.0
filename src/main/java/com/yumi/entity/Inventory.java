package com.yumi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Inventory {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    Integer id;

    /**
     * 订单id
     */
    String sheetId;

    /**
     * 商品id
     */
    String pid;

    /**
     * 数量
     */
    Integer quantity;

    /**
     * 日期
     */
    Date creatTime;
}
