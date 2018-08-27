package com.stylefeng.guns.modular.product.service;

import com.stylefeng.guns.modular.system.model.Product;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
public interface IProductService extends IService<Product> {

    List<Map<String, Object>> list(@Param("condition") String condition);
}
