package net.hxkg.company;



import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CompanySearchAdapter extends BaseAdapter
{
	private final static String TAG = CompanySearchAdapter.class.getSimpleName();
	private Context context;
	private List<CompanyNameEntity> cne;
	Drawable drawable;
	
	
	public CompanySearchAdapter(Context context,List<CompanyNameEntity> cne)
	{
		this.context = context;
		this.cne = cne;
		drawable=context.getResources().getDrawable(R.drawable.ic_gray_search);
	}
	
	public void setHistory(boolean is)	
	{
		if(is)
		{
			drawable=context.getResources().getDrawable(R.drawable.ic_historynotes);
		}
		else {
			drawable=context.getResources().getDrawable(R.drawable.ic_gray_search);
		}
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
		 ImageView img=(ImageView)convertView.findViewById(R.id.img);
		 img.setImageDrawable(drawable);
		
		return convertView;
	}

}
