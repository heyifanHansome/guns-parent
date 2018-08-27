package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.modular.system.model.LoginUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
public interface LoginUserMapper extends BaseMapper<LoginUser> {

    List<Map<String, Object>> list(@Param("condition") String condition);
}
