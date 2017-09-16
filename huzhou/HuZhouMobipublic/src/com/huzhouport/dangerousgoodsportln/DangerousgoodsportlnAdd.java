package com.huzhouport.dangerousgoodsportln;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.Log;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.electricreport.ElectricReportNewAddPort;
import com.huzhouport.model.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Σ��Ʒ��������
 * 
 * @author Administrator
 * 
 */
public class DangerousgoodsportlnAdd extends Activity
{
	private String shipName;
	private String actionUrl, param1;

	private Spinner spinner_kind; // ������
	private int[] timeList = new int[5]; // ʱ��
	private TextView ShowportTime, shipNameText;
	private User user;

	TextView declareTime;
	TextView startingPortName;
	TextView arrivalPortName;
	EditText cargoTypes;
	EditText carrying;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsportln_add_add);

		user = (User) getIntent().getSerializableExtra("User");
		shipName = user.getShipBindingList().get(user.getBindnum())
				.getShipNum();

		declareTime = (TextView) findViewById(R.id.dangerousgoodsportln_add_declareTimeNeirong);
		startingPortName = (TextView) findViewById(R.id.dangerousgoodsportln_add_StartingPortNeirong);
		arrivalPortName = (TextView) findViewById(R.id.dangerousgoodsportln_add_ArrivalPortNeirong);
		cargoTypes = (EditText) findViewById(R.id.dangerousgoodsportln_add_CargoTypesNeirong);
		carrying = (EditText) findViewById(R.id.dangerousgoodsportln_add_CarryingNeirong);

		spinner_kind = (Spinner) findViewById(R.id.dangerousgoodsportln_add_DangerousLevelNeirong);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.dangerousgoodsportln_DangerousLevel_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);

		shipNameText = (TextView) findViewById(R.id.dangerousgoodsportln_add_shipNameNeirong);
		shipNameText.setText(shipName);
		shipNameText.setOnClickListener(new AddShipName());

		// ��ȡ��ǰʱ��
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date curDate = new Date();
		String declareTimes = formatter.format(curDate);
		declareTime.setText(declareTimes);

		// ����ʱ��
		String[] substring = declareTimes.split(" ");
		String[] substring1 = substring[0].split("-");
		String[] substring2 = substring[1].split(":");
		timeList[0] = Integer.parseInt(substring1[0]);
		timeList[1] = Integer.parseInt(substring1[1]);
		timeList[2] = Integer.parseInt(substring1[2]);
		timeList[3] = Integer.parseInt(substring2[0]);
		timeList[4] = Integer.parseInt(substring2[1]);

		ShowportTime = (TextView) findViewById(R.id.dangerousgoodsportln_add_portTimeNeirong);
		ShowportTime.setOnClickListener(new ShowportTime());

		CommonListenerWrapper.commonBackWrapperListener(
				R.id.dangerousgoodsportln_addback, this);

		// �ύ��ť
		ImageButton sumbit = (ImageButton) findViewById(R.id.dangerousgoodsportln_add_submit);
		sumbit.setOnClickListener(new Sumbit());

		startingPortName.setOnClickListener(new SelectPort(10));
		arrivalPortName.setOnClickListener(new SelectPort(11));
	}

	class SelectPort implements OnClickListener
	{
		int requestCode;

		public SelectPort(int requestCode)
		{
			this.requestCode = requestCode;
		}

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(DangerousgoodsportlnAdd.this,
					ElectricReportNewAddPort.class);
			startActivityForResult(intent, requestCode);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		if (125 == resultCode)
		{
			shipName = data.getStringExtra("name");
			shipNameText.setText(shipName);
		}
		if (50 == resultCode)
		{
			String s = data.getStringExtra("name");
			if (s != null && !s.equals(""))
			{
				if (requestCode == 10)
					startingPortName.setText(s);
				else if (requestCode == 11)
					arrivalPortName.setText(s);
			}
		}

	}

	class AddShipName implements View.OnClickListener
	{
		public void onClick(View v)
		{
			// Intent intent = new Intent(DangerousgoodsportlnAdd.this,
			// ElectricReportNewAddShipName.class);
			// intent.putExtra("userid", user.getUserId() + "");
			// startActivityForResult(intent, 100);
		}
	}

	class Sumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			// ���Ҫ����ֵ

			String declareTime1 = declareTime.getText().toString();
			String portTime = ShowportTime.getText().toString();
			String startingPortName1 = startingPortName.getText().toString();
			String arrivalPortName1 = arrivalPortName.getText().toString();
			String cargoTypes1 = cargoTypes.getText().toString();
			String dangerousLevel = spinner_kind.getSelectedItem().toString();
			String carrying1 = carrying.getText().toString();
			if (portTime.equals(""))
			{
				Toast.makeText(DangerousgoodsportlnAdd.this, "����д����ʱ��",
						Toast.LENGTH_SHORT).show();
			} else if (startingPortName1.equals(""))
			{
				Toast.makeText(DangerousgoodsportlnAdd.this, "����д���˸�",
						Toast.LENGTH_SHORT).show();
			} else if (arrivalPortName1.equals(""))
			{
				Toast.makeText(DangerousgoodsportlnAdd.this, "����дĿ�ĸ�",
						Toast.LENGTH_SHORT).show();
			} else if (cargoTypes1.equals(""))
			{
				Toast.makeText(DangerousgoodsportlnAdd.this, "����д��������",
						Toast.LENGTH_SHORT).show();
			} else if (cargoTypes1.length() > 20)
			{
				Toast.makeText(DangerousgoodsportlnAdd.this,
						"�������Ƶ��������ܴ���20������", Toast.LENGTH_SHORT).show();
			} else if (carrying1.equals(""))
			{
				Toast.makeText(DangerousgoodsportlnAdd.this, "����д����",
						Toast.LENGTH_SHORT).show();
			} else if (!isNum(carrying1))
			{
				Toast.makeText(DangerousgoodsportlnAdd.this, "���ر���������",
						Toast.LENGTH_SHORT).show();
			} else
			{

				actionUrl = HttpUtil.BASE_URL + "newDangerousGoodsPortln";
				param1 = "dangerousArrivalDeclareBean.shipName=" + shipName
						+ "&dangerousArrivalDeclareBean.declareTime="
						+ declareTime1
						+ "&dangerousArrivalDeclareBean.portTime=" + portTime
						+ "&dangerousArrivalDeclareBean.startingPortName="
						+ startingPortName1
						+ "&dangerousArrivalDeclareBean.arrivalPortName="
						+ arrivalPortName1
						+ "&dangerousArrivalDeclareBean.cargoTypes="
						+ cargoTypes1
						+ "&dangerousArrivalDeclareBean.dangerousLevel="
						+ dangerousLevel
						+ "&dangerousArrivalDeclareBean.carrying=" + carrying1;

				ListTask task = new ListTask(); // �첽
				task.execute();

			}
		}

	}

	public static boolean isNum(String str)
	{
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	// ��ʾ����
	class ShowportTime implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			new TimePickerDialog(DangerousgoodsportlnAdd.this,
					new OnTimeSetListener()
					{

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute)
						{
							timeList[3] = hourOfDay;
							timeList[4] = minute;
							ShowportTime.setText(timeList[0] + "-"
									+ timeList[1] + "-" + timeList[2] + " "
									+ timeList[3] + ":" + timeList[4]);
							// endDate.setText(timeList[3] + ":" + timeList[4]);
						}
					}, timeList[3], timeList[4], true).show();

			new DatePickerDialog(DangerousgoodsportlnAdd.this,
					new OnDateSetListener()
					{

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth)
						{

							if (year < 1901 || year > 2049)
							{
								// ���ڲ�ѯ��Χ��
								new AlertDialog.Builder(
										DangerousgoodsportlnAdd.this)
										.setTitle("��������")
										.setMessage(
												"��ת���ڷ�Χ(1901/1/1-2049/12/31)")
										.setPositiveButton("ȷ��", null).show();
							} else
							{
								timeList[0] = year;
								timeList[1] = monthOfYear + 1;
								timeList[2] = dayOfMonth;

							}
						}
					}, timeList[0], timeList[1] - 1, timeList[2]).show();

		}

	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// ȡ���첽
			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";

			try
			{
				hfu.sendPost1(actionUrl, param1);
				result = "�ύ�ɹ�";
				Intent intent = new Intent(DangerousgoodsportlnAdd.this,
						DangerousgoodsportlnAddList.class);
				intent.putExtra("User", user);
				startActivity(intent);
				setResult(20);
				finish();

			} catch (Exception e)
			{
				result = "�ύʧ��";
				e.printStackTrace();
			}

			new Log(user, "���Σ��Ʒ��������Ԥ�걨", GlobalVar.LOGSAVE, "").execute();

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			Toast.makeText(DangerousgoodsportlnAdd.this, result,
					Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

	}

}
