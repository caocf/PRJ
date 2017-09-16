package com.huzhouport.dangerousgoodsjob;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.Query;
import com.huzhouport.model.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

public class DangerousgoodsjobapprovalView extends Activity {
	private Query query = new Query();

	private String id;
	private String userid;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsjob_view);

		user = (User) getIntent().getSerializableExtra("User");
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		userid = intent.getStringExtra("userid");

		ImageButton back = (ImageButton) findViewById(R.id.dangerousgoodsjob_back);
		back.setOnClickListener(new Back());

		// getNeirong(id);//获得数据

		// 获取显示列表信息
		ListTask task = new ListTask(this); // 异步
		task.execute();

		ImageButton approval = (ImageButton) findViewById(R.id.dangerousgoodsjob_view_approval);
		approval.setOnClickListener(new Approval());

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode) {
			setResult(20);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void getNeirong(String result) {

		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject jsonArray = data
					.getJSONObject("dangerousWorkDeclareBean");
			System.out.println("jsonArray===" + jsonArray);

			String shipName = jsonArray.getString("shipName");
			TextView shipNameText = (TextView) findViewById(R.id.dangerousgoodsjob_view_shipNameNeirong);
			shipNameText.setText(shipName);

			String declareTime = jsonArray.getString("declareTime").substring(
					0, jsonArray.getString("declareTime").lastIndexOf(":"));
			TextView declareTimeText = (TextView) findViewById(R.id.dangerousgoodsjob_view_declareTimeNeirong);
			declareTimeText.setText(declareTime);

			String startingPortName = jsonArray.getString("startingPortName");
			TextView startingPortNameText = (TextView) findViewById(R.id.dangerousgoodsjob_view_startingPortNameNeirong);
			startingPortNameText.setText(startingPortName);

			String arrivalPortName = jsonArray.getString("arrivalPortName");
			TextView arrivalPortNameText = (TextView) findViewById(R.id.dangerousgoodsjob_view_arrivalPortNameNeirong);
			arrivalPortNameText.setText(arrivalPortName);

			String cargoTypes = jsonArray.getString("cargoTypes");
			TextView cargoTypesText = (TextView) findViewById(R.id.dangerousgoodsjob_view_cargoTypesNeirong);
			cargoTypesText.setText(cargoTypes);

			String dangerousLevel = jsonArray.getString("dangerousLevel");
			TextView dangerousLevelText = (TextView) findViewById(R.id.dangerousgoodsjob_view_dangerousLevelNeirong);
			dangerousLevelText.setText(dangerousLevel);

			String wharfName = jsonArray.getString("wharfName");
			TextView wharfNameText = (TextView) findViewById(R.id.dangerousgoodsjob_view_wharfNameNeirong);
			wharfNameText.setText(wharfName);

			String workTIme = jsonArray.getString("workTIme").substring(0,
					jsonArray.getString("workTIme").lastIndexOf(":"));
			TextView workTImeText = (TextView) findViewById(R.id.dangerousgoodsjob_view_workTImeNeirong);
			workTImeText.setText(workTIme);

			String carrying = jsonArray.getString("carrying");
			TextView carryingText = (TextView) findViewById(R.id.dangerousgoodsjob_view_carryingNeirong);
			carryingText.setText(carrying);

		} catch (Exception e) {
			Toast.makeText(DangerousgoodsjobapprovalView.this, "网络异常",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	class Back implements View.OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}

	class Approval implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(DangerousgoodsjobapprovalView.this,
					DangerousgoodsjobapprovalSubmit.class);
			intent.putExtra("id", id);
			intent.putExtra("userid", userid);
			intent.putExtra("User", user);

			startActivityForResult(intent, 100);
		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		public ListTask(Context context) {
			pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true);
		}

		@Override
		protected String doInBackground(String... params) {

			return query.showDangerousGoodsJobApproval(id);
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result);

			super.onPostExecute(result);
		}

	}

}
