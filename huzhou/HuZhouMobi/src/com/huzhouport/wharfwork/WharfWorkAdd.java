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
private int photoid=0; //�Ƿ���ͼƬ


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
		
		
		
		ListTask1 task1 = new ListTask1(this);  // ��ȡ����ʱ��
        task1.execute();
		
		
		ImageButton back = (ImageButton) findViewById(R.id.wharfwork_add_back);
		back.setOnClickListener(new Back());
		
		goods=(TextView) findViewById(R.id.wharfwork_add_cargoTypesneirong);
		goods.setOnClickListener(new Selectgoods());
		
		carryingtype=(TextView) findViewById(R.id.wharfwork_add_carrying_type);
		carryingtype.setOnClickListener(new AddUniti());

		time=(TextView) findViewById(R.id.wharfwork_add_workTimeneirong);
		
		
		ListTask task = new ListTask(this); // �첽
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
				
				Toast.makeText(WharfWorkAdd.this, "ֻ���ύһ����Ƭ", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        
      //�ύ��ť
		ImageButton sumbit=(ImageButton) findViewById(R.id.wharfwork_add_submit);
		sumbit.setOnClickListener(new Sumbit());
        
		
		
		
        time.setOnClickListener(new Time());
		
		
	/*	GeoPoint gp = mapTool.getAddrLocation();
		if (gp == null)
			Toast.makeText(WharfWorkAdd.this, "��λʧ�ܣ��޷��ύ", Toast.LENGTH_SHORT)
					.show();
		else{
			System.out.println("��ַ==="+gp.getLongitudeE6()+" -- "+gp.getLatitudeE6());
	System.out.println("��ַ==="+mapTool.getAddrLocationAddress());
		}*/
	}
	
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //���Ը��ݶ���������������Ӧ�Ĳ���
           
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
            
        	// ����
			if (requestCode == 2 && resultCode == RESULT_OK && data != null)
			{
				String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
				photoname=oStrings[0]+".jpg";
				photopath=oStrings[1];
				
				files.put(oStrings[0] + ".jpg", new File(oStrings[1]));
	
			
			    //ȡ��Ƭ
				
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
	            photoView.setImageBitmap(pic);// ��ͼƬ�ؼ���������ͼ	
                photoView.setOnClickListener(new Photo());
			    
                photoid=1;*/
                
                
				
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
					return;
				}
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// ��ȡ������ص����ݣ���ת��ΪBitmapͼƬ��ʽ
				FileOutputStream b = null;
				File file = new File("/sdcard/myImage/");
				file.mkdirs();// �����ļ��У�����Ϊmyimage

				// ��Ƭ��������Ŀ���ļ����£��Ե�ǰʱ�����ִ�Ϊ���ƣ�����ȷ��ÿ����Ƭ���Ʋ���ͬ��
				String str = null;
				Date date = null;
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// ��ȡ��ǰʱ�䣬��һ��ת��Ϊ�ַ���
				date = new Date();
				str = format.format(date);
				String fileName = "/sdcard/myImage/" + str + ".jpg";
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�
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

						System.out.println("�ɹ�======" + cameraBitmap.getWidth()
								+ cameraBitmap.getHeight());
					}

				}
                
                
                
			
			}
            
            
            
             super.onActivityResult(requestCode, resultCode, data);
    }
	
	
	class Photo implements View.OnClickListener{
		 public void onClick(View v){
				new AlertDialog.Builder(WharfWorkAdd.this).setTitle("�Ƴ��ļ�")
				.setMessage("�Ƴ�" + photoname + "��")
				.setPositiveButton("��", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog,int which)
					{
						photoView.setImageBitmap(null);
						photoid=0;
						
						files= new HashMap<String, File>();
					}
				}).setNegativeButton("��", null).show();
			
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
			  pDialog.setTitle("��ʾ");
			  pDialog.setMessage("���ڼ����У����Ժ򡣡���");
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
				pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
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
			if(isCancelled()) return null;//ȡ���첽

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
			
			
		
			
			
			
			String wharfID ="��ͷ��ţ�"+jsonArray.getString("wharfID");
			 wharfIDText=(TextView)findViewById(R.id.wharfwork_add_wharfworkid);
			wharfIDText.setText(wharfID);
			
			String wharfWorkSurplusAll ="ʣ����������"+jsonArray.getString("wharfWorkSurplus")+"��";
			wharfWorkSurplus=jsonArray.getString("wharfWorkSurplus");
			 wharfWorkSurplusText=(TextView)findViewById(R.id.wharfwork_add_wharfWorkSurplus);
			wharfWorkSurplusText.setText(wharfWorkSurplusAll);
			
			
		    
		    
		} catch (Exception e) {
			Toast.makeText(WharfWorkAdd.this, "�����쳣", Toast.LENGTH_SHORT).show();
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
		
		GetTime(result);//�������

	    //����ʱ��
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
		Toast.makeText(WharfWorkAdd.this, "���ݻ�ȡʧ��", Toast.LENGTH_SHORT)
				.show();
		e.printStackTrace();
	}
}

//��ʾ����
		class Time implements OnClickListener {

			@Override
			public void onClick(View v) {
		/*		WindowManager wm = (WindowManager) WharfWorkAdd.this.getSystemService(Context.WINDOW_SERVICE);   
				int width = wm.getDefaultDisplay().getWidth();//
				dialog = new PopupWindow(  
		                v, width, 350);
				
				// �ҵ�dialog�Ĳ����ļ�
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.leaveandovertime_time_layout, null);
				
				final WheelMain main = new WheelMain(dialog, view);
				main.showDateTimePicker(v);
				
				Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
				Button btn_cancel = (Button) view
						.findViewById(R.id.btn_datetime_cancel);
				// ȷ��
				btn_sure.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						

						dialog.dismiss();
						System.out.println("time==="+main.getTime());
						time.setText(main.getTime());	
					}
				});
				// ȡ��
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
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
			}

		

		}
		
		
		class Sumbit implements OnClickListener{

			
			public void onClick(View v) {
				//���Ҫ����ֵ
				
				
				
				
					
					
				
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
			
			if(portmarttv.getText().toString().equals("����")){
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
				Toast.makeText(WharfWorkAdd.this, "��λʧ�ܣ��޷��ύ", Toast.LENGTH_SHORT)
						.show();
				
				paramsDtae.put("wharfbean.longitude", "120737391");
				paramsDtae.put("wharfbean.latitude","30739291" );
				paramsDtae.put("wharfbean.locationName", "�㽭ʡ�������Ϻ�������·1369");
				
			
				
				

			}else{
			System.out.println("��ַ==="+gp.getLongitudeE6()+" -- "+gp.getLatitudeE6());
			System.out.println("��ַ==="+mapTool.getAddrLocationAddress());
			String longitude=gp.getLongitudeE6()+"";
			String latitude=gp.getLatitudeE6()+"";
			
			paramsDtae.put("wharfbean.longitude", longitude);
			paramsDtae.put("wharfbean.latitude", latitude);
			paramsDtae.put("wharfbean.locationName", locationName.getText().toString());

		}*/
			
			actionUrl = HttpUtil.BASE_URL + "addWharfWork";
			
			
			
			if(shipName.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "����д������", Toast.LENGTH_SHORT)
				.show();
			}else if(startingPortName.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "����д���˸�", Toast.LENGTH_SHORT)
				.show();
			}else if(arrivalPortName.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "����дĿ�ĸ�", Toast.LENGTH_SHORT)
				.show();
			}else if(goods.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "��ѡ�����", Toast.LENGTH_SHORT)
				.show();
			}else if(carrying.getText().toString().equals("")){
				Toast.makeText(WharfWorkAdd.this, "����д��������", Toast.LENGTH_SHORT)
				.show();
			}else if(portmarttv.getText().toString().endsWith("")){
				Toast.makeText(WharfWorkAdd.this, "��ѡ������۱��", Toast.LENGTH_SHORT)
				.show();
			}else{

			ListTask2 task2 = new ListTask2(WharfWorkAdd.this);  // �첽
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
				  pDialog.setTitle("��ʾ");
				  pDialog.setMessage("�����ύ�У����Ժ򡣡���");
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
					pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
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
				if(isCancelled()) return null;//ȡ���첽
				
			//	UploadActivity.tool.addFile("Υ���ύ",actionUrl, paramsDtae,files, "illegalEvidence.ef");
				UploadActivity.tool.addFile("��ͷ��ҵ����",actionUrl, paramsDtae,files, "wharfbean.ef");
				//UploadActivity.tool.addFile("Σ��Ʒ��ͷ��ҵ", actionUrl, "wharfbean.shipName=�㰲��");
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
