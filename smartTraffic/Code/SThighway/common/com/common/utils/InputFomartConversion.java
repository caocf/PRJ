package com.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.action.Constants;

public class InputFomartConversion {
	/**
	 * 前台输入字符串分割转换成list<Integer>
	 * 输入格式：Constants.SPLITSYMBOL = ",";   //在Constants文本里面可以自定义
	 * 
	 * @return
	 */
	public static List<Integer> springSplitIntegerlist(String stringlist){
		List<Integer> intList = new ArrayList<Integer>();
		if(stringlist==null||stringlist.equals("")){
			return intList;
		}else{
	     String[] stringArray = stringlist.split(Constants.SPLITSYMBOL); 
		 for(int i=0;i<stringArray.length;i++){
			 System.out.println(stringArray[i]);
			 intList.add(Integer.valueOf(stringArray[i]));
		    }
		}
		   return intList;
		
		
	}
	
	public static List<String> springSplitStringlist(String stringlist){
	 	 List<String> intList = new ArrayList<String>();
	     String[] stringArray = stringlist.split(Constants.SPLITSYMBOL);
	     if(stringlist==null||stringlist.equals("")){
				return intList;
		}else{  
		     for(int i=0;i<stringArray.length;i++){
			 System.out.println(stringArray[i]);
			 intList.add(stringArray[i]);
		    }
		   return intList;
		}
		
	}
	
	
	public static List<String> springSplitStringlistTwoSymbol(String stringlist){
		 List<String> intList = new ArrayList<String>();
		 if(stringlist.indexOf(Constants.SPLITSYMBOL)>-1){
			 return springSplitStringlist(stringlist);
		 }
		 if(stringlist.indexOf("、")>-1){
				 String[] stringArray = stringlist.split("、");
				  if(stringlist==null||stringlist.equals("")){
						return intList;
				}else{  
				     for(int i=0;i<stringArray.length;i++){
					 System.out.println(stringArray[i]);
					 intList.add(stringArray[i]);
				    }
				     return intList;
		      }
		 }
		 
		 intList.add(stringlist);
		 return intList;
		 
	}

}
