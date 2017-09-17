package com.channel.hangdao.service;

import com.channel.model.hd.CHdHdaojcxx;
import com.common.action.result.BaseResult;
import com.common.dao.BaseQueryRecords;

public interface HangDaoService {

    public BaseResult searchAllHangDao(int sfgg, int xzqh, String content);

    public BaseResult queryAllHangDao(int sfgg, int xzqh);

    public BaseResult addHangDao(CHdHdaojcxx hdao);

    public BaseResult queryHangDaoInfo(int id, int xzqh);

    public BaseResult delHangDao(int id, int xzqh);

    public BaseResult updateHangDao(CHdHdaojcxx chdhdaojcxx);

    public BaseResult searchHangDaoSjbh(int sfgg, int sjbh, String content);

    public BaseResult searchHangDaoHddj(int sfgg, int xzqh, int hdid, int hddj, String content);

    public BaseResult zhcxHdao(int xzqh);

    public BaseResult exportHdao(int xzqh);

    public BaseResult zhcxHdaoInfo(int xzqh, int sfgg, String content, int page, int rows);

    public BaseResult importHdao();

    public BaseResult exportHdaoInfo(int id, int xzqh);

    public BaseQueryRecords<CHdHdaojcxx> queryHangDao(int i, int xzqh);

    public BaseResult queryHdManXzqh(int sfgg, int xzqh);
}
