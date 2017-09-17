package com.channel.model.yh;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;


/**
 * CYhGgjbxx entity. @author MyEclipse Persistence Tools
 */

public class CYhGgjbxx implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer dw;
	private Integer hdid;
	private Date starttime;
	private Double clsl;
	private Integer cljg;
	private String clbz;
	private Integer sjsl;
	private Integer sjjg;
	private String sjbz;
	private Integer zzjzwxfsl;
	private Integer zzjzwxfjg;
	private String zzjzwxfbz;
	private Integer glmtxfsl;
	private Integer glmtxfjg;
	private String glmtxfbz;
	private Integer xlzxfsl;
	private Integer xlzxfjg;
	private String xlzxfbz;
	private Integer hbwhzcsl;
	private Integer hbwhzsl;
	private Integer hbwhjg;
	private String hbwhbz;
	private Integer wzahwqcssl;
	private Integer wzahwqcdwsl;
	private Integer wzahwqccsl;
	private Integer wzahwqcdsl;
	private Integer wzahwqcjg;
	private String wzahwqcbz;
	private Integer lhyhsl;
	private Integer lhyhjg;
	private String lhyhbz;
	private Integer creater;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CYhGgjbxx() {
	}

	/** minimal constructor */
	public CYhGgjbxx(Integer dw, Integer hdid, Integer creater,
			Date createtime, Date updatetime) {
		this.dw = dw;
		this.hdid = hdid;
		this.creater = creater;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	/** full constructor */
	public CYhGgjbxx(Integer dw, Integer hdid, Date starttime,
			Double clsl, Integer cljg, String clbz, Integer sjsl, Integer sjjg,
			String sjbz, Integer zzjzwxfsl, Integer zzjzwxfjg, String zzjzwxfbz,
			Integer glmtxfsl, Integer glmtxfjg, String glmtxfbz, Integer xlzxfsl,
			Integer xlzxfjg, String xlzxfbz, Integer hbwhzcsl, Integer hbwhzsl,
			Integer hbwhjg, String hbwhbz, Integer wzahwqcssl,
			Integer wzahwqcdwsl, Integer wzahwqccsl, Integer wzahwqcdsl,
			Integer wzahwqcjg, String wzahwqcbz, Integer lhyhsl, Integer lhyhjg,
			String lhyhbz, Integer creater, Date createtime,
			Date updatetime) {
		this.dw = dw;
		this.hdid = hdid;
		this.starttime = starttime;
		this.clsl = clsl;
		this.cljg = cljg;
		this.clbz = clbz;
		this.sjsl = sjsl;
		this.sjjg = sjjg;
		this.sjbz = sjbz;
		this.zzjzwxfsl = zzjzwxfsl;
		this.zzjzwxfjg = zzjzwxfjg;
		this.zzjzwxfbz = zzjzwxfbz;
		this.glmtxfsl = glmtxfsl;
		this.glmtxfjg = glmtxfjg;
		this.glmtxfbz = glmtxfbz;
		this.xlzxfsl = xlzxfsl;
		this.xlzxfjg = xlzxfjg;
		this.xlzxfbz = xlzxfbz;
		this.hbwhzcsl = hbwhzcsl;
		this.hbwhzsl = hbwhzsl;
		this.hbwhjg = hbwhjg;
		this.hbwhbz = hbwhbz;
		this.wzahwqcssl = wzahwqcssl;
		this.wzahwqcdwsl = wzahwqcdwsl;
		this.wzahwqccsl = wzahwqccsl;
		this.wzahwqcdsl = wzahwqcdsl;
		this.wzahwqcjg = wzahwqcjg;
		this.wzahwqcbz = wzahwqcbz;
		this.lhyhsl = lhyhsl;
		this.lhyhjg = lhyhjg;
		this.lhyhbz = lhyhbz;
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

	public Integer getDw() {
		return this.dw;
	}

	public void setDw(Integer dw) {
		this.dw = dw;
	}

	public Integer getHdid() {
		return this.hdid;
	}

	public void setHdid(Integer hdid) {
		this.hdid = hdid;
	}
    @JSON(format="yyyy-MM-dd")
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Double getClsl() {
		return this.clsl;
	}

	public void setClsl(Double clsl) {
		this.clsl = clsl;
	}

	public Integer getCljg() {
		return this.cljg;
	}

	public void setCljg(Integer cljg) {
		this.cljg = cljg;
	}

	public String getClbz() {
		return this.clbz;
	}

	public void setClbz(String clbz) {
		this.clbz = clbz;
	}

	public Integer getSjsl() {
		return this.sjsl;
	}

	public void setSjsl(Integer sjsl) {
		this.sjsl = sjsl;
	}

	public Integer getSjjg() {
		return this.sjjg;
	}

	public void setSjjg(Integer sjjg) {
		this.sjjg = sjjg;
	}

	public String getSjbz() {
		return this.sjbz;
	}

	public void setSjbz(String sjbz) {
		this.sjbz = sjbz;
	}

	public Integer getZzjzwxfsl() {
		return this.zzjzwxfsl;
	}

	public void setZzjzwxfsl(Integer zzjzwxfsl) {
		this.zzjzwxfsl = zzjzwxfsl;
	}

	public Integer getZzjzwxfjg() {
		return this.zzjzwxfjg;
	}

	public void setZzjzwxfjg(Integer zzjzwxfjg) {
		this.zzjzwxfjg = zzjzwxfjg;
	}

	public String getZzjzwxfbz() {
		return this.zzjzwxfbz;
	}

	public void setZzjzwxfbz(String zzjzwxfbz) {
		this.zzjzwxfbz = zzjzwxfbz;
	}

	public Integer getGlmtxfsl() {
		return this.glmtxfsl;
	}

	public void setGlmtxfsl(Integer glmtxfsl) {
		this.glmtxfsl = glmtxfsl;
	}

	public Integer getGlmtxfjg() {
		return this.glmtxfjg;
	}

	public void setGlmtxfjg(Integer glmtxfjg) {
		this.glmtxfjg = glmtxfjg;
	}

	public String getGlmtxfbz() {
		return this.glmtxfbz;
	}

	public void setGlmtxfbz(String glmtxfbz) {
		this.glmtxfbz = glmtxfbz;
	}

	public Integer getXlzxfsl() {
		return this.xlzxfsl;
	}

	public void setXlzxfsl(Integer xlzxfsl) {
		this.xlzxfsl = xlzxfsl;
	}

	public Integer getXlzxfjg() {
		return this.xlzxfjg;
	}

	public void setXlzxfjg(Integer xlzxfjg) {
		this.xlzxfjg = xlzxfjg;
	}

	public String getXlzxfbz() {
		return this.xlzxfbz;
	}

	public void setXlzxfbz(String xlzxfbz) {
		this.xlzxfbz = xlzxfbz;
	}

	public Integer getHbwhzcsl() {
		return this.hbwhzcsl;
	}

	public void setHbwhzcsl(Integer hbwhzcsl) {
		this.hbwhzcsl = hbwhzcsl;
	}

	public Integer getHbwhzsl() {
		return this.hbwhzsl;
	}

	public void setHbwhzsl(Integer hbwhzsl) {
		this.hbwhzsl = hbwhzsl;
	}

	public Integer getHbwhjg() {
		return this.hbwhjg;
	}

	public void setHbwhjg(Integer hbwhjg) {
		this.hbwhjg = hbwhjg;
	}

	public String getHbwhbz() {
		return this.hbwhbz;
	}

	public void setHbwhbz(String hbwhbz) {
		this.hbwhbz = hbwhbz;
	}

	public Integer getWzahwqcssl() {
		return this.wzahwqcssl;
	}

	public void setWzahwqcssl(Integer wzahwqcssl) {
		this.wzahwqcssl = wzahwqcssl;
	}

	public Integer getWzahwqcdwsl() {
		return this.wzahwqcdwsl;
	}

	public void setWzahwqcdwsl(Integer wzahwqcdwsl) {
		this.wzahwqcdwsl = wzahwqcdwsl;
	}

	public Integer getWzahwqccsl() {
		return this.wzahwqccsl;
	}

	public void setWzahwqccsl(Integer wzahwqccsl) {
		this.wzahwqccsl = wzahwqccsl;
	}

	public Integer getWzahwqcdsl() {
		return this.wzahwqcdsl;
	}

	public void setWzahwqcdsl(Integer wzahwqcdsl) {
		this.wzahwqcdsl = wzahwqcdsl;
	}

	public Integer getWzahwqcjg() {
		return this.wzahwqcjg;
	}

	public void setWzahwqcjg(Integer wzahwqcjg) {
		this.wzahwqcjg = wzahwqcjg;
	}

	public String getWzahwqcbz() {
		return this.wzahwqcbz;
	}

	public void setWzahwqcbz(String wzahwqcbz) {
		this.wzahwqcbz = wzahwqcbz;
	}

	public Integer getLhyhsl() {
		return this.lhyhsl;
	}

	public void setLhyhsl(Integer lhyhsl) {
		this.lhyhsl = lhyhsl;
	}

	public Integer getLhyhjg() {
		return this.lhyhjg;
	}

	public void setLhyhjg(Integer lhyhjg) {
		this.lhyhjg = lhyhjg;
	}

	public String getLhyhbz() {
		return this.lhyhbz;
	}

	public void setLhyhbz(String lhyhbz) {
		this.lhyhbz = lhyhbz;
	}

	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@JSON(format="yyyy-MM-dd")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}