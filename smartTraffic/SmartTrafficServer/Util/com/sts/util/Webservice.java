package com.sts.util;

import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Webservice 
{
	public static int TIME_OUT=10000;
	
	public static String getWebservice(String url,String method)
	{
		String result = "";
		try 
		{
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(method);
			call.setTimeout(TIME_OUT);
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			result = (String) call.invoke(new Object[]{});
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getWebservice(String url,String method,String[] params,String[] values)
	{
		String result = "";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(url);
			call.setOperationName(method);
			call.setTimeout(TIME_OUT);

			for (int i = 0; i < params.length; i++) {
				call.addParameter(params[i],org.apache.axis.encoding.XMLType.XSD_DATE,javax.xml.rpc.ParameterMode.IN);
			}
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			result = (String) call.invoke(values);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getSoapWebservice(String url,String prefix,String nameSpace,String method,Map<String, String> params)
	{
		String result = "";
		try 
		{
			ServiceClient sender = new ServiceClient();  
			
			EndpointReference targetEPR = new EndpointReference(url);  
			
			Options options = new Options();  
			options.setAction(nameSpace+method);  
			options.setTo(targetEPR);  
					
			OMFactory fac = OMAbstractFactory.getOMFactory();  
			OMNamespace omNs = fac.createOMNamespace(nameSpace, prefix);  
			OMElement data = fac.createOMElement(method, omNs);  
			
			for(Map.Entry<String, String> param : params.entrySet())
			{
				OMElement inner = fac.createOMElement(param.getKey(), omNs);  
				inner.setText(param.getValue());  
				data.addChild(inner);
			}
			
			sender.setOptions(options);  
			OMElement resultElement = sender.sendReceive(data);  
			result=resultElement.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	  
    public static String getWeather()  
    {  
    	String result="";
    	String url="http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";
        String soapaction="http://WebXml.com.cn"; 
    	
        Object object;
        String City="上海";          
        Service service=new Service();  
        try{  
            Call call=(Call)service.createCall();              
            call.setTargetEndpointAddress(new java.net.URL(url));              
            call.setOperationName(new QName(soapaction,"getWeatherbyCityName")); //设置要调用哪个方法  
            call.addParameter(new QName(soapaction,"theCityName"), //设置要传递的参数  
                    XMLType.XSD_STRING,  
                   javax.xml.rpc.ParameterMode.IN);  
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING); //要返回的数据类型（自定义类型）               
             
            call.setUseSOAPAction(true);  
            call.setSOAPActionURI(soapaction + "getWeatherbyCityName");      
            
            object= (String)call.invoke(new Object[]{City});
            result=(String)object;          
             
        }catch(Exception ex)  
        {  
        }  
        
        return result;
    }  

	public static Object CallWebService(String url,String namespace,String methodName,Map<String, Object> requestParams)
	{
		String SOAP_ACTION = namespace + methodName;

		SoapObject request = new SoapObject(namespace, methodName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		if(requestParams!=null)
		{
			for(Map.Entry<String, Object> entry : requestParams.entrySet())
			{
				request.addProperty(entry.getKey(),entry.getValue());
			}
		}
		
		envelope.bodyOut = request;
		envelope.dotNet = true;
		
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(url);
		Object result = null;
		try
		{
			transport.call(SOAP_ACTION, envelope);
			result = envelope.getResponse();			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		System.out.println("soap request");
		return result;
	}
}
