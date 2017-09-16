package com.huzhouport.electricreport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.model.User;
import com.huzhouport.time.WheelMain;

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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ElectricReportChange extends Activity {
	private TextView tv_port, tv_time;
	private String reportID,oldPort, oldTime;
	private User user=new User();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.electricreport_change);
		Intent intent = getIntent();
		user= (User) intent.getSerializableExtra("User");
		reportID = intent.getStringExtra("reportID");
		oldPort = intent.getStringExtra("oldPort");
		oldTime = intent.getStringExtra("oldTime");

		ImageButton back = (ImageButton) findViewById(R.id.ep_change_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		ImageButton ele_finish = (ImageButton) findViewById(R.id.ep_change_finish);
		ele_finish.setOnClickListener(new finish());

		tv_port = (TextView) findViewById(R.id.ep_change_porttext);
		tv_time = (TextView) findViewById(R.id.ep_change_timetext);
		tv_port.setText(oldPort);
		tv_time.setText(oldTime);
		tv_port.setOnClickListener(new selectPort());
		tv_time.setOnClickListener(new openDate());

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (50 == resultCode) {
			tv_port.setText(data.getStringExtra("name"));

		}
	}

	class selectPort implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ElectricReportChange.this,
					ElectricReportNewAddPort.class);
			intent.putExtra("kind", "1");
			startActivityForResult(intent, 100);
		}

	}

	class openDate implements OnClickListener {

		@Override
		public void onClick(View v) {
			WindowManager wm = (WindowManager) ElectricReportChange.this
					.getSystemService(Context.WINDOW_SERVICE);
			@SuppressWarnings("deprecation")
			int width = wm.getDefaultDisplay().getWidth();//

			final PopupWindow dialog = new PopupWindow(v, width, 350);

			// 找到dialog的布局文件
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.leaveandovertime_time_layout,
					null);

			final WheelMain main = new WheelMain(dialog, view);
			main.showDateTimePicker(v);

			Button btn_sure = (Button) view
					.findViewById(R.id.btn_datetime_sure);
			Button btn_cancel = (Button) view
					.findViewById(R.id.btn_datetime_cancel);
			// 确定
			btn_sure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					dialog.dismiss();

					tv_time.setText(main.getTime());
				}
			});
			// 取消
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
				}
			});

		}
	}

	class finish implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (tv_port.getText().length() == 0) {
				Toast.makeText(ElectricReportChange.this, "请选择目的港！",
						Toast.LENGTH_SHORT).show();
				return ;
			}
			if (tv_time.getText().length() == 0) {
				Toast.makeText(ElectricReportChange.this, "请选择预计进港时间！",
						Toast.LENGTH_SHORT).show();
				return ;

			}
			if(tv_time.getText().toString().equals(oldTime)&&tv_port.getText().toString().equals(oldPort)){
				
				Toast.makeText(ElectricReportChange.this, "未更改任何信息",
						Toast.LENGTH_SHORT).show();
				
				return ;

			}
			new submit(ElectricReportChange.this).execute();
		}

	}

	class submit extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public submit() {

		}

		@SuppressWarnings("deprecation")
		public submit(Context context) {

			pDialog = new ProgressDialog(ElectricReportChange.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("正在加载中，请稍候。。。");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (submit.this != null
							&& submit.this.getStatus() == AsyncTask.Status.RUNNING)
						submit.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (submit.this != null
							&& submit.this.getStatus() == AsyncTask.Status.RUNNING)
						submit.this.cancel(true);

				}
			});
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {

			if (isCancelled())
				return null;// 取消异步
			HttpFileUpTool hft = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL+ "updateReportArrivalPort";
			Map<String, Object> paramDate = new HashMap<String, Object>();
			paramDate.put("electricReportNew.estimatedTimeOfArrival", tv_time.getText().toString());
			paramDate.put("electricReportNew.arrivalPort", tv_port.getText().toString());
			paramDate.put("electricReportNew.reportID", reportID);
			paramDate.put("electricReportNew.startingPort", oldPort);
			paramDate.put("electricReportNew.reportTime", oldTime);
			String result = null;
			try {
				result = hft.post(actionUrl, paramDate);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if(pDialog!=null)
				pDialog.dismiss();
			if (result == null) {
				Toast.makeText(ElectricReportChange.this, "出现错误请重新提交！",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(ElectricReportChange.this, "提交成功",
						Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(ElectricReportChange.this, ElectricReportNewList.class);
				intent.putExtra("User", user);
				startActivity(intent);
				setResult(170);
				finish();
			}
			super.onPostExecute(result);
		}

	}

}
