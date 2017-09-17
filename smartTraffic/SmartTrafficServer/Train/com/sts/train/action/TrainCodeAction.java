package com.sts.train.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.sts.train.model.TrainCode;
import com.sts.train.service.TrainCodeService;
import com.sts.util.Webservice;

public class TrainCodeAction 
{
	String trainCode;
	List<TrainCode> trainCodes;
	
	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}
	
	public List<TrainCode> getTrainCodes() {
		return trainCodes;
	}

	public void setTrainCodes(List<TrainCode> trainCodes) {
		this.trainCodes = trainCodes;
	}


	private final String WEBSERVICE_URL="http://webservice.webxml.com.cn/WebServices/TrainTimeWebService.asmx";
	private final String WEBSERVICE_NAMESPACE="http://WebXml.com.cn/";
	private final String WEBSERVICE_METHOD="getDetailInfoByTrainCode";
	
	private final String[] WEBSERVICE_PARAM_NAME=new String[]{"TrainCode","UserID"};

	public String GetTrainCode()
	{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(WEBSERVICE_PARAM_NAME[0],trainCode);
		params.put(WEBSERVICE_PARAM_NAME[1], "");
				
		Object object=Webservice.CallWebService(WEBSERVICE_URL, WEBSERVICE_NAMESPACE, WEBSERVICE_METHOD, params);
		
		if(object!=null && object instanceof SoapObject)
		{
			trainCodes=TrainCodeService.parse((SoapObject)object);
		}
		
		return "success";
	}
}
