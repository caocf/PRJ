package com.channel.model.hd;

import java.util.Date;

import com.channel.bean.PropDesc;
import com.channel.modelutil.anotation.UiInput;
import com.channel.modelutil.anotation.UiInputValidator;
import com.channel.modelutil.anotation.Validator;

/**
 * CHdFwq entity. @author MyEclipse Persistence Tools
 */

public class CHdFwq implements java.io.Serializable {

    // Fields
    @PropDesc("服务区")
    private String fwqname;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 3, desc = "泊位数", must = false)
    @PropDesc("泊位数")
    private Integer bws;

    private Integer id;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 6, max = 11, msg = "输入必须为11个数字"),
            @Validator(filter = "number"),
            @Validator(filter = "user",fn = "appuniquecheck",msg = "编号已存在")
    })
    @UiInput(groupname = "基础信息", group = 1, order = 1, desc = "编号", must = true)
    @PropDesc("编号")
    private String bh;

    @UiInputValidator({@Validator(filter = "notempty"),
            @Validator(filter = "length", min = 0, max = 32)})
    @UiInput(group = 1, order = 2, desc = "名称", must = true)
    @PropDesc("名称")
    private String mc;

    @UiInput(group = 1, order = 2, must = false)
    @PropDesc("服务区类型")
    private String lx;

    @UiInput(group = 1, order = 4, desc = "是否碍航", must = false, inputtype = "selectyesno", defaultval = "0")
    @PropDesc("是否碍航")
    private Integer sfah;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 5, must = false)
    @PropDesc("靠泊能力（T）")
    private Integer kbnl;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 6, desc = "系缆桩数量", must = false)
    @PropDesc("系缆桩数量")
    private Integer xlzsl;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 7, desc = "轮胎护舷数量", must = false)
    @PropDesc("轮胎护舷数量")
    private Integer lthxsl;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 8, desc = "供水口数量", must = false)
    @PropDesc("供水口数量")
    private Integer gsksl;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 9, desc = "照明灯数量", must = false)
    @PropDesc("照明灯数量")
    private Integer zmdsl;

    @UiInputValidator({@Validator(filter = "number")})
    @UiInput(group = 1, order = 10, desc = "充电设施数量", must = false)
    @PropDesc("充电设施数量")
    private Integer cdsssl;

    @PropDesc("附属物类别")
    private Integer fswlx;

    @UiInput(groupname = "地理信息", group = 2, order = 1, desc = "所属航道编号", must = true, defaultvalfromweb = "hdaoid", readonly = true, hidden = true)
    @PropDesc("所属航道编号")
    private Integer sshdaoid;

    @UiInput(group = 2, order = 2, desc = "所属航段编号", must = true, defaultvalfromweb = "hduanid", readonly = true, hidden = true)
    @PropDesc("所属航段编号")
    private Integer sshduanid;

    @UiInput(group = 2, order = 3, desc = "起点里程桩号", must = false)
    @PropDesc("起点里程桩号")
    private String ssqdlczh;

    @UiInput(group = 2, order = 4, desc = "占用岸线", must = false)
    @PropDesc("占用岸线")
    private Double zyax;

    @UiInputValidator({@Validator(filter = "double")})
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


    @UiInputValidator({@Validator(filter = "notempty", attr = "selitem")})
    @UiInput(group = 2, order = 12, desc = "所在行政区划", must = true, inputtype = "selectxzqh", defaultvalfromweb = "szxzqh", readonly = true)
    @PropDesc("所在行政区划")
    private Integer szxzqh;

    @UiInput(group = 2, order = 13, desc = "岸别", must = false,inputtype = "selectdict", selectdictname = "ab")
    @PropDesc("岸别")
    private Integer ab;

    @UiInput(group = 2, order = 14, desc = "是否在分叉辅助航段", must = false, inputtype = "selectyesno")
    @PropDesc("是否在分叉辅助航段")
    private Integer sffcfhds;

    @UiInput(group = 2, order = 15, must = false, inputtype = "selectmonth")
    @PropDesc("建造年月")
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

    // Constructors

    /**
     * default constructor
     */
    public CHdFwq() {
    }

    public CHdFwq(Integer bws, Integer id, String bh, String mc, String lx, Integer sfah,
                  Integer kbnl, Integer xlzsl, Integer lthxsl, Integer gsksl,
                  Integer zmdsl, Integer cdsssl, Integer fswlx, Integer sshdaoid,
                  Integer sshduanid, String ssqdlczh, Double zyax, Double zyaxcd,
                  Double zyaxqdzbx, Double zyaxqdzby, Double zyaxzdzbx,
                  Double zyaxzdzby, Double jd, Double wd, Integer szxzqh,
                  Integer ab, Integer sffcfhds, String jcnf, String gljgmc,
                  String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz,
                  Integer creater, Date createtime, Date updatetime) {
        super();
        this.bws = bws;
        this.id = id;
        this.bh = bh;
        this.mc = mc;
        this.lx = lx;
        this.sfah = sfah;
        this.kbnl = kbnl;
        this.xlzsl = xlzsl;
        this.lthxsl = lthxsl;
        this.gsksl = gsksl;
        this.zmdsl = zmdsl;
        this.cdsssl = cdsssl;
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

    public CHdFwq(Integer bws, String bh, String mc, Integer sfah,
                  Integer kbnl, Integer xlzsl, Integer lthxsl, Integer gsksl,
                  Integer zmdsl, Integer cdsssl, Integer fswlx, Integer sshdaoid,
                  Integer sshduanid, String ssqdlczh, Double zyax, Double zyaxcd,
                  Double zyaxqdzbx, Double zyaxqdzby, Double zyaxzdzbx,
                  Double zyaxzdzby, Double jd, Double wd, Integer szxzqh,
                  Integer ab, Integer sffcfhds, String jcnf, String gljgmc,
                  String gljglxr, String gljglxdh, Integer gljgszxzqh, String bz,
                  Integer creater, Date createtime, Date updatetime) {
        super();
        this.bws = bws;
        this.bh = bh;
        this.mc = mc;
        this.sfah = sfah;
        this.kbnl = kbnl;
        this.xlzsl = xlzsl;
        this.lthxsl = lthxsl;
        this.gsksl = gsksl;
        this.zmdsl = zmdsl;
        this.cdsssl = cdsssl;
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

    // Property accessors

    public Integer getKbnl() {
        return kbnl;
    }

    public void setKbnl(Integer kbnl) {
        this.kbnl = kbnl;
    }

    public Integer getXlzsl() {
        return xlzsl;
    }

    public void setXlzsl(Integer xlzsl) {
        this.xlzsl = xlzsl;
    }

    public Integer getLthxsl() {
        return lthxsl;
    }

    public void setLthxsl(Integer lthxsl) {
        this.lthxsl = lthxsl;
    }

    public Integer getGsksl() {
        return gsksl;
    }

    public void setGsksl(Integer gsksl) {
        this.gsksl = gsksl;
    }

    public Integer getZmdsl() {
        return zmdsl;
    }

    public void setZmdsl(Integer zmdsl) {
        this.zmdsl = zmdsl;
    }

    public Integer getCdsssl() {
        return cdsssl;
    }

    public void setCdsssl(Integer cdsssl) {
        this.cdsssl = cdsssl;
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

    public Integer getBws() {
        return this.bws;
    }

    public void setBws(Integer bws) {
        this.bws = bws;
    }

    public Integer getSfah() {
        return this.sfah;
    }

    public void setSfah(Integer sfah) {
        this.sfah = sfah;
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

    public Double getZyax() {
        return this.zyax;
    }

    public void setZyax(Double zyax) {
        this.zyax = zyax;
    }

    public Double getZyaxcd() {
        return this.zyaxcd;
    }

    public void setZyaxcd(Double zyaxcd) {
        this.zyaxcd = zyaxcd;
    }

    public Double getZyaxqdzbx() {
        return this.zyaxqdzbx;
    }

    public void setZyaxqdzbx(Double zyaxqdzbx) {
        this.zyaxqdzbx = zyaxqdzbx;
    }

    public Double getZyaxqdzby() {
        return this.zyaxqdzby;
    }

    public void setZyaxqdzby(Double zyaxqdzby) {
        this.zyaxqdzby = zyaxqdzby;
    }

    public Double getZyaxzdzbx() {
        return this.zyaxzdzbx;
    }

    public void setZyaxzdzbx(Double zyaxzdzbx) {
        this.zyaxzdzbx = zyaxzdzbx;
    }

    public Double getZyaxzdzby() {
        return this.zyaxzdzby;
    }

    public void setZyaxzdzby(Double zyaxzdzby) {
        this.zyaxzdzby = zyaxzdzby;
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

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

}