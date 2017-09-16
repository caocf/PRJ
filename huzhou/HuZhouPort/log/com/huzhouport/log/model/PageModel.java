package com.huzhouport.log.model;

import java.util.List;

public class PageModel<T> {
	
	/**总记入数*/
	private int total;
	
	/**当前页面* 第几条开始*/ 
	private int currentPage;
	
	/**每页显示多少条数据*/
	private int manPage;
	
	/**总页数*/
	private int totalPage;
	
	/**分页数据*/
	private List<T> recordsDate;

	private String action;  // action name
	
	
	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	//拿到总页数
	public int getTotalPage() {
		if(total % manPage == 0){
			totalPage = total / manPage;
		}else{
			totalPage = (total / manPage)+1;
		}
			return totalPage;
	}
	
	
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getManPage() {
		return manPage;
	}

	public void setManPage(int manPage) {
		this.manPage = manPage;
	}

	

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getRecordsDate() {
		return recordsDate;
	}

	public void setRecordsDate(List<T> recordsDate) {
		this.recordsDate = recordsDate;
	}
	
	
	
}
