package com.channel.model.hd;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;
import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdSwz entity. @author MyEclipse Persistence Tools
 */

public class CHdSwz implements java.io.Serializable {

    // Fields


    @PropDesc("水文站")
    private String swzname;

    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6, max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user", fn = "appuniquecheck", msg = "编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 2, desc = "编号", must = true)
    @PropDesc("编号")
    private String bh;

    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 3, desc = "名称", must = true)
    @PropDesc("名称")
    private String mc;

    @UiInput(group = 1, order = 4, desc = "是否碍航", must = false, inputtype = "selectyesno")
    @PropDesc("是否碍航")
    private Integer sfah;


    @PropDesc("附属物类别")
    private Integer fswlx;

    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = true, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;

    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = true, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 3, desc = "起点里程桩号", must = false)
    @PropDesc("起点里程桩号")
    private String ssqdlczh;

    @UiInput(group = 2, order = 4, desc = "占用岸线", must = false)
    @PropDesc("占用岸线")
    private Double zyax;

    @UiInput(group = 2, order = 5, desc = "占用岸线长度(米)", must = false)
    @PropDesc("占用岸线长度(米)")
    private Double zyaxcd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 6, desc = "占用岸线起点经度", must = false)
    @PropDesc("占用岸线起点经度")
    private Double zyaxqdzbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 7, desc = "占用岸线起点纬度", must = false)
    @PropDesc("占用岸线起点纬度")
    private Double zyaxqdzby;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 8, desc = "占用岸线终点经度", must = false)
    @PropDesc("占用岸线终点经度")
    private Double zyaxzdzbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 9, desc = "占用岸线终点纬度", must = false)
    @PropDesc("占用岸线终点纬度")
    private Double zyaxzdzby;
    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 10, desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 11, desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;
    @UiInput(group = 2, order = 12, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 2, order = 13, desc = "岸别", must = false, inputtype = "selectdict", selectdictname = "ab")
    @PropDesc("岸别")
    private Integer ab;

    @UiInput(group = 2, order = 14, desc = "是否在分叉辅助航段", must = false, inputtype = "selectyesno")
    @PropDesc("是否在分叉辅助航段")
    private Integer sffcfhds;

    @UiInput(group = 2, order = 15, desc = "建成年份", must = false, inputtype = "selectyear")
    @PropDesc("建成年份")
    private String jcnf;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getSfah() {
        return sfah;
    }

    public void setSfah(Integer sfah) {
        this.sfah = sfah;
    }

    public Integer getFswlx() {
        return fswlx;
    }

    public void setFswlx(Integer fswlx) {
        this.fswlx = fswlx;
    }

    public Integer getSshduanid() {
        return sshduanid;
    }

    public void setSshduanid(Integer sshduanid) {
        this.sshduanid = sshduanid;
    }

    public Integer getSshdaoid() {
        return sshdaoid;
    }

    public void setSshdaoid(Integer sshdaoid) {
        this.sshdaoid = sshdaoid;
    }

    public String getSsqdlczh() {
        return ssqdlczh;
    }

    public void setSsqdlczh(String ssqdlczh) {
        this.ssqdlczh = ssqdlczh;
    }

    public Double getZyax() {
        return zyax;
    }

    public void setZyax(Double zyax) {
        this.zyax = zyax;
    }

    public Double getZyaxcd() {
        return zyaxcd;
    }

    public void setZyaxcd(Double zyaxcd) {
        this.zyaxcd = zyaxcd;
    }

    public Double getZyaxqdzbx() {
        return zyaxqdzbx;
    }

    public void setZyaxqdzbx(Double zyaxqdzbx) {
        this.zyaxqdzbx = zyaxqdzbx;
    }

    public Double getZyaxqdzby() {
        return zyaxqdzby;
    }

    public void setZyaxqdzby(Double zyaxqdzby) {
        this.zyaxqdzby = zyaxqdzby;
    }

    public Double getZyaxzdzbx() {
        return zyaxzdzbx;
    }

    public void setZyaxzdzbx(Double zyaxzdzbx) {
        this.zyaxzdzbx = zyaxzdzbx;
    }

    public Double getZyaxzdzby() {
        return zyaxzdzby;
    }

    public void setZyaxzdzby(Double zyaxzdzby) {
        this.zyaxzdzby = zyaxzdzby;
    }

    public Integer getSzxzqh() {
        return szxzqh;
    }

    public void setSzxzqh(Integer szxzqh) {
        this.szxzqh = szxzqh;
    }

    public Integer getAb() {
        return ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public Integer getSffcfhds() {
        return sffcfhds;
    }

    public void setSffcfhds(Integer sffcfhds) {
        this.sffcfhds = sffcfhds;
    }

    public String getJcnf() {
        return jcnf;
    }

    public void setJcnf(String jcnf) {
        this.jcnf = jcnf;
    }

    public String getGljgmc() {
        return gljgmc;
    }

    public void setGljgmc(String gljgmc) {
        this.gljgmc = gljgmc;
    }

    public String getGljglxr() {
        return gljglxr;
    }

    public void setGljglxr(String gljglxr) {
        this.gljglxr = gljglxr;
    }

    public String getGljglxdh() {
        return gljglxdh;
    }

    public void setGljglxdh(String gljglxdh) {
        this.gljglxdh = gljglxdh;
    }

    public Integer getGljgszxzqh() {
        return gljgszxzqh;
    }

    public void setGljgszxzqh(Integer gljgszxzqh) {
        this.gljgszxzqh = gljgszxzqh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
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

    public CHdSwz() {
        super();
    }

    public CHdSwz(Double jd, Integer id, String bh, String mc, Integer sfah, Integer fswlx, Integer sshdaoid, Integer sshduanid, String ssqdlczh, Double zyax, Double zyaxcd, Double zyaxqdzbx, Double zyaxqdzby, Double zyaxzdzbx, Double zyaxzdzby, Double wd, Integer szxzqh, Integer ab, Integer sffcfhds, String jcnf, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {
        this.jd = jd;
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.sfah = sfah;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.ssqdlczh = ssqdlczh;
        this.zyax = zyax;
        this.zyaxcd = zyaxcd;
        this.zyaxqdzbx = zyaxqdzbx;
        this.zyaxqdzby = zyaxqdzby;
        this.zyaxzdzbx = zyaxzdzbx;
        this.zyaxzdzby = zyaxzdzby;
        this.wd = wd;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.sffcfhds = sffcfhds;
        this.jcnf = jcnf;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdSwz(Integer sfah, String bh, String mc, Integer fswlx, Integer sshdaoid, Integer sshduanid, String ssqdlczh, Double zyax, Double zyaxcd, Double zyaxqdzbx, Double zyaxqdzby, Double zyaxzdzbx, Double zyaxzdzby, Double jd, Double wd, Integer szxzqh, Integer ab, Integer sffcfhds, String jcnf, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {

        this.sfah = sfah;
        this.bh = bh;
        this.mc = mc;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.ssqdlczh = ssqdlczh;
        this.zyax = zyax;
        this.zyaxcd = zyaxcd;
        this.zyaxqdzbx = zyaxqdzbx;
        this.zyaxqdzby = zyaxqdzby;
        this.zyaxzdzbx = zyaxzdzbx;
        this.zyaxzdzby = zyaxzdzby;
        this.jd = jd;
        this.wd = wd;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.sffcfhds = sffcfhds;
        this.jcnf = jcnf;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
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
}