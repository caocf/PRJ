package com.channel.user.service;

import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtDptRela;
import com.channel.model.user.CZdXzqhdm;
import com.common.action.result.BaseResult;
import com.common.utils.tree.TreeService;

import java.util.List;

public interface DptService extends TreeService<CXtDpt, CXtDptRela> {

    public BaseResult queryRoot();

    public BaseResult queryAllDpt(int id, boolean isroot);

    public BaseResult queryDptInfo(int id);

    public BaseResult delDpt(int id);

    public BaseResult addDpt(String name, int xzqh,
                             String dptdesc, int pid, List<Integer> manxzqhs, List<Integer> defaultroles);

    public BaseResult updateDpt(int id, String name,
                                int xzqh, String dptdesc, List<Integer> manxzqhs, List<Integer> defaultroles);

    public String queryDptName(int id);

    //获取辖区
    public List<CZdXzqhdm> queryDptManXzqh(int id);

    //查询当前部门所在的市局部门
    public CXtDpt queryShiDpt(int id);


    //查询当前部门所在的市局部门
    public CXtDpt queryChuDpt(int id);


    public BaseResult queryDptDefaultRoles(int dptid);

    public BaseResult queryXzqhDpt(int id, int xzqh);

    public BaseResult queryTreeDpt(int id, boolean isroot);
}
