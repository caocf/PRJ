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
import com.gh.modol.GoodsMode; 
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.GoodsAdapter;
import com.hztuen.gh.activity.Adapter.PopShipNameAdapter; 
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView; 
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 船货圈找货
 * 
 * @author hztuen7
 *
 */
public class FindGoodsActivity extends Activity implements OnClickListener 
{

	private GoodsAdapter goodsAdapter; 
	public PopupWindow popupWindowArea;
	private View contentView; 
	private PopShipNameAdapter fromAdapter;
	private ListView listview_start, lv_find_goods;
	public ArrayList<String> from_lists;

	private RelativeLayout relative_title_final;
	private LinearLayout liner_case;
	private Boolean isStartOpen = false;  
	private List<GoodsMode> modellist = new ArrayList<GoodsMode>();
	private ImageButton image_add;
	
	String startid="-1",endid="-1",typeid="-1";
	
	TextView togglestart,toggleend,toggletype;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findgoods);
		init(); 
		GetGoodsListTask();
	}

	private void init() 
	{
		goodsAdapter = new GoodsAdapter(getApplicationContext(), modellist);
		
		togglestart = (TextView) findViewById(R.id.togglestart);
		toggleend = (TextView) findViewById(R.id.toggleend);
		toggletype = (TextView) findViewById(R.id.toggletype);
		
		lv_find_goods = (ListView) findViewById(R.id.lv_find_goods);
		lv_find_goods.setAdapter(goodsAdapter);
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
			public void onClick(View v) 
			{
				finish();

			}
		});
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.togglestart://港口列表
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

				listview_start .setOnItemClickListener(new OnItemClickListener() 
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						if(position==0)
						{
							startid="-1";
						}else
						startid = String.valueOf(position);
						popupWindowArea.dismiss();
						
						togglestart.setText(from_lists.get(position)); 

						GetGoodsListTask();

					}
				});

				LinearLayout parent6 = (LinearLayout) this .findViewById(R.id.findgoods_view);// 父窗口view
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT); 
				popupWindowArea.setOutsideTouchable(true);
				popupWindowArea.setTouchable(true);			 

				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				popupWindowArea.setOnDismissListener(new OnDismissListener() 
				{
					@Override
					public void onDismiss() 
					{
						WindowManager.LayoutParams lp = getWindow() .getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						
						togglestart.setTextColor(R.color.text_black_light);
						toggleend.setTextColor(getResources().getColor(R.color.text_black_light));
						toggletype.setTextColor(R.color.text_black_light); 
					}
				});

				popupWindowArea.showAsDropDown(liner_case, 0, 0);
				isStartOpen = true;
			} else 
			{
				popupWindowArea.dismiss();
				isStartOpen = false;
				from_lists.clear();  
			}
			break;
		case R.id.toggleend:
			from_lists.clear();

			if (isStartOpen == false) 
			{
				togglestart.setTextColor(R.color.text_black_light);
				toggleend.setTextColor(getResources().getColor(R.color.text_black_light));
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
				listview_start .setOnItemClickListener(new OnItemClickListener() 
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
					{
						if(position==0)
						{
							endid="-1";
						}else
							endid = String.valueOf(position);
						toggleend.setText(from_lists.get(position)); 
						popupWindowArea.dismiss(); 
						GetGoodsListTask();

					}
				});			

				LinearLayout parent6 = (LinearLayout) this .findViewById(R.id.findgoods_view);// 父窗口view
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
				
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				popupWindowArea.setTouchable(true);
				popupWindowArea.setOutsideTouchable(true);				 

				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);

				popupWindowArea.setOnDismissListener(new OnDismissListener() 
				{
					@Override
					public void onDismiss() 
					{
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						togglestart.setTextColor(R.color.text_black_light);
						toggleend.setTextColor(getResources().getColor(R.color.text_black_light));
						toggletype.setTextColor(R.color.text_black_light); 
					}
				});

				popupWindowArea.showAsDropDown(liner_case, 0, 0);
				isStartOpen = true;
			} else 
			{
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

			if (isStartOpen == false) 
			{
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
							JSONArray array=new JSONArray(result.trim());
							from_lists.add("不限");
							for(int i=0;i<array.length();i++)
							{
								JSONObject object=array.getJSONObject(i);
								from_lists.add(object.getString("unitname"));								
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
				hr1.post(contants.baseUrl+"GoodsTypeList", map);
				

				contentView = getLayoutInflater().inflate(R.layout.pop_start, null);// 港口

				listview_start = (ListView) contentView .findViewById(R.id.listview_start);

				listview_start .setOnItemClickListener(new OnItemClickListener() 
				{
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
					{
						if(position==0)
						{
							typeid="-1";
						}else
						typeid= String.valueOf(position);
						popupWindowArea.dismiss();
						toggletype.setText(from_lists.get(position)); 

						GetGoodsListTask();
					}
				}); 

				LinearLayout parent6 = (LinearLayout) this .findViewById(R.id.findgoods_view);// 父窗口view
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT); 
				popupWindowArea.setTouchable(true);
				popupWindowArea.setOutsideTouchable(true);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);				

				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);

				popupWindowArea.setOnDismissListener(new OnDismissListener()
				{
					@Override
					public void onDismiss() 
					{
						WindowManager.LayoutParams lp = getWindow()
								.getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						togglestart.setTextColor(R.color.text_black_light);
						toggleend.setTextColor(getResources().getColor(R.color.blue));
						toggletype.setTextColor(R.color.text_black_light); 
					}
				});

				popupWindowArea.showAsDropDown(liner_case, 0, 0);
				isStartOpen = true;
			} else 
			{
				popupWindowArea.dismiss();
				isStartOpen = false;
				from_lists.clear(); 
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

			LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.findgoods_view);//父窗口view  
			popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			WindowManager.LayoutParams lp6 = getWindow().getAttributes();
			lp6.alpha = 0.5f;
			getWindow().setAttributes(lp6);
			
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

			default:
				break;
			}
			
		}
		
	}
	
	@Override
	public void onBackPressed()
	{
		if(popupWindowArea!=null&&popupWindowArea.isShowing())
		{
			popupWindowArea.dismiss();
			return;
		}
		
		finish();
	}

	// 获取找货列表
	private void GetGoodsListTask() 
	{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("FromID=" + startid);
		aa.add("ToID=" + endid);
		aa.add("GoodsTypeid=" +typeid); 
		aa.add("TradetypeID=" +1); 		
		aa.add("ShipTypeid=" +"-1");  
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.baseUrl+"TradeListByTerms";
		if (!toUrl.equals("")) 
		{
			kjh.post(toUrl, params, false, new HttpCallBack() {
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
							goodsAdapter.notifyDataSetChanged();
							goodsAdapter.notifyDataSetInvalidated();
						} else 
						{
							modellist.clear();
							for (int i = 0; i < data.length(); i++) 
							{
								JSONObject temp = data.getJSONObject(i);
								GoodsMode model = new GoodsMode();

								model.setid(temp.getString("id"));
								model.settitle(temp.getString("titile"));
								JSONObject jtyp=temp .getJSONObject("type");
								model.settype(jtyp.getString("unitname"));
								model.setname(temp.getString("name"));
								model.settons(temp.getString("tons"));
								model.setpackaging(temp.getString("packaging"));
								JSONObject port1=temp .getJSONObject("startport");
								JSONObject port2=temp .getJSONObject("unloadport");
								model.setstartport(port1.getString("portname"));								
								model.setunloadport(port2.getString("portname"));
								model.setprice(temp.getString("price"));
								model.setlinkman(temp.getString("linkman"));
								model.settel(temp.getString("tel"));
								model.setremark(temp.getString("remark"));
								model.setpostdate(temp.getString("postdate"));

								modellist.add(model);

							}

							goodsAdapter.notifyDataSetChanged();
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
