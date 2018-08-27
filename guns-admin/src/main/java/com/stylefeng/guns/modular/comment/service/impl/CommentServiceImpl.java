package com.stylefeng.guns.modular.comment.service.impl;

import com.stylefeng.guns.modular.system.model.Comment;
import com.stylefeng.guns.modular.system.dao.CommentMapper;
import com.stylefeng.guns.modular.comment.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Override
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
}
