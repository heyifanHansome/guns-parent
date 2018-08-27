package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 关于我们
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-21
 */
@TableName("sys_about_company")
public class AboutCompany extends Model<AboutCompany> {

    private static final long serialVersionUID = 1L;

    /**
     * 关于我们表ID
     */
    private Integer id;
    /**
     * 云科图片
     */
    @TableField("about_picture")
    private String aboutPicture;
    /**
     * 关于我们公司
     */
    @TableField("about_company")
    private String aboutCompany;
    /**
     * 联系我们
     */
    @TableField("contact_us")
    private String contactUs;

    @TableField("commit_time")
    private Date commitTime;

    @TableField("update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAboutPicture() {
        return aboutPicture;
    }

    public void setAboutPicture(String aboutPicture) {
        this.aboutPicture = aboutPicture;
    }

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }


    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AboutCompany{" +
            "id=" + id +
            ", aboutPicture=" + aboutPicture +
            ", aboutCompany=" + aboutCompany +
            ", createTime=" + commitTime +
            ", updateTime=" + updateTime +
            ", contactUs=" + contactUs +
            "}";
    }
}
