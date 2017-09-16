package com.example.smarttraffic.map;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.drivingSchool.OnPopInterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKItemizedOverlay;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;
import cennavi.cenmapsdk.android.map.ICNMKProjection;

public class MapOverItem extends CNMKItemizedOverlay
{

	private List<CNMKOverlayItem> mGeoList = new ArrayList<CNMKOverlayItem>();
	private Drawable marker;
	private Context mContext;
	private CNMKMapView mapView;
	private View pop;
	private GeoPoint[] points;
	private OnPopInterface popInterface;

	public MapOverItem(Drawable marker, Context context,CNMKMapView mapView,View view,GeoPoint[] point,OnPopInterface popInterface) {
		
		super(boundCenterBottom(marker));
		
		this.marker = marker;
		this.mContext = context;
		this.mapView=mapView;
		this.pop=view;
		this.points=point;
		this.popInterface=popInterface;

		for(int i=0;i<points.length;i++)
		{
			mGeoList.add(new CNMKOverlayItem(points[i],"", ""));
		}
		
		populate(); 			
	}
	
	@Override
	public void draw(Canvas canvas, CNMKMapView mapView, boolean shadow) {

		ICNMKProjection projection = mapView.getProjection(); 
		for (int index = size() - 1; index >= 0; index--) {
			CNMKOverlayItem overLayItem = getItem(index); 

			String title = overLayItem.getTitle();
			Point point = projection.toPixels(overLayItem.getPoint(), null); 

			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x-30, point.y, paintText);
		}

		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	}

	@Override
	protected CNMKOverlayItem createItem(int i) {
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		return mGeoList.size();
	}
	
	@Override
	protected boolean onTap(int i) {
		CNMKOverlayItem overItem = getItem(i);
		setFocus(overItem);
		GeoPoint pt = overItem.getPoint();
	
		if(popInterface!=null && pop!=null)
		{
			pop=popInterface.onPop(i, pop);
				
			mapView.updateViewLayout(pop,
					CNMKMapView.newLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
	                		pt, CNMKMapView.LayoutParams_BOTTOM_CENTER));
			pop.setVisibility(View.VISIBLE);
			mapView.getController().animateTo(overItem.getPoint());
		
		}
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, CNMKMapView arg1) {
		
		if(pop!=null)
			pop.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}
