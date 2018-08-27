package com.stylefeng.guns.modular.product.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.dictmap.ProductDict;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.core.util.ResultMsg;
import com.stylefeng.guns.modular.product.service.IProductService;
import com.stylefeng.guns.modular.system.dao.CategoryMapper;
import com.stylefeng.guns.modular.system.dao.ProductMapper;
import com.stylefeng.guns.modular.system.model.Category;
import com.stylefeng.guns.modular.system.model.Picture;
import com.stylefeng.guns.modular.system.model.Product;
import com.stylefeng.guns.modular.system.service.IPictureService;
import com.stylefeng.guns.modular.system.warpper.productWarpper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的商品控制器
 *
 * @author fengshuonan
 * @Date 2018-08-13 19:43:21
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private String PREFIX = "/product/product/";

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 跳转到我的商品首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "product.html";
    }

    /**
     * 跳转到添加我的商品
     */
    @RequestMapping("/product_add")
    public String productAdd() {

        return PREFIX + "product_add.html";
    }

    /**
     * 跳转到修改我的商品
     */
    @RequestMapping("/product_update/{productId}")
    public String productUpdate(@PathVariable Integer productId, Model model) {
        Product product = productService.selectById(productId);
        model.addAttribute("item", product);
        LogObjectHolder.me().set(product);
        return PREFIX + "product_edit.html";
    }

    /**
     * 获取我的商品列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.productService.list(condition);
        return super.warpObject(new productWarpper(list));

    }

    /**
     * 新增我的商品
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "增加自己产品", key = "name",dict = ProductDict.class)
    @ResponseBody
    public Object add(Product product) {
        product.setCommitTime(new DateTime());
        product.setCreateTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        product.setCreater(shiroUser.getName());
        productService.insert(product);
        return SUCCESS_TIP;
    }

    /**
     * 删除我的商品
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除商品",key = "name",dict = ProductDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer productId) {
        productService.deleteById(productId);
        return SUCCESS_TIP;
    }

    /**
     * 修改我的商品
     */
    @RequestMapping(value = "/update")
    @BussinessLog(value = "修改我的商品通知",key ="name",dict = ProductDict.class)
    @ResponseBody
    public Object update(Product product) {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        product.setCreateTime(new DateTime());
        product.setCreater(shiroUser.getName());
        productService.updateById(product);
        return SUCCESS_TIP;
    }


    /**
     * 动态获取分类数据
     */
    @RequestMapping(value = "/getCategoryList")
    @ResponseBody
    public Object getCategoryList() {
        List<Category> categories = new ArrayList<>();
        categories = categoryMapper.selectList(null);
        return categories;
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
     * 动态获取分类数据
     */
    @RequestMapping(value = "/getProductList")
    @ResponseBody
    public Object getProductList() {
        List<Product> products = new ArrayList<>();
        products = productMapper.selectList(null);
        return products;
    }



    /**
     * 我的商品详情
     */
    @RequestMapping(value = "/detail/{productId}")
    @ResponseBody
    public Object detail(@PathVariable("productId") Integer productId) {
        return productService.selectById(productId);
    }
}
