package com.wlan.familymembers.bean;

import android.content.Intent;

import java.io.Serializable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/29.
 */

public class HelpCenterDetailsBean implements Serializable {

    private static final long serialVersionUID = 1;

    public HelpCenterDetailsBean() {
    }

    @Override
    public String toString() {
        return "HelpCenterDetailsBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sort=" + sort +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    private String id;

    private String title;

    private String content;

    private Integer sort;

}
