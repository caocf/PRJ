package com.sts.liveness.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.taglibs.standard.lang.jstl.NullLiteral;

import com.sts.liveness.model.Liveness;
import com.sts.liveness.service.LivenessService;

public class LivenessAction {

	Liveness liveness;
	public void setLiveness(Liveness liveness) {
		this.liveness = liveness;
	}
	public Liveness getLiveness() {
		return liveness;
	}
	LivenessService livenessService;
	
	public void setLivenessService(LivenessService livenessService) {
		this.livenessService = livenessService;
	}
	
	
	public String autoAddUserLiveness()
	{
		if(liveness!=null)
		{
			liveness.setLogindate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			liveness.setIp(ServletActionContext.getRequest().getRemoteAddr());
			
			List<Liveness> results=this.livenessService.findByImei(liveness.getImei(), liveness.getLogindate());
			if(results!=null && results.size()>0)
			{
				//
			}
			else
				livenessService.save(liveness);
		}
		
		return "success";
	}
	
	List<Map<String, Object>> result;
	public List<Map<String, Object>> getResult() {
		return result;
	}
	
	String start;
	String end;
	
	public void setStart(String start) {
		this.start = start;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String queryLivenessAcount()
	{
		this.result=livenessService.queryLivenessCount(start, end);
		
		return "success";
	}

}
