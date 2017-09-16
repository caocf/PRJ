package net.hxkg.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class IssueFragment  extends Fragment implements OnCheckedChangeListener,OnItemClickListener
{
	RadioGroup radioGroup;
	CruiseRecord cruiseRecord;
	ListView listView;
	IssueAdapter issueAdapter;
	List<IssueEntity> dataList=new ArrayList<>();
	TextView tipTextView;
	int issueType=1;
	View view;
	int flag=0;//HD
	ProgressDialog progressDialog=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		view=inflater.inflate(R.layout.issuefragment, null);
		radioGroup=(RadioGroup) view.findViewById(R.id.rg);
		radioGroup.setOnCheckedChangeListener(this);
		cruiseRecord=(CruiseRecord) getArguments().getSerializable("CruiseRecord");
		if(cruiseRecord==null)flag=1;
		listView=(ListView) view.findViewById(R.id.listview);
		issueAdapter=new IssueAdapter(this.getActivity(), dataList);
		listView.setAdapter(issueAdapter);
		listView.setOnItemClickListener(this);
		tipTextView=(TextView) view.findViewById(R.id.tip);		
		
		return view;
	}
	
	public void Invalidate() 
	{		
		int h=mesHeight(view);
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT,
			     h);
		
		listView.setLayoutParams(param);
		
	}
	public int mesHeight(View view) 
	{
		  int width = View.MeasureSpec.makeMeasureSpec(0,
		    View.MeasureSpec.UNSPECIFIED);
		  int height = View.MeasureSpec.makeMeasureSpec(0,
		    View.MeasureSpec.UNSPECIFIED);
		  view.measure(width, height);
		  return view.getMeasuredHeight();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		switch (checkedId) 
		{
		case R.id.r1:
			issueType=1;
			if(flag==1)
			{
				getAllIssues();
			}else {
				getHDData();
			}
			
			break;
		case R.id.r2:
		case R.id.r3:
			issueType=2;
			getOtherIssue();
			break;

		}
		
	}
	
	private void getOtherIssue()
	{
		dataList.clear();
		issueAdapter.notifyDataSetChanged();
		if(dataList.size()<=0)tipTextView.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void onResume()
	{
		if(issueType==1)
		{
			if(flag==1)
			{
				getAllIssues();
			}else {
				getHDData();
			}
		}else 
		{
			getOtherIssue();
		}
		
		super.onResume();
	}
	
	private void getHDData() 
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() 
		{			
			@Override
			public void onSuccess(String result) 
			{
				dataList.clear();
				tipTextView.setVisibility(View.GONE);
				try 
				{
					JSONObject object=new JSONObject(result.trim());
					JSONArray array=object.getJSONArray("dataList");
					for(int i=0;i<array.length();i++)
					{
						JSONArray itemArray=array.getJSONArray(i);
						JSONObject issueObject=itemArray.getJSONObject(0);
						JSONObject channelObject=itemArray.getJSONObject(1);
						JSONObject typeObject=itemArray.getJSONObject(2);
						
						int issueid=issueObject.getInt("id");
						String issuename=typeObject.getString("name");
						String pissuename=typeObject.getString("pname");
						int typeid=typeObject.getInt("id");
						String hdmc=channelObject.getString("hdmc");
						int channelid=channelObject.getInt("id");						
						String markString=issueObject.getString("mark");
						String timeString=issueObject.getString("uptime");
						String leftorright=issueObject.getString("leftorright");
						String kz=issueObject.getString("kz");
						
						IssueEntity issueEntity=new IssueEntity();
						issueEntity.setChannelname(hdmc);
						issueEntity.setIssuetime(timeString);
						issueEntity.setLeftorright(Integer.parseInt(leftorright));
						issueEntity.setPtypename(pissuename);
						issueEntity.setTypename(issuename);
						issueEntity.setKzString(kz);
						issueEntity.setMarkString(markString);
						issueEntity.setIssueid(issueid);
						issueEntity.setTypeid(typeid);
						issueEntity.setChannelid(channelid);
						dataList.add(issueEntity);
						
					}
				} catch (Exception e) 
				{
					// TODO: handle exception
				}				
				issueAdapter.notifyDataSetChanged();
				if(dataList.size()<=0)tipTextView.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("pid", cruiseRecord.getId());
		httpRequest.post(HttpUtil.BASE_URL+"findIssuesByRid", map);
	}
	
	private void getAllIssues()
	{
		User user=(User) getArguments().get("User");
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() 
		{			
			@Override
			public void onSuccess(String result) 
			{
				dataList.clear();
				tipTextView.setVisibility(View.GONE);
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONArray array=object.getJSONArray("dataList");
					for(int i=0;i<array.length();i++)
					{
						JSONArray itemArray=array.getJSONArray(i);
						JSONObject typeObject=itemArray.getJSONObject(0);
						JSONObject channelObject=itemArray.getJSONObject(1);
						JSONObject issueObject=itemArray.getJSONObject(2);
						JSONObject recordObject=itemArray.getJSONObject(3);						
						
						int issueid=issueObject.getInt("id");
						String issuename=typeObject.getString("name");
						String pissuename=typeObject.getString("pname");
						int typeid=typeObject.getInt("id");
						String hdmc=channelObject.getString("hdmc");
						int channelid=channelObject.getInt("id");						
						String markString=issueObject.getString("mark");
						String timeString=issueObject.getString("uptime");
						String leftorright=issueObject.getString("leftorright");
						String kz=issueObject.getString("kz");
						int meters=recordObject.getInt("meters");
						int id=recordObject.getInt("id");
						String starttime=recordObject.getString("startTime");
						String endtime=recordObject.getString("endTime");
						String tool=recordObject.getString("tool");
						JSONArray tacks=recordObject.getJSONArray("tacks");
						JSONArray users=recordObject.getJSONArray("userids");
						int issues=recordObject.getInt("issues");
						cruiseRecord=new CruiseRecord();
						cruiseRecord.setId(id);
						cruiseRecord.setMeters(meters);
						cruiseRecord.setStartTime(starttime);
						cruiseRecord.setEndTime(endtime);
						cruiseRecord.setUserArray(users.toString());
						cruiseRecord.setTrackArray(tacks.toString());
						cruiseRecord.setTool(tool);
						cruiseRecord.setIssues(issues);
						
						IssueEntity issueEntity=new IssueEntity();
						issueEntity.setChannelname(hdmc);
						issueEntity.setIssuetime(timeString);
						issueEntity.setLeftorright(Integer.parseInt(leftorright));
						issueEntity.setPtypename(pissuename);
						issueEntity.setTypename(issuename);
						issueEntity.setKzString(kz);
						issueEntity.setMarkString(markString);
						issueEntity.setIssueid(issueid);
						issueEntity.setTypeid(typeid);
						issueEntity.setChannelid(channelid);
						dataList.add(issueEntity);
						
					}
				} catch (Exception e) 
				{
					// TODO: handle exception
				}				
				issueAdapter.notifyDataSetChanged();
				if(dataList.size()<=0)tipTextView.setVisibility(View.VISIBLE);
				if(progressDialog!=null)
				{
					progressDialog.dismiss();
				}
			}
			
			@Override
			public void onError(int httpcode) {
				if(progressDialog!=null)
				{
					progressDialog.dismiss();
				}
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("pid", user.getUserId());
		httpRequest.post(HttpUtil.BASE_URL+"findIssuesByUser", map);
		progressDialog=new ProgressDialog(this.getActivity());
		progressDialog.setMessage("数据加载中");
		progressDialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		Intent intent=new Intent(this.getActivity(),IssueDetailActivity.class);
		intent.putExtra("IssueEntity", dataList.get(position));
		intent.putExtra("CruiseRecord", cruiseRecord);
		this.getActivity().startActivityForResult(intent,1);
	}
	
}
