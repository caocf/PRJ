package com.example.smarttraffic.common.introduce;

import com.example.smarttraffic.R;

import android.content.Context;

/**
 * 查找简介信息
 * @author Administrator zhou
 *
 */
public class FindIntroduce
{
	/**
	 * 根据ID查找关于信息
	 * @param c 上下文
	 * @param id 关于id，查看About ABOUT_NAME文本顺序
	 * @return 关于信息
	 */
	public static final Introduce findAbout(Context c,int id)
	{
		Introduce about=new Introduce();
		String[] data=null;
		
		int[] arrayString=new int[]{R.array.introduce_smart_bus,R.array.introduce_city_bike,R.array.introduce_driving_school};
		
		data=c.getResources().getStringArray(arrayString[id]);		
	
		about.setIntroduceID(Integer.valueOf(data[0]));
		about.setIntroduceContent(data[1]);
		about.setIntroduceNote(data[2]);
		
		return about;
	}
}
