package net.hxkg.ais;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import net.hxkg.ghmanager.R;
import net.hxkg.lawexcut.EvidenceGridView;
import net.hxkg.lawexcut.GridViewAdapter;
import net.hxkg.lawexcut.PoiArroundActivity;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.user.User;
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
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.EditText; 
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditAISActivity extends Activity implements OnItemClickListener,HttpRequest.onResult
{	
	//文件添加
	GridViewAdapter adapter;
	List<Bitmap> bitmaps=new ArrayList<>();
	Bitmap addFile; 
	final Map<String, File> map_file=new HashMap<>(); 
	File photofile=null;	
	public static final int REQUEST_PHOTO=11;
	public static final int REQUEST_PIC=12;
	public static final int REQUEST_VEDIO=13;
	public static final int REQUEST_RECORD=14;	
	public static final int REQUEST_LOCATION=16;
	//参数
	Map<String, Object> map_params=new HashMap<>();
	EditText desc,edit_firstman;  	
	Double lat,lon; 
	ProgressDialog locationDialog = null;
	ProgressDialog sumDialog = null;
	
	String shipname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_editais); 
		shipname=getIntent().getStringExtra("shipname");
		initView();
		initGridview(); 
	}
	
	
	private void initView()
	{
		edit_firstman=(EditText) findViewById(R.id.firstman); 
		edit_firstman.setText(shipname); 
		desc=(EditText) findViewById(R.id.desc); 
	}
	
	private void initGridview() 
	{
		addFile=BitmapFactory.decodeResource(getResources(), R.drawable.btn_add_normal); 
		EvidenceGridView gridView=(EvidenceGridView) findViewById(R.id.gridView);
		
		adapter=new GridViewAdapter(this,bitmaps);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		FreshData(photofile);
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
		default:
			break;
		}
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    { 		 
		if(requestCode==this.REQUEST_PHOTO&&resultCode==RESULT_OK)//拍照
		{
			if(photofile==null)
				return; 
			FreshData(photofile);
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

	        photofile = new File(path); 
	        FreshData(photofile);
		}		
    }
	
	private void FreshData(File file )
	{
		bitmaps.clear();
		if(file!=null)
		{			
			Bitmap bitmap_oragionBitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
			//bitmap_oragionBitmaps.add(bitmap_oragionBitmap);
			Bitmap bitmap=ThumbnailUtils.extractThumbnail(bitmap_oragionBitmap, 200, 200,  
	                ThumbnailUtils.OPTIONS_RECYCLE_INPUT); 
			bitmaps.add(bitmap);
		}
		else
		{
			bitmaps.add(addFile);
		}
		
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,long id) 
	{
		if(photofile==null)//添加按钮
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
			v3.setVisibility(View.GONE);
			View v4=d_choose.findViewById(R.id.l_record);	
			v4.setVisibility(View.GONE);
			
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
		else//不是空，点击处理
		{			
			AlertDialog.Builder builder = new Builder(this);
			
			builder.setTitle(photofile.getName());
			builder.setPositiveButton("移除", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{ 
					photofile=null;
					FreshData(photofile);
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
					String string=photofile.getName();
					System.out.println(photofile.getAbsolutePath());
					if(string!=null&&!"".equals(string))
					{			
						String s=string.substring(string.indexOf(".")+1);
						//
						if(s.equals("jpg")||s.equals("png")||s.equals("jepg")||s.equals("bmp")||s.equals("ico"))
						{ 
							Bitmap bitmap=BitmapFactory.decodeFile(photofile.getAbsolutePath()); 
							Dialog d_choose=new Dialog(EditAISActivity.this);
							d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);				
							 
							ImageView imvImageView=new ImageView(EditAISActivity.this); 
							imvImageView.setScaleType(ScaleType.FIT_XY);
							imvImageView.setImageBitmap(bitmap);				
							d_choose.setContentView(imvImageView);
							d_choose.show();									
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
		
		final String s6=desc.getText().toString(); 
		 
		
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setMessage("确认提交？");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{ 
				
				if("".equals(s6))
				{
					Toast.makeText(EditAISActivity.this, "请填写AIS号码", Toast.LENGTH_LONG).show();
					return;
				}
				if("".equals(s1))
				{
					Toast.makeText(EditAISActivity.this, "请填写船名", Toast.LENGTH_LONG).show();
					return;
				}
				if(photofile==null){
					Toast.makeText(EditAISActivity.this, "请上传证书文件", Toast.LENGTH_LONG).show();
					return;
				}
				map_params.put("shipname", s1);  
				map_params.put("code", s6);
				map_file.put(photofile.getName(), photofile);
				
				HttpRequest httpRequest=new HttpRequest(EditAISActivity.this);
				
				try 
				{
					httpRequest.post(Constants.BaseURL+"AISCommit", map_params,map_file,"aisfile");
				} catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dialog.dismiss();
				sumDialog=new  ProgressDialog(EditAISActivity.this);
				sumDialog.setCancelable(true);
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

	@Override
	public void onSuccess(String result) 
	{
		if(sumDialog!=null)
			sumDialog.dismiss();
		Toast.makeText(EditAISActivity.this, "提交成功", Toast.LENGTH_LONG).show();
		EditAISActivity.this.setResult(800);
		EditAISActivity.this.finish();	
		
	}

	@Override
	public void onError(int httpcode) 
	{		
	}
}
