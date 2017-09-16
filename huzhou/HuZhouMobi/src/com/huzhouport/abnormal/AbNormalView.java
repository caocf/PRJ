package com.huzhouport.abnormal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.ManagerDialog;
import com.huzhouport.common.util.Query;
import com.huzhouport.integratedquery.Search_tabs;
import com.huzhouport.main.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AbNormalView extends Activity
{
	public  static int FINISH=200;
	
	private static String[] colors=//{"#333333","#d8281f"};
		{"#d8281f","#333333"};
	private static String[] colorr=//{"#d8281f","#333333"};
		{"#333333","#d8281f"};
	
	
	private static String[] status=//{"否","是"};
									{"是","否"};
	private static String[] reportstatus={"正常","异常"};
			//{"异常","正常"};
	private static String[] sail={"不明","上行","下行"};
	private static String[] sailcolor={"#d8281f","#333333","#333333"};
	private static String[] ais={"未开启","开启"};
	private static String[] colorsa={"#d8281f","#333333"};
	
	
	AbnormalInfo abnormalInfo;
	private Query query = new Query(); 
	private User user;
	
	TextView shipnameTextView;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.abnormainfo_activity);
		
		abnormalInfo= (AbnormalInfo) getIntent().getSerializableExtra("abinfo");
		user=(User) this.getIntent().getSerializableExtra("User");
		
		
		shipnameTextView=(TextView) this.findViewById(R.id.shipname);	
		String sn=abnormalInfo.getShipname();
		if(sn==null||"".equals(sn)||"null".equals(sn))
		{
			shipnameTextView.setText("无船名数据");
			shipnameTextView.setTextColor(Color.parseColor("#666666"));
			Button btsButton=(Button) this.findViewById(R.id.putshipname);
			btsButton.setText("补录船名");
			//btsButton.setVisibility(View.VISIBLE);
		}			
		else 
		{
			shipnameTextView.setText(abnormalInfo.getShipname());
			Button btsButton=(Button) this.findViewById(R.id.putshipname);
			btsButton.setText("查看详情");
			//btsButton.setVisibility(View.VISIBLE);
		}
		
		TextView gatename=(TextView) this.findViewById(R.id.gatename);
		gatename.setText(abnormalInfo.getGatename());
		
		TextView route=(TextView) this.findViewById(R.id.route);
		int n1=Integer.valueOf(abnormalInfo.getRoute());
		route.setText(sail[n1]);
		route.setTextColor(Color.parseColor(sailcolor[n1]));
		
		TextView AIS=(TextView) this.findViewById(R.id.AIS);
		int n2=Integer.valueOf(abnormalInfo.getAIS());
		AIS.setText(ais[n2]);
		AIS.setTextColor(Color.parseColor(colorsa[n2]));
		
		TextView blackship=(TextView) this.findViewById(R.id.blackship);
		String s1=abnormalInfo.getBlacklist();
		
		if(s1!=null&&abnormalInfo.getAIS().equals("1")&&!"".equals(s1))
		{
			int n3=Integer.valueOf(s1);
			blackship.setText(status[n3]);
			blackship.setTextColor(Color.parseColor(colors[n3]));	
		}
		else
		{
			blackship.setText("暂无数据");
		}
		
		
		TextView arrearage=(TextView) this.findViewById(R.id.arrearage);
		String s2=abnormalInfo.getArrearage();
		
		if(s2!=null&&abnormalInfo.getAIS().equals("1")&&!"".equals(s1))
		{
			int n4=Integer.valueOf(s2);		
			arrearage.setText(status[n4]);
			arrearage.setTextColor(Color.parseColor(colors[n4]));
			if(n4==1)
			{
				TextView textd=(TextView) this.findViewById(R.id.detail3);
				ImageButton imd=(ImageButton) this.findViewById(R.id.imdetail3);
				textd.setVisibility(View.VISIBLE);
				imd.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			arrearage.setText("暂无数据");
		}
		
		/*TextView report=(TextView) this.findViewById(R.id.report);
		String s3=abnormalInfo.getReport();
		
		if(s3!=null&&abnormalInfo.getAIS().equals("1")&&!"".equals(s1))
		{
			int n5=Integer.valueOf(s3);
			report.setText(reportstatus[n5]);
			report.setTextColor(Color.parseColor(colorr[n5]));
		}
		else
		{
			report.setText("暂无数据");
		}*/
		
		TextView illegal=(TextView) this.findViewById(R.id.illegal);
		String s4=abnormalInfo.getIllegal();
		
		if(s4!=null&&abnormalInfo.getAIS().equals("1")&&!"".equals(s1))
		{
			int n6=Integer.valueOf(abnormalInfo.getIllegal());
			illegal.setText(status[n6]);
			illegal.setTextColor(Color.parseColor(colors[n6]));
			if(n6==1)
			{
				TextView textd=(TextView) this.findViewById(R.id.detail1);
				ImageButton imd=(ImageButton) this.findViewById(R.id.imdetail1);
				textd.setVisibility(View.VISIBLE);
				imd.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			illegal.setText("暂无数据");
		}
		
		TextView cert=(TextView) this.findViewById(R.id.cert);
		String s5=abnormalInfo.getCert();
		
		if(s5!=null&&abnormalInfo.getAIS().equals("1")&&!"".equals(s1))
		{
			int n7=Integer.valueOf(s5);
			cert.setText(status[n7]);
			cert.setTextColor(Color.parseColor(colors[n7]));
			
			if(n7==1)
			{
				TextView textd=(TextView) this.findViewById(R.id.detail2);
				ImageButton imd=(ImageButton) this.findViewById(R.id.imdetail2);
				textd.setVisibility(View.VISIBLE);
				imd.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			cert.setText("暂无数据");
		}
	}
	
	public void GoBack(View v)
	{
		this.finish();
	}
	
	public void PutShip(View v)
	{
		String sn=abnormalInfo.getShipname();
		if(sn==null||"".equals(sn)||"null".equals(sn))
		{
			Intent intent;
			intent = new Intent(AbNormalView.this, AbNormalPutShipN.class);
			intent.putExtra("abinfo", abnormalInfo);
			intent.putExtra("User", user);
			this.startActivityForResult(intent, 20);
		}
		else//查看详情
		{
			Deltail(1, "0");
		}
		
		
	}
	
	public void  Deltail(final int type,final String tab)
	{	
		new ListTask(this,type,tab).execute();
		
		/*new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				
				
				
				
				
			}
		}).start();*/
	}
	
	public void CertInfo(View view)
	{	
		String string=abnormalInfo.getCert();
		if(string.equals("1")&&abnormalInfo.getAIS().equals("1"))
			Deltail(2,"1");		
	}
	
	public void IllegalInfo(View view)
	{	
		if(abnormalInfo.getIllegal().equals("1")&&abnormalInfo.getAIS().equals("1"))
			Deltail(5,"2");		
	}
	
	public void ChargeInfo(View view)
	{	
		if(abnormalInfo.getArrearage().equals("1")&&abnormalInfo.getAIS().equals("1"))
			Deltail(3,"4");		
	}
	
	public void Process(View v)
	{
		Intent intent;
		intent = new Intent(AbNormalView.this, AbNormalProcess.class);
		intent.putExtra("abnormalInfo", abnormalInfo);
		intent.putExtra("process", true);
		
		//startActivity(intent);
		this.startActivityForResult(intent, 12);
	}
	
	public void Pass(View v)
	{
		Intent intent;
		intent = new Intent(AbNormalView.this, AbNormalProcess.class);
		intent.putExtra("abnormalInfo", abnormalInfo);
		intent.putExtra("process", false);
		this.startActivityForResult(intent, 12);
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
		if(resultCode==AbNormalProcess.FINISH)
		{
			this.setResult(FINISH);
			this.finish();
		}
		if(resultCode==AbNormalPutShipN.FINISH)
		{
			 String s=data.getStringExtra("shipname");
			 if(s!=null&&!"".equals(s))
			 {
				 shipnameTextView.setText(s);
				 shipnameTextView.setTextColor(Color.parseColor("#333333"));
			 }
			 
		}
    }
	
	String checkResult;
	class CheckShip extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("electricReportNew.shipName",abnormalInfo.getShipname());

			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = "http://120:8080//HuZhouPort/" + "CheckElectricReport";
			try
			{
				checkResult = hfu.post(actionUrl, paramsDate);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			Log.e("rere", checkResult.toString());
			initData(checkResult);
			super.onPostExecute(result);
		}

	}

	public void initData(String result)
	{
		//System.out.println("check:" + result);

		if (result == null || result.equals("") || result.equals("null"))
			return;

		List<CheckResult> checkResults=new ArrayList<CheckResult>();
		
		try
		{
			JSONTokener jsonParser = new JSONTokener(result);

			JSONArray array = (JSONArray) jsonParser.nextValue();
			
			for(int i=0;i<array.length();i++)
			{
				JSONObject jsonObject = (JSONObject) array.opt(i);
				
				CheckResult checkResult=new CheckResult();
				
				checkResult.setAlarmLevel(jsonObject.getInt("alarmLevel"));

				if(jsonObject.has("description"))
					checkResult.setDescription(jsonObject.getString("description"));
				checkResult.setFilterName(jsonObject.getString("filterName"));
				
				checkResults.add(checkResult);
			}
		} catch (Exception e)
		{
		}

		String content="";
		
		for(int i=0;i<checkResults.size();i++)
		{
			CheckResult cr=checkResults.get(i);
			
			if(cr.getAlarmLevel()!=0)
			{
				if(cr.getFilterName().equals("违章检查器"))
				{
					content+="违章异常："+cr.getDescription()+"\n";
					Log.e("ll", content);
				}
				else if(cr.getFilterName().equals("证书检查器"))
				{
					content+="证书异常："+cr.getDescription()+"\n";
					Log.e("ll", content);
				}
				else if(cr.getFilterName().equals("规费检查器"))
				{
					content+="缴费异常："+cr.getDescription()+"\n";
					Log.e("ll", content);
				}
				else if(cr.getFilterName().equals("签证检查器"))
				{
					content+="签证异常："+cr.getDescription()+"\n";
					Log.e("ll", content);
				}	
			}
		}
		
		if(!content.equals(""))
		ManagerDialog.messageDialog(this, "船舶检查", content, "确定", null, "取消", null).show();
       
	}
	
	private String Search(int iMethod) 
	{
		Map<String,Object> paramsDate=new HashMap<String,Object>();
		paramsDate.put("cbname",abnormalInfo.getShipname());
		paramsDate.put("method", iMethod);
		paramsDate.put("scape", 1);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl =HttpUtil.BASE_URL+"GetAndPostDate";
				//HttpUtil.BASE_URL + "GetAndPostDate";
		String result ="";
		try 
		{
			result= hfu.post(actionUrl, paramsDate);
			//Log.e("nnn", result);
			/*if (iMethod != 5) 
			{
				if (query.CheckShipResult(result)) 
				{
					
					
				} else 
				{
					Toast.makeText(this, "没有数据", Toast.LENGTH_LONG).show();
				}
			} else {

				if (CheckShipNameByWZ()) {
					Intent intent;
					intent = new Intent(AbNormalView.this, Search_tabs.class);
					intent.putExtra("title", abnormalInfo.getShipname());
					intent.putExtra("searchType", "2");
					intent.putExtra("result", result);
					intent.putExtra("User", new User());
					startActivity(intent);
					
				} else {
					Toast.makeText(this, "没有数据", Toast.LENGTH_LONG).show();
				}

			}*/

		} catch (IOException e) 
		{
			//msg = 4;
			e.printStackTrace();
		} catch (Exception e) 
		{
			//msg = 4;
			e.printStackTrace();
		}
		
		return result;

	}
	private Boolean CheckShipNameByWZ() 
	{
		Map<String,Object> paramsDate=new HashMap<String,Object>();
		paramsDate.put("cbname", abnormalInfo.getShipname());
		paramsDate.put("method", 1);
		paramsDate.put("scape", 1);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
		try {
			String result = hfu.post(actionUrl, paramsDate);

			if (query.CheckShipResult(result)) {
				return true;
			} else {
				return false;
			}

		}

		catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}
	
	public  class ListTask extends AsyncTask<String, Integer, String> 
	{
		ProgressDialog pDialog = null;
		int type;
		String tab;
		public ListTask(Context context,int type,String tab) 
		{			
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();	
			this.type=type;
			this.tab=tab;
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			String result=Search(type);

			return result;
		}

		@Override
		protected void onPostExecute(String result) 
		{			
			if (pDialog != null)
				pDialog.dismiss();
			
			if (result == null) 
			{
				Toast.makeText(AbNormalView.this, "网络异常",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				Intent intent;
				intent = new Intent(AbNormalView.this, Search_tabs.class);
				intent.putExtra("title", abnormalInfo.getShipname());
				intent.putExtra("searchType", tab);
				intent.putExtra("result", result);
				intent.putExtra("User", new User());
				startActivity(intent);
			}
			
			super.onPostExecute(result);
		}
	}
	
	class CheckResult
	{
		int alarmLevel;
		String filterName;
		String description;
		public int getAlarmLevel()
		{
			return alarmLevel;
		}
		public void setAlarmLevel(int alarmLevel)
		{
			this.alarmLevel = alarmLevel;
		}
		public String getFilterName()
		{
			return filterName;
		}
		public void setFilterName(String filterName)
		{
			this.filterName = filterName;
		}
		public String getDescription()
		{
			return description;
		}
		public void setDescription(String description)
		{
			this.description = description;
		}
	}
}
