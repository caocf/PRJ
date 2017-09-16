package net.hxkg.supervise;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;
import net.hxkg.ghmanager.CenApplication;
import net.hxkg.network.Constants;

public class ShipFresher extends Thread
{
	AISOverLay aisoverlay;
	
	boolean flag=true;
	
	public ShipFresher(AISOverLay aisoverlay)
	{
		this.aisoverlay=aisoverlay;
		flag=true;
	}
	
	public void Stop()
	{
		flag=false;
	}
	
	public boolean isRunning()
	{
		return flag;
	}
	
	@Override
	public void run()
	{
		while(flag)
		{
			HttpGet hut=new HttpGet();
			String resultString="";
			try 
			{
				resultString=hut.get(Constants.BaseURL+"getAIS");
				JSONArray shiplist=new JSONArray(resultString);
				//Map<String, CNMKOverlayItem> map=new ConcurrentHashMap<>();
				Map<String, CNMKOverlayItem> map=new HashMap<>();
				for(int i=0;i<shiplist.length();i++)
				{
					JSONObject shipinfo=(JSONObject) shiplist.get(i);
					String shipnameString=shipinfo.getString("shipname").trim();
					//筛选船名
					if("".equals(shipnameString)||"null".equals(shipnameString)||shipnameString==null)
					{
						continue; 
					}
					
					//AIS筛选
					String aisnum=shipinfo.getString("ais").trim();
					if(aisnum==null||"".equals(aisnum))
					{
						continue;
					}
					//范围 筛选 
					Double lon=shipinfo.getDouble("longitude");//经度
					Double lat=shipinfo.getDouble("latitude");//纬度
					/*GeoPoint p1=aisoverlay.getCNMKMapView().getProjection().fromPixels(0, 0);
					GeoPoint p2=aisoverlay.getCNMKMapView().getProjection().fromPixels(aisoverlay.getCNMKMapView().mScreenWidth
										, aisoverlay.getCNMKMapView().mScreenHeight);
					
					if(lon<p1.lon/1e6-0.2||lon>p2.lon/1e6+0.2||lat>p1.lat/1e6+0.2||lat<p2.lat/1e6-0.2)
					{
						continue;
					} */
					//方向筛选
					String fxString=shipinfo.getString("cruisedirection").trim();
					int fx=0;
					if("".equals(fxString)||"null".equals(fxString)||fxString==null)
					{
						continue;
					}
					fx=(int) (Double.valueOf(fxString)/10);
					
					String freshtime=shipinfo.getString("shipdate").trim();		
					
					//Point poit=Convertor.wgs_gcj_encrypts(lat, lon);	
					GeoPoint point =new GeoPoint((int)(lat*1e6*AA.LV), (int)(lon*1e6*AA.LV));
					CNMKOverlayItem  item=new CNMKOverlayItem(point, shipnameString, String.valueOf(fx)+","+freshtime
							+","+aisnum);
					item.setMarker(CenApplication.map_drawable.get("1"+fx));
					map.put(shipnameString, item);
				}
				
				aisoverlay.FreshALL(map);
				
				Thread.sleep(1*800);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}			
		}
	}
}
