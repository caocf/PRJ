package net.ghpublic.service;

import framework.modol.DataList;
import framework.service.BaseService;
import javafx.scene.layout.Priority;
import net.ghpublic.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/4/19.
 */
@Service("serviceAdvice")
public class ServiceCommon extends BaseService
{
    //最新版本APP
    public CommonVersionEN getLatestVerstion(String type)
    {
        Session session=sessionFactory.openSession();
        CommonVersionEN versionEN=(CommonVersionEN)session.createCriteria(CommonVersionEN.class)
                .add(Restrictions.eq("appType",type))
                .addOrder(Order.desc("versionNum"))
                .setFirstResult(0)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return versionEN;
    }
    //获取地区列表
    public List<?> AreaList()
    {
        Session session=sessionFactory.openSession();
        List<?> permissionENs=session.createCriteria(CommonAreaEN.class)
                .list();
        session.close();

        return permissionENs;
    }
    //按用户ID获取许可信息
    public List<CommonPermissionEN> getPermitByUser(int id)
    {
        Session session=sessionFactory.openSession();
        List<CommonPermissionEN> permissionENs=session.createCriteria(CommonPermissionEN.class)
                .createCriteria("user")
                .add(Restrictions.eq("userid",id))
                .list();
        session.close();

        return permissionENs;
    }
    //分页按提示获取意见
    public DataList AdviceByTip(String tip, int page)
    {
        Session session=sessionFactory.openSession();
        Criteria cr=session.createCriteria(CommonAdviceEN.class)
                .add(Restrictions.or(Restrictions.like("city","%"+tip+"%"),Restrictions.like("contact","%"+tip+"%"))) ;

        long count=(long)cr.setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list=cr.setProjection(null).setFirstResult((page-1)*10).setMaxResults(10).list();
        session.close();

        return new DataList(pages,list);
    }
    //按ID获取意见
    public CommonAdviceEN AdviceByID(int id)
    {
        Session session=sessionFactory.openSession();
        CommonAdviceEN adviceEN=(CommonAdviceEN)session.createCriteria(CommonAdviceEN.class)
                .add(Restrictions.eq("id",id)).uniqueResult();
        session.close();

        return adviceEN;
    }

    //分页获取本地知识库
    public List<ModelDynmicNews> CodexListLocal(int page)
    {
        Session session=sessionFactory.openSession();
        ProjectionList projections=Projections.projectionList().add(Projections.property("id")).add(Projections.property("titile")).add(Projections.property("date"));
        List<ModelDynmicNews> modelDynmicNewses=session.createCriteria(ModelDynmicNews.class)
                .setProjection(projections)
                .setFirstResult((page-1)*10).setMaxResults(10).list();
        session.close();

        return modelDynmicNewses;
    }

    //按ID获取意见
    public ModelDynmicNews CodexListLocalByID(int id)
    {
        Session session=sessionFactory.openSession();
        ModelDynmicNews modelDynmicNews=(ModelDynmicNews)session.createCriteria(ModelDynmicNews.class)
                .add(Restrictions.eq("id",id)).uniqueResult();
        session.close();

        return modelDynmicNews;
    }




































    //最新APP版本下载地址
    public String NewestAPPAddress(String type)
    {
        Session session=sessionFactory.openSession();
        List<CommonVersionEN>  list=session.createCriteria(CommonVersionEN.class)
                .add(Restrictions.eq("appType",type))
                .addOrder(Order.desc("versionNum"))
                .list();
        session.close();

        return list.get(0).getAddress();
    }
    //全部APP版本信息列表
    public List<CommonVersionEN> AppList()
    {
        Session session=sessionFactory.openSession();
        List<CommonVersionEN>  versionEN=session.createCriteria(CommonVersionEN.class)
                .addOrder(Order.desc("versionNum"))
                .list();
        session.close();
        return versionEN;
    }
    //按版本号查找APP
    public CommonVersionEN AppByVN(int vn)
    {
        Session session=sessionFactory.openSession();
        CommonVersionEN  versionEN=(CommonVersionEN)session.createCriteria(CommonVersionEN.class)
                .add(Restrictions.eq("versionNum",vn))
                .uniqueResult();
        session.close();
        return versionEN;
    }
}
