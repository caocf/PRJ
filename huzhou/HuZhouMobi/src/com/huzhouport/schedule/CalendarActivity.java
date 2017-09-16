package com.huzhouport.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.BorderText;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 日程安排主页
 * **/
@SuppressWarnings("unused")
public class CalendarActivity extends Activity implements OnGestureListener {

	private ViewFlipper flipper = null;// 滑动
	private GestureDetector gestureDetector = null;
	private CalendarView calV = null;
	private LunarCalendar lc = null;
	private GridView gridView = null;
	private BorderText topText = null;
	private Drawable draw = null;
	private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private ImageButton img_back, img_add;
	private String week = "";
	private int userId;// 用户Id

	List<Map<String, Object>> list;
	private String hiddenTime = "";
	private String hiddenYearAndMouth = "";
	private String[] AllTimeList,timeListByMonth;
	private Query query = new Query();
	private User user;
	AlarmManager am;// 管理器

	public CalendarActivity() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d",Locale.getDefault());
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.schedule_main_top);
		gestureDetector = new GestureDetector(this);
		flipper = (ViewFlipper) findViewById(R.id.schedule_flipper);
		img_back = (ImageButton) findViewById(R.id.schedule_main_imgback);
		img_add = (ImageButton) findViewById(R.id.schedule_main_add);
		flipper.removeAllViews();

		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		userId = user.getUserId();

		hiddenTime = countDay(jumpYear, jumpMonth, year_c, month_c) + "-"
				+ day_c;
		hiddenYearAndMouth = countDay(jumpYear, jumpMonth, year_c, month_c);
		
		//获取数据
		lc = new LunarCalendar();
		creatListView();
		
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		img_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CalendarActivity.this, ScheduleAdd.class);
				intent.putExtra("time", currentDate);
				intent.putExtra("User", user);
				startActivity(intent);
				finish();

			}
		});
	}

	
	// 滑动
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
		if (e1.getX() - e2.getX() > 120) {
			// 像左滑动
			addGridView(); // 添加一个gridView
			jumpMonth++; // 下一个月

			hiddenTime = countDay(jumpYear, jumpMonth, year_c, month_c) + "-"
					+ day_c;
			hiddenYearAndMouth = countDay(jumpYear, jumpMonth, year_c, month_c);
				
			getTimeListByMonth();
			
			calV = new CalendarView(this, getResources(), jumpMonth, jumpYear,
					year_c, month_c, day_c, timeListByMonth);
			gridView.setAdapter(calV);
			addTextToTopTextView(topText);
			gvFlag++;
			flipper.addView(gridView, gvFlag);
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_out));
			this.flipper.showNext();
			flipper.removeViewAt(0);
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
			// 向右滑动
			addGridView(); // 添加一个gridView
			jumpMonth--; // 上一个月

			hiddenTime = countDay(jumpYear, jumpMonth, year_c, month_c) + "-"
					+ day_c;
			hiddenYearAndMouth = countDay(jumpYear, jumpMonth, year_c, month_c);

			getTimeListByMonth();
			
			calV = new CalendarView(this, getResources(), jumpMonth, jumpYear,
					year_c, month_c, day_c, timeListByMonth);
			gridView.setAdapter(calV);
			gvFlag++;
			addTextToTopTextView(topText);
			flipper.addView(gridView, gvFlag);
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_out));
			this.flipper.showPrevious();
			flipper.removeViewAt(0);
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return this.gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	// 添加头部的年份 闰哪月等信息

	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		//draw = getResources().getDrawable(R.drawable.top_day);
		view.setBackgroundResource(R.drawable.top_day);
		textDate.append(calV.getShowYear()).append("年")
				.append(calV.getShowMonth()).append("月").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.BLACK);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	// 添加gridview
	@SuppressWarnings("deprecation")
	private void addGridView() {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// 取得屏幕的宽度和高度
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int Width = display.getWidth();
		int Height = display.getHeight();

		gridView = new GridView(this);
		gridView.setNumColumns(7);
		gridView.setColumnWidth(46);

		if (Width == 480 && Height == 800) {
			gridView.setColumnWidth(69);
		}
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT)); // 去除gridView边框
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setBackgroundResource(R.drawable.gridview_bk);
		gridView.setOnTouchListener(new OnTouchListener() {
			// 将gridview中的触摸事件回传给gestureDetector
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return CalendarActivity.this.gestureDetector
						.onTouchEvent(event);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			// gridView中的每一个item的点击事件
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position && position <= endPosition) {
					String scheduleDay = calV.getDateByClickItem(position)
							.split("\\.")[0]; // 这一天的阳历
					String scheduleLunarDay = calV.getDateByClickItem(position)
							.split("\\.")[1]; // 这一天的阴历
					String scheduleYear = calV.getShowYear();
					String scheduleMonth = calV.getShowMonth();

					String lunarDay = getLunarDay(
							Integer.parseInt(scheduleYear),
							Integer.parseInt(scheduleMonth),
							Integer.parseInt(scheduleDay));
					String lunarYear = String.valueOf(lc.getYear());
					String scheduleLunarMonth = lc.getLunarMonth();

					// 直接跳转到需要添加日程的界面

					// 得到这一天是星期几
					switch (position % 7) {
					case 0:
						week = "星期日";
						break;
					case 1:
						week = "星期一";
						break;
					case 2:
						week = "星期二";
						break;
					case 3:
						week = "星期三";
						break;
					case 4:
						week = "星期四";
						break;
					case 5:
						week = "星期五";
						break;
					case 6:
						week = "星期六";
						break;
					}

					Intent intent = new Intent(CalendarActivity.this,
							ScheduleItem.class);
					intent.putExtra("User", user);
					intent.putExtra("tv_day", scheduleDay);
					intent.putExtra("tv_year", scheduleYear);
					intent.putExtra("tv_mouth", scheduleMonth);
					intent.putExtra("week", week);
					intent.putExtra("tv_time", scheduleYear + "."
							+ scheduleMonth + "." + scheduleDay + "  " + week
							+ "\n" + scheduleLunarMonth + scheduleLunarDay);
					startActivity(intent);
					finish();
				}
			}
		});
		gridView.setLayoutParams(params);

	}

	public String getLunarDay(int year, int month, int day) {
		String lunarDay = lc.getLunarDate(year, month, day, true);
		// {由于在取得阳历对应的阴历日期时，如果阳历日期对应的阴历日期为"初一"，就被设置成了月份(如:四月，五月。。。等)},所以在此就要判断得到的阴历日期是否为月份，如果是月份就设置为"初一"
		if (lunarDay.substring(1, 2).equals("月")) {
			lunarDay = "初一";
		}
		return lunarDay;
	}

	// 列表
	public void creatListView() {
		new getScheduleByTime(this).execute();
	}

	class getScheduleByTime extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog = null;


		public getScheduleByTime(Context context) {
			//pDialog = ProgressDialog
				//	.show(context, "数据加载", "数据加载中，请稍候···", true);
			pDialog = new WaitingDialog().createDefaultProgressDialog(CalendarActivity.this, this);
			pDialog.show();	
		}
		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()) return null;//取消异步
			return query.queryScheduleByUserId(userId);
		}

		@Override
		protected void onPostExecute(String result) {
			if(result==null){
				Toast.makeText(CalendarActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			}else{
			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = data.getJSONArray("list");
				AllTimeList = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject3 = (JSONObject) jsonArray.opt(i);
					AllTimeList[i] = TimeFull(jsonObject3
							.getString("eventTime"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			getTimeListByMonth();
			calV = new CalendarView(CalendarActivity.this, getResources(),
					jumpMonth, jumpYear, year_c, month_c, day_c, timeListByMonth);

			addGridView();
			gridView.setAdapter(calV);
			flipper.addView(gridView, 0);
			topText = (BorderText) findViewById(R.id.schedule_toptext);
			addTextToTopTextView(topText);
			}
			if (pDialog != null)
				pDialog.dismiss();
			super.onPostExecute(result);
		}

	}
	private void getTimeListByMonth(){
		int j=0;
		for(int i=0;i<AllTimeList.length;i++){
			if(TimeFull2(AllTimeList[i]).equals(hiddenYearAndMouth))
				j++;
		}
		timeListByMonth=new String[j];
		int n=0;
		for(int i=0;i<AllTimeList.length;i++){
			if(TimeFull2(AllTimeList[i]).equals(hiddenYearAndMouth))
			{
				timeListByMonth[n]=AllTimeList[i];
				n++;
			}
		}
	}


	// 2012-01-01 xxxx转成2012-1-1
	public String TimeFull(String stime) {
		String res;
		String[] seventTimeList = stime.split(" ");
		String[] sTime = seventTimeList[0].split("-");
		res = sTime[0] + "-" + Integer.parseInt(sTime[1]) + "-"
				+ Integer.parseInt(sTime[2]);
		return res;
	}
	public String TimeFull2(String stime) {
		String res;
		String[] seventTimeList = stime.split(" ");
		String[] sTime = seventTimeList[0].split("-");
		res = sTime[0] + "-" + Integer.parseInt(sTime[1]);
		return res;
	}

	// 2012-1x转成2012-01
	public String TimeNotFull(String stime) {

		String[] seventTimeList = stime.split("-");
		String res = seventTimeList[0];
		for (int j = 1; j < seventTimeList.length; j++) {
			if (Integer.parseInt(seventTimeList[j]) < 10)
				seventTimeList[j] = "0" + seventTimeList[j];
			res = res + "-" + seventTimeList[j];
		}

		return res;
	}

	public String countDay(int ijyear, int ijmonth, int iyear, int imouth) {
		int stepYear = iyear + ijyear;
		int stepMonth = imouth + ijmonth;
		if (stepMonth > 0) {
			// 往下一个月滑动
			if (stepMonth % 12 == 0) {
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else {
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else {
			// 往上一个月滑动
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
		}
		return stepYear + "-" + stepMonth;
	}
}