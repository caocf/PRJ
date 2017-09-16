package net.hxkg.company;
import android.app.Activity;
import android.os.Bundle;

/**
 * @author zzq
 * @DateTime 2016年7月21日 上午11:03:09
 * 公司的详细信息
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import org.json.JSONArray;
import org.json.JSONObject; 
import android.content.Intent;
import android.net.Uri; 
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author zzq
 * @DateTime 2016年7月21日 下午4:57:18
 * 企业信息详情
 */
public class CompanyDetialActivity extends Activity implements HttpRequest.onResult
{

	private GridView gview;
	private RelativeLayout relative_title_final;

	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;
	private TextView tv_title;
	private TextView phone;
	private ImageView img_phone;
	private HomeAdapter imgAdapter;
	private List<CardImagesModel> imageUrls=new ArrayList<CardImagesModel>();
	private TextView duckName, duckJing, duckPhone, duckPeople,
	duckCred,area,type;
	CompanyNameEntity companyNameEntity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.companydetailactivity);
		
		init(); 
	}
	private void init() 
	{
		Intent intent = getIntent();
		companyNameEntity = (CompanyNameEntity) intent.getSerializableExtra("CompanyNameEntity");
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		duckName = (TextView) findViewById(R.id.wharfname);// 码头名称
		duckName.setText(companyNameEntity.QYMC);
		duckJing = (TextView) findViewById(R.id.text2_context);// 经营范围
		duckJing.setText(companyNameEntity.HYFL);
		duckPeople = (TextView) findViewById(R.id.text4_context);// 联系人
		duckPeople.setText(companyNameEntity.ZYKYHQ);
		duckCred = (TextView) findViewById(R.id.text5_context);// 信用等级
		duckCred.setText(companyNameEntity.ZYHYHQ);
		phone=(TextView)findViewById(R.id.text3_context);
		phone.setText(companyNameEntity.DHHM);
		area = (TextView) findViewById(R.id.area);
		area.setText(companyNameEntity.QYDZ);
		type = (TextView) findViewById(R.id.type);
		type.setText(companyNameEntity.FRDB);
		((TextView) findViewById(R.id.text6_context)).setText(companyNameEntity.ZYKYFW);
		((TextView) findViewById(R.id.text7_context)).setText(companyNameEntity.ZYHYFW);
		
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
	}
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
	

	// 获取码头证书部分详情信息
	private void GetDetialPictTask() 
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{

			@Override
			public void onSuccess(String result) 
			{
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
							imagemodel.setimgUrl("http://120.55.193.1:8080/GH/"+temp.getString("dir"));
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
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		Map<String, Object> map=new HashMap<>();  
		
	}
	@Override
	public void onSuccess(String result) 
	{
		try {
			JSONObject res = new JSONObject(result);
			JSONObject obj = res.getJSONObject("obj");
			area.setText(obj.getString("region"));
			type.setText(obj.getString("type"));
			duckJing.setText(obj.getString("business"));
			duckPeople.setText(obj.getString("linkman"));
			phone.setText(obj.getString("tel"));
			duckCred.setText(obj.getString("credit"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
