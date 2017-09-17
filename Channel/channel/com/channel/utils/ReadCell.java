package com.channel.utils;

import com.channel.bean.TableCell;

/**
 * Created by 25019 on 2015/11/12.
 */
public class ReadCell extends TableCell {
    private int row;
    private int col;

    public ReadCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public ReadCell(String cellvalue, int row, int col) {
        super(cellvalue);
        this.row = row;
        this.col = col;
    }

    public ReadCell(String cellvalue, int rspan, int cspan, int row, int col) {
        super(cellvalue, rspan, cspan);
        this.row = row;
        this.col = col;
    }

    public ReadCell(String cellvalue, String cssclass, String cssstyle, int row, int col) {
        super(cellvalue, cssclass, cssstyle);
        this.row = row;
        this.col = col;
    }

    public ReadCell(String cellvalue, int rspan, int cspan, String cssclass, String cssstyle, int row, int col) {
        super(cellvalue, rspan, cspan, cssclass, cssstyle);
        this.row = row;
        this.col = col;
    }

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
}
