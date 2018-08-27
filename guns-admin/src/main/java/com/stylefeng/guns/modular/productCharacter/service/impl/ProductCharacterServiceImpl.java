package com.stylefeng.guns.modular.productCharacter.service.impl;

import com.stylefeng.guns.modular.system.model.ProductCharacter;
import com.stylefeng.guns.modular.system.dao.ProductCharacterMapper;
import com.stylefeng.guns.modular.productCharacter.service.IProductCharacterService;
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
public class ProductCharacterServiceImpl extends ServiceImpl<ProductCharacterMapper, ProductCharacter> implements IProductCharacterService {

    @Override
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
}
