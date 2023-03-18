package com.yumi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSheetContent {
    /**
     * 自增id, 新建单据时前端传null
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 进货单id, 新建单据时前端传null
     */
    private String purchaseSheetId;
    /**
     * 商品id
     */
    private String pid;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 金额
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String remark;
}
