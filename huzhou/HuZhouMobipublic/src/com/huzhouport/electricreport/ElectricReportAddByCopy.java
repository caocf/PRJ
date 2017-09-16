package com.huzhouport.electricreport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.POPWindow;
import com.huzhouport.common.util.Query;
import com.huzhouport.electricreport.ElectricReportAddWharf;
import com.huzhouport.model.User;
import com.huzhouport.setup.SelectBoatman;
import com.huzhouport.time.WheelMain;
import com.huzhouport.wharfwork.WharfWorkAddGoods;
import com.huzhouport.wharfwork.WharfWorkAddUniti;

public class ElectricReportAddByCopy extends Activity
{
	private Query query = new Query();
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv15,
			tv16, tv17, title;
	private String shipName, kindid;
	private User user;
	private PopupWindow dialog;
	private String username, goodsname, uniti;
	private EditText et8, et11;

	private ImageButton submit, back;

	private String reportID;
	private int kind;
	private Spinner sp_out;
	private ListView lv_boatman;
	private SimpleAdapter Boatmanadapter;
	private ArrayList<Map<String, String>> listv = new ArrayList<Map<String, String>>();
	private String ShipInfo = "";
	private String SelectIntem = "-1";// ��һ��ѡ�񴬲���Ա�޷���֪
	private int num = 0;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreportnew_add);

		user = (User) getIntent().getSerializableExtra("User");

		username = user.getUserName();
		reportID = getIntent().getStringExtra("reportID");
		shipName = user.getShipBindingList().get(user.getBindnum())
				.getShipNum();
		ListTask task = new ListTask(this); // �첽
		task.execute();

		title = (TextView) findViewById(R.id.electricreport_see_titleText);
		tv1 = (TextView) findViewById(R.id.electricreport_see_name);
		tv2 = (TextView) findViewById(R.id.electricreportnew_show_kind);
		tv3 = (TextView) findViewById(R.id.electricreport_see_shipUserName);
		tv4 = (TextView) findViewById(R.id.electricreport_see_arrival);
		tv5 = (TextView) findViewById(R.id.electricreport_show_address1);
		tv15 = (TextView) findViewById(R.id.electricreport_show_address1_ID);
		tv6 = (TextView) findViewById(R.id.electricreport_show_address2);
		tv16 = (TextView) findViewById(R.id.electricreport_show_address2_ID);
		tv7 = (TextView) findViewById(R.id.electricreport_see_type);
		et8 = (EditText) findViewById(R.id.electricreport_see_cargoNumbers);
		tv8 = (TextView) findViewById(R.id.electricreport_add_uniti);
		tv9 = (TextView) findViewById(R.id.electricreport_show_pier);
		tv10 = (TextView) findViewById(R.id.electricreport_show_arrivaltime);
		et11 = (EditText) findViewById(R.id.electricreport_show_draft);
		tv17 = (TextView) findViewById(R.id.electricreport_show_drafttime);

		lv_boatman = (ListView) findViewById(R.id.setup_boatman_tv);
		back = (ImageButton) findViewById(R.id.electricreport_see_back);
		back.setOnClickListener(new Back());

		sp_out = (Spinner) findViewById(R.id.electricreport_add_outorin);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.electricvisa_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_out.setAdapter(adapter);

		tv5.setCompoundDrawables(null, null, null, null);
		// �Ƿ��Ǻ��ݴ���
		tv5.addTextChangedListener(new PortChange());
		tv6.addTextChangedListener(new PortChange());
		tv6.setOnClickListener(new AddPort2());
		tv7.setOnClickListener(new Selectgoods());
		tv8.setOnClickListener(new AddUniti());
		tv9.setOnClickListener(new AddWharf());
		tv10.setOnClickListener(new ShowDate(10));
		tv17.setOnClickListener(new ShowDate(17));

		submit = (ImageButton) findViewById(R.id.electricreport_see_add);
		submit.setOnClickListener(new Submit());

		ImageButton submitadd = (ImageButton) findViewById(R.id.electricreportnew_add_submit);
		submitadd.setOnClickListener(new SubmitAdd());
		ImageButton addback = (ImageButton) findViewById(R.id.electricreportnew_add_back);
		addback.setOnClickListener(new AddBack());

		onKeyDown(KeyEvent.KEYCODE_BACK, null);// ���ؼ�
		num = 0;
	}

	class PortChange implements TextWatcher
	{

		@Override
		public void afterTextChanged(Editable s)
		{

			if (kind == 1)
			{
				if (tv5.getText().toString().indexOf("����") < 0
						&& tv6.getText().toString().indexOf("����") < 0)
				{
					((TableRow) findViewById(R.id.electricreport_add_tr_outorin))
							.setVisibility(View.GONE);
					((TableRow) findViewById(R.id.electricreport_add_tr_wharf))
							.setVisibility(View.GONE);
				} else
				{
					((TableRow) findViewById(R.id.electricreport_add_tr_outorin))
							.setVisibility(View.VISIBLE);
					((TableRow) findViewById(R.id.electricreport_add_tr_wharf))
							.setVisibility(View.VISIBLE);
				}
			}

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

		}

	}

	// �������ؼ�
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (back.getVisibility() == 8)
			{
				title.setText("�������е��ӱ���");
				back.setVisibility(View.VISIBLE);
				submit.setVisibility(View.VISIBLE);
				findViewById(R.id.electricreportnew_add_rl2).setVisibility(
						View.GONE);
			} else
			{
				if (num == 0)
				{
					num = 1;
				} else
				{
					finish();
				}
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// ���Ը��ݶ���������������Ӧ�Ĳ���
		if (20 == resultCode)
		{
			tv9.setText(data.getStringExtra("text2"));
		}
		if (30 == resultCode)
		{
			goodsname = data.getStringExtra("name");
			tv7.setText(goodsname);
		}
		if (40 == resultCode)
		{
			uniti = data.getStringExtra("unit");
			tv8.setText(uniti);
		}
		if (50 == resultCode)
		{
			if (data.getStringExtra("kind").equals("1"))
			{
				tv5.setText(data.getStringExtra("name"));
				tv15.setText(data.getStringExtra("id"));
			} else
			{
				tv6.setText(data.getStringExtra("name"));
				tv16.setText(data.getStringExtra("id"));
			}

		}
		if (60 == resultCode)
		{
			SelectIntem = data.getStringExtra("SelectIntem");
			ShipInfo = data.getStringExtra("SelectName");
			if (ShipInfo.trim().length() == 0)
			{
				listv.clear();
				Boatmanadapter.notifyDataSetChanged();
				return;
			}
			String[] ShipInfoList = ShipInfo.split(";");
			listv.clear();
			for (int i = 0; i < ShipInfoList.length; i++)
			{
				Map<String, String> map = new HashMap<String, String>();
				String[] ShipInfoList2 = ShipInfoList[i].split(",");
				map.put("value", ShipInfoList2[0] + ":" + ShipInfoList2[1]
						+ "(" + ShipInfoList2[2] + ":" + ShipInfoList2[3] + ")");
				listv.add(map);
			}
			Boatmanadapter.notifyDataSetChanged();
		}
		if (125 == resultCode)
		{
			shipName = data.getStringExtra("name");
			tv1.setText(data.getStringExtra("name"));
			listv.clear();
			Boatmanadapter.notifyDataSetChanged();
		}

	}

	class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{

			finish();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// ȡ���첽

			String result = query.ElectricReportByReportId(reportID);

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			parseContentAndShow(result);
			new StartPortShow(ElectricReportAddByCopy.this).execute();
			super.onPostExecute(result);
		}

	}

	public void parseContentAndShow(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jArray = data.getJSONArray("list");
			if (jArray.length() > 0)
			{
				JSONObject jsonArray = (JSONObject) jArray.opt(0);
				tv1.setText(jsonArray.getString("shipName"));
				kindid = jsonArray.getInt("reportKind") + "";
				if (jsonArray.getInt("reportKind") == 1)
				{
					kind = 1;
					tv2.setText("�ش�����");
					tv7.setText(jsonArray.getString("cargoType"));
					et8.setText(jsonArray.getString("cargoNumber"));
					tv8.setText(jsonArray.getString("uniti"));
				} else
				{
					kind = 2;
					tv2.setText("�մ�����");
					findViewById(R.id.electricreport_show_tr1).setVisibility(
							View.GONE);
					findViewById(R.id.electricreport_show_tr2).setVisibility(
							View.GONE);
					findViewById(R.id.electricreport_add_tr_wharf)
							.setVisibility(View.GONE);
				}
				tv3.setText(jsonArray.getString("shipUserName"));
				tv4.setText(jsonArray.getString("reportTime").substring(0,
						jsonArray.getString("reportTime").lastIndexOf(":")));
				// tv5.setText(jsonArray.getString("startingPort"));
				tv6.setText(jsonArray.getString("arrivalPort"));
				tv9.setText(jsonArray.getString("toBeDockedAtThePier"));
				tv10.setText(CountTime.FormatTime2(jsonArray
						.getString("estimatedTimeOfArrival")));
				et11.setText(jsonArray.getString("draft"));

				if (jsonArray.getInt("outOrIn") == 1)
				{
					sp_out.setSelection(0);
				} else
				{
					sp_out.setSelection(1);
				}
				String shipInfo = jsonArray.getString("shipInfo");
				if (!shipInfo.equals("null"))
				{
					ShipInfo = shipInfo;
					String[] sul = shipInfo.split(";");
					listv = new ArrayList<Map<String, String>>();
					for (int i = 0; i < sul.length; i++)
					{
						Map<String, String> map = new HashMap<String, String>();
						map.put("value",
								sul[i].split(",")[0] + ":"
										+ sul[i].split(",")[1] + "("
										+ sul[i].split(",")[2] + ":"
										+ sul[i].split(",")[3] + ")");
						listv.add(map);
					}
					Boatmanadapter = new SimpleAdapter(
							ElectricReportAddByCopy.this, listv,
							R.layout.electric_item, new String[] { "value" },
							new int[] { R.id.electric_item });

					lv_boatman.setAdapter(Boatmanadapter);
				}
				ImageButton img_selectboatman = (ImageButton) findViewById(R.id.electricreport_add_selectBoatman);
				img_selectboatman.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent(
								ElectricReportAddByCopy.this,
								SelectBoatman.class);
						intent.putExtra("SelectIntem", SelectIntem);
						intent.putExtra("NoIntemNum", ShipInfo);
						intent.putExtra("shipName", tv1.getText());
						startActivityForResult(intent, 60);
					}
				});
				tv17.setText(CountTime.FormatTime2(jsonArray
						.getString("draftTime")));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	class ShowDate implements OnClickListener
	{
		private int textnum = 0;

		public ShowDate(int i)
		{
			textnum = i;
		}

		@Override
		public void onClick(View v)
		{
			WindowManager wm = (WindowManager) ElectricReportAddByCopy.this
					.getSystemService(Context.WINDOW_SERVICE);
			@SuppressWarnings("deprecation")
			int width = wm.getDefaultDisplay().getWidth();//

			dialog = new PopupWindow(v, width, 350);

			// �ҵ�dialog�Ĳ����ļ�
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.leaveandovertime_time_layout,
					null);

			final WheelMain main = new WheelMain(dialog, view);
			main.showDateTimePicker(v);

			Button btn_sure = (Button) view
					.findViewById(R.id.btn_datetime_sure);
			Button btn_cancel = (Button) view
					.findViewById(R.id.btn_datetime_cancel);
			// ȷ��
			btn_sure.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{

					dialog.dismiss();
					if (textnum == 10)
					{
						tv10.setText(main.getTime());
					} else
					{
						tv17.setText(main.getTime());
					}
				}
			});
			// ȡ��
			btn_cancel.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					dialog.dismiss();
				}
			});

		}

	}

	class AddWharf implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ElectricReportAddByCopy.this,
					ElectricReportAddWharf.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 80);
		}
	}

	public class Selectgoods implements View.OnClickListener
	{
		public void onClick(View v)
		{

			Intent intent = new Intent(ElectricReportAddByCopy.this,
					WharfWorkAddGoods.class);

			startActivityForResult(intent, 100);

		}
	}

	class AddUniti implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ElectricReportAddByCopy.this,
					WharfWorkAddUniti.class);
			// intent.putExtra("User", user);
			startActivityForResult(intent, 90);
		}
	}

	class AddPort1 implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ElectricReportAddByCopy.this,
					ElectricReportNewAddPort.class);
			intent.putExtra("kind", "1");
			// intent.putExtra("User", user);
			startActivityForResult(intent, 90);
		}
	}

	class AddPort2 implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ElectricReportAddByCopy.this,
					ElectricReportNewAddPort.class);
			intent.putExtra("kind", "2");

			startActivityForResult(intent, 90);
		}
	}

	class Submit implements OnClickListener
	{

		public void onClick(View v)
		{
			// ���Ҫ����ֵ
			if (tv5.getText().toString().equals(""))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ��ʼ����",
						Toast.LENGTH_SHORT).show();
			} else if (tv6.getText().toString().equals(""))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ��Ŀ�ĸ�",
						Toast.LENGTH_SHORT).show();
			} else if (tv7.getText().toString().equals("")
					&& kindid.equals("1"))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ���ػ�����",
						Toast.LENGTH_SHORT).show();
			} else if (et8.getText().toString().equals("")
					&& kindid.equals("1"))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "�������ػ�����",
						Toast.LENGTH_SHORT).show();
			} else if (!Query.isNumberAndMax(et8.getText().toString())
					&& kindid.equals("1"))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "�ػ��������ܳ���65���",
						Toast.LENGTH_SHORT).show();
			} else if (kind == 1
					&& ((tv5.getText().toString().indexOf("����") >= 0 || tv6
							.getText().toString().indexOf("����") >= 0) && tv9
							.getText().toString().equals("")))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ�񱾴���ҵ��ͷ",
						Toast.LENGTH_SHORT).show();
			} else if (et11.getText().length() != 0
					&& !et11.getText().toString().equals("0")
					&& tv17.getText().toString().equals(""))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ���������ʱ��",
						Toast.LENGTH_SHORT).show();
			} else if (listv.size() <= 0)
			{
				Toast.makeText(ElectricReportAddByCopy.this, "����������һ����Ա��Ϣ",
						Toast.LENGTH_SHORT).show();
			} else
			{
				title.setText("ȷ��ҳ��");
				back.setVisibility(View.GONE);
				submit.setVisibility(View.GONE);
				findViewById(R.id.electricreportnew_add_rl2).setVisibility(
						View.VISIBLE);
			}

		}
	}

	class AddSubmit extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public AddSubmit(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;// ȡ���첽
			String actionUrl = HttpUtil.BASE_URL + "SaveElectricReport";
			Map<String, Object> param1 = new HashMap<String, Object>();
			String draft = et11.getText().toString();
			if (et11.getText().toString().equals(""))
			{
				draft = "0";
			}
			param1.put("electricReportNew.shipName", shipName);
			param1.put("electricReportNew.draft", draft);
			param1.put("electricReportNew.shipUser", user.getUserId());
			param1.put("electricReportNew.shipUserName", username);
			param1.put("electricReportNew.estimatedTimeOfArrival", tv10
					.getText().toString());
			param1.put("electricReportNew.toBeDockedAtThePier", tv9.getText()
					.toString());
			param1.put("electricReportNew.cargoType", tv7.getText().toString());
			param1.put("electricReportNew.cargoNumber", et8.getText()
					.toString());
			param1.put("electricReportNew.uniti", tv8.getText().toString());
			param1.put("electricReportNew.reportKind", kindid);
			param1.put("electricReportNew.startingPort", tv5.getText()
					.toString());
			param1.put("electricReportNew.arrivalPort", tv6.getText()
					.toString());
			param1.put("electricReportNew.outOrIn",
					sp_out.getSelectedItemPosition() + 1);
			param1.put("electricReportNew.draftTime", tv17.getText().toString());
			param1.put("electricReportNew.shipInfo", ShipInfo);

			HttpFileUpTool htp = new HttpFileUpTool();
			String result = null;
			try
			{
				result = htp.post(actionUrl, param1, 60);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			new Log(user, "��Ӻ��е��ӱ���", GlobalVar.LOGSAVE, "").execute();

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			if (result != null)
			{

				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				try
				{
					data = (JSONObject) jsonParser.nextValue();
					String returnValue = data.getString("returnValue");
					// showNoticeDialog(data.getString("returnValue"));
					new POPWindow(ElectricReportAddByCopy.this,
							R.layout.popup_elect_dialog,
							R.id.electricreport_see_shipUserName_tr,
							R.id.popup_elect_sure, closePopupDialog,
							new int[] { R.id.popup_elect_content },
							new String[] { returnValue });
				} catch (Exception e)
				{
					showNoticeDialog("�ύʧ��");
					e.printStackTrace();
				}

			} else
			{
				showNoticeDialog("�ύʧ��");
			}
			super.onPostExecute(result);
		}

	}

	private void showNoticeDialog(String ReturnValue)
	{
		new AlertDialog.Builder(ElectricReportAddByCopy.this).setTitle("������ʾ")
				.setMessage(ReturnValue)
				.setPositiveButton("ȡ��", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				})
				.setNegativeButton("ȷ��", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						Intent intent = new Intent(
								ElectricReportAddByCopy.this,
								ElectricReportNewList.class);
						intent.putExtra("User", user);
						startActivity(intent);
						setResult(170);
						finish();
					}
				}).create().show();
	}

	private OnClickListener closePopupDialog = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ElectricReportAddByCopy.this,
					ElectricReportNewList.class);
			intent.putExtra("User", user);
			startActivity(intent);
			setResult(70);
			finish();
		}

	};

	/**
	 * ��ʾ��ʾ�ύ�Ի���
	 */

	class AddBack implements View.OnClickListener
	{
		public void onClick(View v)
		{
			title.setText("�������е��ӱ���");
			back.setVisibility(View.VISIBLE);
			submit.setVisibility(View.VISIBLE);
			findViewById(R.id.electricreportnew_add_rl2).setVisibility(
					View.GONE);

		}
	}

	class SubmitAdd implements View.OnClickListener
	{
		public void onClick(View v)
		{
			// ���Ҫ����ֵ
			if (tv5.getText().toString().equals(""))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ����ʼ��",
						Toast.LENGTH_SHORT).show();
			} else if (tv6.getText().toString().equals(""))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ��Ŀ�ĸ�",
						Toast.LENGTH_SHORT).show();
			} else if (tv7.getText().toString().equals("")
					&& kindid.equals("1"))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ���ػ�����",
						Toast.LENGTH_SHORT).show();
			} else if (et8.getText().toString().equals("")
					&& kindid.equals("1"))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "�������ػ�����",
						Toast.LENGTH_SHORT).show();
			} else if (!Query.isNumberAndMax(et8.getText().toString())
					&& kindid.equals("1"))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "�ػ��������ܳ���65���",
						Toast.LENGTH_SHORT).show();
			} else if (kind == 1
					&& ((tv5.getText().toString().indexOf("����") >= 0 || tv6
							.getText().toString().indexOf("����") >= 0) && tv9
							.getText().toString().equals("")))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ�񱾴���ҵ��ͷ",
						Toast.LENGTH_SHORT).show();
			} else if (et11.getText().length() != 0
					&& !et11.getText().toString().equals("0")
					&& tv17.getText().toString().equals(""))
			{
				Toast.makeText(ElectricReportAddByCopy.this, "��ѡ���������ʱ��",
						Toast.LENGTH_SHORT).show();
			} else if (listv.size() <= 0)
			{
				Toast.makeText(ElectricReportAddByCopy.this, "����������һ����Ա��Ϣ",
						Toast.LENGTH_SHORT).show();
			} else
			{

				AddSubmit task = new AddSubmit(ElectricReportAddByCopy.this); // �첽
				task.execute();
			}
		}
	}

	class StartPortShow extends AsyncTask<String, Integer, String>
	{

		public StartPortShow()
		{

		}

		public StartPortShow(Context context)
		{

		}

		@Override
		protected String doInBackground(String... params)
		{
			return query.showStartPort(shipName);
		}

		@Override
		protected void onPostExecute(String result)
		{

			getStartPort(result);// �������

			super.onPostExecute(result);
		}

	}

	public void getStartPort(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			if (((JSONObject) data.get("electricReportNew")).getString(
					"arrivalPort").equals("null"))
			{
				tv5.setOnClickListener(new AddPort1());
			} else
			{
				tv5.setText(((JSONObject) data.get("electricReportNew"))
						.getString("arrivalPort"));
			}
		} catch (Exception e)
		{
			Toast.makeText(ElectricReportAddByCopy.this, "�����쳣",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


}