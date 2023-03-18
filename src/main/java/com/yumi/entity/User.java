package com.yumi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yumi.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户身份
     */
    private Role role;

    /**
     * 用户密码
     */
    private String password;

}
