package com.yumi.auth;

import com.yumi.enums.Role;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorized {
    Role[]  roles() default {
            Role.ADMIN, Role.SALE_STAFF, Role.INVENTORY_MANAGER, Role.FINANCIAL_STAFF, Role.HR, Role.GM};
}
