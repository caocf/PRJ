package com.sts.sms.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MessageAction {
	private String mobiles;//手机数组长度不能超过1000
	private String smsContent;// 短信内容：最多500个汉字或1000个纯英文
	private int priority;//优先级范围1~5，数值越高优先级越高(相对于同一序列号)
	private int result;
	public String getMobiles() {
		return mobiles;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public int getPriority() {
		return priority;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String SendMsgForInterface(){
		try {
			smsContent=new String(smsContent.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result=SendMessage.SendSMS(mobiles.split(","),smsContent,priority);
		return "success";
	}
}
