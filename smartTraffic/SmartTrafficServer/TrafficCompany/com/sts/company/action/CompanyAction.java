package com.sts.company.action;

import java.util.List;

import com.sts.company.model.Company;
import com.sts.company.service.CompanyService;

public class CompanyAction {
	private String id;
	private int page;
	private int total;
	private String selectword;
	private int companytype;
	List<Company> companylist;
	List<?> oneCompany;
	CompanyService companyService;
	
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public String findCompany(){
		try {
			companylist=companyService.findCompany(selectword);
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		if(companylist.size()<=10){
			total=1;
		}else{
			total=companylist.size()/10;
			int startlist=(page-1)*10;
			companylist=companylist.subList(startlist, startlist+10);
		}
		return "success";
	}
	public String findOneCompany(){
		oneCompany=companyService.oneCompany(id,companytype);
		return "success";
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSelectword() {
		return selectword;
	}
	public void setSelectword(String selectword) {
		this.selectword = selectword;
	}
	public int getCompanytype() {
		return companytype;
	}
	public void setCompanytype(int companytype) {
		this.companytype = companytype;
	}
	public List<Company> getCompanylist() {
		return companylist;
	}
	public void setCompanylist(List<Company> companylist) {
		this.companylist = companylist;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<?> getOneCompany() {
		return oneCompany;
	}
	public void setOneCompany(List<?> oneCompany) {
		this.oneCompany = oneCompany;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
