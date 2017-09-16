package net.hxkg.channel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.channel.HttpRequest.onResult;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IssueDetailActivity extends Activity implements onResult,OnItemClickListener
{
	IssueEntity issueEntity;
	CruiseRecord cruiseRecord;
	TextView cruiseuser,cruisetime,issuetype,lr,channel,kz,mark;
	EvidenceGridView evidenceGridView;
	GridViewAdapter gridViewAdapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	ArrayList<String> filenameList=new ArrayList<>();
	LinearLayout setLinearLayout;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Bitmap loadingBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_issuedetail);
		issueEntity=(IssueEntity) getIntent().getSerializableExtra("IssueEntity");
		cruiseRecord=(CruiseRecord) getIntent().getSerializableExtra("CruiseRecord");
		loadingBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.icon_jiazaizhong);
		try {
			initView();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initGridView();
	}
	
	private void initView() throws Exception
	{
		cruiseuser=(TextView) findViewById(R.id.cruiseuser);
		cruisetime=(TextView) findViewById(R.id.cruisetime);
		issuetype=(TextView) findViewById(R.id.issuetype);
		lr=(TextView) findViewById(R.id.lr);
		channel=(TextView) findViewById(R.id.channel);
		kz=(TextView) findViewById(R.id.kz);
		mark=(TextView) findViewById(R.id.mark);
		setLinearLayout=(LinearLayout) findViewById(R.id.set);
		
		if(issueEntity==null||cruiseRecord==null)return;
		
		
		JSONArray userArray=new JSONArray(cruiseRecord.getUserArray());
		String useString="";
		for(int i=0;i<userArray.length();i++)
		{
			JSONObject user=userArray.getJSONObject(i);
			String nameString=user.getString("name");
			if("".equals(useString))
			{
				useString+=nameString;
			}else {
				useString+=","+nameString;
			}
		}
		cruiseuser.setText("巡查人员:"+useString);
		cruisetime.setText("巡查时间:"+cruiseRecord.getStartTime()+"~"+cruiseRecord.getEndTime());
		issuetype.setText(issueEntity.getPtypename()+"-"+issueEntity.getTypename());
		String slr=issueEntity.getLeftorright()==1?"左岸":"右岸";
		lr.setText(slr);
		channel.setText(issueEntity.getChannelname());
		kz.setText(issueEntity.getKzString());
		mark.setText(issueEntity.markString);
		String issuetimeString=issueEntity.getIssuetime();
		Date date=simpleDateFormat.parse(issuetimeString);
		if(date.getTime()>=new Date().getTime()-1000*60*60*24)
		{
			setLinearLayout.setVisibility(View.VISIBLE);
		}
		
	}
	
	private void initGridView() 
	{
		evidenceGridView=(EvidenceGridView) findViewById(R.id.evidence);
		gridViewAdapter=new GridViewAdapter(this, bitmaps);
		evidenceGridView.setAdapter(gridViewAdapter);
		evidenceGridView.setOnItemClickListener(this);
		getEvidenceData();
	}
	
	private void getEvidenceData()
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("pid", issueEntity.getIssueid());
		httpRequest.post(HttpUtil.BASE_URL+"findEvidenceByIssueID", map);
	}
	
	public void onBack(View view) 
	{
		setResult(-1);
		this.finish();
	}

	@Override
	public void onSuccess(String result) 
	{
		try {
			JSONObject jsonObject=new JSONObject(result.trim());
			JSONArray array=jsonObject.getJSONArray("dataList");
			
			for(int i=0;i<array.length();i++)
			{
				bitmaps.add(i,loadingBitmap);
				gridViewAdapter.notifyDataSetChanged();
				JSONObject fileJsonObject=array.getJSONObject(i);
				String pathString=fileJsonObject.getString("path");
				downUrlFile(HttpUtil.BASE_URL+pathString,
						Environment.getExternalStorageDirectory().getAbsolutePath()+"/HZGH/",i);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void downUrlFile(final String fileUrl,final String downPath,final int index)
	{		
		 new Thread(new Runnable() {
			
			@Override
			public void run() 
			{
				File savePath = new File(downPath);
			      if (!savePath.exists()) {   
			          savePath.mkdir();   
			      }  
			      String[] urlname = fileUrl.split("/");  
			      int len = urlname.length-1;  
			      String uname = urlname[len];//获取文件名  
			      try 
			      {  
			          File file = new File(savePath+"/"+uname);//创建新文件  
			          if(file!=null && !file.exists()){  
			              file.createNewFile();  
			          }  
			          OutputStream oputstream = new FileOutputStream(file);  
			          URL url = new URL(fileUrl);  
			          HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
			          uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true  
			          uc.connect();  
			          InputStream iputstream = uc.getInputStream();  
			          byte[] buffer = new byte[4*1024];  
			          int byteRead = -1;     
			          while((byteRead=(iputstream.read(buffer)))!= -1){  
			              oputstream.write(buffer, 0, byteRead);  
			          }  
			          oputstream.flush();    
			          iputstream.close();  
			          oputstream.close();
			          filenameList.add(index,file.getAbsolutePath());
			          System.out.println(file.getAbsolutePath());
			          Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
			          bitmaps.remove(index);
			          bitmaps.add(index,bitmap);
			          runOnUiThread(new Runnable() {
						
						@Override
						public void run() 
						{
							gridViewAdapter.notifyDataSetChanged();
							
						}
					});
			          
			      } catch (Exception e) 
			      {  
			          System.out.println("读取失败！");  
			          e.printStackTrace();  
			      }
				
			}
		}).start();	        
		    
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		if(position>filenameList.size()-1)return;
		Intent intent=new Intent(this,PhotoCheck.class);
		intent.putStringArrayListExtra("namelist", filenameList);
		intent.putExtra("currentIndex", position);
		intent.putExtra("currentName", filenameList.get(position));
		intent.putExtra("checkmode", 1);
		startActivityForResult(intent,10);
		
	}
	
	public void onEdit(View view)
	{
		Intent intent=new Intent(this,TypeActivity.class);
		intent.putExtra("IssueEntity", issueEntity);
		intent.putExtra("recordid", cruiseRecord.getId());
		intent.putStringArrayListExtra("filenameList", filenameList);
		startActivity(intent);
		this.finish();
	}
	
	public void onDelete(View view)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage("确定删除本条记录?");
		builder.setPositiveButton("删除", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				HttpRequest httpRequest=new HttpRequest(new onResult() 
				{
					
					@Override
					public void onSuccess(String result) {
						Toast.makeText(IssueDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
						IssueDetailActivity.this.setResult(200);
						IssueDetailActivity.this.finish();
					}
					
					@Override
					public void onError(int httpcode) {
						// TODO Auto-generated method stub
						
					}
				});
				Map<String, Object> map=new HashMap<>();
				map.put("cruiseIssue.id", issueEntity.getIssueid());
				map.put("cruiseIssue.recordid", cruiseRecord.getId());
				httpRequest.post(HttpUtil.BASE_URL+"deleteIssue", map);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
		builder.setCancelable(false);
		builder.create().show();
	}
	
	@Override
	public void onBackPressed()
	{
		setResult(-1);
		this.finish();
	}
}
