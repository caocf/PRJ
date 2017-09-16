package com.huzhouport.integratedquery;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.slidemenu.UserMod;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText; 
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class ShipSearchNinecodeAdd extends Activity 
{
	private String shipname;
	private ImageButton submit,back,img_photograph;
	private EditText edit,num;
	private Query query = new Query();
	Map<String, File> files = new HashMap<String, File>();
	private int[] at_image;
	private String[] at_name;
	private Gallery gr;
	Map<String, Object> paramsDtae = new HashMap<String, Object>();
	File photofile;
	Handler handler;
	
	String usernameString;	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_ninecode_add);
		Intent intent = getIntent();
		shipname = intent.getStringExtra("shipname");
		TextView tv_title = (TextView) findViewById(R.id.basesourch_title);
		tv_title.setText("九位码绑定");
		back = (ImageButton) findViewById(R.id.basesourch_back);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		num=(EditText) findViewById(R.id.search_ninecode_add_num);
		num.setText(intent.getStringExtra("shipname"));
		edit = (EditText) findViewById(R.id.search_ninecode_add_edit);
		submit= (ImageButton) findViewById(R.id.search_ninecode_add_submit);
		submit.setOnClickListener(new Submit());
		img_photograph= (ImageButton) findViewById(R.id.search_ninecode_add_photo);
		gr=(Gallery) findViewById(R.id.search_ninecode_add_gallery);
		img_photograph.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{				
				final Dialog d_choose=new Dialog(ShipSearchNinecodeAdd.this);
				d_choose.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d_choose.setContentView(R.layout.ab_dialog);
				
				View v1=d_choose.findViewById(R.id.l_photo);	
				v1.setOnClickListener(new OnClickListener() 
				{					
					@Override
					public void onClick(View v) 
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
				
				/*View v5=d_choose.findViewById(R.id.cancel);	
				v5.setOnClickListener(new OnClickListener() 
				{
					
					@Override
					public void onClick(View v) 
					{
						d_choose.dismiss();
					}
				});*/
				
				d_choose.show();
				d_choose.setCanceledOnTouchOutside(true);
				WindowManager mwidow = getWindowManager();  
				Display d = mwidow.getDefaultDisplay();  //为获取屏幕宽、高  
				android.view.WindowManager.LayoutParams p = d_choose.getWindow().getAttributes();  //获取对话框当前的参数值  
				//p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
				p.width = (int) (d.getWidth() * 0.8);    //宽度设置为屏幕的0.5 
				d_choose.getWindow().setAttributes(p); 
				
				
				
				
			}
		});
		
		handler=new Handler();
		
		usernameString=getSharedPreferences("SHARED_SAVE_USER_FILE_NAME", 0).getString("SHARED_SAVE_USER_USERNAME", "");
	}
	
	private void getPic()
	{
		 Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);		
		 
		try 
		{ 
		  startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), 3);
		} catch (android.content.ActivityNotFoundException ex)
		{
		    	 
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 拍照
		if (requestCode == 2 && resultCode == RESULT_OK)
		{
			//String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
			if(photofile==null)
				return;
			File file=photofile;
			files.put(photofile.getName(), file);
			creatGV();
		}	
		if (requestCode == 3 && resultCode == RESULT_OK)
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
	        files.put(photofile.getName(), photofile);
	        creatGV();
		}
					
	}
	class Submit implements OnClickListener{
		public void onClick(View v) {
			if("".equals(num.getText().toString().trim()))
			{
				Toast.makeText(ShipSearchNinecodeAdd.this, "请输入船名号", Toast.LENGTH_SHORT).show();
			}else
			if(edit.getText().toString().trim().equals("")){
				Toast.makeText(ShipSearchNinecodeAdd.this, "请输入九位码", Toast.LENGTH_SHORT).show();
			}else if(edit.getText().toString().trim().length()!=9)
			{
				Toast.makeText(ShipSearchNinecodeAdd.this, "九位码必须为9位数字", Toast.LENGTH_SHORT).show();
			}else if(photofile==null)
			{
				Toast.makeText(ShipSearchNinecodeAdd.this, "请上传九位码证书！", Toast.LENGTH_SHORT).show();
			}else
			{
				new UploadDate(ShipSearchNinecodeAdd.this).execute();				
			}
		}
	}
	/*
	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					ListTask.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			return query.ShipSearchNicecodeAdd(shipname,edit.getText().toString().trim());
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
             JSONTokener jsonParser_User = new JSONTokener(result);
     		try
     		{
     			JSONObject data = (JSONObject) jsonParser_User.nextValue();
     			int resultcode = data.getInt("resultCode");
     			if(resultcode==0){
     				Toast.makeText(ShipSearchNinecodeAdd.this, "绑定成功",
        					Toast.LENGTH_SHORT).show();
     				setResult(100);
     				finish();
     			}else if(resultcode==-2){
     				ListTask1 task = new ListTask1(ShipSearchNinecodeAdd.this); // 异步
					task.execute();
					/*JSONObject editResult = (JSONObject) data.getJSONObject("editResult");
     				new AlertDialog.Builder(ShipSearchNinecodeAdd.this).setTitle("提示").setMessage("此船舶已绑定了九位码："+editResult.getString("num")+"，是否需要替换更新？")
    				.setPositiveButton("取消", new DialogInterface.OnClickListener()
    				{
    					public void onClick(DialogInterface dialog, int which)
    					{
    						dialog.dismiss();
    						
    					}
    				}).setNegativeButton("确定", new DialogInterface.OnClickListener()
    				{
    					public void onClick(DialogInterface dialog, int which)
    					{
    						dialog.dismiss();
    						ListTask1 task = new ListTask1(ShipSearchNinecodeAdd.this); // 异步
    						task.execute();
    					}
    				}).create().show();
     			}else{
     				Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
        					Toast.LENGTH_SHORT).show();
     			}
     		}catch(Exception e){
     			e.printStackTrace();
     			Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
    					Toast.LENGTH_SHORT).show();
     		}
			super.onPostExecute(result);
		}
	}
	class ListTask1 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask1(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					ListTask1.this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			return query.ShipSearchNicecodeUpdate(shipname,edit.getText().toString().trim());
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
             JSONTokener jsonParser_User = new JSONTokener(result);
     		try
     		{
     			JSONObject data = (JSONObject) jsonParser_User.nextValue();
     			int resultcode = data.getInt("resultCode");
     			if(resultcode==0){
     				Toast.makeText(ShipSearchNinecodeAdd.this, "替换更新成功",
        					Toast.LENGTH_SHORT).show();
     				setResult(100);
     				finish();
     			}else{
     				Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
        					Toast.LENGTH_SHORT).show();
     			}
     		}catch(Exception e){
     			e.printStackTrace();
     			Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
    					Toast.LENGTH_SHORT).show();
     		}
			super.onPostExecute(result);
		}
	}*/
	public void creatGV()
	{
		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		at_image = new int[files.size()];
		at_name = new String[files.size()];
		Iterator keyIteratorOfMap = files.keySet().iterator();
		for (int i = 0; i < files.size(); i++)
		{
			Object key = keyIteratorOfMap.next();
			at_name[i] = key.toString();
			String str = key.toString();
			int dot = str.lastIndexOf('.');
			String substring = str.substring(dot + 1);
			if (substring.equals("doc"))
				at_image[i] = R.drawable.doc;
			else if (substring.equals("xls"))
				at_image[i] = R.drawable.xls;
			else if (substring.equals("ppt"))
				at_image[i] = R.drawable.ppt;
			else if (substring.equals("amr"))
				at_image[i] = R.drawable.audio;
			else if (substring.equals("mp4"))
				at_image[i] = R.drawable.video;
			else if (substring.equals("3gp"))
				at_image[i] = R.drawable.video;
			else if (substring.equals("pdf"))
				at_image[i] = R.drawable.pdf;
			else
				at_image[i] = R.drawable.other_file;

		}
		for (int i = 0; i < files.size(); i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", at_image[i]);
			map.put("itemText", at_name[i]);

			lst.add(map);
		}
		gr.setAdapter(new galleryAdapter(this));
		gr.setOnItemClickListener(new OnItemClickListener()
		{

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id)
			{
				TextView tv = (TextView) v.findViewById(R.id.text);
				dialog_down((String) tv.getText());

			}
		});
		int nm = 0;
		if (files.size() > 1)
			nm = files.size() / 2;

		gr.setSelection(nm);
		gr.setSpacing(20);
		// gr.setUnselectedAlpha(10.0f);

	}
	public class galleryAdapter extends BaseAdapter
	{

		private int[] img = at_image;
		private String[] str = at_name;
		private Context mContext;

		public galleryAdapter(Context c)
		{
			mContext = c;

		}

		public int getCount()
		{
			return img.length;
		}

		public Object getItem(int position)
		{
			return position;
		}

		public long getItemId(int position)
		{
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.pic_text, null);
				holder.pic = (ImageView) convertView.findViewById(R.id.image);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.pic.setImageResource(img[position]);
			holder.text.setText(str[position]);
			return convertView;
		}

		class ViewHolder
		{
			private ImageView pic;
			private TextView text;
		}

	}
	public void dialog_down(String kt)
	{
		final String kt2 = kt;
		new AlertDialog.Builder(this).setTitle("移除文件")
				.setMessage("移除" + kt + "？")
				.setPositiveButton("是", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int which)
					{
						files.remove(kt2);
						creatGV();
					}
				}).setNegativeButton("否", null).show();

	}
	class UploadDate extends AsyncTask<Integer, Void, String>
	{
		ProgressDialog pDialog = null;

		public UploadDate(Context context)
		{
			// pDialog = ProgressDialog
			// .show(context, "", "", true);
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(Integer... params)
		{
			paramsDtae.put("ais.name", num.getText().toString().trim());
			paramsDtae.put("ais.man", UserMod.name);
			paramsDtae.put("ais.num", edit.getText().toString().trim());
			
			int flag=0;
			if(shipname.equals(num.getText().toString().trim()))//船名改九位码
			{
				flag=1;
			}
			paramsDtae.put("ais.modifyflag", flag);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "saveAisByShipname";
			//String actionUrl = "http://192.168.1.126:6080/HuZhouPort/"+ "saveAisByShipname";
			String result = null;
			if (isCancelled())
				return null;// 取消异步
			try
			{
				result = hfu.post(actionUrl, paramsDtae, files,"ais.file");
				/*handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(ShipSearchNinecodeAdd.this, "绑定成功",
	        					Toast.LENGTH_SHORT).show();
						ShipSearchNinecodeAdd.this.setResult(100);
	     				ShipSearchNinecodeAdd.this.finish();
						
					}
				}, 1500);*/
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			JSONTokener jsonParser = new JSONTokener(result);
			try
			{
				JSONObject data = (JSONObject) jsonParser.nextValue();
     			int resultcode = data.getInt("resultCode");
     			if(resultcode==0){
     				Toast.makeText(ShipSearchNinecodeAdd.this, "绑定成功",
        					Toast.LENGTH_SHORT).show();
     				setResult(100);
     				finish();
     			}else if(resultcode==-2){
     				new UploadDate1(ShipSearchNinecodeAdd.this).execute();
     				/*JSONObject editResult = (JSONObject) data.getJSONObject("editResult");
     				new AlertDialog.Builder(ShipSearchNinecodeAdd.this).setTitle("提示").setMessage("此船舶已绑定了九位码："+editResult.getString("num")+"，是否需要替换更新？")
    				.setPositiveButton("取消", new DialogInterface.OnClickListener()
    				{
    					public void onClick(DialogInterface dialog, int which)
    					{
    						dialog.dismiss();
    						
    					}
    				}).setNegativeButton("确定", new DialogInterface.OnClickListener()
    				{
    					public void onClick(DialogInterface dialog, int which)
    					{
    						dialog.dismiss();
    						new UploadDate1(ShipSearchNinecodeAdd.this).execute();
    					}
    				}).create().show();*/
     			}else{
     				Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
        					Toast.LENGTH_SHORT).show();
     			}
     		}catch(Exception e){
     			e.printStackTrace();
     			Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
    					Toast.LENGTH_SHORT).show();
     		}

			if (pDialog != null)
				pDialog.dismiss();
			super.onPostExecute(result);
		}

	}
	
	class UploadDate1 extends AsyncTask<Integer, Void, String>
	{
		ProgressDialog pDialog = null;

		public UploadDate1(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(Integer... params)
		{

			paramsDtae.put("ais.name", shipname);
			paramsDtae.put("ais.num", edit.getText().toString().trim());
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "updateAis";
			String result = null;
			if (isCancelled())
				return null;// 取消异步
			try
			{
				result = hfu.post(actionUrl, paramsDtae, files,
						"ais.file");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{

			  JSONTokener jsonParser_User = new JSONTokener(result);
	     		try
	     		{
	     			JSONObject data = (JSONObject) jsonParser_User.nextValue();
	     			int resultcode = data.getInt("resultCode");
	     			if(resultcode==0){
	     				Toast.makeText(ShipSearchNinecodeAdd.this, "替换更新成功",
	        					Toast.LENGTH_SHORT).show();
	     				setResult(100);
	     				finish();
	     			}else{
	     				Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
	        					Toast.LENGTH_SHORT).show();
	     			}
	     		}catch(Exception e){
	     			e.printStackTrace();
	     			Toast.makeText(ShipSearchNinecodeAdd.this, "网络异常",
	    					Toast.LENGTH_SHORT).show();
	     		}
			

			if (pDialog != null)
				pDialog.dismiss();
			super.onPostExecute(result);
		}

	}
}
