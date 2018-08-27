package com.stylefeng.guns.modular.system.warpper;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.CategoryMapper;
import com.stylefeng.guns.modular.system.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Heyifan Cotter on 2018/8/14.
 */
public class productWarpper extends BaseControllerWarpper {
    public productWarpper(Object list) {
        super(list);
    }


    @Override
    protected void warpTheMap(Map<String, Object> map) {
        CategoryMapper categoryMapper = SpringContextHolder.getBean(CategoryMapper.class);

        String id = map.get("category_id").toString();
        String type = map.get("type").toString();
        if(type.equals("hot")){
            map.put("newType","热销产品");
        }else {
            map.put("newType","推荐商品");
        }
        if (ToolUtil.isEmpty(id)) {
            map.put("categoryName", "没有对应的产品型号!");
        } else {
            List<Category> categories = new ArrayList<>();
            EntityWrapper<Category> entityWrapper = new EntityWrapper<>();
            entityWrapper.like("category_id", id);
            categories = categoryMapper.selectList(entityWrapper);
            if(categories.size()>0){
                map.put("categoryName", categories.get(0).getName());
            }else{
                map.put("categoryName", "没有对应的产品型号!");
            }

        }
    }
}
