package com.huzhouport.permit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.baidu.mapapi.map.MapView;
import com.elc.GetSDTreeActivity;
import com.example.huzhouport.R;
import com.huzhouport.common.RecoderVideo;
import com.huzhouport.common.util.GetFileFromPhone;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.illegal.IllegalAdd;
import com.huzhouport.main.User;
import com.huzhouport.upload.UploadActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("deprecation")
public class PermitAdd extends Activity {
	private TextView tv_user;
	private EditText et_object, et_decript;
	private Gallery gr;
	private ImageButton bt_finish, img_photograph,
			img_soundrecord, img_camera, img_file, img_search, img_back;
	private Button bt_dep1, bt_dep2, bt_dep3, bt_dep4, bt_dep5;
	private LinearLayout layout_dep;

	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private String userId;
	private String dep;
	private int[] at_image;
	private String[] at_name;
	private Double fileSize = 0.0, fileMaxSize = (double) (30 * 1024 * 1024);

	private User user;
	MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permit_add);

		mapView = (MapView) findViewById(R.id.permit_add_map);

		tv_user = (TextView) findViewById(R.id.permit_add_text01);
		gr = (Gallery) findViewById(R.id.permit_add_gallery);
		img_photograph = (ImageButton) findViewById(R.id.permit_add_photograph);
		img_soundrecord = (ImageButton) findViewById(R.id.permit_add_soundrecord);
		img_camera = (ImageButton) findViewById(R.id.permit_add_camera);
		img_file = (ImageButton) findViewById(R.id.permit_add_file);
		img_search = (ImageButton) findViewById(R.id.permit_add_search);
		img_back = (ImageButton) findViewById(R.id.permit_add_back);
		bt_finish = (ImageButton) findViewById(R.id.permit_add_upload);
		bt_dep1 = (Button) findViewById(R.id.permit_add_bt1);
		bt_dep2 = (Button) findViewById(R.id.permit_add_bt2);
		bt_dep3 = (Button) findViewById(R.id.permit_add_bt3);
		bt_dep4 = (Button) findViewById(R.id.permit_add_bt4);
		bt_dep5 = (Button) findViewById(R.id.permit_add_bt5);
		et_object = (EditText) findViewById(R.id.permit_add_object);
		et_decript = (EditText) findViewById(R.id.permit_add_description);
		layout_dep = (LinearLayout) findViewById(R.id.permit_add_department);

		layout_dep.setVisibility(View.GONE);

		user = (User) getIntent().getSerializableExtra("User");
		userId = String.valueOf(user.getUserId());
		tv_user.setText(user.getName());

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		bt_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkUpload()) {
					layout_dep.setVisibility(View.VISIBLE);
				}
			}
		});

		img_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PermitAdd.this, PermitSearch.class);
				intent.putExtra("User", user);
				startActivity(intent);

			}
		});

		bt_dep1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dep = "1";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dep = "2";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dep = "3";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dep = "4";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				layout_dep.setVisibility(View.GONE);
				Toast.makeText(PermitAdd.this, "已取消提交", Toast.LENGTH_SHORT)
						.show();

			}
		});
		img_file.setOnClickListener(new addFile());
		img_photograph.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent getImageByCamera = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				startActivityForResult(getImageByCamera, 2);
			}
		});

		img_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PermitAdd.this, RecoderVideo.class);
				startActivityForResult(intent, 100); // 获得返回值 用的 然后 判断返回 v
			}

		});
		img_soundrecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						"android.provider.MediaStore.RECORD_SOUND");
				startActivityForResult(intent, 1);

			}
		});

	}

	class addFile implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(PermitAdd.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);
		}

	}

	public Boolean checkUpload() {
		if (et_object.getText().length() == 0) {
			Toast.makeText(PermitAdd.this, "请输入码头/航道", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (et_object.getText().length() >= 100) {
			Toast.makeText(PermitAdd.this, "输入码头/航道请不要大于100个字",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (et_decript.getText().length() >= 2000) {
			Toast.makeText(PermitAdd.this, "输入描述请不要大于2000个字",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (files != null) {
			fileSize = 0.0;
			for (Map.Entry<String, File> file : files.entrySet()) {
				InputStream is;
				try {
					is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						fileSize += len;
					}

					is.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			if (fileSize > fileMaxSize) {
				Toast.makeText(PermitAdd.this, "附件过大，请移除一部分",
						Toast.LENGTH_SHORT).show();
				return false;
			} else
				return true;
		} else
			return true;
	}

	// 提交内容
	public void FinishToUpload() {
		/*GeoPoint gp = mapTool.getAddrLocation();
		if (gp == null)
			Toast.makeText(PermitAdd.this, "定位失败，无法提交", Toast.LENGTH_SHORT)
					.show();
		else
			new UploadDate(this).execute(gp.getLongitudeE6(),
					gp.getLatitudeE6());*/
	}

	class UploadDate extends AsyncTask<Integer, Void, String> {
		ProgressDialog pDialog = null;

		public UploadDate() {

		}

		public UploadDate(Context context) {
			//pDialog = ProgressDialog.show(context, "提交", "数据提交中，请稍候···", true);
			pDialog = new ProgressDialog(PermitAdd.this);
			pDialog.setTitle("提交");
			pDialog.setMessage("数据提交中，请稍候···");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (UploadDate.this != null && UploadDate.this.getStatus() == AsyncTask.Status.RUNNING)
					UploadDate.this.cancel(true);
					
				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (UploadDate.this != null && UploadDate.this.getStatus() == AsyncTask.Status.RUNNING)
					UploadDate.this.cancel(true);
					
				}
			});
			pDialog.show();	
		}

		@Override
		protected String doInBackground(Integer... params) {
			Query query=new Query();
			String time =query.getAndAnServiceTime();
			paramsDate.put("location.longitude", params[0]);
			paramsDate.put("location.latitude", params[1]);
			//paramsDate.put("location.locationName",mapTool.getAddrLocationAddress());
			paramsDate.put("inspection.inspectionUser", userId);
			paramsDate.put("inspection.insCategoriese", dep);
			paramsDate.put("inspection.inspectionObject", et_object.getText());
			paramsDate
					.put("inspection.inspectionContent", et_decript.getText());
			paramsDate.put("inspection.inspectionTime", time);
			if(isCancelled()) return null;//取消异步
			/*HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL
					+ "addInspectionByInspectionId";
			String result = null;
			
			try {
				result = hfu.post(actionUrl, paramsDate, files,
						"illegalEvidence.ef");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;*/
			String actionUrl = HttpUtil.BASE_URL
					+ "addInspectionByInspectionId";
			UploadActivity.tool.addFile("许可踏勘提交",actionUrl, paramsDate,files, "illegalEvidence.ef");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			/*if (result != null) {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				try {
					data = (JSONObject) jsonParser.nextValue();
					String jsonObject0 = data.getString("result");
					if (jsonObject0.equals("1")) {
						Toast.makeText(PermitAdd.this, "保存成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(PermitAdd.this,
								PermitSearch.class);
						intent.putExtra("User", user);
						startActivity(intent);
					} else
						Toast.makeText(PermitAdd.this, "保存失败",
								Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(PermitAdd.this, "发生错误", Toast.LENGTH_SHORT)
							.show();
					e.printStackTrace();
				}

			} else
				Toast.makeText(PermitAdd.this, "发生错误,请重新提交", Toast.LENGTH_SHORT)
						.show();*/
			Intent intent = new Intent(PermitAdd.this,
					PermitSearch.class);
			intent.putExtra("User", user);
			startActivity(intent);
			super.onPostExecute(result);
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1 && resultCode == GetSDTreeActivity.RESULT_CODE)
		{
			if (data != null)
			{
				String path=data.getStringExtra(GetSDTreeActivity.RESULT_PATH);
				String name=data.getStringExtra(GetSDTreeActivity.RESULT_NAME);
				if(path!=null && !path.equals("")&&name!=null && !name.equals(""))
				{
					files.put(name, new File(path));
				}
					
			}
		}
		// 拍照
		if (requestCode == 2 && resultCode == RESULT_OK) {
			String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
			files.put(oStrings[0] + ".jpg", new File(oStrings[1]));
		}
		// 可以根据多个请求代码来作相应的操作
		if (GlobalVar.RECODER_ICON == resultCode) {
			Bundle b = data.getExtras(); // data为B中回传的Intent
			String fileName = b.getString("fileName");// str即为回传的值
			String currentDir = b.getString("currentDir");
			files.put(fileName, new File(currentDir + "/" + fileName));
		}

		creatGV();
	}

	@SuppressWarnings("rawtypes")
	public void creatGV() {
		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		at_image = new int[files.size()];
		at_name = new String[files.size()];
		Iterator keyIteratorOfMap = files.keySet().iterator();
		for (int i = 0; i < files.size(); i++) {
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
		for (int i = 0; i < files.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", at_image[i]);
			map.put("itemText", at_name[i]);

			lst.add(map);
		}
		gr.setAdapter(new galleryAdapter(this));
		gr.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				TextView tv = (TextView) v.findViewById(R.id.text);
				dialog_down((String) tv.getText());

			}
		});
		int nm = 0;
		if (files.size() > 1)
			nm = files.size() / 2;

		gr.setSelection(nm);
		gr.setSpacing(20);

	}

	public class galleryAdapter extends BaseAdapter {

		private int[] img = at_image;
		private String[] str = at_name;
		private Context mContext;

		public galleryAdapter(Context c) {
			mContext = c;

		}

		public int getCount() {
			return img.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(mContext, R.layout.pic_text, null);
				holder.pic = (ImageView) convertView.findViewById(R.id.image);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.pic.setImageResource(img[position]);
			holder.text.setText(str[position]);
			return convertView;
		}

		class ViewHolder {
			private ImageView pic;
			private TextView text;
		}

	}

	public void dialog_down(String kt) {
		final String kt2 = kt;
		new AlertDialog.Builder(this).setTitle("移除文件")
				.setMessage("移除" + kt + "？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						files.remove(kt2);
						creatGV();
					}
				}).setNegativeButton("否", null).show();

	}

	@Override
	protected void onDestroy() {	
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();	
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();		
		super.onResume();
	}
}
