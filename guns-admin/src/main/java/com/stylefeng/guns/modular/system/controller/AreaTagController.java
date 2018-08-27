package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.node.ZTreeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.AreaTag;
import com.stylefeng.guns.modular.system.service.IAreaTagService;

import java.util.List;

/**
 * 地区概览控制器
 *
 * @author fengshuonan
 * @Date 2018-08-16 14:00:22
 */
@Controller
@RequestMapping("/areaTag")
public class AreaTagController extends BaseController {

    private String PREFIX = "/system/areaTag/";

    @Autowired
    private IAreaTagService areaTagService;

    /**
     * 跳转到地区概览首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "areaTag.html";
    }

    /**
     * 跳转到添加地区概览
     */
    @RequestMapping("/areaTag_add")
    public String areaTagAdd() {
        return PREFIX + "areaTag_add.html";
    }

    /**
     * 跳转到修改地区概览
     */
    @RequestMapping("/areaTag_update/{areaTagId}")
    public String areaTagUpdate(@PathVariable Integer areaTagId, Model model) {
        AreaTag areaTag = areaTagService.selectById(areaTagId);
        model.addAttribute("item",areaTag);
        LogObjectHolder.me().set(areaTag);
        return PREFIX + "areaTag_edit.html";
    }

    /**
     * 获取地区概览列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<AreaTag> entityWrapper = new EntityWrapper<>();
        entityWrapper.like("name",condition);
        return areaTagService.selectList(entityWrapper);
    }


    /**
     * 新增地区概览
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AreaTag areaTag) {
        areaTagService.insert(areaTag);
        return SUCCESS_TIP;
    }

    /**
     * 删除地区概览
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer areaTagId) {
        areaTagService.deleteById(areaTagId);
        return SUCCESS_TIP;
    }

    /**
     * 修改地区概览
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AreaTag areaTag) {
        areaTagService.updateById(areaTag);
        return SUCCESS_TIP;
    }


    /**
     * 获取地区的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.areaTagService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }


    /**
     * 地区概览详情
     */
    @RequestMapping(value = "/detail/{areaTagId}")
    @ResponseBody
    public Object detail(@PathVariable("areaTagId") Integer areaTagId) {
        return areaTagService.selectById(areaTagId);
    }
}
