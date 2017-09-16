package net.hxkg.channel;

import java.util.ArrayList;
import java.util.List;

import com.example.huzhouport.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IssueAdapter extends BaseAdapter 
{
	List<IssueEntity> dataList=new ArrayList<>();
	Context context;
	
	public  IssueAdapter(Context context,List<IssueEntity> dataList) 
	{
		this.context=context;
		this.dataList=dataList;
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
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView=View.inflate(context, R.layout.issueitem,null);
		TextView issuename=(TextView) convertView.findViewById(R.id.issuename);
		TextView kz=(TextView) convertView.findViewById(R.id.kz);
		TextView channel=(TextView) convertView.findViewById(R.id.channel);
		TextView lorr=(TextView) convertView.findViewById(R.id.lorr);
		TextView time=(TextView) convertView.findViewById(R.id.time);
		IssueEntity issueEntity=dataList.get(position);
		issuename.setText(issueEntity.getPtypename()+"-"+issueEntity.getTypename());
		kz.setText(issueEntity.getKzString());
		channel.setText(issueEntity.getChannelname());
		String lr=issueEntity.getLeftorright()==1?"左岸":"右岸";
		lorr.setText(lr);
		time.setText(issueEntity.getIssuetime());
		return convertView;
	}

}
