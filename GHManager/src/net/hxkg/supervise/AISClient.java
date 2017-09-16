package net.hxkg.supervise;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;

import android.content.Context;

public class AISClient extends WebSocketClient 
{
	Context context;
	AISOverLay aisoverlay;
	
	public AISClient(URI serverUri, Draft draft,Context context) 
	{
		super(serverUri, draft);
		this.context=context;
	}
	
	public void setOverlay(AISOverLay aisoverlay)
	{
		this.aisoverlay=aisoverlay;
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) 
	{
		System.out.println("onClose");
		connect();
	}

	@Override
	public void onError(Exception arg0) 
	{
		System.out.println("onError");
		arg0.printStackTrace();
		
	}

	@Override 
	public void onMessage(String msg) 
	{
		//System.out.println(msg);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(msg);
			String shipnameString=jsonObject.getString("shipname");
			Double lon=jsonObject.getDouble("lon");//经度
			Double lan=jsonObject.getDouble("lan");//纬度
			//处理偏移	
			Point poit=Convertor.wgs_gcj_encrypts(lan, lon);	
			GeoPoint point =new GeoPoint((int)(poit.getLat()*1e6*AA.LV), (int)(poit.getLng()*1e6*AA.LV));
			CNMKOverlayItem  item=new CNMKOverlayItem(point, "P1", "point1");
			aisoverlay.Fresh(shipnameString,item);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onOpen(ServerHandshake arg0) 
	{
		System.out.println("onOpen");
		String userid=context.getSharedPreferences("User", 0).getString("userid", "");
		send("AIS_"+String.valueOf(userid));
	}	

}
