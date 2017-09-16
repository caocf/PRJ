package com.hztuen.gh.activity.Adapter;

import java.util.List;

import android.content.Context;
import android.provider.Telephony.Sms.Conversations;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ghpublic.entity.CompanyNameEntity;
import com.hxkg.ghpublic.R;

public class CompanySearchAdapter extends BaseAdapter{
	private final static String TAG = CompanySearchAdapter.class.getSimpleName();
	private Context context;
	private List<CompanyNameEntity> cne;
	public CompanySearchAdapter(Context context,List<CompanyNameEntity> cne){
		this.context = context;
		this.cne = cne;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cne.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cne.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.companysearch_list_item, null);
		}
		TextView companyName = (TextView) convertView.findViewById(R.id.companyName);
		companyName.setText(cne.get(position).getCompanyName());
		return convertView;
	}

}
