package com.hztuen.gh.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.ghpublic.entity.CompanyInfoEntity;
import com.ghpublic.entity.CompanyNameEntity;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.CompanySearchAdapter;
import com.hztuen.gh.activity.Adapter.SearchShipNameAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;

/**
 * @author zzq
 * @DateTime 2016年7月19日 下午4:29:16
 * 企业信息
 */
public class CompanyInfoActivity extends AbActivity{
	private final static String TAG = CompanyInfoActivity.class.getSimpleName();

	@AbIocView(id = R.id.back,click="btnClick")TextView back;
	@AbIocView(id = R.id.edt_search) EditText edt_search;
	@AbIocView(id = R.id.listview) ListView listview;
	@AbIocView(id = R.id.txt_clear,click="btnClick") ImageView txt_clear;

	private List<CompanyNameEntity> CIE = new ArrayList<CompanyNameEntity>();//listview 的实体类
	private String sea_name;
	private CompanySearchAdapter adapter,adapter_record;
	private ListView listview_record;
	
	private Editor comp_edit_record;
	
	private List<String> comp_record_list=new ArrayList<String>();//公司历史记录列表
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_company_info);
		//监听文字变化
		edt_search.addTextChangedListener(watcher);
		adapter = new CompanySearchAdapter(getApplicationContext(), CIE);
		listview.setDividerHeight(0);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(onitem);
		
		
		
		listview_record=(ListView)findViewById(R.id.listview_record);
		listview_record.setOnItemClickListener(onitem);
		
		View viewfooter = getLayoutInflater().inflate(
				R.layout.list_footer_clear, null);
		
		viewfooter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				comp_edit_record.putString("comp_record", "");
				comp_edit_record.commit();
				CIE.clear();
				
				comp_record_list.clear();
				adapter_record.notifyDataSetChanged();
				
			}
		});
		
		listview_record.addFooterView(viewfooter);
		
		
		//公司搜索历史列表轻量级数据库
		SharedPreferences shared_comp_record= getSharedPreferences("comp_search_record", 
				Activity.MODE_PRIVATE); 
		
		comp_edit_record = shared_comp_record.edit(); 
		
		
		String Duck_name =shared_comp_record.getString("comp_record", ""); 
		
		if(Duck_name!=null)

		{
		
			try {
				comp_record_list=Util.String2SceneList(Duck_name);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		//搜索输入框获取焦点与失去焦点事件监听
		edt_search.setOnFocusChangeListener(new android.view.View.
				OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					//获取焦点
					
					CIE.clear();
					
					
					if("".equals(edt_search.getText().toString()))//edit失去焦点且内容为空的时候显示历史记录
					{

						
						listview.setVisibility(View.GONE);
						listview_record.setVisibility(View.VISIBLE);
						
						for(int i=0;i<comp_record_list.size();i++)
						{
							CompanyNameEntity cne = new CompanyNameEntity();
							cne.setCompanyName(comp_record_list.get(i).toString());
							CIE.add(cne);
						}
						
						adapter_record = new CompanySearchAdapter(getApplicationContext(),CIE);
						listview_record.setAdapter(adapter_record);
						setListViewHeight(listview_record);
					}
				//	duck_record_list.clear();
				} else {
					//失去焦点
					//showPopAre();
					
					
					
					
				}
			}
		});
		
		
		
	}
	public TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			sea_name = edt_search.getText().toString();
			if(!sea_name.equals("")){
				CIE.clear();
				initData();
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
	};
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.txt_clear:
			edt_search.setText("");
			
			CIE.clear();
			adapter.notifyDataSetChanged();
			
			listview.setVisibility(View.GONE);
			listview_record.setVisibility(View.GONE);
			edt_search.clearFocus();//失去焦点
			break;
		default:
			break;
		}
	}
	public OnItemClickListener onitem = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()){
			case R.id.listview:
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), CompanyDetialActivity.class);
				
				
				
				//如果历史列表中又相同的历史记录，移除原来的记录，添加新的记录
				for(int i=0;i<comp_record_list.size();i++)
				{
					if(comp_record_list.get(i).toString().equals(CIE.get(position).getCompanyName()))
					{
						comp_record_list.remove(i);
					}
				}
				
				comp_record_list.add( 0,CIE.get(position).getCompanyName());
				
				try {
					comp_edit_record.putString("comp_record", Util.SceneList2String(comp_record_list));
					comp_edit_record.commit();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				intent.putExtra("companyName", CIE.get(position).getCompanyName());
				startActivity(intent);
				break;
			//历史列表记录	
			case R.id.listview_record:
				
				Intent intent_record = new Intent();
				intent_record.setClass(getApplicationContext(), CompanyDetialActivity.class);
				intent_record.putExtra("companyName", CIE.get(position).getCompanyName());
				startActivity(intent_record);
				break;
			}
		}
		
	};
	public void initData(){
		//访问网络
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Tip="+sea_name);
		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.companynames;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kj.post(toUrl, params, false, new HttpCallBack(){
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try{
						listview.setVisibility(View.VISIBLE);
						listview_record.setVisibility(View.GONE);
						CIE.clear();
						JSONObject res = new JSONObject(result);
						JSONArray data = res.getJSONArray("data");
						for(int i = 0;i<data.length();i++){
							CompanyNameEntity cne = new CompanyNameEntity();
							cne.setCompanyName(data.getString(i));
							CIE.add(cne);
						}
						adapter.notifyDataSetChanged();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			});
		}//else这里结束
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
}
