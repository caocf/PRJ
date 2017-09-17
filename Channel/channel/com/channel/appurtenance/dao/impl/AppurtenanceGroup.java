package com.channel.appurtenance.dao.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25019 on 2015/10/16.
 */
public class AppurtenanceGroup {
    private Object groupnode;

    private List<Object> items;

    public AppurtenanceGroup(Object obj){
        setGroupnode(obj);
        setItems(new ArrayList<Object>());
    }

    public AppurtenanceGroup addItem(Object obj){
        getItems().add(obj);
        return this;
    }

    public AppurtenanceGroup addItems(Object ...obj){
        for (Object o : obj) {
            getItems().add(o);
        }
        return this;
    }

    public AppurtenanceGroup addNewLine(){
        getItems().add(new NewLine());
        return this;
    }

    public static NewLine  newLine(){
        return new AppurtenanceGroup("").new NewLine();
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public List<Object> getItems() {
        return items;
    }

    public Object getGroupnode() {
        return groupnode;
    }

    public void setGroupnode(Object groupnode) {
        this.groupnode = groupnode;
    }

    public class NewLine{

    }
}