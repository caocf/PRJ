package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh.modol.BreakRulesModel;
import com.gh.modol.CredCardModel;
import com.gh.modol.GoodsMode;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.BreakRulesDetailActivity;
import com.hztuen.gh.activity.GoodsDetailsAdcivity;
import com.hztuen.gh.activity.Adapter.CredCardAdapter.ViewHolder;

public class BreakRulesAdapter extends BaseAdapter implements OnClickListener{
	
	private Context context;
	private List<BreakRulesModel> modellist;
	private String chufa;

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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.activity_break_rules_item, null);
        holder.SLSJ=(TextView)convertView.findViewById(R.id.tv_break_time);
        holder.FADD=(TextView)convertView.findViewById(R.id.tv_break_address);
        holder.WFNR=(TextView)convertView.findViewById(R.id.tv_break_reason);
        
        holder.CFLB=(TextView)convertView.findViewById(R.id.tv_price_fa);
       
        
    	final BreakRulesModel model = modellist.get(position);
       
        holder.SLSJ.setText(modellist.get(position).getSLSJ());
        holder.FADD.setText(modellist.get(position).getFADD());
        holder.WFNR.setText(modellist.get(position).getWFNR());
        
        
        
        String fa=modellist.get(position).getCFLB();

        ArrayList<String> faArray=new ArrayList<String>();
        for(int i=0;i<fa.length();i++)
        {
        	faArray.add(fa.substring(i, i+1));
        }
        
        
        for(int i=0;i<faArray.size();i++)
        {
        	if(faArray.get(i).toString().equals("1"))
        	{
        		chufa=swType(i);
        		 holder.CFLB.setText(swType(i));
        	}
        }
        
       
        
        convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent();
				in.setClass(context, BreakRulesDetailActivity.class);
				in.putExtra("SLSJ", model.getSLSJ());
				in.putExtra("FADD", model.getFADD());
				in.putExtra("WFNR", model.getWFNR());
				in.putExtra("CFLB", chufa);
				in.putExtra("SFCF", model.getSFCF());
				
				
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面  跳转到船货圈找船详情界面
				context.startActivity(in);
				
			}
		});
        
        
		return convertView;
	}

	class ViewHolder {

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
		
		TextView CFLB;//处罚类别

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	private String swType(int type)
	{
		String s="其他";
		switch (type) {
		
		case 0:
			String s0="警告";
			return s0;
		case 1:
			String s1="罚款";
			return s1;
		case 2:
			String s2="暂扣证照";
			return s2;
		case 3:
			String s3="吊销证照";
			return s3;
		case 4:
			String s4="没收船舶";
			return s4;
		case 5:
			String s5="没收非法财物";
			return s5;
		case 6:
			String s6="没收非法所得";
			return s6;
		case 7:
			String s7="责令停产停业";
			return s7;
		case 8:
			String s8="行政拘留";
			return s8;
		case 9:
			String s9="其他";
			return s9;
		
		
		default:
			break;
			
		}
		return s;
	}
	

}
