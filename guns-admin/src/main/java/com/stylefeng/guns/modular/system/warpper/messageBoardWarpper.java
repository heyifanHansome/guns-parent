package com.stylefeng.guns.modular.system.warpper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.system.dao.AreaTagMapper;
import com.stylefeng.guns.modular.system.dao.ProductTagMapper;
import com.stylefeng.guns.modular.system.model.AreaTag;
import com.stylefeng.guns.modular.system.model.ProductTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Heyifan Cotter on 2018/8/17.
 */
public class messageBoardWarpper extends BaseControllerWarpper {
    public messageBoardWarpper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        ProductTagMapper productTagMapper = SpringContextHolder.getBean(ProductTagMapper.class);
        AreaTagMapper areaTagMapper = SpringContextHolder.getBean(AreaTagMapper.class);
        EntityWrapper<ProductTag> entityWrapper = new EntityWrapper<>();
        List<ProductTag> productTags = new ArrayList<>();
        String province = map.get("province").toString();
        String city = map.get("city").toString();

        if(province !=null){
             EntityWrapper<AreaTag> areaTagProvinceEntityWrapper = new EntityWrapper<>();
            areaTagProvinceEntityWrapper.like("id",province);
             List<AreaTag> areaTags = areaTagMapper.selectList(areaTagProvinceEntityWrapper);
             if(areaTags.size()>0){
                 map.put("provinceName", areaTags.get(0).getName());
             }else {
                 map.put("provinceName","--");
             }
        }

        if(city !=null){
            EntityWrapper<AreaTag> areaTagCityEntityWrapper = new EntityWrapper<>();
            areaTagCityEntityWrapper.like("id",city);
            List<AreaTag> cityAreaTags = areaTagMapper.selectList(areaTagCityEntityWrapper);
            if(cityAreaTags.size()>0){
                map.put("cityName", cityAreaTags.get(0).getName());
            }else {
                map.put("cityName","--");
            }
        }
    }
}
