package com.example.smarttraffic.drivingSchool;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrivingFavorFragment extends Fragment
{
	ListView favorListView;
	FavorAdapter favorAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_driving_favor, container,false);
		
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
			DrivingSchool drivingSchool=new DrivingSchool();
			TwoItemsData data=favorAdapter.getData().get(position);
			
			drivingSchool.setName(data.getStart());
			drivingSchool.setAddress(data.getEnd());
			drivingSchool.setLantitude(data.getLan1()/1000000.0);
			drivingSchool.setLongtitude(data.getLon1()/1000000.0);
			
			Bundle bundle=new Bundle();
			bundle.putSerializable(DrivingInfoListFragment.DRIVING_SCHOOL_INFO, drivingSchool);
			
			StartIntent.startIntentWithParam(getActivity(), DetailSchoolActivity.class, bundle);
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
		favorAdapter=new FavorAdapter(getActivity(), favorDBOperate.selectForTwoItem(ContentType.DRIBING_SCHOOL_FAVOR));
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
