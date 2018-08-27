package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.MessageBoard;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 咨询留言表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
public interface MessageBoardMapper extends BaseMapper<MessageBoard> {

    List<Map<String, Object>> list(@Param("condition") String condition, @Param("tipsNames") List<String> tipsNames);
}
