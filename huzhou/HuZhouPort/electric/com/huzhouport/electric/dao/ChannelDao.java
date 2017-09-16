package com.huzhouport.electric.dao;

import java.util.List;

import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.electric.model.Channel;

public interface ChannelDao extends BaseDao<Channel>{
	
	//查询条数
	public int countPageChannelInfo(Channel channel, String condition)throws Exception;
	
	//查询信息
	public List<?> searchChannelInfo(Channel channel,String condition,String sequence,int startSet, int maxSet) throws Exception;
	

}
