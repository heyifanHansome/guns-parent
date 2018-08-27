package com.stylefeng.guns.modular.lijun.controller;

import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.ResultMsg;
import com.stylefeng.guns.modular.lijun.util.*;
import com.stylefeng.guns.modular.system.dao.Dao;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/page/")
public class PageController extends BaseController {
    private static final String FILEPATHIMG= (FinalStaticString.FILEPATHIMG.split("/")[FinalStaticString.FILEPATHIMG.split("/").length-2]+"/"+FinalStaticString.FILEPATHIMG.split("/")[FinalStaticString.FILEPATHIMG.split("/").length-1]+"/");
    /**
     * 用户表
     */
    private final static String UserTableName="sys_login_user";
    /**
     * 地区标签
     */
    private final static String AreaTagTableName="sys_area_tag";
    /**
     * 云科产品(product_id),我们的产品(my_product_id),新闻(new_id)三者评论表
     */
    private final static String CommentTableName="sys_comment";
    /**
     * 云科产品(product_id),我们的产品(my_product_id),新闻(new_id)三者点赞表
     */
    private final static String CommendTableName="sys_other_product_commit";
    /**
     * 新闻表
     */
    private final static String NewsTableName="sys_news";
    /**
     * 图片表(里面的base_id对应所有有base_id字段的表的图片)
     */
    private final static String PictureTableName="picture";
    /**
     * 我们的产品数据表
     */
    private final static String MyProductTableName="sys_product";
    /**
     * 我们的产品特性数据表
     */
    private final static String MyProductCharacterTableName="sys_product";
    /**
     * 产品分类数据表
     */
    private final static String ProductCategoryTableName="sys_category";

    @Autowired
    private Dao dao;
    @Autowired
    private GunsProperties gunsProperties;


    /**
     * 根据ico或者pdf返回文件输出流
     */
    @RequestMapping("getPictureIOByPictureName")
    @ResponseBody
    public void getPictureIOByPictureName(HttpServletResponse res){
        try {
            PageData pd=getPageData();
            if(!Tool.isNull(pd.get("ico"))){
                String path = gunsProperties.getFileUploadPath() + pd.get("ico").toString();
                byte[] bytes = FileUtil.toByteArray(path);
                res.getOutputStream().write(bytes);
            }else if(!Tool.isNull(pd.get("pdf"))){
                String path = gunsProperties.getFileUploadPath() + pd.get("pdf").toString();
                byte[] bytes = FileUtil.toByteArray(path);
                res.getOutputStream().write(bytes);
            }else{
                res.setContentType("text/plain; charset=utf-8");
                res.getOutputStream().write("图片的数据不存在或已被删除".getBytes());
            }
            if(res.getOutputStream()!=null)res.getOutputStream().close();
        }catch (FileNotFoundException fe){
            res.setContentType("text/plain; charset=utf-8");
            try{
                res.getOutputStream().write("图片的文件已被删除".getBytes());
                if(res.getOutputStream()!=null)res.getOutputStream().close();
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }catch (Exception e) {
           e.printStackTrace();
        }
    }


    /**
     * 获取产品的大分类
     * @return 这里只介绍返回值里data的格式:传categoryID的{
     *  *     ctegorys:[
     *  *          {
     *  *              id:xxx,
     *  *              name:xxx,
     *  *              ico:xxx.xxx,
     *  *              pdf:xxx.xxx,
     *  *              ...
     *  *          },
     *  *          {...},
     *  *          ...
     *  *     ]
     *  * }
     *  * 不传categoryID的{
     *  *              id:xxx,
     *  *              name:xxx,
     *  *              ico:xxx.xxx,
     *  *              pdf:xxx.xxx,
     *  *              ...
     *  * 		}
     */
    @RequestMapping("getProductCategory")
    @ResponseBody
    public ResultMsg getProductCategory(){
        try{
            PageData pd=getPageData();
            Map<String,Object>result=new HashMap<>();
            if(!Tool.isNull(pd.get("categoryID"))){
                List<Map<String,Object>>ctegorys=dao.selectBySQL("select * from "+ProductCategoryTableName+" where id='"+pd.get("categoryID")+"'");
                if(!Tool.listIsNull(ctegorys)){
                    result.put("ctegory",ctegorys.get(0));
                }else{
                    return ResultMsg.fail(("ID:"+pd.get("categoryID")+"的分类不存在"),null,null);
                }
            }else{
                List<Map<String,Object>>ctegorys=dao.selectBySQL("select * from "+ProductCategoryTableName);
                result.put("ctegorys",ctegorys);
            }
            return ResultMsg.success("查询成功",null,result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }


    /**
     * 根据pictureID返回图片输出流
     */
    @RequestMapping("getPictureIOByPictureID")
    @ResponseBody
    public void getPictureIOByPictureID(HttpServletResponse res){
        PageData pd=getPageData();
        try{
            if (!Tool.isNull(pd.get("pictureID"))) {
                List<Map<String,Object>>pictures=dao.selectBySQL("select * from "+PictureTableName+" where id="+pd.get("pictureID"));
                if(!Tool.listIsNull(pictures)){
                    String imgpath = "";
                    Map<String,Object>picture=pictures.get(0);
                    if(!Tool.isNull(picture.get("absolutepath"))&&!Tool.isNull(picture.get("picturename"))&&!Tool.isNull(picture.get("suffixname"))){
                        imgpath=picture.get("absolutepath").toString()+picture.get("picturename").toString()+picture.get("suffixname").toString();
                        File file = new File(imgpath);
                        res.setContentType("image/jpg");
                        InputStream in = new FileInputStream(file);
                        IOUtils.copy(in, res.getOutputStream());
                        if(res.getOutputStream()!=null)res.getOutputStream().close();
                        if(in!=null)in.close();
                    }
                }else{
                    res.setContentType("text/plain; charset=utf-8");
                    res.getOutputStream().write("图片的数据不存在或已被删除".getBytes());
                    res.getOutputStream().close();
                }
            }
        }catch (FileNotFoundException fe){
            res.setContentType("text/plain; charset=utf-8");
            try{
                res.getOutputStream().write("图片的文件已被删除".getBytes());
                if(res.getOutputStream()!=null)res.getOutputStream().close();
            }catch (IOException ie){
                ie.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 拉取新闻列表: newsList
     * 说明: 需要用数组内每个对象各自的pictureID去接口getPictureIOByPictureID
     *       拉取图片的IO流,数组里每个对象已经包含了各自的所有内容,建议到详情
     *       页时,直接将单独的对象传到详情页展示便可
     * 参数: pageStart-页数(字符串/数字:校验是否为数字)
     *       pageSize-每页多少条(字符串/数字:校验是否为数字)
     *       (例子:pageStart=1,pageSize=10,十条为一页,查询第一页)
     * 返回: 这里只说明返回值里的data:[
     *         {
     *             id:xxxx,
     *             title:xxx,
     *             ...
     *         },
     *         {...},
     *         ...
     *     ]
     */
    @RequestMapping("newsList")
    @ResponseBody
    public ResultMsg newsList(){
        try{
            PageData pd=getPageData();
            if(!Tool.isNull(pd.get("pageStart"))&&!Tool.isNull(pd.get("pageStart"))){
                List<Map<String,Object>>newsList=dao.selectBySQL("select * from "+NewsTableName+" order by commit_time desc limit "+(((Integer.parseInt(pd.get("pageStart").toString())>0?Integer.parseInt(pd.get("pageStart").toString()):1)-1)*Integer.parseInt(pd.get("pageSize").toString()))+","+Integer.parseInt(pd.get("pageSize").toString()));
                putProductMsg(newsList);
                return ResultMsg.success("查询成功",null,newsList);
            }else{
                return ResultMsg.fail("缺少参数","pageStart,pageSize必传",null);
            }
        }catch (NumberFormatException ne){
            ne.printStackTrace();
            return ResultMsg.fail("查询条件错误",("pageStart,pageSize只能是数字,传参是字符串类型还是整数浮点数类型无所谓:"+ ne.toString()),null);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    /**
     * 拉取热销/推荐产品列表: productList
     * 说明: 需要用数组内每个对象各自的pictureID去接口getPictureIOByPictureID
     *       拉取图片的IO流,数组里每个产品已经包含了各自的部分内容,建议到详情
     *       页时,将这一部分传过去,然后直接调取获取该产品特性的接口便可,三个
     *       参数必传
     * 参数: pageStart-页数(字符串/数字:校验是否为数字)
     *       pageSize-每页多少条(字符串/数字:校验是否为数字)
     *       type-产品类型(hot:热销,commend:推荐)(字符串:校验是否为空)
     *       (例子:pageStart=1,pageSize=10,十条为一页,查询第一页)
     * 返回: 这里只说明返回值里的data:[
     *         {
     *             id:xxxx,
     *             title:xxx,
     *             ...
     *         },
     *         {...},
     *         ...
     *     ]
     */
    @RequestMapping("productList")
    @ResponseBody
    public ResultMsg productList(){
        try{
            PageData pd=getPageData();
            if(!Tool.isNull(pd.get("pageStart"))&&!Tool.isNull(pd.get("pageStart"))&&!Tool.isNull(pd.get("type"))){
                List<Map<String,Object>>productList=dao.selectBySQL("select * from "+MyProductTableName+" where type='"+pd.get("type")+"' order by commit_time desc limit "+(((Integer.parseInt(pd.get("pageStart").toString())>0?Integer.parseInt(pd.get("pageStart").toString()):1)-1)*Integer.parseInt(pd.get("pageSize").toString()))+","+Integer.parseInt(pd.get("pageSize").toString()));
                putProductMsg(productList);
                return ResultMsg.success("查询成功",null,productList);
            }else{
                return ResultMsg.fail("缺少参数","pageStart,pageSize,type必传",null);
            }
        }catch (NumberFormatException ne){
            ne.printStackTrace();
            return ResultMsg.fail("查询条件错误",("pageStart,pageSize只能是数字,传参是字符串类型还是整数浮点数类型无所谓:"+ ne.toString()),null);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }


    /**
     * 首页数据加载
     * @return 这里只介绍返回值里data的格式:{
     *     bannerNews(顶部新闻轮播图):[
     *          {id,title...},{id,title...},...
     *     ],
     *     News(中部"云科头条"):[
     *          {id,title...},{id,title...},...
     *     ],
     *     commendProducts(推荐产品):[
     *          {id,name...},{id,name...},...
     *     ],
     *     hotProducts(热销产品):[
     *          {id,name...},{id,name...},...
     *     ]
     *
     * }
     */
    @RequestMapping("index")
    @ResponseBody
    public ResultMsg index(){
        try{
            Map<String,Object>result=new HashMap<>();
            List<Map<String,Object>>bannerNews=dao.selectBySQL("select * from "+NewsTableName+" order by commit_time desc limit 0,3");
            for (int i=0;i<bannerNews.size();i++){
                List<Map<String,Object>>pictures=dao.selectBySQL("select * from "+PictureTableName+" where base_id='"+bannerNews.get(i).get("base_id")+"'");
                if(!Tool.listIsNull(pictures))bannerNews.get(i).put("pictureID",pictures.get(0).get("id"));
            }
            List<Map<String,Object>>News=dao.selectBySQL("select * from "+NewsTableName+" order by commit_time desc limit 0,2");
            List<Map<String,Object>>commendProducts=dao.selectBySQL("select * from "+MyProductTableName+" where type='commend' order by commit_time desc limit 0,3");
            putProductMsg(commendProducts);
            List<Map<String,Object>>hotProducts=dao.selectBySQL("select * from "+MyProductTableName+" where type='hot' order by commit_time desc limit 0,3");
            putProductMsg(hotProducts);
            result.put("bannerNews",bannerNews);
            result.put("News",News);
            result.put("commendProducts",commendProducts);
            result.put("hotProducts",hotProducts);
            return ResultMsg.success("查询成功",null,result);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    private void putProductMsg(List<Map<String, Object>> Products) {
        for (int i = 0; i < Products.size(); i++) {
            List<Map<String, Object>> pictures = dao.selectBySQL("select * from " + PictureTableName + " where base_id='" + Products.get(i).get("base_id") + "'");
            if (!Tool.listIsNull(pictures)) Products.get(i).put("pictureID", pictures.get(0).get("id"));
            Products.get(i).put("comment_count", dao.selectBySQL("select count(*) count from " + CommentTableName + " where my_product_id='" + Products.get(i).get("id") + "'").get(0).get("count"));
            Products.get(i).put("commend_count", dao.selectBySQL("select count(*) count from " + CommendTableName + " where product_id='" + Products.get(i).get("id") + "'").get(0).get("count"));
        }
    }


    /**
     * 登录发送验证码
     * 参数: phone-手机号(字符串:只做了为空的验证)
     * @return
     */
    @RequestMapping("sendCode")
    @ResponseBody
    public ResultMsg sendCode() {
        try {
            if(Tool.isNull(getPageData().get("phone")))return ResultMsg.fail("手机号不能为空",null,null);
            int code=Tool.getRandomNum();
            String returnMsg=DuanXin_LiJun.tplSendSms(String.valueOf(code),getPageData().get("phone").toString());
            JSONObject returnObject=new JSONObject().fromObject(returnMsg);
            System.out.println(returnObject);
            if (!Tool.isNull(returnObject.get("code"))&&"0".equals(returnObject.get("code").toString())){
                getSession().setAttribute(FinalStaticString.PAGE_CODE,String.valueOf(code));
                return ResultMsg.success(!Tool.isNull(returnObject.get("msg"))?returnObject.get("msg").toString():"",!Tool.isNull(returnObject.get("detail"))?returnObject.get("detail").toString():"",null);
            }else{
                return ResultMsg.success(!Tool.isNull(returnObject.get("msg"))?returnObject.get("msg").toString():"",!Tool.isNull(returnObject.get("detail"))?returnObject.get("detail").toString():"",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    /**
     * 用户登录
     * 参数: phone-手机号(字符串:只做了为空的验证) code-验证码(字符串:只做了为空的验证)
     * @return
     */
    @RequestMapping("userLogin")
    @ResponseBody
    public ResultMsg userLogin(){
        try{
            PageData pd=getPageData();
            if(Tool.isNull(pd.get("phone")))return ResultMsg.fail("手机号不能为空",null,null);
            if(Tool.isNull(pd.get("code")))return ResultMsg.fail("验证码不能为空",null,null);
            if(Tool.isNull(getSession().getAttribute(FinalStaticString.PAGE_CODE)))return ResultMsg.fail("请发送验证码",null,null);
            if(!pd.get("code").equals(getSession().getAttribute(FinalStaticString.PAGE_CODE)))return ResultMsg.fail("验证码错误",null,null);
            List<Map<String,Object>>users=dao.selectBySQL("select * from "+UserTableName+" where phone='"+pd.get("phone")+"'");
            if(Tool.listIsNull(users)){
                dao.insertBySQL("insert into "+UserTableName+" (phone,wechat,user_commit_time) values ('"+pd.get("phone")+"','0','"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()) +"')");
                users=dao.selectBySQL("select * from "+UserTableName+" where phone='"+pd.get("phone")+"'");
                ((HttpSession)Tool.getRequest_Response_Session()[2]).setAttribute(FinalStaticString.PAGE_USER,users.get(0));
                return ResultMsg.success("登录成功",null,users.get(0));
            }else{
                ((HttpSession)Tool.getRequest_Response_Session()[2]).setAttribute(FinalStaticString.PAGE_USER,users.get(0));
                return ResultMsg.success("登录成功",null,users.get(0));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    /**
     * 根据手机号修改用户资料(需登录)
     * 参数: name-用户姓名(或从微型获取昵称)(字符串:不做校验) photo-头像(字符串:不做校验,base64码,前面加文件后缀名加个半角逗号隔开,有就存,没有就不存)
     *       company_name-(字符串:不做校验) area_tag_province-省ID(字符串:不做校验) area_tag_city-市ID(字符串:不做校验)
     *       address-详细地址(字符串:不做校验) wechat-是否获取微信信息(字符串:不做校验,0:否,1:是) phone-账号设置/手机号/登录帐号(字符串:只做了为空的验证)
     * @return 更新后的user
     */
    @RequestMapping("upUserByPhone")
    @ResponseBody
    public ResultMsg upUser(){
        try{
            PageData pd=getPageData();
            if(getUser()==null)return ResultMsg.fail("请登录",null,null);
            if(Tool.isNull(pd.get("phone")))return ResultMsg.fail("手机号不能为空",null,null);
            if(!Tool.isNull(pd.get("photo"))){
                if(pd.get("photo").toString().split(",").length==2){
                    String  ffile = DateUtil.getDays();
                    String FileBase64String=pd.get("photo").toString().split(",")[1];
                    String FileSuffix=pd.get("photo").toString().split(",")[0];
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] bytes1 = decoder.decodeBuffer(FileBase64String);
                    String fileName=UuidUtil.get32UUID();
                    File filePath=new File(FinalStaticString.FILEPATHIMG + ffile);
                    if(!filePath.exists())filePath.mkdir();
                    OutputStream out=new FileOutputStream(FinalStaticString.FILEPATHIMG  + ffile+"/"+fileName+"."+FileSuffix);
                    out.write(bytes1);
                    out.flush();
                    out.close();
                    pd.put("photo",this.FILEPATHIMG+ffile+"/"+fileName+"."+FileSuffix);
                }
            }
            if(!pd.get("phone").equals(getUser().get("phone")))return ResultMsg.fail("不能修改他人账号",null,null);
            dao.updateBySQL("update "+UserTableName+" set name='"+pd.get("name")+"',photo='"+pd.get("photo")+"',company_name='"+pd.get("company_name")+"',area_tag_province='"+pd.get("area_tag_province")+"',area_tag_city='"+pd.get("area_tag_city")+"',address='"+pd.get("address")+"',wechat='"+pd.get("wechat")+"' where phone='"+pd.get("phone")+"'");;
            List<Map<String,Object>>users=dao.selectBySQL("select * from "+UserTableName+" where phone='"+pd.get("phone")+"'");
            if(Tool.listIsNull(users))return ResultMsg.fail((pd.get("phone").toString()+"账号不存在"),null,null);
            return ResultMsg.success("保存成功",null,users.get(0));
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    /**
     * 获取省/市集合/数组(可不传参,可以传province或者city之一,两个都传,优先查province)
     * @return 这里只介绍返回值里data的格式:
     *      传province:{
     *          province:{
     *              Map<key,value>
     *          }
     *      }
     *      传city:{
     *          city:{
     *              Map<key,value>
     *          }
     *      }
     *      什么都不传或者其他值:{
     *          provinces:[
     *              {
     *              id:xxx,
     *              name:xxx,
     *              ...
     *              citys:[
     *                  {
     *                      id:xxx,
     *                      name:xxx,
     *                      ...
     *                  }
     *                  ...
     *                ]
     *              }
     *              ...
     *            ]
     *          }
     */
    @RequestMapping("getArea")
    @ResponseBody
    public ResultMsg getArea(){
        try{
            PageData pd=getPageData();
            Map<String,Object>result=new HashMap<>();
             if(!Tool.isNull(pd.get("province"))){
                List<Map<String,Object>>provinces=dao.selectBySQL("select * from "+AreaTagTableName+" where id='"+pd.get("province")+"'");
                if(!Tool.listIsNull(provinces)){
                    result.put("province",provinces.get(0));
                    return ResultMsg.success("查询成功",null,result);
                }else{
                    return ResultMsg.fail("查询失败,ID为:"+pd.get("province")+"的的'省'不存在或被删除",null,null);
                }
            }else if(!Tool.isNull(pd.get("city"))){
                List<Map<String,Object>>citys=dao.selectBySQL("select * from "+AreaTagTableName+" where id='"+pd.get("city")+"'");
                if(!Tool.listIsNull(citys)){
                    result.put("city",citys.get(0));
                    return ResultMsg.success("查询成功",null,result);
                }else{
                    return ResultMsg.fail(("查询失败,ID为:"+pd.get("city")+"的'市'不存在或被删除"),null,null);
                }
            }else{
                List<Map<String,Object>>provinces=dao.selectBySQL("select * from "+AreaTagTableName+" where parent_area_tag_id='0'");
                for (int i=0;i<provinces.size();i++){
                    provinces.get(i).put("citys",dao.selectBySQL("select * from "+AreaTagTableName+" where parent_area_tag_id='"+provinces.get(i).get("id")+"'"));
                }
                result.put("provinces",provinces);
                return ResultMsg.success("查询成功",null,result);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    /**
     * 根据my_product_id或者product_id之一查询评论数量和评论内容,这两个id必传其中之一.pageStart和pageSize可传可不传,如果要传,两个必须同时传.
     * (例子:pageStart=1,pageSize=10,十条为一页,查询第一页)
     * 参数: my_product_id-热销/推荐商品id(字符串/数字:不做校验) product_id-云科数据库商品id(字符串/数字:不做校验)
     *       pageStart-页数(字符串/数字:校验是否为数字) pageSize-每页多少条(字符串/数字:校验是否为数字)
     * @return 这里只介绍返回值里data的格式:{
     *                                         count:该商品管理员已通过审核的评论的总数数字(必有),
     *                                         comments:根据分页条件查询得来的数组/集合,里面是一个个map,map没有再嵌套什么数组/集合,可以直接遍历comments用于展示(pageStart和pageSize同时传才有)
     *                                      }
     */
    @RequestMapping("getComment")
    @ResponseBody
    public ResultMsg getComment(){
        try{
            PageData pd=getPageData();
            Map<String,Object>result=new HashMap<>();
            List<Map<String,Object>>comments=new ArrayList<>();
            if(!Tool.isNull(pd.get("product_id"))){
                result.put("count",dao.selectBySQL("select count(*) count from "+CommentTableName+" where check_up=1 and product_id="+pd.get("product_id")).get(0).get("count"));
                if(!Tool.isNull(pd.get("pageStart"))&&!Tool.isNull(pd.get("pageSize"))){
                    comments=dao.selectBySQL("select * from "+CommentTableName+" where check_up=1 and product_id="+pd.get("product_id")+" limit "+(((Integer.parseInt(pd.get("pageStart").toString())>0?Integer.parseInt(pd.get("pageStart").toString()):1)-1)*Integer.parseInt(pd.get("pageSize").toString()))+","+Integer.parseInt(pd.get("pageSize").toString()));
                    putUserMsg(comments);
                    result.put("comments",comments);
                }
                return ResultMsg.success("查询成功",null,result);
            }
            if(!Tool.isNull(pd.get("my_product_id"))){
                result.put("count",dao.selectBySQL("select count(*) count from "+CommentTableName+" where check_up=1 and my_product_id='"+pd.get("my_product_id")+"'").get(0).get("count"));
                if(!Tool.isNull(pd.get("pageStart"))&&!Tool.isNull(pd.get("pageSize"))){
                    comments=dao.selectBySQL("select * from "+CommentTableName+" where check_up=1 and my_product_id='"+pd.get("my_product_id")+"' limit "+(((Integer.parseInt(pd.get("pageStart").toString())>0?Integer.parseInt(pd.get("pageStart").toString()):1)-1)*Integer.parseInt(pd.get("pageSize").toString()))+","+Integer.parseInt(pd.get("pageSize").toString()));
                    putUserMsg(comments);
                    result.put("comments",comments);
                }
                return ResultMsg.success("查询成功",null,result);
            }
            if(!Tool.isNull(pd.get("new_id"))){
                result.put("count",dao.selectBySQL("select count(*) count from "+CommentTableName+" where check_up=1 and new_id='"+pd.get("new_id")+"'").get(0).get("count"));
                if(!Tool.isNull(pd.get("pageStart"))&&!Tool.isNull(pd.get("pageSize"))){
                    comments=dao.selectBySQL("select * from "+CommentTableName+" where check_up=1 and new_id='"+pd.get("new_id")+"' limit "+(((Integer.parseInt(pd.get("pageStart").toString())>0?Integer.parseInt(pd.get("pageStart").toString()):1)-1)*Integer.parseInt(pd.get("pageSize").toString()))+","+Integer.parseInt(pd.get("pageSize").toString()));
                    putUserMsg(comments);
                    result.put("comments",comments);
                }
                return ResultMsg.success("查询成功",null,result);
            }
            return ResultMsg.fail("查询条件错误","必须传入my_product_id(热销/推荐商品id)或者product_id(云科数据库商品id)new_id(新闻id)之一",null);
        }catch (NumberFormatException ne){
            ne.printStackTrace();
            return ResultMsg.fail("查询条件错误",("pageStart,pageSize只能是数字,传参是字符串类型还是整数浮点数类型无所谓:"+ ne.toString()),null);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    private void putUserMsg(List<Map<String, Object>> comments) throws Exception {
        List<Map<String, Object>> users;
        for (int i = 0; i < comments.size(); i++) {
            comments.get(i).remove("my_product_id");
            comments.get(i).remove("product_id");
            comments.get(i).remove("admin");
            comments.get(i).remove("check_up");
            users = dao.selectBySQL("select * from " + UserTableName + " where id=" + comments.get(i).get("user_id"));
            comments.get(i).put("user_name", !Tool.listIsNull(users) ? users.get(0).get("name") : "");
            comments.get(i).put("user_photo", !Tool.listIsNull(users) ? users.get(0).get("photo") : "");
            comments.get(i).put("commit_time", Tool.isNull(comments.get(i).get("commit_time")) ? "" : Tool.getTimes(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(comments.get(i).get("commit_time"))));
            comments.get(i).remove("user_id");
        }
    }

    /**
     * 提交评论接口(需登录): submitComment
     * 参数: my_product_id-热销/推荐商品id(字符串/数字:不做校验)
     *       new_id-新闻id(字符串/数字:不做校验)
     *       product_id-云科数据库商品id(字符串/数字:不做校验)
     *       product_name-云科数据库产品名称(字符串:不做校验)
     * 说明: my_product_id,new_id,product_id三个ID最少传一个
     *       当传product_id时,必传product_name
     * 返回: 成功与否信息
     */
    @RequestMapping("submitComment")
    @ResponseBody
    public ResultMsg submitComment(){
        try{
            if(getUser()==null)return ResultMsg.fail("请登录",null,null);
            PageData pd=getPageData();
            if(!Tool.isNull(pd.get("product_id"))&&Tool.isNull(pd.get("product_name")))return ResultMsg.fail("缺少产品名称","已收到product_id,所以必须传product_name",null);
            if(!Tool.isNull(pd.get("my_product_id"))||!Tool.isNull(pd.get("product_id"))||!Tool.isNull(pd.get("new_id"))){
                String xxx_id=!Tool.isNull(pd.get("my_product_id"))?"my_product_id":!Tool.isNull(pd.get("product_id"))?"product_id":"new_id";
                String xxx_id_value=!Tool.isNull(pd.get("my_product_id"))?pd.get("my_product_id").toString():!Tool.isNull(pd.get("product_id"))?("'"+pd.get("product_id").toString()+"'"):pd.get("new_id").toString();
                dao.insertBySQL("insert into "+CommentTableName+" ("+xxx_id+",user_id,content,check_up,commit_time"+(!Tool.isNull(pd.get("product_name"))?",product_name":"")+") values" +
                        " ("+xxx_id_value+","+getUser().get("id")+","+pd.get("content")+",0,"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()))+(!Tool.isNull(pd.get("product_name"))?(","+pd.get("product_name").toString()):"")+")");
                return ResultMsg.success("提交成功",null,null);
            }else{
                return ResultMsg.fail("缺少参数","必须传入my_product_id(热销/推荐商品id)或者product_id(云科数据库商品id)new_id(新闻id)之一",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }


    /**
     * 点赞接口(需登录): submitCommend
     * 参数: product_id-热销/推荐商品id(字符串/数字:不做校验)
     *       new_id-新闻id(字符串/数字:不做校验)
     *       other_product_id-云科数据库商品id(字符串/数字:不做校验)
     * 说明: my_product_id,new_id,product_id三个ID最少传一个
     * 返回: 成功与否信息
     */
    @RequestMapping("submitCommend")
    @ResponseBody
    public ResultMsg submitCommend(){
        try{
            if(getUser()==null)return ResultMsg.fail("请登录",null,null);
            PageData pd=getPageData();
            if(!Tool.isNull(pd.get("other_product_id"))||!Tool.isNull(pd.get("product_id"))||!Tool.isNull(pd.get("new_id"))){
                List<Map<String,Object>>myCommends=dao.selectBySQL("select * from "+CommendTableName+" where user_id="+getUser().get("id"));
                if(!Tool.listIsNull(myCommends))return ResultMsg.fail("不能重复点赞",null,null);
                String xxx_id=!Tool.isNull(pd.get("other_product_id"))?"other_product_id":!Tool.isNull(pd.get("product_id"))?"product_id":"new_id";
                String xxx_id_value=!Tool.isNull(pd.get("product_id"))?pd.get("product_id").toString():!Tool.isNull(pd.get("other_product_id"))?("'"+pd.get("other_product_id").toString()+"'"):pd.get("new_id").toString();
                dao.insertBySQL("insert into "+CommendTableName+" ("+xxx_id+",user_id,commit_time) values" +
                        " ("+xxx_id_value+","+getUser().get("id")+",'"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()))+"')");
                return ResultMsg.success("点赞成功",null,null);
            }else{
                return ResultMsg.fail("缺少参数","必须传入product_id(热销/推荐商品id)或者other_product_id(云科数据库商品id)new_id(新闻id)之一",null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }


    /**
     * 根据other_product_id或者product_id之一查询评论数量和评论内容,这两个id必传其中之一
     * 参数: product_id-热销/推荐商品id(字符串/数字:不做校验) other_product_id-云科数据库商品id(字符串/数字:不做校验)
     * @return 这里只介绍返回值里data的格式:{
     *                                         count:该产品点赞的总数数字(必有)
     *                                      }
     */
    @RequestMapping("getCommend")
    @ResponseBody
    public ResultMsg getCommend(){
        try{
            PageData pd=getPageData();
            Map<String,Object>result=new HashMap<>();
            List<Map<String,Object>>commends=new ArrayList<>();
            if(!Tool.isNull(pd.get("product_id"))){
                commends=dao.selectBySQL("select count(*) count from "+CommendTableName+" where product_id="+pd.get("product_id").toString());
                is_commend(result, commends);
                result.put("count",commends.get(0).get("count"));
                return ResultMsg.success("查询成功",null,result);
            }
            if(!Tool.isNull(pd.get("other_product_id"))){
                commends=dao.selectBySQL("select count(*) count from "+CommendTableName+" where other_product_id='"+pd.get("other_product_id").toString()+"'");
                is_commend(result, commends);
                result.put("count",commends.get(0).get("count"));
                return ResultMsg.success("查询成功",null,result);
            }
            if(!Tool.isNull(pd.get("new_id"))){
                commends=dao.selectBySQL("select count(*) count from "+CommendTableName+" where new_id="+pd.get("new_id").toString());
                is_commend(result, commends);
                result.put("count",commends.get(0).get("count"));
                return ResultMsg.success("查询成功",null,result);
            }
            return ResultMsg.fail("查询条件错误","必须传入product_id(热销/推荐商品id)other_product_id(云科数据库商品id)new_id(新闻id)之一",null);
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsg.fail("服务器发生错误",e.toString(),null);
        }
    }

    private void is_commend(Map<String, Object> result, List<Map<String, Object>> commends) {
        if(getUser()!=null){
            boolean is_commend=false;
            for (int i=0;i<commends.size();i++){
                if(!Tool.isNull(commends.get(i).get("user_id"))&&getUser().get("id").equals(commends.get(i).get("user_id"))){
                    is_commend=true;
                }
            }
            result.put("is_commend",is_commend);
        }
    }


    /** new PageData对象
     * @return
     */
    private PageData getPageData(){
        return new PageData((HttpServletRequest)Tool.getRequest_Response_Session()[0]);
    }

    private Map<String, Object> getUser(){
        return  ((HttpSession)Tool.getRequest_Response_Session()[2]).getAttribute(FinalStaticString.PAGE_USER)==null?null:(Map<String, Object>)(((HttpSession)Tool.getRequest_Response_Session()[2]).getAttribute(FinalStaticString.PAGE_USER));
    }
}
