package com.stylefeng.guns.modular.loginUser.service.impl;

import com.stylefeng.guns.modular.system.model.LoginUser;
import com.stylefeng.guns.modular.system.dao.LoginUserMapper;
import com.stylefeng.guns.modular.loginUser.service.ILoginUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements ILoginUserService {
    @Override
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
}
