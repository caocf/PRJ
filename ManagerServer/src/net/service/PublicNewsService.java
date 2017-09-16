package net.service;

import common.BaseResult;
import net.modol.CrewBaseEN;
import net.modol.publicdata.NewsEN;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class PublicNewsService extends PublicBaseService
{
    //按条件获取通知列表
    public BaseResult NoticeListByItems(String region, String type, String title, String status, Date d1,Date d2,String page)
    {
        Session session=sessionFactory.openSession();
        Criteria criteria=session.createCriteria(NewsEN.class).add(Restrictions.like("region","%"+region+"%"))
                                                            .add(Restrictions.like("newstype","%"+type+"%"))
                                                            .add(Restrictions.like("title","%"+title+"%"))
                                                            .add(Restrictions.like("status","%"+status+"%"))
                                                            .add(Restrictions.between("updatetime",d1,d2));
        Object obj=(criteria .setProjection(Projections.rowCount()) .uniqueResult());
        long count=(long)obj;
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(count<10)
            pages=1;

        List<NewsEN> list=criteria
                .setProjection(null)
                .addOrder(Order.desc("updatetime"))
                .setFirstResult((Integer.valueOf(page)-1)*10)//页码从1开始
                .setMaxResults(10)
                .list();

        BaseResult bs=new BaseResult();
        bs.setS1(list);
        bs.setResultcode(pages);
        session.close();
        return bs;
    }
    //按ID获取单条通知
    public NewsEN NoticeByID(int id)
    {
        Session session=sessionFactory.openSession();
        NewsEN list=(NewsEN)session.createCriteria(NewsEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
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
