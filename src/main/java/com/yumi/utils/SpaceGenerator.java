package com.yumi.utils;

public class SpaceGenerator {
    /**
     * 生成固定长度的字符串
     * @param value
     * @param length
     * @return
     */
    public static String generate(Integer value, Integer length){
        String rs = String.valueOf(value);

        while (rs.length() < length){
            rs = "0" + rs;
        }
        return rs;
    }
}
