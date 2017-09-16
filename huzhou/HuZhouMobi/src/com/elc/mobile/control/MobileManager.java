package com.elc.mobile.control;

import java.util.ArrayList;
import java.util.List;

import com.elc.mobile.control.impl.DefaultSelectFile;

import android.app.Activity;
import android.os.Build;

/**
 * ��ͬ�ֻ��汾���������
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
	 * ��ȡʵ��
	 * @return ʵ��
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
	
	//�ֻ������б�
	public List<MobileControl> mobiles=new ArrayList<MobileControl>();
	
	//Ĭ�ϰ汾������
	public MobileControl defaultMobileControl;
	
	//��ǰ�ֻ��İ汾���ͺ�
	Mobile currentMobile;
	
	
	public void setDefaultMobileControl(MobileControl defaultMobileControl) {
		this.defaultMobileControl = defaultMobileControl;
	}
	/**
	 * ����ֻ��汾������
	 * @param m 
	 */
	public void addMobileControl(MobileControl m)
	{
		if(m!=null && !mobiles.contains(m))
			mobiles.add(m);	
	}
	
	/**
	 * �Ƴ��ֻ��汾������
	 * @param m
	 */
	public void removeMobileControl(MobileControl m)
	{
		if(m!=null && mobiles.contains(m))
			mobiles.remove(m);
	}
	
	
	/**
	 * ��ϵͳ�ж�ȡ��ǰ�汾���ֻ��汾�ź��ͺ�
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
	 * ʵ�ʴ������
	 * ����˳�򣺴������б���  -> Ĭ�ϴ���
	 * @return �Ƿ��Ѵ���
	 */
	public boolean deal()
	{
		//��ȡ��ǰ�ֻ���Ϣ
		if(currentMobile==null)
			getCurrentMobile();
		
		boolean isDeal=false;
		
		//�������б�ί�ɴ���
		if(mobiles!=null || mobiles.size()>=0)
		{
			for(MobileControl m:mobiles)
			{
				isDeal=m.dispacher(currentMobile);
				
				if(isDeal)
					break;
			}
		}
		
		//��δ������Ĭ�ϴ���
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
