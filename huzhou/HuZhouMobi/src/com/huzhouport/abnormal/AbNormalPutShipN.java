package com.huzhouport.abnormal;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AbNormalPutShipN extends Activity
{	
	public static int FINISH=198; 
	
	AbnormalInfo abnormalInfo;
	private Query query = new Query(); 
	private SharedPreferences sp;
	private User user;
	
	EditText edittext;
	
	Map<String, Object> data_map=new HashMap<String, Object>();
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.abnormalputship);
		sp=this.getSharedPreferences("ABNORMAL", Context.MODE_PRIVATE);
		abnormalInfo= (AbnormalInfo) getIntent().getSerializableExtra("abinfo");
		user=(User) this.getIntent().getSerializableExtra("User");
		edittext=(EditText) this.findViewById(R.id.edit_remark);
	}
	
	public void GoBack(View v)
	{
		this.finish();
	}
	
	public void  PutName(View v)
	{	
		
		if(edittext.getText()==null||"".equals(edittext.getText()))
		{
			Toast.makeText(this, "请输入船名！", Toast.LENGTH_LONG);
			return ;
		}
		new CheckShip(this).execute();
	}

	
	String checkResult;
	class CheckShip extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pDialog = null;
		
		public CheckShip(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{					
			
			try
			{
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("excutor.potid", sp.getString("potsid", "572009"));
			map.put("potname", user.getName());
			map.put("abnormalinfo.shipname",edittext.getText());
			map.put("abnormalinfo.aid", abnormalInfo.getAid());
				//checkResult = post(	, obj);
			//checkResult = post("http://192.168.1.100:8080/HuZhouPort/updateab", obj);
			HttpFileUpTool hft = new HttpFileUpTool();
			// "http://192.168.1.100:8080/HuZhouPort/"
			checkResult= hft.post(HttpUtil.BASE_URL+"updateab", map);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			
			if (checkResult == null||"".equals(checkResult)) 
			{
				Toast.makeText(AbNormalPutShipN.this, "网络异常",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				Toast.makeText(AbNormalPutShipN.this, "补录成功",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();  
				String string=edittext.getText().toString();
		        intent.putExtra("shipname", string);  
		      
				AbNormalPutShipN.this.setResult(AbNormalPutShipN.FINISH,intent);
				AbNormalPutShipN.this.finish();
			}
			super.onPostExecute(result);
		}

	}
	
	public String post(String actionUrl,JSONObject obj)throws IOException
	{
		String sb2 = "";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setConnectTimeout(15 * 1000); // 请求的最长时间
		conn.setReadTimeout(15 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", "application/json");


		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.writeBytes(obj.toString());
		InputStream in = null;
		
		outStream.flush();

		// 得到响应码
		int res = conn.getResponseCode();
		if (res == 200) 
		{
			in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) 
			{
				sb2 = sb2 + line;
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2;
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
				Toast.makeText(AbNormalPutShipN.this, "网络异常",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				Intent intent;
				intent = new Intent(AbNormalPutShipN.this, Search_tabs.class);
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
