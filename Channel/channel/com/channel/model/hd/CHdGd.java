package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdGd entity. @author MyEclipse Persistence Tools
 */

public class CHdGd implements java.io.Serializable {

    // Fields


    @PropDesc("管道")
    private String gdname;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 4, desc = "顶部设置深度(米)", must = false)
    @PropDesc("顶部设置深度(米)")
    private Double dbszsd;

    @UiInput(group = 1, order = 5, desc = "有无标志", must = false, inputtype = "selectyesno")
    @PropDesc("有无标志")
    private Integer ywbz;

    @UiInput(group = 1, order = 6, desc = "架设单位", must = false)
    @PropDesc("架设单位")
    private String jsdw;

    @UiInput(group = 1, order = 7, desc = "穿越形式", must = true)
    @PropDesc("穿越形式")
    private String cyxs;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 8, desc = "左岸(米)", must = false)
    @PropDesc("左岸(米)")
    private Double za;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 9, desc = "右岸(米)", must = false)
    @PropDesc("右岸(米)")
    private Double ya;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 10, desc = "左岸经度", must = false)
    @PropDesc("左岸经度")
    private Double zazbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 11, desc = "左岸纬度", must = false)
    @PropDesc("左岸纬度")
    private Double zazby;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 12, desc = "右岸经度", must = false)
    @PropDesc("右岸经度")
    private Double yazbx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 13, desc = "右岸纬度", must = false)
    @PropDesc("右岸纬度")
    private Double yazby;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 14, desc = "测时水位(米)", must = false)
    @PropDesc("测时水位(米)")
    private Double cssw;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 15, desc = "跨径(米)", must = false)
    @PropDesc("跨径(米)")
    private Double kj;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 16, desc = "安全间距", must = false)
    @PropDesc("安全间距")
    private Double aqjj;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 17, desc = "管线高程(米)", must = false)
    @PropDesc("管线高程(米)")
    private Double gxgc;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 18, desc = "埋设深度(米)", must = false)
    @PropDesc("埋设深度(米)")
    private Double mssd;


    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6, max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user",fn = "appuniquecheck",msg = "编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 2, desc = "编号", must = true)
    @PropDesc("编号")
    private String bh;

    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 3, desc = "名称", must = true)
    @PropDesc("名称")
    private String mc;

    @UiInput(group = 1, order = 19, desc = "建成年份", must = false, inputtype = "selectyear")
    @PropDesc("建成年份")
    private String jcnf;

    @UiInput(group = 1, order = 20, desc = "用途分类", must = false)
    @PropDesc("用途分类")
    private String ytfl;

    @UiInput(group = 1, order = 21, desc = "是否达标", must = false, inputtype = "selectyesno")
    @PropDesc("是否达标")
    private Integer sfdb;

    @UiInput(group = 1, order = 22, desc = "是否有防撞设施", must = false, inputtype = "selectyesno")
    @PropDesc("是否有防撞设施")
    private Integer ywfzss;


    @PropDesc("附属物类型")
    private Integer fswlx;


    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = true, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;

    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = true, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 2, order = 3, desc = "详细地址", must = true)
    @PropDesc("详细地址")
    private String xxdz;

    @UiInput(group = 2, order = 4, desc = "中心里程桩号", must = false)
    @PropDesc("中心里程桩号")
    private String zxlczh;


    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 5, desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order =6 , desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;

    @UiInput(group = 2, order = 7, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 2, order = 8, desc = "是否分叉辅航段上", must = false, inputtype = "selectyesno")
    @PropDesc("是否分叉辅航段上")
    private Integer sffcfhds;


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
    public CHdGd() {
    }

    public CHdGd(Double dbszsd, Integer ywbz, String jsdw, String cyxs,
                 Double za, Double ya, Double zazbx, Double zazby, Double yazbx,
                 Double yazby, Double cssw, Double kj, Double aqjj, Double gxgc,
                 Double mssd, Integer id, String bh, String mc, String jcnf,
                 String ytfl, Integer sfdb, Integer ywfzss, Integer fswlx,
                 Integer sshdaoid, Integer sshduanid, String xxdz, String zxlczh,
                 Double jd, Double wd
            , Integer szxzqh, Integer sffcfhds,
                 String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh,
                 String bz, Integer creater, Date createtime, Date updatetime) {
        super();
        this.dbszsd = dbszsd;
        this.ywbz = ywbz;
        this.jsdw = jsdw;
        this.cyxs = cyxs;
        this.za = za;
        this.ya = ya;
        this.zazbx = zazbx;
        this.zazby = zazby;
        this.yazbx = yazbx;
        this.yazby = yazby;
        this.cssw = cssw;
        this.kj = kj;
        this.aqjj = aqjj;
        this.gxgc = gxgc;
        this.mssd = mssd;
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.jcnf = jcnf;
        this.ytfl = ytfl;
        this.sfdb = sfdb;
        this.ywfzss = ywfzss;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.xxdz = xxdz;
        this.zxlczh = zxlczh;
        this.jd = jd;
        this.wd = wd;
        this.szxzqh = szxzqh;
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


    // Property accessors

    public CHdGd(Double dbszsd, Integer ywbz, String jsdw, String cyxs,
                 Double za, Double ya, Double zazbx, Double zazby, Double yazbx,
                 Double yazby, Double cssw, Double kj, Double aqjj, Double gxgc,
                 Double mssd, String bh, String mc, String jcnf, String ytfl,
                 Integer sfdb, Integer ywfzss, Integer fswlx, Integer sshdaoid,
                 Integer sshduanid, String xxdz, String zxlczh, Double jd,
                 Double wd, Integer szxzqh, Integer sffcfhds, String gljgmc,
                 String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz,
                 Integer creater, Date createtime, Date updatetime) {
        super();
        this.dbszsd = dbszsd;
        this.ywbz = ywbz;
        this.jsdw = jsdw;
        this.cyxs = cyxs;
        this.za = za;
        this.ya = ya;
        this.zazbx = zazbx;
        this.zazby = zazby;
        this.yazbx = yazbx;
        this.yazby = yazby;
        this.cssw = cssw;
        this.kj = kj;
        this.aqjj = aqjj;
        this.gxgc = gxgc;
        this.mssd = mssd;
        this.bh = bh;
        this.mc = mc;
        this.jcnf = jcnf;
        this.ytfl = ytfl;
        this.sfdb = sfdb;
        this.ywfzss = ywfzss;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.xxdz = xxdz;
        this.zxlczh = zxlczh;
        this.jd = jd;
        this.wd = wd;
        this.szxzqh = szxzqh;
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


    public String getJcnf() {
        return jcnf;
    }


    public Integer getSzxzqh() {
        return szxzqh;
    }


    public Double getDbszsd() {
        return dbszsd;
    }


    public void setDbszsd(Double dbszsd) {
        this.dbszsd = dbszsd;
    }


    public Double getGxgc() {
        return gxgc;
    }


    public void setGxgc(Double gxgc) {
        this.gxgc = gxgc;
    }


    public void setJcnf(String jcnf) {
        this.jcnf = jcnf;
    }


    public void setSzxzqh(Integer szxzqh) {
        this.szxzqh = szxzqh;
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

    public Integer getYwbz() {
        return this.ywbz;
    }

    public void setYwbz(Integer ywbz) {
        this.ywbz = ywbz;
    }

    public String getJsdw() {
        return this.jsdw;
    }

    public void setJsdw(String jsdw) {
        this.jsdw = jsdw;
    }

    public String getCyxs() {
        return this.cyxs;
    }

    public void setCyxs(String cyxs) {
        this.cyxs = cyxs;
    }

    public Double getZa() {
        return this.za;
    }

    public void setZa(Double za) {
        this.za = za;
    }

    public Double getYa() {
        return this.ya;
    }

    public void setYa(Double ya) {
        this.ya = ya;
    }

    public Double getZazbx() {
        return this.zazbx;
    }

    public void setZazbx(Double zazbx) {
        this.zazbx = zazbx;
    }

    public Double getZazby() {
        return this.zazby;
    }

    public void setZazby(Double zazby) {
        this.zazby = zazby;
    }

    public Double getYazbx() {
        return this.yazbx;
    }

    public void setYazbx(Double yazbx) {
        this.yazbx = yazbx;
    }

    public Double getYazby() {
        return this.yazby;
    }

    public void setYazby(Double yazby) {
        this.yazby = yazby;
    }

    public Double getCssw() {
        return this.cssw;
    }

    public void setCssw(Double cssw) {
        this.cssw = cssw;
    }

    public Double getKj() {
        return this.kj;
    }

    public void setKj(Double kj) {
        this.kj = kj;
    }

    public Double getAqjj() {
        return this.aqjj;
    }

    public void setAqjj(Double aqjj) {
        this.aqjj = aqjj;
    }

    public Double getMssd() {
        return this.mssd;
    }

    public void setMssd(Double mssd) {
        this.mssd = mssd;
    }

    public String getYtfl() {
        return this.ytfl;
    }

    public void setYtfl(String ytfl) {
        this.ytfl = ytfl;
    }

    public Integer getSfdb() {
        return this.sfdb;
    }

    public void setSfdb(Integer sfdb) {
        this.sfdb = sfdb;
    }

    public Integer getYwfzss() {
        return this.ywfzss;
    }

    public void setYwfzss(Integer ywfzss) {
        this.ywfzss = ywfzss;
    }

    public Integer getFswlx() {
        return this.fswlx;
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

    public String getXxdz() {
        return this.xxdz;
    }

    public void setXxdz(String xxdz) {
        this.xxdz = xxdz;
    }

    public String getZxlczh() {
        return this.zxlczh;
    }

    public void setZxlczh(String zxlczh) {
        this.zxlczh = zxlczh;
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

}