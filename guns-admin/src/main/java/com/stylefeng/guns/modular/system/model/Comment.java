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
 *
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@TableName("sys_comment")
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 我们的产品ID
     */
    @TableField("my_product_id")
    private Integer myProductId;
    /**
     * 云科的产品name
     */
    @TableField("product_id")
    private String productId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 管理员ID
     */
    private String admin;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 提交评论时间
     */
    @TableField("commit_time")
    private Date commitTime;
    /**
     * 0,否,1.是
     */
    @TableField("check_up")
    private Integer checkUp;
    @TableField("product_name")
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMyProductId() {
        return myProductId;
    }

    public void setMyProductId(Integer myProductId) {
        this.myProductId = myProductId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Integer getCheckUp() {
        return checkUp;
    }

    public void setCheckUp(Integer checkUp) {
        this.checkUp = checkUp;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", myProductId=" + myProductId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", adminId=" + admin +
                ", content=" + content +
                ", commitTime=" + commitTime +
                ", checkUp=" + checkUp +
                "}";
    }
}
