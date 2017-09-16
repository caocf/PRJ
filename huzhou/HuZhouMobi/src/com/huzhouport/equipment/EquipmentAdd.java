package com.huzhouport.equipment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.ClearEditText;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.equipment.EquipmentShow.ListTask;
import com.huzhouport.main.User;
import com.huzhouport.time.WheelMain;
import com.huzhouport.upload.UploadActivity;
import com.huzhouport.upload.UploadFileTool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EquipmentAdd extends Activity
{
	private String kind;
	private Query query = new Query();

	private String userid, username; // �û�id

	private String actionUrl, param1;
	private String adduser_name, adduser_id;

	private String declareTime;

	private TextView kindNameText, kindIDText;

	private String kindname, kindid;

	private User user = new User();

	private EditText eText;

	private PopupWindow dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipment_add);

		Intent intent = getIntent();
		userid = intent.getStringExtra("userid");
		username = intent.getStringExtra("username");

		user.setUserId(Integer.parseInt(userid));
		user.setName(username);

		TextView userText = (TextView) findViewById(R.id.equipment_add_equipmentUserNeirong);
		userText.setText(username);

		kindNameText = (TextView) findViewById(R.id.equipment_add_equipmentKindNeirong);
		kindIDText = (TextView) findViewById(R.id.equipment_add_equipmentKindID);
		kindNameText.setOnClickListener(new kindText());

		ImageButton back = (ImageButton) findViewById(R.id.equipment_add_back);
		back.setOnClickListener(new Back());

		// �û�ѡ��
		TextView adduser1 = (TextView) findViewById(R.id.equipment_add_approvalIDNeirong);
		adduser1.setOnClickListener(new AddUser1());

		ImageButton sumbit = (ImageButton) findViewById(R.id.equipment_add_add);
		sumbit.setOnClickListener(new Sumbit());

		eText = (EditText) findViewById(R.id.equipment_add_equipmentReasonNeirong);
		// eText.setOnClickListener(new Et());

		eText.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (eText.getText().toString().equals("����дԭ��"))
				{
					eText.setText("");
					eText.setTextColor(EquipmentAdd.this.getResources()
							.getColor(R.color.black));
				}
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 30)
		{

			adduser_name = data.getStringExtra("adduser_name");
			adduser_id = data.getStringExtra("adduser_id");

			TextView approvalName1 = (TextView) findViewById(R.id.equipment_add_approvalIDNeirong);
			TextView approvalID1 = (TextView) findViewById(R.id.equipment_add_approvalIDID);
			approvalName1.setText(adduser_name);
			approvalID1.setText(adduser_id);

		}
		if (resultCode == 40)
		{
			kindname = data.getStringExtra("kind_name");
			kindid = data.getStringExtra("kind_id");
			kindNameText.setText(kindname);
			kindIDText.setText(kindid);
		}

	}

	public class AddUser1 implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(EquipmentAdd.this,
					EquipmentAddUSer.class);
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", userid);
			startActivityForResult(intent, 100);
		}
	}

	public class kindText implements View.OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(EquipmentAdd.this,
					EquipmentAddKindName.class);
			startActivityForResult(intent, 100);
		}

	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
			finish();
		}
	}

	public class Et implements View.OnClickListener
	{
		public void onClick(View v)
		{
			System.out.println("sdadad");
			if (eText.getText().toString().equals("����д����"))
			{
				eText.setText("");
			}
		}
	}

	class Sumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			// ���Ҫ����ֵ
			TextView approvalID1 = (TextView) findViewById(R.id.equipment_add_approvalIDID);
			EditText ReasonText = (EditText) findViewById(R.id.equipment_add_equipmentReasonNeirong);

			String id1 = approvalID1.getText().toString();
			String kindname = kindIDText.getText().toString();
			String Reason = ReasonText.getText().toString();

			if (id1.equals(""))
			{
				Toast.makeText(EquipmentAdd.this, "��ѡ��������Ա", Toast.LENGTH_SHORT)
						.show();
			} else if (kindname.equals(""))
			{
				Toast.makeText(EquipmentAdd.this, "��ѡ��������Ʒ", Toast.LENGTH_SHORT)
						.show();
			} else if (Reason.equals("") || Reason.equals("����дԭ��"))
			{
				Toast.makeText(EquipmentAdd.this, "����дԭ��", Toast.LENGTH_SHORT)
						.show();
			} else
			{

				actionUrl = HttpUtil.BASE_URL + "equipmentAdd";
				param1 = "equipment.equipmentUser=" + userid
						+ "&equipment.equipmentKind=" + kindname
						+ "&equipment.equipmentReason=" + Reason
						+ "&equipment.approvalID=" + id1;
				System.out.println("param====" + param1);
				ListTask task = new ListTask(EquipmentAdd.this); // �첽
				task.execute();

			}

		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask()
		{

		}

		@SuppressWarnings("deprecation")
		public ListTask(Context context)
		{
			pDialog = new ProgressDialog(EquipmentAdd.this);
			pDialog.setTitle("��ʾ");
			pDialog.setMessage("���ڼ����У����Ժ򡣡���");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener()
			{

				@Override
				public void onCancel(DialogInterface dialog)
				{
					if (pDialog != null)
						pDialog.dismiss();
					if (ListTask.this != null
							&& ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
						ListTask.this.cancel(true);

				}
			});
			pDialog.setButton("ȡ��", new DialogInterface.OnClickListener()
			{

				@Override
				public void onClick(DialogInterface dialog, int which)
				{
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
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// ȡ���첽
			// UploadFileTool tool = new UploadFileTool();
			UploadActivity.tool.addFile("�豸����", actionUrl, param1);
			Intent intent = new Intent(EquipmentAdd.this, EquipmentMain.class);
			intent.putExtra("User", user);
			startActivity(intent);
			setResult(20);
			finish();

			Log log = new Log(username, "����豸����", GlobalVar.LOGSAVE,""); // ��־
			log.execute();

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
