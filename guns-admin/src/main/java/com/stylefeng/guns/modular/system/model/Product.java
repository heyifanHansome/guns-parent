package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-13
 */
@TableName("sys_product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * 自己产品的ID
     */
    @TableId(value = "id")
    private Integer id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 商品图片
     */
    private String picture;
    /**
     * 商品分类ID
     */
    @TableField("category_id")
    private String categoryId;
    /**
     * 热销:hot,推荐:commend(后期有再添加)
     */
    private String type;
    /**
     * 更新时间
     */
    @TableField("commit_time")
    private Date commitTime;

    private String creater;

    @TableField("create_time")
    private Date createTime;
    /**
     * baseid
     */
    @TableField("base_id")
    private String baseId;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", picture=" + picture +
                ", categoryId=" + categoryId +
                ", type=" + type +
                ", commitTime=" + commitTime +
                ", commitTime=" + createTime +
                ",creator=" + creater +
                "}";
    }
}
