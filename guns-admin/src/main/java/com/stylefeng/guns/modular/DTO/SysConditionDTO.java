package com.stylefeng.guns.modular.DTO;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Heyifan Cotter on 2018/8/2.
 */
public class SysConditionDTO implements Serializable {
    private String SysSeachFeatureID;
    private String SysSeachFeatureName;
//    private List<JSONObject> jsonObjects;
    private String SourceID;
    private List<Map<String, String>> CategoryInfoMap;
    private String SeachType;

    public String getSysSeachFeatureID() {
        return SysSeachFeatureID;
    }

    public void setSysSeachFeatureID(String sysSeachFeatureID) {
        SysSeachFeatureID = sysSeachFeatureID;
    }

    public String getSysSeachFeatureName() {
        return SysSeachFeatureName;
    }

    public void setSysSeachFeatureName(String sysSeachFeatureName) {
        SysSeachFeatureName = sysSeachFeatureName;
    }

    public String getSourceID() {
        return SourceID;
    }

    public void setSourceID(String sourceID) {
        SourceID = sourceID;
    }

    public List<Map<String, String>> getCategoryInfoMap() {
        return CategoryInfoMap;
    }

    public void setCategoryInfoMap(List<Map<String, String>> categoryInfoMap) {
        CategoryInfoMap = categoryInfoMap;
    }

    public String getSeachType() {
        return SeachType;
    }

    public void setSeachType(String seachType) {
        SeachType = seachType;
    }

    public SysConditionDTO(String sysSeachFeatureID, String sysSeachFeatureName, String sourceID, List<Map<String, String>> categoryInfoMap, String seachType) {
        SysSeachFeatureID = sysSeachFeatureID;
        SysSeachFeatureName = sysSeachFeatureName;
        SourceID = sourceID;
        CategoryInfoMap = categoryInfoMap;
        SeachType = seachType;
    }

    public SysConditionDTO() {
    }

    @Override
    public String toString() {
        return "SysConditionDTO{" +
                "SysSeachFeatureID='" + SysSeachFeatureID + '\'' +
                ", SysSeachFeatureName='" + SysSeachFeatureName + '\'' +
                ", SourceID='" + SourceID + '\'' +
                ", CategoryInfoMap=" + CategoryInfoMap +
                ", SeachType='" + SeachType + '\'' +
                '}';
    }
}
