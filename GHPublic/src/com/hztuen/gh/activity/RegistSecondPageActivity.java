package com.hztuen.gh.activity;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map; 
import net.hxkg.network.HttpRequest;
import android.app.Activity; 
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent; 
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory; 
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.KeyEvent; 
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener; 
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView; 
import android.widget.ImageView.ScaleType;
import android.widget.TextView; 
import android.widget.Toast;

import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.contants;

public class RegistSecondPageActivity extends Activity
{
	EditText shipName,shipNum;
	TextView cert1,certais;
	File cert1fFile,certaisFile;
	File files[]={ cert1fFile,certaisFile};
	String username,password,tel;
	int typeid,cityid;	
	int index;	
	 
	private static final int CAMERA_WITH_DATA = 3023;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021; 
	ProgressDialog regDialog = null;
	int mode;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secondpage);
		
		shipName = (EditText) findViewById(R.id.shipname);
		shipNum = (EditText) findViewById(R.id.shipnum); 
		cert1 = (TextView) findViewById(R.id.guoji);
		certais= (TextView) findViewById(R.id.ais);
		
		username=getIntent().getStringExtra("username");
		password=getIntent().getStringExtra("password");
		tel=getIntent().getStringExtra("telphone");
		typeid=getIntent().getIntExtra("type",0);
		cityid=getIntent().getIntExtra("area",0);
		mode=getIntent().getIntExtra("mode",0);
		
		if(mode==1)//添加我的船舶模式
		{
			((TextView) findViewById(R.id.regist_back)).setText("添加船舶");
		}
		
	}
	
	public void onCamera(View v)
	{
		switch(v.getId())
		{
		case R.id.guoji:
			
			if(files[0]!=null)
			{
				showImg(0);
				return;
			}
			getFile(0);
			break;
		case R.id.ais:
			if(files[1]!=null)
			{
				showImg(1);
				return;
			}
			getFile(1); 
			break;
		}
	}
	
	private void showImg(final int index)
	{
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setTitle("选择操作");
		builder.setPositiveButton("删除", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				
				files[index]=null;
				Drawable drawable=RegistSecondPageActivity.this.getResources().getDrawable(R.drawable.registe_ic_camera);
				drawable.setBounds(0, 0, 100, 100);
				switch(index)
				{
				case 0:
					cert1.setCompoundDrawablePadding(20);			
					cert1.setCompoundDrawables(null, null, drawable, null);
					cert1.setText("");
					cert1.setHint("请确保证件照片四角完整，文字清晰可读");
					break;
					
				case 1:
					certais.setCompoundDrawablePadding(20);				
					certais.setCompoundDrawables(null, null, drawable, null);
					certais.setText("");
					certais.setHint("请确保证件照片四角完整，文字清晰可读");
					
				}
				dialog.dismiss();
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
		builder.setNeutralButton("查看", new DialogInterface.OnClickListener()
		{				
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Bitmap bitmap=BitmapFactory.decodeFile(files[index].getAbsolutePath());
				
				Dialog d_choose=new Dialog(RegistSecondPageActivity.this);
				d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);				
				
				ImageView imvImageView=new ImageView(RegistSecondPageActivity.this);
				
				imvImageView.setScaleType(ScaleType.FIT_XY);
				imvImageView.setImageBitmap(bitmap);				
				d_choose.setContentView(imvImageView);
				d_choose.show();
			}
		});
		builder.create().show();
	}
	
	private void getFile(int index)
	{
		this.index=index;
		
		final Dialog d_choose=new Dialog(this);
		d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d_choose.setContentView(R.layout.ab_dialog);
		
		View v1=d_choose.findViewById(R.id.l_photo);	
		v1.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				getPhoto();
				d_choose.dismiss();
			}
		});
		View v2=d_choose.findViewById(R.id.l_pic);	
		v2.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				getPic();
				d_choose.dismiss();
			}
		});
		View v5=d_choose.findViewById(R.id.cancel);	
		v5.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				d_choose.dismiss();
			}
		});
		
		d_choose.show();
		d_choose.setCanceledOnTouchOutside(false);
		WindowManager mwidow = getWindowManager();  
		Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
		android.view.WindowManager.LayoutParams p = d_choose.getWindow().getAttributes();  //获取对话框当前的参数值  
		//p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
		p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
		d_choose.getWindow().setAttributes(p);
		
	}
	
	private void getPhoto()
	{
		Intent getImageByCamera = new Intent(
				"android.media.action.IMAGE_CAPTURE");
		String path = Environment.getExternalStorageDirectory() + "/GH/";
		File filepath = new File(path);
		if (!filepath.exists()) 
		{

			filepath.mkdirs();

        }
		String  camera_photo_name = System.currentTimeMillis() + ".jpg";

		files[index]= new File(filepath, camera_photo_name);
		getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(files[index]));
		startActivityForResult(getImageByCamera, this.CAMERA_WITH_DATA);
	}
	private void getPic()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);		
		try 
		{ 
		  startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), this.PHOTO_PICKED_WITH_DATA);
		} catch (android.content.ActivityNotFoundException ex)
		{
		    	 
		}
	
		
	}
	
	public void onSubmitShip(View v)
	{
		HttpRequest hr=new HttpRequest(new HttpRequest.onResult() 
		{
			
			@Override
			public void onSuccess(String result) 
			{
				if(regDialog!=null)
				{
					regDialog.dismiss();
				}
				Toast.makeText(RegistSecondPageActivity.this, "资料提交成功，请等待审核", Toast.LENGTH_LONG).show();
				finish();
				
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		//判断
				if("".equals(shipName.getText().toString().trim()))
				{
					Toast.makeText(RegistSecondPageActivity.this, "请输入船名！", Toast.LENGTH_LONG).show();
					return;
				}
				if("".equals( shipNum.getText().toString().trim()))
				{
					Toast.makeText(RegistSecondPageActivity.this, "请输入船舶登记号！", Toast.LENGTH_LONG).show();
					return;
				}
				if(files[0]==null)
				{
					Toast.makeText(RegistSecondPageActivity.this, "请添加船舶国籍证书！", Toast.LENGTH_LONG).show();
					return;
				}
				if(files[1]==null)
				{
					Toast.makeText(RegistSecondPageActivity.this, "请添加船舶AIS证书！", Toast.LENGTH_LONG).show();
					return;
				}
		Map<String, File> fileMap=new HashMap<>();
		fileMap.put("GDPC", files[0]);
		fileMap.put("CON", files[1]);
		
		Map<String, Object> pmap=new HashMap<>();
		String urlString="";
		
		if(mode==0)//注册模式
		{
			urlString=contants.baseUrl+"register";
			pmap.put("username", username);
			pmap.put("password", password);
			pmap.put("tel", tel);
			pmap.put("region", cityid);
			pmap.put("usertype", typeid);
			pmap.put("shipname", shipName.getText().toString().trim());
			pmap.put("shipnum", shipNum.getText().toString().trim());
			
		}else if(mode==1)//添加船舶模式
		{
			urlString=contants.baseUrl+"addmyship";
			pmap.put("Username", username);
			pmap.put("Shipname", shipName.getText().toString().trim());
			pmap.put("Shipnum", shipNum.getText().toString().trim());
		}
		
		
		try {
			hr.post(urlString, pmap, fileMap, "");
			regDialog=new ProgressDialog(this);
			regDialog.setMessage("数据上传中...");
			regDialog.setCancelable(false);
			regDialog.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) 
	{		
		String path="";
		if(PHOTO_PICKED_WITH_DATA==requestCode&&resultCode==RESULT_OK)
		{
			Uri uri = mIntent.getData();
			
			String[] projection = {MediaStore.Images.Media.DATA};
	        Cursor cursor = null;
	        try 
	        {
	            cursor = this.getContentResolver().query(uri, projection,null, null, null);
	            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
	            if (cursor.moveToFirst()) 
	            {            	
	            	path=cursor.getString(column_index);
	            	
	            }
	        } catch (Exception e) 
	        {
	            // Eat it
	        }
	        
	        if("".equals(path))
	        {
	        	return;
	        }
	        files[index]= new File(path);
		
		}
		if(files[index]!=null)
		{
			Drawable drawable=Drawable.createFromPath(files[index].getAbsolutePath());
			drawable.setBounds(0, 0, 100, 100);
			switch(index)
			{
			case 0:
				cert1.setText(files[index].getName());
				cert1.setCompoundDrawablePadding(20);			
				cert1.setCompoundDrawables(null, null, drawable, null);
				break;
			case 1:
				certais.setText(files[1].getName());
				certais.setCompoundDrawablePadding(20);				
				certais.setCompoundDrawables(null, null, drawable, null);
			}			
			
			return;
		}		
		
	}	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK )  
		{  
			ConfirmQite(null);
		} 
		return false;
	}
	
	public void ConfirmQite(View v)
	{
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setTitle("注册未完成，确认退出吗？");
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				RegistSecondPageActivity.this.finish();
			}
		});
		builder.setNegativeButton("继续注册",  new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();			
			}
		});
		builder.create().show();
	}
}
