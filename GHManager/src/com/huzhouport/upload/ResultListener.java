package com.huzhouport.upload;

public interface ResultListener 
{
	/**
	 * 默认监听，所有事件都会触发，可用于页面刷新
	 */
	public void defaultListener();
	
	/**
	 * 发送结果监听
	 * @param i：-1：失败  0：仍在排队中  1：成功
	 */
	public void sendListener(int i);
	
	/**
	 * 重新上传监听
	 */
	public void reloadListener();
	
	/**
	 * 删除上传监听
	 */
	public void deleteListener();
}
