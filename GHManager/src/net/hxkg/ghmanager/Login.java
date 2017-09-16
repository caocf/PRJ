package net.hxkg.ghmanager;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.user.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View; 
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements HttpRequest.onResult,OnCheckedChangeListener
{
	CheckBox cb1;
	EditText editx_user;
	EditText editx_psw;
	
	SharedPreferences sf;
	SharedPreferences userpPreferences;
	ProgressDialog locationDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		cb1=(CheckBox) findViewById(R.id.login_checkbox1);
		cb1.setOnCheckedChangeListener(this);
		
		editx_user=(EditText) findViewById(R.id.EditText01);
		editx_psw=(EditText) findViewById(R.id.EditText02);
		
		sf=getSharedPreferences("data", 0);
		cb1.setChecked(sf.getBoolean("checked", false));
		editx_user.setText(sf.getString("Username", ""));
		editx_psw.setText(sf.getString("PSW", ""));
		
		userpPreferences=getSharedPreferences("User", 0);
		
	}
	
	public void Login(View v)
	{
		String userString=editx_user.getText().toString();
		String pswString=editx_psw.getText().toString();		
		if(userString==null||"".equals(userString))
		{
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
			return;
		}
		if(pswString==null||"".equals(pswString))
		{
			Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
			return;
		}
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Username", userString);
		map.put("PSW", pswString);
		
		httpRequest.post(Constants.BaseURL+"LoginPhone", map);
		locationDialog= new ProgressDialog(this);
		locationDialog.setMessage("登录中");
		locationDialog.setCancelable(true);
		locationDialog.show();
	}

	@Override
	public void onSuccess(String result)
	{
		JSONObject data;
		String s1="";
		try 
		{
			data=new JSONObject(result);
			s1=data.getString("resultdesc");
			
			String s2=data.getString("resultcode");
			JSONObject user=data.getJSONObject("obj");
			User.uidString=user.getString("id");
			User.name=user.getString("xm");
			//User.psw=user.getString("mm");
			User.sjzgString=user.getString("sjzg");
			User.dw=user.getString("dw");
			User.dep=user.getString("bmmc");	
			User.sjhm=user.getString("sjhm");	
			
			JSONObject roleJsonObject=user.getJSONObject("userRoleEN"); 
			JSONArray permitsArray=roleJsonObject.getJSONArray("permissionENs");
			for(int i=0;i<permitsArray.length();i++)
			{
				JSONObject p=(JSONObject) permitsArray.get(i);
				String spString=p.getString("permissionname");
				User.permitsArrayList.add(spString);
			}
			
			if("1".equals(s2))
			{
				if(cb1.isChecked())
				{
					Editor edit=sf.edit();
					edit.putString("Username", editx_user.getText().toString());
					edit.putString("PSW", editx_psw.getText().toString());
					edit.commit();
				}
				else
				{
					Editor edit=sf.edit();
					edit.putString("Username", "");
					edit.putString("PSW", "");
					edit.commit();				
				}
				SharedPreferences.Editor editor=userpPreferences.edit();
				editor.putString("userid", User.uidString);
				editor.commit();				
				
				if(locationDialog!=null)
					locationDialog.dismiss();
				
				Intent  intent=new Intent(Login.this,HomeActivity.class);
				startActivity(intent);
				this.finish();
			}
			
			else 
			{
				if(locationDialog!=null)
					locationDialog.dismiss();
				Toast.makeText(Login.this, s1, Toast.LENGTH_SHORT).show();
			} 
		}
		catch (JSONException e) 
		{
			if(locationDialog!=null)
				locationDialog.dismiss();
			e.printStackTrace();
			Toast.makeText(Login.this, s1, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onError(int httpcode) 
	{
		if(locationDialog!=null)
			locationDialog.dismiss();
		
	}
	
	@Override  
    protected void onDestroy() 
	{  
        super.onDestroy();
        
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
	{
		Editor edit=sf.edit();
		edit.putBoolean("checked", isChecked);
		edit.commit();
		
	} 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			return super.onKeyDown(keyCode, event);
		
		}
		return super.onKeyDown(keyCode, event);
	}
}
