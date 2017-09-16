package com.huzhouport.abnormal;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GetFileFromPhone;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AbNormalProcess extends Activity implements OnItemClickListener
{
	public static int FINISH=200;
	public static int BACK=150;
	
	AbnormalInfo abnormalinfo;
	
	boolean process;
	ImageGridView gridView;	
	EditText edittext;
	List<Bitmap> drawableList=new ArrayList<Bitmap>();
	ArrayList<String> file_pathList=new ArrayList<String>();
	//ArrayList<String> file_namelist=new ArrayList<String>();
	
	//数据
	Map<String, File> file_map=new HashMap<String, File>();
	Map<String, Object> data_map=new HashMap<String, Object>();
	String options[][]={{"已纠正","已立案处罚","已教育警告","加入黑名单","实际正常"}
						,{"海事艇","小渔船","工程船","数据误报"}};
	String  types[]={"abnormalinfo.process","abnormalinfo.pass"};
	
	String option=null;
	String type=null;
	File photofile;	
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.abnormal_process_activity);
		
		abnormalinfo= (AbnormalInfo) getIntent().getSerializableExtra("abnormalInfo");
		
		setViewList();
		
		TextView process_text=(TextView) this.findViewById(R.id.process_text);
		
		FragmentManager fragmentManager = getFragmentManager();
		process= getIntent().getBooleanExtra("process", true);
		if(process)
		{
			FragmentProcess fragmentP=new FragmentProcess();
			
			fragmentManager.beginTransaction().replace(R.id.frame_option,fragmentP ) .commit();
			
			process_text.setText("处理结果");
		}
		else 
		{
			FragmentPass fragmentP=new FragmentPass();
			
			fragmentManager.beginTransaction().replace(R.id.frame_option,fragmentP ) .commit();
			
			process_text.setText("忽略");
		}
		
		edittext=(EditText) this.findViewById(R.id.edit_remark);
		
	}
	
	public void GoBack(View v)
	{
		this.setResult(this.BACK);
		this.finish();
	}
	
	public void GetOptions(int type,int num)
	{
		this.type=types[type];
		option=options[type][num];
	}
	
	public void Submit(View v)
	{
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setMessage("确认提交？");
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Submit();
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
		
		builder.create().show();
		
		WindowManager mwidow = getWindowManager();  
		Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
		//android.view.WindowManager.LayoutParams p = adAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值  
		//p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
		//p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
		//adAlertDialog.getWindow().setAttributes(p);
	}
	
	public void Submit()
	{
		if(type==null||option==null)
		{
			Toast.makeText(this, "请选择处理方式！", Toast.LENGTH_LONG).show();
			return;
		}
		
		//原数据
		//data_map.put("abnormalinfo.potname", abnormalinfo.getPotname());
		data_map.put("abnormalinfo.aid", abnormalinfo.getAid());
		//data_map.put("abnormalinfo.gatename", abnormalinfo.getGatename());
		//data_map.put("abnormalinfo.abdate", abnormalinfo.getAbdate());
		//data_map.put("abnormalinfo.route", abnormalinfo.getRoute());
		//data_map.put("abnormalinfo.AIS", abnormalinfo.getAIS());
		//data_map.put("abnormalinfo.blacklist", abnormalinfo.getBlacklist());
		//data_map.put("abnormalinfo.arrearage", abnormalinfo.getArrearage());
		//data_map.put("abnormalinfo.report", abnormalinfo.getReport());
		//data_map.put("abnormalinfo.illegal", abnormalinfo.getIllegal());
		//data_map.put("abnormalinfo.cert", abnormalinfo.getCert());
		//补充数据
		data_map.put(type, option);
		data_map.put("abnormalinfo.remark", edittext.getText());
		data_map.put("abnormalinfo.status", "1");
		//图片文件
		Set keys = file_map.keySet( );
		Iterator iterator = keys.iterator( );
		int i=0;
		while(iterator.hasNext( ))
		{
			String keyString=(String)iterator.next();
			data_map.put("identity["+i+"]", keyString);
			i++;
		}	
		
		new SubmitTask(this).execute();
		
	}
	
	class SubmitTask extends AsyncTask<String, Integer, String> 
	{
		ProgressDialog pDialog = null;
		
		public SubmitTask(Context context) 
		{			
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();			
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			HttpFileUpTool hft = new HttpFileUpTool();
			String result = null;
			try
			{
				//HttpUtil.BASE_URL
				result = hft.post(HttpUtil.BASE_URL +"SubmitAbNormalProcess", data_map,file_map,"image");
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) 
		{			
			if (pDialog != null)
				pDialog.dismiss();
			
			if (result == null) 
			{
				Toast.makeText(AbNormalProcess.this, "网络异常",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				
				try 
				{
					data = (JSONObject) jsonParser.nextValue();
				}
				catch (Exception e) 
				{
					//Toast.makeText(activity, "暂无数据",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
				Toast.makeText(AbNormalProcess.this.getApplicationContext(), "处理完成", Toast.LENGTH_LONG).show();
				
				AbNormalProcess.this.setResult(AbNormalProcess.FINISH);
				AbNormalProcess.this.finish();
			}
			
			super.onPostExecute(result);
		}		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		File file=null;
				
		if (requestCode == 0x111 && resultCode == RESULT_OK)
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
		}
		
		// 拍照
		else if (requestCode == 2 && resultCode == RESULT_OK)
		{			
			//String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
			
			//file=new File(oStrings[1]);		
			if(photofile==null)
				return;
			file=photofile;
		}
		else  if(requestCode==100&& resultCode == RESULT_OK)
		{
			file_pathList.clear();
			file_pathList=data.getStringArrayListExtra("file_pathList");
			drawableList.clear();
			file_map.clear();
			
			for(int i=0;i<file_pathList.size();i++)
			{
				Bitmap bitmap=getImageThumbnail(file_pathList.get(i),210,210);
				
				drawableList.add(bitmap);
				//adapter.notifyDataSetChanged();
				
				
				
				File f=new File(file_pathList.get(i));
				file_map.put(f.getName(), f);
				
			}
			
			drawableList.add(BitmapFactory.decodeResource(getResources(), R.drawable.btn_add_normal));
			gridView.invalidateViews();
		}
		
		if(file!=null)
		{
			Bitmap bitmap=getImageThumbnail(file.getAbsolutePath(),210,210);//(new BitmapDrawable());
			
			
			refreshList(bitmap);
			//adapter.notifyDataSetChanged();
			gridView.invalidateViews();
			
			
			file_map.put(file.getName(), file);
		
			
			file_pathList.add(file.getAbsolutePath());
		}		
	}	
	
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) 
	{  
		Bitmap bitmap = null;  
        BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        // 获取这个图片的宽和高，注意此处的bitmap为null  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        options.inJustDecodeBounds = false; // 设为 false  
        // 计算缩放比  
        int h = options.outHeight;  
        int w = options.outWidth;  
        int beWidth = w / width;  
        int beHeight = h / height;  
        int be = 1;  
        if (beWidth < beHeight) {  
            be = beWidth;  
        } else {  
            be = beHeight;  
        }  
        if (be <= 0) {  
            be = 1;  
        }  
        options.inSampleSize = be;  
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false  
        bitmap = BitmapFactory.decodeFile(imagePath, options);  
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象  
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,  
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);  
        return bitmap;  
    }
	
	private void getPhoto(View view)
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
		startActivityForResult(getImageByCamera, 2);
	}
	
	private void getPic(View v)
	{
		//Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
		//intent.setType("*/*"); 
		 Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 //new Intent(Intent.ACTION_GET_CONTENT);  
         //intent.addCategory(Intent.CATEGORY_OPENABLE);  
         //intent.setType("image/*");
		//intent.addCategory(Intent.CATEGORY_OPENABLE);
		 
		try 
		{ 
		  startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 0x111);
		} catch (android.content.ActivityNotFoundException ex)
		{
		    	 
		}
	}
	
	private void setViewList()
	{
		gridView=(ImageGridView) this.findViewById(R.id.ab_gridView);
		
		refreshList(BitmapFactory.decodeResource(getResources(), R.drawable.btn_add_normal));
		GridViewAdapter gAdapter=new GridViewAdapter(this,drawableList);
		gridView.setAdapter(gAdapter);
		gridView.setOnItemClickListener(this);
	}
	
	private void  refreshList(Bitmap bitmap)
	{
		if(drawableList.size()==0)
		{
			drawableList.add(bitmap);
		}
		else 
		{
			drawableList.remove(drawableList.size()-1);
			drawableList.add(bitmap);
			
			drawableList.add(BitmapFactory.decodeResource(getResources(), R.drawable.btn_add_normal));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		//添加图片事件
		if(position==drawableList.size()-1)
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
					getPhoto(v);
					d_choose.dismiss();
				}
			});
			View v2=d_choose.findViewById(R.id.l_pic);	
			v2.setOnClickListener(new OnClickListener() 
			{
				
				@Override
				public void onClick(View v) 
				{
					getPic(v);
					d_choose.dismiss();
				}
			});
			
			d_choose.show();
			
			WindowManager mwidow = getWindowManager();  
			Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
			android.view.WindowManager.LayoutParams p = d_choose.getWindow().getAttributes();  //获取对话框当前的参数值  
			//p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
			p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
			d_choose.getWindow().setAttributes(p);     //设置生效 
		}
		else 
		{
			Intent intent;
			intent = new Intent(AbNormalProcess.this, AbNormalImage.class);
			intent.putStringArrayListExtra("images", file_pathList);
			intent.putExtra("index", position);
			
			this.startActivityForResult(intent, 100);
		}
		
	}
	
	@Override
	public void onDestroy()
	{
	    super.onDestroy();

	    drawableList.clear();
	}

}
