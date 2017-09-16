package com.hxkg.meeting;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import net.hxkg.channel.HttpRequest;
import com.example.clander.CalendarAdapter;
import com.example.clander.Constants;
import com.example.clander.SpecialCalendar;
import com.example.clander.adapter.TestAdapter;
import com.example.clander.custom.ScrollableLayout;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.nineoldandroids.view.ViewHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MeetingRecordActivity extends FragmentActivity implements OnGestureListener ,View.OnClickListener
{	
	private GestureDetector gestureDetector = null;
    private CalendarAdapter calV = null;
    private GridView gridView = null;
    private TextView topText = null;
    private ScrollableLayout mScrollLayout;
    private RelativeLayout mTopLayout;
    private LinearLayout mBtnLeft,mBtnRight;
    private ListView mListView;
    private TestAdapter mAdapter;
    private ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

    private  int jumpMonth = 0;      //每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private  int jumpYear = 0;       //滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";

    private float location;             // 最终决定的收缩比例值
    private float currentLoction = 1f;  // 记录当天的收缩比例值
    private float selectLoction = 1f;   // 记录选择那一天的收缩比例值
    
    int meetingRoomID=-1;

    public MeetingRecordActivity()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(new Date());  //当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
    }
    
    public void onBack(View view)
    {
    	finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meetingrecord_activity);
        
        meetingRoomID=getIntent().getIntExtra("roomid", -1);
        year_c=getIntent().getIntExtra("year", year_c);
        month_c=getIntent().getIntExtra("month", month_c);
        day_c=getIntent().getIntExtra("day", day_c);
        
        mListView = (ListView) findViewById(R.id.main_lv);        
        mAdapter = new TestAdapter(mData,this);
        mListView.setAdapter(mAdapter);
        
        gridView = (GridView) findViewById(R.id.gridview);
        calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,meetingRoomID);
        addGridView();
        gridView.setAdapter(calV);      

        mBtnLeft = (LinearLayout) findViewById(R.id.btn_prev_month);
        mBtnRight = (LinearLayout) findViewById(R.id.btn_next_month);
        mBtnLeft.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);

        // TODO 计算当天的位置和收缩比例
        SpecialCalendar calendar = new SpecialCalendar();
        boolean isLeapYear = calendar.isLeapYear(year_c);
        int days = calendar.getDaysOfMonth(isLeapYear,month_c);
        int dayOfWeek = calendar.getWeekdayOfMonth(year_c,month_c);
        int todayPosition = day_c;
        if (dayOfWeek != 7){
            days = days + dayOfWeek;
            todayPosition += dayOfWeek -1;
        }else{
            todayPosition -= 1;
        }
        /**
         * 如果 少于或者等于35天显示五行 多余35天显示六行
         * 五行: 收缩比例是：0.25，0.5，0.75，1
         * 六行: 收缩比例是：0.2，0.4，0.6，0.8，1
         */
        if (days <= 35){
            Constants.scale = 0.25f;
            currentLoction = (4 - todayPosition/7) * Constants.scale;
        }else{
            Constants.scale = 0.2f;
            currentLoction = (5 - todayPosition/7) * Constants.scale;
        }
        location = currentLoction;
        mTopLayout = (RelativeLayout) findViewById(R.id.rl_head);
        mScrollLayout = (ScrollableLayout)findViewById(R.id.scrollableLayout);
        mScrollLayout.setOnScrollListener(new ScrollableLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {

                ViewHelper.setTranslationY(mTopLayout, currentY * location);
            }
        });

        mScrollLayout.getHelper().setCurrentContainer(mListView);

        gestureDetector = new GestureDetector(this);
       
        
        topText = (TextView) findViewById(R.id.tv_month);
        addTextToTopTextView(topText);
        
        
        TextView titleTextView=(TextView) findViewById(R.id.title);
        titleTextView.setText(getIntent().getStringExtra("roomname")+"申请记录");
        
        if(calV.currentFlag!=-1)
        	getRecord(calV.currentDay,calV.currentMonth,calV.currentYear);
        else {
        	getRecord(calV.getShowYear(),calV.getShowMonth(),"01"); 	
        	
		}
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (e1.getX() - e2.getX() > 120) {
            //像左滑动
            jumpMonth++;     //下一个月
            upDateView();
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            //向右滑动
            jumpMonth--;     //上一个月
            upDateView();
            return true;
        }
        return false;
    }

    private void upDateView(){
        addGridView();   //添加一个gridView
        calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,meetingRoomID);
        gridView.setAdapter(calV);
      
        addTextToTopTextView(topText);
        
        
        if(calV.currentFlag!=-1)
        	getRecord(calV.currentDay,calV.currentMonth,calV.currentYear);
        else {
        	getRecord(calV.getShowYear(),calV.getShowMonth(),"01"); 	
        	
		}
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return this.gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) 
    {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY) 
    {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) 
    {
        return false;
    }

    //添加头部的年份 闰哪月等信息
    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        textDate.append(calV.getShowYear()).append("年").append(
                calV.getShowMonth()).append("月").append("\t");
        view.setText(textDate);
        view.setTextColor(Color.WHITE);
        view.setTypeface(Typeface.DEFAULT_BOLD);
    }

    //添加gridview
    private void addGridView() 
    {
        // TODO 如果滑动到其他月默认定位到第一行，划回本月定位到当天那行
        if (jumpMonth == 0)
        {
            location = currentLoction;
        }else
        {
            location = 1f;
        }
        // TODO 选择的月份 定位到选择的那天
        if (((jumpMonth + month_c)+"").equals(Constants.zMonth))
        {
            location = selectLoction;
        }

        gridView.setOnTouchListener(new OnTouchListener() {
            //将gridview中的触摸事件回传给gestureDetector
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return MeetingRecordActivity.this.gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() 
        {
            //gridView中的每一个item的点击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3)
            {
                //点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                String scheduleDay;
                String scheduleYear;
                String scheduleMonth;
                location = (float) ((5 - position/7) * 0.2);
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //这一天的阳历
                    //String scheduleLunarDay = calV.getDateByClickItem(position).split("\\.")[1];  //这一天的阴历
                    scheduleYear = calV.getShowYear();
                    scheduleMonth = calV.getShowMonth();
                    Constants.zYear = scheduleYear;
                    Constants.zMonth = scheduleMonth;
                    Constants.zDay = scheduleDay;

                    if (Constants.scale == 0.2f){
                        location = (5 - position/7) * Constants.scale;
                    }else{
                        location = (4 - position/7) * Constants.scale;
                    }
                    selectLoction = location;
                    calV.notifyDataSetChanged();
                    //Toast.makeText(MeetingRecordActivity.this, scheduleYear + "-" + scheduleMonth + "-" + scheduleDay, Toast.LENGTH_SHORT).show();
                    getRecord(scheduleDay,scheduleMonth,scheduleYear);
                   
                }               
                
            }

        });        
        
    }
    
    public void getRecord(String scheduleDay,String scheduleMonth,String scheduleYear)
    {        
        HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) {
				if("".equals(result.trim()))return;
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONArray array=object.getJSONArray("list");
					
				
					for(int i=0;i<array.length();i++)
					{
						JSONArray arr=array.getJSONArray(i);
						JSONObject meetingBasic=arr.getJSONObject(0);
						String topic=meetingBasic.getString("topic");
						String meetingdate=meetingBasic.getString("meetingdate");
						String meetingtime=meetingBasic.getString("meetingtime");
						int status=meetingBasic.getInt("depstatus");
						Map<String, Object> map=new HashMap<>();
						map.put("topic", topic);
						map.put("meetingdate", meetingdate);
						map.put("meetingtime", meetingtime);
						map.put("status", status);
						
						JSONObject user=arr.getJSONObject(1);
						String usernameString=user.getString("name");
						map.put("username", usernameString);
						
						mData.add(map);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				mAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onError(int httpcode) {
				mAdapter.notifyDataSetChanged();
				
			}
		});
        Map<String, Object> map=new HashMap<>();
        map.put("meetingBasic.meetingroom", meetingRoomID);
        map.put("meetingBasic.meetingdate", scheduleYear + "-" + scheduleMonth + "-" + scheduleDay);
        httpRequest.post(HttpUtil.BASE_URL+"queryApplicationRecordByDay", map);
        mData.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_prev_month:
                jumpMonth --;     //上一个月
                upDateView();
                break;
            case R.id.btn_next_month:
                jumpMonth ++;     //下一个月
                upDateView();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO 页面被销毁时，清空选择的日期数据
        Constants.zYear = "";
        Constants.zMonth ="";
        Constants.zDay = "";
    }
}
