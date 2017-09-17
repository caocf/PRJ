package com.channel.hangduan.dao.impl;

import java.util.List;

import com.channel.model.user.CZdXzqhdm;
import com.common.dao.impl.SQL;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.channel.hangduan.dao.HangDuanDao;
import com.channel.model.hd.CHdHduanjcxx;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;

@Repository("hangduandao")
public class HangDuanDaoImpl extends BaseDaoDB implements HangDuanDao {

    @Override
    public CHdHduanjcxx queryMaxHdbh(Integer sshdid) {
        String hql = "from CHdHduanjcxx where sshdid=? order by hdbh desc";
        return (CHdHduanjcxx) super.findUnique(new HQL(hql, sshdid));
    }

    @Override
    public Boolean queryHdbhExisted(String hdbh, Integer sshdid) {
        String hql = "select count(*) from CHdHduanjcxx where hdbh='?' and sshdid=?";
        long cnt = super.count(new HQL(hql, hdbh, sshdid));
        return cnt <= 0 ? true : false;
    }

    @Override
    public Boolean queryIsQdhd(String qdmc, int id) {
        String hql = "select count(*) from CHdHduanjcxx where hdzdmc='?' and sshdid=?";
        long cnt = super.count(new HQL(hql, qdmc, id));
        return cnt <= 0 ? true : false;
    }

    @Override
    public Boolean queryIsZdhd(String zdmc, int id) {
        String hql = "select count(*) from CHdHduanjcxx where hdqdmc='?' and sshdid=?";
        long cnt = super.count(new HQL(hql, zdmc, id));
        return cnt <= 0 ? true : false;
    }

    @Override
    public BaseQueryRecords queryHangDuanByXzqh(List<CZdXzqhdm> xzqhs) {
        String hql = "from CHdHduanjcxx a,CHdHdaojcxx b " +
                "where a.sshdid = b.id ";
        if (xzqhs != null) {
            hql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        return super.find(new HQL(hql));
    }


    @Override
    public BaseQueryRecords<CHdHduanjcxx> searchAllHangDuan(List<CZdXzqhdm> xzqhs, int sshdid, int hddj, String content) {
        String hql = "select b.id as hdaoid, b.hdbh as hdaobh, b.hdmc as hdaomc," +
                "a.id as hduanid,a.hdbh as hduanbh,a.hdqdmc as hduanqdmc,a.hdzdmc as hduanzdmc " +
                "from CHdHduanjcxx a,CHdHdaojcxx b " +
                "where a.sshdid = b.id ";
        if (content != null && !"".equals(content)) {
            hql += " and (a.hdqdmc||'-'||a.hdzdmc like '%" + content + "%' or (b.hdbh || a.hdbh) like '%" + content + "%')"; //搜索内容在 ‘起点-终点’内字符的
        }
        if (xzqhs != null) {
            hql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        if (hddj > 0) {
            hql += " and a.hddj=" + hddj;
        }
        if (sshdid > 0) {
            hql += " and a.sshdid=" + sshdid;
        }
        return (BaseQueryRecords<CHdHduanjcxx>) super.find(new HQL(hql));
    }

    @Override
    public BaseQueryRecords<CHdHduanjcxx> queryHangDuanBySshdid(int sshdid, List<CZdXzqhdm> xzqhdms) {
        String hql = "select a from CHdHduanjcxx a where a.sshdid=" + sshdid;
        if (xzqhdms != null && xzqhdms.size() > 0) {
            hql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhdms.size(); i++) {
                hql += xzqhdms.get(i).getId();
                if (i != xzqhdms.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        return (BaseQueryRecords<CHdHduanjcxx>) super.find(new HQL(hql));
    }


    @Override
    public BaseQueryRecords searchHangDuanHddj(Integer sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content) {
        String hql = "select a from CHdHduanjcxx a where a.sshdid=" + sshdid;
        if (xzqhs != null && xzqhs.size() > 0) {
            hql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        if (hddj != -1) {
            hql += " and a.hddj=" + hddj;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.hdqdmc like '%" + content + "%' or a.hdzdmc like '%" + content + "%')";
        }
        return (BaseQueryRecords<CHdHduanjcxx>) super.find(new HQL(hql));
    }

    @Override
    public CHdHduanjcxx queryHangDuanByBh(String hduanbh, int hdaoid) {
        String hql = "from CHdHduanjcxx where hdbh='?' and sshdid=?";
        return (CHdHduanjcxx) super.findUnique(new HQL(hql, hduanbh, hdaoid));
    }

    @Override
    public BaseQueryRecords<CHdHduanjcxx> queryXzqhBySjbh(int sjbh) {
        String hql = "select a from CHdHduanjcxx a,CHdHdaojcxx b where a.sshdid=b.id";
        if (sjbh != -1) {
            hql += " and sssjbh=" + sjbh;
        }
        hql += " group by a.hdszxzqh";
        return (BaseQueryRecords<CHdHduanjcxx>) super.find(new HQL(hql));
    }

    @Override
    public BaseQueryRecords zhcxHduanInfo(int sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content, int page, int rows) {
        String hql = "select a.id,concat(b.hdbh,a.hdbh),concat(a.hdqdmc,'-',a.hdzdmc),b.hdmc,c.attrdesc,d.name,a.hdlc from CHdHduanjcxx a,CHdHdaojcxx b,CZdAppattribute c,CZdXzqhdm d where a.sshdid=b.id and a.hddj=c.id and a.hdszxzqh=d.id";
        if (sshdid != -1 && sshdid > 0) {
            hql += " and a.sshdid=" + sshdid;
        }
        if (xzqhs != null && xzqhs.size() > 0) {
            hql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        if (hddj != -1 && hddj > 0) {
            hql += " and a.hddj=" + hddj;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.hdqdmc like '%" + content + "%' or a.hdzdmc like '%" + content + "%')";
        }
        return (BaseQueryRecords<CHdHduanjcxx>) super.find(new HQL(hql), page, rows);
    }

    @Override
    public List countAppByHdid(int sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content) {

        Session session = getSessionFactory().openSession();
        String sql = "select a.id,concat(b.hdbh,a.hdbh) as bh,concat(a.hdqdmc,'-',a.hdzdmc) as hduanmc,b.hdmc as hdaomc,c.attrdesc as hddj,d.name as xzqh,a.hdlc ,ifnull(hbnum,0) as hb,ifnull(qlnum,0) as ql,ifnull(lxnum,0) as lx,ifnull(kynum,0)+ifnull(hynum,0)+ifnull(gwnum,0)+ifnull(mbqnum,0)+ifnull(fwqnum,0) as mt,ifnull(snnum,0) as sn,ifnull(jgnum,0)+ifnull(spnum,0)+ifnull(rgnum,0) as gcd from c_hd_hduanjcxx a\n" +
                "LEFT JOIN c_hd_hdaojcxx b\n" +
                "on a.sshdid=b.id\n" +
                "LEFT JOIN c_zd_appattribute c\n" +
                "on a.hddj=c.id\n" +
                "LEFT JOIN c_zd_xzqhdm d \n" +
                "on a.hdszxzqh=d.id\n" +
                "LEFT JOIN \n" +
                "(select sshduanid,ifnull(count(*),0) as hbnum from c_hd_hb GROUP BY SSHDUANID) e \n" +
                "ON a.id=e.sshduanid\n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as qlnum from c_hd_ql group by sshduanid) f\n" +
                "ON a.id=f.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as lxnum from c_hd_lx group by sshduanid) g\n" +
                "ON a.id=g.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as kynum from c_hd_kymt group by sshduanid) h\n" +
                "ON a.id=h.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as hynum from c_hd_hymt group by sshduanid) i\n" +
                "ON a.id=i.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as gwnum from c_hd_gwmt group by sshduanid) j\n" +
                "ON a.id=j.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as mbqnum from c_hd_mbq group by sshduanid) k\n" +
                "ON a.id=k.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as fwqnum from c_hd_fwq group by sshduanid) l\n" +
                "ON a.id=l.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as snnum from c_hd_sn group by sshduanid) m\n" +
                "ON a.id=m.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as jgnum from c_hd_jgllgcd group by sshduanid) n\n" +
                "ON a.id=n.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as spnum from c_hd_spgcd group by sshduanid) o\n" +
                "ON a.id=o.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as rgnum from c_hd_rggcd group by sshduanid) p\n" +
                "ON a.id=p.sshduanid \n" +
                "where 1=1";
        if (sshdid != -1) {
            sql += " and a.sshdid=" + sshdid;
        }
        if (xzqhs != null && xzqhs.size() > 0) {
            sql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        if (hddj != -1) {
            sql += " and a.hddj=" + hddj;
        }
        if (content != null && !"".equals(content)) {
            sql += " and (a.hdqdmc like '%" + content + "%' or a.hdzdmc like '%" + content + "%')";
        }
        Query query = session.createSQLQuery(
                new SQL(sql).toString());
        List list = query.list();
        session.flush();
        session.close();
        return list;
    }


    @Override
    public List countTotalByHdid(int sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content) {

        Session session = getSessionFactory().openSession();
        String sql = "select sum(hb),sum(ql),sum(lx),sum(mt),sum(sn),sum(gcd) from (select a.id,concat(b.hdbh,a.hdbh) as bh,concat(a.hdqdmc,'-',a.hdzdmc) as hduanmc,b.hdmc as hdaomc,c.attrdesc as hddj,d.name as xzqh,a.hdlc ,ifnull(hbnum,0) as hb,ifnull(qlnum,0) as ql,ifnull(lxnum,0) as lx,ifnull(kynum,0)+ifnull(hynum,0)+ifnull(gwnum,0)+ifnull(mbqnum,0)+ifnull(fwqnum,0) as mt,ifnull(snnum,0) as sn,ifnull(jgnum,0)+ifnull(spnum,0)+ifnull(rgnum,0) as gcd from c_hd_hduanjcxx a\n" +
                "LEFT JOIN c_hd_hdaojcxx b\n" +
                "on a.sshdid=b.id\n" +
                "LEFT JOIN c_zd_appattribute c\n" +
                "on a.hddj=c.id\n" +
                "LEFT JOIN c_zd_xzqhdm d \n" +
                "on a.hdszxzqh=d.id\n" +
                "LEFT JOIN \n" +
                "(select sshduanid,ifnull(count(*),0) as hbnum from c_hd_hb GROUP BY SSHDUANID) e \n" +
                "ON a.id=e.sshduanid\n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as qlnum from c_hd_ql group by sshduanid) f\n" +
                "ON a.id=f.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as lxnum from c_hd_lx group by sshduanid) g\n" +
                "ON a.id=g.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as kynum from c_hd_kymt group by sshduanid) h\n" +
                "ON a.id=h.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as hynum from c_hd_hymt group by sshduanid) i\n" +
                "ON a.id=i.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as gwnum from c_hd_gwmt group by sshduanid) j\n" +
                "ON a.id=j.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as mbqnum from c_hd_mbq group by sshduanid) k\n" +
                "ON a.id=k.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as fwqnum from c_hd_fwq group by sshduanid) l\n" +
                "ON a.id=l.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as snnum from c_hd_sn group by sshduanid) m\n" +
                "ON a.id=m.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as jgnum from c_hd_jgllgcd group by sshduanid) n\n" +
                "ON a.id=n.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as spnum from c_hd_spgcd group by sshduanid) o\n" +
                "ON a.id=o.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as rgnum from c_hd_rggcd group by sshduanid) p\n" +
                "ON a.id=p.sshduanid \n" +
                "where 1=1";
        if (sshdid != -1) {
            sql += " and a.sshdid=" + sshdid;
        }
        if (xzqhs != null && xzqhs.size() > 0) {
            sql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        if (hddj != -1) {
            sql += " and a.hddj=" + hddj;
        }
        if (content != null && !"".equals(content)) {
            sql += " and (a.hdqdmc like '%" + content + "%' or a.hdzdmc like '%" + sql + "%')";
        }
        sql += ") aaa";
        Query query = session.createSQLQuery(
                new SQL(sql).toString());
        List list = query.list();
        session.flush();
        session.close();
        return list;
    }

    @Override
    public List countAppByHdid(Integer sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content, int page, int rows) {
        Session session = getSessionFactory().openSession();
        String sql = "select a.id,concat(b.hdbh,a.hdbh) as bh,concat(a.hdqdmc,'-',a.hdzdmc) as hduanmc,b.hdmc as hdaomc,c.attrdesc as hddj,d.name as xzqh,a.hdlc ,ifnull(hbnum,0) as hb,ifnull(qlnum,0) as ql,ifnull(lxnum,0) as lx,ifnull(kynum,0)+ifnull(hynum,0)+ifnull(gwnum,0)+ifnull(mbqnum,0)+ifnull(fwqnum,0) as mt,ifnull(snnum,0) as sn,ifnull(jgnum,0)+ifnull(spnum,0)+ifnull(rgnum,0) as gcd from c_hd_hduanjcxx a\n" +
                "LEFT JOIN c_hd_hdaojcxx b\n" +
                "on a.sshdid=b.id\n" +
                "LEFT JOIN c_zd_appattribute c\n" +
                "on a.hddj=c.id\n" +
                "LEFT JOIN c_zd_xzqhdm d \n" +
                "on a.hdszxzqh=d.id\n" +
                "LEFT JOIN \n" +
                "(select sshduanid,ifnull(count(*),0) as hbnum from c_hd_hb GROUP BY SSHDUANID) e \n" +
                "ON a.id=e.sshduanid\n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as qlnum from c_hd_ql group by sshduanid) f\n" +
                "ON a.id=f.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as lxnum from c_hd_lx group by sshduanid) g\n" +
                "ON a.id=g.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as kynum from c_hd_kymt group by sshduanid) h\n" +
                "ON a.id=h.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as hynum from c_hd_hymt group by sshduanid) i\n" +
                "ON a.id=i.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as gwnum from c_hd_gwmt group by sshduanid) j\n" +
                "ON a.id=j.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as mbqnum from c_hd_mbq group by sshduanid) k\n" +
                "ON a.id=k.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as fwqnum from c_hd_fwq group by sshduanid) l\n" +
                "ON a.id=l.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as snnum from c_hd_sn group by sshduanid) m\n" +
                "ON a.id=m.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as jgnum from c_hd_jgllgcd group by sshduanid) n\n" +
                "ON a.id=n.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as spnum from c_hd_spgcd group by sshduanid) o\n" +
                "ON a.id=o.sshduanid \n" +
                "LEFT JOIN\n" +
                "(select sshduanid,ifnull(count(*),0) as rgnum from c_hd_rggcd group by sshduanid) p\n" +
                "ON a.id=p.sshduanid \n" +
                "where 1=1";
        if (sshdid != -1 && sshdid > 0) {
            sql += " and a.sshdid=" + sshdid;
        }
        if (xzqhs != null && xzqhs.size() > 0) {
            sql += " and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        if (hddj != -1 && hddj > 0) {
            sql += " and a.hddj=" + hddj;
        }
        if (content != null && !"".equals(content)) {
            sql += " and (a.hdqdmc like '%" + content + "%' or a.hdzdmc like '%" + content + "%')";
        }
        Query query = session.createSQLQuery(
                new SQL(sql).toString());
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List list = query.list();
        session.flush();
        session.close();
        return list;
    }

    @Override
    public BaseQueryRecords<CHdHduanjcxx> queryHangDuanByXzqhSshdid(String xzqh, int sshdid) {
        String hql = "from CHdHduanjcxx where 1=1 ";
        if (xzqh != null && !"".equals(xzqh)) {
            hql += " and hdszxzqh in(" + xzqh + ")\n";
        }
        if (sshdid != -1 && sshdid > 0) {
            hql += " and sshdid =" + sshdid + "\n";
        }
        return (BaseQueryRecords<CHdHduanjcxx>) super.find(new HQL(hql));
    }

    @Override
    public List<Object[]> importHduan() {
        return (List<Object[]>) super.find(new SQL("select * from temphduan order by hdaobh,hduanbh")).getData();
    }

    @Override
    public CHdHduanjcxx queryHangDuanByQdzdSshdid(Integer id, String qd, String zd) {
        String hql = "from CHdHduanjcxx where sshdid=? and hdqdmc='?' and hdzdmc='?'";
        return (CHdHduanjcxx) super
                .findUnique(new HQL(hql, id, qd, zd));
    }

    @Override
    public CHdHduanjcxx queryHangDuanById(int id) {
        // TODO Auto-generated method stub
        return (CHdHduanjcxx) super.findUnique(new CHdHduanjcxx(), "id", id);

    }

    @Override
    public void delHangDuan(CHdHduanjcxx hangduan) {
        String hql = "delete from CHdHduanjcxx where id=?";
        super.delete(new HQL(hql, hangduan.getId()));
    }

    @Override
    public CHdHduanjcxx queryHangDuanByHdid(Integer hdid) {
        // TODO Auto-generated method stub
        return (CHdHduanjcxx) super
                .findUnique(new CHdHduanjcxx(), "id", hdid);
    }

    @Override
    public void addHangDuan(CHdHduanjcxx hangduan) {
        // TODO Auto-generated method stub
        super.save(hangduan);
    }

    @Override
    public void updateHangDuan(CHdHduanjcxx hangduan) {
        // TODO Auto-generated method stub
        super.update(hangduan);
    }

    @Override
    public List<CHdHduanjcxx> queryHDuanInXzqh(String xzqhs) {
        // TODO Auto-generated method stub
        String hql = "from CHdHduanjcxx where hdszxzqh in(?)";
        return (List<CHdHduanjcxx>) this.find(new HQL(hql, xzqhs)).getData();
    }



}
