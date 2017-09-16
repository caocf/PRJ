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
import com.gh.modol.RecordLetIn;
import com.hxkg.ereport.ShipReportInfoActivity;
import com.hxkg.ereport.ShipSpinner;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.RecordLetInAdapter;
import com.hztuen.gh.contact.contants; 
import com.hztuen.lvyou.utils.Util;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LetInRecordActivity extends Activity implements OnClickListener,OnItemClickListener
{	
	private List<RecordLetIn> recordlist = new ArrayList<RecordLetIn>();
	private RecordLetInAdapter recordAdapter;
	public ListView lv_record;	
	private RelativeLayout relative_title;
	ShipSpinner shipSpinner;
	String shipid="";
	TextView textnoTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letin_record);
		init();
		//GetRecord();
		
	}
	void init()	
	{		
		lv_record=(ListView)findViewById(R.id.listview_record);
		lv_record.setOnItemClickListener(this);
		recordAdapter = new RecordLetInAdapter(LetInRecordActivity.this, recordlist);	
		lv_record.setAdapter(recordAdapter);
		
		textnoTextView=(TextView)findViewById(R.id.textno);
		
		
		relative_title=(RelativeLayout)findViewById(R.id.relative_title);
		relative_title.setOnClickListener(this);	
		
		shipSpinner=(ShipSpinner) findViewById(R.id.ship);
		shipSpinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				GetRecord();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) 
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		
		shipid=getIntent().getStringExtra("shipid");
		
		shipSpinner.setID(shipid); 
		
	}	
	
	public void GetRecord() 
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Shipname="+(String)shipSpinner.getSelectedItem());//SystemStatic.searchShipName
		
		HttpParams params = null;
		try 
		{
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.baseUrl+"DangerShipByName";
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kjh.post(toUrl, params,false,new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t)
				{
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
				
					String result = new String(t).trim();
					String res=result.replace("\\", "");
					String s=res.substring(1,res.length()-1);
					recordlist.clear();
					lv_record.setVisibility(View.VISIBLE);
					textnoTextView.setVisibility(View.GONE);
					try 
					{  
						JSONArray data = new  JSONArray(s);
						int n=data.length();
						for(int i = 0;i<data.length();i++)
						{
							JSONObject temp = data.getJSONObject(i);
							RecordLetIn record= new RecordLetIn();					 
							
							record.shipnameString=temp.getString("shipname");
							record.setid(temp.getString("id"));
							record.setberthtime(temp.getString("berthtime"));
							record.setgoods(temp.getString("goods"));
							record.setnumber(temp.getString("number"));
							JSONObject rankobj=temp.getJSONObject("dangerrankEN");
							record.setrank(rankobj.getString("rankname"));
							record.rankidString=rankobj.getString("id");
							JSONObject startobj=temp.getJSONObject("startportEN");
							record.setstart(startobj.getString("portname"));
							record.startidString=startobj.getString("id");
							JSONObject startportEN=temp.getJSONObject("status");
							String string=startportEN.getString("id");
							record.setstatus(startportEN.getString("id"));
							JSONObject endportEN=temp.getJSONObject("endportEN");
							record.settarget(endportEN.getString("portname"));
							record.targetidString=endportEN.getString("id");
							record.settons(temp.getString("tons"));
							JSONObject goodsunitEN=temp.getJSONObject("goodsunitEN");
							record.setunit(goodsunitEN.getString("unitname"));
							record.unitidString=goodsunitEN.getString("id");
							
							record.uptime=temp.getString("committime");
							record.cheker=temp.getString("checker");
							record.statusString=startportEN.getString("status");	
							
							recordlist.add(record);
																																				
						}
						if(recordlist.size()==0)
						{
							textnoTextView.setVisibility(View.VISIBLE);
							lv_record.setVisibility(View.GONE);
							return;
						}
						recordAdapter.notifyDataSetChanged();												
					} catch (Exception e1) {
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent=new Intent(this,ShipReportInfoActivity.class);
		intent.putExtra("RecordLetIn", recordlist.get(position));
		startActivity(intent);
		
	}
	

}
