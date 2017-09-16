package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.DuckDangerRecordModel; 
import com.hxkg.company.CompanySpinner;
import com.hxkg.ghpublic.R; 
import com.hztuen.gh.activity.Adapter.DuckRecordAdapter; 
import com.hztuen.gh.contact.contants; 
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
/*
 * 码头危货作业记录
 */
public class DuckDangersRecordActivity extends Activity implements OnClickListener,OnItemSelectedListener
{
	private ListView lv_record;
	
	private RelativeLayout relative_title;
	private DuckRecordAdapter dangerDuckAdapter;
	
	private List<DuckDangerRecordModel> recordlist = new ArrayList<DuckDangerRecordModel>();
	CompanySpinner companySpinner;
	String comid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danger_duck_record);
		init();
	}
	private void init()
	{
		comid=getIntent().getStringExtra("comid");
		
		lv_record=(ListView)findViewById(R.id.listview_record); 	
		
		relative_title=(RelativeLayout)findViewById(R.id.relative_title);
		relative_title.setOnClickListener(this);
		
		dangerDuckAdapter=new DuckRecordAdapter(this, recordlist);
		lv_record.setAdapter(dangerDuckAdapter);
		companySpinner=(CompanySpinner) findViewById(R.id.company);
		companySpinner.setID(comid);
		companySpinner.setOnItemSelectedListener(this);
		
	}
	//获取码头危货作业记录
	public  void GetRecord() 
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Name="+companySpinner.getSelectedItem().toString());
		
		HttpParams params = null;
		try 
		{
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.baseUrl+"DangerWorkListByComName";
		if(params == null)
		{
			Util.getTip(getApplicationContext(), "构造参数失败");
		}
		else if(!toUrl.equals(""))
		{
			kjh.post(toUrl, params,false,new HttpCallBack() 
			{
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t)
				{
					super.onSuccess(headers, t);
					
					KJLoger.debug("===" + headers.get("Set-Cookie"));
				
					String result = new String(t).trim();
					String reString=result.replace("\\", "");
					String string=reString.substring(1,reString.length());
					try 
					{ 
						JSONArray data = new JSONArray(string); 
						if(data.length()==0)
						{
							Toast.makeText(getApplicationContext(), "无作业记录", Toast.LENGTH_SHORT).show();
						}
						else
						{
							recordlist.clear();
							for(int i = 0;i<data.length();i++)
							{
								JSONObject temp = data.getJSONObject(i);
								DuckDangerRecordModel record= new DuckDangerRecordModel();
	
								record.wharfnameString=temp.getString("wharfEN");
								record.setid(temp.getString("id"));
								record.setship(temp.getString("ship"));
								record.setgoods(temp.getString("goodsname"));
								record.setgoodsweight(temp.getString("mount"));
								
								JSONObject typeoJsonObject=temp.getJSONObject("rank");
								record.setgoodstype(typeoJsonObject.getString("rankname"));
								record.rankidString=typeoJsonObject.getString("id");
								
								JSONObject sObject=temp.getJSONObject("startport");
								JSONObject tJsonObject=temp.getJSONObject("targetport");
								record.setstartport(sObject.getString("portname"));
								record.startidString=sObject.getString("id");
								record.settargetport(tJsonObject.getString("portname"));
								record.tartgetidString=tJsonObject.getString("id");
								
								JSONObject statusObject=temp.getJSONObject("status");
								record.setstatus(statusObject.getString("status"));
								record.statusidString=statusObject.getString("id");
								
								record.setportime(temp.getString("portime")); 							
								record.setnumber(temp.getString("number"));
								record.setendtime(temp.getString("endtime"));
								
								JSONObject unitObject=temp.getJSONObject("unit");
								record.setunit(unitObject.getString("unitname"));
								record.unitidString=unitObject.getString("id");
																																										
								recordlist.add(record);
																																					
							}
						}
						
						dangerDuckAdapter.notifyDataSetChanged();											
					} 
					catch (Exception e1)
					{
						e1.printStackTrace();
					}
					
				}

				@Override
				public void onFailure(int errorNo, String strMsg)
				{
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
	
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{		
		case R.id.relative_title:
			finish();
			break;			
		}		
	}
	@Override
	public void onResume()
	{
		super.onResume();
		//GetRecord();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	{
		GetRecord();
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) 
	{
		
	}
		
	
	
	

}
