package com.stylefeng.guns.modular.category.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.constant.dictmap.CategoryDict;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.support.DateTime;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.category.service.ICategoryService;
import com.stylefeng.guns.modular.system.dao.CategoryMapper;
import com.stylefeng.guns.modular.system.model.Category;
import com.stylefeng.guns.modular.system.model.Product;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 产品分类控制器
 *
 * @author fengshuonan
 * @Date 2018-08-13 19:34:45
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

    private String PREFIX = "/category/category/";
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 跳转到产品分类首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "category.html";
    }

    /**
     * 跳转到添加产品分类
     */
    @RequestMapping("/category_add")
    public String categoryAdd() {
        return PREFIX + "category_add.html";
    }

    /**
     * 跳转到修改产品分类
     */
    @RequestMapping("/category_update/{categoryId}")
    public String categoryUpdate(@PathVariable Integer categoryId, Model model) {
        Category category = categoryService.selectById(categoryId);
        model.addAttribute("item", category);
        LogObjectHolder.me().set(category);
        return PREFIX + "category_edit.html";
    }

    /**
     * 获取产品分类列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        EntityWrapper<Category> entityWrapper = new EntityWrapper<>();
        List<Category> categories = new ArrayList<>();
        if (ToolUtil.isEmpty(condition)) {
            categories = categoryService.selectList(null);
        } else {
            entityWrapper.like("name", condition);
            categories = categoryService.selectList(entityWrapper);
        }

        return categories;
    }

    /**
     * 同步方法
     */
    @RequestMapping(value = "/add")
    @BussinessLog(value = "同步分类数据",key = "name",dict = CategoryDict.class)
    @ResponseBody
    public Object add(Category newsd) {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory()); // 使用HttpClient，支持GZIP
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 支持中文编码
        ResponseEntity<String> response = restTemplate.getForEntity("http://app.xinyun-elec.com/XyProduct/CategoryManager.asq?Fn=GetAllCategoryList&Lang=CN&ReadMode=3&JustUse=1&JustApprove=1", String.class);
        String strBody = response.getBody();
        JSONObject jsonObject = JSONObject.fromObject(strBody);
        JSONObject itemJson = JSONObject.fromObject(jsonObject.get("CategoryList"));
        JSONArray jsonObject1 = (JSONArray) itemJson.get("Items");
        List<Map<String, String>> categoryInfo = new ArrayList<>();
        for (int i = 1; i < jsonObject1.size(); i++) {
            JSONObject itemInfo = (JSONObject) jsonObject1.get(i);
            if (itemInfo.get("PID").toString().equals("000")) {
                Map<String, String> map = new HashMap<>();
                map.put("ID", itemInfo.get("ID").toString());
                map.put("Name", itemInfo.get("Name").toString());
                categoryInfo.add(map);
            } else {
                continue;
            }
        }

        for (Map<String, String> map : categoryInfo) {
            List<Category> categories = new ArrayList<>();
            String id = map.get("ID");
            EntityWrapper<Category> entityWrapper = new EntityWrapper<>();
            entityWrapper.like("category_id", id);
            categories = categoryMapper.selectList(entityWrapper);

            if (categories.size() > 0 && categories.get(0) != null) {
                Category category = new Category();
                Integer updateId = categories.get(0).getId();
                category = categoryService.selectById(updateId);
                category.setCategoryId(map.get("ID"));
                category.setBaseId(UUID.randomUUID().toString());
                category.setSyncTime(new Date());
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                category.setCreater(shiroUser.getName());
                category.setName(map.get("Name"));
                categoryService.updateById(category);
            } else {
                Category insertCategory = new Category();
                insertCategory.setName(map.get("Name"));
                insertCategory.setCategoryId(map.get("ID"));
                insertCategory.setSyncTime(new Date());
                ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
                insertCategory.setBaseId(UUID.randomUUID().toString());
                insertCategory.setCreater(shiroUser.getName());
                categoryService.insert(insertCategory);
            }
        }


        return SUCCESS_TIP;

    }


    /**
     * 新增我的商品
     */
    @RequestMapping(value = "/insert")
    @BussinessLog(value = "新增我的分类",key="name",  dict = CategoryDict.class)
    @ResponseBody
    public Object insert(Category category) {
        category.setSyncTime(new DateTime());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        category.setCreater(shiroUser.getName());
        category.setCategoryId("我们自己的商品");
        categoryService.insert(category);

        return SUCCESS_TIP;
    }


    /**
     * 删除产品分类
     */
    @RequestMapping(value = "/delete")
    @BussinessLog(value = "删除产品分类",key="name",  dict = CategoryDict.class)
    @ResponseBody
    public Object delete(@RequestParam Integer categoryId) {
        categoryService.deleteById(categoryId);
        return SUCCESS_TIP;
    }

    /**
     * 修改产品分类
     */
    @RequestMapping(value = "/update")
    @BussinessLog(value = "修改产品分类", key="name",  dict = CategoryDict.class)
    @ResponseBody
    public Object update(Category category) {
        category.setUpdateTime(new Date());
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        category.setCreater(shiroUser.getName());
        categoryService.updateById(category);

        return SUCCESS_TIP;
    }

    /**
     * 产品分类详情
     */
    @RequestMapping(value = "/detail/{categoryId}")
    @ResponseBody
    public Object detail(@PathVariable("categoryId") Integer categoryId) {
        return categoryService.selectById(categoryId);
    }
}
