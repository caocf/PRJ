package com.hxkg.ghpublic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import com.hztuen.gh.activity.MainFragmentAround;
import com.hztuen.gh.activity.MineChuanHuActivity;
import com.hztuen.gh.activity.MineMaTouActivity;
import com.hztuen.gh.activity.MineYanHaiaActivity;
import com.hztuen.gh.activity.ShipGoodsCircleActivity;
import com.hztuen.gh.activity.StartActivity;
import com.hztuen.gh.activity.UnLoginActivity;
import com.hztuen.lvyou.utils.SystemStatic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class OptionFragmentShipAndWharf extends Fragment
{
	private GridView gridView;	
	private SimpleAdapter adapter;
	private String[] fromdata={"icon","text"};
	private int[] toview={R.id.griditem_image,R.id.griditem_text};

	private int[] icon ={R.drawable.ic_selector_home,R.drawable.ic_selector_around,R.drawable.ic_selector_trade
			,R.drawable.ic_selector_personal};
	private String[] iconName ={"首页","周边","船货圈","我的"};
	//这里替换为各功能页面
	private Class<?>[] visitorActionPage = {null,MainFragmentAround.class,ShipGoodsCircleActivity.class
			,UnLoginActivity.class};//游客
	private Class<?>[] shipActionPage = {null,MainFragmentAround.class,ShipGoodsCircleActivity.class
			,MineChuanHuActivity.class};//船户
	private Class<?>[] wharfActionPage = {null,MainFragmentAround.class,ShipGoodsCircleActivity.class
			,MineMaTouActivity.class};//码头
	private Class<?>[] coastalActionPage = {null,MainFragmentAround.class,ShipGoodsCircleActivity.class
			,MineYanHaiaActivity.class};//沿海
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_optionbar, container,false);

		gridView=(GridView) rootView.findViewById(R.id.gridView);
		adapter=new SimpleAdapter(this.getActivity(),getData(),R.layout.gridview_item_optionbar,fromdata,toview);
		gridView.setAdapter(adapter);
		//船户和码头不同
		gridView.setOnItemClickListener(new ShipUserOnClickListener());
		//船户和码头不同

		return rootView;
	}

	private List<Map<String, Object>> getData()
	{
		List<Map<String, Object>> data_list=new ArrayList<Map<String, Object>>();
		for(int i=0;i<icon.length;i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}

		return data_list;
	}

	//船户点击事件
	class ShipUserOnClickListener implements OnItemClickListener 
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int index,long arg3) 
		{
			if(index==0)//当前为主页面，不需要跳转
				return;

			Intent intent = null;
			
			switch(SystemStatic.usertypeId)
			{
			case "0":intent = new Intent(getActivity(),visitorActionPage[index]);
			break;
			case "1":intent = new Intent(getActivity(),shipActionPage[index]);
			break;
			case "2":
			case "3":
			case "4":
			case "5":
			case "6":
			case "7":
			intent = new Intent(getActivity(),coastalActionPage[index]);
			break;
			case "null":intent = new Intent(getActivity(),StartActivity.class);
			break;
			}
			if(intent!=null)
				startActivity(intent);
		}		
	}
}
