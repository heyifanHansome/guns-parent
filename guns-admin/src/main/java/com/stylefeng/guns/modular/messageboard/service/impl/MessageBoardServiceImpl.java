package com.stylefeng.guns.modular.messageboard.service.impl;

import com.stylefeng.guns.modular.system.model.MessageBoard;
import com.stylefeng.guns.modular.system.dao.MessageBoardMapper;
import com.stylefeng.guns.modular.messageboard.service.IMessageBoardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 咨询留言表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@Service
public class MessageBoardServiceImpl extends ServiceImpl<MessageBoardMapper, MessageBoard> implements IMessageBoardService {

    @Override
    public List<Map<String, Object>> list(String condition, List<String> tipsNames) {
        return this.baseMapper.list(condition , tipsNames);
    }
}
