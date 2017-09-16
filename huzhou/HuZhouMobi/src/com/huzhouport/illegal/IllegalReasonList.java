package com.huzhouport.illegal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.SharedPreferencesClass;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class IllegalReasonList extends Activity {
	private ImageButton eltap_back;
	private Button bt_his, bt_list;
	private ListView hislist, reasonlist;
	private ArrayList<HashMap<String, String>> resonlistdate;

	private AutoCompleteTextView searchtext; // ������
	private SharedPreferencesClass sp;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.illegal_reason_list);

		eltap_back = (ImageButton) findViewById(R.id.reasonlsit_back);

		bt_his = (Button) findViewById(R.id.reasonlsit_list_historybutton);
		bt_list = (Button) findViewById(R.id.reasonlsit_list_button);

		hislist = (ListView) findViewById(R.id.reasonlsit_list_historylist);
		reasonlist = (ListView) findViewById(R.id.reasonlsit_list);

		eltap_back.setOnClickListener(new MyBack());
		searchtext = (AutoCompleteTextView) findViewById(R.id.reasonlsit_autotext);

		bt_his.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (hislist.getVisibility() == 0) {
					hislist.setVisibility(View.GONE);
				} else {
					hislist.setVisibility(View.VISIBLE);
				}

			}
		});
		bt_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (reasonlist.getVisibility() == 0) {
					reasonlist.setVisibility(View.GONE);
				} else {
					reasonlist.setVisibility(View.VISIBLE);
				}

			}
		});
		// ������ť
		ImageButton search1 = (ImageButton) findViewById(R.id.reasonlsit_bt);
		search1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sp.setValueByKey(searchtext.getText().toString(),
						sp.key_illegal_search_resonname);
				new ListTask1().execute();

			}
		});

		sp = new SharedPreferencesClass(this);
		initAutoComplete();
		CreateHistoryList();// ��ʾ5���ڸ���ʷ����
		new ListTask1().execute();
	}

	// ��ʾ5���ڸ���ʷ����
	private void CreateHistoryList() {
		ArrayList<HashMap<String, Object>> historylist = new ArrayList<HashMap<String, Object>>();
		String[] hisresonid = sp.getValueByKey(sp.key_illegal_resonid).split(
				",");
		String[] hisresonname = sp.getValueByKey(sp.key_illegal_resonname)
				.split(",");
		if (sp.getValueByKey(sp.key_illegal_resonid).toString().length() == 0) {
			bt_his.setVisibility(View.GONE);
		} else {
			for (int i = 0; i < hisresonid.length; i++) {
				if (hisresonid[i].length() != 0) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", hisresonid[i]);
					map.put("name", hisresonname[i]);
					historylist.add(map);
				}
			}
			System.out.println("hisresonid:" + hisresonid.length);
			SimpleAdapter adapter = new SimpleAdapter(IllegalReasonList.this,
					historylist, R.layout.listview_item, new String[] { "name",
							"id" }, new int[] { R.id.listview_name,
							R.id.listview_id });
			if (hisresonid.length == 0) {
				View moreView = getLayoutInflater().inflate(
						R.layout.listview_noitem, null);
				hislist.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
			}
			hislist.setAdapter(adapter);

			hislist.setOnItemClickListener(new ListItemChoose());
		}
	}

	/**
	 * ��ʼ��AutoCompleteTextView�������ʾ5����ʾ��ʹ AutoCompleteTextView��һ��ʼ��ý���ʱ�Զ���ʾ
	 * 
	 * @param auto
	 *            Ҫ������AutoCompleteTextView
	 */
	private void initAutoComplete() {

		String[] hisArrays = sp.getValueByKey(sp.key_illegal_search_resonname)
				.split(",");
		if (sp.getValueByKey(sp.key_illegal_search_resonname).toString().length() != 0) {
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, hisArrays);
			// ֻ���������5���ļ�¼
			if (hisArrays.length > 5) {
				String[] newArrays = new String[5];
				System.arraycopy(hisArrays, 0, newArrays, 0, 5);
				adapter2 = new ArrayAdapter<String>(this,
						android.R.layout.simple_dropdown_item_1line, newArrays);
			}
			searchtext.setAdapter(adapter2);
			// searchtext.setDropDownHeight(auto);
			searchtext.setThreshold(1);
			// searchtext.setCompletionHint("�����5����¼");
			searchtext.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					AutoCompleteTextView view = (AutoCompleteTextView) v;
					if (hasFocus) {
						view.showDropDown();
					}
				}
			});
		}
	}

	// ѡ����
	class ListItemChoose implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView usertv_id = (TextView) arg1.findViewById(R.id.listview_id);
			TextView usertv_name = (TextView) arg1
					.findViewById(R.id.listview_name);
			sp.setValueByKey(usertv_id.getText().toString(), usertv_name
					.getText().toString(), sp.key_illegal_resonid,
					sp.key_illegal_resonname);
			Intent intent = new Intent();
			intent.putExtra("resonid", usertv_id.getText().toString());// ���ûش�����ͼ
			intent.putExtra("resonname", usertv_name.getText().toString());// ���ûش�����ͼ
			setResult(70, intent);
			finish();

		}
	}

	class MyBack implements OnClickListener {

		@Override
		public void onClick(View v) {

			finish();
		}
	}

	class ListTask1 extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask1() {

		}

		@SuppressWarnings("deprecation")
		public ListTask1(Context context) {
			pDialog = new ProgressDialog(IllegalReasonList.this);
			pDialog.setTitle("��ʾ");
			pDialog.setMessage("�������ڼ����У����Ժ򡤡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask1.this != null
							&& ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask1.this.cancel(true);

				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask1.this != null
							&& ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask1.this.cancel(true);
				}
			});
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String resultString = null;
			HttpFileUpTool htf = new HttpFileUpTool();
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			try {
				paramsDate.put("cruiselogid", searchtext.getText().toString());
				resultString = htf.post(HttpUtil.BASE_URL
						+ "searchIllegalReasonList", paramsDate);
			} catch (IOException e) {
				resultString = null;
				e.printStackTrace();
			}

			return resultString;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if (result == null) {
				Toast.makeText(IllegalReasonList.this, "�޷���ȡԵ������",
						Toast.LENGTH_SHORT).show();
			}
			showIllegalReason(result);

			super.onPostExecute(result);
		}

	}

	public void showIllegalReason(String result) {
		resonlistdate = new ArrayList<HashMap<String, String>>();
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");

			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				map.put("reasonName", jsonObject.getString("reasonName"));
				map.put("reasonId", jsonObject.getString("reasonId"));
				resonlistdate.add(map);
			}

		} catch (Exception e) {
			Toast.makeText(IllegalReasonList.this, "���ݻ�ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(IllegalReasonList.this,
				resonlistdate, R.layout.listview_item, new String[] {
						"reasonName", "reasonId" }, new int[] {
						R.id.listview_name, R.id.listview_id });
		reasonlist.setAdapter(adapter);
		reasonlist.setOnItemClickListener(new ListItemChoose());
	}

	// webservice�����ַ����Ƿ��������
	boolean containsAny(String str, String searchChars) {
		if (str.contains(searchChars))
			return true;
		else
			return false;
	}

}
