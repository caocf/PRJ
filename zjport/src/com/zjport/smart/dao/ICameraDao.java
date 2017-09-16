package com.zjport.smart.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.BaseDao;
import com.zjport.video.model.ChannelCamera;

import java.util.List;
import java.util.Map;

/**
 * Created by Will on 2016/10/13 13:41.
 */
public interface ICameraDao extends BaseDao{
    List<ChannelCamera> queryParamentLocationByIds(Integer[] cameraIds);

    List<Integer> queryCameraIdsByHduanId(Integer hduanId);

    BaseRecords queryCamerasByHdaoId(Integer hdaoId);

    List<Map<String,Object>> queryCameraInfosByHdaoId(Integer hduanId,Integer order);
}
