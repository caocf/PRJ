package com.hztuen.gh.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent; 
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.util.AbDateUtil;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbStrUtil;
import com.ab.view.wheel.AbNumericWheelAdapter;
import com.ab.view.wheel.AbStringWheelAdapter;
import com.ab.view.wheel.AbWheelView;
import com.ab.view.wheel.AbWheelView.AbOnWheelChangedListener;
import com.gh.modol.RecordLetIn;
import com.hxkg.ghpublic.HomeActivity;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.PopShipNameAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

public class DangersLetInActivity extends Activity implements OnClickListener,AbOnWheelChangedListener {
	private RelativeLayout relative_dangers_goods_type, relative_goods_type,
			relative_goods_units, relative_start, relative_final,
			relative_ship_name, relative_time_choose, relative_title_final;

	public PopupWindow popupWindowArea;
	private Button btn_confirm;
	private View contentView;
	private ListView listView_shapmane, 
			listview_dangersgoodstype, listview_goods_units, listview_start;
	private PopShipNameAdapter nameAdapter, goodsnameAdapter, unitAdapter,
			dangersgoodAdapter, fromAdapter;
	public ArrayList<String> ship_name_lists, goodsname_lists,
			dangersgoodstype_lists, unit_lists, from_lists;
	public static DangersLetInActivity instance;
	public TextView ship_name, from, to, rank, unit, time_will_post,
			time_in;
	private EditText tons ,goods;
	private ImageButton image_list;
	private ImageView back;
	public int poptpye = 0;
	private ArrayList<String> date_string, hour_string, second_string;

	private AbWheelView mWheelViewMD, mWheelViewMM, mWheelViewHH;
    private List<String> textDMDateList;
	
	private  String val;
	
	int fromid,toid,rankid,unitid=1,id=-1;
	
	ProgressDialog loginDialog = null;
	RecordLetIn model=null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dangersletin);
		init();
		instance = this;
	}

	private void init() 
	{

		relative_start = (RelativeLayout) findViewById(R.id.relative2);
		relative_start.setOnClickListener(this);
		relative_final = (RelativeLayout) findViewById(R.id.relative3);
		relative_final.setOnClickListener(this);
		relative_dangers_goods_type = (RelativeLayout) findViewById(R.id.relative5);
		relative_dangers_goods_type.setOnClickListener(this);
		relative_goods_type = (RelativeLayout) findViewById(R.id.relative4);
		relative_goods_type.setOnClickListener(this);
		relative_goods_units = (RelativeLayout) findViewById(R.id.relative6);
		relative_goods_units.setOnClickListener(this);
		relative_ship_name = (RelativeLayout) findViewById(R.id.relative1);
		relative_ship_name.setOnClickListener(this);
		relative_time_choose = (RelativeLayout) findViewById(R.id.relative8);
		relative_time_choose.setOnClickListener(this);
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		
		ship_name = (TextView) findViewById(R.id.text1_context);
		
		from = (TextView) findViewById(R.id.text2_context);
		to = (TextView) findViewById(R.id.text3_context);
		goods = (EditText) findViewById(R.id.text4_context);
		rank = (TextView) findViewById(R.id.text5_context);
		tons = (EditText) findViewById(R.id.text6_context);
		unit = (TextView) findViewById(R.id.unit);
		time_will_post = (TextView) findViewById(R.id.text8_context);

		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);

		ship_name_lists = new ArrayList<String>(); 
		goodsname_lists = new ArrayList<String>();
		unit_lists = new ArrayList<String>();
		dangersgoodstype_lists = new ArrayList<String>();
		from_lists = new ArrayList<String>();

		nameAdapter = new PopShipNameAdapter(this, ship_name_lists, 1);
		goodsnameAdapter = new PopShipNameAdapter(this, goodsname_lists, 2);
		unitAdapter = new PopShipNameAdapter(this, unit_lists, 3);
		dangersgoodAdapter = new PopShipNameAdapter(this,
				dangersgoodstype_lists, 4);
		fromAdapter = new PopShipNameAdapter(this, from_lists, 5);

		image_list = (ImageButton) findViewById(R.id.image_list);
		image_list.setOnClickListener(this);

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);

		model=(RecordLetIn) getIntent().getSerializableExtra("RecordLetIn");
		if (model!=null) 
		{
			image_list.setVisibility(View.GONE);
			SystemStatic.recordID = model.getid();
			id=Integer.valueOf(model.getid());
			String starts = model.getstart();
			String targets = model.gettarget();
			String goodss = model.getgoods();
			String ranks = model.getrank();
			String tonss = model.gettons();
			String units = model.getunit();
			String berthtimes = model.getberthtime();

			ship_name.setText(model.shipnameString);
			from.setText(starts);
			fromid=Integer.valueOf(model.startidString);
			to.setText(targets);
			toid=Integer.valueOf(model.targetidString);
			goods.setText(goodss);
			rank.setText(ranks);
			rankid=Integer.valueOf(model.rankidString);
			tons.setText(tonss);			
			unit.setText(units);
			unitid=Integer.valueOf(model.unitidString);

			time_will_post.setText(berthtimes);

		}		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_confirm:
			SendRecord();
			break;

		case R.id.relative_title_final:
			finish();
			break;

		case R.id.relative1:
			poptpye = 1;
			GetShipName();

			break;
		case R.id.relative2:
			poptpye = 6;
			from_lists.clear();
			HttpRequest hrs=new HttpRequest(new HttpRequest.onResult() 
			 {				
				@Override
				public void onSuccess(String result) 
				{
					String string=result.replace("\\", "");
					String s1=string.substring(1,string.length()-1);
					try {
						JSONArray array=new JSONArray(s1);
						for(int i=0;i<array.length();i++)
						{
							JSONObject object=array.getJSONObject(i);
							String nameString=object.getString("portname");
							from_lists.add(nameString);
							
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					listview_start.setAdapter(fromAdapter);
				}
				
				@Override
				public void onError(int httpcode) 
				{
					
				}
			});
			 Map<String,Object> map1=new HashMap<>();
			 
			 hrs.post(contants.baseUrl+"DangerPortList", map1);		

			contentView = getLayoutInflater().inflate(R.layout.pop_from, null);// 货物类型
			TextView pop_dis6 = (TextView) contentView
					.findViewById(R.id.pop_dis);
			listview_start = (ListView) contentView
					.findViewById(R.id.listview_start); 
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
			
			
			listview_start.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{ 
					switch (parent.getId()){
					case R.id.listview_start:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position) {
					// TODO Auto-generated method stub
					from.setText(from_lists.get(position).toString());
					fromid=position+1;
					from_lists.clear();
					popupWindowArea.dismiss();
				}
			});

			LinearLayout parent6 = (LinearLayout) this
					.findViewById(R.id.parent_view2);// 父窗口view
			popupWindowArea = new PopupWindow(contentView,
					parent6.getWidth() * 4 / 5,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
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

			popupWindowArea.showAtLocation(parent6, Gravity.TOP, 0,
					parent6.getHeight() * 1 / 8);

			break;
		case R.id.relative3:
			from_lists.clear();
			HttpRequest hrs1=new HttpRequest(new HttpRequest.onResult() 
			 {				
				@Override
				public void onSuccess(String result) 
				{
					String string=result.replace("\\", "");
					String s1=string.substring(1,string.length()-1);
					try {
						JSONArray array=new JSONArray(s1);
						for(int i=0;i<array.length();i++)
						{
							JSONObject object=array.getJSONObject(i);
							String nameString=object.getString("portname");
							from_lists.add(nameString);
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					listview_start.setAdapter(fromAdapter);
				}
				
				@Override
				public void onError(int httpcode) 
				{
					// TODO Auto-generated method stub
					
				}
			});
			 Map<String,Object> map2=new HashMap<>();
			 
			 hrs1.post(contants.baseUrl+"DangerPortList", map2);	

			contentView = getLayoutInflater().inflate(R.layout.pop_from, null);// 货物类型
			TextView pop_dis7 = (TextView) contentView
					.findViewById(R.id.pop_dis);
			listview_start = (ListView) contentView
					.findViewById(R.id.listview_start); 
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
			
			
			listview_start.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					switch (parent.getId()){
					case R.id.listview_start:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position) {
					// TODO Auto-generated method stub
					to.setText(from_lists.get(position).toString());
					toid=position+1;
					from_lists.clear();
					popupWindowArea.dismiss();
				}
			});

			LinearLayout parent7 = (LinearLayout) this
					.findViewById(R.id.parent_view2);// 父窗口view
			popupWindowArea = new PopupWindow(contentView,
					parent7.getWidth() * 4 / 5,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
			WindowManager.LayoutParams lp7 = getWindow().getAttributes();
			lp7.alpha = 0.5f;
			getWindow().setAttributes(lp7);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			popupWindowArea.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
				}
			});

			popupWindowArea.showAtLocation(parent7, Gravity.TOP, 0,
					parent7.getHeight() * 1 / 8);

			break;
		case R.id.relative5:
			dangersgoodstype_lists.clear();
			HttpRequest hr=new HttpRequest(new HttpRequest.onResult() 
			{				
				@Override
				public void onSuccess(String result) 
				{
					try 
					{						
						String string=result.trim().replace("\\", "");
						String s1=string.substring(1,string.length()-1);						
						JSONArray array=new  JSONArray(s1);
						for(int i=0;i<array.length();i++)
						{
							JSONObject object=array.getJSONObject(i);
							String rankname=object.getString("rankname");
							dangersgoodstype_lists.add(rankname);
						}
						listview_dangersgoodstype.setAdapter(dangersgoodAdapter);
					} catch (JSONException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					
				}
				
				@Override
				public void onError(int httpcode) {
					// TODO Auto-generated method stub
					
				}
			});
			Map<String,Object> maps=new HashMap<>();
			
			hr.post(contants.baseUrl+"DangerRankList", maps);

			contentView = getLayoutInflater().inflate(
					R.layout.pop_dangers_goods, null);// 货物类型
			TextView pop_dis5 = (TextView) contentView
					.findViewById(R.id.pop_dis);
			listview_dangersgoodstype = (ListView) contentView
					.findViewById(R.id.listview_danger_good);
			listview_dangersgoodstype.setAdapter(dangersgoodAdapter);
			// listview_goodsname.setAdapter(goodsnameAdapter);
			pop_dis5.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
					popupWindowArea.dismiss();
					dangersgoodstype_lists.clear();

				}
			});		

			listview_dangersgoodstype.setOnItemClickListener(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					switch (parent.getId()){
					case R.id.listview_danger_good:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position) {
					// TODO Auto-generated method stub
					rank.setText(dangersgoodstype_lists.get(position).toString());
					rankid=position+1;
					dangersgoodstype_lists.clear();
					popupWindowArea.dismiss();
				}
			});


			LinearLayout parent5 = (LinearLayout) this
					.findViewById(R.id.parent_view2);// 父窗口view
			popupWindowArea = new PopupWindow(contentView,
					parent5.getWidth() * 4 / 5,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
			WindowManager.LayoutParams lp5 = getWindow().getAttributes();
			lp5.alpha = 0.5f;
			getWindow().setAttributes(lp5);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			popupWindowArea.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
				}
			});

			popupWindowArea.showAtLocation(parent5, Gravity.TOP, 0,
					parent5.getHeight() * 1 / 8);
			break;
 
		case R.id.relative6:
			unit_lists.clear();
			HttpRequest hruHttpRequest=new HttpRequest(new HttpRequest.onResult() 
			{				
				@Override
				public void onSuccess(String result) 
				{
					String string=result.replace("\\", "");
					String string2=string.substring(1,string.length()-1);
					try {
						JSONArray ar=new JSONArray(string2);
						for(int i=0;i<ar.length();i++)
						{
							JSONObject object=ar.getJSONObject(i);
							String nameString=object.getString("unitname");
							unit_lists.add(nameString);
						}
					} catch (Exception e) 
					{
						// TODO: handle exception
					}
					
					 listview_goods_units.setAdapter(unitAdapter);
				}
				
				@Override
				public void onError(int httpcode) 
				{
					// TODO Auto-generated method stub					
				}
			});	
			
			Map<String, Object> map=new HashMap<>();
			hruHttpRequest.post(contants.baseUrl+"DangerGoodsUnit", map);

			contentView = getLayoutInflater().inflate(R.layout.pop_goods_units,
					null);// 货物类型
			TextView pop_dis3 = (TextView) contentView
					.findViewById(R.id.pop_dis);
			listview_goods_units = (ListView) contentView
					.findViewById(R.id.listview_goods_units); 
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
			
			
			listview_goods_units.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					switch (parent.getId()){
					case R.id.listview_goods_units:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position) {
					// TODO Auto-generated method stub
					unit.setText(unit_lists.get(position).toString());
					unitid=position+1;
					unit_lists.clear();
					popupWindowArea.dismiss();
				}
			});

			LinearLayout parent3 = (LinearLayout) this
					.findViewById(R.id.parent_view2);// 父窗口view
			popupWindowArea = new PopupWindow(contentView,
					parent3.getWidth() * 4 / 5,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
			WindowManager.LayoutParams lp3 = getWindow().getAttributes();
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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

			popupWindowArea.showAtLocation(parent3, Gravity.TOP, 0,
					parent3.getHeight() * 1 / 8);
			break;

		case R.id.image_list://我的危货报告
			Intent intent_to_listRecord = new Intent();
			intent_to_listRecord.setClass(DangersLetInActivity.this, LetInRecordActivity.class);
			intent_to_listRecord.putExtra("shipname", ship_name.getText()
					.toString());
			SystemStatic.searchShipName = ship_name.getText().toString();
			startActivity(intent_to_listRecord);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.relative8:

			contentView = getLayoutInflater().inflate(R.layout.pop_time_choose, null); 
			final Dialog dialog = new Dialog(DangersLetInActivity.this,
					R.style.dialog);
			dialog.setContentView(contentView); 

			time_in = (TextView) contentView.findViewById(R.id.time_in);
			dialog.getWindow().setGravity(Gravity.BOTTOM);
			WindowManager windowManager = getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			WindowManager.LayoutParams lp9 = dialog.getWindow().getAttributes();
			lp9.width = WindowManager.LayoutParams.FILL_PARENT;
			// 设置透明度为0.3
			lp9.alpha = 1f;
			// lp.dimAmount=0.7f;
			dialog.getWindow().setAttributes(lp9);
			dialog.getWindow().setBackgroundDrawableResource(
					R.drawable.pop_shape);
			dialog.show();

			initWheelTime(contentView, time_will_post,dialog);

			break;

		}

	}

	private void initPopWindowShopName() {

		{
			contentView = getLayoutInflater().inflate(R.layout.pop_ship_name,
					null);// 船名
			listView_shapmane = (ListView) contentView
					.findViewById(R.id.listview_ship_name);
			listView_shapmane.setAdapter(nameAdapter);
			TextView pop_dis = (TextView) contentView
					.findViewById(R.id.pop_dis);

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
			
			
			listView_shapmane.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					switch (parent.getId()){
					case R.id.listview_ship_name:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position) {
					// TODO Auto-generated method stub
					ship_name.setText(ship_name_lists.get(position).toString());
					ship_name_lists.clear();
					popupWindowArea.dismiss();
				}
			});
			

			LinearLayout parent = (LinearLayout) this
					.findViewById(R.id.parent_view2);// 父窗口view
			popupWindowArea = new PopupWindow(contentView,
					parent.getWidth() * 4 / 5,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.alpha = 0.5f;
			getWindow().setAttributes(lp);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			popupWindowArea.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
				}
			});

			popupWindowArea.showAtLocation(parent, Gravity.TOP, 0,
					parent.getHeight() * 1 / 8);
		}

	}

	private void GetShipName() {

		//

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
		String toUrl = contants.baseUrl+"myshiplistPass";
		if (params == null) {
			// 提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		} else if (!toUrl.equals("")) {
			kjh.post(toUrl, params, false, new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					String result = new String(t).trim();
					try {
						ship_name_lists.clear();  
						JSONArray data = new JSONArray(result); 

						for (int i = 0; i < data.length(); i++) 
						{
							JSONObject temp = data.getJSONObject(i);
							String name = new String(); 

							name = temp.getString("shipname"); 
							ship_name_lists.add(name);
						 } 

						initPopWindowShopName();

						// MissionPauseHandler.sendEmptyMessage(Reagain_SERVICE_OK);
						// newsAdapter.pre(newslist);
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

	/**
	 * 提交申报接口
	 */

	private void SendRecord() 
	{
		String ship_name = this.ship_name.getText().toString();
		String from = this.from.getText().toString();
		String to = this.to.getText().toString();
		String goods = this.goods.getText().toString();
		String rank = this.rank.getText().toString();
		String unit = this.unit.getText().toString();
		String time = this.time_will_post.getText().toString();
		String tons = this.tons.getText().toString();

		if (ship_name.equals("") || from.equals("") || to.equals("")
				|| goods.equals("") || rank.equals("") || unit.equals("")
				|| time.equals("") || tons.equals("")) {
			Toast.makeText(getApplicationContext(), "请完善提交信息",
					Toast.LENGTH_SHORT).show();
		} else {
			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Shipname=" + ship_name);
			aa.add("From=" + fromid);
			aa.add("To=" + toid);
			aa.add("Goods=" + goods);
			aa.add("Rank=" + rankid);
			aa.add("Tons=" + tons);
			aa.add("Unit=" + unitid);
			aa.add("Time=" + time);
			aa.add("id=" + id);

			if (SystemStatic.recordID != null) {
				aa.add("Reportid=" + SystemStatic.recordID);
			} else {
				aa.add("Reportid=" + "");
			}

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.baseUrl+"CommitDangerShip";
			if (params == null) {
				// 提示参数制造失败
				Util.getTip(getApplicationContext(), "构造参数失败");
			} else if (!toUrl.equals("")) {
				loginDialog=new ProgressDialog(this);
				loginDialog.setMessage("数据上传中...");
				loginDialog.setCancelable(false);
				loginDialog.show();
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);

						Toast.makeText(getApplicationContext(), "提交成功",
								Toast.LENGTH_SHORT).show();
						Intent intent_ok=new Intent(getApplicationContext(),HomeActivity.class);
						startActivity(intent_ok);
						if(loginDialog!=null)
						{
							loginDialog.dismiss();
						}
					}

					@Override
					public void onFailure(int errorNo, String strMsg) {

						super.onFailure(errorNo, strMsg);
						if(loginDialog!=null)
						{
							loginDialog.dismiss();
						}
					}
				});
			}

		}
	}

	// /**
	// * 初始化日期滚轮数据
	// */
	//
	// private void initstring() {
	// //date_string = new ArrayList<String>();
	// hour_string = new ArrayList<String>();
	// second_string = new ArrayList<String>();
	//
	//
	// date_string = (ArrayList<String>) TimePicker(2013,1,1,10,0,true);
	// // date_string.add(StringData());
	// // date_string.add(StringData());
	// // date_string.add(StringData());
	// // date_string.add(StringData());
	// // date_string.add(StringData());
	//
	// for (int i = 0; i < 24; i++) {
	//
	// hour_string.add(i + " 时");
	//
	// }
	//
	// for (int i = 0; i < 60; i++) {
	// if (0 <= i && i < 10) {
	// second_string.add("0" + i + "分");
	// } else {
	// second_string.add(i + "分");
	// }
	// }
	//
	// }
	//
	// /**
	// * 获取当前日期星期
	// *
	// * @return
	// */
	//
	// public String StringData() {
	//
	// String mYear;
	// String mMonth;
	// String mDay;
	// String mWay;
	// final Calendar c = Calendar.getInstance();
	// c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	// mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
	// mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
	// mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
	// mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
	//
	// if ("1".equals(mWay)) {
	// mWay = "天";
	// } else if ("2".equals(mWay)) {
	// mWay = "一";
	// } else if ("3".equals(mWay)) {
	// mWay = "二";
	// } else if ("4".equals(mWay)) {
	// mWay = "三";
	// } else if ("5".equals(mWay)) {
	// mWay = "四";
	// } else if ("6".equals(mWay)) {
	// mWay = "五";
	// } else if ("7".equals(mWay)) {
	// mWay = "六";
	// }
	// return mMonth + "月" + mDay + "日" + "周" + mWay;
	// // return "星期"+mWay;
	//
	// }
	//
	// /**
	// * 计算每个月天数
	// */
	//
	// public int MonthsMax(int year, int month) {
	// Calendar calendar = Calendar.getInstance();
	// // int year = 2007;
	// // int month = Calendar.FEBRUARY;
	// int date = 1;
	// calendar.set(year, month, date); // 2007/2/1
	// int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	// System.out.println("Max Day: " + maxDay);
	// calendar.set(2004, Calendar.FEBRUARY, 1);// 2004/2/1
	// maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	// return maxDay;
	//
	// }
	//
	// /**
	// * 根据日期计算周几
	// */
	// String pTime = "2016-01-01";
	//
	// private String getWeek(String pTime) throws java.text.ParseException {
	//
	// String Week = "";
	//
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	// Calendar c = Calendar.getInstance();
	// try {
	// c.setTime(format.parse(pTime));
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 1) {
	// Week += "天";
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 2) {
	// Week += "一";
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 3) {
	// Week += "二";
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 4) {
	// Week += "三";
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 5) {
	// Week += "四";
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 6) {
	// Week += "五";
	// }
	// if (c.get(Calendar.DAY_OF_WEEK) == 7) {
	// Week += "六";
	// }
	//
	// return Week;
	// }
	//
	//
	// public List<String> TimePicker( int defaultYear,int defaultMonth,int
	// defaultDay,int defaultHour,int defaultMinute,boolean initStart){
	// Calendar calendar = Calendar.getInstance();
	// int year = calendar.get(Calendar.YEAR);
	// int month = calendar.get(Calendar.MONTH)+1;
	// int day = calendar.get(Calendar.DATE);
	// int hour = calendar.get(Calendar.HOUR_OF_DAY);
	// int minute = calendar.get(Calendar.MINUTE);
	// int second = calendar.get(Calendar.SECOND);
	//
	//
	//
	// // 添加大小月月份并将其转换为list,方便之后的判断
	// String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	// String[] months_little = { "4", "6", "9", "11" };
	// final List<String> list_big = Arrays.asList(months_big);
	// final List<String> list_little = Arrays.asList(months_little);
	// //
	// final List<String> textDMList = new ArrayList<String>();
	// final List<String> textDMDateList = new ArrayList<String>();
	// for(int i=1;i<13;i++){
	// if(list_big.contains(String.valueOf(i))){
	// for(int j=1;j<32;j++){
	// textDMList.add(year+"年"+i+"月"+" "+j+"日");
	// textDMDateList.add(defaultYear+"-"+i+"-"+j);
	// }
	// }else{
	// if(i==2){
	// if(AbDateUtil.isLeapYear(defaultYear)){
	// for(int j=1;j<28;j++){
	// textDMList.add(year+"年"+i+"月"+" "+j+"日");
	// textDMDateList.add(defaultYear+"-"+i+"-"+j);
	// }
	// }else{
	// for(int j=1;j<29;j++){
	// textDMList.add(year+"年"+i+"月"+" "+j+"日");
	// textDMDateList.add(defaultYear+"-"+i+"-"+j);
	// }
	// }
	// }else{
	// for(int j=1;j<29;j++){
	// textDMList.add(year+"年"+i+"月"+" "+j+"日");
	// textDMDateList.add(defaultYear+"-"+i+"-"+j);
	// }
	// }
	// }
	//
	// }
	// String currentDay = year+"年"+month+"月"+" "+day+"日";
	// int currentDayIndex = textDMList.indexOf(currentDay);
	//
	// SystemStatic.currentDayIndex=currentDayIndex;
	// return textDMList;
	//
	//
	// }

	public void initWheelTime(View mTimeView, TextView tv, Dialog dialog) {
		 mWheelViewMD = (AbWheelView) mTimeView
				.findViewById(R.id.wheelView1);
		 mWheelViewMM = (AbWheelView) mTimeView
				.findViewById(R.id.wheelView2);
		 mWheelViewHH = (AbWheelView) mTimeView
				.findViewById(R.id.wheelView3);

		int[] centerSelectGradientColors = new int[] { 0x00F8F8FF, 0x00F8F8FF,
				0x00F8F8FF };

		Button okBtn = (Button) mTimeView.findViewById(R.id.btn_ok);
		Button cancelBtn = (Button) mTimeView.findViewById(R.id.btn_cancel);
		// mWheelViewMD.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.bg_date));
		// mWheelViewMM.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.bg_date));
		// mWheelViewHH.setCenterSelectDrawable(this.getResources().getDrawable(R.drawable.bg_date));

		mWheelViewMD.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewMM.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewHH.setCenterSelectGradientColors(centerSelectGradientColors);
		
		
		mWheelViewMD.addChangingListener(this);
		mWheelViewMM.addChangingListener(this);
		mWheelViewHH.addChangingListener(this);
//		
//		mWheelViewMD.setBackgroundColor(Color.parseColor("#F5F5F5"));
//		mWheelViewMM.setBackgroundColor(Color.parseColor("#F5F5F5"));
//		mWheelViewHH.setBackgroundColor(Color.parseColor("#F5F5F5"));
		
	
		
		
		initWheelTimePicker(DangersLetInActivity.this, dialog, tv, okBtn,
				cancelBtn, mWheelViewMD, mWheelViewMM, mWheelViewHH, 2013, 1,
				1, 10, 0, true);
	}

	public void initWheelTimePicker(DangersLetInActivity dangersLetInActivity,
			final Dialog dialog, final TextView tv, Button okBtn,
			Button cancelBtn, final AbWheelView mWheelViewMD,
			final AbWheelView mWheelViewHH, final AbWheelView mWheelViewMM,
			int defaultYear, int defaultMonth, int defaultDay, int defaultHour,
			int defaultMinute, boolean initStart) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(1);
		int month = calendar.get(2) + 1;
		int day = calendar.get(5);
		int hour = calendar.get(11);
		int minute = calendar.get(12);
		int second = calendar.get(13);

		if (initStart) {
			defaultYear = year;
			defaultMonth = month;
			defaultDay = day;
			defaultHour = hour;
			defaultMinute = minute;
		}

		 val = AbStrUtil.dateTimeFormat(defaultYear + "-" + defaultMonth
				+ "-" + defaultDay + " " + defaultHour + ":" + defaultMinute
				+ ":" + second);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		List<String> list_big = Arrays.asList(months_big);
		List<String> list_little = Arrays.asList(months_little);

		List<String> textDMList = new ArrayList();
		textDMDateList = new ArrayList();
		for (int i = 1; i < 13; i++) {
			if (list_big.contains(String.valueOf(i))) {
				for (int j = 1; j < 32; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(defaultYear + "-" + i + "-" + j);
				}

			} else if (i == 2) {
				if (AbDateUtil.isLeapYear(defaultYear)) {
					for (int j = 1; j < 28; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(defaultYear + "-" + i + "-" + j);
					}
				} else {
					for (int j = 1; j < 29; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(defaultYear + "-" + i + "-" + j);
					}
				}
			} else {
				for (int j = 1; j < 29; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(defaultYear + "-" + i + "-" + j);
				}
			}
		}
		
		
		
		
		int next_year=defaultYear+1;
		for (int i = 1; i < 13; i++) {
			if (list_big.contains(String.valueOf(i))) {
				for (int j = 1; j < 32; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(next_year + "-" + i + "-" + j);
				}

			} else if (i == 2) {
				if (AbDateUtil.isLeapYear(next_year)) {
					for (int j = 1; j < 28; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(next_year + "-" + i + "-" + j);
					}
				} else {
					for (int j = 1; j < 29; j++) {
						textDMList.add(i + "月" + " " + j + "日");
						textDMDateList.add(next_year + "-" + i + "-" + j);
					}
				}
			} else {
				for (int j = 1; j < 29; j++) {
					textDMList.add(i + "月" + " " + j + "日");
					textDMDateList.add(next_year + "-" + i + "-" + j);
				}
			}
		}

		String currentDay = defaultMonth + "月" + " " + defaultDay + "日";
		int currentDayIndex = textDMList.indexOf(currentDay);

		mWheelViewMD.setAdapter(new AbStringWheelAdapter(textDMList, AbStrUtil
				.strLength("12月 12日")));
		mWheelViewMD.setCyclic(true);
		mWheelViewMD.setLabel("");
		mWheelViewMD.setCurrentItem(currentDayIndex);

		mWheelViewMD.setLabelTextSize(35);
		mWheelViewMD.setLabelTextColor(Integer.MIN_VALUE);
		mWheelViewMD.setAdditionalItemHeight(82);
		mWheelViewMD.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewMD.setValueTextSize(38);

		
		
		
		mWheelViewHH.setAdapter(new AbNumericWheelAdapter(0, 23));
		mWheelViewHH.setCyclic(true);
		mWheelViewHH.setLabel("点");
		mWheelViewHH.setCurrentItem(defaultHour);
		mWheelViewHH.setLabelTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewHH.setLabelTextSize(35);
		// mWheelViewHH.setLabelTextColor(Integer.MIN_VALUE);
		mWheelViewHH.setAdditionalItemHeight(82);
		mWheelViewHH.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewHH.setValueTextSize(38);

		
		
		
		
		
		mWheelViewMM.setAdapter(new AbNumericWheelAdapter(0, 59));
		mWheelViewMM.setCyclic(true);
		mWheelViewMM.setLabel("分");

		mWheelViewMM.setCurrentItem(defaultMinute);

		mWheelViewMM.setLabelTextSize(35);
		mWheelViewMM.setLabelTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewMM.setAdditionalItemHeight(82);
		mWheelViewMM.setValueTextColor(this.getResources().getColor(
				R.color.home_second_header_color));
		mWheelViewMM.setValueTextSize(38);

		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AbDialogUtil.removeDialog(v.getContext());
				int index1 = mWheelViewMD.getCurrentItem();
				int index2 = mWheelViewHH.getCurrentItem();
				int index3 = mWheelViewMM.getCurrentItem();

				String dmStr = textDMDateList.get(index1);
				Calendar calendar = Calendar.getInstance();
				int second = calendar.get(Calendar.SECOND);
				val = AbStrUtil.dateTimeFormat(dmStr + " " + index2
						+ ":" + index3);
				
				
				SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");       
				String date_now=sDateFormat.format(new java.util.Date());    //当前时间
				
				
				try {

		            Date beginTime=sDateFormat.parse(date_now);//当前时间
		            Date endTime=sDateFormat.parse(val);//所选择的时间
		            //判断是否大于
		            if(((endTime.getTime() - beginTime.getTime())<0)) {
		                Log.v("hi","所选择的时间应该在当前时间之后");
		                Toast.makeText(getApplicationContext(), "所选择的时间应该在当前时间之后", Toast.LENGTH_SHORT).show();
		            }else{
		                Log.v("hi","小于两天"); 
		                tv.setText(val);
						dialog.dismiss();
		                }

		        } catch (ParseException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
				
			}

		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});

	}

	@Override
	public void onChanged(AbWheelView wheel, int oldValue, int newValue) 
	{		
		if(wheel==mWheelViewMD)
		{
			int index1 = mWheelViewMD.getCurrentItem();
			int index2 = mWheelViewHH.getCurrentItem();
			int index3 = mWheelViewMM.getCurrentItem();

			String dmStr = textDMDateList.get(index1);
			Calendar calendar = Calendar.getInstance();
			int second = calendar.get(Calendar.SECOND);
			val = AbStrUtil.dateTimeFormat(dmStr + " " + index3
					+ ":" + index2);
			time_in.setText(val);
		}
		if(wheel==mWheelViewHH)
		{
			int index1 = mWheelViewMD.getCurrentItem();
			int index2 = mWheelViewHH.getCurrentItem();
			int index3 = mWheelViewMM.getCurrentItem();

			String dmStr = textDMDateList.get(index1);
			Calendar calendar = Calendar.getInstance();
			int second = calendar.get(Calendar.SECOND);
			val = AbStrUtil.dateTimeFormat(dmStr + " " + index3
					+ ":" + index2);
			time_in.setText(val);
		}
		if(wheel==mWheelViewMM)
		{
			int index1 = mWheelViewMD.getCurrentItem();
			int index2 = mWheelViewHH.getCurrentItem();
			int index3 = mWheelViewMM.getCurrentItem();

			String dmStr = textDMDateList.get(index1);
			Calendar calendar = Calendar.getInstance();
			int second = calendar.get(Calendar.SECOND);
			val = AbStrUtil.dateTimeFormat(dmStr + " " + index3
					+ ":" + index2);
			time_in.setText(val);
		}
		
	}
}
