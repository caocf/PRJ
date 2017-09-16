package net.hxkg.cruise;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cennavi.cenmapsdk.android.location.CNMKLocation;

import com.github.chrisbanes.photoview.PhotoView;

import net.hxkg.ghmanager.HomeActivity;
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.EvidenceGridView;
import net.hxkg.lawexcut.GridViewAdapter;
import net.hxkg.lawexcut.PoiArroundActivity;
import net.hxkg.network.Constants;
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
import android.location.Location;
import android.media.ThumbnailUtils;
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
import android.widget.ImageView.ScaleType;
import android.widget.EditText; 
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
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
	EditText target,desc,location,edit_firstman;  
	TextView locationtext;
	Double lat,lon; 
	ProgressDialog locationDialog = null;
	ProgressDialog sumDialog = null;
	
	int cruiseid;
	CNMKLocation nowLocation=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_record); 
		cruiseid=getIntent().getIntExtra("cruiseid", 0);
		initView();
		initGridview(); 
	}
	
	
	private void initView()
	{
		edit_firstman=(EditText) findViewById(R.id.firstman); 
		target=(EditText) findViewById(R.id.target);
		desc=(EditText) findViewById(R.id.desc);
		location=(EditText) findViewById(R.id.location); 
		locationtext=(TextView) findViewById(R.id.locationtext); 
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
		
		case R.id.imlocation://定位
			locationDialog= new ProgressDialog(RecordActivity.this);
			locationDialog.setMessage("定位中");
			locationDialog.setCancelable(false);
			locationDialog.show();
			
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if(locationDialog!=null)locationDialog.dismiss();
                	nowLocation=HomeActivity.nowLocation;
                	if(nowLocation==null)
                	{
                		Toast.makeText(RecordActivity.this, "暂无定位数据，请稍后重试", Toast.LENGTH_SHORT).show();	
                		
                	}else {
                		//String textString=	LawActivity.this.location.getText().toString();
                		//String string=	textString.substring(0,textString.lastIndexOf("("));
                		RecordActivity.this.locationtext.setText(
                					"(东经:"+nowLocation.getLongitude()+",\n北纬:"+nowLocation.getLatitude()+")");
                		lat=nowLocation.getLatitude();
                		lon=nowLocation.getLongitude();
					}
					
				}
			},500);
			
			//Intent intent3=new Intent(this,PoiArroundActivity.class);
			//startActivityForResult(intent3,REQUEST_LOCATION);
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
		  
		
		File file=null;
		if(requestCode==this.REQUEST_PHOTO&&resultCode==RESULT_OK)//拍照
		{
			//String[] oStrings = GetFileFromPhone.getImageFromPhone(data);m			
			//file=new File(oStrings[1]);
			if(photofile==null)
				return;
			
			map_file.put(photofile.getName(), photofile);
			listfilename.add(photofile.getName());
			FreshData(photofile,REQUEST_PHOTO);
		}else if(requestCode==this.REQUEST_PHOTO&&resultCode!=this.RESULT_OK)
		{
			photofile=null;
		}
		if(requestCode==this.REQUEST_PIC&&resultCode==RESULT_OK)//图片
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
	            // Eat it
	        }

	        file = new File(path);
	        map_file.put(file.getName(), file);
	        listfilename.add(file.getName());
	        FreshData(file,REQUEST_PIC);
		}		
		if(requestCode==this.REQUEST_VEDIO&&resultCode==RESULT_OK)//录像
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
		if(requestCode==this.REQUEST_RECORD&&resultCode==RESULT_OK)//录音
		{
			Uri videoUri = data.getData();
			file = new File(videoUri.getPath());
			map_file.put(file.getName(), file);
			listfilename.add(file.getName());
			FreshData(file,REQUEST_RECORD);
			/*Cursor cursor = this.getContentResolver().query(videoUri, null, null, null, null);
			if(cursor.moveToFirst())
			{
				String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
				file = new File(videoUri.getPath());
				map_file.put(file.getName(), file);
				FreshData(file,REQUEST_RECORD);
			}*/
		}
		
    }
	
	private void FreshData(File file,int type)
	{
		if(bitmaps.size()>=1)
		{
			bitmaps.remove(bitmaps.size()-1);
			switch (type) 
			{
			case RecordActivity.REQUEST_PHOTO:				
			case RecordActivity.REQUEST_PIC:
				Bitmap bitmap_oragionBitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
				//bitmap_oragionBitmaps.add(bitmap_oragionBitmap);
				Bitmap bitmap=ThumbnailUtils.extractThumbnail(bitmap_oragionBitmap, 200, 200,  
		                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
				bitmaps.add(bitmap);
				break;
			case RecordActivity.REQUEST_VEDIO:
				Bitmap vediobitmap=ThumbnailUtils.extractThumbnail(vedio, 200, 200,  
		                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
				bitmaps.add(vediobitmap);
				//bitmap_oragionBitmaps.add(vediobitmap);
				break;
			case RecordActivity.REQUEST_RECORD:
				Bitmap audiobitmap=ThumbnailUtils.extractThumbnail(audio, 200, 200,  
		                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
				bitmaps.add(audiobitmap);
				//bitmap_oragionBitmaps.add(audiobitmap);
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
			//p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
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
					//bitmap_oragionBitmaps.remove(position);
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
							//Bitmap bitmap=bitmap_oragionBitmaps.get(position);
							final Dialog d_choose=new Dialog(RecordActivity.this);
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
						        //System.out.println(Constants.BaseURL+string);
						        Uri uri=null;
								try {
									//uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+string,"UTF-8"));
									
									 intent.setDataAndType(uri.fromFile(file), type);
								        startActivity(intent);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
							}
							else 
							{
								Intent intent = new Intent(Intent.ACTION_VIEW);
						        String type = "video/* ";
						        //System.out.println(Constants.BaseURL+string);
						        Uri uri=null;
								try {
									//uri = Uri.parse(URLDecoder.decode(Constants.BaseURL+string,"UTF-8"));
									intent.setDataAndType(uri.fromFile(file), type);
								        startActivity(intent);
								} catch (Exception e) {
									// TODO Auto-generated catch block
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
		final String s3=location.getText().toString(); 
		
		final String s6=desc.getText().toString();
				
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setMessage("确认提交？");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				//map_params.put("lat", lat);
				//map_params.put("lon", lon);
				map_params.put("title", s1);  
				map_params.put("content", s6);
				map_params.put("id", cruiseid);
				map_params.put("location", s3);  
				
				HttpRequest httpRequest=new HttpRequest(RecordActivity.this);
				
				try 
				{
					httpRequest.post(Constants.BaseURL+"AddRecord", map_params,map_file,"record");
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
				sumDialog=new  ProgressDialog(RecordActivity.this);
				sumDialog.setCancelable(false);
				sumDialog.setMessage("数据上传中");
				sumDialog.show();
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
		
		WindowManager mwidow = getWindowManager();  
		Display d = mwidow.getDefaultDisplay(); 		
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

		photofile= new File(filepath, camera_photo_name);
		getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
		startActivityForResult(getImageByCamera, this.REQUEST_PHOTO);	
	}
	private void getPic()
	{
		 Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);		
		 
		try 
		{ 
		  startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), this.REQUEST_PIC);
		} catch (android.content.ActivityNotFoundException ex)
		{
		    	 
		}
	}
	private void getVedio()
	{
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE); 
		startActivityForResult(intent, this.REQUEST_VEDIO);
	}
	private void getRecord()
	{
		/*Intent intentFromRecord=new Intent();
		intentFromRecord.setType("audio/*");
		intentFromRecord.setAction(Intent.ACTION_GET_CONTENT);
		intentFromRecord.putExtra("return-data", true);
		startActivityForResult(intentFromRecord, 100);*/
		Intent intent = new Intent(Media.RECORD_SOUND_ACTION); 
		startActivityForResult(intent, this.REQUEST_RECORD);
	}

	@Override
	public void onSuccess(String result) 
	{
		if(sumDialog!=null)
			sumDialog.dismiss();
		Toast.makeText(RecordActivity.this, "提交成功", Toast.LENGTH_LONG).show();
		RecordActivity.this.setResult(800);
		RecordActivity.this.finish();
		
		
	}

	@Override
	public void onError(int httpcode) {
		System.out.print(httpcode);
		System.out.print(httpcode);
		System.out.print(httpcode);
		
	}
}
