package net.hxkg.channel;

import com.example.huzhouport.R;
import com.huzhouport.main.User;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

public class IssueListActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issuelistactivity);		
		initData();
	}
	
	private void initData()
	{
		CruiseRecord cruiseRecord=(CruiseRecord) getIntent().getSerializableExtra("CruiseRecord");
		User user=(User) getIntent().getSerializableExtra("User");
		FragmentManager fragmentManager=getFragmentManager();
		IssueFragment issFragment=new IssueFragment();
		Bundle bundle=new Bundle();
		bundle.putSerializable("CruiseRecord", cruiseRecord);
		if(user!=null)
			bundle.putSerializable("User", user);
		issFragment.setArguments(bundle);
		fragmentManager.beginTransaction().replace(R.id.frame, issFragment).commit();
	}
	
	public void onBack(View view)
	{
		this.finish();
	}
	
	
}
