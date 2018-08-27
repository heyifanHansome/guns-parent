package com.stylefeng.guns.modular.loginUser.service;

import com.stylefeng.guns.modular.system.model.LoginUser;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
public interface ILoginUserService extends IService<LoginUser> {


    List<Map<String, Object>> list(@Param("condition") String condition);
}
