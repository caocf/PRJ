package net.hxkg.patrolboat;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.book.TreeNode;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class BoatInfoActivity extends Activity
{
	TextView text_user,len,year,unit;
	TreeNode node;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boatinfo);	
		
		node=(TreeNode) getIntent().getSerializableExtra("treeNode");
		initView();
	}
	
	private void initView()
	{
		text_user=(TextView) findViewById(R.id.user);
		len=(TextView) findViewById(R.id.len);
		year=(TextView) findViewById(R.id.year);
		unit=(TextView) findViewById(R.id.unit);
		
		getData(node.getContentText());
		
	}
	
	private void getData(String name)
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{

			@Override
			public void onSuccess(String result) 
			{
				//System.out.println(result);
				try 
				{
					JSONObject object=new JSONObject(result);
					JSONObject user=object.getJSONObject("obj");
					String nameString=user.getString("name");
					String buildtime=user.getString("buildtime");
					String shiplength=user.getString("shiplength");
					JSONArray array=object.getJSONArray("s1");
					String st1="";
					
					for(int i=array.length()-1;i>=0;i--)
					{
						String string= array.getString(i);
						st1+=string;
						if(!st1.equals("")&&i!=0)
						{
							st1+=" > ";
						}						
					}			
					
					text_user.setText(nameString);
					len.setText(shiplength);
					year.setText(buildtime);
					unit.setText(st1);
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Map<String, Object> map=new HashMap<>();
		map.put("Name", name);
		
		httpRequest.post(Constants.BaseURL+"DepsByName", map);
	}
	
	public void onView(View v)
	{
		switch (v.getId()) 
		{
		case R.id.count:
		case R.id.back:
			finish();
			break;
		
		

		default:
			break;
		}
		
	}
}
