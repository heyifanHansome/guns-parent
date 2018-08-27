package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 咨询留言表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@TableName("sys_message_board")
public class MessageBoard extends Model<MessageBoard> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 留言板分类标签id
     */
    @TableField("tag_name")
    private String tagName;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 公司名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 市
     */
    private String city;
    /**
     * 省
     */
    private String province;
    /**
     * 联系人
     */
    private String Contact;
    /**
     * 提交时间
     */
    @TableField("commit_time")
    private Date commitTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageBoard{" +
                "id=" + id +
                "tagName" + tagName +
                ", content=" + content +
                ", companyName=" + companyName +
                ", phone=" + phone +
                ", city=" + city +
                ", province=" + province +
                ", Contact=" + Contact +
                ", commitTime=" + commitTime +
                "}";
    }
}
