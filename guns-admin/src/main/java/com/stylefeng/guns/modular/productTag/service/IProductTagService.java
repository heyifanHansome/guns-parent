package com.stylefeng.guns.modular.productTag.service;

import com.stylefeng.guns.modular.system.model.ProductTag;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品标签 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
public interface IProductTagService extends IService<ProductTag> {
    List<Map<String, Object>> list(@Param("condition") String condition);
}
