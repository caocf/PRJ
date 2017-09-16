package com.huzhouport.electricreport.dao.impl;

import java.util.List;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electricreport.dao.BoatcardDao;
import com.huzhouport.electricreport.model.Certificate;


public class BoatcardDaoImpl extends BaseDaoImpl<Certificate> implements BoatcardDao {
	// 数据字典列表
	/*public List<?> ShowBoatmanKindList(BoatmanKind bk) throws Exception{
		String hql="from BoatmanKind bk where bk.isDelete='N' order by bk.boatmanKindID asc";
		if(bk.getBoatmanKindName()!=null&&!bk.getBoatmanKindName().equals("null")){
			 hql="from BoatmanKind bk where bk.isDelete='N' and bk.boatmanKindName like '%"+bk.getBoatmanKindName()+"%' order by bk.boatmanKindID asc";
		}
		return this.hibernateTemplate.find(hql);
	}*/
	// 删除
	public boolean DeleteBoatCard(Certificate certificate) throws Exception{
		String hql="update Certificate cf set cf.isDelete='Y' where cf.cardID="+certificate.getCardID();
		this.hibernateTemplate.bulkUpdate(hql);
		return true;
	}
	// 修改
	public boolean UpdateBoatCard(Certificate certificate) throws Exception{
		Boolean check=true;
		int n=this.hibernateTemplate.find("from Certificate cf where cf.cardID!="+certificate.getCardID()+" and cf.isDelete='N' and cf.cardName='"+certificate.getCardName()+"'").size();
		if(n!=0) {
			check=false;
		}else{
			String hql="update Certificate cf set cf.cardName='"+certificate.getCardName()+"' where cf.cardID="+certificate.getCardID();
			this.hibernateTemplate.bulkUpdate(hql);
		}	
		return check;
	}
	// 新增
	public boolean AddBoatCard(Certificate certificate) throws Exception{
		Boolean check=true;
		int n=this.hibernateTemplate.find("from Certificate cf where cf.isDelete='N' and cf.cardName='"+certificate.getCardName()+"'").size();
		if(n!=0) {
			check=false;
		}else{
			this.hibernateTemplate.save(certificate);
		}	
		return check;
	}
	public int CountBoatcardListByPage(Certificate certificate) throws Exception {
		String hql=null;
		
		hql = "select count(*) from Certificate cf where cf.isDelete='N' ";
		if(certificate.getCardName()!=null&&!certificate.getCardName().equals("")){
			hql+=" and cf.cardName like '%"+certificate.getCardName()+"%'";
		}
		return this.countEForeignKey(null,hql);
	}
	public List<?> ShowBoatcardListByPage(Certificate certificate,int startSet, int maxSet) throws Exception {
		String hql = null;
		hql = "from Certificate cf where cf.isDelete='N' ";
		if(certificate.getCardName()!=null&&!certificate.getCardName().equals("")){
			hql+=" and cf.cardName like '%"+certificate.getCardName()+"%'";
		}
		hql += " order by cf.cardID asc";
		return this.queryqueryEForeignKey(null, hql, startSet, maxSet);
	}
	public List<?> ShowBoatcardList(Certificate certificate) throws Exception {
		String hql = null;
		hql = "from Certificate cf where cf.isDelete='N' ";
		return this.hibernateTemplate.find(hql);
	}
}
