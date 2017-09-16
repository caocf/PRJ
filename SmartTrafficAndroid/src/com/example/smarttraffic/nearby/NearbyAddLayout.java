package com.example.smarttraffic.nearby;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.util.ListviewControl;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NearbyAddLayout
{
	Activity activity;
	
	public NearbyAddLayout(Activity activity)
	{
		this.activity=activity;
	}
	
	public void addAllNearby(LinearLayout parentLayout,List<List<NearbyType>> data)
	{
		for(int i=0;i<data.size();i++)
		{
			addNearby(parentLayout, data.get(i),i);
		}
	}
	
	/**
	 * 添加热点标题和列表
	 * @param parentLayout
	 * @param data
	 */
	public void addNearby(LinearLayout parentLayout,List<NearbyType> data,int i)
	{
		View root=LayoutInflater.from(parentLayout.getContext()).inflate(R.layout.gridview_nearby,null);
		
		TextView nameTextView=(TextView)root.findViewById(R.id.gridview_nearby_name);
		nameTextView.setText(data.get(0).getName());
		nameTextView.setTextColor(TEXT_COLOR[i%TEXT_COLOR.length]);
		ViewSetter.setDrawableImage(parentLayout.getContext(), nameTextView, TEXT_DRAWERBLE_RIGHT[i%TEXT_COLOR.length],3,6);
		
		creatGridViewForNearby(root, data);
		
		parentLayout.addView(root);
//		LinearLayout newLinearLayout=new LinearLayout(parentLayout.getContext());
//		newLinearLayout.setOrientation(LinearLayout.VERTICAL);
//		newLinearLayout.setPadding(20, 0, 20, 0);
//		
//		TextView nameTextView=new TextView(parentLayout.getContext());
//		nameTextView.setText(data.get(0).getName());
//		nameTextView.setTextSize(20);
//		nameTextView.setTextColor(TEXT_COLOR[i%TEXT_COLOR.length]);
//		ViewSetter.setDrawableImage(parentLayout.getContext(), nameTextView, TEXT_DRAWERBLE_RIGHT[i%TEXT_COLOR.length],3,6);
//		nameTextView.setPadding(0, 20, 0, 8);
//		nameTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		newLinearLayout.addView(nameTextView);
//		
//		data.remove(0);
//		creatGridViewForNearby(newLinearLayout, data);
//		
//		parentLayout.addView(newLinearLayout);
	}
	
//	private final int COLUMN=3;
	private final int[] TEXT_COLOR=new int[]{0xfffe4e4e,0xff4a89fc,0xff80c541,0xfff79d1d,0xffa358ff,0xff31963a};
	private final int[] TEXT_DRAWERBLE_RIGHT=new int[]{R.drawable.circle_arrow_red,R.drawable.circle_arrow_blue,R.drawable.circle_arrow_low_green,R.drawable.circle_arrow_yellow,R.drawable.circle_arrow_purple,R.drawable.circle_arrow_deep_green};
	/**
	 * 添加热点列表
	 * @param layout
	 * @param data
	 */
	public void creatGridViewForNearby(View root,List<NearbyType> data)
	{
		if(data==null)
			return;
		
		int count=data.size();
		if(count==0)
			return;
		
		int row=1+(count-1)/3;
		
		GridView content=(GridView)root.findViewById(R.id.gridview_nearby_content);

//		content.setNumColumns(COLUMN);
		content.setAdapter(new NearbyAdapter(root.getContext(), data));
		content.setOnItemClickListener(clickListener);	
		content.setBackgroundColor(0xffffff);
		
//		layout.addView(content);
		
		ListviewControl.setGridViewHeightBasedOnChildren(content, row,0);
	}
	
	/**
	 * 添加热点点击事件
	 */
	OnItemClickListener clickListener=new OnItemClickListener()
	{
		public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id)
		{
			Bundle bundle=new Bundle();
			bundle.putInt(NearbyDetailActivity.NEAR_BY_TYPE_ID, ((NearbyAdapter)parent.getAdapter()).getData().get(position).getId());
			bundle.putString(NearbyDetailActivity.NEAR_BY_TYPE_NAME, ((NearbyAdapter)parent.getAdapter()).getData().get(position).getName());
			
			StartIntent.startIntentWithParam(activity, NearbyDetailActivity.class, bundle);
		};
	};
}
