package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.ProductTag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品标签 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
public interface ProductTagMapper extends BaseMapper<ProductTag> {

    List<Map<String, Object>> list(@Param("condition") String condition);
}
