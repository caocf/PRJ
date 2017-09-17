package com.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.highwaycenter.jd.service.impl.JxdczxxServiceImpl;

/**
 * webservice请求
 * 
 * @author Administrator zwc
 * 
 */
public class SoapUtil {

	static Logger log = Logger.getLogger(SoapUtil.class);
	
	
	/**
	 * 获取webservice信息
	 * 
	 * @param url
	 *            webservice地址
	 * @param namespace
	 *            命名空间
	 * @param methodName
	 *            方法名
	 * @param requestParams
	 *            请求参数
	 * @return 返回webservice信息
	 */
	public static Object CallWebService(String url, String namespace,
			String methodName,  List<Map<String, Object>> requestParams) {
		String SOAP_ACTION = methodName;

		SoapObject request = new SoapObject(namespace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		if (requestParams != null) {
			for(int i=0;i<requestParams.size();i++){	
				Map<String, Object> entryobj = requestParams.get(i);
				
			for (Map.Entry<String, Object> entry : entryobj.entrySet()) {
				request.addProperty(entry.getKey(), entry.getValue());
				
			}
			}
		}
		
		envelope.bodyOut = request;
		envelope.dotNet = false;

		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(url, 5000);// 5s超时

		Object result = null;
		System.out.println(envelope);
		try {
			transport.call(SOAP_ACTION, envelope);
			result = envelope.getResponse();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(result.toString());

		return result;
	}

	/**
	 * 获取webservice信息
	 * 
	 * @param url
	 *            webservice地址
	 * @param namespace
	 *            命名空间
	 * @param methodName
	 *            方法名
	 * @param requestParams
	 *            请求参数
	 * @return 返回webservice信息
	 */
	public static SoapObject CallWebServiceSoap(String url, String namespace,
			String methodName, List<Map<String, Object>> requestParams) {
		String SOAP_ACTION = methodName;

		SoapObject request = new SoapObject(namespace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		if (requestParams != null) {
			for(int i=0;i<requestParams.size();i++){	
				Map<String, Object> entryobj = requestParams.get(i);
				
			for (Map.Entry<String, Object> entry : entryobj.entrySet()) {
				request.addProperty(entry.getKey(), entry.getValue());
				
			}
			}
		}
		envelope.bodyOut = request;
		envelope.dotNet = false;
		envelope.encodingStyle="utf-8";
		envelope.setOutputSoapObject(request);
		HttpTransportSE transport = new HttpTransportSE(url, 5000);// 5s超时
		transport.debug=true;
		SoapObject result = null;

		try {
			transport.call(SOAP_ACTION, envelope);
		
			log.debug("request——debug"+transport.requestDump);
			result =  (SoapObject)envelope.bodyIn;
			log.debug("response——debug"+transport.responseDump);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		    System.out.println("结果result----"+result.toString());

		return result;
	}

	/**
	 * 获取webservice信息
	 * 
	 * @param url
	 *            webservice地址
	 * @param namespace
	 *            命名空间
	 * @param methodName
	 *            方法名
	 * @param requestParams
	 *            请求参数
	 * @return 返回webservice信息
	 */
	public static String CallWebServiceForString(String url, String namespace,
			String methodName, List<Map<String, Object>> requestParams) {
		try {
			return CallWebService(url, namespace, methodName, requestParams)
					.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	 /** 
     * soap转化为javabean 
     * @param <T> 
     * @param clazz 
     * @param soapObject 
     * @return 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     * @throws NoSuchMethodException 
     * @throws InstantiationException 
     */  
    public static <T> T soapToPojo(Class<T> clazz, SoapObject soapObject)  
            throws IllegalArgumentException, SecurityException,  
            IllegalAccessException, InvocationTargetException,  
            NoSuchMethodException, InstantiationException {  
          
        Field[] fields = clazz.getDeclaredFields();  
        Object obj = clazz.newInstance();  
        for (Field f : fields) { 
        	
            String method = "set" + f.getName().substring(0, 1).toUpperCase()  
                    + f.getName().substring(1);  
            if (hasMethod(method, clazz.getMethods())) {  
            	Object temp =  new Object();
            	if(soapObject.hasProperty(f.getName())==true){
            	 if(f.getType().toString().indexOf("Integer")>-1){
            		
            		if(soapObject.getProperty(f.getName())==null){
            			temp = 0;
            		}else{
            		temp =Integer.valueOf(soapObject.getPropertyAsString(f.getName()));
            		}
            	}
            	else if(f.getType().toString().indexOf("String")>-1){
            		if(soapObject.getProperty(f.getName())==null){
            			temp = null;
            		}else{
            		temp = soapObject.getPropertyAsString(f.getName());
            		}
            	}else if(f.getType().toString().indexOf("Double")>-1){
            		if(soapObject.getProperty(f.getName())==null){
            			temp = (Double)0.0;
            		}else{
            		temp = Double.parseDouble(soapObject.getPropertyAsString(f.getName()));
            		}
            	}
         
                clazz.getMethod(method, new Class[] { f.getType() }).invoke(  
                        obj,  
                        temp);    
                }
                
            }  
        }  
        return (T) obj;  
    }  
  
    private static boolean hasMethod(String methodName, Method[] method) {  
        for (Method m : method) {  
            if (methodName.equals(m.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }  
	
	
public static <T> List<T> parseSoapVector(Class<T> clazz, String propertyName,SoapObject sobj) throws Exception{
	try{
	  List <T> list = new ArrayList<T>() ;
	  for(int attr=0;attr<sobj.getPropertyCount();attr++){
		String soapstr =  sobj.getPropertyAsString(attr);
		if(soapstr.indexOf(propertyName)>-1){
			SoapObject soap =  (SoapObject) sobj.getProperty(attr);
	
		   T tobj = soapToPojo(clazz,soap);
		   list.add(tobj);
		}
	  }
	return list;
	}catch(Exception e){
		e.printStackTrace();
		throw(e);
		
	}
	}
	
	
	
}
