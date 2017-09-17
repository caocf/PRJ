package com.sts.taxi.action;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
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
}
