package com.example.clander;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import net.hxkg.channel.HttpRequest;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter 
{
	private Context context;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	private SpecialCalendar sc = null;
	private LunarCalendar lc = null; 
	//基准时间
	private String sys_year = "";
	private String sys_month = "";
	private int mJumpMonth;
	int roomid;
	List<String> recorddays=new ArrayList<>();//有记录的天数
	public String currentYear;
	public String currentMonth;
	public String currentDay;	
	private int daysOfMonth ;      //某月的天数
	private int dayOfWeek ;        //具体某一天是星期几
	private int lastDaysOfMonth;  //上一个月的总天数
	private int items;	
	private String[] dayNumber;  //一个gridview中的日期存入此数组中
	public int currentFlag = -1;     //用于标记当天
	private String mDay;
	
		
	private String showYear = "";   //用于在头部显示的年份
	private String showMonth = "";  //用于在头部显示的月份
	private String animalsYear = ""; 
	private String leapMonth = "";   //闰哪一个月
	private String cyclical = "";   //天干地支			
	
	public CalendarAdapter(Context context,Resources rs,int jumpMonth,int jumpYear,int year_c,int month_c,int day_c,int roomid)
	{		
		this.context= context;
		sc = new SpecialCalendar();
		lc = new LunarCalendar();
		this.mJumpMonth = jumpMonth;
		this.roomid=roomid;
		sys_year=String.valueOf(year_c);
		sys_month=String.valueOf(month_c);

		int stepYear = year_c+jumpYear;
		int stepMonth = month_c+jumpMonth ;
		if(stepMonth > 0)
		{
			//往下一个月滑动
			if(stepMonth%12 == 0)//12月以内
			{
				stepYear = year_c + stepMonth/12 -1;
				stepMonth = 12;
			}else
			{
				stepYear = year_c + stepMonth/12;
				stepMonth = stepMonth%12;
			}
		}
		else
		{
			//往上一个月滑动
			stepYear = year_c - 1 + stepMonth/12;
			stepMonth = stepMonth%12 + 12;
			if(stepMonth%12 == 0){
				
			}
		}
	
		currentYear = String.valueOf(stepYear);  //得到当前的年份
		currentMonth = String.valueOf(stepMonth);  //得到本月 （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
		currentDay = String.valueOf(day_c);  //得到当前日期是哪天
		
		getCalendar(Integer.parseInt(currentYear),Integer.parseInt(currentMonth));
		
		 HttpRequest hRequest=new HttpRequest(new HttpRequest.onResult() 
		 {				
				@Override
				public void onSuccess(String result) {
					if("".equals(result))return;
					try {
						JSONObject object=new JSONObject(result.trim());
						JSONArray array=object.getJSONArray("list");
						for(int i=0;i<array.length();i++)
						{
							String timeString=array.getString(i).split("-")[2].substring(0,2);
							String dateString=array.getString(i).substring(0,10);
							Date date=sdf.parse(dateString);
							Calendar calendar=Calendar.getInstance();
							calendar.set(Calendar.HOUR_OF_DAY,0);
							calendar.set(Calendar.MINUTE,0);
							calendar.set(Calendar.SECOND,0);
							calendar.set(Calendar.MILLISECOND, 0);
							Date date2=calendar.getTime();
							long t1=date.getTime();
							long t2=date2.getTime();
							if(t1>=t2)
							{
								recorddays.add(timeString);
							}
							
						}
						
						notifyDataSetChanged();
					} catch (Exception e) {
					}				
					
				}
				
				@Override
				public void onError(int httpcode) {
					
				}
			});
	        Map<String, Object> map=new HashMap<>();
	        map.put("year", currentYear);
	        map.put("month", currentMonth);
	        map.put("meetingBasic.meetingroom", roomid);
	        hRequest.post(HttpUtil.BASE_URL+"queryApplicationRecord", map);
		
	}
	
	//得到某年的某月的天数且这月的第一天是星期几
	public void getCalendar(int year, int month)
	{
		boolean isLeapyear = sc.isLeapYear(year);              //是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);  //某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month);      //某月第一天为星期几
		lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month-1);  //上一个月的总天数

		int days = daysOfMonth;//界面天数
		if (dayOfWeek != 7)
		{
			days = days + dayOfWeek;
		}
		if (days <= 35)
		{
			items = 35;
			Constants.scale = 0.25f;
		}else
		{
			items = 42;
			Constants.scale = 0.2f;
		}

		getweek(year,month);
	}
	//将一个月中的每一天的值添加入数组dayNuber中
	private void getweek(int year, int month) 
	{
		int j = 1;
		String lunarDay = "";
		dayNumber = new String[items];
		//得到当前月的所有日程日期(这些日期需要标记)
		for (int i = 0; i < dayNumber.length; i++) 
		{
			 //前一个月
			if(i < dayOfWeek)
			{  
				int temp = lastDaysOfMonth - dayOfWeek+1;
				dayNumber[i] = (temp + i)+"."+lunarDay;				
			}
			else if(i < daysOfMonth + dayOfWeek)
			{   //本月
				String day = String.valueOf(i-dayOfWeek+1);   //得到的日期
				dayNumber[i] = i-dayOfWeek+1+"."+lunarDay;
				//对于当前月才去标记当前日期
				//if(sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day))
				if(sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && currentDay.equals(day))
				{
					//标记当前日期
					currentFlag = i;
				}
				setShowYear(String.valueOf(year));
				setShowMonth(String.valueOf(month));
				setAnimalsYear(lc.animalsYear(year));
				setLeapMonth(lc.leapMonth == 0?"":String.valueOf(lc.leapMonth));
				setCyclical(lc.cyclical(year));
			}else
			{   //下一个月
				dayNumber[i] = j+"."+lunarDay;
				j++;
			}
		}
	}
	
	@Override
	public int getCount() 
	{
		return items;
	}

	@Override
	public Object getItem(int position) 
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.tvtext);
		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		LinearLayout re = (LinearLayout) convertView.findViewById(R.id.back);
		mDay = dayNumber[position].split("\\.")[0];
		textView.setText(mDay + "");
		if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) 
		{
			// 当前月信息显示
			textView.setTextColor(Color.BLACK);// 当月字体设黑
			// 设置 选中的背景
			if (Constants.zYear.equals(currentYear) && Constants.zMonth.equals(currentMonth) && Constants.zDay.equals(mDay))
			{
				//re.setBackgroundResource(R.drawable.select_day_bg);
				re.setBackgroundColor(Color.parseColor("#0067ac"));
				textView.setTextColor(Color.WHITE);
			}else if(currentFlag == position)
			{
				//设置 当天的背景
				//re.setBackgroundResource(R.drawable.today_bg);
				re.setBackgroundColor(Color.RED);
				textView.setTextColor(Color.WHITE);
			} else if (!Constants.zMonth.equals(currentMonth) && mJumpMonth != 0 && "1".equals(mDay)){
				// TODO 切换月份时，如果该月不是当前月默认选第一天
				//re.setBackgroundResource(R.drawable.select_day_bg);
				re.setBackgroundColor(Color.parseColor("#0067ac"));
				textView.setTextColor(Color.WHITE);
			}else{
				re.setBackgroundResource(R.color.white);
			}
			
			for(String day:recorddays)
			{
				if(Integer.valueOf(day)==Integer.valueOf(mDay))
				{
					img.setVisibility(View.VISIBLE);
					break;
				}
			}
		}
		else
		{
			textView.setTextColor(Color.WHITE);
		}
		
		return convertView;
	}	
	/**
	 * 点击每一个item时返回item中的日期
	 * @param position
	 * @return
	 */
	public String getDateByClickItem(int position){
		return dayNumber[position];
	}
	
	/**
	 * 在点击gridView时，得到这个月中第一天的位置
	 * @return
	 */
	public int getStartPositon(){
		return dayOfWeek + 7;
	}
	
	/**
	 * 在点击gridView时，得到这个月中最后一天的位置
	 * @return
	 */
	public int getEndPosition(){
		return  (dayOfWeek + daysOfMonth + 7) - 1;
	}
	
	public String getShowYear() {
		return showYear;
	}

	public void setShowYear(String showYear) {
		this.showYear = showYear;
	}

	public String getShowMonth() {
		return showMonth;
	}

	public void setShowMonth(String showMonth) {
		this.showMonth = showMonth;
	}
	
	public String getAnimalsYear() {
		return animalsYear;
	}

	public void setAnimalsYear(String animalsYear) {
		this.animalsYear = animalsYear;
	}
	
	public String getLeapMonth() {
		return leapMonth;
	}

	public void setLeapMonth(String leapMonth) {
		this.leapMonth = leapMonth;
	}
	
	public String getCyclical() {
		return cyclical;
	}

	public void setCyclical(String cyclical) {
		this.cyclical = cyclical;
	}
}
