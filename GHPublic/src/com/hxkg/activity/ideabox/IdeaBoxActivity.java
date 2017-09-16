package com.hxkg.activity.ideabox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil; 
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ActivityArea; 
import com.hztuen.gh.activity.LoginActivity;  
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;  
import android.content.Intent;
import android.content.SharedPreferences; 
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText; 
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author zzq
 * @DateTime 2016年7月12日 下午2:58:38
 */
public class IdeaBoxActivity extends AbActivity
{
	private TextView ideaPostion;//提交意见位置
	private EditText ideaPhone;//提交意见电话
	private EditText ideaInput;//提交意见输入
	private Button ideaSubmitBtn;//提交意见按钮
	private TextView ideaBack;//返回
	private String City = "";
	private final String TAG = IdeaBoxActivity.class.getSimpleName();
	String tel;//获取的手机号
	String content;//获取的输入文字
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea_box);
		ideaPostion = (TextView) findViewById(R.id.idea_postion);
		ideaPhone = (EditText) findViewById(R.id.idea_phone);
		ideaPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
		ideaInput = (EditText) findViewById(R.id.idea_input);
		ideaSubmitBtn = (Button) findViewById(R.id.idea_submit);
		ideaBack = (TextView) findViewById(R.id.idea_back);
		/*
		 * 获取主界面缓存的城市数据
		 * SharedPreferences缓存的数据在全局都可以得到
		 * */
		SharedPreferences preferences= getSharedPreferences("Data", 0);
		City=preferences.getString("cityname", "杭州");
//		ideaPostion.setText(City);

		ideaPostion.setOnClickListener(onclicklisetener);
		ideaSubmitBtn.setOnClickListener(onclicklisetener);
		ideaBack.setOnClickListener(onclicklisetener);
	}
	public OnClickListener onclicklisetener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.idea_postion:
				Intent intent = new Intent(getApplicationContext(),ActivityArea.class);
				intent.putExtra("city", City);
				startActivityForResult(intent, 1000);
				break;
			case R.id.idea_submit:
				if(SystemStatic.userName.equals("")){
					AbDialogUtil.showProgressDialog(IdeaBoxActivity.this, 0,
							"提交中...");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "提交需要登录的哟。", Toast.LENGTH_SHORT).show();
							AbDialogUtil.removeDialog(IdeaBoxActivity.this);
							Intent mintent = new Intent(getApplicationContext(), LoginActivity.class);
							mintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(mintent);
						}
					}, 1000);

				}else{
					AbDialogUtil.showProgressDialog(IdeaBoxActivity.this, 0,
							"提交中...");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {

							ideaSubmit();	
						}
					}, 1000);

				}
				break;
			case R.id.idea_back:
				finish();	
			default:
				break;
			}
		}
	};
	/*
	 * 获取城市返回值
	 * */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (resultCode) {
		case 1001:
			City="杭州";
			ideaPostion.setText("杭州");
			break;
		case 1002:
			City="宁波";
			ideaPostion.setText("宁波");
			break;
		case 1003:
			City="温州";
			ideaPostion.setText("温州");
			break;
		case 1004:
			City="绍兴";
			ideaPostion.setText("绍兴");
			break;
		case 1005:
			City="湖州";
			ideaPostion.setText("湖州");
			break;
		case 1006:
			City="嘉兴";
			ideaPostion.setText("嘉兴");
			break;
		case 1007:
			City="金华";
			ideaPostion.setText("金华");
			break;
		case 1008:
			City="衢州";
			ideaPostion.setText("衢州");
			break;
		case 1009:
			City="台州";
			ideaPostion.setText("台州");
			break;
		case 1010:
			City="丽水";
			ideaPostion.setText("丽水");
			break;
		case 1011:
			City="舟山";
			ideaPostion.setText("舟山");
			break;
		default:
			break;
		}
	}
	/*
	 * 意见箱提交
	 * */
	public void ideaSubmit() 
	{ 
		tel = ideaPhone.getText().toString();
		content = ideaInput.getText().toString();
		if(ideaPostion.getText().toString().equals("")){
			Toast.makeText(IdeaBoxActivity.this, "请选择您所在的城市", Toast.LENGTH_LONG).show();
			AbDialogUtil.removeDialog(IdeaBoxActivity.this);
			return;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		  
		Matcher m = p.matcher(tel);
		if(!m.matches())
		{
			Toast.makeText(IdeaBoxActivity.this, "手机号格式错误...", Toast.LENGTH_LONG).show();
			AbDialogUtil.removeDialog(IdeaBoxActivity.this);
			return;
		}
		if(content.equals("")){
			Toast.makeText(IdeaBoxActivity.this, "请输入你需要提交的意见", Toast.LENGTH_LONG).show();
			AbDialogUtil.removeDialog(IdeaBoxActivity.this);
			return;
		}
		//访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("tel="+tel);
		aa.add("city="+City);
		aa.add("content="+content);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String toUrl = contants.idea_url;
		if(params == null){
			System.out.println(params);
			System.out.println(params);
			System.out.println(params);
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			kjh.post(toUrl, params,false,new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) {
					super.onSuccess(headers, t);
					// 获取cookie
					KJLoger.debug("===" + headers.get("Set-Cookie"));
					Log.i(TAG+":kymjs---http", new String(t));
					String result = new String(t).trim();
					try {
						JSONObject resultJO = new JSONObject(result);
						Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
						JSONObject  res = new JSONObject(result);
						int resultcode = res.getInt("resultcode");
						if(resultcode>=0){
							Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
							ideaPhone.setText("");
							ideaInput.setText("");
							AbDialogUtil.removeDialog(IdeaBoxActivity.this);
						}else
							if(resultcode==-1){
								Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();

							}
							else{
								Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
							}
						AbDialogUtil.removeDialog(IdeaBoxActivity.this);
					} catch (JSONException e1) {
						Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}}
				@Override
				public void onFailure(int errorNo, String strMsg) {
					//关闭进度条
					Log.d(TAG, strMsg);
					super.onFailure(errorNo, strMsg);
				}
			});

		}


	}	

}
