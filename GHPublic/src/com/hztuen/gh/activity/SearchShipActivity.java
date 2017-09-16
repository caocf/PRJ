package com.hztuen.gh.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast; 
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.SearchShipNameAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/*
 * 搜索船舶界面
 */

public class SearchShipActivity extends Activity implements OnClickListener
{
	private EditText edit_search;
	private ArrayList<String> shipName=new ArrayList<String>();
	
	 private List<String> ship_record_list=new ArrayList<String>();//船舶历史记录列表
	private ListView list_ship_name,list_ship_record;
	private RelativeLayout relative_title_final;
	private SearchShipNameAdapter nameAdapter,ship_search_record_adapter;
	private RelativeLayout relative_detail_info;
	private RelativeLayout relative_base_info,relative_cred_info,relative_break_rules,relative_deduct_points
							,relativie_baogang,relative_danger_record;
	private ImageView img_clear;
	
	private Editor ship_edit_record;
	
	
	private String weizhang,zhengshu,jiaofei;
	private ImageView img_zhengshu,img_weizhang;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_ship);
		init();
		
		
		
		//船舶搜索历史列表轻量级数据库
				SharedPreferences shared_ship_record= getSharedPreferences("ship_search_record", 
						Activity.MODE_PRIVATE); 
				
				ship_edit_record = shared_ship_record.edit(); 
				
				
				String Duck_name =shared_ship_record.getString("duck_record", ""); 
				
				if(Duck_name!=null)

				{
				
					try {
						ship_record_list=Util.String2SceneList(Duck_name);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	private void init()
	{
		edit_search=(EditText)findViewById(R.id.text1_context);
		
		View viewfooter = getLayoutInflater().inflate(
				R.layout.list_footer_clear, null);
		
		viewfooter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ship_edit_record.putString("duck_record", "");
				ship_edit_record.commit();
				ship_record_list.clear();
				ship_search_record_adapter.notifyDataSetChanged();
				
			}
		});
		
		
		img_zhengshu=(ImageView)findViewById(R.id.img_zhengshu);		
		img_weizhang=(ImageView)findViewById(R.id.img_weizhang);
		
		list_ship_name=(ListView)findViewById(R.id.list_ship_name);//船舶名字
		list_ship_record=(ListView)findViewById(R.id.list_ship_record);//历史记录
		list_ship_record.addFooterView(viewfooter);
		
		nameAdapter=new SearchShipNameAdapter(getApplicationContext(), shipName);
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		relative_detail_info=(RelativeLayout)findViewById(R.id.relative_detail_info);//被隐藏的部分
		
		relative_base_info=(RelativeLayout)findViewById(R.id.relative2);//基本信息		
		relative_base_info.setOnClickListener(this);
		
		relative_cred_info=(RelativeLayout)findViewById(R.id.relative3);//船舶证书		
		relative_cred_info.setOnClickListener(this);
		
		relative_break_rules=(RelativeLayout)findViewById(R.id.relative4);//违章证书		
		relative_break_rules.setOnClickListener(this);
		
		relative_deduct_points=(RelativeLayout)findViewById(R.id.relative5);//诚信扣分		
		relative_deduct_points.setOnClickListener(this);
		
		relative_deduct_points=(RelativeLayout)findViewById(R.id.relative5);//诚信扣分		
		relative_deduct_points.setOnClickListener(this);
		
		relativie_baogang=(RelativeLayout)findViewById(R.id.relative6);	//报港记录
		relativie_baogang.setOnClickListener(this);
		
		relative_danger_record=(RelativeLayout)findViewById(R.id.relative8);	//危货申报
		relative_danger_record.setOnClickListener(this);
		img_clear=(ImageView)findViewById(R.id.img_clear);
		img_clear.setOnClickListener(this);
		
		img_clear.setVisibility(View.INVISIBLE);
		//监听文字变化
		edit_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				relative_detail_info.setVisibility(View.GONE);
				list_ship_name.setVisibility(View.VISIBLE);
				
				if("".equals(edit_search.getText().toString()))
				{
					img_clear.setVisibility(View.INVISIBLE);
				}else{
				GetShipListTask();
				img_clear.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		//搜索输入框获取焦点与失去焦点事件监听
				edit_search.setOnFocusChangeListener(new android.view.View.
						OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (hasFocus) {
							//获取焦点
							
							shipName.clear();
							
							
							if("".equals(edit_search.getText().toString()))//获取焦点且内容为空的时候显示历史记录
							{

								
								
								list_ship_record.setVisibility(View.VISIBLE);
								ship_search_record_adapter = new SearchShipNameAdapter(getApplicationContext(),ship_record_list);
								list_ship_record.setAdapter(ship_search_record_adapter);
								setListViewHeight(list_ship_record);
							}
						//	duck_record_list.clear();
						} else {
							//失去焦点
							//showPopAre();
							
							
							
							
						}
					}
				});
				

//		//软键盘点击事件
//		edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEND
//						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//					// do something;
//					relative_detail_info.setVisibility(View.GONE);
//					list_ship_name.setVisibility(View.VISIBLE);
//					GetShipListTask();
//					return true;
//				}
//				return false;
//			}
//		});
		
		//点击listview的item
		list_ship_name.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("GH_TEXT", "item点击事件");
				edit_search.setText(shipName.get(position).toString());
				relative_detail_info.setVisibility(View.VISIBLE);
				list_ship_name.setVisibility(View.GONE);
				
				getItemStateTask(shipName.get(position).toString());
				
				//如果历史列表中又相同的历史记录，移除原来的记录，添加新的记录
				for(int i=0;i<ship_record_list.size();i++)
				{
					if(ship_record_list.get(i).toString().equals(shipName.get(position).toString()))
					{
						ship_record_list.remove(i);
					}
				}
				
				
				ship_record_list.add(0,shipName.get(position).toString());
				try {
					ship_edit_record.putString("duck_record", Util.SceneList2String(ship_record_list));
					ship_edit_record.commit();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		
		//历史列表item点击事件
				list_ship_record.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

								relative_detail_info.setVisibility(View.VISIBLE);
								list_ship_name.setVisibility(View.GONE);
								getItemStateTask(ship_record_list.get(position).toString());
								
							}
						});
						
						
	}
	
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//基本信息
		case R.id.relative2:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_base_info=new Intent(getApplicationContext(),SearchShipBaseInfoActivity.class);
			startActivity(intent_base_info);
			break;
		//返回按钮
		case R.id.relative_title_final:
			finish();
			break;
		//船舶证书	
		case R.id.relative3:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_cred_info=new Intent();
			intent_cred_info.setClass(getApplicationContext(),CredCardInfoActivity.class);
			intent_cred_info.putExtra("type", "船舶证书");
			startActivity(intent_cred_info);
			break;
			
		//违章信息	
		case R.id.relative4:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_break_info=new Intent(getApplicationContext(),BreakRulesActivity.class);
			startActivity(intent_break_info);
			break;
			
		//诚信扣分	
		case R.id.relative5:
			SystemStatic.searchShipName=edit_search.getText().toString();
			
			
			Intent intent_deduct_info = new Intent();
					
			
			intent_deduct_info.setClass(getApplicationContext(), DeductPointsActivity.class);
			intent_deduct_info.putExtra("PointsType", "船舶扣分");
			startActivity(intent_deduct_info);
			break;
			
		//报港记录	
		case R.id.relative6:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_baogang=new Intent(getApplicationContext(),BaoGangActivity.class);
			startActivity(intent_baogang);
			break;
			
		//危货申报	
		case R.id.relative8:
			SystemStatic.searchShipName=edit_search.getText().toString();
			Intent intent_danger=new Intent(getApplicationContext(),LetInRecordActivity.class);
			startActivity(intent_danger);
			break;
		//点击清除按钮
		case R.id.img_clear:
			relative_detail_info.setVisibility(View.GONE);
			list_ship_name.setVisibility(View.VISIBLE);
			edit_search.setText("");
			
			shipName.clear();
			nameAdapter.notifyDataSetChanged();
			
			list_ship_record.setVisibility(View.GONE);
			edit_search.clearFocus();//失去焦点
			
			break;
		default:
			break;
		}
		
	}
	
	// 按提示文字获取船名列表
	private void GetShipListTask() {

		// TODO Auto-generated method stub

		// 访问网络

		String tip = edit_search.getText().toString();
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("tip=" + tip);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.baseUrl+"ShipNamesByTip";
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try {

						shipName.clear();
						list_ship_record.setVisibility(View.GONE);
						JSONObject res = new JSONObject(result);
						//JSONObject res=JSONObject.
						JSONArray data = res.getJSONArray("data"); 
						if (data.length() == 0) 
						{
							Toast.makeText(getApplicationContext(), "无信息",
									Toast.LENGTH_SHORT).show();
							shipName.clear(); 
							nameAdapter.notifyDataSetChanged();
						} else 
						{
							String name = data.toString().replace("[", "")
									.replace("]", "").replace("\"", "");

							String[] aa = name.split("\\,");
							
							for (int i = 0; i < aa.length; i++) 
							{
								shipName.add(aa[i]);
							}
							list_ship_name.setAdapter(nameAdapter);
							setListViewHeight(list_ship_name);
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
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
	
	
	//判断证书，违章，缴费状态
	private void getItemStateTask(final String shipname) {

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Shipname=" + shipname);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.shipcheck;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					// Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try {

						JSONObject res = new JSONObject(result);
						JSONObject ress = res.getJSONObject("result");
						JSONObject dataList = ress.getJSONObject("dataList");
						Log.i("GH_TEXT", dataList.length() + "我是数据的size");
						if (dataList.length() == 0) {
							Toast.makeText(getApplicationContext(), "",
									Toast.LENGTH_SHORT).show();
						} else {
							JSONArray json_jiaofei = dataList
									.getJSONArray("data4");
							 jiaofei = json_jiaofei.getString(1)
									.toString();

							JSONArray json_weizhang = dataList
									.getJSONArray("data0");
							 weizhang = json_weizhang.getString(1)
									.toString();

							JSONArray json_zhengshu = dataList
									.getJSONArray("data2");
							 zhengshu = json_zhengshu.getString(1)
									.toString();
							 
							 
							 StateChoose(zhengshu,weizhang);
						}
							
					} catch (Exception e1) {
						e1.printStackTrace();
						
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
					
				}
			});
		}
	}
	
	//z感叹号判断
	public void StateChoose(String zhenghsu,String weizhang)
	{
		if ("0".equals(zhengshu)) {
			img_zhengshu.setVisibility(View.GONE);			
		} else if ("1".equals(zhengshu)) {
			img_zhengshu.setVisibility(View.VISIBLE);	
		} else if ("2".equals(zhengshu)) {
			img_zhengshu.setVisibility(View.VISIBLE);	
		}else{
			img_zhengshu.setVisibility(View.VISIBLE);	
		}
				
		
		if ("0".equals(weizhang)) {
			img_weizhang.setVisibility(View.GONE);			
		} else if ("1".equals(weizhang)) {
			img_weizhang.setVisibility(View.VISIBLE);	
		} else if ("2".equals(weizhang)) {
			img_weizhang.setVisibility(View.VISIBLE);	
		}else{
			img_weizhang.setVisibility(View.VISIBLE);	
		}
		
	}
	
}
