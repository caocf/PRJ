package com.channel.bean;

import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/4.
 */
public class YhlxdBean {
    private Integer id;//巡查id
    private Integer hdid;//航道id
    private String hdmc;//航道名称
    private Integer ztqk;//总体情况
    private Date starttime;//开始时间
    private Date endtime;//结束时间
    private String qd;//起点
    private String zd;//终点
    private String qdzh;//起点桩号
    private String zdzh;//终点桩号
    private String xhth;//巡航艇号
    private String cyr;//参与人
    private Integer dept;//参与人
    private String bgbm;//报告部门
    private Integer qk;//情况分类
    private String qksm;//情况分类说明
    private Integer wt;//问题分类
    private String wtsm;//问题分类说明
    private String clyj;//处理意见
    private String cljg;//处理结果
    private String jtwz;//具体位置
    private Integer ab;//岸别
    private String ms;//描述


    public YhlxdBean() {

    }

    public YhlxdBean(String jtwz, Integer id, Integer hdid, String hdmc, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, String bgbm, Integer qk, String qksm, Integer wt, String wtsm, String clyj, String cljg, Integer ab, String ms) {
        this.jtwz = jtwz;
        this.id = id;
        this.hdid = hdid;
        this.hdmc = hdmc;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.bgbm = bgbm;
        this.qk = qk;
        this.qksm = qksm;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.ab = ab;
        this.ms = ms;
    }

    public YhlxdBean(String bgbm, Integer hdid, String hdmc, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer qk, String qksm, Integer wt, String wtsm, String clyj, String cljg, String jtwz, Integer ab, String ms) {

        this.bgbm = bgbm;
        this.hdid = hdid;
        this.hdmc = hdmc;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.qk = qk;
        this.qksm = qksm;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.jtwz = jtwz;
        this.ab = ab;
        this.ms = ms;
    }

    public YhlxdBean(Integer id, Integer hdid, String hdmc, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer dept, String bgbm, Integer qk, String qksm, Integer wt, String wtsm, String clyj, String cljg, String jtwz, Integer ab, String ms) {
        this.id = id;
        this.hdid = hdid;
        this.hdmc = hdmc;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.dept = dept;
        this.bgbm = bgbm;
        this.qk = qk;
        this.qksm = qksm;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.jtwz = jtwz;
        this.ab = ab;
        this.ms = ms;
    }

    public YhlxdBean(Integer hdid, String hdmc, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String cyr, Integer dept, String bgbm, Integer qk, String qksm, Integer wt, String wtsm, String clyj, String cljg, String jtwz, Integer ab, String ms) {
        this.hdid = hdid;
        this.hdmc = hdmc;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.cyr = cyr;
        this.dept = dept;
        this.bgbm = bgbm;
        this.qk = qk;
        this.qksm = qksm;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.jtwz = jtwz;
        this.ab = ab;
        this.ms = ms;
    }

    public YhlxdBean(Integer id, Integer hdid, String hdmc, Integer ztqk, Date starttime, Date endtime, String qd, String zd, String qdzh, String zdzh, String xhth, String cyr, Integer dept, String bgbm, Integer qk, String qksm, Integer wt, String wtsm, String clyj, String cljg, String jtwz, Integer ab, String ms) {
        this.id = id;
        this.hdid = hdid;
        this.hdmc = hdmc;
        this.ztqk = ztqk;
        this.starttime = starttime;
        this.endtime = endtime;
        this.qd = qd;
        this.zd = zd;
        this.qdzh = qdzh;
        this.zdzh = zdzh;
        this.xhth = xhth;
        this.cyr = cyr;
        this.dept = dept;
        this.bgbm = bgbm;
        this.qk = qk;
        this.qksm = qksm;
        this.wt = wt;
        this.wtsm = wtsm;
        this.clyj = clyj;
        this.cljg = cljg;
        this.jtwz = jtwz;
        this.ab = ab;
        this.ms = ms;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHdid() {
        return hdid;
    }

    public void setHdid(Integer hdid) {
        this.hdid = hdid;
    }

    public Integer getZtqk() {
        return ztqk;
    }

    public void setZtqk(Integer ztqk) {
        this.ztqk = ztqk;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @JSON(format = "yyyy-MM-dd HH:mm:ss")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getQd() {
        return qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public String getZd() {
        return zd;
    }

    public void setZd(String zd) {
        this.zd = zd;
    }

    public String getQdzh() {
        return qdzh;
    }

    public void setQdzh(String qdzh) {
        this.qdzh = qdzh;
    }

    public String getZdzh() {
        return zdzh;
    }

    public void setZdzh(String zdzh) {
        this.zdzh = zdzh;
    }

    public String getCyr() {
        return cyr;
    }

    public void setCyr(String cyr) {
        this.cyr = cyr;
    }

    public String getBgbm() {
        return bgbm;
    }

    public void setBgbm(String bgbm) {
        this.bgbm = bgbm;
    }

    public Integer getQk() {
        return qk;
    }

    public void setQk(Integer qk) {
        this.qk = qk;
    }

    public String getQksm() {
        return qksm;
    }

    public void setQksm(String qksm) {
        this.qksm = qksm;
    }

    public Integer getWt() {
        return wt;
    }

    public void setWt(Integer wt) {
        this.wt = wt;
    }

    public String getWtsm() {
        return wtsm;
    }

    public void setWtsm(String wtsm) {
        this.wtsm = wtsm;
    }

    public String getClyj() {
        return clyj;
    }

    public void setClyj(String clyj) {
        this.clyj = clyj;
    }

    public String getCljg() {
        return cljg;
    }

    public void setCljg(String cljg) {
        this.cljg = cljg;
    }

    public String getJtwz() {
        return jtwz;
    }

    public void setJtwz(String jtwz) {
        this.jtwz = jtwz;
    }

    public Integer getAb() {
        return ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getXhth() {
        return xhth;
    }

    public void setXhth(String xhth) {
        this.xhth = xhth;
    }
}
