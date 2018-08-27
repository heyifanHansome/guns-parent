package com.stylefeng.guns.modular.aboutCompany.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.dictmap.AboutCompanyDict;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.core.util.ResultMsg;
import com.stylefeng.guns.modular.aboutCompany.service.IAboutCompanyService;
import com.stylefeng.guns.modular.system.model.AboutCompany;
import com.stylefeng.guns.modular.system.model.Picture;
import com.stylefeng.guns.modular.system.service.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 公司信息控制器
 *
 * @author fengshuonan
 * @Date 2018-08-21 15:07:31
 */
@Controller
@RequestMapping("/aboutCompany")
public class AboutCompanyController extends BaseController {

    private String PREFIX = "/aboutCompany/aboutCompany/";

    @Autowired
    private IAboutCompanyService aboutCompanyService;

    @Autowired
    private IPictureService pictureService;

    /**
     * 跳转到公司信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "aboutCompany.html";
    }

    /**
     * 跳转到添加公司信息
     */
    @RequestMapping("/aboutCompany_add")
    public String aboutCompanyAdd() {
        return PREFIX + "aboutCompany_add.html";
    }

    /**
     * 跳转到修改公司信息
     */
    @RequestMapping("/aboutCompany_update/{aboutCompanyId}")
    public String aboutCompanyUpdate(@PathVariable Integer aboutCompanyId, Model model) {
        AboutCompany aboutCompany = aboutCompanyService.selectById(aboutCompanyId);
        model.addAttribute("item",aboutCompany);
        LogObjectHolder.me().set(aboutCompany);
        return PREFIX + "aboutCompany_edit.html";
    }

    /**
     * 获取公司信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return aboutCompanyService.selectList(null);
    }

    /**
     * 新增公司信息
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "新增公司信息",key = "contactUs",dict = AboutCompanyDict.class)
    @ResponseBody
    public Object add(AboutCompany aboutCompany) {
        aboutCompany.setCommitTime(new DateTime());
        aboutCompany.setUpdateTime(new DateTime());
        aboutCompanyService.insert(aboutCompany);

        return SUCCESS_TIP;
    }

    /**
     * 删除公司信息
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除公司信息",key = "contactUs",dict = AboutCompanyDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer aboutCompanyId) {
        aboutCompanyService.deleteById(aboutCompanyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改公司信息
     */
    @RequestMapping(value = "/update")
    @BussinessLog(value = "修改公司信息",key = "contactUs",dict = AboutCompanyDict.class)
    @ResponseBody
    public Object update(AboutCompany aboutCompany) {
        aboutCompany.setUpdateTime(new DateTime());
        aboutCompanyService.updateById(aboutCompany);
        return SUCCESS_TIP;
    }

    @GetMapping(value = "/img")
    public ResponseEntity<?> getUser(String baseId) {
        ResultMsg resultMsg = new ResultMsg();
        try {
            EntityWrapper<Picture> entityWrapper = new EntityWrapper<>();
            entityWrapper.like("base_id", baseId);
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
     * 公司信息详情
     */
    @RequestMapping(value = "/detail/{aboutCompanyId}")
    @ResponseBody
    public Object detail(@PathVariable("aboutCompanyId") Integer aboutCompanyId) {
        return aboutCompanyService.selectById(aboutCompanyId);
    }
}
