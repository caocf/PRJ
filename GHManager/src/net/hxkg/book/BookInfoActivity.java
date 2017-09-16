package net.hxkg.book;

import java.text.SimpleDateFormat; 
import java.util.HashMap; 
import java.util.Map; 
import org.json.JSONObject;
import net.hxkg.ghmanager.R; 
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest; 
import android.app.Activity;   
import android.content.Intent;
import android.graphics.Bitmap; 
import android.net.Uri;
import android.os.Bundle;
import android.view.View; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;  
import android.widget.ImageView;
import android.widget.TextView;

public class BookInfoActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	public static Map<Integer,Bitmap> map_bitmap=new HashMap<>();
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final int REQUEST=41; 
	
	YHModel yhModel;
	
	ImageView mtel,mtel1,officetel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_bookinfo);  
		yhModel=(YHModel) getIntent().getSerializableExtra("YHModel");
		initView();
		//freshData();
	}
	
	private void  initView()
	{
		if(yhModel==null)
			return;
		//((TextView)findViewById(R.id.dep)).setText(yhModel.);
		 ((TextView)findViewById(R.id.title)).setText(yhModel.nameString);		
		((TextView)findViewById(R.id.name)).setText(yhModel.nameString);
		((TextView)findViewById(R.id.tel)).setText(yhModel.tel1);
		if(yhModel.dw!=null)
		{
			String string=yhModel.dw+">>"+yhModel.bmmc;
			((TextView)findViewById(R.id.dep)).setText(string);
		}else {
			freshData();
		}
		
	} 
	
	public void onClick(View v) 
	{
		finish();
	}
	
	private void  freshData()
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("id",yhModel.dwid);
		httpRequest.post(Constants.BaseURL+"DepartmentByID", map);
	}
	
	public void onPhone(View v)
	{
		String tel="";
		
		switch (v.getId()) 
		{
		case R.id.mtel:
			tel=((TextView)findViewById(R.id.tel)).getText().toString();
			break;
		case R.id.tel1:
			tel=((TextView)findViewById(R.id.tel1)).getText().toString();
			break;
		case R.id.officetel:
			tel=((TextView)findViewById(R.id.officetel)).getText().toString();
			break;

		default:
			break;
		}
		
		if(!"".equals(tel))	
		{
			Uri uri = Uri.parse("tel:"+tel);
			Intent dialntent = new Intent(Intent.ACTION_DIAL,uri);
			startActivity(dialntent);
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		 
	}

	@Override
	public void onSuccess(String result) 
	{
		JSONObject object; 
		try
		{
			object=new JSONObject(result); 
			JSONObject user=object.getJSONObject("obj") ; 
			String dwString=user.getString("zzjgmc");
			
			String string=dwString+">>"+yhModel.bmmc;
			 
			((TextView)findViewById(R.id.dep)).setText(string);
			
		} catch (Exception e) 
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
