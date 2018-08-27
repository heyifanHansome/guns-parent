package com.stylefeng.guns.modular.system.service;

import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.AreaTag;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 地区标签 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-16
 *
 */
public interface IAreaTagService extends IService<AreaTag> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();
}
