package com.stylefeng.guns.modular.loginUser.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.loginUser.service.ILoginUserService;
import com.stylefeng.guns.modular.system.model.LoginUser;
import com.stylefeng.guns.modular.system.warpper.loginUserWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * app用户管理控制器
 *
 * @author fengshuonan
 * @Date 2018-08-13 19:38:17
 */
@Controller
@RequestMapping("/loginUser")
public class LoginUserController extends BaseController {

    private String PREFIX = "/loginUser/loginUser/";

    @Autowired
    private ILoginUserService loginUserService;

    /**
     * 跳转到app用户管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "loginUser.html";
    }

    /**
     * 跳转到添加app用户管理
     */
    @RequestMapping("/loginUser_add")
    public String loginUserAdd() {
        return PREFIX + "loginUser_add.html";
    }

    /**
     * 跳转到修改app用户管理
     */
    @RequestMapping("/loginUser_update/{loginUserId}")
    public String loginUserUpdate(@PathVariable Integer loginUserId, Model model) {
        LoginUser loginUser = loginUserService.selectById(loginUserId);
        model.addAttribute("item",loginUser);
        LogObjectHolder.me().set(loginUser);
        return PREFIX + "loginUser_edit.html";
    }

    /**
     * 获取app用户管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.loginUserService.list(condition);
        return super.warpObject(new loginUserWarpper(list));
    }

    /**
     * 新增app用户管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(LoginUser loginUser) {
        loginUserService.insert(loginUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除app用户管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer loginUserId) {
        loginUserService.deleteById(loginUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改app用户管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(LoginUser loginUser) {
        loginUserService.updateById(loginUser);
        return SUCCESS_TIP;
    }

    /**
     * app用户管理详情
     */
    @RequestMapping(value = "/detail/{loginUserId}")
    @ResponseBody
    public Object detail(@PathVariable("loginUserId") Integer loginUserId) {
        return loginUserService.selectById(loginUserId);
    }
}
