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
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

public class WharfDangerListActivity extends Activity implements RadioGroup.OnCheckedChangeListener,OnItemClickListener
																								,HttpRequest.onResult
{
	RadioButton bt1,bt2;
	
	ListView listView;
	SimpleAdapter adpter;
	List<Map<String, Object>> datalist=new ArrayList<>();
	List<WharfDangerreportEN> list=new ArrayList<>();
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	Bitmap statusDrawable[]; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_wharfdangerllist);
		statusDrawable=new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.leave_status1),//待审批
				BitmapFactory.decodeResource(getResources(), R.drawable.leave_status4),//批准
				BitmapFactory.decodeResource(getResources(), R.drawable.leave_status3)//不批准
				};
		
		initListView();
		initRadioButton();
	}
	
	public void toSearch(View v)
	{
		Intent intent=new Intent(this,SearchList1Activity.class);
		startActivity(intent);
	}
	
	@Override
	public void onResume()
	{
		if(bt1.isChecked())
		{
			freshData(1);
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
	
	public void onBack(View v)
	{
		finish();
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
		httpRequest.post(Constants.BaseURL+"DangerWorkByStatus", map);
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
			freshData(1);//待审核
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
			break;
		case R.id.btn_1:
			freshData(-1);//"已审核"
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		int statusString=list.get(position).statusid;
		if(statusString==1)//待审核
		{
			Intent intent=new Intent(this,WharfDangerInfoActivity.class);
			intent.putExtra("WharfDangerreportEN", list.get(position));
			intent.putExtra("model", 1);
			startActivity(intent);
		}		
		else
		{
			Intent intent=new Intent(this,WharfDangerInfoActivity.class);
			intent.putExtra("WharfDangerreportEN", list.get(position));
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
			dataArray=new JSONArray(result); 
			for(int i=0;i<dataArray.length();i++)
			{			
				
				JSONObject item=(JSONObject)dataArray.get(i);
				JSONObject object1=item.getJSONObject("startport");
				JSONObject object2=item.getJSONObject("targetport");
				JSONObject object3=item.getJSONObject("status");
				JSONObject object4=item.getJSONObject("unit");
				JSONObject object5=item.getJSONObject("rank");
				String s1=item.getString("id");
				String s2=item.getString("ship");
				
				String s3=object1.getString("portname");
				String s4=object2.getString("portname");
				String s5=item.getString("goodsname");
				String s6=object5.getString("rankname");
				String s7=item.getString("mount");
				String s8=item.getString("portime");
				String s9=object3.getString("status");
				String s10=item.getString("number");
				String s11=item.getString("reason");
				String s12=item.getString("endtime"); 
				
				WharfDangerreportEN lawBaseEN=new WharfDangerreportEN();
				lawBaseEN.setId(Integer.valueOf(s1));
				lawBaseEN.setShip(s2);
				lawBaseEN.setStartport(s3);
				lawBaseEN.setTargetport(s4);
				lawBaseEN.setGoods(s5);
				lawBaseEN.setGoodstype(s6);
				lawBaseEN.setGoodsweight(s7);
				lawBaseEN.setPortime(s8);
				lawBaseEN.setStatus(s9);
				lawBaseEN.setNumber(s10);
				lawBaseEN.setReason(s11);
				lawBaseEN.setEndtime(s12);
				lawBaseEN.statusid=object3.getInt("id"); 
				list.add(lawBaseEN);
				
				
				Map<String, Object> map=new HashMap<>();
				map.put("reportnum", "报告单号："+s10);
				map.put("status", statusDrawable[lawBaseEN.statusid-1]);
				map.put("shipname", s2);
				map.put("goods", s5+s7);
				map.put("time", s8);
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
