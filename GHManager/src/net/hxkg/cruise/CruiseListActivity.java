package net.hxkg.cruise;

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
import net.hxkg.user.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CruiseListActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	ListView listView;
	CruiseAdapter cruiseAdapter;
	List<CruiseRecordEN> list=new ArrayList();
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycruiselist);
		
		initListView();
	}
	
	private void  initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		cruiseAdapter=new CruiseAdapter(this,list);
		listView.setAdapter(cruiseAdapter);
		listView.setOnItemClickListener(this);
		freshData(User.name);
	}
	
	public void onClick(View v)
	{
		finish();
	}
	
	private void  freshData(String name)
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("name",User.name);
		httpRequest.post(Constants.BaseURL+"CruisesByMember", map);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		Intent intent=new Intent(this,CruiseDetailActivity.class);
		intent.putExtra("CruiseRecordEN", list.get(position));
		intent.putExtra("position",position);
		startActivity(intent);
		
	}

	@Override
	public void onSuccess(String result) 
	{		
		JSONArray data;
		list.clear();
		try
		{
			JSONObject jbJsonObject=new JSONObject(result);
			data=jbJsonObject.getJSONArray("data");
			for(int i=0;i<data.length();i++)
			{
				JSONObject item=(JSONObject)data.get(i);
				String s1=item.getString("id");
				String s2=item.getString("member");
				String s3=item.getString("area");
				String s4=item.getString("period");
				String s5=item.getString("miles");
				String s6=item.getString("dir");
				String s7=item.getString("tools");
				
				CruiseRecordEN lawBaseEN=new CruiseRecordEN();
				lawBaseEN.setId(Integer.valueOf(s1));
				lawBaseEN.setMember(s2);
				lawBaseEN.setArea(s3);
				lawBaseEN.setPeriod(s4);
				lawBaseEN.setMiles(s5);
				lawBaseEN.setDir(s6);
				lawBaseEN.settoolsString(s7);
				System.out.println(s2);
				list.add(lawBaseEN);
			}			 
			
			cruiseAdapter.notifyDataSetChanged();
			//listView.setAdapter(cruiseAdapter);
			
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
