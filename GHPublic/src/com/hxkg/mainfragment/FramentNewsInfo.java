package com.hxkg.mainfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import com.hxkg.ghpublic.CenApplication;
import com.hxkg.ghpublic.R;
import com.hxkg.mainfragment.share.pop.SharePopupWindow;
import com.hztuen.gh.activity.LoginActivity;
import com.hztuen.gh.contact.Contact;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FramentNewsInfo extends Activity implements onResult
{
protected static final String TAG = FramentNewsInfo.class.getSimpleName();
	
	private TextView newsinfotitle;
	private TextView newsinfoupdatetime;
	private WebView newsinfocontent;
	private TextView newslist_back;
	private LinearLayout myLayout;
	private LinearLayout linearLayout1;
	private ImageButton message;
	private EditText write;
	private Button sendmessage;
	private Context context;
	String newsid;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newinfo);
		
		newsinfotitle = (TextView) findViewById(R.id.newsinfotitle);
		newsinfoupdatetime = (TextView) findViewById(R.id.newsinfoupdate);
		newsinfocontent = (WebView) findViewById(R.id.newsinfocontent);//内容WEBview控件
		newsinfocontent.requestFocus(); 
		newsinfocontent.getSettings().setJavaScriptEnabled(true);		
		newsinfocontent.setDownloadListener(new DownloadListener() 
		{			
			@Override
			public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype, long contentLength) 
			{
				// TODO Auto-generated method stub
				Uri uri = Uri.parse(url);   
		        Intent intent = new Intent(Intent.ACTION_VIEW, uri);   
		        startActivity(intent);
			}
		});
		newslist_back = (TextView) findViewById(R.id.newslist_back);
		newslist_back.setOnClickListener(onclicklistener);
		message = (ImageButton) findViewById(R.id.message);
		message.setOnClickListener(onclicklistener);
		myLayout = (LinearLayout) findViewById(R.id.mylayout);		
		
		//评论控件
		linearLayout1=(LinearLayout) findViewById(R.id.linearLayout1);
		write = (EditText) findViewById(R.id.write);
		write.setOnFocusChangeListener(onfocus);
		sendmessage = (Button) findViewById(R.id.sendmessage);		
		sendmessage.setOnClickListener(onclicklistener);
		//获取新闻
		getNewsInfo();
		//本地已读库
		db=((CenApplication)getApplication()).getData();
	}
	public OnFocusChangeListener onfocus = new OnFocusChangeListener() 
	{

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(hasFocus){
				//imageshare.setVisibility(View.GONE);
				message.setVisibility(View.GONE);
				sendmessage.setVisibility(View.VISIBLE);
				write.setGravity(Gravity.LEFT);

			}else{
				//imageshare.setVisibility(View.VISIBLE);
				message.setVisibility(View.VISIBLE);
				sendmessage.setVisibility(View.GONE);
				write.setGravity(Gravity.CENTER);
			}
		}
	};

	public OnClickListener onclicklistener = new OnClickListener()
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.newslist_back:
				finish();
				break;
			
			case R.id.sendmessage:
				try {
					sendmessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case R.id.message:
				Intent intent = new Intent();
				intent.putExtra("newsid", newsid);
				intent.setClass(getApplicationContext(), NewsComment.class);
				startActivity(intent);
			default:
				break;
			}
		}

	};

	//按ID获取新闻详情
	private  void getNewsInfo()
	{
		newsid = getIntent().getStringExtra("infonewsid");
		HttpRequest hr=new HttpRequest(new onResult() 
		{
			
			@Override
			public void onSuccess(String result)//填充数据 
			{
				JSONObject newsObject;
				try 
				{
					newsObject = new JSONObject(result);
					String titleString=newsObject.getString("title");
					String contentString=newsObject.getString("content");	
					String updatetime=newsObject.getString("updatetime").substring(0,10);
					newsinfotitle.setText(titleString);
					newsinfoupdatetime.setText(updatetime);
					newsinfocontent.loadDataWithBaseURL("", contentString, "text/html", "UTF-8",""); 
					
					JSONObject newstypeJsonObject=newsObject.getJSONObject("newstype");
					int typeid=newstypeJsonObject.getInt("id");
					if(typeid>3)//公告类型，不显示评论
					{
						linearLayout1.setVisibility(View.GONE);
					}
					JSONObject newregionsonObject=newsObject.getJSONObject("region");
					int regionid=newregionsonObject.getInt("id");
					//本地存入已读新闻的ID
					db.execSQL("REPLACE INTO GPUB VALUES (?,?,?)", new Object[]{newsid,typeid,regionid});
					
				} catch (JSONException e)
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
		});
		Map<String, Object> map=new HashMap<>();
		map.put("Newsid", newsid);
		hr.post(contants.baseUrl+"NewsByID", map);
	}


	public void sendmessage() throws Exception
	{
		String content = write.getText().toString();
		
		
		String toUrl = contants.sendcomment;
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Content", content);
		map.put("Newsid", newsid);
		map.put("Username", SystemStatic.userName);
		hr.post(toUrl, map);
		

	}
	@Override
	public void onSuccess(String result) 
	{
		try{
			JSONObject res = new JSONObject(result);
			int resultcode = res.getInt("resultcode");
			if(resultcode>=0){
				Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
				write.setText("");
				write.clearFocus();
			}else if(resultcode==-1){
				Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public class MyWebViewClient extends WebViewClient 
	{  

		// 如果页面中链接，如果希望点击链接继续在当前browser中响应，  

		// 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。  

		public boolean shouldOverviewUrlLoading(WebView view, String url)
		{  

		            view.loadUrl(url);  

		return true;  

		        }  

		public void onPageStarted(WebView view, String url, Bitmap favicon) 
		{  
 

		            //showProgress();  

		        }  

		public void onPageFinished(WebView view, String url) 
		{  


		            //closeProgress();  

		        }  

		public void onReceivedError(WebView view, int errorCode,  

		                String description, String failingUrl) {  

		         

		            //closeProgress();  

		        }  

		    }
	
}
