package net.hxkg.ship;
import java.util.ArrayList;
import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BreakRulesAdapter extends BaseAdapter
{
	
	private Context context;
	private List<BreakRulesModel> modellist;
	final String items[]={"警告","罚款","暂扣证照","吊销证照","没收船舶","没收非法财物","没收违法所得",
						"责令停产停业","行政拘留","其他"};
	public BreakRulesAdapter(Context context, List<BreakRulesModel> modellist) {
		this.context = context;
		this.modellist = modellist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return modellist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return modellist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.activity_break_rules_item, null);
        holder.SLSJ=(TextView)convertView.findViewById(R.id.tv_break_time);
        holder.FADD=(TextView)convertView.findViewById(R.id.tv_break_address);
        holder.WFNR=(TextView)convertView.findViewById(R.id.tv_break_reason);
        holder.CFLB=(TextView)convertView.findViewById(R.id.tv_price_fa);
        holder.NUM=(TextView)convertView.findViewById(R.id.num);
       
        
    	final BreakRulesModel model = modellist.get(position);
       
    	holder.NUM.setText("受理号:"+model.getSLH());
    	holder.SLSJ.setText("时间:"+model.getSLSJ());
        holder.FADD.setText("地点:"+model.getFADD());
        holder.WFNR.setText(model.getWFNR());
        
        List<String> itemsList=new ArrayList<>();
        String lbString="";
        String string=model.getCFLB();        
        for(int i=0;i<string.length();i++)
        {        	
        	if(string.charAt(i)=='1')
        	{
        		itemsList.add(items[i]);
        	}
        		
        }
        for(String item:itemsList)
        {
        	lbString+=item;
        	lbString+=",";
        }
        holder.CFLB.setText("处罚类别:"+lbString.substring(0,lbString.length()-1));
        
        convertView.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				Intent in = new Intent();
				in.setClass(context, IllegalDetail.class);
				in.putExtra("BreakRulesModel", model);
				
				
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面  跳转到船货圈找船详情界面
				context.startActivity(in);
				
			}
		});
        
        
		return convertView;
	}

	class ViewHolder 
	{

//		private String AJLB;    //	AJLB	案件类别
//		private String AY;		//	AY	案由
//		private String CFLB;    //处罚类别，每位0表示无此类，1表示有此类。从第一位到第10位的处罚类别顺序分别为：
//		private String CFYJ;    //	CFYJ	处罚意见		
//		private String CFTK;    //	CFTK	处罚条款
//		private String FADD;    //	FADD	法案地点	
//		private String FASJ;   //	FASJ	发案时间
//		private String JARQ;   //	JARQ	结案日期	
//		private String SFCF;   //	SFCF	是否处罚，0表示否，1表示是		
//		private String ID;		//	ID	ID
//		private String SFJA;	//	SFJA	是否结案，0表示否，1表示是
//		private String SLH;		//	SLH	受理号
//		private String SLSJ;	//	SLSJ	受理时间	
//		private String WFNR;	//	WFNR	违法内容
//		private String WFTK;    //	WFTK	违反条款
//		private String ZFSCBH;  //	ZFSCBH	执法手册编号
//		private String ZWCM;    //	ZWCM	中文船名
//		private String ZYSS;    //	ZYSS	主要事实
		
		
		TextView SLSJ;//受理时间	
		TextView FADD;//法案地点
		TextView WFNR;//违法内容
		TextView NUM;//受理号
		TextView CFLB;//处罚类别

	}
}
