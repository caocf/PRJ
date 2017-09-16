package com.example.smarttraffic.carRepair;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CarRepairFavorFragment extends Fragment
{
	ListView favorListView;
	FavorAdapter favorAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_car_repair_favor, container,false);
		
		favorListView=(ListView)rootView.findViewById(R.id.driving_favor_list);
		favorListView.setOnItemClickListener(new onFavorItemClick());
			
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("收藏夹");
		fragment.setRightName("删除");
		fragment.setRightListen(new delete());
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_favor_head, fragment);
		
		return rootView;
	}
	
	class onFavorItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			CarRepair carRepair=new CarRepair();
			TwoItemsData data=favorAdapter.getData().get(position);
			
			carRepair.setName(data.getStart());
			carRepair.setAddress(data.getEnd());
			carRepair.setLantitude(data.getLan1()/1000000.0);
			carRepair.setLongtitude(data.getLon1()/1000000.0);
			
			Bundle bundle=new Bundle();
			bundle.putSerializable(CarRepairInfoListFragment.CAR_REPAIR_INFO, carRepair);
			
			StartIntent.startIntentWithParam(getActivity(), DetailCarRepairActivity.class, bundle);
		}
	}
	
	
	@Override
	public void onResume()
	{
		initList();
		super.onResume();
	}
	
	private void initList()
	{
		FavorDBOperate favorDBOperate=new FavorDBOperate(getActivity());
		favorAdapter=new FavorAdapter(getActivity(), favorDBOperate.selectForTwoItem(ContentType.CAR_REPAIR_FAVOR));
		favorListView.setAdapter(favorAdapter);
		favorDBOperate.CloseDB();
	}
	
	class delete implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			StartIntent.startIntent(getActivity(), FavorDeleteActivity.class);		
		}
	}
}
