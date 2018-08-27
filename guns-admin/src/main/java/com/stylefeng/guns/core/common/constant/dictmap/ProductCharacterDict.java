package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * Created by Heyifan Cotter on 2018/8/24.
 */
public class ProductCharacterDict extends AbstractDictMap {
    @Override
    public void init() {
        put("id","商品特性ID" );
    }

    @Override
    protected void initBeWrapped() {

    }
}
