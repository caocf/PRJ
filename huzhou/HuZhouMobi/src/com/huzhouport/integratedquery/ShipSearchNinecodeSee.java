package com.huzhouport.integratedquery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.huzhouport.R;
import com.huzhouport.common.util.GetBitmap;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class ShipSearchNinecodeSee extends Activity {
	private String shipnum,shipname,type,picname,picpath;
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private Query query = new Query();
	private int msg;
	private String searchType;
	private User user;
	private Gallery  gv2;
	private List<Map<String, Object>> lsmap=new ArrayList<Map<String,Object>>();
	private Handler handler = new Handler(new Handler.Callback() {
		// 当发出信息时就执行Handler
		public boolean handleMessage(Message msg) {
			if (msg.what == 1) {
				Toast.makeText(ShipSearchNinecodeSee.this, "不存在该船名", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 2) {
				Toast.makeText(ShipSearchNinecodeSee.this, "不存在该船名或该船没有违章信息",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(ShipSearchNinecodeSee.this, "该接口还未开通", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 4) {
				Toast.makeText(ShipSearchNinecodeSee.this, "无法连接服务端", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == 5) {
				Toast.makeText(ShipSearchNinecodeSee.this, "不存在该航运企业", Toast.LENGTH_SHORT)
						.show();
			}
			return false;
		}
	});
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_ninecode_see);
		Intent intent = getIntent();
		shipnum = intent.getStringExtra("shipnum");
		shipname= intent.getStringExtra("shipname");
		type= intent.getStringExtra("type");
		searchType= intent.getStringExtra("searchType");
		user=(User)intent.getSerializableExtra("User");
		TextView tv_title = (TextView) findViewById(R.id.basesourch_title);
		tv_title.setText("船舶九位码");
		ImageButton back = (ImageButton) findViewById(R.id.basesourch_back);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		TextView num=(TextView) findViewById(R.id.search_ninecode_add_num);
		TextView name=(TextView) findViewById(R.id.search_ninecode_add_name);
		num.setText("船舶九位码： "+intent.getStringExtra("shipnum"));
	    name.setText("船名号：        "+intent.getStringExtra("shipname"));
		Button see= (Button) findViewById(R.id.search_ninecode_see);
		see.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new Thread() {
					public void run() {
							Search(1);
						handler.sendEmptyMessage(msg);
					}
				}.start();
			}
		});
		Button update= (Button) findViewById(R.id.search_ninecode_update);
		update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent =new Intent(ShipSearchNinecodeSee.this,ShipSearchNinecodeAdd.class);
				intent.putExtra("shipname", shipname);
				startActivityForResult(intent, 125);
			}
		});
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.search_ninecode_see_update_rl);
		RelativeLayout rl1=(RelativeLayout) findViewById(R.id.search_ninecode_see_rl);
		TextView num1=(TextView) findViewById(R.id.search_ninecode_add_num1);
		TextView name1=(TextView) findViewById(R.id.search_ninecode_add_name1);
		gv2= (Gallery) findViewById(R.id.search_ninecode_add_gallery);
		if(type.equals("4")){
			rl.setVisibility(View.VISIBLE);
			picname=intent.getStringExtra("picname");
			picpath=intent.getStringExtra("picpath");
			num1.setText("船舶九位码： "+intent.getStringExtra("shipnum1"));
			name1.setText("船名号：        "+intent.getStringExtra("shipname1"));
			if(!picpath.equals("null")){
			GetBitmapList oGetBitmap = new GetBitmapList(this);
			oGetBitmap.execute();
			}
		}
		if(type.equals("3")){
			rl.setVisibility(View.VISIBLE);
			rl1.setVisibility(View.GONE);
			see.setVisibility(View.GONE);
			shipname=intent.getStringExtra("shipname1");
			picname=intent.getStringExtra("picname");
			picpath=intent.getStringExtra("picpath");
			num1.setText("船舶九位码： "+intent.getStringExtra("shipnum1"));
			name1.setText("船名号：        "+intent.getStringExtra("shipname1"));
			if(!picpath.equals("null")){
			GetBitmapList oGetBitmap = new GetBitmapList(this);
			oGetBitmap.execute();
			}
		}
	}
	private void Search(int iMethod) {
		paramsDate.put("cbname", shipname);
		paramsDate.put("method", iMethod);
		paramsDate.put("scape", 1);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
		try {
			String result = hfu.post(actionUrl, paramsDate);
			
			System.out.println("result===="+result);
				if (query.CheckShipResult(result)) {
					Intent intent;
					intent = new Intent(ShipSearchNinecodeSee.this, Search_tabs.class);
					intent.putExtra("title", shipname);
					intent.putExtra("shipnum", shipnum);
					intent.putExtra("searchType", searchType);
					intent.putExtra("result", result);
					intent.putExtra("User", user);
					startActivity(intent);
					msg = 0;
				} else {
					msg = 1;
				}
		} catch (IOException e) {
			msg = 4;
			e.printStackTrace();
		} catch (Exception e) {
			msg = 4;
			e.printStackTrace();
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// 拍照
					if (resultCode == 100)
					{
					   finish();
					}
					
	}
	class GetBitmapList extends AsyncTask<Void, Void, Void>
	{
		ProgressDialog pDialog = null;

		
		public GetBitmapList(Context context)
		{
			// pDialog = ProgressDialog.show(context, "图片", "图片加载中，请稍候···",
			// true);
			/*pDialog = new WaitingDialog().createDefaultProgressDialog(context, this,"图片加载中，请稍候···");
			pDialog.show();*/
		}

		@Override
		protected Void doInBackground(Void... params)
		{
				Bitmap pic = GetBitmap.GetThumbnailUtils(picpath);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemText", picname);
				map.put("itemPath", pic);
				lsmap.add(map);
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			MyAdapter ma = new MyAdapter(ShipSearchNinecodeSee.this, lsmap);
			gv2.setAdapter(ma);
			gv2.setOnItemClickListener(new gridViewOnClickListener2());
			int nm = 0;
			if (1 > 1)
				nm =1 / 2;
			gv2.setSelection(nm);
			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}
	}
	class gridViewOnClickListener2 implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{

			final int N = arg2;
			new AlertDialog.Builder(ShipSearchNinecodeSee.this)
					.setTitle("查看图片")
					.setMessage("查看" + picname + "?")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener()
							{

								public void onClick(DialogInterface dialog,
										int which)
								{
									String urlstr = HttpUtil.BASE_URL
											+ GlobalVar.FILE_SERVER_PATH
											+ picpath;
									new Download().execute(urlstr, picpath);
								}
							}).setNegativeButton("否", null).show();

		}
	}
	class Download extends AsyncTask<String, Void, Integer>
	{

		String imageURL;

		@Override
		protected Integer doInBackground(String... params)
		{
			imageURL = GlobalVar.FILE_DOWNLOAD_PATH + params[1];
			return query.download(params[0], GlobalVar.FILE_DOWNLOAD_PATH,
					params[1]);
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			if(result==0 || result ==1)
				ShowImage.ShowImageBySystemApp(imageURL, ShipSearchNinecodeSee.this);
				if (result == -1)
					Toast.makeText(ShipSearchNinecodeSee.this, "下载失败",
							Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

	}
}
