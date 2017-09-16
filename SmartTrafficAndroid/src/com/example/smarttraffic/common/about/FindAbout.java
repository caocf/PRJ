package com.example.smarttraffic.common.about;

import com.example.smarttraffic.R;

import android.content.Context;

/**
 * 查找关于信息
 * @author Administrator zhou
 *
 */
public class FindAbout
{
	/**
	 * 根据ID查找关于信息
	 * @param c 上下文
	 * @param id 关于id，查看About ABOUT_NAME文本顺序
	 * @return 关于信息
	 */
	public static final About findAbout(Context c,int id)
	{
		About about=new About();
		String[] data=null;
		
		int[] arrayString=new int[]{R.array.about_us,R.array.about_trip,R.array.about_bus,R.array.about_train,R.array.about_air,R.array.about_repair,R.array.about_drving,R.array.about_news,R.array.about_bike,R.array.about_smart_bus};
		
		data=c.getResources().getStringArray(arrayString[id]);		
	
		about.setAboutID(Integer.valueOf(data[0]));
		about.setAboutContent(data[1]);
		about.setAboutNote(data[2]);
		
		return about;
	}
}
