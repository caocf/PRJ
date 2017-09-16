package com.hxkg.ghpublic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.hxkg.mainfragment.MainFramentNewsActivity;
import com.hztuen.gh.activity.AISDynamicActivity;
import com.hztuen.gh.activity.CallActivity; 
import com.hztuen.gh.activity.DuckDangersActivity;
import com.hztuen.gh.activity.PermissionInformationActivity; 
import com.hztuen.gh.activity.TideActivity;
import com.hxkg.activity.ideabox.IdeaBoxActivity;
import com.hxkg.activity.water.WaterInfoActivity;
public class MainFragmentCoastal extends Fragment implements OnItemClickListener
{
	private GridView gridView;	
    private SimpleAdapter adapter;
    private String[] fromdata={"icon","text"};
    private int[] toview={R.id.griditem_image,R.id.griditem_text};
    
    private int[] icon ={R.drawable.ic_portnews,R.drawable.ic_notify,R.drawable.ic_water,R.drawable.ic_alarm
    		,R.drawable.ic_advice
    		,R.drawable.ic_permission
    		,R.drawable.ic_tide
			,R.drawable.ic_ais
			,R.drawable.ic_degoods/*,R.drawable.ic_company*/
			/*,R.drawable.ic_ship	*/		};
    private String[] iconName ={"港航新闻","通知公告","水文信息","电话报警","意见箱","许可信息","潮汐信息","AIS动态"
    							,"危货作业"/*,"船舶查询"*/};
    //这里替换为各功能页面
    private Class<?>[] actionPage = {MainFramentNewsActivity.class,MainFramentNewsActivity.class
    		,WaterInfoActivity.class,CallActivity.class,IdeaBoxActivity.class
    		,PermissionInformationActivity.class
    								,TideActivity.class
    								,AISDynamicActivity.class
    								,/*CompanyInfoActivity.class ,*/DuckDangersActivity.class
    								/*,SearchShipActivity.class*/};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
		
		gridView=(GridView) rootView.findViewById(R.id.gridView);
		adapter=new SimpleAdapter(this.getActivity(),getData(),R.layout.gridview_item_main,fromdata,toview);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		
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
	
	//各个模块业务跳转
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) 
	{
		Intent intent = null;
		intent = new Intent(getActivity(),actionPage[index]);
		if(index==0)//新闻
		{
			intent.putExtra("model", 0);
		}else
		if(index==1)//通知
		{
			intent.putExtra("model", 1);
		}
		startActivity(intent);		
				
	}
}
