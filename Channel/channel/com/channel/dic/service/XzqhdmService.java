package com.channel.dic.service;

import com.channel.model.user.CZdXzqhRela;
import com.channel.model.user.CZdXzqhdm;
import com.common.action.result.BaseResult;
import com.common.utils.tree.TreeService;

public interface XzqhdmService extends TreeService<CZdXzqhdm, CZdXzqhRela> {

    public BaseResult queryAllXzqh(int id, boolean isroot);

    public BaseResult queryXzqhInfo(int id);

    public BaseResult delXzqh(int id);

    public BaseResult updateXzqh(int id, int pid ,String name, String code);

    public BaseResult addXzqh(String code, String name, int pid);

    /**
     * 查询当前id的行政区划所在的市行政区划
     */
    public CZdXzqhdm queryShiXzqh(int id);

    public BaseResult queryTreeXzqh(int xzqh, boolean b);
}
