package com.channel.utils;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.service.BaseService;

@Service("utilsservice")
public class UtilsService extends BaseService {
	@Resource(name = "utilsdao")
	private UtilsDao utilsDao;

	public void test() {
		this.utilsDao.test();
	}
}
