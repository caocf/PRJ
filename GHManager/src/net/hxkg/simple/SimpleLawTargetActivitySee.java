package net.hxkg.simple;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.ReasonActivity;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SimpleLawTargetActivitySee extends Activity implements HttpRequest.onResult
{	
	public static final int RESULTCODE=2015;
	
	TextView edit_firstman,gender,secman,lawman,duty,desc,location,text_reason;
	
	String targetname=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simpletargetlawsee);
		
		targetname= getIntent().getStringExtra("targetname");
		
		initView();
	}	
	
	private void initView()
	{
		edit_firstman=(TextView) findViewById(R.id.firstman);	
		gender=(TextView) findViewById(R.id.gender);
		secman=(TextView) findViewById(R.id.secman);//身份证号		
		lawman=(TextView) findViewById(R.id.lawman);
		duty=(TextView) findViewById(R.id.duty);		
		desc=(TextView) findViewById(R.id.desc);		
		location=(TextView) findViewById(R.id.location);
		
		if(targetname!=null)
		{
			HttpRequest hr=new HttpRequest(this);
			Map<String, Object> map=new HashMap<>();
			map.put("name", targetname);
			hr.post(Constants.BaseURL+"TargetByName", map);
		}
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.back:
		case R.id.law:
			this.finish();
			break;
		case R.id.toreason:
			Intent intent=new Intent(this,ReasonActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.myevidence:
			/*Intent intent1=new Intent(this,MyIllegalListActivity.class);
			startActivity(intent1);*/
			break;
		default:
			break;
		}
	}
	
	
	
	
	@Override
	public void onSuccess(String result) 
	{
		try 
		{
			JSONObject object=new JSONObject(result);
			String nameString=object.getString("name");
			String gender=object.getString("gender");
			String cert=object.getString("cert");
			String lawname=object.getString("lawname");
			String duty=object.getString("duty");
			String tel=object.getString("tel");
			String location=object.getString("location");
			
			edit_firstman.setText(nameString);
			this.gender.setText(gender);
			secman.setText(cert);
			lawman.setText(lawname);
			this.duty.setText(duty);
			desc.setText(tel);
			this.location.setText(location);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
