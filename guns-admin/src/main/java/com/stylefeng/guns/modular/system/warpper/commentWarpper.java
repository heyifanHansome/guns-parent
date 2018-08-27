package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.system.dao.CommentMapper;
import com.stylefeng.guns.modular.system.dao.LoginUserMapper;
import com.stylefeng.guns.modular.system.dao.ProductMapper;
import com.stylefeng.guns.modular.system.model.LoginUser;
import com.stylefeng.guns.modular.system.model.Product;

import java.util.Map;

/**
 * Created by Heyifan Cotter on 2018/8/16.
 */
public class commentWarpper extends BaseControllerWarpper {
    public commentWarpper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        LoginUserMapper loginUserMapper = SpringContextHolder.getBean(LoginUserMapper.class);
        CommentMapper categoryMapper = SpringContextHolder.getBean(CommentMapper.class);
        ProductMapper productMapper = SpringContextHolder.getBean(ProductMapper.class);

        Integer id = (Integer) map.get("my_product_id");
        Integer userId = (Integer) map.get("user_id");
        Integer check_up = (Integer) map.get("check_up");

        if (id != null) {
            Product product = productMapper.selectById(id);
            map.put("productName", product);
        }
        if (userId != null) {
            LoginUser loginUser = loginUserMapper.selectById(userId);
            if(loginUser!=null){
                map.put("LoginUserName", loginUser.getName());
            }else {
                map.put("LoginUserName", "没有用户名");
            }

        }
        if (check_up != null) {
            if(check_up ==1){
                map.put("checkUpName" ,"是");
            }
            if(check_up ==0){
                map.put("checkUpName" ,"否");
            }
        }

    }
}
