package net.service;

import net.modol.CompanyNamesEN;
import net.modol.JcCbCbjbxxEN;
import net.modol.LawBaseEN;
import net.modol.ShipNamesEN;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/10/13.
 */
@Service
public class ShipService extends BaseService
{
    //按提示获取船名列表
    public List<?> ShipNamesByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<?> names=session.createCriteria(JcCbCbjbxxEN.class)
                .setProjection(Projections.property("zwcm"))
                .add(Restrictions.like("zwcm","%"+tip+"%"))
                .setMaxResults(160)
                .list();
        session.close();
        return names;
    }
    //按提示获取船名列表
    public List<?> companynames(String tip)
    {
        Session session=sessionFactory.openSession();
        List<?> names=session.createCriteria(CompanyNamesEN.class)
                .setProjection(Projections.property("name"))
                .add(Restrictions.like("name","%"+tip+"%"))
                .list();
        session.close();
        return names;
    }
}
