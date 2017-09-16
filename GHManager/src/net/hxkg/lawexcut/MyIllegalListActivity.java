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
import net.hxkg.user.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyIllegalListActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	ListView listView;
	EvidenceAdapter adpter;
	List<Map<String, Object>> datalist=new ArrayList<>();
	List<LawBaseEN> list=new ArrayList<>();
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 
	final int REQUEST=21;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myillegallist);
		
		initListView();
	}
	
	private void  initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		adpter=new EvidenceAdapter(this,datalist,R.layout.lawlist_item
									,new String[]{"title","status","content"}
									,new int[]{R.id.title,R.id.status,R.id.content});
		listView.setAdapter(adpter);
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
		map.put("name",name);
		httpRequest.post(Constants.BaseURL+"myevidences", map);		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{		
		String statusString=list.get(position).getStatus();
		if(LawBaseEN.CHECKING.equals(statusString))//待审核
		{
			Intent intent=new Intent(this,LawDetailActivity.class);
			intent.putExtra("LawBaseEN", list.get(position));
			intent.putExtra("mode", 1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		freshData(User.name);
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
			dataArray=jbJsonObject.getJSONArray("data");
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
				String lat=item.getString("lat");
				String lon=item.getString("lon");
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
				lawBaseEN.lat=Double.valueOf(lat);
				lawBaseEN.lon=Double.valueOf(lon);
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
			//
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) {
		
	}
}
