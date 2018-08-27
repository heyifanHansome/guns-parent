package com.stylefeng.guns.modular.DTO;



import java.io.Serializable;

/**
 * Created by Heyifan Cotter on 2018/8/2.
 */
public class TPdtSysSeachFeatureMst implements Serializable {
    private String ID;
    private String  Name;
    private String SeachType;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSeachType() {
        return SeachType;
    }

    public void setSeachType(String seachType) {
        SeachType = seachType;
    }

    public TPdtSysSeachFeatureMst(String ID, String name, String seachType) {
        this.ID = ID;
        Name = name;
        SeachType = seachType;
    }

    public TPdtSysSeachFeatureMst() {
    }

    @Override
    public String toString() {
        return "TPdtSysSeachFeatureMst{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", SeachType='" + SeachType + '\'' +
                '}';
    }
}
