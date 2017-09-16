package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.News;
import com.gh.modol.PermissionInformation;
import com.gh.modol.RegionList;
import com.hxkg.ghpublic.R;
import com.hxkg.mainfragment.MainFramentNewsActivity;
import com.hztuen.gh.activity.Adapter.PermissionInformationAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class PermissionInformationActivity extends Activity implements OnClickListener{

	private List<PermissionInformation> permissionlist = new ArrayList<PermissionInformation>();
	private PermissionInformationAdapter permissionAdapter;
	private ListView lv_permission;
	private RelativeLayout permission_have,empty_permission,relative_title;
	private ImageView permission_list_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_permission_information);
		lv_permission=(ListView)findViewById(R.id.lv_permission);
		permissionAdapter = new PermissionInformationAdapter(PermissionInformationActivity.this, permissionlist);
		RefrashTask();
		
		empty_permission=(RelativeLayout)findViewById(R.id.empty_permission);
		permission_have=(RelativeLayout)findViewById(R.id.permission_have);
		
		permission_list_back=(ImageView)findViewById(R.id.permission_list_back);
		permission_list_back.setOnClickListener(this);
		
		relative_title=(RelativeLayout)findViewById(R.id.relative_title);
		relative_title.setOnClickListener(this);
		
}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.relative_title:
			finish();
			break;
		}
		
	}

	
	private void RefrashTask() {

		// TODO Auto-generated method stub


		//访问网络

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Userid="+"1");
		
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.getpermissionlist;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kjh.post(toUrl, params,false,new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
				//	Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try {
						JSONObject resultJO = new JSONObject(result);
						//String resultMsg = resultJO.getString("resultMsg");
				//		Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
						JSONObject  res = new JSONObject(result);
						JSONArray data = res.getJSONArray("data");
						Log.i("GH_TEXT", data.length()+"我是数据的size");
						if(data.length()==0)
						{
							empty_permission.setVisibility(View.VISIBLE);
							permission_have.setVisibility(View.GONE);
						}
						else
						{
						for(int i = 0;i<data.length();i++){
							JSONObject temp = data.getJSONObject(i);
							PermissionInformation permission= new PermissionInformation();
							//RegionList region = new RegionList();
							permission.setUpdatetime(temp.getString("applytime"));
							permission.setTitle(temp.getString("permmitnum"));
							permission.setContent(temp.getString("projectname"));
							permission.setState(temp.getString("status"));
							
//							"buildproperty": "建造",
//						      "unit": "中建公司",
//						      "operator": "张伟",
//						      "tel": "139",
//						      "designer": "设计院",
//						      "accept": "国土局",
//						      "permmitnum": "15487",
//						      "applytime": "2016-09-05 00:00:00",
//						      "status": "待审批"
							
							
							permission.setProjectname(temp.getString("projectname"));
							permission.setlocation(temp.getString("location"));
							permission.setbuildproperty(temp.getString("buildproperty"));
							permission.setunit(temp.getString("unit"));
							permission.setoperator(temp.getString("operator"));
							permission.settel(temp.getString("tel"));
							permission.setdesigner(temp.getString("designer"));
							permission.setaccept(temp.getString("accept"));
							permission.setpermmitnum(temp.getString("permmitnum"));
							permission.setapplytime(temp.getString("applytime"));
							
							
			
							
							permissionlist.add(permission);
							
						}
						empty_permission.setVisibility(View.GONE);
						permission_have.setVisibility(View.VISIBLE);
						lv_permission.setAdapter(permissionAdapter);	
						}
					//	newsAdapter.pre(newslist);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					//关闭进度条
				//	Log.d(TAG, strMsg);
					//					Util.getTip(cxt, errorNo+"");
					//						mSVProgressHUD.showErrorWithStatus("验证过程出现问题");
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}



	

}
