package net.hxkg.ship;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.tuen.util.SystemStatic;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SearchShipActivity extends Activity implements onResult,TextWatcher
{
	private EditText edit_search;
	private ArrayList<String> shipName=new ArrayList<String>();
	private ListView list_ship_name;
	private SearchShipNameAdapter nameAdapter;
	private RelativeLayout relative_detail_info;
	
	SharedPreferences spPreferences;
	Set<String> set=new HashSet<>();
	SharedPreferences.Editor editor;
	
	ImageView warn;
	View viewfooter;
	
	String selectnameString=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_ship);
		spPreferences=getSharedPreferences("Shipname", 0);
		set=spPreferences.getStringSet("shipname", set);
		editor=spPreferences.edit();
		
		init();
		//地图跳转判断
		selectnameString=getIntent().getStringExtra("shipnameofmap");
		
		if(selectnameString!=null&&!("".equals(selectnameString)))
		{
			ShowInfo();
		}
		
	}
	private void init()
	{
		edit_search=(EditText)findViewById(R.id.text1_context);
		
		viewfooter= getLayoutInflater().inflate(R.layout.list_footer_clear, null);		
		list_ship_name=(ListView)findViewById(R.id.list_ship_name);
		list_ship_name.addFooterView(viewfooter);		
		nameAdapter=new SearchShipNameAdapter(getApplicationContext(), shipName);
		//历史搜索列表
		for(String name:set)
		{
			shipName.add(name);
		}
		if(shipName.size()>0)
		viewfooter.setVisibility(View.VISIBLE);
		else 
		{
			viewfooter.setVisibility(View.GONE);
		}
		nameAdapter.setHistory(true);
		list_ship_name.setAdapter(nameAdapter);
		//被隐藏的船舶详情
		relative_detail_info=(RelativeLayout)findViewById(R.id.relative_detail_info);		
		
		warn=(ImageView) findViewById(R.id.warn);
		
		//软键盘点击事件
		edit_search.addTextChangedListener(this);		
		
		//点击listview的item
		list_ship_name.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			{
				//清空历史列表
				if(position==shipName.size())
				{
					set.clear();
					shipName.clear();
					nameAdapter.notifyDataSetChanged();
					viewfooter.setVisibility(View.GONE);
					return;
				}
				selectnameString=shipName.get(position);
				ShowInfo();
			}
		});
	}
	public void onSearch(View view)
	{
		
	}
	
	private void ShowInfo()
	{
		edit_search.setText(selectnameString);
		relative_detail_info.setVisibility(View.VISIBLE);
		list_ship_name.setVisibility(View.GONE);			
		set.add(selectnameString);
		
		Map<String, Object> map =new HashMap<>();
		map.put("Shipname", selectnameString);
		HttpRequest hr=new HttpRequest(new onResult()
		{
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject object=new JSONObject(result);
					JSONObject data=object.getJSONObject("result");
					JSONObject data1=data.getJSONObject("dataList");
					JSONArray array=data1.getJSONArray("data2");
					String n=(String)array.get(1);
					
					if(Integer.valueOf(n)>0)
					{
						warn.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(int httpcode) {
				
			}
			
		});
	}
	
	public void onViewClick(View v) 
	{
		switch (v.getId()) 
		{		
		//返回按钮
		case R.id.back:
			finish();
			break;
		//基本信息
		case R.id.relative2:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_base_info=new Intent(getApplicationContext(),SearchShipBaseInfoActivity.class);
			startActivity(intent_base_info);
			break;		
		//船舶证书	
		case R.id.relative3:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_cred_info=new Intent(getApplicationContext(),CredCardInfoActivity.class);
			startActivity(intent_cred_info);
			break;
			
		//违章信息	
		case R.id.relative4:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_break_info=new Intent(getApplicationContext(),BreakRulesActivity.class);
			startActivity(intent_break_info);
			break;
			
		//缴费	
		case R.id.relative5:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_deduct_info=new Intent(getApplicationContext(),ChargeListActivity.class);
			startActivity(intent_deduct_info);
			break;
			
		//检验
		case R.id.relative6:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_baogang=new Intent(getApplicationContext(),CheckListActivity.class);
			startActivity(intent_baogang);
			break;
			
		//诚信扣分
		case R.id.relative7:
			/*SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_danger=new Intent(getApplicationContext(),ShipDangerListActivity.class);
			startActivity(intent_danger);*/
			break;
		//点击清除按钮
		case R.id.img_clear:
			relative_detail_info.setVisibility(View.GONE);
			list_ship_name.setVisibility(View.VISIBLE);
			edit_search.setText("");
			break;
		default:
			break;
		}
		
	}
	
	// 按提示文字获取船名列表
	private void GetShipListTask() 
	{
		String tip = edit_search.getText().toString();
		String toUrl = Constants.BaseURL+"ShipNamesByTip";
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("tip", tip);
		hr.post(toUrl, map);		
	}

	// 计算listview高度
	public void setListViewHeight(ListView listView) {

		// 获取ListView对应的Adapter

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	//船名列表结果
	@Override
	public void onSuccess(String result)
	{
		try {

			shipName.clear();

			JSONObject res = new JSONObject(result);
			//JSONObject res=JSONObject.
			JSONArray data = res.getJSONArray("obj");
			if (data.length() == 0)
			{
				Toast.makeText(getApplicationContext(), "无信息",
						Toast.LENGTH_SHORT);//.show();
			} else
			{			
				for (int i = 0; i < data.length(); i++) 
				{
					String name = data.getString(i);
					shipName.add(name.trim());					
				}
				viewfooter.setVisibility(View.GONE);
				nameAdapter.setHistory(false);
				//list_ship_name.setAdapter(nameAdapter);
				nameAdapter.notifyDataSetChanged();
				setListViewHeight(list_ship_name);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void afterTextChanged(Editable s) 
	{
		if("".equals(s.toString()))
		{
			shipName.clear();
			//shipName=new ArrayList<>(set);
			for(String name:set)
			{
				shipName.add(name);
			}
			if(shipName.size()>0)
				viewfooter.setVisibility(View.VISIBLE);
			else {
				viewfooter.setVisibility(View.GONE);
			}
			nameAdapter.setHistory(true);
			nameAdapter.notifyDataSetChanged();
			setListViewHeight(list_ship_name);
			return;
		}
		relative_detail_info.setVisibility(View.GONE);
		list_ship_name.setVisibility(View.VISIBLE);
		GetShipListTask();
		
	}
	@Override
	protected void onDestroy() 
	{
		editor.putStringSet("shipname", set);
		editor.commit();
		super.onDestroy();
	}
	
}
