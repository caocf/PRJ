package com.channel.model.hz;

import java.util.Date;

/**
 * CHzAdminpenalty entity. @author MyEclipse Persistence Tools
 */

public class CHzAdminpenalty implements java.io.Serializable {

    // Fields

    private Integer id;
    private String slh;//受理号
    private String slsj;//受理时间
    private String zwcm;//中文船名
    private String ay;//案由
    private String fasj;//发案时间
    private String fadd;//发案地点
    private String ajlb;//案件类别
    private String zyss;//主要事实
    private String zfscbh;//执法手册编号
    private String wfnr;//违法内容
    private String wftk;//违法条款
    private String cftk;//处罚条款
    private String cfyj;//处罚意见
    private String cflb;//处罚类别
    private Integer sfcf;//是否处罚
    private Integer sfja;//是否结案
    private String jarq;//结案日期
    private Date jrrq;//接入日期
    private Date gxrq;//更新日期

    // Constructors

    /**
     * default constructor
     */
    public CHzAdminpenalty() {
    }

    /**
     * minimal constructor
     */
    public CHzAdminpenalty(String slh, String slsj, String zwcm) {
        this.slh = slh;
        this.slsj = slsj;
        this.zwcm = zwcm;
    }

    /**
     * full constructor
     */
    public CHzAdminpenalty(String slh, String slsj, String zwcm, String ay,
                           String fasj, String fadd, String ajlb, String zyss, String zfscbh,
                           String wfnr, String wftk, String cftk, String cfyj, String cflb,
                           Integer sfcf, Integer sfja, String jarq, Date jrrq,
                           Date gxrq) {
        this.slh = slh;
        this.slsj = slsj;
        this.zwcm = zwcm;
        this.ay = ay;
        this.fasj = fasj;
        this.fadd = fadd;
        this.ajlb = ajlb;
        this.zyss = zyss;
        this.zfscbh = zfscbh;
        this.wfnr = wfnr;
        this.wftk = wftk;
        this.cftk = cftk;
        this.cfyj = cfyj;
        this.cflb = cflb;
        this.sfcf = sfcf;
        this.sfja = sfja;
        this.jarq = jarq;
        this.jrrq = jrrq;
        this.gxrq = gxrq;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlh() {
        return this.slh;
    }

    public void setSlh(String slh) {
        this.slh = slh;
    }

    public String getSlsj() {
        return this.slsj;
    }

    public void setSlsj(String slsj) {
        this.slsj = slsj;
    }

    public String getZwcm() {
        return this.zwcm;
    }

    public void setZwcm(String zwcm) {
        this.zwcm = zwcm;
    }

    public String getAy() {
        return this.ay;
    }

    public void setAy(String ay) {
        this.ay = ay;
    }

    public String getFasj() {
        return this.fasj;
    }

    public void setFasj(String fasj) {
        this.fasj = fasj;
    }

    public String getFadd() {
        return this.fadd;
    }

    public void setFadd(String fadd) {
        this.fadd = fadd;
    }

    public String getAjlb() {
        return this.ajlb;
    }

    public void setAjlb(String ajlb) {
        this.ajlb = ajlb;
    }

    public String getZyss() {
        return this.zyss;
    }

    public void setZyss(String zyss) {
        this.zyss = zyss;
    }

    public String getZfscbh() {
        return this.zfscbh;
    }

    public void setZfscbh(String zfscbh) {
        this.zfscbh = zfscbh;
    }

    public String getWfnr() {
        return this.wfnr;
    }

    public void setWfnr(String wfnr) {
        this.wfnr = wfnr;
    }

    public String getWftk() {
        return this.wftk;
    }

    public void setWftk(String wftk) {
        this.wftk = wftk;
    }

    public String getCftk() {
        return this.cftk;
    }

    public void setCftk(String cftk) {
        this.cftk = cftk;
    }

    public String getCfyj() {
        return this.cfyj;
    }

    public void setCfyj(String cfyj) {
        this.cfyj = cfyj;
    }

    public String getCflb() {
        return this.cflb;
    }

    public void setCflb(String cflb) {
        this.cflb = cflb;
    }

    public Integer getSfcf() {
        return this.sfcf;
    }

    public void setSfcf(Integer sfcf) {
        this.sfcf = sfcf;
    }

    public Integer getSfja() {
        return this.sfja;
    }

    public void setSfja(Integer sfja) {
        this.sfja = sfja;
    }

    public String getJarq() {
        return this.jarq;
    }

    public void setJarq(String jarq) {
        this.jarq = jarq;
    }

    public Date getJrrq() {
        return this.jrrq;
    }

    public void setJrrq(Date jrrq) {
        this.jrrq = jrrq;
    }

    public Date getGxrq() {
        return this.gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

}