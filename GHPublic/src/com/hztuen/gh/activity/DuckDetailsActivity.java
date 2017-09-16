package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gh.modol.CardImagesModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.HomeAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/*
 * 码头详情信息
 */
public class DuckDetailsActivity extends Activity {

	private GridView gview;
	private RelativeLayout relative_title_final;

	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;
	private TextView tv_title;
	private TextView phone;
	private ImageView img_phone;
	private HomeAdapter imgAdapter;
    private List<CardImagesModel> imageUrls=new ArrayList<CardImagesModel>();
	private TextView duckName, duckNum, duckJing, duckPhone, duckPeople,
			duckCred;
	// 图片封装为一个数组
//	private int[] icon = { R.drawable.ic_alarm, R.drawable.ic_alarm,
//			R.drawable.ic_alarm, R.drawable.ic_alarm, R.drawable.ic_alarm,
//			R.drawable.ic_alarm, R.drawable.ic_alarm, R.drawable.ic_alarm,
//			R.drawable.ic_alarm, R.drawable.ic_alarm, R.drawable.ic_alarm,
//			R.drawable.ic_alarm };
//	private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
//			"设置", "语音", "天气", "浏览器", "视频" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duck_details);
		init();
		GetDetialTextTask();

	}

	private void init() {
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		duckName = (TextView) findViewById(R.id.wharfname);// 码头名称
		duckNum = (TextView) findViewById(R.id.wharfnum);// 码头编号
		duckJing = (TextView) findViewById(R.id.text2_context);// 经营范围
//		duckPhone = (TextView) findViewById(R.id.text3_context);// 联系方式
		duckPeople = (TextView) findViewById(R.id.text4_context);// 联系人
		duckCred = (TextView) findViewById(R.id.text5_context);// 信用等级
		
		phone=(TextView)findViewById(R.id.text3_context);
		
		img_phone=(ImageView)findViewById(R.id.img_phone);
		img_phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				if("".equals(phone.getText().toString())){
					Toast.makeText(getApplicationContext(), "暂无商家联系方式。", Toast.LENGTH_SHORT).show();
				}else{
					Uri uri = Uri.parse("tel:"+phone.getText().toString());

					Intent dialntent = new Intent(Intent.ACTION_DIAL,uri);

					startActivity(dialntent);
				}
				
			}
		});

		tv_title = (TextView) findViewById(R.id.textView1);

		tv_title.setText(SystemStatic.Wharfname);

		gview = (GridView) findViewById(R.id.gview);
		
		
		// 新建List
		data_list = new ArrayList<Map<String, Object>>();
		// 获取数据
	//	getData();
		// 新建适配器
//		String[] from = { "image", "text" };
//		int[] to = { R.id.image, R.id.text };
//		sim_adapter = new SimpleAdapter(this, data_list, R.layout.gird_item,
//				from, to);
//		// 配置适配器
//		gview.setAdapter(sim_adapter);
//		setListViewHeightBasedOnChildren(gview);
		
		
		GetDetialPictTask();
		
//		for(int i=0;i<9;i++)
//		{
//			CardImagesModel model=new CardImagesModel();
//			model.setimgUrl("http://120.55.193.1:8080/GH/FolderFiles/wharfile/appicon.png");
//			imageUrls.add(model);
//			
//		}
//		gview.setAdapter(imgAdapter);
		setListViewHeightBasedOnChildren(gview);
	}

//	public List<Map<String, Object>> getData() {
//		// cion和iconName的长度是相同的，这里任选其一都可以
//		for (int i = 0; i < icon.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("image", icon[i]);
//			map.put("text", iconName[i]);
//			data_list.add(map);
//		}
//
//		return data_list;
//	}

	// 设定girdview 高度
	public static void setListViewHeightBasedOnChildren(GridView listView) {
		// 获取listview的adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		// 固定列宽，有多少列
		int col = 4;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置margin
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
	}

	// 获取码头文字部分详情信息
	private void GetDetialTextTask() {

		// TODO Auto-generated method stub

		// 访问网络

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Wharfname=" + SystemStatic.Wharfname);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.getwharf;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					// Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try {

						JSONObject res = new JSONObject(result);
						JSONObject obj = res.getJSONObject("obj");

						// String duckName=obj.getString("wharfname");
						duckName.setText(obj.getString("wharfname"));

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}

	// 获取码头证书部分详情信息
	private void GetDetialPictTask() {

		// TODO Auto-generated method stub

		// 访问网络

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Wharfname=" + SystemStatic.Wharfname);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.getwharfcert;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					// Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try {

						JSONObject res = new JSONObject(result);
						
						JSONArray obj = res.getJSONArray("obj");
						
						
						if (obj.length() == 0) {
							Toast.makeText(getApplicationContext(), "最近无发布信息",
									Toast.LENGTH_SHORT).show();
						
							
						} else {
							
							for (int i = 0; i < obj.length(); i++) {
								JSONObject temp = obj.getJSONObject(i);
								CardImagesModel imagemodel = new CardImagesModel();

								imagemodel.setimgUrl("http://120.55.193.1:8080/GH/"+temp.getString("bl"));
								imageUrls.add(imagemodel);

							}
							
							imgAdapter=new HomeAdapter(getApplicationContext(),imageUrls);
							gview.setAdapter(imgAdapter);
							
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
	


}
