package com.huzhouport.addresslist;

import java.util.Comparator;
import android.content.ContentValues;

@SuppressWarnings("rawtypes")
public class ComparatorUser implements Comparator {

	public int compare(Object arg0, Object arg1) {
		//ContentValues cv = new ContentValues();
		ContentValues user0 = (ContentValues) arg0;
		ContentValues user1 = (ContentValues) arg1;

		// 首先比较年龄，如果年龄相同，则比较名字

		int flag = user0.getAsString("sort_key").compareTo(
				user1.getAsString("sort_key"));
		// System.out.println("flag"+flag);
		if (flag == 0) {
			// flag=user0.getAsString("number").compareTo(user1.getAsString("number"));
			// System.out.println("flag"+flag);
			return user0.getAsString("number").compareTo(
					user1.getAsString("number"));
		} else {

			return flag;
		}
	}

}