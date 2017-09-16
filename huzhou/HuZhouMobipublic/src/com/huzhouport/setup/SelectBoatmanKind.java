package com.huzhouport.setup;

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

public class SelectBoatmanKind extends Activity
{

	private ListView kindlist;

	private List<Map<String, Object>> boatmanList_data;

	private ClearEditText searchtext; // ËÑË÷¿ò


	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.boatmankind);

		findViewById(R.id.boatmankind_back).setOnClickListener(new Back());


		searchtext = (ClearEditText) findViewById(R.id.boatmankind_edit);
		ImageButton search1 = (ImageButton) findViewById(R.id.boatmankind_bt);
		search1.setOnClickListener(new searchOrBack());

		kindlist = (ListView) findViewById(R.id.boatmankind_list);
	

		kindlist.setOnItemClickListener(new selectKindListener());
		
		new queryKindList(this).execute("");
	}
	
	
	
	class Back implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			finish();
		}
	}


	/*-------------------------ÄÚÈÝËÑË÷--------------------------------------*/

	// ËÑË÷
	class searchOrBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			String content = searchtext.getText().toString();

			new queryKindList(SelectBoatmanKind.this).execute(content);
		}

	}

	/*----------------------------¼àÌý·½·¨---------------------------------*/

	class selectKindListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			Intent intent = new Intent();
			intent.putExtra("kind",  boatmanList_data.get(arg2).get("kind")
					.toString());

			intent.putExtra("kindId", boatmanList_data.get(arg2).get("kindId")
					.toString());

			setResult(20, intent);
			finish();
		}
	}

	

	/*--------------------------²éÑ¯¸ÛÇø-----------------------------------*/

	class queryKindList extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public queryKindList(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			return new Query().queryBoatmanKindList(params[0]);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			parseBoatmanKindList(result);
			showBoatmanKindList();
		}

	}

	private void parseBoatmanKindList(String result)
	{
		if (result == null)
			return;

		try
		{
			boatmanList_data = new ArrayList<Map<String, Object>>();

			JSONObject json = new JSONObject(result);
			JSONArray array = json.getJSONArray("list");

			for (int i = 0; i < array.length(); i++)
			{
				json = array.getJSONObject(i);

				Map<String, Object> temp = new HashMap<String, Object>();
				temp.put("kindId", json.getString("boatmanKindID"));
				temp.put("kind", json.getString("boatmanKindName"));

				boatmanList_data.add(temp);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void showBoatmanKindList()
	{
		if (boatmanList_data == null)
			return;

		SimpleAdapter adapter = new SimpleAdapter(SelectBoatmanKind.this,
				boatmanList_data, R.layout.dangerousgoodsjob_add_additem,
				new String[] { "kindId", "kind" }, new int[] {
						R.id.dangerousgoodsjob_wharfID,
						R.id.dangerousgoodsjob_addwharfName });
		kindlist.setAdapter(adapter);

	}


}
