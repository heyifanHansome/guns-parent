package com.stylefeng.guns.modular.category.service.impl;

import com.stylefeng.guns.modular.system.model.Category;
import com.stylefeng.guns.modular.system.dao.CategoryMapper;
import com.stylefeng.guns.modular.category.service.ICategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
