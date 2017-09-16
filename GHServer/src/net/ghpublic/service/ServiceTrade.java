package net.ghpublic.service;

import framework.service.BaseService;
import net.ghpublic.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wzd on 2016/4/19.
 */
@Service("serviceTrade")
public class ServiceTrade extends BaseService
{
    //private final int NUM=10;

    //获取港口列表
    public List<CommonPortEN> PortList()
    {
        Session session=sessionFactory.openSession();
        List<CommonPortEN> latestENs=session.createCriteria(CommonPortEN.class)
                .list();
        session.close();
        return latestENs;
    }
    //获取货物类型列表
    public List<CommonGoodsunitEN> GoodsTypeList()
    {
        Session session=sessionFactory.openSession();
        List<CommonGoodsunitEN> latestENs=session.createCriteria(CommonGoodsunitEN.class)
                .list();
        session.close();
        return latestENs;
    }
   //获取船舶类型列表
    public List<CommonGoodsunitEN> ShipTypeList()
    {
        Session session=sessionFactory.openSession();
        List<CommonGoodsunitEN> latestENs=session.createCriteria(TradeShiptypeEN.class)
                .list();
        session.close();
        return latestENs;
    }
    //获取包装形式列表
    public List<TradePackegeEN> PackegeList()
    {
        Session session=sessionFactory.openSession();
        List<TradePackegeEN> latestENs=session.createCriteria(TradePackegeEN.class)
                .list();
        session.close();
        return latestENs;
    }
    //获取最新发布列表
    public List<TradeGoodssourceEN> getTradeLatest()
    {
        Session session=sessionFactory.openSession();
        List<TradeGoodssourceEN> latestENs=session.createCriteria(TradeGoodssourceEN.class)
                                                .add(Restrictions.eq("status","1"))
                                                .addOrder(Order.desc("postdate"))
                                                .setFirstResult(0).setMaxResults(15)
                                                .list();
        session.close();
        return latestENs;
    }
    //我的发布列表
    public List<TradeGoodssourceEN> getMyPost(int userid)
    {
        Session session=sessionFactory.openSession();
        List<TradeGoodssourceEN> mypostENs=session.createCriteria(TradeGoodssourceEN.class)
                .add(Restrictions.eq("status","1"))//只显示已发布状态
                .addOrder(Order.desc("postdate"))
                .createCriteria("userEN")
                .add(Restrictions.eq("userid",userid))
                //.setFirstResult((page-1)*NUM)
                //.setMaxResults(NUM)
                .list();
        session.close();
        return mypostENs;
    }
    //按条件时间排序查询货源记录
    public List<TradeGoodssourceEN> getGoodsListByParams(int from,int to,int type,int goodstype,int shiptype)
    {
        Criterion se1=from==-1?Restrictions.isNotNull("status"):Restrictions.eq("start.id",from);
        Criterion se2=to==-1?Restrictions.isNotNull("id"):Restrictions.eq("unload.id",to);
        Criterion se3=type==-1?Restrictions.isNotNull("id"):Restrictions.eq("tradetype.id",type);
        Criterion se4=shiptype==-1?Restrictions.isNotNull("id"):Restrictions.eq("shiptype.id",shiptype);
        Criterion se5=goodstype==-1?Restrictions.isNotNull("id"):Restrictions.eq("goodstype.id",goodstype);

        Session session=sessionFactory.openSession();
        Criteria criteria=session.createCriteria(TradeGoodssourceEN.class)
                .add(Restrictions.eq("status","1"))//只显示已发布状态
                .addOrder(Order.desc("postdate"));

        if(from!=-1)
        {
            criteria=criteria.createAlias("startport","start").add(Restrictions.eq("start.id",from));
        }
        if(to!=-1)
        {
            criteria=criteria.createAlias("unloadport","unload").add(Restrictions.eq("unload.id",to));
        }
        if(type!=-1)
        {
            criteria=criteria.createAlias("tradeTypeEN","tradetype").add(Restrictions.eq("tradetype.id",type));
        }
        if(shiptype!=-1)
        {
            criteria=criteria .createAlias("tradeShiptypeEN","shiptype").add(Restrictions.eq("shiptype.id",shiptype));
        }
        if(goodstype!=-1)
        {
            criteria=criteria.createAlias("type","goodstype").add(Restrictions.eq("goodstype.id",goodstype));
        }

        List<TradeGoodssourceEN> tradeGoodssourceENs=criteria.list();

        session.close();
        return tradeGoodssourceENs;
    }
    //获取单位
    public List<CommonUnitEN> getUnit()
    {
        Session session=sessionFactory.openSession();
        List<CommonUnitEN> ens=session.createCriteria(CommonUnitEN.class) .list();

        session.close();
        return ens;
    }
































    //按id查找货源
    public TradeGoodssourceEN getGoodsByid(int id)
    {
        Session session=sessionFactory.openSession();
        TradeGoodssourceEN tradeGoodssourceENs=(TradeGoodssourceEN)session.createCriteria(TradeGoodssourceEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();

        session.close();
        return tradeGoodssourceENs;
    }
    //按ID获取港口
    public CommonPortEN PortByID (int id)
    {
        Session session=sessionFactory.openSession();
        CommonPortEN latestEN=(CommonPortEN)session.createCriteria(CommonPortEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return latestEN;
    }
    //按ID获取交易类型
    public TradeTypeEN TradeTypeByID (int id)
    {
        Session session=sessionFactory.openSession();
        TradeTypeEN latestEN=(TradeTypeEN)session.createCriteria(TradeTypeEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return latestEN;
    }
    //按ID获取货物类型
    public CommonGoodsunitEN GoodsTypeByID(int id)
    {
        Session session=sessionFactory.openSession();
        CommonGoodsunitEN latestEN=(CommonGoodsunitEN)session.createCriteria(CommonGoodsunitEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return latestEN;
    }
    //按ID获取货物单位
    public CommonUnitEN GoodsUnitByID(int id)
    {
        Session session=sessionFactory.openSession();
        CommonUnitEN latestEN=(CommonUnitEN)session.createCriteria(CommonUnitEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return latestEN;
    }
    //按ID获取船舶类型
    public TradeShiptypeEN ShipTypeByID(int id)
    {
        Session session=sessionFactory.openSession();
        TradeShiptypeEN latestEN=(TradeShiptypeEN)session.createCriteria(TradeShiptypeEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return latestEN;
    }

}
