package com.sts.common.util;

import java.util.List;

import com.sts.common.model.Message;

/**
 * 消息信息生成类
 * @author Administrator zwc
 *
 */
public class MessageFactory {
	
	/**
	 * 创建消息信息,根据list的内容
	 * @param list
	 * @return
	 */
	public static Message createMessage(List<?> list)
	{
		Message message=new Message();
		if(list==null)
			message=new Message(-1, "查询数据失败");
		else if(list.size()==0)
			message=new Message(0,"查询无数据");
		else 
			message=new Message(1, "查询成功");
		
		return message;
	}
}
