package net.hxkg.office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.book.BookActivity;
import net.hxkg.ghmanager.R;
import com.huzhouport.leaveandovertime.leaveandovertimeNewListMain;
import com.huzhouport.schedule.CalendarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class OfficeActivity extends Activity implements OnItemClickListener
{
	GridView gridView;	
    SimpleAdapter adapter;
    final String[] fromdata={"icon","text"};
    final int[] toview={R.id.griditem_image,R.id.griditem_text};
    final int[] icon ={R.drawable.ic_contacts,R.drawable.ic_knowledge_base,R.drawable.ic_punch_in_and_out,R.drawable.ic_calendar};
    final String[] iconName ={"通讯录","知识库","考勤","日程安排"};
    
    final Class<?>[] actionPage = {BookActivity.class,
    								ComprehensiveMain.class,
    							 	leaveandovertimeNewListMain.class,
    							 	CalendarActivity.class};
    
    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_office);
		
		gridView=(GridView) findViewById(R.id.gridView);
		adapter=new SimpleAdapter(this,getData(),R.layout.gridview_item_main,fromdata,toview);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
	}
    
    private List<Map<String, Object>> getData()
	{
		List<Map<String, Object>> data_list=new ArrayList<Map<String, Object>>();
		for(int i=0;i<icon.length;i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
		}		
		return data_list;
	} 
    
    public void onBack(View view)
    {
    	finish();
    }
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Intent intent=new Intent();
		
		intent.setClass(this, actionPage[position]);
		startActivity(intent);
		
	}

}
