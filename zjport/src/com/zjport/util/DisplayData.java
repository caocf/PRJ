package com.zjport.util;

import java.util.List;
import java.util.Map;

/**
 * Created by TWQ on 2016/11/30.
 */
public class DisplayData {
    private String title;

    private String link;

    private List<String> oneByOnename;

    private Map<String, Double> oneByOnedata;

    private List<String> oneByMorename;

    private Map<String, List<Double>> oneByMoredata;

    private List<String> yName;

    private Map<String, Object> position;

    private Map<String, String> color;

    private Map<String, String> textColor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getOneByOnename() {
        return oneByOnename;
    }

    public void setOneByOnename(List<String> oneByOnename) {
        this.oneByOnename = oneByOnename;
    }

    public Map<String, Double> getOneByOnedata() {
        return oneByOnedata;
    }

    public void setOneByOnedata(Map<String, Double> oneByOnedata) {
        this.oneByOnedata = oneByOnedata;
    }

    public List<String> getOneByMorename() {
        return oneByMorename;
    }

    public void setOneByMorename(List<String> oneByMorename) {
        this.oneByMorename = oneByMorename;
    }

    public Map<String, List<Double>> getOneByMoredata() {
        return oneByMoredata;
    }

    public void setOneByMoredata(Map<String, List<Double>> oneByMoredata) {
        this.oneByMoredata = oneByMoredata;
    }

    public List<String> getyName() {
        return yName;
    }

    public void setyName(List<String> yName) {
        this.yName = yName;
    }

    public Map<String, Object> getPosition() {
        return position;
    }

    public void setPosition(Map<String, Object> position) {
        this.position = position;
    }

    public Map<String, String> getColor() {
        return color;
    }

    public void setColor(Map<String, String> color) {
        this.color = color;
    }

    public Map<String, String> getTextColor() {
        return textColor;
    }

    public void setTextColor(Map<String, String> textColor) {
        this.textColor = textColor;
    }
}
