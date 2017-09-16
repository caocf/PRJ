package com.example.smarttraffic.train;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.about.AboutActivity;
import com.example.smarttraffic.common.complain.ComplainActivity;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.HistoryDBOperate;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.SharePreference;
import com.example.smarttraffic.util.StartIntent;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 更多铁路信息
 * @author Administrator zhou
 *
 */
public class TrainMoreActivity extends FragmentActivity  {

	TextView selectDateTextView;
	ImageView selectShowTypeImageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bus_more);
		
		selectDateTextView=(TextView)findViewById(R.id.bus_more_select_date);
		selectShowTypeImageView=(ImageView)findViewById(R.id.bus_more_select_show_type);
		
		initHead(savedInstanceState);
		
		showImage(new SharePreference(this).getValueForBool(SharePreference.TRAIN_SHARE_IS_SHOW));
		showSelectDate(new SharePreference(this).getValueForInt(SharePreference.TRAIN_SHARE_DEFAULT_DATE));
	}
	
	private void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadNameFragment headFragment=new HeadNameFragment();
    		
    		String nameString="更多";
    		headFragment.setTitleName(nameString);
    		
    		ManagerFragment.replaceFragment(this, R.id.bus_more_head, headFragment);
        }
		
		((TextView)findViewById(R.id.bus_more_about)).setText("关于铁路信息");
		
	}
	
	public void click(View v)
	{
		switch (v.getId())
		{
			case R.id.bus_more_about:
				Bundle bundle=new Bundle();
				bundle.putInt(AboutActivity.ABOUT_ID, 3);
				StartIntent.startIntentWithParam(this, AboutActivity.class, bundle);
				break;
			case R.id.bus_more_suggestion:
				Bundle suggesiontBundle=new Bundle();
				suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 3);
				StartIntent.startIntentWithParam(this, SuggestionActivity.class,suggesiontBundle);
				break;
			case R.id.bus_more_complain:
				Bundle complainBundle=new Bundle();
				complainBundle.putInt(ComplainActivity.COMPLAIN_NAME, 3);
				StartIntent.startIntentWithParam(this, ComplainActivity.class,complainBundle);
				break;
			case R.id.bus_clear_history:
				
				GetDialog.messageDialog(this, "提示", "确定删除历史记录？", "是", new OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						HistoryDBOperate dbOperate=new HistoryDBOperate(TrainMoreActivity.this);
						dbOperate.deleteAll(ContentType.TRAIN_STATION_SEARCH_HISROTY);	
						dbOperate.deleteAll(ContentType.TRAIN_STATION_STATION_SEARCH_HISROTY);
						dbOperate.deleteAll(ContentType.TRAIN_STATION_NO_SEARCH_HISROTY);
						dbOperate.CloseDB();
						
						Toast.makeText(TrainMoreActivity.this, "历史记录清除成功", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				}, "否", new OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.cancel();
					}
				}).show();
				
				
				break;
				
			case R.id.bus_more_select_date:
				
				GetDialog.getRadioDialog(this, "出发日期", R.array.array_air_date, new OnItemSelectListener(), "", null, new SharePreference(this).getValueForInt(SharePreference.TRAIN_SHARE_DEFAULT_DATE)).show();
				
				break;
				
			case R.id.bus_more_select_show_type:
				SharePreference preference=new SharePreference(this);
				boolean b=preference.getValueForBool(SharePreference.TRAIN_SHARE_IS_SHOW);
				
				b=!b;
				preference.saveValeForBool(SharePreference.TRAIN_SHARE_IS_SHOW, b);
				showImage(b);
				
				break;
		}
	}
	
	class OnItemSelectListener implements OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			showSelectDate(which);
			new SharePreference(TrainMoreActivity.this).saveValeForInt(SharePreference.TRAIN_SHARE_DEFAULT_DATE, which);
			dialog.cancel();
		}

	}
	
	public void showSelectDate(int i)
	{
		String[] data=getResources().getStringArray(R.array.array_air_date);
		selectDateTextView.setText("第"+data[i]+"天");
	}
	
	public void showImage(boolean b)
	{
		if(b)
		{
			selectShowTypeImageView.setImageResource(R.drawable.switch_on_);
		}
		else
		{
			selectShowTypeImageView.setImageResource(R.drawable.switch_off_);
		}
	}
}
