package com.stylefeng.guns.modular.sysnewmanager.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.dictmap.NewsDict;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.core.util.ResultMsg;
import com.stylefeng.guns.modular.sysnewmanager.service.INewsService;
import com.stylefeng.guns.modular.system.model.News;
import com.stylefeng.guns.modular.system.model.Picture;
import com.stylefeng.guns.modular.system.service.IPictureService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻管理控制器
 *
 * @author fengshuonan
 * @Date 2018-08-17 10:53:52
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    private String PREFIX = "/sysnewmanager/news/";

    @Autowired
    private INewsService newsService;
    @Autowired
    private IPictureService pictureService;


    /**
     * 跳转到新闻管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "news.html";
    }

    /**
     * 跳转到添加新闻管理
     */
    @RequestMapping("/news_add")
    public String newsAdd() {
        return PREFIX + "news_add.html";
    }

    /**
     * 跳转到修改新闻管理
     */
    @RequestMapping("/news_update/{newsId}")
    public String newsUpdate(@PathVariable Integer newsId, Model model) {
        News news = newsService.selectById(newsId);
        model.addAttribute("item", news);
        LogObjectHolder.me().set(news);
        return PREFIX + "news_edit.html";
    }

    /**
     * 获取新闻管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<News> entityWrapper = new EntityWrapper<>();
        entityWrapper.like("title",condition);
        entityWrapper.like("content",condition);
        return newsService.selectList(entityWrapper);
    }

    /**
     * 新增新闻管理
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "增加新闻",key = "title",dict = NewsDict.class)
    @ResponseBody
    public Object add(News news) {
        news.setCommitTime(new DateTime());
        news.setUpdateTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        news.setCreator(shiroUser.getName());
        newsService.insert(news);
        return SUCCESS_TIP;
    }

    /**
     * 删除新闻管理
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除新闻",key = "title",dict = NewsDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer newsId) {
        newsService.deleteById(newsId);
        return SUCCESS_TIP;
    }

    /**
     * 修改新闻管理
     */
    @RequestMapping(value = "/update")
    @BussinessLog(value = "更新新闻",key = "title",dict = NewsDict.class)
    @ResponseBody
    public Object update(News news) {
         News oldNews = newsService.selectById(news.getId());
        news.setCommitTime(oldNews.getCommitTime());
        news.setUpdateTime(new DateTime());

        newsService.updateById(news);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/img")
    public ResponseEntity<?> getUser(String baseId) {
        ResultMsg resultMsg = new ResultMsg();
        try {

            EntityWrapper<Picture> entityWrapper = new EntityWrapper<>();
            entityWrapper.like("base_id",baseId);
            List<Picture> picture = pictureService.selectList(entityWrapper);
            StringBuffer sbBuffer = new StringBuffer();

            for (int i = 0; i < picture.size(); i++) {
                sbBuffer.append("," + picture.get(i).getId());
            }

            resultMsg = ResultMsg.success("查询成功", "查询成功", sbBuffer);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<ResultMsg>(ResultMsg.fail("系统错误", "系统错误", ""), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResultMsg>(resultMsg, HttpStatus.OK);

    }

    /**
     * 新闻管理详情
     */
    @RequestMapping(value = "/detail/{newsId}")
    @ResponseBody
    public Object detail(@PathVariable("newsId") Integer newsId) {
        return newsService.selectById(newsId);
    }
}
