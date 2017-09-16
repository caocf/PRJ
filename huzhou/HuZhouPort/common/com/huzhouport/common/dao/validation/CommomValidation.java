package com.huzhouport.common.dao.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huzhouport.common.util.StringOfValidation;
import com.huzhouport.common.util.ValidationGlobalVar;

public class CommomValidation {
	/**
	 * 判空
	 * 
	 * @param values
	 * @return
	 */
	public boolean IsNull(String values) {
		if (values.length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 全角转半角（包括'。'转'.'）
	 */
	public String QtoB(String input) {

		char c[] = input.toCharArray();

		for (int i = 0; i < c.length; i++) {

			if (c[i] == '\u3000') {

				c[i] = ' ';
			}

			else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {

				c[i] = (char) (c[i] - 65248);

			} else if (c[i] == '。') {
				c[i] = '.';
			}

		}

		return new String(c);

	}

	/**
	 * 半角转全角
	 * 
	 * @param input
	 * @return
	 */
	public String BtoQ(String input) {

		char c[] = input.toCharArray();

		for (int i = 0; i < c.length; i++) {

			if (c[i] == ' ') {

				c[i] = '\u3000';

			}

			else if (c[i] < '\177') {

				c[i] = (char) (c[i] + 65248);

			}

		}

		return new String(c);

	}

	/**
	 * 判断长度
	 * 
	 * @param minimum
	 * @param biggest
	 * @param value
	 * @return
	 */
	public boolean DatevalueLength(int minimum, int biggest, String value) {

		if (value.length() > biggest || value.length() < minimum) {
			return false;
		}
		return true;
	}

	/**
	 * 判断中文
	 * 
	 * @param values
	 * @return
	 */
	public boolean isChinese(String values) {
		if (values.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISCHINESE);
			Matcher m = p.matcher(values);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	// 只能包含_ ,英文字母,数字
	public boolean regularChk(String values) {
		if (values.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISLINEENGCHI);
			Matcher m = p.matcher(values);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断特殊字符
	 * 
	 * @param values
	 * @return
	 */
	public boolean unusedCode(String values) {
		if (!values.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISUNUSEDCODE);
			Matcher m = p.matcher(values);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	public boolean isYMD(String value) throws Exception {
		/*
		 * if(values.length()!=10){ throw new
		 * Exception("请输入正确的格式：如，2010-02-03"); }else if()
		 */
		if (!value.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISMYD);
			Matcher m = p.matcher(value);
			if (!m.matches()) {
				throw new Exception("请输入正确的格式：如，2010-02-03");
			}
		}

		return true;
	}

	/**
	 * 判断地址
	 * 
	 * @param value
	 * @return
	 */
	public boolean isUrlSite(String value) {
		if (!value.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISURL);
			Matcher m = p.matcher(value);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证座机号
	 * 
	 * @param value
	 * @return
	 */
	public boolean isPhone(String value) {
		if (!value.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISPHONE);
			Matcher m = p.matcher(value);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证手机号
	 * 
	 * @param value
	 * @return
	 */
	public boolean isCellPhone(String value) {
		if (!value.equals("")) {
			Pattern p = Pattern.compile(ValidationGlobalVar.ISCELLPHONE);
			Matcher m = p.matcher(value);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证0-1之间
	 * 
	 * @param value
	 * @return
	 */
	public boolean isRate(Double value) {
		if (value < 0 || value > 1) {
			return false;
		}
		return true;
	}

	/*
	 * public static void main(String[] args) throws Exception { String a =
	 * "2012-11-31"; System.out.println(isYMD(a)); }
	 */

	/**
	 * 验证长度和非法字符
	 * 
	 * @param minimum
	 * @param biggest
	 * @param value
	 * @return
	 */
	public String lengthUnusedCode(int minimum, int biggest, String value) {
		String errorInfo = "";
		if (DatevalueLength(minimum, biggest, value) == false) {
			return StringOfValidation.LENGTHWARNING + minimum + "-" + biggest
					+ "之间";
		} else if (unusedCode(value) == false) {
			return StringOfValidation.ISUNUSEDCODE;
		}
		return errorInfo;
	}
	/**
	 * 验证Double类型数值是否是2位小数
	 * @param value  需要验证的数值
	 * @param isString  是否可空（1：是     0：否）
	 * @param message
	 * @return
	 */
	public void twoNumber(Double value,String isString,String message) throws Exception{  
		if("0".equals(isString)){
			if(value!=null){
				Pattern p = Pattern.compile(ValidationGlobalVar.TWONUMBER);
				Matcher m = p.matcher(String.valueOf(value));
				if (!m.matches()) {
					throw new Exception(message+"格式不对");
				}
			}else{
				throw new Exception(message+"不能为空");
			}
		}else{
			if(value!=null){
				Pattern p = Pattern.compile(ValidationGlobalVar.TWONUMBER);
				Matcher m = p.matcher(String.valueOf(value));
				if (!m.matches()) {
					throw new Exception(message+"格式不对");
				}
			}
		}
	}

	/*
	 * 从小到大顺序
	 */
	public static String[] ToUpper(String[] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = i; j < a.length; j++) {
				if (Double.valueOf(a[j].trim()) < Double.valueOf(a[i].trim())) {
					Double temp = Double.valueOf(a[j]);
					a[j] = a[i];
					a[i] = String.valueOf(temp);
				}
			}
		}
		return a;
	}
}
