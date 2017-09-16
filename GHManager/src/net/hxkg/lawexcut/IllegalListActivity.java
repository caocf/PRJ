package net.hxkg.lawexcut;

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
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class IllegalListActivity extends Activity implements RadioGroup.OnCheckedChangeListener,OnItemClickListener
																								,HttpRequest.onResult
{
	RadioButton bt1,bt2;
	
	ListView listView;
	EvidenceAdapter adpter;
	List<Map<String, Object>> datalist=new ArrayList<>();
	List<LawBaseEN> list=new ArrayList<>();
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	final int REQUEST=31;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_illegallist);
		
		initListView();
		initRadioButton();
	}
	
	public void onClick(View v)
	{
		finish();
	}
	
	private void  initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		adpter=new EvidenceAdapter(this,datalist,R.layout.lawlist_item
									,new String[]{"title","status","content"}
									,new int[]{R.id.title,R.id.status,R.id.content});
		listView.setAdapter(adpter);
		listView.setOnItemClickListener(this);
	}
	
	private void  freshData(String status)
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("status",status);
		map.put("page",1);
		httpRequest.post(Constants.BaseURL+"evidencelist", map);
	}
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		initRadioButton();
    }

	private void  initRadioButton()
	{
		bt1=(RadioButton) findViewById(R.id.btn_0);
		bt2=(RadioButton) findViewById(R.id.btn_1);
		RadioGroup options=(RadioGroup) findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);
		
		if(bt1.isChecked())
		{
			freshData(LawBaseEN.CHECKING);
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
		}
		else
		{
			freshData(LawBaseEN.CHECKED);
			bt2.setTextColor(Color.parseColor("#1766b1"));
			bt1.setTextColor(Color.parseColor("#901766b1"));
		}
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.btn_0:
			freshData(LawBaseEN.CHECKING);
			bt1.setTextColor(Color.parseColor("#1766b1"));
			bt2.setTextColor(Color.parseColor("#901766b1"));
			break;
		case R.id.btn_1:
			freshData(LawBaseEN.CHECKED);
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
		String statusString=list.get(position).getStatus();
		if(LawBaseEN.CHECKING.equals(statusString))
		{
			Intent intent=new Intent(this,IllegalCheckActivity.class);
			intent.putExtra("LawBaseEN", list.get(position));
			startActivityForResult(intent, REQUEST);
		}
		else//已审核
		{
			Intent intent=new Intent(this,LawDetailActivity.class);
			intent.putExtra("LawBaseEN", list.get(position));
			intent.putExtra("mode", 0);
			startActivityForResult(intent, REQUEST);
		}
		
	}

	@Override
	public void onSuccess(String result) 
	{
		JSONArray dataArray;
		datalist.clear();
		list.clear();
		try
		{
			JSONObject jbJsonObject=new JSONObject(result);
			dataArray=jbJsonObject.getJSONArray("obj");
			for(int i=0;i<dataArray.length();i++)
			{
				JSONObject item=(JSONObject)dataArray.get(i);
				String s1=item.getString("id");
				String s2=item.getString("firstman");
				String s3=item.getString("secman");
				String s4=item.getString("target");
				String s5=item.getString("reason");
				String s6=item.getString("detail");
				String s7=item.getString("location");
				String s8=item.getString("checker");
				String s9=item.getString("status");
				String s10=item.getString("sumbdate");
				String s11=item.getString("isllegal");
				JSONObject typeJsonObject=item.getJSONObject("typeEN");
				String typeidString=typeJsonObject.getString("id");
				String typename=typeJsonObject.getString("status");
				
				LawBaseEN lawBaseEN=new LawBaseEN();
				lawBaseEN.setId(Integer.valueOf(s1));
				lawBaseEN.setFirstman(s2);
				lawBaseEN.setSecman(s3);
				lawBaseEN.setTarget(s4);
				lawBaseEN.setReason(s5);
				lawBaseEN.setDetail(s6);
				lawBaseEN.setLocation(s7);
				lawBaseEN.setChecker(s8);
				lawBaseEN.setStatus(s9);
				lawBaseEN.setSumbdate(sf.parse((s10)));
				lawBaseEN.setIsllegal(s11);
				lawBaseEN.typeid=Integer.valueOf(typeidString);
				lawBaseEN.typename=typename;
				list.add(lawBaseEN);
				
				
				Map<String, Object> map=new HashMap<>();
				map.put("title", s4);
				map.put("status", s9);
				map.put("content", s5);
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
