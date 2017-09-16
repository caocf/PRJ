package com.zjport.smart.controller;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.framework.FileDownload;
import com.zjport.log.LogService;
import com.zjport.model.TCurise;
import com.zjport.model.TCurisePhoto;
import com.zjport.smart.service.ICameraService;
import com.zjport.smart.service.IChannelService;
import com.zjport.smart.service.ICruiseService;
import com.zjport.util.ExportExcel;
import com.zjport.util.Value;
import com.zjport.web.UserSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * Created by Will on 2016/9/28 10:37.
 */
@Controller
@RequestMapping("/smart")
public class CruiseController {

    private static final String PIC_ROOT = "/smart/upload";
    private static final Logger log = LoggerFactory.getLogger(CruiseController.class);

    @Resource(name = "cruiseService")
    private ICruiseService cruiseService;
    @Resource(name = "cameraService")
    private ICameraService cameraService;
    @Resource(name = "channelService")
    private IChannelService channelService;
    @Resource(name = "logService")
    private LogService logService;


    /**
     * 开始巡航
     *
     * @param request the request
     * @param curise  the curise
     * @param model   the model
     * @return the string
     * @Auth Will
     * @Date 2016 -09-29 16:16:09
     */
    @RequestMapping(value = "/start")
    public String startCruise(HttpServletRequest request, Integer[] cameraIds, TCurise curise, Model model, Integer timeOut, Integer hduanId, Integer order) {

        String userId = UserSession.getInstance(request).getStUserId();
        List<String[]> map = cameraService.queryCameraInfosByHdaoId(hduanId, order);
//逻辑优化        cameraIds = cameraService.queryCameraIdsByHduanId(hduanId);
//        Integer[] cids = channelService.queryCameraIds(cameraIds);//无用
        curise.setDtCuriseBegin(new Timestamp(System.currentTimeMillis()));
        curise.setDtCuriseEnd(null);
        curise.setStCruiseUser(Integer.valueOf(userId));
        curise.setStCuriseMiddleId(UUID.randomUUID().toString());
        model.addAttribute(curise);
        model.addAttribute("timeOut", timeOut);
//逻辑优化        model.addAttribute("perviewParam", cameraService.queryParamentByIds(cameraIds));//播放信息
//逻辑优化        model.addAttribute("location",cameraService.queryParamentLocationByIds(cameraIds));//经纬度信息
        log.info("{}", map.toString());
        model.addAttribute("perviewParam", map);
        cruiseService.saveCruise(curise);
        log.debug(model.toString());
        logService.saveLog(Value.of(userId, 0), "开始巡航");
        return "smart/cruiesing";
    }

    /**
     * 结束巡航
     *
     * @param request the request
     * @param curise  the curise
     * @return the string
     * @Auth Will
     * @Date 2016 -09-29 16:16:21
     */
    @ResponseBody
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public BaseResult endCruise(HttpServletRequest request, TCurise curise) {
        log.debug("{}", curise.getStCuriseMiddleId());
        TCurise dbCurise = cruiseService.queryCruisebyMiddeId(curise.getStCuriseMiddleId());
        dbCurise.setStCuriseContent(curise.getStCuriseContent());
        dbCurise.setDtCuriseEnd(new Timestamp(System.currentTimeMillis()));
        cruiseService.updateCruise(dbCurise);

        String userId = UserSession.getInstance(request).getStUserId();
        logService.saveLog(Value.of(userId, 0), "结束巡航");
        return new BaseResult(BaseResult.RESULT_OK, "结束巡航");
    }

    /**
     * 保存巡航中截图
     *
     * @param request  the request
     * @param cruiseId the cruise id
     * @param picBs64  the pic bs 64
     * @param describe the describe
     * @param address  the address
     * @return the base result
     * @Auth Will
     * @Date 2016 -09-29 16:16:21
     */
    @ResponseBody
    @RequestMapping(value = "/saveCruisePhoto", method = RequestMethod.POST)
    public BaseResult saveCruisePhoto(HttpServletRequest request, String cruiseId, String picBs64, String describe, String address) {
        String rootPath = request.getServletContext().getRealPath(PIC_ROOT);
        TCurisePhoto tp = new TCurisePhoto();
        tp.setDtCurise(new Timestamp(System.currentTimeMillis()));
        tp.setStCruiseAddress(address);
        tp.setStCuriseMiddleId(cruiseId);
        tp.setStPhotoDescribe(describe);
        String str = cruiseService.savePic(PIC_ROOT, rootPath, picBs64);
        tp.setStSource(str);

        String userId = UserSession.getInstance(request).getStUserId();
        logService.saveLog(Value.of(userId, 0), "添加巡航截图");
        return cruiseService.saveCruisePhoto(tp);
    }

    /**
     * 查询巡航历史记录
     *
     * @param request   HttpServletRequest
     * @param page      分页页码
     * @param rows      　分页行数
     * @param startTime 　起始时间　yyyy-MM-dd
     * @param endTime   结束时间
     * @param channel   航道
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public BaseResult queryCruiseHistory(HttpServletRequest request, String page, String rows, String startTime, String endTime, String channel) {
        int i_page = Value.of(page, 0);
        int i_rows = Value.of(rows, 10);
        BaseResult br = new BaseResult();
        try {

            if (!StringUtils.isBlank(startTime) && startTime.length() == 10) {
                startTime = startTime + " 00:00:00";
            }
            if (!StringUtils.isBlank(endTime) && endTime.length() == 10) {
                endTime = endTime + " 23:59:59";
            }
            String userId = UserSession.getInstance(request).getStUserId();//TODO 暂时只能查看自己的
            if ("null".equals(userId)) {
                userId = null;
            }
            BaseRecords<TCurise> curises = cruiseService.queryCruiseHistory(userId, i_page, i_rows, startTime, endTime, channel);
            br.setResultcode(BaseResult.RESULT_OK);
            if (curises.getData() == null || curises.getData().size() == 0) {
                br.setResultdesc("未找到符合条件是数据");
            } else {
                br.setResultdesc("查询成功");
                br.setRecords(curises);
            }
        } catch (Exception e) {
            log.error("{}：{}", "查询异常", e.getMessage());
        }
        return br;
    }

    /**
     * 删除巡航历史
     *
     * @param stCruiseId the st cruise id
     * @return the base result
     * @Auth Will
     * @Date 2016 -10-12 16:56:09
     */
    @ResponseBody
    @RequestMapping(value = "/deletedHistory", method = RequestMethod.POST)
    public BaseResult deleteHistory(HttpServletRequest request, int[] stCruiseId) {

        String userId = UserSession.getInstance(request).getStUserId();
        logService.saveLog(Value.of(userId, 0), "删除巡航记录");
        return cruiseService.deleteCruisesById(stCruiseId);
    }

    /**
     * 跟新摄像头参数信息
     *
     * @return the string
     * @Auth Will
     * @Date 2016 -10-12 16:56:09
     */
    @ResponseBody
    @RequestMapping(value = "/ucp", method = RequestMethod.GET)
    public String updateCameraParam() {
        cameraService.updateCameraInfo();
        return "ok";
    }

    /**
     * 跟新摄像头信息
     *
     * @return the string
     * @Auth Will
     * @Date 2016 -10-12 16:56:09
     */
    @ResponseBody
    @RequestMapping(value = "/uc", method = RequestMethod.GET)
    public String updateCamera() {
        cameraService.updateCamera();
        return "ok";
    }

    /**
     * 跟新巡航截图历史
     *
     * @param request the request
     * @param photo   the photo
     * @return the base result
     * @Auth Will
     * @Date 2016 -10-12 16:56:09
     */
    @ResponseBody
    @RequestMapping(value = "/updatePhoto", method = RequestMethod.POST)
    public BaseResult updatePhoto(HttpServletRequest request, TCurisePhoto photo) {
        BaseResult result = new BaseResult();
        try {
            TCurisePhoto tCurisePhoto = cruiseService.queryPhotoByID(photo.getStCruisePhotoId());
            tCurisePhoto.setStPhotoDescribe(photo.getStPhotoDescribe());
            cruiseService.updateCruisePhoto(tCurisePhoto);
            result.setResultcode(BaseResult.RESULT_OK);
            result.setResultdesc("修改成功");
        } catch (Exception e) {
            log.error("", e);
            result.setResultcode(BaseResult.RESULT_FAILED);
            result.setResultdesc("修改失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 巡航详情.
     *
     * @param request  the request
     * @param cruiseId the cruise id
     * @return the base result
     * @Auth Will
     * @Date 2016 -10-12 17:10:33
     */
    @ResponseBody
    @RequestMapping(value = "/queryCruiseDesc", method = RequestMethod.POST)
    public BaseResult queryCruiseDesc(HttpServletRequest request, Integer cruiseId) {
        BaseResult result = null;
        try {
            result = cruiseService.queryCruiseDesc(cruiseId);
        } catch (Exception e) {
            log.error("", e);
            result = BaseResult.newResultFailed(e.getMessage());
        }
        return result;
    }

    /**
     * Download history.
     *
     * @param response  the response
     * @param request   the request
     * @param startTime the start time
     * @param endTime   the end time
     * @param channel   the channel
     * @Auth Will
     * @Date 2016 -10-18 11:11:48
     */
    @RequestMapping(value = "/downloadHistory", method = RequestMethod.GET)
    public void downloadHistory(HttpServletResponse response, HttpServletRequest request, String startTime, String endTime, String channel) {
        if (!StringUtils.isBlank(startTime) && startTime.length() == 10) {
            startTime = startTime + " 00:00:00";
        }
        if (!StringUtils.isBlank(endTime) && endTime.length() == 10) {
            endTime = endTime + " 23:59:59";
        }
        String userId = UserSession.getInstance(request).getStUserId();//TODO 暂时只能查看自己的
        if ("null".equals(userId)) {
            userId = null;
        }
        List<TCurise> curises = cruiseService.queryCruiseHistory(userId, 0, 0, startTime, endTime, channel).getData();

        //解决中文乱码问题
        String filename = "日志记录.xls";
        String encodeName = FileDownload.filenameEncode(request, filename);
        if (encodeName == null) {
            try {
                encodeName = "file=" + (URLEncoder.encode(filename, "UTF-8")).getBytes();
            } catch (Exception e) {
                encodeName = "file=log.xls";
            }
        }
        ExportExcel<TCurise> exportExcel = new ExportExcel<>();
        String[] headers = {"巡航编号", "巡航航道", "巡航起点", "巡航终点", "巡航起始时间", "巡航结束时间", "巡航用户id", "巡航关联键", "删除标记", "巡航结果"};
        try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream()); InputStream fis = exportExcel.exportExcel("巡航日志", headers, curises, "yyyy-MM-dd hh:mm:ss", "省综合管理与服务平台")) {


            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            response.reset();

            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));

            response.setContentType("application/x-download");
            response.setHeader("Content-disposition", "attachment;" + encodeName);

            toClient.write(buffer);
            toClient.flush();

            logService.saveLog(Value.of(userId, 0), "导出巡航");
        } catch (IOException e) {
            log.error("文件导出异常:{}", e.getMessage());
        }
    }

    /**
     * 查询航道
     *
     * @param regionID the region id
     * @return the base result
     * @Auth Will
     * @Date 2016 -10-18 11:11:48
     */
//    @ResponseBody
//    @RequestMapping(value="/queryChannel")
//    public BaseResult queryChannel(String regionID){
//        BaseRecords<ChannelForTest>  cs =  channelService.queryAllChannel(regionID);
//        return BaseResult.newResultOK(cs);
//    }

    /**
     * 查询航道上的监控 起点为空查询终点之前所有点，终点为空查询起点后所有点，都为空查询全部点，起点小于终点正序（不适用一方为空），起点大于终点倒序（不适用一方为空）
     *
     * @param channelId the 航道id
     * @param start     the 监控起点
     * @param end       the 监控终点
     * @return the base 起点与终点间全部监控点
     * @Auth Will
     * @Date 2016 -10-18 11:11:48
     */
//    @ResponseBody
//    @RequestMapping(value="/queryMonitor")
//    public BaseResult queryMonitor(Integer channelId,Integer start,Integer end){
//        BaseRecords<MonitorForTest> ms = channelService.queryAllMonitor(channelId,start,end);
//        return BaseResult.newResultOK(ms);
//    }

    /**
     * 查询摄像头播放参数
     *
     * @param cid the cid
     * @return the base result
     * @Auth Will
     * @Date 2016 -10-24 09:21:00
     */
    @ResponseBody
    @RequestMapping(value = "/queryCameraParament")
    public BaseResult queryCameraParament(int[] cid) {
        Integer[] integers = new Integer[cid.length];
        for (int i = 0; i < cid.length; i++) {
            integers[i] = cid[i];
        }
        List<String> param = cameraService.queryParamentByIds(integers);
        log.debug("list:\n{}", param);
        return BaseResult.newResultOK(param);
    }

    /**
     * 根据航道查询摄像头信息
     *
     * @param hdaoId the hdao id
     * @return the base result
     * @Auth Will
     * @Date 2016 -12-13 14:23:01
     */
    @ResponseBody
    @RequestMapping(value = "/queryCameras")
    public BaseResult queryCameras(Integer hdaoId) {
        BaseRecords baseRecords = cameraService.queryCamerasByHdaoId(hdaoId);
        return BaseResult.newResultOK(baseRecords);
    }
}
