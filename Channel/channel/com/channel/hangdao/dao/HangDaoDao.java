package com.channel.hangdao.dao;

import com.channel.bean.HdaoBean;
import com.channel.model.hd.CHdHdaojcxx;
import com.channel.model.user.CZdXzqhdm;
import com.common.action.result.BaseResult;
import com.common.dao.BaseQueryRecords;

import java.util.List;

public interface HangDaoDao {
    public BaseQueryRecords<CHdHdaojcxx> searchAllHangDao(int sfgg, List<CZdXzqhdm> xzqhs, String content);

    public BaseQueryRecords<CHdHdaojcxx> queryAllHangDao(int sfgg, List<CZdXzqhdm> xzqhs);

    public void addHangDao(CHdHdaojcxx hdao);

    public CHdHdaojcxx queryHangDaoByHdid(Integer hdid);

    public CHdHdaojcxx queryHangDaoById(int id);

    public void delHangDao(CHdHdaojcxx hangdao);

    public void updateHangDao(CHdHdaojcxx hdao);

    public BaseQueryRecords queryHangDaoHduanCnt(List<CHdHdaojcxx> hdaolist);

    public BaseQueryRecords<CHdHdaojcxx> searchHangDaoSjbh(int sfgg, int sjbh, String content);

    public BaseQueryRecords<CHdHdaojcxx> searchHangDaoHddj(int sfgg, List<CZdXzqhdm> xzqhs, int hdid, int hddj, String content);

    public BaseQueryRecords<CHdHdaojcxx> queryHangDaoZero(int sfgg,String name);

    public CHdHdaojcxx queryHangDaoByHdBh(String hdbh);

    public BaseQueryRecords zhcxHdao(int sfgg, int sjbh, String content, String xzqh);

    public BaseQueryRecords zhcxHdao(String xzqh);

    public BaseQueryRecords zhcxHdaoInfo(String sqlXzqh, int sfgg, String content, int page, int rows);

    public List<Object[]> importHdao();

    public List<CHdHdaojcxx> queryHdidInXzqh(String s);

    public BaseQueryRecords<CHdHdaojcxx> queryHangDaoInXzqh(int sfgg, String manxzqh);
}
