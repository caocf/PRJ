package com.sts.smartbus.action;

import com.sts.smartbus.model.QRCode;
import com.sts.smartbus.service.LineTraceService;

public class LineTraceAction 
{
	private int lineID;
	private int direct;
	
	LineTraceService lineTraceService;
	public void setLineTraceService(LineTraceService lineTraceService) {
		this.lineTraceService = lineTraceService;
	}
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	
	String trace;
	public String getTrace() {
		return trace;
	}
	
	//线路轨迹
	public String QueryBaseLineTrace()
	{
		if(direct==0)
			direct=1;
		trace=this.lineTraceService.queryTrace(lineID, direct);
		return "success";
	}
	
	
	String QRCode;
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	
	QRCode qr;
	public QRCode getQr() {
		return qr;
	}
	
	//二维码
	public String QueryStationIDByQRCodeFromDB()
	{
		qr=this.lineTraceService.queryQRCode(QRCode);
		
		return "success";
	}
	
}
