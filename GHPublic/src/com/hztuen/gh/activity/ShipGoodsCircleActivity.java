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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil;
import com.gh.modol.ShipCircleListModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.ShipGoodsCircleListAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;
/**
 * 船货圈首页
 * @author hztuen7
 *
 */
public class ShipGoodsCircleActivity extends AbActivity implements OnClickListener
{
	private ListView lv_ship_circle;
	private ShipGoodsCircleListAdapter circleListAdapter;
	private List<ShipCircleListModel> modellist = new ArrayList<ShipCircleListModel>();
	private RelativeLayout relative_title_final;
	private LinearLayout liner_find_goods,liner_find_ship,liner_ship_rent,liner_ship_buy;
	private ImageButton image_add;
	public PopupWindow popupWindowArea;
	private View contentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ship_goods_circle);
		AbDialogUtil.showProgressDialog(ShipGoodsCircleActivity.this, 0,
				"数据加载中...");
		init();
	}
	
	public void onResume()
	{
		super.onResume();
		GetLatestTask();
	}
	public void init()
	{
		circleListAdapter = new ShipGoodsCircleListAdapter(getApplicationContext(), modellist);
		
		
		lv_ship_circle=(ListView)findViewById(R.id.lv_ship_circle); 
		
		View headerView = getLayoutInflater().inflate( 
		        R.layout.activity_ship_goods_circle_header, null); 
		lv_ship_circle.addHeaderView(headerView,null,false); 
		lv_ship_circle.setAdapter(circleListAdapter);
		
		liner_find_goods=(LinearLayout)headerView.findViewById(R.id.liner_find_goods);
		liner_find_ship=(LinearLayout)headerView.findViewById(R.id.liner_find_ship);
		liner_ship_rent=(LinearLayout)headerView.findViewById(R.id.liner_ship_rent);
		liner_ship_buy=(LinearLayout)headerView.findViewById(R.id.liner_ship_buy);
		image_add=(ImageButton)findViewById(R.id.image_add);
		image_add.setOnClickListener(this);
		liner_find_goods.setOnClickListener(this);
		liner_find_ship.setOnClickListener(this);
		liner_ship_rent.setOnClickListener(this);
		liner_ship_buy.setOnClickListener(this);
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
	
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		//点击找货
		case R.id.liner_find_goods:
			Intent intent_find_goods=new Intent(getApplicationContext(),FindGoodsActivity.class);
			startActivity(intent_find_goods);
		break;
		//点击租船;
		case R.id.liner_ship_rent:
			Intent intent_shop_rent=new Intent(getApplicationContext(),ShipRentActivity.class);
			startActivity(intent_shop_rent);
			break;
		//点击售船	
		case R.id.liner_ship_buy:
			Intent intent_shop_bought=new Intent(getApplicationContext(),ShipBoughtActivity.class);
			startActivity(intent_shop_bought);
			break;
		
		//点击找船
		case R.id.liner_find_ship:
			Intent intent_find_ship=new Intent(getApplicationContext(),FindShipActivity.class);
			startActivity(intent_find_ship);
			break;
			
		//点击添加按钮
		case R.id.image_add:
			
			if("0".equals(SystemStatic.usertypeId))//游客跳到登录
			{
				Intent intent=new   Intent(this,LoginActivity.class);
				startActivity(intent);
				return;
			}
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_ship_circle_add, null);
			 TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
			 TextView send_goods_msg = (TextView) contentView.findViewById(R.id.send_goods_msg);
			 TextView send_ship_msg = (TextView) contentView.findViewById(R.id.send_ship_msg);
			 TextView send_rent_msg = (TextView) contentView.findViewById(R.id.send_rent_msg);
			 TextView send_bought_msg = (TextView) contentView.findViewById(R.id.send_bought_msg);
			 TextView my_send_record = (TextView) contentView.findViewById(R.id.my_send_record);
			 pop_dis6.setOnClickListener(new itemClick());
			 send_goods_msg.setOnClickListener(new itemClick());
			 send_ship_msg.setOnClickListener(new itemClick());
			 send_rent_msg.setOnClickListener(new itemClick());
			 send_bought_msg.setOnClickListener(new itemClick());
			 my_send_record.setOnClickListener(new itemClick());


			LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.father_ship_circle);//父窗口view  
			popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			WindowManager.LayoutParams lp6 = getWindow().getAttributes();
			lp6.alpha = 0.5f;
			getWindow().setAttributes(lp6);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			popupWindowArea.setOnDismissListener(new OnDismissListener()
			{

				@Override
				public void onDismiss() 
				{
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
				//点击我的发布记录
			case R.id.my_send_record:
				Intent intent_send_record=new Intent(getApplicationContext(),MySendRecordActivity.class);
				startActivity(intent_send_record);
				popupWindowArea.dismiss();	
				break;
			default:
				break;
			}
			
		}
		
	}

	// 获取最新发布列表
	private void GetLatestTask() 
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		 aa.add(" ="+" ");

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.latest;
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
					try 
					{ 
						JSONArray data = new JSONArray(result); 
						if (data.length() == 0) 
						{
							Toast.makeText(getApplicationContext(), "最近无发布信息",
									Toast.LENGTH_SHORT).show();
							modellist.clear();
							circleListAdapter.notifyDataSetChanged();
							circleListAdapter.notifyDataSetInvalidated();
							AbDialogUtil.removeDialog(ShipGoodsCircleActivity.this);
							
						} else 
						{
							modellist.clear();
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
									model.to=end.getString("portname");
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

								modellist.add(model);

							}
							AbDialogUtil.removeDialog(ShipGoodsCircleActivity.this);
							circleListAdapter.notifyDataSetChanged();
						} 
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) 
				{
					AbDialogUtil.removeDialog(ShipGoodsCircleActivity.this);
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
	
	

}
