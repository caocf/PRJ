package com.hztuen.gh.activity.Adapter;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.network.HttpRequest; 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast; 
import com.gh.modol.ShipCircleListModel; 
import com.hxkg.ghpublic.R; 
import com.hztuen.gh.activity.GoodsDetailsAdcivity;
import com.hztuen.gh.activity.SendGoodsMsgActivity;
import com.hztuen.gh.activity.SendRentMsgActivity;
import com.hztuen.gh.activity.SendShipResMsgActivity;
import com.hztuen.gh.activity.SendShipSellActivity;
import com.hztuen.gh.activity.ShipBoughtDetailsActivity;
import com.hztuen.gh.activity.ShipDetailsActivity;
import com.hztuen.gh.activity.ShipRentDetailsActivity;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic; 

public class MySendRecordAdapter extends BaseAdapter 
{
	private Context context;
	private List<ShipCircleListModel> recordletinlist;
	public PopupWindow popupWindowArea;
	private View contentView;
	

	public MySendRecordAdapter(Context context, List<ShipCircleListModel> recordletinlist) 
	{
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context,
				R.layout.my_send_record_item, null);

		holder.tv_title = (TextView) convertView.findViewById(R.id.text2);
		holder.tv_content = (TextView) convertView.findViewById(R.id.text2_context);
		holder.tv_id=(TextView)convertView.findViewById(R.id.id_id_id);
		holder.tv_sourceid = (TextView) convertView.findViewById(R.id.id_id);

		holder.tv_postime = (TextView) convertView.findViewById(R.id.text4);
		holder.tv_price = (TextView) convertView.findViewById(R.id.text_money);
		holder.tv_tradetype = (TextView) convertView.findViewById(R.id.text3);
		holder.linear_detail=(LinearLayout)convertView.findViewById(R.id.liner_first);

		holder.btn_cancel=(Button)convertView.findViewById(R.id.btn_cancel);
		holder.btn_revise=(Button)convertView.findViewById(R.id.btn_revise);
		
		final ShipCircleListModel model = recordletinlist.get(position);

		
		holder.tv_title.setText(model.gettitle());
		holder.tv_content.setText(model.getcontent());

		holder.tv_postime.setText(model.getpostime());
		holder.tv_price.setText(model.getprice());
		holder.tv_tradetype.setText(model.typenameString);
		
		holder.tv_sourceid.setText(model.getsourceid());
		holder.tv_id.setText(model.getid());
		
		
		holder.btn_revise.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				Intent intent=new Intent();
				switch(Integer.valueOf(model.gettradetype()))
				{
				case 1:
					intent=new Intent(context,SendGoodsMsgActivity.class);					
					break;
				case 2:
					intent=new Intent(context,SendShipResMsgActivity.class);					
					break;
				case 3:
					intent=new Intent(context,SendRentMsgActivity.class);					
					break;
				case 4:
					intent=new Intent(context,SendShipSellActivity.class);					
					break;
				
				}
				intent.putExtra("ShipCircleListModel", model);
				context.startActivity(intent);
				
			}
		});
		
		
		//点击取消发布，根据不同类型的发布记录进行取消
		holder.btn_cancel.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				SystemStatic.removepostiion=position;
				SystemStatic.deleteUrl=contants.removeshiprent;
				SystemStatic.sourceid=holder.tv_sourceid.getText().toString();
				SystemStatic.sendid=holder.tv_id.getText().toString(); 
				PopDelete(model.getid());
				
			}
		});
		
		
		//点击item上半部分进入我的发布记录详情
		holder.linear_detail.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				// 我的发布记录，租船详情
				if ("3".endsWith(model.gettradetype()))
				{
					Intent in = new Intent();
					in.setClass(context,ShipRentDetailsActivity.class);
					in.putExtra("title", model.gettitle()); 
					in.putExtra("shiptype",model.shiptypename);
					in.putExtra("shipname", model.shipname);
					in.putExtra("load", model.load);
					in.putExtra("rentprice",model.getprice());
					in.putExtra("linkman", model.getcontent());
					in.putExtra("tel", model.tel);
					in.putExtra("remark", model.remark);
					
					in.putExtra("postdate", model.getpostime());
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					context.startActivity(in);
				}
				
				// 我的发布记录，售船详情
				else if("4".endsWith(model.gettradetype()))
				{
					Intent in = new Intent();
					 in.setClass(context, ShipBoughtDetailsActivity.class);
					 in.putExtra("title", model.gettitle());
					
						in.putExtra("shiptype",model.shiptypename);
					 in.putExtra("shipname", model.shipname);
					 in.putExtra("load", model.load);
					 in.putExtra("price", model.getprice());
					 in.putExtra("linkman", model.getcontent());
					 in.putExtra("tel", model.tel);
					 in.putExtra("remark", model.remark);
					 in.putExtra("postdate", model.getpostime());
					 in.putExtra("shipage", model.age);
					 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// 跳转界面
					context.startActivity(in);
					
					
				}
				// 我的发布记录，船源详情
				else if("2".endsWith(model.gettradetype()))
				{
					
					Intent in = new Intent();
					in.setClass(context, ShipDetailsActivity.class);
					
					in.putExtra("title", model.gettitle()); 
					in.putExtra("shiptype",model.shiptypename);
					in.putExtra("shipname", model.shipname);
					in.putExtra("load", model.load);
					in.putExtra("rentprice", model.getprice());
					in.putExtra("linkman", model.getcontent());
					in.putExtra("tel", model.tel);
					in.putExtra("remark", model.remark);
					in.putExtra("postdate", model.getpostime()); 
					in.putExtra("emptydock", model.fromString);
					in.putExtra("targetdock",model.to);
					in.putExtra("route", model.route);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// 跳转界面
					context.startActivity(in);				
					
				}
				// 我的发布记录，货源详情
				else if("1".endsWith(model.gettradetype()))
				{
					
					Intent in = new Intent();
					in.setClass(context, GoodsDetailsAdcivity.class); 
					in.putExtra("title", model.gettitle()); 
					in.putExtra("type", model.goodstypename);
					in.putExtra("name", model.goodsname);
					in.putExtra("tons", model.tons);
					in.putExtra("packaging", model.packaging);
					in.putExtra("linkman", model.getcontent());
					in.putExtra("tel", model.tel);
					in.putExtra("remark", model.remark);
					in.putExtra("postdate", model.getpostime());
					in.putExtra("price", model.getprice()); 
					in.putExtra("startport", model.fromString);
					in.putExtra("unloadport",model.to);
					
					
					context.startActivity(in);
					
				}

			}
		});

		return convertView;
	}

	class ViewHolder 
	{
		TextView tv_id;
		TextView tv_title;
		TextView tv_content;

		TextView tv_postime;
		TextView tv_userid;
		TextView tv_price;
		TextView tv_tradetype;
		TextView tv_sourceid;
		Button btn_cancel;
		Button btn_revise;
		LinearLayout linear_detail;
	} 

	
	// 按条件Id删除我的发布记录
	private void DeleteMyRecordTask(String posttime) 
	{			
		// 访问地址
		HttpRequest hr=new HttpRequest(new HttpRequest.onResult()
		{

			@Override
			public void onSuccess(String result) 
			{
				Toast.makeText(context, "取消发布成功", Toast.LENGTH_SHORT).show();
				recordletinlist.remove(SystemStatic.removepostiion);
				notifyDataSetChanged();
				
				
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
	
		});
		Map<String,Object> map =new HashMap(); 
		map.put("id", posttime); 
		hr.post(contants.baseUrl+"CancelMyPost", map); //"http://192.168.1.104:8080/"
		
	}
	
	private void PopDelete(final String posttime)
	{
		contentView = ((Activity) context).getLayoutInflater().inflate(R.layout.pop_delete_my_send_record, null);
		
		
		Button btn_confirm=(Button)contentView.findViewById(R.id.btn_confirm);
		Button btn_cancel=(Button)contentView.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindowArea.dismiss();
				
			}
		});
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DeleteMyRecordTask(posttime);
				popupWindowArea.dismiss();
				
			}
		});

			LinearLayout parent6 = (LinearLayout) ((Activity) context).findViewById(R.id.father_send_record);//父窗口view  
			popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindowArea.setFocusable(true);
			popupWindowArea.setOutsideTouchable(true);
			popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			popupWindowArea.setBackgroundDrawable(new PaintDrawable());
			WindowManager.LayoutParams lp6 =((Activity) context). getWindow().getAttributes();
			lp6.alpha = 0.5f;
			((Activity) context).getWindow().setAttributes(lp6);
			
			popupWindowArea.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					WindowManager.LayoutParams lp =((Activity) context). getWindow().getAttributes();
					lp.alpha = 1f;
					((Activity) context).getWindow().setAttributes(lp);
				}
			});
			
			popupWindowArea.showAtLocation(parent6, Gravity.CENTER, 0,0);
	}
}
