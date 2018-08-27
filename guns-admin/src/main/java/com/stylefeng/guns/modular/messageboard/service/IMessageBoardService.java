package com.stylefeng.guns.modular.messageboard.service;

import com.stylefeng.guns.modular.system.model.MessageBoard;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 咨询留言表 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
public interface IMessageBoardService extends IService<MessageBoard> {

    List<Map<String, Object>> list(@Param("condition") String condition, @Param("tipsNames")List<String>  tipsNames);

}
