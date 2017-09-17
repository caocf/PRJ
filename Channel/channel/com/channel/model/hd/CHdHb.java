package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdHb entity. @author MyEclipse Persistence Tools
 */

public class CHdHb implements java.io.Serializable {

    // Fields
    @PropDesc("航标")
    private String hbname;

    private Integer id;


    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6, max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user",fn = "appuniquecheck",msg = "标志编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 2, desc = "标志编号", must = true)
    @PropDesc("标志编号")
    private String bh;


    @UiInputValidator({@Validator(filter = "notempty"), @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 3, desc = "标志名称", must = true)
    @PropDesc("标志名称")
    private String mc;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 4, desc = "标志类型", must = true, inputtype = "selectdict", selectdictname = "hblx")
    @PropDesc("标志类型")
    private Integer lx;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 5, desc = "标志结构", must = true, inputtype = "selectdict", selectdictname = "hbjg")
    @PropDesc("标志结构")
    private Integer jg;


    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 6, desc = "标志支撑方式", must = true, inputtype = "selectdict", selectdictname = "hbzcfs")
    @PropDesc("标志支撑方式")
    private Integer zcfs;

    @UiInput(group = 1, order = 7, desc = "电源类别", must = false, inputtype = "selectdict", selectdictname = "hbdylb")
    @PropDesc("电源类别")
    private Integer dylb;

    @UiInput(group = 1, order = 8, desc = "是否遥测遥控", must = false, inputtype = "selectyesno")
    @PropDesc("是否遥测遥控")
    private Integer ycyk;


    @UiInput(group = 1, order = 9, desc = "标志颜色", must = false)
    @PropDesc("标志颜色")
    private String bzys;

    @UiInput(group = 1, order = 10, desc = "灯光颜色", must = false)
    @PropDesc("灯光颜色")
    private String dgys;

    @UiInput(group = 1, order = 11, desc = "闪光节奏", must = false)
    @PropDesc("闪光节奏")
    private String sgjz;

    @UiInput(group = 1, order = 12, desc = "闪光周期", must = false)
    @PropDesc("闪光周期")
    private String sgzq;

    @UiInput(group = 1, order = 13, desc = "灯光射程(米)", must = false)
    @PropDesc("灯光射程(米)")
    private String dgsc;

    @UiInput(group = 1, order = 14, desc = "灯器型号", must = false)
    @PropDesc("灯器型号")
    private String dqxh;

    @UiInput(group = 1, order = 15, desc = "灯质信号", must = false, inputtype = "selectdict", selectdictname = "hbdzxh")
    @PropDesc("灯质信号")
    private Integer dzxh;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 16, desc = "光学性质", must = true, inputtype = "selectdict", selectdictname = "hbgxxz")
    @PropDesc("光学性质")
    private Integer gxxz;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 17, desc = "灯标颜色", must = true, inputtype = "selectdict", selectdictname = "hbdbys")
    @PropDesc("灯标颜色")
    private Integer dbys;

    @UiInputValidator({@Validator(filter = "double", msg = "您的输入必须为数字")})
    @UiInput(group = 1, order = 18, desc = "航标灯高度(米)", must = false)
    @PropDesc("航标灯高度(米)")
    private Double hbdgd;

    @UiInput(group = 1, order = 19, desc = "是否有顶标", must = false, inputtype = "selectyesno")
    @PropDesc("是否有顶标")
    private Integer sfydb;


    @UiInput(group = 1, order = 20, desc = "牌面尺寸", must = false)
    @PropDesc("牌面尺寸")
    private String pmcc;

    @UiInput(group = 1, order = 21, desc = "是否临时标", must = false, inputtype = "selectyesno")
    @PropDesc("是否临时标")
    private Integer sflsb;


    @PropDesc("附属物类别")
    private Integer fswlx;

    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = true, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;

    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = true, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

    @UiInputValidator({@Validator(filter = "notempty", attr = "selitem")})
    @UiInput(group = 2, order = 3, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 2, order = 4, desc = "岸别", must = false,inputtype = "selectdict", selectdictname = "ab")
    @PropDesc("岸别")
    private Integer ab;

    @UiInput(group = 2, order = 5, desc = "中心里程桩号", must = false)
    @PropDesc("中心里程桩号")
    private String zxlczh;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 6, desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order =7 , desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;

    @UiInput(group = 2, order = 8, desc = "是否在分叉辅航段上", must = false, inputtype = "selectyesno")
    @PropDesc("是否在分叉辅航段上")
    private Integer sffcfhds;

    @UiInput(groupname = "辅助信息", group = 3, order = 1, desc = "灯器生产厂家", must = false)
    @PropDesc("灯器生产厂家")
    private String dqsccj;

    @UiInput(group = 3, order = 2, desc = "设标时间", must = false, inputtype = "selectdate")
    @PropDesc("设标时间")
    private Date sbsj;


    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 3, order = 3, desc = "管养单位名称", must = false)
    @PropDesc("管养单位名称")
    private String gljgmc;

    @UiInput(group = 3, order = 5, desc = "管养单位联系人", must = false)
    @PropDesc("管养单位联系人")
    private String gljglxr;

    @UiInputValidator({@Validator(filter = "phone")})
    @UiInput(group = 3, order = 6, desc = "管养单位联系电话", must = false)
    @PropDesc("管养单位联系电话")
    private String gljglxdh;

    @UiInput(group = 3, order = 4, desc = "管养单位所在行政区划", must = false, inputtype = "selectxzqh")
    @PropDesc("管养单位所在行政区划")
    private Integer gljgszxzqh;

    @UiInput(group = 3, order = 7, desc = "备注", inputtype = "textarea", oneline = true)
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
    public CHdHb() {
    }
    // Property accessors

    public Integer getSzxzqh() {
        return szxzqh;
    }

    public CHdHb(Integer id, String bh, String mc, Integer lx, Integer jg,
                 Integer zcfs, Integer dylb, Integer ycyk, String bzys, String dgys,
                 String sgjz, String sgzq, String dgsc, String dqxh, Integer dzxh,
                 Integer gxxz, Integer dbys, Double hbdgd, Integer sfydb,
                 String pmcc, Integer sflsb, Integer fswlx, Integer sshdaoid,
                 Integer sshduanid, Integer szxzqh, Integer ab, String zxlczh,
                 Double jd, Double wd, Integer sffcfhds, String dqsccj,
                 Date sbsj, String gljgmc, String gljglxr, String gljglxdh,
                 Integer gljgszxzqh, String bz, Integer creater, Date createtime,
                 Date updatetime) {
        super();
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.lx = lx;
        this.jg = jg;
        this.zcfs = zcfs;
        this.dylb = dylb;
        this.ycyk = ycyk;
        this.bzys = bzys;
        this.dgys = dgys;
        this.sgjz = sgjz;
        this.sgzq = sgzq;
        this.dgsc = dgsc;
        this.dqxh = dqxh;
        this.dzxh = dzxh;
        this.gxxz = gxxz;
        this.dbys = dbys;
        this.hbdgd = hbdgd;
        this.sfydb = sfydb;
        this.pmcc = pmcc;
        this.sflsb = sflsb;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.zxlczh = zxlczh;
        this.jd = jd;
        this.wd = wd;
        this.sffcfhds = sffcfhds;
        this.dqsccj = dqsccj;
        this.sbsj = sbsj;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdHb(String bh, String mc, Integer lx, Integer jg, Integer zcfs,
                 Integer dylb, Integer ycyk, String bzys, String dgys, String sgjz,
                 String sgzq, String dgsc, String dqxh, Integer dzxh, Integer gxxz,
                 Integer dbys, Double hbdgd, Integer sfydb, String pmcc,
                 Integer sflsb, Integer fswlx, Integer sshdaoid, Integer sshduanid,
                 Integer szxzqh, Integer ab, String zxlczh, Double jd,
                 Double wd, Integer sffcfhds, String dqsccj, Date sbsj,
                 String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh,
                 String bz, Integer creater, Date createtime, Date updatetime) {
        super();
        this.bh = bh;
        this.mc = mc;
        this.lx = lx;
        this.jg = jg;
        this.zcfs = zcfs;
        this.dylb = dylb;
        this.ycyk = ycyk;
        this.bzys = bzys;
        this.dgys = dgys;
        this.sgjz = sgjz;
        this.sgzq = sgzq;
        this.dgsc = dgsc;
        this.dqxh = dqxh;
        this.dzxh = dzxh;
        this.gxxz = gxxz;
        this.dbys = dbys;
        this.hbdgd = hbdgd;
        this.sfydb = sfydb;
        this.pmcc = pmcc;
        this.sflsb = sflsb;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.szxzqh = szxzqh;
        this.ab = ab;
        this.zxlczh = zxlczh;
        this.jd = jd;
        this.wd = wd;
        this.sffcfhds = sffcfhds;
        this.dqsccj = dqsccj;
        this.sbsj = sbsj;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
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

    public Integer getLx() {
        return this.lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public Integer getJg() {
        return this.jg;
    }

    public void setJg(Integer jg) {
        this.jg = jg;
    }

    public Integer getZcfs() {
        return this.zcfs;
    }

    public void setZcfs(Integer zcfs) {
        this.zcfs = zcfs;
    }

    public Integer getDylb() {
        return this.dylb;
    }

    public void setDylb(Integer dylb) {
        this.dylb = dylb;
    }

    public Integer getYcyk() {
        return this.ycyk;
    }

    public void setYcyk(Integer ycyk) {
        this.ycyk = ycyk;
    }

    public String getBzys() {
        return this.bzys;
    }

    public void setBzys(String bzys) {
        this.bzys = bzys;
    }

    public String getDgys() {
        return this.dgys;
    }

    public void setDgys(String dgys) {
        this.dgys = dgys;
    }

    public String getSgjz() {
        return this.sgjz;
    }

    public void setSgjz(String sgjz) {
        this.sgjz = sgjz;
    }

    public String getSgzq() {
        return this.sgzq;
    }

    public void setSgzq(String sgzq) {
        this.sgzq = sgzq;
    }

    public String getDgsc() {
        return this.dgsc;
    }

    public void setDgsc(String dgsc) {
        this.dgsc = dgsc;
    }

    public String getDqxh() {
        return this.dqxh;
    }

    public void setDqxh(String dqxh) {
        this.dqxh = dqxh;
    }

    public Integer getDzxh() {
        return dzxh;
    }

    public void setDzxh(Integer dzxh) {
        this.dzxh = dzxh;
    }

    public Integer getGxxz() {
        return gxxz;
    }

    public void setGxxz(Integer gxxz) {
        this.gxxz = gxxz;
    }

    public Integer getDbys() {
        return dbys;
    }

    public void setDbys(Integer dbys) {
        this.dbys = dbys;
    }

    public Double getHbdgd() {
        return this.hbdgd;
    }

    public void setHbdgd(Double hbdgd) {
        this.hbdgd = hbdgd;
    }

    public Integer getSfydb() {
        return this.sfydb;
    }

    public void setSfydb(Integer sfydb) {
        this.sfydb = sfydb;
    }

    public String getPmcc() {
        return this.pmcc;
    }

    public void setPmcc(String pmcc) {
        this.pmcc = pmcc;
    }

    public Integer getSflsb() {
        return this.sflsb;
    }

    public void setSflsb(Integer sflsb) {
        this.sflsb = sflsb;
    }

    public Date getSbsj() {
        return this.sbsj;
    }

    public void setSbsj(Date sbsj) {
        this.sbsj = sbsj;
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

    public Integer getAb() {
        return this.ab;
    }

    public void setAb(Integer ab) {
        this.ab = ab;
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

    public String getDqsccj() {
        return this.dqsccj;
    }

    public void setDqsccj(String dqsccj) {
        this.dqsccj = dqsccj;
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