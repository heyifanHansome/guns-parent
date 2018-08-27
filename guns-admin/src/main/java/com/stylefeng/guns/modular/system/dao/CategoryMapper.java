package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.Category;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
public interface CategoryMapper extends BaseMapper<Category> {

  public void deleteAll();
}
