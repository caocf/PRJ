package com.huzhouport.common.util;

public final class ValidationGlobalVar {

	public static final String ISUNUSEDCODE = "[^| \";#&\\/]{1,}";// 不能含有特殊字符

	public static final String ISCHINESE = "^[\u4e00-\u9fa5]+$";// 只能输入汉字

	public static final String ISLINEENGCHI = "^\\w+$";// 只能包含_ ,英文字母,数字

	public static final String ISURL = "^((https|http|ftp|rtsp|mms)?://)"
			+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
			+ "[a-z]{2,6})" // first level domain- .com or .museum
			+ "(:[0-9]{1,4})?" // 端口- :80
			+ "((/?)|" // a slash isn't required if there is no file name
			+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";;// 验证网址
		

	public static final String ISPHONE = "^(([0/+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";// 验证座机号

	public static final String ISCELLPHONE = "0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}";// 验证手机号

	public static final String ISMYD="(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])";
		/*"\\d{4}[-](1[02])[-]\\d{2}";//判断时间类型 yyyy-mm-dd		 
*/
     public static final String ISEMAIL="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";//验证邮箱	
     
     public static final String ISNUMBER="^\\d+$";//验证数字
     
     public static final String TWONUMBER="^(([1-9]\\d*)|\\d)(\\.\\d{1,2})?$";

}
