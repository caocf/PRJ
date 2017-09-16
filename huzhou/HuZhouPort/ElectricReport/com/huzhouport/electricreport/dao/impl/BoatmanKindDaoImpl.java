package com.huzhouport.electricreport.dao.impl;

import java.util.List;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.electricreport.dao.BoatmanKindDao;
import com.huzhouport.electricreport.model.BoatmanKind;


public class BoatmanKindDaoImpl extends BaseDaoImpl<BoatmanKind> implements BoatmanKindDao {
	// 数据字典列表
	public List<?> ShowBoatmanKindList(BoatmanKind bk) throws Exception{
		String hql="from BoatmanKind bk where bk.isDelete='N' order by bk.boatmanKindID asc";
		if(bk.getBoatmanKindName()!=null&&!bk.getBoatmanKindName().equals("null")){
			 hql="from BoatmanKind bk where bk.isDelete='N' and bk.boatmanKindName like '%"+bk.getBoatmanKindName()+"%' order by bk.boatmanKindID asc";
		}
		return this.hibernateTemplate.find(hql);
	}
	// 删除
	public boolean DeleteBoatmanKind(BoatmanKind bk) throws Exception{
		String hql="update BoatmanKind bk set bk.isDelete='Y' where bk.boatmanKindID="+bk.getBoatmanKindID();
		this.hibernateTemplate.bulkUpdate(hql);
		return true;
	}
	// 修改
	public boolean UpdateBoatmanKind(BoatmanKind bk) throws Exception{
		Boolean check=true;
		int n=this.hibernateTemplate.find("from BoatmanKind bk where bk.boatmanKindID!="+bk.getBoatmanKindID()+" and bk.isDelete='N' and bk.boatmanKindName='"+bk.getBoatmanKindName()+"'").size();
		if(n!=0) {
			check=false;
		}else{
			String hql="update BoatmanKind bk set bk.boatmanKindName='"+bk.getBoatmanKindName()+"' where bk.boatmanKindID="+bk.getBoatmanKindID();
			this.hibernateTemplate.bulkUpdate(hql);
		}	
		return check;
	}
	// 新增
	public boolean AddBoatmanKind(BoatmanKind bk) throws Exception{
		Boolean check=true;
		int n=this.hibernateTemplate.find("from BoatmanKind bk where bk.isDelete='N' and bk.boatmanKindName='"+bk.getBoatmanKindName()+"'").size();
		if(n!=0) {
			check=false;
		}else{
			this.hibernateTemplate.save(bk);
		}	
		return check;
	}
	public int CountBoatmanKindListByPage(String condition) throws Exception {
		String hql = "select count(*) from BoatmanKind bk where bk.isDelete='N' ";
		if (condition != "") {
			hql += "and ( " + condition + " )";
		}
		return this.countEForeignKey(null,hql);
	}
	public List<?> ShowBoatmanKindListByPage(String condition, String sequence,
			int startSet, int maxSet) throws Exception {
		String hql = "select bk from BoatmanKind bk where bk.isDelete='N' ";
		if (condition != "") {
			hql += "and ( " + condition + " )";
		}
		hql += " order by bk.boatmanKindID asc";
		return this.queryqueryEForeignKey(null, hql, startSet, maxSet);
	}
}
