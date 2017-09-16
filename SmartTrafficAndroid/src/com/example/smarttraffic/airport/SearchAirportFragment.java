package com.example.smarttraffic.airport;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.favor.OneItemAdapter;
import com.example.smarttraffic.favor.OneItemData;
import com.example.smarttraffic.util.StartIntent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchAirportFragment extends Fragment
{
	TextView busNoTextView;
	TextView busTimeTextView;
	
	TextView busNoTextViewName;
	TextView item1TextView;
	TextView item2TextView;
	
	OneItemAdapter oneItemAdapter;
	ListView historyListView;
	
	Button searchButton;
	
	EditText et;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_search_bus,container, false);
		
		init(rootView);
		isConnect=true;
		
		return rootView;
	}
	
	private void init(View rootView)
	{
		busNoTextView=(TextView)rootView.findViewById(R.id.search_bus_no);
		busNoTextView.setHint("请输入机场名");
		busTimeTextView=(TextView)rootView.findViewById(R.id.search_bus_no_time);
		searchButton=(Button)rootView.findViewById(R.id.search_bus_no_button);
		
		busNoTextViewName=(TextView)rootView.findViewById(R.id.search_bus_no_name);
		busNoTextViewName.setText("航班号  ");
		
		item1TextView=(TextView)rootView.findViewById(R.id.bus_no_real);
		item2TextView=(TextView)rootView.findViewById(R.id.bus_no_station);
		
		busNoTextViewName.setText("机场名称");
		item1TextView.setText("联网查询");
		item2TextView.setText("站名百科");
		
		onclick c=new onclick();
		item1TextView.setOnClickListener(c);
		item2TextView.setOnClickListener(c);
		searchButton.setOnClickListener(c);
		busTimeTextView.setOnClickListener(c);
		
		historyListView=(ListView)rootView.findViewById(R.id.search_bus_no_history);
		new BaseAdapterLongClickWrapper(historyListView);
		
		dialog=new GetDialog(busTimeTextView);	
		busTimeTextView.setText(dialog.getMonth()+1+"月"+dialog.getDay()+"日  "+"星期"+GetDialog.weeks[dialog.getWeek()]);
	}
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) 
			{
				case R.id.search_bus_no_time:
					onCreateDialog(1).show();
					break;
				case R.id.bus_no_real:
					isConnect=exchange(item1TextView, isConnect);
					break;
					
				case R.id.bus_no_station:
					isStation=exchange(item2TextView, isStation);
					break;
				case R.id.search_bus_no_button:
					search(busNoTextView.getText().toString());

					updateHistory();
			}
		}
	}
	

	public static final String SEARCH_NAME="air_no_request";
	public static final String SEARCH_STATION_NAME="air_station_request";
	public void search(String str)
	{
		Bundle resultInfo=new Bundle();
		if(str.equals(""))
		{
			Toast.makeText(getActivity(), "机场名称不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		else
		{			
			if(isStation)
			{
				resultInfo.putString(AirNoActivity.SEARCH_AIR_NO,str);
				StartIntent.startIntentWithParam(getActivity(), AirStationInfoActivity.class,resultInfo);
			}
			else
			{				
				resultInfo.putString(AirTicketsInfoActivity.AIR_PORT_NAME,str);
				resultInfo.putBoolean(AirTicketsInfoActivity.IS_AIR_PORT_SEARCH, true);
				StartIntent.startIntentWithParam(getActivity(), AirTicketsInfoActivity.class,resultInfo);	
			}
		}
	}
	
	GetDialog dialog;
	
	public Dialog onCreateDialog(int i)
	{
		Dialog d=null;
		if(i==1)
		{
			if(dialog==null)
				dialog=new GetDialog(busTimeTextView);
			d=dialog.getDateDialog(getActivity(),2);
		}
		else if(i==2)
		{
			et=null;
			et= new EditText(getActivity());
			d=GetDialog.editDialog(getActivity(), "输入",et, "确定", new selectOnclick(), "取消", null);
		}
		return d;
	}
	
	class selectOnclick implements android.content.DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which) 
		{
			busNoTextView.setText(et.getText());
		}
	}
	
	boolean isConnect;
	boolean isStation;
	private boolean exchange(TextView textView,int[] drawable,boolean status)
	{
		Drawable drawable1;
		if(status)
			drawable1= getResources().getDrawable(drawable[0]); 
		else
			drawable1=getResources().getDrawable(drawable[1]);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
		
		textView.setCompoundDrawables(drawable1, null, null, null);
		
		return !status;
	}
	
	private  boolean exchange(TextView textView,boolean status)
	{
		int[] resource=new int[]{R.drawable.item_uncheck,R.drawable.item_check};
		return exchange(textView, resource,status);
	}
	
	private void updateHistory()
	{
		String tempString=busNoTextView.getText().toString();
		
		if(tempString!="")
		{
			HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());
			
			if(!dbOperate.isHistory(ContentType.AIR_STATION_STATION_SEARCH_HISROTY, tempString))
				dbOperate.insert(ContentType.AIR_STATION_STATION_SEARCH_HISROTY,tempString);
			
			dbOperate.CloseDB();
		}
	}
	
	OnItemClickListener historyClickListener =new OnItemClickListener()
	{
		public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id)
		{
			search(oneItemAdapter.getData().get(position).getData());
		}
	};
	
	@Override
	public void onResume()
	{
		
		HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());
		List<OneItemData> datas=dbOperate.selectForOneItem(ContentType.AIR_STATION_STATION_SEARCH_HISROTY);
		
		if(historyListView.getAdapter()==null)
		{
			oneItemAdapter=new OneItemAdapter(getActivity(),datas);
			historyListView.setAdapter(oneItemAdapter);
			historyListView.setOnItemClickListener(historyClickListener);
		}
		else 
			oneItemAdapter.update(datas);
		
		dbOperate.CloseDB();
		
		super.onResume();
	}
}
