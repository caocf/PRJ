package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdJgspgcd entity. @author MyEclipse Persistence Tools
 */

public class CHdJgllgcd implements java.io.Serializable {

    // Fields
    @PropDesc("激光流量观测点")
    private String jgllgcdname;

    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6,max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user",fn = "appuniquecheck",msg = "编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 1, desc = "编号", must = true)
    @PropDesc("编号")
    private String bh;

    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 2, desc = "名称", must = true)
    @PropDesc("名称")
    private String mc;


    @PropDesc("附属物类别")
    private Integer fswlx;


    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = true, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;


    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = true, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 1, order = 3, desc = "船舶流量观测设备名称", must = false)
    @PropDesc("船舶流量观测设备名称")
    private String cbllgcsbmc;

    @UiInput(group = 1, order = 4, desc = "船舶流量观测设备型号", must = false)
    @PropDesc("船舶流量观测设备型号")
    private String cbllgcsbxh;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 1, order = 5, desc = "摄像设备名称", must = false)
    @PropDesc("摄像设备名称")
    private String sxsbmc;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 1, order = 6, desc = "摄像设备型号", must = false)
    @PropDesc("摄像设备型号")
    private String sxsbxh;

    @UiInput(group = 1, order = 7, desc = "摄像设备安装岸别", must = false)
    @PropDesc("摄像设备安装岸别")
    private String sxsbazab;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 1, order = 8, desc = "摄像设备安装岸别", must = false)
    @PropDesc("设备维护单位名称")
    private String sbwhdwmc;

    @UiInput(group = 1, order = 9, desc = "设备维护单位联系人", must = false)
    @PropDesc("设备维护单位联系人")
    private String sbwhdwlxr;

    @UiInputValidator({@Validator(filter = "phone")})
    @UiInput(group = 1, order = 10, desc = "设备维护单位联系电话", must = false)
    @PropDesc("设备维护单位联系电话")
    private String sbwhdwlxdh;

    @UiInput(group = 2, order = 3, desc = "河流代码", must = false)
    @PropDesc("河流代码")
    private String hldm;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 2, order = 4, desc = "河流名称", must = false)
    @PropDesc("河流名称")
    private String hlmc;


    @UiInput(group = 2, order = 5, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 6, desc = "所在里程桩号", must = false)
    @PropDesc("所在里程桩号")
    private String szlczh;
    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order =7 , desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 8, desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 2, order = 9, desc = "所在详细地址", must = false)
    @PropDesc("所在详细地址")
    private String szxxdz;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(groupname = "辅助信息", group = 3, order = 1, desc = "管养单位名称", must = false)
    @PropDesc("管养单位名称")
    private String gljgmc;

    @UiInput(group = 3, order = 3, desc = "管养单位联系人", must = false)
    @PropDesc("管养单位联系人")
    private String gljglxr;

    @UiInputValidator({@Validator(filter = "phone")})
    @UiInput(group = 3, order = 4, desc = "管养单位联系电话", must = false)
    @PropDesc("管养单位联系电话")
    private String gljglxdh;

    @UiInput(group = 3, order = 2, desc = "管养单位所在行政区划", must = false, inputtype = "selectxzqh")
    @PropDesc("管养单位所在行政区划")
    private Integer gljgszxzqh;

    @UiInput(group = 3, order = 5, desc = "备注", must = false, inputtype = "textarea", oneline = true)
    @PropDesc("备注")
    private String bz;
    @PropDesc("创建人")
    private Integer creater;
    @PropDesc("创建时间")
    private Date createtime;
    @PropDesc("更新时间")
    private Date updatetime;

    // Constructors

    /**
     * default constructor
     */
    public CHdJgllgcd() {
    }


    public CHdJgllgcd(Integer id, String bh, String mc, String cbllgcsbmc,
                      String cbllgcsbxh, String sxsbmc, String sxsbxh, String sxsbazab,
                      String sbwhdwmc, String sbwhdwlxr, String sbwhdwlxdh,
                      Integer fswlx, Integer sshdaoid, Integer sshduanid, String hldm,
                      String hlmc, Integer szxzqh, String szlczh, Double jd,
                      Double wd, String szxxdz, String gljgmc, String gljglxr,
                      String gljglxdh, Integer gljgszxzqh, String bz, Integer creater,
                      Date createtime, Date updatetime) {
        super();
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.cbllgcsbmc = cbllgcsbmc;
        this.cbllgcsbxh = cbllgcsbxh;
        this.sxsbmc = sxsbmc;
        this.sxsbxh = sxsbxh;
        this.sxsbazab = sxsbazab;
        this.sbwhdwmc = sbwhdwmc;
        this.sbwhdwlxr = sbwhdwlxr;
        this.sbwhdwlxdh = sbwhdwlxdh;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.hldm = hldm;
        this.hlmc = hlmc;
        this.szxzqh = szxzqh;
        this.szlczh = szlczh;
        this.jd = jd;
        this.wd = wd;
        this.szxxdz = szxxdz;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }


    public CHdJgllgcd(String bh, String mc, String cbllgcsbmc,
                      String cbllgcsbxh, String sxsbmc, String sxsbxh, String sxsbazab,
                      String sbwhdwmc, String sbwhdwlxr, String sbwhdwlxdh,
                      Integer fswlx, Integer sshdaoid, Integer sshduanid, String hldm,
                      String hlmc, Integer szxzqh, String szlczh, Double jd,
                      Double wd, String szxxdz, String gljgmc, String gljglxr,
                      String gljglxdh, Integer gljgszxzqh, String bz, Integer creater,
                      Date createtime, Date updatetime) {
        super();
        this.bh = bh;
        this.mc = mc;
        this.cbllgcsbmc = cbllgcsbmc;
        this.cbllgcsbxh = cbllgcsbxh;
        this.sxsbmc = sxsbmc;
        this.sxsbxh = sxsbxh;
        this.sxsbazab = sxsbazab;
        this.sbwhdwmc = sbwhdwmc;
        this.sbwhdwlxr = sbwhdwlxr;
        this.sbwhdwlxdh = sbwhdwlxdh;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.hldm = hldm;
        this.hlmc = hlmc;
        this.szxzqh = szxzqh;
        this.szlczh = szlczh;
        this.jd = jd;
        this.wd = wd;
        this.szxxdz = szxxdz;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }


    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBh() {
        return this.bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getMc() {
        return this.mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getCbllgcsbmc() {
        return this.cbllgcsbmc;
    }

    public void setCbllgcsbmc(String cbllgcsbmc) {
        this.cbllgcsbmc = cbllgcsbmc;
    }

    public String getCbllgcsbxh() {
        return this.cbllgcsbxh;
    }

    public void setCbllgcsbxh(String cbllgcsbxh) {
        this.cbllgcsbxh = cbllgcsbxh;
    }

    public String getSxsbmc() {
        return this.sxsbmc;
    }

    public void setSxsbmc(String sxsbmc) {
        this.sxsbmc = sxsbmc;
    }

    public String getSxsbxh() {
        return this.sxsbxh;
    }

    public void setSxsbxh(String sxsbxh) {
        this.sxsbxh = sxsbxh;
    }

    public String getSxsbazab() {
        return this.sxsbazab;
    }

    public void setSxsbazab(String sxsbazab) {
        this.sxsbazab = sxsbazab;
    }

    public String getSbwhdwmc() {
        return this.sbwhdwmc;
    }

    public void setSbwhdwmc(String sbwhdwmc) {
        this.sbwhdwmc = sbwhdwmc;
    }

    public String getSbwhdwlxr() {
        return this.sbwhdwlxr;
    }

    public void setSbwhdwlxr(String sbwhdwlxr) {
        this.sbwhdwlxr = sbwhdwlxr;
    }

    public String getSbwhdwlxdh() {
        return this.sbwhdwlxdh;
    }

    public void setSbwhdwlxdh(String sbwhdwlxdh) {
        this.sbwhdwlxdh = sbwhdwlxdh;
    }

    public Integer getFswlx() {
        return this.fswlx;
    }

    public void setFswlx(Integer fswlx) {
        this.fswlx = fswlx;
    }

    public Integer getSshdaoid() {
        return sshdaoid;
    }

    public void setSshdaoid(Integer sshdaoid) {
        this.sshdaoid = sshdaoid;
    }

    public Integer getSshduanid() {
        return sshduanid;
    }

    public void setSshduanid(Integer sshduanid) {
        this.sshduanid = sshduanid;
    }

    public String getHldm() {
        return this.hldm;
    }

    public void setHldm(String hldm) {
        this.hldm = hldm;
    }

    public String getHlmc() {
        return this.hlmc;
    }

    public void setHlmc(String hlmc) {
        this.hlmc = hlmc;
    }

    public Integer getSzxzqh() {
        return this.szxzqh;
    }

    public void setSzxzqh(Integer szxzqh) {
        this.szxzqh = szxzqh;
    }

    public String getSzlczh() {
        return this.szlczh;
    }

    public void setSzlczh(String szlczh) {
        this.szlczh = szlczh;
    }

    public Double getJd() {
        return jd;
    }

    public void setJd(Double jd) {
        this.jd = jd;
    }

    public Double getWd() {
        return wd;
    }

    public void setWd(Double wd) {
        this.wd = wd;
    }




    public String getSzxxdz() {
        return szxxdz;
    }


    public void setSzxxdz(String szxxdz) {
        this.szxxdz = szxxdz;
    }


    public String getGljgmc() {
        return this.gljgmc;
    }

    public void setGljgmc(String gljgmc) {
        this.gljgmc = gljgmc;
    }

    public String getGljglxr() {
        return this.gljglxr;
    }

    public void setGljglxr(String gljglxr) {
        this.gljglxr = gljglxr;
    }

    public String getGljglxdh() {
        return this.gljglxdh;
    }

    public void setGljglxdh(String gljglxdh) {
        this.gljglxdh = gljglxdh;
    }

    public Integer getGljgszxzqh() {
        return this.gljgszxzqh;
    }

    public void setGljgszxzqh(Integer gljgszxzqh) {
        this.gljgszxzqh = gljgszxzqh;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getCreater() {
        return this.creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}