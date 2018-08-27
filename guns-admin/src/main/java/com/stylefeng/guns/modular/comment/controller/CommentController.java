package com.stylefeng.guns.modular.comment.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.heyifanStringutils;
import com.stylefeng.guns.modular.comment.service.ICommentService;
import com.stylefeng.guns.modular.system.model.Comment;
import com.stylefeng.guns.modular.system.warpper.commentWarpper;
import org.beetl.ext.fn.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 用户评论控制器
 *
 * @author fengshuonan
 * @Date 2018-08-13 19:24:20
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    private String PREFIX = "/comment/comment/";

    @Autowired
    private ICommentService commentService;

    /**
     * 跳转到用户评论首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "comment.html";
    }

    /**
     * 跳转到添加用户评论
     */
    @RequestMapping("/comment_add")
    public String commentAdd() {
        return PREFIX + "comment_add.html";
    }

    /**
     * 跳转到修改用户评论
     */
    @RequestMapping("/comment_update/{commentId}")
    public Object commentUpdate(@PathVariable String commentId) {

        if (heyifanStringutils.isNotEmpty(commentId)) {
            String[] idArray = commentId.split(",");
            for (String id : idArray) {
                Comment comment = new Comment();
                comment = comment.selectById(id);
                comment.setCheckUp(1);
                commentService.updateById(comment);
            }

        }
        return  "ok";
    }

    /**
     * 获取用户评论列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {

        List<Map<String, Object>> list = this.commentService.list(condition);
        return super.warpObject(new commentWarpper(list));
    }

    /**
     * 新增用户评论
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Comment comment) {
        commentService.insert(comment);
        return SUCCESS_TIP;
    }

    /**
     * 删除用户评论
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer commentId) {
        commentService.deleteById(commentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改用户评论
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Comment comment) {
        commentService.updateById(comment);
        return SUCCESS_TIP;
    }

    /**
     * 用户评论详情
     */
    @RequestMapping(value = "/detail/{commentId}")
    @ResponseBody
    public Object detail(@PathVariable("commentId") Integer commentId) {
        return commentService.selectById(commentId);
    }
}
