package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams; 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.ab.activity.AbActivity;
import com.ab.util.AbDialogUtil; 
import com.gh.modol.Usertype;
import com.hxkg.ghpublic.HomeActivity;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.contact.Contact;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

public class LoginActivity extends AbActivity
{
	private EditText name;
	private EditText pwd;
	
	private CheckBox rePwd;//记住密码
	private ImageView pwd_eye;//密码加密按钮
	private boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
		
		init();
	}
	
	public void init()
	{
		name = (EditText) findViewById(R.id.userName);
		pwd = (EditText) findViewById(R.id.userPwd);
		rePwd=(CheckBox) findViewById(R.id.repwdrepwd);
		pwd_eye=(ImageView) findViewById(R.id.pwd_eye);
		
		SharedPreferences mShare = getSharedPreferences(Contact.user_ache, Context.MODE_PRIVATE);
		if(mShare.getAll().size()>0)
		{
			name.setText(mShare.getString("username", ""));
			pwd.setText(mShare.getString("password", ""));
			rePwd.setChecked(mShare.getBoolean("rePwd", false));
		}
		
	}	
		
	public void onViewClick(View v) 
	{
		switch(v.getId()){
		case R.id.login:
			
			CheckLogin();
			break;
		case R.id.loginBack:			
			finish();
			break;	
		case R.id.forget_password://忘记密码
			Intent mintent = new Intent();
			mintent.setClass(getApplicationContext(), ForgetPasswordActivity.class);
			startActivity(mintent);
			break;
		case R.id.uer_regist:
			Intent nintent = new Intent();
			nintent.setClass(getApplicationContext(), RegistActivity.class);
			startActivity(nintent);
			break;
		case R.id.pwd_eye:
			if(Switch()){
				pwd_eye.setImageResource(R.drawable.icon_eye_open);
				pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			}else{
				pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				pwd_eye.setImageResource(R.drawable.icon_eye_close);
			}
			break;
		default:
			break;
		}
	}
	
	public void CheckLogin()
	{
		String userName = name.getText().toString();
		String userPwd = pwd.getText().toString();
		if(userName.equals("")||userPwd.equals(""))
		{
			Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		//访问网络
		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("username="+userName);
		aa.add("password="+userPwd);
		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//访问地址
		String toUrl = contants.login_url;
		if(params == null){
			//提示参数制造失败
			Util.getTip(getApplicationContext(), "构造参数失败");
		}else if(!toUrl.equals("")){
			AbDialogUtil.showProgressDialog(LoginActivity.this, 0,"登录中");
			kjh.post(toUrl, params,false,new HttpCallBack() {
				@Override
				public void onSuccess(Map<String, String> headers, byte[] t) 
				{
					super.onSuccess(headers, t);
					String result = new String(t).trim();
					try 
					{
						JSONObject  res = new JSONObject(result);
						String resultcode = res.getString("resultcode");
						if(resultcode.equals("1"))
						{
							JSONObject rc=res.getJSONObject("obj");
						
							SystemStatic.userId=rc.getLong("userid");
							SystemStatic.userName=rc.getString("username");
							SystemStatic.phoneNum=rc.getString("tel");							
							
							JSONObject usertpyeob = rc.getJSONObject("usertype");
							Usertype usertype = new Usertype();
							usertype.setId(usertpyeob.getInt("id"));
							SystemStatic.usertypeId = usertpyeob.getString("id");
							usertype.setTypename(usertpyeob.getString("typename"));
							usertype.setBindclass(usertpyeob.getString("bindclass"));
						
							SharedPreferences mSharedPreferences = getSharedPreferences(Contact.user_ache, Context.MODE_PRIVATE);
							Editor edit  = mSharedPreferences.edit();
							edit.putString("username",SystemStatic.userName);
							edit.putBoolean("rePwd", rePwd.isChecked());
							if(rePwd.isChecked())
							{								
								edit.putString("password", pwd.getText().toString().trim());								  								
							}else 
							{
								edit.putString("password","");								
							}
							edit.commit();
							
							AbDialogUtil.removeDialog(LoginActivity.this);
							//Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
							
							Intent mIntent = new Intent();
							mIntent.setClass(getApplicationContext(), HomeActivity.class);
							startActivity(mIntent);
							LoginActivity.this.finish();

						}else
							if(resultcode.equals("-1")){
								Toast.makeText(getApplicationContext(), "用户不存在,请重新输入", Toast.LENGTH_SHORT).show();
								AbDialogUtil.removeDialog(LoginActivity.this);
							}else if(resultcode.equals("-2")){
								Toast.makeText(getApplicationContext(), "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
								AbDialogUtil.removeDialog(LoginActivity.this);
							}
					} catch (JSONException e1) {
						AbDialogUtil.removeDialog(LoginActivity.this);
						e1.printStackTrace();
					} catch (Exception e1) {
						AbDialogUtil.removeDialog(LoginActivity.this);
						Toast.makeText(getApplicationContext(), "您的网络不稳定，请检查网络", Toast.LENGTH_SHORT).show();
						e1.printStackTrace();
					}
				}
				@Override
				public void onFailure(int errorNo, String strMsg)
				{
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}	
	 
	/**
	 * @return
	 * 眼睛开关
	 */
	public boolean Switch()
	{	
		flag=flag?false:true;
		return flag;
	}
}
