package com.huzhouport.setup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouportpublic.R;

import com.huzhouport.common.util.Query;
import com.huzhouport.electricreport.ElectricReportNewAdd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectBoatman extends Activity {
	private String shipName;
	private Query query = new Query();
	private ListView oldList;
	private ArrayList<HashMap<String, Object>> oldDat=new ArrayList<HashMap<String, Object>>();
	private BoatmanAdapter bAdapter;
	private String SelectIntem;
	private List<Integer> SelectIntemList= new ArrayList<Integer>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_boatman);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_boatman_back);
		ImageButton img_submit = (ImageButton) findViewById(R.id.setup_boatman_add);
				
		oldList= (ListView) findViewById(R.id.setup_boatman_listview);
		
		SelectIntem= getIntent().getStringExtra("SelectIntem");
		String[] selectList=SelectIntem.split(",");
		for(int i=0;i<selectList.length;i++){
			if(selectList[i].length()!=0) SelectIntemList.add(Integer.parseInt(selectList[i]));
		}
		
		shipName = getIntent().getStringExtra("shipName");

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		
		img_submit.setOnClickListener(new MarkSure());

		new ListTask(this).execute();// 异步
	}

	
	public class MarkSure implements View.OnClickListener {
		public void onClick(View v) {
			RetrueBack();
		}
	}
	
	public void RetrueBack(){
		Intent intent = new Intent();
		intent.setClass(SelectBoatman.this, ElectricReportNewAdd.class);
		String SelectName="";
		SelectIntem="";
		for(int i=0;i<SelectIntemList.size();i++){
			if(i>0) {SelectName+=";";SelectIntem+=",";}
			SelectIntem+=String.valueOf(SelectIntemList.get(i));
			SelectName+=oldDat.get(SelectIntemList.get(i)).get("postName")+","+
					oldDat.get(SelectIntemList.get(i)).get("name")+","+
					oldDat.get(SelectIntemList.get(i)).get("cardName")+","+
					oldDat.get(SelectIntemList.get(i)).get("cardNum");
		}
		intent.putExtra("SelectIntem", SelectIntem);
		intent.putExtra("SelectName", SelectName);
		setResult(60, intent);
		finish();
	}
	

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context) {
			pDialog = new ProgressDialog(SelectBoatman.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("正在加载中，请稍候。。。");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
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

			super.onPostExecute(result);
		}

		public void getNeirong(String result) {

			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				JSONArray jsonArray=data.getJSONArray("list");
				//SelectIntem=‘-1’
				if(SelectIntem.equals("-1")) NoIntemNum(jsonArray);
				for(int i=0;i<jsonArray.length();i++){
					JSONArray jb=(JSONArray) jsonArray.opt(i);
					JSONObject jb_boatman=(JSONObject) jb.opt(0);
					JSONObject jb_post=(JSONObject) jb.opt(1);
					JSONObject jb_Cerd=(JSONObject) jb.opt(2);
					HashMap<String, Object> map=new HashMap<String, Object>();
					map.put("postName", jb_post.getString("boatmanKindName"));
					map.put("postId", jb_post.getString("boatmanKindID"));
					map.put("cardName", jb_Cerd.getString("cardName"));
					
					map.put("img", R.drawable.checkbox_no);
					for(int j=0;j<SelectIntemList.size();j++){
						if(SelectIntemList.get(j)==i){
							map.put("img", R.drawable.checkbox_yes);
							break;
						}
					}
					
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
				
				bAdapter=new BoatmanAdapter(SelectBoatman.this);
				oldList.setAdapter(bAdapter);
				
			} catch (Exception e) {
				Toast.makeText(SelectBoatman.this, "无法获取船员信息", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
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
				convertView = lin.inflate(R.layout.select_boatman_item, null);

				ImageButton deleteButton = (ImageButton) convertView
						.findViewById(R.id.setup_boatman_deletebutton);
				TextView tv_postName = (TextView) convertView
						.findViewById(R.id.boatkind);
				TextView tv_postId = (TextView) convertView
						.findViewById(R.id.boatkindId);
				TextView tv_cardName= (TextView) convertView
						.findViewById(R.id.boatkind1_card);
				TextView et_name= (TextView) convertView
						.findViewById(R.id.setup_boatman_et);
				TextView tv_cardNum = (TextView) convertView
						.findViewById(R.id.setup_boatman_num);

				Map<String, Object> map = oldDat.get(position);
				tv_postName.setText(map.get("postName").toString());
				tv_postId.setText(map.get("postId").toString());
				
								et_name.setText(map.get("name").toString());
				tv_cardNum.setText(map.get("cardNum").toString());
				tv_cardName.setText(map.get("cardName").toString());
				deleteButton.setImageResource((Integer)map.get("img"));
				final Integer index=position;
				deleteButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						boolean img=false;
						for(int j=0;j<SelectIntemList.size();j++){
							if(SelectIntemList.get(j)==index){
								img=true;
								SelectIntemList.remove(j);
								break;
							}
						}
						ImageButton dbt = (ImageButton) v.findViewById(R.id.setup_boatman_deletebutton);
						if(img) dbt.setImageResource(R.drawable.checkbox_no);
						else{ 
							dbt.setImageResource(R.drawable.checkbox_yes);
							SelectIntemList.add(index);
						}
					}
				});
			}

			return convertView;
		}

	}
	private void NoIntemNum(JSONArray jsonArray){
		String[] NoIntemNum=getIntent().getStringExtra("NoIntemNum").split(";");
		SelectIntemList.clear();
		try {
			for(int i=0;i<jsonArray.length();i++){
				JSONArray jb=(JSONArray) jsonArray.opt(i);
				JSONObject jb_boatman=(JSONObject) jb.opt(0);
				JSONObject jb_post=(JSONObject) jb.opt(1);
				JSONObject jb_Cerd=(JSONObject) jb.opt(2);
				
				String str = jb_post.getString("boatmanKindName")+","+jb_boatman.getString("boatmanName")+","
							+jb_Cerd.getString("cardName")+","+jb_boatman.getString("boatCardNum");
				for(int j=0;j<NoIntemNum.length;j++){
					if(NoIntemNum[j].equals(str)){
						SelectIntemList.add(i);
						break;
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
