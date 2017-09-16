package net.hxkg.ship;

import java.net.URLEncoder;

import net.hxkg.network.Constants;

public class ShipInfo 
{
	private ShipInfoListener slInfoListener;
	
	
	
	public ShipInfo(ShipInfoListener sl)
	{
		slInfoListener=sl;
	}
	
	public void  getChargeListByShip(final String ship,final String type,final int page)
	{       
        new Thread(new Runnable() 
        {			
			@Override
			public void run() 
			{
				String token;
				try 
				{
					token = Token.makeToken();
					HttpFileUpTool post=new HttpFileUpTool();

			        String result=post.get(Constants.shipInfo+"?token="+token
			        						+"&serviceCode="+type
			        		+"&name="+URLEncoder.encode(ship, "UTF-8")
			        		+"&format=xml"
			        		//+"&pageSize=1000"
			        		//+"&pageIndex="+page
			        		);
			        if(result==null)
			        {
			        	slInfoListener.onError();
			        	return;
			        } 
			        /*String string=XML2Json.xml2JSON(result);
			        JSONObject o=new JSONObject(string);
			        JSONArray ar=o.getJSONArray("recordset");
			        JSONObject kObject= (JSONObject)ar.get(0);
			        kObject.getString("JFXMMC");*/
			        //System.out.println(result);
			        slInfoListener.onShipInofo(result);
				} catch (Exception e) 
				{
					slInfoListener.onError();
					e.printStackTrace();
				}
				
			}
		}).start();
	}
}
