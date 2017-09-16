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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.gh.modol.DuckDangerRecordModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.DuckDangerAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util; 

public class DuckDangersActivity extends Activity implements OnClickListener,AbOnWheelChangedListener
{	
	private RelativeLayout relative_dangers_goods_type,relative_goods_type,relative_goods_units,relative_start,relative_final,
	relative_ship_name,relative_time_choose,relative_title_final,relative_time_finish;

	public PopupWindow popupWindowArea;
	private Button btn_confirm;
	private View contentView;
	private ListView listView_shapmane,listview_goodsname,listview_dangersgoodstype,listview_goods_units
	,listview_start;
	private DuckDangerAdapter nameAdapter,goodsnameAdapter,unitAdapter,dangersgoodAdapter,fromAdapter;
	public ArrayList<String> ship_name_lists,goodsname_lists,dangersgoodstype_lists,unit_lists,
	from_lists; 
	public TextView ship_name,from,to,rank,unit,time,time_end,time_in;
	private EditText tons,edit_ship,goods;
	private ImageButton image_list;
	private ImageView back;
	 
	private ArrayList<String> date_string,hour_string,second_string;
	private List<String> textDMDateList;
	private AbWheelView mWheelViewMD,mWheelViewHH,mWheelViewMM;
	private String  val;
	
	int startportid,endportid,rankid,unitid=1,id=-1;
	ProgressDialog loginDialog = null;
	
	DuckDangerRecordModel model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_duck_dangerslitin);
		
		init();	
	}	
	
	private void init()
	{
		//承运船舶
		edit_ship=(EditText)findViewById(R.id.text30_context);
		
		//结束作业时间
		time_end=(TextView)findViewById(R.id.text10_context);
		
		//发货港
		relative_start=(RelativeLayout)findViewById(R.id.relative2);
		relative_start.setOnClickListener(this);
		
		//目的港
		relative_final=(RelativeLayout)findViewById(R.id.relative3);
		relative_final.setOnClickListener(this);
		
		//危货类型
		relative_dangers_goods_type=(RelativeLayout)findViewById(R.id.relative5);
		relative_dangers_goods_type.setOnClickListener(this);
		
		//货物名称
		relative_goods_type=(RelativeLayout)findViewById(R.id.relative4);
		relative_goods_type.setOnClickListener(this);
		
		//货物单位
		relative_goods_units=(RelativeLayout)findViewById(R.id.relative6);
		relative_goods_units.setOnClickListener(this);
		
		//作业码头
		relative_ship_name=(RelativeLayout)findViewById(R.id.relative1);
		relative_ship_name.setOnClickListener(this);
		
		//开始作业
		relative_time_choose=(RelativeLayout)findViewById(R.id.relative8);
		relative_time_choose.setOnClickListener(this);
		
		//结束作业时间
		relative_time_finish=(RelativeLayout)findViewById(R.id.relative10);
		relative_time_finish.setOnClickListener(this);
		
		//title
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(this);
		
		
		
		ship_name=(TextView)findViewById(R.id.text1_context);//作业码头 		
		
		from=(TextView)findViewById(R.id.text2_context);//始发港
		to=(TextView)findViewById(R.id.text3_context);//目的港
		goods=(EditText)findViewById(R.id.text4_context);//危险品货物名称
		rank=(TextView)findViewById(R.id.text5_context);//危货类型
		tons=(EditText)findViewById(R.id.text6_context);//危货重量
		unit=(TextView)findViewById(R.id.unit);//重量单位
		time=(TextView)findViewById(R.id.text8_context);//开始作业时间
		
		btn_confirm=(Button)findViewById(R.id.btn_confirm);//提交按钮
		btn_confirm.setOnClickListener(this);
		
		ship_name_lists=new ArrayList<String>();//码头列表
		goodsname_lists=new ArrayList<String>();//危险品货物名称列表
		unit_lists=new ArrayList<String>();//重量单位列表
		dangersgoodstype_lists=new ArrayList<String>();
		from_lists=new ArrayList<String>();//发货港，进货刚列表
		
		nameAdapter=new DuckDangerAdapter(this, ship_name_lists,1);
		goodsnameAdapter=new DuckDangerAdapter(this, goodsname_lists,2);
		unitAdapter=new DuckDangerAdapter(this, unit_lists,3);
		dangersgoodAdapter=new DuckDangerAdapter(this, dangersgoodstype_lists,4);
		fromAdapter=new DuckDangerAdapter(this, from_lists,5);
		
		
		image_list=(ImageButton)findViewById(R.id.image_list);//申报记录图标
		image_list.setOnClickListener(this);		
		
		Intent in = getIntent();
		
		model=(DuckDangerRecordModel) in.getSerializableExtra("DuckDangerRecordModel");
		if(model!=null)
		{		
			ship_name.setText(model.wharfnameString);
			
			SystemStatic.recordID = model.getid();
			id=Integer.valueOf(model.getid());
			
			String ship = model.getship();
			String startport = model.getstartport();			
			String targetport = model.gettargetport();
			String goodss = model.getgoods();
			String goodsweight = model.getgoodsweight();
			String goodstype = model.getgoodstype();
			String portime = model.getportime();		
			String endtime= model.getendtime();		
			String units=model.getunit();
			
			edit_ship.setText(ship);
			from.setText(startport);
			startportid=Integer.valueOf(model.startidString);
			to.setText(targetport);
			endportid=Integer.valueOf(model.tartgetidString);
			goods.setText(goodss);
			rank.setText(goodstype);
			rankid=Integer.valueOf(model.rankidString);
			tons.setText(goodsweight);
			unit.setText(units);
			unitid=Integer.valueOf(model.unitidString);
			time.setText(portime);
			time_end.setText(endtime);
			
			
			btn_confirm.setText("提交修改");		
			
		}
		
	}
	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		
		//点击提交按钮
		case R.id.btn_confirm:
			SendRecord();
			break;
		//点击返回	
		case R.id.relative_title_final:
			finish();
			break;
		
		//获取作业码头
		case R.id.relative1:
			 
			GetDuckName();
		
			break;
		//选择目的港	
		case R.id.relative3:
			PortPop(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{ 
					switch (parent.getId())
					{
					case R.id.listview_start:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position)
				{
					to.setText(from_lists.get(position).toString());
					endportid=position+1;
					from_lists.clear();
					popupWindowArea.dismiss();
				}
			});
			break;
		//先择始发港
		case R.id.relative2:
			PortPop(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
				{ 
					switch (parent.getId())
					{
					case R.id.listview_start:
						expressItemClick(position);//position 代表你点的哪一个
						break;
					}
					
				}
				private void expressItemClick(int position)
				{
					from.setText(from_lists.get(position).toString());
					startportid=position+1;
					from_lists.clear();
					popupWindowArea.dismiss();
				}
			});	
			
			break;		
			 
		//选择危货类型	
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
			  
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_dangers_goods, null);//货物类型
			 TextView pop_dis5 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_dangersgoodstype=(ListView) contentView.findViewById(R.id.listview_danger_good);
			  
				pop_dis5.setOnClickListener(new OnClickListener() 
				{
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
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{ 
						switch (parent.getId()){
						case R.id.listview_danger_good:
							expressItemClick(position);//position 代表你点的哪一个
							break;
						}
						
					}
					private void expressItemClick(int position) 
					{ 
						rank.setText(dangersgoodstype_lists.get(position).toString());
						rankid=position+1;
						dangersgoodstype_lists.clear();
						popupWindowArea.dismiss();
					}
				});

				LinearLayout parent5 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent5.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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

		//选择货物重量单位
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
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_goods_units, null);//货物类型
			 TextView pop_dis3 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_goods_units=(ListView) contentView.findViewById(R.id.listview_goods_units);
			
				pop_dis3.setOnClickListener(new OnClickListener() 
				{
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
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
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

				LinearLayout parent3 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent3.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				popupWindowArea.setBackgroundDrawable(new PaintDrawable());
				WindowManager.LayoutParams lp3 = getWindow().getAttributes();
				lp3.alpha = 0.5f;
				getWindow().setAttributes(lp3);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
			
		//提交的作业的列表	
		case R.id.image_list:
			Intent intent_to_listRecord=new Intent();
			intent_to_listRecord.setClass(getApplicationContext(), DuckDangersRecordActivity.class);			
				
			SystemStatic.Wharfname=ship_name.getText().toString();
			startActivity(intent_to_listRecord);
			break;
		//选择开始作业时间
		case R.id.relative8:
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
		//	View view = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
			final Dialog dialog = new Dialog(DuckDangersActivity.this, R.style.dialog);
			dialog.setContentView(contentView);
			
			Button button_ok=(Button)contentView.findViewById(R.id.btn_ok);
			Button button_cancel=(Button)contentView.findViewById(R.id.btn_cancel);
			TextView choose_time_title=(TextView)contentView.findViewById(R.id.choose_time_title);
			choose_time_title.setText("预计作业开始时间");
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
			dialog.getWindow().setBackgroundDrawableResource(R.drawable.pop_shape);			
			dialog.show();			

			
			//初始化滚轮时间数据
			initWheelTime(contentView,time, dialog,0);

			break;
		//选择结束作业时间	
		case R.id.relative10:
			
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
				//	View view = getLayoutInflater().inflate(R.layout.pop_time_choose, null);
					final Dialog dialog2 = new Dialog(DuckDangersActivity.this, R.style.dialog);
					dialog2.setContentView(contentView);
					
					Button button_ok2=(Button)contentView.findViewById(R.id.btn_ok);
					Button button_cancel2=(Button)contentView.findViewById(R.id.btn_cancel);
					time_in = (TextView) contentView.findViewById(R.id.time_in);
					TextView choose_time_title_end=(TextView)contentView.findViewById(R.id.choose_time_title);
					choose_time_title_end.setText("预计作业结束时间");
					dialog2.getWindow().setGravity(Gravity.BOTTOM);	
					WindowManager windowManager2 = getWindowManager();
					Display display2 = windowManager2.getDefaultDisplay();
					WindowManager.LayoutParams lp2= dialog2.getWindow().getAttributes();
					lp2.width = WindowManager.LayoutParams.FILL_PARENT;		
					// 设置透明度为0.3
					lp2.alpha = 1f;
					// lp.dimAmount=0.7f;
					dialog2.getWindow().setAttributes(lp2);
					dialog2.getWindow().setBackgroundDrawableResource(R.drawable.pop_shape);			
					dialog2.show();			


					
					initWheelTime(contentView,time_end, dialog2,1);
			
			
		}
		
	}
	
	private void PortPop(OnItemClickListener listener)
	{
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
		 Map<String,Object> map1=new HashMap<>();
		 
		 hrs.post(contants.baseUrl+"DangerPortList", map1);			
		
		 contentView = getLayoutInflater().inflate(R.layout.pop_from, null);//货物类型
		 TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
		 listview_start=(ListView) contentView.findViewById(R.id.listview_start);
		 
		
			pop_dis6.setOnClickListener(new OnClickListener() 
			{

				@Override
				public void onClick(View v) {
					
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
					popupWindowArea.dismiss();
					from_lists.clear();

				}
			});				
			
			listview_start.setOnItemClickListener(listener);

			LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
			popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
			WindowManager.LayoutParams lp6 = getWindow().getAttributes();
			lp6.alpha = 0.7f; 
			getWindow().setAttributes(lp6);
			popupWindowArea.setOnDismissListener(new OnDismissListener() 
			{

				@Override
				public void onDismiss() {
					WindowManager.LayoutParams lp = getWindow().getAttributes();
					lp.alpha = 1f;
					getWindow().setAttributes(lp);
				}
			});
			
			popupWindowArea.showAtLocation(parent6, Gravity.TOP, 0, parent6.getHeight()*1/8);
	}
	
	
	private void initPopWindowShopName()
	{		
		contentView = getLayoutInflater().inflate(R.layout.pop_ship_name, null);//
		listView_shapmane=(ListView)contentView.findViewById(R.id.listview_ship_name);
		listView_shapmane.setAdapter(nameAdapter);
		TextView pop_dis = (TextView) contentView.findViewById(R.id.pop_dis);
		TextView title=(TextView)contentView.findViewById(R.id.textView2);
		title.setText("请选择码头");
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
		
		
		LinearLayout parent = (LinearLayout) this.findViewById(R.id.parent_view2);//父窗口view  
		popupWindowArea = new PopupWindow(contentView, parent.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindowArea.setFocusable(true);
		popupWindowArea.setOutsideTouchable(true); 
		popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindowArea.setBackgroundDrawable(new PaintDrawable());
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
	
	
	
	private void GetDuckName() 
	{		
		//访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Username="+SystemStatic.userName);
		
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.baseUrl+"MyCompanyPass";
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kjh.post(toUrl, params,false,new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));			
					String result = new String(t).trim();
					try {
						
						ship_name_lists.clear();
						
						JSONArray data =new  JSONArray(result); 					
						
						for(int i = 0;i<data.length();i++)
						{
							JSONObject temp = data.getJSONObject(i);
							String name;  
							name=temp.getString("name");
							ship_name_lists.add(name);																																																																											
						} 
						
						initPopWindowShopName();
						
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
	 *提交申报接口
	 */
	
	private void SendRecord() 
	{
		String Wharfname=this.ship_name.getText().toString();//作业码头
		
		String Ship=this.edit_ship.getText().toString();//承运船舶
		String from=this.from.getText().toString();
		String to=this.to.getText().toString();
		String goods=this.goods.getText().toString();
		String rank=this.rank.getText().toString();
		String unit=this.unit.getText().toString();
		String time=this.time.getText().toString();
		String tons=this.tons.getText().toString();
		
		String time_ends=this.time_end.getText().toString();
		
		if(Wharfname.equals("")||Ship.equals("")||from.equals("")||to.equals("")||goods.equals("")||
				rank.equals("")||unit.equals("")||time.equals("")||tons.equals("")||time_ends.equals(""))
		{
			Toast.makeText(getApplicationContext(), "请完善提交信息", Toast.LENGTH_SHORT).show();
		}
		else{
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Wharfname="+Wharfname);	
		aa.add("Shipname="+Ship);
		aa.add("FromID="+startportid);
		aa.add("ToID="+endportid);
		aa.add("Goods="+goods);
		aa.add("RankID="+rankid);
		aa.add("Tons="+tons);
		aa.add("UnitID="+unitid);
		aa.add("StartTime="+time);
		aa.add("EndTime="+time_ends);
		aa.add("WorkManner="+"");
		aa.add("id="+id);
		
		if(SystemStatic.recordID!=null)
		{
			aa.add("Reportid="+SystemStatic.recordID);
		}else
		{
			aa.add("Reportid="+"" );
		}
		
		
		
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.baseUrl+"CommitDangerWork";
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			
			loginDialog=new ProgressDialog(this);
			loginDialog.setMessage("数据上传中...");
			loginDialog.setCancelable(false);
			loginDialog.show();
			
			kjh.post(toUrl, params,false,new HttpCallBack() 
			{
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) 
				{
					super.onSuccess(headers, t); 
					
					Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
					finish();
					
					if(loginDialog!=null)
						loginDialog.dismiss();
					
				}

				@Override
				public void onFailure(int errorNo, String strMsg)
				{
					
					super.onFailure(errorNo, strMsg);
					Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
					if(loginDialog!=null)
						loginDialog.dismiss();
				}
			});
		}
		
		}
	}
	
	public void initWheelTime(View mTimeView,TextView tv,Dialog dialog,int type){//type表示是选择开始时间还是选择结束时间
    	 mWheelViewMD = (AbWheelView)mTimeView.findViewById(R.id.wheelView1);
		 mWheelViewMM = (AbWheelView)mTimeView.findViewById(R.id.wheelView2);
		 mWheelViewHH = (AbWheelView)mTimeView.findViewById(R.id.wheelView3);
		
		 
		 mWheelViewMD.addChangingListener(this);
		 mWheelViewMM.addChangingListener(this);
		 mWheelViewHH.addChangingListener(this);
		
		int[] centerSelectGradientColors = new int[] { 0x00F8F8FF, 0x00F8F8FF,
				0x00F8F8FF };
		//mWheelViewHH.setBackgroundColor(this.getResources().getColor(R.color.popw_text_normal));
		
		
		Button okBtn = (Button)mTimeView.findViewById(R.id.btn_ok);
		Button cancelBtn = (Button)mTimeView.findViewById(R.id.btn_cancel);
		mWheelViewMD.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewMM.setCenterSelectGradientColors(centerSelectGradientColors);
		mWheelViewHH.setCenterSelectGradientColors(centerSelectGradientColors);
		
		
		
		
		initWheelTimePicker(type,DuckDangersActivity.this,dialog,tv,okBtn,cancelBtn, mWheelViewMD, mWheelViewMM, mWheelViewHH,2013,1,1,10,0,true);
    }
	
	
	
	public  void initWheelTimePicker(final int type,DuckDangersActivity dangersLetInActivity,final Dialog dialog,final TextView tv, Button okBtn, Button cancelBtn, final AbWheelView mWheelViewMD, final AbWheelView mWheelViewHH, final AbWheelView mWheelViewMM,  int defaultYear, int defaultMonth, int defaultDay, int defaultHour, int defaultMinute, boolean initStart)
	/*     */   {
	/* 228 */     Calendar calendar = Calendar.getInstance();
	/* 229 */     int year = calendar.get(1);
	/* 230 */     int month = calendar.get(2) + 1;
	/* 231 */     int day = calendar.get(5);
	/* 232 */     int hour = calendar.get(11);
	/* 233 */     int minute = calendar.get(12);
	/* 234 */     int second = calendar.get(13);
	/*     */     
	/* 236 */     if (initStart) {
	/* 237 */       defaultYear = year;
	/* 238 */       defaultMonth = month;
	/* 239 */       defaultDay = day;
	/* 240 */       defaultHour = hour;
	/* 241 */       defaultMinute = minute;
	/*     */     }
	/*     */     
	/* 244 */     val = AbStrUtil.dateTimeFormat(defaultYear + "-" + defaultMonth + "-" + defaultDay + " " + defaultHour + ":" + defaultMinute + ":" + second);
	/* 245 */    
	/*     */     
	/* 247 */     String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	/* 248 */     String[] months_little = { "4", "6", "9", "11" };
	/* 249 */     List<String> list_big = Arrays.asList(months_big);
	/* 250 */     List<String> list_little = Arrays.asList(months_little);
	/*     */     
	/* 252 */     List<String> textDMList = new ArrayList();
	/* 253 */    textDMDateList = new ArrayList();
	/* 254 */     for (int i = 1; i < 13; i++) {
	/* 255 */       if (list_big.contains(String.valueOf(i))) {
	/* 256 */         for (int j = 1; j < 32; j++) {
	/* 257 */           textDMList.add(i + "月" + " " + j + "日");
	/* 258 */           textDMDateList.add(defaultYear + "-" + i + "-" + j);
	/*     */         }
	/*     */         
	/* 261 */       } else if (i == 2) {
	/* 262 */         if (AbDateUtil.isLeapYear(defaultYear)) {
	/* 263 */           for (int j = 1; j < 28; j++) {
	/* 264 */             textDMList.add(i + "月" + " " + j + "日");
	/* 265 */             textDMDateList.add(defaultYear + "-" + i + "-" + j);
	/*     */           }
	/*     */         } else {
	/* 268 */           for (int j = 1; j < 29; j++) {
	/* 269 */             textDMList.add(i + "月" + " " + j + "日");
	/* 270 */             textDMDateList.add(defaultYear + "-" + i + "-" + j);
	/*     */           }
	/*     */         }
	/*     */       } else {
	/* 274 */         for (int j = 1; j < 29; j++) {
	/* 275 */           textDMList.add(i + "月" + " " + j + "日");
	/* 276 */           textDMDateList.add(defaultYear + "-" + i + "-" + j);
	/*     */         }
	/*     */       }
	/*     */     }
	/*     */     
	/*     */ 
	/* 282 */     String currentDay = defaultMonth + "月" + " " + defaultDay + "日";
	/* 283 */     int currentDayIndex = textDMList.indexOf(currentDay);
	/*     */     
	/*     */ 
	/* 286 */     mWheelViewMD.setAdapter(new AbStringWheelAdapter(textDMList, AbStrUtil.strLength("12月 12日")));
	/* 287 */     mWheelViewMD.setCyclic(true);
	/* 288 */     mWheelViewMD.setLabel("");
	/* 289 */     mWheelViewMD.setCurrentItem(currentDayIndex);
	/* 290 */     
	/* 291 */     mWheelViewMD.setLabelTextSize(35);
	/* 292 */     mWheelViewMD.setLabelTextColor(Integer.MIN_VALUE);
				  mWheelViewMD.setAdditionalItemHeight(82);
				  mWheelViewMD.setValueTextColor(this.getResources().getColor(R.color.home_second_header_color));
				  mWheelViewMD.setValueTextSize(38);
					

	/*     */     
	/*     */ 
	/*     */ 
	/* 296 */     mWheelViewHH.setAdapter(new AbNumericWheelAdapter(0, 23));
	/* 297 */     mWheelViewHH.setCyclic(true);
	/* 298 */     mWheelViewHH.setLabel("点");
	/* 299 */     mWheelViewHH.setCurrentItem(defaultHour);
	/* 300 */     mWheelViewHH.setLabelTextColor(this.getResources().getColor(R.color.home_second_header_color));
	/* 301 */     mWheelViewHH.setLabelTextSize(35);
	/* 302 */    // mWheelViewHH.setLabelTextColor(Integer.MIN_VALUE);
	      	      mWheelViewHH.setAdditionalItemHeight(82);
	/*     */     mWheelViewHH.setValueTextColor(this.getResources().getColor(R.color.home_second_header_color));
	/*     */ 	  mWheelViewHH.setValueTextSize(38);
	/*     */ 
	
	
	/* 306 */     mWheelViewMM.setAdapter(new AbNumericWheelAdapter(0, 59));
	/* 307 */     mWheelViewMM.setCyclic(true);
	/* 308 */     mWheelViewMM.setLabel("分");
				  
	/* 309 */     mWheelViewMM.setCurrentItem(defaultMinute);
	/* 310 */     
	/* 311 */     mWheelViewMM.setLabelTextSize(35);
	/* 312 */     mWheelViewMM.setLabelTextColor(this.getResources().getColor(R.color.home_second_header_color));
				  mWheelViewMM.setAdditionalItemHeight(82);
				  mWheelViewMM.setValueTextColor(this.getResources().getColor(R.color.home_second_header_color));
				  mWheelViewMM.setValueTextSize(38);
				
				  
	
	
	
			okBtn.setOnClickListener(new OnClickListener(){
		
				@Override
				public void onClick(View v) {
					AbDialogUtil.removeDialog(v.getContext());
					int index1 = mWheelViewMD.getCurrentItem();
					int index2 = mWheelViewHH.getCurrentItem();
					int index3 = mWheelViewMM.getCurrentItem();
					
					String dmStr =  textDMDateList.get(index1);
					Calendar calendar = Calendar.getInstance();
					int second = calendar.get(Calendar.SECOND);
					String val = AbStrUtil.dateTimeFormat(dmStr+" "+index2+":"+index3) ;
					
					/**
					 * 对所选择的时间进行判断，如果是预计作业开始时间则应该大于当前时间
					 * 
					 * 如果是预计作业结束时间应该大于预计作业开始时间
					 * 应先选择预计作业开始时间再选择预计作业结束时间
					 */
					if (type==0)
					{
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
					}else if(type==1){
						if(time.getText().toString().equals(""))
						{
							 Toast.makeText(getApplicationContext(), "请先选择预计作业开始时间", Toast.LENGTH_SHORT).show();
						}else{
							SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
							
							try {

					            Date beginTime=sDateFormat.parse(time.getText().toString());//预计作业开始时间
					            Date endTime=sDateFormat.parse(val);//所选择的时间
					            //判断是否大于
					            if(((endTime.getTime() - beginTime.getTime())<0)) {
					                Log.v("hi","所选择的时间应该大于预计作业开始时间");
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
						
						
					}
					
				}
				
			});
			
			cancelBtn.setOnClickListener(new OnClickListener(){
		
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
				
			});
			
	}
	
	
	
	@Override
	public void onChanged(AbWheelView wheel, int oldValue, int newValue) {
		Log.i("Time_change", "oldValue="+oldValue+";"+"newValue="+newValue);
		
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
