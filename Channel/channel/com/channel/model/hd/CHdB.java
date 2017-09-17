package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdZzjzw entity. @author MyEclipse Persistence Tools
 */

public class CHdB implements java.io.Serializable {

    // Fields
    @PropDesc("大坝")
    private String bname;

    @UiInput(group = 1, order = 3, desc = "类型", must = false, inputtype = "selectdict", selectdictname = "blx")
    @PropDesc("类型")
    private Integer lx;

    @UiInput(group = 1, order = 4, desc = "结构", must = false)
    @PropDesc("结构")
    private String jg;

    @UiInput(group = 1, order = 5, desc = "技术状况等级", must = false, inputtype = "selectdict", selectdictname = "jsdj")
    @PropDesc("技术状况等级")
    private Integer jszkdj;

    @UiInput(group = 1, order = 6, desc = "技术状况等级评定时间", must = false, inputtype = "selectdate")
    @PropDesc("技术状况等级评定时间")
    private Date jszkdjpdsj;

    @UiInput(group = 1, order = 7, desc = "顶高程", must = false)
    @PropDesc("顶高程")
    private String dgc;


    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 8, desc = "长度(米)", must = false)
    @PropDesc("长度(米)")
    private Double cd;


    @UiInput(group = 2, order = 3, desc = "建成年份", must = false, inputtype = "selectyear")
    @PropDesc("建成年份")
    private String jcnf;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 4, desc = "设施起点里程桩号", must = false)
    @PropDesc("设施起点里程桩号")
    private String ssqdlczh;

    @UiInput(group = 2, order = 5, desc = "所属行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 2, order = 6, desc = "岸别", must = false,inputtype = "selectdict", selectdictname = "ab")
    @PropDesc("岸别")
    private Integer ab;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 7, desc = "起点经度", must = false)
    @PropDesc("起点经度")
    private Double qdzbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 8, desc = "起点纬度", must = false)
    @PropDesc("起点纬度")
    private Double qdzby;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 9, desc = "转折点经度", must = false)
    @PropDesc("转折点经度")
    private Double zzdzbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 10, desc = "转折点纬度", must = false)
    @PropDesc("转折点纬度")
    private Double zzdzby;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 11, desc = "终点经度", must = false)
    @PropDesc("终点经度")
    private Double zdzbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 12, desc = "终点纬度", must = false)
    @PropDesc("终点纬度")
    private Double zdzby;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 13, desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 14, desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;

    @UiInput(group = 2, order = 15, desc = "是否在分叉辅航段上", must = false, inputtype = "selectyesno")
    @PropDesc("是否在分叉辅航段上")
    private Integer sffcfhds;

    private Integer id;


    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6, max = 11, msg = "输入必须为11个数字"),
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
    public CHdB() {
    }


    public CHdB(Integer id, String bh, String mc, Integer lx, String jg,
                Integer jszkdj, Date jszkdjpdsj, String dgc, Double cd, String jcnf,
                Integer fswlx, Integer sshdaoid, Integer sshduanid, String ssqdlczh,
                Integer szxzqh, Integer ab, Double qdzbx, Double qdzby,
                Double zzdzbx, Double zzdzby, Double zdzbx, Double zdzby,
                Integer sffcfhds, String gljgmc, String gljglxr, String gljglxdh,
                Integer gljgszxzqh, String bz, Integer creater, Date createtime,
                Date updatetime) {
        super();
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.lx = lx;
        this.jg = jg;
        this.jszkdj = jszkdj;
        this.jszkdjpdsj = jszkdjpdsj;
        this.dgc = dgc;
        this.cd = cd;
        this.jcnf = jcnf;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.ssqdlczh = ssqdlczh;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.qdzbx = qdzbx;
        this.qdzby = qdzby;
        this.zzdzbx = zzdzbx;
        this.zzdzby = zzdzby;
        this.zdzbx = zdzbx;
        this.zdzby = zdzby;
        this.sffcfhds = sffcfhds;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }


    public CHdB(String bh, String mc, Integer lx, String jg, Integer jszkdj,
                Date jszkdjpdsj, String dgc, Double cd, String jcnf, Integer fswlx,
                Integer sshdaoid, Integer sshduanid, String ssqdlczh, Integer szxzqh,
                Integer ab, Double qdzbx, Double qdzby, Double zzdzbx,
                Double zzdzby, Double zdzbx, Double zdzby, Integer sffcfhds,
                String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh,
                String bz, Integer creater, Date createtime, Date updatetime) {
        super();
        this.bh = bh;
        this.mc = mc;
        this.lx = lx;
        this.jg = jg;
        this.jszkdj = jszkdj;
        this.jszkdjpdsj = jszkdjpdsj;
        this.dgc = dgc;
        this.cd = cd;
        this.jcnf = jcnf;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.ssqdlczh = ssqdlczh;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.qdzbx = qdzbx;
        this.qdzby = qdzby;
        this.zzdzbx = zzdzbx;
        this.zzdzby = zzdzby;
        this.zdzbx = zdzbx;
        this.zdzby = zdzby;
        this.sffcfhds = sffcfhds;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdB(Double qdzby, Integer lx, String jg, Integer jszkdj, Date jszkdjpdsj, String dgc, Double cd, String jcnf, String ssqdlczh, Integer szxzqh, Integer ab, Double qdzbx, Double zzdzbx, Double zzdzby, Double zdzbx, Double zdzby, Double jd, Double wd, Integer sffcfhds, String bh, String mc, Integer fswlx, Integer sshdaoid, Integer sshduanid, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {
        this.qdzby = qdzby;
        this.lx = lx;
        this.jg = jg;
        this.jszkdj = jszkdj;
        this.jszkdjpdsj = jszkdjpdsj;
        this.dgc = dgc;
        this.cd = cd;
        this.jcnf = jcnf;
        this.ssqdlczh = ssqdlczh;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.qdzbx = qdzbx;
        this.zzdzbx = zzdzbx;
        this.zzdzby = zzdzby;
        this.zdzbx = zdzbx;
        this.zdzby = zdzby;
        this.jd = jd;
        this.wd = wd;
        this.sffcfhds = sffcfhds;
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

    public CHdB(String dgc, Integer lx, String jg, Integer jszkdj, Date jszkdjpdsj, Double cd, String jcnf, String ssqdlczh, Integer szxzqh, Integer ab, Double qdzbx, Double qdzby, Double zzdzbx, Double zzdzby, Double zdzbx, Double zdzby, Double jd, Double wd, Integer sffcfhds, Integer id, String bh, String mc, Integer fswlx, Integer sshdaoid, Integer sshduanid, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {
        this.dgc = dgc;
        this.lx = lx;
        this.jg = jg;
        this.jszkdj = jszkdj;
        this.jszkdjpdsj = jszkdjpdsj;
        this.cd = cd;
        this.jcnf = jcnf;
        this.ssqdlczh = ssqdlczh;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.qdzbx = qdzbx;
        this.qdzby = qdzby;
        this.zzdzbx = zzdzbx;
        this.zzdzby = zzdzby;
        this.zdzbx = zdzbx;
        this.zdzby = zdzby;
        this.jd = jd;
        this.wd = wd;
        this.sffcfhds = sffcfhds;
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

    public String getJg() {
        return this.jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
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

    public Integer getAb() {
        return this.ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
    }

    public Double getQdzbx() {
        return this.qdzbx;
    }

    public void setQdzbx(Double qdzbx) {
        this.qdzbx = qdzbx;
    }

    public Double getQdzby() {
        return this.qdzby;
    }

    public void setQdzby(Double qdzby) {
        this.qdzby = qdzby;
    }

    public Double getZzdzbx() {
        return this.zzdzbx;
    }

    public void setZzdzbx(Double zzdzbx) {
        this.zzdzbx = zzdzbx;
    }

    public Double getZzdzby() {
        return this.zzdzby;
    }

    public void setZzdzby(Double zzdzby) {
        this.zzdzby = zzdzby;
    }

    public Double getZdzbx() {
        return this.zdzbx;
    }

    public void setZdzbx(Double zdzbx) {
        this.zdzbx = zdzbx;
    }

    public Double getZdzby() {
        return this.zdzby;
    }

    public void setZdzby(Double zdzby) {
        this.zdzby = zdzby;
    }

    public Integer getSffcfhds() {
        return this.sffcfhds;
    }

    public void setSffcfhds(Integer sffcfhds) {
        this.sffcfhds = sffcfhds;
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
}