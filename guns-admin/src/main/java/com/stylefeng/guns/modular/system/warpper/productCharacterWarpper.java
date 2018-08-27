package com.stylefeng.guns.modular.system.warpper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.CategoryMapper;
import com.stylefeng.guns.modular.system.dao.ProductMapper;
import com.stylefeng.guns.modular.system.model.Category;
import com.stylefeng.guns.modular.system.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Heyifan Cotter on 2018/8/15.
 */
public class productCharacterWarpper   extends BaseControllerWarpper {
    public productCharacterWarpper(Object list) {
        super(list);
    }
    @Override
    protected void warpTheMap(Map<String, Object> map) {
        ProductMapper categoryMapper = SpringContextHolder.getBean(ProductMapper.class);
        String id = map.get("product_id").toString();
        if (ToolUtil.isEmpty(id)) {
            map.put("productName", "没有对应的产品型号!");
        } else {
            List<Product> products = new ArrayList<>();
            EntityWrapper<Product> entityWrapper = new EntityWrapper<>();
            entityWrapper.like("id", id);
            products = categoryMapper.selectList(entityWrapper);
            if(products.size()>0){
                map.put("productName", products.get(0).getName());
            }else{
                map.put("productName", "没有对应的产品名称!");
            }

        }
    }
}
