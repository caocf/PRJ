package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ghpublic.entity.PortName;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.TidePortAdapter;

public class TidePortActivity extends AbActivity
{
	private static final String TAG = TideActivity.class.getName();
	@AbIocView (id = R.id.relative_title_final,click = "click") RelativeLayout back;  //
	@AbIocView (id = R.id.port_list,click = "click") ListView port_list;   //
	
	private List<PortName> portlist = new ArrayList<PortName>();
	private TidePortAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tide_port);
		port_list.setOnItemClickListener(onitem);
		initDate();
	}
	public void initDate()
	{
		port_list.setDividerHeight(0);
		portlist.clear();
		for(int i = 0;i<2;i++){
			PortName pt = new PortName();
			if(i == 0){
				pt.setPortName("乍浦");
			}else{
				pt.setPortName("大连");
			}
			portlist.add(pt);
		}
		
		mAdapter = new TidePortAdapter(abApplication, portlist);
		mAdapter.notifyDataSetChanged();
		port_list.setAdapter(mAdapter);
	}
	
	/**
	 * 
	 */
	public OnItemClickListener onitem = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (parent.getId()){
			case R.id.port_list:
				expressItemClick(position);//position 代表你点的哪一个
				break;
			}
		}
		
		//
		private void expressItemClick(int position) 
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(TidePortActivity.this, TideActivity.class);
			intent.putExtra("port", portlist.get(position).getPortName());
			setResult(RESULT_OK,intent);
			finish();
		}
	};
	
	/**
	 * 
	 * @param v
	 */
	public void click(View v){
		switch (v.getId()) {
		case R.id.relative_title_final:
			finish();
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initDate();
	}
}
