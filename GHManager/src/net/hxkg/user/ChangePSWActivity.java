package net.hxkg.user;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.ghmanager.Login;
import net.hxkg.ghmanager.R;

import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePSWActivity extends Activity implements HttpRequest.onResult
{
	EditText edit_old;
	EditText edit_new;
	EditText edit_sure;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepsw);
		
		edit_old= (EditText) findViewById(R.id.edit_old);
		edit_new= (EditText) findViewById(R.id.edit_new);
		edit_sure= (EditText) findViewById(R.id.edit_sure);
		
	}
	
	public void onBack(View v)
	{
		this.finish();
	}
	
	public void onChange(View v)
	{
		String s1=edit_old.getText().toString();
		String s2=edit_new.getText().toString();
		String s3=edit_sure.getText().toString();
		if(s1==null||"".equals(s1))
		{
			Toast.makeText(this, "原密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(s2==null||"".equals(s2))
		{
			Toast.makeText(this, "新密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if(s3==null||"".equals(s2))
		{
			Toast.makeText(this, "请确认密码", Toast.LENGTH_LONG).show();
			return;
		}
		if(!s3.equals(s2))
		{
			Toast.makeText(this, "确认密码不符", Toast.LENGTH_LONG).show();
			return;
		}
		
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Username", User.uidString);
		map.put("Pswold", s1);
		map.put("Pswnew", s2);
		httpRequest.post(Constants.BaseURL+"changepsw", map);
	}

	@Override
	public void onSuccess(String result) 
	{
		JSONObject data;
		try 
		{
			data=new JSONObject(result);
			String s1=data.getString("resultdesc");
			Toast.makeText(ChangePSWActivity.this, s1, Toast.LENGTH_LONG).show();
			String s2=data.getString("resultcode");
			if("1".equals(s2))
			{
				this.finish();
			}
		}
		catch (JSONException e) 
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
}
