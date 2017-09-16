package net.hxkg.report;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import net.hxkg.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WharfDangerInfoActivity extends Activity implements onResult,RadioGroup.OnCheckedChangeListener
{
	WharfDangerreportEN wharfDangerreportEN;
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	int model;
	
	RadioButton bt1,bt2;
	EditText edit_descr;
	String  ispermit=null;
	
	String statusString[]={"待审批","批准","不批准"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wharfdangerinfo);
		wharfDangerreportEN=(WharfDangerreportEN) getIntent().getSerializableExtra("WharfDangerreportEN");
		model=getIntent().getIntExtra("model", 0);
		init();	
		initView();
		initRadioButton(); 
	}
	
	private void initView()
	{
		edit_descr=(EditText) findViewById(R.id.edit_descr);
		Button button=(Button) findViewById(R.id.button);
		if(model==0)
		{
			edit_descr.setVisibility(View.GONE);
			button.setVisibility(View.GONE);
		}
		
	}
	
	public void onView(View view)
	{
		switch (view.getId())
		{
		case R.id.button:
			Submit(null);
			break;
		case R.id.textView1:
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}
	
	public void Submit(View v)
	{
		
		if(ispermit==null)
		{
			Toast.makeText(this, "请选择是否批准", Toast.LENGTH_LONG).show();
			return;
		}
		
		String s=edit_descr.getText().toString();
		final String deString=s==null?"":s;
		
		AlertDialog.Builder builder = new Builder(this);		
		builder.setMessage("确认提交？");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(final DialogInterface dialog, int which)
			{
				Map<String, Object> map_params=new HashMap<>();
				
				map_params.put("id", wharfDangerreportEN.getId());
				map_params.put("statusid", ispermit);
				map_params.put("reason", deString);
				map_params.put("checker", User.name);
				
				HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
				{

					@Override
					public void onSuccess(String result) 
					{
						dialog.dismiss();
						Toast.makeText(WharfDangerInfoActivity.this, "提交成功", Toast.LENGTH_LONG).show();
						WharfDangerInfoActivity.this.finish();
						
					}

					@Override
					public void onError(int httpcode) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				try 
				{
					//httpRequest.post(Constants.BaseURL+"checkdangerreport", map_params);
					httpRequest.post(Constants.BaseURL+"approvalworkport", map_params);
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		builder.setNegativeButton("取消",  new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();			
			}
		});
		
		builder.create().show();
	}
	
	private void init()
	{		
		String s0=wharfDangerreportEN.getNumber();
		String s01=s0==null||s0.equals("null")?"":s0;
		String s1=wharfDangerreportEN.getShip();
		String s11=s1==null||s1.equals("null")?"":s1;
		String s2=wharfDangerreportEN.getStartport();
		String s21=s2==null||s2.equals("null")?"":s2;
		String s3=wharfDangerreportEN.getTargetport();
		String s31=s3==null||s3.equals("null")?"":s3;
		String s4=wharfDangerreportEN.getGoods();
		String s41=s4==null||s4.equals("null")?"":s4;
		String s5=wharfDangerreportEN.getGoodstype();
		String s51=s5==null||s5.equals("null")?"":s5;
		String s6=wharfDangerreportEN.getGoodsweight();
		String s61=s6==null||s6.equals("null")?"":s6;
		//String s7=wharfDangerreportEN.getUnit();
		//String s71=s7==null||s7.equals("null")?"":s7;
		String s8=wharfDangerreportEN.getPortime();
		String s81=s8==null||s8.equals("null")?"":s8;
		String s9=wharfDangerreportEN.getStatus();
		String s91=s9==null||s9.equals("null")?"":s9;
		String s10=wharfDangerreportEN.getEndtime();
		String s101=s10==null||s10.equals("null")?"":s10;
		
		((TextView)findViewById(R.id.shipname)).setText(s11);
		((TextView)findViewById(R.id.start)).setText(s21);
		((TextView)findViewById(R.id.target)).setText(s31);
		((TextView)findViewById(R.id.goods)).setText(s41);
		((TextView)findViewById(R.id.type)).setText(s51);
		((TextView)findViewById(R.id.tons)).setText(s61+"吨");
		((TextView)findViewById(R.id.time)).setText(s81);
		((TextView)findViewById(R.id.endtime)).setText(s101);
		((TextView)findViewById(R.id.status)).setText("审核状态："+statusString[wharfDangerreportEN.statusid-1]);
		((TextView)findViewById(R.id.number)).setText("报告单号："+s01);
	}
	
	private void  initRadioButton()
	{
		bt1=(RadioButton) findViewById(R.id.btn_0);
		bt2=(RadioButton) findViewById(R.id.btn_1);
		RadioGroup options=(RadioGroup) findViewById(R.id.ra_group);
		options.setOnCheckedChangeListener(this);
		if(model==0)
		{
			options.setVisibility(View.GONE);
			
		}
		
	}
	@Override
	public void onSuccess(String result) 
	{
		
	}


	@Override
	public void onError(int httpcode) 
	{		// TODO Auto-generated method stub
		
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) 
		{
		case R.id.btn_0:
			ispermit="2";//批准
			break;
		case R.id.btn_1:
			ispermit="3";//不批准
			break;

		default:
			break;
		}
		
	}
	
}
