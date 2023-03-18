package com.yumi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@TableName(value = "sale_sheet")
public class SaleSheetPO {
    /**
     * 销售单单据编号（格式为：XSD-yyyyMMdd-xxxxx
     */
    private String id;
    /**
     * 客户/销售商id
     */
    private Integer supplier;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 总额
     */
    private BigDecimal totalAmount;
    /**
     * 单据状态
     */
    private Integer state;
    /**
     * 单据状态
     */
    private Integer warehouseState;
    /**
     * 单据状态
     */
    private Integer financialState;
    /**
     * 创建时间
     */
    private Date createTime;
}
