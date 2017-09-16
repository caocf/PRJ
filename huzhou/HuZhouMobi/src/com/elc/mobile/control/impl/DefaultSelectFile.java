package com.elc.mobile.control.impl;

import android.app.Activity;
import android.content.Intent;

import com.elc.mobile.control.Mobile;
import com.elc.mobile.control.MobileControl;

/**
 * 默认手机
 * @author Administrator
 *
 */
public class DefaultSelectFile extends MobileControl{

	Activity c;
	public DefaultSelectFile(Activity c)
	{
		Mobile mobile=new Mobile();
		mobile.setAndroidName("");
		mobile.setMobileName("");
		
		this.c=c;
		
		setMobile(mobile);
	}
	
	@Override
	public void ProcessDefault() {
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		c.startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
				1);
	}

	@Override
	public void ProcessWithThisModel() {
		
	}

	@Override
	public void ProcessWithThisModelAndVersion() {
		
	}

	@Override
	public void ProcessWithThisVersion() {
		
	}

	
}
