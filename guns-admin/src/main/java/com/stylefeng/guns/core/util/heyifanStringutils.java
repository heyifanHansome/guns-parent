package com.stylefeng.guns.core.util;

/**
 * Created by Heyifan Cotter on 2018/8/17.
 */
public class heyifanStringutils {
    public heyifanStringutils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
