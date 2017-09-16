package net.hxkg.ship;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CredCardAdapter extends BaseAdapter 
{

	private Context context;
	private List<CredCardModel> modellist;
	SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd");

	public CredCardAdapter(Context context, List<CredCardModel> modellist) {
		this.context = context;
		this.modellist = modellist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return modellist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return modellist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.cred_info_item, null);
        holder.ZSMC=(TextView)convertView.findViewById(R.id.tv_title);
        holder.FZRQ=(TextView)convertView.findViewById(R.id.tv_send_date_detail1);
        holder.YXRQ=(TextView)convertView.findViewById(R.id.tv_have_use_detail);
        holder.ZSBH=(TextView)convertView.findViewById(R.id.bh);
        
       
        holder.ZSMC.setText(modellist.get(position).getZSMC());
        String string=modellist.get(position).getFZRQ();
        holder.FZRQ.setText(string);
        String string1=modellist.get(position).getYXRQ();
        holder.YXRQ.setText(string1);
        String zsbh=modellist.get(position).getZSBH();
        holder.ZSBH.setText(zsbh);
        
        
        Date date=new Date();
      
        Date date1=null;
		try {
			date1 = (Date) sdDateFormat.parse(string1);
			if(date.after(date1))
	        {
				holder.YXRQ.setTextColor(Color.RED);
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		return convertView;
	}

	class ViewHolder {

		
		TextView ZSMC;//证书名称
		TextView FZRQ;//发证日期
		TextView YXRQ;//有效日期
		TextView ZSBH;//有效日期

	}
}
