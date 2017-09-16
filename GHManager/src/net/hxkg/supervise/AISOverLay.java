package net.hxkg.supervise;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.hxkg.ghmanager.R;
import net.hxkg.ship.SearchShipActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect; 
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;  
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;  
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKItemizedOverlay;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;

public class AISOverLay extends CNMKItemizedOverlay implements OnClickListener
{
	Map<String,CNMKOverlayItem> mGeoList = new HashMap<>();
	Drawable marker1,marker2,marker3;
	Context context;
	CNMKMapView mMapView;
	View popView;
	String selectItem=""; 
	
	Handler handler;
	Paint paint=new Paint();

	public AISOverLay(Drawable marker, Context context,CNMKMapView mMapView,Handler handler) 
	{	
		super(marker);
		this.context=context; 
		this.mMapView=mMapView;	
		
		this.handler=handler;
		
		populate();
		
		PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);  
		paint.setPathEffect(effects);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(6);
		paint.setStyle(Style.STROKE);
	}
	
	@Override
	public void draw(Canvas canvas, CNMKMapView mapView, boolean shadow) 
	{
		try {
			super.draw(canvas, mapView, shadow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//int level=mMapView.getZoomLevel(); 
		/*if(level<10)
		{
			for(String key:mGeoList.keySet())
			{				
				CNMKOverlayItem item=mGeoList.get(key); 
				item.setMarker(CenApplication.map_drawable.get("dot"));
			}
		}
		else if(level<15)
		{
			for(String key:mGeoList.keySet())
			{
				CNMKOverlayItem item=mGeoList.get(key);
				String fx=item.getSnippet().trim().split(",")[0];
				
				//item.setMarker(CenApplication.map_drawable.get("1"+fx));
				
			}
		}*/
		if(selectItem!=null&&!"".equals(selectItem))
		{
			CNMKOverlayItem item=mGeoList.get(selectItem);
			if(item==null) return;
			/*item.setMarker(CenApplication.map_drawable.get(item));*/
			android.graphics.Point  pixPoint=mapView.getProjection().toPixels(item.getPoint(), null);			
			canvas.drawCircle(pixPoint.x+item.getMarker(0).getMinimumWidth(),
					pixPoint.y+item.getMarker(0).getMinimumHeight(), item.getMarker(0).getMinimumHeight()*2, paint);
		}
	}
	
	public void setPopView(View view)
	{
		popView=view;
		popView.setOnClickListener(this);
	}
	
	public void Fresh(String key,CNMKOverlayItem newItem)
	{
		GeoPoint shipPoint=newItem.getPoint();
		
		GeoPoint poit1=mMapView.getProjection().fromPixels((int)mMapView.getY(), (int)mMapView.getX());//西北
		GeoPoint poit2=mMapView.getProjection().fromPixels((int)mMapView.getY()+mMapView.mScreenHeight//东南
													, (int)mMapView.getX()+mMapView.mScreenWidth);
		if(shipPoint.lon>poit1.lon&&shipPoint.lon<poit2.lon&&shipPoint.lat<poit1.lat&&shipPoint.lat>poit2.lat)
		{
			mGeoList.put(key, newItem);
			this.populate(); 		
		}
		
	}

	@Override
	protected synchronized CNMKOverlayItem createItem(int i) 
	{
		Set<String>  set=mGeoList.keySet();
		Object[] keysString=set.toArray();
		return mGeoList.get(keysString[i]);
	}
	
	@Override
	protected boolean onTap(int a_iIndex)
	{		
		Set<String>  set=mGeoList.keySet();
		Object[] keysString=set.toArray();
		
		selectItem = (String) keysString[a_iIndex];
		
		updatePopView(mGeoList.get(selectItem));
		
		return true;		
	}
	
	private void updatePopView(CNMKOverlayItem overItem)
	{
		String info[]=overItem.getSnippet().split(",");
		// 更新气泡位置,并使之显示
		setFocus(overItem); 
		GeoPoint pt = overItem.getPoint();
	
		mMapView.updateViewLayout( popView,
				CNMKMapView.newLayoutParams(CNMKMapView.LayoutParams.WRAP_CONTENT, CNMKMapView.LayoutParams.WRAP_CONTENT,//LayoutParams.WRAP_CONTENT,
                		pt, CNMKMapView.LayoutParams_BOTTOM_CENTER));
		popView.setVisibility(View.VISIBLE);
		//mMapView.getController().animateTo(overItem.getPoint());
		mMapView.getController().setCenter(overItem.getPoint());
		((TextView)(popView.findViewById(R.id.shipname))).setText(overItem.getTitle());
		((TextView)(popView.findViewById(R.id.time))).setText(info[1]);
		((TextView)(popView.findViewById(R.id.ais))).setText(info[2]);
	}
	
	@Override
	public boolean onTap(GeoPoint arg0, CNMKMapView arg1)
	{		
		LinearLayout layout=(LinearLayout) popView.findViewById(R.id.cuise);
		layout.setVisibility(View.GONE);
		popView.setVisibility(View.GONE);
		selectItem="";
		return super.onTap(arg0, arg1);
	}

	@Override
	public int size() 
	{
		//if(mGeoList.size()<=0)return 0;
		return mGeoList.size();
	}
	
	public void FreshALL(Map<String,CNMKOverlayItem> list)
	{
		mGeoList.clear();
		mGeoList=list;
		//System.out.println(list.size());
		this.populate();		
	
		handler.post(new Runnable() 
		{			
			@Override
			public void run() 
			{
				//mMapView.getController().animateTo(mMapView.getCenter());
				mMapView.getController().animateTo(new GeoPoint(mMapView.getCenter().getLatitudeE6() + 1, mMapView.getCenter().getLongitudeE6() + 1));
				//mMapView.getController().setCenter(mMapView.getCenter());
				
			}
		});
	}
	
	public void Pop()
	{
		this.populate();
	}

	@Override
	public void onClick(View v) 
	{
		Intent intent=new Intent((SuperviseActivity)context,SearchShipActivity.class);
		intent.putExtra("shipnameofmap", ((TextView)(popView.findViewById(R.id.shipname))).getText().toString());
		context.startActivity(intent);
		
	}
	
	public void SearchNames(List<String> list,String tip)
	{
		for(String key:mGeoList.keySet())
		{
			if(key.contains(tip))
				list.add(key);
		}	
	}
	
	public void SearchToTarget(String name)
	{
		selectItem=name;
		updatePopView(mGeoList.get(selectItem));
	}
	
	public CNMKMapView getCNMKMapView()
	{
		return mMapView;		
	}
	
}
