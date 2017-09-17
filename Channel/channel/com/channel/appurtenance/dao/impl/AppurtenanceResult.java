package com.channel.appurtenance.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.common.action.result.BaseResultOK;

public class AppurtenanceResult extends BaseResultOK {

    private Object modelobj; // 详情实体结果，用于编辑时初始信息

    public AppurtenanceResult(Object modelobj) {
        super();
        setObj(new ArrayList<List<Object>>());
        setModelobj(modelobj);
    }

    public AppurtenanceResult() {
        super();
        setObj(new ArrayList<List<Object>>());
    }

    public AppurtenanceResult addGroup(AppurtenanceGroup group) {
        List<Object> items = group.getItems();

        this.AddLine(group.getGroupnode());

        List<Object> line = new ArrayList<>();
        for (Object item : items) {
            if (item instanceof AppurtenanceGroup.NewLine == false) {
                if (line.size() >= 4) {
                    this.AddLineList(line);
                    line = new ArrayList<>();
                    line.add(item);
                } else {
                    line.add(item);
                }
            } else {
                if (line.size() > 0)
                    this.AddLineList(line);
                line = new ArrayList<>();
            }
            if (line.size() >= 4) {
                this.AddLineList(line);
                line = new ArrayList<>();
            }
        }
        if (line.size() > 0)
            this.AddLineList(line);
        return this;
    }

    public AppurtenanceResult AddLine(Object... obj) {
        @SuppressWarnings("unchecked")
        List<List<Object>> list = (List<List<Object>>) getObj();
        List<Object> listObj = new ArrayList<Object>();
        for (Object o : obj) {
            listObj.add(o);
        }
        list.add(listObj);

        return this;
    }

    public AppurtenanceResult AddLineList(List<Object> obj) {
        @SuppressWarnings("unchecked")
        List<List<Object>> list = (List<List<Object>>) getObj();
        List<Object> listObj = new ArrayList<Object>();
        for (Object o : obj) {
            listObj.add(o);
        }
        list.add(listObj);

        return this;
    }

    public Object getModelobj() {
        return modelobj;
    }

    public void setModelobj(Object modelobj) {
        this.modelobj = modelobj;
    }

}
