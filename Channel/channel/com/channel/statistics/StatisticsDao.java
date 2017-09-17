package com.channel.statistics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import com.channel.bean.TableCell;
import com.channel.model.bb.CBbJSYOriBb;
import com.channel.statistics.model.CBfBaoBiao;
import com.common.dao.impl.HQL;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.channel.model.user.CZdXzqhdm;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;

@Repository("StatisticsDao")
public class StatisticsDao extends BaseDaoDB {

    public BaseQueryRecords<Object[]> queryQLByXzqhsYtfl(List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.name, a.ytfl,c.ATTRDESC from c_hd_ql a "
                + "right join c_zd_xzqhdm b on a.szxzqh = b.id left join c_zd_appattribute c on a.ytfl = c.id "
                + "where b.id in (";
        for (int i = 0; i < xzqhs.size(); i++) {
            sql += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                sql += ",";
        }
        sql += ") GROUP BY b.id,a.ytfl";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHbByXzqhsBzfl(List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.name, a.lx,c.ATTRDESC from c_hd_hb a "
                + "right join c_zd_xzqhdm b on a.SZXZQH = b.ID left join c_zd_appattribute c on a.lx = c.ID "
                + " where b.id in (";
        for (int i = 0; i < xzqhs.size(); i++) {
            sql += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                sql += ",";
        }
        sql += ") GROUP BY b.id,a.lx";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryMtByXzqhsMtlx(List<CZdXzqhdm> xzqhs) {
        String xzqhstr = " (";
        for (int i = 0; i < xzqhs.size(); i++) {
            xzqhstr += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                xzqhstr += ",";
        }
        xzqhstr += ") ";
        String sql = "select sum(z.cnt), z.name,z.lx,z.id from( "
                + "select count(a.mc) as cnt,b.id, b.name as name, a.lx as lx from c_hd_kymt a "
                + " right join c_zd_xzqhdm b on a.SZXZQH = b.id "
                + "where b.id in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.name as name, a.lx as lx from c_hd_hymt a "
                + " right join c_zd_xzqhdm b on a.SZXZQH = b.id "
                + "where b.id in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.name as name, a.lx as lx from c_hd_gwmt a "
                + " right join c_zd_xzqhdm b on a.SZXZQH = b.id "
                + "where b.id in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.name as name, a.lx as lx from c_hd_mbq a "
                + " right join c_zd_xzqhdm b on a.SZXZQH = b.id "
                + "where  b.id in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.name as name, a.lx as lx from c_hd_fwq a "
                + " right join c_zd_xzqhdm b on a.SZXZQH = b.id "
                + "where b.id in " + xzqhstr
                + "group by b.id, a.lx) z " + "GROUP BY z.name,z.lx ";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryGxByXzqhsGxzl(List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.name, a.zl,c.ATTRDESC from c_hd_lx a "
                + "right join c_zd_xzqhdm b on a.szxzqh = b.id left join c_zd_appattribute c on a.zl = c.id "
                + "where b.id in (";
        for (int i = 0; i < xzqhs.size(); i++) {
            sql += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                sql += ",";
        }
        sql += ") GROUP BY b.id,a.zl";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public List queryJsy671TableByHdid(Integer hdid) {
        String sql = "SELECT * FROM (SELECT a.qlnum+b.lxnum+c.dcnum as waterupnum from\n" +
                "(SELECT COUNT(*) as qlnum from c_hd_ql where sshdaoid in(?)) a,\n" +
                "(SELECT COUNT(*) as lxnum from c_hd_lx where sshdaoid in(?)) b,\n" +
                "(SELECT COUNT(*) as dcnum from c_hd_dc where sshdaoid in(?)) c\n" +
                "UNION ALL\n" +
                "SELECT d.sdnum+e.gdnum as waterdownnum from\n" +
                "(SELECT COUNT(*) as sdnum from c_hd_sd where sshdaoid in(?)) d,\n" +
                "(SELECT COUNT(*) as gdnum from c_hd_gd where sshdaoid in(?)) e\n" +
                "UNION ALL\n" +
                "SELECT  kyf.kymtnum+hyf.hymtnum+gwf.gwmtnum+g.fwqnum+h.mbqnum+i.ccnum+j.swznum+l.glznum+m.qpsknum as nearrivernum from\n" +
                "(SELECT COUNT(*) as kymtnum from c_hd_kymt where sshdaoid in(?)) kyf,\n" +
                "(SELECT COUNT(*) as hymtnum from c_hd_hymt where sshdaoid in(?)) hyf,\n" +
                "(SELECT COUNT(*) as gwmtnum from c_hd_gwmt where sshdaoid in(?)) gwf,\n" +
                "(SELECT COUNT(*) as fwqnum from c_hd_fwq where sshdaoid in(?)) g,\n" +
                "(SELECT COUNT(*) as mbqnum from c_hd_mbq where sshdaoid in(?)) h,\n" +
                "(SELECT COUNT(*) as ccnum from c_hd_cc where sshdaoid in(?)) i,\n" +
                "(SELECT COUNT(*) as swznum from c_hd_swz where sshdaoid in(?)) j,\n" +
                "(SELECT COUNT(*) as glznum from c_hd_glz where sshdaoid in(?)) l,\n" +
                "(SELECT COUNT(*) as qpsknum from c_hd_qpsk where sshdaoid in(?)) m) n";
        return super.find(new SQL(sql, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid)).getData();
    }

    public List queryJsy671TableByNotHdid(String hdids, String xzqhids) {
        String sql = "SELECT * FROM (SELECT a.qlnum+b.lxnum+c.dcnum as waterupnum from\n" +
                "(SELECT COUNT(*) as qlnum from c_hd_ql where sshdaoid not in(?) and szxzqh in(?)) a,\n" +
                "(SELECT COUNT(*) as lxnum from c_hd_lx where sshdaoid not  in(?) and szxzqh in(?)) b,\n" +
                "(SELECT COUNT(*) as dcnum from c_hd_dc where sshdaoid  not in(?) and szxzqh in(?)) c\n" +
                "UNION ALL\n" +
                "SELECT d.sdnum+e.gdnum as waterdownnum from\n" +
                "(SELECT COUNT(*) as sdnum from c_hd_sd where sshdaoid not  in(?) and szxzqh in(?)) d,\n" +
                "(SELECT COUNT(*) as gdnum from c_hd_gd where sshdaoid  not in(?) and szxzqh in(?)) e\n" +
                "UNION ALL\n" +
                "SELECT  kyf.kymtnum+hyf.hymtnum+gwf.gwmtnum+g.fwqnum+h.mbqnum+i.ccnum+j.swznum+l.glznum+m.qpsknum as nearrivernum from\n" +
                "(SELECT COUNT(*) as kymtnum from c_hd_kymt where sshdaoid in(?) and szxzqh in(?)) kyf,\n" +
                "(SELECT COUNT(*) as hymtnum from c_hd_hymt where sshdaoid in(?) and szxzqh in(?)) hyf,\n" +
                "(SELECT COUNT(*) as gwmtnum from c_hd_gwmt where sshdaoid in(?) and szxzqh in(?)) gwf,\n" +
                "(SELECT COUNT(*) as fwqnum from c_hd_fwq where sshdaoid  not in(?) and szxzqh in(?)) g,\n" +
                "(SELECT COUNT(*) as mbqnum from c_hd_mbq where sshdaoid  not in(?) and szxzqh in(?)) h,\n" +
                "(SELECT COUNT(*) as ccnum from c_hd_cc where sshdaoid  not in(?) and szxzqh in(?)) i,\n" +
                "(SELECT COUNT(*) as swznum from c_hd_swz where sshdaoid  not in(?) and szxzqh in(?)) j,\n" +
                "(SELECT COUNT(*) as glznum from c_hd_glz where sshdaoid  not in(?) and szxzqh in(?)) l,\n" +
                "(SELECT COUNT(*) as qpsknum from c_hd_qpsk where sshdaoid  not in(?) and szxzqh in(?)) m) n";
        return super.find(new SQL(sql, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids, hdids, xzqhids)).getData();
    }

    public List queryJsy61TableByHdid(Integer hdid) {
        String sql = "SELECT * from (select aa.onewh+bb.twowh+cc.threewh as totalwh,aa.onewh,bb.twowh,cc.threewh,jj.onedj+kk.twodj+dd.threedj+ee.fourdj+ff.fivedj+gg.sixdj+hh.sevendj+ii.sevenplusdj as totaldj,jj.onedj,kk.twodj,dd.threedj,ee.fourdj,ff.fivedj,gg.sixdj,hh.sevendj,ii.sevenplusdj from(\n" +
                "(select ifnull(round(sum(hdlc),2),0) as onewh FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDWHLB=162) aa,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as twowh FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDWHLB=163) bb,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as threewh FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDWHLB=164) cc,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as onedj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=92) jj,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as twodj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=93) kk,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as threedj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=94) dd,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as fourdj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=95) ee,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as fivedj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=96) ff,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as sixdj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=97) gg,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as sevendj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=98) hh,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as sevenplusdj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID=? and a.HDDJ=99) ii)) ll";
        return super.find(new SQL(sql, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid, hdid)).getData();
    }

    public List queryJsy61TableByNotHdid(String hdids, String xzqhs) {
        String sql = "SELECT * from (select aa.onewh+bb.twowh+cc.threewh as totalwh,aa.onewh,bb.twowh,cc.threewh,jj.onedj+kk.twodj+dd.threedj+ee.fourdj+ff.fivedj+gg.sixdj+hh.sevendj+ii.sevenplusdj as totaldj,jj.onedj,kk.twodj,dd.threedj,ee.fourdj,ff.fivedj,gg.sixdj,hh.sevendj,ii.sevenplusdj from(\n" +
                "(select ifnull(round(sum(hdlc),2),0) as onewh FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?) and a.hdszxzqh in(?) and a.HDWHLB=162) aa,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as twowh FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDWHLB=163) bb,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as threewh FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDWHLB=164) cc,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as onedj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDDJ=92) jj,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as twodj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID  not in(?)  and a.hdszxzqh in(?) and a.HDDJ=93) kk,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as threedj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDDJ=94) dd,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as fourdj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID  not in(?)  and a.hdszxzqh in(?) and a.HDDJ=95) ee,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as fivedj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDDJ=96) ff,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as sixdj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDDJ=97) gg,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as sevendj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDDJ=98) hh,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as sevenplusdj FROM c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.SSHDID=b.ID and b.ID not in(?)  and a.hdszxzqh in(?) and a.HDDJ=99) ii)) ll";
        return super.find(new SQL(sql, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs, hdids, xzqhs)).getData();
    }

    public List queryJsy62TableById(String ids) {
        String sql = "select * from (select a.hdlc,b.onewh,c.twowh,d.threewh,a.whss,a.whdk,a.whwqbj,a.whssbzl,a.hbwhzcl from (\n" +
                "select ifnull(round(sum(hdlc),2),0) as HDLC ,min(WHSS) as WHSS,max(WHDK) as WHDK,max(WHWQBJ) as WHWQBJ,max(WHSSBZL) as WHSSBZL,MAX(HBWHZCL) as HBWHZCL from c_hd_hduanjcxx\n" +
                "where ID in(?)) a,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as onewh from c_hd_hduanjcxx\n" +
                "where ID in(?) and HDWHLB=162) b,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as twowh from c_hd_hduanjcxx\n" +
                "where ID in(?) and HDWHLB=163) c,\n" +
                "(select ifnull(round(sum(hdlc),2),0) as threewh from c_hd_hduanjcxx\n" +
                "where ID in(?) and HDWHLB=164) d) e";
        return super.find(new SQL(sql, ids, ids, ids, ids)).getData();
    }

    public List queryWhhzById(String ids) {
        String sql = "SELECT round(SUM(hdlc),2) as hdzc,'' as onewh,'' as twowh,'' as threewh,MIN(ss) as ss,MIN(dk) as kd,MIN(wqbj) as wqbj,MIN(ssbzl) as ssbzl,MIN(whzcl) as whbzl from c_bh_whhz\n" +
                "where id in(?)";
        return super.find(new SQL(sql, ids)).getData();
    }

    public List queryJsy63TableById(int id) {
        String sql = "SELECT a.zlc,b.zdlc,a.hbwhzcl from (\n" +
                "(select ifnull(round(sum(HBSBLC),2),0) as zlc,ifnull(max(HBWHZCL),0) as HBWHZCL from c_hd_hduanjcxx\n" +
                "where SSHDID=?) a,\n" +
                "(select ifnull(round(sum(HBSBLC),2),0) as zdlc from c_hd_hduanjcxx\n" +
                "where SSHDID=? and HBPBLB='重点') b\n" +
                ")";
        return super.find(new SQL(sql, id, id)).getData();
    }

    public List queryJsy63TableLtId(String id) {
        String sql = "SELECT a.zlc,b.zdlc,a.hbwhzcl from (\n" +
                "(select ifnull(round(sum(HBSBLC),2),0) as zlc,max(HBWHZCL) as HBWHZCL from c_hd_hduanjcxx\n" +
                "where SSHDID>?) a,\n" +
                "(select ifnull(round(sum(HBSBLC),2),0) as zdlc from c_hd_hduanjcxx\n" +
                "where SSHDID>? and HBPBLB='重点') b\n" +
                ")";
        return super.find(new SQL(sql, id, id)).getData();
    }

    public void addCBbBb(CBbJSYOriBb bb, List<List<TableCell>> data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(data);
        byte[] bytes = out.toByteArray();
        oos.close();
        Session session = getCurrentSession();
        bb.setTableobj(session.getLobHelper().createBlob(bytes));
        super.save(bb);
    }

    public CBbJSYOriBb queryJSYTableFromDB(int tableindex) {
        return (CBbJSYOriBb) super.findUnique(new CBbJSYOriBb(), "tableindex", tableindex);
    }

    public void delCBbBb(int tableindex) {
        String hql = "delete from CBbJSYOriBb where tableindex=?";
        super.delete(new HQL(hql, tableindex));
    }

    public boolean ifBaobiaoExist(String baobiaokey, String baobiaotime) {
        String hql = "select count(a) from CBfBaoBiao a where a.baobiaokey='?' and a.baobiaotime = '?'";
        long cnt = super.count(new HQL(hql, baobiaokey, baobiaotime));
        return cnt <= 0 ? false : true;
    }

    public List<CBfBaoBiao> loadBaoBiao(String baobiaokey, String baobiaotime) {
        String hql = "select a from CBfBaoBiao a where a.baobiaokey='?' and a.baobiaotime = '?'";
        return (List<CBfBaoBiao>) super.find(new HQL(hql, baobiaokey, baobiaotime)).getData();
    }

    public List queryLcflGgXZQH(String xzqh) {
        String sql = "SELECT aa.hdmc,round(aa.hdlc,2) as hdlc,round(bb.onelc,2) as onelc,round(cc.twolc,2) as twolc,round(dd.threelc,2) as threelc,round(ee.fourlc,2) as fourlc,round(ff.fivelc,2) as fivelc,round(gg.sixlc,2) as sixlc,round(hh.sevenlc,2) as sevenlc,round(ii.sevenpluslc,2) as sevenpluslc from (\n" +
                "(SELECT a.sshdid,b.hdmc, sum(a.HDLC) as hdlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1\n" +
                "GROUP BY a.SSHDID) aa \n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as onelc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=92 \n" +
                "GROUP BY a.SSHDID) bb ON aa.sshdid=bb.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as twolc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=93 \n" +
                "GROUP BY a.SSHDID) cc ON aa.sshdid=cc.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as threelc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=94 \n" +
                "GROUP BY a.SSHDID) dd ON aa.sshdid=dd.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as fourlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=95 \n" +
                "GROUP BY a.SSHDID) ee ON aa.sshdid=ee.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as fivelc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=96 \n" +
                "GROUP BY a.SSHDID) ff ON aa.sshdid=ff.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as sixlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=97 \n" +
                "GROUP BY a.SSHDID) gg ON aa.sshdid=gg.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as sevenlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=98\n" +
                "GROUP BY a.SSHDID) hh ON aa.sshdid=gg.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as sevenpluslc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=1 and a.HDDJ=99 \n" +
                "GROUP BY a.SSHDID) ii ON aa.sshdid=ii.sshdid\n" +
                ")order by aa.sshdid asc";
        return super.find(new SQL(sql, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh)).getData();
    }

    public List queryLcflZxXZQH(String xzqh) {
        String sql = "SELECT aa.hdmc,round(aa.hdlc,2) as hdlc,round(bb.onelc,2) as onelc,round(cc.twolc,2) as twolc,round(dd.threelc,2) as threelc,round(ee.fourlc,2) as fourlc,round(ff.fivelc,2) as fivelc,round(gg.sixlc,2) as sixlc,round(hh.sevenlc,2) as sevenlc,round(ii.sevenpluslc,2) as sevenpluslc from (\n" +
                "(SELECT a.sshdid,a.HDSZXZQH,b.hdmc, sum(a.HDLC) as hdlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0\n" +
                "GROUP BY a.SSHDID) aa \n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as onelc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=92\n" +
                "GROUP BY a.SSHDID) bb ON aa.sshdid=bb.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as twolc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=93\n" +
                "GROUP BY a.SSHDID) cc ON aa.sshdid=cc.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as threelc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=94\n" +
                "GROUP BY a.SSHDID) dd ON aa.sshdid=dd.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as fourlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=95\n" +
                "GROUP BY a.SSHDID) ee ON aa.sshdid=ee.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as fivelc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=96\n" +
                "GROUP BY a.SSHDID) ff ON aa.sshdid=ff.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as sixlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=97\n" +
                "GROUP BY a.SSHDID) gg ON aa.sshdid=gg.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as sevenlc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=98\n" +
                "GROUP BY a.SSHDID) hh ON aa.sshdid=hh.sshdid\n" +
                "LEFT JOIN\n" +
                "(SELECT a.sshdid,sum(a.HDLC) as sevenpluslc from c_hd_hduanjcxx a,c_hd_hdaojcxx b\n" +
                "where a.HDSZXZQH in(?) and a.SSHDID=b.ID and b.SFGG=0 and a.HDDJ=99\n" +
                "GROUP BY a.SSHDID) ii ON aa.sshdid=ii.sshdid\n" +
                ")\n" +
                "ORDER BY aa.HDSZXZQH,aa.sshdid";
        return super.find(new SQL(sql, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh, xzqh)).getData();
    }

    public List queryJsy63TableByNotHdid(String hdids, String xzqhs) {
        String sql = "SELECT a.zlc,b.zdlc,a.hbwhzcl from (\n" +
                "(select ifnull(round(sum(HBSBLC),2),0) as zlc,ifnull(max(HBWHZCL),0) as HBWHZCL from c_hd_hduanjcxx\n" +
                "where SSHDID not in(?) and hdszxzqh in(?)) a,\n" +
                "(select ifnull(round(sum(HBSBLC),2),0) as zdlc from c_hd_hduanjcxx\n" +
                "where SSHDID not in(?) and hdszxzqh in(?) and HBPBLB='重点') b\n" +
                ")";
        return super.find(new SQL(sql, hdids, xzqhs, hdids, xzqhs)).getData();
    }

    public BaseQueryRecords<Object[]> queryHdQlByYtfl(List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.hdmc,a.ytfl,c.ATTRDESC from c_hd_ql a "
                + " right join c_hd_hdaojcxx b on b.id = a.sshdaoid right join c_zd_appattribute c on a.ytfl = c.id "
                + "where a.szxzqh in (";
        for (int i = 0; i < xzqhs.size(); i++) {
            sql += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                sql += ",";
        }
        sql += ") GROUP BY b.id,a.ytfl";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdGxByGxzl(List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.hdmc, a.zl,c.ATTRDESC from c_hd_lx a "
                + "right join c_hd_hdaojcxx b on a.sshdaoid = b.id right join c_zd_appattribute c on a.zl = c.id "
                + "where a.szxzqh in (";
        for (int i = 0; i < xzqhs.size(); i++) {
            sql += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                sql += ",";
        }
        sql += ") GROUP BY b.id,a.zl";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdHbByBzfl(List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.hdmc, a.lx,c.ATTRDESC from c_hd_hb a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.ID right join c_zd_appattribute c on a.lx = c.ID "
                + " where a.szxzqh in (";
        for (int i = 0; i < xzqhs.size(); i++) {
            sql += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                sql += ",";
        }
        sql += ") GROUP BY b.id,a.lx";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdMtByMtlx(List<CZdXzqhdm> xzqhs) {
        String xzqhstr = " (";
        for (int i = 0; i < xzqhs.size(); i++) {
            xzqhstr += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                xzqhstr += ",";
        }
        xzqhstr += ") ";
        String sql = "select sum(z.cnt), z.name,z.lx,z.id from( "
                + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_kymt a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                + "where a.szxzqh in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_hymt a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                + "where a.szxzqh in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_gwmt a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                + "where a.szxzqh in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_mbq a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                + "where a.szxzqh in "
                + xzqhstr
                + "group by b.id, a.lx "
                + "UNION "
                + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_fwq a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                + "where a.szxzqh in " + xzqhstr
                + "group by b.id, a.lx) z " + "GROUP BY z.name,z.lx ";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdQlByYtfl(String ids, List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.hdmc,a.ytfl,c.ATTRDESC from c_hd_ql a "
                + " right join c_hd_hdaojcxx b on b.id = a.sshdaoid right join c_zd_appattribute c on a.ytfl = c.id "
                + "where 1=1 ";
        if (ids != null && !"".equals(ids)) {
            sql += " and b.id in(" + ids + ")";
        }
        if (xzqhs.size() > 0) {
            sql += " and a.szxzqh in (";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        sql += "  GROUP BY b.id,a.ytfl";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdGxByGxzl(String ids, List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.hdmc, a.zl,c.ATTRDESC from c_hd_lx a "
                + "right join c_hd_hdaojcxx b on a.sshdaoid = b.id right join c_zd_appattribute c on a.zl = c.id "
                + "where 1=1";
        if (ids != null && !"".equals(ids)) {
            sql += " and b.id in(" + ids + ")";
        }
        if (xzqhs.size() > 0) {
            sql += " and a.szxzqh in (";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        sql += " GROUP BY b.id,a.zl";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdHbByBzfl(String ids, List<CZdXzqhdm> xzqhs) {
        String sql = "select count(a.mc) as cnt,b.id,b.hdmc, a.lx,c.ATTRDESC from c_hd_hb a "
                + " right join c_hd_hdaojcxx b on a.sshdaoid = b.ID right join c_zd_appattribute c on a.lx = c.ID "
                + " where 1=1";
        if (ids != null && !"".equals(ids)) {
            sql += " and b.id in(" + ids + ")";
        }
        if (xzqhs.size() > 0) {
            sql += " and a.szxzqh in (";
            for (int i = 0; i < xzqhs.size(); i++) {
                sql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    sql += ",";
            }
            sql += ")";
        }
        sql += " GROUP BY b.id,a.lx";
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }

    public BaseQueryRecords<Object[]> queryHdMtByMtlx(String ids, List<CZdXzqhdm> xzqhs) {
        String xzqhstr = " (";
        for (int i = 0; i < xzqhs.size(); i++) {
            xzqhstr += xzqhs.get(i).getId();
            if (i != xzqhs.size() - 1)
                xzqhstr += ",";
        }
        xzqhstr += ") ";
        String sql = "";
        if (ids != null && !"".equals(ids)) {
            sql = "select sum(z.cnt), z.name,z.lx,z.id from( "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_kymt a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr + " and b.id in(" + ids + ") "
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_hymt a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr + " and b.id in(" + ids + ") "
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_gwmt a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr + " and b.id in(" + ids + ") "
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_mbq a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr + " and b.id in(" + ids + ") "
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_fwq a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in " + xzqhstr + " and b.id in(" + ids + ") "
                    + "group by b.id, a.lx) z " + "GROUP BY z.name,z.lx ";
        } else {
            sql = "select sum(z.cnt), z.name,z.lx,z.id from( "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_kymt a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_hymt a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_gwmt a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_mbq a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in "
                    + xzqhstr
                    + "group by b.id, a.lx "
                    + "UNION "
                    + "select count(a.mc) as cnt,b.id, b.hdmc as name, a.lx as lx from c_hd_fwq a "
                    + " right join c_hd_hdaojcxx b on a.sshdaoid = b.id "
                    + "where a.szxzqh in " + xzqhstr
                    + "group by b.id, a.lx) z " + "GROUP BY z.name,z.lx ";
        }
        @SuppressWarnings("unchecked")
        BaseQueryRecords<Object[]> ret = (BaseQueryRecords<Object[]>) super
                .find(new SQL(sql));
        return ret;
    }
}
