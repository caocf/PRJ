package com.huzhouport.slidemenu;

import com.example.huzhouport.R;
import com.huzhouport.main.Login;
import com.huzhouport.main.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class RightMenuFragment extends Fragment
{
	private User user;	

	private ImageButton	img_login;

	@Override
	public void onAttach(Activity activity)
	{

		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		user = (User) getActivity().getIntent().getSerializableExtra("User");      

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_right_menu, null);

		findView(rootView);
		
		img_login = (ImageButton) rootView
				.findViewById(R.id.right_permsg_center_imgbtn_select);

		img_login.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (user != null)
				{
					Toast.makeText(getActivity(), "您已登录，无需重新登录",
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent intent = null;

					intent = new Intent(getActivity(), Login.class);
					// intent.putExtra("User", user);
					startActivity(intent);
				}

			}
		});

		return rootView;
	}

	private void findView(View rootView)
	{

	}

}
