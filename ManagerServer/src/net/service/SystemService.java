package net.service;
import common.DataList;
import net.modol.AisEditEN;
import net.modol.SystemLogEN;
import net.modol.VersionEN;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class SystemService extends BaseService
{
    public VersionEN getLatestVerstion()
    {
        Session session=sessionFactory.openSession();
        VersionEN user=(VersionEN)session.createCriteria(VersionEN.class).addOrder(Order.desc("versionNum"))
                                                        .setFirstResult(0).setMaxResults(1).uniqueResult();
        session.close();
        return user;
    }

    //全部APP版本信息列表
    public List<VersionEN> AppList()
    {
        Session session=sessionFactory.openSession();
        List<VersionEN>  versionEN=session.createCriteria(VersionEN.class)
                .addOrder(Order.desc("versionNum"))
                .list();
        session.close();
        return versionEN;
    }
    //按版本号查找APP
    public VersionEN AppByVN(int vn)
    {
        Session session=sessionFactory.openSession();
        VersionEN  versionEN=(VersionEN)session.createCriteria(VersionEN.class)
                .add(Restrictions.eq("versionNum",vn))
                .uniqueResult();
        session.close();
        return versionEN;
    }

    //按ID查询日志
    public SystemLogEN SystemLogByID(int id)
    {
        Session session=sessionFactory.openSession();
        SystemLogEN  systemLogEN=(SystemLogEN)session.createCriteria(SystemLogEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return systemLogEN;
    }

    //按用户名提示分页日志
    public DataList SystemLogsByUser(String name, int page, Date d1,Date d2)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(SystemLogEN.class)
                .addOrder(Order.desc("optime"))
                .add(Restrictions.like("username","%"+name+"%"))
                .add(Restrictions.between("optime",d1,d2));

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
}
