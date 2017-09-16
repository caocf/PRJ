package com.huzhouport.cruiselog;





import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.illegal.IllegalSee;
import com.huzhouport.main.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CruiselogViewFinsh extends Activity {
	private Query query=new Query();
	private ListView lv;
	private ArrayList<HashMap<String ,Object>> illegallist;
	private String id;
    private User user;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cruiselog_viewfinsh);

		
		user = (User) getIntent().getSerializableExtra("User");
		
		
		
		Intent intent = getIntent();
		 id = intent.getStringExtra("id");
		
	    
	
	    ImageButton back=(ImageButton)findViewById(R.id.cruiselog_viewback);
		
		lv=(ListView) findViewById(R.id.cruiselog_viewlist);
		
		
		
		ListTask task = new ListTask(this);  // 异步
        task.execute();
		
		back.setOnClickListener(new Back());
		
		
		
	}
public void getNeirong(String result){

	JSONTokener jsonParser_User = new JSONTokener(result);
	try {
		JSONObject data = (JSONObject) jsonParser_User.nextValue();
		JSONObject	jsonArray=data.getJSONObject("cruiselogbean");
		System.out.println("jsonArray==="+jsonArray);
		
		String cruiseLogLoaction=jsonArray.getString("cruiseLogLoaction");
		String cruiseLogUserName=jsonArray.getString("cruiseLogUserName");
		String shipName=jsonArray.getString("shipName");
		String startTime=jsonArray.getString("startTime").substring(0, jsonArray.getString("startTime").indexOf("."));
		String endTime=jsonArray.getString("endTime").substring(0, jsonArray.getString("endTime").indexOf("."));
		
	
		TextView cruiseLogLoactionText=(TextView)findViewById(R.id.cruiselog_cruiseLogLoactionNeirong);
		cruiseLogLoactionText.setText(cruiseLogLoaction);
		TextView cruiseLogUserNameText=(TextView)findViewById(R.id.cruiselog_cruiseLogUserNameNeirong);
		cruiseLogUserNameText.setText(cruiseLogUserName);
		TextView shipNameText=(TextView)findViewById(R.id.cruiselog_shipNameNeirong);
		shipNameText.setText(shipName);
		TextView startTimeText=(TextView)findViewById(R.id.cruiselog_startTimeNeirong);
		startTimeText.setText(startTime);
		TextView endTimeText=(TextView)findViewById(R.id.cruiselog_endTimeNeirong);
		endTimeText.setText(endTime);
		
		
		
		JSONArray	jsonArray1=data.getJSONArray("illegallist");
		 if(jsonArray1.length()==0){
			 findViewById(R.id.cruiselog_shijian).setVisibility(View.GONE);
			 }{
		illegallist=new ArrayList<HashMap<String, Object>>();
		 for(int i=0;i<jsonArray1.length();i++){
			 HashMap<String, Object> illegallistmap = new HashMap<String, Object>();	
			 JSONObject jsonObject =(JSONObject) jsonArray1.opt(i);
		     String name="违章描述："+jsonObject.getString("illegalContent");
		     String time=jsonObject.getString("illegalTime");
		     String illegalId=jsonObject.getString("illegalId");
		     illegallistmap.put("name", name);
		     illegallistmap.put("time", time);
		     illegallistmap.put("illegalId", illegalId);
		     illegallist.add(illegallistmap); 
	       }
		}
	
	


	} catch (Exception e) {
		Toast.makeText(CruiselogViewFinsh.this, "数据异常", Toast.LENGTH_SHORT).show();
		e.printStackTrace();
	}
}

public void showListview(){
	if(illegallist.size()==0){
	}else{
		SimpleAdapter adapter = new SimpleAdapter(CruiselogViewFinsh.this,illegallist, R.layout.cruiselog_viewfinshitem, new String[] {"name", "time","illegalId" }, new int[] { R.id.cruiselog_finsh_name,R.id.cruiselog_finsh_time,R.id.cruiselog_finsh_id});
    	lv.setAdapter(adapter);
    	lv.setOnItemClickListener(new Illegal());
	}
}
    
	class Back implements View.OnClickListener{
		 public void onClick(View v){
		
			finish();
		 }
	 }
	class Illegal implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
	       TextView tv_id=(TextView) arg1.findViewById(R.id.cruiselog_finsh_id);
			 Intent intent=new Intent(CruiselogViewFinsh.this,IllegalSee.class);
		     intent.putExtra("illegalId", tv_id.getText().toString());
		     intent.putExtra("User", user);	

			 //startActivityForResult(intent,100);
			 startActivity(intent);
		     //Toast.makeText(DangerousgoodsportlnList.this, tv_id.getText().toString(), Toast.LENGTH_SHORT).show();
		}
		  
	  }
	
	 class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
		  @SuppressWarnings("deprecation")
			public ListTask(Context context){
		    	  pDialog = new ProgressDialog(CruiselogViewFinsh.this);
				  pDialog.setTitle("提示");
				  pDialog.setMessage("正在加载中，请稍候。。。");
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
		protected String  doInBackground(String... params) {
			if(isCancelled()) return null;//取消异
			
			return query.showCruiseLogAndIllegal(id);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result);//获得数据
			showListview();
			super.onPostExecute(result);
		}
		  
	  }


}
