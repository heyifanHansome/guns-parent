package com.stylefeng.guns.modular.productCommitUser.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.ProductCommitUser;
import com.stylefeng.guns.modular.productCommitUser.service.IProductCommitUserService;

/**
 * 用户产品评论控制器
 *
 * @author fengshuonan
 * @Date 2018-08-13 19:32:13
 */
@Controller
@RequestMapping("/productCommitUser")
public class ProductCommitUserController extends BaseController {

    private String PREFIX = "/productCommitUser/productCommitUser/";

    @Autowired
    private IProductCommitUserService productCommitUserService;

    /**
     * 跳转到用户产品评论首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "productCommitUser.html";
    }

    /**
     * 跳转到添加用户产品评论
     */
    @RequestMapping("/productCommitUser_add")
    public String productCommitUserAdd() {
        return PREFIX + "productCommitUser_add.html";
    }

    /**
     * 跳转到修改用户产品评论
     */
    @RequestMapping("/productCommitUser_update/{productCommitUserId}")
    public String productCommitUserUpdate(@PathVariable Integer productCommitUserId, Model model) {
        ProductCommitUser productCommitUser = productCommitUserService.selectById(productCommitUserId);
        model.addAttribute("item",productCommitUser);
        LogObjectHolder.me().set(productCommitUser);
        return PREFIX + "productCommitUser_edit.html";
    }

    /**
     * 获取用户产品评论列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return productCommitUserService.selectList(null);
    }

    /**
     * 新增用户产品评论
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProductCommitUser productCommitUser) {
        productCommitUserService.insert(productCommitUser);
        return SUCCESS_TIP;
    }

    /**
     * 删除用户产品评论
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer productCommitUserId) {
        productCommitUserService.deleteById(productCommitUserId);
        return SUCCESS_TIP;
    }

    /**
     * 修改用户产品评论
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProductCommitUser productCommitUser) {
        productCommitUserService.updateById(productCommitUser);
        return SUCCESS_TIP;
    }

    /**
     * 用户产品评论详情
     */
    @RequestMapping(value = "/detail/{productCommitUserId}")
    @ResponseBody
    public Object detail(@PathVariable("productCommitUserId") Integer productCommitUserId) {
        return productCommitUserService.selectById(productCommitUserId);
    }
}
