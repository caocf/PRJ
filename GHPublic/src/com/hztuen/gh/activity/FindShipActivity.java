package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.network.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener; 
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gh.modol.ShipModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.PopShipNameAdapter;
import com.hztuen.gh.activity.Adapter.ShipAdapter; 
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

/**
 * 船货圈找船
 * 
 * @author hztuen7
 *
 */
public class FindShipActivity extends Activity implements OnClickListener 
{

	private ShipAdapter shipAdapter;
	private TextView togglestart, toggleend, toggletype;
	public PopupWindow popupWindowArea;
	private View contentView;
	public static FindShipActivity instance;
	private ListView listview_start,lv_find_ship;
	public ArrayList<String> from_lists;
	private PopShipNameAdapter fromAdapter;
	private RelativeLayout relative_title_final;
	private LinearLayout liner_case;
	private Boolean isStartOpen = false;  
	private List<ShipModel> modellist = new ArrayList<ShipModel>();
	private ImageButton image_add;
	
	String startid="-1",endid="-1",typeid="-1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findship);
		init();
		instance = this;
		GetShipsListTask();
	}

	private void init() 
	{		
		shipAdapter = new ShipAdapter(getApplicationContext(), modellist); 
		togglestart = (TextView) findViewById(R.id.togglestart);

		toggleend = (TextView) findViewById(R.id.toggleend);

		toggletype = (TextView) findViewById(R.id.toggletype);
		lv_find_ship=(ListView)findViewById(R.id.lv_find_ship);
		listview_start = (ListView) findViewById(R.id.listview_start);
		from_lists = new ArrayList<String>();
		fromAdapter = new PopShipNameAdapter(this, from_lists, 6);
		liner_case = (LinearLayout) findViewById(R.id.liner_case);		
		
		image_add=(ImageButton)findViewById(R.id.image_add);
		image_add.setOnClickListener(this);

		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		switch (v.getId()) 
		{
		case R.id.togglestart:
			from_lists.clear();
			if (isStartOpen == false) 
			{
				togglestart.setTextColor(getResources().getColor(R.color.blue));
				toggleend.setTextColor(R.color.text_black_light);
				toggletype.setTextColor(R.color.text_black_light); 
				HttpRequest hr1=new HttpRequest(new HttpRequest.onResult() 
				{					
					@Override
					public void onSuccess(String result) 
					{
						try
						{
							from_lists.add("不限");
							JSONArray array=new JSONArray(result.trim());
							for(int i=0;i<array.length();i++)
							{
								JSONObject object=array.getJSONObject(i);
								from_lists.add(object.getString("portname"));								
							}
							listview_start.setAdapter(fromAdapter);
						} catch (JSONException e) 
						{ 
							e.printStackTrace();
						}
						
					}
					
					@Override
					public void onError(int httpcode) 
					{ 
						
					}
				});
				Map<String, Object> map=new HashMap<>();
				hr1.post(contants.baseUrl+"PortList", map);

				contentView = getLayoutInflater().inflate(R.layout.pop_start, null);// 港口

				listview_start = (ListView) contentView .findViewById(R.id.listview_start);
				
				listview_start.setAdapter(fromAdapter);

				LinearLayout parent6 = (LinearLayout) this .findViewById(R.id.findship_view);// 父窗口view
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT); 
				 popupWindowArea.setOutsideTouchable(true);

				listview_start.setOnItemClickListener(new OnItemClickListener() 
				{
					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) {
						if(position==0)
						{
							startid="-1";
						}else
							startid = String.valueOf(position);
						popupWindowArea.dismiss();
						togglestart.setText(from_lists.get(position));  					
						

						GetShipsListTask();
						
					}
				});
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				popupWindowArea.setTouchable(true);
				popupWindowArea.setTouchInterceptor(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						
						return false;
					}
				});

				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha =0.5f;
				getWindow().setAttributes(lp6);

				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});

				popupWindowArea.showAsDropDown(liner_case, 0, 0);
				isStartOpen = true;
			} else {
				popupWindowArea.dismiss();
				isStartOpen = false;
				from_lists.clear();

				togglestart.setTextColor(R.color.text_black_light);
				toggleend.setTextColor(R.color.text_black_light);
				toggletype.setTextColor(R.color.text_black_light); 
				
			}
			break;
		case R.id.toggleend:
			from_lists.clear();

			if (isStartOpen == false) {

				togglestart.setTextColor(R.color.text_black_light);
				toggleend.setTextColor(getResources().getColor(R.color.blue));
				toggletype.setTextColor(R.color.text_black_light); 
				
				HttpRequest hr1=new HttpRequest(new HttpRequest.onResult() 
				{
					
					@Override
					public void onSuccess(String result) 
					{
						try
						{
							from_lists.add("不限");
							JSONArray array=new JSONArray(result.trim());
							for(int i=0;i<array.length();i++)
							{
								JSONObject object=array.getJSONObject(i);
								from_lists.add(object.getString("portname"));								
							}
							listview_start.setAdapter(fromAdapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					@Override
					public void onError(int httpcode) {
						// TODO Auto-generated method stub
						
					}
				});
				Map<String, Object> map=new HashMap<>();
				hr1.post(contants.baseUrl+"PortList", map);

				contentView = getLayoutInflater().inflate(R.layout.pop_start,
						null);// 港口

				listview_start = (ListView) contentView
						.findViewById(R.id.listview_start);
				
				listview_start.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) 
					{
						if(position==0)
						{
							endid="-1";
						}else
						endid = String.valueOf(position);
						popupWindowArea.dismiss();
						toggleend.setText(from_lists.get(position)); 
						
						
						GetShipsListTask();
						
					}
				});
				listview_start.setAdapter(fromAdapter);

				LinearLayout parent6 = (LinearLayout) this
						.findViewById(R.id.findship_view);// 父窗口view
				popupWindowArea = new PopupWindow(contentView,
						parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT); 

				popupWindowArea.setTouchable(true);
				popupWindowArea.setTouchInterceptor(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				
				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						
						togglestart.setTextColor(R.color.text_black_light);
						toggleend.setTextColor(R.color.text_black_light);
						toggletype.setTextColor(R.color.text_black_light); 
					}
				});

				popupWindowArea.showAsDropDown(liner_case, 0, 0);
				isStartOpen = true;
			} else {
				popupWindowArea.dismiss();
				isStartOpen = false;
				from_lists.clear();
				togglestart.setTextColor(R.color.text_black_light);
				toggleend.setTextColor(R.color.text_black_light);
				toggletype.setTextColor(R.color.text_black_light); 
			}
			break;
		case R.id.toggletype:

			from_lists.clear();

			if (isStartOpen == false) {
				togglestart.setTextColor(R.color.text_black_light);
				toggleend.setTextColor(R.color.text_black_light);
				toggletype.setTextColor(getResources().getColor(R.color.blue)); 
				HttpRequest hr1=new HttpRequest(new HttpRequest.onResult() 
				{
					
					@Override
					public void onSuccess(String result) 
					{
						try
						{
							from_lists.add("不限");
							JSONArray array=new JSONArray(result.trim());
							for(int i=0;i<array.length();i++)
							{
								JSONObject object=array.getJSONObject(i);
								from_lists.add(object.getString("shiptype"));								
							}
							listview_start.setAdapter(fromAdapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					@Override
					public void onError(int httpcode)
					{
						
					}
				});
				Map<String, Object> map=new HashMap<>();
				hr1.post(contants.baseUrl+"ShipTypeList", map);

				contentView = getLayoutInflater().inflate(R.layout.pop_start,
						null);// 港口

				listview_start = (ListView) contentView
						.findViewById(R.id.listview_start);
				
				listview_start.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
					{
						if(position==0)
						{
							typeid="-1";
						}else
							typeid = String.valueOf(position);
						popupWindowArea.dismiss();
						toggletype.setText(from_lists.get(position)); 
						
						GetShipsListTask();
						
						togglestart.setTextColor(R.color.text_black_light);
						toggleend.setTextColor(R.color.text_black_light);
						toggletype.setTextColor(R.color.text_black_light); 
						
					}
				});
				listview_start.setAdapter(fromAdapter);

				LinearLayout parent6 = (LinearLayout) this
						.findViewById(R.id.findship_view);// 父窗口view
				popupWindowArea = new PopupWindow(contentView,
						parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

				popupWindowArea.setTouchable(true);
				popupWindowArea.setTouchInterceptor(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) 
					{
						return false;
					}
				});

				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);

				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						
						togglestart.setTextColor(R.color.text_black_light);
						toggleend.setTextColor(R.color.text_black_light);
						toggletype.setTextColor(R.color.text_black_light); 
					}
				});

				popupWindowArea.showAsDropDown(liner_case, 0, 0);
				isStartOpen = true;
			} else {
				popupWindowArea.dismiss();
				isStartOpen = false;
				from_lists.clear();
				togglestart.setTextColor(R.color.text_black_light);
				toggleend.setTextColor(R.color.text_black_light);
				toggletype.setTextColor(R.color.text_black_light); 
			}
			break;
			
			
			
		case R.id.image_add:
			

			if("0".equals(SystemStatic.usertypeId))
			{
				Toast.makeText(this, "您尚未登录，请先登录", Toast.LENGTH_LONG).show();
				break;
			}
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


				LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.findship_view);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);
				
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
	
	
	class itemClick implements OnClickListener{

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

			default:
				break;
			}
			
		}
		
	}
	
	
	// 获取找船列表
		private void GetShipsListTask() 
		{
			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			 aa.add("FromID="+startid);
			 aa.add("ToID="+endid); 
			 aa.add("GoodsTypeid=" +typeid); 
			aa.add("TradetypeID=" +2); 		
			aa.add("ShipTypeid=" +"-1"); 

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.baseUrl+"TradeListByTerms";
			if (!toUrl.equals("")) {
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
								shipAdapter.notifyDataSetChanged();
								shipAdapter.notifyDataSetInvalidated();
								
							} else 
							{
								modellist.clear();
								for (int i = 0; i < data.length(); i++) 
								{
									JSONObject temp = data.getJSONObject(i);
									ShipModel model = new ShipModel();

									model.setid(temp.getString("id"));
									model.settitle(temp.getString("titile"));  
									model.settons(temp.getString("tons")); 
									JSONObject port1=temp .getJSONObject("startport");
									JSONObject port2=temp .getJSONObject("unloadport");
									model.setemptydock(port1.getString("portname"));								
									model.settargetdock(port2.getString("portname"));
									model.setprice(temp.getString("price"));
									model.setlinkman(temp.getString("linkman"));
									model.settel(temp.getString("tel"));
									model.setremark(temp.getString("remark"));
									model.setpostdate(temp.getString("postdate"));
									
									JSONObject shiptype=temp .getJSONObject("tradeShiptypeEN");
									model.setshiptype(shiptype.getString("shiptype"));
									
									model.setshipname(temp.getString("shipname"));
									String rr=temp.getString("route");
									model.setroute(temp.getString("route"));
									

									modellist.add(model);

								}

								lv_find_ship.setAdapter(shipAdapter);
							}
							
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
		
		
}
