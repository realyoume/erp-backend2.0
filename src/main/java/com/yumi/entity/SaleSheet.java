package com.yumi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleSheet {

    /**
     * 进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx), 新建单据时前端传null
     */
    private String id;
    /**
     * 供应商id
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
     * 总额合计, 新建单据时前端传null(在后端计算总金额
     */
    private BigDecimal totalAmount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 单据状态, 新建单据时前端传null
     */
    private Integer state;
    /**
     * 单据出入库状态, 新建单据时前端传null
     */
    private Integer warehouseState;
    /**
     * 单据收付款状态, 新建单据时前端传null
     */
    private Integer financialState;
    /**
     * 进货单具体内容
     */
    List<SaleSheetContent> saleSheetContent;
}
