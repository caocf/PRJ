package com.highwaycenter.jd.model;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Transient;

import com.highwaycenter.lxjbxx.model.HJcXzqh;

public class HJdJxdczxx implements java.io.Serializable {

	private static final long serialVersionUID = -7066096057196009832L;
	private String dczbh;	//调查站编号
	private String dczmc;	//调查站名称
	private Integer nf;	    //年份
	private String gljg;	//管理机构
	private HJcXzqh xzqh;   //行政区划
	//private Integer xzqhdm; //行政区划代码
	private String lx;     //	路线
	private HJdDczlx dczlx; //	调查站类型
	//private Integer dczlxbh;	//调查站类型编号
	private Date jzsj;	    //建站时间
	private HJdDcff dcff;      //调查方法
	//private Integer dcffbh;	//调查方法编号
	private String tcyf;	//停测月份
	private float zh;	//桩号(km)
	private float qdzh;    //	起点桩号(km)
	private float zdzh;	//止点桩号(km)
	private String qdmc;	//起点名称
	private String zdmc;	//止点名称
	private String sxmc;	//上行名称
	private String xxmc;	//下行名称
	private float dblc;	//代表里程(km)
	private HJdJsdj jsdj;	//技术等级
	//private Integer jsdjbh;	//技术等级编号
	private String jsdjwy;	//技术等级唯一
	private float yjlc;	//一级里程(km)
	private float ejlc;	//二级里程(km)
	private float sjlc;	//三级里程(km)
	private float sijlc;	//四级里程(km)
	private float gslc;	//高速里程(km)
	private float dwlc;	//等外里程(km)
	private HJdCdtz cdtz;	//车道特征
	// private Integer cdtzbh;	//车道特征编号
	private HJdLmlx lmlx;	//路面类型
	//private Integer lmlxbh;	//路面类型编号
	private float lmkd;	//路面宽度(米)
	private Integer sjss;	//设计时速(km/h)
	private String dm;	//地貌
	private Integer syjtl;	//适应交通量
	private HJdGdfs gdfs;	//供电方式
	//private Integer gdfsbh;	//供电方式编号
	private HJdTxfs txfs;	//通讯方式
	//private Integer txfsbh;	//通讯方式编号
	private HJdGlgn glgn;	//公路功能
	//private Integer glgnbh;	//公路功能编号
	private Integer dcrysl;	//调查人员数量
	private String bz;	//备注
	private String dczzt;	//调查站状态
	private float bzqdzh;	//比重起点桩号(km)
	private float bzzdzh;	//比重止点桩号(km)
	private float bzdblc;	//比重代表里程(km)
	@Transient
	private Integer xzqhdm; //行政区划代码
	@Transient
	private String xzqhmc;//行政区划名称
	@Transient
	private Integer dczlxbh;//调查站类型编号
	@Transient
	private String dczlxmc;//调查站类型名称
	@Transient
	private Integer dcffbh;
	@Transient
	private String dcffmc;//调查方法名称
	@Transient
	private Integer gdfsbh;
	@Transient
	private String gdfsmc;
	@Transient
	private Integer txfsbh;
	@Transient
	private String txfsmc;
	@Transient
	private Integer glgnbh;
	@Transient
	private String glgnmc;
	@Transient
	private String lxjc;   // 路线简称
	@Transient
	private String jd;
	@Transient
	private String wd;
	
	public HJdJxdczxx(){
		
	}
	
	
   public HJdJxdczxx(String dczbh, String dczmc,Integer nf,String gljg,String xzqhmc,String lx,String dczlxmc,Date jzsj,
			String dcffmc,String tcyf, float zh, float qdzh, float zdzh,
			String qdmc, String zdmc,float dblc,String gdfsmc,
			String txfsmc, String glgnmc, Integer dcrysl, String bz,
			String dczzt){  //前台显示字段
		super();
		this.dczbh = dczbh;
		this.dczmc = dczmc;
		this.nf = nf;
		this.gljg = gljg;
		this.xzqhmc = xzqhmc;
		this.lx = lx;
		this.dczlxmc = dczlxmc;
		this.jzsj = jzsj;
		this.dcffmc = dcffmc;
		this.tcyf = tcyf;
		this.zh = zh;
		this.qdzh = qdzh;
		this.zdzh = zdzh;
		this.qdmc = qdmc;
		this.zdmc = zdmc;
		this.dblc = dblc;
		this.gdfsmc = gdfsmc;
		this.txfsmc = txfsmc;
		this.glgnmc= glgnmc;
		this.dcrysl = dcrysl;
		this.bz = bz;
		this.dczzt = dczzt;

	}
	
   public HJdJxdczxx(String dczbh, String dczmc,Integer xzqhdm,String xzqhmc,String lx,Integer dczlxbh, String dczlxmc,
		   Integer dcffbh,String dcffmc,Integer gdfsbh,String gdfsmc,
		   Integer txfsbh,String txfsmc, Integer glgnbh,String glgnmc,float zh, float qdzh, float zdzh){  //列表显示字段
		super();
		this.dczbh = dczbh;
		this.dczmc = dczmc;
		this.xzqhdm = xzqhdm;
		this.xzqhmc = xzqhmc;
		this.lx = lx;
		this.dczlxbh = dczlxbh;
		this.dczlxmc = dczlxmc;
		this.dcffbh = dcffbh;
		this.dcffmc = dcffmc;
		this.gdfsbh = gdfsbh;
		this.gdfsmc = gdfsmc;
		this.txfsbh = txfsbh;
		this.txfsmc = txfsmc;
		this.glgnbh= glgnbh;
		this.glgnmc= glgnmc;
		this.zh = zh;
		this.qdzh = qdzh;
		this.zdzh = zdzh;

	}
	
	public HJdJxdczxx(String dczbh, String dczmc, Integer nf, String gljg,
			HJcXzqh xzqh, String lx, HJdDczlx dczlx, Timestamp jzsj,
			HJdDcff dcff, String tcyf, float zh, float qdzh, float zdzh,
			String qdmc, String zdmc, String sxmc, String xxmc, float dblc,
			HJdJsdj jsdj, String jsdjwy, float yjlc, float ejlc, float sjlc,
			float sijlc, float gslc, float dwlc, HJdCdtz cdtz, HJdLmlx lmlx,
			float lmkd, Integer sjss, String dm, Integer syjtl, HJdGdfs gdfs,
			HJdTxfs txfs, HJdGlgn glgn, Integer dcrysl, String bz,
			String dczzt, float bzqdzh, float bzzdzh, float bzdblc) {
		super();
		this.dczbh = dczbh;
		this.dczmc = dczmc;
		this.nf = nf;
		this.gljg = gljg;
		this.xzqh = xzqh;
		this.lx = lx;
		this.dczlx = dczlx;
		this.jzsj = jzsj;
		this.dcff = dcff;
		this.tcyf = tcyf;
		this.zh = zh;
		this.qdzh = qdzh;
		this.zdzh = zdzh;
		this.qdmc = qdmc;
		this.zdmc = zdmc;
		this.sxmc = sxmc;
		this.xxmc = xxmc;
		this.dblc = dblc;
		this.jsdj = jsdj;
		this.jsdjwy = jsdjwy;
		this.yjlc = yjlc;
		this.ejlc = ejlc;
		this.sjlc = sjlc;
		this.sijlc = sijlc;
		this.gslc = gslc;
		this.dwlc = dwlc;
		this.cdtz = cdtz;
		this.lmlx = lmlx;
		this.lmkd = lmkd;
		this.sjss = sjss;
		this.dm = dm;
		this.syjtl = syjtl;
		this.gdfs = gdfs;
		this.txfs = txfs;
		this.glgn = glgn;
		this.dcrysl = dcrysl;
		this.bz = bz;
		this.dczzt = dczzt;
		this.bzqdzh = bzqdzh;
		this.bzzdzh = bzzdzh;
		this.bzdblc = bzdblc;
	}
	public String getDczbh() {
		return dczbh;
	}
	public void setDczbh(String dczbh) {
		this.dczbh = dczbh;
	}
	public String getDczmc() {
		return dczmc;
	}
	public void setDczmc(String dczmc) {
		this.dczmc = dczmc;
	}
	public Integer getNf() {
		return nf;
	}
	public void setNf(Integer nf) {
		this.nf = nf;
	}
	public String getGljg() {
		return gljg;
	}
	public void setGljg(String gljg) {
		this.gljg = gljg;
	}
	public HJcXzqh getXzqh() {
		return xzqh;
	}
	public void setXzqh(HJcXzqh xzqh) {
		this.xzqh = xzqh;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public HJdDczlx getDczlx() {
		return dczlx;
	}
	public void setDczlx(HJdDczlx dczlx) {
		this.dczlx = dczlx;
	}
	public Date getJzsj() {
		return jzsj;
	}
	public void setJzsj(Date jzsj) {
		this.jzsj = jzsj;
	}
	public HJdDcff getDcff() {
		return dcff;
	}
	public void setDcff(HJdDcff dcff) {
		this.dcff = dcff;
	}
	
	public String getTcyf() {
		return tcyf;
	}
	public void setTcyf(String tcyf) {
		this.tcyf = tcyf;
	}
	public float getZh() {
		return zh;
	}
	public void setZh(float zh) {
		this.zh = zh;
	}
	public float getQdzh() {
		return qdzh;
	}
	public void setQdzh(float qdzh) {
		this.qdzh = qdzh;
	}
	public float getZdzh() {
		return zdzh;
	}
	public void setZdzh(float zdzh) {
		this.zdzh = zdzh;
	}
	public String getQdmc() {
		return qdmc;
	}
	public void setQdmc(String qdmc) {
		this.qdmc = qdmc;
	}
	public String getZdmc() {
		return zdmc;
	}
	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}
	public String getSxmc() {
		return sxmc;
	}
	public void setSxmc(String sxmc) {
		this.sxmc = sxmc;
	}
	public String getXxmc() {
		return xxmc;
	}
	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
	}
	public float getDblc() {
		return dblc;
	}
	public void setDblc(float dblc) {
		this.dblc = dblc;
	}
	public HJdJsdj getJsdj() {
		return jsdj;
	}
	public void setJsdj(HJdJsdj jsdj) {
		this.jsdj = jsdj;
	}
	public String getJsdjwy() {
		return jsdjwy;
	}
	public void setJsdjwy(String jsdjwy) {
		this.jsdjwy = jsdjwy;
	}
	public float getYjlc() {
		return yjlc;
	}
	public void setYjlc(float yjlc) {
		this.yjlc = yjlc;
	}
	public float getEjlc() {
		return ejlc;
	}
	public void setEjlc(float ejlc) {
		this.ejlc = ejlc;
	}
	public float getSjlc() {
		return sjlc;
	}
	public void setSjlc(float sjlc) {
		this.sjlc = sjlc;
	}
	public float getSijlc() {
		return sijlc;
	}
	public void setSijlc(float sijlc) {
		this.sijlc = sijlc;
	}
	public float getGslc() {
		return gslc;
	}
	public void setGslc(float gslc) {
		this.gslc = gslc;
	}
	public float getDwlc() {
		return dwlc;
	}
	public void setDwlc(float dwlc) {
		this.dwlc = dwlc;
	}
	public HJdCdtz getCdtz() {
		return cdtz;
	}
	public void setCdtz(HJdCdtz cdtz) {
		this.cdtz = cdtz;
	}
	public HJdLmlx getLmlx() {
		return lmlx;
	}
	public void setLmlx(HJdLmlx lmlx) {
		this.lmlx = lmlx;
	}
	public float getLmkd() {
		return lmkd;
	}
	public void setLmkd(float lmkd) {
		this.lmkd = lmkd;
	}
	public Integer getSjss() {
		return sjss;
	}
	public void setSjss(Integer sjss) {
		this.sjss = sjss;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public Integer getSyjtl() {
		return syjtl;
	}
	public void setSyjtl(Integer syjtl) {
		this.syjtl = syjtl;
	}
	public HJdGdfs getGdfs() {
		return gdfs;
	}
	public void setGdfs(HJdGdfs gdfs) {
		this.gdfs = gdfs;
	}
	public HJdTxfs getTxfs() {
		return txfs;
	}
	public void setTxfs(HJdTxfs txfs) {
		this.txfs = txfs;
	}
	public HJdGlgn getGlgn() {
		return glgn;
	}
	public void setGlgn(HJdGlgn glgn) {
		this.glgn = glgn;
	}
	public Integer getDcrysl() {
		return dcrysl;
	}
	public void setDcrysl(Integer dcrysl) {
		this.dcrysl = dcrysl;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getDczzt() {
		return dczzt;
	}
	public void setDczzt(String dczzt) {
		this.dczzt = dczzt;
	}
	public float getBzqdzh() {
		return bzqdzh;
	}
	public void setBzqdzh(float bzqdzh) {
		this.bzqdzh = bzqdzh;
	}
	public float getBzzdzh() {
		return bzzdzh;
	}
	public void setBzzdzh(float bzzdzh) {
		this.bzzdzh = bzzdzh;
	}
	public float getBzdblc() {
		return bzdblc;
	}
	public void setBzdblc(float bzdblc) {
		this.bzdblc = bzdblc;
	}


	public Integer getXzqhdm() {
		return xzqhdm;
	}


	public void setXzqhdm(Integer xzqhdm) {
		this.xzqhdm = xzqhdm;
	}


	public String getXzqhmc() {
		return xzqhmc;
	}


	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}


	public Integer getDczlxbh() {
		return dczlxbh;
	}


	public void setDczlxbh(Integer dczlxbh) {
		this.dczlxbh = dczlxbh;
	}


	public String getDczlxmc() {
		return dczlxmc;
	}


	public void setDczlxmc(String dczlxmc) {
		this.dczlxmc = dczlxmc;
	}


	public Integer getDcffbh() {
		return dcffbh;
	}


	public void setDcffbh(Integer dcffbh) {
		this.dcffbh = dcffbh;
	}


	public String getDcffmc() {
		return dcffmc;
	}


	public void setDcffmc(String dcffmc) {
		this.dcffmc = dcffmc;
	}


	public Integer getGdfsbh() {
		return gdfsbh;
	}


	public void setGdfsbh(Integer gdfsbh) {
		this.gdfsbh = gdfsbh;
	}


	public String getGdfsmc() {
		return gdfsmc;
	}


	public void setGdfsmc(String gdfsmc) {
		this.gdfsmc = gdfsmc;
	}


	public Integer getTxfsbh() {
		return txfsbh;
	}


	public void setTxfsbh(Integer txfsbh) {
		this.txfsbh = txfsbh;
	}


	public String getTxfsmc() {
		return txfsmc;
	}


	public void setTxfsmc(String txfsmc) {
		this.txfsmc = txfsmc;
	}


	public Integer getGlgnbh() {
		return glgnbh;
	}


	public void setGlgnbh(Integer glgnbh) {
		this.glgnbh = glgnbh;
	}


	public String getGlgnmc() {
		return glgnmc;
	}


	public void setGlgnmc(String glgnmc) {
		this.glgnmc = glgnmc;
	}


	public String getLxjc() {
		return lxjc;
	}


	public void setLxjc(String lxjc) {
		this.lxjc = lxjc;
	}


	public String getJd() {
		return jd;
	}


	public void setJd(String jd) {
		this.jd = jd;
	}


	public String getWd() {
		return wd;
	}


	public void setWd(String wd) {
		this.wd = wd;
	}


}
