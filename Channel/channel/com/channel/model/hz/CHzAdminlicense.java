package com.channel.model.hz;


import org.apache.struts2.json.annotations.JSON;

import java.util.Date;

/**
 * CHzAdminlicense entity. @author MyEclipse Persistence Tools
 */

public class CHzAdminlicense implements java.io.Serializable {

	// Fields

	private Integer id;
	private String xmmc;
	private String xmwz;
	private Integer xmlx;
	private String qljgxs;
	private Double qlthjk;
	private Double qlthjg;
	private Double qlyhdjj;
	private String lxlb;
	private Double lxkj;
	private Double lxthjg;
	private Double lxtjlajl;
	private String gxlb;
	private Double sdgxdbg;
	private Double sdmssd;
	private Double sdrtklajl;
	private Double qpskscaxjl;
	private Double qpskzgdbg;
	private Double qpskhxls;
	private Double qpskhlls;
	private Double zbthdj;
	private Double zbzscd;
	private Double zbzmtsjg;
	private String gcxt;
	private Double sjzgthsw;
	private Double sjzdthsw;
	private Integer jzwxz;
	private String sqdw;
	private String sqdwjbr;
	private String dz;
	private String lxdh;
	private String xmsjdw;
	private String xksldw;
	private String xkbh;
	private Date xkrq;
	private Integer creater;
	private Date createtime;
	private Date updatetime;

	// Constructors

	/** default constructor */
	public CHzAdminlicense() {
	}

	/** minimal constructor */
	public CHzAdminlicense(String xmmc, String xmwz, Integer xmlx,
			String xksldw, String xkbh, Date xkrq) {
		this.xmmc = xmmc;
		this.xmwz = xmwz;
		this.xmlx = xmlx;
		this.xksldw = xksldw;
		this.xkbh = xkbh;
		this.xkrq = xkrq;
	}

	public CHzAdminlicense(String lxlb, Integer id, String xmmc, String xmwz, Integer xmlx, String qljgxs, Double qlthjk, Double qlthjg, Double qlyhdjj, Double lxkj, Double lxthjg, Double lxtjlajl, String gxlb, Double sdgxdbg, Double sdmssd, Double sdrtklajl, Double qpskscaxjl, Double qpskzgdbg, Double qpskhxls, Double qpskhlls, Double zbthdj, Double zbzscd, Double zbzmtsjg, String gcxt, Double sjzgthsw, Double sjzdthsw, Integer jzwxz, String sqdw, String sqdwjbr, String dz, String lxdh, String xmsjdw, String xksldw, String xkbh, Date xkrq, Integer creater, Date createtime, Date updatetime) {
		this.lxlb = lxlb;
		this.id = id;
		this.xmmc = xmmc;
		this.xmwz = xmwz;
		this.xmlx = xmlx;
		this.qljgxs = qljgxs;
		this.qlthjk = qlthjk;
		this.qlthjg = qlthjg;
		this.qlyhdjj = qlyhdjj;
		this.lxkj = lxkj;
		this.lxthjg = lxthjg;
		this.lxtjlajl = lxtjlajl;
		this.gxlb = gxlb;
		this.sdgxdbg = sdgxdbg;
		this.sdmssd = sdmssd;
		this.sdrtklajl = sdrtklajl;
		this.qpskscaxjl = qpskscaxjl;
		this.qpskzgdbg = qpskzgdbg;
		this.qpskhxls = qpskhxls;
		this.qpskhlls = qpskhlls;
		this.zbthdj = zbthdj;
		this.zbzscd = zbzscd;
		this.zbzmtsjg = zbzmtsjg;
		this.gcxt = gcxt;
		this.sjzgthsw = sjzgthsw;
		this.sjzdthsw = sjzdthsw;
		this.jzwxz = jzwxz;
		this.sqdw = sqdw;
		this.sqdwjbr = sqdwjbr;
		this.dz = dz;
		this.lxdh = lxdh;
		this.xmsjdw = xmsjdw;
		this.xksldw = xksldw;
		this.xkbh = xkbh;
		this.xkrq = xkrq;
		this.creater = creater;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public CHzAdminlicense(Double qlthjk, String xmmc, String xmwz, Integer xmlx, String qljgxs, Double qlthjg, Double qlyhdjj, String lxlb, Double lxkj, Double lxthjg, Double lxtjlajl, String gxlb, Double sdgxdbg, Double sdmssd, Double sdrtklajl, Double qpskscaxjl, Double qpskzgdbg, Double qpskhxls, Double qpskhlls, Double zbthdj, Double zbzscd, Double zbzmtsjg, String gcxt, Double sjzgthsw, Double sjzdthsw, Integer jzwxz, String sqdw, String sqdwjbr, String dz, String lxdh, String xmsjdw, String xksldw, String xkbh, Date xkrq, Integer creater, Date createtime, Date updatetime) {
		this.qlthjk = qlthjk;
		this.xmmc = xmmc;
		this.xmwz = xmwz;
		this.xmlx = xmlx;
		this.qljgxs = qljgxs;
		this.qlthjg = qlthjg;
		this.qlyhdjj = qlyhdjj;
		this.lxlb = lxlb;
		this.lxkj = lxkj;
		this.lxthjg = lxthjg;
		this.lxtjlajl = lxtjlajl;
		this.gxlb = gxlb;
		this.sdgxdbg = sdgxdbg;
		this.sdmssd = sdmssd;
		this.sdrtklajl = sdrtklajl;
		this.qpskscaxjl = qpskscaxjl;
		this.qpskzgdbg = qpskzgdbg;
		this.qpskhxls = qpskhxls;
		this.qpskhlls = qpskhlls;
		this.zbthdj = zbthdj;
		this.zbzscd = zbzscd;
		this.zbzmtsjg = zbzmtsjg;
		this.gcxt = gcxt;
		this.sjzgthsw = sjzgthsw;
		this.sjzdthsw = sjzdthsw;
		this.jzwxz = jzwxz;
		this.sqdw = sqdw;
		this.sqdwjbr = sqdwjbr;
		this.dz = dz;
		this.lxdh = lxdh;
		this.xmsjdw = xmsjdw;
		this.xksldw = xksldw;
		this.xkbh = xkbh;
		this.xkrq = xkrq;
		this.creater = creater;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	public String getQljgxs() {
		return qljgxs;
	}

	public void setQljgxs(String qljgxs) {
		this.qljgxs = qljgxs;
	}

	public String getLxlb() {
		return lxlb;
	}

	public void setLxlb(String lxlb) {
		this.lxlb = lxlb;
	}

	public String getGxlb() {
		return gxlb;
	}

	public void setGxlb(String gxlb) {
		this.gxlb = gxlb;
	}

	public Double getSjzgthsw() {
		return sjzgthsw;
	}

	public void setSjzgthsw(Double sjzgthsw) {
		this.sjzgthsw = sjzgthsw;
	}

	public Double getSjzdthsw() {
		return sjzdthsw;
	}

	public void setSjzdthsw(Double sjzdthsw) {
		this.sjzdthsw = sjzdthsw;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getXmmc() {
		return this.xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getXmwz() {
		return this.xmwz;
	}

	public void setXmwz(String xmwz) {
		this.xmwz = xmwz;
	}

	public Integer getXmlx() {
		return this.xmlx;
	}

	public void setXmlx(Integer xmlx) {
		this.xmlx = xmlx;
	}

	public Double getQlthjk() {
		return qlthjk;
	}

	public void setQlthjk(Double qlthjk) {
		this.qlthjk = qlthjk;
	}

	public Double getQlthjg() {
		return qlthjg;
	}

	public void setQlthjg(Double qlthjg) {
		this.qlthjg = qlthjg;
	}

	public Double getQlyhdjj() {
		return qlyhdjj;
	}

	public void setQlyhdjj(Double qlyhdjj) {
		this.qlyhdjj = qlyhdjj;
	}

	public Double getLxkj() {
		return this.lxkj;
	}

	public void setLxkj(Double lxkj) {
		this.lxkj = lxkj;
	}

	public Double getLxthjg() {
		return this.lxthjg;
	}

	public void setLxthjg(Double lxthjg) {
		this.lxthjg = lxthjg;
	}

	public Double getLxtjlajl() {
		return this.lxtjlajl;
	}

	public void setLxtjlajl(Double lxtjlajl) {
		this.lxtjlajl = lxtjlajl;
	}

	public Double getSdgxdbg() {
		return this.sdgxdbg;
	}

	public void setSdgxdbg(Double sdgxdbg) {
		this.sdgxdbg = sdgxdbg;
	}

	public Double getSdmssd() {
		return this.sdmssd;
	}

	public void setSdmssd(Double sdmssd) {
		this.sdmssd = sdmssd;
	}

	public Double getSdrtklajl() {
		return this.sdrtklajl;
	}

	public void setSdrtklajl(Double sdrtklajl) {
		this.sdrtklajl = sdrtklajl;
	}

	public Double getQpskscaxjl() {
		return this.qpskscaxjl;
	}

	public void setQpskscaxjl(Double qpskscaxjl) {
		this.qpskscaxjl = qpskscaxjl;
	}

	public Double getQpskzgdbg() {
		return this.qpskzgdbg;
	}

	public void setQpskzgdbg(Double qpskzgdbg) {
		this.qpskzgdbg = qpskzgdbg;
	}

	public Double getQpskhxls() {
		return this.qpskhxls;
	}

	public void setQpskhxls(Double qpskhxls) {
		this.qpskhxls = qpskhxls;
	}

	public Double getQpskhlls() {
		return this.qpskhlls;
	}

	public void setQpskhlls(Double qpskhlls) {
		this.qpskhlls = qpskhlls;
	}

	public Double getZbthdj() {
		return this.zbthdj;
	}

	public void setZbthdj(Double zbthdj) {
		this.zbthdj = zbthdj;
	}

	public Double getZbzscd() {
		return this.zbzscd;
	}

	public void setZbzscd(Double zbzscd) {
		this.zbzscd = zbzscd;
	}

	public Double getZbzmtsjg() {
		return this.zbzmtsjg;
	}

	public void setZbzmtsjg(Double zbzmtsjg) {
		this.zbzmtsjg = zbzmtsjg;
	}

	public String getGcxt() {
		return this.gcxt;
	}

	public void setGcxt(String gcxt) {
		this.gcxt = gcxt;
	}

	public Integer getJzwxz() {
		return this.jzwxz;
	}

	public void setJzwxz(Integer jzwxz) {
		this.jzwxz = jzwxz;
	}

	public String getSqdw() {
		return this.sqdw;
	}

	public void setSqdw(String sqdw) {
		this.sqdw = sqdw;
	}

	public String getSqdwjbr() {
		return this.sqdwjbr;
	}

	public void setSqdwjbr(String sqdwjbr) {
		this.sqdwjbr = sqdwjbr;
	}

	public String getDz() {
		return this.dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getXmsjdw() {
		return this.xmsjdw;
	}

	public void setXmsjdw(String xmsjdw) {
		this.xmsjdw = xmsjdw;
	}

	public String getXksldw() {
		return this.xksldw;
	}

	public void setXksldw(String xksldw) {
		this.xksldw = xksldw;
	}

	public String getXkbh() {
		return this.xkbh;
	}

	public void setXkbh(String xkbh) {
		this.xkbh = xkbh;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getXkrq() {
		return this.xkrq;
	}

	public void setXkrq(Date xkrq) {
		this.xkrq = xkrq;
	}

	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}
	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
    @JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}