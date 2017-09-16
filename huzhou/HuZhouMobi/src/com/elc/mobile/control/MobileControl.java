package com.elc.mobile.control;

/**
 * android�汾�� 
 * ����ͬandroid�汾����ͬandroid�ֻ��ķ���
 * @author Administrator
 *
 */
public abstract class MobileControl implements VersionControl,ModelControl
{

	//�ֻ���Ϣ
	Mobile mobile;
	public Mobile getMobile() {
		return mobile;
	}
	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}
	
	/**
	 * �˰汾�´�����
	 */
	@Override
	public void ProcessWithThisVersion() {
		// TODO Auto-generated method stub
	}

	/**
	 * ���ֻ��´�����
	 */
	@Override
	public void ProcessWithThisModel() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * ���ֻ����˰汾�µĴ�����
	 */
	public void ProcessWithThisModelAndVersion()
	{
	}
	
	/**
	 * ����Ĭ�ϵķ���
	 */
	public void ProcessDefault()
	{
		
	}
	
	/**
	 * ί�ɴ�����
	 * ����˳��ȫ��Ĭ��->���а汾��->�����ֻ��ͺ�->�˰汾���ֻ��ͺ�->��������
	 * @param m ��������ֻ�
	 * @return �Ƿ��Ѵ���
	 */
	public boolean dispacher(Mobile m)
	{
		boolean result=true;
		
		if(this.mobile.getAndroidName().equals("")&&this.mobile.getMobileName().equals(""))
			ProcessDefault();
		else if(this.mobile.getAndroidName().equals(""))
			ProcessWithThisVersion();
		else if(this.mobile.getMobileName().equals(""))
			ProcessWithThisModel();		
		else if(this.mobile.getAndroidName().equals(m.getAndroidName()) && this.mobile.getMobileName().equals(m.getMobileName()))
			ProcessWithThisModelAndVersion();
		else 
			result=false;
		
		return result;
		
		
	}
}
