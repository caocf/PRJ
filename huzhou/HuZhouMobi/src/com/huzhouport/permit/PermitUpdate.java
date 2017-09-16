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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("deprecation")
public class PermitUpdate extends Activity {
	private TextView tv_user, tv_object;
	private EditText et_decript;
	private Gallery gr;
	private ImageButton  img_photograph, img_soundrecord,
			img_camera, img_file, img_back;
	private ImageButton bt_upload;

	private Query query = new Query();
	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private String inspectionId;
	private int[] at_image;
	private String[] at_name;
	private Double fileSize, fileMaxSize = (double) (30 * 1024 * 1024);
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.permit_update);

		tv_user = (TextView) findViewById(R.id.permit_update_text01);
		gr = (Gallery) findViewById(R.id.permit_update_gallery);
		img_photograph = (ImageButton) findViewById(R.id.permit_update_photograph);
		img_soundrecord = (ImageButton) findViewById(R.id.permit_update_soundrecord);
		img_camera = (ImageButton) findViewById(R.id.permit_update_camera);
		img_file = (ImageButton) findViewById(R.id.permit_update_file);
		img_back = (ImageButton) findViewById(R.id.permit_update_back);
		bt_upload = (ImageButton) findViewById(R.id.permit_update_upload);
		tv_object = (TextView) findViewById(R.id.permit_update_object);
		et_decript = (EditText) findViewById(R.id.permit_update_description);

		user = (User) getIntent().getSerializableExtra("User");
		inspectionId = getIntent().getStringExtra("inspectionId");

		tv_user.setText(user.getName());

		// 获取数据
		new GetDate().execute();
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
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

		img_camera.setOnClickListener(new cameraOpen());
		img_soundrecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("audio/amr");
				intent.setClassName("com.android.soundrecorder",
						"com.android.soundrecorder.SoundRecorder");
				startActivityForResult(intent, 1);

			}
		});
		bt_upload.setOnClickListener(new FinishToUpload());

	}

	class cameraOpen implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(PermitUpdate.this, RecoderVideo.class);
			startActivityForResult(intent, 100); // 获得返回值 用的 然后 判断返回 v
		}

	}

	class addFile implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(PermitUpdate.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);

		}

	}

	public Boolean checkUpload() {
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
				Toast.makeText(PermitUpdate.this, "附件过大，请移除一部分",
						Toast.LENGTH_SHORT).show();
				return false;
			} else
				return true;
		} else
			return true;
	}

	// 提交内容
	class FinishToUpload implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (checkUpload()) {
				new UploadDate(PermitUpdate.this).execute();
			}
		}
	}

	class UploadDate extends AsyncTask<Void, Void, String> {

		ProgressDialog pDialog = null;

		public UploadDate() {

		}

		public UploadDate(Context context) {
			//pDialog = ProgressDialog.show(context, "提交", "数据提交中，请稍候···", true);
			pDialog = new ProgressDialog(PermitUpdate.this);
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
		protected String doInBackground(Void... params) {
			String time =query.getAndAnServiceTime();
			paramsDate.put("inspection.inspectionId", inspectionId);
			paramsDate.put("inspectionSupplement.userId", user.getUserId());
			paramsDate
					.put("inspection.inspectionContent", et_decript.getText());
			paramsDate.put("inspectionSupplement.supplementTime", time);
			if(isCancelled()) return null;//取消异步
			/*HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL
					+ "addInspectionSupplementByInspectionId_mobi";
			String result = null;
			if(isCancelled()) return null;//取消异步
			try {
				result = hfu.post(actionUrl, paramsDate, files,
						"illegalEvidence.ef");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;*/
			String url = HttpUtil.BASE_URL + "addInspectionSupplementByInspectionId_mobi";
			UploadActivity.tool.addFile("许可踏勘修改",url, paramsDate,files, "illegalEvidence.ef");
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
						Toast.makeText(PermitUpdate.this, "保存成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(PermitUpdate.this,
								PermitSearch.class);
						intent.putExtra("User", user);
						startActivity(intent);
						setResult(20);
						finish();
					} else
						Toast.makeText(PermitUpdate.this, "保存失败",
								Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(PermitUpdate.this, "发生错误",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			} else
				Toast.makeText(PermitUpdate.this, "发生错误", Toast.LENGTH_SHORT)
						.show();*/
			Intent intent = new Intent(PermitUpdate.this,
					PermitSearch.class);
			intent.putExtra("User", user);
			startActivity(intent);
			setResult(20);
			finish();
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
			System.out.println(substring);
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
		// gr.setUnselectedAlpha(10.0f);

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

	class GetDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.showInspectionInfo(inspectionId);
		}

		@Override
		protected void onPostExecute(String result) {
			initDate(result);
			super.onPostExecute(result);
		}

	}

	public void initDate(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
				tv_object.setText(jsonObject1.getString("inspectionObject"));
				et_decript.setText(jsonObject1.getString("inspectionContent"));

			}
		} catch (Exception e) {
			Toast.makeText(PermitUpdate.this, "没有搜索到相关数据", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}
}
