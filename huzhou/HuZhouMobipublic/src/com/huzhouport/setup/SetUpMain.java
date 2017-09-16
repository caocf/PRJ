package com.huzhouport.setup;

import com.example.huzhouportpublic.R;
import com.huzhouport.main.Login;
import com.huzhouport.main.MainPage;
import com.huzhouport.main.Register;
import com.huzhouport.main.UpdateAppManager;
import com.huzhouport.model.User;
import com.huzhouport.upload.UploadActivity;
import com.huzhouport.version.CheckVersion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetUpMain extends Activity 
{
	private User user;
	UpdateAppManager oUpdateAppManager;

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupmain);

		ImageButton img_back = (ImageButton) findViewById(R.id.setup_back);
		TextView tv_person = (TextView) findViewById(R.id.setup_person);
		TextView tv_login = (TextView) findViewById(R.id.login);
		TextView tv_Binding = (TextView) findViewById(R.id.change_Binding);
		TextView tv_password = (TextView) findViewById(R.id.setup_update_password);
		TextView tv_ver = (TextView) findViewById(R.id.setup_update_ver);
		TextView tv_above = (TextView) findViewById(R.id.setup_above);

		TextView tv_upload = (TextView) findViewById(R.id.setup_update_upload);
		TextView tv_zixun = (TextView) findViewById(R.id.setup_zixun);
		TextView tv_loginupdate = (TextView) findViewById(R.id.login_update);
		user = (User) getIntent().getSerializableExtra("User");

		if (user != null) {

		}
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		tv_person.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user == null) {
					Toast.makeText(SetUpMain.this, "请先登录", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(SetUpMain.this,
							SetUpPerson.class);
					intent.putExtra("User", user);
					startActivity(intent);
				}

			}
		});

		tv_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user != null) {
					Toast.makeText(SetUpMain.this, "您已登录，无需重新登录",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(SetUpMain.this, Login.class);
					intent.putExtra("User", user);
					startActivity(intent);
				}

			}
		});
		tv_Binding.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user == null) {
					Toast.makeText(SetUpMain.this, "请先登录", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(SetUpMain.this, ChangeBinding.class);
					intent.putExtra("User", user);
					intent.putExtra("checkboxvalue1", getIntent().getStringExtra("checkboxvalue1"));
					intent.putExtra("checkboxvalue2", getIntent().getStringExtra("checkboxvalue2"));
					startActivity(intent);
				}

			}
		});
		tv_password.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user == null) {
					Toast.makeText(SetUpMain.this, "请先登录", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(SetUpMain.this,
							SetUpPassword.class);
					intent.putExtra("User", user);
					startActivity(intent);
				}

			}
		});
		// 版本更新
		tv_ver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GetOther(null);// 更新

			}
		});
		// 关于我们
		tv_above.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetUpMain.this, SetMy.class);
				startActivity(intent);

			}
		});

		tv_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SetUpMain.this, UploadActivity.class);
				startActivity(intent);

			}
		});
		// 咨询
		tv_zixun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String number = "12395";
				Intent intent = new Intent(); // 创建一个意图
				intent.setAction("android.intent.action.CALL");

				intent.setData(Uri.parse("tel:" + number));
				startActivity(intent); // 方法内部会自动为intent添加类别
										// ：android.intent.category.DEFAULT".

			}
		});

		tv_loginupdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (user == null) {
					Toast.makeText(SetUpMain.this, "请先登录", Toast.LENGTH_SHORT)
							.show();
				} else {
					Editor oEditor = getSharedPreferences("UserDataPublic", 0)
							.edit();
					oEditor.clear();
					oEditor.commit();
					Intent intent = new Intent(SetUpMain.this, Login.class);
					startActivity(intent);

				}
			}
		});
		// 投诉
		/*
		 * tv_tousu.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent intent = new
		 * Intent(SetUpMain.this, Complain.class); startActivity(intent);
		 * 
		 * } });
		 */
	}

	public void GetOther(View view)
	{
		new CheckVersion(this).check();
		// oUpdateAppManager = new UpdateAppManager(SetUpMain.this);
		// UpdateTask oUpdateTask = new UpdateTask(SetUpMain.this);
		// oUpdateTask.execute(oUpdateAppManager);
	}

	class UpdateTask extends AsyncTask<UpdateAppManager, Integer, Integer> {

		ProgressDialog pDialog;

		public UpdateTask() {

		}

		public UpdateTask(Context context) {
			pDialog = ProgressDialog.show(context, "更新", "正在获取版本信息", true);
		}

		@Override
		protected Integer doInBackground(UpdateAppManager... params)
		{
			// TODO 自动生成的方法存根
			Integer iRet = params[0].GetNetVersion();
			return iRet;
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			// TODO 自动生成的方法存根
			if (pDialog != null)
				pDialog.dismiss();

			if (result > oUpdateAppManager.getVerCode()) {
				oUpdateAppManager.checkUpdateInfo();

			} else {
				Toast.makeText(SetUpMain.this, "暂无更新", Toast.LENGTH_SHORT)
						.show();
			}

			super.onPostExecute(result);

		}

	}
	
	public void ToRegister(View v)
	{
		if(user!=null)
		{
			Toast.makeText(SetUpMain.this, "您已登录", Toast.LENGTH_SHORT)
			.show();
			return;
		}
		Intent intent = new Intent(SetUpMain.this, Register.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
}
