package com.common.service;

import com.common.framework.CXFFilter;
import org.apache.struts2.ServletActionContext;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * BaseService已实现事务，继承该类自动继承事务
 *
 * @author DJ
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseService {
    /**
     * 获得当前项目上下文路径
     *
     * @return
     */
    public String getContextPath() {
        return CXFFilter.contextPath;
    }

    public String getRealPath(String path) {
        return CXFFilter.contextPath + "/" + path;
    }

    public String getRealPath() {
        return getContextPath();
    }

    public HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    public HttpSession getNewSession(){
        HttpSession session = getSession();
        session.invalidate();
        return ServletActionContext.getRequest().getSession(true);
    }
}
