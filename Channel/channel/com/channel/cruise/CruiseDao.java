package com.channel.cruise;


import com.channel.bean.XcBean;
import com.channel.bean.YhlxdBean;
import com.channel.model.xc.*;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("cruiseDao")
public class CruiseDao extends BaseDaoDB {

    public BaseQueryRecords queryCruise(String ids, String starttime, String endtime, int page, int rows) {
        String hql = "from CXcGk a,CHdHdaojcxx b where a.hdid=b.id";
        if (ids != null && !"".equals(ids)) {
            hql += " and a.hdid in(" + ids + ")";
        }
        if (starttime != null && !"".equals(starttime)) {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.starttime <='" + endtime + "'";
                hql += " and a.endtime >='" + starttime + "'";
            } else {
                hql += " and a.starttime >='" + starttime + "'";
            }
        } else {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.endtime <='" + endtime + "'";
            }
        }

        return super.find(new HQL(hql), page, rows);
    }

    public CXcGk queryGkById(int id) {
        return (CXcGk) super.findUnique(new CXcGk(), "id", id);
    }

    public List<CXcQk> queryQkflsById(int id) {
        return (List<CXcQk>) super.find(new CXcQk(), "xcid", id).getData();
    }

    public List<CXcWt> queryWtsByIds(String ids) {
        String hql = "from CXcWt where qkid in(?)";
        return (List<CXcWt>) super.find(new HQL(hql, ids)).getData();
    }

    public void delObj(Object obj) {
        super.delete(obj);
    }

    public CXcYhgk queryYhgkById(Integer id) {
        return (CXcYhgk) super.findUnique(new CXcYhgk(), "id", id);
    }

    public List<CXcYhqk> queryYhqksById(Integer id) {
        return (List<CXcYhqk>) super.find(new CXcYhqk(), "xcid", id).getData();
    }

    public List<CXcYhwt> queryYhwtsByIds(String ids) {
        String hql = "from CXcYhwt where qkid in(?)";
        return (List<CXcYhwt>) super.find(new HQL(hql, ids)).getData();
    }

    public void delQk(String ids) {
        String hql = "delete from CXcQk where id in(?)";
        super.delete(new HQL(hql, ids));
    }

    public void delWt(String ids) {
        String hql = "delete from CXcWt where id in(?)";
        super.delete(new HQL(hql, ids));
    }

    public void delSj(String ids) {
        String hql = "delete from CXcSj where wtid in(?)";
        super.delete(new HQL(hql, ids));
    }

    public void delYhqk(String ids) {
        String hql = "delete from CXcYhqk where id in(?)";
        super.delete(new HQL(hql, ids));
    }

    public void delYhwt(String ids) {
        String hql = "delete from CXcYhwt where id in(?)";
        super.delete(new HQL(hql, ids));
    }

    public void delYhsj(String ids) {
        String hql = "delete from CXcYhsj where wtid in(?)";
        super.delete(new HQL(hql, ids));
    }

    public void delFjByXcid(Integer id) {
        String hql = "delete from CXcFj where xcid =?";
        super.delete(new HQL(hql, id));
    }

    public void addCXcFj(CXcFj fj) {
        super.save(fj);
    }

    public CXcFj queryFjByMd5(int id, String infileMd5) {
        String hql = "from CXcFj where xcid=? and fmd5='?'";
        return (CXcFj) super.findUnique(new HQL(hql, id, infileMd5));
    }

    public CXcFj queryFjByid(int id) {
        return (CXcFj) super.findUnique(new CXcFj(), "id", id);
    }

    public BaseQueryRecords queryYhlxd(String ids, String starttime, String endtime, String content, int page, int rows) {
        String hql = "from CXcYhgk a,CHdHdaojcxx b where a.hdid=b.id";
        if (ids != null && !"".equals(ids)) {
            hql += " and a.hdid in(" + ids + ")";
        }
        if (starttime != null && !"".equals(starttime)) {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.starttime <='" + endtime + "'";
                hql += " and a.endtime >='" + starttime + "'";
            } else {
                hql += " and a.starttime >='" + starttime + "'";
            }
        } else {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.endtime <='" + endtime + "'";
            }
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.qd like '%" + content + "%' or a.zd like '%" + content + "%')";
        }
        return super.find(new HQL(hql), page, rows);
    }

    public List<YhlxdBean> queryYhlxdById(int id) {
        String sql = "select a.ID,a.hdid,e.hdmc,a.ztqk,a.STARTTIME,a.ENDTIME,a.QD,a.ZD,a.QDZH,a.ZDZH,a.XHTH,a.CYR,a.DEPT,a.BGBM,b.QK,b.QKSM,c.wt,c.wtsm,c.CLYJ,c.CLJG,d.JTWZ,d.ab,d.MS from c_xc_yhgk a LEFT JOIN c_xc_yhqk b on a.ID = b.XCID LEFT JOIN c_xc_yhwt c on b.ID = c.QKID LEFT JOIN c_xc_yhsj d ON c.ID = d.WTID LEFT JOIN c_hd_hdaojcxx e on a.hdid = e.ID where a.ID=? ";
        SQLQuery query = getCurrentSession().createSQLQuery(sql);
        query.setParameter(0, id);
        query.addScalar("id", StandardBasicTypes.INTEGER);
        query.addScalar("hdid", StandardBasicTypes.INTEGER);
        query.addScalar("hdmc", StandardBasicTypes.STRING);
        query.addScalar("ztqk", StandardBasicTypes.INTEGER);
        query.addScalar("starttime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("endtime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("qd", StandardBasicTypes.STRING);
        query.addScalar("zd", StandardBasicTypes.STRING);
        query.addScalar("qdzh", StandardBasicTypes.STRING);
        query.addScalar("zdzh", StandardBasicTypes.STRING);
        query.addScalar("xhth", StandardBasicTypes.STRING);
        query.addScalar("cyr", StandardBasicTypes.STRING);
        query.addScalar("dept", StandardBasicTypes.INTEGER);
        query.addScalar("bgbm", StandardBasicTypes.STRING);
        query.addScalar("qk", StandardBasicTypes.INTEGER);
        query.addScalar("qksm", StandardBasicTypes.STRING);
        query.addScalar("wt", StandardBasicTypes.INTEGER);
        query.addScalar("wtsm", StandardBasicTypes.STRING);
        query.addScalar("clyj", StandardBasicTypes.STRING);
        query.addScalar("cljg", StandardBasicTypes.STRING);
        query.addScalar("jtwz", StandardBasicTypes.STRING);
        query.addScalar("ab", StandardBasicTypes.INTEGER);
        query.addScalar("ms", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(YhlxdBean.class));
        List<YhlxdBean> list = query.list();
        return list;
    }

    public List<XcBean> queryCruiseById(int id) {
        String sql = "select a.ID,a.hdid,e.hdmc,a.ztqk,a.STARTTIME,a.ENDTIME,a.QD,a.ZD,a.QDZH,a.ZDZH,a.XHTH,a.CYR,a.DEPT,b.QK,b.QKSM,c.wt,c.wtsm,c.CLYJ,c.CLJG,d.JTWZ,d.ab,d.MS from c_xc_gk a LEFT JOIN c_xc_qk b on a.ID = b.XCID LEFT JOIN c_xc_wt c on b.ID = c.QKID LEFT JOIN c_xc_sj d ON c.ID = d.WTID LEFT JOIN c_hd_hdaojcxx e on a.hdid = e.ID where a.ID=? ";
        SQLQuery query = getCurrentSession().createSQLQuery(sql);
        query.setParameter(0, id);
        query.addScalar("id", StandardBasicTypes.INTEGER);
        query.addScalar("hdid", StandardBasicTypes.INTEGER);
        query.addScalar("hdmc", StandardBasicTypes.STRING);
        query.addScalar("ztqk", StandardBasicTypes.INTEGER);
        query.addScalar("starttime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("endtime", StandardBasicTypes.TIMESTAMP);
        query.addScalar("qd", StandardBasicTypes.STRING);
        query.addScalar("zd", StandardBasicTypes.STRING);
        query.addScalar("qdzh", StandardBasicTypes.STRING);
        query.addScalar("zdzh", StandardBasicTypes.STRING);
        query.addScalar("xhth", StandardBasicTypes.STRING);
        query.addScalar("cyr", StandardBasicTypes.STRING);
        query.addScalar("dept", StandardBasicTypes.INTEGER);
        query.addScalar("qk", StandardBasicTypes.INTEGER);
        query.addScalar("qksm", StandardBasicTypes.STRING);
        query.addScalar("wt", StandardBasicTypes.INTEGER);
        query.addScalar("wtsm", StandardBasicTypes.STRING);
        query.addScalar("clyj", StandardBasicTypes.STRING);
        query.addScalar("cljg", StandardBasicTypes.STRING);
        query.addScalar("jtwz", StandardBasicTypes.STRING);
        query.addScalar("ab", StandardBasicTypes.INTEGER);
        query.addScalar("ms", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(XcBean.class));
        List<XcBean> list = query.list();
        return list;

    }

    public BaseQueryRecords queryFjByPid(int id) {
        return (BaseQueryRecords<CXcFj>) super.find(new CXcFj(), "xcid", id);
    }

    public void delCXcFjById(Integer id) {
        String hql = "delete from CXcFj where id=?";
        super.delete(new HQL(hql, id));
    }

    public void delCXcYhfjById(Integer id) {
        String hql = "delete from CXcYhfj where id=?";
        super.delete(new HQL(hql, id));
    }

    public CXcYhfj queryYhfjByMd5(int yhid, String infileMd5) {
        String hql = "from CXcYhfj where yhid=? and fmd5='?'";
        return (CXcYhfj) super.findUnique(new HQL(hql, yhid, infileMd5));
    }

    public CXcYhgk queryYhgkByXcid(Integer id) {
        return (CXcYhgk) super.findUnique(new CXcYhgk(), "xcid", id);
    }

    public void updateFjId(Integer id, int newxcid) {
        String hql = "update CXcFj set xcid=? where xcid=?";
        super.update(new HQL(hql, newxcid, id));
    }

    public void delFjByYhid(Integer id) {
        String hql = "delete from CXcYhfj where yhid =?";
        super.delete(new HQL(hql, id));
    }

    public void updateXcId(Integer id, int newxcid) {
        String hql = "update CXcYhgk set xcid=? where xcid=?";
        super.update(new HQL(hql, newxcid, id));
    }

    public BaseQueryRecords queryYhfjByPid(Integer id) {
        return (BaseQueryRecords<CXcYhfj>) super.find(new CXcYhfj(), "yhid", id);
    }

    public CXcYhfj queryYhfjByid(int id) {
        return (CXcYhfj) super.findUnique(new CXcYhfj(), "id", id);
    }

    public void addCXcYhfj(CXcYhfj yhfj) {
        super.save(yhfj);
    }

    public BaseQueryRecords queryCruise(String ids, String starttime, String endtime, int userxzqh, int page, int rows) {
        String hql = "select a,b,d from CXcGk a,CHdHdaojcxx b,CXtUser c,CXtDpt d where a.hdid=b.id and a.creater=c.id and c.department=d.id and (a.dept=" + userxzqh + " or d.xzqh=" + userxzqh + ")";
        if (ids != null && !"".equals(ids)) {
            hql += " and a.hdid in(" + ids + ")";
        }
        if (starttime != null && !"".equals(starttime)) {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.starttime <='" + endtime + "'";
                hql += " and a.endtime >='" + starttime + "'";
            } else {
                hql += " and a.starttime >='" + starttime + "'";
            }
        } else {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.endtime <='" + endtime + "'";
            }
        }
        hql += " order by a.starttime desc";
        return super.find(new HQL(hql), page, rows);
    }

    public BaseQueryRecords queryYhlxd(String ids, String starttime, String endtime, String content, int userxzqh, int page, int rows) {
        String hql = "select a,b from CXcYhgk a,CHdHdaojcxx b,CXtUser c,CXtDpt d where a.hdid=b.id and a.creater=c.id and c.department=d.id and (a.dept=" + userxzqh + " or d.xzqh=" + userxzqh + ")";
        if (ids != null && !"".equals(ids)) {
            hql += " and a.hdid in(" + ids + ")";
        }
        if (starttime != null && !"".equals(starttime)) {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.starttime <='" + endtime + "'";
                hql += " and a.endtime >='" + starttime + "'";
            } else {
                hql += " and a.starttime >='" + starttime + "'";
            }
        } else {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.endtime <='" + endtime + "'";
            }
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.qd like '%" + content + "%' or a.zd like '%" + content + "%')";
        }
        hql += " order by a.starttime desc";
        return super.find(new HQL(hql), page, rows);
    }

    public BaseQueryRecords queryCruiseXzqh(String ids, String starttime, String endtime, String xzqhs, int page, int rows) {
        String hql = "select a,b,d from CXcGk a,CHdHdaojcxx b,CXtUser c,CXtDpt d where a.hdid=b.id and a.creater=c.id and c.department=d.id and (a.dept in(" + xzqhs + ") or d.xzqh in(" + xzqhs + "))";
        if (ids != null && !"".equals(ids)) {
            hql += " and a.hdid in(" + ids + ")";
        }
        if (starttime != null && !"".equals(starttime)) {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.starttime <='" + endtime + "'";
                hql += " and a.endtime >='" + starttime + "'";
            } else {
                hql += " and a.starttime >='" + starttime + "'";
            }
        } else {
            if (endtime != null && !"".equals(endtime)) {
                hql += " and a.endtime <='" + endtime + "'";
            }
        }
        hql += " order by a.starttime desc";
        return super.find(new HQL(hql), page, rows);
    }
}
