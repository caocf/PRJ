package com.example.smarttraffic.alarm;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AlarmFavorFragment extends Fragment
{
	int type;
	boolean delete;
		
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public boolean isDelete()
	{
		return delete;
	}

	public void setDelete(boolean delete)
	{
		this.delete = delete;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View root=inflater.inflate(R.layout.fragment_smart_bus_favor, container, false);
		
		listView=(ListView)root.findViewById(R.id.smart_bus_favor_list);
		
		
		return root;
	}
	
	ListView listView;
	AlarmFavorAdapter alarmFavorAdapter;
	
	private void initList(int type,boolean isDelete)
	{
		int[] contentType=new int[]{ContentType.SMART_BUS_ALARM_FAVOR,ContentType.SMART_BUS_ALARM_DOWN_FAVOR};
		FavorDBOperate dbOperate=new FavorDBOperate(getActivity());
		
		alarmFavorAdapter=new AlarmFavorAdapter(getActivity(), dbOperate.selectForAlarmInfo(contentType[type]),delete);
		
		listView.setAdapter(alarmFavorAdapter);
		if(isDelete)
		{
			listView.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					((AlarmFavorAdapter)parent.getAdapter()).changeCheck(position);
				}
			});
		}

		dbOperate.CloseDB();		
	}
	
	public void deleteData()
	{
		if(delete)
		{
			List<AlarmInfo> dataAlarmInfos=alarmFavorAdapter.getData();
			FavorDBOperate dbOperate=new FavorDBOperate(getActivity());
			for(int i=0;i<dataAlarmInfos.size();i++)
			{
				if(dataAlarmInfos.get(i).isSelect)
				{
					dbOperate.delete(dataAlarmInfos.get(i).getId());
//					dataAlarmInfos.remove(i);
				}
			}
			dbOperate.CloseDB();
			
			initList(type, delete);
		}
	}
	
	@Override
	public void onResume()
	{
		initList(type, delete);

		super.onResume();
	}
}
