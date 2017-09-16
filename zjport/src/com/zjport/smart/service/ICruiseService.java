package com.zjport.smart.service;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.zjport.model.TCurise;
import com.zjport.model.TCurisePhoto;

import java.util.List;

/**
 * Created by Will on 2016/9/28 10:35.
 */
public interface ICruiseService {
    /**
     * 保存base64图片到硬盘返回文件相对路径
     *
     * @param relativePath 相对目录
     * @param realPath     绝对目录
     * @param picBs64      图片base64信息
     * @return the string
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    String savePic(String relativePath,String realPath, String picBs64);

    /**
     * 保存巡航图片到数据库
     *
     * @param tp 截图保存对象
     * @return the base result
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    BaseResult saveCruisePhoto(TCurisePhoto tp);

    /**
     * 保存巡航记录
     *
     * @param curise 巡航
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    void saveCruise(TCurise curise);

    /**
     * 更新巡航记录
     *
     * @param curise 巡航
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    void updateCruise(TCurise curise);

    /**
     * 更新巡航记录
     *
     * @param curises 巡航集合
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    void updateCruises(List<TCurise> curises);

    /**
     * 更新巡航记录
     *
     * @param curise 巡航
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    void deleteCruise(TCurise curise);

    /**
     * 更新巡航记录
     *
     * @param curises 巡航集合
     * @Auth Will
     * @Date 2016 -09-29 15:05:02
     */
    void deleteCruises(List<TCurise> curises);


    /**
     * 根据条件分页查询巡航历史
     *
     * @param userId    the user id
     * @param i_page    the page
     * @param i_rows    the rows
     * @param startTime the start time
     * @param endTime   the end time
     * @param channel   the channel
     * @return the base records
     * @Auth Will
     * @Date 2016 -10-12 16:53:50
     */
    BaseRecords<TCurise> queryCruiseHistory(String userId, int i_page, int i_rows, String startTime, String endTime, String channel);

    /**
     * 根据Id查询巡航记录
     *
     * @param stCruiseId the st cruise id
     * @return the t curise
     * @Auth Will
     * @Date 2016 -10-12 16:53:50
     */
    TCurise queryCruisebyId(Integer stCruiseId);

    /**
     * 根据中间键查询巡航记录
     *
     * @param stCuriseMiddleId the st curise middle id
     * @return the t curise
     * @Auth Will
     * @Date 2016 -10-12 16:53:50
     */
    TCurise queryCruisebyMiddeId(String stCuriseMiddleId);

    /**
     * 批量删除巡航记录
     *
     * @param stCruiseId 记录id
     * @return the base result
     * @Auth Will
     * @Date 2016 -10-12 16:53:50
     */
    BaseResult deleteCruisesById(int[] stCruiseId);

    /**
     * 根据id查询巡航截图记录
     *
     * @param stCruisePhotoId the st cruise photo id
     * @return the t curise photo
     * @Auth Will
     * @Date 2016 -10-12 16:53:51
     */
    TCurisePhoto queryPhotoByID(Integer stCruisePhotoId);

    /**
     * 跟新巡航截图记录
     *
     * @param tCurisePhoto the t curise photo
     * @Auth Will
     * @Date 2016 -10-12 16:53:51
     */
    void updateCruisePhoto(TCurisePhoto tCurisePhoto);

    BaseResult queryCruiseDesc(Integer cruiseId);
}
