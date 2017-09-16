package com.huzhouport.upload;

import java.util.ArrayList;

import net.hxkg.ghmanager.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyUploadFileListAdapter extends BaseAdapter
{
	
	Context mContent = null;
	ArrayList<UploadFileInfo> data = null;

	public MyUploadFileListAdapter(Context context, ArrayList<UploadFileInfo> arrayList) {
		this.mContent = context;
		this.data = arrayList;
		// isChecked=new ArrayList<Integer>();

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		convertView = LayoutInflater.from(mContent).inflate(
				R.layout.upload_file_listview, null);
		holder = new ViewHolder();

		holder.filename = (TextView) convertView
				.findViewById(R.id.upload_filename);
		holder.filestatus = (TextView) convertView
				.findViewById(R.id.upload_filestatus);
		holder.checked = (ImageView) convertView
				.findViewById(R.id.upload_check);

		if (data.get(position).isChecked())
			holder.checked.setImageDrawable(mContent.getResources().getDrawable(
					R.drawable.fileupload_check));
		else {
			holder.checked.setImageDrawable(mContent.getResources().getDrawable(
					R.drawable.fileupload_uncheck));
		}

		holder.filename.setText(data.get(position).getName());

		switch (data.get(position).getStatus()) {
		case -1:
			holder.filestatus.setText("�ϴ�ʧ��");
			holder.filestatus.setTextColor(0xffda251c);
			break;

		case 0:
			holder.filestatus
					.setText(data.get(position).getPercentString());
			break;

		case 1:
			holder.filestatus.setText("���");
			break;
		}

		return convertView;
	}


	public void changeCheck(int position) {
		data.get(position).setChecked(!data.get(position).isChecked());
	}



	public void updata(ArrayList<UploadFileInfo> d) {
		this.data.clear();
		this.data.addAll(d);
		notifyDataSetChanged();
	}



	class ViewHolder {
		TextView filename;
		TextView filestatus;
		ImageView checked;
	}
}
