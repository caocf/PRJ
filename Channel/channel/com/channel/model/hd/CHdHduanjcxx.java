package com.channel.model.hd;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdHduanjcxx entity. @author MyEclipse Persistence Tools
 */

public class CHdHduanjcxx implements java.io.Serializable {

    // Fields
    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 3, max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user",fn = "hduanbhuniquecheck",msg = "航段编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 1, desc = "航段编号", must = true)
    @PropDesc("航段编号")
    private String hdbh;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 2, desc = "航段起点名称", must = true)
    @PropDesc("航段起点名称")
    private String hdqdmc;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 3, desc = "航段终点名称", must = true)
    @PropDesc("航段终点名称")
    private String hdzdmc;

    @PropDesc("所属航道编号")
    private Integer sshdid;

    @UiInputValidator({@Validator(filter = "notempty", attr = "selitem")})
    @UiInput(group = 1, order = 5, must = true, inputtype = "selectxzqh")
    @PropDesc("所在行政区划")
    private Integer hdszxzqh;

    @UiInputValidator({@Validator(filter = "notempty", msg = "航段里程不能为空"),
            @Validator(filter = "double", msg = "输入必须为小数")})
    @UiInput(group = 1, order = 6, desc = "航段里程(公里)", must = true)
    @PropDesc("航段里程(公里)")
    private Double hdlc;

    @UiInputValidator({@Validator(filter = "double", msg = "航段水深必须为数字")})
    @UiInput(group = 1, order = 7, desc = "航段水深(米)", must = false)
    @PropDesc("航段水深(米)")
    private Double hdss;

    @UiInput(group = 1, order = 8, desc = "航段水深采集时间", must = false, inputtype = "selectdate")
    @PropDesc("航段水深采集时间")
    private Date hdsscjsj;

    @UiInputValidator({@Validator(filter = "double", msg = "航段宽度必须为数字")})
    @UiInput(group = 1, order = 9, desc = "航段宽度(米)", must = false)
    @PropDesc("航段宽度(米)")
    private Double hdkd;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double", msg = "航段最小弯曲半径必须为数字")})
    @UiInput(group = 1, order = 10, desc = "航段最小弯曲半径(米)", must = true)
    @PropDesc("航段最小弯曲半径(米)")
    private Double hdzxwqbj;

    @UiInputValidator({@Validator(filter = "double", msg = "最高通航水位必须为数字")})
    @UiInput(group = 1, order = 11, desc = "最高通航水位(米)", must = false)
    @PropDesc("最高通航水位(米)")
    private Double zgthsw;

    @UiInputValidator({@Validator(filter = "double", msg = "最低通航水位必须为数字")})
    @UiInput(group = 1, order = 12, desc = "最低通航水位(米)", must = false)
    @PropDesc("最低通航水位(米)")
    private Double zdthsw;

    @UiInput(group = 1, order = 13, desc = "航段现状技术等级", must = false, inputtype = "selectdict", selectdictname = "jsdj")
    @PropDesc("航段现状技术等级")
    private Integer hdxzjsdj;

    @UiInput(group = 1, order = 14, desc = "航段定级技术等级", must = false, inputtype = "selectdict", selectdictname = "jsdj")
    @PropDesc("航段定级技术等级")
    private Integer hddjjsdj;

    @UiInputValidator({@Validator(filter = "double", msg = "最低通航水位保证率必须为数字"),
            @Validator(filter = "valrange", min = 0, max = 100)})
    @UiInput(group = 1, order = 15, desc = "最低通航水位保证率(%)", must = false)
    @PropDesc("最低通航水位保证率")
    private Double zdthswbzl;

    @UiInput(group = 1, order = 16, desc = "乘潮通航海船代表船型载重吨", must = false)
    @PropDesc("乘潮通航海船代表船型载重吨")
    private Double ccthhcdbcxzzd;

    @UiInput(group = 1, order = 17, desc = "规划乘潮通航海船代表船型载重吨", must = false)
    @PropDesc("规划乘潮通航海船代表船型载重吨")
    private Double ghccthhcdbcxzzd;

    @UiInput(group = 1, order = 18, desc = "航段是否通航", must = false, inputtype = "selectyesno")
    @PropDesc("航段是否通航")
    private Integer hdsfth;

    @UiInput(group = 1, order = 19, desc = "是否季节性航道", must = false, inputtype = "selectyesno")
    @PropDesc("是否季节性航道")
    private Integer sfjjxhd;

    @UiInputValidator({@Validator(filter = "double", msg = "过河建筑最小通航净高数必须为数字")})
    @UiInput(group = 1, order = 20, desc = "过河建筑最小通航净高(米)", must = false)
    @PropDesc("过河建筑最小通航净高(米)")
    private Double ghjzzxthjg;

    @UiInputValidator({@Validator(filter = "double", msg = "过河建筑最小通航净宽必须为数字")})
    @UiInput(group = 1, order = 21, desc = "过河建筑最小通航净宽(米)", must = false)
    @PropDesc("过河建筑最小通航净宽(米)")
    private Double ghjzzxthjk;

    @UiInputValidator({@Validator(filter = "double", msg = "过河建筑门槛水深必须为数字")})
    @UiInput(group = 1, order = 22, desc = "过河建筑门槛水深(米)", must = false)
    @PropDesc("过河建筑门槛水深(米)")
    private Double ghjzmkss;

    @UiInputValidator({@Validator(filter = "double", msg = "过河建筑限宽必须为数字")})
    @UiInput(group = 1, order = 23, desc = "过河建筑限宽(米)", must = false)
    @PropDesc("过河建筑限宽(米)")
    private Double ghjzxk;

    @UiInput(group = 1, order = 24, desc = "分界航段类型", must = false)
    @PropDesc("分界航段类型")
    private String fjhdlx;

    @UiInput(group = 1, order = 25, desc = "是否有分叉辅航段", must = false, inputtype = "selectyesno")
    @PropDesc("是否有分叉辅航段")
    private Integer sfyfcfhd;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 26, desc = "分叉辅航段里程(公里)", must = false)
    @PropDesc("分叉辅航段里程(公里)")
    private Double fcfhdlc;

    @UiInput(group = 1, order = 27, desc = "分叉辅航段现状技术等级", must = false, inputtype = "selectdict", selectdictname = "jsdj")
    @PropDesc("分叉辅航段现状技术等级")
    private Integer fcfhdxzjsdj;

    @UiInput(group = 1, order = 28, desc = "分叉辅航段定级技术等级", must = false, inputtype = "selectdict", selectdictname = "jsdj")
    @PropDesc("分叉辅航段定级技术等级")
    private Integer fcfhddjjsdj;

    @UiInput(group = 1, order = 29, desc = "分叉辅航段维护类别", must = false)
    @PropDesc("分叉辅航段维护类别")
    private String fcfhdwhlb;

    @UiInput(group = 1, order = 30, desc = "分叉辅航段航标配布类别", must = false)
    @PropDesc("分叉辅航段航标配布类别")
    private String fcfhdhbpblb;

    @UiInput(group = 1, order = 31, desc = "是否有浅滩", must = false, inputtype = "selectyesno")
    @PropDesc("是否有浅滩")
    private Integer sfyqt;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 6, suborder = 2, must = false)
    @PropDesc("重复里程")
    private Double cflc;

    @UiInput(group = 1, order = 33, desc = "是否专用航段", must = false, inputtype = "selectyesno")
    @PropDesc("是否专用航段")
    private Integer sfzyhd;

    @UiInput(group = 1, order = 34, desc = "是否为界河航段", must = false, inputtype = "selectyesno")
    @PropDesc("是否为界河航段")
    private Integer sfwjhhd;

    @UiInput(group = 1, order = 36, desc = "水域管理类型", must = false)
    @PropDesc("水域管理类型")
    private Integer sygllx;

    @UiInput(group = 1, order = 37, desc = "航段维护类别", must = true, inputtype = "selectdict", selectdictname = "hdwhlb")
    @PropDesc("航段维护类别")
    private Integer hdwhlb;

    @UiInputValidator(@Validator(filter = "notempty"))
    @UiInput(group = 1, order = 38, desc = "航段等级", must = true, inputtype = "selectdict", selectdictname = "hddj")
    @PropDesc("航段等级")
    private Integer hddj;

    @UiInput(group = 1, order = 39, desc = "航段属性", must = true, inputtype = "selectdict", selectdictname = "hdsx")
    @PropDesc("航段属性")
    private Integer hdsx;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 40, desc = "维护水深", must = false)
    @PropDesc("维护水深")
    private Double whss;

    @UiInput(group = 1, order = 41, desc = "维护底宽", must = false)
    @PropDesc("维护底宽")
    private String whdk;

    @UiInput(group = 1, order = 42, desc = "维护弯曲半径", must = false)
    @PropDesc("维护弯曲半径")
    private String whwqbj;

    @UiInput(group = 1, order = 43, desc = "维护水深保证率", must = false)
    @PropDesc("维护水深保证率")
    private String whssbzl;

    @UiInputValidator({@Validator(filter = "double")})
    @UiInput(group = 1, order = 44, desc = "航标设标里程", must = false)
    @PropDesc("航标设标里程")
    private Double hbsblc;

    @UiInput(group = 1, order = 45, desc = "航标配布类别", must = false)
    @PropDesc("航标配布类别")
    private String hbpblb;

    @UiInput(group = 1, order = 46, desc = "航标维护正常率", must = false)
    @PropDesc("航标维护正常率")
    private String hbwhzcl;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "河流代码", must = false)
    @PropDesc("河流代码")
    private String hldm;

    @UiInputValidator(@Validator(filter = "length", min = 0, max = 32))
    @UiInput(group = 2, order = 2, desc = "河流名称", must = false)
    @PropDesc("河流名称")
    private String hlmc;

    @UiInput(group = 2, order = 3, desc = "航段起点里程桩号", must = false)
    @PropDesc("航段起点里程桩号")
    private String hdqdlczh;

    @UiInput(group = 2, order = 4, desc = "航段终点里程桩号", must = false)
    @PropDesc("航段终点里程桩号")
    private String hdzdlczh;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double")})
    @UiInput(group = 2, order = 5, desc = "航段起点经度", must = true)
    @PropDesc("航段起点经度")
    private Double hdqdzbx;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double")})
    @UiInput(group = 2, order = 6, desc = "航段起点纬度", must = true)
    @PropDesc("航段起点纬度")
    private Double hdqdzby;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double")})
    @UiInput(group = 2, order = 7, desc = "航段终点经度", must = true)
    @PropDesc("航段终点经度")
    private Double hdzdzbx;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "double")})
    @UiInput(group = 2, order = 8, desc = "航段终点纬度", must = true)
    @PropDesc("航段终点纬度")
    private Double hdzdzby;

    @UiInput(group = 2, order = 9, desc = "起点是否为主控点", must = true, inputtype = "selectyesno")
    @PropDesc("起点是否为主控点")
    private Integer qdsfwzkd;

    @UiInput(group = 2, order = 11, desc = "起点是否为分界点", must = false, inputtype = "selectyesno")
    @PropDesc("起点是否为分界点")
    private Integer qdsfwfjd;

    @UiInput(group = 2, order = 12, desc = "起点分界点类别", must = false)
    @PropDesc("起点分界点类别")
    private String qdfjdlb;

    @UiInput(group = 2, order = 10, desc = "终点是否为主控点", must = true, inputtype = "selectyesno")
    @PropDesc("终点是否为主控点")
    private Integer zdsfwzkd;

    @UiInput(group = 2, order = 13, desc = "终点是否为分界点", must = false, inputtype = "selectyesno")
    @PropDesc("终点是否为分界点")
    private Integer zdsfwfjd;

    @UiInput(group = 2, order = 14, desc = "终点分界点类别", must = false)
    @PropDesc("终点分界点类型")
    private String zdfjdlx;

    @UiInputValidator({@Validator(filter = "notempty", attr = "selitem"),
            @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(groupname = "辅助信息", group = 3, order = 1, desc = "所属管理机构编号", must = true, inputtype = "selectdpt")
    @PropDesc("所属管理机构编号")
    private Integer gljgbh;

    @UiInput(group = 3, order = 3, desc = "所属管理机构联系人")
    @PropDesc("所属管理机构联系人")
    private String gljglxr;

    @UiInputValidator(@Validator(filter = "phone"))
    @UiInput(group = 3, order = 4, desc = "所属管理机构联系电话")
    @PropDesc("所属管理机构联系电话")
    private String gljglxdh;

    @PropDesc("所属管理机构所在行政区划")
    private Integer gljgszxzqh;

    @UiInput(group = 3, order = 5, desc = "备注", must = false, inputtype = "textarea", oneline = true)
    @PropDesc("备注")
    private String bz;

    @PropDesc("创建时间")
    private Date createtime;
    @PropDesc("更新时间")
    private Date updatetime;

    // Constructors


    public CHdHduanjcxx(Double hdss, Integer id, String hdbh, String hdqdmc, String hdzdmc, Integer sshdid, Integer hdszxzqh, Double hdlc, Date hdsscjsj, Double hdkd, Double hdzxwqbj, Double zgthsw, Double zdthsw, Integer hdxzjsdj, Integer hddjjsdj, Double zdthswbzl, Double ccthhcdbcxzzd, Double ghccthhcdbcxzzd, Integer hdsfth, Integer sfjjxhd, Double ghjzzxthjg, Double ghjzzxthjk, Double ghjzmkss, Double ghjzxk, String fjhdlx, Integer sfyfcfhd, Double fcfhdlc, Integer fcfhdxzjsdj, Integer fcfhddjjsdj, String fcfhdwhlb, String fcfhdhbpblb, Integer sfyqt, Double cflc, Integer sfzyhd, Integer sfwjhhd, Integer sygllx, Integer hdwhlb, Integer hddj, Integer hdsx, Double whss, String whdk, String whwqbj, String whssbzl, Double hbsblc, String hbpblb, String hbwhzcl, String hldm, String hlmc, String hdqdlczh, String hdzdlczh, Double hdqdzbx, Double hdqdzby, Double hdzdzbx, Double hdzdzby, Integer qdsfwzkd, Integer qdsfwfjd, String qdfjdlb, Integer zdsfwzkd, Integer zdsfwfjd, String zdfjdlx, Integer gljgbh, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Date createtime, Date updatetime) {
        this.hdss = hdss;
        this.id = id;
        this.hdbh = hdbh;
        this.hdqdmc = hdqdmc;
        this.hdzdmc = hdzdmc;
        this.sshdid = sshdid;
        this.hdszxzqh = hdszxzqh;
        this.hdlc = hdlc;
        this.hdsscjsj = hdsscjsj;
        this.hdkd = hdkd;
        this.hdzxwqbj = hdzxwqbj;
        this.zgthsw = zgthsw;
        this.zdthsw = zdthsw;
        this.hdxzjsdj = hdxzjsdj;
        this.hddjjsdj = hddjjsdj;
        this.zdthswbzl = zdthswbzl;
        this.ccthhcdbcxzzd = ccthhcdbcxzzd;
        this.ghccthhcdbcxzzd = ghccthhcdbcxzzd;
        this.hdsfth = hdsfth;
        this.sfjjxhd = sfjjxhd;
        this.ghjzzxthjg = ghjzzxthjg;
        this.ghjzzxthjk = ghjzzxthjk;
        this.ghjzmkss = ghjzmkss;
        this.ghjzxk = ghjzxk;
        this.fjhdlx = fjhdlx;
        this.sfyfcfhd = sfyfcfhd;
        this.fcfhdlc = fcfhdlc;
        this.fcfhdxzjsdj = fcfhdxzjsdj;
        this.fcfhddjjsdj = fcfhddjjsdj;
        this.fcfhdwhlb = fcfhdwhlb;
        this.fcfhdhbpblb = fcfhdhbpblb;
        this.sfyqt = sfyqt;
        this.cflc = cflc;
        this.sfzyhd = sfzyhd;
        this.sfwjhhd = sfwjhhd;
        this.sygllx = sygllx;
        this.hdwhlb = hdwhlb;
        this.hddj = hddj;
        this.hdsx = hdsx;
        this.whss = whss;
        this.whdk = whdk;
        this.whwqbj = whwqbj;
        this.whssbzl = whssbzl;
        this.hbsblc = hbsblc;
        this.hbpblb = hbpblb;
        this.hbwhzcl = hbwhzcl;
        this.hldm = hldm;
        this.hlmc = hlmc;
        this.hdqdlczh = hdqdlczh;
        this.hdzdlczh = hdzdlczh;
        this.hdqdzbx = hdqdzbx;
        this.hdqdzby = hdqdzby;
        this.hdzdzbx = hdzdzbx;
        this.hdzdzby = hdzdzby;
        this.qdsfwzkd = qdsfwzkd;
        this.qdsfwfjd = qdsfwfjd;
        this.qdfjdlb = qdfjdlb;
        this.zdsfwzkd = zdsfwzkd;
        this.zdsfwfjd = zdsfwfjd;
        this.zdfjdlx = zdfjdlx;
        this.gljgbh = gljgbh;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdHduanjcxx(String hdqdmc, String hdbh, String hdzdmc, Integer sshdid, Integer hdszxzqh, Double hdlc, Double hdss, Date hdsscjsj, Double hdkd, Double hdzxwqbj, Double zgthsw, Double zdthsw, Integer hdxzjsdj, Integer hddjjsdj, Double zdthswbzl, Double ccthhcdbcxzzd, Double ghccthhcdbcxzzd, Integer hdsfth, Integer sfjjxhd, Double ghjzzxthjg, Double ghjzzxthjk, Double ghjzmkss, Double ghjzxk, String fjhdlx, Integer sfyfcfhd, Double fcfhdlc, Integer fcfhdxzjsdj, Integer fcfhddjjsdj, String fcfhdwhlb, String fcfhdhbpblb, Integer sfyqt, Double cflc, Integer sfzyhd, Integer sfwjhhd, Integer sygllx, Integer hdwhlb, Integer hddj, Integer hdsx, Double whss, String whdk, String whwqbj, String whssbzl, Double hbsblc, String hbpblb, String hbwhzcl, String hldm, String hlmc, String hdqdlczh, String hdzdlczh, Double hdqdzbx, Double hdqdzby, Double hdzdzbx, Double hdzdzby, Integer qdsfwzkd, Integer qdsfwfjd, String qdfjdlb, Integer zdsfwzkd, Integer zdsfwfjd, String zdfjdlx, Integer gljgbh, String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz, Date createtime, Date updatetime) {
        this.hdqdmc = hdqdmc;
        this.hdbh = hdbh;
        this.hdzdmc = hdzdmc;
        this.sshdid = sshdid;
        this.hdszxzqh = hdszxzqh;
        this.hdlc = hdlc;
        this.hdss = hdss;
        this.hdsscjsj = hdsscjsj;
        this.hdkd = hdkd;
        this.hdzxwqbj = hdzxwqbj;
        this.zgthsw = zgthsw;
        this.zdthsw = zdthsw;
        this.hdxzjsdj = hdxzjsdj;
        this.hddjjsdj = hddjjsdj;
        this.zdthswbzl = zdthswbzl;
        this.ccthhcdbcxzzd = ccthhcdbcxzzd;
        this.ghccthhcdbcxzzd = ghccthhcdbcxzzd;
        this.hdsfth = hdsfth;
        this.sfjjxhd = sfjjxhd;
        this.ghjzzxthjg = ghjzzxthjg;
        this.ghjzzxthjk = ghjzzxthjk;
        this.ghjzmkss = ghjzmkss;
        this.ghjzxk = ghjzxk;
        this.fjhdlx = fjhdlx;
        this.sfyfcfhd = sfyfcfhd;
        this.fcfhdlc = fcfhdlc;
        this.fcfhdxzjsdj = fcfhdxzjsdj;
        this.fcfhddjjsdj = fcfhddjjsdj;
        this.fcfhdwhlb = fcfhdwhlb;
        this.fcfhdhbpblb = fcfhdhbpblb;
        this.sfyqt = sfyqt;
        this.cflc = cflc;
        this.sfzyhd = sfzyhd;
        this.sfwjhhd = sfwjhhd;
        this.sygllx = sygllx;
        this.hdwhlb = hdwhlb;
        this.hddj = hddj;
        this.hdsx = hdsx;
        this.whss = whss;
        this.whdk = whdk;
        this.whwqbj = whwqbj;
        this.whssbzl = whssbzl;
        this.hbsblc = hbsblc;
        this.hbpblb = hbpblb;
        this.hbwhzcl = hbwhzcl;
        this.hldm = hldm;
        this.hlmc = hlmc;
        this.hdqdlczh = hdqdlczh;
        this.hdzdlczh = hdzdlczh;
        this.hdqdzbx = hdqdzbx;
        this.hdqdzby = hdqdzby;
        this.hdzdzbx = hdzdzbx;
        this.hdzdzby = hdzdzby;
        this.qdsfwzkd = qdsfwzkd;
        this.qdsfwfjd = qdsfwfjd;
        this.qdfjdlb = qdfjdlb;
        this.zdsfwzkd = zdsfwzkd;
        this.zdsfwfjd = zdsfwfjd;
        this.zdfjdlx = zdfjdlx;
        this.gljgbh = gljgbh;
        this.gljglxr = gljglxr;
        this.gljglxdh = gljglxdh;
        this.gljgszxzqh = gljgszxzqh;
        this.bz = bz;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdHduanjcxx(String hdqdlczh, String hdzdlczh, Double hdqdzbx, Double hdqdzby, Double hdzdzbx, Double hdzdzby, Integer qdsfwzkd, Integer qdsfwfjd, String qdfjdlb, Integer zdsfwzkd, Integer zdsfwfjd, String zdfjdlx) {
        this.hdqdlczh = hdqdlczh;
        this.hdzdlczh = hdzdlczh;
        this.hdqdzbx = hdqdzbx;
        this.hdqdzby = hdqdzby;
        this.hdzdzbx = hdzdzbx;
        this.hdzdzby = hdzdzby;
        this.qdsfwzkd = qdsfwzkd;
        this.qdsfwfjd = qdsfwfjd;
        this.qdfjdlb = qdfjdlb;
        this.zdsfwzkd = zdsfwzkd;
        this.zdsfwfjd = zdsfwfjd;
        this.zdfjdlx = zdfjdlx;
    }

    public Double getHbsblc() {
        return hbsblc;
    }

    public void setHbsblc(Double hbsblc) {
        this.hbsblc = hbsblc;
    }

    public String getHbpblb() {
        return hbpblb;
    }

    public void setHbpblb(String hbpblb) {
        this.hbpblb = hbpblb;
    }

    public String getHbwhzcl() {
        return hbwhzcl;
    }

    public void setHbwhzcl(String hbwhzcl) {
        this.hbwhzcl = hbwhzcl;
    }

    public Integer getId() {
        return this.id;
    }


    public CHdHduanjcxx() {
        super();
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getHdbh() {
        return this.hdbh;
    }

    public void setHdbh(String hdbh) {
        this.hdbh = hdbh;
    }

    public String getHdqdmc() {
        return this.hdqdmc;
    }

    public void setHdqdmc(String hdqdmc) {
        this.hdqdmc = hdqdmc;
    }

    public String getHdzdmc() {
        return this.hdzdmc;
    }

    public void setHdzdmc(String hdzdmc) {
        this.hdzdmc = hdzdmc;
    }

    public Double getHdlc() {
        return this.hdlc;
    }

    public void setHdlc(Double hdlc) {
        this.hdlc = hdlc;
    }

    public Double getHdss() {
        return this.hdss;
    }

    public void setHdss(Double hdss) {
        this.hdss = hdss;
    }

    @JSON(format = "yyyy-MM-dd")
    public Date getHdsscjsj() {
        return this.hdsscjsj;
    }

    public void setHdsscjsj(Date hdsscjsj) {
        this.hdsscjsj = hdsscjsj;
    }

    public Double getHdkd() {
        return this.hdkd;
    }

    public void setHdkd(Double hdkd) {
        this.hdkd = hdkd;
    }

    public Double getHdzxwqbj() {
        return this.hdzxwqbj;
    }

    public void setHdzxwqbj(Double hdzxwqbj) {
        this.hdzxwqbj = hdzxwqbj;
    }

    public Double getZgthsw() {
        return this.zgthsw;
    }

    public void setZgthsw(Double zgthsw) {
        this.zgthsw = zgthsw;
    }

    public Double getZdthsw() {
        return this.zdthsw;
    }

    public void setZdthsw(Double zdthsw) {
        this.zdthsw = zdthsw;
    }

    public Integer getHdxzjsdj() {
        return this.hdxzjsdj;
    }

    public void setHdxzjsdj(Integer hdxzjsdj) {
        this.hdxzjsdj = hdxzjsdj;
    }

    public Integer getHddjjsdj() {
        return this.hddjjsdj;
    }

    public void setHddjjsdj(Integer hddjjsdj) {
        this.hddjjsdj = hddjjsdj;
    }

    public Double getZdthswbzl() {
        return this.zdthswbzl;
    }

    public void setZdthswbzl(Double zdthswbzl) {
        this.zdthswbzl = zdthswbzl;
    }

    public Double getCcthhcdbcxzzd() {
        return this.ccthhcdbcxzzd;
    }

    public void setCcthhcdbcxzzd(Double ccthhcdbcxzzd) {
        this.ccthhcdbcxzzd = ccthhcdbcxzzd;
    }

    public Double getGhccthhcdbcxzzd() {
        return this.ghccthhcdbcxzzd;
    }

    public void setGhccthhcdbcxzzd(Double ghccthhcdbcxzzd) {
        this.ghccthhcdbcxzzd = ghccthhcdbcxzzd;
    }

    public Integer getHdsfth() {
        return this.hdsfth;
    }

    public void setHdsfth(Integer hdsfth) {
        this.hdsfth = hdsfth;
    }

    public Integer getSfjjxhd() {
        return this.sfjjxhd;
    }

    public void setSfjjxhd(Integer sfjjxhd) {
        this.sfjjxhd = sfjjxhd;
    }

    public Double getGhjzzxthjg() {
        return this.ghjzzxthjg;
    }

    public void setGhjzzxthjg(Double ghjzzxthjg) {
        this.ghjzzxthjg = ghjzzxthjg;
    }

    public Double getGhjzzxthjk() {
        return this.ghjzzxthjk;
    }

    public void setGhjzzxthjk(Double ghjzzxthjk) {
        this.ghjzzxthjk = ghjzzxthjk;
    }

    public Double getGhjzmkss() {
        return this.ghjzmkss;
    }

    public void setGhjzmkss(Double ghjzmkss) {
        this.ghjzmkss = ghjzmkss;
    }

    public Double getGhjzxk() {
        return this.ghjzxk;
    }

    public void setGhjzxk(Double ghjzxk) {
        this.ghjzxk = ghjzxk;
    }

    public Integer getHdsx() {
        return this.hdsx;
    }

    public void setHdsx(Integer hdsx) {
        this.hdsx = hdsx;
    }

    public Integer getSshdid() {
        return sshdid;
    }

    public void setSshdid(Integer sshdid) {
        this.sshdid = sshdid;
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

    public Integer getHdszxzqh() {
        return this.hdszxzqh;
    }

    public void setHdszxzqh(Integer hdszxzqh) {
        this.hdszxzqh = hdszxzqh;
    }

    public String getHdqdlczh() {
        return this.hdqdlczh;
    }

    public void setHdqdlczh(String hdqdlczh) {
        this.hdqdlczh = hdqdlczh;
    }

    public Double getHdqdzbx() {
        return this.hdqdzbx;
    }

    public void setHdqdzbx(Double hdqdzbx) {
        this.hdqdzbx = hdqdzbx;
    }

    public Double getHdqdzby() {
        return this.hdqdzby;
    }

    public void setHdqdzby(Double hdqdzby) {
        this.hdqdzby = hdqdzby;
    }

    public String getHdzdlczh() {
        return this.hdzdlczh;
    }

    public void setHdzdlczh(String hdzdlczh) {
        this.hdzdlczh = hdzdlczh;
    }

    public Double getHdzdzbx() {
        return this.hdzdzbx;
    }

    public void setHdzdzbx(Double hdzdzbx) {
        this.hdzdzbx = hdzdzbx;
    }

    public Double getHdzdzby() {
        return this.hdzdzby;
    }

    public void setHdzdzby(Double hdzdzby) {
        this.hdzdzby = hdzdzby;
    }

    public Integer getSfwjhhd() {
        return this.sfwjhhd;
    }

    public void setSfwjhhd(Integer sfwjhhd) {
        this.sfwjhhd = sfwjhhd;
    }

    public String getFjhdlx() {
        return this.fjhdlx;
    }

    public void setFjhdlx(String fjhdlx) {
        this.fjhdlx = fjhdlx;
    }

    public Integer getSfyfcfhd() {
        return this.sfyfcfhd;
    }

    public void setSfyfcfhd(Integer sfyfcfhd) {
        this.sfyfcfhd = sfyfcfhd;
    }

    public Double getFcfhdlc() {
        return this.fcfhdlc;
    }

    public void setFcfhdlc(Double fcfhdlc) {
        this.fcfhdlc = fcfhdlc;
    }

    public Integer getFcfhdxzjsdj() {
        return this.fcfhdxzjsdj;
    }

    public void setFcfhdxzjsdj(Integer fcfhdxzjsdj) {
        this.fcfhdxzjsdj = fcfhdxzjsdj;
    }

    public Integer getFcfhddjjsdj() {
        return this.fcfhddjjsdj;
    }

    public void setFcfhddjjsdj(Integer fcfhddjjsdj) {
        this.fcfhddjjsdj = fcfhddjjsdj;
    }

    public String getFcfhdwhlb() {
        return this.fcfhdwhlb;
    }

    public void setFcfhdwhlb(String fcfhdwhlb) {
        this.fcfhdwhlb = fcfhdwhlb;
    }

    public String getFcfhdhbpblb() {
        return this.fcfhdhbpblb;
    }

    public void setFcfhdhbpblb(String fcfhdhbpblb) {
        this.fcfhdhbpblb = fcfhdhbpblb;
    }

    public Integer getSfyqt() {
        return this.sfyqt;
    }

    public void setSfyqt(Integer sfyqt) {
        this.sfyqt = sfyqt;
    }

    public Double getCflc() {
        return cflc;
    }

    public void setCflc(Double cflc) {
        this.cflc = cflc;
    }

    public Integer getSfzyhd() {
        return this.sfzyhd;
    }

    public void setSfzyhd(Integer sfzyhd) {
        this.sfzyhd = sfzyhd;
    }

    public Integer getSygllx() {
        return this.sygllx;
    }

    public void setSygllx(Integer sygllx) {
        this.sygllx = sygllx;
    }

    public Integer getHdwhlb() {
        return this.hdwhlb;
    }

    public void setHdwhlb(Integer hdwhlb) {
        this.hdwhlb = hdwhlb;
    }

    public Integer getQdsfwzkd() {
        return this.qdsfwzkd;
    }

    public void setQdsfwzkd(Integer qdsfwzkd) {
        this.qdsfwzkd = qdsfwzkd;
    }

    public Integer getQdsfwfjd() {
        return this.qdsfwfjd;
    }

    public void setQdsfwfjd(Integer qdsfwfjd) {
        this.qdsfwfjd = qdsfwfjd;
    }

    public String getQdfjdlb() {
        return this.qdfjdlb;
    }

    public void setQdfjdlb(String qdfjdlb) {
        this.qdfjdlb = qdfjdlb;
    }

    public Integer getZdsfwzkd() {
        return this.zdsfwzkd;
    }

    public void setZdsfwzkd(Integer zdsfwzkd) {
        this.zdsfwzkd = zdsfwzkd;
    }

    public Integer getZdsfwfjd() {
        return this.zdsfwfjd;
    }

    public void setZdsfwfjd(Integer zdsfwfjd) {
        this.zdsfwfjd = zdsfwfjd;
    }

    public String getZdfjdlx() {
        return this.zdfjdlx;
    }

    public void setZdfjdlx(String zdfjdlx) {
        this.zdfjdlx = zdfjdlx;
    }

    public Integer getGljgbh() {
        return this.gljgbh;
    }

    public void setGljgbh(Integer gljgbh) {
        this.gljgbh = gljgbh;
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

    public Integer getHddj() {
        return hddj;
    }

    public void setHddj(Integer hddj) {
        this.hddj = hddj;
    }

    public Double getWhss() {
        return whss;
    }

    public void setWhss(Double whss) {
        this.whss = whss;
    }

    public String getWhdk() {
        return whdk;
    }

    public void setWhdk(String whdk) {
        this.whdk = whdk;
    }

    public String getWhwqbj() {
        return whwqbj;
    }

    public void setWhwqbj(String whwqbj) {
        this.whwqbj = whwqbj;
    }

    public String getWhssbzl() {
        return whssbzl;
    }

    public void setWhssbzl(String whssbzl) {
        this.whssbzl = whssbzl;
    }

    @Override
    public String toString() {
        return getHdqdmc() + "-" + getHdzdmc();
    }
}