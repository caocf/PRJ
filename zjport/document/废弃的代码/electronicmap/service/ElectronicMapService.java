package com.zjport.electronicmap.service;

import com.common.base.BaseRecords;
import com.zjport.model.ZAcCameraInfo;
import com.zjport.model.ZAcRegionInfo;

/**
 * Created by Will on 2016/11/4 13:50.
 */
public interface ElectronicMapService{
    BaseRecords<ZAcRegionInfo> queryRegionList(int i);

    BaseRecords<ZAcCameraInfo> queryCameras(Integer regionId);
}
