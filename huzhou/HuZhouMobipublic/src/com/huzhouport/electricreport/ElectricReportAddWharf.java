package com.huzhouport.electricreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

public class ElectricReportAddWharf extends Activity
{
	private User user;

	private ListView elt_portlist;
	private ListView elt_pierlist;
	private ListView elt_wharflist;

	private List<Map<String, Object>> elt_port_data;
	private List<Map<String, Object>> elt_pier_data;
	private List<Map<String, Object>> elt_wharf_data;

	private ClearEditText searchtext; // 搜索框

	int count;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.electricreport_addpier);

		findViewById(R.id.electricreport_addpier_back).setOnClickListener(new Back());

		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");

		searchtext = (ClearEditText) findViewById(R.id.electricreport_addpier_edit01);
		ImageButton search1 = (ImageButton) findViewById(R.id.electricreport_addpier_bt);
		search1.setOnClickListener(new searchOrBack());

		elt_portlist = (ListView) findViewById(R.id.electricreport_main_portlist);
		elt_pierlist = (ListView) findViewById(R.id.electricreport_main_pierlist);
		elt_wharflist = (ListView) findViewById(R.id.electricreport_main_wharflist);

		elt_portlist.setOnItemClickListener(new selectPortListener());
		elt_pierlist.setOnItemClickListener(new selectPieceListener());
		elt_wharflist.setOnItemClickListener(new selectWharfListener());

		new queryPortTask(this).execute();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
			back();
		
		return true;
	}
	
	
	class Back implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			back();
		}
	}

	private void back()
	{
		switch (count)
		{
		case -1:
			elt_portlist.setVisibility(View.VISIBLE);
			elt_pierlist.setVisibility(View.GONE);
			elt_wharflist.setVisibility(View.GONE);
			count=0;
			break;

		case 0:
			finish();
			break;
			
		case 1:
			elt_portlist.setVisibility(View.VISIBLE);
			elt_pierlist.setVisibility(View.GONE);
			elt_wharflist.setVisibility(View.GONE);
			count=0;
			break;
			
		case 2:
			elt_portlist.setVisibility(View.GONE);
			elt_pierlist.setVisibility(View.VISIBLE);
			elt_wharflist.setVisibility(View.GONE);
			count=1;
			break;
			
		}
	}

	/*-------------------------内容搜索--------------------------------------*/

	// 搜索
	class searchOrBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			count = -1;

			String content = searchtext.getText().toString();

			new queryWharfTask(ElectricReportAddWharf.this, -1, content)
					.execute();
		}

	}

	/*----------------------------监听方法---------------------------------*/

	class selectPortListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			count = 1;
			int id = Integer.valueOf(elt_port_data.get(arg2).get("id")
					.toString());

			new queryPieceTask(ElectricReportAddWharf.this, id).execute();
		}
	}

	class selectPieceListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			count = 2;
			int id = Integer.valueOf(elt_pier_data.get(arg2).get("id")
					.toString());

			new queryWharfTask(ElectricReportAddWharf.this, id, null).execute();
		}

	}

	class selectWharfListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			Intent intent = new Intent();
			intent.putExtra("User", user);

			intent.putExtra("text2", elt_wharf_data.get(arg2).get("mc")
					.toString());

			setResult(20, intent);
			finish();
		}

	}

	/*--------------------------查询港区-----------------------------------*/

	class queryPortTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public queryPortTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			return new Query().querySelectPort();
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			parsePort(result);
			showPort();
		}

	}

	private void parsePort(String result)
	{
		if (result == null)
			return;

		try
		{
			elt_port_data = null;
			elt_port_data = new ArrayList<Map<String, Object>>();

			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("portAreas");

			for (int i = 0; i < array.length(); i++)
			{
				json = array.getJSONObject(i);

				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", json.getString("id"));
				temp.put("name", json.getString("name"));

				elt_port_data.add(temp);
			}
		} catch (Exception e)
		{
		}

	}

	private void showPort()
	{
		if (elt_port_data == null)
			return;

		SimpleAdapter adapter = new SimpleAdapter(ElectricReportAddWharf.this,
				elt_port_data, R.layout.dangerousgoodsjob_add_additem,
				new String[] { "id", "name" }, new int[] {
						R.id.dangerousgoodsjob_wharfID,
						R.id.dangerousgoodsjob_addwharfName });
		elt_portlist.setAdapter(adapter);

		elt_portlist.setVisibility(View.VISIBLE);
		elt_pierlist.setVisibility(View.GONE);
		elt_wharflist.setVisibility(View.GONE);
	}

	/*---------------------------查询片区----------------------------------*/

	class queryPieceTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		int id;

		public queryPieceTask(Context context, int id)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();

			this.id = id;
		}

		@Override
		protected String doInBackground(String... params)
		{
			return new Query().querySelectPiece(id);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			parsePiece(result);
			showPiece();
		}

	}

	private void parsePiece(String result)
	{
		if (result == null)
			return;

		try
		{
			elt_pier_data = null;
			elt_pier_data = new ArrayList<Map<String, Object>>();

			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("pieceAreas");

			for (int i = 0; i < array.length(); i++)
			{
				json = array.getJSONObject(i);

				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", json.getString("id"));
				temp.put("name", json.getString("name"));

				elt_pier_data.add(temp);
			}
		} catch (Exception e)
		{
		}

	}

	private void showPiece()
	{
		if (elt_pier_data == null)
			return;

		SimpleAdapter adapter = new SimpleAdapter(ElectricReportAddWharf.this,
				elt_pier_data, R.layout.dangerousgoodsjob_add_additem,
				new String[] { "id", "name" }, new int[] {
						R.id.dangerousgoodsjob_wharfID,
						R.id.dangerousgoodsjob_addwharfName });
		elt_pierlist.setAdapter(adapter);

		elt_portlist.setVisibility(View.GONE);
		elt_pierlist.setVisibility(View.VISIBLE);
		elt_wharflist.setVisibility(View.GONE);
	}

	/*---------------------------查询码头----------------------------------*/

	class queryWharfTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;
		int id;
		String content;

		public queryWharfTask(Context context, int id, String content)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();

			this.id = id;
			this.content = content;
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (content != null && !content.equals(""))
				return new Query().querySelectWharfByContent(content, -1, -1);

			return new Query().querySelectWharf(id);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			parseWharf(result);
			showWharf();
		}

	}

	private void parseWharf(String result)
	{
		if (result == null)
			return;

		try
		{
			elt_wharf_data = null;
			elt_wharf_data = new ArrayList<Map<String, Object>>();

			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("wharfItems");

			for (int i = 0; i < array.length(); i++)
			{
				json = array.getJSONObject(i);

				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("id", json.getString("id"));
				temp.put("mc", json.getString("mc"));

				elt_wharf_data.add(temp);
			}
		} catch (Exception e)
		{
		}

	}

	private void showWharf()
	{
		if (elt_wharf_data == null)
			return;

		SimpleAdapter adapter = new SimpleAdapter(ElectricReportAddWharf.this,
				elt_wharf_data, R.layout.dangerousgoodsjob_add_additem,
				new String[] { "id", "mc" }, new int[] {
						R.id.dangerousgoodsjob_wharfID,
						R.id.dangerousgoodsjob_addwharfName });
		elt_wharflist.setAdapter(adapter);

		elt_portlist.setVisibility(View.GONE);
		elt_pierlist.setVisibility(View.GONE);
		elt_wharflist.setVisibility(View.VISIBLE);
	}

}
