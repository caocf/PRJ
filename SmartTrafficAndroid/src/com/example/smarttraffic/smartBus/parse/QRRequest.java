package com.example.smarttraffic.smartBus.parse;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;

/**
 * qr码请求类
 * @author Administrator zwc
 *
 */
public class QRRequest extends BaseRequest
{
	
	public QRRequest(String qr)
	{
		this.qr = qr;
	}
	
	String qr;
	public void setQr(String qr)
	{
		this.qr = qr;
	}
	
	@Override
	public String CreateUrl()
	{
		return HttpUrlRequestAddress.SMART_BUS_QR_URL+"?QRCode="+qr;
	}
}
