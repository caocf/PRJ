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

import com.gh.modol.ShipCircleListModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.PopShipNameAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity; 
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 发布船源信息界面
 * @author hztuen7
 *
 */
public class SendShipResMsgActivity extends Activity implements OnClickListener 
{
	private Button face_talk, make_price, btn_post;
	private EditText edit_price;
	private TextView text_yuan, tv_line, tv_ship_type, tv_hangxian, tv_start,
			tv_end;
	private RelativeLayout relative_title_final, 
			relativie_goods_type, relative_start, relative_end,
			relative_hold_style;
	private EditText tv_Title, tv_ship_name, tv_Load, tv_Linkman, tv_Tel,
			tv_Remark;
	public ArrayList<String> from_lists;
	private PopShipNameAdapter fromAdapter;
	public PopupWindow popupWindowArea;
	private View contentView;
	private ListView listview_start;
	int typeid=1,fromid=1,toid=1,id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_ship_res_mag);
		init();
	}

	private void init() 
	{
		face_talk = (Button) findViewById(R.id.face_talk); // 面谈按钮
		make_price = (Button) findViewById(R.id.make_price);// 自定义价格按钮
		edit_price = (EditText) findViewById(R.id.edit_price);// 填写自定义价格
		text_yuan = (TextView) findViewById(R.id.text_yuan);// "元"
		tv_line = (TextView) findViewById(R.id.tv_line);// 横线
		
		/**
		 * 初始化为面议
		 */
		face_talk.setBackgroundColor(getResources().getColor(
				R.color.home_second_header_color));
		make_price.setBackgroundColor(getResources().getColor(
				R.color.text_color_c));
		face_talk.setTextColor(getResources()
				.getColor(R.color.text_color_a));
		make_price.setTextColor(getResources().getColor(
				R.color.text_color_b));
		edit_price.setVisibility(View.GONE);
		text_yuan.setVisibility(View.GONE);
		tv_line.setVisibility(View.GONE);

		
		
		face_talk.setOnClickListener(this);
		make_price.setOnClickListener(this);

		tv_Title = (EditText) findViewById(R.id.text1_context);// 标题
		tv_ship_type = (TextView) findViewById(R.id.text2_context);// 船类型
		tv_ship_name = (EditText) findViewById(R.id.text3_context);// 船名字
		tv_Load = (EditText) findViewById(R.id.text4_context);// 填写重量

		tv_Linkman = (EditText) findViewById(R.id.text6_context);// 填写联系人
		tv_Tel = (EditText) findViewById(R.id.text8_context);// 填写联系方式
		
		tv_Tel.setText(SystemStatic.phoneNum);
		tv_Remark = (EditText) findViewById(R.id.text9_context);// 填写备注
		tv_hangxian = (TextView) findViewById(R.id.text15_context);// 
		tv_start = (TextView) findViewById(R.id.text16_context);// 填写发货港
		tv_end = (TextView) findViewById(R.id.text17_context);// 填写到达港

		relativie_goods_type = (RelativeLayout) findViewById(R.id.relative2);//
		relativie_goods_type.setOnClickListener(this);

		relative_start = (RelativeLayout) findViewById(R.id.relative16);//
		relative_start.setOnClickListener(this);

		relative_end = (RelativeLayout) findViewById(R.id.relative17);//
		relative_end.setOnClickListener(this);

		relative_hold_style = (RelativeLayout) findViewById(R.id.relative15);//
		relative_hold_style.setOnClickListener(this);

		btn_post = (Button) findViewById(R.id.btn_post);
		btn_post.setOnClickListener(this);

		from_lists = new ArrayList<String>();
		fromAdapter = new PopShipNameAdapter(this, from_lists, 6);

		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		dataInit();
	}
	
	private void dataInit()
	{
		ShipCircleListModel model=(ShipCircleListModel) getIntent().getSerializableExtra("ShipCircleListModel");
		if(model==null)
			return;
		id=Integer.valueOf(model.getid());
		tv_Title.setText(model.gettitle());
		tv_ship_type.setText(model.shiptypename);
		typeid=Integer.valueOf(model.shiptypeid);
		
		tv_ship_name.setText(model.shipname);
		tv_Load.setText(model.load); 
		tv_start.setText(model.fromString);
		fromid=Integer.valueOf(model.fromid);
		tv_end.setText(model.to);
		toid=Integer.valueOf(model.toid);
		tv_Linkman.setText(model.getcontent());
		tv_Tel.setText(model.tel);
		tv_Remark.setText(model.remark); 
		
		tv_hangxian.setText(model.route);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击面议
		case R.id.face_talk:
			face_talk.setBackgroundColor(getResources().getColor(
					R.color.home_second_header_color));
			make_price.setBackgroundColor(getResources().getColor(
					R.color.text_color_c));
			face_talk.setTextColor(getResources()
					.getColor(R.color.text_color_a));
			make_price.setTextColor(getResources().getColor(
					R.color.text_color_b));
			edit_price.setVisibility(View.GONE);
			text_yuan.setVisibility(View.GONE);
			tv_line.setVisibility(View.GONE);

			break;
		// 点击自定义价格
		case R.id.make_price:
			make_price.setBackgroundColor(getResources().getColor(
					R.color.home_second_header_color));
			face_talk.setBackgroundColor(getResources().getColor(
					R.color.text_color_c));
			make_price.setTextColor(getResources().getColor(
					R.color.text_color_a));
			face_talk.setTextColor(getResources()
					.getColor(R.color.text_color_b));
			edit_price.setVisibility(View.VISIBLE);
			text_yuan.setVisibility(View.VISIBLE);
			tv_line.setVisibility(View.VISIBLE);
			break;
		// 点击提交按钮
		case R.id.btn_post:

			SendGoodsMsgTask();
			break;
		// 点击船型
		case R.id.relative2:

			HttpRequest hr1=new HttpRequest(new HttpRequest.onResult() 
			{
				
				@Override
				public void onSuccess(String result) 
				{
					try
					{
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
				public void onError(int httpcode) {
					// TODO Auto-generated method stub
					
				}
			});
			Map<String, Object> map=new HashMap<>();
			hr1.post(contants.baseUrl+"ShipTypeList", map);

			popcontent(1);

			break;
		// 点击发货港
		case R.id.relative16:

			HttpRequest hr2=new HttpRequest(new HttpRequest.onResult() 
			{
				
				@Override
				public void onSuccess(String result) 
				{
					try
					{
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
			Map<String, Object> map1=new HashMap<>();
			hr2.post(contants.baseUrl+"PortList", map1);
			popcontent(2);
			break;
		// 点击卸货港
		case R.id.relative17:

			HttpRequest hr3=new HttpRequest(new HttpRequest.onResult() 
			{
				
				@Override
				public void onSuccess(String result) 
				{
					try
					{
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
			Map<String, Object> map2=new HashMap<>();
			hr3.post(contants.baseUrl+"PortList", map2);
			popcontent(3);
			break;
		//点击航线
		case R.id.relative15:
			String d1 = "长江流域";
			String d2 = "浙江本省";
			String d3 = "国内港口";
			String d4 = "国际";
			
			from_lists.add(d1);
			from_lists.add(d2);
			from_lists.add(d3);
			from_lists.add(d4);
			
			popcontent(4);
			break;
		default:
			break;
		}

	}

	// popwindow弹出框
	private void popcontent(final int k) {
		contentView = getLayoutInflater().inflate(R.layout.pop_from, null);
		TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
		TextView tv_title=(TextView)contentView.findViewById(R.id.textView2);
		listview_start = (ListView) contentView
				.findViewById(R.id.listview_start);
		
		if (k == 1) {			
			tv_title.setText("请选择船型");
		} else if (k == 2) {
			tv_title.setText("请选择发货港");
		} else if (k == 3) {
			tv_title.setText("请选择到达港");
		} else if (k == 4) {
			tv_title.setText("请选择航线");
		}
		listview_start.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (k == 1) {
					tv_ship_type.setText(from_lists.get(position));
					typeid=position+1;
				} else if (k == 2) {
					tv_start.setText(from_lists.get(position));
					fromid=position+1;
				} else if (k == 3) {
					tv_end.setText(from_lists.get(position));
					toid=position+1;
				} else if (k == 4) {
					tv_hangxian.setText(from_lists.get(position));
				}
				popupWindowArea.dismiss();
				from_lists.clear();

			}
		});

		listview_start.setAdapter(fromAdapter);
		// listview_goodsname.setAdapter(goodsnameAdapter);
		pop_dis6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				popupWindowArea.dismiss();
				from_lists.clear();

			}
		});

		LinearLayout parent6 = (LinearLayout) this
				.findViewById(R.id.father_send_ship_res_msg);// 父窗口view
		popupWindowArea = new PopupWindow(contentView,
				parent6.getWidth() * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindowArea.setFocusable(true);
		popupWindowArea.setOutsideTouchable(true);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindowArea.setBackgroundDrawable(new PaintDrawable());
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

		popupWindowArea.showAtLocation(parent6, Gravity.TOP, 0,
				parent6.getHeight() * 1 / 8);
	}

	// 发布船源信息
	private void SendGoodsMsgTask()
	{
		String Title = tv_Title.getText().toString();
		String Shiptype = tv_ship_type.getText().toString();
		String Shipname = tv_ship_name.getText().toString();
		String Load = tv_Load.getText().toString();
		
		
		String Route = tv_hangxian.getText().toString();
		String From = tv_start.getText().toString();
		String To = tv_end.getText().toString();
		
		
		String Price = edit_price.getText().toString().equals("")?"面议":edit_price.getText().toString()+"元";
		String Linkman = tv_Linkman.getText().toString();
		String Tel = tv_Tel.getText().toString();
		String Remark = tv_Remark.getText().toString();

		if (Title.equals("") || Shiptype.equals("") || Shipname.equals("")
				|| Load.equals("") || Tel.equals("")
				|| Route.equals("")||From.equals("")) {
			Toast.makeText(getApplicationContext(), "请完善必填信息",
					Toast.LENGTH_SHORT).show();
		} else {
			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Title=" + Title);
			aa.add("ShiptypeID=" + typeid);
			int type=typeid;
			aa.add("Shipname=" + Shipname);
			aa.add("Load=" + Load);
			aa.add("Route=" + Route);
			aa.add("FromID=" + fromid);
			aa.add("ToID=" + toid);			
			aa.add("Price=" + Price);
			aa.add("Linkman=" + Linkman);
			aa.add("Tel=" + Tel);			
			aa.add("Remark=" + Remark);
			aa.add("Userid=" + SystemStatic.userId);
			aa.add("tradetype=" + 2);
			aa.add("id=" + id);

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.baseUrl+"postTrade";
			if (!toUrl.equals("")) 
			{
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);
						// 获取cookie
						KJLoger.debug("===" + headers.get("Set-Cookie")); 
						String result = new String(t).trim();
						try 
						{
							JSONObject res = new JSONObject(result);
							String resultcode = res.getString("resultcode");

							if (!"".equals(resultcode) && resultcode != null) {
								if ("-1".equals(resultcode)) {
									Toast.makeText(getApplicationContext(),
											"提交失败", Toast.LENGTH_SHORT).show();
								} else if (Integer.valueOf(resultcode)
										.intValue() >= 0) {
									Toast.makeText(getApplicationContext(),
											"提交成功", Toast.LENGTH_SHORT).show();
									finish();
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"有错误，请检查", Toast.LENGTH_SHORT).show();
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


}
