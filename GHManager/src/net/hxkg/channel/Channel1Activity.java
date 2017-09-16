package net.hxkg.channel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.huzhouport.schedule.SelectPicPopupWindow;
import net.hxkg.ghmanager.R;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Channel1Activity extends Activity implements HttpRequest.onResult
{	 
	EditText start1,stop1,zstartn,zstopn,menber;
	TextView time1,time2;
	 ChannelSpinner channelSpinner;
	 GeneralSpinner generalSpinner;
	 DepSpinner depSpinner;
	 Map<String, String> depmap=new HashMap<>();
	 
	 SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
		initView();
		LoginHD();
	}
	
	public void onDate(View v)
	{
		//TextView txTextView=(TextView) findViewById(R.id.time1);
		TextView txTextView=(TextView)v;
		SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
				this, v, txTextView);
		menuWindow.showAtLocation(v, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0); 
	}
	
	public void LoginHD()
	{
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("username", "admin");
		//map.put("username", "汤伟强");
		map.put("password", "111111");
		hr.post(ChannelURL.URL+"user/"+"login", map);
	}
	
	public void Next(View v)
	{
		String xcr=menber.getText().toString().trim();
		String s3=start1.getText().toString().trim();
		String s4=stop1.getText().toString().trim();
		String s5=zstartn.getText().toString().trim();
		String s6=zstopn.getText().toString().trim();
		/*String s1=time1.getText().toString().trim();
		String s2=time2.getText().toString().trim();
		
		
		
		Date d1=null;
		Date d2=null;
		try {
			d1 = sdDateFormat.parse(s1);
			d2=sdDateFormat.parse(s2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!d2.after(d1))//结束时间不晚于开始时间
		{
			Toast.makeText(this, "结束时间不能晚于开始时间", Toast.LENGTH_LONG).show();
			return;
		}*/
		if("".equals(s3)&&"".equals(s4)&&"".equals(s5)&&"".equals(s6))
		{
			Toast.makeText(this, "请填写完整信息", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(xcr))
		{
			Toast.makeText(this, "请填写巡查人员", Toast.LENGTH_LONG).show();
			return;
		}
		
		Intent intent=new Intent(this,Channel2Activity.class);
		CModel model=new CModel();
		model.cyr=xcr;
		model.qd=s3;
		model.zd=s4;
		model.qdzh=s5;
		model.zdzh=s6;
		//model.starttime=s1;
		//model.endtime=s2;*/
		model.starttime=sdDateFormat.format(new Date());
		model.hdid=String.valueOf(channelSpinner.getSelectedItemPosition()+1);
		model.ztqk=String.valueOf(generalSpinner.getSelectedItemPosition()+1);
		
		//model.dept="45";
		String nameString=(String)depSpinner.getSelectedItem();
		model.dept=depmap.get(nameString);
		
		intent.putExtra("CModel", model);
		startActivity(intent);
		finish();		
	}	 
	
	private void initView()
	{
		time1=(TextView) findViewById(R.id.time1);
		time2=(TextView) findViewById(R.id.time2);
		start1=(EditText) findViewById(R.id.start1);
		stop1=(EditText) findViewById(R.id.stop1);
		zstartn=(EditText) findViewById(R.id.zstartn);
		
		zstopn=(EditText) findViewById(R.id.zstopn);
		menber=(EditText) findViewById(R.id.menber);
		
		channelSpinner=(ChannelSpinner) findViewById(R.id.channelspinner);
		generalSpinner=(GeneralSpinner) findViewById(R.id.generalspinner);
		depSpinner=(DepSpinner) findViewById(R.id.depspinner);
	}
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		 finish();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		 
		
    }
	 
	private void RequestDepData()
	{
		HttpRequest hr=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) 
			{
				System.out.println(result);
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONObject records= object.getJSONObject("records");
					JSONArray arry=records.getJSONArray("data");
					
					for(int i=0;i<arry.length();i++)
					{
						JSONObject item=(JSONObject) arry.get(i);
						ChannelDep dep=new ChannelDep();
						//dep.id=item.getString("id");
						//dep.name=item.getString("name");
						depmap.put(item.getString("name"), item.getString("id"));
					}
					depSpinner.Notify(depmap);
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
		map.put("id",12);
		map.put("isroot",0);
		hr.post(ChannelURL.URL+"user/queryalldpt", map);
	}
 

	@Override
	public void onSuccess(String result) 
	{		 
		System.out.println(result);
	
		RequestDepData();
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
