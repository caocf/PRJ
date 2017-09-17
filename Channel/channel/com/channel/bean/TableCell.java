package com.channel.bean;

public class TableCell implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String cellvalue;
    private int rspan;
    private int cspan;

    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    /**
     * 页面样式属性
     */
    private String cssclass;//class类别
    private String cssstyle;//style样式

    public String getCellvalue() {
        return cellvalue;
    }

    public void setCellvalue(String cellvalue) {
        this.cellvalue = cellvalue;
    }

    public int getRspan() {
        return rspan;
    }

    public void setRspan(int rspan) {
        this.rspan = rspan;
    }

    public int getCspan() {
        return cspan;
    }

    public void setCspan(int cspan) {
        this.cspan = cspan;
    }

    public TableCell() {
        super();
    }

    public String getCssstyle() {
        return cssstyle;
    }

    public void setCssstyle(String cssstyle) {
        this.cssstyle = cssstyle;
    }

    public String getCssclass() {
        return cssclass;
    }

    public void setCssclass(String cssclass) {
        this.cssclass = cssclass;
    }

    public TableCell(String cellvalue) {
        this.cellvalue = cellvalue;
        this.rspan = 1;
        this.cspan = 1;
        this.cssclass = null;
        this.cssstyle = null;
    }

    public TableCell(String cellvalue, int rspan, int cspan, int row, int col) {
        this.cellvalue = cellvalue;
        this.rspan = rspan;
        this.cspan = cspan;
        this.row = row;
        this.col = col;
    }

    public TableCell(String cellvalue, int rspan, int cspan) {
        this.cellvalue = cellvalue;
        this.rspan = rspan;
        this.cspan = cspan;
        this.cssclass = null;
        this.cssstyle = null;
    }

    public TableCell(String cellvalue, String cssclass, String cssstyle) {
        this.cellvalue = cellvalue;
        this.rspan = 1;
        this.cspan = 1;
        this.cssclass = cssclass;
        this.cssstyle = cssstyle;
    }

    public TableCell(String cellvalue, int rspan, int cspan, String cssclass, String cssstyle) {
        this.cellvalue = cellvalue;
        this.rspan = rspan;
        this.cspan = cspan;
        this.cssclass = cssclass;
        this.cssstyle = cssstyle;
    }
}
