package net.hxkg.channel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;
import com.huzhouport.schedule.ScheduleSelectUser;
import com.huzhouport.slidemenu.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Channel1Activity extends Activity implements HttpRequest.onResult,OnItemClickListener
{	 
	
	TextView time1,time2,tv_user,shipEditText;
	 ChannelSpinner channelSpinner;
	 GeneralSpinner generalSpinner;
	 DepSpinner depSpinner;
	 Map<String, String> depmap=new HashMap<>();	 
	 
	 User user;
	 private String userlist = "";
	 ImageView cruisingImageView;
	 
	 SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 ArrayList<HashMap<String, Object>> usermapHashMaps = new ArrayList<HashMap<String, Object>>();
	 ArrayList<HashMap<String, Boolean>> dmapHashMaps = new ArrayList<HashMap<String, Boolean>>();
		
	LinearLayout newTaskLayout;
	RelativeLayout isCruisingLayout;
	Button button;
	ListView listView;
	List<Map<String, Object>> nameList=new ArrayList<>();
	SimpleAdapter simpleAdapter;
	PopupWindow popupWindow;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
		user=(User) getIntent().getSerializableExtra("User");
		initView();
		//LoginHD();
		
	}
	
	public void onIssueList(View view)
	{
		Intent intent=new Intent(this,IssueListActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
		
	}
	
	private void Cruising() 
	{
		Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.cruising);  
		LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿  
		circle_anim.setInterpolator(interpolator);  
		if (circle_anim != null) 
		{  
			cruisingImageView.startAnimation(circle_anim);  //开始动画  
		}
	}
	
	public void onRecordList(View view)
	{
		Intent intent = new Intent(this,RecordListActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
	public void onUserChoose(View view)
	{
		Intent intent = new Intent(this,
				ScheduleSelectUser.class);
		intent.putExtra("User", user);
		intent.putExtra("userMap", usermapHashMaps);
		intent.putExtra("dMap", dmapHashMaps);
		startActivityForResult(intent, 80);
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
		if(!isCruising())
		{
			if("".equals(shipEditText.getText().toString().trim()))
			{
				Toast.makeText(this, "请输入巡查工具", Toast.LENGTH_LONG).show();
				return;
			}
		}		
		Intent intent=new Intent(this,CruiseMapAcitvity.class);
		if(isCruising())
		{
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			
			startActivity(intent);
			this.finish();
		}else 
		{
			intent.putExtra("userids", userlist);
			intent.putExtra("usernames", tv_user.getText().toString().trim());
			intent.putExtra("tool", shipEditText.getText().toString().trim());
			startActivity(intent);
			this.finish();
		}
		
	}	 
	
	private void initView()
	{
		listView=new ListView(this);
		listView.setBackgroundColor(Color.WHITE);
		simpleAdapter=new SimpleAdapter(this, nameList, R.layout.channelitem, new String[]{"name"}, new int[]{R.id.text});
		listView.setAdapter(simpleAdapter);
		listView.setOnItemClickListener(this);
		popupWindow=new PopupWindow(this);
		popupWindow.setContentView(listView);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        popupWindow.setWidth((int) (metric.widthPixels*0.45));
        popupWindow.setHeight((int) (metric.heightPixels*0.45));
		tv_user=(TextView) findViewById(R.id.menber);
		tv_user.setText(user.getName());
		userlist+=user.getUserId();
		shipEditText=(TextView) findViewById(R.id.ship);
		newTaskLayout=(LinearLayout) findViewById(R.id.newtask);
		isCruisingLayout=(RelativeLayout) findViewById(R.id.incruise);
		button=(Button) findViewById(R.id.button);
		cruisingImageView=(ImageView) findViewById(R.id.cruising);
		Cruising();		
	}
	
	private void  updateView() {
		if(isCruising())
		{
			newTaskLayout.setVisibility(View.GONE);
			isCruisingLayout.setVisibility(View.VISIBLE);
			button.setText("返回巡航");
		}else {
			newTaskLayout.setVisibility(View.VISIBLE);
			isCruisingLayout.setVisibility(View.GONE);
			button.setText("开始巡航");
		}
	}
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
		updateView();
	}
	
	public void onViewClick(View v)
	{
		Intent intent=new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(intent);
		this.finish();		
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {		 
		if (80 == resultCode) //人员选择
		{
			usermapHashMaps = (ArrayList<HashMap<String, Object>>) data
					.getSerializableExtra("userMap");
			dmapHashMaps = (ArrayList<HashMap<String, Boolean>>) data
					.getSerializableExtra("dMap");
			if (usermapHashMaps.size() != 0) 
			{
				String stext = user.getName();
				
				for (int i = 0; i < usermapHashMaps.size(); i++) 
				{
					if ((Boolean) usermapHashMaps.get(i).get("oboolen")) 
					{
						userlist += ","+ (String) usermapHashMaps.get(i).get("uId");

						stext += ","
								+ (String) usermapHashMaps.get(i).get("uName");
					}
				}
				tv_user.setText(stext);
			}

		}
		if(200==resultCode)//工具选择
		{
			String nameString=data.getStringExtra("name");
			shipEditText.setText(nameString);
		}
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
						depmap.put(item.getString("name"), item.getString("id"));
					}
					depSpinner.Notify(depmap);
				} catch (JSONException e) 
				{
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError(int httpcode) 
			{
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("id",12);
		map.put("isroot",0);
		hr.post(ChannelURL.URL+"user/queryalldpt", map);
	}
	
	@Override
	public void onBackPressed()
	{
		Intent intent=new Intent(this,MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(intent);
		this.finish();
		
	}
	
	private boolean  isCruising() 
	{	       
		return CruiseMapAcitvity.isRunning;
		
	}
 

	@Override
	public void onSuccess(String result) 
	{		 
		System.out.println(result);
	
		RequestDepData();
	}

	@Override
	public void onError(int httpcode) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		shipEditText.setText((String)nameList.get(position).get("name"));
		nameList.clear();
		simpleAdapter.notifyDataSetChanged();
		if(popupWindow!=null&&popupWindow.isShowing())
		{
			popupWindow.dismiss();
		}
	}
	
	public void onTools(View view)
	{
		Intent intent=new Intent(this,ToolsActivity.class);
		startActivityForResult(intent, 1);
		
		/*HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() 
		{				
			@Override
			public void onSuccess(String result) {
				try {
					nameList.clear();
					JSONObject object=new JSONObject(result.trim());
					JSONArray array=object.getJSONArray("dataList"); 
					for(int i=0;i<array.length();i++)
					{
						JSONObject object2=array.getJSONObject(i);
						String nameString=object2.getString("name");
						Map<String, Object> map=new HashMap<>();
						map.put("name", nameString);
						nameList.add(map);
						
					}
				} catch (Exception e) 
				{
					
				}
				if(nameList.size()>0)
				{
					popupWindow.showAsDropDown(newTaskLayout);
				}
				simpleAdapter.notifyDataSetChanged();
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				
			}
		});
		Map<String, Object> params=new HashMap<>();
		params.put("tip", shipEditText.getText().toString().trim());
		httpRequest.post(HttpUtil.BASE_URL+"findCruiseToolsByTip", params);*/
		
	}
}
