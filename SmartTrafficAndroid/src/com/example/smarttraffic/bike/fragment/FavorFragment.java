package com.example.smarttraffic.bike.fragment;

import java.util.List;

import com.example.smarttraffic.ChangeHead;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeRidingListActivity;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.adapter.BikeRideAdapter;
import com.example.smarttraffic.bike.adapter.StationFavorAdapter;
import com.example.smarttraffic.bike.bean.BikeFavor;
import com.example.smarttraffic.bike.bean.BikeRiding;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.bike.db.BikeFavorDB;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * 收藏信息界面
 * @author Administrator zhou
 *
 */
public class FavorFragment extends Fragment
{	
	ListView listView;
	BikeRideAdapter adapter;
	StationFavorAdapter favorAdapter;
	ImageView deleteButton;
	
	/**
	 * 获取收藏线路数量
	 * @return 数量
	 */
	public int getCount()
	{
		return adapter.getCount();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_trip_favor,container, false);
		listView=(ListView)rootView.findViewById(R.id.trip_favor_listview);
		
		if(isDelete())
		{
			deleteButton=(ImageView)rootView.findViewById(R.id.trip_favor_delete);
			deleteButton.setVisibility(View.VISIBLE);
			deleteButton.setOnClickListener(new delete());
		}
		
		updateList(getFavorKind(),isDelete());
		
		return rootView;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		updateList(getFavorKind(),isDelete());
	}
	
	/**
	 * 删除收藏信息界面
	 * @author Administrator
	 *
	 */
	class delete implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if(getFavorKind()==0)
			{
				List<BikeStation> bikeStations=favorAdapter.getData();
				
				FavorDBOperate dbOperate=new FavorDBOperate(getActivity());
				for(int i=0;i<bikeStations.size();i++)
				{
					BikeStation temp=bikeStations.get(i);
					if(temp.isSelect())
					{
						dbOperate.delete(temp.getFavorID());
						bikeStations.remove(i--);
					}
				}
				dbOperate.CloseDB();
				favorAdapter.refreshList(bikeStations);
			}
			else
			{	
				List<BikeFavor> datas=adapter.getData();
				FavorDBOperate dbOperate=new FavorDBOperate(getActivity());
				for(int i=0;i<datas.size();i++)
				{
					BikeFavor temp=datas.get(i);
					if(temp.isSelect())
					{
						dbOperate.delete(temp.getFavorID());
						datas.remove(i--);
					}
				}
				dbOperate.CloseDB();
				adapter.update(datas);
			}
			
			if(changeHead!=null)
				changeHead.changeHead(0);
		}
	}
	
	private int favorKind;
	private boolean isDelete;
	
	ChangeHead changeHead;
	public void setChangeHead(ChangeHead changeHead)
	{
		this.changeHead = changeHead;
	}
	
	public int getFavorKind()
	{
		return favorKind;
	}

	public boolean isDelete()
	{
		return isDelete;
	}

	public void setFavorKind(int favorKind)
	{
		this.favorKind = favorKind;
	}

	public void setDelete(boolean isDelete)
	{
		this.isDelete = isDelete;
	}

	/**
	 * 刷新收藏信息类别
	 * @param id 类别
	 * @param isDelete 是否显示删除图标
	 */
	public void updateList(int id,boolean isDelete)
	{
		
		List<BikeStation> datas;		
		
		if(id==0)
		{
			BikeFavorDB favorDB=new BikeFavorDB(getActivity());
			
			datas=favorDB.selectForBikeStation();
			
			favorDB.CloseDB();
			
			if(favorAdapter==null)
			{
				favorAdapter=new StationFavorAdapter(getActivity(), datas,isDelete?2:1);
				listView.setAdapter(favorAdapter);	
				listView.setOnItemClickListener(new itemOnclick());
			}
			else 
			{
				favorAdapter.refreshList(datas);
			}
		}
		else 
		{
			BikeFavorDB favorDB=new BikeFavorDB(getActivity());
			List<BikeFavor> data=favorDB.selectForBikeRiding();
			
			if(adapter==null)
			{
				adapter=new BikeRideAdapter(getActivity(), data,isDelete);
				listView.setAdapter(adapter);	
				listView.setOnItemClickListener(new itemOnclick());
			}
			else 
			{
				adapter.update(data);
			}
		}
	}
	
	/**
	 * 全选
	 */
	public void selectAll()
	{
		if(favorKind==0)
		{
			favorAdapter.selectALL(true);
			if(changeHead!=null)
				changeHead.changeHead(favorAdapter.countSelect());
		}
		else
		{
			adapter.selectALL(true);
			if(changeHead!=null)
				changeHead.changeHead(adapter.countSelect());
		}
	}
	
	/**
	 * 取消全选
	 */
	public void clearAll()
	{
		if(favorKind==0)
			favorAdapter.selectALL(false);
		else
			adapter.selectALL(false);
	}
	
	/**
	 * 收藏信息点击事件
	 *
	 */
	class itemOnclick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			
			if(getFavorKind()==1)
			{
				if(isDelete())
				{
					adapter.changeCheck(position);
					if(changeHead!=null)
						changeHead.changeHead(adapter.countSelect());
				}
				else 
				{			
					BikeFavor favor=((BikeRideAdapter)parent.getAdapter()).getData().get(position);
					Bundle bundle=new Bundle();
					BikeRiding bikeRiding=new BikeRiding();
					bikeRiding.setStart(favor.getStart());
					bikeRiding.setEnd(favor.getEnd());
					bikeRiding.setLan1(favor.getLan1());
					bikeRiding.setLan2(favor.getLan2());
					bikeRiding.setLon1(favor.getLon1());
					bikeRiding.setLon2(favor.getLon2());
					
					bundle.putSerializable(BikeRidingListActivity.REQUEST_RIDING_INFO, bikeRiding);
					StartIntent.startIntentWithParam(FavorFragment.this.getActivity(), BikeRidingListActivity.class, bundle);
				}
			}
			else if(getFavorKind()==0)
			{
			
				if(isDelete())
				{
					favorAdapter.changeCheck(position);
					if(changeHead!=null)
						changeHead.changeHead(favorAdapter.countSelect());
				}
				else 
				{			
					Bundle bundle=new Bundle();
					bundle.putSerializable(BikeStationInfoListFragment.BIKE_STATION_INFO,((StationFavorAdapter)parent.getAdapter()).getData().get(position));
					
					StartIntent.startIntentWithParam(getActivity(), BikeStationDetailActivity.class, bundle);
				}
			}
		}
	}
}
