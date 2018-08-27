package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * Created by Heyifan Cotter on 2018/8/24.
 */
public class ProductDict extends AbstractDictMap {
    @Override
    public void init() {
        put("name", "产品名称");
    }

    @Override
    protected void initBeWrapped() {

    }
}
