package com.zjport.smart.service;

/**
 * Created by Will on 2016/10/18 12:06.
 */
public interface IChannelService {

//    BaseRecords<ChannelForTest> queryAllChannel(String regionID);

//    BaseRecords<MonitorForTest> queryAllMonitor(Integer channelId, Integer start, Integer end);

    Integer[] queryCameraIds(int[] cameraIds);
}
