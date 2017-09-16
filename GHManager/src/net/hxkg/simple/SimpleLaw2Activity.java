package net.hxkg.simple;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.LawBaseEN;
import net.hxkg.lawexcut.MyIllegalListActivity;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleLaw2Activity extends Activity implements HttpRequest.onResult
{	
	Map<String, Object> map_params=new HashMap<>();
	EditText n1,n2,n3,suggest,number;
	TextView depend;
	
	
	LawSimpletargetEN lawSimpletargetEN=null;
	LawBaseEN lawbase;
	String evidid;
	String status,result="";
	SharedPreferences sp;
	ProgressDialog sumDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplelaw2);
		sp=getSharedPreferences("data", 0);
		evidid=getIntent().getStringExtra("evidid");
		lawbase=(LawBaseEN) getIntent().getSerializableExtra("LawBaseEN");
		initView();
	}
	
	private void initView()
	{
		n1=(EditText) findViewById(R.id.n1);
		n2=(EditText) findViewById(R.id.n2);
		n3=(EditText) findViewById(R.id.n3);
		suggest=(EditText) findViewById(R.id.suggest);
		number=(EditText) findViewById(R.id.number);		
		depend=(EditText) findViewById(R.id.depend);
	}
	
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.back:
		case R.id.law:
			this.finish();
			break;
		case R.id.ldepend:
			Intent intent=new Intent(this,LawListActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.myevidence:
			Intent intent1=new Intent(this,MyIllegalListActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}
	
	public void Submit(View view)
	{
		
		if("".equals(depend.getText().toString())||"null".equals(depend.getText().toString()))
		{
			Toast.makeText(this, "执法依据不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(n1.getText().toString())||"".equals(n2.getText().toString())||"".equals(n3.getText().toString()))
		{
			Toast.makeText(this, "请选择条款", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(number))
		{
			Toast.makeText(this, "处罚措施不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setMessage("请选择处理方式");
		builder.setPositiveButton("现场执法", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				status="已审核";
				result="构成违章";
				putData();
				
				HttpRequest httpRequest=new HttpRequest(SimpleLaw2Activity.this);
								
				sendData(httpRequest,dialog);
			}
		});
		builder.setNeutralButton("提交审核", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				status="待审核";
				putData();
				
				HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
				{
					
					@Override
					public void onSuccess(String result) 
					{
						if(sumDialog!=null)
							sumDialog.dismiss();
						Toast.makeText(SimpleLaw2Activity.this, "提交成功，请等待审核", Toast.LENGTH_LONG).show(); 
						Intent intent1=new Intent(SimpleLaw2Activity.this,SimpleList.class);
						startActivity(intent1);
						
					}
					
					@Override
					public void onError(int httpcode) 
					{
						
					}
				});		
				
				sendData(httpRequest,dialog);
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
	
	public void putData()
	{
		map_params.put("depend", depend.getText().toString());
		map_params.put("n1", n1.getText().toString());
		map_params.put("n2", n2.getText().toString());
		map_params.put("n3", n3.getText().toString());
		map_params.put("suggest", suggest.getText().toString());
		map_params.put("number", number.getText().toString());
		
		map_params.put("evidid", evidid);	
		map_params.put("result", result);
		map_params.put("status", status);	
		map_params.put("targetid", sp.getInt("targetid", 0));
		map_params.put("actiontime",lawbase.getShowdate());
		map_params.put("location",lawbase.getLocation());
		map_params.put("reason",lawbase.getReason());
	}
	
	public void sendData(HttpRequest httpRequest,DialogInterface dialog)
	{
		try 
		{
			httpRequest.post(Constants.BaseURL+"CommitPenalty", map_params);
			sumDialog=new  ProgressDialog(SimpleLaw2Activity.this);
			sumDialog.setCancelable(true);
			sumDialog.setMessage("数据上传中");
			sumDialog.show();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		dialog.dismiss();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		
		depend.setText(data.getStringExtra("name"));
    }
	
	@Override
	public void onSuccess(String result) 
	{
		String pid="";
		try 
		{
			JSONObject dataJsonObject=new JSONObject(result);								
			pid=dataJsonObject.getString("resultcode");
			SharedPreferences.Editor editor=this.getSharedPreferences("data", 0).edit();
			editor.putInt("penaltyid", Integer.valueOf(pid));
			editor.commit();
			
		} catch (JSONException e)
		{
			e.printStackTrace();
		}	
		if(sumDialog!=null)
			sumDialog.dismiss();
		Toast.makeText(SimpleLaw2Activity.this, "提交成功", Toast.LENGTH_LONG).show();
		Intent intent=new Intent(SimpleLaw2Activity.this,OfPreview1Activity.class);
		intent.putExtra("LawBaseEN", lawbase);
		intent.putExtra("penaltyid", pid);
		intent.putExtra("type", 0);
		startActivity(intent);
		
		SimpleLaw2Activity.this.finish();
		
	}

	@Override
	public void onError(int httpcode) {
		
	}
}
