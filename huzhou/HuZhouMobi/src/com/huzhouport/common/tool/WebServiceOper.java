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
		// ����SoapObjectʵ��
		SoapObject request = new SoapObject(namespace, methodName);
		// ���ɵ���web service ������soap������Ϣ
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// ����.net web service
		envelope.dotNet = true;

		// �������
		if (params != null && !params.isEmpty())
		{
			for (Iterator<Entry<String, Object>> it = params.entrySet()
					.iterator(); it.hasNext();)
			{
				Map.Entry<String, Object> e = (Entry<String, Object>) it.next();
				request.addProperty(e.getKey().toString(), e.getValue());
			}
		}
		// ��������
		envelope.setOutputSoapObject(request);		
		HttpTransportSE transport = new HttpTransportSE(url,5000);
		//MyAndroidHttpTransport transport = new MyAndroidHttpTransport(url,5000);
		SoapObject result = null;
		try
		{
			// web service����
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
	private int timeout =5000; //Ĭ�ϳ�ʱʱ��Ϊ20s
	public MyAndroidHttpTransport(String url) {
	super(url); 
	} 
	public MyAndroidHttpTransport(String url, int timeout) {
	super(url); 
	this.timeout = timeout; 
	} 
	//����ע��˷���
	protected ServiceConnection getServiceConnection() throws IOException 
	{ 
	ServiceConnectionSE serviceConnection = new ServiceConnectionSE(this.url,timeout);
	return serviceConnection;
	} 
	}*/
}
