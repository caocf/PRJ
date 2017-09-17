package com.channel.hangduan.service;

import java.util.List;

import com.channel.model.hd.CHdHduanjcxx;
import com.channel.model.user.CZdXzqhdm;
import com.common.action.result.BaseResult;

public interface HangDuanService {

    public BaseResult searchAllHangDuan(int xzqh, int sshdid, int hddj, String content);

    public BaseResult queryHangDuanBySshdid(int sshdid, int xzqh);

    public BaseResult queryHangDuanInfo(int id);

    public BaseResult delHangDuan(int id);

    public BaseResult delHangDuans(int hdaoid, List<CZdXzqhdm> xzqhdms);

    public BaseResult addHangDuan(CHdHduanjcxx hangduan);

    public BaseResult updateHangDuan(CHdHduanjcxx chdhduanjcxx);

    public List<CHdHduanjcxx> queryHDuanInXzqh(String strin);

    public BaseResult queryMaxHdbh(Integer sshdid);

    public BaseResult queryHdbhExisted(String hdbh, Integer sshdid);

    public BaseResult queryHangDuanByXzqh(int xzqh);

    public BaseResult searchHangDuanHddj(int xzqh, Integer sshdid, int hddj, String content);

    public BaseResult zhcxHduan(int xzqhid, int sshdid, int hddj, String content);

    public BaseResult exportHduan(int xzqh, Integer sshdid);

    public BaseResult zhcxHduanInfo(int xzqh, Integer sshdid, int hddj, String content, int page, int rows);

    public BaseResult importHduan();

    public BaseResult exportHduanInfo(int id);
}
