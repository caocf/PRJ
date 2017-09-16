package com.huzhouport.dangerousgoodsjob;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Σ��Ʒ��ͷ�ϱ�
 * 
 * @author Administrator
 * 
 */
public class DangerousgoodsjobAddList extends Activity
{
	private ListView lv;
	private EditText searchtext;
	private String content="";
	private Query query = new Query();
	private ArrayList<HashMap<String, Object>> dangerousgoodsjob;
	private String whatfName;
	private User user;
	private boolean issearch=false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_add_list);

		// �����¼�
		CommonListenerWrapper.commonBackWrapperListener(
				R.id.dangerousgoodsjob_back, this);

		lv = (ListView) findViewById(R.id.dangerousgoodsjob_viewlist);

		user = (User) getIntent().getSerializableExtra("User");
		whatfName = user.getWharfBindingList().get(user.getBindnum())
				.getWharfNum();

		new queryDangerTask(this).execute();

		findViewById(R.id.dangerousgoodsjob_search).setOnClickListener(new Search());
		findViewById(R.id.dangerousgoodsjob_search_searchbutton).setOnClickListener(new SearchButton());
		findViewById(R.id.dangerousgoodsjob_search_back).setOnClickListener(new NoSearch());
		findViewById(R.id.dangerousgoodsjob_add).setOnClickListener(new Add());
		(searchtext=(EditText)findViewById(R.id.dangerousgoodsjob_searchtext)).addTextChangedListener(new SearchDangerWatcher());

	}
	/**
	 * ���˰�ť�¼�����
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
		   if(issearch){
				searchtext.setText("");
				findViewById(R.id.dangerousgoodsjob_table).setVisibility(
						View.VISIBLE);
				findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
						View.INVISIBLE);
				content="";
				issearch=false;
				queryDangerTask task = new queryDangerTask(DangerousgoodsjobAddList.this); // �첽
				task.execute();
		   }else{
			   finish();
		   }

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * ��ʾ������
	 * @author Administrator
	 *
	 */
	class Search implements View.OnClickListener
	{
		public void onClick(View v)
		{
			findViewById(R.id.dangerousgoodsjob_table).setVisibility(
					View.INVISIBLE);
			findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
					View.VISIBLE);
			issearch=true;
		}
	}

	/**
	 * ȡ����������
	 * @author Administrator
	 *
	 */
	class NoSearch implements View.OnClickListener
	{
		public void onClick(View v)
		{
			searchtext.setText("");
			
			findViewById(R.id.dangerousgoodsjob_table).setVisibility(
					View.VISIBLE);
			findViewById(R.id.dangerousgoodsjob_table2).setVisibility(
					View.INVISIBLE);
		}
	}


	/**
	 * ������������
	 * @author Administrator
	 *
	 */
	class SearchButton implements View.OnClickListener
	{
		public void onClick(View v)
		{
			content = searchtext.getText().toString();
			queryDangerTask task = new queryDangerTask(DangerousgoodsjobAddList.this); // �첽
			task.execute();

		}
	}
	
	/**
	 * ������������
	 * @author Administrator
	 *
	 */
	class SearchDangerWatcher implements TextWatcher
	{
		@Override
		public void afterTextChanged(Editable s)
		{
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count)
		{
			content = searchtext.getText().toString();
			if(content.equals("")){
			queryDangerTask task = new queryDangerTask(DangerousgoodsjobAddList.this); // �첽
			task.execute();
			}
		}

	}

	public void getDangerousgoodsjob(String result)
	{

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			System.out.println("jsonArray===" + jsonArray.length() + jsonArray);
			if (jsonArray.length() == 0)
			{
				Toast.makeText(DangerousgoodsjobAddList.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
			}
			{
				dangerousgoodsjob = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> dangerousgoodsjobmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String text1 = jsonObject.getString("shipName");
					String text2 = jsonObject.getString("wharfID");
					String text3 = jsonObject.getString("declareTime")
							.substring(
									0,
									jsonObject.getString("declareTime")
											.lastIndexOf(":"));
					String text4 = jsonObject.getString("declareID");
					dangerousgoodsjobmap.put("text1", text1);
					dangerousgoodsjobmap.put("text2", text2);
					dangerousgoodsjobmap.put("text3", text3);
					dangerousgoodsjobmap.put("text4", text4);
					dangerousgoodsjob.add(dangerousgoodsjobmap);
				}
			}
		} catch (Exception e)
		{
			Toast.makeText(DangerousgoodsjobAddList.this, "��������",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public void showDangerousgoodsjob()
	{
		SimpleAdapter adapter = new SimpleAdapter(
				DangerousgoodsjobAddList.this, dangerousgoodsjob,
				R.layout.dangerousgoodsjob_item, new String[] { "text1",
						"text2", "text3", "text4" }, new int[] {
						R.id.dangerousgoodsjob_shipName,
						R.id.dangerousgoodsjob_wharfName,
						R.id.dangerousgoodsjob_declareTime,
						R.id.dangerousgoodsjob_declareID });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new DangerousgoodsportlnItem());
	}

	class DangerousgoodsportlnItem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.dangerousgoodsjob_declareID);
			Intent intent = new Intent(DangerousgoodsjobAddList.this,
					DangerousgoodsjobAddView.class);
			intent.putExtra("id", tv_id.getText().toString());
			startActivity(intent);

			new Log(user, "�鿴Σ�ջ�����ͷ��ҵ", GlobalVar.LOGSEE, "").execute(); 
		}

	}

	

	class Add implements View.OnClickListener
	{
		public void onClick(View v)
		{

			Intent intent = new Intent(DangerousgoodsjobAddList.this,
					DangerousgoodsjobAdd.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 100); // ��÷���ֵ �õ� Ȼ�� �жϷ���
		}
	}

	class queryDangerTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public queryDangerTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;

			String result;
			if (content.equals(""))
			{
				result = query.showDangerousByWharf(whatfName);
			} else
			{
				result = query.showDangerousByWharf(whatfName,content);
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			  if(result==null){
		      Toast.makeText(DangerousgoodsjobAddList.this, "�����쳣", Toast.LENGTH_SHORT).show();
		      }else{
			  getDangerousgoodsjob(result);
			  // ��ʾ�б���Ϣ
			  showDangerousgoodsjob();
		      }
			super.onPostExecute(result);
		}

	}

}
