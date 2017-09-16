package net.hxkg.channel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.huzhouport.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecordAdapter extends BaseAdapter
{
	Context context;
	List<CruiseRecord> dataList=new ArrayList<>();
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public RecordAdapter(Context context,List<CruiseRecord> dataList) 
	{
		this.context=context;
		this.dataList=dataList;
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView=View.inflate(context, R.layout.recordlistitem,null);
		TextView timeTextView=(TextView) convertView.findViewById(R.id.time);
		TextView meters=(TextView) convertView.findViewById(R.id.meters);
		TextView timelong=(TextView) convertView.findViewById(R.id.timelong);
		TextView issues=(TextView) convertView.findViewById(R.id.issues);
		TextView users=(TextView) convertView.findViewById(R.id.users);
		
		CruiseRecord cruiseRecord=dataList.get(position);
		timeTextView.setText(cruiseRecord.getStartTime());
		meters.setText(String.valueOf(cruiseRecord.getMeters())+"米");
		issues.setText(String.valueOf(cruiseRecord.getIssues()));
		String startime=cruiseRecord.getStartTime();
		String endtimeString=cruiseRecord.getEndTime();
		
		try {
			Date d1 = simpleDateFormat1.parse(startime);
			Date d2= simpleDateFormat1.parse(endtimeString);
			Date pDate=new Date(d2.getTime()-d1.getTime());
			timelong.setText(simpleDateFormat.format(pDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		try 
		{
			JSONArray userArray = new JSONArray(cruiseRecord.getUserArray());
			String useString="";
			for(int i=0;i<userArray.length();i++)
			{
				JSONObject user=userArray.getJSONObject(i);
				String nameString=user.getString("name");
				if("".equals(useString))
				{
					useString+=nameString;
				}else {
					useString+=","+nameString;
				}
			}
			users.setText(useString+"的巡航日志");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return convertView;
	}

}
