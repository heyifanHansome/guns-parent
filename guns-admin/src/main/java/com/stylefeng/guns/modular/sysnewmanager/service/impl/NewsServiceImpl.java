package com.stylefeng.guns.modular.sysnewmanager.service.impl;

import com.stylefeng.guns.modular.system.model.News;
import com.stylefeng.guns.modular.system.dao.NewsMapper;
import com.stylefeng.guns.modular.sysnewmanager.service.INewsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
