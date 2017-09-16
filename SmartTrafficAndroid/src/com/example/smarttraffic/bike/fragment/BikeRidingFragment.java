package com.example.smarttraffic.bike.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeFavorActivity;
import com.example.smarttraffic.bike.SelectPointActivity;
import com.example.smarttraffic.bike.adapter.BikeHistoryAdapter;
import com.example.smarttraffic.bike.bean.BikeRiding;
import com.example.smarttraffic.bike.db.BikeHistoryDB;
import com.example.smarttraffic.common.adapter.BaseAdapterLongClickWrapper;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;
import com.example.smarttraffic.view.InputListView;
import com.example.smarttraffic.view.InputListViewListener;
import com.example.smarttraffic.view.VoiceListener;

/**
 * 骑行出行界面
 * @author Administrator zhou
 *
 */
public class BikeRidingFragment extends Fragment {
	
	BikeHistoryAdapter historyAdapter;
	ListView historyListView;
	InputListView startInputListView;
	InputListView endInputListView;
	Button searchButton;
	TextView clearTextView;
	ImageView exchangeImageView;
	
	BikeRiding bikeRiding;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bike_riding,container, false);
	
		HeadFragment headFragment=new HeadFragment();
		headFragment.setTitleName("骑行规划");		
		ManagerFragment.replaceFragment(getActivity(), R.id.trip_driving_self_head, headFragment);
			
		bikeRiding= new BikeRiding();
		
		initView(rootView);
		return rootView;
	}
	
	/**
	 * 初始化视图
	 * @param rootView
	 */
	private void initView(View rootView)
	{
		historyListView=(ListView)rootView.findViewById(R.id.trip_driving_self_history);
		searchButton=(Button)rootView.findViewById(R.id.trip_driving_self_search);
		endInputListView=(InputListView)rootView.findViewById(R.id.trip_driving_self_end);
		startInputListView=(InputListView)rootView.findViewById(R.id.trip_driving_self_start);		
		clearTextView=(TextView)rootView.findViewById(R.id.trip_driving_self_clear);
		exchangeImageView=(ImageView)rootView.findViewById(R.id.bus_search_exchange);
		
		startInputListView.setHint(getResources().getString(R.string.string_trip_enter_start_city_label));
		endInputListView.setHint(getResources().getString(R.string.string_trip_enter_end_city_label));
		
		onclick click=new onclick();
		searchButton.setOnClickListener(click);
		clearTextView.setOnClickListener(click);
		exchangeImageView.setOnClickListener(click);
		
		historyListView.setOnItemClickListener(new onHistoryItemClick());
		new BaseAdapterLongClickWrapper(historyListView);
		
		startInputListView.setListeners(new VoiceListener(getActivity(), startInputListView),0);
		endInputListView.setListeners(new VoiceListener(getActivity(), endInputListView), 0);	
		startInputListView.setListeners(new inputFavorListener(),1);
		endInputListView.setListeners(new inputFavorListener(), 1);		
		startInputListView.setListeners(new inputLocationListener(startInputListView,1), 2);
		endInputListView.setListeners(new inputLocationListener(endInputListView,2), 2);

	}
	
	/**
	 * 历史记录内容点击事件监听
	 */
	class onHistoryItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			bikeRiding=((BikeHistoryAdapter)parent.getAdapter()).getData().get(position);
			search(bikeRiding.getStart(), bikeRiding.getEnd());
		}
	}
	
	public static final String DRIVING_INFO="driving_info";
	public static final String STATEGY="stategy";
	
	/**
	 * 视图点击监听事件
	 *
	 */
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				
			case R.id.trip_driving_self_clear:
				deleteHistory();
				loadHistory();
				break;
				
			case R.id.bus_search_exchange:
				ViewSetter.exchangeInputlistValue(startInputListView, endInputListView);
				break;
				
			case R.id.trip_driving_self_search:
				saveHistory();
				search(startInputListView.getText(), endInputListView.getText());
				break;
			}			
		}
	}
	
	
	private void search(String start,String end)
	{	
		if(start.equals(""))
		{
			Toast.makeText(getActivity(), "当前起点为空", Toast.LENGTH_SHORT).show();
		}
		else if(end.equals(""))
		{
			Toast.makeText(getActivity(), "当前终点为空", Toast.LENGTH_SHORT).show();
		}
		else
		{
			bikeRiding.setStart(start);			
			bikeRiding.setEnd(end);
			
			Bundle bundle=new Bundle();
			bundle.putSerializable(SelectPointActivity.BIKE_RIDING_INFO, bikeRiding);
			StartIntent.startIntentWithParam(getActivity(), SelectPointActivity.class,bundle);
		}
	}
	
	/**
	 * 加载搜索历史记录
	 */
	private void loadHistory()
	{
		BikeHistoryDB dbOperate=new BikeHistoryDB(getActivity());
		
		if(historyListView.getAdapter()==null)
		{
			historyAdapter=new BikeHistoryAdapter(getActivity(), dbOperate.selectForBikeRiding());
			historyListView.setAdapter(historyAdapter);
		}
		else
		{
			historyAdapter.update(dbOperate.selectForBikeRiding());
		}
		
		dbOperate.CloseDB();
	}
	
	/**
	 * 删除搜索历史记录
	 */
	private void deleteHistory()
	{
		HistoryDBOperate dbOperate=new HistoryDBOperate(getActivity());
		dbOperate.deleteAll(ContentType.BIKE_RIDING_DRIVING);
		dbOperate.CloseDB();
		
		clearTextView.setVisibility(View.GONE);
		getActivity().findViewById(R.id.trip_history_name).setVisibility(View.GONE);
	}
	
	/**
	 * 保存搜索历史记录
	 */
	private void saveHistory()
	{
		String start=startInputListView.getText();
		String end=endInputListView.getText();
		
		BikeRiding bikeRiding=new BikeRiding();
		bikeRiding.setStart(start);
		bikeRiding.setEnd(end);
		
		if(start !="" && end !="")
		{
			BikeHistoryDB dbOperate=new BikeHistoryDB(getActivity());
			
			if(!dbOperate.isHistoryRiding(bikeRiding))
			{
				dbOperate.insertRiding(bikeRiding);				
			}
			dbOperate.CloseDB();
		}
	}
	
	
	public static final String MY_LOCATION="我的位置";
	
	/**
	 * 输入选项监听函数
	 *
	 */
	class inputLocationListener implements InputListViewListener
	{
		private InputListView inputListView;
		private int id;
		
		public inputLocationListener(InputListView inputListView,int id)
		{
			this.inputListView=inputListView;
			this.id=id;
		}
		
		@Override
		public void inputSelectListener()
		{
			inputListView.setText(MY_LOCATION);	
			inputListView.showList(false);
			myLocationExchangeLock(id);
		}
	}
	
	/**
	 * 
	 * @param i
	 */
	public void myLocationExchangeLock(int i)
	{
		String start=startInputListView.getText();
		String end=endInputListView.getText();
		
		if(i==1 && end.equals(MY_LOCATION))
		{
			endInputListView.setText("");
		}
		else if(i==2 && start.equals(MY_LOCATION))
		{
			startInputListView.setText("");
		}
	}
	
	class inputFavorListener implements InputListViewListener
	{		
		@Override
		public void inputSelectListener()
		{
			Bundle bundle=new Bundle();
			bundle.putInt(BikeFavorActivity.INIT_PAGER, 0);
			
			StartIntent.startIntentWithParam(getActivity(), BikeFavorActivity.class,bundle);
		}
	}
	
	@Override
	public void onResume()
	{
		loadHistory();
		
		if(historyAdapter.getCount()==0)
		{
			clearTextView.setVisibility(View.GONE);
			getActivity().findViewById(R.id.trip_history_name).setVisibility(View.GONE);
		}
		else
		{
			clearTextView.setVisibility(View.VISIBLE);
			getActivity().findViewById(R.id.trip_history_name).setVisibility(View.VISIBLE);
		}
		super.onResume();
	}
}

