package com.example.smarttraffic.train;

import java.util.Calendar;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.favor.OneItemAdapter;
import com.example.smarttraffic.favor.OneItemData;
import com.example.smarttraffic.util.DoString;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 搜索车次号
 * @author Administrator zhou
 *
 */
public class SearchTrainFragment extends Fragment
{
	EditText busNoTextView;
	TextView busTimeTextView;
	
	TextView busNoTextViewName;
	TextView item1TextView;
	TextView item2TextView;
	
	OneItemAdapter oneItemAdapter;
	ListView historyListView;
	
	
	Button searchButton;
	
	TrainNoRequest request;
	EditText et;
	
	int type;
	Calendar calendar;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_search_bus,container, false);
		
		request=new TrainNoRequest();
		init(rootView);
		
		
		type=1;
		
		return rootView;
	}
	
	private void init(View rootView)
	{
		busNoTextView=(EditText)rootView.findViewById(R.id.search_bus_no);
		busNoTextView.setHint("请输入车次号");
		busTimeTextView=(TextView)rootView.findViewById(R.id.search_bus_no_time);
		searchButton=(Button)rootView.findViewById(R.id.search_bus_no_button);
		
		busNoTextViewName=(TextView)rootView.findViewById(R.id.search_bus_no_name);
		busNoTextViewName.setText("列车车次");
		
		item1TextView=(TextView)rootView.findViewById(R.id.bus_no_real);
		item2TextView=(TextView)rootView.findViewById(R.id.bus_no_station);
		
		onclick c=new onclick();
		item1TextView.setOnClickListener(c);
		item2TextView.setOnClickListener(c);
		searchButton.setOnClickListener(c);
		busTimeTextView.setOnClickListener(c);
		
		historyListView=(ListView)rootView.findViewById(R.id.search_bus_no_history);
		new BaseAdapterLongClickWrapper(historyListView);
		
		
		calendar=Calendar.getInstance();
		request.setCalendar(calendar);
		
		updateTime();
	}
	
	public void updateTime()
	{
		busTimeTextView.setText(request.getCalendar().get(Calendar.MONTH)+1+"月"+request.getCalendar().get(Calendar.DAY_OF_MONTH)+"日  "+"星期"+DoString.GetWeekName(calendar));
	}
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) 
			{
				case R.id.search_bus_no:
//					onCreateDialog(2).show();
					break;
				case R.id.search_bus_no_time:
					onCreateDialog(1).show();
					break;
				case R.id.bus_no_real:
					exchangeImage(item1TextView, 1);
					break;
					
				case R.id.bus_no_station:
					exchangeImage(item2TextView, 2);
					break;
				case R.id.search_bus_no_button:
					search(busNoTextView.getText().toString());
												
					updateHistory();
			}
		}
	}
	

	public static final String SEARCH_NAME="bus_no_request";
	public static final String SEARCH_STATION_NAME="bus_station_request";
	public void search(String str)
	{
		if(str.equals(""))
		{
			Toast.makeText(getActivity(), "输入不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Bundle resultInfo=new Bundle();
		
		if(type==0 || type==1)
		{
			request.setCalendar(calendar);
			request.setBusNo(str);
			request.setType(type);
			
			resultInfo.putSerializable(SEARCH_NAME, request);
													
			StartIntent.startIntentWithParam(getActivity(), TrainRealActivity.class,resultInfo);	
		
		}
		else if(type==2)
		{
			resultInfo.putString(TrainStationInfoActivity.AIR_STATION_THROUGHT_LINE,str);
			StartIntent.startIntentWithParam(getActivity(), TrainStationInfoActivity.class,resultInfo);
		}

	}
	
	
	public Dialog onCreateDialog(int i)
	{
		Dialog d=null;
		if(i==1)
		{
			d=GetDialog.getDateDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener()
			{
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth)
				{
					calendar.set(Calendar.YEAR, year);
					calendar.set(Calendar.MONTH, monthOfYear);
					calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);	
					
					updateTime();
				}
			}, calendar);
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
	
	private void exchangeImage(TextView textView,int id)
	{
		Drawable drawable1= getResources().getDrawable(R.drawable.item_check);  
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());  
		
		Drawable drawable2= getResources().getDrawable(R.drawable.item_uncheck);  
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());  
		
		if(type==0)
		{
			textView.setCompoundDrawables(drawable1, null, null, null);
			type=id;
		}
		else if(type==1)
		{
			if(id==1)
			{
				textView.setCompoundDrawables(drawable2,null,null,null);
				type=0;
			}
			else if(id==2)
			{
				item1TextView.setCompoundDrawables(drawable2,null,null,null);
				item2TextView.setCompoundDrawables(drawable1,null,null,null);
				type=2;
			}
		}
		else if(type==2)
		{
			if(id==2)
			{
				textView.setCompoundDrawables(drawable2,null,null,null);
				type=0;
			}
			else if(id==1)
			{
				item1TextView.setCompoundDrawables(drawable1,null,null,null);
				item2TextView.setCompoundDrawables(drawable2,null,null,null);
				type=1;
			}
		}
	}
	
	private void updateHistory()
	{
		
		HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());

		String tempString=busNoTextView.getText().toString();
		
		if(tempString!="")
		{
			if(!dbOperate.isHistory(ContentType.TRAIN_STATION_NO_SEARCH_HISROTY, tempString))
				dbOperate.insert(ContentType.TRAIN_STATION_NO_SEARCH_HISROTY,tempString);
		}
		
		dbOperate.CloseDB();
	}
	
	class OnHistoryItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			
			search(((OneItemData)oneItemAdapter.getItem(position)).getData());						
		}
	}
	
	@Override
	public void onResume()
	{
		HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());
		List<OneItemData> datas;
		

		datas=dbOperate.selectForOneItem(ContentType.TRAIN_STATION_NO_SEARCH_HISROTY);	
		
		
		if(historyListView.getAdapter()!=null)
		{
			oneItemAdapter.update(datas);
		}
		else 
		{
			oneItemAdapter=new OneItemAdapter(getActivity(), datas);
			historyListView.setAdapter(oneItemAdapter);
			historyListView.setOnItemClickListener(new OnHistoryItemClick());
		}
		dbOperate.CloseDB();
		
		super.onResume();
	}
}
