package net.ghpublic.service;

import framework.service.BaseService;
import net.ghpublic.modol.WaterInfoEN;
import net.ghpublic.modol.WaterTideEN;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/4/19.
 */
@Service("serviceWater")
public class ServiceWater extends BaseService
{
    public WaterInfoEN WaterInfoByPoint(String whatchpoint)
    {
        Session session=sessionFactory.openSession();
        WaterInfoEN waterinfoEN=(WaterInfoEN) session.createCriteria(WaterInfoEN.class)
                                    .add(Restrictions.eq("whatchpoint",whatchpoint))
                                    .addOrder(Order.desc("updatetime"))
                                    .uniqueResult();
        session.close();

        return waterinfoEN;
    }

    public List<WaterInfoEN> WaterinfoByRegion(int cityid)
    {
        Session session=sessionFactory.openSession();
        List<WaterInfoEN> waterinfoENs=session.createCriteria(WaterInfoEN.class)
                            .createCriteria("region")
                            .add(Restrictions.eq("id",cityid))
                            .list();
        session.close();
        return waterinfoENs;
    }
    //三、按港口和时间获取潮汐表
    public List<WaterTideEN> GetTide(String port,Date d1,Date d2)
    {
        Session session=sessionFactory.openSession();
        List<WaterTideEN> waterinfoENs=session.createCriteria(WaterTideEN.class)
                .add(Restrictions.eq("port",port))
                .add(Restrictions.between("freshdate",d1,d2))
                .list();
        session.close();
        return waterinfoENs;
    }
}
