package com.example.smarttraffic.network;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * http内容访问类
 * @author Administrator
 *
 */
public class HttpClient 
{
	
	public static HttpGet getHttpGet(String url)
	{
		HttpGet request = new HttpGet(url);
		 return request;
	}
	
	public static HttpPost getHttpPost(String url)
	{
		 HttpPost request = new HttpPost(url);
		 return request;
	}
	
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException
	{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException
	{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	
	
	public static String queryStringForPost(String url)
	{
		HttpPost request = HttpClient.getHttpPost(url);
		String result = null;
		try {
			HttpResponse response = HttpClient.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return result;
    }
	
	public static String queryStringForPost(HttpPost request)
	{
		String result = null;
		try {
			HttpResponse response = HttpClient.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
	
	public static  String queryStringForGet(String url)
	{
		HttpGet request = HttpClient.getHttpGet(url);
		String result = null;
		try {
			HttpResponse response = HttpClient.getHttpResponse(request);
			int a= response.getStatusLine().getStatusCode();
			if(response.getStatusLine().getStatusCode()==200){
				result = //response.getEntity().toString();
						EntityUtils.toString(response.getEntity(),"utf-8");
			 return result;
			}
				int a1= response.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
        return null;
    }
}
