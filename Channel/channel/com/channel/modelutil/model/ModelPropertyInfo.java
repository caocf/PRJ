package com.channel.modelutil.model;

import java.util.List;

public class ModelPropertyInfo {
    private String name; // 属性名
    private String type; // 属性类型
    private String desc; // 属性描述

    private String defaultval; // 默认值
    private String defaultvalpre;//默认值前缀
    /**********
     * UI相关
     ***********/

    // 如果edithidden为true，则前台自动保存该值，在编辑提交时将该值做为参数之一进行提交
    private boolean edithidden;

    // 是否可编辑
    private boolean editable;

    // 组
    private int group;
    // 组内排序
    private int order;

    // 用于自定义排序
    private int suborder;

    // 组名
    private String groupname;

    // 对应UI是input输入还是select等
    private String inputtype;

    // 输入是否为只读
    private boolean readonly;

    // 是否一行显示，如备注等需要一行显示
    private boolean oneline;

    // 当inputtype为selectdict时设置有效
    private String selectdictname;

    // 是否为必须字段，如为必须，则会添加*表示必须输入
    private boolean must;

    private List<String> validatorjsons;

    private boolean autoajax;

    // 表单提交时提交名
    private String autoajax_name;

    // 表单提交时提交属性或VALUE
    private String autoajax_attr;

    // 自动提交表单时，如果用户未输入，则以此属性进行提交
    private String autoajax_defaultval;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    public String getAutoajax_name() {
        return autoajax_name;
    }

    public void setAutoajax_name(String autoajax_name) {
        this.autoajax_name = autoajax_name;
    }

    public String getAutoajax_attr() {
        return autoajax_attr;
    }

    public void setAutoajax_attr(String autoajax_attr) {
        this.autoajax_attr = autoajax_attr;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public boolean isAutoajax() {
        return autoajax;
    }

    public void setAutoajax(boolean autoajax) {
        this.autoajax = autoajax;
    }

    public String getSelectdictname() {
        return selectdictname;
    }

    public void setSelectdictname(String selectdictname) {
        this.selectdictname = selectdictname;
    }

    public int getSuborder() {
        return suborder;
    }

    public void setSuborder(int suborder) {
        this.suborder = suborder;
    }

    public List<String> getValidatorjsons() {
        return validatorjsons;
    }

    public void setValidatorjsons(List<String> validatorjsons) {
        this.validatorjsons = validatorjsons;
    }

    public boolean isOneline() {
        return oneline;
    }

    public void setOneline(boolean oneline) {
        this.oneline = oneline;
    }

    public String getAutoajax_defaultval() {
        return autoajax_defaultval;
    }

    public void setAutoajax_defaultval(String autoajax_defaultval) {
        this.autoajax_defaultval = autoajax_defaultval;
    }

    public String getDefaultval() {
        return defaultval;
    }

    public void setDefaultval(String defaultval) {
        this.defaultval = defaultval;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isEdithidden() {
        return edithidden;
    }

    public void setEdithidden(boolean edithidden) {
        this.edithidden = edithidden;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getDefaultvalpre() {
        return defaultvalpre;
    }

    public void setDefaultvalpre(String defaultvalpre) {
        this.defaultvalpre = defaultvalpre;
    }
}
