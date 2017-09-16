package net.ghpublic.service;
import framework.modol.BaseRecords;
import framework.modol.BaseResult;
import framework.service.BaseService;
import net.ghpublic.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by WZD on 2016/3/2.
 */
@Service("serviceNews")
public class ServiceNews extends BaseService
{
    //获取地区列表
    public List<?>  NewsRegions()
    {
        Session session=sessionFactory.openSession();

        List<?> list=session.createCriteria(CommonRegionEN.class).list();
        session.close();

        return list;
    }
    //获取地区列表
    public List<?>  NewsTypes()
    {
        Session session=sessionFactory.openSession();

        List<?> list=session.createCriteria(NewsTypeEN.class).list();
        session.close();

        return list;
    }
    //按地区和类别和页码获取新闻列表
    public BaseResult getNewsListByCatelog(int region, int type, int page)
    {
         Session session=sessionFactory.openSession();
        Criteria ci=session.createCriteria(NewsEN.class)
                                            .createAlias("region","r").add(Restrictions.eq("r.id",region))
                                            .createAlias("newstype","n").add(Restrictions.eq("n.id",type));

        long count=(long)ci .setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        ProjectionList plist= Projections.projectionList();
        plist.add(Projections.property("id")).add(Projections.property("title")).add(Projections.property("updatetime")).add(Projections.property("r.region"));
        List<?> list= ci .setProjection(null).setProjection(plist).setFirstResult((page-1)*10)
                                            .setMaxResults(10)
                                            .addOrder(Order.desc("updatetime"))
                                            .list();

         session.close();
         return new BaseResult(page,list);
    }
    //获取知识库
    public BaseResult Knowlage( int page)
    {
        Session session=sessionFactory.openSession();
        Criteria ci=session.createCriteria(ModelDynmicNews.class);

        long count=(long)ci .setProjection(Projections.rowCount()) .uniqueResult();
        int pages=(int)count/10;
        pages =count%10>0?pages+1:pages;
        if(pages==0)
            pages=1;

        List<?> list= ci .setProjection(null).setFirstResult(page*10).setMaxResults(10)
                .addOrder(Order.desc("date"))
                .list();

        session.close();
        return new BaseResult(page,list);
    }
    //按ID获取新闻
    public NewsEN  NewsByID(int id)
    {
        Session session=sessionFactory.openSession();

        NewsEN list=(NewsEN)session.createCriteria(NewsEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();

        return list;
    }
    //按新闻ID和页码获取评论
    public List<NewsCommentEN> getCommentListByCategory(int category,int page)
    {
        Session session=sessionFactory.openSession();

        List<NewsCommentEN> list=session.createCriteria(NewsCommentEN.class)
                .addOrder(Order.desc("sumbtime"))
                .add(Restrictions.eq("newsid",category))
                .setFirstResult(page*10)
                .setMaxResults(10)
                .list();

        session.close();

        return list;
    }
    //按地区和类别获取新闻条数
    public long NewsCountByRegionType(int region,int type)
    {
        Session session=sessionFactory.openSession();

        long count=(long)session.createCriteria(NewsEN.class)
                                 .createAlias("region","r").add(Restrictions.eq("r.id",region))
                                 .createAlias("newstype","n").add(Restrictions.eq("n.id",type))
                                .setProjection(Projections.rowCount()).uniqueResult();
        session.close();

        return count;
    }
    //按ID获取某个地区
    public CommonRegionEN    RegionByID(int id)
    {
        Session session=sessionFactory.openSession();

        CommonRegionEN list=(CommonRegionEN)session.createCriteria(CommonRegionEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();

        return list;
    }
    //按ID获取某个新闻类型
    public NewsTypeEN NewsTypeByID(int id)
    {
        Session session=sessionFactory.openSession();

        NewsTypeEN list=(NewsTypeEN)session.createCriteria(NewsTypeEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();

        return list;
    }
}
