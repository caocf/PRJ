package com.channel.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class GcdBean {
    private String id;
    private String tag;
    private String name;

    public GcdBean() {
    }

    public GcdBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GcdBean(String id, String tag, String name) {
        this.id = id;
        this.tag = tag;
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
