package com.channel.model.hd;

import java.util.Date;

public class CZdAppattribute {

    // Fields

    private Integer id;
    private Integer type;
    private String name;
    private String namedesc;
    private String attrdesc;
    private Date createtime;
    private Date updatetime;
    private Integer valid;
    private Integer editable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamedesc() {
        return namedesc;
    }

    public void setNamedesc(String namedesc) {
        this.namedesc = namedesc;
    }

    public String getAttrdesc() {
        return attrdesc;
    }

    public void setAttrdesc(String attrdesc) {
        this.attrdesc = attrdesc;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public CZdAppattribute() {
        super();
    }

    public CZdAppattribute(Integer type, String name, String namedesc,
                           String attrdesc, Date createtime, Date updatetime, Integer valid) {
        super();
        this.type = type;
        this.name = name;
        this.namedesc = namedesc;
        this.attrdesc = attrdesc;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.valid = valid;
    }

    public CZdAppattribute(Integer id, Integer type, String name,
                           String namedesc, String attrdesc, Date createtime, Date updatetime,
                           Integer valid) {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
        this.namedesc = namedesc;
        this.attrdesc = attrdesc;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.valid = valid;
    }

    public CZdAppattribute(Integer type, String name, String namedesc, String attrdesc, Date createtime, Date updatetime, Integer valid, Integer editable) {
        this.type = type;
        this.name = name;
        this.namedesc = namedesc;
        this.attrdesc = attrdesc;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.valid = valid;
        this.editable = editable;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }
}