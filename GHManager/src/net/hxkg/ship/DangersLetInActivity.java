package net.hxkg.ship;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import net.hxkg.ghmanager.R;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import net.hxkg.network.contants;
import net.hxkg.user.User;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tuen.util.SystemStatic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

public class DangersLetInActivity extends Activity implements OnClickListener,onResult
{
	private RelativeLayout relative_dangers_goods_type,relative_goods_type,relative_goods_units,relative_start,relative_final,
	relative_ship_name,relative_time_choose,relative_title_final;

	public PopupWindow popupWindowArea;
	private Button btn_confirm;
	private View contentView;
	private ListView listView_shapmane,listview_goodsname,listview_dangersgoodstype,listview_goods_units
	,listview_start;
	private PopShipNameAdapter nameAdapter,goodsnameAdapter,unitAdapter,dangersgoodAdapter,fromAdapter;
	public ArrayList<String> ship_name_lists,goodsname_lists,dangersgoodstype_lists,unit_lists,
	from_lists;
	public static DangersLetInActivity instance;
	public TextView ship_name,from,to,goods,rank,unit,time;
	private EditText tons;
	private ImageButton image_list;
	private ImageView back;
	public int poptpye=0;
	private ArrayList<String> date_string,hour_string,second_string;
	
	private WheelView wheeldate,wheelhour,wheelsecond;
	RecordLetIn model=null;
	
//	private Handler MissionPauseHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case Reagain_SERVICE_OK:
//				
//				break;
//			
//			default:
//				break;
//			}
//		};
//	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// 
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dangersletin);
		model=(RecordLetIn) getIntent().getSerializableExtra("RecordLetIn");
		init();
		instance=this;
		initstring();
		
		
	}
	

	private void init()
	{		
		relative_start=(RelativeLayout)findViewById(R.id.relative2);
		relative_start.setOnClickListener(this);
		relative_final=(RelativeLayout)findViewById(R.id.relative3);
		relative_final.setOnClickListener(this);
		relative_dangers_goods_type=(RelativeLayout)findViewById(R.id.relative5);
		relative_dangers_goods_type.setOnClickListener(this);
		relative_goods_type=(RelativeLayout)findViewById(R.id.relative4);
		relative_goods_type.setOnClickListener(this);
		relative_goods_units=(RelativeLayout)findViewById(R.id.relative6);
		relative_goods_units.setOnClickListener(this);
		relative_ship_name=(RelativeLayout)findViewById(R.id.relative1);
		relative_ship_name.setOnClickListener(this);
		relative_time_choose=(RelativeLayout)findViewById(R.id.relative8);
		relative_time_choose.setOnClickListener(this);
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		ship_name=(TextView)findViewById(R.id.text1_context);
		//ship_name.setText(model.getShipname());
		from=(TextView)findViewById(R.id.text2_context);		
		to=(TextView)findViewById(R.id.text3_context);		
		goods=(TextView)findViewById(R.id.text4_context);		
		rank=(TextView)findViewById(R.id.text5_context);		
		tons=(EditText)findViewById(R.id.text6_context);		
		unit=(TextView)findViewById(R.id.unit);		
		time=(TextView)findViewById(R.id.text8_context);
		
		if(model!=null)
		{
			ship_name.setText(SystemStatic.searchShipName);
			from.setText(model.getstart());
			to.setText(model.gettarget());
			goods.setText(model.getgoods());
			rank.setText(model.getrank());
			tons.setText(model.gettons());
			unit.setText(model.getunit());
			time.setText(model.getberthtime());
		}
		
		
		btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
		
		ship_name_lists=new ArrayList<String>();
		goodsname_lists=new ArrayList<String>();
		unit_lists=new ArrayList<String>();
		dangersgoodstype_lists=new ArrayList<String>();
		from_lists=new ArrayList<String>();
		
		nameAdapter=new PopShipNameAdapter(this, ship_name_lists,1);
		goodsnameAdapter=new PopShipNameAdapter(this, goodsname_lists,2);
		unitAdapter=new PopShipNameAdapter(this, unit_lists,3);
		dangersgoodAdapter=new PopShipNameAdapter(this, dangersgoodstype_lists,4);
		fromAdapter=new PopShipNameAdapter(this, from_lists,5);
		
		
		image_list=(ImageButton)findViewById(R.id.image_list);
		image_list.setOnClickListener(this);
		
		back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		
		
		case R.id.btn_confirm:
			SendRecord();
			break;
			
		case R.id.relative_title_final:
			finish();
			break;
			
		case R.id.relative1:
			poptpye=1;
			GetShipName();
			
			
			break;
		case R.id.relative2:
			 poptpye=6;
			 String ad1="杭州";
			 String ad2="宁波";
			 String ad3="绍兴";
			 String ad4="湖州";
			 String ad5="嘉兴";
			 String ad6="金华";
			 String ad7="衢州";
			 String ad8="台州";
			 String ad9="丽水";
			 String ad10="舟山";
			 from_lists.add(ad1);
			 from_lists.add(ad2);
			 from_lists.add(ad3);
			 from_lists.add(ad4);
			 from_lists.add(ad5);
			 from_lists.add(ad6);
			 from_lists.add(ad7);
			 from_lists.add(ad8);
			 from_lists.add(ad9);
			 from_lists.add(ad10);
			 
			 Log.i("GH_TEXT",  from_lists.size()+"   sizesize");
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_from, null);//货物类型
			 TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_start=(ListView) contentView.findViewById(R.id.listview_start);
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

				LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
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
		case R.id.relative3:
			
			 poptpye=7;
			 String ac1="杭州";
			 String ac2="宁波";
			 String ac3="绍兴";
			 String ac4="湖州";
			 String ac5="嘉兴";
			 String ac6="金华";
			 String ac7="衢州";
			 String ac8="台州";
			 String ac9="丽水";
			 String ac10="舟山";
			 from_lists.add(ac1);
			 from_lists.add(ac2);
			 from_lists.add(ac3);
			 from_lists.add(ac4);
			 from_lists.add(ac5);
			 from_lists.add(ac6);
			 from_lists.add(ac7);
			 from_lists.add(ac8);
			 from_lists.add(ac9);
			 from_lists.add(ac10);
			 
			 Log.i("GH_TEXT",  from_lists.size()+"   sizesize");
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_from, null);//货物类型
			 TextView pop_dis7 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_start=(ListView) contentView.findViewById(R.id.listview_start);
			 listview_start.setAdapter(fromAdapter);
			// listview_goodsname.setAdapter(goodsnameAdapter);
				pop_dis7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						popupWindowArea.dismiss();
						from_lists.clear();

					}
				});

				LinearLayout parent7 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent7.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				WindowManager.LayoutParams lp7 = getWindow().getAttributes();
				lp7.alpha = 0.5f;
				getWindow().setAttributes(lp7);
				
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent7, Gravity.TOP, 0, parent7.getHeight()*1/8);
			
			
			
			
			
			
			break;
		case R.id.relative5:
			 String aaa="爆炸品";
			 String bbb="气体";
			 String ccc="易燃液体";
			 String eee="易燃固体";
			 String fff="氧化剂";
			 String ggg="毒性物质";
			 String hhh="放射物质";
			 String iii="腐蚀品";
			 String jjj="杂项危险物品";
			 dangersgoodstype_lists.add(aaa);
			 dangersgoodstype_lists.add(bbb);
			 dangersgoodstype_lists.add(ccc);
			 dangersgoodstype_lists.add(eee);
			 dangersgoodstype_lists.add(fff);
			 dangersgoodstype_lists.add(ggg);
			 dangersgoodstype_lists.add(hhh);
			 dangersgoodstype_lists.add(iii);
			 dangersgoodstype_lists.add(jjj);
			 
			 Log.i("GH_TEXT",  dangersgoodstype_lists.size()+"   sizesize");
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_dangers_goods, null);//货物类型
			 TextView pop_dis5 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_dangersgoodstype=(ListView) contentView.findViewById(R.id.listview_danger_good);
			 listview_dangersgoodstype.setAdapter(dangersgoodAdapter);
			// listview_goodsname.setAdapter(goodsnameAdapter);
				pop_dis5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						popupWindowArea.dismiss();
						goodsname_lists.clear();

					}
				});

				LinearLayout parent5 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent5.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				WindowManager.LayoutParams lp5 = getWindow().getAttributes();
				lp5.alpha = 0.5f;
				getWindow().setAttributes(lp5);
				
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent5, Gravity.TOP, 0, parent5.getHeight()*1/8);
			break;
			
		case R.id.relative4:
			 poptpye=2;
			 String a="液体货";
			 String b="杂件货";
			 String c="散货";
			 goodsname_lists.add(a);
			 goodsname_lists.add(b);
			 goodsname_lists.add(c);
			 
			 Log.i("GH_TEXT",  goodsname_lists.size()+"   sizesize");
			
			 contentView = getLayoutInflater().inflate(R.layout.goods_type, null);//货物类型
			 TextView pop_dis = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_goodsname=(ListView) contentView.findViewById(R.id.listview_goodsname);
			 listview_goodsname.setAdapter(goodsnameAdapter);
			// listview_goodsname.setAdapter(goodsnameAdapter);
				pop_dis.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						popupWindowArea.dismiss();
						goodsname_lists.clear();

					}
				});

				LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 0.5f;
				getWindow().setAttributes(lp);
				
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent, Gravity.TOP, 0, parent.getHeight()*1/8);
			break;
		case R.id.relative6:
			 poptpye=3;
			 String aa="吨";
			 String bb="立方米";
			 String cc="标箱";
			 String dd="标货";
			 unit_lists.add(aa);
			 unit_lists.add(bb);
			 unit_lists.add(cc);
			 unit_lists.add(dd);
			 
			 Log.i("GH_TEXT",  unit_lists.size()+"   sizesize");
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_goods_units, null);//货物类型
			 TextView pop_dis3 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_goods_units=(ListView) contentView.findViewById(R.id.listview_goods_units);
			 listview_goods_units.setAdapter(unitAdapter);
			// listview_goodsname.setAdapter(goodsnameAdapter);
				pop_dis3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						popupWindowArea.dismiss();
						unit_lists.clear();

					}
				});

				LinearLayout parent3 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent3.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				WindowManager.LayoutParams lp3 = getWindow().getAttributes();
				lp3.alpha = 0.5f;
				getWindow().setAttributes(lp3);
				
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent3, Gravity.TOP, 0, parent3.getHeight()*1/8);
			break;
			
			
		case R.id.image_list:
			Intent intent_to_listRecord=new Intent();
			intent_to_listRecord.setClass(DangersLetInActivity.this, LetInRecordActivity.class);	
			SystemStatic.searchShipName=ship_name.getText().toString();
			//intent_to_listRecord.putExtra("shipname",ship_name.getText().toString());			
			startActivity(intent_to_listRecord);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.relative8:
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
		//	View view = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
			final Dialog dialog = new Dialog(DangersLetInActivity.this);
			dialog.setContentView(contentView);
			
			Button button_ok=(Button)contentView.findViewById(R.id.btn_ok);
			Button button_cancel=(Button)contentView.findViewById(R.id.btn_cancel);
			
			
			dialog.getWindow().setGravity(Gravity.BOTTOM);	
			WindowManager windowManager = getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			WindowManager.LayoutParams lp9 = dialog.getWindow().getAttributes();
			lp9.width = WindowManager.LayoutParams.FILL_PARENT;		
			// 设置透明度为0.3
			lp9.alpha = 1f;
			// lp.dimAmount=0.7f;
			dialog.getWindow().setAttributes(lp9);
			dialog.getWindow().setBackgroundDrawableResource(R.drawable.pop_shape);			
			dialog.show();			

			
			wheeldate=(WheelView) dialog.findViewById(R.id.date);
			wheelhour=(WheelView) dialog.findViewById(R.id.hour);
			wheelsecond=(WheelView) dialog.findViewById(R.id.second);
			
			wheeldate.setData(date_string);
			wheelhour.setData(hour_string);
			wheelsecond.setData(second_string);
			
			button_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();					
				}
			});
			
			button_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String num = wheeldate.getSelectedText()+wheelhour.getSelectedText()+wheelsecond.getSelectedText();
						time.setText(num);												
						dialog.dismiss();
						
				}
			});
			
			
			break;
			
		}
		
	}
	
	
	
	
	private void initPopWindowShopName(){
		
		{
		contentView = getLayoutInflater().inflate(R.layout.pop_ship_name, null);//船名
		listView_shapmane=(ListView)contentView.findViewById(R.id.listview_ship_name);
		listView_shapmane.setAdapter(nameAdapter);
		TextView pop_dis = (TextView) contentView.findViewById(R.id.pop_dis);

		pop_dis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
				popupWindowArea.dismiss();
				ship_name_lists.clear();

			}
		});
		
		LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
		popupWindowArea = new PopupWindow(contentView, parent.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindowArea.setFocusable(true);
		popupWindowArea.setOutsideTouchable(true);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		
		popupWindowArea.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		
		popupWindowArea.showAtLocation(parent, Gravity.TOP, 0, parent.getHeight()*1/8);
		}
		
	}
	
	
	
	private void GetShipName() 
	{
		String toUrl = contants.getshipnamelist;
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("Username", User.name);
		hr.post(toUrl, map);		
	}
	
	/**
	 *提交申报接口
	 */
	
	private void SendRecord() 
	{		// 
		
		String toUrl = contants.sendrecord;
		HttpRequest hr=new HttpRequest(new onResult() 
		{
			
			@Override
			public void onSuccess(String result) 
			{
				Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		String ship_name=this.ship_name.getText().toString();
		String from=this.from.getText().toString();
		String to=this.to.getText().toString();
		String goods=this.goods.getText().toString();
		String rank=this.rank.getText().toString();
		String unit=this.unit.getText().toString();
		String time=this.time.getText().toString();
		String tons=this.tons.getText().toString();
		if(ship_name.equals("")||from.equals("")||to.equals("")||goods.equals("")||
				rank.equals("")||unit.equals("")||time.equals("")||tons.equals(""))
		{
			Toast.makeText(getApplicationContext(), "请完善提交信息", Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String, Object> map=new HashMap<>();
		map.put("Shipname", ship_name);
		map.put("From", from);
		map.put("To", to);
		map.put("Goods", goods);
		map.put("Rank", rank);
		map.put("Tons", tons);
		map.put("Unit", unit);
		map.put("Time", time);
		if(model==null)
			map.put("Reportid", "");
		else{
			System.out.println(model.getId());
			map.put("Reportid", model.getId());
		}
		
		hr.post(toUrl, map);	
		//hr.post("http://192.168.1.116:8080/dangerreport", map);
	}
	
	/**
	 * 初始化日期滚轮数据
	 */

	private void initstring() {
		date_string = new ArrayList<String>();
		hour_string = new ArrayList<String>();
		second_string = new ArrayList<String>();
		
		date_string.add(StringData());
		date_string.add(StringData());
		date_string.add(StringData());
		date_string.add(StringData());
		date_string.add(StringData());

		for (int i = 0; i < 24; i++) {

			hour_string.add(i + " 时");

		}

		for (int i = 0; i < 60; i++) {
			if (0 <= i && i < 10) {
				second_string.add("0" + i + "分");
			} else {
				second_string.add(i + "分");
			}
		}

	}
	
	/**
	 * 获取当前日期星期
	 * 
	 * @return
	 */

	public String StringData() {

		String mYear;
		String mMonth;
		String mDay;
		String mWay;
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
		mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
		mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

		if ("1".equals(mWay)) {
			mWay = "天";
		} else if ("2".equals(mWay)) {
			mWay = "一";
		} else if ("3".equals(mWay)) {
			mWay = "二";
		} else if ("4".equals(mWay)) {
			mWay = "三";
		} else if ("5".equals(mWay)) {
			mWay = "四";
		} else if ("6".equals(mWay)) {
			mWay = "五";
		} else if ("7".equals(mWay)) {
			mWay = "六";
		}
		return mMonth + "月" + mDay + "日" + "周" + mWay;
		// return "星期"+mWay;

	}

	/**
	 * 计算每个月天数
	 */

	public int MonthsMax(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		// int year = 2007;
		// int month = Calendar.FEBRUARY;
		int date = 1;
		calendar.set(year, month, date); // 2007/2/1
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("Max Day: " + maxDay);
		calendar.set(2004, Calendar.FEBRUARY, 1);// 2004/2/1
		maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;

	}
				
	/**
	 * 根据日期计算周几
	 */
	String pTime = "2016-01-01";

	private String getWeek(String pTime) throws java.text.ParseException {

		String Week = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "天";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}

		return Week;
	}


	@Override
	public void onSuccess(String result) {
		try {
			JSONObject resultJO = new JSONObject(result);						
			JSONObject  res = new JSONObject(result);
			JSONArray data = res.getJSONArray("data");
			Log.i("GH_TEXT", data.length()+"我是数据的size");
			
			
			for(int i = 0;i<data.length();i++){
				JSONObject temp = data.getJSONObject(i);
				String name= new String();
				//RegionList region = new RegionList();
				
				name=temp.getString("shipname");
				
				
				ship_name_lists.add(name);
																																																																								
			}
			
			Log.i("GH_TEXT", "添加数据完成");
			
			initPopWindowShopName();
			
			
		//	MissionPauseHandler.sendEmptyMessage(Reagain_SERVICE_OK);	
		//	newsAdapter.pre(newslist);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}


	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
	
}
