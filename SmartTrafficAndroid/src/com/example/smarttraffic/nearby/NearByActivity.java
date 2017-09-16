package com.example.smarttraffic.nearby;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeHomeActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

public class NearByActivity extends FragmentActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_near_by);
		
		initHead();
		
		NearbyAddLayout addLayout=new NearbyAddLayout(this);
		addLayout.addAllNearby((LinearLayout)findViewById(R.id.nearby_type_content_class_layout), GetType());
	}
	
	private void initHead()
	{
		HeadFragment fragment=new HeadFragment();
		fragment.setTitleName("周边");
		ManagerFragment.replaceFragment(this, R.id.nearby_head, fragment);
	}
	
	private List<List<NearbyType>> GetType()
	{
		List<List<NearbyType>> result=new ArrayList<List<NearbyType>>();
		
		String[] topType=getResources().getStringArray(R.array.array_circle_type_top);
		
		for(int i=0;i<topType.length;i++)
		{
			int id=this.getResources().getIdentifier(topType[i], "array", "com.example.smarttraffic");
			String[] array=getResources().getStringArray(id);
			
			List<NearbyType> temp=new ArrayList<NearbyType>();
			for(int j=0;j<array.length;j++)
			{
				NearbyType type=new NearbyType();
				
				String[] spit=array[j].split(",");
				type.setId(Integer.valueOf(spit[0]));
				type.setName(spit[1]);
				
				temp.add(type);
			}
					
			result.add(temp);
		}
		
		return result;
	}
	
	public void onclick(View v)
	{
		Bundle bundle=new Bundle();
		switch (v.getId())
		{
			case R.id.nearby_item_1:
				StartIntent.startIntent(this,BikeHomeActivity.class);
				break;
				
			case R.id.nearby_item_2:
				bundle.putInt(NearbyDetailActivity.NEAR_BY_TYPE_ID,902);
				bundle.putString(NearbyDetailActivity.NEAR_BY_TYPE_NAME,"旅游景点");
				
				StartIntent.startIntentWithParam(this, NearbyDetailActivity.class, bundle);			
				break;
							
			case R.id.nearby_item_3:
				bundle.putInt(NearbyDetailActivity.NEAR_BY_TYPE_ID,202);
				bundle.putString(NearbyDetailActivity.NEAR_BY_TYPE_NAME,"长途车站");
				
				StartIntent.startIntentWithParam(this, NearbyDetailActivity.class, bundle);	
				break;
				
			case R.id.nearby_item_4:
				bundle.putInt(NearbyDetailActivity.NEAR_BY_TYPE_ID,211);
				bundle.putString(NearbyDetailActivity.NEAR_BY_TYPE_NAME,"加油站");
				
				StartIntent.startIntentWithParam(this, NearbyDetailActivity.class, bundle);
				break;
				
			case R.id.nearby_item_5:
				
				break;
				
			case R.id.nearby_item_6:
				
				break;
				
			case R.id.nearby_item_7:
				
				break;
				
			case R.id.nearby_item_8:
				
				break;

		}
	}
}
