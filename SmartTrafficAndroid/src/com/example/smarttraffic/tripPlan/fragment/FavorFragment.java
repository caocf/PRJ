package com.example.smarttraffic.tripPlan.fragment;

import java.util.List;





import com.example.smarttraffic.ChangeHead;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.favor.TwoItemsAdapter;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.tripPlan.TripLineMapActivity;
import com.example.smarttraffic.tripPlan.bean.DrivingPlan;
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
	TwoItemsAdapter adapter;
	ImageView deleteButton;
	
	/**
	 * 获取收藏线路数量
	 * @return 数量
	 */
	public int getCount()
	{
		return adapter.getCount();
	}
	
	ChangeHead changeHead;
	public void setChangeHead(ChangeHead changeHead)
	{
		this.changeHead = changeHead;
	}
	public ChangeHead getChangeHead()
	{
		return changeHead;
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
			List<TwoItemsData> datas=adapter.getData();
			FavorDBOperate dbOperate=new FavorDBOperate(getActivity());
			for(int i=0;i<datas.size();i++)
			{
				TwoItemsData temp=datas.get(i);
				if(temp.isSelect())
				{
					dbOperate.delete(temp.getId());
					datas.remove(i--);
				}
			}
			dbOperate.CloseDB();
			adapter.update(datas);
			if(changeHead!=null)
				changeHead.changeHead(0);
		}
	}
	
	private int favorKind;
	private boolean isDelete;
	
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
		List<TwoItemsData> datas;		
		
		if(id==0)
		{
			datas=new FavorDBOperate(getActivity()).selectForTwoItem(ContentType.TRIP_SELF_FAVOR);
		}
		else 
		{
			datas=new FavorDBOperate(getActivity()).selectForTwoItem(ContentType.TRIP_COMMON_FAVOR);
		}
		
		if(adapter==null)
		{
			adapter=new TwoItemsAdapter(getActivity(), datas,isDelete);
			listView.setAdapter(adapter);	
			listView.setOnItemClickListener(new itemOnclick());
		}
		else 
		{
			adapter.update(datas);
		}
	}
	
	/**
	 * 全选
	 */
	public void selectAll()
	{
		adapter.selectALL(true);
		if(changeHead!=null)
			changeHead.changeHead(adapter.countSelect());
	}
	
	/**
	 * 取消全选
	 */
	public void clearAll()
	{
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
			if(isDelete())
			{
				adapter.changeCheck(position);
				
				if(changeHead!=null)
					changeHead.changeHead(adapter.countSelect());
			}
			else 
			{								
				DrivingPlan plan=new DrivingPlan();
				plan.setStart(adapter.getData().get(position).getStart());
				plan.setEnd(adapter.getData().get(position).getEnd());
				
				int lan1=adapter.getData().get(position).getLan1();
				int lan2=adapter.getData().get(position).getLan2();
				int lon1=adapter.getData().get(position).getLon1();
				int lon2=adapter.getData().get(position).getLon2();
				
				if(lan1!=0 &&lon1!=0 &&lan2!=0&& lon2!=0)
				{
					plan.setLan1(lan1);
					plan.setLan2(lan2);
					plan.setLon1(lon1);
					plan.setLon2(lon2);
				}
				plan.setStrategy(0);
				
				Bundle bundle=new Bundle();
				bundle.putSerializable(TripDrivingSelfFragment.DRIVING_INFO, plan);
				StartIntent.startIntentWithParam(getActivity(), TripLineMapActivity.class,bundle);
			}
		}
	}
}
