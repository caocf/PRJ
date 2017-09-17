package com.sts.system.service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.sts.parking.action.PostDateFromSIWEI;
import com.sts.system.model.About;
import com.sts.system.model.Version;
import com.sts.system.model.VersionShortInfo;


public class SystemService 
{
	public About GetSystemAbout()
	{
		About result=new About();
		
		result.setTitle("关于我们");
		result.setContent("<p>　　　随着全面建设小康社会进程的加快，人民的生活将更加富足，追求安全、便捷、经济、舒适和个性化的出行需求将更加旺盛，公众对交通信息服务提出了更高的要求，迫切需要交通运输行业能够提供满足公众需求的出行信息服务。同时，城乡一体化步伐的加快和交通运输网络规模的迅猛发展也为公众出行创造了良好的基础设施条件，全社会的交通出行规律也随之产生变化。一是人民生活水平的提高以及消费结构的显著变化，特别是家庭轿车的普及，城市居民日常出行距离显著增加，并呈现多样化、个性化的趋势；二是农村居民生活方式发生改变，平日出行频率明显提高，出行范围不断扩大，同时城市化进程拉近了城乡距离，城乡间客流也因此大幅增加；三是都市圈快速交通网络发展迅速，同城化趋势日益显现，城际、省际间通勤客流也快速增长。 </p><p>　　　因此，通过本网站实施，能够为广大出行者提供更节约的出行成本、更便捷的出行效率、更高水平的服务手段、更丰富的服务内容，更好的满足公众出行服务的需要。</p><p>　　　嘉兴智慧交通综合信息服务平台是嘉兴市“智慧交通”的重要组成部分，是智慧交通的基础，是智慧交通试点建设的核心。按照“智于管理、慧及民生”的理念，围绕“惠民便民、服务经济”两大主题，以公众信息服务、物流服务为重点，以新一代信息技术为支撑，以更全面的感知、更智能的控制、更广泛的交互、更深度的融合、更紧密的协同为基础，统筹交通信息资源，实现基础设施感知化、信息资源集成化、综合交通一体化、区域交通联动化、交通管理协同化、决策指挥科学化、公众服务人文化，构建安全、通畅、低碳、便捷、高效、智慧的现代综合交通运输体系，为打造“智慧嘉兴”提供有力支撑。</p>");
		result.setDate(new Date());
		
		return result;
	}
	
	public String[] GetHelp()
	{
		String[] result=new String[]{"1、用户注册需收集验证","2、用户登录后可收藏信息","3、可查看精确的线路规划","4、提供实时的交通状况数据信息"};
		
		return result;
		
	}
	
	public List<VersionShortInfo> GetAppList(String path)
	{
		String result="";
		result=GetFile(path);
		
		List<VersionShortInfo> r=new ArrayList<VersionShortInfo>();
		
		JSONObject jsonObject=JSONObject.fromObject(result);
		JSONArray array=jsonObject.getJSONArray("versionList");
		
		for(int i=0;i<array.size();i++)
		{
			VersionShortInfo versionShortInfo=new VersionShortInfo();
			versionShortInfo.setVersionID(array.getJSONObject(i).getInt("versionID"));
			versionShortInfo.setVersionDownUrl(array.getJSONObject(i).getString("versionDownUrl"));
			
			r.add(versionShortInfo);
		}
		
		return r;
	}
	
	public Version GetAppUrl(String path)
	{		
		String result="";
//		
//		result=GetFile(path);
//		
//		System.out.println(result);
//
//		
//		JSONObject jsonObject=JSONObject.fromObject(result);
//		JSONArray array=jsonObject.getJSONArray("versionList");
//		
//		jsonObject=array.getJSONObject(0);
//		
//		int versionID=jsonObject.getInt("versionID");
//		String url=jsonObject.getString("versionDownUrl");
//		
//		System.out.println(versionID);
//		System.out.println(url);

		List<VersionShortInfo> vl=GetAppList(path);
		
		String url="";
		int id=0;
		
		for(VersionShortInfo v:vl)
		{
			if(v.getVersionID()>id)
			{
				id=v.getVersionID();
				url=v.getVersionDownUrl();
			}
		}
		
		result=new PostDateFromSIWEI().GetData(url);
		
		Version v=JSON.parseObject(result,Version.class);
		
		return v;
	}
	
	
	public String GetFile(String path)
	{
		 StringBuffer   sbFile;   
		 try
		 {
	         FileReader   in   =   new   FileReader(path);   
	         char[]   buffer   =   new   char[4096];   
	         int   len;   
	         sbFile   =   new   StringBuffer();   
	         while   (   (len   =   in.read(buffer))   !=   -1)   {   
	             String   s   =   new   String(buffer,   0,   len);   
	             sbFile.append(s);   
	         }
	         return   sbFile.toString();   
		 }
		 catch (Exception e) 
		 {
			 System.out.println(e);
		 }
		 
         return "";
	}
}
