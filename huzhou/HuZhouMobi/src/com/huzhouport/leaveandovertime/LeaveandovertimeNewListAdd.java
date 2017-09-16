package com.huzhouport.leaveandovertime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

@SuppressLint("SimpleDateFormat")
public class LeaveandovertimeNewListAdd extends Activity
{
	private String kind;
	private Query query = new Query();
	Map<String, Object> params = new HashMap<String, Object>();
	private TextView beginDate, endDate, lastDate;
	private TextView beginDate_morning, endDate_afternoon;
	private String userid, username; // 用户id
	private String[] kindNameList;
	private String[] kindIdList;
	private int[] timeList = new int[5];
	private int[] timeList1 = new int[5];
	private String actionUrl, param1;
	private String adduserid, adduser_name, adduser_id;

	private String declareTime;

	private TextView kindNameText, kindIDText;

	private String kindname, kindid;

	private User user = new User();

	private EditText eText;

	private TextView adduser1, adduser2, adduser3;
	private TextView id1, id2, id3;
	private PopupWindow popupWindow;
	private ScrollView scroll;

	private String timestring = "4";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveandovertime_newlist_add);
		Intent intent = getIntent();
		kind = intent.getStringExtra("kind");
		userid = intent.getStringExtra("userid");
		username = intent.getStringExtra("username");

		user.setUserId(Integer.parseInt(userid));
		user.setName(username);

		kindNameText = (TextView) findViewById(R.id.leaveandovertime_newlist_add_kindNameNeirong);
		kindIDText = (TextView) findViewById(R.id.leaveandovertime_newlist_add_kindNameID);

		if (kind.equals("1"))
		{
			kindNameText.setOnClickListener(new kindText());

		}

		if (kind.equals("2"))
		{
			TextView lastdate = (TextView) findViewById(R.id.leaveandovertime_newlist_add_lastDate);
			TextView leaveOrOtReason = (TextView) findViewById(R.id.leaveandovertime_newlist_add_leaveOrOtReason);
			TextView title = (TextView) findViewById(R.id.leaveandovertime_newlist_add_title);
			lastdate.setText("加班时长：");
			leaveOrOtReason.setText("加班事由：");
			title.setText("加班申请");
			kindNameText.setCompoundDrawables(null, null, null, null);
			kindNameText.setText("加班");
			kindIDText.setText("2");
		}
		if (kind.equals("3"))
		{
			TextView lastdate = (TextView) findViewById(R.id.leaveandovertime_newlist_add_lastDate);
			TextView leaveOrOtReason = (TextView) findViewById(R.id.leaveandovertime_newlist_add_leaveOrOtReason);
			TextView title = (TextView) findViewById(R.id.leaveandovertime_newlist_add_title);
			lastdate.setText("出差时长：");
			leaveOrOtReason.setText("出差事由：");
			title.setText("出差申请");
			kindNameText.setCompoundDrawables(null, null, null, null);
			kindNameText.setText("出差");
			kindIDText.setText("5");
			findViewById(R.id.leaveandovertime_newlist_add_address_tr)
					.setVisibility(View.VISIBLE);
		}

		TextView userText = (TextView) findViewById(R.id.leaveandovertime_newlist_add_leaveOrOtUserNeirong);
		userText.setText(username);

		beginDate = (TextView) findViewById(R.id.leaveandovertime_newlist_add_beginDateNeirong);
		endDate = (TextView) findViewById(R.id.leaveandovertime_newlist_add_endDateNeirong);
		lastDate = (TextView) findViewById(R.id.leaveandovertime_newlist_add_lastDateNeirong);
		lastDate.setText("半天");

		ListTask task = new ListTask(this); // 获取当地时间
		task.execute();
		beginDate.setOnClickListener(new ShowDate());
		endDate.setOnClickListener(new ShowDate1());

		beginDate_morning = (TextView) findViewById(R.id.leaveandovertime_newlist_add_beginDateNeirong_morning);
		endDate_afternoon = (TextView) findViewById(R.id.leaveandovertime_newlist_add_endDateNeirong_afternoon);
		beginDate_morning.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showtimePopupWindow(v, "1");
			}
		});
		endDate_afternoon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showtimePopupWindow(v, "2");
			}
		});
		scroll = (ScrollView) findViewById(R.id.leaveandovertime_newlist_view_scroll);

		ImageButton back = (ImageButton) findViewById(R.id.leaveandovertime_newlist_add_back);
		back.setOnClickListener(new Back());

		// 用户选择
		adduser1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_approvalID1Neirong);
		adduser2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_approvalID2Neirong);
		adduser3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_approvalID3Neirong);
		id1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID1ID);
		id2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID2ID);
		id3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID3ID);
		adduser1.setOnClickListener(new AddUser1());

		ImageButton sumbit = (ImageButton) findViewById(R.id.leaveandovertime_newlist_add_add);
		sumbit.setOnClickListener(new Sumbit());

		eText = (EditText) findViewById(R.id.leaveandovertime_newlist_add_leaveOrOtReasonNeirong);
		// eText.setOnClickListener(new Et());

		eText.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				//System.out.println("sdadad");
				if (eText.getText().toString().equals("请填写事由"))
				{
					eText.setText("");
					eText.setTextColor(LeaveandovertimeNewListAdd.this
							.getResources().getColor(R.color.black));
				}

			}
		});

		ListTaskApproval taskapp = new ListTaskApproval(this); // 异步 获取审批人
		taskapp.execute();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 30)
		{

			// 从子窗口中获取返回结果
			adduserid = data.getStringExtra("adduserid");
			adduser_name = data.getStringExtra("adduser_name");
			adduser_id = data.getStringExtra("adduser_id");

			if (adduserid.equals("1"))
			{
				TextView approvalName1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_approvalID1Neirong);
				TextView approvalID1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID1ID);
				approvalName1.setText(adduser_name);
				approvalID1.setText(adduser_id);
			}
			if (adduserid.equals("2"))
			{
				TextView approvalName2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_approvalID2Neirong);
				TextView approvalID2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID2ID);
				approvalName2.setText(adduser_name);
				approvalID2.setText(adduser_id);
			}
			if (adduserid.equals("3"))
			{
				TextView approvalName3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_approvalID3Neirong);
				TextView approvalID3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID3ID);
				approvalName3.setText(adduser_name);
				approvalID3.setText(adduser_id);
			}
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
			TextView approvalID1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID1ID);
			TextView approvalID2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID2ID);
			TextView approvalID3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID3ID);

			Intent intent = new Intent(LeaveandovertimeNewListAdd.this,
					LeaveandovertimeAddUSer.class);
			intent.putExtra("adduserid", "1");
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", userid);
			intent.putExtra("adduserid1", approvalID1.getText().toString());
			intent.putExtra("adduserid2", approvalID2.getText().toString());
			intent.putExtra("adduserid3", approvalID3.getText().toString());
			startActivityForResult(intent, 100);

		}
	}

	public class AddUser2 implements View.OnClickListener
	{
		public void onClick(View v)
		{
			TextView approvalID1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID1ID);
			TextView approvalID2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID2ID);
			TextView approvalID3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID3ID);
			Intent intent = new Intent(LeaveandovertimeNewListAdd.this,
					LeaveandovertimeAddUSer.class);
			intent.putExtra("adduserid", "2");
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", userid);
			intent.putExtra("adduserid1", approvalID1.getText().toString());
			intent.putExtra("adduserid2", approvalID2.getText().toString());
			intent.putExtra("adduserid3", approvalID3.getText().toString());
			startActivityForResult(intent, 100);

		}
	}

	public class AddUser3 implements View.OnClickListener
	{
		public void onClick(View v)
		{
			/*
			 * parentList = new ArrayList<Map<String, Object>>(); allchildList =
			 * new ArrayList<ArrayList<HashMap<String, Object>>>(); name=null;
			 * 
			 * appid=3; // 人员获取 ListTask1 task = new
			 * ListTask1(LeaveandovertimeAdd.this); // 异步 task.execute();
			 */

			TextView approvalID1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID1ID);
			TextView approvalID2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID2ID);
			TextView approvalID3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID3ID);
			Intent intent = new Intent(LeaveandovertimeNewListAdd.this,
					LeaveandovertimeAddUSer.class);
			intent.putExtra("adduserid", "3");
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", userid);
			intent.putExtra("adduserid1", approvalID1.getText().toString());
			intent.putExtra("adduserid2", approvalID2.getText().toString());
			intent.putExtra("adduserid3", approvalID3.getText().toString());
			startActivityForResult(intent, 100);
		}

	}

	public class kindText implements View.OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(LeaveandovertimeNewListAdd.this,
					LeaveandovertimeNewListAddKindName.class);
			intent.putExtra("kind", kind);
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
			if (eText.getText().toString().equals("请填写事由"))
			{
				eText.setText("");
			}
		}
	}

	public class Neirong implements View.OnClickListener
	{
		public void onClick(View v)
		{

			findViewById(R.id.leaveandovertime_add_leaveOrOtReasonNeirong)
					.setFocusableInTouchMode(true);
		}
	}

	public void GetKindtype(String result)
	{

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("leaveOrOtKind");
			kindNameList = new String[jsonArray.length()];
			kindIdList = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				kindNameList[i] = jsonObject.getString("kindName");
				kindIdList[i] = jsonObject.getString("kindID");
			}

		} catch (Exception e)
		{
			Toast.makeText(LeaveandovertimeNewListAdd.this, "数据获取失败",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void GetTime(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			declareTime = data.getString("serverTime");

		} catch (Exception e)
		{
			Toast.makeText(LeaveandovertimeNewListAdd.this, "数据获取失败",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	// 显示日期
	class ShowDate implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			new DatePickerDialog(LeaveandovertimeNewListAdd.this,
					new OnDateSetListener()
					{
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth)
						{

							if (year < 1901 || year > 2049)
							{
								// 不在查询范围内
								new AlertDialog.Builder(
										LeaveandovertimeNewListAdd.this)
										.setTitle("错误日期")
										.setMessage(
												"跳转日期范围(1901/1/1-2049/12/31)")
										.setPositiveButton("确认", null).show();
							} else
							{
								timeList[0] = year;
								timeList[1] = monthOfYear + 1;
								timeList[2] = dayOfMonth;

								String time = "";
								if (timeList[1] < 10)
								{
									time = timeList[0] + "-0" + timeList[1];
								} else
								{
									time = timeList[0] + "-" + timeList[1];
								}
								if (timeList[2] < 10)
								{
									time = time + "-0" + timeList[2];
								} else
								{
									time = time + "-" + timeList[2];
								}
								beginDate.setText(time);

								// 计算天数
								String time1 = endDate.getText().toString();
								String time2 = beginDate.getText().toString();
								long quot = 0;
								SimpleDateFormat ft = new SimpleDateFormat(
										"yyyy-MM-dd HH");
								try
								{
									Date date1 = ft.parse(time1);
									Date date2 = ft.parse(time2);
									quot = date1.getTime() - date2.getTime();
									quot = quot / 1000 / 60 / 60 / 24; // 计算几天
									// quot1 =
									// quot1%(1000*24*60*60)/(1000*60*60);//计算差多少小时

									int lt = (int) quot;
									if (lt == 0)
									{
										if (beginDate_morning.getText()
												.toString().equals("上午"))
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												lastDate.setText("半天");
												timestring = "4";
											} else
											{
												lastDate.setText("1天");
												timestring = "8";
											}
										} else
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												Toast.makeText(
														LeaveandovertimeNewListAdd.this,
														"结束时间不能早于开始时间",
														Toast.LENGTH_SHORT)
														.show();
												lastDate.setText("");
												timestring = "";
											} else
											{
												lastDate.setText("半天");
												timestring = "4";
											}
										}
									} else if (lt < 0)
									{
										Toast.makeText(
												LeaveandovertimeNewListAdd.this,
												"结束时间不能早于开始时间",
												Toast.LENGTH_SHORT).show();
										lastDate.setText("");
										timestring = "";
									} else
									{
										if (beginDate_morning.getText()
												.toString().equals("上午"))
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												lastDate.setText(lt + "天半");
												timestring = lt * 8 + 4 + "";
											} else
											{
												lastDate.setText(lt + 1 + "天");
												timestring = lt * 8 + 8 + "";
											}
										} else
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												lastDate.setText(lt + "天");
												timestring = lt * 8 + "";
											} else
											{
												lastDate.setText(lt + "天半");
												timestring = lt * 8 + 4 + "";
											}
										}
									}
								} catch (Exception e)
								{

								}
							}
						}
					}, timeList[0], timeList[1] - 1, timeList[2]).show();

		}

	}

	// 显示日期
	class ShowDate1 implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			new DatePickerDialog(LeaveandovertimeNewListAdd.this,
					new OnDateSetListener()
					{
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth)
						{

							if (year < 1901 || year > 2049)
							{
								// 不在查询范围内
								new AlertDialog.Builder(
										LeaveandovertimeNewListAdd.this)
										.setTitle("错误日期")
										.setMessage(
												"跳转日期范围(1901/1/1-2049/12/31)")
										.setPositiveButton("确认", null).show();
							} else
							{
								timeList[0] = year;
								timeList[1] = monthOfYear + 1;
								timeList[2] = dayOfMonth;

								String time = "";
								if (timeList[1] < 10)
								{
									time = timeList[0] + "-0" + timeList[1];
								} else
								{
									time = timeList[0] + "-" + timeList[1];
								}
								if (timeList[2] < 10)
								{
									time = time + "-0" + timeList[2];
								} else
								{
									time = time + "-" + timeList[2];
								}
								endDate.setText(time);

								// 计算天数
								String time1 = endDate.getText().toString();
								String time2 = beginDate.getText().toString();
								long quot = 0;
								SimpleDateFormat ft = new SimpleDateFormat(
										"yyyy-MM-dd");
								try
								{
									Date date1 = ft.parse(time1);
									Date date2 = ft.parse(time2);
									quot = date1.getTime() - date2.getTime();
									quot = quot / 1000 / 60 / 60 / 24; // 计算几天
									// quot1 =
									// quot1%(1000*24*60*60)/(1000*60*60);//计算差多少小时

									int lt = (int) quot;
									if (lt == 0)
									{
										if (beginDate_morning.getText()
												.toString().equals("上午"))
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												lastDate.setText("半天");
												timestring = "4";
											} else
											{
												lastDate.setText("1天");
												timestring = "8";
											}
										} else
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												Toast.makeText(
														LeaveandovertimeNewListAdd.this,
														"结束时间不能早于开始时间",
														Toast.LENGTH_SHORT)
														.show();
												lastDate.setText("");
												timestring = "";
											} else
											{
												lastDate.setText("半天");
												timestring = "4";
											}
										}
									} else if (lt < 0)
									{
										Toast.makeText(
												LeaveandovertimeNewListAdd.this,
												"结束时间不能早于开始时间",
												Toast.LENGTH_SHORT).show();
										lastDate.setText("");
										timestring = "";
									} else
									{
										if (beginDate_morning.getText()
												.toString().equals("上午"))
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												lastDate.setText(lt + "天半");
												timestring = lt * 8 + 4 + "";
											} else
											{
												lastDate.setText(lt + 1 + "天");
												timestring = lt * 8 + 8 + "";
											}
										} else
										{
											if (endDate_afternoon.getText()
													.toString().equals("上午"))
											{
												lastDate.setText(lt + "天");
												timestring = lt * 8 + "";
											} else
											{
												lastDate.setText(lt + "天半");
												timestring = lt * 8 + 4 + "";
											}
										}
									}
								} catch (Exception e)
								{

								}

							}
						}
					}, timeList[0], timeList[1] - 1, timeList[2]).show();

		}

	}

	class Sumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			// 获得要传的值
			TextView approvalID1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID1ID);
			TextView approvalID2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID2ID);
			TextView approvalID3 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_ApprovalID3ID);
			EditText ReasonText = (EditText) findViewById(R.id.leaveandovertime_newlist_add_leaveOrOtReasonNeirong);
			TextView Data1 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_beginDateNeirong);
			TextView Data2 = (TextView) findViewById(R.id.leaveandovertime_newlist_add_endDateNeirong);
			EditText addressText = (EditText) findViewById(R.id.leaveandovertime_newlist_add_addressneirong);

			String id1 = approvalID1.getText().toString();
			String id2 = approvalID2.getText().toString();
			String id3 = approvalID3.getText().toString();
			String kindname = kindIDText.getText().toString();
			String begintime = Data1.getText().toString();
			String endtime = Data2.getText().toString();
			String Reason = ReasonText.getText().toString();
			String address = addressText.getText().toString();

			if (beginDate_morning.getText().toString().equals("上午"))
			{
				begintime = begintime + " 08:00";
			} else
			{
				begintime = begintime + " 13:00";
			}
			if (endDate_afternoon.getText().toString().equals("上午"))
			{
				endtime = endtime + " 12:00";
			} else
			{
				endtime = endtime + " 17:00";
			}

			if (id1.equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "请选择审批人",
						Toast.LENGTH_SHORT).show();
			} else 
			if (kindname.equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "请填写假别",
						Toast.LENGTH_SHORT).show();
			} else if (id2.equals("") && !id3.equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "请填写上级领导",
						Toast.LENGTH_SHORT).show();
			} else if (Reason.equals("") || Reason.equals("请填写事由"))
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "请填写事由",
						Toast.LENGTH_SHORT).show();
			} else if (Reason.length() > 200)
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "事由不能大于200个字数",
						Toast.LENGTH_SHORT).show();
			} else if (lastDate.getText().toString().equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "结束时间不能早于开始时间",
						Toast.LENGTH_SHORT).show();
			} else if (kind.equals("3") && address.equals(""))
			{
				Toast.makeText(LeaveandovertimeNewListAdd.this, "请填写出差地点",
						Toast.LENGTH_SHORT).show();
			} else if (id2.equals(""))
			{
				//System.out.println("timestring====" + timestring);
				actionUrl = HttpUtil.BASE_URL + "newLeaveAndOvertime";
				param1 = "leaveOrOtKindbean.approvalID1="
						+ id1
						+ "&leaveOrOtKindbean.approvalID2=0&leaveOrOtKindbean.approvalID3=0&leaveOrOtKindbean.leaveOrOtUser="
						+ userid + "&leaveOrOtKindbean.kindName=" + kindname
						+ "&leaveOrOtKindbean.leaveOrOtReason=" + Reason
						+ "&leaveOrOtKindbean.beginDate=" + begintime
						+ "&leaveOrOtKindbean.endDate=" + endtime
						+ "&leaveOrOtKindbean.address=" + address
						+ "&leaveOrOtKindbean.lastDate=" + timestring;
				
				ListTask4 task = new ListTask4(LeaveandovertimeNewListAdd.this); 
				task.execute();

				
				

			}
			else if (id3.equals(""))
			{ // 1 2
				// Toast.makeText(LeaveandovertimeAdd.this, "成功2",
				// Toast.LENGTH_SHORT).show();
				// 提交
				if (id1.equals(id2))
				{
					Toast.makeText(LeaveandovertimeNewListAdd.this,
							"审批人不能是同一个人", Toast.LENGTH_SHORT).show();
				} else
				{

					actionUrl = HttpUtil.BASE_URL + "newLeaveAndOvertime";
					param1 = "leaveOrOtKindbean.approvalID1="
							+ id1
							+ "&leaveOrOtKindbean.approvalID2="
							+ id2
							+ "&leaveOrOtKindbean.approvalID3=0&leaveOrOtKindbean.leaveOrOtUser="
							+ userid + "&leaveOrOtKindbean.kindName="
							+ kindname + "&leaveOrOtKindbean.leaveOrOtReason="
							+ Reason + "&leaveOrOtKindbean.beginDate="
							+ begintime + "&leaveOrOtKindbean.endDate="
							+ endtime + "&leaveOrOtKindbean.address=" + address
							+ "&leaveOrOtKindbean.lastDate=" + timestring;
					ListTask4 task = new ListTask4(
							LeaveandovertimeNewListAdd.this); // 异步
					task.execute();
				}

			} else
			{ // 1 2 3
				// Toast.makeText(LeaveandovertimeAdd.this, "成功3",
				// Toast.LENGTH_SHORT).show();
				// 提交
				if (id1.equals(id2) || id1.equals(id3) || id2.equals(id3))
				{
					Toast.makeText(LeaveandovertimeNewListAdd.this,
							"审批人不能是同一个人", Toast.LENGTH_SHORT).show();
				} else
				{
					actionUrl = HttpUtil.BASE_URL + "newLeaveAndOvertime";
					param1 = "leaveOrOtKindbean.approvalID1=" + id1
							+ "&leaveOrOtKindbean.approvalID2=" + id2
							+ "&leaveOrOtKindbean.approvalID3=" + id3
							+ "&leaveOrOtKindbean.leaveOrOtUser=" + userid
							+ "&leaveOrOtKindbean.kindName=" + kindname
							+ "&leaveOrOtKindbean.leaveOrOtReason=" + Reason
							+ "&leaveOrOtKindbean.beginDate=" + begintime
							+ "&leaveOrOtKindbean.endDate=" + endtime
							+ "&leaveOrOtKindbean.address=" + address
							+ "&leaveOrOtKindbean.lastDate=" + timestring;
					ListTask4 task = new ListTask4(
							LeaveandovertimeNewListAdd.this); // 异步
					task.execute();
				}
			}
		}

	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(LeaveandovertimeNewListAdd.this, this);	
	    	pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			return query.GetServiceTime();
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			GetTime(result);// 获得数据

			// 进港时间
			String[] substring = declareTime.split(" ");
			String[] substring1 = substring[0].split("-");
			String[] substring2 = substring[1].split(":");
			timeList[0] = Integer.parseInt(substring1[0]);
			timeList[1] = Integer.parseInt(substring1[1]);
			timeList[2] = Integer.parseInt(substring1[2]);
			timeList[3] = Integer.parseInt(substring2[0]);
			timeList[4] = Integer.parseInt(substring2[1]);
			timeList1[0] = Integer.parseInt(substring1[0]);
			timeList1[1] = Integer.parseInt(substring1[1]);
			timeList1[2] = Integer.parseInt(substring1[2]);
			timeList1[3] = Integer.parseInt(substring2[0]);
			timeList1[4] = Integer.parseInt(substring2[1]);
			beginDate
					.setText(declareTime.substring(0, declareTime.indexOf(" ")));
			endDate.setText(declareTime.substring(0, declareTime.indexOf(" ")));

			super.onPostExecute(result);
		}

	}

	class ListTask4 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask4(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(
					LeaveandovertimeNewListAdd.this, this);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;// 取消异步

			// UploadActivity.tool.addFile("请假加班", actionUrl, param1);
			/*
			 * Intent intent=new Intent(LeaveandovertimeNewListAdd.this,
			 * leaveandovertimeNewListMain.class); intent.putExtra("User",
			 * user); startActivity(intent); setResult(20); finish();
			 */

			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = "";

			try
			{
				hfu.sendPost1(actionUrl, param1);
				result = "申请成功";
				Intent intent = new Intent(LeaveandovertimeNewListAdd.this,
						leaveandovertimeNewListMain.class);
				intent.putExtra("User", user);
				startActivity(intent);
				setResult(20);
				finish();

			} catch (Exception e)
			{
				result = "申请失败";
				e.printStackTrace();
			}
			if(user!=null)
			new Log(user.getName(), "添加请假加班", GlobalVar.LOGSAVE, "").execute();

			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			Toast.makeText(LeaveandovertimeNewListAdd.this, result,
					Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

	}

	class ListTaskApproval extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTaskApproval(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(LeaveandovertimeNewListAdd.this, this);	
	    	  pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			return query.LeaveOrOtApproval(userid);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getNeirongapproval(result);// 获得数据

			super.onPostExecute(result);
		}
	}

	public void getNeirongapproval(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("approvalbean");
			if (jsonArray.length() == 0)
			{
				// Toast.makeText(LeaveandovertimeNewListAdd.this,
				// "请添加请假加班的审批人", Toast.LENGTH_SHORT).show();
			} else
			{
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				if (jsonObject.getString("approvalName1").equals("0"))
				{

				} else
				{
					adduser1.setText(jsonObject.getString("approvalName1"));
					id1.setText(jsonObject.getString("approval1"));
				}

				if (jsonObject.getString("approvalName2").equals("0"))
				{

				} else
				{
					adduser2.setText(jsonObject.getString("approvalName2"));
					id2.setText(jsonObject.getString("approval2"));
				}

				if (jsonObject.getString("approvalName3").equals("0"))
				{

				} else
				{
					adduser3.setText(jsonObject.getString("approvalName3"));
					id3.setText(jsonObject.getString("approval3"));
				}

			}
		} catch (Exception e)
		{
			Toast.makeText(LeaveandovertimeNewListAdd.this, "数据异常",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	

	private void showtimePopupWindow(View v, String arg)
	{
		// 获取要显示在PopupWindow上的窗体视图
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.leaveandovertime_popw_listview,
				null);
		// 实例化并且设置PopupWindow显示的视图
		// popupWindow = new PopupWindow(view, et01.getWidth(),
		// LayoutParams.WRAP_CONTENT);
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// 获取PopupWindow中的控件
		ListView listview = (ListView) view
				.findViewById(R.id.leaveandovertime_listview);

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String[] timetypes = getResources().getStringArray(
				R.array.leaveandovertime_newlist_add_time);
		for (int i = 0; i < timetypes.length; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", timetypes[i]);
			map.put("id", arg);
			list.add(map);
		}
		// 绑定ListView的数据
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.leaveandovertime_popw_item, new String[] { "name",
						"id" }, new int[] { R.id.popw_name, R.id.popw_id });
		listview.setAdapter(adapter);
		// 设置ListView点击事件
		listview.setOnItemClickListener(new TimeListItem());

		// 想要让PopupWindow中的控件能够使用，就必须设置PopupWindow为focusable
		popupWindow.setFocusable(true);

		// 想做到在你点击PopupWindow以外的区域时候让PopupWindow消失就做如下两步操作
		// 1：设置setOutsideTouchable为ture，这个很容易理解吧，跟AlertDialog一样的
		popupWindow.setOutsideTouchable(true);
		// 2：设置PopupWindow的背景不能为空，所以你就随便设置个背景吧，你不用担心背景会影响你的显示效果
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.white));

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		popupWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});

		popupWindow.showAtLocation(scroll, Gravity.CENTER, 0, 0);
	}

	// 让PopupWindow消失
	private void dismissPopupWindow()
	{
		if (popupWindow != null && popupWindow.isShowing())
		{
			popupWindow.dismiss();
		}
	}

	class TimeListItem implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv_name = (TextView) arg1.findViewById(R.id.popw_name);
			TextView tv_id = (TextView) arg1.findViewById(R.id.popw_id);
			if (tv_name.getText().toString().equals("取消"))
			{
				dismissPopupWindow();
			} else
			{
				dismissPopupWindow();
				if (tv_id.getText().toString().equals("1"))
				{
					beginDate_morning.setText(tv_name.getText().toString());

					// 计算天数
					String time1 = endDate.getText().toString();
					String time2 = beginDate.getText().toString();
					long quot = 0;
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
					try
					{
						Date date1 = ft.parse(time1);
						Date date2 = ft.parse(time2);
						quot = date1.getTime() - date2.getTime();
						quot = quot / 1000 / 60 / 60 / 24; // 计算几天
						// quot1 = quot1%(1000*24*60*60)/(1000*60*60);//计算差多少小时

						int lt = (int) quot;
						if (lt == 0)
						{
							if (beginDate_morning.getText().toString()
									.equals("上午"))
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									lastDate.setText("半天");
									timestring = "4";
								} else
								{
									lastDate.setText("1天");
									timestring = "8";
								}
							} else
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									Toast.makeText(
											LeaveandovertimeNewListAdd.this,
											"结束时间不能早于开始时间", Toast.LENGTH_SHORT)
											.show();
									lastDate.setText("");
									timestring = "";
								} else
								{
									lastDate.setText("半天");
									timestring = "4";
								}
							}
						} else if (lt < 0)
						{
							Toast.makeText(LeaveandovertimeNewListAdd.this,
									"结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
							lastDate.setText("");
							timestring = "";
						} else
						{
							if (beginDate_morning.getText().toString()
									.equals("上午"))
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									lastDate.setText(lt + "天半");
									timestring = lt * 8 + 4 + "";
								} else
								{
									lastDate.setText(lt + 1 + "天");
									timestring = lt * 8 + 8 + "";
								}
							} else
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									lastDate.setText(lt + "天");
									timestring = lt * 8 + "";
								} else
								{
									lastDate.setText(lt + "天半");
									timestring = lt * 8 + 4 + "";
								}
							}
						}
					} catch (Exception e)
					{

					}

				} else
				{
					endDate_afternoon.setText(tv_name.getText().toString());

					// 计算天数
					String time1 = endDate.getText().toString();
					String time2 = beginDate.getText().toString();
					long quot = 0;
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
					try
					{
						Date date1 = ft.parse(time1);
						Date date2 = ft.parse(time2);
						quot = date1.getTime() - date2.getTime();
						quot = quot / 1000 / 60 / 60 / 24; // 计算几天
						// quot1 = quot1%(1000*24*60*60)/(1000*60*60);//计算差多少小时

						int lt = (int) quot;
						if (lt == 0)
						{
							if (beginDate_morning.getText().toString()
									.equals("上午"))
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									lastDate.setText("半天");
									timestring = "4";
								} else
								{
									lastDate.setText("1天");
									timestring = "8";
								}
							} else
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									Toast.makeText(
											LeaveandovertimeNewListAdd.this,
											"结束时间不能早于开始时间", Toast.LENGTH_SHORT)
											.show();
									lastDate.setText("");
									timestring = "";
								} else
								{
									lastDate.setText("半天");
									timestring = "4";
								}
							}
						} else if (lt < 0)
						{
							Toast.makeText(LeaveandovertimeNewListAdd.this,
									"结束时间不能早于开始时间", Toast.LENGTH_SHORT).show();
							lastDate.setText("");
							timestring = "";
						} else
						{
							if (beginDate_morning.getText().toString()
									.equals("上午"))
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									lastDate.setText(lt + "天半");
									timestring = lt * 8 + 4 + "";
								} else
								{
									lastDate.setText(lt + 1 + "天");
									timestring = lt * 8 + 8 + "";
								}
							} else
							{
								if (endDate_afternoon.getText().toString()
										.equals("上午"))
								{
									lastDate.setText(lt + "天");
									timestring = lt * 8 + "";
								} else
								{
									lastDate.setText(lt + "天半");
									timestring = lt * 8 + 4 + "";
								}
							}
						}
					} catch (Exception e)
					{

					}
				}
			}
		}
	}

}
