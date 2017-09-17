package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdQl entity. @author MyEclipse Persistence Tools
 */

public class CHdQl implements java.io.Serializable {

    // Fields

    @PropDesc("桥梁")
    private String qlname;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double", msg = "您输入的必须为数字")})
    @UiInput(group = 1, order = 3, desc = "桥面长(米)", must = true)
    @PropDesc("桥面长(米)")
    private Double qmc;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double", msg = "您输入的必须为数字")})
    @UiInput(group = 1, order = 4, desc = "桥面宽(米)", must = true)
    @PropDesc("桥面宽(米)")
    private Double qmk;

    @UiInput(group = 1, order = 5, desc = "是否有标志", must = false, inputtype = "selectyesno")
    @PropDesc("是否有标志")
    private Integer ywbz;

    @UiInputValidator({@Validator(filter = "double", msg = "您输入的必须为数字")})
    @UiInput(group = 1, order = 6, desc = "梁底标高(米)", must = false)
    @PropDesc("梁底标高(米)")
    private Double ldbg;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 7, desc = "设计最高通航水位(米)", must = false)
    @PropDesc("设计最高通航水位(米)")
    private Double sjzgthsw;

    @UiInput(group = 1, order = 8, desc = "最高通航水位洪水重现期", must = false)
    @PropDesc("最高通航水位洪水重现期")
    private Double sjzgthswhscxq;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 9, desc = "结构型式", must = true, inputtype = "selectdict", selectdictname = "qljgxs")
    @PropDesc("结构型式")
    private Integer jgxs;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 10, desc = "通航孔数", must = false)
    @PropDesc("通航孔数")
    private Integer thks;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 11, desc = "通航净高(米)", must = false)
    @PropDesc("通航净高(米)")
    private Double thjg;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 12, desc = "通航净宽(米)", must = false)
    @PropDesc("通航净宽(米)")
    private Double thjk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 13, desc = "孔一净宽/上底宽/侧高(米)", must = false)
    @PropDesc("孔一净宽/上底宽/侧高(米)")
    private Double kyjk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 14, desc = "孔二净宽/上底宽/侧高(米)", must = false)
    @PropDesc("孔二净宽/上底宽/侧高(米)")
    private Double kejk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 15, desc = "孔三净宽/上底宽/侧高(米)", must = false)
    @PropDesc("孔三净宽/上底宽/侧高(米)")
    private Double ksanjk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 16, desc = "孔四净宽/上底宽/侧高(米)", must = false)
    @PropDesc("孔四净宽/上底宽/侧高(米)")
    private Double ksijk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 17, desc = "孔五净宽/上底宽/侧高(米)", must = false)
    @PropDesc("孔五净宽/上底宽/侧高(米)")
    private Double kwjk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 18, desc = "法线与水流交角(度)", must = false)
    @PropDesc("法线与水流交角(度)")
    private Double fxysljj;

    @UiInput(group = 1, order = 19, desc = "老规划航道等级", must = false)
    @PropDesc("老规划航道等级")
    private String lghhddj;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 20, desc = "老规划要求净宽(米)", must = false)
    @PropDesc("老规划要求净宽(米)")
    private Double lghyqjk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 21, desc = "老规划要求净高(米)", must = false)
    @PropDesc("老规划要求净高(米)")
    private Double lghyqjg;

    @UiInput(group = 1, order = 22, desc = "修编规划航道等级", must = false)
    @PropDesc("修编规划航道等级")
    private String xbghhddj;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 23, desc = "修编规划要求净宽(米)", must = false)
    @PropDesc("修编规划要求净宽(米)")
    private Double xbghyqjk;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 24, desc = "修编规划要求净高(米)", must = false)
    @PropDesc("修编规划要求净高(米)")
    private Double xbghyqjg;

    @UiInput(group = 1, order = 25, desc = "是否符合航道等级", must = true, inputtype = "selectyesno")
    @PropDesc("是否符合航道等级")
    private Integer sffhhddj;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 26, desc = "是否满足老规划", must = true, inputtype = "selectyesno")
    @PropDesc("是否满足老规划")
    private Integer sfmzlgh;

    @UiInputValidator({@Validator(filter = "notempty")})
    @UiInput(group = 1, order = 27, desc = "是否满足新编规划", must = true, inputtype = "selectyesno")
    @PropDesc("是否满足新编规划")
    private Integer sfmzxbgh;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 28, desc = "建造总价(万元)", must = false)
    @PropDesc("建造总价(万元)")
    private Double jzzj;

    @UiInput(group = 1, order = 29, desc = "建造年月", must = false, inputtype = "selectdate")
    @PropDesc("建造年月")
    private String zjny;

    @UiInput(group = 1, order = 30, desc = "测时水位(米)", must = false)
    @PropDesc("测时水位(米)")
    private Double cssw;

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

    @UiInput(group = 1, order = 31, desc = "建成年份", must = false, inputtype = "selectyear")
    @PropDesc("建成年份")
    private String jcnf;

    @UiInput(group = 1, order = 32, desc = "用途分类", must = false, inputtype = "selectdict", selectdictname = "qlytfl")
    @PropDesc("用途分类")
    private Integer ytfl;

    @UiInput(group = 1, order = 33, desc = "是否达标", must = false, inputtype = "selectyesno")
    @PropDesc("是否达标")
    private Integer sfdb;

    @UiInput(group = 1, order = 34, desc = "是否有防撞设施", must = false, inputtype = "selectyesno")
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
    @UiInput(group = 2, order =5 , desc = "中心点经度", must = false)
    @PropDesc("中心点经度")
    private Double jd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 2, order = 6, desc = "中心点纬度", must = false)
    @PropDesc("中心点纬度")
    private Double wd;


    @UiInputValidator({@Validator(filter = "notempty", attr = "selitem")})
    @UiInput(group = 2, order = 6, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 1, order = 33, desc = "是否分叉辅航段上", must = false, inputtype = "selectyesno")
    @PropDesc("是否分叉辅航段上")
    private Integer sffcfhds;


    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(groupname = "辅助信息", group = 3, order = 1, desc = "管养单位名称", must = false)
    @PropDesc("管养单位名称")
    private String gljgmc;

    @UiInput(group = 3, order = 2, desc = "管养单位联系人", must = false)
    @PropDesc("管养单位联系人")
    private String gljglxr;


    @UiInputValidator({@Validator(filter = "phone")})
    @UiInput(group = 3, order = 3, desc = "管养单位联系电话", must = false)
    @PropDesc("管养单位联系电话")
    private String gljglxdh;

    @UiInput(group = 3, order = 4, desc = "管养单位所在行政区划", must = false, inputtype = "selectxzqh")
    @PropDesc("管养单位所在行政区划")
    private Integer gljgszxzqh;

    @UiInput(group = 3, order = 5, desc = "备注", inputtype = "textarea", oneline = true)
    @PropDesc("备注")
    private String bz;


    @PropDesc("创建人")
    private Integer creater;
    @PropDesc("创建时间")
    private Date createtime;
    @PropDesc("更新时间")
    private Date updatetime;

    public CHdQl() {
    }

    public CHdQl(Double qmc, Double qmk, Integer ywbz, Double ldbg, Double sjzgthsw, Double sjzgthswhscxq, Integer jgxs, Integer thks, Double thjg, Double thjk, Double kyjk, Double kejk, Double ksanjk, Double ksijk, Double kwjk, Double fxysljj, String lghhddj, Double lghyqjk, Double lghyqjg, String xbghhddj, Double xbghyqjk, Double xbghyqjg, Integer sffhhddj, Integer sfmzlgh, Integer sfmzxbgh, Double jzzj, String zjny, Double cssw, String bh, String mc, String jcnf, Integer ytfl, Integer sfdb, Integer ywfzss, Integer fswlx, Integer sshdaoid, Integer sshduanid, String xxdz, String zxlczh, Double jd, Double wd, Integer szxzqh, Integer sffcfhds, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {
        this.qmc = qmc;
        this.qmk = qmk;
        this.ywbz = ywbz;
        this.ldbg = ldbg;
        this.sjzgthsw = sjzgthsw;
        this.sjzgthswhscxq = sjzgthswhscxq;
        this.jgxs = jgxs;
        this.thks = thks;
        this.thjg = thjg;
        this.thjk = thjk;
        this.kyjk = kyjk;
        this.kejk = kejk;
        this.ksanjk = ksanjk;
        this.ksijk = ksijk;
        this.kwjk = kwjk;
        this.fxysljj = fxysljj;
        this.lghhddj = lghhddj;
        this.lghyqjk = lghyqjk;
        this.lghyqjg = lghyqjg;
        this.xbghhddj = xbghhddj;
        this.xbghyqjk = xbghyqjk;
        this.xbghyqjg = xbghyqjg;
        this.sffhhddj = sffhhddj;
        this.sfmzlgh = sfmzlgh;
        this.sfmzxbgh = sfmzxbgh;
        this.jzzj = jzzj;
        this.zjny = zjny;
        this.cssw = cssw;
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

    public CHdQl(Double qmc, Double qmk, Integer ywbz, Double ldbg, Double sjzgthsw, Double sjzgthswhscxq, Integer jgxs, Integer thks, Double thjg, Double thjk, Double kyjk, Double kejk, Double ksanjk, Double ksijk, Double kwjk, Double fxysljj, String lghhddj, Double lghyqjk, Double lghyqjg, String xbghhddj, Double xbghyqjk, Double xbghyqjg, Integer sffhhddj, Integer sfmzlgh, Integer sfmzxbgh, Double jzzj, String zjny, Double cssw, Integer id, String bh, String mc, String jcnf, Integer ytfl, Integer sfdb, Integer ywfzss, Integer fswlx, Integer sshdaoid, Integer sshduanid, String xxdz, String zxlczh, Double jd, Double wd, Integer szxzqh, Integer sffcfhds, String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Integer creater, Date createtime, Date updatetime) {

        this.qmc = qmc;
        this.qmk = qmk;
        this.ywbz = ywbz;
        this.ldbg = ldbg;
        this.sjzgthsw = sjzgthsw;
        this.sjzgthswhscxq = sjzgthswhscxq;
        this.jgxs = jgxs;
        this.thks = thks;
        this.thjg = thjg;
        this.thjk = thjk;
        this.kyjk = kyjk;
        this.kejk = kejk;
        this.ksanjk = ksanjk;
        this.ksijk = ksijk;
        this.kwjk = kwjk;
        this.fxysljj = fxysljj;
        this.lghhddj = lghhddj;
        this.lghyqjk = lghyqjk;
        this.lghyqjg = lghyqjg;
        this.xbghhddj = xbghhddj;
        this.xbghyqjk = xbghyqjk;
        this.xbghyqjg = xbghyqjg;
        this.sffhhddj = sffhhddj;
        this.sfmzlgh = sfmzlgh;
        this.sfmzxbgh = sfmzxbgh;
        this.jzzj = jzzj;
        this.zjny = zjny;
        this.cssw = cssw;
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

    public String getXbghhddj() {
        return xbghhddj;
    }

    public void setXbghhddj(String xbghhddj) {
        this.xbghhddj = xbghhddj;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQmc() {
        return qmc;
    }

    public void setQmc(Double qmc) {
        this.qmc = qmc;
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

    public Double getQmk() {
        return this.qmk;
    }

    public void setQmk(Double qmk) {
        this.qmk = qmk;
    }

    public Integer getYwbz() {
        return this.ywbz;
    }

    public void setYwbz(Integer ywbz) {
        this.ywbz = ywbz;
    }

    public Double getLdbg() {
        return this.ldbg;
    }

    public void setLdbg(Double ldbg) {
        this.ldbg = ldbg;
    }

    public Double getSjzgthsw() {
        return this.sjzgthsw;
    }

    public void setSjzgthsw(Double sjzgthsw) {
        this.sjzgthsw = sjzgthsw;
    }

    public Double getSjzgthswhscxq() {
        return this.sjzgthswhscxq;
    }

    public void setSjzgthswhscxq(Double sjzgthswhscxq) {
        this.sjzgthswhscxq = sjzgthswhscxq;
    }

    public Integer getThks() {
        return this.thks;
    }

    public void setThks(Integer thks) {
        this.thks = thks;
    }

    public Double getThjg() {
        return this.thjg;
    }

    public void setThjg(Double thjg) {
        this.thjg = thjg;
    }

    public Double getThjk() {
        return this.thjk;
    }

    public void setThjk(Double thjk) {
        this.thjk = thjk;
    }

    public Double getKyjk() {
        return this.kyjk;
    }

    public void setKyjk(Double kyjk) {
        this.kyjk = kyjk;
    }

    public Double getKejk() {
        return this.kejk;
    }

    public void setKejk(Double kejk) {
        this.kejk = kejk;
    }

    public Double getKsanjk() {
        return this.ksanjk;
    }

    public void setKsanjk(Double ksanjk) {
        this.ksanjk = ksanjk;
    }

    public Double getKsijk() {
        return this.ksijk;
    }

    public void setKsijk(Double ksijk) {
        this.ksijk = ksijk;
    }

    public Double getKwjk() {
        return this.kwjk;
    }

    public void setKwjk(Double kwjk) {
        this.kwjk = kwjk;
    }

    public Double getFxysljj() {
        return this.fxysljj;
    }

    public void setFxysljj(Double fxysljj) {
        this.fxysljj = fxysljj;
    }

    public String getLghhddj() {
        return this.lghhddj;
    }

    public void setLghhddj(String lghhddj) {
        this.lghhddj = lghhddj;
    }

    public Double getLghyqjk() {
        return this.lghyqjk;
    }

    public void setLghyqjk(Double lghyqjk) {
        this.lghyqjk = lghyqjk;
    }

    public Double getLghyqjg() {
        return this.lghyqjg;
    }

    public void setLghyqjg(Double lghyqjg) {
        this.lghyqjg = lghyqjg;
    }

    public Double getXbghyqjk() {
        return this.xbghyqjk;
    }

    public void setXbghyqjk(Double xbghyqjk) {
        this.xbghyqjk = xbghyqjk;
    }

    public Double getXbghyqjg() {
        return this.xbghyqjg;
    }

    public void setXbghyqjg(Double xbghyqjg) {
        this.xbghyqjg = xbghyqjg;
    }

    public Integer getSffhhddj() {
        return this.sffhhddj;
    }

    public void setSffhhddj(Integer sffhhddj) {
        this.sffhhddj = sffhhddj;
    }

    public Integer getSfmzlgh() {
        return this.sfmzlgh;
    }

    public void setSfmzlgh(Integer sfmzlgh) {
        this.sfmzlgh = sfmzlgh;
    }

    public Integer getSfmzxbgh() {
        return this.sfmzxbgh;
    }

    public void setSfmzxbgh(Integer sfmzxbgh) {
        this.sfmzxbgh = sfmzxbgh;
    }

    public Double getJzzj() {
        return this.jzzj;
    }

    public void setJzzj(Double jzzj) {
        this.jzzj = jzzj;
    }

    public Double getCssw() {
        return this.cssw;
    }

    public void setCssw(Double cssw) {
        this.cssw = cssw;
    }

    public String getJcnf() {
        return this.jcnf;
    }

    public void setJcnf(String jcnf) {
        this.jcnf = jcnf;
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

    public Integer getSzxzqh() {
        return this.szxzqh;
    }

    public void setSzxzqh(Integer szxzqh) {
        this.szxzqh = szxzqh;
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

    public String getZjny() {
        return zjny;
    }

    public void setZjny(String zjny) {
        this.zjny = zjny;
    }

    public Integer getJgxs() {
        return jgxs;
    }

    public void setJgxs(Integer jgxs) {
        this.jgxs = jgxs;
    }

    public Integer getYtfl() {
        return ytfl;
    }

    public void setYtfl(Integer ytfl) {
        this.ytfl = ytfl;
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