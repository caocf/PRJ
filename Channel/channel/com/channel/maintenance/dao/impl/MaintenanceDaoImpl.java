package com.channel.maintenance.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.channel.model.user.CZdXzqhdm;
import com.channel.model.yh.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.channel.maintenance.dao.MaintenanceDao;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.utils.DateTimeUtil;

@Repository("maintenancedao")
public class MaintenanceDaoImpl extends BaseDaoDB implements MaintenanceDao {

    @Override
    public BaseQueryRecords queryMainById(int id) {
        // TODO Auto-generated method stub
        String hql = "from CYhGgjbxx j,CHdHdaojcxx h,CXtDpt u where j.dw=u.id and j.hdid=h.id and j.id=?";
        return super.find(new HQL(hql, id));
    }

    @Override
    public BaseQueryRecords queryMaintenances(String dwstr, String hdid, String strstarttime,
                                              String strendtime, int page, int rows) {
        String hql = "select g,h.hdmc,u.name from CYhGgjbxx g,CHdHdaojcxx h,CXtDpt u where g.hdid=h.id and g.dw=u.id";
        if (dwstr != null && !dwstr.equals("")) {
            hql += " and g.dw in (" + dwstr + ")";
        }
        if (hdid != null && !"".equals(hdid)) {
            hql += " and g.hdid in(" + hdid + ")";
        }
        if (strstarttime != null && !"".equals(strstarttime)) {
            hql += " and g.starttime >= '" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            hql += " and g.starttime < '" + strendtime + "'";
        }
        /*
         * if (content != null && !"".equals(content)) { hql +=
		 * " and g.dw like '%" + content + "%'"; }
		 */
        hql += " order by g.starttime desc";
        return super.find(new HQL(hql), page, rows);
    }

    @Override
    public void delMaintenance(CYhGgjbxx jbxx) {
        super.delete(jbxx);
    }

    @Override
    public CYhGgjbxx queryJbxxById(int jbxxid) {
        return (CYhGgjbxx) super.findUnique(new CYhGgjbxx(), "id", jbxxid);
    }

    @Override
    public BaseQueryRecords queryZxgcById(int zxgcid) {
        // TODO Auto-generated method stub
        String hql = "select z,a.attrdesc from CYhZxgc z,CZdAppattribute a where z.status=a.id and z.zxgcid=?";
        return super.find(new HQL(hql, zxgcid));
    }

    @Override
    public BaseQueryRecords queryZxgcs(String gldwstr, String strstarttime,
                                       String strendtime, String content, int page, int rows) {
        // TODO Auto-generated method stub
        String hql = "select z,a.attrdesc, d.name from CYhZxgc z,CZdAppattribute a, CXtDpt d where z.status=a.id and z.gldw = d.id";
        if (gldwstr != null && gldwstr.length() > 0) {
            hql += " and z.gldw in(" + gldwstr + ")";
        }
        if (strstarttime != null && !"".equals(strstarttime)) {
            hql += " and z.createtime >= '" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            hql += " and z.createtime <= '" + strendtime + "'";
        }
        if (content != null && !"".equals(content)) {
            hql += " and z.gcmc like '%" + content + "%'";
        }
        hql += " order by z.updatetime desc";
        return super.find(new HQL(hql), page, rows);
    }

    @Override
    public void delYbb(Integer zxgcid) {
        // TODO Auto-generated method stub
        String hql = "delete from CYhYdjdqk where CYhZxgc.zxgcid=?";
        super.delete(new HQL(hql, zxgcid));
    }

    @Override
    public void delFj(Integer zxgcid) {
        // TODO Auto-generated method stub
        String hql = "delete from CYhFj where CYhZxgc.zxgcid=?";
        super.delete(new HQL(hql, zxgcid));
    }

    @Override
    public void delZxgc(CYhZxgc zxgc) {
        // TODO Auto-generated method stub
        super.delete(zxgc);
    }

    @Override
    public CYhZxgc queryZxgcJbxxById(Integer zxgcid) {
        // TODO Auto-generated method stub
        return (CYhZxgc) super.findUnique(new CYhZxgc(), "zxgcid", zxgcid);
    }

    @Override
    public void addZxgc(CYhZxgc zxgc) {
        // TODO Auto-generated method stub
        super.save(zxgc);
    }

    @Override
    public void addCYhFj(CYhFj fj) {
        // TODO Auto-generated method stub
        super.save(fj);
    }

    @Override
    public void addCYhYbb(CYhYdjdqk ybb) {
        // TODO Auto-generated method stub
        super.save(ybb);
    }

    @Override
    public BaseQueryRecords queryBranchById(int id) {
        // TODO Auto-generated method stub
        String hql = "from CYhZxjbxx z,CXtUser u,CXtDpt d where z.creater=u.id and z.dw=d.id and z.id=?";
        return super.find(new HQL(hql, id));
    }

    @Override
    public BaseQueryRecords queryBranches(String dwstr, String strstarttime,
                                          String strendtime, int page, int rows) {
        // TODO Auto-generated method stub
        String hql = "from CYhZxjbxx z,CXtUser u,CXtDpt d where z.creater=u.id and z.dw=d.id";
        if (strstarttime != null && !"".equals(strstarttime)) {
            hql += " and z.starttime >= '" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            hql += " and z.starttime < '" + strendtime + "'";
        }
        if (dwstr != null && dwstr.length() > 0) {
            hql += " and z.dw in(" + dwstr + ")";
        }
        hql += " order by z.starttime desc";
        return super.find(new HQL(hql), page, rows);
    }

    @Override
    public CYhZxjbxx queryZxjbxxById(Integer zxjbxxid) {
        // TODO Auto-generated method stub
        return (CYhZxjbxx) super.findUnique(new CYhZxjbxx(), "id", zxjbxxid);
    }

    @Override
    public void delBranch(CYhZxjbxx zxjbxx) {
        // TODO Auto-generated method stub
        super.delete(zxjbxx);
    }

    @Override
    public BaseQueryRecords queryAllJsdw() {
        // TODO Auto-generated method stub
        String hql = "select distinct jsdw from CYhZxgc";
        return super.find(new HQL(hql));
    }

    @Override
    public void updateZxgc(CYhZxgc zxgc) {
        // TODO Auto-generated method stub
        super.update(zxgc);
    }

    @Override
    public CYhYdjdqk queryYbbByid(Integer ydjdqkid) {
        // TODO Auto-generated method stub
        return (CYhYdjdqk) super.findUnique(new CYhYdjdqk(), "ydjdqkid",
                ydjdqkid);
    }

    @Override
    public void delYbb(CYhYdjdqk ybb) {
        // TODO Auto-generated method stub
        super.delete(ybb);
    }

    @Override
    public CYhFj queryFjByid(Integer fjid) {
        // TODO Auto-generated method stub
        return (CYhFj) super.findUnique(new CYhFj(), "fjid", fjid);
    }

    @Override
    public void delCYhFj(CYhFj cyhfj) {
        // TODO Auto-generated method stub
        super.delete(cyhfj);
    }

    @Override
    public void delCYhYbb(CYhYdjdqk ybb) {
        // TODO Auto-generated method stub
        super.delete(ybb);
    }

    @Override
    public void updateBranch(CYhZxjbxx zxjbxx) {
        // TODO Auto-generated method stub
        super.update(zxjbxx);
    }

    @Override
    public void addBranch(CYhZxjbxx zxjbxx) {
        // TODO Auto-generated method stub
        super.save(zxjbxx);
    }

    @Override
    public void addMaintenance(CYhGgjbxx jbxx) {
        // TODO Auto-generated method stub
        super.save(jbxx);
    }

    @Override
    public void updateMaintenance(CYhGgjbxx jbxx) {
        // TODO Auto-generated method stub
        super.update(jbxx);
    }

    @Override
    public CYhFj queryFjByMd5(int zxgcid, String infileMd5) {
        // TODO Auto-generated method stub
        String hql = "from CYhFj where CYhZxgc.zxgcid=? and filemd5='?'";
        return (CYhFj) super.findUnique(new HQL(hql, zxgcid, infileMd5));
    }

    @Override
    public void updateCyhybb(CYhYdjdqk ybb) {
        // TODO Auto-generated method stub
        super.update(ybb);
    }

    @Override
    public BaseQueryRecords<CYhFj> queryFjByZxgcid(int zxgcid) {
        // TODO Auto-generated method stub
        String hql = "from CYhFj where CYhZxgc.zxgcid=? order by createtime desc";
        return (BaseQueryRecords<CYhFj>) super.find(new HQL(hql, zxgcid));
    }

    @Override
    public BaseQueryRecords<CYhYdjdqk> queryYbbByZxgcid(int zxgcid) {
        // TODO Auto-generated method stub
        String hql = "from CYhYdjdqk where CYhZxgc.zxgcid=? order by ny desc";
        return (BaseQueryRecords<CYhYdjdqk>) super.find(new HQL(hql, zxgcid));
    }

    @Override
    public CYhYdjdqk queryLastYdjdqk(int zxgcid) {
        // TODO Auto-generated method stub
        String hql = "from CYhYdjdqk where CYhZxgc.zxgcid=? order by ny desc";
        return (CYhYdjdqk) super.findUnique(new HQL(hql, zxgcid));
    }

    @Override
    public CYhYdjdqk queryLastYdjdqk(int zxgcid, String strendtime) {
        // TODO Auto-generated method stub
        String hql = "from CYhYdjdqk where CYhZxgc.zxgcid=? and ny<'?' order by ny desc";
        return (CYhYdjdqk) super.findUnique(new HQL(hql, zxgcid, strendtime));
    }

    @Override
    public BaseQueryRecords queryZxgcTable(String strstarttime,
                                           String strendtime) {
        // TODO Auto-generated method stub
        String hql = "select y from CYhZxgc z,CYhYdjdqk y where z.zxgcid=y.CYhZxgc.zxgcid ";
        if (strstarttime != null && !"".equals(strstarttime)) {
            hql += " and y.ny >= '" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            hql += " and y.ny < '" + strendtime + "'";
        }
        hql += " order by y.CYhZxgc.zxgcid desc";
        return super.find(new HQL(hql));
    }

    @Override
    public BaseQueryRecords queryZxgcTable(String gldw, String strstarttime, String strendtime) {
        // TODO Auto-generated method stub
        String hql = "select y from CYhZxgc z,CYhYdjdqk y where z.zxgcid=y.CYhZxgc.zxgcid ";
        if (strstarttime != null && !"".equals(strstarttime)) {
            hql += " and y.ny >= '" + strstarttime + "'";
        }
        if (strendtime != null && !"".equals(strendtime)) {
            hql += " and y.ny < '" + strendtime + "'";
        }
        if (gldw != null && !"".equals(gldw)) {
            hql += " and z.gldw in(" + gldw + ")";
        }
        hql += " order by y.CYhZxgc.zxgcid desc";
        return super.find(new HQL(hql));
    }

    @Override
    public Date queryLastesttime() {
        // TODO Auto-generated method stub
        List<Object> objects = (List<Object>) super.find(
                getCriteria(CYhYdjdqk.class).setProjection(
                        Projections.max("ny"))).getData();
        if (objects != null && objects.size() > 0) {
            return (Date) objects.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Date queryEarliesttime() {
        // TODO Auto-generated method stub
        List<Object> objects = (List<Object>) super.find(
                getCriteria(CYhYdjdqk.class).setProjection(
                        Projections.min("ny"))).getData();
        if (objects != null && objects.size() > 0) {
            return (Date) objects.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Date queryGgLastesttime() {
        // TODO Auto-generated method stub
        List<Object> objects = (List<Object>) super.find(
                getCriteria(CYhGgjbxx.class).setProjection(
                        Projections.max("starttime"))).getData();
        if (objects != null && objects.size() > 0) {
            return (Date) objects.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Date queryGgEarliesttime() {
        // TODO Auto-generated method stub
        List<Object> objects = (List<Object>) super.find(
                getCriteria(CYhGgjbxx.class).setProjection(
                        Projections.min("starttime"))).getData();
        if (objects != null && objects.size() > 0) {
            return (Date) objects.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Date queryZxEarliesttime() {
        // TODO Auto-generated method stub
        List<Object> objects = (List<Object>) super.find(
                getCriteria(CYhZxjbxx.class).setProjection(
                        Projections.min("starttime"))).getData();
        if (objects != null && objects.size() > 0) {
            return (Date) objects.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Date queryZxLastesttime() {
        // TODO Auto-generated method stub
        List<Object> objects = (List<Object>) super.find(
                getCriteria(CYhZxjbxx.class).setProjection(
                        Projections.max("starttime"))).getData();
        if (objects != null && objects.size() > 0) {
            return (Date) objects.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<CYhGgjbxx> queryGgTable(Integer[] department, Date starttime,
                                        Date endtime) {
        // TODO Auto-generated method stub
        return (List<CYhGgjbxx>) super.find(
                getCriteria(CYhGgjbxx.class)
                        .add(Restrictions.in("dw", department))
                        .add(Restrictions.ge("starttime", starttime))
                        .add(Restrictions.lt("starttime", endtime))).getData();
    }

    @Override
    public List<CYhZxjbxx> queryZxTable(Integer[] department, Date starttime, Date endtime) {
        // TODO Auto-generated method stub
        return (List<CYhZxjbxx>) super.find(
                getCriteria(CYhZxjbxx.class).add(Restrictions.in("dw", department)).
                        add(Restrictions.ge("starttime", starttime)).
                        add(Restrictions.lt("starttime", endtime))).getData();
    }

    @Override
    public CYhZxjbxx queryLastZx(Date endtime) {
        // TODO Auto-generated method stub
        CYhZxjbxx zxjbxx = new CYhZxjbxx();
        String strendtime = DateTimeUtil.getTimeFmt(endtime,
                "yyyy-MM-dd HH:mm:ss");
        String hql = "from CYhZxjbxx where starttime<'?' order by starttime desc";
        CYhZxjbxx tempzxjbxx = (CYhZxjbxx) super.findUnique(new HQL(hql, strendtime));
        if (tempzxjbxx != null) {
            return tempzxjbxx;
        } else {
            return zxjbxx;
        }
    }

    @Override
    public List<Object[]> querySsTable(List<CZdXzqhdm> xzqhdms) {
        // TODO Auto-generated method stub
        String sids = "";
        for (int i = 0; i < xzqhdms.size(); i++) {
            sids += xzqhdms.get(i).getId();
            if (i != xzqhdms.size() - 1)
                sids += ",";
        }
        Session session = getSessionFactory().openSession();
        String sql =
                /*0*/"select hdao.id as id," +
                /*1*/"hdao.HDMC as name," +
                /*2*/"round(SUM(hduan.HDLC),2) as mileage, " +
                /*3*/"ifnull(sum(qlsl),0) as qlnum," +
                /*4*/"ifnull(sum(lxsl),0) as lxnum," +
                /*5*/"ifnull(sum(hbsl),0) as hbnum," +
                /*6*/"ifnull(sum(kymtsl),0)+ifnull(sum(mbqsl),0)+ifnull(sum(fwqsl),0) as mtnum," +
                /*7*/"ifnull(sum(dcsl),0) as dcnum," +
                /*8*/"ifnull(sum(gdsl),0) as gdnum," +
                /*9*/"ifnull(sum(sdsl),0) as sdnum," +
                /*10*/"ifnull(sum(ccsl),0) as ccnum," +
                /*11*/"ifnull(sum(qpsksl),0) as qpsknum," +
                /*12*/"ifnull(sum(swzsl),0) as swznum," +
                /*13*/"ifnull(sum(glzsl),0) as glznum," +
                /*14*/"ifnull(sum(snsl),0) as snnum," +
                /*15*/"ifnull(sum(bsl),0) as bnum," +
                /*16*/"ifnull(sum(jgllgcdsl),0) as jgllgcdnum," +
                /*17*/"ifnull(sum(rggcdsl),0) as rggcdnum," +
                /*18*/"ifnull(sum(spgcdsl),0) as spgcdnum," +
                /*19*/"ifnull(sum(xlzsl),0) as xlznum," +
                /*20*/"ifnull(sum(zzhasl),0) as zzhanum ," +
                /*21*/"NOW() as CREATETIME "
                + "from c_hd_hduanjcxx hduan "
                + "left join (select sshduanid,count(*) as qlsl from c_hd_ql group by sshduanid) ql on hduan.id=ql.sshduanid "
                + "left join (select sshduanid,count(*) as lxsl from c_hd_lx group by sshduanid) lx on hduan.id=lx.sshduanid "
                + "left join (select sshduanid,count(*) as hbsl from c_hd_hb group by sshduanid) hb on hduan.id=hb.sshduanid "
                + "left join (select sshduanid,count(*) as kymtsl from c_hd_kymt group by sshduanid) kymt on hduan.id=kymt.sshduanid "
                + "left join (select sshduanid,count(*) as hymtsl from c_hd_hymt group by sshduanid) hymt on hduan.id=hymt.sshduanid "
                + "left join (select sshduanid,count(*) as gwmtsl from c_hd_gwmt group by sshduanid) gwmt on hduan.id=gwmt.sshduanid "
                + "left join (select sshduanid,count(*) as mbqsl from c_hd_mbq group by sshduanid) mbq on hduan.id=mbq.sshduanid "
                + "left join (select sshduanid,count(*) as fwqsl from c_hd_fwq group by sshduanid) fwq on hduan.id=fwq.sshduanid "
                + "left join (select sshduanid,count(*) as dcsl from c_hd_dc group by sshduanid) dc on hduan.id=dc.sshduanid "
                + "left join (select sshduanid,count(*) as gdsl from c_hd_gd group by sshduanid) gd on hduan.id=gd.sshduanid "
                + "left join (select sshduanid,count(*) as sdsl from c_hd_sd group by sshduanid) sd on hduan.id=sd.sshduanid "
                + "left join (select sshduanid,count(*) as ccsl from c_hd_cc group by sshduanid) cc on hduan.id=cc.sshduanid "
                + "left join (select sshduanid,count(*) as qpsksl from c_hd_qpsk group by sshduanid) qpsk on hduan.id=qpsk.sshduanid "
                + "left join (select sshduanid,count(*) as swzsl from c_hd_swz group by sshduanid) swz on hduan.id=swz.sshduanid "
                + "left join (select sshduanid,count(*) as glzsl from c_hd_glz group by sshduanid) glz on hduan.id=glz.sshduanid "
                + "left join (select sshduanid,count(*) as snsl from c_hd_sn group by sshduanid) sn on hduan.id=sn.sshduanid "
                + "left join (select sshduanid,count(*) as bsl from c_hd_b group by sshduanid) b on hduan.id=b.sshduanid "
                + "left join (select sshduanid,count(*) as jgllgcdsl from c_hd_jgllgcd group by sshduanid) jgllgcd on hduan.id=jgllgcd.sshduanid "
                + "left join (select sshduanid,count(*) as rggcdsl from c_hd_rggcd group by sshduanid) rggcd on hduan.id=rggcd.sshduanid "
                + "left join (select sshduanid,count(*) as spgcdsl from c_hd_spgcd group by sshduanid) spgcd on hduan.id=spgcd.sshduanid "
                + "left join (select sshduanid,count(*) as xlzsl from c_hd_xlz group by sshduanid) xlz on hduan.id=xlz.sshduanid "
                + "left join (select sshduanid,count(*) as zzhasl from c_hd_zzha group by sshduanid) zzha on hduan.id=zzha.sshduanid "
                + "LEFT JOIN c_hd_hdaojcxx hdao on hduan.SSHDID=hdao.ID where 1=1 and hduan.HDSZXZQH in(?) GROUP BY hduan.SSHDID ";
        Query query = session.createSQLQuery(
                new SQL(sql, sids).toString());
        List list = query.list();
        session.flush();
        session.close();
        return list;
    }

    @Override
    public void addYjqtgc(CYhYjqtgc yjqtgc) {
        super.save(yjqtgc);
    }

    @Override
    public CYhYjqtgcfj queryYjqtgcfjByMd5(int pid, String infileMd5) {
        String hql = "from CYhYjqtgcfj where pid=? and fmd5='?'";
        return (CYhYjqtgcfj) super.findUnique(new HQL(hql, pid, infileMd5));
    }

    @Override
    public void addCYhYjqtgcfj(CYhYjqtgcfj fj) {
        super.save(fj);
    }

    @Override
    public BaseQueryRecords searchYjqtgc(String dw, Integer hdaoid, Integer hduanid, String starttime, String endtime, String content, int page, int rows) {
        String hql = "from CYhYjqtgc a,CHdHdaojcxx b,CHdHduanjcxx c,CXtDpt d where a.hdaoid=b.id and a.hduanid=c.id and a.mandept=d.id";
        if (dw != null && dw.length() > 0) {
            hql += " and a.mandept in(" + dw + ")";
        }
        if (hdaoid != null && hdaoid > 0) {
            hql += " and a.hdaoid = '" + hdaoid + "'";
        }
        if (hduanid != null && hduanid > 0) {
            hql += " and a.hduanid = '" + hduanid + "'";
        }
        if (starttime != null && !"".equals(starttime)) {
            hql += " and a.starttime >= '" + starttime + "'";
        }
        if (endtime != null && !"".equals(endtime)) {
            hql += " and a.starttime <= '" + endtime + "'";
        }
        if (content != null && !"".equals(content)) {
            hql += " and a.name like( '%" + content + "%')";
        }
        hql += " order by a.starttime desc";
        return super.find(new HQL(hql), page, rows);
    }

    @Override
    public void delYjqtgcInId(String id) {
        String hql = "delete from CYhYjqtgc  where id in(?)";
        super.delete(new HQL(hql, id));
    }

    @Override
    public void delYjqtgcfjInFid(String pid) {
        String hql = "delete from CYhYjqtgcfj  where pid in(?)";
        super.delete(new HQL(hql, pid));
    }

    @Override
    public BaseQueryRecords queryYjqtgcById(int id) {
        String hql = "from CYhYjqtgc a,CHdHdaojcxx b,CHdHduanjcxx c,CXtDpt d where a.hdaoid=b.id and a.hduanid=c.id and a.mandept=d.id and a.id=?";
        return super.find(new HQL(hql, id));
    }

    @Override
    public BaseQueryRecords<CYhYjqtgcfj> queryYjqtgcfjByPid(int id) {
        return (BaseQueryRecords<CYhYjqtgcfj>) super.find(new CYhYjqtgcfj(), "pid", id);
    }

    @Override
    public CYhYjqtgcfj queryYjqtgcfjByid(int id) {
        return (CYhYjqtgcfj) super.findUnique(new CYhYjqtgcfj(), "id", id);
    }

    @Override
    public CYhYjqtgc queryYjqtgcByPk(int id) {
        return (CYhYjqtgc) super.findUnique(new CYhYjqtgc(), "id", id);
    }

    @Override
    public void updateYjqtgc(CYhYjqtgc gc) {
        super.save(gc);
    }

    @Override
    public void delYjqtgcfjInId(String id) {
        String hql = "delete from CYhYjqtgcfj  where id in(?)";
        super.delete(new HQL(hql, id));
    }

}
