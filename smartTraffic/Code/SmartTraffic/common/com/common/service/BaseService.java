package com.common.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.common.framework.CXFFilter;

/**
 * BaseService已实现事务，继承该类自动继承事务
 * 
 * @author DJ
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class BaseService {
	/**
	 * 获得当前项目上下文路径
	 * @return
	 */
	public String getContextPath() {
		return CXFFilter.contextPath;
	}
}
