package com.stylefeng.guns.modular.upChina;

import com.stylefeng.guns.core.util.ResultMsg;
import com.stylefeng.guns.modular.DTO.SysCondition;
import com.stylefeng.guns.modular.DTO.SysConditionDTO;
import com.stylefeng.guns.modular.DTO.TPdtSysSeachFeatureMst;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * upChina api 前台接口文档
 * Created by Heyifan on 18/7/19.
 */

@RestController
@RequestMapping("upChina")
public class UpChinaController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 定义全局变量 url前缀
     */
    private String urlPrefix;
    /**
     * 定义全局变量 url后缀
     */
    private String urlSuffix;
    /**
     * 定义全局变量 url
     */
    private String url;
    /**
     * 初始化构造方法
     */
    /*@PostConstruct*/

    /**
     * 查询获得分类详情
     *
     * @return
     */
    @RequestMapping("getCategoryInfo")
    public List<Map<String, String>> getCategoryInfo() {
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
        return categoryInfo;
    }
//
//    public  List<Map<String, String>>hello(){
//        List<Map<String, String>> maps = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
//        map.put("some","fdasfd");
//        maps.add(map);
//    return  maps;
//    }

    /**
     * 根据分类产品id获取查询条件需要的数据
     *
     * @param CategoryID 分类Id
     * @return 返回前台数据
     */
    @RequestMapping(value = "HeyifangetSysSeachFeatureList", method = RequestMethod.GET)
    public ResponseEntity<?> /*Map<String, List<SysConditionDTO>>*/ HeyifangetSysSeachFeatureList(String CategoryID) {
        /**
         * 拼接url
         */
        urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtGetMainListByQueryConditionAndCategoryIDPageQuery&JsonQueryCondition=&LangID=CN&ReadMode=1&PageNo=1&PageSize=1&CategoryID=";
        urlSuffix = "&JustUse=1&IncludeRelateInfo=1";
        url = urlPrefix + CategoryID + urlSuffix;
        /**
         * 请求第三方接口返回text/html封装成json
         */
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String strBody = response.getBody();

        long startTime = System.currentTimeMillis();   //获取开始时间


        List<SysConditionDTO> mainConditionDTOS = null;
        List<SysConditionDTO> otherConditionDTOS = null;
        try {
            JSONObject jsonObject = JSONObject.fromObject(strBody);
            Map<String, JSONObject> Select = new HashMap<>();

            /**
             * 定义选项数据源，单位数据源，系统查询参数 并封装成json格式
             */
            JSONObject OptionSourceList = JSONObject.fromObject(jsonObject.get("OptionSourceList"));

            JSONObject SysSeachFeatureList = JSONObject.fromObject(jsonObject.get("SysSeachFeatureList"));

            JSONObject UnitSourceList = JSONObject.fromObject(jsonObject.get("UnitSourceList"));

            /**
             *定义Select 为Map<String, JSONObject> Select = new HashMap<>()格式 来装上面封装好的参数
             */

            Select.put("OptionSourceList", OptionSourceList);
            Select.put("SysSeachFeatureList", SysSeachFeatureList);
            Select.put("UnitSourceList", UnitSourceList);

            /**
             * 获取可以循环遍历的系统查询参数以  List<Map<String, Object>>  格式装入sysList里面
             */
            Object SysList = SysSeachFeatureList.get("Items");
            List<Map<String, Object>> sysList = (List<Map<String, Object>>) SysList;


            /**
             * 获取可以循环遍历的选项数据源  List<Map<String, Object>>  格式装入optList里面
             */
            Object OptList = OptionSourceList.get("Items");
            List<Map<String, Object>> optList = (List<Map<String, Object>>) OptList;


            /**
             * 获取可以循环遍历的单位数据源以  List<Map<String, Object>>  格式装入UnitList里面
             */
            Object UnitList = UnitSourceList.get("Items");
            List<Map<String, Object>> unitList = (List<Map<String, Object>>) UnitList;

            /**
             * 封装查询条件
             */
            List<SysConditionDTO> sysConditionDTOS = new ArrayList<>();
            /**
             * 循环比例系统查询参数，这里是可以使用增强for循环便利，当时考虑foreach的一些弊端，改用普通for循环
             */

            for (int i = sysList.size() - 1; i >= 0; i--) {
                /**
                 * 便利每个系统查询参数的TPdtSysSeachFeatureMst的值转化为json格式好用来取值
                 */
                JSONObject TPdtSysSeachFeatureMstList = JSONObject.fromObject(sysList.get(i).get("TPdtSysSeachFeatureMst"));

                /**
                 * 这里面使用构造函数 构造一个系统查询参数在遍历单位数据源和选项数据源的一个对象
                 */
                TPdtSysSeachFeatureMst TPdtSysSeachFeatureMsts = new TPdtSysSeachFeatureMst(TPdtSysSeachFeatureMstList.get("ID").toString(), TPdtSysSeachFeatureMstList.get("Name").toString(), TPdtSysSeachFeatureMstList.get("SeachType").toString());
                /**
                 * 封装查询结果集
                 */
                SysConditionDTO sysConditionDTO = new SysConditionDTO();

                /**
                 * 获得系统查询参数的TPdtSysSeachFeatureDtlList的json对象
                 */
                JSONObject TPdtSysSeachFeatureDtlList = JSONObject.fromObject(sysList.get(i).get("TPdtSysSeachFeatureDtlList"));

                sysConditionDTO.setSysSeachFeatureName(TPdtSysSeachFeatureMsts.getName());
                sysConditionDTO.setSysSeachFeatureID(TPdtSysSeachFeatureMstList.get("ID").toString());
                sysConditionDTO.setSeachType(TPdtSysSeachFeatureMstList.get("SeachType").toString());

                JSONArray TPdtSysSeachFeatureDtlListItems = JSONArray.fromObject(TPdtSysSeachFeatureDtlList.get("Items"));

                String TPdtSysSeachFeatureDtlListItemsSourceID = null;

                for (int z = 0; z < TPdtSysSeachFeatureDtlListItems.size(); z++) {
                    JSONObject ob = (JSONObject) TPdtSysSeachFeatureDtlListItems.get(z);
                    TPdtSysSeachFeatureDtlListItemsSourceID = ob.getString("SourceID");
                }
                sysConditionDTO.setSourceID(TPdtSysSeachFeatureDtlListItemsSourceID);
                SysCondition sysCondition = new SysCondition();
                sysCondition.setSeachType(TPdtSysSeachFeatureMsts.getSeachType());
                List<JSONObject> jsonObjects = new ArrayList<>();
                List<Map<String, String>> mapList = new ArrayList<>();
                for (int j = optList.size() - 1; j >= 0; j--) {
                    String SourceID = sysConditionDTO.getSourceID();
                    JSONObject OptionSourceDtlList = JSONObject.fromObject(optList.get(j).get("TPdtOptionSourceDtlList"));
                    JSONArray OptionSourceDtlListArayy = JSONArray.fromObject(OptionSourceDtlList.get("Items"));

                    String TPdtSysSeachFeatureDtlListItemsMasteId = null;
                    /**
                     * 通过系统查询参数和选项数据源进行比较如何id相同放入集合中
                     */
                    for (int z = 0; z < OptionSourceDtlListArayy.size(); z++) {
                        JSONObject ob = (JSONObject) OptionSourceDtlListArayy.get(z);
                        TPdtSysSeachFeatureDtlListItemsMasteId = ob.getString("MastID");
                    }
                    /**
                     * 判断TPdtSysSeachFeatureDtlListItemsMasteId和sourceId 是否一样
                     */
                    if (TPdtSysSeachFeatureDtlListItemsMasteId.equals(SourceID)) {
                        jsonObjects.add(JSONObject.fromObject(optList.get(j)));
                        JSONObject jsonObject1 = JSONObject.fromObject(optList.get(j).get("TPdtOptionSourceDtlList"));
                        JSONArray jsonArray = (JSONArray) jsonObject1.get("Items");
                        for (Object mapInfo : jsonArray) {
                            Map<String, String> map = new HashMap<>();
                            JSONObject mapJson = (JSONObject) mapInfo;
                            map.put("ID", mapJson.get("ID").toString());
                            map.put("Name", mapJson.get("Name").toString());
                            map.put("Type", "optList");
                            map.put("FileTypeId", sysCondition.getSeachType());
                            mapList.add(map);
                            sysConditionDTO.setCategoryInfoMap(mapList);
                        }
                        sysConditionDTO.setCategoryInfoMap(mapList);
                    }
                }

                /**
                 * 通过系统查询参数和单位数据源进行比较如何id相同放入集合中
                 */
                for (int j = unitList.size() - 1; j >= 0; j--) {
                    String SourceID = sysConditionDTO.getSourceID();
                    JSONObject OptionSourceDtlList = JSONObject.fromObject(unitList.get(j).get("TPdtUnitSourceDtlList"));
                    JSONArray OptionSourceDtlListArayy = JSONArray.fromObject(OptionSourceDtlList.get("Items"));

                    String TPdtSysSeachFeatureDtlListItemsMasteId = null;
                    for (int z = 0; z < OptionSourceDtlListArayy.size(); z++) {
                        JSONObject ob = (JSONObject) OptionSourceDtlListArayy.get(z);
                        TPdtSysSeachFeatureDtlListItemsMasteId = ob.getString("MastID");
                    }
                    if (TPdtSysSeachFeatureDtlListItemsMasteId.equals(SourceID)) {
                        jsonObjects.add(JSONObject.fromObject(unitList.get(j)));

                        JSONObject jsonObject1 = JSONObject.fromObject(unitList.get(j).get("TPdtUnitSourceDtlList"));
                        JSONArray jsonArray = JSONArray.fromObject(jsonObject1.get("Items"));
                        for (Object mapInfo : jsonArray) {
                            Map<String, String> map = new HashMap<>();
                            JSONObject mapJson = (JSONObject) mapInfo;
                            map.put("ID", mapJson.get("ID").toString());
                            map.put("Name", mapJson.get("Name").toString());
                            map.put("Type", "unitList");
                            map.put("FileTypeId", sysCondition.getSeachType());
                            mapList.add(map);
                            sysConditionDTO.setCategoryInfoMap(mapList);
                        }
                    }
                }
                sysConditionDTOS.add(sysConditionDTO);
            }

            /**
             *  因为前台需要知道那几个是主要查询参数 所以封装的结果集里面要根据系统查询参数的名称来进行判断并返回给前台显示  必须给默认值不然不可以设置顺序,当前集合为主要查询参数
             */
            mainConditionDTOS = new ArrayList<>();
            mainConditionDTOS.add(null);
            mainConditionDTOS.add(null);
            mainConditionDTOS.add(null);
            mainConditionDTOS.add(null);

            /**
             * 次要查询参数集合
             */
            otherConditionDTOS = new ArrayList<>();

            /**
             * 根据分类id查询封装主要条件和辅助查询条件
             */


            if (CategoryID.equals("000.004")) {

                /**
                 * 增强for循环遍历封装好了的返回结果集，并根据条件排序
                 */
                for (SysConditionDTO sysConditionDTO : sysConditionDTOS) {
                    if (sysConditionDTO.getSysSeachFeatureName().equals("质量等级")) {
                        mainConditionDTOS.set(0, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("标称阻值")) {
                        mainConditionDTOS.set(1, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("产品型号")) {
                        mainConditionDTOS.set(2, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("电阻温度特性")) {
                        mainConditionDTOS.set(3, sysConditionDTO);
                    } else {
                        otherConditionDTOS.add(sysConditionDTO);
                    }
                }
            }

            if (CategoryID.equals("000.006")) {

                /**
                 * 增强for循环遍历封装好了的返回结果集，并根据条件排序
                 */
                for (SysConditionDTO sysConditionDTO : sysConditionDTOS) {
                    if (sysConditionDTO.getSysSeachFeatureName().equals("质量等级")) {
                        mainConditionDTOS.set(0, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("标称阻值")) {
                        mainConditionDTOS.set(1, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("产品型号")) {
                        mainConditionDTOS.set(2, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("电阻温度特性")) {
                        mainConditionDTOS.set(3, sysConditionDTO);
                    } else {
                        otherConditionDTOS.add(sysConditionDTO);
                    }
                }
            }


            if (CategoryID.equals("000.007")) {

                /**
                 * 增强for循环遍历封装好了的返回结果集，并根据条件排序
                 */
                for (SysConditionDTO sysConditionDTO : sysConditionDTOS) {
                    if (sysConditionDTO.getSysSeachFeatureName().equals("质量等级")) {
                        mainConditionDTOS.set(0, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("标称阻值")) {
                        mainConditionDTOS.set(1, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("产品型号")) {
                        mainConditionDTOS.set(2, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("电阻温度特性")) {
                        mainConditionDTOS.set(3, sysConditionDTO);
                    } else {
                        otherConditionDTOS.add(sysConditionDTO);
                    }
                }
            }


            if (CategoryID.equals("000.008")) {

                /**
                 * 增强for循环遍历封装好了的返回结果集，并根据条件排序
                 */
                for (SysConditionDTO sysConditionDTO : sysConditionDTOS) {
                    if (sysConditionDTO.getSysSeachFeatureName().equals("质量等级")) {
                        mainConditionDTOS.set(0, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("标称阻值")) {
                        mainConditionDTOS.set(1, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("产品型号")) {
                        mainConditionDTOS.set(2, sysConditionDTO);
                    } else if (sysConditionDTO.getSysSeachFeatureName().equals("电阻温度特性")) {
                        mainConditionDTOS.set(3, sysConditionDTO);
                    } else {
                        otherConditionDTOS.add(sysConditionDTO);
                    }
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<ResultMsg>(ResultMsg.fail("服务器错误!", "", ""), HttpStatus.BAD_REQUEST);
        }
        ResultMsg resultMsg = new ResultMsg();
        Map<String, List<SysConditionDTO>> map = new HashMap<>();
        map.put("mainCondition", mainConditionDTOS);
        map.put("auxiliaryCondition", otherConditionDTOS);

        long endTime = System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
        resultMsg = ResultMsg.success("操作成功!", "操作成功!", map);
        return new ResponseEntity<ResultMsg>(resultMsg, HttpStatus.OK);
    }


    /**
     * 返回产品规格说明书
     * 分页查询
     * PageNo 页码
     * PageSize 页大小
     *
     * @param JsonQueryCondition 前台封装的查询条件和前台约定json里面应该添加CategoryID的一个节点，为了方便查询那个分类下面的。
     * @return 返回产品规格说明书前台需要的数据
     */
    @RequestMapping(value = "productSpecificationsData", method = RequestMethod.POST)
    public JSONObject returnProductSpecificationsData(@RequestBody JSONObject JsonQueryCondition) {
        String urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtGetMainListByQueryConditionAndCategoryIDPageQuery&";
        String CategoryID = "";
        String PageNo = JsonQueryCondition.get("PageNo").toString();
        String PageSize = JsonQueryCondition.get("PageSize").toString();
        String urlSuffix = "&LangID=CN&ReadMode=1&PageNo=" + PageNo + "&PageSize=" + PageSize + "&" + CategoryID + "&JustUse=1&IncludeRelateInfo=1";
        String url = urlPrefix + JsonQueryCondition + urlSuffix;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    /**
     * 根据商品Id 查询商品详情
     *
     * @param ProductID 商品Id
     * @return 返回产品详情前台需要的数据
     */
    @RequestMapping(value = "getProductionInfoByProductionId/{ProductID}", method = RequestMethod.POST)
    public JSONObject returnProductSpecificationsData(@PathVariable("ProductID") String ProductID) {

        String urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtGetProductDetailInfo&";
        String urlSuffix = "&LangID=CN&ReadMode=1";
        String url = urlPrefix + ProductID + urlSuffix;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = new JSONObject();

        return jsonObject;
    }


    /**
     * 根据查询条件分页查询返回商品列表
     *
     * @param JsonQueryCondition 前台封装的查询条件
     * @param CategoryID         大分类的ID
     * @param pageSize           需要分页的页大小
     * @param pageNo             需要分页的页码
     * @return 根据查询条件分页查询返回商品列表
     * @List<Map<String, Object>> 后台封装的查询参数
     */
    @RequestMapping(value = "getProduction", method = RequestMethod.POST)
    public ResponseEntity<?> /*List<Map<String, Object>> */getProduction(String JsonQueryCondition,
                                                                         @RequestParam(value = "CategoryID", defaultValue = "000.004", required = false) String CategoryID,
                                                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                                         @RequestParam(value = "pageNo", defaultValue = "1", required = false) Integer pageNo) {
        urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtGetMainListByQueryConditionAndCategoryIDPageQuery&LangID=CN&ReadMode=1&PageNo=" + pageNo + "&PageSize=" + pageSize + "&CategoryID=";
        urlSuffix = "&JustUse=1&IncludeRelateInfo=1&JsonQueryCondition={JsonQueryCondition}";
        url = urlPrefix + CategoryID + urlSuffix;
        ResultMsg resultMsg = new ResultMsg();


        /**
         * restTemplate 风格的post请求设置请求头
         */
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        Map<String, Object> Maps = new HashMap<String, Object>();
        Maps.put("JsonQueryCondition", JsonQueryCondition);
        //使用restTemplate.postForEntity 这种方式可以传递json数据
        ResponseEntity<String> response = restTemplate.postForEntity(url, Maps, String.class, Maps);

        String strBody = response.getBody();
        JSONObject jsonObject = JSONObject.fromObject(strBody);

        /**
         * 定义图片ID
         */
        String imageID = "";
        /**
         * 定义产品名称ID
         */
        String productNameID = "";
        /**
         * 商品类型ID
         */
        String productTypeID = "";
        /**
         * 添加异常处理
         */
        try {
            JSONObject FeatureDefList;
            FeatureDefList = JSONObject.fromObject(jsonObject.get("FeatureDefList"));
            /**
             * 获取特性集合
             */
            JSONArray FeatureDefListArrys = JSONArray.fromObject(FeatureDefList.get("Items"));
            /**
             * 循环特性集合
             */
            for (Object FeatureDefListArry : FeatureDefListArrys) {
                JSONObject jsonObjectFeatureDefListArry = (JSONObject) FeatureDefListArry;
                String featureName = jsonObjectFeatureDefListArry.get("Name").toString();
                if (featureName.trim().equals("产品名称")) {
                    productNameID = ((JSONObject) FeatureDefListArry).get("ID").toString();
                }
                if (featureName.trim().equals("产品图片")) {
                    imageID = ((JSONObject) FeatureDefListArry).get("ID").toString();
                }
                if (featureName.trim().equals("产品型号")) {
                    productTypeID = ((JSONObject) FeatureDefListArry).get("ID").toString();
                }
                continue;
            }

            JSONObject OptionSourceList = JSONObject.fromObject(jsonObject.get("ProductList"));
            JSONArray jsonArrays = JSONArray.fromObject(OptionSourceList.get("Items"));
            List<Map<String, Object>> producteInfoMaps = new ArrayList<>();
            for (Object jsonArray : jsonArrays) {
                JSONObject jsonObjectArray = JSONObject.fromObject(jsonArray);
                JSONObject TPdtFeatureList = JSONObject.fromObject(jsonObjectArray.get("TPdtFeatureList"));
                String productID = jsonObjectArray.get("ID").toString();
                List<String> stringList = new ArrayList<>();
                Map<String, Object> map = new HashMap<>();
                map.put("productId", productID);
                for (Object ItemsList : JSONArray.fromObject(TPdtFeatureList.get("Items"))) {

                    JSONObject ItemList = JSONObject.fromObject(ItemsList);
                    if (ItemList.get("FeatDefID").toString().equals(imageID)) {

//                        urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtFileTransfer&FilePathServer=";
                        String pngPath = ItemList.get("Path").toString();

//                        url = urlPrefix + pngPath;
//                        ResponseEntity<String> pngPathResponse = restTemplate.getForEntity(url, String.class);
//                        String pathBody = pngPathResponse.getBody();
//                        JSONObject pathJson = JSONObject.fromObject(pathBody);
//                        String fileUrl = pathJson.get("FileUrl").toString();

                        stringList.add(pngPath);

                        map.put("path", pngPath);

                    }
                    if (ItemList.get("FeatDefID").toString().equals(productNameID) && ItemList.get("LangID").toString().equals("CN")) {
                        map.put("productName", ItemList.get("Text").toString());
                    }
                    if (ItemList.get("FeatDefID").toString().equals(productTypeID) && ItemList.get("LangID").toString().equals("CN")) {
                        map.put("productType", ItemList.get("Text").toString());
                    }
                }
                producteInfoMaps.add(map);
            }
            List<Map<String, Object>> newMap = new ArrayList<>();


            List<String> stringImage = new ArrayList<>();
            for (Map<String, Object> producteInfoMap : producteInfoMaps) {
                stringImage.add(producteInfoMap.get("path").toString());
            }
            List<String> originalString = new ArrayList<>();
            originalString.addAll(stringImage);
            for (int i = 0; i < stringImage.size() - 1; i++) {
                for (int j = stringImage.size() - 1; j > i; j--) {
                    if (stringImage.get(j).equals(stringImage.get(i))) {
                        stringImage.remove(j);
                    }
                }
            }

            for (String s : stringImage) {
                Map<String, Object> imagePath = new HashMap<>();
                urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtFileTransfer&FilePathServer=";
                String pngPath = s;
                url = urlPrefix + pngPath;
                ResponseEntity<String> pngPathResponse = restTemplate.getForEntity(url, String.class);
                String pathBody = pngPathResponse.getBody();
                JSONObject pathJson = JSONObject.fromObject(pathBody);
                String fileUrl = pathJson.get("FileUrl").toString();
                imagePath.put(s, fileUrl);
                newMap.add(imagePath);
            }

            List<Map<String, Object>> heyifan = new ArrayList<>();

            for (Map<String, Object> producteInfoMap : producteInfoMaps) {
                Map<String, Object> info = new HashMap<>();
                info.put("productId", producteInfoMap.get("productId"));
                info.put("productType", producteInfoMap.get("productType"));
                info.put("productName", producteInfoMap.get("productName"));
                String path = producteInfoMap.get("path").toString();
                for (Map<String, Object> map : newMap) {
                        if(map.get(path)!=null){
                            info.put("Path",map.get(path));
                    }
                }
                heyifan.add(info);
            }


            resultMsg = ResultMsg.success("操作成功!", "操作成功!", heyifan);
        } catch (RestClientException msg) {
            return new ResponseEntity<ResultMsg>(ResultMsg.fail("调用接口失败，请检查查询参数!", "null", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ResultMsg>(resultMsg, HttpStatus.OK);
    }

    /**
     * 根据商品id获得商品详情
     *
     * @param productId 商品id
     * @return 商品详情数据
     */

    @RequestMapping("getProductInfoByProductId")
    public ResponseEntity<?> getProductInfoByProductId(String productId) {
        ResultMsg resultMsg = new ResultMsg();
        urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtGetProductDetailInfo&ProductID=";
        urlSuffix = "&LangID=CN&ReadMode=1";
        url = urlPrefix + productId + urlSuffix;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String strBody = response.getBody();
        List<Map<String, String>> productValueInfos = null;
        try {
            JSONObject jsonObject = JSONObject.fromObject(strBody);
            JSONObject FeatureDefList = JSONObject.fromObject(jsonObject.get("FeatureDefList"));

            JSONObject UnitSourceList = JSONObject.fromObject(jsonObject.get("UnitSourceList"));

            JSONObject OptionSourceList = JSONObject.fromObject(jsonObject.get("OptionSourceList"));

            JSONArray FeatureDefListArray = JSONArray.fromObject(FeatureDefList.get("Items"));

            List<Map<String, Object>> FeatureLists = (List<Map<String, Object>>) FeatureDefListArray;

            List<Map<String, Object>> UnitList = (List<Map<String, Object>>) (JSONArray) UnitSourceList.get("Items");

            List<Map<String, Object>> OptionList = (List<Map<String, Object>>) (JSONArray) OptionSourceList.get("Items");

            JSONObject Product = JSONObject.fromObject(jsonObject.get("Product"));
            Map<String, Object> map = new HashMap<>();
            map.put("productId", Product.get("ID"));
            JSONObject TPdtFeatureList = JSONObject.fromObject(Product.get("TPdtFeatureList"));
            JSONArray jsonArrays = JSONArray.fromObject(TPdtFeatureList.get("Items"));
            productValueInfos = new ArrayList<>();
            for (Object jsonArray : jsonArrays) {
                Map<String, String> productValueInfo = new HashMap<>();
                String FeatTypeJson = JSONObject.fromObject(jsonArray).get("FeatType").toString();
                if (FeatTypeJson.trim().equals("1")) {
                    String fileCondition = (JSONObject.fromObject(jsonArray)).get("FeatDefID").toString();
                    String fileName = "";
                    for (Map<String, Object> featureList : FeatureLists) {
                        if (fileCondition.equals(featureList.get("ID"))) {
                            fileName = featureList.get("Name").toString();
                        }
                    }
                    String UnitSourceDtlIDCondition = (JSONObject.fromObject(jsonArray)).get("UnitSourceDtlID").toString();
                    String UnitValue = "";
                    for (Map<String, Object> featureList : UnitList) {
                        for (Object item : (JSONArray) ((JSONObject) featureList.get("TPdtUnitSourceDtlList")).get("Items")) {
                            JSONObject.fromObject(item).get("ID").equals(UnitSourceDtlIDCondition);
                            UnitValue = JSONObject.fromObject(item).get("Name").toString();
                        }
                    }
                    productValueInfo.put("fileName", fileName);
                    productValueInfo.put("ExistOffset", (JSONObject.fromObject(jsonArray)).get("ExistOffset").toString());
                    productValueInfo.put("LowValue", (JSONObject.fromObject(jsonArray)).get("LowValue").toString() + UnitValue);
                    productValueInfo.put("StandardValue", (JSONObject.fromObject(jsonArray)).get("StandardValue").toString() + UnitValue);
                    productValueInfo.put("UpValue", (JSONObject.fromObject(jsonArray)).get("UpValue").toString() + UnitValue);

                    productValueInfo.put("Type", "1");
                }
                if (FeatTypeJson.trim().equals("2")) {

                    String fileCondition = (JSONObject.fromObject(jsonArray)).get("FeatDefID").toString();
                    String fileName = "";
                    for (Map<String, Object> featureList : FeatureLists) {
                        if (fileCondition.equals(featureList.get("ID"))) {
                            fileName = featureList.get("Name").toString();
                        }
                    }
                    String UnitSourceDtlIDCondition = (JSONObject.fromObject(jsonArray)).get("UnitSourceDtlID").toString();
                    String UnitValue = "";
                    for (Map<String, Object> featureList : UnitList) {
                        for (Object item : (JSONArray) ((JSONObject) featureList.get("TPdtUnitSourceDtlList")).get("Items")) {
                            JSONObject.fromObject(item).get("ID").equals(UnitSourceDtlIDCondition);
                            UnitValue = JSONObject.fromObject(item).get("Name").toString();
                        }
                    }
                    productValueInfo.put("fileName", fileName);
                    productValueInfo.put("ExistOffset", (JSONObject.fromObject(jsonArray)).get("ExistOffset").toString());
                    productValueInfo.put("LowValue", (JSONObject.fromObject(jsonArray)).get("LowValue").toString() + UnitValue);
                    productValueInfo.put("StandardValue", (JSONObject.fromObject(jsonArray)).get("StandardValue").toString() + UnitValue);
                    productValueInfo.put("UpValue", (JSONObject.fromObject(jsonArray)).get("UpValue").toString() + UnitValue);

                }
                if (FeatTypeJson.trim().equals("4")) {
                    String FileId = "";
                    String FileName = "";
                    JSONObject TPdtFeatureOptionMst = JSONObject.fromObject(JSONObject.fromObject(jsonArray).get("TPdtFeatureOptionMst"));
                    FileId = TPdtFeatureOptionMst.get("FeatDefID").toString();
                    for (Map<String, Object> featureList : FeatureLists) {
                        if (featureList.get("ID").toString().trim().equals(FileId)) {
                            FileName = featureList.get("Name").toString();
                        }
                    }
                    productValueInfo.put("fileTitle", FileName);
                    Integer value = 1;
                    JSONArray optIds = (JSONArray) ((JSONObject) (JSONObject.fromObject(jsonArray).get("TPdtFeatureOptionDtlList"))).get("Items");
                    for (Object optId : optIds) {
                        String OptionSourceDtlID = JSONObject.fromObject(optId).get("OptionSourceDtlID").toString();
                        for (Map<String, Object> stringObjectMap : OptionList) {
                            JSONArray optName = (JSONArray) (JSONObject.fromObject(stringObjectMap.get("TPdtOptionSourceDtlList")).get("Items"));
                            for (Object o : optName) {
                                if (OptionSourceDtlID.trim().equals((JSONObject.fromObject(o).get("ID"))))
                                    productValueInfo.put("fileName" + value++, JSONObject.fromObject(o).get("Name").toString());
                            }
                        }
                    }
                }

                if (FeatTypeJson.trim().equals("6") && JSONObject.fromObject(jsonArray).get("LangID").toString().trim().equals("CN")) {
                    String FeatDefID = JSONObject.fromObject(jsonArray).get("FeatDefID").toString();
                    String FeatName = null;
                    for (Map<String, Object> featureList : FeatureLists) {
                        if (featureList.get("ID").toString().trim().equals(FeatDefID)) ;
                        FeatName = featureList.get("Name").toString();
                    }
                    productValueInfo.put("FeatName", FeatName);
                    productValueInfo.put("ProductText", JSONObject.fromObject(jsonArray).get("Text").toString());
                }
                if (FeatTypeJson.trim().equals("7")) {
                    String FeatDefID = JSONObject.fromObject(jsonArray).get("FeatDefID").toString();
                    String FeatName = null;
                    for (Map<String, Object> featureList : FeatureLists) {
                        if (featureList.get("ID").toString().trim().equals(FeatDefID)) ;
                        FeatName = featureList.get("Name").toString();
                    }
                    productValueInfo.put("FeatName", FeatName);
                    productValueInfo.put("PicName", JSONObject.fromObject(jsonArray).get("PicName").toString());
                    productValueInfo.put("Type", "PNG");

                    urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtFileTransfer&FilePathServer=";
                    String pngPath = JSONObject.fromObject(jsonArray).get("Path").toString();
                    url = urlPrefix + pngPath;
                    ResponseEntity<String> pngPathResponse = restTemplate.getForEntity(url, String.class);
                    String pathBody = pngPathResponse.getBody();
                    JSONObject pathJson = JSONObject.fromObject(pathBody);

                    String fileUrl = pathJson.get("FileUrl").toString();

                    productValueInfo.put("Path", fileUrl);

                }
                /**
                 * 这里用json格式判断type类型进而根据type类型相应
                 */
                if (FeatTypeJson.trim().equals("8")) {
                    String FeatDefID = JSONObject.fromObject(jsonArray).get("FeatDefID").toString();
                    String FeatName = null;
                    for (Map<String, Object> featureList : FeatureLists) {
                        if (featureList.get("ID").toString().trim().equals(FeatDefID)) ;
                        FeatName = featureList.get("Name").toString();
                    }
                    productValueInfo.put("FeatName", FeatName);
                    productValueInfo.put("FileName", JSONObject.fromObject(jsonArray).get("FileName").toString());
                    productValueInfo.put("Type", "PDF");
                    urlPrefix = "http://app.xinyun-elec.com/XyProduct/ProductInfoSearchManager.asq?Fn=PdtFileTransfer&FilePathServer=";
                    String pdfPath = JSONObject.fromObject(jsonArray).get("Path").toString();
                    url = urlPrefix + pdfPath;
                    ResponseEntity<String> pdfPathResponse = restTemplate.getForEntity(url, String.class);
                    String pdfpathBody = pdfPathResponse.getBody();
                    JSONObject pdfpathJson = JSONObject.fromObject(pdfpathBody);
                    String fileUrl = pdfpathJson.get("FileUrl").toString();
                    productValueInfo.put("Path", fileUrl);
                }
                if (productValueInfo.size() == 0) {

                } else {
                    productValueInfos.add(productValueInfo);
                }
            }
        } catch (RestClientException e) {
            return new ResponseEntity<ResultMsg>(ResultMsg.fail("调用接口失败，请检查查询参数!", "null", null), HttpStatus.BAD_REQUEST);
        }
        resultMsg = ResultMsg.success("操作成功!", "操作成功!", productValueInfos);
        return new ResponseEntity<ResultMsg>(resultMsg, HttpStatus.OK);
    }

    @RequestMapping("city.do")
    public void city(String data) {
        JSONArray arr = new JSONArray().fromObject(data);
//	for (int i=0;i<arr.size();i++) {
//		String SHENG_DICTIONARIES_ID=UuidUtil.get32UUID();
//		String SHENG_PARENT_ID="0";
//		String SHENG_NAME=new JSONObject().fromObject(arr.get(i)).getString("name");
//		dao.insertBySQL("dao.insertBySQL",  "insert into area_tag (area_tag_id,name,parent_area_tag_id) values('"+SHENG_DICTIONARIES_ID+"','"+SHENG_NAME+"','"+SHENG_PARENT_ID+"')");
//		JSONArray arr1=new JSONArray().fromObject(new JSONObject().fromObject(arr.get(i)).get("city"));
//		for (int j = 0; j < arr1.size(); j++) {
//			String SHI_DICTIONARIES_ID=UuidUtil.get32UUID();
//			String SHI_NAME=new JSONObject().fromObject(arr1.get(j)).getString("name");
//			dao.insertBySQL("dao.insertBySQL", "insert into area_tag (area_tag_id,name,parent_area_tag_id) values('"+SHI_DICTIONARIES_ID+"','"+SHI_NAME+"','"+SHENG_DICTIONARIES_ID+"')");
//		}
//	}


    }

    /**
     * Spring配置restTemplate第三方接口  可以识别Gzip格式的Response 返回的数据 请不要修改
     *
     * @return Spring RestTemplate模板
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory()); // 使用HttpClient，支持GZIP
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 支持中文编码
        return restTemplate;
    }
}