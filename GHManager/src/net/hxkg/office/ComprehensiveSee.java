package net.hxkg.office;

import net.hxkg.ghmanager.R; 
import org.json.JSONObject;
import org.json.JSONTokener;
import com.huzhouport.leaveandovertime.Query;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageButton; 

public class ComprehensiveSee extends Activity 
{
	private Query query = new Query();
	private WebView tex; 
	private ImageButton pns_back; 
	private int contentString;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comprehensivesee_see); 
		pns_back = (ImageButton) findViewById(R.id.comprehensivesee_see_back);	
		pns_back.setOnClickListener(new MyBack());
		Intent intent = getIntent();
		contentString=intent.getIntExtra("content",-1);//获取内容ID
		
		tex = (WebView) findViewById(R.id.comprehensivesee_see_count);
		tex.requestFocus(); 
		/*tex.getSettings().setJavaScriptEnabled(true);
		tex.loadDataWithBaseURL("", , "text/html", "UTF-8",""); */
		
		new ListTask().execute(contentString);
	}
	
	public void searchListView(String result) 
	{
		//String result=query.seePortDynmicNews(url);
		//System.out.println(result);
		JSONTokener jsonParser = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser.nextValue();
			String content=data.getString("contenct");
			WebSettings webSettings = tex.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setBuiltInZoomControls(true);
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN) ;
			tex.loadDataWithBaseURL("", "<html><body>" + content
					+ "</body></html>", "text/html", "utf-8", "");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	class MyBack implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			/*Intent intent=new Intent(PortDynmicNewsSee.this,PortDynmicNewsMain.class);
			intent.putExtra("userId", userId);
			intent.putExtra("userName", userName);
			startActivity(intent);*/
			finish();	
		}
		
	}
	 class ListTask extends AsyncTask<Integer ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context)
	      {
	    	  pDialog = new ProgressDialog(ComprehensiveSee.this);
				
				pDialog.setMessage("数据加载中...");
				pDialog.setCancelable(true);
				pDialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.setButton("取消", new DialogInterface.OnClickListener()
				{					
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.show();	
		  }
		  
		@Override
		protected String  doInBackground(Integer... params) {
		//dangerousgoodsportln=getDangerousgoodsportln1(params[0]);
		//showDangerousgoodsportln();
			
			String result;
			
				result=query.seeComprehensive(params[0]);
			
			
			
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			searchListView(result);
			super.onPostExecute(result);
		}
		  
	  }
}
