package com.huzhouport.car;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class CarCheckDetailActivity extends Activity
{
	TextView tx1,tx2, starttime,endtime,number,location,target,reason,tel,flow1,flow2,mark1,mark2,sign1,sign2;

	ImageView img,img1,img2;
	User user;
	CarApplication record;
	LinearLayout container,flowl2;
	
	LinearLayout check1,check2;	
	PopupWindow popupWindow=null;
	int checktype;
	int assignmentid=-1;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	String selectedcar;
	int selectedid;
	String driver,dtel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carcheckdetail_activity);
		user=(User) getIntent().getSerializableExtra("User");
		record=(CarApplication) getIntent().getSerializableExtra("CarApplication");
		initView();
	}
	
	private void initView()
	{
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		starttime=(TextView) findViewById(R.id.starttime);
		endtime=(TextView) findViewById(R.id.endtime);
		number=(TextView) findViewById(R.id.number);
		location=(TextView) findViewById(R.id.location);
		target=(TextView) findViewById(R.id.target);
		reason=(TextView) findViewById(R.id.reason);
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
		
		if(record==null)return;
		tx1.setText("申请人:  "+record.getUsername());
		tx2.setText("申请时间:"+record.getAptime().replace("T", " "));
		
		starttime.setText(record.getStarttime().replace("T", " "));
		endtime.setText(record.getEndtime().replace("T", " "));
		number.setText(String.valueOf(record.getNumber()));
		location.setText(record.getLocation());
		target.setText(record.getTarget());
		reason.setText(record.getReason());
		tel.setText(record.getTel());
				
		if(record.getStatus1id()!=3&&record.getStatus2id()==1)
			img.setBackgroundResource(R.drawable.meeting_approving);
		else if(record.getStatus1id()==2&&record.getStatus2id()==2)
			img.setBackgroundResource(R.drawable.meeting_approved);
		else if(record.getStatus1id()==3||record.getStatus2id()==3)
			img.setBackgroundResource(R.drawable.meeting_reject);
		else {
			img.setBackgroundResource(R.drawable.pollback);
		}
		
		if(record.getStatus1id()==2)
		{
			flow1.setText("同意");
			flow1.setTextColor(Color.GREEN);
			img1.setBackgroundResource(R.drawable.ic_state_agree);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(record.getMark1());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(record.getApname()+" "+record.getAptime1().replace("T", " "));
			
		}
		else if(record.getStatus1id()==3)
		{
			flow1.setText("驳回");
			flow1.setTextColor(Color.RED);
			img1.setBackgroundResource(R.drawable.ic_state_reject);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(record.getMark1());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(record.getApname()+" "+record.getAptime1().replace("T", " "));
			
			flowl2.setVisibility(View.GONE);
		}
		
		if(record.getStatus2id()==2&&record.getStatus1id()!=3)
		{
			flow2.setText("同意");
			flow2.setTextColor(Color.GREEN);
			img2.setBackgroundResource(R.drawable.ic_state_agree);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(record.getMark2());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+record.getAptime2().replace("T", " "));
			
		}else if(record.getStatus2id()==3&&record.getStatus1id()!=3)
		{
			flow2.setText("驳回");
			flow2.setTextColor(Color.RED);
			img2.setBackgroundResource(R.drawable.ic_state_reject);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(record.getMark2());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+record.getAptime2().replace("T", " "));
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
		int pid=user.getPartOfDepartment();
		if((userid==record.getApprover1id())&&record.getStatus1id()==1)
		{
			check1.setVisibility(View.VISIBLE);
		}
		if(record.getApprover2id()==pid&&record.getStatus2id()==1)
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
		//showCheckView(2,2);
		Intent intent=new Intent(this,CarArrange.class);
		String ddString=starttime.getText().toString().trim();
		intent.putExtra("d1", ddString);
		intent.putExtra("d2", endtime.getText().toString().trim());
		intent.putExtra("cap", record.getNumber());
		startActivityForResult(intent, 11);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==200&&requestCode==11)
		{
			selectedcar=data.getStringExtra("name");
			selectedid=data.getIntExtra("id", -1);
			driver=data.getStringExtra("driver");
			dtel=data.getStringExtra("tel");
			showCheckView(2,2);
		}
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
		if(type1==2&&type2==2)
		{
			mark.setText("已安排车辆:"+selectedcar+"\n司机:"+driver+" 电话:"+dtel);
			mark.setEnabled(false);
		}
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
					record.setStatus1id(type1);
					record.setMark1(mark.getText().toString().trim());
					record.setAptime1(simpleDateFormat.format(new Date()));
					
				}else {
					record.setStatus2id(type1);
					record.setMark2(mark.getText().toString().trim());
					record.setAssignmentid(assignmentid);
					record.setAptime2(simpleDateFormat.format(new Date()));
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
				Toast.makeText(CarCheckDetailActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
				if(popupWindow!=null)popupWindow.dismiss();
				
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONObject meetingBasic=object.getJSONObject("carApplication");				
					
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
		map.put("checktype", checktype);
		map.put("carApplication.id", record.getId());
		map.put("carApplication.status1id", record.getStatus1id());
		map.put("carApplication.mark1", record.getMark1());
		map.put("carApplication.status2id", record.getStatus2id());
		map.put("carApplication.mark2", record.getMark2());
		map.put("carApplication.assignmentid", record.getAssignmentid());
		//map.put("carApplication.aptime1", record.getAptime1() );
		//map.put("carApplication.aptime2", record.getAptime2());
		
		//httpRequest.post("http://192.168.1.135:6080/HuZhouPort/commitCarCheck", map);	
		httpRequest.post(HttpUtil.BASE_URL+"commitCarCheck", map);		
	}
	
}
