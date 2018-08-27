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
@TableName("product_commit_user")
public class ProductCommitUser extends Model<ProductCommitUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品评论关系表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 评论id
     */
    @TableField("commit_id")
    private Integer commitId;
    /**
     * 商品id
     */
    @TableField("product_id")
    private Integer productId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
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

    public Integer getCommitId() {
        return commitId;
    }

    public void setCommitId(Integer commitId) {
        this.commitId = commitId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProductCommitUser{" +
        "id=" + id +
        ", commitId=" + commitId +
        ", productId=" + productId +
        ", userId=" + userId +
        ", commitTime=" + commitTime +
        "}";
    }
}
