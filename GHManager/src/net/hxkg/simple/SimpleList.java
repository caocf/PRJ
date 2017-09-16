package net.hxkg.simple;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SimpleList extends Activity implements HttpRequest.onResult,OnItemClickListener
{
	ListView listView;
	SimpleAdapt adapt;
	List<SimpleModel> datalist=new ArrayList<>();
	SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplelist);
		
		initListView();
	}
	public void onBack(View view)
	{
		finish();
	}
	
	private void initListView()
	{
		listView=(ListView) findViewById(R.id.listview);
		adapt=new SimpleAdapt(this,datalist);
		listView.setAdapter(adapt);
		listView.setOnItemClickListener(this);
		RequestDate();
	}
	
	private void RequestDate()
	{
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("name", User.name);
		hr.post(Constants.BaseURL+"SimpleListByName", map);
		
	}

	@Override
	public void onSuccess(String result) 
	{
		try 
		{
			JSONArray array=new JSONArray(result);
			for(int i=0;i<array.length();i++)
			{
				JSONObject jsonObject=(JSONObject) array.get(i);
				
				String pid=jsonObject.getString("id");
				String number=jsonObject.getString("number");
				JSONObject lawObject=jsonObject.getJSONObject("lawEvidencesEN"); 				
				String target=lawObject.getString("target");
				String reason=lawObject.getString("reason");
				String sumbdate=lawObject.getString("sumbdate");
				String location=lawObject.getString("location");
				String firstString=lawObject.getString("firstman");
				String sec=lawObject.getString("secman");
				String detail=lawObject.getString("detail");
				String lawid=lawObject.getString("id");
				String status=lawObject.getString("status");
				
				JSONObject typeEN=lawObject.getJSONObject("typeEN"); 
				String type=typeEN.getString("status");
			
				
				SimpleModel smModel=new SimpleModel();
				smModel.setPid(pid);//处罚ID
				smModel.penaltyString=number;
				smModel.setTarget(target);
				smModel.setReason(reason);
				smModel.setSumbdate(sumbdate);
				smModel.setLocation(location);
				smModel.setFirstman(firstString);
				smModel.setSecman(sec);
				smModel.setDetail(detail);
				smModel.typename=type;
				smModel.baseID=Integer.valueOf(lawid);
				smModel.setStatus(status);
				
				datalist.add(smModel);
				
			}
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapt.notifyDataSetChanged();
		
	}

	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent intent=new Intent(this,SimpleDetailActivity.class);
		intent.putExtra("SimpleModel", datalist.get(position));
		startActivity(intent);
	}

}
