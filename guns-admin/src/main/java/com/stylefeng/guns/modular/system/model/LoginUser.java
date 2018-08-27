package com.stylefeng.guns.modular.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@TableName("sys_login_user")
public class LoginUser extends Model<LoginUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户姓名(或从微型获取昵称)
     */
    private String name;
    /**
     * 头像(或者储存微型登录头像)
     */
    private String photo;
    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 省_id
     */
    @TableField("area_tag_province")
    private String areaTagProvince;
    /**
     * 市_id
     */
    @TableField("area_tag_city")
    private String areaTagCity;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 是否获取微信信息,0:否,1:是
     */
    private Integer wechat;
    /**
     * 账号设置/手机号/登录帐号
     */
    private String phone;
    /**
     * 用户提交时间
     */
    @TableField("user_commit_time")
    private Date userCommitTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAreaTagProvince() {
        return areaTagProvince;
    }

    public void setAreaTagProvince(String areaTagProvince) {
        this.areaTagProvince = areaTagProvince;
    }

    public String getAreaTagCity() {
        return areaTagCity;
    }

    public void setAreaTagCity(String areaTagCity) {
        this.areaTagCity = areaTagCity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWechat() {
        return wechat;
    }

    public void setWechat(Integer wechat) {
        this.wechat = wechat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getUserCommitTime() {
        return userCommitTime;
    }

    public void setUserCommitTime(Date userCommitTime) {
        this.userCommitTime = userCommitTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
        "id=" + id +
        ", name=" + name +
        ", photo=" + photo +
        ", companyName=" + companyName +
        ", areaTagProvince=" + areaTagProvince +
        ", areaTagCity=" + areaTagCity +
        ", address=" + address +
        ", wechat=" + wechat +
        ", phone=" + phone +
        ", userCommitTime=" + userCommitTime +
        "}";
    }
}
