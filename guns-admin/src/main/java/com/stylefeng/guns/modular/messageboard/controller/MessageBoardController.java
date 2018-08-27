package com.stylefeng.guns.modular.messageboard.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.modular.messageboard.service.IMessageBoardService;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.model.MessageBoard;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.warpper.messageBoardWarpper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 咨询留言表控制器
 *
 * @author fengshuonan
 * @Date 2018-08-17 11:00:09
 */
@Controller
@RequestMapping("/messageBoard")
public class MessageBoardController extends BaseController {

    private String PREFIX = "/messageboard/messageBoard/";

    @Autowired
    private IMessageBoardService messageBoardService;

    @Autowired
    private IDeptService deptService;

    /**
     * 跳转到咨询留言表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "messageBoard.html";
    }

    /**
     * 跳转到添加咨询留言表
     */
    @RequestMapping("/messageBoard_add")
    public String messageBoardAdd() {
        return PREFIX + "messageBoard_add.html";
    }

    /**
     * 跳转到修改咨询留言表
     */
    @RequestMapping("/messageBoard_update/{messageBoardId}")
    public String messageBoardUpdate(@PathVariable Integer messageBoardId, Model model) {
        MessageBoard messageBoard = messageBoardService.selectById(messageBoardId);
        model.addAttribute("item", messageBoard);
        LogObjectHolder.me().set(messageBoard);
        return PREFIX + "messageBoard_edit.html";
    }

    /**
     * 获取咨询留言表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Integer id = shiroUser.getDeptId();
        Dept dept = deptService.selectById(id);
        String tipsNames = dept.getTips();
        String[] tipsNamesArray = tipsNames.split(",");
        List<String>  names = new ArrayList<>();
            for (String tipsName : tipsNamesArray) {
                names.add(tipsName);
            }

        List<Map<String, Object>> list = this.messageBoardService.list(condition,names);



//        if (tipsNames.isEmpty()) {
//            return super.warpObject(new messageBoardWarpper(list));
//        } else {
//            String[] tipsNamesArray = tipsNames.split(",");
//            for (String tipsName : tipsNamesArray) {
//                Map<String, Object> newList = new HashMap<>();
//                EntityWrapper<MessageBoard> entityWrapper = new EntityWrapper<>();
//                entityWrapper.like("tag_name", tipsName);
//                newList = messageBoardService.selectMap(entityWrapper);
//                newLists.add(newList);
//            }
//            return super.warpObject(new messageBoardWarpper(newLists));
//        }

        return super.warpObject(new messageBoardWarpper(list));
    }

    /**
     * 新增咨询留言表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MessageBoard messageBoard) {
        messageBoardService.insert(messageBoard);
        return SUCCESS_TIP;
    }

    /**
     * 删除咨询留言表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer messageBoardId) {
        messageBoardService.deleteById(messageBoardId);
        return SUCCESS_TIP;
    }

    /**
     * 修改咨询留言表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MessageBoard messageBoard) {
        messageBoardService.updateById(messageBoard);
        return SUCCESS_TIP;
    }

    /**
     * 咨询留言表详情
     */
    @RequestMapping(value = "/detail/{messageBoardId}")
    @ResponseBody
    public Object detail(@PathVariable("messageBoardId") Integer messageBoardId) {
        return messageBoardService.selectById(messageBoardId);
    }
}
