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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
//import com.ab.task.AbTaskPool;
import com.ab.util.AbDialogUtil;
import com.gh.modol.MineMaTouItemStateModel;
import com.gh.modol.MineMatouMode;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.MineMaTouAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/**
 * @author zzq
 * @DateTime 2016年7月13日 上午9:43:38
 * 我的   码头版
 */
public class MineMaTouActivity extends AbActivity implements OnClickListener{
	private RelativeLayout relative_title_final;
	private List<MineMatouMode> modellist = new ArrayList<MineMatouMode>();
	private List<MineMaTouItemStateModel> modelliststate = new ArrayList<MineMaTouItemStateModel>();
	private MineMaTouAdapter myShipAdapter;

	private ListView myShipList;
	private Button btn_add;
	private ImageButton btn_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine_matou);
		init();
	}
	public void init(){
		myShipList = (ListView) findViewById(R.id.my_ship_list);

		myShipAdapter = new MineMaTouAdapter(getApplicationContext(),
				modellist, modelliststate);  
		myShipList.setAdapter(myShipAdapter);
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		btn_setting = (ImageButton) findViewById(R.id.btn_setting);
		btn_setting.setOnClickListener(this);

		btn_add = (Button) findViewById(R.id.btn_add);
		btn_add.setOnClickListener(this);
		AbDialogUtil.showProgressDialog(MineMaTouActivity.this, 0, "加载中，请稍后...");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GetMyShipListTask();
			}
		}, 0);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		// 点击返回
		case R.id.relative_title_final:
			finish();
			break;
			// 点击添加按钮
		case R.id.btn_add:
			Intent intent = new Intent(getApplicationContext(),
					MineAddShipActivity.class);
			/**
			 * 在此传递参数给下个界面  用以判定到底是那个  我的模块的添加
			 * 用来减少界面等
			 * */
			intent.putExtra("usertype", "wharf");
			startActivity(intent);
			break;

			// 点击设置按钮
		case R.id.btn_setting:
			Intent intent_setting = new Intent(getApplicationContext(),
					MineSettingActivity.class);
			startActivity(intent_setting);
			break;
		default:
			break;
		}
	}
	// 获取我的船舶列表
	private void GetMyShipListTask() {
		// TODO Auto-generated method stub
		// 访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Username=" + SystemStatic.userName);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.mywharflist;
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
						JSONObject resultJO = new JSONObject(result);
						// String resultMsg = resultJO.getString("resultMsg");
						// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
						JSONObject res = new JSONObject(result);
						final JSONArray data = res.getJSONArray("data");
						Log.i("GH_TEXT", data.length() + "我是数据的size");
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "",
									Toast.LENGTH_SHORT).show();
						} else {
							for (int i = 0; i < data.length(); i++) {
								JSONObject temp = data.getJSONObject(i);
								MineMatouMode model = new MineMatouMode();
								model.setWharfid(temp.getString("wharfid"));
								model.setWharfname(temp.getString("wharfname"));
								model.setWharfnum(temp.getString("wharfnum"));
								model.setOperations(temp.getString("operations"));
								model.setArea(temp.getString("area"));
								model.setCity(temp.getString("city"));
								modellist.add(model);
								myShipAdapter.notifyDataSetChanged();
								
								getItemStateTask(modellist.get(i).getWharfname(),data.length());
							}
//							AbTaskPool mAbTaskPool = AbTaskPool.getInstance();
//							//							mAbTaskPool.
//							//定义异步执行的对象
//
//							for(int i =0;i<modellist.size();i++){
//								final int j=i;
//								final AbTaskItem item = new AbTaskItem();
//								item.setListener(new AbTaskListener() {
//									@Override
//									public void get() {
//										try {
//											//下面写要执行的代码，如下载数据
											
//										} catch (Exception e) {
//											System.out.println("22222222222222222222222222222222222222");
//											System.out.println(e.getMessage());
//											AbDialogUtil.removeDialog(MineMaTouActivity.this);
//										}
//									};
//								});
//								//开始执行
//								mAbTaskPool.execute(item);
//							}



						}

					} catch (Exception e1) {
						e1.printStackTrace();
						AbDialogUtil.removeDialog(MineMaTouActivity.this);
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
					AbDialogUtil.removeDialog(MineMaTouActivity.this);
				}
			});
		}
	}


	/**
	 * @param shipname
	 * @param size
	 * 采用线程池的方法可能会出现对应不上的问题
	 * 后续处理
	 */
	private void getItemStateTask(String shipname,final int size) {

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Wharfname=" + shipname);

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.wharfoperations;
		if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					// Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					result=result.replace("\\",""); 

					try {
						JSONObject res;
						try {
							res = new JSONObject(result);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							//
							result = result.substring(1, result.length()-1);
							res = new JSONObject(result);
						}

						MineMaTouItemStateModel msism = new MineMaTouItemStateModel();
						msism.setZuoye(res.getString("operations"));
						msism.setZhengshu(res.getString("cert"));
						modelliststate.add(msism);
						if(modelliststate.size()==size)
						{
							myShipList.setAdapter(myShipAdapter);
						}
						AbDialogUtil.removeDialog(MineMaTouActivity.this);
					} catch (Exception e1) {
						e1.printStackTrace();
						AbDialogUtil.removeDialog(MineMaTouActivity.this);
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
					AbDialogUtil.removeDialog(MineMaTouActivity.this);
				}
			});
		}
	}
}
