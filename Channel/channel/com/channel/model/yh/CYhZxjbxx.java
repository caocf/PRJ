package com.channel.model.yh;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * CYhZxjbxx entity. @author MyEclipse Persistence Tools
 */

public class CYhZxjbxx implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer dw;
	private Date starttime;
	private Double clsl;
	private Integer clje;
	private String clbz;
	private Integer ahsjsl;
	private Integer ahsjje;
	private String ahsjbz;
	private Integer zzjzwxfsl;
	private Integer zzjzwxfje;
	private String zzjzwxfbz;
	private Integer wxglmtsl;
	private Integer wxglmtje;
	private String wxglmtbz;
	private Integer hbwhzsl;
	private Integer hbwhzcsl;
	private Integer hbwhje;
	private String hbwhbz;
	private Integer dlccssl;
	private Integer dlccdwsl;
	private Integer dlccje;
	private String dlccbz;
	private Integer qtqzcsl;
	private Integer qtqzdsl;
	private Integer qtqzje;
	private String qtqzbz;
	private Integer qtwxgcsl;
	private Integer qtwxgcje;
	private String qtwxgcbz;
	private Integer creater;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CYhZxjbxx() {
	}

	/** minimal constructor */
	public CYhZxjbxx(Integer dw, Integer creater, Date createtime,
			Date updatetime) {
		this.dw = dw;
		this.creater = creater;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	/** full constructor */
	public CYhZxjbxx(Integer dw, Date starttime, Double clsl, Integer clje,
			String clbz, Integer ahsjsl, Integer ahsjje, String ahsjbz,
			Integer zzjzwxfsl, Integer zzjzwxfje, String zzjzwxfbz,
			Integer wxglmtsl, Integer wxglmtje, String wxglmtbz, Integer hbwhzsl,
			Integer hbwhzcsl, Integer hbwhje, String hbwhbz, Integer dlccssl,
			Integer dlccdwsl, Integer dlccje, String dlccbz, Integer qtqzcsl,
			Integer qtqzdsl, Integer qtqzje, String qtqzbz, Integer qtwxgcsl,
			Integer qtwxgcje, String qtwxgcbz, Integer creater, Date createtime,
			Date updatetime) {
		this.dw = dw;
		this.starttime = starttime;
		this.clsl = clsl;
		this.clje = clje;
		this.clbz = clbz;
		this.ahsjsl = ahsjsl;
		this.ahsjje = ahsjje;
		this.ahsjbz = ahsjbz;
		this.zzjzwxfsl = zzjzwxfsl;
		this.zzjzwxfje = zzjzwxfje;
		this.zzjzwxfbz = zzjzwxfbz;
		this.wxglmtsl = wxglmtsl;
		this.wxglmtje = wxglmtje;
		this.wxglmtbz = wxglmtbz;
		this.hbwhzsl = hbwhzsl;
		this.hbwhzcsl = hbwhzcsl;
		this.hbwhje = hbwhje;
		this.hbwhbz = hbwhbz;
		this.dlccssl = dlccssl;
		this.dlccdwsl = dlccdwsl;
		this.dlccje = dlccje;
		this.dlccbz = dlccbz;
		this.qtqzcsl = qtqzcsl;
		this.qtqzdsl = qtqzdsl;
		this.qtqzje = qtqzje;
		this.qtqzbz = qtqzbz;
		this.qtwxgcsl = qtwxgcsl;
		this.qtwxgcje = qtwxgcje;
		this.qtwxgcbz = qtwxgcbz;
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

	@JSON(format = "yyyy-MM-dd")
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

	public Integer getClje() {
		return this.clje;
	}

	public void setClje(Integer clje) {
		this.clje = clje;
	}

	public String getClbz() {
		return this.clbz;
	}

	public void setClbz(String clbz) {
		this.clbz = clbz;
	}

	public Integer getAhsjsl() {
		return this.ahsjsl;
	}

	public void setAhsjsl(Integer ahsjsl) {
		this.ahsjsl = ahsjsl;
	}

	public Integer getAhsjje() {
		return this.ahsjje;
	}

	public void setAhsjje(Integer ahsjje) {
		this.ahsjje = ahsjje;
	}

	public String getAhsjbz() {
		return this.ahsjbz;
	}

	public void setAhsjbz(String ahsjbz) {
		this.ahsjbz = ahsjbz;
	}

	public Integer getZzjzwxfsl() {
		return this.zzjzwxfsl;
	}

	public void setZzjzwxfsl(Integer zzjzwxfsl) {
		this.zzjzwxfsl = zzjzwxfsl;
	}

	public Integer getZzjzwxfje() {
		return this.zzjzwxfje;
	}

	public void setZzjzwxfje(Integer zzjzwxfje) {
		this.zzjzwxfje = zzjzwxfje;
	}

	public String getZzjzwxfbz() {
		return this.zzjzwxfbz;
	}

	public void setZzjzwxfbz(String zzjzwxfbz) {
		this.zzjzwxfbz = zzjzwxfbz;
	}

	public Integer getWxglmtsl() {
		return this.wxglmtsl;
	}

	public void setWxglmtsl(Integer wxglmtsl) {
		this.wxglmtsl = wxglmtsl;
	}

	public Integer getWxglmtje() {
		return this.wxglmtje;
	}

	public void setWxglmtje(Integer wxglmtje) {
		this.wxglmtje = wxglmtje;
	}

	public String getWxglmtbz() {
		return this.wxglmtbz;
	}

	public void setWxglmtbz(String wxglmtbz) {
		this.wxglmtbz = wxglmtbz;
	}

	public Integer getHbwhzsl() {
		return this.hbwhzsl;
	}

	public void setHbwhzsl(Integer hbwhzsl) {
		this.hbwhzsl = hbwhzsl;
	}

	public Integer getHbwhzcsl() {
		return this.hbwhzcsl;
	}

	public void setHbwhzcsl(Integer hbwhzcsl) {
		this.hbwhzcsl = hbwhzcsl;
	}

	public Integer getHbwhje() {
		return this.hbwhje;
	}

	public void setHbwhje(Integer hbwhje) {
		this.hbwhje = hbwhje;
	}

	public String getHbwhbz() {
		return this.hbwhbz;
	}

	public void setHbwhbz(String hbwhbz) {
		this.hbwhbz = hbwhbz;
	}

	public Integer getDlccssl() {
		return this.dlccssl;
	}

	public void setDlccssl(Integer dlccssl) {
		this.dlccssl = dlccssl;
	}

	public Integer getDlccdwsl() {
		return this.dlccdwsl;
	}

	public void setDlccdwsl(Integer dlccdwsl) {
		this.dlccdwsl = dlccdwsl;
	}

	public Integer getDlccje() {
		return this.dlccje;
	}

	public void setDlccje(Integer dlccje) {
		this.dlccje = dlccje;
	}

	public String getDlccbz() {
		return this.dlccbz;
	}

	public void setDlccbz(String dlccbz) {
		this.dlccbz = dlccbz;
	}

	public Integer getQtqzcsl() {
		return this.qtqzcsl;
	}

	public void setQtqzcsl(Integer qtqzcsl) {
		this.qtqzcsl = qtqzcsl;
	}

	public Integer getQtqzdsl() {
		return this.qtqzdsl;
	}

	public void setQtqzdsl(Integer qtqzdsl) {
		this.qtqzdsl = qtqzdsl;
	}

	public Integer getQtqzje() {
		return this.qtqzje;
	}

	public void setQtqzje(Integer qtqzje) {
		this.qtqzje = qtqzje;
	}

	public String getQtqzbz() {
		return this.qtqzbz;
	}

	public void setQtqzbz(String qtqzbz) {
		this.qtqzbz = qtqzbz;
	}

	public Integer getQtwxgcsl() {
		return this.qtwxgcsl;
	}

	public void setQtwxgcsl(Integer qtwxgcsl) {
		this.qtwxgcsl = qtwxgcsl;
	}

	public Integer getQtwxgcje() {
		return this.qtwxgcje;
	}

	public void setQtwxgcje(Integer qtwxgcje) {
		this.qtwxgcje = qtwxgcje;
	}

	public String getQtwxgcbz() {
		return this.qtwxgcbz;
	}

	public void setQtwxgcbz(String qtwxgcbz) {
		this.qtwxgcbz = qtwxgcbz;
	}

	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}