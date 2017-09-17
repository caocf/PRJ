package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdZzha entity. @author MyEclipse Persistence Tools
 */

public class CHdZzha implements java.io.Serializable {

    // Fields
    @PropDesc("整治护岸")
    private String zzhaname;

    @UiInput(group = 1, order = 3, desc = "护岸类型", must = false)
    @PropDesc("护岸类型")
    private Integer lx;

    @UiInput(group = 1, order = 4, desc = "技术状况等级", must = false, inputtype = "selectdict", selectdictname = "jsdj")
    @PropDesc("技术状况等级")
    private Integer jszkdj;

    @UiInput(group = 1, order = 5, desc = "技术状况等级评定时间", must = false, inputtype = "selectdate")
    @PropDesc("技术状况等级评定时间")
    private Date jszkdjpdsj;


    @UiInput(group = 1, order = 6, desc = "顶高程", must = false)
    @PropDesc("顶高程")
    private String dgc;

    @UiInput(group = 1, order = 7, desc = "长度(米)", must = false)
    @PropDesc("长度(米)")
    private Double cd;

    @UiInput(group = 1, order = 8, desc = "是否在分叉辅航段上", must = false, inputtype = "selectyesno")
    @PropDesc("是否在分叉辅航段上")
    private Integer sffcfhds;

    @UiInput(group = 2, order = 3, desc = "建成年份", must = false, inputtype = "selectyear")
    @PropDesc("建成年份")
    private String jcnf;

    @UiInput(group = 2, order = 4, desc = "岸别", must = false,inputtype = "selectdict", selectdictname = "ab")
    @PropDesc("岸别")
    private Integer ab;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 5, desc = "设施起点里程桩号", must = false)
    @PropDesc("设施起点里程桩号")
    private String ssqdlczh;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order =6 , desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 7, desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;

    @UiInput(group = 2, order = 8, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

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

    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = false, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;

    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = false, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

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
    public CHdZzha() {
    }

    public CHdZzha(Double wd, Integer lx, Integer jszkdj, Date jszkdjpdsj, String dgc, Double cd, Integer sffcfhds, String jcnf, Integer ab, String ssqdlczh, Double jd, Integer szxzqh, Integer id, String bh, String mc, Integer fswlx, Integer sshdaoid, Integer sshduanid, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {
        this.wd = wd;
        this.lx = lx;
        this.jszkdj = jszkdj;
        this.jszkdjpdsj = jszkdjpdsj;
        this.dgc = dgc;
        this.cd = cd;
        this.sffcfhds = sffcfhds;
        this.jcnf = jcnf;
        this.ab = ab;
        this.ssqdlczh = ssqdlczh;
        this.jd = jd;
        this.szxzqh = szxzqh;
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdZzha(Integer szxzqh, Integer lx, Integer jszkdj, Date jszkdjpdsj, String dgc, Double cd, Integer sffcfhds, String jcnf, Integer ab, String ssqdlczh, Double jd, Double wd, String bh, String mc, Integer fswlx, Integer sshdaoid, Integer sshduanid, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {

        this.szxzqh = szxzqh;
        this.lx = lx;
        this.jszkdj = jszkdj;
        this.jszkdjpdsj = jszkdjpdsj;
        this.dgc = dgc;
        this.cd = cd;
        this.sffcfhds = sffcfhds;
        this.jcnf = jcnf;
        this.ab = ab;
        this.ssqdlczh = ssqdlczh;
        this.jd = jd;
        this.wd = wd;
        this.bh = bh;
        this.mc = mc;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
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

    public Integer getAb() {
        return ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

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

    public Integer getLx() {
        return this.lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public Integer getJszkdj() {
        return this.jszkdj;
    }

    public void setJszkdj(Integer jszkdj) {
        this.jszkdj = jszkdj;
    }

    public Date getJszkdjpdsj() {
        return this.jszkdjpdsj;
    }

    public void setJszkdjpdsj(Date jszkdjpdsj) {
        this.jszkdjpdsj = jszkdjpdsj;
    }

    public String getDgc() {
        return this.dgc;
    }

    public void setDgc(String dgc) {
        this.dgc = dgc;
    }

    public Double getCd() {
        return this.cd;
    }

    public void setCd(Double cd) {
        this.cd = cd;
    }

    public Integer getSffcfhds() {
        return this.sffcfhds;
    }

    public void setSffcfhds(Integer sffcfhds) {
        this.sffcfhds = sffcfhds;
    }

    public String getJcnf() {
        return this.jcnf;
    }

    public void setJcnf(String jcnf) {
        this.jcnf = jcnf;
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

    public String getSsqdlczh() {
        return this.ssqdlczh;
    }

    public void setSsqdlczh(String ssqdlczh) {
        this.ssqdlczh = ssqdlczh;
    }

    public Integer getSzxzqh() {
        return this.szxzqh;
    }

    public void setSzxzqh(Integer szxzqh) {
        this.szxzqh = szxzqh;
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