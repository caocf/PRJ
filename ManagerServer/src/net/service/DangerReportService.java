package net.service;

import common.BaseResult;
import net.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class DangerReportService extends BaseService
{
    //获取港口列表
    public List<CommonPortEN> DangerPortList()
    {
        Session session=sessionFactory.openSession();
        List<CommonPortEN> list=session.createCriteria(CommonPortEN.class)
                .list();
        session.close();
        return list;
    }
    //按ID获取港口
    public CommonPortEN DangerPortByID(int id)
    {
        Session session=sessionFactory.openSession();
        CommonPortEN list=(CommonPortEN)session.createCriteria(CommonPortEN.class)
                            .add(Restrictions.eq("id",id))
                            .uniqueResult();
        session.close();
        return list;
    }
    //获取危险品种类列表
    public List<?> DangerRankList()
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(CommonDangerrankEN.class)
                .list();
        session.close();
        return list;
    }
    //按ID获取危货种类
    public CommonDangerrankEN DangerRankByID(int id)
    {
        Session session=sessionFactory.openSession();
        CommonDangerrankEN list=(CommonDangerrankEN)session.createCriteria(CommonDangerrankEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return list;
    }
    //获取货物单位列表
    public List<?> DangerGoodsUnit()
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(CommonGoodsunitEN.class)
                .list();
        session.close();
        return list;
    }
    //按ID获取危货单位
    public CommonGoodsunitEN DangerUnitByID(int id)
    {
        Session session=sessionFactory.openSession();
        CommonGoodsunitEN list=(CommonGoodsunitEN)session.createCriteria(CommonGoodsunitEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return list;
    }
    //按ID获取危货单状态
    public ShipStatusEN DangerStatusByID(int id)
    {
        Session session=sessionFactory.openSession();
        ShipStatusEN list=(ShipStatusEN)session.createCriteria(ShipStatusEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return list;
    }
    //按船名获取危货进港报告列表（船户）
    public List<ShipDangerreportEN> DangerShipByName(String shipname)
    {
        Session session=sessionFactory.openSession();
        List<ShipDangerreportEN>  dangerreportENs=session.createCriteria(ShipDangerreportEN.class)
                .addOrder(Order.desc("committime"))
                .add(Restrictions.eq("shipname",shipname))
                .list();
        //.setProjection(Projections.)
        //.uniqueResult();

        session.close();
        return dangerreportENs;
    }
    //按状态获取危货进港报告列表(内部APP)
    public List<ShipDangerreportEN> DangerShipByTip(int status)
    {
        Criterion cn=status==1?Restrictions.eq("id",1):Restrictions.or(Restrictions.eq("id",2),Restrictions.eq("id",3));

        Session session=sessionFactory.openSession();
        List<ShipDangerreportEN>  dangerreportENs=session.createCriteria(ShipDangerreportEN.class)
                .addOrder(Order.desc("committime"))
                .createCriteria("status")
                .add(cn)
                .list();
        //.setProjection(Projections.)
        //.uniqueResult();

        session.close();
        return dangerreportENs;
    }
    //按提示获取危货进港报告列表(内部APP)
    public List<ShipDangerreportEN> DangerShipByKey(String tip)
    {
        Session session=sessionFactory.openSession();
        List<ShipDangerreportEN>  dangerreportENs=session.createCriteria(ShipDangerreportEN.class)
                .addOrder(Order.desc("committime"))
                .add(Restrictions.like("shipname","%"+tip+"%"))
                .list();
        //.setProjection(Projections.)
        //.uniqueResult();

        session.close();
        return dangerreportENs;
    }
    //获取作业方式列表
    public List<?> WorkWayList()
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(WharfWorkmannerEN.class)
                .list();
        session.close();
        return list;
    }
    //按公司名获取危货作业列表(企业)
    public List<?> DangerWorkListByComName(String name)
    {
        Session session=sessionFactory.openSession();
        List<?> list=session.createCriteria(WharfDangerreportEN.class)
                .add(Restrictions.eq("wharfEN",name))
                .list();
        session.close();
        return list;
    }
    //按状态获取危货作业报告列表(内部APP)
    public List<ShipDangerreportEN> DangerWorkByStatus(int status)
    {
        Criterion cn=status==1?Restrictions.eq("id",1):Restrictions.or(Restrictions.eq("id",2),Restrictions.eq("id",3));

        Session session=sessionFactory.openSession();
        List<ShipDangerreportEN>  dangerreportENs=session.createCriteria(WharfDangerreportEN.class)
                .createCriteria("status")
                .add(cn)
                .list();

        session.close();
        return dangerreportENs;
    }
    //按状态获取危货作业报告列表(内部APP)
    public List<ShipDangerreportEN> DangerWorkByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<ShipDangerreportEN>  dangerreportENs=session.createCriteria(WharfDangerreportEN.class)
                .add(Restrictions.or(Restrictions.like("ship","%"+tip+"%"),Restrictions.like("goodsname","%"+tip+"%")))
                .list();

        session.close();
        return dangerreportENs;
    }
    //危货进港列表分页（后台WEB）
    public BaseResult dangrousPort(String shipname, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ShipDangerreportEN.class);
        if (shipname != null && !"".equals(shipname)) {
            criteria.add(Restrictions.like("shipname", "%" + shipname + "%"))
           ;
        }
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10) .addOrder(Order.desc("committime")).list();
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }
    //按ID获取危货进港报告
    public BaseResult dangrousPortById(int id) {
        Session session = sessionFactory.openSession();
        BaseResult result = new BaseResult();
        ShipDangerreportEN obj = new ShipDangerreportEN();
        obj = (ShipDangerreportEN) session.createCriteria(ShipDangerreportEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        result.setObj(obj);
        return result;
    }
    //危货作业报告列表分页（web）
    public BaseResult workPort(String shipname, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(WharfDangerreportEN.class);
        if (shipname != null && !"".equals(shipname)) {
            criteria.add(Restrictions.like("ship", "%" + shipname + "%"));
        }
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }
    //按ID获取危货作业报告
    public BaseResult workPortById(int id) {
        Session session = sessionFactory.openSession();
        BaseResult result = new BaseResult();
        WharfDangerreportEN obj = new WharfDangerreportEN();
        obj = (WharfDangerreportEN) session.createCriteria(WharfDangerreportEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        result.setObj(obj);
        return result;
    }

    //生成审批编号
    public String CheckNum()
    {
       return String.valueOf((int) (Math.random()*100000000));
    }

    //统计危货进港数量待审批
    public int DangerShipCount()
    {
        Session session = sessionFactory.openSession();
        long count = (long) session.createCriteria(ShipDangerreportEN.class).createCriteria("status")
                .add(Restrictions.eq("id", 1))
                .setProjection(Projections.rowCount())
                .uniqueResult();
        session.close();

        return (int)count;
    }
    //统计危货作业数量待审批
    public int DangerComCount()
    {
        Session session = sessionFactory.openSession();
        long count = (long) session.createCriteria(WharfDangerreportEN.class).createCriteria("status")
                .add(Restrictions.eq("id", 1))
                .setProjection(Projections.rowCount())
                .uniqueResult();
        session.close();

        return (int)count;
    }
}
