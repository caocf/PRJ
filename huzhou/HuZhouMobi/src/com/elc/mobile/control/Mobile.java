package com.elc.mobile.control;

/**
 * �ֻ���Ϣ��
 * 
 * ��mobileName == "" and androidName == "" ֧������android�汾�������ֻ�
 * ��mobileName == "" and androidName != "" ֧�ִ��ֻ��ͺ��µ�����android�汾
 * ��mobileName != "" and androidName == "" ֧�ִ�android�汾�µ������ֻ�
 * 
 * 
 * @author Administrator
 *
 */
public class Mobile 
{
	int priority;						//�������ȼ�
	String mobileName;					//�ֻ��ͺ�
	String androidName;					//�ֻ�android�汾��
	
	public String getMobileName() {
		return mobileName;
	}
	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}
	public String getAndroidName() {
		return androidName;
	}
	public void setAndroidName(String androidName) {
		this.androidName = androidName;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
