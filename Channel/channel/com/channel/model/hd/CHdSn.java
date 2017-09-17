package com.channel.model.hd;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

import java.util.Date;

/**
 * Created by 25019 on 2015/10/15.
 */
public class CHdSn {
    // Fields
    @PropDesc("枢纽")
    private String snname;

    @UiInput(group = 1, order = 3, desc = "设计水级", must = false)
    @PropDesc("设计水级")
    private Double sjsj;

    @UiInput(group = 1, order = 4, desc = "设计最高通航水位回水里程(米)", must = false)
    @PropDesc("设计最高通航水位回水里程(米)")
    private Double sjzgthswhslc;

    @UiInput(group = 1, order = 5, desc = "设计最低通航水位回水里程(米)", must = false)
    @PropDesc("设计最低通航水位回水里程(米)")
    private Double sjzdthswhslc;

    @UiInput(group = 1, order = 6, desc = "是否竣工", must = false, inputtype = "selectyesno")
    @PropDesc("是否竣工")
    private Integer sfjg;

    @UiInput(group = 1, order = 7, desc = "升船机是否竣工", must = false, inputtype = "selectyesno")
    @PropDesc("升船机是否竣工")
    private Integer scjsfjg;

    @UiInput(group = 1, order = 8, desc = "船闸是否竣工", must = false, inputtype = "selectyesno")
    @PropDesc("船闸是否竣工")
    private Integer czsfjg;

    @UiInput(group = 1, order = 9, desc = "标志类型", must = false, inputtype = "selectdict", selectdictname = "snthlx")
    @PropDesc("通航类型")
    private Integer thlx;

    @UiInput(group = 1, order = 10, desc = "通航船舶吨级", must = false)
    @PropDesc("通航船舶吨级")
    private Double thcbdj;

    @UiInput(group = 1, order = 11, desc = "设计年单向旅客通过量", must = false)
    @PropDesc("设计年单向旅客通过量")
    private Double sjndxlktgl;

    @UiInput(group = 1, order = 12, desc = "设计年单向货物通过量", must = false)
    @PropDesc("设计年单向货物通过量")
    private Double sjndxhwtgl;

    @UiInput(group = 1, order = 13, desc = "是否建有桥梁", must = false, inputtype = "selectyesno")
    @PropDesc("是否建有桥梁")
    private Integer sfjyql;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 14, desc = "通航净高(米)", must = false)
    @PropDesc("通航净高(米)")
    private Double thjg;

    @UiInput(group = 1, order = 15, desc = "使用情况", must = false)
    @PropDesc("使用情况")
    private Integer syqk;

    @UiInput(group = 1, order = 16, desc = "通过能力是否匹配", must = false, inputtype = "selectyesno")
    @PropDesc("通过能力是否匹配")
    private Integer tgnlsfpp;

    @UiInput(group = 1, order = 17, desc = "当年上行货物通过量", must = false)
    @PropDesc("当年上行货物通过量")
    private Double dnsxhwtgl;

    @UiInput(group = 1, order = 18, desc = "当年下行货物通过量", must = false)
    @PropDesc("当年下行货物通过量")
    private Double dnxxhwtgl;

    @UiInput(group = 1, order = 19, desc = "当年上行旅客通过量", must = false)
    @PropDesc("当年上行旅客通过量")
    private Double dnsxlktgl;

    @UiInput(group = 1, order = 20, desc = "当年下行旅客通过量", must = false)
    @PropDesc("当年下行旅客通过量")
    private Double dnxxlktgl;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 21, desc = "上游引航道长度(米)", must = false)
    @PropDesc("上游引航道长度(米)")
    private Double syyhdcd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 22, desc = "下游引航道长度(米)", must = false)
    @PropDesc("下游引航道长度(米)")
    private Double xyyhdcd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 23, desc = "上游设计最高通航水位(米)", must = false)
    @PropDesc("上游设计最高通航水位(米)")
    private Double sysjzgthsw;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 24, desc = "上游设计最低通航水位(米)", must = false)
    @PropDesc("上游设计最低通航水位(米)")
    private Double sysjzdthsw;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 25, desc = "下游设计最高通航水位(米)", must = false)
    @PropDesc("下游设计最高通航水位(米)")
    private Double xysjzgthsw;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 26, desc = "下游设计最低通航水位(米)", must = false)
    @PropDesc("下游设计最低通航水位(米)")
    private Double xysjzdthsw;

    @UiInput(group = 1, order = 27, desc = "标志类型", must = false, inputtype = "selectdict", selectdictname = "snxs")
    @PropDesc("型式")
    private Integer xs;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 28, desc = "厢/架长(米)", must = false)
    @PropDesc("厢/架长(米)")
    private Double xjc;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 29, desc = "宽度(米)", must = false)
    @PropDesc("宽度(米)")
    private Double kd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 30, desc = "水深(米)", must = false)
    @PropDesc("水深(米)")
    private Double ss;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 31, desc = "一线船闸长度(米)", must = false)
    @PropDesc("一线船闸长度(米)")
    private Double yxczcd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 32, desc = "一线船闸宽度(米)", must = false)
    @PropDesc("一线船闸宽度(米)")
    private Double yxczkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 33, desc = "一线船闸闸首口门宽度(米)", must = false)
    @PropDesc("一线船闸闸首口门宽度(米)")
    private Double yxczzskmkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 34, desc = "一线船闸闸槛水深(米)", must = false)
    @PropDesc("一线船闸闸槛水深(米)")
    private Double yxczzkss;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 35, desc = "二线船闸长度(米)", must = false)
    @PropDesc("二线船闸长度(米)")
    private Double exczcd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 36, desc = "二线船闸宽度(米)", must = false)
    @PropDesc("二线船闸宽度(米)")
    private Double exczkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 37, desc = "二线船闸闸首口门宽度(米)", must = false)
    @PropDesc("二线船闸闸首口门宽度(米)")
    private Double exczzskmkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 38, desc = "二线船闸闸槛水深(米)", must = false)
    @PropDesc("二线船闸闸槛水深(米)")
    private Double exczzkss;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 39, desc = "预留船闸长度(米)", must = false)
    @PropDesc("预留船闸长度(米)")
    private Double ylczcd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 40, desc = "预留船闸宽度(米)", must = false)
    @PropDesc("预留船闸宽度")
    private Double ylczkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 41, desc = "预留船闸闸首口门宽度(米)", must = false)
    @PropDesc("预留船闸闸首口门宽度(米)")
    private Double ylczzskmkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 42, desc = "预留船闸闸槛水深(米)", must = false)
    @PropDesc("预留船闸闸槛水深(米)")
    private Double ylczzkss;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 43, desc = "闸门宽度(米)", must = false)
    @PropDesc("闸门宽度(米)")
    private Double zmkd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 44, desc = "门槛水深(米)", must = false)
    @PropDesc("门槛水深(米)")
    private Double mkss;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 45, desc = "孔数", must = false)
    @PropDesc("孔数")
    private Integer ks;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 46, desc = "净高(米)", must = false)
    @PropDesc("净高(米)")
    private Double jg;


    @UiInput(group = 2, order = 3, desc = "建成年份", must = false, inputtype = "selectyear")
    @PropDesc("建成年份")
    private String jcnf;

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


    @UiInput(group = 2, order = 7, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 2, order = 8, desc = "过船设施位置", must = false, inputtype = "selectdict", selectdictname = "sngcsswz")
    @PropDesc("过船设施位置")
    private Integer gcsswz;


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


    @PropDesc("附属物类型")
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
    public CHdSn() {
    }


    public CHdSn(Integer id, String bh, String mc, Double sjsj,
                 Double sjzgthswhslc, Double sjzdthswhslc, Integer sfjg,
                 Integer scjsfjg, Integer czsfjg, Integer thlx, Double thcbdj,
                 Double sjndxlktgl, Double sjndxhwtgl, Integer sfjyql, Double thjg,
                 Integer syqk, Integer tgnlsfpp, Double dnsxhwtgl, Double dnxxhwtgl,
                 Double dnsxlktgl, Double dnxxlktgl, Double syyhdcd, Double xyyhdcd,
                 Double sysjzgthsw, Double sysjzdthsw, Double xysjzgthsw,
                 Double xysjzdthsw, Integer xs, Double xjc, Double kd, Double ss,
                 Double yxczcd, Double yxczkd, Double yxczzskmkd, Double yxczzkss,
                 Double exczcd, Double exczkd, Double exczzskmkd, Double exczzkss,
                 Double ylczcd, Double ylczkd, Double ylczzskmkd, Double ylczzkss,
                 Double zmkd, Double mkss, Integer ks, Double jg, String jcnf,
                 Integer fswlx, Integer sshdaoid, Integer sshduanid, String zxlczh,
                 Double jd, Double wd, Integer szxzqh, Integer gcsswz,
                 String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh,
                 String bz, Integer creater, Date createtime, Date updatetime) {
        super();
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.sjsj = sjsj;
        this.sjzgthswhslc = sjzgthswhslc;
        this.sjzdthswhslc = sjzdthswhslc;
        this.sfjg = sfjg;
        this.scjsfjg = scjsfjg;
        this.czsfjg = czsfjg;
        this.thlx = thlx;
        this.thcbdj = thcbdj;
        this.sjndxlktgl = sjndxlktgl;
        this.sjndxhwtgl = sjndxhwtgl;
        this.sfjyql = sfjyql;
        this.thjg = thjg;
        this.syqk = syqk;
        this.tgnlsfpp = tgnlsfpp;
        this.dnsxhwtgl = dnsxhwtgl;
        this.dnxxhwtgl = dnxxhwtgl;
        this.dnsxlktgl = dnsxlktgl;
        this.dnxxlktgl = dnxxlktgl;
        this.syyhdcd = syyhdcd;
        this.xyyhdcd = xyyhdcd;
        this.sysjzgthsw = sysjzgthsw;
        this.sysjzdthsw = sysjzdthsw;
        this.xysjzgthsw = xysjzgthsw;
        this.xysjzdthsw = xysjzdthsw;
        this.xs = xs;
        this.xjc = xjc;
        this.kd = kd;
        this.ss = ss;
        this.yxczcd = yxczcd;
        this.yxczkd = yxczkd;
        this.yxczzskmkd = yxczzskmkd;
        this.yxczzkss = yxczzkss;
        this.exczcd = exczcd;
        this.exczkd = exczkd;
        this.exczzskmkd = exczzskmkd;
        this.exczzkss = exczzkss;
        this.ylczcd = ylczcd;
        this.ylczkd = ylczkd;
        this.ylczzskmkd = ylczzskmkd;
        this.ylczzkss = ylczzkss;
        this.zmkd = zmkd;
        this.mkss = mkss;
        this.ks = ks;
        this.jg = jg;
        this.jcnf = jcnf;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.zxlczh = zxlczh;
        this.jd = jd;
        this.wd = wd;
        this.szxzqh = szxzqh;
        this.gcsswz = gcsswz;
        this.gljgmc = gljgmc;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.creater = creater;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }


    public CHdSn(String bh, String mc, Double sjsj, Double sjzgthswhslc,
                 Double sjzdthswhslc, Integer sfjg, Integer scjsfjg, Integer czsfjg,
                 Integer thlx, Double thcbdj, Double sjndxlktgl, Double sjndxhwtgl,
                 Integer sfjyql, Double thjg, Integer syqk, Integer tgnlsfpp,
                 Double dnsxhwtgl, Double dnxxhwtgl, Double dnsxlktgl,
                 Double dnxxlktgl, Double syyhdcd, Double xyyhdcd,
                 Double sysjzgthsw, Double sysjzdthsw, Double xysjzgthsw,
                 Double xysjzdthsw, Integer xs, Double xjc, Double kd, Double ss,
                 Double yxczcd, Double yxczkd, Double yxczzskmkd, Double yxczzkss,
                 Double exczcd, Double exczkd, Double exczzskmkd, Double exczzkss,
                 Double ylczcd, Double ylczkd, Double ylczzskmkd, Double ylczzkss,
                 Double zmkd, Double mkss, Integer ks, Double jg, String jcnf,
                 Integer fswlx, Integer sshdaoid, Integer sshduanid, String zxlczh,
                 Double jd, Double wd, Integer szxzqh, Integer gcsswz,
                 String gljgmc, String gljglxr, String gljglxdh, Integer gljgszxzqh,
                 String bz, Integer creater, Date createtime, Date updatetime) {
        super();
        this.bh = bh;
        this.mc = mc;
        this.sjsj = sjsj;
        this.sjzgthswhslc = sjzgthswhslc;
        this.sjzdthswhslc = sjzdthswhslc;
        this.sfjg = sfjg;
        this.scjsfjg = scjsfjg;
        this.czsfjg = czsfjg;
        this.thlx = thlx;
        this.thcbdj = thcbdj;
        this.sjndxlktgl = sjndxlktgl;
        this.sjndxhwtgl = sjndxhwtgl;
        this.sfjyql = sfjyql;
        this.thjg = thjg;
        this.syqk = syqk;
        this.tgnlsfpp = tgnlsfpp;
        this.dnsxhwtgl = dnsxhwtgl;
        this.dnxxhwtgl = dnxxhwtgl;
        this.dnsxlktgl = dnsxlktgl;
        this.dnxxlktgl = dnxxlktgl;
        this.syyhdcd = syyhdcd;
        this.xyyhdcd = xyyhdcd;
        this.sysjzgthsw = sysjzgthsw;
        this.sysjzdthsw = sysjzdthsw;
        this.xysjzgthsw = xysjzgthsw;
        this.xysjzdthsw = xysjzdthsw;
        this.xs = xs;
        this.xjc = xjc;
        this.kd = kd;
        this.ss = ss;
        this.yxczcd = yxczcd;
        this.yxczkd = yxczkd;
        this.yxczzskmkd = yxczzskmkd;
        this.yxczzkss = yxczzkss;
        this.exczcd = exczcd;
        this.exczkd = exczkd;
        this.exczzskmkd = exczzskmkd;
        this.exczzkss = exczzkss;
        this.ylczcd = ylczcd;
        this.ylczkd = ylczkd;
        this.ylczzskmkd = ylczzskmkd;
        this.ylczzkss = ylczzkss;
        this.zmkd = zmkd;
        this.mkss = mkss;
        this.ks = ks;
        this.jg = jg;
        this.jcnf = jcnf;
        this.fswlx = fswlx;
        this.sshdaoid = sshdaoid;
        this.sshduanid = sshduanid;
        this.zxlczh = zxlczh;
        this.jd = jd;
        this.wd = wd;
        this.szxzqh = szxzqh;
        this.gcsswz = gcsswz;
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

    public Double getSjsj() {
        return this.sjsj;
    }

    public void setSjsj(Double sjsj) {
        this.sjsj = sjsj;
    }

    public Double getSjzgthswhslc() {
        return this.sjzgthswhslc;
    }

    public void setSjzgthswhslc(Double sjzgthswhslc) {
        this.sjzgthswhslc = sjzgthswhslc;
    }

    public Double getSjzdthswhslc() {
        return this.sjzdthswhslc;
    }

    public void setSjzdthswhslc(Double sjzdthswhslc) {
        this.sjzdthswhslc = sjzdthswhslc;
    }

    public Integer getSfjg() {
        return this.sfjg;
    }

    public void setSfjg(Integer sfjg) {
        this.sfjg = sfjg;
    }

    public Integer getScjsfjg() {
        return this.scjsfjg;
    }

    public void setScjsfjg(Integer scjsfjg) {
        this.scjsfjg = scjsfjg;
    }

    public Integer getCzsfjg() {
        return this.czsfjg;
    }

    public void setCzsfjg(Integer czsfjg) {
        this.czsfjg = czsfjg;
    }

    public Integer getThlx() {
        return this.thlx;
    }

    public void setThlx(Integer thlx) {
        this.thlx = thlx;
    }

    public Double getThcbdj() {
        return this.thcbdj;
    }

    public void setThcbdj(Double thcbdj) {
        this.thcbdj = thcbdj;
    }

    public Double getSjndxlktgl() {
        return this.sjndxlktgl;
    }

    public void setSjndxlktgl(Double sjndxlktgl) {
        this.sjndxlktgl = sjndxlktgl;
    }

    public Double getSjndxhwtgl() {
        return this.sjndxhwtgl;
    }

    public void setSjndxhwtgl(Double sjndxhwtgl) {
        this.sjndxhwtgl = sjndxhwtgl;
    }

    public Integer getSfjyql() {
        return this.sfjyql;
    }

    public void setSfjyql(Integer sfjyql) {
        this.sfjyql = sfjyql;
    }

    public Double getThjg() {
        return this.thjg;
    }

    public void setThjg(Double thjg) {
        this.thjg = thjg;
    }

    public Integer getSyqk() {
        return this.syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public Integer getTgnlsfpp() {
        return this.tgnlsfpp;
    }

    public void setTgnlsfpp(Integer tgnlsfpp) {
        this.tgnlsfpp = tgnlsfpp;
    }

    public Double getDnsxhwtgl() {
        return this.dnsxhwtgl;
    }

    public void setDnsxhwtgl(Double dnsxhwtgl) {
        this.dnsxhwtgl = dnsxhwtgl;
    }

    public Double getDnxxhwtgl() {
        return this.dnxxhwtgl;
    }

    public void setDnxxhwtgl(Double dnxxhwtgl) {
        this.dnxxhwtgl = dnxxhwtgl;
    }

    public Double getDnsxlktgl() {
        return this.dnsxlktgl;
    }

    public void setDnsxlktgl(Double dnsxlktgl) {
        this.dnsxlktgl = dnsxlktgl;
    }

    public Double getDnxxlktgl() {
        return this.dnxxlktgl;
    }

    public void setDnxxlktgl(Double dnxxlktgl) {
        this.dnxxlktgl = dnxxlktgl;
    }

    public Double getSyyhdcd() {
        return this.syyhdcd;
    }

    public void setSyyhdcd(Double syyhdcd) {
        this.syyhdcd = syyhdcd;
    }

    public Double getXyyhdcd() {
        return this.xyyhdcd;
    }

    public void setXyyhdcd(Double xyyhdcd) {
        this.xyyhdcd = xyyhdcd;
    }

    public Double getSysjzgthsw() {
        return this.sysjzgthsw;
    }

    public void setSysjzgthsw(Double sysjzgthsw) {
        this.sysjzgthsw = sysjzgthsw;
    }

    public Double getSysjzdthsw() {
        return this.sysjzdthsw;
    }

    public void setSysjzdthsw(Double sysjzdthsw) {
        this.sysjzdthsw = sysjzdthsw;
    }

    public Double getXysjzgthsw() {
        return this.xysjzgthsw;
    }

    public void setXysjzgthsw(Double xysjzgthsw) {
        this.xysjzgthsw = xysjzgthsw;
    }

    public Double getXysjzdthsw() {
        return this.xysjzdthsw;
    }

    public void setXysjzdthsw(Double xysjzdthsw) {
        this.xysjzdthsw = xysjzdthsw;
    }

    public Integer getXs() {
        return this.xs;
    }

    public void setXs(Integer xs) {
        this.xs = xs;
    }

    public Double getXjc() {
        return this.xjc;
    }

    public void setXjc(Double xjc) {
        this.xjc = xjc;
    }

    public Double getKd() {
        return this.kd;
    }

    public void setKd(Double kd) {
        this.kd = kd;
    }

    public Double getSs() {
        return this.ss;
    }

    public void setSs(Double ss) {
        this.ss = ss;
    }

    public Double getYxczcd() {
        return this.yxczcd;
    }

    public void setYxczcd(Double yxczcd) {
        this.yxczcd = yxczcd;
    }

    public Double getYxczkd() {
        return this.yxczkd;
    }

    public void setYxczkd(Double yxczkd) {
        this.yxczkd = yxczkd;
    }

    public Double getYxczzskmkd() {
        return this.yxczzskmkd;
    }

    public void setYxczzskmkd(Double yxczzskmkd) {
        this.yxczzskmkd = yxczzskmkd;
    }

    public Double getYxczzkss() {
        return this.yxczzkss;
    }

    public void setYxczzkss(Double yxczzkss) {
        this.yxczzkss = yxczzkss;
    }

    public Double getExczcd() {
        return this.exczcd;
    }

    public void setExczcd(Double exczcd) {
        this.exczcd = exczcd;
    }

    public Double getExczkd() {
        return this.exczkd;
    }

    public void setExczkd(Double exczkd) {
        this.exczkd = exczkd;
    }

    public Double getExczzskmkd() {
        return this.exczzskmkd;
    }

    public void setExczzskmkd(Double exczzskmkd) {
        this.exczzskmkd = exczzskmkd;
    }

    public Double getExczzkss() {
        return this.exczzkss;
    }

    public void setExczzkss(Double exczzkss) {
        this.exczzkss = exczzkss;
    }

    public Double getYlczcd() {
        return this.ylczcd;
    }

    public void setYlczcd(Double ylczcd) {
        this.ylczcd = ylczcd;
    }

    public Double getYlczkd() {
        return this.ylczkd;
    }

    public void setYlczkd(Double ylczkd) {
        this.ylczkd = ylczkd;
    }

    public Double getYlczzskmkd() {
        return this.ylczzskmkd;
    }

    public void setYlczzskmkd(Double ylczzskmkd) {
        this.ylczzskmkd = ylczzskmkd;
    }

    public Double getYlczzkss() {
        return this.ylczzkss;
    }

    public void setYlczzkss(Double ylczzkss) {
        this.ylczzkss = ylczzkss;
    }

    public Double getZmkd() {
        return this.zmkd;
    }

    public void setZmkd(Double zmkd) {
        this.zmkd = zmkd;
    }

    public Double getMkss() {
        return this.mkss;
    }

    public void setMkss(Double mkss) {
        this.mkss = mkss;
    }

    public Integer getKs() {
        return this.ks;
    }

    public void setKs(Integer ks) {
        this.ks = ks;
    }

    public Double getJg() {
        return this.jg;
    }

    public void setJg(Double jg) {
        this.jg = jg;
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

    public Integer getGcsswz() {
        return this.gcsswz;
    }

    public void setGcsswz(Integer gcsswz) {
        this.gcsswz = gcsswz;
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
