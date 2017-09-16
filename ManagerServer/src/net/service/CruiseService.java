package net.service;

import com.alibaba.fastjson.JSONObject;
import com.nci.data.DataDriver;
import common.BaseResult;
import common.Time;
import net.bean.ChartBean;
import net.bean.XctjBean;
import net.modol.*;
import net.service.websocket.WSHandler;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class CruiseService extends BaseService {
    //按ID获取巡航日志
    public CruiseRecordEN getCruiseByID(int id) {
        Session session = sessionFactory.openSession();
        CruiseRecordEN cruiseRecordEN = (CruiseRecordEN) session.createCriteria(CruiseRecordEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return cruiseRecordEN;
    }

    //按包含人员获取巡航日志列表
    public List<CruiseRecordEN> CruisesByMember(String name) {
        Session session = sessionFactory.openSession();
        List<CruiseRecordEN> cruiseRecordENs = session.createCriteria(CruiseRecordEN.class)
                .add(Restrictions.like("member", "%" + name + "%"))
                .add(Restrictions.eq("records", "1"))
                .list();
        session.close();
        return cruiseRecordENs;
    }

    //按包含人员和时间段获取巡航日志列表
    public List<CruiseRecordEN> CruisesByMemberAndTime(String name, Date d1, Date d2) {
        Session session = sessionFactory.openSession();
        List<CruiseRecordEN> cruiseRecordENs = session.createCriteria(CruiseRecordEN.class)
                .add(Restrictions.like("member", "%" + name + "%"))
                .add(Restrictions.eq("records", "1"))
                .add(Restrictions.between("starttime", d1, d2))
                .list();
        session.close();
        return cruiseRecordENs;
    }

    //按巡航日志ID获取事件记录列表
    public List<CruiseLogEN> RecordsByCruiseID(int id) {
        Session session = sessionFactory.openSession();
        List<CruiseLogEN> cruiseRecordENs = session.createCriteria(CruiseLogEN.class)
                .createCriteria("cruiseRecordEN")
                .add(Restrictions.eq("id", id))
                .list();
        session.close();
        return cruiseRecordENs;
    }

    //按巡航日志ID获取事件记录总数
    public int CountRecordsByCruiseID(int id) {
        Session session = sessionFactory.openSession();
        long count = (long) session.createCriteria(CruiseLogEN.class)
                .createCriteria("cruiseRecordEN")
                .add(Restrictions.eq("id", id))
                .setProjection(Projections.rowCount()).uniqueResult();
        session.close();
        return (int) count;
    }

    //巡航统计（WEB）
    public BaseResult xctj(String starttime, String endtime) {
        BaseResult result = new BaseResult();
        List<ChartBean> cb = new ArrayList<>();
        Date sd = new Date();
        Date ed = new Date();
        Calendar cal = Calendar.getInstance();
        if ("".equals(starttime) && "".equals(endtime)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -7);
            sd = cal.getTime();
        } else {
            if ("".equals(starttime)) {
                ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                cal.setTime(ed);
                cal.add(Calendar.DAY_OF_YEAR, -7);
                sd = cal.getTime();
            } else {
                if ("".equals(endtime)) {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    cal.setTime(sd);
                    cal.add(Calendar.DAY_OF_YEAR, 7);
                    ed = cal.getTime();
                } else {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                }
            }
        }
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CruiseRecordEN.class);
        criteria.add(Restrictions.eq("records", "1"));
        criteria.add(Restrictions.between("starttime", sd, ed));
        List<CruiseRecordEN> list = criteria.list();
        Map<String, List<CruiseRecordEN>> map = new HashMap<>();
        for (CruiseRecordEN en : list) {
            Date temp = en.getStarttime();
            String sday = Time.getTimeFmt(temp, "yyyy-MM-dd");
            if (map.containsKey(sday)) {
                map.get(sday).add(en);
            } else {
                List<CruiseRecordEN> enlist = new ArrayList<>();
                enlist.add(en);
                map.put(sday, enlist);
            }
        }
        Iterator i = map.keySet().iterator();
        while (i.hasNext()) {
            String k = (String) i.next();
            List<CruiseRecordEN> v = map.get(k);
            ChartBean b = new ChartBean();
            int sumevent = 0;
            double summiles = 0;
            if (v != null && v.size() > 0) {
                for (CruiseRecordEN en : v) {
                    double tempmile = en.getMiles();
                    summiles += tempmile;
                    int cruiseid = en.getId();
                    Criteria logc = session.createCriteria(CruiseLogEN.class);
                    logc.add(Restrictions.eq("cruiseRecordEN.id", cruiseid));
                    logc.setProjection(Projections.rowCount());
                    int tempi = Integer.parseInt(logc.uniqueResult().toString());
                    sumevent += tempi;
                }
            }
            b.setName(k);
            b.setD1(summiles);
            b.setSum(sumevent);
            cb.add(b);
        }
        Collections.sort(cb, new Comparator<ChartBean>() {
            @Override
            public int compare(ChartBean o1, ChartBean o2) {
                return -o1.getName().compareTo(o2.getName());
            }
        });
        String sql = "select a.id,a.member,a.starttime,a.miles,c.ZZJGMC,count(d.id) as eventnum from cruise_record as a join jcxx_yh as b on a.userid=b.id and a.records='1' and a.starttime>='" + Time.getTimeFmt(sd) + "' and a.starttime<='" + Time.getTimeFmt(ed) + "' join jcxx_zzjg as c on b.dw=c.id left JOIN cruise_log as d  on a.id=d.cruiseid GROUP BY a.id ORDER BY a.starttime desc";
        SQLQuery q = session.createSQLQuery(sql);
        q.addScalar("id", StandardBasicTypes.INTEGER);
        q.addScalar("member", StandardBasicTypes.STRING);
        q.addScalar("starttime", StandardBasicTypes.STRING);
        q.addScalar("miles", StandardBasicTypes.DOUBLE);
        q.addScalar("zzjgmc", StandardBasicTypes.STRING);
        q.addScalar("eventnum", StandardBasicTypes.INTEGER);
        q.setResultTransformer(Transformers.aliasToBean(XctjBean.class));
        List<XctjBean> datalist = q.list();
        Map<String, List<XctjBean>> zzjgmap = new HashMap<>();
        Map<String, Map<String, List<XctjBean>>> retmap = new HashMap<>();
        for (XctjBean en : datalist) {
            String temp = en.getZzjgmc();
            if (zzjgmap.containsKey(temp)) {
                zzjgmap.get(temp).add(en);
            } else {
                List<XctjBean> enlist = new ArrayList<>();
                enlist.add(en);
                zzjgmap.put(temp, enlist);
            }
        }
        Iterator j = zzjgmap.keySet().iterator();
        while (j.hasNext()) {
            Map<String, List<XctjBean>> timemap = new HashMap<>();
            String k = (String) j.next();
            List<XctjBean> v = zzjgmap.get(k);

            for (XctjBean en : v) {
                String temp = en.getStarttime();
                String sday = temp.substring(0, 10);
                if (timemap.containsKey(sday)) {
                    timemap.get(sday).add(en);
                } else {
                    List<XctjBean> enlist = new ArrayList<>();
                    enlist.add(en);
                    timemap.put(sday, enlist);
                }
            }
            Map<String, List<XctjBean>> sortMaps = this.mapSort(timemap);
            retmap.put(k, sortMaps);
        }
        result.addToMap("coldata", cb);
        result.addToMap("retmap", retmap);
        session.flush();
        session.close();
        return result;
    }

    public static Map mapSort(Map<String, List<XctjBean>> map) {
        Map<String, List<XctjBean>> mapVK = new TreeMap<String, List<XctjBean>>(new Comparator<Object>() {
            public int compare(Object obj1, Object obj2) {
                String v1 = (String) obj1;
                String v2 = (String) obj2;
                int s = v2.compareTo(v1);
                return s;
            }
        }
        );

        Set col = map.keySet();
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            List<XctjBean> value = (List<XctjBean>) map.get(key);
            mapVK.put(key, value);
        }
        return mapVK;
    }

    public BaseResult xhrzDt(String member, String starttime, String endtime, int page) {
        Date sd = new Date();
        Date ed = new Date();
        Calendar cal = Calendar.getInstance();
        if ("".equals(starttime) && "".equals(endtime)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -7);
            sd = cal.getTime();
        } else {
            if ("".equals(starttime)) {
                ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                cal.setTime(ed);
                cal.add(Calendar.DAY_OF_YEAR, -7);
                sd = cal.getTime();
            } else {
                if ("".equals(endtime)) {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    cal.setTime(sd);
                    cal.add(Calendar.DAY_OF_YEAR, 7);
                    ed = cal.getTime();
                } else {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                }
            }
        }
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(CruiseRecordEN.class);
        criteria.add(Restrictions.eq("records", "1"));
        criteria.add(Restrictions.between("starttime", sd, ed));
        if (member != null && !"".equals(member)) {
            criteria.add(Restrictions.like("member", "%" + member + "%"));
        }
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<CruiseRecordEN> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        for (CruiseRecordEN c : datas) {
            int cid = c.getId();
            Criteria tempc = session.createCriteria(CruiseLogEN.class);
            tempc.add(Restrictions.eq("cruiseRecordEN.id", cid));
            List list = tempc.list();
            if (list != null) {
                c.setEventnum(list.size());
            } else {
                c.setEventnum(0);
            }
        }
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }

    public BaseResult showXhrz(int id) {
        Session session = sessionFactory.openSession();
        BaseResult result = new BaseResult();
        CruiseRecordEN obj = this.getCruiseByID(id);
        Criteria ec = session.createCriteria(CruiseLogEN.class);
        ec.add(Restrictions.eq("cruiseRecordEN.id", id));
        List eventlist = ec.list();
        session.close();
        result.addToMap("xhobj", obj);
        result.addToMap("xhevent", eventlist);
        return result;
    }

    public BaseResult showXhfj(int id) {
        Session session = sessionFactory.openSession();
        BaseResult result = new BaseResult();
        Criteria c = session.createCriteria(CruiseFileEN.class);
        c.add(Restrictions.eq("cruiseLogEN.id", id));
        List list = c.list();
        result.setObj(list);
        session.close();
        return result;
    }

    public CruiseFileEN showFileById(int id) {
        Session session = sessionFactory.openSession();
        CruiseFileEN obj = new CruiseFileEN();
        obj = (CruiseFileEN) session.createCriteria(CruiseFileEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return obj;
    }
}
