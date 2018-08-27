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
 * 产品标签
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@TableName("sys_product_tag")
public class ProductTag extends Model<ProductTag> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类标签ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类标签名称
     */
    private String name;
    /**
     * 提交时间
     */
    @TableField("commit_time")
    private Date commitTime;
    /**
     * 操作人
     */
    private String creater;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

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

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
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
        return "ProductTag{" +
                "id=" + id +
                ", name=" + name +
                ", commitTime=" + commitTime +
                ", createTime=" + createTime +
                ", creater=" + creater +
                "}";
    }
}
