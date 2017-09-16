package net.service;

import common.DataList;
import net.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class DictoryService extends BaseService
{
    //获取字典组别
    public List<?> DictoryGroup()
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(DictoryGroupEN.class)
                            .list();
        session.close();
        return list;
    }
    //按分组ID和提示查询字典列表
    public DataList DictoryListByGroupID(int id, String tip,int page)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(DictoryUrlEN.class)
                .add(Restrictions.eq("groupEN",id))
                .add(Restrictions.like("name","%"+tip+"%"));

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
    //港口列表分页
    public DataList PortListPage(int page)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(CommonPortEN.class);

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
    //执法类别列表分页
    public DataList LawTypeListPage(int page)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(LawTypeEN.class);

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
    //危货品类别列表分页
    public DataList DangerListPage(int page)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(CommonDangerrankEN.class);

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();

        session.close();
        return new DataList(pages,list);
    }
    //危货品类别列表分页
    public DataList UnitListPage(int page)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(CommonGoodsunitEN.class);

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
