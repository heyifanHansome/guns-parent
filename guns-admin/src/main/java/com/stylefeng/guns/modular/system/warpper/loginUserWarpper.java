package com.stylefeng.guns.modular.system.warpper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.AreaTagMapper;
import com.stylefeng.guns.modular.system.model.AreaTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Heyifan Cotter on 2018/8/16.
 */
public class loginUserWarpper extends BaseControllerWarpper {
    public loginUserWarpper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        AreaTagMapper areaTagMapper = SpringContextHolder.getBean(AreaTagMapper.class);

        String provinceId = map.get("area_tag_province").toString();
        String cityId = map.get("area_tag_city").toString();
        Integer wechat = (Integer) map.get("wechat");

        if (wechat == 0) {
            map.put("wechatName", "否");
        } else {
            map.put("wechatName", "是");
        }

        if (ToolUtil.isEmpty(cityId)) {
            map.put("heyifan", "没有找到对应的市区信息");
        } else {
            List<AreaTag> cityTags = new ArrayList<>();
            EntityWrapper<AreaTag> cityWarpper = new EntityWrapper<>();
            cityWarpper.like("id", cityId);
            cityTags = areaTagMapper.selectList(cityWarpper);
            if (cityTags.size() > 0) {
                map.put("heyifan", cityTags.get(0).getName());
            }

        }


        if (ToolUtil.isEmpty(provinceId)) {
            map.put("provinceName", "没有找到对应的省份信息");
        } else {
            List<AreaTag> proviceTags = new ArrayList<>();
            EntityWrapper<AreaTag> proviceEnity = new EntityWrapper<>();
            proviceEnity.like("id", provinceId);
            proviceTags = areaTagMapper.selectList(proviceEnity);
            if (proviceTags.size() > 0) {
                map.put("provinceName", proviceTags.get(0).getName());
            }

        }

    }
}
