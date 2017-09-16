package com.example.smarttraffic.network;


/**
 * 基本请求处理实现类
 * 所有请求处理类都继承此类
 * @author Administrator zhou
 *
 */
public class BaseSearch implements Search
{
	@Override
	public Object search(Request request) 
	{
		try
		{
			String dataString;
			Object dataObject;
			Object object;
			
			if(request.CreatePostParams()!=null)
			{
				PostParams params=request.CreatePostParams();
				
				dataString=searchPost(params);
				object=parse(dataString);
			}
			else if(request.createWebServiceParams()==null)
			{
				String urlString=request.CreateUrl();
				
				dataString=searchHttp(urlString);
				object=parse(dataString);
			}
			else
			{
				dataObject=searchWebService(request.createWebServiceParams());
				object=parseWebService(dataObject);
			}
			
			order(object);
			return object;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getSearchKind() {
		return HttpThread.DEFAULT;
	}

	@Override
	public String searchHttp(String url) {
		return HttpClient.queryStringForGet(url);
	}

	@Override
	public Object searchWebService(WebServiceParams params) {
		return SoapRequest.CallWebService(params);
	}
	
	@Override
	public Object parse(String data) {
		return null;
	}

	@Override
	public Object parseWebService(Object object) {
		return null;
	}
	
	@Override
	public Object order(Object data) {
		return data;
	}

	@Override
	public String searchPost(PostParams params)
	{
		return PostClient.post(params.getUrl(), params.getParams());
	}
	
}
