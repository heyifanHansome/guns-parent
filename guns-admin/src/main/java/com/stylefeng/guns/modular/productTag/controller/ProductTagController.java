package com.stylefeng.guns.modular.productTag.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.dictmap.ProductTagDict;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.modular.productTag.service.IProductTagService;
import com.stylefeng.guns.modular.system.model.ProductTag;
import com.stylefeng.guns.modular.system.warpper.productCharacterWarpper;
import org.apache.shiro.SecurityUtils;
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
 * 产品标签控制器
 *
 * @author fengshuonan
 * @Date 2018-08-17 10:25:43
 */
@Controller
@RequestMapping("/productTag")
public class ProductTagController extends BaseController {

    private String PREFIX = "/productTag/productTag/";

    @Autowired
    private IProductTagService productTagService;

    /**
     * 跳转到产品标签首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "productTag.html";
    }

    /**
     * 跳转到添加产品标签
     */
    @RequestMapping("/productTag_add")
    public String productTagAdd() {
        return PREFIX + "productTag_add.html";
    }

    /**
     * 跳转到修改产品标签
     */
    @RequestMapping("/productTag_update/{productTagId}")
    public String productTagUpdate(@PathVariable Integer productTagId, Model model) {
        ProductTag productTag = productTagService.selectById(productTagId);
        model.addAttribute("item", productTag);
        LogObjectHolder.me().set(productTag);
        return PREFIX + "productTag_edit.html";
    }

    /**
     * 获取产品标签列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.productTagService.list(condition);
    return list;

    }

    /**
     * 新增产品标签
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "新增产品标签",key = "name"  ,dict = ProductTagDict.class)
    @ResponseBody
    public Object add(ProductTag productTag) {
        productTag.setCommitTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        productTag.setCreater(shiroUser.getName());
        productTag.setCreateTime(new DateTime());
        productTagService.insert(productTag);
        return SUCCESS_TIP;
    }

    /**
     * 删除产品标签
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除产品标签",key = "name"  ,dict = ProductTagDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer productTagId) {
        productTagService.deleteById(productTagId);
        return SUCCESS_TIP;
    }

    /**
     * 修改产品标签
     */
    @RequestMapping(value = "/update")
    @BussinessLog(value = "修改产品标签",key = "name"  ,dict = ProductTagDict.class)
    @ResponseBody
    public Object update(ProductTag productTag) {
        productTag.setCreateTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        productTag.setCreater(shiroUser.getName());
        productTagService.updateById(productTag);
        return SUCCESS_TIP;
    }

    /**
     * 产品标签详情
     */
    @RequestMapping(value = "/detail/{productTagId}")
    @ResponseBody
    public Object detail(@PathVariable("productTagId") Integer productTagId) {
        return productTagService.selectById(productTagId);
    }
}
