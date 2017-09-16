package com.huzhouport.wharfwork;









import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MapView;

import com.example.huzhouport.R;
import com.huzhouport.common.tool.SelectPicPopupWindow;

import com.huzhouport.common.util.GetFileFromPhone;
import com.huzhouport.common.util.HttpUtil;


import com.huzhouport.common.util.Query;
import com.huzhouport.upload.UploadActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.view.Gravity;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class WharfWorkAdd extends Activity {
	private Query query	= new Query();
    MapView mapView;
private String wharfworkid;
private Double lat, lon;
private String loca;
private TextView address;
private TextView goods,carryingtype ,time,portmarttv ,wharfIDText,wharfWorkSurplusText;
private String name,uniti,portmart;
private String declareTime;
private int[] timeList = new int[5];
private ImageButton photoimg;
private ImageView photoView;
private String wharfWorkSurplus;

private String photoname;
private String photopath;
private int photoid=0; //是否有图片


private String actionUrl;
Map<String, Object>			paramsDtae	= new HashMap<String, Object>();

Map<String, File>					files		= new HashMap<String, File>();
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_add);
		mapView = (MapView) findViewById(R.id.wharfwork_add_map);
		
		
        wharfworkid=getIntent().getStringExtra("wharfworkid");
		
		portmarttv=(TextView) findViewById(R.id.wharfwork_add_portMartneirong);
		portmarttv.setOnClickListener(new Selectportmart());
		
		address = (TextView) findViewById(R.id.wharfwork_add_locationNameneirong);
		
		
		
		ListTask1 task1 = new ListTask1(this);  // 获取当地时间
        task1.execute();
		
		
		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_add_back);
		back.setOnClickListener(new Back());
		
		goods=(TextView) findViewById(R.id.wharfwork_add_cargoTypesneirong);
		goods.setOnClickListener(new Selectgoods());
		
		carryingtype=(TextView) findViewById(R.id.wharfwork_add_carrying_type);
		carryingtype.setOnClickListener(new AddUniti());

		time=(TextView) findViewById(R.id.wharfwork_add_workTimeneirong);
		
		
		ListTask task = new ListTask(this); // 异步
		task.execute();
		
	
        
        photoView=(ImageView) findViewById(R.id.wharfwork_add_workPhoto);
        photoimg=(ImageButton) findViewById(R.id.wharfwork_add_bottom);
        photoimg.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(photoid==0){
				Intent getImageByCamera = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				startActivityForResult(getImageByCamera, 2);
				}else{
				
				Toast.makeText(WharfWorkAdd.this, "只能提交一张照片", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        
      //提交按钮
		ImageButton sumbit=(ImageButton) findViewById(R.id.wharfwork_add_submit);
		sumbit.setOnClickListener(new Sumbit());
        
		
		
		
        time.setOnClickListener(new Time());
		
		
	/*	GeoPoint gp = mapTool.getAddrLocation();
		if (gp == null)
			Toast.makeText(WharfWorkAdd.this, "定位失败，无法提交", Toast.LENGTH_SHORT)
					.show();
		else{
			System.out.println("地址==="+gp.getLongitudeE6()+" -- "+gp.getLatitudeE6());
	System.out.println("地址==="+mapTool.getAddrLocationAddress());
		}*/
	}
	
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //可以根据多个请求代码来作相应的操作
           
		  if(20==resultCode)
          {
          	portmart = data.getStringExtra("portmart"); 
          	portmarttv.setText(portmart);
          	
          }
		
		
            if(30==resultCode)
            {
            	name = data.getStringExtra("name"); 
            	goods.setText(name);
            	
            }
            if(40==resultCode)
            {
            	uniti = data.getStringExtra("unit"); 
            	carryingtype.setText(uniti);
            	
            }
            
        	// 拍照
			if (requestCode == 2 && resultCode == RESULT_OK && data != null)
			{
				String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
				photoname=oStrings[0]+".jpg";
				photopath=oStrings[1];
				
				files.put(oStrings[0] + ".jpg", new File(oStrings[1]));
	
			
			    //取照片
				
			/*    Uri selectedImage = data.getData();
			    String[] filePathColumn = {
						MediaStore.Images.Media.DATA,
						MediaStore.Audio.Media.DATA,
						MediaStore.Video.Media.DATA };
	 
			    System.out.println("selectedImage==" +selectedImage);
			    
	            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
			    
	            Drawable drawable = BitmapDrawable.createFromPath(picturePath);
	            
	            Bitmap pic=drawable2Bitmap(drawable);
	            pic= ThumbnailUtils.extractThumbnail(pic, 120, 120);
	            photoView.setImageBitmap(pic);// 向图片控件设置略缩图	
                photoView.setOnClickListener(new Photo());
			    
                photoid=1;*/
                
                
				
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					return;
				}
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				File file = new File("/sdcard/myImage/");
				file.mkdirs();// 创建文件夹，名称为myimage

				// 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。
				String str = null;
				Date date = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 获取当前时间，进一步转化为字符串
				date = new Date();
				str = format.format(date);
				String fileName = "/sdcard/myImage/" + str + ".jpg";
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (data != null) {
						Bitmap cameraBitmap = (Bitmap) data.getExtras().get(
								"data");
						System.out.println("fdf================="
								+ data.getDataString());
						photoView.setImageBitmap(cameraBitmap);

						System.out.println("成功======" + cameraBitmap.getWidth()
								+ cameraBitmap.getHeight());
					}

				}
                
                
                
			
			}
            
            
            
             super.onActivityResult(requestCode, resultCode, data);
    }
	
	
	class Photo implements View.OnClickListener{
		 public void onClick(View v){
				new AlertDialog.Builder(WharfWorkAdd.this).setTitle("移除文件")
				.setMessage("移除" + photoname + "？")
				.setPositiveButton("是", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog,int which)
					{
						photoView.setImageBitmap(null);
						photoid=0;
						
						files= new HashMap<String, File>();
					}
				}).setNegativeButton("否", null).show();
			
		 }
	 }
	
	
	public static Bitmap drawable2Bitmap(Drawable drawable){  
        if(drawable instanceof BitmapDrawable){  
            return ((BitmapDrawable)drawable).getBitmap() ;  
        }else if(drawable instanceof NinePatchDrawable){  
            Bitmap bitmap = Bitmap  
                    .createBitmap(  
                            drawable.getIntrinsicWidth(),  
                            drawable.getIntrinsicHeight(),  
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
                                    : Bitmap.Config.RGB_565);  
            Canvas canvas = new Canvas(bitmap);  
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
                    drawable.getIntrinsicHeight());  
            drawable.draw(canvas);  
            return bitmap;  
        }else{  
            return null ;  
        }  
    }  
	
	
	
	class AddUniti implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(WharfWorkAdd.this,
					WharfWorkAddUniti.class);
			//intent.putExtra("User", user);
			startActivityForResult(intent, 80);
		}
	}
	
	
	
	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
      
			finish();
		}
	}
	public class Selectgoods implements View.OnClickListener
	{
		public void onClick(View v)
		{
      
			Intent intent=new Intent(WharfWorkAdd.this,WharfWorkAddGoods.class);
		
			startActivityForResult(intent, 100);
			
			
		}
	}
	public class Selectportmart implements View.OnClickListener
	{
		public void onClick(View v)
		{
      
			Intent intent=new Intent(WharfWorkAdd.this,WharfWorkAddPortMart.class);
		
			startActivityForResult(intent, 100);
		}
	}
	
	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog	pDialog	= null;
		public ListTask()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context)
		{
			pDialog = new ProgressDialog(WharfWorkAdd.this);
			  pDialog.setTitle("提示");
			  pDialog.setMessage("正在加载中，请稍候。。。");
			  pDialog.setCancelable(true);
			  pDialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.setButton("取消", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.show();	
		}
		@Override
		protected String doInBackground(String... params)
		{
			if(isCancelled()) return null;//取消异步

			String result = query.viewWharf(wharfworkid);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			
			getNeirong(result);

			if (pDialog != null)
				pDialog.dismiss();
			
			super.onPostExecute(result);
		}

	}
	
public void getNeirong(String result){
		
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject	jsonArray=data.getJSONObject("wharf");
			//System.out.println("jsonArray==="+jsonArray);
			
			
		
			
			
			
			String wharfID ="码头编号："+jsonArray.getString("wharfID");
			 wharfIDText=(TextView)findViewById(R.id.wharfwork_add_wharfworkid);
			wharfIDText.setText(wharfID);
			
			String wharfWorkSurplusAll ="剩余吞吐量："+jsonArray.getString("wharfWorkSurplus")+"吨";
			wharfWorkSurplus=jsonArray.getString("wharfWorkSurplus");
			 wharfWorkSurplusText=(TextView)findViewById(R.id.wharfwork_add_wharfWorkSurplus);
			wharfWorkSurplusText.setText(wharfWorkSurplusAll);
			
			
		    
		    
		} catch (Exception e) {
			Toast.makeText(WharfWorkAdd.this, "网络异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


class ListTask1 extends AsyncTask<String ,Integer,String>{
	  public ListTask1(){
		  
	  }
    public ListTask1(Context context){
	  }
	  
	@Override
	protected String  doInBackground(String... params) {
	
		
		return query.GetServiceTime();
	}
	@Override
	protected void onPostExecute(String result) {
		
		GetTime(result);//获得数据

	    //进港时间
	    String[] substring = declareTime.split(" ");
	    String[] substring1 = substring[0].split("-");
	    String[] substring2 = substring[1].split(":");
	    timeList[0] = Integer.parseInt(substring1[0]);
	    timeList[1] = Integer.parseInt(substring1[1]);
	    timeList[2] = Integer.parseInt(substring1[2]);
	    timeList[3] = Integer.parseInt(substring2[0]);
	    timeList[4] = Integer.parseInt(substring2[1]);
		time.setText(declareTime.substring(0, declareTime.lastIndexOf(":")));

		
		super.onPostExecute(result);
	}
	  
}
public void GetTime(String result){
	JSONTokener jsonParser = new JSONTokener(result);
	JSONObject data;
	try {
		data = (JSONObject) jsonParser.nextValue();
		declareTime=data.getString("serverTime");
	

	} catch (Exception e) {
		Toast.makeText(WharfWorkAdd.this, "数据获取失败", Toast.LENGTH_SHORT)
				.show();
		e.printStackTrace();
	}
}

//显示日期
		class Time implements OnClickListener {

			@Override
			public void onClick(View v) {
		/*		WindowManager wm = (WindowManager) WharfWorkAdd.this.getSystemService(Context.WINDOW_SERVICE);   
				int width = wm.getDefaultDisplay().getWidth();//
				dialog = new PopupWindow(  
		                v, width, 350);
				
				// 找到dialog的布局文件
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.leaveandovertime_time_layout, null);
				
				final WheelMain main = new WheelMain(dialog, view);
				main.showDateTimePicker(v);
				
				Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
				Button btn_cancel = (Button) view
						.findViewById(R.id.btn_datetime_cancel);
				// 确定
				btn_sure.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						

						dialog.dismiss();
						System.out.println("time==="+main.getTime());
						time.setText(main.getTime());	
					}
				});
				// 取消
				btn_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});*/
				
				SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
						WharfWorkAdd.this, v, time);
				menuWindow.showAtLocation(
						v,
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
			}

		

		}
		
		
		class Sumbit implements OnClickListener{

			
			public void onClick(View v) {
				//获得要传的值
				
				
				
				
					
					
				
		    EditText shipName=(EditText) findViewById(R.id.wharfwork_add_shipNameneirong);
			EditText startingPortName=(EditText) findViewById(R.id.wharfwork_add_startingPortneirong);	
			EditText arrivalPortName=(EditText) findViewById(R.id.wharfwork_add_arrivalPortneirong);
			EditText carrying=(EditText) findViewById(R.id.wharfwork_add_carryingneirong);
			TextView locationName=(TextView) findViewById(R.id.wharfwork_add_locationNameneirong);
			
			paramsDtae.put("wharfbean.wharfID",wharfworkid);
			paramsDtae.put("wharfbean.wharfWorkSurplus", wharfWorkSurplus);
			paramsDtae.put("wharfbean.shipName", shipName.getText().toString());
			paramsDtae.put("wharfbean.startingPortName", startingPortName.getText().toString());
			paramsDtae.put("wharfbean.arrivalPortName", arrivalPortName.getText().toString());
			
			if(portmarttv.getText().toString().equals("进港")){
				paramsDtae.put("wharfbean.portMart", 1);
			}else{
				paramsDtae.put("wharfbean.portMart", 2);
			}
			
			paramsDtae.put("wharfbean.cargoTypes", goods.getText().toString());
			paramsDtae.put("wharfbean.carrying", carrying.getText().toString());
			paramsDtae.put("wharfbean.uniti", carryingtype.getText().toString());
			paramsDtae.put("wharfbean.workTime", time.getText().toString());
			
			
			
			
			/*GeoPoint gp = mapTool.getAddrLocation();
			if (gp == null){
				Toast.makeText(WharfWorkAdd.this, "定位失败，无法提交", Toast.LENGTH_SHORT)
						.show();
				
				paramsDtae.put("wharfbean.longitude", "120737391");
				paramsDtae.put("wharfbean.latitude","30739291" );
				paramsDtae.put("wharfbean.locationName", "浙江省嘉兴市南湖区城南路1369");
				
			
				
				

			}else{
			System.out.println("地址==="+gp.getLongitudeE6()+" -- "+gp.getLatitudeE6());
			System.out.println("地址==="+mapTool.getAddrLocationAddress());
			String longitude=gp.getLongitudeE6()+"";
			String latitude=gp.getLatitudeE6()+"";
			
			paramsDtae.put("wharfbean.longitude", longitude);
			paramsDtae.put("wharfbean.latitude", latitude);
			paramsDtae.put("wharfbean.locationName", locationName.getText().toString());

		}*/
			
			actionUrl = HttpUtil.BASE_URL + "addWharfWork";
			
			
			
			if(shipName.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "请填写船名号", Toast.LENGTH_SHORT)
				.show();
			}else if(startingPortName.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "请填写起运港", Toast.LENGTH_SHORT)
				.show();
			}else if(arrivalPortName.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "请填写目的港", Toast.LENGTH_SHORT)
				.show();
			}else if(goods.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "请选择货物", Toast.LENGTH_SHORT)
				.show();
			}else if(carrying.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "请填写货物数量", Toast.LENGTH_SHORT)
				.show();
			}else if(portmarttv.getText().toString().endsWith("")){
				Toast.makeText(WharfWorkAdd.this, "请选择进出港标记", Toast.LENGTH_SHORT)
				.show();
			}else{

			ListTask2 task2 = new ListTask2(WharfWorkAdd.this);  // 异步
		    task2.execute();
			}
			}
}
		
		
		class ListTask2 extends AsyncTask<String ,Integer,String>{
			  ProgressDialog	pDialog	= null;
			  public ListTask2(){
				  
			  }
		      @SuppressWarnings("deprecation")
			public ListTask2(Context context){
		    	  pDialog = new ProgressDialog(WharfWorkAdd.this);
				  pDialog.setTitle("提示");
				  pDialog.setMessage("正在提交中，请稍候。。。");
				  pDialog.setCancelable(true);
				  pDialog.setOnCancelListener(new OnCancelListener()
					{
						
						@Override
						public void onCancel(DialogInterface dialog)
						{
							if (pDialog != null)
								pDialog.dismiss();
							if (ListTask2.this != null && ListTask2.this.getStatus() == AsyncTask.Status.RUNNING)
								ListTask2.this.cancel(true);
							
						}
					});
					pDialog.setButton("取消", new DialogInterface.OnClickListener()
					{
						
						@Override
						public void onClick(DialogInterface dialog,int which)
						{
							if (pDialog != null)
								pDialog.dismiss();
							if (ListTask2.this != null && ListTask2.this.getStatus() == AsyncTask.Status.RUNNING)
								ListTask2.this.cancel(true);
							
						}
					});
					pDialog.show();	
			  }
			  
			@Override
			protected String doInBackground(String... params) {
				if(isCancelled()) return null;//取消异步
				
			//	UploadActivity.tool.addFile("违章提交",actionUrl, paramsDtae,files, "illegalEvidence.ef");
				UploadActivity.tool.addFile("码头作业报告",actionUrl, paramsDtae,files, "wharfbean.ef");
				//UploadActivity.tool.addFile("危险品码头作业", actionUrl, "wharfbean.shipName=浙安吉");
				System.out.println("paramsDtae==="+paramsDtae);

				/*	    Intent intent=new Intent(WharfWorkAdd.this, WharfWorkList.class);
						startActivity(intent);
						setResult(20);
						finish();*/

			
				return null ;
			}
			@Override
			protected void onPostExecute(String result) {
				if (pDialog != null)
					pDialog.dismiss();

				
				super.onPostExecute(result);
			}
			  
		  }
		
		
}
