package com.huzhouport.schedule;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.tool.PushMsg;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;
import com.hxkg.meeting.DownLoadFileActivity;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("UseValueOf")
@SuppressWarnings("unused")
public class ScheduleSeeTime extends Activity
{
	private String eventId;
	private Query query = new Query();
	private TextView tv_name,tv_kind,tv_clock,tv_time,tv_first_user,tv_userlistId,tv_userlist,tv_content,tv_location;
	private ImageView img_clock;
	private ImageButton imgbt_back,imgbt_delete,imgbt_update,imgbt_remind,imgbt_feedback,imgbt_yes,imgbt_no,imgbt_remind2,imgbt_add,sendaround;
	private GridView gv;
	private TableRow tr1,tr2;
	private int iUserType,iremind;//用户是否是发起人
	private int toastNum = -1;
	private String eventRemind,eventRemindType,agreeReason,week;
	private int toastNumber = -1,userAgree,feedback;
	private int[] timeList=new int[5];
	private int[] at_image;
	private String[] at_name,at_path;
	private String userId,oldpath,oldname;
	private User user;
	SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_see);		
		
		tv_name=(TextView)findViewById(R.id.schedule_see_name);
		tv_kind=(TextView)findViewById(R.id.schedule_see_kind);
		tv_clock=(TextView)findViewById(R.id.schedule_see_clock);
		tv_first_user=(TextView)findViewById(R.id.schedule_see_firstuser);
		tv_userlist=(TextView)findViewById(R.id.schedule_see_user);
		tv_userlistId=(TextView)findViewById(R.id.schedule_see_userId);
		tv_content=(TextView)findViewById(R.id.schedule_see_content);
		tv_time=(TextView)findViewById(R.id.schedule_see_time);
		tv_location=(TextView)findViewById(R.id.schedule_see_location);
		img_clock=(ImageView)findViewById(R.id.schedule_see_clockImage);
		gv=(GridView)findViewById(R.id.schedule_see_gridView);
		tr1=(TableRow)findViewById(R.id.schedule_see_row4);
		tr2=(TableRow)findViewById(R.id.schedule_see_row5);	
		imgbt_back=(ImageButton)findViewById(R.id.schedule_see_back);
		
		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		user=(User) intent.getSerializableExtra("User");
		userId=String.valueOf(user.getUserId());
		week=intent.getStringExtra("week");//星期x
		userId=String.valueOf(user.getUserId());		
				
		imgbt_back.setOnClickListener(new MyBack());
		((TextView)findViewById(R.id.schedule_see_timett)).setText("公务时间: ");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		new GetScheduleInfo(this).execute();
		new GetAttachment().execute();
		
	}

	class GetScheduleInfo extends AsyncTask<Void, Void, String> {
		ProgressDialog pDialog = null;

		public GetScheduleInfo() {

		}

		public GetScheduleInfo(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();	
		}

		@Override
		protected String doInBackground(Void... params)
		{
			if(isCancelled()) return null;//取消异步
			return query.queryScheduleByEventId(eventId);
		}

		@Override
		protected void onPostExecute(String result) {
			initDate(result);
			if (pDialog != null)
				pDialog.dismiss();
			super.onPostExecute(result);
		}

	}
	class GetAttachment extends AsyncTask<Void, Void, String> {
	
		@Override
		protected String doInBackground(Void... params) {
			return query.FindAttachmentByEventId(eventId);
		}

		@Override
		protected void onPostExecute(String result) {
			getAttachment(result);
			
			super.onPostExecute(result);
		}

	}
	class DeleteSchedule implements OnClickListener{

		@Override
		public void onClick(View v) {
			dialog_delete();
		}
		
	}
	public void dialog_delete(){

		Dialog	dialog =new AlertDialog.Builder(this).setTitle("删除会议").setMessage("确定删除吗？")
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						new DeleteScheduleAsyncTask().execute(eventId);
					}} )
				.setNegativeButton("取消", null)
				.show();
			
		
	}
	class DeleteScheduleAsyncTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return query.deleteEvent(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			 JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				try {
					data = (JSONObject) jsonParser.nextValue();
					String jsonObject0=data.getString("ieventId");
					if(jsonObject0.equals("1"))
					{
					   	Toast.makeText(ScheduleSeeTime.this, "删除成功", Toast.LENGTH_SHORT).show();
					   	Intent intent=new Intent(ScheduleSeeTime.this,ScheduleItemTime.class);
						intent.putExtra("User", user);
						startActivity(intent);
						finish();
						
						if(user!=null){
						    Log log = new Log(user.getName(),"删除会议安排",GlobalVar.LOGDELETE,"");  // 日志
							log.execute();
						}
					}
					else Toast.makeText(ScheduleSeeTime.this, "删除失败", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(ScheduleSeeTime.this, "发生错误", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}					
		
			super.onPostExecute(result);
		}
		
	}
	public void dialog_down()
	{
		
		Dialog	dialog2 =new AlertDialog.Builder(this).setTitle("下载文档").setMessage("下载"+oldname+"？")
				.setPositiveButton("是",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						new DownloadAttachment().execute();
					}
				} )
				.setNegativeButton("否", null)
				.show();
		/*	*/
		
	}
	class DownloadAttachment extends AsyncTask<Void, Void, Integer>
	{
		@Override
		protected Integer doInBackground(Void... params) {
			String urlstr=HttpUtil.BASE_URL+GlobalVar.FILE_SERVER_PATH+oldpath;
			int num=query.download(urlstr, GlobalVar.FILE_DOWNLOAD_PATH, oldname);
			Integer n = new Integer(num);  
			return n;
		}
		@Override
		protected void onPostExecute(Integer result) 
		{
			/*if(result==0)
			{
				Toast.makeText(ScheduleSeeTime.this,GlobalVar.FILE_DOWNLOAD_SUCCESS, Toast.LENGTH_SHORT).show();
				//openAssignFolder(Environment.getExternalStorageDirectory()+"/"+GlobalVar.FILE_DOWNLOAD_PATH);
				Intent intent=new Intent(ScheduleSeeTime.this,DownLoadFileActivity.class);
				ScheduleSeeTime.this.startActivity(intent);
			}
			if(result==1)
				Toast.makeText(ScheduleSeeTime.this, "文件已存在", Toast.LENGTH_SHORT).show();*/
			if(result==-1)
			{
				Toast.makeText(ScheduleSeeTime.this, "下载失败", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Dialog	dialog2 =new AlertDialog.Builder(ScheduleSeeTime.this).setTitle(oldname)//.setMessage("下载"+oldname+"？")
						.setPositiveButton("打开",new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) 
							{
								//new DownloadAttachment().execute();
								openAssignFolder();
							}
						} )
						.setNeutralButton("文件夹", new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent=new Intent(ScheduleSeeTime.this,DownLoadFileActivity.class);
								ScheduleSeeTime.this.startActivity(intent);
								
							}
							
						})
						.setNegativeButton("取消", null)
						.show();
			}
				
			super.onPostExecute(result);
		}
		
	}
	private void openAssignFolder()
	{  
	    /*File file = new File(path);  
	    if(null==file || !file.exists()){  
	        return;  
	    }  
	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
	    //Intent intent = new Intent(Intent.ACTION_VIEW);  
	    intent.addCategory(Intent.CATEGORY_DEFAULT);  
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	    intent.setDataAndType(Uri.fromFile(file), "file/*");  
	    try {  
	        startActivity(intent);  
	        //  startActivity(Intent.createChooser(intent,"选择浏览工具"));  
	    } catch (ActivityNotFoundException e) {  
	        e.printStackTrace();  
	    } */
	    
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.setAction(android.content.Intent.ACTION_VIEW);
	    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/download"+"/"+oldname)),"*/*");
	    ScheduleSeeTime.this.startActivity(intent);
	} 
	class MyBack implements OnClickListener{

		@Override
		public void onClick(View v) {
//			Intent intent=new Intent(ScheduleSeeTime.this,ScheduleItemTime.class);
//			intent.putExtra("User", user);
//			startActivity(intent);
			finish();		
		}
		
	}
	class GotoUpdate implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(ScheduleSeeTime.this,ScheduleUpdateTime.class);
			intent.putExtra("eventId", eventId);
			intent.putExtra("User", user);
			intent.putExtra("week", week);
			startActivity(intent);
			finish();			
		}
		
	}

	public void initDate(String result) 
	{
		String sUser = "";
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;

			data = (JSONObject) jsonParser.nextValue();

			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			JSONArray jsonArray1 = (JSONArray) jsonArray.getJSONArray(0);
			// 事件表
			JSONObject jsonObject0 = (JSONObject) jsonArray1.opt(0);
			tv_name.setText(jsonObject0.getString("eventName"));
			if (jsonObject0.getString("eventContent") == null
					| jsonObject0.getString("eventContent").length() == 0)
				tv_content.setText("无");
			else
				tv_content.setText(jsonObject0.getString("eventContent"));
			if (jsonObject0.getString("eventLocation") != null
					| jsonObject0.getString("eventLocation").length() != 0)
				tv_location.setText(jsonObject0.getString("eventLocation"));
			String time = jsonObject0.getString("eventTime");
			String[] sub = time.split(" ");
			String[] substring = sub[0].split("-");
			timeList[0] = Integer.parseInt(substring[0]);
			timeList[1] = Integer.parseInt(substring[1]);
			timeList[2] = Integer.parseInt(substring[2]);
			String[] substring2 = sub[1].split(":");
			timeList[3] = Integer.parseInt(substring2[0]);
			timeList[4] = Integer.parseInt(substring2[1]);

			
			tv_time.setText(CountTime.FormatTime2(time));
				
			JSONObject jsonObject5 = (JSONObject) jsonArray1.opt(3);
			tv_kind.setText(jsonObject5.getString("scheduleKindName"));
			feedback = jsonArray.length();
			for (int i = 0; i < jsonArray.length(); i++) 
			{
				Map<String, Object> map = new HashMap<String, Object>();
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				// 事件用户关联表
				JSONObject jsonObject6 = (JSONObject) jsonArray2.opt(1);

				// 事件用户表
				JSONObject jsonObject7 = (JSONObject) jsonArray2.opt(2);
				if (jsonObject6.getInt("userAgree") == 0)
					tv_first_user.setText(jsonObject7.getString("name"));
				sUser += jsonObject7.getString("name") + " ";
				if (jsonObject6.getString("userId").equals(userId)) {// 当前用户信息
					userAgree = Integer.parseInt(jsonObject6
							.getString("userAgree"));
					agreeReason = jsonObject6.getString("agreeReason");
					if (jsonObject6.getString("eventRemind") == null)
						eventRemind = "3";
					else
						eventRemind = jsonObject6.getString("eventRemind");
					if (jsonObject6.getString("eventRemindType") == null)
						eventRemindType = "0";
					else
						eventRemindType = jsonObject6
								.getString("eventRemindType");
					iremind = jsonObject6.getInt("eventRemind");
					if (iremind == 0) {
						img_clock.setVisibility(View.GONE);
					} else
						getClockTime(iremind);

					if (userAgree == 0) {
						imgbt_delete = (ImageButton) findViewById(R.id.schedule_see_delete);
						imgbt_update = (ImageButton) findViewById(R.id.schedule_see_update);
						imgbt_remind = (ImageButton) findViewById(R.id.schedule_see_remind);
						imgbt_feedback = (ImageButton) findViewById(R.id.schedule_see_feedback);
						// 当前用户是发起人
						tr1.setVisibility(View.VISIBLE);
						tr2.setVisibility(View.GONE);
						// 加载sliding1
						iUserType = 1;
						imgbt_delete.setOnClickListener(new DeleteSchedule());
						imgbt_update.setOnClickListener(new GotoUpdate());
						imgbt_remind.setOnClickListener(new RemindSchedule());
						imgbt_feedback.setOnClickListener(new FeedbackList());
					} else 
					{
						// 当前用户是参加人
						imgbt_yes = (ImageButton) findViewById(R.id.schedule_see_yes);
						imgbt_no = (ImageButton) findViewById(R.id.schedule_see_no);
						
						sendaround = (ImageButton) findViewById(R.id.sendaround);
						imgbt_remind2 = (ImageButton) findViewById(R.id.schedule_see_remind2);
						Date date=simpleDateFormat1.parse(time);
						if(date.before(new Date()))
						{
							imgbt_yes.setEnabled(false);
							imgbt_no.setEnabled(false);
							if(userAgree==2)
							imgbt_remind2.setEnabled(false);
							
						}
						
						tr2.setVisibility(View.VISIBLE);
						tr1.setVisibility(View.GONE);
						imgbt_yes.setOnClickListener(new ScheduleYES());
						imgbt_no.setOnClickListener(new ScheduleNO());
						sendaround.setOnClickListener(new ScheduleSendAround());
						imgbt_remind2.setOnClickListener(new RemindSchedule());
					}
				}
			}
			if (sUser.length() == 0) {
				tv_userlist.setText("无");
				tr1.setVisibility(View.GONE);
				tr2.setVisibility(View.VISIBLE);
			} else
				tv_userlist.setText(sUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class RemindSchedule implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ScheduleSeeTime.this, ScheduleClockTime.class);
			intent.putExtra("week", week);
			intent.putExtra("eventId", eventId);
			intent.putExtra("User", user);
			startActivity(intent);
			finish();
		}
	}
	class FeedbackList implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(feedback==1)
				Toast.makeText(ScheduleSeeTime.this, "没有反馈信息", Toast.LENGTH_SHORT).show();	
			else{	
			Intent intent = new Intent(ScheduleSeeTime.this, ScheduleFeedback.class);			
			intent.putExtra("eventId", eventId);
			intent.putExtra("userId", userId);
			intent.putExtra("isMeeting", "true");
			startActivity(intent);
			
			}
		}
	}

	class ScheduleYES implements OnClickListener {
		@Override
		public void onClick(View v) {
			//imgbt_yes.setEnabled(false);
			new UploadScheduleYES().execute();
		}
	}
	class ScheduleNO implements OnClickListener 
	{
		@Override
		public void onClick(View v) 
		{
			final Dialog dialog=new Dialog(ScheduleSeeTime.this);
			dialog.setContentView(R.layout.dialog_reasonbox);
			dialog.show();
			dialog.setTitle("不参与");
			final EditText reasonEditText= (EditText) dialog.findViewById(R.id.reason);
			Button button1=(Button) dialog.findViewById(R.id.b1);
			button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					
				}
			});
			Button button2=(Button) dialog.findViewById(R.id.b2);
			button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//imgbt_no.setEnabled(false);
					agreeReason=reasonEditText.getText().toString();
					
					dialog.dismiss();
					new UploadScheduleNO().execute();
					
				}
			});
		}
	}
	class UploadScheduleYES extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("scheduleEventUser.eventId", eventId);
			paramsDate.put("scheduleEventUser.userId", userId);
			paramsDate.put("scheduleEventUser.userAgree", 1);
			paramsDate.put("scheduleEventUser.agreeReason", agreeReason);
			paramsDate.put("scheduleEventUser.eventRemind", iremind);
			paramsDate.put("scheduleEventUser.eventRemindType", eventRemindType);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "AddAgreeReason";
			String result = null;
				try {
					result = hfu.post(actionUrl, paramsDate);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				data = (JSONObject) jsonParser.nextValue();
				String jsonObject0 = data.getString("ieventId");
				if (jsonObject0.equals("1"))
					Toast.makeText(ScheduleSeeTime.this, "提交成功", Toast.LENGTH_SHORT)
							.show();
				else
					Toast.makeText(ScheduleSeeTime.this, "提交失败", Toast.LENGTH_SHORT)
							.show();
			} catch (Exception e) {
				Toast.makeText(ScheduleSeeTime.this, "发生错误", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			}
			else Toast.makeText(ScheduleSeeTime.this, "提交失败", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		
	}
	class UploadScheduleNO extends AsyncTask<Void, Void, String>
	{
		@Override
		protected String doInBackground(Void... params) {
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("scheduleEventUser.eventId", eventId);
			paramsDate.put("scheduleEventUser.userId", userId);
			paramsDate.put("scheduleEventUser.userAgree", 2);
			paramsDate.put("scheduleEventUser.agreeReason", agreeReason);
			paramsDate.put("scheduleEventUser.eventRemind", iremind);
			paramsDate.put("scheduleEventUser.eventRemindType", eventRemindType);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "AddAgreeReason";
			String result = null;
				try {
					result = hfu.post(actionUrl, paramsDate);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if(result!=null){
			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				data = (JSONObject) jsonParser.nextValue();
				String jsonObject0 = data.getString("ieventId");
				if (jsonObject0.equals("1"))
					Toast.makeText(ScheduleSeeTime.this, "提交成功", Toast.LENGTH_SHORT)
							.show();
				else
					Toast.makeText(ScheduleSeeTime.this, "提交失败", Toast.LENGTH_SHORT)
							.show();
			} catch (Exception e) {
				Toast.makeText(ScheduleSeeTime.this, "发生错误", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			}
			else Toast.makeText(ScheduleSeeTime.this, "提交失败", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		
	}
	class ScheduleSendAround implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(ScheduleSeeTime.this, ScheduleAddForwardTime.class);
			String beizhu=user.getName()+"转发的"+tv_name.getText().toString();
			System.out.println("aa"+tv_userlist.getText().toString());
			intent.putExtra("User", user);
			intent.putExtra("eventId", eventId);
			intent.putExtra("sname", beizhu);
			intent.putExtra("time", tv_time.getText().toString());
			intent.putExtra("kind", tv_kind.getText().toString());
			intent.putExtra("location", tv_location.getText().toString());
			intent.putExtra("content", tv_content.getText().toString());
			intent.putExtra("content", tv_content.getText().toString());
			intent.putExtra("content", tv_content.getText().toString());
			intent.putExtra("userNamelist", tv_userlist.getText().toString());
			intent.putExtra("userNamelistId", tv_userlistId.getText().toString());
			System.out.println("cda"+tv_userlistId.getText().toString());
			startActivity(intent);
			finish();
		}
	}
	
	
	public void getAttachment(String result) {
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;

			data = (JSONObject) jsonParser.nextValue();

			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			int jsonlength = jsonArray.length();
			at_image = new int[jsonlength];
			at_name = new String[jsonlength];
			at_path = new String[jsonlength];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				at_name[i] = jsonObject.getString("attachmentName");
				at_path[i] = jsonObject.getString("attachmentPath");
				String str = jsonObject.getString("attachmentName");
				int dot = str.lastIndexOf('.');
				String substring = str.substring(dot + 1);
				if (substring.equals("doc"))
					at_image[i] = R.drawable.doc;
				else if (substring.equals("xml"))
					at_image[i] = R.drawable.xls;
				else if (substring.equals("ppt"))
					at_image[i] = R.drawable.ppt;
				else if (substring.equals("pdf"))
					at_image[i] = R.drawable.pdf;
				else if (substring.equals("mp4"))
					at_image[i] = R.drawable.video;
				else if (substring.equals("3gp"))
					at_image[i] = R.drawable.video;
				else if (substring.equals("amr"))
					at_image[i] = R.drawable.audio;
				else if (substring.equals("3ga"))
					at_image[i] = R.drawable.audio;
				else if (substring.equals("wav"))
					at_image[i] = R.drawable.audio;
				else if (substring.equals("mp3"))
					at_image[i] = R.drawable.audio;
				else
					at_image[i] = R.drawable.other_file;
			}

			ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonlength; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", at_image[i]);
				map.put("itemText", at_name[i]);

				lst.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, lst,
					R.layout.menulist,
					new String[] { "itemImage", "itemText" }, new int[] {
							R.id.imageView_ItemImage, R.id.textView_ItemText });
			gv.setAdapter(adapter);
			gv.setOnItemClickListener(new gridViewOnClickListener());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	class gridViewOnClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			oldname=at_name[arg2];
			oldpath=at_path[arg2];
			dialog_down();
		}
		
	}

	
	public void getClockTime(int iremind){
		String daytimeString;
		try {
			daytimeString = CountTime
					.countClickTime(tv_time.getText().toString(),iremind);
			tv_clock.setText(daytimeString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* int itime=timeList[3]*60+timeList[4];
		if(iremind<=itime){
			itime=itime-iremind;
			tv_clock.setText(itime/60+":"+itime%60);
		}
		else {
			itime=itime-iremind+1440;
			tv_clock.setText(itime/60+":"+itime%60);
		}*/
		
	}
	
}
