package com.sts.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.sts.company.dao.CompanyDao;
import com.sts.company.model.Company;

public class CompanyService {
	/*----------------------dao类-------------------------------*/
	CompanyDao companyDao;
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	/*--------------------------service 内容----------------------------*/
	
	/**
	 * 
	 * @return
	 */
	
	public List<Company> findCompany(String selectword){
		List<Company> companylist=new ArrayList<Company>();
		String selectString="";
		if(!StringUtils.isEmpty(selectword)){
			selectString="where a.yhmc like '%"+selectword+"%'";
		}
		String sql="";
		List<?> sqlList;
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_chuzuchejyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(1);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_gongjiaojyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(2);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_huoyunjyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(3);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_jiapeijyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(4);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_jyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(5);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_keyunjyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(6);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_weixiujyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(7);
				companylist.add(company);
			}
		}
		sql="select a.yhid,a.yhmc,a.yhdz,a.fddbr,a.jyxkzh from z_yh_weiyunjyyhxx a ";
		sql+=selectString;
		sqlList= this.companyDao.querySQL(sql);
		if(sqlList!=null){
			for (int i = 0; i < sqlList.size(); i++) {
				Company company=new Company();
				Map data= (Map) sqlList.get(i);
				String id=String.valueOf(data.get("YHID"));
				String name=String.valueOf (data.get("YHMC"));
				String address=String.valueOf (data.get("YHDZ"));
				String fddbr=String.valueOf (data.get("FDDBR"));
				String hm=String.valueOf (data.get("JYXKZH"));
				company.setId(id);
				company.setCompanyName(name);
				company.setCompanyAddress(address);
				company.setCompanyPerson(fddbr);
				company.setCompanyNo(hm);
				company.setCompanyType(8);
				companylist.add(company);
			}
		}
		return companylist;
	}
	public List<?> oneCompany(String id,int type){
		String sql="";
		//330000000002177397
		//330000000002177400
		if(type==1){
			sql="select * from z_yh_chuzuchejyyhxx a where  a.yhid="+id;
		}
		if(type==2){
			sql="select * from z_yh_gongjiaojyyhxx a where  a.yhid="+id;
		}
		if(type==3){
			sql="select * from z_yh_huoyunjyyhxx a where  a.yhid="+id;
		}
		if(type==4){
			sql="select * from z_yh_jiapeijyyhxx a where  a.yhid="+id;
		}
		if(type==5){
			sql="select * from z_yh_jyyhxx a where  a.yhid="+id;
		}
		if(type==6){
			sql="select * from z_yh_keyunjyyhxx a where  a.yhid="+id;
		}
		if(type==7){
			sql="select * from z_yh_weixiujyyhxx a where  a.yhid="+id;
		}
		if(type==8){
			sql="select * from z_yh_weiyunjyyhxx a where  a.yhid="+id;
		}
		return this.companyDao.querySQL(sql);
	}
}
