package net.service;

import common.BaseResult;
import net.modol.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class EReportService extends BaseService {
    //按ID获取进出港
    public ShipReporttypeEN InOrOutByID(int id) {
        Session session = sessionFactory.openSession();
        ShipReporttypeEN list = (ShipReporttypeEN) session.createCriteria(ShipReporttypeEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return list;
    }

    //获取普通载货种类列表
    public List<?> GoodsTypeList() {
        Session session = sessionFactory.openSession();
        List<?> list = session.createCriteria(CommonGoodstypeEN.class)
                .list();
        session.close();
        return list;
    }

    //按ID获取普通种类
    public CommonGoodstypeEN GoodsTypeByID(int id) {
        Session session = sessionFactory.openSession();
        CommonGoodstypeEN list = (CommonGoodstypeEN) session.createCriteria(CommonGoodstypeEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return list;
    }

    //按ID获取报告类型
    public ShipReportclassEN RePortTypeByID(int id) {
        Session session = sessionFactory.openSession();
        ShipReportclassEN list = (ShipReportclassEN) session.createCriteria(ShipReportclassEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return list;
    }

    //按状态和船名获取报告报告
    public List<?> ERePortByStatusAndShip(int statusid, String name) {
        Criterion se1 = statusid == -1 ? Restrictions.lt("status.id", 10000) : Restrictions.eq("status.id", statusid);
        Session session = sessionFactory.openSession();
        List<?> list = session.createCriteria(ShipEreportEN.class)
                .add(Restrictions.eq("shipname", name))
                .createAlias("statusEN", "status")
                .add(se1)
                .list();
        session.close();
        return list;
    }

    //按状态和船名获取报告报告
    public List<?> ERePortByTip(String tip) {
        Session session = sessionFactory.openSession();
        List<?> list = session.createCriteria(ShipEreportEN.class)
                .add(Restrictions.like("shipname", "%" + tip + "%"))
                .list();
        session.close();
        return list;
    }

    public BaseResult dzbgDt(String shipname, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(ShipEreportEN.class);
        if (shipname != null && !"".equals(shipname)) {
            criteria.add(Restrictions.like("shipname", "%" + shipname + "%"));
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

    public ShipEreportEN ereportById(int id) {
        Session session = sessionFactory.openSession();
        ShipEreportEN obj = (ShipEreportEN) session.createCriteria(ShipEreportEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return obj;
    }
}
