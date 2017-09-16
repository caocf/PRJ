package com.elc.mobile.control;

/**
 * android版本类 
 * 处理不同android版本、不同android手机的方法
 * @author Administrator
 *
 */
public abstract class MobileControl implements VersionControl,ModelControl
{

	//手机信息
	Mobile mobile;
	public Mobile getMobile() {
		return mobile;
	}
	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * 此版本下处理方法
	 */
	@Override
	public void ProcessWithThisVersion() {
		// TODO Auto-generated method stub
	}

	/**
	 * 此手机下处理方法
	 */
	@Override
	public void ProcessWithThisModel() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 此手机、此版本下的处理方法
	 */
	public void ProcessWithThisModelAndVersion()
	{
	}
	
	/**
	 * 处理默认的方法
	 */
	public void ProcessDefault()
	{
		
	}
	
	/**
	 * 委派处理方法
	 * 处理顺序：全部默认->所有版本号->所有手机型号->此版本此手机型号->不做处理
	 * @param m 欲处理的手机
	 * @return 是否已处理
	 */
	public boolean dispacher(Mobile m)
	{
		boolean result=true;
		
		if(this.mobile.getAndroidName().equals("")&&this.mobile.getMobileName().equals(""))
			ProcessDefault();
		else if(this.mobile.getAndroidName().equals(""))
			ProcessWithThisVersion();
		else if(this.mobile.getMobileName().equals(""))
			ProcessWithThisModel();		
		else if(this.mobile.getAndroidName().equals(m.getAndroidName()) && this.mobile.getMobileName().equals(m.getMobileName()))
			ProcessWithThisModelAndVersion();
		else 
			result=false;
		
		return result;
		
		
	}
}
