package com.sts.parking.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.xfire.client.Client;

import com.alibaba.fastjson.JSON;
import com.sts.common.util.ClientAuthenticationHandler;
import com.sts.common.util.GlobalVar;
import com.sts.parking.model.ParkingCG;
import com.sts.parking.model.ParkingCGReal;

public class ParkingService 
{
	public List<ParkingCG> queryParkingFromCG()
	{
		try
		{
			Client client = new Client(new URL(GlobalVar.WSDL_URI));
			client.addOutHandler(new ClientAuthenticationHandler("sunland", "jxcg_parkingfee"));
			client.setTimeout(1500);

			Object[] results = client.invoke("getParkPointListForPublic",new Object[] { "{\"regioncode\":\"01\"}" });
			
			JSONObject jsonObject=JSONObject.fromObject((String)results[0]);
			JSONArray array=jsonObject.getJSONArray("msgList");
			
			return JSON.parseArray(array.toString(),ParkingCG.class);
		}
		catch (Exception e)
		{
		}
		
		return new ArrayList<ParkingCG>();
	}
	
	public List<ParkingCGReal> queryParkingRealCountFromCG()
	{
		try
		{
			Client client = new Client(new URL(GlobalVar.WSDL_URI));
			client.addOutHandler(new ClientAuthenticationHandler("sunland", "jxcg_parkingfee"));
			client.setTimeout(1500);

			Object[] results = client.invoke("getFreeParklotCountForPublic",new Object[] { "{\"regioncode\":\"01\"}" });
			
			JSONObject jsonObject=JSONObject.fromObject((String)results[0]);
			JSONArray array=jsonObject.getJSONArray("msgList");
			
			return JSON.parseArray(array.toString(),ParkingCGReal.class);
		}
		catch (Exception e)
		{
		}
		
		return new ArrayList<ParkingCGReal>();
	}
	
	public List<ParkingCG> queryByName(String name)
	{
		List<ParkingCG> total=queryParkingFromCG();
		
		List<ParkingCG> result=new ArrayList<ParkingCG>();
		
		if(total!=null && total.size()>0)
		{
			for(ParkingCG p:total)
			{
				if(p.getPointname().contains(name))
					result.add(p);
			}
		}
		
		return result;
	}
	
	public ParkingCG queryFullByID(String id)
	{
		List<ParkingCG> total=queryParkingFromCG();
		List<ParkingCGReal> totalReal=queryParkingRealCountFromCG();
		
		ParkingCG result=new ParkingCG();
		
		if(total!=null && total.size()>0)
		{
			for(ParkingCG p:total)
			{
				if(p.getParkpointid().equals(id))
					result=p;
			}
		}
		
		if(totalReal!=null && totalReal.size()>0)
		{
			for(ParkingCGReal pr:totalReal)
			{
				if(pr.getParkpointid().equals(id))
					result.setFreecount(pr.getFreecount());
			}
		}
		
		return result;
	}
	
	//0不区分类型
	public List<ParkingCG> queryByNameAndType(int type,String name,boolean showReal)
	{
		List<ParkingCG> total=queryParkingFromCG();
		List<ParkingCGReal> totalReal=queryParkingRealCountFromCG();
		
		List<ParkingCG> result=new ArrayList<ParkingCG>();
		

		if(total!=null && total.size()>0)
		{
			if(type>0)
			{
				for(ParkingCG p:total)
				{
					if(p.getParktype()==type && p.getPointname().contains(name))
						result.add(p);
				}
			}
			else if(type==0)
			{
				for(ParkingCG p:total)
				{
					if(p.getPointname().contains(name))
						result.add(p);
				}
			}
		}

		if(showReal)
		{
			for(ParkingCG p:result)
			{
				for(ParkingCGReal pr:totalReal)
				{
					if(pr.getParkpointid().equals(p.getParkpointid()))
						p.setFreecount(pr.getFreecount());
				}
			}
		}
		
		return result;
	}
	
	public ParkingCGReal queryRealByID(String id)
	{
		ParkingCGReal result=new ParkingCGReal();
		
		List<ParkingCGReal> totalReal=queryParkingRealCountFromCG();
		
		if(totalReal!=null && totalReal.size()>0)
		{
			for(ParkingCGReal pr:totalReal)
			{
				if(pr.getParkpointid().equals(id))
					result= pr;
			}
		}
		
		return result;
	}
	
	public List<ParkingCG> queryByType(int typeid)
	{
		List<ParkingCG> total=queryParkingFromCG();
		
		List<ParkingCG> result=new ArrayList<ParkingCG>();
		
		if(total!=null && total.size()>0)
		{
			for(ParkingCG p:total)
			{
				if(p.getParktype()==typeid)
					result.add(p);
			}
		}
		
		return result;
	}
	
	
	public List<ParkingCG> queryCount(List<ParkingCG> data,int page,int num)
	{
		List<ParkingCG> result=new ArrayList<ParkingCG>();
		
		if((page-1)*num < data.size())
		{
			for(int i=(page-1)*num;i<page*num;i++)
			{
				if(i < data.size())
				{
					result.add(data.get(i));
				}
			}
		}
		
		return result;
	}
	
	
	public List<ParkingCGReal> queryRealCount(List<ParkingCGReal> data,int page,int num)
	{
		List<ParkingCGReal> result=new ArrayList<ParkingCGReal>();
		
		if((page-1)*num < data.size())
		{
			for(int i=(page-1)*num;i<page*num;i++)
			{
				if(i < data.size())
				{
					result.add(data.get(i));
				}
			}
		}
		
		return result;
	}
}
