package com.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
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
@TableName("product_character")
public class ProductCharacter extends Model<ProductCharacter> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品特性ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 自己产品的ID
     */
    @TableField("product_id")
    private Integer productId;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String ico;
    /**
     * 类型:img,text,pdf
     */
    private String type;
    /**
     * 内容:img/pdf为逗号分隔储存路径,text就直接是文本了
     */
    private String content;
    /**
     * APP展示的顺序
     */
    private Integer sort;
    /**
     * 更新时间
     */
    @TableField("commit_time")
    private Date commitTime;


    private String creater;
    /**
     * 图片id
     */

    @TableField("base_id")
    private String baseId;
    @TableField("create_time")
    private Date createTime;


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

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        return "ProductCharacter{" +
                "id=" + id +
                ", productId=" + productId +
                ", title=" + title +
                ", ico=" + ico +
                ", type=" + type +
                ", content=" + content +
                ", sort=" + sort +
                ", sort=" + createTime +
                "}";
    }
}
