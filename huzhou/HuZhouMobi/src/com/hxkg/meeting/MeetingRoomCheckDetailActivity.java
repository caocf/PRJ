package com.hxkg.meeting;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import net.hxkg.channel.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class MeetingRoomCheckDetailActivity extends Activity
{
	TextView tx1,tx2, meetingroom,topic,tel,date,start,end,flow1,flow2,mark1,mark2,sign1,sign2;

	ImageView img,img1,img2;
	User user;
	MeetingRecord meetingRecord;
	LinearLayout container,flowl2;
	LinearLayout check1,check2;
	
	PopupWindow popupWindow=null;
	int checktype;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroomcheckdetail_activity);
		user=(User) getIntent().getSerializableExtra("User");
		meetingRecord=(MeetingRecord) getIntent().getSerializableExtra("MeetingRecord");
		initView();
	}
	
	private void initView()
	{
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		meetingroom=(TextView) findViewById(R.id.meetingroom);
		date=(TextView) findViewById(R.id.date);
		start=(TextView) findViewById(R.id.start);
		end=(TextView) findViewById(R.id.end);
		topic=(TextView) findViewById(R.id.topic);
		tel=(TextView) findViewById(R.id.tel);
		img=(ImageView) findViewById(R.id.img);
		img1=(ImageView) findViewById(R.id.img1);
		img2=(ImageView) findViewById(R.id.img2);
		flow1=(TextView) findViewById(R.id.flow1);
		flow2=(TextView) findViewById(R.id.flow2);
		mark1=(TextView) findViewById(R.id.mark1);
		mark2=(TextView) findViewById(R.id.mark2);
		sign1=(TextView) findViewById(R.id.sign1);
		sign2=(TextView) findViewById(R.id.sign2);
		container=(LinearLayout) findViewById(R.id.container);
		flowl2=(LinearLayout) findViewById(R.id.flowl2);
		
		if(meetingRecord==null)return;
		tx1.setText("申请人:  "+meetingRecord.getUsername());
		tx2.setText("申请时间:"+meetingRecord.getAptime().replace("T", " "));
		meetingroom.setText(meetingRecord.getRoomname());
		date.setText(meetingRecord.getDate());
		topic.setText(meetingRecord.getTopic());
		tel.setText(meetingRecord.getTel());
		start.setText("会议开始时间:\n"+meetingRecord.getTime().split("-")[0]);
		end.setText("会议结束时间:\n"+meetingRecord.getTime().split("-")[1]);
		
		/*switch (meetingRecord.getStatusShow()) {
		case 1:
			img.setBackgroundResource(R.drawable.meeting_approving);
			break;
		case 2:
			img.setBackgroundResource(R.drawable.meeting_approved);
			break;
		case 3:
			img.setBackgroundResource(R.drawable.meeting_reject);
			break;
		case 4:
			img.setBackgroundResource(R.drawable.pollback);
			break;

		default:
			break;
		}*/
		
		if(meetingRecord.getStatus()==1||meetingRecord.getDepstatus()==1)
			img.setBackgroundResource(R.drawable.meeting_approving);
		else if(meetingRecord.getStatus()==2&&meetingRecord.getDepstatus()==2)
			img.setBackgroundResource(R.drawable.meeting_approved);
		else if(meetingRecord.getStatus()==3||meetingRecord.getDepstatus()==3)
			img.setBackgroundResource(R.drawable.meeting_reject);
		else {
			img.setBackgroundResource(R.drawable.pollback);
		}
		
		
		if(meetingRecord.getStatus()==2)
		{
			flow1.setText("同意");
			flow1.setTextColor(Color.GREEN);
			img1.setBackgroundResource(R.drawable.ic_state_agree);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(meetingRecord.getApprovemark());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(meetingRecord.getApprovername()+" "+meetingRecord.getApprovetime().replace("T", " "));
			
		}
		else if(meetingRecord.getStatus()==3)
		{
			flow1.setText("驳回");
			flow1.setTextColor(Color.RED);
			img1.setBackgroundResource(R.drawable.ic_state_reject);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(meetingRecord.getApprovemark());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(meetingRecord.getApprovername()+" "+meetingRecord.getApprovetime().replace("T", " "));
			
			flowl2.setVisibility(View.GONE);
		}
		
		if(meetingRecord.getDepstatus()==2&&meetingRecord.getStatus()!=3)
		{
			flow2.setText("同意");
			flow2.setTextColor(Color.GREEN);
			img2.setBackgroundResource(R.drawable.ic_state_agree);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(meetingRecord.getDepapmark());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+meetingRecord.getDepaptime().replace("T", " "));
			
		}else if(meetingRecord.getDepstatus()==3&&meetingRecord.getStatus()!=3)
		{
			flow2.setText("驳回");
			flow2.setTextColor(Color.RED);
			img2.setBackgroundResource(R.drawable.ic_state_reject);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(meetingRecord.getDepapmark());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+meetingRecord.getDepaptime().replace("T", " "));
		}
		initCheck();
	}
	
	private void initCheck()
	{
		check1=(LinearLayout) findViewById(R.id.check1);
		check2=(LinearLayout) findViewById(R.id.check2);
		check1.setVisibility(View.GONE);
		check2.setVisibility(View.GONE);
		
		int userid=user.getUserId();
		//int pid=user.getPartOfDepartment();
		if((userid==meetingRecord.getApproveid())&&meetingRecord.getStatus()==1)
		{
			check1.setVisibility(View.VISIBLE);
		}
		if(meetingRecord.getOfficeid()==userid&&meetingRecord.getDepstatus()==1)
		{
			check2.setVisibility(View.VISIBLE);
		}
		
	}
	
	public void onApAgree(View view)
	{
		showCheckView(2,1);
	}
	public void onApReject(View view)
	{
		showCheckView(3,1);
	}
	public void onOfficeAgree(View view)
	{
		showCheckView(2,2);
	}
	public void onOfficeReject(View view)
	{
		showCheckView(3,2);
	}
	
	private void showCheckView(final int type1,final int type2)
	{
		checktype=type2;
		String title=type1==2?"同意":"驳回";
		
		View view =View.inflate(this, R.layout.popview_check, null);
		TextView titleTextView=(TextView) view.findViewById(R.id.title);
		titleTextView.setText(title);
		final EditText mark=(EditText) view.findViewById(R.id.mark);
		TextView cancel=(TextView) view.findViewById(R.id.cancel);
		
		TextView commit=(TextView) view.findViewById(R.id.commit);
		popupWindow=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		 
		popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.showAtLocation(findViewById(R.id.parent), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		popupWindow.setTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());  
		popupWindow.setOutsideTouchable(true);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				LayoutParams l=getWindow().getAttributes();
				l.alpha=1.0f;
				getWindow().setAttributes(l);
				
			}
		});
		
		LayoutParams l=getWindow().getAttributes();
		l.alpha=0.5f;
		getWindow().setAttributes(l);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();				
			}
		});
		
		commit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				if(type2==1)
				{
					meetingRecord.setStatus(type1);
					meetingRecord.setApprovemark(mark.getText().toString().trim());
					
				}else {
					meetingRecord.setDepstatus(type1);
					meetingRecord.setDepapmark(mark.getText().toString().trim());
				}
				submit();
			}
		});
		
	}
	
	public void onViewClick(View view)
	{
		switch (view.getId()) 
		{
		case R.id.setup_back:
		case R.id.title:
					finish();		
					break;
		case R.id.record:
			if(meetingRecord==null)
			{
				return;
			}
			Intent intentrecord=new Intent(this,MeetingRecordActivity.class);
			intentrecord.putExtra("roomid", meetingRecord.getRoomid());
			String nameString= meetingroom.getText().toString();
			intentrecord.putExtra("roomname",nameString);
			startActivity(intentrecord);
			break;
		
		default:
			break;
		}
	}
	
	private void  submit() 
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{			
			@Override
			public void onSuccess(String result) 
			{			
				Toast.makeText(MeetingRoomCheckDetailActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
				if(popupWindow!=null)popupWindow.dismiss();
				
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONObject meetingBasic=object.getJSONObject("meetingBasic");
					int status=meetingBasic.getInt("status");
					String aptimeString="";
					String apmarkString="";
					if(status==2||status==3)
					{
						aptimeString=meetingBasic.getString("approvetime");
						apmarkString=meetingBasic.getString("approvemark");
					}
					int depstatus=meetingBasic.getInt("depstatus");
					String oftimeString="";
					String ofmarkString="";
					if(depstatus==2||depstatus==3)
					{
						oftimeString=meetingBasic.getString("depaptime");
						ofmarkString=meetingBasic.getString("depapmark");
					}
					
					meetingRecord.setStatus(status);
					meetingRecord.setApprovetime(aptimeString);
					meetingRecord.setApprovemark(apmarkString);
					meetingRecord.setDepstatus(depstatus);
					meetingRecord.setDepapmark(ofmarkString);
					meetingRecord.setDepaptime(oftimeString);
				} catch (Exception e) {
					// TODO: handle exception
				}
				initView();
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		
		map.put("meetingBasic.id", meetingRecord.getId());
		map.put("meetingBasic.approvemark", meetingRecord.getApprovemark());
		map.put("meetingBasic.depapmark", meetingRecord.getDepapmark());
		map.put("meetingBasic.status", meetingRecord.getStatus());
		map.put("meetingBasic.depstatus", meetingRecord.getDepstatus());
		map.put("checktype", checktype);
		
		httpRequest.post(HttpUtil.BASE_URL+"commitCheck", map);
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (popupWindow != null && popupWindow.isShowing())
		{
			popupWindow.dismiss();
			popupWindow = null;
		}
		return super.onTouchEvent(event);
	}
	
}
