package com.channel.hangduan.dao;

import java.util.List;

import com.channel.model.hd.CHdHduanjcxx;
import com.channel.model.user.CZdXzqhdm;
import com.common.action.result.BaseResult;
import com.common.dao.BaseQueryRecords;

public interface HangDuanDao {
    public BaseQueryRecords<CHdHduanjcxx> searchAllHangDuan(List<CZdXzqhdm> xzqhs, int sshdid, int hddj, String content);

    public BaseQueryRecords<CHdHduanjcxx> queryHangDuanBySshdid(int sshdid, List<CZdXzqhdm> xzqhs);

    public CHdHduanjcxx queryHangDuanById(int id);

    public void delHangDuan(CHdHduanjcxx hangduan);

    public CHdHduanjcxx queryHangDuanByHdid(Integer hdid);

    public void addHangDuan(CHdHduanjcxx hangduan);

    public void updateHangDuan(CHdHduanjcxx hangduan);

    public List<CHdHduanjcxx> queryHDuanInXzqh(String strin);

    public CHdHduanjcxx queryMaxHdbh(Integer sshdid);

    public Boolean queryHdbhExisted(String hdbh, Integer sshdid);

    public Boolean queryIsQdhd(String trim, int id);

    public Boolean queryIsZdhd(String trim, int id);

    public BaseQueryRecords queryHangDuanByXzqh(List<CZdXzqhdm> xzqhs);

    public BaseQueryRecords searchHangDuanHddj(Integer sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content);

    public CHdHduanjcxx queryHangDuanByBh(String hduanbh, int hdaoid);

    public BaseQueryRecords<CHdHduanjcxx> queryXzqhBySjbh(int sjbh);

    public BaseQueryRecords<CHdHduanjcxx> zhcxHduanInfo(int sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content, int page, int rows);

   public List countAppByHdid(int sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content);

    public List countTotalByHdid(int sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content);

    public List countAppByHdid(Integer sshdid, List<CZdXzqhdm> xzqhs, int hddj, String content, int page, int rows);

    public BaseQueryRecords<CHdHduanjcxx> queryHangDuanByXzqhSshdid(String totalxzqh, int sshdid);

    public List<Object[]> importHduan();

    public CHdHduanjcxx queryHangDuanByQdzdSshdid(Integer id, String qd, String zd);
}
