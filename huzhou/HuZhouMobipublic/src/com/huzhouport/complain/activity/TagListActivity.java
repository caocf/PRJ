package com.huzhouport.complain.activity;

import com.appkefu.lib.interfaces.KFInterfaces;
import com.appkefu.lib.ui.entity.KFUserTagsEntity;
import com.example.huzhouportpublic.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * ���ڴ������û��ı�ǩ���Ա��ڸ��õ�ʶ�𡢱�ʶ�������û�, Ŀǰ���ŵı�ǩ�У�
 * 
 * nickname:�ǳ�
 * sex     :�Ա�
 * language:����
 * city	   :����
 * province:ʡ��
 * country :����
 * other   :����
 *               
 * ��������QQȺ:48661516
 * �ͷ������ĵ��� http://appkefu.com/AppKeFu/tutorial-android.html
 * @author jack ning, http://github.com/pengjinning
 *
 */    

public class TagListActivity extends Activity implements OnClickListener{
	
	private KFUserTagsEntity tagEntity;
	
	private RelativeLayout	mNicknameRelativeLayout;
	private RelativeLayout  mBusinessIDRelativeLayout;
	private RelativeLayout	mSexRelativeLayout;
	private RelativeLayout	mLanguageRelativeLayout;
	private RelativeLayout	mCityRelativeLayout;
	private RelativeLayout	mProvinceRelativeLayout;
	private RelativeLayout	mCountryRelativeLayout;
	private RelativeLayout  mOtherRelativeLayout;
	
	private TextView	mNicknameTextView;
	private TextView	mBusinessIDTextView;
	private TextView	mSexTextView;
	private TextView	mLanguageTextView;
	private TextView	mCityTextView;
	private TextView	mProvinceTextView;
	private TextView	mCountryTextView;
	private TextView	mOtherTextView;
	
	private Button 		mBackBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag_list);
		
		mNicknameRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_nickname);
		mNicknameRelativeLayout.setOnClickListener(this);
		
		mBusinessIDRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_id);
		mBusinessIDRelativeLayout.setOnClickListener(this);

		mSexRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_sex);
		mSexRelativeLayout.setOnClickListener(this);

		mLanguageRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_language);
		mLanguageRelativeLayout.setOnClickListener(this);

		mCityRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_city);
		mCityRelativeLayout.setOnClickListener(this);

		mProvinceRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_province);
		mProvinceRelativeLayout.setOnClickListener(this);

		mCountryRelativeLayout = (RelativeLayout) findViewById(R.id.layout_personal_country);
		mCountryRelativeLayout.setOnClickListener(this);

		mOtherRelativeLayout = (RelativeLayout) findViewById(R.id.layout_other_country);
		mOtherRelativeLayout.setOnClickListener(this);
		
		mBackBtn = (Button) findViewById(R.id.profile_reback_btn);
		mBackBtn.setOnClickListener(this);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		tagEntity = KFInterfaces.getTags(this);
		
		mNicknameTextView = (TextView) findViewById(R.id.personal_nickname_detail);
		mNicknameTextView.setText(tagEntity.getNickname());
		
		mBusinessIDTextView = (TextView) findViewById(R.id.personal_id_detail);
		mBusinessIDTextView.setText(tagEntity.getLinkfullID());
		
		mSexTextView = (TextView) findViewById(R.id.personal_sex_detail);
		mSexTextView.setText(tagEntity.getSex());
		
		mLanguageTextView = (TextView) findViewById(R.id.personal_language_detail);
		mLanguageTextView.setText(tagEntity.getLanguage());
		  
		mCityTextView = (TextView) findViewById(R.id.personal_city_detail);
		mCityTextView.setText(tagEntity.getCity());
		 
		mProvinceTextView = (TextView) findViewById(R.id.personal_province_detail);
		mProvinceTextView.setText(tagEntity.getProvince());
		
		mCountryTextView = (TextView) findViewById(R.id.personal_country_detail);
		mCountryTextView.setText(tagEntity.getCountry());
		
		mOtherTextView = (TextView) findViewById(R.id.personal_other_detail);
		mOtherTextView.setText(tagEntity.getOther());
		
		tagEntity.toString();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {

		case R.id.profile_reback_btn:
			finish();
			break;
		case R.id.layout_personal_nickname:
			change_nickname();
			break;
		case R.id.layout_personal_id:
			change_businessid();
			break;
		case R.id.layout_personal_sex:
			change_sex();
			break;
		case R.id.layout_personal_language:
			change_language();
			break;
		case R.id.layout_personal_city:
			change_city();
			break;
		case R.id.layout_personal_province:
			change_province();
			break;
		case R.id.layout_personal_country:
			change_country();
			break;
		case R.id.layout_other_country:
			change_other();
			break;
		default:
			break;
		}
	}

	public void change_nickname()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "NICKNAME");
		intent.putExtra("value", tagEntity.getNickname());
		startActivity(intent);
	}
	
	//linkfull
	public void change_businessid()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "LINKFULLID");
		intent.putExtra("value", tagEntity.getLinkfullID());
		startActivity(intent);
	}
	
	public void change_sex()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "SEX");
		intent.putExtra("value", tagEntity.getSex());
		startActivity(intent);
	}
	
	public void change_language()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "LANGUAGE");
		intent.putExtra("value", tagEntity.getLanguage());
		startActivity(intent);
	}
	
	public void change_city()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "CITY");
		intent.putExtra("value", tagEntity.getCity());
		startActivity(intent);
	}
	
	public void change_province()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "PROVINCE");
		intent.putExtra("value", tagEntity.getProvince());
		startActivity(intent);
	}
	
	public void change_country()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "COUNTRY");
		intent.putExtra("value", tagEntity.getCountry());
		startActivity(intent);
	}
	
	public void change_other()
	{
		Intent intent = new Intent(this, ChangeTagActivity.class);
		intent.putExtra("profileField", "OTHER");
		intent.putExtra("value", tagEntity.getOther());
		startActivity(intent);
	}

}
