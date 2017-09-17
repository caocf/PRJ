package com.elc.transfer.mvc;

import com.elc.transfer.cache.CacheResult;
import com.elc.transfer.cache.IResultCache;
import com.elc.transfer.cache.ResultCacheOnDatabase;
import com.elc.transfer.core.IArithmeticCore;
import com.elc.transfer.core.IndependenceArithmetic;
import com.elc.transfer.entity.Reply;
import com.elc.transfer.entity.Request;
import com.elc.transfer.input.InputControl;
import com.elc.transfer.output.OutputControl;

/**
 * 
 * @author Administrator
 * 
 */
public class TransferAction {

	/*------------------处理类-------------------------------*/
	
	public static InputControl inputControl=new InputControl();
	public static OutputControl outputControl=new OutputControl();
	public static IResultCache resultCache=new ResultCacheOnDatabase();
	public static IArithmeticCore arithmeticCore=new IndependenceArithmetic();

	
	
	/*-------------------请求参数----------------------------*/
	Request request;
	public void setRequest(Request request) {
		this.request = request;
	}
	
	double lan1;
	double lon1;
	double lan2;
	double lon2;
	
	public void setLan1(double lan1) {
		this.lan1 = lan1;
	}
	public void setLan2(double lan2) {
		this.lan2 = lan2;
	}
	public void setLon1(double lon1) {
		this.lon1 = lon1;
	}
	public void setLon2(double lon2) {
		this.lon2 = lon2;
	}
	
	/*----------------------返回结果--------------------------*/
	
	Reply reply;
	public Reply getReply() {
		return reply;
	}
	
	
	/*------------------------接口方法---------------------------------*/
	
	public String transfer()
	{
		request=new Request();
		request.setLan1(lan1);
		request.setLan2(lan2);
		request.setLon1(lon1);
		request.setLon2(lon2);
		
		if (inputControl.chains(request)) {

			CacheResult c = null;
			if ((c = resultCache.queryResutlFromCache(request)) != null)
				reply = c.getResult();
			else
				reply = arithmeticCore.arithmeticTransfer(request);
		}

		outputControl.chains(reply);

		return "success";
	}
}
