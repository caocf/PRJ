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
import android.os.Bundle; 
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.gh.modol.MySendRecordModel;
import com.gh.modol.ShipCircleListModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.MySendRecordAdapter; 
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/**
 * 我的发布记录界面
 * 
 * @author hztuen7
 *
 */
public class MySendRecordActivity extends Activity implements OnClickListener {

	//	private ListView lv_record;
	private List<ShipCircleListModel> recordlist = new ArrayList<ShipCircleListModel>();
	private ImageView permission_list_back;
	private RelativeLayout relative_title;
	private MySendRecordAdapter recordAdapter;

	private ImageButton image_add;
	private PullToRefreshListView lv_record;
	private GridView mGridView;

	private int pagenumber=1;//当前页
	private int totolrows = 0;//数据总量
	private int pageSize = 10;//每页默认条数
	
	public PopupWindow popupWindowArea;
	private View contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_send_record);
		init();
		MySendRecord();
	}

	void init() {
		//	lv_record = (ListView) findViewById(R.id.listview_record);

		lv_record = (PullToRefreshListView)findViewById(R.id.listview_record);
		//mGridView = lv_record.getRefreshableView();

		recordAdapter = new MySendRecordAdapter(MySendRecordActivity.this,
				recordlist);

		permission_list_back = (ImageView) findViewById(R.id.permission_list_back);
		permission_list_back.setOnClickListener(this);

		relative_title = (RelativeLayout) findViewById(R.id.relative_title);
		relative_title.setOnClickListener(this);
		
		image_add=(ImageButton)findViewById(R.id.image_add);
		image_add.setOnClickListener(this);

		lv_record.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				recordlist.clear();	
				pagenumber=1;
				MySendRecord();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				pagenumber++;
				if((pagenumber-1)*pageSize >= totolrows){
					Toast.makeText(getApplicationContext(), "已加载完全部数据", Toast.LENGTH_SHORT).show();
					lv_record.postDelayed(new Runnable() {
						@Override
						public void run() {
							lv_record.onRefreshComplete();
						}
					}, 1000);
				}else {
					MySendRecord();
				}

			}

		});
	}

	private void MySendRecord() 
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("UserID=" + SystemStatic.usertypeId);

		HttpParams params = null;
		try 
		{
			params = Util.prepareKJparams(aa);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.mypost;
		if (params == null) 
		{
			Util.getTip(getApplicationContext(), "构造参数失败");
		} else if (!toUrl.equals("")) 
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
					try 
					{
						lv_record.onRefreshComplete(); 

						JSONArray data = new JSONArray(result);  

						for (int i = 0; i < data.length(); i++) 
						{
							JSONObject temp = data.getJSONObject(i);
							
							
							
							ShipCircleListModel model = new ShipCircleListModel();
							JSONObject typeJsonObject=temp.getJSONObject("tradeTypeEN");
							model.settradetype(typeJsonObject.getString("id"));
							model.typenameString=typeJsonObject.getString("type");
							
							model.remark=temp.getString("remark");
							model.tel=temp.getString("tel");								
							model.setid(temp.getString("id"));
							model.settitle(temp.getString("titile"));
							model.setcontent(temp.getString("linkman"));
							model.setprice(temp.getString("price"));								
							model.setpostime(temp.getString("postdate")); 
							if("1".equals(model.gettradetype())||"2".equals(model.gettradetype()))
							{
								JSONObject start=temp.getJSONObject("startport");
								JSONObject end=temp.getJSONObject("unloadport");
								model.fromString=start.getString("portname");
								model.fromid=start.getString("id");
								model.to=end.getString("portname");
								model.toid=end.getString("id");
							}							
							if("1".equals(model.gettradetype()))
							{
								JSONObject goods=temp.getJSONObject("type");
								model.goodstypeid=goods.getString("id");
								model.goodstypename=goods.getString("unitname");
							} 
							else 
							{
								JSONObject ship=temp.getJSONObject("tradeShiptypeEN");
								model.shiptypeid=ship.getString("id");
								model.shiptypename=ship.getString("shiptype");
							}
							
							model.goodsname=temp.getString("name");
							model.tons=temp.getString("tons");
							model.packaging=temp.getString("packaging");
							
							
							
							model.shipname=temp.getString("shipname");
							model.load=temp.getString("load");
							model.route=temp.getString("route");
							model.age=temp.getString("age");

							recordlist.add(model);

						}
						lv_record.setAdapter(recordAdapter);
					} catch (Exception e1) {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.permission_list_back:
			finish();
			break;

		case R.id.relative_title:
			finish();
			break;
		case R.id.image_add:
			 contentView = getLayoutInflater().inflate(R.layout.pop_ship_circle_add, null);
			 TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
			 TextView send_goods_msg = (TextView) contentView.findViewById(R.id.send_goods_msg);
			 TextView send_ship_msg = (TextView) contentView.findViewById(R.id.send_ship_msg);
			 TextView send_rent_msg = (TextView) contentView.findViewById(R.id.send_rent_msg);
			 TextView send_bought_msg = (TextView) contentView.findViewById(R.id.send_bought_msg);
			 TextView my_send_record = (TextView) contentView.findViewById(R.id.my_send_record);
			 
			 
			 TextView textView10=(TextView)contentView.findViewById(R.id.textView10);
			 textView10.setVisibility(View.GONE);
			 my_send_record.setVisibility(View.GONE);
			 pop_dis6.setOnClickListener(new itemClick());
			 send_goods_msg.setOnClickListener(new itemClick());
			 send_ship_msg.setOnClickListener(new itemClick());
			 send_rent_msg.setOnClickListener(new itemClick());
			 send_bought_msg.setOnClickListener(new itemClick()); 

			LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.father_send_record);//父窗口view  
			popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			WindowManager.LayoutParams lp6 = getWindow().getAttributes();
			lp6.alpha = 0.5f;
			getWindow().setAttributes(lp6);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			popupWindowArea.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
				}
			});
			
			popupWindowArea.showAtLocation(parent6, Gravity.TOP, 0, parent6.getHeight()*1/8);
			break;

		}

	}
	
	class itemClick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//点击取消按钮
			case R.id.pop_dis:
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				popupWindowArea.dismiss();									
				break;
			//点击船舶出租信息
			case R.id.send_rent_msg:
				Intent intent_ship_rent_msg=new Intent(getApplicationContext(),SendRentMsgActivity.class);
				startActivity(intent_ship_rent_msg);
				popupWindowArea.dismiss();	
				break;
				
				//点击发布货源信息
			case R.id.send_goods_msg:
				Intent intent_good_res_msg=new Intent(getApplicationContext(),SendGoodsMsgActivity.class);
				startActivity(intent_good_res_msg);
				popupWindowArea.dismiss();	
				break;
				
				//点击发布船源信息
			case R.id.send_ship_msg:
				Intent intent_ship_res_msg=new Intent(getApplicationContext(),SendShipResMsgActivity.class);
				startActivity(intent_ship_res_msg);
				popupWindowArea.dismiss();	
				break;
				
				//点击发布船舶出售信息
			case R.id.send_bought_msg:
				Intent intent_ship_sell_msg=new Intent(getApplicationContext(),SendShipSellActivity.class);
				startActivity(intent_ship_sell_msg);
				popupWindowArea.dismiss();	
				break;
//				//点击我的发布记录
//			case R.id.my_send_record:
//				Intent intent_send_record=new Intent(getApplicationContext(),MySendRecordActivity.class);
//				startActivity(intent_send_record);
//				popupWindowArea.dismiss();	
//				break;
			default:
				break;
			}
			
		}
		
	}


}
