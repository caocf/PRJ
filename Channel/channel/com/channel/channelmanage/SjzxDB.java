package com.channel.channelmanage;

import com.common.dao.BaseDao;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.framework.CXFFilter;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
// 默认声明baseDao Bean.
public class SjzxDB extends BaseDaoDB {

    @Resource(name = "sjzxsf")
    private SessionFactory sjzxsf;


    public SessionFactory getSessionFactory() {
        return sjzxsf;
    }
}
