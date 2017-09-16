package com.zjport.electronicmap.service.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.service.BaseService;
import com.zjport.electronicmap.dao.ElectronicMapDao;
import com.zjport.electronicmap.service.ElectronicMapService;
import com.zjport.model.ZAcCameraInfo;
import com.zjport.model.ZAcRegionInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Will on 2016/11/4 13:50.
 */
@Repository("electronicMapService")
public class ElectronicMapServiceImpl extends BaseService implements ElectronicMapService {

    @Resource(name = "electronicMapDao")
    private ElectronicMapDao dao;

    @Override
    public BaseRecords<ZAcCameraInfo> queryCameras(Integer regionId) {
        String hql = "from ZAcCameraInfo as zci";
        Map<String,Object> param = new HashMap<>();
        if(!regionId.equals(0)){
            hql += " where zci.regionId = :regionId";
            param.put("regionId",regionId);
        }
        return (BaseRecords<ZAcCameraInfo>) dao.findByHQL(new HQL(hql,param));
    }

    @Override
    public BaseRecords<ZAcRegionInfo> queryRegionList(int cId) {
        String hql = "from ZAcRegionInfo as zri where zri.controlUnitId =?";
        return (BaseRecords<ZAcRegionInfo>) dao.findByHQL(new HQL(hql,cId));
    }
}
