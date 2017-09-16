package net.hxkg.channel; 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.huzhouport.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Channel2Activity extends Activity implements HttpRequest.onResult
{	
	String detailString;
	CModel model;
	ProgressDialog sumDialog = null;
	ChannelAdepter channelAdepter;
	ListView itemListView;
	List<String> itemlist=new ArrayList<>();
	
	Map<String, Map<String, List<String>>> jsonmodel=new HashMap<>(); 
	 SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	final String[] first={"护岸","码头","航标","绿化","碍航","违章"};
	final String[][] second={{"压顶破损","墙身破损","勾缝脱落","踏步破损","沉降破损","其他问题"},
							{"防撞轮胎 附属设施损坏","沉降位移","码头破损","其他问题"},
							{"标志牌 缺失损毁","标志牌倾斜","标志牌被遮挡","反光膜老化脱落","浮标位移","其他问题"},
							{"树木枯死缺失","树木倒伏","树木虫病","绿化带内有杂草垃圾","其他问题"},
							{"浅滩浅点","沉船沉物","其他问题"},
							{"临时码头","过河管线管道","施工遗留物","其他问题"}};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel2);
		model=(CModel) getIntent().getSerializableExtra("CModel");
		
		itemListView=(ListView) findViewById(R.id.list);
		channelAdepter=new ChannelAdepter(this,itemlist);
		itemListView.setAdapter(channelAdepter);
	}
	
	 
	public void onType(View v)
	{
		Intent intent=new Intent(this,TypeActivity.class);
		intent.putExtra("ab", "1");
		intent.putExtra("ab", "1");
		intent.putExtra("ab", "1");
		startActivityForResult(intent, 102);
	}
	
	public void addItem(View v)
	{
		final Dialog d_choose=new Dialog(this);
		d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d_choose.setContentView(R.layout.channel_dialog);
		d_choose.show();
		WindowManager mwidow = getWindowManager();  
		Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = d_choose.getWindow().getAttributes();  //获取对话框当前的参数值  
		//p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
		d_choose.getWindow().setAttributes(p); 
		
		
		final ItemSpinner itemSpinner=(ItemSpinner) d_choose.findViewById(R.id.sp);		
		
		Button button=(Button) d_choose.findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent iniIntent=new Intent(Channel2Activity.this,TypeActivity.class);
				iniIntent.putExtra("item", itemSpinner.getSelectedItemPosition());
				startActivityForResult(iniIntent, 102);
				d_choose.cancel();
				
			}
		});
	}
	
	public void Submit(View v)
	{
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setTitle("确认提交本次巡查日志？");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{				
				Commit();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消",  new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();			
			}
		});
		builder.create().show();
		
	}
	
	
	private void Commit()
	{		
		try 
		{
			JSONArray array=new JSONArray();
			JSONObject object =new  JSONObject();
			object.put("qks", array);
			//构建
			for(String key:jsonmodel.keySet())
			{
				Map<String, List<String>> map=jsonmodel.get(key);
				JSONObject object1 =new  JSONObject();
				object1.put("qk", key);
				object1.put("qksm", first[Integer.valueOf(key)-1]+"情况");
				JSONArray wts=new JSONArray();
				for(String seckey:map.keySet())
				{
					List<String> list=map.get(seckey);
					JSONObject object2 =new  JSONObject();
					object2.put("wt", seckey);
					object2.put("wtsm", second[Integer.valueOf(key)-1][Integer.valueOf(seckey)-1]);
					object2.put("clyj", "");
					object2.put("cljg", "");
					
					JSONArray sjs=new JSONArray();
					for(String json:list)
					{
						JSONObject object3 =new  JSONObject(json);
						sjs.put(object3);
					}
					object2.put("sjs", sjs);
					wts.put(object2);		
				}
				object1.put("wts", wts);
				array.put(object1);					
			}
			
			detailString=object.toString();
			System.out.println(detailString);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("jsondata", detailString);
		map.put("gk.starttime", model.starttime);
		map.put("gk.endtime", sdDateFormat.format(new Date()));
		map.put("gk.cyr", model.cyr);
		map.put("gk.hdid", model.hdid);
		map.put("gk.ztqk", model.ztqk);		
		map.put("gk.dept", model.dept);
		
		map.put("gk.qd", model.qd);
		map.put("gk.zd", model.zd);
		map.put("gk.qdzh", model.qdzh);		
		map.put("gk.zdzh", model.zdzh);	
		
		sumDialog=new  ProgressDialog(this);
		sumDialog.setCancelable(false);
		sumDialog.setMessage("数据上传中");
		sumDialog.show();
		hr.post(ChannelURL.URL+"addcruise", map);
	}
	 
	
	@Override
	protected void onResume() 
	{ 
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		Submit(v);
	}
	
	public void onBack(View view)
	{
		finish();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		if(data!=null)
		{
			String json= data.getStringExtra("json");
			int index= data.getIntExtra("index",1);
			String name= data.getStringExtra("name");
			int itemindex= data.getIntExtra("itemindex",1);
			String itemname= data.getStringExtra("itemname");
			String title=(itemlist.size()+1)+"  "+itemname+">>"+name;
			itemlist.add(title);
			channelAdepter.notifyDataSetChanged();
			
			Map<String, List<String>> secondMap=null;
			List<String> list=null;
			secondMap=jsonmodel.get(String.valueOf(itemindex));
			if(secondMap==null)
			{
				secondMap=new HashMap<>();
				list=new ArrayList<>();
				list.add(json);
				secondMap.put(String.valueOf(index), list);
				jsonmodel.put(String.valueOf(itemindex), secondMap);
				
			}else 
			{
				list=secondMap.get(index);
				if(list==null)
				{
					list=new ArrayList<>();
					list.add(json);
					secondMap.put(String.valueOf(index), list);
					
				}else 
				{
					list.add(json);
				}
			}
		}
		
		
    }
	
	@Override
	public void onBackPressed()
	{
		Submit(null);
	}
 

	@Override
	public void onSuccess(String result) 
	{
		//System.out.println(result); 
		if(sumDialog!=null)
			sumDialog.dismiss();
		Toast.makeText(this, "提交成功", Toast.LENGTH_LONG).show();
		finish();
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
