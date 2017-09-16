package com.zjport.smart.service.impl;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.common.base.service.BaseService;
import com.zjport.smart.dao.IChannelDao;
import com.zjport.smart.service.IChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Will on 2016/10/18 12:07.
 */
@Repository("channelService")
public class ChannelServiceImpl extends BaseService implements IChannelService {
    private static final Logger log = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Resource(name = "channelDao")
    private IChannelDao channelDao;

//    @Override
//    public BaseRecords<ChannelForTest> queryAllChannel(String regionID) {
//        ObjectQuery query = new ObjectQuery(ChannelForTest.class);
//        return (BaseRecords<ChannelForTest>) channelDao.find(query);
//    }

    @Override
    public Integer[] queryCameraIds(int[] cameraIds) {
        String str = "select camera_id from mc_map where monitor_id in (?)";
        List<Integer> mids = new ArrayList<>();
        for (int i : cameraIds) {
            mids.add(i);
        }
        SQL sql = new SQL(str,mids);
        List<Integer> list = (List<Integer>) channelDao.queryBySQL(sql).getData();
        return list.toArray(new Integer[cameraIds.length]);
    }

//    @Override
//    public BaseRecords<MonitorForTest> queryAllMonitor(Integer channelId, Integer start, Integer end) {
//        boolean isEsc = true;
//        if (start != null && end != null) {
//            isEsc = start < end;
//        }
//        if (!isEsc) {
//            Integer temp = start;
//            start = end;
//            end = temp;
//        }
//        StringBuffer sb = new StringBuffer();
//        Map<String, Object> param = new HashMap<>();
//        sb.append(" from MonitorForTest as mt where mt.channelId = :channelId ");
//        param.put("channelId", channelId);
//        if (start != null) {
//            sb.append(" and mt.orderId >= :start");
//            param.put("start", start);
//        }
//        if (end != null) {
//            sb.append(" and mt.orderId <= :end");
//            param.put("end", end);
//        }
//        if (!isEsc) {
//            sb.append(" order by orderId desc");
//        }
//
//        HQL hql = new HQL(sb.toString(), param);
//        return (BaseRecords<MonitorForTest>) channelDao.findByHQL(hql);
//    }
}
