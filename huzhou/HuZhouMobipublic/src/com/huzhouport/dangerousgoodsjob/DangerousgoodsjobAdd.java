package com.huzhouport.dangerousgoodsjob;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.CommonListenerWrapper;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.ManagerDialog;
import com.huzhouport.electricreport.ElectricReportNewAddPort;
import com.huzhouport.model.User;
import com.huzhouport.upload.UploadActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 添加一条危险品码头上报
 * 
 * @author Administrator
 * 
 */
public class DangerousgoodsjobAdd extends Activity
{
	private Spinner spinner_kind;

	private String actionUrl, param1;
	private User user;
	private Calendar c; // 申报作业时间
	private Calendar d; // 预订作业时间

	private TextView wharftext;
	private TextView declareTime;
	private TextView startingPortName;
	private TextView arrivalPortName;
	private EditText cargoTypes;
	private TextView workTIme;
	private EditText carrying;
	private TextView shipEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_add_add);

		user = (User) getIntent().getSerializableExtra("User");

		CommonListenerWrapper.commonBackWrapperListener(
				R.id.dangerousgoodsjob_addback, this);

		declareTime = (TextView) findViewById(R.id.dangerousgoodsjob_add_declareTimeNeirong);
		startingPortName = (TextView) findViewById(R.id.dangerousgoodsjob_add_StartingPortNeirong);
		arrivalPortName = (TextView) findViewById(R.id.dangerousgoodsjob_add_ArrivalPortNeirong);
		cargoTypes = (EditText) findViewById(R.id.dangerousgoodsjob_add_CargoTypesNeirong);
		workTIme = (TextView) findViewById(R.id.dangerousgoodsjob_add_workTImeNeirong);
		carrying = (EditText) findViewById(R.id.dangerousgoodsjob_add_CarryingNeirong);
		shipEditText = (TextView) findViewById(R.id.dangerousgoodsjob_add_shipNameNeirong);
		wharftext = (TextView) findViewById(R.id.dangerousgoodsjob_add_wharfNameNeirong);

		// 下拉框
		spinner_kind = (Spinner) findViewById(R.id.dangerousgoodsjob_add_DangerousLevelNeirong);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.dangerousgoodsportln_DangerousLevel_spinner,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);

		// 选择作业码头
		wharftext.setText(user.getWharfBindingList().get(user.getBindnum())
				.getWharfNum());

		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		c = Calendar.getInstance();
		String str = formatter.format(c.getTime());

		declareTime.setText(str);

		// 申请作业时间
		workTIme.setOnClickListener(new ShowworkTIme());

		// 提交按钮
		findViewById(R.id.dangerousgoodsjob_add_submit).setOnClickListener(
				new Sumbit());

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
			Intent intent = new Intent(DangerousgoodsjobAdd.this,
					ElectricReportNewAddPort.class);
			startActivityForResult(intent, requestCode);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == 50)
		{
			String s = data.getStringExtra("name");
			if (s != null && !s.equals(""))
			{
				if (requestCode == 10)
				{
					startingPortName.setText(s);
				} else
				{
					arrivalPortName.setText(s);
				}
			}
		}

	}

	class Sumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			// 获得要传的值

			String ship = shipEditText.getText().toString();
			String declareTime1 = declareTime.getText().toString();
			String startingPortName1 = startingPortName.getText().toString();
			String arrivalPortName1 = arrivalPortName.getText().toString();
			String cargoTypes1 = cargoTypes.getText().toString();
			String dangerousLevel = spinner_kind.getSelectedItem().toString();
			String wharfID1 = wharftext.getText().toString();
			String workTIme1 = workTIme.getText().toString();
			String carrying1 = carrying.getText().toString();

			if (ship.equals(""))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "请输入船名号",
						Toast.LENGTH_SHORT).show();
			}
			if (startingPortName1.equals(""))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "请填写起运港",
						Toast.LENGTH_SHORT).show();
			} else if (arrivalPortName1.equals(""))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "请填写目的港",
						Toast.LENGTH_SHORT).show();
			} else if (cargoTypes1.equals(""))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "请填写货物名称",
						Toast.LENGTH_SHORT).show();
			} else if (workTIme1.equals(""))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "请填写申请作业时间",
						Toast.LENGTH_SHORT).show();
			} else if (carrying1.equals(""))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "请填写载重",
						Toast.LENGTH_SHORT).show();
			} else if (!isNum(carrying1))
			{
				Toast.makeText(DangerousgoodsjobAdd.this, "载重必须是数字",
						Toast.LENGTH_SHORT).show();
			} else
			{

				actionUrl = HttpUtil.BASE_URL + "newDangerousGoodsJob";
				param1 = "dangerousWorkDeclareBean.shipName=" + ship
						+ "&dangerousWorkDeclareBean.declareTime="
						+ declareTime1
						+ "&dangerousWorkDeclareBean.startingPortName="
						+ startingPortName1
						+ "&dangerousWorkDeclareBean.arrivalPortName="
						+ arrivalPortName1
						+ "&dangerousWorkDeclareBean.cargoTypes=" + cargoTypes1
						+ "&dangerousWorkDeclareBean.dangerousLevel="
						+ dangerousLevel + "&dangerousWorkDeclareBean.wharfID="
						+ wharfID1 + "&dangerousWorkDeclareBean.workTIme="
						+ workTIme1 + "&dangerousWorkDeclareBean.carrying="
						+ carrying1;

				submitTask task = new submitTask(DangerousgoodsjobAdd.this); // 异步
				task.execute();
			}
		}
	}

	// 判断是否是数字
	public static boolean isNum(String str)
	{
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	// 显示日期
	class ShowworkTIme implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (d == null)
				d = Calendar.getInstance();
			ManagerDialog.getTimeDialog(
					DangerousgoodsjobAdd.this,
					new OnTimeSetListener()
					{
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute)
						{
							d.set(Calendar.HOUR_OF_DAY, hourOfDay);
							d.set(Calendar.MINUTE, minute);

							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
							String str = formatter.format(d.getTime());
							((TextView) findViewById(R.id.dangerousgoodsjob_add_workTImeNeirong))
									.setText(str);
						}
					}, d.get(Calendar.HOUR_OF_DAY), d.get(Calendar.MINUTE),
					true).show();

			ManagerDialog.getDateDialog(
					DangerousgoodsjobAdd.this,
					new OnDateSetListener()
					{

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth)
						{
							d.set(Calendar.YEAR, year);
							d.set(Calendar.MONTH, monthOfYear);
							d.set(Calendar.DAY_OF_MONTH, dayOfMonth);

						}
					}, d.get(Calendar.YEAR), d.get(Calendar.MONTH),
					d.get(Calendar.DAY_OF_MONTH)).show();

		}

	}

	class submitTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public submitTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this, "数据正在提交中，请稍后。。。");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			UploadActivity.tool.addFile("危险品码头作业", actionUrl, param1);
			Intent intent = new Intent(DangerousgoodsjobAdd.this,
					DangerousgoodsjobAddList.class);
			intent.putExtra("User", user);
			startActivity(intent);
			setResult(20);
			finish();

			new Log(user, "添加危险货物码头作业预申报", GlobalVar.LOGSAVE, "").execute();

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}
	}
}
