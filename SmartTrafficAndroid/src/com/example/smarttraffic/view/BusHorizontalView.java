package com.example.smarttraffic.view;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.SmartBusStationDetailActivity;
import com.example.smarttraffic.smartBus.StationRealActivity;
import com.example.smarttraffic.smartBus.bean.BusLine;
import com.example.smarttraffic.smartBus.bean.BusLocation;
import com.example.smarttraffic.smartBus.bean.StationOnLine;
import com.example.smarttraffic.util.DensityUtil;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 公交线路视图
 * @author Administrator zhou
 *
 */
public class BusHorizontalView extends HorizontalScrollView
{
	private List<StationOnLine> stationList;			//公交线路站点列表
	private List<BusLocation> busLocations;				//公交定位列表
	private BusLine busLine;
	private Activity activity;
		
	private int direct;
	public void setDirect(int direct)
	{
		this.direct = direct;
	}
	public int getDirect()
	{
		return direct;
	}
	public void setBusLine(BusLine busLine)
	{
		this.busLine = busLine;
	}
	public void setActivity(Activity activity)
	{
		this.activity = activity;
	}
	
	public void setBusLocations(List<BusLocation> busLocations)
	{
		this.busLocations = busLocations;
	}

	public void setStationList(List<StationOnLine> stationList)
	{
		this.stationList = stationList;
	}
	public List<StationOnLine> getStationList()
	{
		return stationList;
	}
	
	public BusHorizontalView(Context context)
	{
		this(context,null);
	}
	
	public BusHorizontalView(Context context, AttributeSet attrs) {  
        super(context, attrs); 
	}

	public void refresh()
	{
		this.removeAllViews();
		initList();
	}
	
	class OnStationClick implements OnClickListener
	{
		int lineId;
		String stationName;
		String lineName;
		
		int stationId;
		double lan;
		double lon;

		
		public OnStationClick(int lineID,String linename,String stationname,int id,double lan,double lon)
		{
			this.lineId=lineID;
			this.lineName=linename;
			this.stationName=stationname;
			this.stationId=id;
			this.lan=lan;
			this.lon=lon;
		}
		@Override
		public void onClick(View v)
		{
			Bundle bundle=new Bundle();
			bundle.putInt(SmartBusStationDetailActivity.SMART_BUS_STATTON_ID, stationId);
			bundle.putDouble(SmartBusStationDetailActivity.SMART_BUS_STATION_LANTITUDE, lan);
			bundle.putDouble(SmartBusStationDetailActivity.SMART_BUS_STATION_LONGTITUDE, lon);
			StartIntent.startIntentWithParam(activity, SmartBusStationDetailActivity.class, bundle);
			
//			Bundle bundle=new Bundle();
//			bundle.putInt(StationRealActivity.STATION_REAL_LINE_ID, lineId);
//			bundle.putInt(StationRealActivity.STATION_REAL_STARIONT_ID, stationId);
//			bundle.putString(StationRealActivity.STATION_REAL_STARIONT_NAME, stationName);
//			bundle.putString(StationRealActivity.STATION_REAL_LINE_NAME, lineName);
//			bundle.putInt(StationRealActivity.STATION_REAL_ORIENT, direct);
//			
//			StartIntent.startIntentWithParam(activity, StationRealActivity.class, bundle);
			
		}
	}
	
	private int DEFAULT_SIZE=30;
	
	private int DEFAULT_IMAGE_ID=R.drawable.smart_bus_onroad;
	
	private void initList()
	{
		if(stationList==null ||stationList.size() ==0)
		{
			return;
		}
		
		DEFAULT_SIZE=DensityUtil.dip2px(this.getContext(), 30);
		
		LinearLayout layout=new LinearLayout(this.getContext());
		layout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout stationInfo=new LinearLayout(this.getContext());
		RelativeLayout realInfo=new RelativeLayout(this.getContext());
		this.addView(layout);
		layout.addView(realInfo);
		layout.addView(stationInfo);
		for(int i=0;i<stationList.size();i++)
		{
			TextView textView=null;
			textView=new TextView(this.getContext());
			
			//设置单字不一定有用
			textView.setEms(1);
			textView.setWidth(DEFAULT_SIZE);
			textView.setGravity(Gravity.CENTER_HORIZONTAL);
			
			//每个字后加换行符
			textView.setText(DoString.OneWordOneLine(stationList.get(i).getStationName()));
			textView.setBackgroundResource(R.drawable.selector_text_under_2);
			
			Drawable drawable=getResources().getDrawable(R.drawable.smart_bus_line);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			textView.setCompoundDrawables(null, drawable, null, null);	
			
			textView.setOnClickListener(new OnStationClick(busLine.getId(),busLine.getName(),stationList.get(i).getStationName(),stationList.get(i).getStationId(), stationList.get(i).getLatitude(),stationList.get(i).getLongitude()));
			stationInfo.addView(textView);
		}
		
		if(busLocations==null || busLocations.size()==0)
		{
			ImageView imageView=null;
			imageView=new ImageView(getContext());
			imageView.setBackgroundResource(DEFAULT_IMAGE_ID);
			imageView.setVisibility(View.INVISIBLE);
			realInfo.addView(imageView);	
			
			return;
		}
		else
		{
			Bitmap bitmap=BitmapFactory.decodeResource(getResources(), DEFAULT_IMAGE_ID);
			
			for(int i=0;i<busLocations.size();i++)
			{
				int l=stationList.size();
				
				ImageView imageView=null;
				imageView=new ImageView(getContext());
				imageView.setBackgroundResource(DEFAULT_IMAGE_ID);
				realInfo.addView(imageView);
				
				android.widget.RelativeLayout.LayoutParams params=new android.widget.RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				params.leftMargin=(l-busLocations.get(i).getStationIndex())*(DEFAULT_SIZE)-DEFAULT_SIZE/2+(DEFAULT_SIZE-bitmap.getWidth())/2;
				imageView.setLayoutParams(params);
			}
		}
	}
	
}
