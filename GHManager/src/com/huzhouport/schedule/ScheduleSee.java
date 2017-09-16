package com.huzhouport.schedule;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.leaveandovertime.HttpFileUpTool;
import com.huzhouport.leaveandovertime.HttpUtil;
import com.huzhouport.leaveandovertime.PushMsg;
import com.huzhouport.leaveandovertime.Query;
import com.huzhouport.leaveandovertime.WaitingDialog;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
public class ScheduleSee extends Activity{
	private String eventId;
	private Query query = new Query();
	private TextView tv_mouth,tv_day,tv_week,tv_name,tv_kind,tv_clock,tv_time,tv_first_user,tv_userlistId,tv_userlist,tv_content,tv_location;
	private ImageView img_clock;
	private ImageButton imgbt_back,imgbt_delete,imgbt_update,imgbt_remind,imgbt_feedback,imgbt_yes,imgbt_no,imgbt_remind2,imgbt_add;
	private GridView gv;
	private TableRow tr1,tr2;
	private int iUserType,iremind;
	private int toastNum = -1;
	private String eventRemind,eventRemindType,agreeReason;
	private int toastNumber = -1,userAgree,feedback;
	private int[] timeList=new int[5];
	private int[] at_image;
	private String[] at_name,at_path;
	private String userId,oldpath,oldname;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_see);
		
		tv_mouth=(TextView)findViewById(R.id.schedule_see_mouth);
		tv_day=(TextView)findViewById(R.id.schedule_see_day);
		tv_week=(TextView)findViewById(R.id.schedule_see_week);
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
//		imgbt_add = (ImageButton) findViewById(R.id.schedule_see_add);
		
		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		//user=(User) intent.getSerializableExtra("User");
		userId=String.valueOf(User.id);
		tv_week.setText(intent.getStringExtra("week"));
		
		new GetScheduleInfo(this).execute();
		new GetAttachment().execute();
		PushMsg msg=new PushMsg();
		//msg.PushMsgToChange(eventId,userId);
		imgbt_back.setOnClickListener(new MyBack());
		
		
	}
	

	class GetScheduleInfo extends AsyncTask<Void, Void, String> {
		ProgressDialog pDialog = null;

	
		public GetScheduleInfo(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(ScheduleSee.this, this);
			pDialog.show();	
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(isCancelled()) return null;
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
	public void dialog_delete()
	{

		Dialog	dialog =new AlertDialog.Builder(this).setTitle("ɾ���ճ�").setMessage("ȷ��ɾ����")
				.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						new DeleteScheduleAsyncTask().execute(eventId);
					}} )
				.setNegativeButton("ȡ��", null)
				.show();
			
		
	}
	class DeleteScheduleAsyncTask extends AsyncTask<String, Void, String>{

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
					   	Toast.makeText(ScheduleSee.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
					   	Intent intent=new Intent(ScheduleSee.this,CalendarActivity.class);
						//intent.putExtra("User", user);
						startActivity(intent);
						finish();
						
						
					}
					else Toast.makeText(ScheduleSee.this, "ɾ��ʧ��", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(ScheduleSee.this, "��������", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}					
		
			super.onPostExecute(result);
		}
		
	}
	public void dialog_down(){
		
		Dialog	dialog2 =new AlertDialog.Builder(this).setTitle("�����ĵ�").setMessage("����"+oldname+"��")
				.setPositiveButton("��",new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						new DownloadAttachment().execute();
					}
				} )
				.setNegativeButton("��", null)
				.show();
			
		
	}
	class DownloadAttachment extends AsyncTask<Void, Void, Integer>{
		@Override
		protected Integer doInBackground(Void... params) {
			String urlstr=HttpUtil.BASE_URL+GlobalVar.FILE_SERVER_PATH+oldpath;
			int num=query.download(urlstr, GlobalVar.FILE_DOWNLOAD_PATH, oldname);
			Integer n = new Integer(num);  
			return n;
		}
		@Override
		protected void onPostExecute(Integer result) {
			if(result==0)
				Toast.makeText(ScheduleSee.this,GlobalVar.FILE_DOWNLOAD_SUCCESS, Toast.LENGTH_SHORT).show();
			if(result==1)
				Toast.makeText(ScheduleSee.this, "�ļ��Ѵ���", Toast.LENGTH_SHORT).show();
			if(result==-1)
				Toast.makeText(ScheduleSee.this, "����ʧ��", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		
	}
	class MyBack implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(ScheduleSee.this,CalendarActivity.class);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();		
		}
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent=new Intent(ScheduleSee.this,CalendarActivity.class);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();	
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	class GotoUpdate implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(ScheduleSee.this,ScheduleUpdate.class);
			intent.putExtra("eventId", eventId);
			//intent.putExtra("User", user);
			intent.putExtra("week", tv_week.getText());
			startActivity(intent);
			finish();			
		}
		
	}

	public void initDate(String result) 
	{
		if(result==null||"".equals(result.trim()))return;
		try {
			JSONObject object=new JSONObject(result.trim());
			String timeString=object.getString("scheduletime");
			String location=object.getString("location");
			String content=object.getString("content");
			String type=object.getString("type");			
			String nameString=object.getString("name");
			
			tv_name.setText(nameString);			
			tv_time.setText(timeString.substring(0, 10));
			tv_kind.setText(type);
			tv_location.setText(location);
			tv_content.setText(content);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	class RemindSchedule implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ScheduleSee.this, ScheduleClock.class);
			intent.putExtra("week", tv_week.getText());
			intent.putExtra("eventId", eventId);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();
		}
	}
	class FeedbackList implements OnClickListener{
		@Override
		public void onClick(View v) {
			if(feedback==1)
				Toast.makeText(ScheduleSee.this, "û�з�����Ϣ", Toast.LENGTH_SHORT).show();	
			else{	
			Intent intent = new Intent(ScheduleSee.this, ScheduleFeedback.class);			
			intent.putExtra("eventId", eventId);
			intent.putExtra("userId", userId);
			startActivity(intent);
			
			}
		}
	}

	class ScheduleYES implements OnClickListener {
		@Override
		public void onClick(View v) {
			new UploadScheduleYES().execute();
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
					Toast.makeText(ScheduleSee.this, "�ύ�ɹ�", Toast.LENGTH_SHORT)
							.show();
				else
					Toast.makeText(ScheduleSee.this, "�ύʧ��", Toast.LENGTH_SHORT)
							.show();
			} catch (Exception e) {
				Toast.makeText(ScheduleSee.this, "��������", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			}
			else Toast.makeText(ScheduleSee.this, "�ύʧ��", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		
	}
	class ScheduleNO implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(ScheduleSee.this, ScheduleAddForward.class);
			String beizhu=User.name+"ת����"+tv_name.getText().toString();
			//intent.putExtra("User", user);
			intent.putExtra("eventId", eventId);
			intent.putExtra("sname", beizhu);
			intent.putExtra("time", tv_time.getText().toString());
			intent.putExtra("kind", tv_kind.getText().toString());
			intent.putExtra("location", tv_location.getText().toString());
			intent.putExtra("content", tv_content.getText().toString());
			intent.putExtra("userNamelist", tv_userlist.getText().toString());
			intent.putExtra("userNamelistId", tv_userlistId.getText().toString());
			startActivity(intent);
			finish();
		}
	}
	
	public void getAttachment(String result) {
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;

			data = (JSONObject) jsonParser.nextValue();

			// �������ľ���JSON����Ĳ�����
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
