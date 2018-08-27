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
 * 产品点赞表
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-18
 */
@TableName("sys_other_product_commit")
public class OtherProductCommit extends Model<OtherProductCommit> {

    private static final long serialVersionUID = 1L;

    /**
     * 产品点赞表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 第三方商品id
     */
    @TableField("other_product_id")
    private String otherProductId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 更新时间
     */
    @TableField("commit_time")
    private Date commitTime;
    /**
     * 自己产品id
     */
    @TableField("product_id")
    private Integer productId;
    /**
     * 新闻表id
     */
    @TableField("new_id")
    private Integer newId;
    /**
     * 云科商品名称
     */
    @TableField("product_name")
    private String productName;
    /**
     * 云科产品图片
     */
    @TableField("product_img_url")
    private String productImgUrl;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtherProductId() {
        return otherProductId;
    }

    public void setOtherProductId(String otherProductId) {
        this.otherProductId = otherProductId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getnewId() {
        return newId;
    }

    public void setnewId(Integer newId) {
        this.newId = newId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OtherProductCommit{" +
        "id=" + id +
        ", otherProductId=" + otherProductId +
        ", userId=" + userId +
        ", commitTime=" + commitTime +
        ", productId=" + productId +
        ", newId=" + newId +
        ", productName=" + productName +
        ", productImgUrl=" + productImgUrl +
        "}";
    }
}
