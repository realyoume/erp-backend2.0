package com.yumi.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工人id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 工人名称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birth;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色
     */
    private String role;

    /**
     * 账户
     */
    private String account;
}

