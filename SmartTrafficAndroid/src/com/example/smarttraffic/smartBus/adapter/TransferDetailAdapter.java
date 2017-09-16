package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.bike.fragment.BikeStationInfoListFragment;
import com.example.smarttraffic.bikenew.BikeNewFavorActivity;
import com.example.smarttraffic.smartBus.SelectLineActivity;
import com.example.smarttraffic.smartBus.bean.TransferLine;
import com.example.smarttraffic.util.StartIntent;

/**
 * 换乘详细信息视频器
 * 
 * @author Administrator zhou
 * 
 */
public class TransferDetailAdapter extends BaseAdapter
{
	List<TransferLine> data;
	String start;
	String end;
	Activity context;

	public TransferDetailAdapter(Activity context, List<TransferLine> data,
			String start, String end)
	{
		this.data = data;
		this.context = context;
		this.start = start;
		this.end = end;

		System.out.println("init");
	}

	public List<TransferLine> getData()
	{
		return data;
	}

	@Override
	public int getCount()
	{

		if (data == null)
			return 0;

		int c = data.size();

		c += 2;

		return c;
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{

		System.out.println("id:" + position);

		return position;
	}

	int seq = 0;

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		System.out.println(position + ":" + seq);

		ViewHolder holder;
		holder = new ViewHolder();

		convertView = LayoutInflater.from(context).inflate(
				R.layout.listview_smart_bus_transfer_detail, null);
		holder.imageView = (ImageView) convertView
				.findViewById(R.id.listview_transfer_detatil_image);
		holder.content = (TextView) convertView
				.findViewById(R.id.listview_transfer_detatil_content);

		if (!addExtra(holder, position))
		{

			TransferLine t = data.get(position - 1);

			if (t.getType() == 1)
			{
				holder.content.setText("乘坐" + t.getLineDetails().getName()
						+ "经过" + t.getLineDetails().getCount() + "站,到达"
						+ t.getStationName());

				Drawable drawable = context.getResources().getDrawable(
						R.drawable.arrow_right);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());

				holder.content.setCompoundDrawables(null, null, drawable, null);

				convertView.setOnClickListener(new goLineClick(t
						.getLineDetails().getName()));

				holder.imageView.setImageResource(R.drawable.smart_bus_bus);

				System.out.println(holder.content.getText().toString());

				seq++;
				return convertView;
			} else if (t.getType() == 0)
			{
				if (t.getStationType() == 2)
				{
					holder.content.setText("步行"
							+ t.getDistance()
							+ "米"
							+ ((t.getStationName() == null) ? "" : ",到达"
									+ t.getBikeStationAddress()) + "\n 在自行车租赁点("
							+ t.getStationName()+")租用自行车");
					
					
					Drawable drawable = context.getResources().getDrawable(
							R.drawable.arrow_right);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());

					holder.content.setCompoundDrawables(null, null, drawable, null);

					convertView.setOnClickListener(new goBikeClick(t.getStationID()));
					
				} else
				{
					holder.content.setText("步行"
							+ t.getDistance()
							+ "米"
							+ ((t.getStationName() == null) ? "" : ",到达"
									+ t.getStationName()));
				}
				holder.imageView.setImageResource(R.drawable.smart_bus_foot);

				System.out.println(holder.content.getText().toString());
				seq++;
				return convertView;
			} else if (t.getType() == 2)
			{
				holder.content.setText("自行车骑行"
						+ t.getDistance()
						+ "米"
						+ ((t.getStationName() == null) ? "" : ",到达"
								+ t.getBikeStationAddress())+"\n在自行车租赁点("+ t.getStationName()+")还车");
				
				Drawable drawable = context.getResources().getDrawable(
						R.drawable.arrow_right);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(),
						drawable.getMinimumHeight());

				holder.content.setCompoundDrawables(null, null, drawable, null);

				convertView.setOnClickListener(new goBikeClick(t.getStationID()));
				
				holder.imageView.setImageResource(R.drawable.smart_bus_bicycle);
				seq++;
				return convertView;
			}

		}

		return convertView;
	}

	class goBikeClick implements OnClickListener
	{
		int stationID;
		
		public goBikeClick(int stationID)
		{
			this.stationID = stationID;
		}


		@Override
		public void onClick(View v)
		{
			BikeStation b=new BikeStation();
			b.setId(stationID);
			
			Bundle bundle=new Bundle();
			bundle.putSerializable(BikeStationInfoListFragment.BIKE_STATION_INFO, b);
			bundle.putInt(BikeStationDetailActivity.IS_NEED_REQUEST, 1);
			
			StartIntent.startIntentWithParam(context, BikeStationDetailActivity.class,bundle);
		}
		
	}
	
	class goLineClick implements OnClickListener
	{
		String name;

		public goLineClick(String n)
		{
			this.name = n;
		}

		@Override
		public void onClick(View v)
		{
			Bundle bundle = new Bundle();
			bundle.putString(SelectLineActivity.SELECT_LINE_NAME, name);
			StartIntent.startIntentWithParam(context, SelectLineActivity.class,
					bundle);

		}
	}

	public boolean addExtra(ViewHolder holder, int position)
	{
		if (position == 0)
		{
			holder.content.setText("从" + start + "出发");
			// holder.imageView.setImageResource(R.drawable.smart_bus_foot);
		} else if (position == (getCount() - 1))
		{
			holder.content.setText("到达目的地" + end);
		} else
		{
			return false;
		}

		return true;
	}

	public void update(List<TransferLine> data)
	{
		this.data = null;
		if (data == null)
			this.data = new ArrayList<TransferLine>();
		else
			this.data = data;
		notifyDataSetChanged();
	}

	public void update()
	{
		notifyDataSetChanged();
	}

	class ViewHolder
	{
		ImageView imageView;
		TextView content;
	}
}