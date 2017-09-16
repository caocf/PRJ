package com.example.smarttraffic.alarm;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AlarmFavorAdapter extends BaseAdapter
{
	Context context;
	List<AlarmInfo> data;
	boolean delete;
	
	public List<AlarmInfo> getData()
	{
		return data;
	}

	public AlarmFavorAdapter(Context context,List<AlarmInfo> data,boolean delete)
	{
		this.context=context;
		this.data=data;
		this.delete=delete;		
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	String[] method=new String[]{"站","分","米"};
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_alarm_favor, null);
		
		Holder holder=new Holder();
		holder.alarmTime=(TextView)convertView.findViewById(R.id.listview_alarm_favor_time);
		holder.crowed=(TextView)convertView.findViewById(R.id.listview_alarm_favor_crowed);
		holder.date=(TextView)convertView.findViewById(R.id.listview_alarm_favor_date);
		holder.lineName=(TextView)convertView.findViewById(R.id.listview_alarm_favor_line);
		holder.planTime=(TextView)convertView.findViewById(R.id.listview_alarm_favor_plan_time);
		holder.stationName=(TextView)convertView.findViewById(R.id.listview_alarm_favor_station);
		
		AlarmInfo alarmInfo=data.get(position);
		
		holder.stationName.setText(alarmInfo.getStationName());
		
		holder.alarmTime.setText("提醒方式：提前"+alarmInfo.getTime()+method[alarmInfo.getMethod()]+"提醒");
		holder.lineName.setText("提醒线路："+alarmInfo.getLineName());
		
		if(alarmInfo.isAdvance())
		{
			holder.crowed.setText("是否考虑拥挤度："+(alarmInfo.isCrowed()?"是":"否"));
			holder.planTime.setText("上车时间："+alarmInfo.getTime());
			
			String t="";
			for(int i=0;i<alarmInfo.getWeeks().length;i++)
			{
				if(alarmInfo.getWeeks()[i])
					t+=dateToStr[i]+" ";
			}
			
			holder.date.setText("提醒周期："+t);
		}
		else
		{
			holder.crowed.setVisibility(View.GONE);
			holder.planTime.setVisibility(View.GONE);
			holder.date.setVisibility(View.GONE);
		}
		
		if(delete)
		{
			if(alarmInfo.isSelect)
			{
				exchange(holder.stationName, R.drawable.item_check);
			}
			else
			{
				exchange(holder.stationName, R.drawable.item_uncheck);
			}
		}
		else
		{
			if(alarmInfo.isOpen())
			{
				exchange(holder.stationName, R.drawable.switch_on_);
			}
			else
			{
				exchange(holder.stationName, R.drawable.switch_off_);
			}
		}
		
		holder.stationName.setOnClickListener(new onSelectClick(position));
		
		return convertView;
	}
	
	class onSelectClick implements View.OnClickListener
	{
		int position;
		public onSelectClick(int p)
		{
			this.position=p;
		}
		
		@Override
		public void onClick(View v)
		{
			changeCheck(position);
		}
	}
	
	private void exchange(TextView textView,int drawable)
	{
		Drawable drawable1=context.getResources().getDrawable(drawable);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
		
		textView.setCompoundDrawables(null, null, drawable1, null);

	}
	
	String[] dateToStr=new String[]{"周日","周一","周二","周三","周四","周五","周六"};

	public void refreshList(List<AlarmInfo> data)
	{
		this.data=data;
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data.clear();
		notifyDataSetChanged();
	}

	public void selectALL(boolean select)
	{
		for(int i=0;i<data.size();i++)
		{
			data.get(i).setSelect(select);
		}
		notifyDataSetChanged();
	}
	
	public void changeCheck(int position)
	{
		if(delete)
		{
			data.get(position).setSelect(!data.get(position).isSelect());
		}
		else
		{
			data.get(position).setOpen(!data.get(position).isOpen());
			FavorDBOperate dbOperate=new FavorDBOperate(context);
			dbOperate.delete(data.get(position).getId());
			dbOperate.insertAlarm(data.get(position).isUp()?ContentType.SMART_BUS_ALARM_FAVOR:ContentType.SMART_BUS_ALARM_DOWN_FAVOR, data.get(position));
			dbOperate.CloseDB();
			
			int c=0;
			boolean up=true;
			for(AlarmInfo a:data)
			{
				up=a.isUp();
				if(a.isOpen())
				{
					c++;
				}
			}
			
			/**
			 * 启动停止服务
			 */
			if(up)
			{
				if(c>0)
					context.startService(new Intent(context, UpService.class));
				else 
					context.stopService(new Intent(context, UpService.class));
			}
			else
			{
				if(c>0)
					context.startService(new Intent(context, DownService.class));
				else 
					context.stopService(new Intent(context, DownService.class));
			}
			
		}
		notifyDataSetChanged();
	}
	
	class Holder
	{
		public TextView stationName;
		public TextView lineName;
		public TextView alarmTime;
		public TextView planTime;
		public TextView date;
		public TextView crowed;
	}
}
