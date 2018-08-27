package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.AreaTag;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 地区标签 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-16
 */
public interface AreaTagMapper extends BaseMapper<AreaTag> {
    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();
}
