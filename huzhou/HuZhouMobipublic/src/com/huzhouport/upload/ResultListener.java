package com.huzhouport.upload;

public interface ResultListener 
{
	/**
	 * Ĭ�ϼ����������¼����ᴥ����������ҳ��ˢ��
	 */
	public void defaultListener();
	
	/**
	 * ���ͽ������
	 * @param i��-1��ʧ��  0�������Ŷ���  1���ɹ�
	 */
	public void sendListener(int i);
	
	/**
	 * �����ϴ�����
	 */
	public void reloadListener();
	
	/**
	 * ɾ���ϴ�����
	 */
	public void deleteListener();
}
