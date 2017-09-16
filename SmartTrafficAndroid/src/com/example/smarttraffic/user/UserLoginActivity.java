package com.example.smarttraffic.user;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.EmailVerify;
import com.example.smarttraffic.util.SharePreference;
import com.example.smarttraffic.util.StartIntent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 用户登录界面
 * 
 * @author Administrator zhou
 * 
 */
public class UserLoginActivity extends FragmentActivity implements UpdateView
{

	public static String USER_DATA = "user_data";

	@Override
	public void update(Object data)
	{
		if (data instanceof User)
		{
			User user = (User) data;

			UserControl.setUser(user);

			Bundle bundle = new Bundle();
			bundle.putSerializable(USER_DATA, user);

			SharePreference preference = new SharePreference(this);
			preference.saveValeForString(SharePreference.USER_NAME, username);
			preference.saveValeForString(SharePreference.USER_PASSWORD,
					password);

			StartIntent.startIntentWithParam(this, UserMainActivity.class,
					bundle);
			this.finish();

		} else if (data instanceof Message)
		{
			Toast.makeText(this, ((Message) data).getMsg(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	EditText phoneEditText;
	EditText passwordEditText;
	TextView autoTextView;

	UserLoginRequest request;

	String username;
	String password;
	boolean autoSave;

	/**
	 * 获取保存用户名密码信息
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_login);

		phoneEditText = (EditText) findViewById(R.id.user_login_phones);
		passwordEditText = (EditText) findViewById(R.id.user_login_password);
		autoTextView = (TextView) findViewById(R.id.user_login_auto);

		SharePreference sharePreference = new SharePreference(this);

		autoSave = sharePreference.getValueForBool(
				SharePreference.USER_AUTO_SAVE, false);
		changeImage(autoSave);

		if (autoSave)
		{
			username = sharePreference.getValueForString(
					SharePreference.USER_NAME, "");
			password = sharePreference.getValueForString(
					SharePreference.USER_PASSWORD, "");

			if (!username.equals("") && !password.equals(""))
			{
				phoneEditText.setText(username);
				passwordEditText.setText(password);

				request = new UserLoginRequest();
				request.setPhone(username);
				request.setPassword(password);
				new HttpThread(new UserLoginSearch(), request, this, this,
						R.string.error_user_login_info).start();
			}
		}

		initHead();
	}

	public void auto(View v)
	{
		autoSave = !autoSave;
		SharePreference sharePreference = new SharePreference(this);
		sharePreference.saveValeForBool(SharePreference.USER_AUTO_SAVE,
				autoSave);
		changeImage(autoSave);
	}

	private void changeImage(boolean select)
	{
		int[] id = new int[] { R.drawable.item_check, R.drawable.item_uncheck };
		Drawable drawable;
		if (select)
		{
			drawable = getResources().getDrawable(id[0]);
		} else
		{
			drawable = getResources().getDrawable(id[1]);
		}

		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		autoTextView.setCompoundDrawables(drawable, null, null, null);
	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("登录");
		fragment.setRightName("注册");

		fragment.setRightListen(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(UserLoginActivity.this,
						UserRegisterActivity.class);
			}
		});

		ManagerFragment.replaceFragment(this, R.id.user_head, fragment);
	}

	public void forget(View v)
	{
		StartIntent.startIntent(this, ForgetFindActivity.class);
	}

	public void finish(View v)
	{
		if (request == null)
			request = new UserLoginRequest();

		username = phoneEditText.getText().toString();
		password = passwordEditText.getText().toString();

		if (username.equals(""))
		{
			Toast.makeText(this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
		} else if (password.equals(""))
		{
			Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
		}
		else
		{
			request.setPhone(username);
			request.setPassword(password);
			new HttpThread(new UserLoginSearch(), request, this, this,
					R.string.error_user_login_info).start();
		}
	}

}
