package com.stylefeng.guns.modular.product.service.impl;

import com.stylefeng.guns.modular.system.model.Product;
import com.stylefeng.guns.modular.system.dao.ProductMapper;
import com.stylefeng.guns.modular.product.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
}
