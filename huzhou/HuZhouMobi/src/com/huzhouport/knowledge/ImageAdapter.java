package com.huzhouport.knowledge;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.huzhouport.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, Object>> knowledge=new ArrayList<HashMap<String, Object>>() ;
	private LayoutInflater mlInflater;
	private Context mContext;
	private ViewHolder holder;

	public ImageAdapter(ArrayList<HashMap<String, Object>> knowledge, Context mContext) {
		super();
		this.knowledge = knowledge;
		this.mContext = mContext;
		mlInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {

		return knowledge != null ? knowledge.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return knowledge.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mlInflater.inflate(R.layout.knowledge_item, null);
			holder.textView = (TextView) convertView.findViewById(R.id.knowledge_kindname);
			holder.textView1 = (TextView) convertView.findViewById(R.id.knowledge_kindid);
			holder.imagebutton = (ImageButton) convertView.findViewById(R.id.knowledge_xiazai);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(knowledge.get(position).get("kindName").toString());
		holder.textView1.setText(knowledge.get(position).get("kindID").toString());
		holder.imagebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, "第"+position+"个按钮被单击了", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView textView;
		TextView textView1;
		ImageButton imagebutton;
	}
}
