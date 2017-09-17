package com.highwaycenter.bean;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class CommonPageReq implements KvmSerializable {//视频请求webservice的参数对象

	
	private Integer reqPage;   //当前页数
	private Integer pageItems;  //每页行数
	
	public CommonPageReq(){
		
	}
	
	public CommonPageReq(Integer reqPage, Integer pageItems) {
		super();
		this.reqPage = reqPage;
		this.pageItems = pageItems;
	}

	public Integer getReqPage() {
		return reqPage;
	}
	public void setReqPage(Integer reqPage) {
		this.reqPage = reqPage;
	}
	public Integer getPageItems() {
		return pageItems;
	}
	public void setPageItems(Integer pageItems) {
		this.pageItems = pageItems;
	}

	@Override
	public Object getProperty(int arg0) {
		 switch (arg0) {
	       case 0:
	           return reqPage;
	       case 1:
	           return pageItems;

	       }
	       return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		 switch (arg0) {
		 case 0:
		  arg2.type = PropertyInfo.INTEGER_CLASS;
		  arg2.name = "reqPage";
		  break;
		 case 1:
		  arg2.type = PropertyInfo.INTEGER_CLASS;
		  arg2.name = "pageItems";
		  break;
		  default:
		 break;
		 }
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		  switch (arg0) {
	       case 0:
	    	   reqPage = (Integer)arg1;
	       case 1:
	    	   pageItems = (Integer)arg1;
	    
	       }
		
	}


	
	
	
	

}
