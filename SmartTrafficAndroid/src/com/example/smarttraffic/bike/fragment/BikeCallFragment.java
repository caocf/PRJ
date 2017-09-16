package com.example.smarttraffic.bike.fragment;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BikeCallFragment extends Fragment
{

	Button callButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_bike_call, container, false);
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("公共自行车");
		ManagerFragment.replaceFragment(getActivity(), R.id.bike_call_head, fragment);
		
		initView(rootView);
		
		return rootView;
	}
	
	private void initView(View rootView)
	{
		callButton=(Button)rootView.findViewById(R.id.bike_call_button);
		callButton.setOnClickListener(new onclick());
	}
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			switch (v.getId())
			{
				case R.id.bike_call_button:
					GetDialog.messageDialog(getActivity(), "提示：", "是否拨号", "是", new DialogInterface.OnClickListener()
					{
						
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.cancel();
						}
					}, "否", new DialogInterface.OnClickListener()
					{
						
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.cancel();
						}
					}).show();
					break;

			}
		}
		
	}
}
