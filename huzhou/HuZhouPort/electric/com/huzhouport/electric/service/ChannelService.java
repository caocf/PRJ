package com.huzhouport.electric.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.BaseService;
import com.huzhouport.electric.model.Channel;

public interface ChannelService extends BaseService<Channel> {
	// 查询信息
	public List<?> searchChannelInfo(Channel channel, int pageNo, int pageSize)
			throws Exception;

	// 查询条数
	public Map<String, Integer> countPageChannelInfo(Channel channel,
			int pageSize) throws Exception;

	// 获取产品EXL模版
	public InputStream getChannelModelInputStream() throws Exception;

	// 导入产品信息
	public String inputChannel(Channel channel, File excelFile,
			String excelFileFileName) throws Exception;

}
