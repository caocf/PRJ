package com.huzhouport.version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.huzhouport.common.util.HttpUtil;

public class HttpGetVersion 
{
	public int getLastVersion(String url)
	{	
		List<Version> dataList=getVersions(url);
		sort(dataList);
		
		return dataList.get(0).getVersionID();
		
	}
	
	public int getLastVersion(List<Version> list)
	{
		sort(list);		
		return list.get(0).getVersionID();
	}
	
	private final String LIST_NAME="versionList";
	private final String VERSION_ID="versionID";
	private final String VERSION_DOWM_URL="versionDownUrl";
	
	private final String VERSION_NAME="versionName";
	private final String VERSION_DESCRIBE="versionDescribe";
	private final String VERSION_UPDATELOG="versionUpdateLog";
	private final String VERSION_DEBUGLOG="versionDebugLog";
	private final String VERSION_UPDATETIME="versionUpdateTime";
	private final String VERSION_KIND="versionKind";
			
	
	public List<Version> getVersions(String url)
	{
		String data=HttpUtil.queryStringForGet(url);
		
		List<Version> result=new ArrayList<Version>();
		
		try 
		{  
            JSONObject jsonObject=new JSONObject(data);  
            JSONArray jsonArray=jsonObject.getJSONArray(LIST_NAME);  
            
            for(int i=0;i<jsonArray.length();i++)
            {  
                JSONObject json=jsonArray.getJSONObject(i);  
                Version version=new Version();
                
                version.setVersionID(json.getInt(VERSION_ID));
                version.setDetailUrl(json.getString(VERSION_DOWM_URL));
                
                result.add(version);
        	}  
        } 
		catch (Exception e) 
		{  
            e.printStackTrace();  
        }
		return result;
	}
	
	public void sort(List<Version> data)
	{
		Collections.sort(data, new versionCompare());
	}
	
	class versionCompare implements Comparator<Version>
	{
		@Override
		public int compare(Version lhs, Version rhs) {
			
			return lhs.getVersionID() < rhs.getVersionID()?1:-1;
		}
	}

	public VersionInfo getVersionInfo(String url)
	{
		String data=HttpUtil.queryStringForGet(url);
		
//		String s1=EncodingUtils.getString(data.getBytes(),"GB2312");
//		String s2=EncodingUtils.getString(data.getBytes(),"UTF-8");
//		String s3=EncodingUtils.getString(data.getBytes(), "ASCII");
//		String s4=EncodingUtils.getString(data.getBytes(), "unicode");
//		String s5=EncodingUtils.getString(data.getBytes(), "GBK");
		
		
		VersionInfo result=new VersionInfo();
			
		try 
		{  
            JSONObject jsonObject=new JSONObject(data);  
            
            result.setVersionID(jsonObject.getInt(VERSION_ID));
            result.setVersionDebugLog(jsonObject.getString(VERSION_DEBUGLOG));
            result.setVersionDescribe(jsonObject.getString(VERSION_DESCRIBE));
            result.setVersionDownUrl(jsonObject.getString(VERSION_DOWM_URL));
            result.setVersionKind(jsonObject.getInt(VERSION_KIND));
            result.setVersionName(jsonObject.getString(VERSION_NAME));
            result.setVersionUpdateLog(jsonObject.getString(VERSION_UPDATELOG));
            result.setVersionUpdateTime(jsonObject.getString(VERSION_UPDATETIME));
            
            return result;
        } 
		catch (Exception e) 
		{  
            e.printStackTrace();  
        }
		return null;
	}
}
