package net.service;

import net.modol.CrewBaseEN;
import net.modol.VersionEN;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class CrewService extends BaseService
{
    //按提示获取船员名字列表
    public List<String> CrewnamesByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<String> list=session.createCriteria(CrewBaseEN.class)
                            .setProjection(Projections.projectionList().add(Property.forName("name")))
                            .add(Restrictions.like("name","%"+tip+"%"))
                            .list();
        session.close();
        return list;
    }
    //按船员名获取基本信息
    public CrewBaseEN CrewinfoByName(String name)
    {
        Session session=sessionFactory.openSession();
        CrewBaseEN list=(CrewBaseEN)session.createCriteria(CrewBaseEN.class)
                .add(Restrictions.eq("name",name))
                .uniqueResult();
        session.close();
        return list;
    }
}
