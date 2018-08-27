package com.stylefeng.guns.modular.giveLke.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.giveLke.service.IOtherProductCommitService;
import com.stylefeng.guns.modular.system.model.OtherProductCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 点赞控制器
 *
 * @author fengshuonan
 * @Date 2018-08-18 16:24:21
 */
@Controller
@RequestMapping("/otherProductCommit")
public class OtherProductCommitController extends BaseController {

    private String PREFIX = "/giveLke/otherProductCommit/";

    @Autowired
    private IOtherProductCommitService otherProductCommitService;

    /**
     * 跳转到点赞首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "otherProductCommit.html";
    }

    /**
     * 跳转到添加点赞
     */
    @RequestMapping("/otherProductCommit_add")
    public String otherProductCommitAdd() {
        return PREFIX + "otherProductCommit_add.html";
    }

    /**
     * 跳转到修改点赞
     */
    @RequestMapping("/otherProductCommit_update/{otherProductCommitId}")
    public String otherProductCommitUpdate(@PathVariable Integer otherProductCommitId, Model model) {
        OtherProductCommit otherProductCommit = otherProductCommitService.selectById(otherProductCommitId);
        model.addAttribute("item",otherProductCommit);
        LogObjectHolder.me().set(otherProductCommit);
        return PREFIX + "otherProductCommit_edit.html";
    }

    /**
     * 获取点赞列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return otherProductCommitService.selectList(null);
    }

    /**
     * 新增点赞
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(OtherProductCommit otherProductCommit) {
        otherProductCommitService.insert(otherProductCommit);
        return SUCCESS_TIP;
    }

    /**
     * 删除点赞
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer otherProductCommitId) {
        otherProductCommitService.deleteById(otherProductCommitId);
        return SUCCESS_TIP;
    }

    /**
     * 修改点赞
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(OtherProductCommit otherProductCommit) {
        otherProductCommitService.updateById(otherProductCommit);
        return SUCCESS_TIP;
    }

    /**
     * 点赞详情
     */
    @RequestMapping(value = "/detail/{otherProductCommitId}")
    @ResponseBody
    public Object detail(@PathVariable("otherProductCommitId") Integer otherProductCommitId) {
        return otherProductCommitService.selectById(otherProductCommitId);
    }
}
