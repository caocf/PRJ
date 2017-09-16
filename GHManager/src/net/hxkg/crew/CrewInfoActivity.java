package net.hxkg.crew;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import net.hxkg.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CrewInfoActivity extends Activity implements onResult
{	
	String crewnameString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crewinfo);
		crewnameString=getIntent().getStringExtra("crewName");
		
		initData();	
	}
	
	private void initData()
	{
		Map<String, Object> map_params=new HashMap<>();		
		map_params.put("name", crewnameString);
		HttpRequest httpRequest=new HttpRequest(this);
		httpRequest.post(Constants.BaseURL+"CrewinfoByName", map_params);
	}
	
	public void onView(View view)
	{
		switch (view.getId())
		{
		case R.id.textView1:
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
	

	@Override
	public void onSuccess(String result) 
	{
		try {
			JSONObject object=new JSONObject(result);
			String s1=object.getString("name");
			String s2=object.getString("age");
			String s3=object.getString("gender");
			String s4=object.getString("cert");
			
			((TextView)findViewById(R.id.shipname)).setText(s1);
			((TextView)findViewById(R.id.start)).setText(s2);
			((TextView)findViewById(R.id.target)).setText(s3);
			((TextView)findViewById(R.id.goods)).setText(s4);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onError(int httpcode) 
	{		// TODO Auto-generated method stub
		
	}

	
}
