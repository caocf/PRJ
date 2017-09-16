package com.huzhouport.dangerousgoodsportln;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouportpublic.R;
import com.huzhouport.common.util.Query;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;

public class DangerousgoodsportlnAddView extends Activity {
	private Query query = new Query();

	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsportln_add_view);

		Intent intent = getIntent();
		id = intent.getStringExtra("id");

		ImageButton back = (ImageButton) findViewById(R.id.dangerousgoodsportln_back);
		back.setOnClickListener(new Back());

		// getNeirong(id);//获得数据
		ListTask task = new ListTask(this); // 异步
		task.execute();

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
					.getJSONObject("dangerousArrivalDeclareBean");
			System.out.println("jsonArray===" + jsonArray);

			String shipName = jsonArray.getString("shipName");
			TextView shipNameText = (TextView) findViewById(R.id.dangerousgoodsportln_view_shipNameNeirong);
			shipNameText.setText(shipName);

			String declareTime = jsonArray.getString("declareTime").substring(
					0, jsonArray.getString("declareTime").lastIndexOf(":"));
			TextView declareTimeText = (TextView) findViewById(R.id.dangerousgoodsportln_view_declareTimeNeirong);
			declareTimeText.setText(declareTime);

			String portTime = jsonArray.getString("portTime").substring(0,
					jsonArray.getString("portTime").lastIndexOf(":"));
			TextView portTimeText = (TextView) findViewById(R.id.dangerousgoodsportln_view_portTimeNeirong);
			portTimeText.setText(portTime);

			String startingPortName = jsonArray.getString("startingPortName");
			TextView startingPortNameText = (TextView) findViewById(R.id.dangerousgoodsportln_view_startingPortNameNeirong);
			startingPortNameText.setText(startingPortName);

			String arrivalPortName = jsonArray.getString("arrivalPortName");
			TextView arrivalPortNameText = (TextView) findViewById(R.id.dangerousgoodsportln_view_arrivalPortNameNeirong);
			arrivalPortNameText.setText(arrivalPortName);

			String cargoTypes = jsonArray.getString("cargoTypes");
			TextView cargoTypesText = (TextView) findViewById(R.id.dangerousgoodsportln_view_cargoTypesNeirong);
			cargoTypesText.setText(cargoTypes);

			String dangerousLevel = jsonArray.getString("dangerousLevel");
			TextView dangerousLevelText = (TextView) findViewById(R.id.dangerousgoodsportln_view_dangerousLevelNeirong);
			dangerousLevelText.setText(dangerousLevel);

			String carrying = jsonArray.getString("carrying");
			TextView carryingText = (TextView) findViewById(R.id.dangerousgoodsportln_view_carryingNeirong);
			carryingText.setText(carrying);

			String reviewUserName = jsonArray.getString("reviewUserName");
			if (reviewUserName.equals("0")) {
				findViewById(R.id.dangerousgoodsportln_view_reviewUserName)
						.setVisibility(View.GONE);
				findViewById(
						R.id.dangerousgoodsportln_view_reviewUserNameNeirong)
						.setVisibility(View.GONE);
				findViewById(R.id.dangerousgoodsportln_view_reviewOpinion_tr)
						.setVisibility(View.GONE);
				findViewById(R.id.dangerousgoodsportln_view_reviewTime_tr)
						.setVisibility(View.GONE);
				TextView reviewResultText = (TextView) findViewById(R.id.dangerousgoodsportln_view_reviewResultNeirong);
				reviewResultText.setText("未审批");
			} else {
				TextView reviewUserNameText = (TextView) findViewById(R.id.dangerousgoodsportln_view_reviewUserNameNeirong);
				reviewUserNameText.setText(reviewUserName);

				String reviewResult = jsonArray.getString("reviewResult");
				TextView reviewResultText = (TextView) findViewById(R.id.dangerousgoodsportln_view_reviewResultNeirong);
				if (reviewResult.equals("3")) {
					reviewResultText.setText("驳回");
				} else {
					reviewResultText.setText("已通过");
				}

				TextView reviewOpinionText = (TextView) findViewById(R.id.dangerousgoodsportln_view_reviewOpinionNeirong);
				TextView reviewTimeText = (TextView) findViewById(R.id.dangerousgoodsportln_view_reviewTimeNeirong);
				if (jsonArray.getString("reviewOpinion") == null
						|| jsonArray.getString("reviewOpinion").equals("")) {
					reviewOpinionText.setText("无");
				} else {
					reviewOpinionText.setText(jsonArray
							.getString("reviewOpinion"));
				}
				reviewTimeText.setText(jsonArray.getString("reviewTime")
						.substring(
								0,
								jsonArray.getString("reviewTime").lastIndexOf(
										":")));

			}

		} catch (Exception e) {
			Toast.makeText(DangerousgoodsportlnAddView.this, "网络异常",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	class Back implements View.OnClickListener {
		public void onClick(View v) {

			finish();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {
		ProgressDialog pDialog = null;

		public ListTask() {

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context) {
			pDialog = new ProgressDialog(DangerousgoodsportlnAddView.this);
			pDialog.setTitle("提示");
			pDialog.setMessage("正在加载中，请稍候。。。");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			return query.showDangerousGoodsPortlnApproval(id);
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
