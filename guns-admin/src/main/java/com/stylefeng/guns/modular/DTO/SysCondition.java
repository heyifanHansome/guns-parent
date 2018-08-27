package com.stylefeng.guns.modular.DTO;


import java.io.Serializable;

/**
 * Created by Heyifan Cotter on 2018/8/2.
 */

public class SysCondition implements Serializable {

    private String SeachType;
    private String UpUnitSourceDtlID;
    private String OptionSourceDtlID;
    private String Name;

    public String getSeachType() {
        return SeachType;
    }

    public void setSeachType(String seachType) {
        SeachType = seachType;
    }

    public String getUpUnitSourceDtlID() {
        return UpUnitSourceDtlID;
    }

    public void setUpUnitSourceDtlID(String upUnitSourceDtlID) {
        UpUnitSourceDtlID = upUnitSourceDtlID;
    }

    public String getOptionSourceDtlID() {
        return OptionSourceDtlID;
    }

    public void setOptionSourceDtlID(String optionSourceDtlID) {
        OptionSourceDtlID = optionSourceDtlID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public SysCondition(String seachType, String upUnitSourceDtlID, String optionSourceDtlID, String name) {
        SeachType = seachType;
        UpUnitSourceDtlID = upUnitSourceDtlID;
        OptionSourceDtlID = optionSourceDtlID;
        Name = name;
    }

    public SysCondition() {
    }

    @Override
    public String toString() {
        return "SysCondition{" +
                "SeachType='" + SeachType + '\'' +
                ", UpUnitSourceDtlID='" + UpUnitSourceDtlID + '\'' +
                ", OptionSourceDtlID='" + OptionSourceDtlID + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
