package com.example.smarttraffic.drivingSchool;

import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class FavorDeleteActivity extends FragmentActivity {

	ListView favorListView;
	FavorAdapter favorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_favor_delete);
		
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("已选择0项");
		fragment.setRightName("全选");
		fragment.setRightListen(new selectAll());
		ManagerFragment.replaceFragment(this, R.id.driving_favor_delete_head, fragment);
		
		favorListView=(ListView)findViewById(R.id.delete_content);
		
		FavorDBOperate dbOperate=new FavorDBOperate(this);
		
		favorAdapter=new FavorAdapter(this, dbOperate.selectForTwoItem(ContentType.DRIBING_SCHOOL_FAVOR),2);
		favorListView.setAdapter(favorAdapter);
		favorListView.setOnItemClickListener(new listSelect());
		dbOperate.CloseDB();
	}
	
	class listSelect implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			favorAdapter.changeCheck(position);	
			((TextView)findViewById(R.id.common_head_title)).setText("已选择"+favorAdapter.countSelect()+"项");
		}
	}
	class selectAll implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			favorAdapter.selectALL(true);		
			((TextView)findViewById(R.id.common_head_title)).setText("已选择"+favorAdapter.countSelect()+"项");
		}
	}
	
	public void delete(View v)
	{
		List<TwoItemsData> datas=favorAdapter.getData();
		FavorDBOperate dbOperate=new FavorDBOperate(this);
		for(int i=0;i<datas.size();i++)
		{
			TwoItemsData temp=datas.get(i);
			if(temp.isSelect())
			{
				dbOperate.delete(temp.getId());
				datas.remove(i--);
			}
		}
		dbOperate.CloseDB();
		favorAdapter.refreshList(datas);
		((TextView)findViewById(R.id.common_head_title)).setText("已选择0项");
	}
}
