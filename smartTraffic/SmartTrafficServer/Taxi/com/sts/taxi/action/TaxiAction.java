package com.sts.taxi.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sts.common.model.Message;
import com.sts.common.util.MessageFactory;
import com.sts.taxi.model.TaxiOrder;
import com.sts.taxi.service.TaxiService;
import com.sts.user.action.UserAction;

public class TaxiAction {
	int userid;
	String start;
	String end;
	String date;
	String company;
	String alert;
	String status;
	String phone;
	boolean result;
	TaxiService taxiService;
	List<TaxiOrder> taxiOrders;

	public List<TaxiOrder> getTaxiOrders() {
		return taxiOrders;
	}

	public void setTaxiService(TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isResult() {
		return result;
	}

	// 预约
	public String orderTaxi() {

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();

		if (session.containsKey(UserAction.USER_SESSION_ID)) {

			TaxiOrder taxiOrder = new TaxiOrder();

			taxiOrder.setAlert(alert);
			taxiOrder.setCompany(company);
			taxiOrder.setDate(date);
			taxiOrder.setEnd(end);
			taxiOrder.setStart(start);
			taxiOrder.setStatus(status);
			taxiOrder.setUserid((Integer)session.get(UserAction.USER_SESSION_ID));
			taxiOrder.setPhone(phone);

			result = this.taxiService.save(taxiOrder);
		}

		return "success";
	}

	// 查询
	public String queryOrder() {
		
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		
		if (session.containsKey(UserAction.USER_SESSION_ID)) 
			taxiOrders = this.taxiService.queryById((Integer)session.get(UserAction.USER_SESSION_ID));

		return "success";
	}
	
	
	
	
	/*------------------------------------------------------------------------*/
	
	double lan;
	double lon;
	int radius;
	int num;
	
	public void setLan(double lan) {
		this.lan = lan;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	// 返回结果
	List<?> results;

	public List<?> getResults() {
		return results;
	}

	// 结果消息
	Message message;

	public Message getMessage() {
		return message;
	}
	
	
	int page;
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * 
	 * @return
	 */
	public String searchAllTaxis()
	{
		try {
			
			if(page<=0)
				page=1;
			if(num<=0)
				num=10000;
			
			if(date==null|| date.equals(""))
			{
				Date temp=new Date();
				long t=temp.getTime()-5*60*1000;
				temp=new Date(t);
				
				date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp);
			}
			
			results = this.taxiService.searchAllTaxis(page,num,date);

			message = MessageFactory.createMessage(results);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}
		return "success";
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String searchTaxtByLocationCircle()
	{
		try {
			
			if(num<=0)
				num=5;
			results = this.taxiService.searchByLocationCircle(radius, lan, lon, num);

			message = MessageFactory.createMessage(results);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}
		return "success";
	}
	
	String taxiName;
	public void setTaxiName(String taxiName) {
		this.taxiName = taxiName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String searchTaxiDetailByName()
	{
		try {
			results = this.taxiService.searchTaxiDetailByName(taxiName);

			message = MessageFactory.createMessage(results);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
		}		
		return "success";
	}
	
	/**
	 * 查询出租车电话号码
	 * @return
	 */
	public String queryTaxiPhoneById()
	{
		try {
			results = this.taxiService.queryTaxiPhoneById(taxiName);

			message = MessageFactory.createMessage(results);
		} catch (Exception e) {
			message = MessageFactory.createMessage(null);
			System.out.println(e);
		}		
		
		return "success";
	}
}
