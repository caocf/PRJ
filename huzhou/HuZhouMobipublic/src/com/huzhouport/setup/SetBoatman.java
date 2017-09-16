package com.huzhouport.setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SetBoatman extends Activity {
	private User user;
	private String shipName;
	private Query query = new Query();
	private ListView oldList;
	private ArrayList<HashMap<String, Object>> oldDat=new ArrayList<HashMap<String, Object>>();
	private BoatmanAdapter bAdapter;
	private LinearLayout layout;
	private int defaultCardID=0;
	private String defaultCardName="选择证书";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_boatman);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_boatman_back);
		ImageButton img_submit = (ImageButton) findViewById(R.id.setup_boatman_add);
		ImageButton img_add = (ImageButton) findViewById(R.id.setup_boatman_addImage);
		
		layout = (LinearLayout) findViewById(R.id.setup_boatman_addcontent); 
		
		oldList= (ListView) findViewById(R.id.setup_boatman_listview);
		
		user=(User) getIntent().getSerializableExtra("User");
		shipName = user.getShipBindingList().get(user.getBindnum()).getShipNum();

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		img_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SetBoatman.this,SelectBoatmanKind.class);
				startActivityForResult(intent, 20);
			}
		});
		
		img_submit.setOnClickListener(new Submit());

		new ListTask(this).execute();// 异步
		
		new Log(user, "查看绑定船员信息", GlobalVar.LOGSEE, "").execute();
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (20 == resultCode) {
			String kind = data.getStringExtra("kind"); // 船户职位id
			String kindId = data.getStringExtra("kindId"); // 船户职位名

			layout.addView(createView(kind, kindId));
		}
		if (40 == resultCode) {
			String cardId = data.getStringExtra("cardId"); // 船户证件id
			String cardName = data.getStringExtra("cardName"); // 船户证件名
			String DateKind = data.getStringExtra("DateKind"); // 是否是新数据  1.新增加的列表
			int position = Integer.parseInt(data.getStringExtra("position")); // 位置
			if(DateKind.equals("1")){
				for(int i=0;i<layout.getChildCount();i++){
					if(layout.getChildAt(i).getId()==position){
						//View view=layout.getChildAt(i);
						((TextView) layout.getChildAt(i).findViewById(R.id.boatkind1_cardID)).setText(cardId);
						((TextView) layout.getChildAt(i).findViewById(R.id.boatkind1_card)).setText(cardName);
						break;
					}
				}
				
			}else{
				oldDat.get(position).put("cardName", cardName);
				oldDat.get(position).put("cardID", cardId);
				oldList.setAdapter(bAdapter);
			}
		}
	}
	private View createView(String kind,String kindId){
		View view =LayoutInflater.from(this).inflate(R.layout.setup_boatman_item, null);//也可以从XML中加载布局 
		((TextView) view.findViewById(R.id.boatkind)).setText(kind);
		((TextView) view.findViewById(R.id.boatkindId)).setText(kindId);
		((TextView) view.findViewById(R.id.boatkind1_cardID)).setText(String.valueOf(defaultCardID));
		TextView tv_cardName=(TextView) view.findViewById(R.id.boatkind1_card);
		tv_cardName.setText(defaultCardName);
		final int vID=view.getId();
		tv_cardName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( null != v ) {
					Intent intent=new Intent(SetBoatman.this,SelectCard.class);
					intent.putExtra("DateKind", "1");//新增加的列表
					intent.putExtra("position", String.valueOf(vID));
					intent.putExtra("CardID", ((TextView)findViewById(R.id.boatkind1_cardID)).getText().toString());
					startActivityForResult(intent, 40);  
				}
			}
		});
		ImageButton deleteButton = (ImageButton) view
				.findViewById(R.id.setup_boatman_deletebutton);
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( null != v ) {
				      ViewGroup parent = ( ViewGroup ) v.getParent().getParent() ;
				      parent.removeView ( (View) v.getParent() ) ;
				}
			}
		});
		return view;
	}
	public class Submit implements View.OnClickListener {
		public void onClick(View v) {
			int a=0;
			//旧增加数据
			for(int i=0;i<oldList.getChildCount();i++){
				EditText et_name=(EditText) oldList.getChildAt(i).findViewById(R.id.setup_boatman_et);
				if(!CheckInfo(et_name.getText().toString(), "姓名")){
					a=1;
					break;
				}
				EditText et_card=(EditText) oldList.getChildAt(i).findViewById(R.id.setup_boatman_num);
				if(!CheckInfo(et_card.getText().toString(), "证件号")){
					a=1;
					break;
				}
			}
			//原数据
			for(int i=0;i<layout.getChildCount();i++){
				EditText et_name=(EditText) layout.getChildAt(i).findViewById(R.id.setup_boatman_et);
				if(!CheckInfo(et_name.getText().toString(), "姓名")){
					a=1;
					break;
				}
				EditText et_card=(EditText) layout.getChildAt(i).findViewById(R.id.setup_boatman_num);
				if(!CheckInfo(et_card.getText().toString(), "证件号")){
					a=1;
					break;
				}
			}
			if(a==0){
				new ListTask4(SetBoatman.this).execute();
			}
			
		}
	}

	public Boolean CheckInfo(String str, String msg) {
		if (str.length() == 0) {
			Toast.makeText(SetBoatman.this, msg + "不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else if (str.length() > 20) {
			Toast.makeText(SetBoatman.this, msg + "长度不能超过20位",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (str.indexOf(",")>=0) {
			Toast.makeText(SetBoatman.this, msg + "不能出现逗号",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if (str.indexOf(";")>=0) {
			Toast.makeText(SetBoatman.this, msg + "不能出现分号",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;

	}
	class ListTask4 extends AsyncTask<String, Integer, Boolean> {
		ProgressDialog pDialog = null;

		
		public ListTask4(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			Boolean result=true;
			if (isCancelled())
				return null;// 取消异步
			String actionUrl = HttpUtil.BASE_URL + "SetBoatUserInfo";
			
			
			HttpFileUpTool hft=new HttpFileUpTool();
			Map<String, Object> paramDateMap=new HashMap<String, Object>();
			String name="";
			String card="";
			String kindId="";
			String cardId="";
			//旧增加数据
			for(int i=0;i<oldList.getChildCount();i++){
				EditText et_name=(EditText) oldList.getChildAt(i).findViewById(R.id.setup_boatman_et);
				EditText et_card=(EditText) oldList.getChildAt(i).findViewById(R.id.setup_boatman_num);
				TextView tv_kindId=(TextView) oldList.getChildAt(i).findViewById(R.id.boatkindId);
				TextView tv_cardId=(TextView) oldList.getChildAt(i).findViewById(R.id.boatkind1_cardID);
				
				name+=et_name.getText().toString()+",";
				card+=et_card.getText().toString()+",";
				kindId+=tv_kindId.getText().toString()+",";
				cardId+=tv_cardId.getText().toString()+",";
			}
			//原数据
			for(int i=0;i<layout.getChildCount();i++){
				EditText et_name=(EditText) layout.getChildAt(i).findViewById(R.id.setup_boatman_et);
				EditText et_card=(EditText) layout.getChildAt(i).findViewById(R.id.setup_boatman_num);
				TextView tv_kindId=(TextView) layout.getChildAt(i).findViewById(R.id.boatkindId);
				TextView tv_cardId=(TextView) layout.getChildAt(i).findViewById(R.id.boatkind1_cardID);
				
				name+=et_name.getText().toString()+",";
				card+=et_card.getText().toString()+",";
				kindId+=tv_kindId.getText().toString()+",";
				cardId+=tv_cardId.getText().toString()+",";
			}
			paramDateMap.put("boatman.boatmanName", name);
			paramDateMap.put("boatman.boatmanShip", shipName);
			paramDateMap.put("boatman.boatCardNum",card);
			paramDateMap.put("boatman.kindList",kindId);
			paramDateMap.put("boatman.boatCardID",cardId);
			try {
				hft.post(actionUrl, paramDateMap);
				result=true;
			} catch (IOException e) {
				result=false;
				e.printStackTrace();
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (pDialog != null)
				pDialog.dismiss();
			if(result==true){
				finish();
			}else{
				Toast.makeText(SetBoatman.this, "船员信息提交失败，请重新提交", Toast.LENGTH_LONG).show();
			}
		
			super.onPostExecute(result);
		}

	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		
		public ListTask(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			return query.ShowBoatUserInfo(shipName);
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result);// 获得数据
			new GetFrstCard(SetBoatman.this).execute();// 异步
			super.onPostExecute(result);
		}

		public void getNeirong(String result) {

			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray jsonArray=data.getJSONArray("list");
				for(int i=0;i<jsonArray.length();i++){
					JSONArray jb=(JSONArray) jsonArray.opt(i);
					JSONObject jb_boatman=(JSONObject) jb.opt(0);
					JSONObject jb_post=(JSONObject) jb.opt(1);
					JSONObject jb_Cerd=(JSONObject) jb.opt(2);
					HashMap<String, Object> map=new HashMap<String, Object>();
					map.put("postName", jb_post.getString("boatmanKindName"));
					map.put("postId", jb_post.getString("boatmanKindID"));
					map.put("cardName", jb_Cerd.getString("cardName"));
					map.put("cardID", jb_Cerd.getString("cardID"));
					if(jb_boatman.getString("boatmanName")!=null&&jb_boatman.getString("boatmanName")!="null"){
						map.put("name", jb_boatman.getString("boatmanName"));
					}else{
						map.put("name","");
					}
					if(jb_boatman.getString("boatCardNum")!=null&&jb_boatman.getString("boatCardNum")!="null"){
						map.put("cardNum", jb_boatman.getString("boatCardNum"));
					}else{
						map.put("cardNum","");
					}
					oldDat.add(map);
				}
				
				bAdapter=new BoatmanAdapter(SetBoatman.this);
				oldList.setAdapter(bAdapter);
				
			} catch (Exception e) {
				Toast.makeText(SetBoatman.this, "无法获取船员信息", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
		}
	}
	class GetFrstCard extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		
		public GetFrstCard(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			return query.ShowBoatcardList();
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray jsonArray=data.getJSONArray("list");
				if(jsonArray.length()>0){
					JSONObject jb=(JSONObject) jsonArray.opt(0);
					defaultCardID=jb.getInt("cardID");
					defaultCardName=jb.getString("cardName");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}
	}
	
	public class BoatmanAdapter extends BaseAdapter {
		private LayoutInflater lin;

		public BoatmanAdapter(Context context) {
			
			lin = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return oldDat.size();
		}

		@Override
		public Object getItem(int arg0) {
			return oldDat.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = lin.inflate(R.layout.setup_boatman_item, null);

				ImageButton deleteButton = (ImageButton) convertView
						.findViewById(R.id.setup_boatman_deletebutton);
				TextView tv_postName = (TextView) convertView
						.findViewById(R.id.boatkind);
				TextView tv_postId = (TextView) convertView
						.findViewById(R.id.boatkindId);
				TextView tv_cardName = (TextView) convertView
						.findViewById(R.id.boatkind1_card);
				TextView tv_cardID = (TextView) convertView
						.findViewById(R.id.boatkind1_cardID);
				
				EditText et_name= (EditText) convertView
						.findViewById(R.id.setup_boatman_et);
				EditText et_cardNum = (EditText) convertView
						.findViewById(R.id.setup_boatman_num);

				Map<String, Object> map = oldDat.get(position);
				tv_postName.setText(map.get("postName").toString());
				tv_postId.setText(map.get("postId").toString());
				et_name.setText(map.get("name").toString());
				et_cardNum.setText(map.get("cardNum").toString());
				tv_cardName.setText(map.get("cardName").toString());
				tv_cardID.setText(map.get("cardID").toString());
				final int index=position;
				deleteButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						oldDat.remove(index);
						oldList.setAdapter(bAdapter);
					}
				});
				tv_cardName.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(SetBoatman.this,SelectCard.class);
						intent.putExtra("DateKind", "2");//原数据
						intent.putExtra("position", String.valueOf(index));//原数据位置
						intent.putExtra("CardID",((TextView)findViewById(R.id.boatkind1_cardID)).getText().toString());
						startActivityForResult(intent, 40);  
					}
				});
			}

			return convertView;
		}

	}

}
