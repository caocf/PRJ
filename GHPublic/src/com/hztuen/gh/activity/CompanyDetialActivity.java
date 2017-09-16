package com.hztuen.gh.activity;

/**
 * @author zzq
 * @DateTime 2016年7月21日 上午11:03:09
 * 公司的详细信息
 */
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
import android.widget.TextView;
import android.widget.Toast;
import com.gh.modol.CardImagesModel;
import com.hxkg.company.CompanyMolel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.HomeAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;

/**
 * @author zzq
 * @DateTime 2016年7月21日 下午4:57:18
 * 企业信息详情
 */
public class CompanyDetialActivity extends Activity
{
	private GridView gview;
	private RelativeLayout relative_title_final;  
	private TextView tv_title; 
	private ImageView img_phone;
	private HomeAdapter imgAdapter;
	private List<CardImagesModel> imageUrls=new ArrayList<CardImagesModel>();
	private TextView duckName, duckJing, phone, duckPeople, duckCred,area,type;
	CompanyMolel comModol=new CompanyMolel();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companydetailactivity);
		init();
		//GetDetialTextTask();
	}
	private void init() 
	{
		Intent intent = getIntent();
		comModol = (CompanyMolel) intent.getSerializableExtra("CompanyMolel");
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		duckName = (TextView) findViewById(R.id.wharfname);// 码头名称
		duckName.setText(comModol.name);
		duckJing = (TextView) findViewById(R.id.text2_context);// 经营范围
		duckJing.setText(comModol.business); 
		duckPeople = (TextView) findViewById(R.id.text4_context);// 联系人
		duckPeople.setText(comModol.linkman);
		duckCred = (TextView) findViewById(R.id.text5_context);// 信用等级

		phone=(TextView)findViewById(R.id.text3_context);
		phone.setText(comModol.tel);
		area = (TextView) findViewById(R.id.area);
		area.setText(comModol.user_region);
		type = (TextView) findViewById(R.id.type);
		type.setText(comModol.user_type);
		img_phone=(ImageView)findViewById(R.id.img_phone);
		img_phone.setOnClickListener(new OnClickListener() 
		{
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
		tv_title.setText(comModol.name);
		gview = (GridView) findViewById(R.id.gview); 
		GetDetialPictTask();
		setListViewHeightBasedOnChildren(gview);
	}
	// 设定girdview 高度
	public static void setListViewHeightBasedOnChildren(GridView listView)
	{ 
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
	private void GetDetialTextTask() 
	{

		// TODO Auto-generated method stub
		// 访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Name=" + comModol.name);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.getcompany;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) 
				{
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try {
						JSONObject res = new JSONObject(result);
						JSONObject obj = res.getJSONObject("obj");
						area.setText(obj.getString("region"));
						type.setText(obj.getString("type"));
						duckJing.setText(obj.getString("business"));
						duckPeople.setText(obj.getString("linkman"));
						phone.setText(obj.getString("tel")); 
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
	private void GetDetialPictTask() 
	{ 
		// 访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("ID=" + comModol.id);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.baseUrl+"CompanyCertByID";
		if (!toUrl.equals("")) 
		{
			kjh.post(toUrl, params, false, new HttpCallBack() 
			{
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) 
				{
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie")); 
					String result = new String(t).trim();
					try {
						JSONObject res = new JSONObject(result);
						JSONArray obj = res.getJSONArray("obj");
						if (obj.length() == 0) {
							Toast.makeText(getApplicationContext(), "最近无发布信息",
									Toast.LENGTH_SHORT).show();
						} else {
							for (int i = 0; i < obj.length(); i++)
							{
								JSONObject temp = obj.getJSONObject(i);
								CardImagesModel imagemodel = new CardImagesModel();
								imagemodel.setimgUrl(contants.baseUrl+temp.getString("dir"));
								imageUrls.add(imagemodel);
							}
							imgAdapter=new HomeAdapter(getApplicationContext(),imageUrls);
							gview.setAdapter(imgAdapter);
						}
					} catch (Exception e1) 
					{
						e1.printStackTrace();
					}
				}
				@Override
				public void onFailure(int errorNo, String strMsg) 
				{
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
}
