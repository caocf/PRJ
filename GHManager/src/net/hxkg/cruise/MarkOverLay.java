package net.hxkg.cruise;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKItemizedOverlay;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;

public class MarkOverLay extends CNMKItemizedOverlay
{
	List<CNMKOverlayItem> mGeoList = new ArrayList<CNMKOverlayItem>();
	Context context;
	Drawable marker;
	CNMKMapView mMapView;
	Paint paint=new Paint();
	Paint paintline=new Paint();

	public MarkOverLay(Drawable marker, Context context,CNMKMapView mMapView) 
	{
		super(marker);
		this.context=context;
		this.marker=marker;
		this.mMapView=mMapView;	
		
		paint.setColor(Color.parseColor("#0067ac"));
		paint.setAlpha(80);
		//paint.setARGB(100,0,0,255);
		paintline.setColor(Color.YELLOW);
		paintline.setStrokeWidth(20);
			
		populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}
	
	@Override
	public void draw(Canvas canvas, CNMKMapView mapView, boolean shadow) 
	{
		super.draw(canvas, mapView, shadow);	
		
		if(mGeoList.size()<=0)
			return;
		
		int level=mMapView.getZoomLevel();
		if(level<14)return;
		
		GeoPoint point=mGeoList.get(0).getPoint();
		Point pixPoint=mapView.getProjection().toPixels(point, null);
		canvas.drawCircle(pixPoint.x, pixPoint.y, mapView.getProjection().metersToEquatorPixels(1500), paint);		
		
		if(InCruiseActivity.points.size()<=1)
			return;
		for(int i=1;i<InCruiseActivity.points.size();i++)
		{
			Point p1=mMapView.convertMap2Scr(InCruiseActivity.points.get(i-1));
			Point p2=mMapView.convertMap2Scr(InCruiseActivity.points.get(i));
			canvas.drawLine(p1.x, p1.y,p2.x, p2.y, paintline);
			System.out.println("line"+p1.x+p1.y);
		}
		
		
	}
	
	public void Fresh(CNMKOverlayItem createItem)
	{
		mGeoList.clear();
		mGeoList.add(createItem);
		this.populate();
	}

	@Override
	protected CNMKOverlayItem createItem(int i) 
	{
		// TODO Auto-generated method stub
		return mGeoList.get(i);
	}
	
	@Override
	protected boolean onTap(int a_iIndex)
	{
		
		return true;
		
	}
	
	@Override
	public boolean onTap(GeoPoint arg0, CNMKMapView arg1)
	{
		return super.onTap(arg0, arg1);
	}

	@Override
	public int size() 
	{
		// TODO Auto-generated method stub
		return mGeoList.size();
	}
	
	public void FreshALL(List<CNMKOverlayItem> list)
	{
		mGeoList.clear();
		mGeoList=list;
		this.populate();
	}
	
	public void Pop()
	{
		this.populate();
	}
	
	public void updateInfo(String shipname,String date,GeoPoint p)
	{
		/**/		
		
	}

}
