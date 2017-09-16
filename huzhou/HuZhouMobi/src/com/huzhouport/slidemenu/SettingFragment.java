package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import com.huzhouport.main.UpdateAppManager;
import com.huzhouport.main.User;
import com.huzhouport.upload.UploadActivity;
import com.huzhouport.version.CheckVersion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

	private TextView t1, t2, t3, t5, t6, tv_upload, tv_leave,tv_editpassword;

	UpdateAppManager oUpdateAppManager;
	private User user;

	public SettingFragment() {
	}

	private String userid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_set, container,
				false);

		t1 = (TextView) rootView.findViewById(R.id.fragment_set_text1);
		t2 = (TextView) rootView.findViewById(R.id.fragment_set_text2);
		t3 = (TextView) rootView.findViewById(R.id.fragment_set_text3);
		t5 = (TextView) rootView.findViewById(R.id.fragment_set_text5);
		t6 = (TextView) rootView.findViewById(R.id.fragment_set_pushtime);
		tv_upload = (TextView) rootView.findViewById(R.id.fragment_set_upload);
		tv_editpassword=(TextView)rootView.findViewById(R.id.setup_update_password);

		tv_leave = (TextView) rootView.findViewById(R.id.fragment_set_leave);

		t1.setOnClickListener(new tv1());
		t2.setOnClickListener(new tv2());
		t3.setOnClickListener(new tv3());
		t5.setOnClickListener(new tv5());
		t6.setOnClickListener(new tv6());
		tv_upload.setOnClickListener(new tv_upload());
		tv_leave.setOnClickListener(new Leave());
		
		tv_editpassword.setOnClickListener(new editpassword());

		Bundle bundle = getArguments();
		if (bundle != null) {
			user = (User) bundle.getSerializable("User");
			if(user!=null){
			userid = user.getUserId() + "";
			}else{
				userid="";
			}
		}
		
		return rootView;
	}

	class editpassword implements View.OnClickListener{

		@Override
		public void onClick(View v)
		{
			if (user == null) {
				Toast.makeText(SettingFragment.this.getActivity(), "请先登录", Toast.LENGTH_SHORT)
						.show();
			} else {
				Intent intent = new Intent(SettingFragment.this.getActivity(),
						SetUpPassword.class);
				intent.putExtra("User", user);
				startActivity(intent);
			}
		}
		
	}
	
	public class tv1 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(), SetIp.class);
			startActivity(intent);

		}
	}

	public class tv2 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(), SetMei.class);
			startActivity(intent);

		}
	}

	public class tv3 implements View.OnClickListener {
		public void onClick(View v) {

			GetOther(null);// 更新
		}
	}

	public class tv6 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(), SetPushMsgTimer.class);
			startActivity(intent);

		}
	}

	public class tv_upload implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), UploadActivity.class);
			startActivity(intent);

		}
	}

	public class tv5 implements View.OnClickListener {
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(), SetMy.class);
			startActivity(intent);

		}
	}

	public class Leave implements View.OnClickListener {
		public void onClick(View v) {
            if(userid.equals("")){
            Toast.makeText(getActivity(), "请您先登录", Toast.LENGTH_SHORT).show();	
            }else{
			Intent intent = new Intent(getActivity(), SetLeave.class);
			intent.putExtra("userid", userid);
			startActivity(intent);
            }
		}
	}

	public void GetOther(View view) {

		new CheckVersion(getActivity()).check();
//		oUpdateAppManager = new UpdateAppManager(getActivity());
//		UpdateTask oUpdateTask = new UpdateTask(getActivity());
//		oUpdateTask.execute(oUpdateAppManager);
	}

	class UpdateTask extends AsyncTask<UpdateAppManager, Integer, Integer> {

		ProgressDialog pDialog;

		public UpdateTask() {

		}

		public UpdateTask(Context context) {
			pDialog = ProgressDialog.show(context, "更新", "正在获取版本信息", true);
		}

		@Override
		protected Integer doInBackground(UpdateAppManager... params) {
			// TODO 自动生成的方法存根
			Integer iRet = params[0].GetNetVersion();
			return iRet;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO 自动生成的方法存根
			if (pDialog != null)
				pDialog.dismiss();
			if (result > oUpdateAppManager.getVerCode()) {
				oUpdateAppManager.checkUpdateInfo();
			} else {
				Toast.makeText(getActivity(), "暂无更新", Toast.LENGTH_SHORT)
						.show();
			}
			super.onPostExecute(result);
		}

	}
}
