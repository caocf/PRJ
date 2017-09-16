package com.huzhouport.upload;

import java.util.ArrayList;

import com.example.huzhouportpublic.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class FileUploadFinishFragment extends android.support.v4.app.Fragment
{
	ListView listView;
	
	public static MyUploadFileListAdapter adapter;
		
	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.upload_finish_fragment, container,
				false);
		listView=(ListView)view.findViewById(R.id.listView2);
		adapter=new MyUploadFileListAdapter(getActivity(),(ArrayList<UploadFileInfo>) UploadActivity.tool.getSelectFile(false, true, true).clone());
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			
			 @Override
			 public void onItemClick(AdapterView<?> parent, View view,
			 int position, long id) {
			 adapter.changeCheck(position);
			 adapter.notifyDataSetChanged();
			 }
			
		 });
		
		return view;
	}
	
	
}
