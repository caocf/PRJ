package net.hxkg.ship;
import java.util.List;
import com.tuen.util.SystemStatic;
import net.hxkg.ghmanager.R;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordLetInAdapter extends BaseAdapter{

	
	private Context context;
	private List<RecordLetIn> recordletinlist;
	
	
	public RecordLetInAdapter(Context context,List<RecordLetIn> recordletinlist){
		this.context = context;
		this.recordletinlist = recordletinlist;
	}
	@Override
	
	public int getCount() {
		
		return recordletinlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return recordletinlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;
		
		   
		
		holder = new ViewHolder();
		convertView=LinearLayout.inflate(context, R.layout.activity_letin_record_item, null);
		
		 		
		holder.tv_start = (TextView)convertView.findViewById(R.id.text3); 
		holder.tv_target = (TextView) convertView.findViewById(R.id.text3_context); 
	//	holder.tv_goods = (TextView) convertView.findViewById(R.id.date_time); 
		
		holder.tv_rank = (TextView) convertView.findViewById(R.id.text2_context); 
		holder.tv_tons = (TextView) convertView.findViewById(R.id.goods_weight); 
		holder.tv_unit = (TextView) convertView.findViewById(R.id.danwei); 
		holder.tv_berthtime = (TextView) convertView.findViewById(R.id.text4); 
		holder.tv_status = (TextView) convertView.findViewById(R.id.text_state); 
		holder.number = (TextView) convertView.findViewById(R.id.text1_context);
		
		holder.tv_ship_name = (TextView) convertView.findViewById(R.id.text2);
		
		
		final RecordLetIn model = recordletinlist.get(position);
		
		holder.tv_start.setText(model.getstart());
		holder.tv_target.setText(model.gettarget());
	//	holder.tv_goods.setText(model.getgoods());
		holder.tv_rank.setText(model.getrank());
		holder.tv_tons.setText(model.gettons());
		holder.tv_unit.setText(model.getunit());
		holder.tv_berthtime.setText(model.getberthtime());
		holder.tv_status.setText(model.getstatus());
		holder.number.setText(model.getnumber());
		holder.tv_ship_name.setText(SystemStatic.searchShipName);
		
		Button button=(Button) convertView.findViewById(R.id.btn_revise);
		button.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent(context,DangersLetInActivity.class);
				intent.putExtra("RecordLetIn", model);
				context.startActivity(intent);
				
			}
		});
		
	
			return convertView;
		}


		 class  ViewHolder{
			 
			 
//			 "id": 6,
//		      "start": "杭州",
//		      "berthtime": "2016-6-20",
//		      "status": "待审批",
//		      "target": "上海",
//		      "goods": "散货",
//		      "rank": "爆炸品",
//		      "tons": "100",
//		      "unit": "吨",
////		      "number": "12423000"
				
				TextView tv_start; 
				TextView tv_target; 
				TextView tv_goods; 
				
				TextView tv_rank; 
				TextView tv_tons; 
				TextView tv_unit; 
				TextView tv_berthtime; 
				TextView tv_status; 
				
				TextView number; 
				
				
				TextView tv_ship_name;
				
			  
		}


	

}
