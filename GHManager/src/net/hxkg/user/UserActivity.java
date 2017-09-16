package net.hxkg.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.cruise.CruiseActivity;
import net.hxkg.cruise.CruiseListActivity; 
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.IllegalListActivity;
import net.hxkg.lawexcut.LawActivity;
import net.hxkg.lawexcut.LawOffLineListActivity;
import net.hxkg.lawexcut.MyIllegalListActivity;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest; 
import net.hxkg.system.AboutActivity;
import net.hxkg.system.CodeActivity;
import net.hxkg.system.Dialog;
import net.hxkg.system.DownloadFile;
import net.hxkg.system.VersionEN;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity implements HttpRequest.onResult
{
	TextView text_user;
	TextView text_dep;
	android.app.Dialog progress=null;
	PackageManager pm;	
	
	public static Handler handler;
	public static final int TOTALSIZE=0;
	public static final int PROGRESS=1;
	public static final int DONE=2;
	
	ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		
		pm=getPackageManager();
		
		text_user=(TextView) findViewById(R.id.user);
		text_user.setText(User.name);
		text_dep=(TextView) findViewById(R.id.dep);		
		
		handler=new Handler()
		{
			public void handleMessage(Message msg) 
			{
				switch (msg.what) 
				{
				case TOTALSIZE:
					progress=new android.app.Dialog(UserActivity.this);
					progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
					progress.setContentView(R.layout.versionupdate);
					progress.show();
					progress.setCanceledOnTouchOutside(false);
					WindowManager mwidow = getWindowManager();  
					Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
					android.view.WindowManager.LayoutParams p = progress.getWindow().getAttributes(); 
					p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.5 
					progress.getWindow().setAttributes(p); 
					pb=(ProgressBar)progress. findViewById(R.id.bar);
					pb.setMax(msg.arg1);
					
					break;
				case PROGRESS:
					pb.setProgress(msg.arg1);
					break;
				case DONE:					
					DownloadFile.installAPK(Environment.getExternalStorageDirectory()
							+ "/GHManager.apk", UserActivity.this);
					if(progress!=null)
					{
						progress.dismiss();
					}
					break;

				default:
					break;
				}
			}
		};
		Dep();
	}
	
	public void Dep()
	{
		HttpRequest hr=new HttpRequest(new HttpRequest.onResult()
		{

			@Override
			public void onSuccess(String result) 
			{
				try 
				{
					JSONObject resultJsonObject=new JSONObject(result);
					JSONObject object=resultJsonObject.getJSONObject("obj");
					String string=object.getString("zzjgjc");
					text_dep.setText(string+">>"+User.dep);
					
				} 
				catch (JSONException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
			
		});
		Map<String, Object> map=new HashMap<>();
		map.put("id", User.dw);
		hr.post(Constants.BaseURL+"DepartmentByID", map);		
		
	}
	
	public void onClick(View v)
	{
		this.finish();
	}
	
	public void onView(View v)
	{
		switch (v.getId()) 
		{
		case R.id.back:
			finish();
			break;
		case R.id.tocount:
			Intent intent =new Intent(UserActivity.this,CountActivity.class);
			startActivity(intent);
			break;
		case R.id.download:
			File filedir=new File(Environment.getExternalStorageDirectory()+"/GH");
			
			if(!filedir.exists())
			{
				filedir.mkdirs();
			}
			File file=new File(filedir.getAbsolutePath()+ "/barcode.jpg");
			BarCode.createQRImage(Constants.BaseURL+"DownLoadLatest", 700, 700, null, 
					file.getAbsolutePath());
			Intent intent1 =new Intent(UserActivity.this,CodeActivity.class);
			intent1.putExtra("path", file.getAbsolutePath());
			startActivity(intent1);
			break;
		case R.id.version:
			HttpRequest httpRequest=new HttpRequest(this);
			Map<String, Object> map=new HashMap<>();			
			PackageInfo p;
			try 
			{
				p = pm.getPackageInfo(getPackageName(), 0);
				map.put("VersionNum", p.versionCode);
				httpRequest.post(Constants.BaseURL+"versioncheck", map);
			} catch (NameNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.check:
			if(!User.checkpermit(IllegalListActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent4=new Intent(this,IllegalListActivity.class);
			startActivity(intent4);
			break;
		case R.id.potrade:
			if(!User.checkpermit(CruiseActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent3=new Intent(this,CruiseListActivity.class);
			startActivity(intent3);
			
			break;
		case R.id.law:
			if(!User.checkpermit(LawActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent2=new Intent(this,MyIllegalListActivity.class);
			startActivity(intent2);
			break;
		case R.id.simple:
			/*if(!User.checkpermit(SimpleLawActivity.class.getSimpleName()))
			{
				Toast.makeText(this, "您没有相关权限！", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent5 =new Intent(this,SimpleLawActivity.class);
			startActivity(intent5);*/
			Intent inte  =new Intent( this,LawOffLineListActivity.class); 
			startActivity(inte);
			break;
		case R.id.aboutus:
			Intent intent6 =new Intent(this,AboutActivity.class);
			startActivity(intent6);
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void onSuccess(String result) 
	{
		
		try 
		{
			JSONObject dataJsonObject=new JSONObject(result);
			int code=dataJsonObject.getInt("resultcode");
			if(code==0)
			{
				Toast.makeText(this, "已是最新版本", Toast.LENGTH_LONG).show();
			}
			else 
			{
				JSONObject versionJsonObject=dataJsonObject.getJSONObject("obj");
				String s1=versionJsonObject.getString("verstionDec");
				String s2=versionJsonObject.getString("uptime");
				String s3=versionJsonObject.getString("address");
				int num=versionJsonObject.getInt("versionNum");
				
				VersionEN version=new VersionEN();
				version.setNum(num);
				version.setDesc(s1);
				version.setUptime(s2);
				version.setAddress(Constants.BaseURL+s3);
				
				new Dialog (this,version).show();
			}
		} 
		catch (JSONException e)
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
}
