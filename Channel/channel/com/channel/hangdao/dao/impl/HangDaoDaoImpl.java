package com.channel.hangdao.dao.impl;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.channel.bean.HdaoBean;
import com.common.dao.impl.SQL;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.channel.hangdao.dao.HangDaoDao;
import com.channel.model.hd.CHdHdaojcxx;
import com.channel.model.user.CZdXzqhdm;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;

@Repository("hangdaodao")
public class HangDaoDaoImpl extends BaseDaoDB implements HangDaoDao {

    @Override
    public BaseQueryRecords<CHdHdaojcxx> searchAllHangDao(int sfgg, List<CZdXzqhdm> xzqhs, String content) {
        String hql = null;
        if (xzqhs != null && xzqhs.size() > 0) {
            hql = "select distinct a,count(b) from CHdHdaojcxx a,CHdHduanjcxx b where a.id=b.sshdid and b.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ") ";
            if (content != null && !"".equals(content)) {
                hql += " and (a.hdmc like '%" + content + "%' or a.hdbh like '%" + content + "%')";
            }
            if (sfgg != -1)
                hql += " and a.sfgg = " + sfgg;
        } else {
            hql = "select distinct a,count(b) from CHdHdaojcxx a,CHdHduanjcxx b where a.id=b.sshdid ";
            if (sfgg != -1)
                hql += "and a.sfgg = " + sfgg;
            if (content != null && !"".equals(content)) {
                hql += " and (a.hdmc like '%" + content + "%' or a.hdbh like '%" + content + "%')";
            }
        }
        hql += " group by a.id";
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super.find(new HQL(hql));
        List<CHdHdaojcxx> hdaos = new ArrayList();
        for (Object[] objs : ret.getData()) {
            CHdHdaojcxx hdao = (CHdHdaojcxx) objs[0];
            hdao.setHduancnt(((Long) objs[1]).intValue());
            hdaos.add(hdao);
        }
        return new BaseQueryRecords<CHdHdaojcxx>(hdaos);
    }

    @Override
    public BaseQueryRecords<CHdHdaojcxx> searchHangDaoSjbh(int sfgg, int sjbh, String content) {
        String hql = null;
        hql = "select distinct a,count(b),c.attrdesc from CHdHdaojcxx a,CHdHduanjcxx b,CZdAppattribute c where a.id=b.sshdid and a.sssjbh=c.id";
        if (sfgg != -1) {
            hql += " and a.sfgg = " + sfgg;
        }
        if (sjbh != -1) {
            hql += " and a.sssjbh = " + sjbh;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.hdmc like '%" + content + "%' or a.hdbh like '%" + content + "%')";
        }
        hql += " group by a.id";
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super.find(new HQL(hql));
        List<CHdHdaojcxx> hdaos = new ArrayList();
        for (Object[] objs : ret.getData()) {
            CHdHdaojcxx hdao = (CHdHdaojcxx) objs[0];
            hdao.setHduancnt(((Long) objs[1]).intValue());
            hdao.setHduansjbh(String.valueOf(objs[2]));
            hdaos.add(hdao);
        }
        return new BaseQueryRecords<CHdHdaojcxx>(hdaos);
    }

    @Override
    public BaseQueryRecords<CHdHdaojcxx> searchHangDaoHddj(int sfgg, List<CZdXzqhdm> xzqhs, int hdid, int hddj, String content) {
        String hql = null;
        hql = "select distinct a,count(b),c.attrdesc from CHdHdaojcxx a,CHdHduanjcxx b,CZdAppattribute c where a.id=b.sshdid and a.sssjbh=c.id";
        if (sfgg != -1) {
            hql += " and a.sfgg = " + sfgg;
        }
        if (xzqhs != null && xzqhs.size() > 0) {
            hql += " and b.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ") ";
        }
        if (hdid != -1) {
            hql += " and a.id = " + hdid;
        }
        if (hddj != -1) {
            hql += " and b.hddj = " + hddj;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (b.hdqdmc like '%" + content + "%' or b.hdzdmc like '%" + content + "%')";
        }
        hql += " group by a.id";
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super.find(new HQL(hql));
        List<CHdHdaojcxx> hdaos = new ArrayList();
        for (Object[] objs : ret.getData()) {
            CHdHdaojcxx hdao = (CHdHdaojcxx) objs[0];
            hdao.setHduancnt(((Long) objs[1]).intValue());
            hdao.setHduansjbh(String.valueOf(objs[2]));
            hdaos.add(hdao);
        }
        return new BaseQueryRecords<CHdHdaojcxx>(hdaos);
    }


    @Override
    public BaseQueryRecords<CHdHdaojcxx> queryHangDaoZero(int sfgg, String name) {
        String hql = "";
        if (name != null && !"".equals(name)) {
            hql = "select a,0 from CHdHdaojcxx a,CZdAppattribute b  where a.sssjbh=b.id and a.id not in(select distinct c.sshdid from CHdHduanjcxx c) and b.attrdesc='" + name + "'";
            if (sfgg != -1)
                hql += " and a.sfgg = " + sfgg;
        } else {
            hql = "select a,0 from CHdHdaojcxx a where a.id not in(select distinct c.sshdid from CHdHduanjcxx c)";
            if (sfgg != -1)
                hql += " and a.sfgg = " + sfgg;
        }
        hql += " order by a.id";
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super.find(new HQL(hql));
        List<CHdHdaojcxx> hdaos = new ArrayList();
        for (Object[] objs : ret.getData()) {
            CHdHdaojcxx hdao = (CHdHdaojcxx) objs[0];
            hdao.setHduancnt(0);
            hdaos.add(hdao);
        }
        return new BaseQueryRecords<CHdHdaojcxx>(hdaos);

    }

    @Override
    public CHdHdaojcxx queryHangDaoByHdBh(String hdbh) {
        return (CHdHdaojcxx) super.findUnique(new CHdHdaojcxx(), "hdbh", hdbh);
    }

    @Override
    public BaseQueryRecords zhcxHdao(int sfgg, int sjbh, String content, String xzqh) {
        String sql = null;
        sql = "select a.id,a.hdbh,a.hdmc,round(ifnull(b.lc,0),2),round(ifnull(c.cflc,0),2),ifnull(l.hdnum,0),round(ifnull(d.onelc,0),2),round(ifnull(e.twolc,0),2),round(ifnull(f.threelc,0),2),round(ifnull(g.fourlc,0),2),round(ifnull(h.fivelc,0),2),round(ifnull(i.sixlc,0),2),round(ifnull(j.sevenlc,0),2),round(ifnull(k.pluslc,0),2) from \n" +
                "(select id,hdbh,hdmc from c_hd_hdaojcxx where 1=1 ";
        if (sfgg != -1) {
            sql += " and sfgg=" + sfgg;
        }
        if (sjbh != -1) {
            sql += " and sssjbh=" + sjbh;
        }
        if (content != null && !"".equals(content)) {
            sql += " and (hdmc like '%" + content + "%' or hdbh like '%" + content + "%')";
        }
        sql += ") a \n" +
                "LEFT JOIN\n" +
                "(select SSHDID,SUM(hdlc) as  lc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) \n" +
                " GROUP BY SSHDID) b \n" +
                "on a.id=b.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(cflc) as  cflc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) \n" +
                "GROUP BY SSHDID) c\n" +
                "on a.id=c.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  onelc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=92\n" +
                "GROUP BY SSHDID) d\n" +
                "on a.id=d.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  twolc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=93 GROUP BY SSHDID) e\n" +
                "on a.id=e.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  threelc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=94 GROUP BY SSHDID) f\n" +
                "on a.id=f.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  fourlc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=95 GROUP BY SSHDID) g\n" +
                "on a.id=g.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  fivelc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=96 GROUP BY SSHDID) h\n" +
                "on a.id=h.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  sixlc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=97 GROUP BY SSHDID) i\n" +
                "on a.id=i.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  sevenlc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=98 GROUP BY SSHDID) j\n" +
                "on a.id=j.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, SUM(hdlc) as  pluslc from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) and hddj=99 GROUP BY SSHDID) k\n" +
                "on a.id=k.SSHDID\n" +
                "LEFT JOIN\n" +
                "(select SSHDID, count(*) as  hdnum from c_hd_hduanjcxx\n" +
                "where HDSZXZQH IN(?) GROUP BY SSHDID) l\n" +
                "on a.id=l.SSHDID";
        return super.find(new SQL(sql, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh));
    }

    @Override
    public BaseQueryRecords zhcxHdao(String xzqh) {
        if (!"".equals(xzqh)) {
            String sql = "select ifnull(COUNT(DISTINCT a.sshdid),0),ifnull(SUM(a.hdlc),0)-ifnull(SUM(a.cflc),0) from c_hd_hduanjcxx a right join c_hd_hdaojcxx b\n" +
                    "on a.sshdid=b.id and b.sfgg=1 and a.HDSZXZQH in(?) \n" +
                    "UNION ALL\n" +
                    "select ifnull(COUNT(DISTINCT a.sshdid),0),ifnull(SUM(a.hdlc),0)-ifnull(SUM(a.cflc),0) from c_hd_hduanjcxx a right join c_hd_hdaojcxx b\n" +
                    "on a.sshdid=b.id  and b.sfgg=0 and a.HDSZXZQH in(?)\n" +
                    "UNION ALL\n" +
                    "select ifnull(COUNT(DISTINCT sshdid),0),ifnull(SUM(hdlc),0)-ifnull(SUM(cflc),0) from c_hd_hduanjcxx \n" +
                    "where HDSZXZQH in(?)";
            return super.find(new SQL(sql, xzqh, xzqh, xzqh));
        } else {
            return null;
        }
    }

    @Override
    public BaseQueryRecords zhcxHdaoInfo(String xzqh, int sfgg, String content, int page, int rows) {
        String sql = "";
        boolean addsfgg = sfgg == -1 ? false : true;
        boolean addcontent = "".equals(content) ? false : true;
        if (!"".equals(xzqh)) {
            sql = "select aa.ID,aa.HDBH,aa.HDMC,round(ifnull(aa.hdlc,0),2),round(ifnull(aa.cflc,0),2),round(ifnull(aa.hdsl,0),2),ifnull(bb.onedj,0),round(ifnull(bb.onelc,0),2),ifnull(cc.twodj,0),round(ifnull(cc.twolc,0),2),ifnull(dd.threedj,0),round(ifnull(dd.threelc,0),2),ifnull(ee.fourdj,0),round(ifnull(ee.fourlc,0),2),ifnull(ff.fivedj,0),round(ifnull(ff.fivelc,0),2),ifnull(gg.sixdj,0),round(ifnull(gg.sixlc,0),2),ifnull(hh.sevendj,0),round(ifnull(hh.sevenlc,0),2),ifnull(ii.plusdj,0),round(ifnull(ii.pluslc,0),2) from \n" +
                    "(select a.ID,a.HDBH,a.HDMC,SUM(b.HDLC) as hdlc,SUM(b.CFLC) as cflc,count(b.id) as hdsl from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                    "on a.id=b.SSHDID \n" +
                    "where b.HDSZXZQH in(" + xzqh + ") \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql += "GROUP BY a.ID) aa \n" +
                    "LEFT JOIN \n" +
                    "(select a.ID,count(b.id) as onedj,sum(b.hdlc) as onelc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                    "on a.id=b.SSHDID \n" +
                    "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=92 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql += "GROUP BY a.ID) bb\n" +
                    "on aa.id=bb.id\n" +
                    "LEFT JOIN\n" +
                    "(select a.ID,count(b.id) as twodj,sum(b.hdlc) as twolc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                    "on a.id=b.SSHDID \n" +
                    "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=93 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) cc\n" +
                            "on aa.id=cc.id\n" +
                            "LEFT JOIN\n" +
                            "(select a.ID,count(b.id) as threedj,sum(b.hdlc) as threelc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                            "on a.id=b.SSHDID \n" +
                            "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=94 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) dd\n" +
                            "on aa.id=dd.id\n" +
                            "LEFT JOIN\n" +
                            "(select a.ID,count(b.id) as fourdj,sum(b.hdlc) as fourlc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                            "on a.id=b.SSHDID \n" +
                            "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=95 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) ee\n" +
                            "on aa.id=ee.id\n" +
                            "LEFT JOIN\n" +
                            "(select a.ID,count(b.id) as fivedj,sum(b.hdlc) as fivelc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                            "on a.id=b.SSHDID \n" +
                            "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=96 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) ff\n" +
                            "on aa.id=ff.id\n" +
                            "LEFT JOIN\n" +
                            "(select a.ID,count(b.id) as sixdj,sum(b.hdlc) as sixlc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                            "on a.id=b.SSHDID \n" +
                            "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=97 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) gg\n" +
                            "on aa.id=gg.id\n" +
                            "LEFT JOIN\n" +
                            "(select a.ID,count(b.id) as sevendj,sum(b.hdlc) as sevenlc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                            "on a.id=b.SSHDID \n" +
                            "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=98 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) hh\n" +
                            "on aa.id=hh.id\n" +
                            "LEFT JOIN\n" +
                            "(select a.ID,count(b.id) as plusdj,sum(b.hdlc) as pluslc from c_hd_hdaojcxx a LEFT JOIN c_hd_hduanjcxx b\n" +
                            "on a.id=b.SSHDID \n" +
                            "where b.HDSZXZQH in(" + xzqh + ") and b.HDDJ=99 \n";
            if (addsfgg) {
                sql += "and a.SFGG=" + sfgg + "\n";
            }
            if (addcontent) {
                sql += "and a.hdmc like '%" + content + "%'\n";
            }
            sql +=
                    "GROUP BY a.ID) ii\n" +
                            "on aa.id=ii.id\n";

            return super.find(new SQL(sql), page, rows);
        } else {
            return null;
        }
    }

    @Override
    public List<Object[]> importHdao() {
        return (List<Object[]>) super.find(new SQL("select * from temphdao where tmpxzqh like '3302%' order by tmphddm")).getData();
    }

    @Override
    public List<CHdHdaojcxx> queryHdidInXzqh(String xzqhs) {
        // TODO Auto-generated method stub
        String hql = "select a from CHdHdaojcxx a,CHdHduanjcxx b where b.sshdid=a.id and a.sfgg=1 and b.hdszxzqh in(?) group by a.id";
        return (List<CHdHdaojcxx>) this.find(new HQL(hql, xzqhs)).getData();
    }

    @Override
    public BaseQueryRecords<CHdHdaojcxx> queryAllHangDao(int sfgg, List<CZdXzqhdm> xzqhs) {
        String hql = null;

        if (xzqhs != null && xzqhs.size() > 0) {
            hql = "select distinct b, count(a) from CHdHduanjcxx a, CHdHdaojcxx b where a.sshdid = b.id and a.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ") ";
            if (sfgg != -1)
                hql += " and b.sfgg = " + sfgg;
            hql += " group by b.id order by b.createtime";

            BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super.find(new HQL(hql));
            List<CHdHdaojcxx> hdaos = new ArrayList();
            for (Object[] objs : ret.getData()) {
                CHdHdaojcxx hdao = (CHdHdaojcxx) objs[0];
                hdao.setHduancnt(((Long) objs[1]).intValue());
                hdaos.add(hdao);
            }
            return new BaseQueryRecords<CHdHdaojcxx>(hdaos);
        } else {
            hql = "select a from CHdHdaojcxx a ";
            if (sfgg != -1)
                hql += "where a.sfgg = " + sfgg;
            hql += " group by a.id order by a.createtime";
            return (BaseQueryRecords<CHdHdaojcxx>) super.find(new HQL(hql));
        }
    }

    @Override
    public BaseQueryRecords<CHdHdaojcxx> queryHangDaoInXzqh(int sfgg, String xzqhs) {
        String hql = null;
        if (xzqhs != null && !"".equals(xzqhs)) {
            hql = "select distinct b, count(a) from CHdHduanjcxx a, CHdHdaojcxx b where a.sshdid = b.id and a.hdszxzqh in(" + xzqhs + ")";
            if (sfgg != -1)
                hql += " and b.sfgg = " + sfgg;
            hql += " group by b.id order by b.createtime";
            BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super.find(new HQL(hql));
            List<CHdHdaojcxx> hdaos = new ArrayList();
            for (Object[] objs : ret.getData()) {
                CHdHdaojcxx hdao = (CHdHdaojcxx) objs[0];
                hdao.setHduancnt(((Long) objs[1]).intValue());
                hdaos.add(hdao);
            }
            return new BaseQueryRecords<CHdHdaojcxx>(hdaos);
        } else {
            hql = "select a from CHdHdaojcxx a ";
            if (sfgg != -1)
                hql += "where a.sfgg = " + sfgg;
            hql += " group by a.id order by a.createtime";
            return (BaseQueryRecords<CHdHdaojcxx>) super.find(new HQL(hql));
        }
    }

    @Override
    public void addHangDao(CHdHdaojcxx hdao) {
        super.save(hdao);
    }

    @Override
    public CHdHdaojcxx queryHangDaoByHdid(Integer hdid) {
        return (CHdHdaojcxx) super.findUnique(new CHdHdaojcxx(), "id", hdid);
    }

    @Override
    public CHdHdaojcxx queryHangDaoById(int id) {
        return (CHdHdaojcxx) super.findUnique(new CHdHdaojcxx(), "id", id);
    }

    @Override
    public void delHangDao(CHdHdaojcxx hangdao) {
        super.delete(hangdao);
    }

    @Override
    public void updateHangDao(CHdHdaojcxx hdao) {
        super.update(hdao);
    }

    @Override
    public BaseQueryRecords queryHangDaoHduanCnt(List<CHdHdaojcxx> hdaolist) {
        String sql = "select SSHDID,count(SSHDID) from c_hd_hduanjcxx where sshdid in(";

        for (int i = 0; i < hdaolist.size(); i++) {
            CHdHdaojcxx hdao = hdaolist.get(i);
            sql += "'" + hdao.getId() + "'";
            if (i != hdaolist.size() - 1)
                sql += ",";
        }
        sql += ") group by SSHDID";

        return super.find(new SQL(sql));
    }


}
