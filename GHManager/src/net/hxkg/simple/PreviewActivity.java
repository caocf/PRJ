package net.hxkg.simple;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.LawBaseEN;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PreviewActivity extends Activity
{
	int targetid,penaltyid;
	TextView man,gender,duty,tel,location,cert,lawman,content,date;
	LawBaseEN lawbase;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		SharedPreferences sp=getSharedPreferences("data", 0);
		targetid=sp.getInt("targetid", 0);
		penaltyid=sp.getInt("penaltyid", 0);//System.out.println(Integer.valueOf("penaltyid"+penaltyid));
		lawbase=(LawBaseEN) getIntent().getSerializableExtra("LawBaseEN");
		initView();
		showdata();
	}
	
	private void initView()
	{
		man=(TextView) findViewById(R.id.man);//getSystemService(name)
		gender=(TextView) findViewById(R.id.gender);
		duty=(TextView) findViewById(R.id.duty);
		tel=(TextView) findViewById(R.id.tel);
		location=(TextView) findViewById(R.id.location);
		cert=(TextView) findViewById(R.id.cert);
		lawman=(TextView) findViewById(R.id.lawman);
		content=(TextView) findViewById(R.id.content);
		date=(TextView) findViewById(R.id.date);
	}
	
	public void Submit(View view)
	{
		Intent intent=new Intent(this,SimpleLaw3Activity.class);
		intent.putExtra("LawBaseEN", lawbase);
		startActivity(intent);
		finish();
	}
	
	private void showdata()
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			
			@Override
			public void onSuccess(String result)
			{
				try 
				{
					JSONObject dataJsonObject=new JSONObject(result);								
					JSONObject obj=dataJsonObject.getJSONObject("obj");
					String s1=obj.getString("name");
					String s2=obj.getString("gender");
					String s3=obj.getString("cert");
					String s4=obj.getString("lawname");
					String s5=obj.getString("duty");
					String s6=obj.getString("tel");
					String s7=obj.getString("location");
					
					man.setText(s1);
					gender.setText(s2);
					duty.setText(s5);
					tel.setText(s6);
					location.setText(s7);
					cert.setText(s3);
					lawman.setText(s4);
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("targetid", targetid);
		httpRequest.post(Constants.BaseURL+"TargetByID", map);
		
		HttpRequest httpRequest1=new HttpRequest(new HttpRequest.onResult()
		{
			
			@Override
			public void onSuccess(String result)
			{
				System.out.println(result);
				try 
				{
					JSONObject dataJsonObject=new JSONObject(result);								
					JSONObject obj=dataJsonObject.getJSONObject("obj");
					String s1=obj.getString("depend");
					String s2=obj.getString("item1");
					String s3=obj.getString("item2");
					String s4=obj.getString("item3");
					String s5=obj.getString("suggest");
					String s6=obj.getString("number");
					
					String contentString="	现依据"+s1+"第"+s2+"条，第"+s3+"款，第"+s4+"项之规定，本机关对你（单位）作以下"+
										"行政处罚：\n"+s5;
					
					content.setText(contentString);
					
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map1=new HashMap<>();
		map1.put("penaltyid", penaltyid);
		httpRequest1.post(Constants.BaseURL+"PenaltyByID", map1);
	}
	
	public void onBack(View view)
	{
		finish();
	}

}
