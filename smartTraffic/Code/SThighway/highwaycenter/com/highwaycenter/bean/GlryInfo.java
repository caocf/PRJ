package com.highwaycenter.bean;

import java.util.List;

import com.highwaycenter.gljg.model.HJcRybm;


public class GlryInfo   implements java.io.Serializable {

	private static final long serialVersionUID = -3831679217411468053L;
	
	private Integer rybh;     //人员编号
	
	private String bmdm;  //人员管理部门编号
	private String bmmc ;  //人员管理部门名称
	
	private String ryxm;      //人员姓名
	private String xmqp;      //姓名全拼
	private String xmpyszm;   //姓名拼音首字母
	private String sjch;      //手机长号
	private String sjdh;     //手机短号
	private String zw;      //职位
	private String bgsdh;   //办公室电话
	
	private Integer jsbh;     //角色编号
	private String jsmc;  // 角色名称
	
	private String headPicture;  //头像路径
	private String headsmall;
	private String fjgdm;  // 父机构代码
	
	private List<HJcRybm> rybmlist;
	public GlryInfo(){
		
	}
	
public GlryInfo(Integer rybh,String bmdm,String bmmc, String ryxm,String xmqp,String xmpyszm,
		String sjch,String sjdh,String zw,String bgsdh,Integer jsbh,String jsmc){
	     this.rybh = rybh;
	     this.bmdm = bmdm;
	     this.bmmc = bmmc;
	     this.ryxm= ryxm;
	     this.xmqp = xmqp;
	     this.xmpyszm = xmpyszm;
	     this.sjch = sjch;
	     this.sjdh = sjdh;
	     this.zw = zw;
	     this.bgsdh = bgsdh;
	     this.jsbh = jsbh;
	     this.jsmc = jsmc;

	}

public GlryInfo(Integer rybh, String ryxm,String xmqp,String xmpyszm,
		String sjch,String sjdh,String zw,String bgsdh,Integer jsbh,String jsmc){
	     this.rybh = rybh;
	     this.ryxm= ryxm;
	     this.xmqp = xmqp;
	     this.xmpyszm = xmpyszm;
	     this.sjch = sjch;
	     this.sjdh = sjdh;
	     this.zw = zw;
	     this.bgsdh = bgsdh;
	     this.jsbh = jsbh;
	     this.jsmc = jsmc;

	}

     public GlryInfo(Integer rybh,String bmdm,String bmmc, String ryxm,String xmqp,String xmpyszm,
		String sjch,String sjdh,String zw,String bgsdh){
	     this.rybh = rybh;
	     this.bmdm = bmdm;
	     this.bmmc = bmmc;
	     this.ryxm= ryxm;
	     this.xmqp = xmqp;
	     this.xmpyszm = xmpyszm;
	     this.sjch = sjch;
	     this.sjdh = sjdh;
	     this.zw = zw;
	     this.bgsdh = bgsdh;
	}
     
     public GlryInfo(Integer rybh, String ryxm,String xmqp,String xmpyszm,
    			String sjch,String sjdh,String zw,String bgsdh){
    		     this.rybh = rybh;
    		     this.ryxm= ryxm;
    		     this.xmqp = xmqp;
    		     this.xmpyszm = xmpyszm;
    		     this.sjch = sjch;
    		     this.sjdh = sjdh;
    		     this.zw = zw;
    		     this.bgsdh = bgsdh;
    		}
     
     
     
	
	public Integer getRybh() {
		return rybh;
	}


	public String getBmmc() {
		return bmmc;
	}

	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}

	public String getRyxm() {
		return ryxm;
	}
	public void setRyxm(String ryxm) {
		this.ryxm = ryxm;
	}
	public String getXmqp() {
		return xmqp;
	}
	public void setXmqp(String xmqp) {
		this.xmqp = xmqp;
	}
	public String getXmpyszm() {
		return xmpyszm;
	}
	public void setXmpyszm(String xmpyszm) {
		this.xmpyszm = xmpyszm;
	}
	public String getSjch() {
		return sjch;
	}
	public void setSjch(String sjch) {
		this.sjch = sjch;
	}
	public String getSjdh() {
		return sjdh;
	}
	public void setSjdh(String sjdh) {
		this.sjdh = sjdh;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getBgsdh() {
		return bgsdh;
	}
	public void setBgsdh(String bgsdh) {
		this.bgsdh = bgsdh;
	}

	
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

public Integer getJsbh() {
	return jsbh;
}

public void setJsbh(Integer jsbh) {
	this.jsbh = jsbh;
}

public void setRybh(Integer rybh) {
	this.rybh = rybh;
}

public String getHeadPicture() {
	return headPicture;
}

public void setHeadPicture(String headPicture) {
	this.headPicture = headPicture;
}

public String getFjgdm() {
	return fjgdm;
}

public void setFjgdm(String fjgdm) {
	this.fjgdm = fjgdm;
}

public String getHeadsmall() {
	return headsmall;
}

public void setHeadsmall(String headsmall) {
	this.headsmall = headsmall;
}

public List<HJcRybm> getRybmlist() {
	return rybmlist;
}

public void setRybmlist(List<HJcRybm> rybmlist) {
	this.rybmlist = rybmlist;
}


}
