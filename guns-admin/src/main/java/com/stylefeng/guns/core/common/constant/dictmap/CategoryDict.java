package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * Created by Heyifan Cotter on 2018/8/24.
 */
public class CategoryDict extends AbstractDictMap {

    @Override
    public void init() {
        put("name", "分类名称");
    }

    @Override
    protected void initBeWrapped() {
    }
}
