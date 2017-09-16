package com.elc.mobile.control;

import java.util.ArrayList;
import java.util.List;

import com.elc.mobile.control.impl.DefaultSelectFile;

import android.app.Activity;
import android.os.Build;

/**
 * 不同手机版本处理管理器
 * @author Administrator
 *
 */
public class MobileManager 
{
	private MobileManager()
	{
	}
	
	public static MobileManager mobileControl;
	/**
	 * 获取实例
	 * @return 实例
	 */
	public static MobileManager getInstance(Activity activity)
	{
		if(mobileControl==null)
		{
			mobileControl=new MobileManager();
			mobileControl.setDefaultMobileControl(new DefaultSelectFile(activity));
			
//			mobileControl.addMobileControl("");
		}
		
		return mobileControl;
	}
	
	//手机处理列表
	public List<MobileControl> mobiles=new ArrayList<MobileControl>();
	
	//默认版本处理器
	public MobileControl defaultMobileControl;
	
	//当前手机的版本和型号
	Mobile currentMobile;
	
	
	public void setDefaultMobileControl(MobileControl defaultMobileControl) {
		this.defaultMobileControl = defaultMobileControl;
	}
	/**
	 * 添加手机版本处理器
	 * @param m 
	 */
	public void addMobileControl(MobileControl m)
	{
		if(m!=null && !mobiles.contains(m))
			mobiles.add(m);	
	}
	
	/**
	 * 移除手机版本处理器
	 * @param m
	 */
	public void removeMobileControl(MobileControl m)
	{
		if(m!=null && mobiles.contains(m))
			mobiles.remove(m);
	}
	
	
	/**
	 * 从系统中读取当前版本的手机版本号和型号
	 */
	@SuppressWarnings("deprecation")
	public void getCurrentMobile()
	{
		currentMobile=new Mobile();
		currentMobile.setAndroidName(Build.VERSION.SDK);
		currentMobile.setMobileName(Build.MODEL);
		
		System.out.println(Build.VERSION.SDK+":"+Build.MODEL);
	}
	
	
	/**
	 * 实际处理入口
	 * 处理顺序：处理器列表处理  -> 默认处理
	 * @return 是否已处理
	 */
	public boolean deal()
	{
		//获取当前手机信息
		if(currentMobile==null)
			getCurrentMobile();
		
		boolean isDeal=false;
		
		//处理器列表委派处理
		if(mobiles!=null || mobiles.size()>=0)
		{
			for(MobileControl m:mobiles)
			{
				isDeal=m.dispacher(currentMobile);
				
				if(isDeal)
					break;
			}
		}
		
		//如未处理，则默认处理
		if(!isDeal)
		{
			if(defaultMobileControl!=null)
			{
				isDeal=defaultMobileControl.dispacher(currentMobile);
			}
		}
		
		return isDeal;
	}
}
