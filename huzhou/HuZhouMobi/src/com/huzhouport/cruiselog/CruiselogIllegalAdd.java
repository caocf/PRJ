package com.huzhouport.cruiselog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.mapapi.map.MapView;
import com.elc.GetSDTreeActivity;
import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.RecoderVideo;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GetFileFromPhone;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.illegal.IlleageSelectUser;
import com.huzhouport.illegal.IllegalReasonList;
import com.huzhouport.illegal.IllegalSearch;
import com.huzhouport.main.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

@SuppressWarnings("deprecation")
public class CruiselogIllegalAdd extends Activity
{
	private TextView tv_user2, tv_user3;
	private EditText et_object, et_decript;
	private Gallery gr;
	private ImageButton img_addUser, img_addUser1, img_photograph,
			img_soundrecord, img_camera, img_file, img_search, img_back,
			bt_finish;
	private Button bt_dep1, bt_dep2, bt_dep3, bt_dep4, bt_dep5;
	private TextView text_reason;
	private LinearLayout layout_dep;

	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDtae = new HashMap<String, Object>();
	private String userId, reasonId = null;
	private String checkUser = "", checkUser1 = ""; // 记录选择的人员
	private String dep;
	private int[] at_image;
	private String[] at_name;
	private Double fileSize = 0.0, fileMaxSize = (double) (30 * 1024 * 1024);
	private List<Map<String, Object>> list;
	MapView mapView;

	private User user;

	private String cruiselogid;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.illegal_add);

		mapView = (MapView) findViewById(R.id.illegal_add_map);

		TextView tv_user1 = (TextView) findViewById(R.id.illegal_add_text01);
		tv_user2 = (TextView) findViewById(R.id.illegal_add_text02);
		tv_user3 = (TextView) findViewById(R.id.illegal_add_text03);
		gr = (Gallery) findViewById(R.id.illegal_add_gallery);
		img_addUser = (ImageButton) findViewById(R.id.illegal_add_adduser);
		img_addUser1 = (ImageButton) findViewById(R.id.illegal_add_adduser1);
		img_photograph = (ImageButton) findViewById(R.id.illegal_add_photograph);
		img_soundrecord = (ImageButton) findViewById(R.id.illegal_add_soundrecord);
		img_camera = (ImageButton) findViewById(R.id.illegal_add_camera);
		img_file = (ImageButton) findViewById(R.id.illegal_add_file);
		img_search = (ImageButton) findViewById(R.id.illegal_add_search);
		img_back = (ImageButton) findViewById(R.id.illegal_add_back);
		bt_finish = (ImageButton) findViewById(R.id.illegal_add_upload);
		bt_dep1 = (Button) findViewById(R.id.illegal_add_bt1);
		bt_dep2 = (Button) findViewById(R.id.illegal_add_bt2);
		bt_dep3 = (Button) findViewById(R.id.illegal_add_bt3);
		bt_dep4 = (Button) findViewById(R.id.illegal_add_bt4);
		bt_dep5 = (Button) findViewById(R.id.illegal_add_bt5);
		text_reason = (TextView) findViewById(R.id.illegal_add_text);
		et_object = (EditText) findViewById(R.id.illegal_add_object);
		et_decript = (EditText) findViewById(R.id.illegal_add_description);
		layout_dep = (LinearLayout) findViewById(R.id.illegal_add_department);

		layout_dep.setVisibility(View.GONE);

		user = (User) getIntent().getSerializableExtra("User");
		userId = String.valueOf(user.getUserId());
		cruiselogid = getIntent().getStringExtra("cruiselogid");
		if (cruiselogid == null)
		{
			cruiselogid = "0";
			System.out.println("cruiselogid===" + cruiselogid);
		}

		tv_user1.setText(user.getName());
		// 选择缘由
		text_reason.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CruiselogIllegalAdd.this,
						IllegalReasonList.class);
				startActivityForResult(intent, 70);

			}
		});

		img_addUser.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CruiselogIllegalAdd.this,
						IlleageSelectUser.class);
				intent.putExtra("User", user);
				startActivityForResult(intent, 80);
			}
		});
		img_addUser1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CruiselogIllegalAdd.this,
						IlleageSelectUser.class);
				intent.putExtra("User", user);
				intent.putExtra("userkind", "2");
				startActivityForResult(intent, 90);
			}
		});

		// 违章缘由列表
		// new GetIllegalReason().execute();

		img_back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		bt_finish.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (checkUpload())
				{
					layout_dep.setVisibility(View.VISIBLE);
				}
			}
		});

		img_search.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CruiselogIllegalAdd.this,
						IllegalSearch.class);
				intent.putExtra("User", user);
				startActivity(intent);

			}
		});

		bt_dep1.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				dep = "1";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep2.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				dep = "2";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep3.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				dep = "3";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep4.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				dep = "4";
				layout_dep.setVisibility(View.GONE);
				FinishToUpload();

			}
		});
		bt_dep5.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				layout_dep.setVisibility(View.GONE);
				Toast.makeText(CruiselogIllegalAdd.this, "已取消提交",
						Toast.LENGTH_SHORT).show();

			}
		});
		img_file.setOnClickListener(new addFile());
		img_photograph.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent getImageByCamera = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				startActivityForResult(getImageByCamera, 2);
			}
		});

		img_camera.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(CruiselogIllegalAdd.this,
						RecoderVideo.class);
				startActivityForResult(intent, 100); // 获得返回值 用的 然后 判断返回 v

			}

		});
		img_soundrecord.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(
						MediaStore.Audio.Media.RECORD_SOUND_ACTION);
				startActivityForResult(intent, 1);
			}
		});

		img_search.setVisibility(View.GONE);// 巡航日志里不需要

	}

	public void showNameList(String result)
	{

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject JB = (JSONObject) jsonArray.opt(i);
				if (userId != JB.getString("userId"))
				{
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userName", JB.getString("name"));
					map.put("userId", JB.getString("userId"));
					list.add(map);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "没有搜索到相关数据",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	class addFile implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.setClass(CruiselogIllegalAdd.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);

		}

	}

	public Boolean checkUpload()
	{
		if (checkUser.length() == 0)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "请选择第二执法人",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (checkUser1.length() == 0)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "请选择审核人",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (et_object.getText().length() == 0)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "请输入船名",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (et_object.getText().length() >= 100)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "输入船名请不要大于100个字",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (et_decript.getText().length() >= 2000)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "输入描述请不要大于2000个字",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (reasonId == null)
		{
			Toast.makeText(CruiselogIllegalAdd.this, "违章缘由不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (files != null)
		{
			fileSize = 0.0;
			for (Map.Entry<String, File> file : files.entrySet())
			{
				InputStream is;
				try
				{
					is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1)
					{
						fileSize += len;
					}

					is.close();

				} catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}

			}
			if (fileSize > fileMaxSize)
			{
				Toast.makeText(CruiselogIllegalAdd.this, "附件过大，请移除一部分",
						Toast.LENGTH_SHORT).show();
				return false;
			} else
				return true;
		} else
			return true;
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

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			Date curDate = new Date();
			String str = formatter.format(curDate);
			String time = str;// 获取当前时间
			paramsDtae.put("location.longitude", params[0]);
			paramsDtae.put("location.latitude", params[1]);
			//paramsDtae.put("location.locationName",mapTool.getAddrLocationAddress());
			paramsDtae.put("illegal.enforecers1", userId);
			paramsDtae.put("illegal.enforecers2", checkUser);
			paramsDtae.put("illegal.reviewUser", checkUser1);
			paramsDtae.put("illegal.illegalCategories", dep);
			paramsDtae.put("illegal.illegalObject", et_object.getText());
			paramsDtae.put("illegal.illegalReason", reasonId);
			paramsDtae.put("illegal.illegalContent", et_decript.getText());
			paramsDtae.put("illegal.illegalTime", time);
			paramsDtae.put("cruiselogid", cruiselogid);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "addIllegalByIllegalId";
			String result = null;
			if (isCancelled())
				return null;// 取消异步
			try
			{
				result = hfu.post(actionUrl, paramsDtae, files,
						"illegalEvidence.ef");
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
			JSONObject data;
			try
			{
				data = (JSONObject) jsonParser.nextValue();
				String jsonObject0 = data.getString("result");
				String resultvalue = data.getString("totalPage"); // 回传值的内容
				if (jsonObject0.equals("1"))
				{
					Toast.makeText(CruiselogIllegalAdd.this, "保存成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("result", resultvalue);// 设置回传的意图
					setResult(1001, intent);
					finish();

					if (user != null)
					{
						Log log = new Log(user.getName(), "添加巡航日志违章取证",
								GlobalVar.LOGSAVE, ""); // 日志
						log.execute();
					}

				} else
					Toast.makeText(CruiselogIllegalAdd.this, "保存失败",
							Toast.LENGTH_SHORT).show();
			} catch (Exception e)
			{
				Toast.makeText(CruiselogIllegalAdd.this, "发生错误",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			if (pDialog != null)
				pDialog.dismiss();
			super.onPostExecute(result);
		}

	}

	// 提交内容
	public void FinishToUpload()
	{
		/*GeoPoint gp = mapTool.getAddrLocation();
		if (gp == null)
			Toast.makeText(CruiselogIllegalAdd.this, "定位失败，无法提交",
					Toast.LENGTH_SHORT).show();
		else
			new UploadDate(this).execute(gp.getLongitudeE6(),
					gp.getLatitudeE6());*/
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (70 == resultCode)
		{// 违章缘由
			reasonId = data.getStringExtra("resonid");
			text_reason.setText(data.getStringExtra("resonname"));

		} else if (90 == resultCode)
		{// 审核人
			checkUser1 = data.getStringExtra("user_id");
			tv_user3.setText(data.getStringExtra("user_name"));
			System.out.println("user_id:" + data.getStringExtra("user_id"));
		}

		else if (80 == resultCode)
		{// 第二取证人
			checkUser = data.getStringExtra("user_id");
			tv_user2.setText(data.getStringExtra("user_name"));
			System.out.println("user_id:" + data.getStringExtra("user_id"));
		} else
		{
			if (requestCode == 1 && resultCode == GetSDTreeActivity.RESULT_CODE)
			{
				if (data != null)
				{
					String path = data
							.getStringExtra(GetSDTreeActivity.RESULT_PATH);
					String name = data
							.getStringExtra(GetSDTreeActivity.RESULT_NAME);
					if (path != null && !path.equals("") && name != null
							&& !name.equals(""))
					{
						files.put(name, new File(path));
					}

				}
			}

			// 拍照
			if (requestCode == 2 && resultCode == RESULT_OK)
			{
				String[] oStrings = GetFileFromPhone.getImageFromPhone(data);
				files.put(oStrings[0] + ".jpg", new File(oStrings[1]));
			}
			// 可以根据多个请求代码来作相应的操作
			if (GlobalVar.RECODER_ICON == resultCode)
			{
				Bundle b = data.getExtras(); // data为B中回传的Intent
				String fileName = b.getString("fileName");// str即为回传的值
				String currentDir = b.getString("currentDir");
				files.put(fileName, new File(currentDir + "/" + fileName));
			}
			creatGV();
		}
	}

	@SuppressWarnings("rawtypes")
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

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		mapView.onResume();
		super.onResume();
	}

}
