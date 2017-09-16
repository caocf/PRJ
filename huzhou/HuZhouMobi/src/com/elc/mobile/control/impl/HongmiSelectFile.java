package com.elc.mobile.control.impl;

import com.elc.mobile.control.Mobile;
import com.elc.mobile.control.MobileControl;

/**
 * ºìÃ×ÊÖ»ú
 * @author Administrator
 *
 */
public class HongmiSelectFile extends MobileControl
{
	public HongmiSelectFile()
	{
		Mobile mobile=new Mobile();
		mobile.setAndroidName("4.0");
		mobile.setMobileName("hongmi");
		
		setMobile(mobile);
	}
	
	@Override
	public void ProcessDefault() {
		
		System.out.println("call Hongmi ProcessDefault");
	}

	@Override
	public void ProcessWithThisModel() {
		System.out.println("call Hongmi ProcessWithThisModel");
	}

	@Override
	public void ProcessWithThisModelAndVersion() {
		System.out.println("call Hongmi ProcessWithThisModelAndVersion");
	}

	@Override
	public void ProcessWithThisVersion() {
		System.out.println("call Hongmi ProcessWithThisVersion");
	}

	
}
