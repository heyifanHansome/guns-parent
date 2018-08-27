package com.stylefeng.guns.modular.system.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 地区标签
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-16
 */
@TableName("sys_area_tag")
public class AreaTag extends Model<AreaTag> {

    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    private Integer id;
    /**
     * 名字
     */
    private String name;
    @TableField("parent_area_tag_id")
    private Integer parentAreaTagId;
    private Date addtime;


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

    public Integer getParentAreaTagId() {
        return parentAreaTagId;
    }

    public void setParentAreaTagId(Integer parentAreaTagId) {
        this.parentAreaTagId = parentAreaTagId;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AreaTag{" +
                "id=" + id +
                ", name=" + name +
                ", parentAreaTagId=" + parentAreaTagId +
                ", addtime=" + addtime +
                "}";
    }
}
