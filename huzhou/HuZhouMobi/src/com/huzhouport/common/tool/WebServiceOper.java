package com.huzhouport.common.tool;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceOper
{
	public static final String	URL			= "http://202.107.242.115:7879/PortWebService_dpl/services/WebService";
	//http://202.107.242.115:7879/PortWebService_dpl/services/WebService/
	//http://172.21.25.6/PortWebService/WebService.asmx
	public static final String	NAMESPACE	= "http://172.21.24.15/PortWebService/";

	public static Object CallWebService(String url,String namespace,
			String methodName,HashMap<String, Object> params)
	{
		String SOAP_ACTION = namespace + methodName;
		// 创建SoapObject实例
		SoapObject request = new SoapObject(namespace, methodName);
		// 生成调用web service 方法的soap请求消息
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// 设置.net web service
		envelope.dotNet = true;

		// 请求参数
		if (params != null && !params.isEmpty())
		{
			for (Iterator<Entry<String, Object>> it = params.entrySet()
					.iterator(); it.hasNext();)
			{
				Map.Entry<String, Object> e = (Entry<String, Object>) it.next();
				request.addProperty(e.getKey().toString(), e.getValue());
			}
		}
		// 发送请求
		envelope.setOutputSoapObject(request);		
		HttpTransportSE transport = new HttpTransportSE(url,5000);
		//MyAndroidHttpTransport transport = new MyAndroidHttpTransport(url,5000);
		SoapObject result = null;
		try
		{
			// web service请求
			transport.call(SOAP_ACTION, envelope);
			result = (SoapObject) envelope.bodyIn;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;

	}
	
	/*public static class MyAndroidHttpTransport extends HttpTransportSE 
	{ 
	private int timeout =5000; //默认超时时间为20s
	public MyAndroidHttpTransport(String url) {
	super(url); 
	} 
	public MyAndroidHttpTransport(String url, int timeout) {
	super(url); 
	this.timeout = timeout; 
	} 
	//尤其注意此方法
	protected ServiceConnection getServiceConnection() throws IOException 
	{ 
	ServiceConnectionSE serviceConnection = new ServiceConnectionSE(this.url,timeout);
	return serviceConnection;
	} 
	}*/
}
