package com.channel.traffic;

import com.channel.bean.Constants;
import com.channel.bean.ShipTrafficDT;
import com.channel.bean.TrafficDT;
import com.channel.model.hd.CHdJgllgcd;
import com.channel.model.hd.CHdRggcd;
import com.channel.model.ll.CLlCbllgc;
import com.channel.model.ll.CLlGcd;
import com.channel.model.ll.CLlHdllgc;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by 25019 on 2015/10/20.
 */
@Repository("trafficDao")
public class TrafficDao extends BaseDaoDB {

    public BaseQueryRecords<CHdRggcd> queryRgGcdByIds(String ids) {
        String hql = "select a from CHdRggcd a";
        if (ids != null && !"".equals(ids)) {
            String conditions = " where a.sshdaoid in(" + ids + ")";
            hql += conditions;
        }
        return (BaseQueryRecords<CHdRggcd>) super.find(new HQL(hql));
    }

    public BaseQueryRecords<CHdJgllgcd> queryJgGcdByIds(String ids) {
        String hql = "select a from CHdJgllgcd a";
        if (ids != null && !"".equals(ids)) {
            String conditions = " where a.sshdaoid in(" + ids + ")";
            hql += conditions;
        }
        return (BaseQueryRecords<CHdJgllgcd>) super.find(new HQL(hql));
    }

    //查询人工流量观测点
    public BaseQueryRecords<CHdRggcd> queryRgGcdByHdao(Integer hdaoid) {
        String hql = "select a from CHdRggcd a";

        if (hdaoid > 0) {
            String conditions = " where a.sshdaoid in(" + hdaoid + ")";
            hql += conditions;
        }

        return (BaseQueryRecords<CHdRggcd>) super.find(new HQL(hql));
    }

    //查询激光流量观测点
    public BaseQueryRecords<CHdJgllgcd> queryJgGcdByHdao(Integer hdaoid) {
        String hql = "select a from CHdJgllgcd a";

        if (hdaoid > 0) {
            String conditions = " where a.sshdaoid in(" + hdaoid + ")";
            hql += conditions;
        }
        return (BaseQueryRecords<CHdJgllgcd>) super.find(new HQL(hql));
    }

    public void addTraffic(CLlHdllgc hdllgc) {
        super.save(hdllgc);
    }

    public void delTraffic(CLlHdllgc hdllgc) {
        super.delete(hdllgc);
    }

    public CLlHdllgc queryHdllgcByID(Integer id) {
        return (CLlHdllgc) super.findUnique(new CLlHdllgc(), "id", id);
    }

    public BaseQueryRecords<TrafficDT> queryTraffic(String strjgs, String strrgs, String strstarttime, String strendtime, int flag, int page, int rows) {
        List list = new ArrayList<>();
        String sql = "";
        String sqljg = "select a.id,a.gczid,b.mc,a.type,a.starttime,a.endtime,a.upcbnum,a.upcbton,a.upgoodston,a.downcbnum,a.downcbton,a.downgoodston from c_ll_hdllgc a,c_hd_jgllgcd b where a.GCZID=b.ID and a.TYPE=" + Constants.GCZ_JG + " and a.TIMEFLAG=" + flag;
        String sqlrg = "select a.id,a.gczid,c.mc,a.type,a.starttime,a.endtime,a.upcbnum,a.upcbton,a.upgoodston,a.downcbnum,a.downcbton,a.downgoodston from c_ll_hdllgc a,c_hd_rggcd c where a.GCZID=c.ID and a.TYPE=" + Constants.GCZ_RG + " and a.TIMEFLAG=" + flag;
        String sqlunion = "";
        String sqlorder = " order by a.starttime desc";
        if (strstarttime != null && !"".equals(strstarttime)) {
            sqljg += " and a.starttime>='" + strstarttime + "'";
            sqlrg += " and a.starttime>='" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            sqljg += " and a.endtime<='" + strendtime + "'";
            sqlrg += " and a.endtime<='" + strendtime + "'";
        }
        if (strjgs != null && !"".equals(strjgs)) {
            sqljg += " and a.gczid in(" + strjgs + ")";
            if (strrgs != null && !"".equals(strrgs)) {//都有
                sqlrg += " and a.gczid in(" + strrgs + ")";
                sqlunion = " union all ";
                sql = "select * from (" + sqljg + sqlunion + sqlrg + ") a" + sqlorder;
            } else {//激光有 人工没
                sqlrg = " ";
                sql = sqljg + sqlunion + sqlrg + sqlorder;
            }
        } else {
            if (strrgs != null && !"".equals(strrgs)) {//人工有 激光没
                sqlrg += " and a.gczid in(" + strrgs + ")";
                sqljg = " ";
                sql = sqljg + sqlunion + sqlrg + sqlorder;
            } else {//都没有
                sqlunion = " union all ";
                sql = "select * from (" + sqljg + sqlunion + sqlrg + ") a" + sqlorder;
            }
        }
        SQLQuery query = (SQLQuery) getCurrentSession().createSQLQuery(sql).setFirstResult(page * rows - rows).setMaxResults(rows);
        query.addScalar("id", StandardBasicTypes.INTEGER);
        query.addScalar("gczid", StandardBasicTypes.INTEGER);
        query.addScalar("mc", StandardBasicTypes.STRING);
        query.addScalar("type", StandardBasicTypes.INTEGER);
        query.addScalar("starttime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("endtime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("upcbnum", StandardBasicTypes.INTEGER);
        query.addScalar("upcbton", StandardBasicTypes.DOUBLE);
        query.addScalar("upgoodston", StandardBasicTypes.DOUBLE);
        query.addScalar("downcbnum", StandardBasicTypes.INTEGER);
        query.addScalar("downcbton", StandardBasicTypes.DOUBLE);
        query.addScalar("downgoodston", StandardBasicTypes.DOUBLE);
        query.setResultTransformer(Transformers.aliasToBean(TrafficDT.class));
        list = query.list();
        long total = super.count(new SQL("select count(*) from (" + sql + ") a"));
        return new BaseQueryRecords<>(list, total, page, rows);
    }

    public BaseQueryRecords queryTrafficInfo(CLlHdllgc hdllgc) {
        int id = hdllgc.getId();
        int type = hdllgc.getType();
        String hql = "";
        if (type == Constants.GCZ_JG) {
            hql = "from CLlHdllgc a,CHdJgllgcd b,CHdHdaojcxx c where a.gczid=b.id and b.sshdaoid=c.id and a.id=?";
        } else if (type == Constants.GCZ_RG) {
            hql = "from CLlHdllgc a,CHdRggcd b,CHdHdaojcxx c where a.gczid=b.id and b.sshdaoid=c.id and a.id=?";
        } else {
            return null;
        }
        return super.find(new HQL(hql, id));
    }


    public BaseQueryRecords queryShipTraffic(String strjgs, String strrgs, String strstarttime, String strendtime, int page, int rows) {
        List list = new ArrayList<>();
        String sql = "";
        String sqljg = "select a.id,a.gcdid,b.mc,a.type,a.starttime,a.endtime,a.shipname,a.shipdire,d.attrdesc as shiptype,a.shipton,a.shipempty,e.attrdesc as goodstype,a.goodston from c_ll_cbllgc a,c_hd_jgllgcd b,c_zd_appattribute d,c_zd_appattribute e where a.GCDID=b.ID and a.shiptype=d.id and a.goodstype=e.id and a.TYPE=" + Constants.GCZ_JG;
        String sqlrg = "select a.id,a.gcdid,c.mc,a.type,a.starttime,a.endtime,a.shipname,a.shipdire,f.attrdesc as shiptype,a.shipton,a.shipempty,g.attrdesc as goodstype,a.goodston from c_ll_cbllgc a,c_hd_rggcd c,c_zd_appattribute f,c_zd_appattribute g where a.GCDID=c.ID and a.shiptype=f.id and a.goodstype=g.id and a.TYPE=" + Constants.GCZ_RG;
        String sqlunion = "";
        String sqlorder = " order by a.starttime desc";
        if (strstarttime != null && !"".equals(strstarttime)) {
            sqljg += " and a.starttime>='" + strstarttime + "'";
            sqlrg += " and a.starttime>='" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            sqljg += " and a.endtime<='" + strendtime + "'";
            sqlrg += " and a.endtime<='" + strendtime + "'";
        }
        if (strjgs != null && !"".equals(strjgs)) {
            sqljg += " and a.gcdid in(" + strjgs + ")";
            if (strrgs != null && !"".equals(strrgs)) {//都有
                sqlrg += " and a.gcdid in(" + strrgs + ")";
                sqlunion = " union all ";
                sql = "select * from (" + sqljg + sqlunion + sqlrg + ") a" + sqlorder;
            } else {//激光有 人工没
                sqlrg = " ";
                sql = sqljg + sqlunion + sqlrg + sqlorder;
            }
        } else {
            if (strrgs != null && !"".equals(strrgs)) {//人工有 激光没
                sqlrg += " and a.gcdid in(" + strrgs + ")";
                sqljg = " ";
                sql = sqljg + sqlunion + sqlrg + sqlorder;
            } else {//都没有
                sqlunion = " union all ";
                sql = "select * from (" + sqljg + sqlunion + sqlrg + ") a" + sqlorder;
            }
        }
        SQLQuery query = (SQLQuery) getCurrentSession().createSQLQuery(sql).setFirstResult(page * rows - rows).setMaxResults(rows);
        query.addScalar("id", StandardBasicTypes.INTEGER);
        query.addScalar("gcdid", StandardBasicTypes.INTEGER);
        query.addScalar("mc", StandardBasicTypes.STRING);
        query.addScalar("type", StandardBasicTypes.INTEGER);
        query.addScalar("starttime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("endtime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("shipname", StandardBasicTypes.STRING);
        query.addScalar("shipdire", StandardBasicTypes.INTEGER);
        query.addScalar("shiptype", StandardBasicTypes.STRING);
        query.addScalar("shipton", StandardBasicTypes.DOUBLE);
        query.addScalar("shipempty", StandardBasicTypes.INTEGER);
        query.addScalar("goodstype", StandardBasicTypes.STRING);
        query.addScalar("goodston", StandardBasicTypes.DOUBLE);
        query.setResultTransformer(Transformers.aliasToBean(ShipTrafficDT.class));
        list = query.list();
        long total = super.count(new SQL("select count(*) from (" + sql + ") a"));
        return new BaseQueryRecords<>(list, total, page, rows);
    }

    public CLlCbllgc queryCbllgcByID(Integer id) {
        return (CLlCbllgc) super.findUnique(new CLlCbllgc(), "id", id);
    }

    public BaseQueryRecords queryShipTrafficInfo(CLlCbllgc cbllgc) {
        int id = cbllgc.getId();
        int type = cbllgc.getType();
        String hql = "";
        if (type == Constants.GCZ_JG) {
            hql = "from CLlCbllgc a,CHdJgllgcd b,CHdHdaojcxx c,CZdAppattribute d,CZdAppattribute e where a.gcdid=b.id and b.sshdaoid=c.id and a.shiptype=d.id and a.goodstype=e.id and a.id=?";
        } else if (type == Constants.GCZ_RG) {
            hql = "from CLlCbllgc a,CHdRggcd b,CHdHdaojcxx c,CZdAppattribute d,CZdAppattribute e where a.gcdid=b.id and b.sshdaoid=c.id and a.shiptype=d.id and a.goodstype=e.id and a.id=?";
        } else {
            return null;
        }
        return super.find(new HQL(hql, id));
    }

    public BaseQueryRecords queryCbGcd() {
        return super.find(new CLlGcd());
    }

    public BaseQueryRecords queryCbLl(String gcdbh, String starttime, String endtime) {
        String sql = "select ifnull(sum(sxkzzsl),0),ifnull(sum(sxkzzdw),0),ifnull(sum(sxzzzsl),0),ifnull(sum(sxzzzdw),0),ifnull(sum(sxzsl),0),ifnull(sum(sxzdw),0),ifnull(sum(xxkzzsl),0),ifnull(sum(xxkzzdw),0),ifnull(sum(xxzzzsl),0),ifnull(sum(xxzzzdw),0),ifnull(sum(xxzsl),0),ifnull(sum(xxzdw),0),ifnull(sum(zsl),0),ifnull(sum(zdw),0) from c_ll_cbsjhz where 1=1";
        if (!"-1".equals(gcdbh)) {
            sql += " and name='" + gcdbh + "'";
        }
        if (starttime != null && !"".equals(starttime)) {
            sql += " and hzrq>='" + starttime + "'";
        }
        if (endtime != null && !"".equals(endtime)) {
            sql += " and hzrq<='" + endtime + "'";
        }
        return super.find(new SQL(sql));
    }

    public CLlGcd queryGcdByID(String gcdid) {
        return (CLlGcd) super.findUnique(new CLlGcd(), "gcdid", gcdid);
    }

}
