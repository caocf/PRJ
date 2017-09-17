package com.channel.appurtenance.service;

import com.channel.model.hd.CZdAppurtenance;
import com.channel.model.hd.CZdAppurtenanceRela;
import com.common.action.result.BaseResult;
import com.common.utils.tree.TreeService;

public interface AppurtenanceTypeService extends
        TreeService<CZdAppurtenance, CZdAppurtenanceRela> {

    public BaseResult queryAllParent();

    public BaseResult queryAllHduanParent(Integer hduanid);

    public BaseResult querySubClass(int parentid, int sshduanid);

    public BaseResult queryAllFswClass();

    public BaseResult searchAllHduanParent(int hduanid, int fswlx, String content);

    public BaseResult queryAppByType(int type);

    public BaseResult searchAllHduanXzqh(int xzqh, Integer hduanid, int fswlx, String content);
}
