package com.zjport.smart.service;

import com.common.base.BaseRecords;

import java.util.List;

/**
 * Created by EastLanCore on 16/10/8.
 */
public interface ICameraService {

    /**
     * 登录
     * @return
     */
    String userLogin();

    /**
     * 获取全部摄像头信息
     * @param sessionId 登录后sessionId
     * @return
     */
    List getAllCamera(String sessionId,int page,int rows);

    /**
     * 获取摄像头播放参数
     * @param sessionId sessionId
     * @param cameraId 摄像头ID
     * @return
     */
    String getPreviewParam(String sessionId,String cameraId);

    /**
     * 跟新摄像头信息
     *
     * @return the boolean
     * @Auth Will
     * @Date 2016 -10-12 17:00:12
     */
    boolean updateCameraInfo();

    /**
     * 根据摄像头id查询摄像头param
     *
     * @param cameraIds the camera ids
     * @return the list
     * @Auth Will
     * @Date 2016 -10-12 17:00:12
     */
    List<String> queryParamentByIds(Integer[] cameraIds);

    void userOnlineHeartbeat();

    boolean updateCamera();

    List<String> queryParamentLocationByIds(Integer[] cameraIds);

    Integer[] queryCameraIdsByHduanId(Integer hduanId);

    BaseRecords queryCamerasByHdaoId(Integer hdaoId);

    List<String[]> queryCameraInfosByHdaoId(Integer id, Integer hduanId);
}
