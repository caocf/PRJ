package com.channel.appurtenance.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.channel.appurtenance.service.impl.AppurtenanceServiceImpl;
import com.channel.model.hd.*;
import com.channel.model.user.CZdXzqhdm;
import com.common.dao.impl.SQL;
import com.common.dao.impl.StringExpression;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.channel.appurtenance.dao.AppurtenanceDao;
import com.channel.bean.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;

@Repository("appurtenancedao")
public class AppurtenanceDaoImpl extends BaseDaoDB implements AppurtenanceDao {

    @Override
    public BaseQueryRecords searchAppurtenances(List<CZdXzqhdm> xzqhs, int sshdid, int sshduanid, int fswsecondclassid, String content) {
        String hql = "";
        String subsql = "(select " +
                "a.id as fswid," +
                "a.bh as fswbh," +
                "a.mc as fswmc," +
                "b.id as hdaoid," +
                "b.hdbh as hdaobh," +
                "b.hdmc as hdaomc," +
                "a.sshduanid as hduanid," +
                "c.pid as fswclass, " +
                "a.fswlx as fswsecondclass " +
                "from ? a,C_Hd_Hdaojcxx b, c_zd_appurtenance_rela c " +
                "where a.fswlx=c.sid and a.sshdaoid=b.id and (a.mc like '%" + content + "%' or (concat('?', a.bh)) like '%" + content + "%'))";

        if (fswsecondclassid > 0) {
            switch (fswsecondclassid) {
                case Constants.APP_NAVIGATIONMARK:
                    hql = new StringExpression(subsql, "C_Hd_Hb", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_NAVIGATIONMARK)).toString();
                    break;
                case Constants.APP_BRIDGE:
                    hql = new StringExpression(subsql, "C_Hd_Ql", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_BRIDGE)).toString();
                    break;
                case Constants.APP_AQUEDUCT:
                    hql = new StringExpression(subsql, "C_Hd_Dc", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_AQUEDUCT)).toString();
                    break;
                case Constants.APP_CABLE:
                    hql = new StringExpression(subsql, "C_Hd_Lx", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_CABLE)).toString();
                    break;
                case Constants.APP_PIPELINE:
                    hql = new StringExpression(subsql, "C_Hd_Gd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_PIPELINE)).toString();
                    break;
                case Constants.APP_TUNNEL:
                    hql = new StringExpression(subsql, "C_Hd_Sd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_TUNNEL)).toString();
                    break;
                case Constants.APP_KYDOCK:
                    hql = new StringExpression(subsql, "C_Hd_Kymt", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_KYDOCK)).toString();
                    break;
                case Constants.APP_HYDOCK:
                    hql = new StringExpression(subsql, "C_Hd_Hymt", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_HYDOCK)).toString();
                    break;
                case Constants.APP_GWDOCK:
                    hql = new StringExpression(subsql, "C_Hd_Gwmt", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_GWDOCK)).toString();
                    break;
                case Constants.APP_SHIPYARD:
                    hql = new StringExpression(subsql, "C_Hd_Cc", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_SHIPYARD)).toString();
                    break;
                case Constants.APP_TAKEOUTFALL:
                    hql = new StringExpression(subsql, "C_Hd_Qpsk", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_TAKEOUTFALL)).toString();
                    break;
                case Constants.APP_HYDROLOGICALSTATION:
                    hql = new StringExpression(subsql, "C_Hd_Swz", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_HYDROLOGICALSTATION)).toString();
                    break;
                case Constants.APP_MANAGEMENTSTATION:
                    hql = new StringExpression(subsql, "C_Hd_Glz", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_MANAGEMENTSTATION)).toString();
                    break;
                case Constants.APP_SERVICEAREA:
                    hql = new StringExpression(subsql, "C_Hd_Fwq", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_SERVICEAREA)).toString();
                    break;
                case Constants.APP_MOORINGAREA:
                    hql = new StringExpression(subsql, "C_Hd_Mbq", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_MOORINGAREA)).toString();
                    break;
                case Constants.APP_HUB:
                    hql = new StringExpression(subsql, "C_Hd_Sn", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_HUB)).toString();
                    break;
                case Constants.APP_DAM:
                    hql = new StringExpression(subsql, "C_Hd_B", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_DAM)).toString();
                    break;
                case Constants.APP_REGULATIONREVEMENT:
                    hql = new StringExpression(subsql, "C_Hd_Zzha", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_REGULATIONREVEMENT)).toString();
                    break;
                case Constants.APP_LASEROBSERVATION:
                    hql = new StringExpression(subsql, "C_Hd_Jgllgcd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_LASEROBSERVATION)).toString();
                    break;
                case Constants.APP_VIDEOOBSERVATION:
                    hql = new StringExpression(subsql, "C_Hd_Spgcd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_VIDEOOBSERVATION)).toString();
                    break;
                case Constants.APP_MANUALOBSERVATION:
                    hql = new StringExpression(subsql, "C_Hd_Rggcd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_MANUALOBSERVATION)).toString();
                    break;
                case Constants.APP_BOLLARD:
                    hql = new StringExpression(subsql, "C_Hd_Xlz", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_BOLLARD)).toString();
                    break;
                default:
                    break;
            }
        } else {
            hql += new StringExpression(subsql, "C_Hd_Hb", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_NAVIGATIONMARK)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Ql", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_BRIDGE)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Dc", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_AQUEDUCT)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Lx", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_CABLE)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Gd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_PIPELINE)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Sd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_TUNNEL)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Kymt", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_KYDOCK)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Hymt", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_HYDOCK)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Gwmt", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_GWDOCK)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Cc", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_SHIPYARD)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Qpsk", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_TAKEOUTFALL)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Swz", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_HYDROLOGICALSTATION)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Glz", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_MANAGEMENTSTATION)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Fwq", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_SERVICEAREA)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Mbq", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_MOORINGAREA)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Sn", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_HUB)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_B", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_DAM)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Zzha", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_REGULATIONREVEMENT)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Jgllgcd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_LASEROBSERVATION)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Spgcd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_VIDEOOBSERVATION)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Rggcd", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_MANUALOBSERVATION)).toString();
            hql += "UNION";
            hql += new StringExpression(subsql, "C_Hd_Xlz", AppurtenanceServiceImpl.getAppBHPre(Constants.APP_BOLLARD)).toString();
        }

        String sql = "select " +
                "a.*," +
                "c.hdbh as hduanbh," +
                "c.hdqdmc as hduanqdmc," +
                "c.hdzdmc as hduanzdmc from ( " + hql + " ) a, C_hd_hduanjcxx c where a.hduanid=c.id";

        if (xzqhs != null) {
            sql += " and c.hdszxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        if (sshdid > 0) {
            sql += " and c.sshdid=" + sshdid;
        }

        if (sshduanid > 0) {
            sql += " and c.id=" + sshduanid;
        }

        return super.find(new SQL(sql));
    }

    public long countEachApp(int sid, Integer sshduanid) {
        // TODO Auto-generated method stub
        String hql = "select count(a) from ? a where a.fswlx=? and a.sshduanid=?";
        String tablename = "";
        long cnt = -1;
        switch (sid) {
            case Constants.APP_NAVIGATIONMARK:
                tablename = "CHdHb";
                break;
            case Constants.APP_BRIDGE:
                tablename = "CHdQl";
                break;
            case Constants.APP_AQUEDUCT:
                tablename = "CHdDc";
                break;
            case Constants.APP_CABLE:
                tablename = "CHdLx";
                break;
            case Constants.APP_PIPELINE:
                tablename = "CHdGd";
                break;
            case Constants.APP_TUNNEL:
                tablename = "CHdSd";
                break;
            case Constants.APP_KYDOCK:
                tablename = "CHdKymt";
                break;
            case Constants.APP_HYDOCK:
                tablename = "CHdHymt";
                break;
            case Constants.APP_GWDOCK:
                tablename = "CHdGwmt";
                break;
            case Constants.APP_SHIPYARD:
                tablename = "CHdCc";
                break;
            case Constants.APP_TAKEOUTFALL:
                tablename = "CHdQpsk";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                tablename = "CHdSwz";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                tablename = "CHdGlz";
                break;
            case Constants.APP_SERVICEAREA:
                tablename = "CHdFwq";
                break;
            case Constants.APP_MOORINGAREA:
                tablename = "CHdMbq";
                break;
            case Constants.APP_HUB:
                tablename = "CHdSn";
                break;
            case Constants.APP_DAM:
                tablename = "CHdB";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                tablename = "CHdZzha";
                break;
            case Constants.APP_LASEROBSERVATION:
                tablename = "CHdJgllgcd";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                tablename = "CHdSpgcd";
                break;
            case Constants.APP_MANUALOBSERVATION:
                tablename = "CHdRggcd";
                break;
            case Constants.APP_BOLLARD:
                tablename = "CHdXlz";
                break;
            default:
                break;
        }
        cnt = super.count(new HQL(hql, tablename, sid, sshduanid));
        return cnt;
    }

    @Override
    public long queryAppCount(int sid, int hduanid, String content) {
        String hql = "select count(a) from ? a where a.fswlx=? ";
        if (hduanid != -1) {
            hql += " and a.sshduanid=" + hduanid;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.mc like '%" + content + "%' or a.bh like '%" + content + "%')";
        }
        String tablename = "";
        long cnt = 0;
        switch (sid) {
            case Constants.APP_NAVIGATIONMARK:
                tablename = "CHdHb";
                break;
            case Constants.APP_BRIDGE:
                tablename = "CHdQl";
                break;
            case Constants.APP_AQUEDUCT:
                tablename = "CHdDc";
                break;
            case Constants.APP_CABLE:
                tablename = "CHdLx";
                break;
            case Constants.APP_PIPELINE:
                tablename = "CHdGd";
                break;
            case Constants.APP_TUNNEL:
                tablename = "CHdSd";
                break;
            case Constants.APP_KYDOCK:
                tablename = "CHdKymt";
                break;
            case Constants.APP_HYDOCK:
                tablename = "CHdHymt";
                break;
            case Constants.APP_GWDOCK:
                tablename = "CHdGwmt";
                break;
            case Constants.APP_SHIPYARD:
                tablename = "CHdCc";
                break;
            case Constants.APP_TAKEOUTFALL:
                tablename = "CHdQpsk";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                tablename = "CHdSwz";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                tablename = "CHdGlz";
                break;
            case Constants.APP_SERVICEAREA:
                tablename = "CHdFwq";
                break;
            case Constants.APP_MOORINGAREA:
                tablename = "CHdMbq";
                break;
            case Constants.APP_HUB:
                tablename = "CHdSn";
                break;
            case Constants.APP_DAM:
                tablename = "CHdB";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                tablename = "CHdZzha";
                break;
            case Constants.APP_LASEROBSERVATION:
                tablename = "CHdJgllgcd";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                tablename = "CHdSpgcd";
                break;
            case Constants.APP_MANUALOBSERVATION:
                tablename = "CHdRggcd";
                break;
            case Constants.APP_BOLLARD:
                tablename = "CHdXlz";
                break;
            default:
                break;
        }
        cnt = super.count(new HQL(hql, tablename, sid));
        return cnt;
    }

    @Override
    public BaseQueryRecords<Object> queryEachApp(int sid, Integer sshduanid, int page, int rows) {
        BaseQueryRecords<Object> records = null;
        String hql = "";
        switch (sid) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb a,CZdAppattribute b,CZdAppattribute c,CZdAppattribute d,CZdAppattribute e ,CZdAppattribute f ,CZdAppattribute g where a.fswlx=? and a.sshduanid=? and a.lx=b.id and a.gxxz=c.id and a.dzxh=d.id and a.dbys=e.id and a.zcfs=f.id and a.jg=g.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl a,CZdAppattribute b,CZdAppattribute c where a.fswlx=? and a.sshduanid=? and a.jgxs=b.id and a.ytfl=c.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx a ,CZdAppattribute b where a.fswlx=? and a.sshduanid=? and a.zl=b.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt a,CZdAppattribute b where a.fswlx=? and a.sshduanid=? and a.jglx=b.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt a,CZdAppattribute b where a.fswlx=? and a.sshduanid=? and a.jglx=b.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt a,CZdAppattribute b where a.fswlx=? and a.sshduanid=? and a.jglx=b.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk a,CZdAppattribute b where a.fswlx=? and a.sshduanid=? and a.lx=b.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn a,CZdAppattribute b ,CZdAppattribute c ,CZdAppattribute d where a.fswlx=? and a.sshduanid=? and a.xs=b.id and a.thlx=c.id and a.gcsswz=d.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_DAM:
                hql = "from CHdB a,CZdAppattribute b where a.fswlx=? and a.sshduanid=? and a.lx=b.id order by a.updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz where fswlx=? and sshduanid=? order by updatetime desc";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                        sshduanid), page, rows);
                break;
            default:
                break;
        }
        return records;
    }

    @Override
    public BaseQueryRecords<Object> queryEachApp(int sid, Integer sshduanid, String content) {
        BaseQueryRecords<Object> records = null;
        String hql = "";
        switch (sid) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb a where a.fswlx=?";
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl a where a.fswlx=?";
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc a where a.fswlx=?";
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx a where a.fswlx=?";
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd a where a.fswlx=?";
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd a where a.fswlx=?";
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt a where a.fswlx=?";
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt a where a.fswlx=?";
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt a where a.fswlx=?";
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc a where a.fswlx=?";
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk a where a.fswlx=?";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz a where a.fswlx=?";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz a where a.fswlx=?";
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq a where a.fswlx=?";
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq a where a.fswlx=?";
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn a where a.fswlx=?";
                break;
            case Constants.APP_DAM:
                hql = "from CHdB a where a.fswlx=?";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha a where a.fswlx=?";
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd a where a.fswlx=?";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd a where a.fswlx=?";
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd a where a.fswlx=?";
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz a where a.fswlx=?";
                break;
            default:
                break;
        }
        if (sshduanid != null && sshduanid != -1 && sshduanid != 0) {
            hql += " and a.sshduanid=" + sshduanid;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.mc like '%" + content + "%' or a.bh like '%" + content + "%')";
        }
        hql += " order by a.updatetime desc";
        records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                sshduanid));
        return records;
    }

    @Override
    public BaseQueryRecords queryAppByType(int type) {
        return super.find(new CZdAppurtenance(), "type", type);
    }

    @Override
    public List zhcxApp(String xzqh) {
        List list = new ArrayList();
        if (!"".equals(xzqh)) {
            Session session = getSessionFactory().openSession();
            String sql = "select ifnull(sum(hbnum),0) as hb,ifnull(sum(qlnum),0) as ql,ifnull(sum(lxnum),0) as lx,ifnull(sum(kynum),0)+ifnull(sum(hynum),0)+ifnull(sum(gwnum),0)+ifnull(sum(mbqnum),0)+ifnull(sum(fwqnum),0) as mt,ifnull(sum(snnum),0) as sn,ifnull(sum(jgnum),0)+ifnull(sum(spnum),0)+ifnull(sum(rgnum),0) as gcd\n" +
                    "from (\n" +
                    "(select distinct id from c_hd_hduanjcxx where hdszxzqh in(" + xzqh + ")) a\n" +
                    "LEFT JOIN \n" +
                    "(select sshduanid,ifnull(count(*),0) as hbnum from c_hd_hb GROUP BY SSHDUANID) e ON a.id=e.sshduanid) \n" +
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
                    "ON a.id=p.sshduanid \n";
            Query query = session.createSQLQuery(
                    new SQL(sql).toString());
            list = query.list();
            session.flush();
            session.close();
        }
        return list;
    }

    @Override
    public BaseQueryRecords<Object> zhcxApps(int fswlx, String hduanids, String content, int page, int rows) {
        BaseQueryRecords<Object> records = null;
        String hql = "";
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb a,CHdHdaojcxx b,CHdHduanjcxx c,CZdAppattribute d,CZdAppattribute e,CZdAppattribute f,CZdAppattribute g,CZdAppattribute h,CZdAppattribute i where a.sshdaoid=b.id and a.sshduanid=c.id and a.fswlx=? and a.sshduanid in(?) and a.lx=d.id and a.gxxz=e.id and a.dzxh=f.id and a.dbys=g.id and a.zcfs=h.id and a.jg=i.id\n";
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl a,CHdHdaojcxx b,CHdHduanjcxx c,CZdAppattribute d,CZdAppattribute e where a.sshdaoid=b.id and a.sshduanid=c.id and a.fswlx=? and a.sshduanid in(?) and a.jgxs=d.id and a.ytfl=e.id\n";
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx a,CHdHdaojcxx b,CHdHduanjcxx c,CZdAppattribute e  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.zl=e.id\n";
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) \n";
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) \n";
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt a,CHdHdaojcxx b,CHdHduanjcxx c ,CZdAppattribute d  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.jglx=d.id \n";
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt a,CHdHdaojcxx b,CHdHduanjcxx c ,CZdAppattribute d  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.jglx=d.id\n";
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt a,CHdHdaojcxx b,CHdHduanjcxx c ,CZdAppattribute d  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.jglx=d.id\n";
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk a,CHdHdaojcxx b,CHdHduanjcxx c,CZdAppattribute d  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.lx=d.id\n";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn a,CHdHdaojcxx b,CHdHduanjcxx c,CZdAppattribute d ,CZdAppattribute e ,CZdAppattribute f  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.xs=d.id and a.thlx=e.id and a.gcsswz=f.id \n";
                break;
            case Constants.APP_DAM:
                hql = "from CHdB a,CHdHdaojcxx b,CHdHduanjcxx c,CZdAppattribute d  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?) and a.lx=d.id\n";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz a,CHdHdaojcxx b,CHdHduanjcxx c  where a.sshdaoid=b.id and a.sshduanid=c.id and  a.fswlx=? and a.sshduanid in(?)\n";
                break;
            default:
                break;
        }
        if (!"".equals(content)) {
            hql += " and mc like '%" + content + "%'\n";
        }
        hql += " order by a.updatetime desc";
        records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                hduanids), page, rows);
        return records;
    }


    @Override
    public Object queryAppurtenanceById(int id, int fswlx) {
        // TODO Auto-generated method stub
        Object object = null;
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                object = super.findUnique(new CHdHb(), "id", id);
                break;
            case Constants.APP_BRIDGE:
                object = super.findUnique(new CHdQl(), "id", id);
                break;
            case Constants.APP_AQUEDUCT:
                object = super.findUnique(new CHdDc(), "id", id);
                break;
            case Constants.APP_CABLE:
                object = super.findUnique(new CHdLx(), "id", id);
                break;
            case Constants.APP_PIPELINE:
                object = super.findUnique(new CHdGd(), "id", id);
                break;
            case Constants.APP_TUNNEL:
                object = super.findUnique(new CHdSd(), "id", id);
                break;
            case Constants.APP_KYDOCK:
                object = super.findUnique(new CHdKymt(), "id", id);
                break;
            case Constants.APP_HYDOCK:
                object = super.findUnique(new CHdHymt(), "id", id);
                break;
            case Constants.APP_GWDOCK:
                object = super.findUnique(new CHdGwmt(), "id", id);
                break;
            case Constants.APP_SHIPYARD:
                object = super.findUnique(new CHdCc(), "id", id);
                break;
            case Constants.APP_TAKEOUTFALL:
                object = super.findUnique(new CHdQpsk(), "id", id);
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                object = super.findUnique(new CHdSwz(), "id", id);
                break;
            case Constants.APP_MANAGEMENTSTATION:
                object = super.findUnique(new CHdGlz(), "id", id);
                break;
            case Constants.APP_SERVICEAREA:
                object = super.findUnique(new CHdFwq(), "id", id);
                break;
            case Constants.APP_MOORINGAREA:
                object = super.findUnique(new CHdMbq(), "id", id);
                break;
            case Constants.APP_HUB:
                object = super.findUnique(new CHdSn(), "id", id);
                break;
            case Constants.APP_DAM:
                object = super.findUnique(new CHdB(), "id", id);
                break;
            case Constants.APP_REGULATIONREVEMENT:
                object = super.findUnique(new CHdZzha(), "id", id);
                break;
            case Constants.APP_LASEROBSERVATION:
                object = super.findUnique(new CHdJgllgcd(), "id", id);
                break;
            case Constants.APP_VIDEOOBSERVATION:
                object = super.findUnique(new CHdSpgcd(), "id", id);
                break;
            case Constants.APP_MANUALOBSERVATION:
                object = super.findUnique(new CHdRggcd(), "id", id);
                break;
            case Constants.APP_BOLLARD:
                object = super.findUnique(new CHdXlz(), "id", id);
                break;
            default:
                break;
        }
        return object;
    }

    @Override
    public List<Object[]> queryAppurtenanceInfo(int id, int fswlx) {
        List<Object[]> records = null;
        List<Object> list = new ArrayList<Object>();
        String hql = "";
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_DAM:
                hql = "from CHdB a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz a,CHdHduanjcxx b where a.sshduanid=b.id and a.id=?";
                records = (List<Object[]>) super.find(new HQL(hql, id)).getData();
                break;
            default:
                break;
        }
        return records;
    }

    @Override
    public void delAppurtenance(Object object) {
        // TODO Auto-generated method stub
        super.delete(object);
    }

    @Override
    public void delAppurtenances(int hduanid) {
        String tablename = "";
        String hql = "delete from ? where sshduanid=?";

        tablename = "CHdHb";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdQl";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdDc";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdLx";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdGd";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdSd";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdKymt";
        super.delete(new HQL(hql, tablename, hduanid));
        tablename = "CHdHymt";
        super.delete(new HQL(hql, tablename, hduanid));
        tablename = "CHdGwmt";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdCc";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdQpsk";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdSwz";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdGlz";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdFwq";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdMbq";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdSn";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdB";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdZzha";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdJgllgcd";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdSpgcd";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdRggcd";
        super.delete(new HQL(hql, tablename, hduanid));

        tablename = "CHdXlz";
        super.delete(new HQL(hql, tablename, hduanid));
    }

    @Override
    public void addNavigationmark(CHdHb chdhb) {
        // TODO Auto-generated method stub
        super.save(chdhb);
    }

    @Override
    public void addServicearea(CHdFwq chdfwq) {
        // TODO Auto-generated method stub
        super.save(chdfwq);
    }

    @Override
    public void addBridge(CHdQl chdql) {
        // TODO Auto-generated method stub
        super.save(chdql);
    }

    @Override
    public void addAqueduct(CHdDc chddc) {
        // TODO Auto-generated method stub
        super.save(chddc);
    }

    @Override
    public void addCable(CHdLx chdlx) {
        // TODO Auto-generated method stub
        super.save(chdlx);
    }

    @Override
    public void addPipeline(CHdGd chdgd) {
        // TODO Auto-generated method stub
        super.save(chdgd);
    }

    @Override
    public void addTunnel(CHdSd chdsd) {
        // TODO Auto-generated method stub
        super.save(chdsd);
    }

    @Override
    public void addKyDock(CHdKymt chdmt) {
        // TODO Auto-generated method stub
        super.save(chdmt);
    }

    @Override
    public void addHyDock(CHdHymt chdmt) {
        // TODO Auto-generated method stub
        super.save(chdmt);
    }

    @Override
    public void addGwDock(CHdGwmt chdmt) {
        // TODO Auto-generated method stub
        super.save(chdmt);
    }

    @Override
    public void addTakeoutfall(CHdQpsk chdqpsk) {
        // TODO Auto-generated method stub
        super.save(chdqpsk);
    }

    @Override
    public void addShipyard(CHdCc chdcc) {
        // TODO Auto-generated method stub
        super.save(chdcc);
    }

    @Override
    public void addHydrologicalstation(CHdSwz chdswz) {
        // TODO Auto-generated method stub
        super.save(chdswz);
    }

    @Override
    public void addBollard(CHdXlz chdxlz) {
        // TODO Auto-generated method stub
        super.save(chdxlz);
    }

    @Override
    public void addVideoobservation(CHdSpgcd chdspgcd) {
        // TODO Auto-generated method stub
        super.save(chdspgcd);
    }

    @Override
    public void addManualobservation(CHdRggcd chdrggcd) {
        super.save(chdrggcd);
    }

    @Override
    public void addLaserobservation(CHdJgllgcd chdjgllgcd) {
        // TODO Auto-generated method stub
        super.save(chdjgllgcd);
    }

    @Override
    public void addRegulationrevement(CHdZzha chdzzha) {
        // TODO Auto-generated method stub
        super.save(chdzzha);
    }

    @Override
    public void addHub(CHdSn chdsn) {
        // TODO Auto-generated method stub
        super.save(chdsn);
    }

    @Override
    public void addDam(CHdB chdb) {
        // TODO Auto-generated method stub
        super.save(chdb);
    }

    @Override
    public void addMooringarea(CHdMbq chdmbq) {
        // TODO Auto-generated method stub
        super.save(chdmbq);
    }


    @Override
    public void addManagementstation(CHdGlz chdglz) {
        // TODO Auto-generated method stub
        super.save(chdglz);
    }

    @Override
    public void updateNavigationmark(CHdHb chdhb) {
        // TODO Auto-generated method stub
        super.update(chdhb);
    }

    @Override
    public void updateServicearea(CHdFwq cHdFwq) {
        // TODO Auto-generated method stub
        super.update(cHdFwq);
    }

    @Override
    public void updateBridge(CHdQl chdql) {
        // TODO Auto-generated method stub
        super.update(chdql);
    }

    @Override
    public void updateAqueduct(CHdDc chddc) {
        // TODO Auto-generated method stub
        super.update(chddc);
    }

    @Override
    public void updateCable(CHdLx chdlx) {
        // TODO Auto-generated method stub
        super.update(chdlx);
    }

    @Override
    public void updatePipeline(CHdGd chdgd) {
        // TODO Auto-generated method stub
        super.update(chdgd);
    }

    @Override
    public void updateTunnel(CHdSd chdsd) {
        // TODO Auto-generated method stub
        super.update(chdsd);
    }

    @Override
    public void updateKyDock(CHdKymt chdmt) {
        // TODO Auto-generated method stub
        super.update(chdmt);
    }

    @Override
    public void updateHyDock(CHdHymt chdmt) {
        // TODO Auto-generated method stub
        super.update(chdmt);
    }

    @Override
    public void updateGwDock(CHdGwmt chdmt) {
        // TODO Auto-generated method stub
        super.update(chdmt);
    }

    @Override
    public void updateTakeoutfall(CHdQpsk chdqpsk) {
        // TODO Auto-generated method stub
        super.update(chdqpsk);
    }

    @Override
    public void updateShipyard(CHdCc chdcc) {
        // TODO Auto-generated method stub
        super.update(chdcc);
    }

    @Override
    public void updateHydrologicalstation(CHdSwz chdswz) {
        // TODO Auto-generated method stub
        super.update(chdswz);
    }

    @Override
    public void updateBollard(CHdXlz chdxlz) {
        // TODO Auto-generated method stub
        super.update(chdxlz);
    }

    @Override
    public void updateVideoobservation(CHdSpgcd chdspgcd) {
        // TODO Auto-generated method stub
        super.update(chdspgcd);
    }

    @Override
    public void updateLaserobservation(CHdJgllgcd chdjgllgcd) {
        // TODO Auto-generated method stub
        super.update(chdjgllgcd);
    }

    @Override
    public void updateManualobservation(CHdRggcd chdrggcd) {
        super.update(chdrggcd);
    }

    @Override
    public void updateRegulationrevement(CHdZzha chdzzha) {
        // TODO Auto-generated method stub
        super.update(chdzzha);
    }

    @Override
    public void updateHub(CHdSn chdsn) {
        // TODO Auto-generated method stub
        super.update(chdsn);
    }

    @Override
    public void updateDam(CHdB chdb) {
        // TODO Auto-generated method stub
        super.update(chdb);
    }

    @Override
    public void updateMooringarea(CHdMbq chdmbq) {
        // TODO Auto-generated method stub
        super.update(chdmbq);
    }


    @Override
    public void updateManagementstation(CHdGlz chdglz) {
        // TODO Auto-generated method stub
        super.update(chdglz);
    }

    @Override
    public void addCHdFj(CHdFj fj) {
        // TODO Auto-generated method stub
        super.save(fj);
    }

    @Override
    public List<CHdFj> queryChdfjByApptype(int appid, int apptype) {
        // TODO Auto-generated method stub
        String hql = "from CHdFj where appid=? and apptype=?";
        return (List<CHdFj>) super.find(new HQL(hql, appid, apptype)).getData();
    }

    @Override
    public CHdFj queryCHdFjById(int id) {
        // TODO Auto-generated method stub
        return (CHdFj) super.findUnique(new CHdFj(), "id", id);
    }

    @Override
    public void delCHdFj(int id, int fswlx) {
        // TODO Auto-generated method stub
        String hql = "delete from CHdFj where appid=? and apptype=?";
        super.delete(new HQL(hql, id, fswlx));
    }

    @Override
    public void delCHdFj(Integer id, int appid, int apptype) {
        // TODO Auto-generated method stub
        String hql = "delete from CHdFj where id=? and appid=? and apptype=?";
        super.delete(new HQL(hql, id, appid, apptype));
    }

    @Override
    public CHdSn querySnByBh(String bh) {
        CHdSn sn = new CHdSn();
        sn = (CHdSn) super.findUnique(new CHdSn(), "bh", bh);
        return sn;
    }

    @Override
    public List queryAppurtenance(int fswlx) {
        String hql = "";
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb";
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl";
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc";
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx";
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd";
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd";
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt";
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt";
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt";
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc";
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz";
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq";
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq";
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn";
                break;
            case Constants.APP_DAM:
                hql = "from CHdB";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha";
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd";
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd";
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz";
                break;
            default:
                break;
        }
        if (!"".equals(hql)) {
            return super.find(new HQL(hql)).getData();
        } else {
            return null;
        }
    }

    @Override
    public List<CHdSn> querySnByThlx() {
        String hql = "from CHdSn where thlx=88";
        return (List<CHdSn>) super.find(new HQL(hql)).getData();
    }

    @Override
    public List<?> querySnByThlx(String xzqhs) {
        String hql = "from CHdSn a,CHdHdaojcxx b where a.sshdaoid=b.id and a.szxzqh in(?) and a.thlx=88";
        return (List<?>) super.find(new HQL(hql, xzqhs)).getData();
    }

    @Override
    public Boolean queryAppbhExisted(String bh, int fswlx) {
        String hql = "";
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "select count(*) from CHdHb where bh='?'";
                break;
            case Constants.APP_BRIDGE:
                hql = "select count(*) from CHdQl where bh='?'";
                break;
            case Constants.APP_AQUEDUCT:
                hql = "select count(*) from CHdDc where bh='?'";
                break;
            case Constants.APP_CABLE:
                hql = "select count(*) from CHdLx where bh='?'";
                break;
            case Constants.APP_PIPELINE:
                hql = "select count(*) from CHdGd where bh='?'";
                break;
            case Constants.APP_TUNNEL:
                hql = "select count(*) from CHdSd where bh='?'";
                break;
            case Constants.APP_KYDOCK:
                hql = "select count(*) from CHdKymt where bh='?'";
                break;
            case Constants.APP_HYDOCK:
                hql = "select count(*) from CHdHymt where bh='?'";
                break;
            case Constants.APP_GWDOCK:
                hql = "select count(*) from CHdGwmt where bh='?'";
                break;
            case Constants.APP_SHIPYARD:
                hql = "select count(*) from CHdCc where bh='?'";
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "select count(*) from CHdQpsk where bh='?'";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "select count(*) from CHdSwz where bh='?'";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "select count(*) from CHdGlz where bh='?'";
                break;
            case Constants.APP_SERVICEAREA:
                hql = "select count(*) from CHdFwq where bh='?'";
                break;
            case Constants.APP_MOORINGAREA:
                hql = "select count(*) from CHdMbq where bh='?'";
                break;
            case Constants.APP_HUB:
                hql = "select count(*) from CHdSn where bh='?'";
                break;
            case Constants.APP_DAM:
                hql = "select count(*) from CHdB where bh='?'";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "select count(*) from CHdZzha where bh='?'";
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "select count(*) from CHdJgllgcd where bh='?'";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "select count(*) from CHdSpgcd where bh='?'";
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "select count(*) from CHdRggcd where bh='?'";
                break;
            case Constants.APP_BOLLARD:
                hql = "select count(*) from CHdXlz where bh='?'";
                break;
            default:
                break;
        }
        if (!"".equals(hql)) {
            long cnt = super.count(new HQL(hql, bh));
            return cnt <= 0 ? true : false;
        } else {
            return false;
        }
    }

    @Override
    public Long queryMaxAppbh(int fswlx) {
        String hql = "";
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "select max(a.id) from CHdHb a";
                break;
            case Constants.APP_BRIDGE:
                hql = "select max(a.id) from CHdQl a";
                break;
            case Constants.APP_AQUEDUCT:
                hql = "select max(a.id) from CHdDc a";
                break;
            case Constants.APP_CABLE:
                hql = "select max(a.id) from CHdLx a";
                break;
            case Constants.APP_PIPELINE:
                hql = "select max(a.id) from CHdGd a";
                break;
            case Constants.APP_TUNNEL:
                hql = "select max(a.id) from CHdSd a";
                break;
            case Constants.APP_KYDOCK:
                hql = "select max(a.id) from CHdKymt a";
                break;
            case Constants.APP_HYDOCK:
                hql = "select max(a.id) from CHdHymt a";
                break;
            case Constants.APP_GWDOCK:
                hql = "select max(a.id) from CHdGwmt a";
                break;
            case Constants.APP_SHIPYARD:
                hql = "select max(a.id) from CHdCc a";
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "select max(a.id) from CHdQpsk a";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "select max(a.id) from CHdSwz a";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "select max(a.id) from CHdGlz a";
                break;
            case Constants.APP_SERVICEAREA:
                hql = "select max(a.id) from CHdFwq a";
                break;
            case Constants.APP_MOORINGAREA:
                hql = "select max(a.id) from CHdMbq a";
                break;
            case Constants.APP_HUB:
                hql = "select max(a.id) from CHdSn a";
                break;
            case Constants.APP_DAM:
                hql = "select max(a.id) from CHdB a";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "select max(a.id) from CHdZzha a";
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "select max(a.id) from CHdJgllgcd a";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "select max(a.id) from CHdSpgcd a";
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "select max(a.id) from CHdRggcd a";
                break;
            case Constants.APP_BOLLARD:
                hql = "select max(a.id) from CHdXlz a";
                break;
            default:
                break;
        }
        Integer maxId = -1;
        if (!"".equals(hql)) {
            maxId = (Integer) getCurrentSession().createQuery(hql).uniqueResult();
        }
        if (maxId == null || maxId == -1) {
            return Long.parseLong("0");
        } else {
            return Long.parseLong(String.valueOf(maxId));
        }
    }

    @Override
    public BaseQueryRecords<Object> queryAppPopup(int id, int fswlx) {
        String hql = "";
        BaseQueryRecords<Object> records = new BaseQueryRecords<Object>();
        switch (fswlx) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb a,CZdAppattribute b,CZdAppattribute c,CZdAppattribute d,CZdAppattribute e ,CZdAppattribute f ,CZdAppattribute g where a.fswlx=? and a.id=? and a.lx=b.id and a.gxxz=c.id and a.dzxh=d.id and a.dbys=e.id and a.zcfs=f.id and a.jg=g.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl a,CZdAppattribute b,CZdAppattribute c where a.fswlx=? and a.id=? and a.jgxs=b.id and a.ytfl=c.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx a ,CZdAppattribute b where a.fswlx=? and a.id=? and a.zl=b.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt a,CZdAppattribute b where a.fswlx=? and a.id=? and a.jglx=b.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt a,CZdAppattribute b where a.fswlx=? and a.id=? and a.jglx=b.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt a,CZdAppattribute b where a.fswlx=? and a.id=? and a.jglx=b.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk a,CZdAppattribute b where a.fswlx=? and a.id=? and a.lx=b.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn a,CZdAppattribute b ,CZdAppattribute c ,CZdAppattribute d where a.fswlx=? and a.id=? and a.xs=b.id and a.thlx=c.id and a.gcsswz=d.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_DAM:
                hql = "from CHdB a,CZdAppattribute b where a.fswlx=? and a.id=? and a.lx=b.id";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz where fswlx=? and id=? ";
                records = (BaseQueryRecords<Object>) super.find(new HQL(hql, fswlx,
                        id));
                break;
            default:
                break;
        }
        return records;
    }

    @Override
    public List<Object[]> importQl() {
        return (List<Object[]>) super.find(new SQL("select * from tempql order by id")).getData();
    }

    @Override
    public List<Object[]> importLx() {
        return (List<Object[]>) super.find(new SQL("select * from templx order by id")).getData();
    }

    @Override
    public List<Object[]> importKymt() {
        return (List<Object[]>) super.find(new SQL("select * from tempmt order by id")).getData();
    }

    @Override
    public List<Object[]> importSn() {
        return (List<Object[]>) super.find(new SQL("select * from tempsn order by id")).getData();
    }

    @Override
    public long queryAppCountXzqh(List<CZdXzqhdm> xzqhs, int sid, Integer hduanid, String content) {
        String hql = "select count(a) from ? a where a.fswlx=? ";
        if (hduanid != -1) {
            hql += " and a.sshduanid=" + hduanid;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.mc like '%" + content + "%' or a.bh like '%" + content + "%')";
        }
        if (xzqhs != null) {
            hql += " and a.szxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        String tablename = "";
        long cnt = 0;
        switch (sid) {
            case Constants.APP_NAVIGATIONMARK:
                tablename = "CHdHb";
                break;
            case Constants.APP_BRIDGE:
                tablename = "CHdQl";
                break;
            case Constants.APP_AQUEDUCT:
                tablename = "CHdDc";
                break;
            case Constants.APP_CABLE:
                tablename = "CHdLx";
                break;
            case Constants.APP_PIPELINE:
                tablename = "CHdGd";
                break;
            case Constants.APP_TUNNEL:
                tablename = "CHdSd";
                break;
            case Constants.APP_KYDOCK:
                tablename = "CHdKymt";
                break;
            case Constants.APP_HYDOCK:
                tablename = "CHdHymt";
                break;
            case Constants.APP_GWDOCK:
                tablename = "CHdGwmt";
                break;
            case Constants.APP_SHIPYARD:
                tablename = "CHdCc";
                break;
            case Constants.APP_TAKEOUTFALL:
                tablename = "CHdQpsk";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                tablename = "CHdSwz";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                tablename = "CHdGlz";
                break;
            case Constants.APP_SERVICEAREA:
                tablename = "CHdFwq";
                break;
            case Constants.APP_MOORINGAREA:
                tablename = "CHdMbq";
                break;
            case Constants.APP_HUB:
                tablename = "CHdSn";
                break;
            case Constants.APP_DAM:
                tablename = "CHdB";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                tablename = "CHdZzha";
                break;
            case Constants.APP_LASEROBSERVATION:
                tablename = "CHdJgllgcd";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                tablename = "CHdSpgcd";
                break;
            case Constants.APP_MANUALOBSERVATION:
                tablename = "CHdRggcd";
                break;
            case Constants.APP_BOLLARD:
                tablename = "CHdXlz";
                break;
            default:
                break;
        }
        cnt = super.count(new HQL(hql, tablename, sid));
        return cnt;
    }

    @Override
    public BaseQueryRecords<Object> queryEachAppXzqh(List<CZdXzqhdm> xzqhs, int sid, Integer sshduanid, String content) {
        BaseQueryRecords<Object> records = null;
        String hql = "";
        switch (sid) {
            case Constants.APP_NAVIGATIONMARK:
                hql = "from CHdHb a where a.fswlx=?";
                break;
            case Constants.APP_BRIDGE:
                hql = "from CHdQl a where a.fswlx=?";
                break;
            case Constants.APP_AQUEDUCT:
                hql = "from CHdDc a where a.fswlx=?";
                break;
            case Constants.APP_CABLE:
                hql = "from CHdLx a where a.fswlx=?";
                break;
            case Constants.APP_PIPELINE:
                hql = "from CHdGd a where a.fswlx=?";
                break;
            case Constants.APP_TUNNEL:
                hql = "from CHdSd a where a.fswlx=?";
                break;
            case Constants.APP_KYDOCK:
                hql = "from CHdKymt a where a.fswlx=?";
                break;
            case Constants.APP_HYDOCK:
                hql = "from CHdHymt a where a.fswlx=?";
                break;
            case Constants.APP_GWDOCK:
                hql = "from CHdGwmt a where a.fswlx=?";
                break;
            case Constants.APP_SHIPYARD:
                hql = "from CHdCc a where a.fswlx=?";
                break;
            case Constants.APP_TAKEOUTFALL:
                hql = "from CHdQpsk a where a.fswlx=?";
                break;
            case Constants.APP_HYDROLOGICALSTATION:
                hql = "from CHdSwz a where a.fswlx=?";
                break;
            case Constants.APP_MANAGEMENTSTATION:
                hql = "from CHdGlz a where a.fswlx=?";
                break;
            case Constants.APP_SERVICEAREA:
                hql = "from CHdFwq a where a.fswlx=?";
                break;
            case Constants.APP_MOORINGAREA:
                hql = "from CHdMbq a where a.fswlx=?";
                break;
            case Constants.APP_HUB:
                hql = "from CHdSn a where a.fswlx=?";
                break;
            case Constants.APP_DAM:
                hql = "from CHdB a where a.fswlx=?";
                break;
            case Constants.APP_REGULATIONREVEMENT:
                hql = "from CHdZzha a where a.fswlx=?";
                break;
            case Constants.APP_LASEROBSERVATION:
                hql = "from CHdJgllgcd a where a.fswlx=?";
                break;
            case Constants.APP_VIDEOOBSERVATION:
                hql = "from CHdSpgcd a where a.fswlx=?";
                break;
            case Constants.APP_MANUALOBSERVATION:
                hql = "from CHdRggcd a where a.fswlx=?";
                break;
            case Constants.APP_BOLLARD:
                hql = "from CHdXlz a where a.fswlx=?";
                break;
            default:
                break;
        }
        if (sshduanid != null && sshduanid != -1 && sshduanid != 0) {
            hql += " and a.sshduanid=" + sshduanid;
        }
        if (content != null && !"".equals(content)) {
            hql += " and (a.mc like '%" + content + "%' or a.bh like '%" + content + "%')";
        }
        if (xzqhs != null) {
            hql += " and a.szxzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        hql += " order by a.updatetime desc";
        records = (BaseQueryRecords<Object>) super.find(new HQL(hql, sid,
                sshduanid));
        return records;

    }
}
