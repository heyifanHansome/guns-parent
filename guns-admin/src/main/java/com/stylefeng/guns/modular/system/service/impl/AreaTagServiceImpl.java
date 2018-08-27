package com.stylefeng.guns.modular.system.service.impl;

import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.AreaTag;
import com.stylefeng.guns.modular.system.dao.AreaTagMapper;
import com.stylefeng.guns.modular.system.service.IAreaTagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区标签 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-16
 */
@Service
public class AreaTagServiceImpl extends ServiceImpl<AreaTagMapper, AreaTag> implements IAreaTagService {


    @Override
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }
}
