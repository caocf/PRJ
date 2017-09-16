package net.hxkg.cruise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.book.BookActivity;
import net.hxkg.ghmanager.R; 
import net.hxkg.lawexcut.ManAdapter;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.patrolboat.SearchBoatActivity;
import net.hxkg.user.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View; 
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CruiseActivity extends Activity implements HttpRequest.onResult
{   
	EditText edit_tool,edit_area;
	String edit_member="";
	GridView eg;
	ManAdapter adapter;
	List<String> list=new ArrayList<>();
	
	public static final int REQUEST_MEMBER=15;
	CruiseRecordEN cruiseRecordEN;
	TextView toatlmilest,evidnumt,timest;
	ProgressDialog sumDialog = null;
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_cruise);
		
		edit_tool=(EditText) findViewById(R.id.tools);
		edit_area=(EditText) findViewById(R.id.area);
		
		initGridView();
		initGeneral();
	}
	
	private void initGridView()
	{
		eg=(GridView) findViewById(R.id.gridView);
		list.add(User.name);
		adapter=new ManAdapter(this,list);
		eg.setAdapter(adapter);
		eg.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				if(position==0)
					return;
				list.remove(list.get(position));	
				adapter.notifyDataSetChanged();
			}			
		});
	}
	
	private void initGeneral() 
	{
		toatlmilest=(TextView) findViewById(R.id.toatlmiles);
		evidnumt=(TextView) findViewById(R.id.evidnum);
		timest=(TextView) findViewById(R.id.times);
				
		
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{

			@Override
			public void onSuccess(String result) 
			{
				try {
					JSONObject jobJsonObject=new JSONObject(result);
					String toatlmiles=jobJsonObject.getString("toatlmiles");
					toatlmilest.setText(toatlmiles);
					String evidnum=jobJsonObject.getString("evidnum");
					evidnumt.setText(evidnum);
					String times=jobJsonObject.getString("times");
					timest.setText(times);
					
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
		map.put("name", User.name);
		try {
			map.put("starttime", getMinMonthDate(new Date()));
			map.put("endtime", getMaxMonthDate(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		httpRequest.post(Constants.BaseURL+"CruiseCount", map);
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		if(resultCode==BookActivity.RESULT)
		{
			String nameString=data.getStringExtra("name");
			if(nameString==null)
				return;
			for(String s:list)
			{
				if(s.equals(nameString))
				{
					Toast.makeText(this, nameString+"已添加！", Toast.LENGTH_LONG).show();
					return;
				}
			}
			list.add(data.getStringExtra("name"));
			adapter.notifyDataSetChanged();
		}
		if(resultCode==200)
		{
			String nameString=data.getStringExtra("name");
			if(nameString==null)
				return;
			edit_tool.setText(nameString);
		}
    }
	
	public void onViewClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.start://System.out.println(Distance.Distance(120.720711,30.726721, 120.720741,30.726731));
			
			String s1=edit_tool.getText().toString();
			if(s1==null||s1.equals(""))
			{
				Toast.makeText(this, "请输入巡查工具", Toast.LENGTH_LONG).show();
				return;
			}
			String s2=edit_area.getText().toString();
			if(s2==null||s2.equals(""))
			{
				Toast.makeText(this, "请输入巡查区域", Toast.LENGTH_LONG).show();
				return;
			}
			for(String name:list)
			{
				if("".equals(edit_member))
				{
					edit_member+=name;
				}
				else 
				{
					edit_member=edit_member+","+name;
				}
				
			}
			String s3=edit_member;
			if(s3==null||s3.equals(""))
			{
				Toast.makeText(this, "请输入巡查人员", Toast.LENGTH_LONG).show();
				return;
			}	
			
			cruiseRecordEN=new CruiseRecordEN();
			cruiseRecordEN.settoolsString(s1);
			cruiseRecordEN.setArea(s2);
			cruiseRecordEN.setMember(s3);
			addcruise();
			
			break;
		case R.id.back:
			this.finish();
			break;
		case R.id.record:
			//this.finish();
			Intent intent2 =new Intent(CruiseActivity.this,CruiseListActivity.class);
			this.startActivity(intent2);
			break;
		case R.id.imb:
			Intent intent3=new Intent(this,BookActivity.class);
			intent3.putExtra("mode", 2);
			startActivityForResult(intent3, REQUEST_MEMBER);
			break;
		case R.id.imbtool:
			Intent intent4=new Intent(this,SearchBoatActivity.class);
			intent4.putExtra("mode", 2);
			startActivityForResult(intent4, REQUEST_MEMBER);
			break;

		default:
			break;
		}
	}
	
	private void addcruise()
	{
		String s1=cruiseRecordEN.getArea();
		String s2=cruiseRecordEN.gettoolsString();
		String s3=cruiseRecordEN.getMember();
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("area", s1);
		map.put("tools", s2);
		map.put("member", s3);
		map.put("status", 0);//非正式日志
		map.put("userid", User.uidString);
		httpRequest.post(Constants.BaseURL+"addcruise", map);
		sumDialog=new  ProgressDialog(CruiseActivity.this);
		sumDialog.setCancelable(false);
		sumDialog.setMessage("地图加载中");
		sumDialog.show();
	}
	
	public void onBack(View view)
	{
		finish();
	}
	@Override
	public void onDestroy()
	{
		if(sumDialog!=null)
			sumDialog.dismiss();
		super.onDestroy();
	}

	@Override
	public void onSuccess(String result)
	{
		try
		{
			JSONObject jb=new JSONObject(result);
			String string=jb.getString("resultcode");
			cruiseRecordEN.setId(Integer.valueOf(string));
			
			Intent intent =new Intent(CruiseActivity.this,InCruiseActivity.class);
			intent.putExtra("CruiseRecordEN", cruiseRecordEN);
			startActivity(intent);
			
			this.finish();
			
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
	}

	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}
	/**
	* 获取月份起始日期
	* @param date
	* @return
	* @throws ParseException
	*/
	public  String getMinMonthDate(Date date) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	* 获取月份最后日期
	* @param date
	* @return
	* @throws ParseException
	*/
	public  String getMaxMonthDate(Date date) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

}
