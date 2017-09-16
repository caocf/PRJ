package com.hztuen.gh.activity.Adapter;

import java.util.List;

import com.gh.modol.News;
import com.gh.modol.PermissionInformation;
import com.hxkg.ghpublic.R;






import com.hztuen.gh.activity.PermissionDetailActivity;
import com.hztuen.gh.activity.PermissionInformationActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PermissionInformationAdapter extends BaseAdapter implements OnClickListener{

	private Context context;
	private List<PermissionInformation> permissionlist;
	
	
	public PermissionInformationAdapter(Context context,List<PermissionInformation> permissionlist){
		this.context = context;
		this.permissionlist = permissionlist;
	}

	@Override
	public int getCount() {
		
		return permissionlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return permissionlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		   
		
			holder = new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.activity_permission_item, null);
			
			 convertView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//Intent in=new Intent(context,PermissionDetailActivity.class);
						// TODO Auto-generated method stub
		               Intent in=new Intent();
		               in.setClass(context, PermissionDetailActivity.class);
		              

					
		               
		               in.putExtra("projectname",permissionlist.get(position).getProjectname());
		               in.putExtra("location", permissionlist.get(position).getlocation());
		               in.putExtra("buildproperty", permissionlist.get(position).getbuildproperty());
		               in.putExtra("unit", permissionlist.get(position).getunit());
		               in.putExtra("operator", permissionlist.get(position).getoperator());
		               in.putExtra("tel", permissionlist.get(position).gettel());
		               in.putExtra("designer", permissionlist.get(position).getdesigner());
		               in.putExtra("accept", permissionlist.get(position).getaccept());
		               in.putExtra("permmitnum", permissionlist.get(position).getpermmitnum());
		               in.putExtra("applytime", permissionlist.get(position).getapplytime());
		               in.putExtra("state", permissionlist.get(position).getState());
		               //跳转界面
						context.startActivity(in);
						
					}
				});
			
			holder.tv_permission_order = (TextView)convertView.findViewById(R.id.order_id); //编号
			holder.tv_content = (TextView) convertView.findViewById(R.id.context); // 标题
			holder.tv_date_time = (TextView) convertView.findViewById(R.id.date_time); // 日期
			holder.relative_button = (RelativeLayout) convertView.findViewById(R.id.relative_button); // 按钮背景
			holder.btn_check = (Button) convertView.findViewById(R.id.btn_check); // 按钮
		
			
			final PermissionInformation model = permissionlist.get(position);
			
		
			holder.tv_permission_order.setText(model.getTitle());// 设置编号
			holder.tv_content.setText(model.getContent());// 设置标题
			holder.tv_date_time.setText(model.getUpdatetime());// 设置日期
			holder.btn_check.setText(model.getState());// 设置状态
			
			if(model.getState().equals("待审批"))
			{
				holder.relative_button.setBackgroundResource(R.color.color_permission_state_blue);
			}else if(model.getState().equals("通过"))
			{
				holder.relative_button.setBackgroundResource(R.color.color_permission_state_green);
			}else if(model.getState().equals("未通过"))
			{
				holder.relative_button.setBackgroundResource(R.color.color_permission_state_red);
			}
			
		
		return convertView;
	}
	
	
	class ViewHolder {

		TextView tv_permission_order; // 编号
		TextView tv_content; // 标题
		TextView tv_date_time; // 日期
		Button btn_check; // 按钮
		RelativeLayout relative_button;

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
