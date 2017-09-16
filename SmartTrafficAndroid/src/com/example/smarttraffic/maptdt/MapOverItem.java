//package com.example.smarttraffic.maptdt;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.smarttraffic.drivingSchool.OnPopInterface;
//import com.tianditu.android.maps.GeoPoint;
//import com.tianditu.android.maps.ItemizedOverlay;
//import com.tianditu.android.maps.MapView;
//import com.tianditu.android.maps.Overlay;
//import com.tianditu.android.maps.OverlayItem;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//
//public class MapOverItem extends ItemizedOverlay<OverlayItem> implements Overlay.Snappable
//{
//	@Override
//	protected OverlayItem createItem(int arg0)
//	{
//		return mGeoList.get(arg0);
//	}
//
//	@Override
//	public int size()
//	{
//		return mGeoList.size();
//	}
//
//	@Override
//	protected boolean onTap(int i)
//	{
//		GeoPoint pt = mGeoList.get(i).getPoint();
//	
//		if(popInterface!=null)
//		{
//			pop=popInterface.onPop(i, pop);
//				
//			mapView.updateViewLayout(pop,
//					new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//                    		pt, MapView.LayoutParams.BOTTOM_CENTER));
//			pop.setVisibility(View.VISIBLE);
//			mapView.getController().animateTo(pt);		
//		}
//		return true;
//	}
//
//	private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
//	private Context mContext;
//	private MapView mapView;
//	private View pop;
//	private List<GeoPoint> points;
//	private OnPopInterface popInterface;
//
//	public MapOverItem(Drawable mark, Context context,MapView mapView,View view,List<GeoPoint> point,OnPopInterface popInterface) {
//		 super(mark);
//		this.mContext = context;
//		this.mapView=mapView;
//		this.pop=view;
//		this.points=point;
//		this.popInterface=popInterface;
//
//		
//		for(GeoPoint g:this.points)
//		{
//			mGeoList.add(new OverlayItem(g, "", ""));
//		}
//		
//		populate(); 			
//	}
////
////	@Override
////	public void draw(Canvas canvas, CNMKMapView mapView, boolean shadow) {
////
////		ICNMKProjection projection = mapView.getProjection(); 
////		for (int index = size() - 1; index >= 0; index--) {
////			CNMKOverlayItem overLayItem = getItem(index); 
////
////			String title = overLayItem.getTitle();
////			Point point = projection.toPixels(overLayItem.getPoint(), null); 
////
////			Paint paintText = new Paint();
////			paintText.setColor(Color.BLUE);
////			paintText.setTextSize(15);
////			canvas.drawText(title, point.x-30, point.y, paintText);
////		}
////
////		super.draw(canvas, mapView, shadow);
////		boundCenterBottom(marker);
////	}
////
////	@Override
////	protected CNMKOverlayItem createItem(int i) {
////		return mGeoList.get(i);
////	}
////
////	@Override
////	public int size() {
////		return mGeoList.size();
////	}
////	
////	@Override
////	protected boolean onTap(int i) {
////		CNMKOverlayItem overItem = getItem(i);
////		setFocus(overItem);
////		GeoPoint pt = overItem.getPoint();
////	
////		if(popInterface!=null)
////		{
////			pop=popInterface.onPop(i, pop);
////				
////			mapView.updateViewLayout(pop,
////					CNMKMapView.newLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
////	                		pt, CNMKMapView.LayoutParams_BOTTOM_CENTER));
////			pop.setVisibility(View.VISIBLE);
////			mapView.getController().animateTo(overItem.getPoint());
////			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
////					Toast.LENGTH_SHORT).show();
////		
////		}
////		return true;
////	}
////
////	@Override
////	public boolean onTap(GeoPoint arg0, CNMKMapView arg1) {
////		pop.setVisibility(View.GONE);
////		return super.onTap(arg0, arg1);
////	}
//}
