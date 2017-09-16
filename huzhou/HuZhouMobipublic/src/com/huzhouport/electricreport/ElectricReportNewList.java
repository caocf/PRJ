package com.huzhouport.electricreport;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ElectricReportNewList extends Activity {
	private User user = new User();
	private Query query = new Query();
	private String shipName; // �û�id
	private ListView lv;
	private View moreView; // ���ظ���ҳ��
	private SimpleAdapter adapter;
	private int cpage = 1, maxpage, index = 0;
	private ImageButton ele_search, img_add, img_delete;
	private ArrayList<HashMap<String, Object>> showlist;
	private Button bt1, bt2;
	private PopupWindow popupWindow;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreport_list);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		shipName = user.getShipBindingList().get(user.getBindnum())
				.getShipNum();
		lv = (ListView) findViewById(R.id.electricreport_main_list);

		moreView = getLayoutInflater().inflate(R.layout.dateload, null);
		ShowList();

		ListTask task = new ListTask(this); // �첽
		task.execute();

		ImageButton back = (ImageButton) findViewById(R.id.electricreport_list_back);
		back.setOnClickListener(new Back());

		ele_search = (ImageButton) findViewById(R.id.electricreport_list_searchreportadd);
		ele_search.setOnClickListener(new Search());

		img_add = (ImageButton) findViewById(R.id.electricreport_list_addreport);
		img_add.setOnClickListener(new Add());
		img_delete = (ImageButton) findViewById(R.id.electricreport_list_delete);
		img_delete.setOnClickListener(new Delete());

		bt1 = (Button) findViewById(R.id.electricreport_addL_kind1);
		bt1.setOnClickListener(new Bt1());
		bt2 = (Button) findViewById(R.id.electricreport_addL_kind2);
		bt2.setOnClickListener(new Bt2());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ���Ը��ݶ���������������Ӧ�Ĳ���
		if (70 == resultCode) {
			finish();
		}
		if (170 == resultCode) {
			setResult(170);
			finish();
		}
	}

	public class Bt1 implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportNewAdd.class);
			intent.putExtra("User", user);
			intent.putExtra("kindid", "1");
			startActivityForResult(intent, 100);
		}
	}

	public class Bt2 implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportNewAdd.class);
			intent.putExtra("User", user);
			intent.putExtra("kindid", "2");
			startActivityForResult(intent, 100);
		}
	}

	public class Add implements View.OnClickListener {
		public void onClick(View v) {
			findViewById(R.id.electricreport_addL).setVisibility(View.VISIBLE);
			findViewById(R.id.electricreport_list_delete).setVisibility(
					View.VISIBLE);
			findViewById(R.id.electricreport_list_addreport).setVisibility(
					View.GONE);
		}
	}

	public class Delete implements View.OnClickListener {
		public void onClick(View v) {
			findViewById(R.id.electricreport_addL).setVisibility(View.GONE);
			findViewById(R.id.electricreport_list_delete).setVisibility(
					View.GONE);
			findViewById(R.id.electricreport_list_addreport).setVisibility(
					View.VISIBLE);
		}
	}

	// ����
	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportNewSearch.class);
			intent.putExtra("User", user);
			startActivity(intent);
			finish();
		}
	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}

	public void ShowList() {
		showlist = new ArrayList<HashMap<String, Object>>();
		adapter = new SimpleAdapter(ElectricReportNewList.this, showlist,
				R.layout.electricreportnew_item, new String[] { "text5",
						"text3", "text2", "text4", "text1", "text6", "img" },
				new int[] { R.id.electricreportnew_item_name,
						R.id.electricreportnew_item_time,
						R.id.electricreportnew_item_kind,
						R.id.electricreportnew_item_id,
						R.id.electricreportnew_item_port,
						R.id.electricreportnew_item_index,
						R.id.electricreportnew_item_img });
		lv.addFooterView(moreView); // ��ӵײ�view��һ��Ҫ��setAdapter֮ǰ��ӣ�����ᱨ��
		lv.setAdapter(adapter);
		// adapter.notifyDataSetChanged(); //ˢ������
		moreView.setVisibility(View.GONE);
		lv.setOnItemClickListener(new ShowView());

		lv.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// ��������ʱ
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					// �жϹ������ײ�
					if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
						moreView.setVisibility(View.VISIBLE);
						moreView.findViewById(R.id.progressBar2).setVisibility(
								View.VISIBLE);
						((TextView) moreView.findViewById(R.id.loadmore_text))
								.setText(R.string.more);
						LoadList();
					}
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				View pv = (View) ElectricReportNewList.this.getLayoutInflater()
						.inflate(R.layout.electricreport_popw, null);
				popupWindow = new PopupWindow(pv, LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				TextView poptv = (TextView) pv
						.findViewById(R.id.reportport_list_popw_tv2);
				poptv.setOnClickListener(new Poptv(arg2));
				// ��Ҫ��PopupWindow�еĿؼ��ܹ�ʹ�ã��ͱ�������PopupWindowΪfocusable
				popupWindow.setFocusable(true);

				// ������������PopupWindow���������ʱ����PopupWindow��ʧ����������������
				// 1������setOutsideTouchableΪture��������������ɣ���AlertDialogһ����
				popupWindow.setOutsideTouchable(true);
				// 2������PopupWindow�ı�������Ϊ�գ��������������ø������ɣ��㲻�õ��ı�����Ӱ�������ʾЧ��
				popupWindow.setBackgroundDrawable(getResources().getDrawable(
						R.color.white));

				// popupWindow.showAsDropDown(kind, 0, 0);
				popupWindow.showAtLocation(arg1, Gravity.LEFT | Gravity.TOP,
						getWindowManager().getDefaultDisplay().getWidth() / 4,
						getStateBar() + arg1.getHeight() * (arg2 + 1));
				return false;
			}
		});
	}

	private int getStateBar() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println("---------------------->" + statusBarHeight);
		return statusBarHeight;
	}

	public class Poptv implements OnClickListener {
		private String reportID;

		public Poptv(int arg2) {
			reportID = showlist.get(arg2).get("text4").toString();
		}

		@Override
		public void onClick(View v) {
			if (popupWindow != null && popupWindow.isShowing()) {
				popupWindow.dismiss();
			}
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportAddByCopy.class);
			intent.putExtra("User", user);
			intent.putExtra("reportID", reportID);
			startActivityForResult(intent, 100);
		}
	}

	private void LoadList() {
		if (cpage < maxpage) {
			cpage += 1;
			new ListTask(this).execute();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask(Context context) {
			if (cpage == 1) {
				pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// ȡ���첽

			String result = query.ElectricReportListByInfo(shipName, cpage);

			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (cpage == 1) {
				if (pDialog != null)
					pDialog.dismiss();
			}
			if (result == null) {
				Toast.makeText(ElectricReportNewList.this, "�����쳣",
						Toast.LENGTH_SHORT).show();
			} else {
				GetJson(result);

				adapter.notifyDataSetChanged();// ˢ��
				if (cpage >= maxpage) {
					moreView.setVisibility(View.GONE);
					lv.removeFooterView(moreView); // �Ƴ��ײ���ͼ
					// Toast.makeText(IllegalSearch.this, "�Ѽ���ȫ������",
					// 3000).show();
				}
				moreView.findViewById(R.id.progressBar2).setVisibility(
						View.GONE);
				((TextView) moreView.findViewById(R.id.loadmore_text))
						.setText(R.string.moreing);
			}
			super.onPostExecute(result);
		}
	}

	public void GetJson(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			maxpage = data.getInt("totalPage");
			JSONArray jsonArray = data.getJSONArray("list");
			if (jsonArray.length() == 0) {
				Toast.makeText(ElectricReportNewList.this,
						R.string.addresslist_nofind, Toast.LENGTH_LONG).show();
				findViewById(R.id.tableRow16).setVisibility(View.GONE);
			}
			{

				for (int i = 0; i < jsonArray.length(); i++) {
					HashMap<String, Object> showlistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					String text1 = jsonObject.getString("shipName");
					String text2 = CountTime.FormatTime2(jsonObject
							.getString("reportTime"));
					String text3 = "";
					if (jsonObject.getInt("reportKind") == 1) {
						text3 = "���ͣ��ش�����";
					} else {
						text3 = "���ͣ��մ�����";
					}
					String text4 = jsonObject.getString("reportID");
					String text5 = jsonObject.getString("startingPort") + "--"
							+ jsonObject.getString("arrivalPort");
					index = index + 1;
					String text6 = index + "";
					int img = R.drawable.abnormal;
					if (jsonObject.getInt("abnormal") == 1)
						img = R.drawable.isnormal;
					showlistmap.put("text1", text1);
					showlistmap.put("text2", text2);
					showlistmap.put("text3", text3);
					showlistmap.put("text4", text4);
					showlistmap.put("text5", text5);
					showlistmap.put("text6", text6);
					showlistmap.put("img", img);
					showlist.add(showlistmap);
				}
			}
		} catch (Exception e) {
			Toast.makeText(ElectricReportNewList.this, "��������",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			findViewById(R.id.tableRow16).setVisibility(View.GONE);
		}

	}

	class ShowView implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv_id = (TextView) arg1
					.findViewById(R.id.electricreportnew_item_id);
			TextView tv_index = (TextView) arg1
					.findViewById(R.id.electricreportnew_item_index);
			Intent intent = new Intent(ElectricReportNewList.this,
					ElectricReportNewShow.class);
			intent.putExtra("User", user);
			intent.putExtra("reportID", tv_id.getText().toString());
			intent.putExtra("index", tv_index.getText().toString());
			startActivityForResult(intent, 20);

			new Log(user, "�鿴���е��ӱ���", GlobalVar.LOGSEE, "").execute();
		}

	}

	

}
