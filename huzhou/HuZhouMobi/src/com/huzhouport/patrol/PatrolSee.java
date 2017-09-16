package com.huzhouport.patrol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.mapapi.map.MapView;
import com.example.huzhouport.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.GetBitmap;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.MyAdapter;
import com.huzhouport.common.util.Query;
import com.huzhouport.common.util.ShowImage;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressWarnings("deprecation")
public class PatrolSee extends Activity {
	private TextView tv_title, tv_object, tv_desc, tv_user, tv_time,
			tv_updateTime, tv_updateUse;
	private ImageButton imgbt_back,bt_gotoupdate;
	private Gallery gv, gv2;
	private TableRow tr_supplement;

	private String patrolId;
	private Query query = new Query();
	private int[] at_image;
	private String[] at_name, at_path, at_image2, at_name2, at_path2;
	private MyAdapter ma;
	private List<Map<String, Object>> lsmap;
	private GetBitmapList oGetBitmap;
	private User user;
	MapView mapView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patrol_see);
		mapView = (MapView) findViewById(R.id.patrol_see_map);
		tv_title = (TextView) findViewById(R.id.patrol_see_title);
		tv_object = (TextView) findViewById(R.id.patrol_see_object);
		tv_desc = (TextView) findViewById(R.id.patrol_see_text02);
		tv_user = (TextView) findViewById(R.id.patrol_see_text03);
		tv_time = (TextView) findViewById(R.id.patrol_see_text04);
		tr_supplement = (TableRow) findViewById(R.id.patrol_see_tr_supplement);
		tv_updateUse = (TextView) findViewById(R.id.patrol_see_text05);
		tv_updateTime = (TextView) findViewById(R.id.patrol_see_text06);
		imgbt_back = (ImageButton) findViewById(R.id.patrol_see_back);
		gv = (Gallery) findViewById(R.id.patrol_see_gallery);
		gv2 = (Gallery) findViewById(R.id.patrol_see_galleryImg);
		bt_gotoupdate = (ImageButton) findViewById(R.id.patrol_see_bt_update);

		user = (User) getIntent().getSerializableExtra("User");
		patrolId = getIntent().getStringExtra("patrolId");
		// 获取数据
		new GetDate().execute();
		// 补充情况
		new GetUpdateDate().execute();
		// 附件
		new GetEvidence().execute();
		oGetBitmap = new GetBitmapList(this);
		// 获取位置信息
		new GetLocation().execute();
		imgbt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		bt_gotoupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PatrolSee.this, PatrolUpdate.class);
				intent.putExtra("User", user);
				intent.putExtra("patrolId", patrolId);
				startActivityForResult(intent,100); //获得返回值 用的  然后 判断返回

			}
		});
		
	}

	class GetEvidence extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.showEvidenceByPatrolId(patrolId);
		}

		@Override
		protected void onPostExecute(String result) {
			getEvidenceDate(result);
			super.onPostExecute(result);
		}

	}

	// 附件
	public void getEvidenceDate(String result) {

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			int jsonlength = jsonArray.length();
			int imgLength = 0;

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				String str = jsonObject.getString("evidencePath");
				int dot = str.lastIndexOf('.');
				String substring = str.substring(dot + 1);
				if (substring.equals("jpg"))
					imgLength++;
				else if (substring.equals("png"))
					imgLength++;
			}
			at_image2 = new String[imgLength];
			at_name2 = new String[imgLength];
			at_path2 = new String[imgLength];
			at_image = new int[jsonlength - imgLength];
			at_name = new String[jsonlength - imgLength];
			at_path = new String[jsonlength - imgLength];
			int n = 0, m = 0;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				String str = jsonObject.getString("evidencePath");
				int dot = str.lastIndexOf('.');
				String substring = str.substring(dot + 1);
				if (substring.equals("doc")) {
					at_image[n] = R.drawable.doc;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("xls")) {
					at_image[n] = R.drawable.xls;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("ppt")) {
					at_image[n] = R.drawable.ppt;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("amr")) {
					at_image[n] = R.drawable.audio;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}else if (substring.equals("mp4")) {
					at_image[n] = R.drawable.video;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}else if (substring.equals("mp3")) {
					at_image[n] = R.drawable.video;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}else if (substring.equals("pdf")) {
					at_image[n] = R.drawable.pdf;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("jpg")) {
					at_image2[m] = jsonObject.getString("evidencePath");
					at_name2[m] = jsonObject.getString("evidenceName");
					at_path2[m] = jsonObject.getString("evidencePath");
					m++;
				} else if (substring.equals("png")) {
					at_image2[m] = jsonObject.getString("evidencePath");
					at_name2[m] = jsonObject.getString("evidenceName");
					at_path2[m] = jsonObject.getString("evidencePath");
					m++;
				} else {
					at_image[n] = R.drawable.other_file;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}

			}
			ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonlength - imgLength; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", at_image[i]);
				map.put("itemText", at_name[i]);
				map.put("itemPath", at_path[i]);
				lst.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, lst,
					R.layout.gv_item,
					new String[] { "itemImage", "itemText" }, new int[] {
							R.id.gv_item_ItemImage, R.id.gv_item_ItemText });
			gv.setAdapter(adapter);
			gv.setOnItemClickListener(new gridViewOnClickListener());
			int nm = 0;
			if ((jsonlength - imgLength) > 1)
				nm = (jsonlength - imgLength) / 2;
			gv.setSelection(nm);

			lsmap = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < imgLength; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", at_image2[i]);
				map.put("itemText", at_name2[i]);
				map.put("itemPath", at_path2[i]);
				lsmap.add(map);
			}

			oGetBitmap.execute();
		} catch (Exception e) {
			Toast.makeText(PatrolSee.this, "补充信息获取失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class GetBitmapList extends AsyncTask<Void, Void, Void> {
		ProgressDialog pDialog = null;

		public GetBitmapList() {

		}

		public GetBitmapList(Context context) {
			//pDialog = ProgressDialog.show(context, "图片", "图片加载中，请稍候···", true);
			pDialog = new ProgressDialog(PatrolSee.this);
			pDialog.setTitle("图片");
			pDialog.setMessage("图片加载中，请稍候···");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{
				
				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (GetBitmapList.this != null && GetBitmapList.this.getStatus() == AsyncTask.Status.RUNNING)
					GetBitmapList.this.cancel(true);
					
				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (GetBitmapList.this != null && GetBitmapList.this.getStatus() == AsyncTask.Status.RUNNING)
					GetBitmapList.this.cancel(true);
					
				}
			});
			pDialog.show();	
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 0; i < lsmap.size(); i++) {
				if(isCancelled()) return null;//取消异步
				Bitmap pic = GetBitmap.GetThumbnailUtils(lsmap.get(i)
						.get("itemPath").toString());
				lsmap.get(i).put("itemPath", pic);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (pDialog != null)
				pDialog.dismiss();
			ma = new MyAdapter(PatrolSee.this, lsmap);
			gv2.setAdapter(ma);
			gv2.setOnItemClickListener(new gridViewOnClickListener2());
			int nm = 0;
			if (at_image2.length > 1)
				nm = at_image2.length / 2;
			gv2.setSelection(nm);
		
			super.onPostExecute(result);
		}

	}

	class GetLocation extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.showLocationByInspectionId(patrolId);
		}

		@Override
		protected void onPostExecute(String result) {
			getLocationFromService(result);
			super.onPostExecute(result);
		}

	}

	// 获取位置信息
	public void getLocationFromService(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				/*GeoPoint geoPoint=new GeoPoint((int) (jsonObject.getDouble("latitude")*1E6),(int) (jsonObject.getDouble("longitude")*1E6));
				mapTool.setCenter(geoPoint);
				mapTool.drawPoint(geoPoint);*/
			}
		} catch (Exception e) {
			Toast.makeText(PatrolSee.this, "位置信息获取失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class gridViewOnClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			dialog_down(arg2);
		}

	}

	class gridViewOnClickListener2 implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			final int N = arg2;
			new AlertDialog.Builder(PatrolSee.this)
					.setTitle("查看图片")
					.setMessage("查看" + at_name2[N] + "？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									String urlstr = HttpUtil.BASE_URL +  GlobalVar.FILE_SERVER_PATH 
											+ at_path2[N];
									new Download().execute(urlstr, at_name2[N]);

								}
							}).setNegativeButton("否", null).show();

		}

	}
	class Download extends AsyncTask<String, Void, Integer> {

		String imageURL;
		@Override
		protected Integer doInBackground(String... params) {

			imageURL = GlobalVar.FILE_DOWNLOAD_PATH + params[1];
			return query.download(params[0], GlobalVar.FILE_DOWNLOAD_PATH,
					params[1]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			if(result==0 || result ==1)
				ShowImage.ShowImageBySystemApp(imageURL, PatrolSee.this);
//			if (result == 0)
//				Toast.makeText(PatrolSee.this, GlobalVar.FILE_DOWNLOAD_SUCCESS,
//						Toast.LENGTH_SHORT).show();
//			if (result == 1)
//				Toast.makeText(PatrolSee.this, "文件已存在", Toast.LENGTH_SHORT)
//						.show();
			if (result == -1)
				Toast.makeText(PatrolSee.this, "下载失败", Toast.LENGTH_SHORT)
						.show();
			super.onPostExecute(result);
		}

	}
	public void dialog_down(final int arg2) {

		new AlertDialog.Builder(this).setTitle("下载文档")
				.setMessage("下载" + at_name[arg2] + "？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						String urlstr = HttpUtil.BASE_URL + GlobalVar.FILE_SERVER_PATH 
								+ at_path[arg2];
						new Download().execute(urlstr, at_name[arg2]);

					}
				}).setNegativeButton("否", null).show();

	}

	class GetUpdateDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.showPatrolUpdateInfo(patrolId);
		}

		@Override
		protected void onPostExecute(String result) {
			getUpdateDate(result);
			super.onPostExecute(result);
		}

	}

	// 补充情况
	public void getUpdateDate(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(1);
				tv_updateUse.setText(tv_updateUse.getText() + "\n"
						+ jsonObject2.getString("name"));
				tv_updateTime.setText(tv_updateTime.getText() + "\n"
						+ CountTime.FormatTime(jsonObject1.getString("supplementTime")));

			}
			if(jsonArray.length()==0)
			{
				tr_supplement.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			Toast.makeText(PatrolSee.this, "补充信息获取失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class GetDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.showPatrolInfo(patrolId);
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
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(1);
				tv_title.setText(jsonObject1.getString("patrolObject"));
				tv_object.setText(jsonObject1.getString("patrolObject"));
				tv_desc.setText(jsonObject1.getString("patrolContent"));
				tv_user.setText(jsonObject2.getString("name"));
				tv_time.setText(CountTime.FormatTime(jsonObject1.getString("patrolTime")));
			}
		} catch (Exception e) {
			Toast.makeText(PatrolSee.this, "没有搜索到相关数据", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //可以根据多个请求代码来作相应的操作
            if(20==resultCode)
            {
               setResult(20);
         	   finish();
            }
            super.onActivityResult(requestCode, resultCode, data);
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
