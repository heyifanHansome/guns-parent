package com.stylefeng.guns.modular.productCharacter.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.dictmap.ProductCharacterDict;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.core.util.ResultMsg;
import com.stylefeng.guns.modular.productCharacter.service.IProductCharacterService;
import com.stylefeng.guns.modular.system.model.Picture;
import com.stylefeng.guns.modular.system.model.ProductCharacter;
import com.stylefeng.guns.modular.system.service.IPictureService;
import com.stylefeng.guns.modular.system.warpper.productCharacterWarpper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 产品特性控制器
 *
 * @author fengshuonan
 * @Date 2018-08-13 19:29:40
 */
@Controller
@RequestMapping("/productCharacter")
public class ProductCharacterController extends BaseController {

    private String PREFIX = "/productCharacter/productCharacter/";
    @Autowired
    private IPictureService pictureService;

    @Autowired
    private IProductCharacterService productCharacterService;

    /**
     * 跳转到产品特性首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "productCharacter.html";
    }

    /**
     * 跳转到添加产品特性
     */
    @RequestMapping("/productCharacter_add")
    public String productCharacterAdd() {
        return PREFIX + "productCharacter_add.html";
    }

    /**
     * 跳转到修改产品特性
     */
    @RequestMapping("/productCharacter_update/{productCharacterId}")
    public String productCharacterUpdate(@PathVariable Integer productCharacterId, Model model) {
        ProductCharacter productCharacter = productCharacterService.selectById(productCharacterId);
        model.addAttribute("item", productCharacter);
        LogObjectHolder.me().set(productCharacter);
        return PREFIX + "productCharacter_edit.html";
    }

    /**
     * 获取产品特性列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.productCharacterService.list(condition);
        return super.warpObject(new productCharacterWarpper(list));
    }

    /**
     * 新增产品特性
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "增加产品特性",key ="id",dict = ProductCharacterDict.class)
    @ResponseBody
    public Object add(ProductCharacter productCharacter) {
        productCharacter.setCommitTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        productCharacter.setCreater(shiroUser.getName());
        productCharacter.setCreateTime(new DateTime());
        productCharacterService.insert(productCharacter);
        return SUCCESS_TIP;
    }

    /**
     * 删除产品特性
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除产品特性",key ="id",dict = ProductCharacterDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer productCharacterId) {
        productCharacterService.deleteById(productCharacterId);
        return SUCCESS_TIP;
    }

    /**
     * 修改产品特性
     */
    @RequestMapping(value = "/update")
    @BussinessLog(value = "更新产品特性",key ="id",dict = ProductCharacterDict.class)
    @ResponseBody
    public Object update(ProductCharacter productCharacter) {
        productCharacter.setCreateTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        productCharacter.setCreater(shiroUser.getName());
        productCharacterService.updateById(productCharacter);
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
     * 产品特性详情
     */
    @RequestMapping(value = "/detail/{productCharacterId}")
    @ResponseBody
    public Object detail(@PathVariable("productCharacterId") Integer productCharacterId) {

        return productCharacterService.selectById(productCharacterId);
    }
}
