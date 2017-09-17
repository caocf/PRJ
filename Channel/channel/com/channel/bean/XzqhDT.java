package com.channel.bean;

import com.channel.model.user.CZdXzqhdm;

import java.util.Collections;

public class XzqhDT{
    private CZdXzqhdm xzqhdm;
    private int countchild;

    public CZdXzqhdm getXzqhdm() {
        return xzqhdm;
    }

    public void setXzqhdm(CZdXzqhdm xzqhdm) {
        this.xzqhdm = xzqhdm;
    }

    public XzqhDT() {
        super();
    }

    public XzqhDT(CZdXzqhdm xzqhdm, int countchild) {
        super();
        this.xzqhdm = xzqhdm;
        this.countchild = countchild;
    }

    public int getCountchild() {
        return countchild;
    }

    public void setCountchild(int countchild) {
        this.countchild = countchild;
    }

}
