package net.hxkg.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color; 
import android.os.Bundle;
import android.view.View; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class ShipDangerListActivity extends Activity implements RadioGroup.OnCheckedChangeListener,OnItemClickListener
																								,HttpRequest.onResult
{
	RadioButton bt1,bt2;	
	ListView listView;
	SimpleAdapter adpter;
	List<Map<String, Object>> datalist=new ArrayList<>();
	List<ShipDangerreportEN> list=new ArrayList<>();
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Bitmap statusDrawable[];
	int tab=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_shipdangerllist);
		
		statusDrawable=new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.leave_status1),//待审批
				BitmapFactory.decodeResource(getResources(), R.drawable.leave_status4),//不批准
				BitmapFactory.decodeResource(getResources(), R.drawable.leave_status3),};//批准
		
		initListView();
		initRadioButton();
	}
	
	public void onBack(View v)
	{
		finish();
	}
	
	public void toSearch(View v)
	{
		Intent intent=new Intent(this,SearchList2Activity.class);
		startActivity(intent);
	}
	
	private void  initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		adpter=new SimpleAdapter(this,datalist,R.layout.item_shipdangerlist
									,new String[]{"reportnum","status","shipname","goods","time"}
									,new int[]{R.id.reportnum,R.id.status,R.id.shipname,R.id.goods,R.id.time});
		adpter.setViewBinder(new  ViewBinder()
		{
			@Override
			public boolean setViewValue(View view, Object data,String textRepresentation) 
			{
				 if(view instanceof ImageView && data instanceof Bitmap) 
				 {  
                     ImageView iv=(ImageView)view;  
                     iv.setImageBitmap((Bitmap) data);  
                     return true;  
				 }
				 return false;
			}
			
		});
		
		listView.setAdapter(adpter);
		listView.setOnItemClickListener(this);
	}
	
	private void  freshData(int status)
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("status",status);
		//httpRequest.post(Constants.BaseURL+"evidencelist", map);
		httpRequest.post(Constants.BaseURL+"DangerShipByTip", map);
	}

	private void  initRadioButton()
	{
		bt1=(RadioButton) findViewById(R.id.btn_0);
		bt2=(RadioButton) findViewById(R.id.btn_1);
		RadioGroup options=(RadioGroup) findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);
		
		
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.btn_0:
			freshData(1);//待审批
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1")); 
			break;
		case R.id.btn_1:
			freshData(-1);//已审核
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
			break;

		default:
			break;
		}
		
	}
	@Override
	public void onResume()
	{
		if(bt1.isChecked())
		{
			tab=0;
			freshData(1);//待审批
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
		}
		else
		{
			freshData(-1);
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
		}
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		int statusid=list.get(position).statusid;
		if(statusid==1)//待审批
		{
			Intent intent=new Intent(this,ShipDangerInfoActivity.class);
			intent.putExtra("ShipDangerreportEN", list.get(position));
			intent.putExtra("model", 1);
			startActivity(intent);
		}		
		else
		{
			Intent intent=new Intent(this,ShipDangerInfoActivity.class);
			intent.putExtra("ShipDangerreportEN", list.get(position));
			intent.putExtra("model", 0);
			startActivity(intent);
		}
	}

	@Override
	public void onSuccess(String result) 
	{
		//System.out.println(result);
		JSONArray dataArray;
		datalist.clear();
		list.clear();
		try
		{ 
			dataArray=new JSONArray(result.trim());
			for(int i=0;i<dataArray.length();i++)
			{
				JSONObject item=(JSONObject)dataArray.get(i);
				String s1=item.getString("id");
				JSONObject s2obj=item.getJSONObject("startportEN");
				JSONObject s3obj=item.getJSONObject("endportEN");
				String s4=item.getString("number");
				JSONObject s5obj=item.getJSONObject("goodsunitEN");
				String s6=item.getString("tons");
				JSONObject s7obj=item.getJSONObject("dangerrankEN");
				String s8=item.getString("goods");
				JSONObject s9obj=item.getJSONObject("status");
				String s10=item.getString("berthtime");
				String s11=item.getString("reason"); 
				String shipnameString=item.getString("shipname");
				
				ShipDangerreportEN lawBaseEN=new ShipDangerreportEN();
				lawBaseEN.setId(Integer.valueOf(s1));
				lawBaseEN.setNumber(s4);
				lawBaseEN.setStatus(s9obj.getString("status"));
				lawBaseEN.setGoods(s8);
				lawBaseEN.setBerthtime(s10); 
				lawBaseEN.setRank(s7obj.getString("rankname"));
				lawBaseEN.setTons(s6);
				lawBaseEN.setUnit(s5obj.getString("unitname"));
				lawBaseEN.setShipname(shipnameString);
				lawBaseEN.setStart(s2obj.getString("portname"));
				lawBaseEN.setTarget(s3obj.getString("portname"));
				lawBaseEN.statusid=s9obj.getInt("id");
				list.add(lawBaseEN);
				
				
				Map<String, Object> map=new HashMap<>();
				map.put("reportnum", "报告单号："+s4);
				map.put("status",statusDrawable[ lawBaseEN.statusid-1]);
				map.put("shipname", shipnameString);
				map.put("goods", s8+s6+lawBaseEN.getUnit());
				map.put("time", s10);
				datalist.add(map);
			}
			
			adpter.notifyDataSetChanged();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
