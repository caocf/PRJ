package com.huzhouport.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesClass {
	public SharedPreferences sharedPref;
	public String key_illegal_userid = "his_userid";//Υ�µڶ�ȡ֤��id
	public String key_illegal_username = "his_username";//Υ�µڶ�ȡ֤��name
	public String key_illegal_check_userid = "his_check_userid";//Υ�������id
	public String key_illegal_check_username = "his_check_username";//Υ�������name
	public String key_illegal_resonid = "his_reasonid";//Ե��id
	public String key_illegal_resonname = "his_reasonname";//Ե��name
	public String key_illegal_search_resonname = "his_search_reasonname";//Ե������name

	
	public SharedPreferencesClass(Context context) {
		sharedPref = ((Activity) context).getPreferences(Context.MODE_PRIVATE);
	}

	/**
	 * ��ֵд��ȫ��������ȥ
	 * 
	 * @param VALUE1,VALUE2,KEY1,KEY2
	 */
	public void setValueByKey(String VALUE1,String VALUE2,String KEY1,String KEY2) {
		SharedPreferences.Editor editor = sharedPref.edit();
		String oldVALUE1=getValueByKey(KEY1);
		String oldVALUE2=getValueByKey(KEY2);
		String[] oldVALUEArray1=oldVALUE1.split(",");
		String[] oldVALUEArray2=oldVALUE2.split(",");
		int j=0;
		for(int i=0;i<oldVALUEArray1.length;i++){
			if(oldVALUEArray1[i].equals(VALUE1)){
				j=1;
				
			}
		}	
		
		if(j==0){
			if(oldVALUEArray1.length>=5){
				oldVALUE1=oldVALUEArray1[0]+","+oldVALUEArray1[1]+","+oldVALUEArray1[2]+","+oldVALUEArray1[3];
				oldVALUE2=oldVALUEArray2[0]+","+oldVALUEArray2[1]+","+oldVALUEArray2[2]+","+oldVALUEArray2[3];
			}
			editor.putString(KEY1, VALUE1+","+oldVALUE1);
			editor.putString(KEY2, VALUE2+","+oldVALUE2);
			editor.commit();
		}
	}
	/**
	 * ��ֵд��ȫ��������ȥ
	 * 
	 * @param VALUE1,VALUE2,KEY1,KEY2
	 */
	public void setValueByKey(String VALUE1,String KEY1) {
		if(VALUE1.length()>0){
			SharedPreferences.Editor editor = sharedPref.edit();
			String oldVALUE1=getValueByKey(KEY1);	
			String[] oldVALUEArray1=oldVALUE1.split(",");	
			int j=0;
			for(int i=0;i<oldVALUEArray1.length;i++){
				if(oldVALUEArray1[i].equals(VALUE1)){
					j=1;				
				}
			}	
			
			if(j==0){
				if(oldVALUEArray1.length>=5){
					oldVALUE1=oldVALUEArray1[0]+","+oldVALUEArray1[1]+","+oldVALUEArray1[2]+","+oldVALUEArray1[3];		
				}
				editor.putString(KEY1, VALUE1+","+oldVALUE1);
				editor.commit();
			}
		}
	}
	public String getValueByKey(String key) {
		String values = sharedPref.getString(key, ""); 
		return values;
	}
	

}
