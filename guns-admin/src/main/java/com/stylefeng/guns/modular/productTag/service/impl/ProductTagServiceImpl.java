package com.stylefeng.guns.modular.productTag.service.impl;

import com.stylefeng.guns.modular.system.model.ProductTag;
import com.stylefeng.guns.modular.system.dao.ProductTagMapper;
import com.stylefeng.guns.modular.productTag.service.IProductTagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品标签 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@Service
public class ProductTagServiceImpl extends ServiceImpl<ProductTagMapper, ProductTag> implements IProductTagService {
    @Override
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
}
