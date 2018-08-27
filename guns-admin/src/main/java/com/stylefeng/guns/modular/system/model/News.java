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
 * 新闻
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-17
 */
@TableName("sys_news")
public class News extends Model<News> {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 主标题
     */
    private String title;
    /**
     * 标题头
     */
    private String head;
    /**
     * 副标题
     */
    private String subtitle;
    /**
     * 文本类容
     */
    private String content;
    /**
     * baseid对应picture的base_id
     */
    @TableField("base_id")
    private String baseId;
    @TableField("commit_time")
    private Date commitTime;

    private String creator;

    @TableField("update_time")
    private Date updateTime;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
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
        return "News{" +
                "id=" + id +
                ", title=" + title +
                ", head=" + head +
                ", subtitle=" + subtitle +
                ", content=" + content +
                ", baseId=" + baseId +
                ", commitTime=" + commitTime +
                ", updateTime=" + updateTime +
                ", creator=" + creator +
                "}";
    }
}
