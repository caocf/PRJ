package net.hxkg.lawexcut;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import com.github.chrisbanes.photoview.PhotoView;
import net.hxkg.book.BookActivity;
import net.hxkg.ghmanager.CenApplication;
import net.hxkg.ghmanager.HomeActivity;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class LawActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	//文件添加
	GridViewAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	Bitmap addFile;
	Bitmap vedio;
	Bitmap audio;
	final Map<String, File> map_file=new HashMap<>();
	List<String> listfilename=new ArrayList<>();
	File photofile;	
	public static final int REQUEST_PHOTO=11;
	public static final int REQUEST_PIC=12;
	public static final int REQUEST_VEDIO=13;
	public static final int REQUEST_RECORD=14;	
	public static final int REQUEST_LOCATION=16;
	//参数
	Map<String, Object> map_params=new HashMap<>();
	EditText target,desc,location;
	String secman="";
	Lawtypespinner spinner;
	TextView edit_firstman,text_reason,locationtext;//text_checker;	
	Double lat=0.0,lon=0.0;
	//人员选择
	public static final int REQUEST_MEMBER=15;
	List<String> member=new ArrayList<>();
	GridView eg;
	ManAdapter manadapter;	
	ProgressDialog locationDialog = null;
	ProgressDialog sumDialog = null;
	
	SQLiteDatabase database;
	
	CNMKLocation nowLocation=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_law);
		text_reason=(TextView) findViewById(R.id.reason);
		initView();
		initGridview();
		initManView();
		database=((CenApplication)getApplication()).getData();		
		
	}
	
	private void  initManView()
	{
		eg=(GridView) findViewById(R.id.gridView1);
		manadapter=new ManAdapter(this,member);
		eg.setAdapter(manadapter);
		eg.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				member.remove(member.get(position));	
				manadapter.notifyDataSetChanged();
			}			
		});
	}
	
	private void initView()
	{
		edit_firstman=(TextView) findViewById(R.id.firstman);
		edit_firstman.setText(User.name);
		target=(EditText) findViewById(R.id.target);
		desc=(EditText) findViewById(R.id.desc);
		location=(EditText) findViewById(R.id.location);
		locationtext=(TextView) findViewById(R.id.locationtext);
		text_reason=(TextView) findViewById(R.id.reason);
		spinner=(Lawtypespinner) findViewById(R.id.spinner);
	}
	
	private void initGridview() 
	{
		addFile=BitmapFactory.decodeResource(getResources(), R.drawable.btn_add_normal);
		vedio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_videofile);		
		audio=BitmapFactory.decodeResource(getResources(), R.drawable.ic_audiofile);
		EvidenceGridView gridView=(EvidenceGridView) findViewById(R.id.gridView);
		bitmaps.add(addFile);
		adapter=new GridViewAdapter(this,bitmaps);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
	}
	
	@Override
	protected void onResume() 
	{
		if(locationDialog!=null)
			locationDialog.cancel();
		super.onResume();
	}
	
	public void onViewClick(View v)
	{
		switch (v.getId())
		{
		case R.id.back:
		case R.id.law:
			this.finish();
			break;
		case R.id.toreason:
			Intent intent=new Intent(this,ReasonActivity.class);
			startActivityForResult(intent, 100);
			break;
		case R.id.myevidence://我的取证
			Intent intent1=new Intent(this,MyIllegalListActivity.class);
			startActivity(intent1);
			break;
		case R.id.secl:
		case R.id.imb:
			Intent intent2=new Intent(this,BookActivity.class);
			intent2.putExtra("mode", 2);
			startActivityForResult(intent2, REQUEST_MEMBER);
			break;
		case R.id.imlocation://定位
			locationDialog= new ProgressDialog(LawActivity.this);
			locationDialog.setMessage("定位中");
			locationDialog.setCancelable(true);
			locationDialog.show();			
			
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if(locationDialog!=null)locationDialog.dismiss();
                	nowLocation=HomeActivity.nowLocation;
                	if(nowLocation==null)
                	{
                		Toast.makeText(LawActivity.this, "暂无定位数据，请稍后重试", Toast.LENGTH_SHORT).show();	
                		
                	}else {
                		//String textString=	LawActivity.this.location.getText().toString();
                		//String string=	textString.substring(0,textString.lastIndexOf("("));
                			LawActivity.this.locationtext.setText(
                					"(东经:"+nowLocation.getLongitude()+",\n北纬:"+nowLocation.getLatitude()+")");
                		lat=nowLocation.getLatitude();
                		lon=nowLocation.getLongitude();
					}
					
				}
			},500);
			
			/*Intent intent3=new Intent(this,PoiArroundActivity.class);
			startActivityForResult(intent3,REQUEST_LOCATION);*/
			break;
		default:
			break;
		}
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 
		//位置
		if(requestCode==REQUEST_LOCATION&&resultCode==PoiArroundActivity.RESULTCODE)
		{
			String s= data.getStringExtra("location");
			lat= data.getDoubleExtra("lat", 0);
			lon= data.getDoubleExtra("lon",0);
			location.setText(s);
		}
		//人员选择
		if(resultCode==BookActivity.RESULT)
		{
			String nameString=data.getStringExtra("name");
			if(nameString==null)
				return;
			for(String s:member)
			{
				if(s.equals(nameString))
				{
					Toast.makeText(this, nameString+"已添加！", Toast.LENGTH_LONG).show();
					return;
				}
			}
			if(edit_firstman.getText().toString().equals(nameString))
			{
				Toast.makeText(this, nameString+"已添加！", Toast.LENGTH_LONG).show();
				return;
			}
			member.add(nameString);
			manadapter.notifyDataSetChanged();
			
			
		}
		
		if(resultCode==ReasonActivity.RESULTCODE)
		{
			text_reason.setText(data.getStringExtra("reason"));
		}
		
		File file=null;
		if(requestCode==LawActivity.REQUEST_PHOTO&&resultCode==RESULT_OK)//拍照
		{
			if(photofile==null)
				return;
			
			map_file.put(photofile.getName(), photofile);
			listfilename.add(photofile.getName());
			FreshData(photofile,REQUEST_PHOTO);
		}
		else if(requestCode==LawActivity.REQUEST_PHOTO&&resultCode!=LawActivity.RESULT_OK)
		{
			photofile=null;
		}
		if(requestCode==LawActivity.REQUEST_PIC&&resultCode==RESULT_OK)//图片
		{
			Uri uri = data.getData();
			String path="";
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
	            e.printStackTrace();
	        }

	        file = new File(path);
	        map_file.put(file.getName(), file);
	        listfilename.add(file.getName());
	        FreshData(file,REQUEST_PIC);
		}		
		if(requestCode==LawActivity.REQUEST_VEDIO&&resultCode==RESULT_OK)//录像
		{
			Uri videoUri = data.getData();
			Cursor cursor = this.getContentResolver().query(videoUri, null, null, null, null);
			if(cursor.moveToFirst())
			{
				String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
				file = new File(filePath);
				map_file.put(file.getName(), file);
				listfilename.add(file.getName());
				FreshData(file,REQUEST_VEDIO);
			}
		}
		if(requestCode==LawActivity.REQUEST_RECORD&&resultCode==RESULT_OK)//录音
		{
			Uri videoUri = data.getData();
			file = new File(videoUri.getPath());
			map_file.put(file.getName(), file);
			listfilename.add(file.getName());
			FreshData(file,REQUEST_RECORD);
			
		}
		
    }
	
	private void FreshData(File file,int type)
	{
		if(bitmaps.size()>=1)
		{
			bitmaps.remove(bitmaps.size()-1);
			switch (type) 
			{
			case LawActivity.REQUEST_PHOTO:				
			case LawActivity.REQUEST_PIC:
				Bitmap bitmap_oragionBitmap=BitmapFactory.decodeFile(file.getAbsolutePath());				
				Bitmap bitmap=ThumbnailUtils.extractThumbnail(bitmap_oragionBitmap, 200, 200,  
		                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
				bitmaps.add(bitmap);
				break;
			case LawActivity.REQUEST_VEDIO:
				Bitmap vediobitmap=ThumbnailUtils.extractThumbnail(vedio, 200, 200,  
		                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
				bitmaps.add(vediobitmap);
				break;
			case LawActivity.REQUEST_RECORD:
				Bitmap audiobitmap=ThumbnailUtils.extractThumbnail(audio, 200, 200,  
		                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
				bitmaps.add(audiobitmap);
				break;
			default:
				break;
			}
		}
		bitmaps.add(addFile);
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,long id) 
	{
		if(position==bitmaps.size()-1)//最后，添加按钮
		{
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
			View v3=d_choose.findViewById(R.id.l_vedio);	
			v3.setOnClickListener(new OnClickListener() 
			{
				
				@Override
				public void onClick(View v) 
				{
					getVedio();
					d_choose.dismiss();
				}
			});
			View v4=d_choose.findViewById(R.id.l_record);	
			v4.setOnClickListener(new OnClickListener() 
			{
				
				@Override
				public void onClick(View v) 
				{
					getRecord();
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
			p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
			d_choose.getWindow().setAttributes(p);     //设置生效 
		}
		else//点击删除
		{			
			AlertDialog.Builder builder = new Builder(this);
			
			builder.setTitle(listfilename.get(position));
			builder.setPositiveButton("移除", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					map_file.remove(listfilename.get(position));
					listfilename.remove(listfilename.get(position));
					bitmaps.remove(position);
					adapter.notifyDataSetChanged();
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
					File file =map_file.get(listfilename.get(position));
					String string=file.getName();
					System.out.println(file.getAbsolutePath());
					if(string!=null&&!"".equals(string))
					{			
						String s=string.substring(string.indexOf(".")+1);
						//
						if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
						{
							File newFile=new File(file.getAbsolutePath());
							Bitmap bitmap=BitmapFactory.decodeFile(newFile.getAbsolutePath());
							final Dialog d_choose=new Dialog(LawActivity.this);
							d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);	
							d_choose.setContentView(R.layout.dialog_imgview);
							
							final PhotoView pv=	(PhotoView) d_choose.findViewById(R.id.iv_photo);
							pv.setImageBitmap(bitmap);
							
							ImageButton b=(ImageButton) d_choose.findViewById(R.id.button);
							b.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									d_choose.dismiss();
									
								}
							});
							
							ImageButton b2=(ImageButton) d_choose.findViewById(R.id.rotate);
							b2.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									pv.setRotationBy(90);
									
								}
							});
							
							pv.setScaleType(ScaleType.FIT_CENTER);
						
							//d_choose.setContentView(pv);
							d_choose.show();	
							WindowManager wmManager=d_choose.getWindow().getWindowManager();
							LayoutParams lParams=d_choose.getWindow().getAttributes();
							lParams.width=(int) (wmManager.getDefaultDisplay().getWidth()*0.9);
							lParams.height=(int) (wmManager.getDefaultDisplay().getHeight()*0.9);
							d_choose.getWindow().setAttributes(lParams);
							d_choose.setCanceledOnTouchOutside(false);
							
						}
						else//视频或音频
						{
							if(s.equals("amr"))
							{
								Intent intent = new Intent(Intent.ACTION_VIEW);
						        String type = "audio/* ";
						       
								try {
									
									 intent.setDataAndType(Uri.fromFile(file), type);
								        startActivity(intent);
								} catch (Exception e) {
									e.printStackTrace();
								} 
							}
							else 
							{
								Intent intent = new Intent(Intent.ACTION_VIEW);
						        String type = "video/* ";
						      
								try {
									intent.setDataAndType(Uri.fromFile(file), type);
								        startActivity(intent);
								} catch (Exception e) {
									e.printStackTrace();
								} 
							}					       
						}
					}
					
				}
				
			});
			
			builder.create().show();			
		}
		
	}
	
	public void Submit(View view)
	{		
		final String s1=edit_firstman.getText().toString();
		final String s2=target.getText().toString();
		final String s3=location.getText().toString();
		final String s4=text_reason.getText().toString();
		secman="";
		for(String name:member)
		{
			if("".equals(secman))
			{
				secman+=name;
			}
			else 
			{
				secman=secman+","+name;
			}
			
		}
		final String s5=secman;		
		final String s6=desc.getText().toString();
		
		if("".equals(s2))
		{
			Toast.makeText(this, "违章对象不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		/*if("".equals(s3))
		{
			Toast.makeText(this, "所在位置不能为空", Toast.LENGTH_LONG).show();
			return;
		}*/
		if("".equals(s4))
		{
			Toast.makeText(this, "违章理由不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		
		AlertDialog.Builder builder = new Builder(this);
		
		//判断网络
		ConnectivityManager connection=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=connection.getActiveNetworkInfo();
		if(info!=null&&info.isConnected()&&info.getState()==NetworkInfo.State.CONNECTED)
		{
			builder.setMessage("确认提交？");
			builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					map_params.put("lat", nowLocation.getLatitude());
					map_params.put("lon", nowLocation.getLongitude());
					map_params.put("firstman", s1);
					map_params.put("secman", s5);
					map_params.put("target", s2);
					map_params.put("reason", s4);
					map_params.put("detail", s6);
					map_params.put("location", s3+locationtext.getText().toString().trim());
					map_params.put("type", spinner.selectedValue);
					map_params.put("issimple", "0");//不是简易执法
					
					HttpRequest httpRequest=new HttpRequest(LawActivity.this);
					
					try 
					{
						httpRequest.post(Constants.BaseURL+"evidence", map_params,map_file,"evidence");
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
					dialog.dismiss();
					sumDialog=new  ProgressDialog(LawActivity.this);
					sumDialog.setCancelable(true);
					sumDialog.setMessage("数据上传中");
					sumDialog.show();
				}
			});
		}
		else//离线执法
		{
			builder.setMessage("当前网络不通，是否转为离线执法？");
			builder.setPositiveButton("离线提交", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Date date=new  Date(); 
					long time=date.getTime();
					database.execSQL("INSERT INTO Lawbase VALUES (?,?,?,?,?,?,?,?,?,?,?)",
							new Object[]{s1,s5,s2,s4,s6,s3,spinner.selectedValue,lon,lat,time,0});//0 未提交状态
					for(String key:map_file.keySet())
					{
						File file=map_file.get(key);
						String path=file.getAbsolutePath();
						database.execSQL("INSERT INTO Lawfile VALUES (?,?,?)",
								new Object[]{path,null,time});
						
					}
					
					
					dialog.dismiss();
					Toast.makeText(LawActivity.this, "保存成功", Toast.LENGTH_LONG).show();
					LawActivity.this.finish();
				}
			});
		}		
		
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
	
	private void getPhoto()
	{
		Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
		String path = Environment.getExternalStorageDirectory() + "/GH/";
		File filepath = new File(path);
		if (!filepath.exists()) 
		{

			filepath.mkdirs();

        }
		String  camera_photo_name = System.currentTimeMillis() + ".jpg";

		photofile= new File(filepath, camera_photo_name);
		getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
		startActivityForResult(getImageByCamera, LawActivity.REQUEST_PHOTO);	
	}
	private void getPic()
	{
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);		
		 
		try 
		{ 
		  startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), LawActivity.REQUEST_PIC);
		} catch (android.content.ActivityNotFoundException ex)
		{
		    	 
		}
	}
	private void getVedio()
	{
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE); 
		startActivityForResult(intent, LawActivity.REQUEST_VEDIO);
	}
	private void getRecord()
	{
		Intent intent = new Intent(Media.RECORD_SOUND_ACTION); 
		startActivityForResult(intent, LawActivity.REQUEST_RECORD);
	}

	@Override
	public void onSuccess(String result) 
	{
		if(sumDialog!=null)
			sumDialog.dismiss();
		Toast.makeText(LawActivity.this, "提交成功", Toast.LENGTH_LONG).show();
		Intent intent=new Intent(LawActivity.this,MyIllegalListActivity.class);
		LawActivity.this.startActivity(intent);
		LawActivity.this.finish();
		
		
	}

	@Override
	public void onError(int httpcode) {
		
	}
}
