package com.huzhouport.illegal;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressWarnings("deprecation")
public class IllegalSee extends Activity
{
	private TextView tv_title, tv_object, tv_reason, tv_desc, tv_user, tv_time,
			tv_updateTime, tv_updateUse, tv_checkResult, tv_checkcontent,
			tv_checkInfo, tv_approval;
	private TextView tv_supplement;
	private ImageButton imgbt_back, bt_gotoupdate;
	private Gallery gv, gv2;
	private LinearLayout row;
	private RelativeLayout layout2;
	private ImageView img_check;

	private String illegalId;
	private Query query = new Query();
	private int[] at_image;
	private String[] at_name, at_path, at_image2, at_name2, at_path2;
	private MyAdapter ma;
	private List<Map<String, Object>> lsmap;
	private GetCheckName oGetCheckName;
	private GetBitmapList oGetBitmap;
	private User user;
	MapView mapView;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.illegal_see);
		mapView = (MapView) findViewById(R.id.illegal_see_map);
		tv_approval = (TextView) findViewById(R.id.illegal_see_approval);
		tv_title = (TextView) findViewById(R.id.illegal_see_title);
		tv_object = (TextView) findViewById(R.id.illegal_see_object);
		tv_reason = (TextView) findViewById(R.id.illegal_see_text01);
		tv_desc = (TextView) findViewById(R.id.illegal_see_text02);
		tv_user = (TextView) findViewById(R.id.illegal_see_text03);
		tv_time = (TextView) findViewById(R.id.illegal_see_text04);
		tv_updateUse = (TextView) findViewById(R.id.illegal_see_text05);
		tv_updateTime = (TextView) findViewById(R.id.illegal_see_text06);
		tv_checkResult = (TextView) findViewById(R.id.illegal_see_checkResult);
		tv_checkcontent = (TextView) findViewById(R.id.illegal_see_checkcontent);
		tv_checkInfo = (TextView) findViewById(R.id.illegal_see_checkInfo);
		imgbt_back = (ImageButton) findViewById(R.id.illegal_see_back);
		gv = (Gallery) findViewById(R.id.illegal_see_gallery);
		gv2 = (Gallery) findViewById(R.id.illegal_see_galleryImg);
		row = (LinearLayout) findViewById(R.id.illegal_see_updateRow);
		bt_gotoupdate = (ImageButton) findViewById(R.id.illegal_see_bt_update);
		layout2 = (RelativeLayout) findViewById(R.id.illegal_see_layout2);
		img_check = (ImageView) findViewById(R.id.illegal_see_checkImg);
		tv_supplement = (TextView) findViewById(R.id.illegal_see_supplement);

		user = (User) getIntent().getSerializableExtra("User");
		illegalId = getIntent().getStringExtra("illegalId");
		// ��ȡ����
		new GetDate().execute();
		// �������
		new GetUpdateDate().execute();
		// ����
		new GetEvidence().execute();
		oGetBitmap = new GetBitmapList(this);
		// ��ȡλ����Ϣ
		new GetLocation().execute();

		oGetCheckName = new GetCheckName();

		imgbt_back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();

			}
		});

		bt_gotoupdate.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(IllegalSee.this, IllegalUpdate.class);
				intent.putExtra("User", user);
				intent.putExtra("illegalId", illegalId);
				startActivityForResult(intent, 100); // ��÷���ֵ �õ� Ȼ�� �жϷ���

			}
		});

	}

	class GetEvidence extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			return query.showEvidenceByIllegalId(illegalId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			getEvidenceDate(result);
			super.onPostExecute(result);
		}

	}

	// ����
	public void getEvidenceDate(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			int jsonlength = jsonArray.length();
			int imgLength = 0;

			for (int i = 0; i < jsonArray.length(); i++)
			{
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
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				String str = jsonObject.getString("evidencePath");
				int dot = str.lastIndexOf('.');
				String substring = str.substring(dot + 1);
				if (substring.equals("doc"))
				{
					at_image[n] = R.drawable.doc;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("xls"))
				{
					at_image[n] = R.drawable.xls;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("pdf"))
				{
					at_image[n] = R.drawable.pdf;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}

				else if (substring.equals("ppt"))
				{
					at_image[n] = R.drawable.ppt;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("amr"))
				{
					at_image[n] = R.drawable.audio;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("mp4"))
				{
					at_image[n] = R.drawable.video;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				} else if (substring.equals("3gp"))
				{
					at_image[n] = R.drawable.video;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}

				else if (substring.equals("jpg"))
				{
					at_image2[m] = jsonObject.getString("evidencePath");
					at_name2[m] = jsonObject.getString("evidenceName");
					at_path2[m] = jsonObject.getString("evidencePath");
					m++;
				} else if (substring.equals("png"))
				{
					at_image2[m] = jsonObject.getString("evidencePath");
					at_name2[m] = jsonObject.getString("evidenceName");
					at_path2[m] = jsonObject.getString("evidencePath");
					m++;
				} else
				{
					at_image[n] = R.drawable.other_file;
					at_name[n] = jsonObject.getString("evidenceName");
					at_path[n] = jsonObject.getString("evidencePath");
					n++;
				}

			}
			ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < jsonlength - imgLength; i++)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", at_image[i]);
				map.put("itemText", at_name[i]);
				map.put("itemPath", at_path[i]);
				lst.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, lst,
					R.layout.gv_item, new String[]
					{ "itemImage", "itemText" }, new int[]
					{ R.id.gv_item_ItemImage, R.id.gv_item_ItemText });
			gv.setAdapter(adapter);
			gv.setOnItemClickListener(new gridViewOnClickListener());
			int nm = 0;
			if ((jsonlength - imgLength) > 1)
				nm = (jsonlength - imgLength) / 2;
			gv.setSelection(nm);

			lsmap = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < imgLength; i++)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", at_image2[i]);
				map.put("itemText", at_name2[i]);
				map.put("itemPath", at_path2[i]);
				lsmap.add(map);
			}
			// ��ȡԭʼͼƬ
			oGetBitmap.execute();

		} catch (Exception e)
		{
			Toast.makeText(IllegalSee.this, "������Ϣ��ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class GetBitmapList extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pDialog = null;

		public GetBitmapList()
		{

		}

		public GetBitmapList(Context context)
		{
			// pDialog = ProgressDialog.show(context, "ͼƬ", "ͼƬ�����У����Ժ򡤡���",
			// true);
			pDialog = new ProgressDialog(IllegalSee.this);
			pDialog.setTitle("ͼƬ");
			pDialog.setMessage("ͼƬ�����У����Ժ򡤡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{

				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (GetBitmapList.this != null
							&& GetBitmapList.this.getStatus() == AsyncTask.Status.RUNNING)
					{
						GetBitmapList.this.cancel(true); // ���Task�������У�����ȡ����
					}

				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (GetBitmapList.this != null
							&& GetBitmapList.this.getStatus() == AsyncTask.Status.RUNNING)
					{
						GetBitmapList.this.cancel(true); // ���Task�������У�����ȡ����
					}

				}
			});
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			for (int i = 0; i < lsmap.size(); i++)
			{
				if (isCancelled())
					return null;
				Bitmap pic = GetBitmap.GetThumbnailUtils(lsmap.get(i)
						.get("itemPath").toString());
				lsmap.get(i).put("itemPath", pic);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			ma = new MyAdapter(IllegalSee.this, lsmap);
			gv2.setAdapter(ma);
			gv2.setOnItemClickListener(new gridViewOnClickListener2());
			int nm = 0;
			if (at_image2.length > 1)
				nm = at_image2.length / 2;
			gv2.setSelection(nm);
			if (pDialog != null)
				pDialog.dismiss();
			// ��������
			final ScrollView sView = (ScrollView) findViewById(R.id.illegal_see_ScrollView);
			sView.post(new Runnable()
			{
				@Override
				public void run()
				{
					sView.scrollTo(0, 0);
				}
			});
			super.onPostExecute(result);
		}

	}

	class GetLocation extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			return query.showLocationByIllegalId(illegalId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			getLocationFromService(result);
			super.onPostExecute(result);
		}

	}

	// ��ȡλ����Ϣ
	public void getLocationFromService(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				/*GeoPoint geoPoint = new GeoPoint(jsonObject.getInt("latitude"),
						jsonObject.getInt("longitude"));
				mapTool.setCenter(geoPoint);
				mapTool.drawPoint(geoPoint);*/
			}
		} catch (Exception e)
		{
			Toast.makeText(IllegalSee.this, "λ����Ϣ��ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class gridViewOnClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			dialog_down(arg2);
		}

	}

	class gridViewOnClickListener2 implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{

			final int N = arg2;
			new AlertDialog.Builder(IllegalSee.this)
					.setTitle("�鿴ͼƬ")
					.setMessage("�鿴" + at_name2[N] + "��")
					.setPositiveButton("��",
							new DialogInterface.OnClickListener()
							{

								public void onClick(DialogInterface dialog,
										int which)
								{
									String urlstr = HttpUtil.BASE_URL
											+ GlobalVar.FILE_SERVER_PATH
											+ at_path2[N];
									new Download().execute(urlstr, at_name2[N]);
								}
							}).setNegativeButton("��", null).show();

		}

	}

	
	class Download extends AsyncTask<String, Void, Integer>
	{
		String imageURL;
		@Override
		protected Integer doInBackground(String... params)
		{
			imageURL=GlobalVar.FILE_DOWNLOAD_PATH+params[1];
			return query.download(params[0], GlobalVar.FILE_DOWNLOAD_PATH,params[1]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			if(result==0 || result ==1)
				ShowImage.ShowImageBySystemApp(imageURL, IllegalSee.this);
			
//			if (result == 0)
//				Toast.makeText(IllegalSee.this,
//						GlobalVar.FILE_DOWNLOAD_SUCCESS, Toast.LENGTH_SHORT)
//						.show();
//			if (result == 1)
//				Toast.makeText(IllegalSee.this, "�ļ��Ѵ���", Toast.LENGTH_SHORT)
//						.show();
			if (result == -1)
				Toast.makeText(IllegalSee.this, "����ʧ��", Toast.LENGTH_SHORT)
						.show();
			super.onPostExecute(result);
		}

	}

	public void dialog_down(final int arg2)
	{
		new AlertDialog.Builder(this).setTitle("�����ĵ�")
				.setMessage("����" + at_name[arg2] + "��")
				.setPositiveButton("��", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int which)
					{
						String urlstr = HttpUtil.BASE_URL
								+ GlobalVar.FILE_SERVER_PATH + at_path[arg2];
						new Download().execute(urlstr, at_name[arg2]);
					}
				}).setNegativeButton("��", null).show();

	}

	class GetUpdateDate extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			return query.showIllegalUpdateInfo(illegalId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			getSupplementDate(result);
			super.onPostExecute(result);
		}

	}

	// �������
	public void getSupplementDate(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{

				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(1);
				tv_updateUse.setText(tv_updateUse.getText() + "\n"
						+ jsonObject2.getString("name"));
				tv_updateTime.setText(tv_updateTime.getText()
						+ "\n"
						+ CountTime.FormatTime(jsonObject1
								.getString("supplementTime")));

			}
			if (jsonArray.length() == 0)
			{
				tv_supplement.setVisibility(View.GONE);
				tv_updateUse.setVisibility(View.GONE);
				tv_updateTime.setVisibility(View.GONE);
			}
		} catch (Exception e)
		{
			Toast.makeText(IllegalSee.this, "������Ϣ��ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class GetDate extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			return query.showIllegalInfo(illegalId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			initDate(result);
			super.onPostExecute(result);
		}

	}

	public void initDate(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{

				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(1);
				JSONObject jsonObject3 = (JSONObject) jsonArray2.opt(2);
				JSONObject jsonObject4 = (JSONObject) jsonArray2.opt(3);
				JSONObject jsonObject5 = (JSONObject) jsonArray2.opt(4);
				tv_approval.setText(jsonObject5.getString("name"));
				tv_title.setText(jsonObject1.getString("illegalObject"));
				tv_object.setText(jsonObject1.getString("illegalObject"));
				tv_reason.setText(jsonObject4.getString("reasonName"));
				tv_desc.setText(jsonObject1.getString("illegalContent"));
				tv_user.setText(jsonObject2.getString("name") + ","
						+ jsonObject3.getString("name"));
				tv_time.setText(CountTime.FormatTime(jsonObject1
						.getString("illegalTime")));
				int reviewWether = jsonObject1.getInt("reviewWether");
				int reviewResult = jsonObject1.getInt("reviewResult"); // 1ͨ��2δͨ��
				if (reviewWether == 0)
				{
					layout2.setVisibility(View.GONE);
					img_check.setImageDrawable(getResources().getDrawable(
							R.drawable.status1));
				} else
				{

					if (reviewResult == 1)
					{
						row.setVisibility(View.GONE);
						tv_checkResult.setText("��ͨ��");
						img_check.setImageDrawable(getResources().getDrawable(
								R.drawable.status4));
					} else
					{
						tv_checkResult.setText("δͨ��");
						img_check.setImageDrawable(getResources().getDrawable(
								R.drawable.status3));
					}
					if (jsonObject1.getString("reviewComment") == null)
						tv_checkcontent.setText("��");
					else
						tv_checkcontent.setText(jsonObject1
								.getString("reviewComment"));
					// �����
					oGetCheckName.execute();

				}
			}
		} catch (Exception e)
		{
			Toast.makeText(IllegalSee.this, "û���������������", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

	}

	class GetCheckName extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{
			// TODO Auto-generated method stub
			return query.showCheckIllegalInfo(illegalId);
		}

		@Override
		protected void onPostExecute(String result)
		{
			getCheckName(result);
			super.onPostExecute(result);
		}

	}

	// �����
	public void getCheckName(String result2)
	{
		JSONTokener jsonParser = new JSONTokener(result2);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++)
			{

				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject1 = (JSONObject) jsonArray2.opt(0);
				JSONObject jsonObject2 = (JSONObject) jsonArray2.opt(1);
				tv_checkInfo.setText(jsonObject2.getString("name")
						+ " "
						+ CountTime.FormatTime(jsonObject1
								.getString("reviewTime")));

			}
		} catch (Exception e)
		{
			Toast.makeText(IllegalSee.this, "�����Ϣ��ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// ���Ը��ݶ���������������Ӧ�Ĳ���
		if (20 == resultCode)
		{

			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
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
