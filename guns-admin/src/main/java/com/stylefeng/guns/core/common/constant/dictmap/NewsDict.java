package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * Created by Heyifan Cotter on 2018/8/24.
 */
public class NewsDict extends AbstractDictMap {
    @Override
    public void init() {
        put("title","新闻标题");
    }

    @Override
    protected void initBeWrapped() {

    }
}
