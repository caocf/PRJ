package com.hztuen.gh.activity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.utils.KJLoger;

import android.app.Activity;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.gh.modol.PassInfoPop;
import com.gh.modol.PassInfoShipMode;
import com.ghpublic.entity.PassInfoEntity;
import com.hxkg.ghpublic.R;
import com.hxkg.mainfragment.NewsComment;
import com.hztuen.gh.activity.Adapter.PassInfoPopAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.ParseUtil;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/**
 * @author zzq
 * @DateTime 2016年7月18日 下午4:34:36
 * 过闸信息
 */
public class PassInfoActivity extends AbActivity{
	private final static String TAG = PassInfoActivity.class.getSimpleName();
	private LinearLayout parent;
	private TextView shipnameTitle;//第一个船名
	private TextView shipnameTitle_sec;
	private LinearLayout title_ship_fir;//title 
	private LinearLayout title_ship_sec;//titl;
	private LinearLayout passinfo_first;//主体布局
	private LinearLayout passinfo_second;//主体布局
	private LinearLayout parent_view;
	private ImageView ref;//刷新
	private ImageView ref_sec;//刷新
	private TextView back;//返回
	private TextView back_second;
	private TextView tm;
	private List<PassInfoShipMode> shipList;
	private List<PassInfoPop> pip;
	/**
	 * 过闸信息
	 * */
	private String Shipname;
	private TextView pass_order;
	private TextView shipname;
	private TextView level1;
	private TextView level2;
	private TextView shipnum;

	private PopupWindow popship = null;//弹出船舶列表
	private PopupWindow pb = null;//刷新效果
	private PassInfoPopAdapter adapter;
	private boolean flag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_passinfo);
		initView();//初始化界面布局
		init();
		//		refView();//更新界面
	}
	public void init(){
		AbDialogUtil.showProgressDialog(PassInfoActivity.this, 0,
				"加载中...");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				initData();//加载数据
			}
		}, 1000);
	}
	/**
	 * 初始化界面布局
	 */
	public void initView(){
		parent_view = (LinearLayout) findViewById(R.id.parent_view);

		shipnameTitle = (TextView) findViewById(R.id.shipnameTitle);
		shipnameTitle_sec = (TextView) findViewById(R.id.shipnameTitle_sec);

		title_ship_fir = (LinearLayout) findViewById(R.id.title_ship_fir);
		title_ship_sec = (LinearLayout) findViewById(R.id.title_ship_sec);

		passinfo_first = (LinearLayout) findViewById(R.id.passinfo_first);
		passinfo_second = (LinearLayout) findViewById(R.id.passinfo_second);

		pass_order = (TextView) findViewById(R.id.pass_order);
		shipname = (TextView) findViewById(R.id.shipname);
		level1 = (TextView) findViewById(R.id.level1);
		level2 = (TextView) findViewById(R.id.level2);
		shipnum = (TextView) findViewById(R.id.shipnum);
		ref = (ImageView) findViewById(R.id.ref);
		ref_sec = (ImageView) findViewById(R.id.ref_second);
		back = (TextView) findViewById(R.id.back);
		back_second = (TextView) findViewById(R.id.back_second);
		back.setOnClickListener(onclick);
		back_second.setOnClickListener(onclick);
		ref.setOnClickListener(onclick);
		ref_sec.setOnClickListener(onclick);
		title_ship_fir.setOnClickListener(onclick);
		title_ship_sec.setOnClickListener(onclick);
		parent_view.setOnClickListener(onclick);
		parent = (LinearLayout) findViewById(R.id.parent);

		tm = (TextView) findViewById(R.id.tm);
	}
	public OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.parent_view:
				if(popship==null){
					//不处理
				}else{
					popship.dismiss();
					popship=null;
				}
				break;
			case R.id.back_second:
				finish();
				break;
			case R.id.back:
				finish();
				break;
			case R.id.ref:
				try {
					initProgress();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case R.id.ref_second:
				try {
					initProgress();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				break;
			case R.id.title_ship_fir:
				if(popship==null){
					initPopupWindow();
				}else{
					popship.dismiss();
					popship=null;
				}

				break;
			case R.id.title_ship_sec:
				if(popship==null){
					initPopupWindow();
				}else{
					popship.dismiss();
					popship=null;
				}
				break;
			default:
				break;
			}
		}
	};
	/**
	 * 加载数据
	 */
	public void initData(){
		shipList = new ArrayList<PassInfoShipMode>();
		//访问网络
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Username="+SystemStatic.userName);
		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.myshiplist;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kj.post(toUrl, params, false, new HttpCallBack(){

				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);

					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try{
						JSONObject res = new JSONObject(result);
						JSONArray shipArray = res.getJSONArray("data");
						for(int i = 0;i<shipArray.length();i++){
							JSONObject ship = shipArray.getJSONObject(i);
							PassInfoShipMode pism = new PassInfoShipMode();
							pism.setShipname(ship.getString("shipname"));
							shipList.add(pism);
						}
						if(shipArray.length()<=0){
							passinfo_first.setVisibility(View.GONE);
							passinfo_second.setVisibility(View.VISIBLE);
							shipnameTitle.setText("暂无船舶");
							shipnameTitle_sec.setText("暂无船舶");
							Toast.makeText(getApplicationContext(), "暂无船舶信息，您可以在我的里面添加您的船舶信息哟。", Toast.LENGTH_SHORT).show();
						}else{
							Shipname = shipList.get(0).getShipname().toString();
							refView();
						}
					}catch(Exception e){
						e.printStackTrace();
						AbDialogUtil.removeDialog(PassInfoActivity.this);
					}
				}
			});
		}//else这里结束
	}
	public void refView(){
		shipnameTitle.setText(shipList.get(0).getShipname());
		shipnameTitle_sec.setText(shipList.get(0).getShipname());
		passInfo();
	}

	/**
	 * 过闸信息
	 */
	public void passInfo(){
		//访问网络
		KJHttp kj = new KJHttp();
		List<String> aa = new ArrayList<String>();

		aa.add("Shipname="+Shipname);

		//访问地址
		org.kymjs.kjframe.http.HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.passinfo;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kj.post(toUrl, params, false, new HttpCallBack(){

				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					// TODO Auto-generated method stub
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try{
						JSONObject res = new JSONObject(result);
						PassInfoEntity pie = new PassInfoEntity();
						pie = ParseUtil.parseDataToEntity(res, PassInfoEntity.class);
						if(pie.getObj() == null){
							passinfo_first.setVisibility(View.GONE);
							passinfo_second.setVisibility(View.VISIBLE);
							flag = false;
						}else{
							passinfo_second.setVisibility(View.GONE);
							passinfo_first.setVisibility(View.VISIBLE);
							pass_order.setText(pie.getObj().getOrder());
							shipname.setText(Shipname);
							level1.setText(pie.getObj().getLevel1()+"米");
							level2.setText(pie.getObj().getLevel2()+"米");
							shipnum.setText(pie.getObj().getShipnum());

							//							Date now = new Date(); 
							//							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格
							//							String tt = dateFormat.format( now ); 
							Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
							int hour = c.get(Calendar.HOUR_OF_DAY); 
							//							int minute = c.get(Calendar.MINUTE); 
							tm.setText("水位信息采集与"+hour+" : 00");
							flag = true;
						}
						if(pb==null){
							//不处理
						}else{
							pb.dismiss();
							pb = null;
						}
						AbDialogUtil.removeDialog(PassInfoActivity.this);
					}catch(Exception e){
						e.printStackTrace();
						AbDialogUtil.removeDialog(PassInfoActivity.this);
					}
				}
			});
		}//else这里结束
	}
	public void initPopupWindow(){
		View contentView = getLayoutInflater().inflate(R.layout.pop_passinfo_ship_list, null);//初始化弹出界面
		/*
		 *弹出界面的布局文件的监听
		 */
		pip = new ArrayList<PassInfoPop>();
		adapter = new PassInfoPopAdapter(getApplicationContext(), pip);
		ListView listview = (ListView) contentView.findViewById(R.id.listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (parent.getId()) {
				case R.id.listview:
					expressItemClick(position);//position 代表你点的哪一个
					break;
				default:
					break;
				}
			}
			private void expressItemClick(int position) {
				// TODO Auto-generated method stub
				Shipname = pip.get(position).getName();
				popship.dismiss();
				popship=null;
				shipnameTitle.setText(Shipname);
				shipnameTitle_sec.setText(Shipname);
				
				AbDialogUtil.showProgressDialog(PassInfoActivity.this, 0, "加载中...");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						passInfo();//
					}
				}, 1000);
				
			}
		});
		listview.setAdapter(adapter);

		for(int i = 0;i<shipList.size();i++){
			PassInfoPop pop = new PassInfoPop();
			pop.setName(shipList.get(i).getShipname());
			pip.add(pop);
		}
		adapter.notifyDataSetChanged();
		//				LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent);//父窗口view  
		popship = new PopupWindow(contentView, title_ship_fir.getWidth()+20, ViewGroup.LayoutParams.WRAP_CONTENT);//设置0弹出宽 高
		popship.setFocusable(false);

		popship.setOutsideTouchable(true); //设置父布局是否可以触摸
		/*
		 *父布局变灰色透明
		 **/
		//		popship.showAtLocation(parent, Gravity.CENTER, 0, 0);
		if(flag){
			popship.showAsDropDown(title_ship_fir, 0, 0);   //这个可以让popupwindow显示在某某布局之下
		}else{
			popship.showAsDropDown(title_ship_sec, 0, 0);   //这个可以让popupwindow显示在某某布局之下
		}

	}


	@SuppressWarnings("static-access")
	public void initProgress() throws InterruptedException{
		View contentView = getLayoutInflater().inflate(R.layout.passinfoprogress, null);//初始化弹出界面

		ProgressBar progressBar1 = (ProgressBar) contentView.findViewById(R.id.progressBar1);
		pb = new PopupWindow(contentView, parent_view.getWidth(), parent_view.getHeight()+100);//设置0弹出宽 高
		pb.setFocusable(true);
		pb.setOutsideTouchable(false); //设置父布局是否可以触摸
		pb.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				//				progressBar1.dismiss();
			}
		});

		pb.showAtLocation(parent_view, Gravity.CENTER, 0, 0);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.currentThread().sleep(2000);//毫秒  
					passInfo();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}
}
