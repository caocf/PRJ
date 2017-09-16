package net.service;

import com.alibaba.fastjson.JSONObject;
import com.nci.data.DataDriver;
import common.DataList;
import net.modol.AisEditEN;
import net.modol.AisStatusEN;
import net.modol.ShipInfo;
import net.service.websocket.WSHandler;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class AISService extends BaseService
{
    @Resource
    public WSHandler wsHandler;

    public AISService()
    {

    }
    public List<?> LocalAIS()
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(ShipInfo.class)
                .add(Restrictions.ge("shipdate",new Date(new Date().getTime()-1000*60*5)));


        List<ShipInfo> shipInfos=cr.list();

        session.close();
        return shipInfos;
    }


    //按船名查9位码
    public List<Map> AISByShipName(String name) throws Exception
    {
        DataDriver driver=DataDriver.getInstance();
        driver.setHost("10.100.70.101");
        driver.setPort(8090);
        driver.setUser("csp");
        driver.setPwd("password");
        driver.connect();

        List<Map> list1=driver.getAissbh(new String(name.getBytes("UTF-8")));
        return list1;
    }

    //获取9位码列表分页
    public DataList AISList(int page,String name)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(AisEditEN.class)
                            .add(Restrictions.or(Restrictions.like("shipnameid","%"+name+"%"),Restrictions.like("bh","%"+name+"%")));

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
    //按船名查询9位码编辑
    public AisEditEN AISByShip(String name)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(AisEditEN.class)
                .add(Restrictions.eq("shipnameid",name));


        AisEditEN aisEditEN=(AisEditEN)cr.uniqueResult();

        session.close();
        return aisEditEN;
    }
    //按ID查询状态
    public AisStatusEN AISStatusByID(int id)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(AisStatusEN.class)
                .add(Restrictions.eq("id",id));


        AisStatusEN aisStatusEN=(AisStatusEN)cr.uniqueResult();

        session.close();
        return aisStatusEN;
    }

}
