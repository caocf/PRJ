package com.zjport.smart.service.impl;

import com.common.base.BaseRecords;
import com.common.base.service.BaseService;
import com.zjport.model.Camera;
import com.zjport.model.Parament;
import com.zjport.smart.ThirdInfoService.ThirdInfoService;
import com.zjport.smart.ThirdInfoService.ThirdInfoServicePortType;
import com.zjport.smart.dao.ICameraDao;
import com.zjport.smart.dao.IParamentDao;
import com.zjport.smart.service.ICameraService;
import com.zjport.util.Value;
import com.zjport.util.XMLObject;
import com.zjport.util.XMLUtils;
import com.zjport.video.model.ChannelCamera;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EastLanCore on 16/10/8.
 */
@Repository("cameraService")
public class CamerServiceImpl extends BaseService implements ICameraService {

    private static final Logger log = LoggerFactory.getLogger(CamerServiceImpl.class);

    private int totalPage = Integer.MAX_VALUE;

    private static String sessionId;
    private XMLObject userInfo;

    @Resource(name = "paramentDao")
    private IParamentDao paramentDao;
    @Resource(name = "cameraDao")
    private ICameraDao cameraDao;

    ThirdInfoServicePortType thirdInfoService = new ThirdInfoService().getThirdInfoServiceHttpSoap11Endpoint();


    @Override
    public String userLogin() {

        Map<String,Object> root = new HashMap<>();

        Map<String,Object> requestInfo = new HashMap<>();
        Map<String,Object> userName = new HashMap<>();
        Map<String,Object> pwd = new HashMap<>();
        Map<String,Object> clientPort = new HashMap<>();
        Map<String,Object> clientIp = new HashMap<>();
        Map<String,Object> cmsIP = new HashMap<>();

        cmsIP.put("value", "10.100.70.202");
        clientPort.put("value", "80");
        clientIp.put("value", "192.168.1.1");
        pwd.put("value", "Admin12345");
        userName.put("value", "admin");
        requestInfo.put("userName", userName);
        requestInfo.put("pwd", pwd);
        requestInfo.put("clientIp", clientIp);
        requestInfo.put("clientPort", clientPort);
        requestInfo.put("cmsIP", cmsIP);

        root.put("requestInfo", requestInfo);
        String xmlStr = XMLUtils.map2Xml(root);


        String result = thirdInfoService.userLogin(xmlStr);
        XMLObject map = XMLUtils.xml2Map(result);

        sessionId = String.valueOf(map.getXMLObject("responseInfo").getXMLObject("sessionId").get("value"));
        userInfo = map.getXMLObject("responseInfo").getXMLObject("dataInfo");
        return sessionId;
    }

    @Override
    public List<XMLObject> getAllCamera(String sessionId, int page, int rows) {
        if (sessionId == null) {
            sessionId = this.sessionId;
        }
        if (sessionId == null) {
            sessionId = userLogin();
        }


        Map<String,Object> root = new HashMap<>();

        Map<String,Object> requestInfo = new HashMap<>();

        Map<String,Object> session = new HashMap<>();
        Map<String,Object> commonPageReq = new HashMap<>();
        Map<String,Object> dataReq = new HashMap<>();

        Map<String,Object> currentPage = new HashMap<>();
        Map<String,Object> pageSize = new HashMap<>();
        Map<String,Object> controlUnitId = new HashMap<>();
        Map<String,Object> regionId = new HashMap<>();

        session.put("value", sessionId);
        currentPage.put("value", page);
        pageSize.put("value", rows);
        controlUnitId.put("value", 0);
        regionId.put("value", 0);


        commonPageReq.put("currentPage", currentPage);
        commonPageReq.put("pageSize", pageSize);

        dataReq.put("controlUnitId", controlUnitId);
        dataReq.put("regionId", regionId);

        requestInfo.put("sessionId", session);
        requestInfo.put("commonPageReq", commonPageReq);
        requestInfo.put("dataReq", dataReq);

        root.put("requestInfo", requestInfo);
//        System.out.println(root);
        String xmlStr = XMLUtils.map2Xml(root);
//        System.out.println(xmlStr);
        String resultStr = thirdInfoService.getAllCamera(xmlStr);
//        System.out.println(resultStr);
        XMLObject resultMap = XMLUtils.xml2Map(resultStr);
        XMLObject info = resultMap.getXMLObject("responseInfo");
//        System.out.println(info.get("pagInfo"));
        totalPage = Value.of(info.getXMLObject("pagInfo").getXMLObject("totalPage").get("value"), Integer.MAX_VALUE);

        List<XMLObject> infoList = info.getXMLObject("cameraInfoList").getList("cameraInfo");
        return infoList;
    }

    @Override
    public boolean updateCamera() {
        boolean result = false;
        long start = System.currentTimeMillis();
        for (int i = 1; i < totalPage; i++) {
            String sessionId = userLogin();
            if (i % 4 == 0) {
                System.out.printf("====================%d========%d============", i, totalPage);
            }
            List<XMLObject> list = getAllCamera(sessionId, i, 100);
            List<Object> cameras = new ArrayList<>();
            for (XMLObject o:list){
                cameras.add(new Camera(o));
            }
            cameraDao.saveOrUpdateAll(cameras);
        }
        log.info("user time {} ms",(System.currentTimeMillis() - start));
        return result;
    }

    @Override
    public List<String> queryParamentLocationByIds(Integer[] cameraIds) {
        List<ChannelCamera> channelCameras =  cameraDao.queryParamentLocationByIds(cameraIds);
        List<String> result = new ArrayList<>();
        for (ChannelCamera cc: channelCameras) {
            String str = "{\"lat\":"+cc.getLat()+",\"lng\":"+cc.getLon()+"}";
            result.add(str);
        }
        return result;
    }

    @Override
    public Integer[] queryCameraIdsByHduanId(Integer hduanId) {
        List<Integer> list = cameraDao.queryCameraIdsByHduanId(hduanId);
        return list.toArray(new Integer[list.size()]);
    }

    @Override
    public BaseRecords queryCamerasByHdaoId(Integer hdaoId) {
        return cameraDao.queryCamerasByHdaoId(hdaoId);
    }

    @Override
    public List<String[]> queryCameraInfosByHdaoId(Integer hduanId,Integer order) {
        List<Map<String, Object>> list = cameraDao.queryCameraInfosByHdaoId(hduanId,order);
        List<String[]> result = new ArrayList<>();
        for (Map<String,Object> map: list) {
            StringBuffer xmlModel = new StringBuffer();
            xmlModel.append("<?xml version=\\\"1.0\\\"?>");
            xmlModel.append("<Parament>");
            xmlModel.append("<MatrixCode>");
            xmlModel.append(Value.of(map.get(".matrix_code"),""));
            xmlModel.append("</MatrixCode>");
            xmlModel.append(" <MonitorID>");
            xmlModel.append(Value.of(map.get(".monitor_id"),""));
            xmlModel.append("</MonitorID>");
            xmlModel.append("<CameraID>");
            xmlModel.append(Value.of(map.get("camera_id"),""));
            xmlModel.append("</CameraID>");
            xmlModel.append("<CameraName>");
            xmlModel.append(Value.of(map.get("camera_name"),""));
            xmlModel.append("</CameraName>");
            xmlModel.append("<DeviceIP>");
            xmlModel.append(Value.of(map.get("device_ip"),""));
            xmlModel.append("</DeviceIP>");
            xmlModel.append("<DevicePort>");
            xmlModel.append(Value.of(map.get("device_port"),""));
            xmlModel.append("</DevicePort>");
            xmlModel.append("<DeviceType>");
            xmlModel.append(Value.of(map.get("device_type"),""));
            xmlModel.append("</DeviceType>");
            xmlModel.append("<User>");
            xmlModel.append(Value.of(map.get("user"),""));
            xmlModel.append("</User>");
            xmlModel.append("<Password>");
            xmlModel.append(Value.of(map.get("password"),""));
            xmlModel.append("</Password>");
            xmlModel.append("<ChannelNum>");
            xmlModel.append(Value.of(map.get("channal_num"),""));
            xmlModel.append("</ChannelNum>");
            xmlModel.append("<ProtocolType>");
            xmlModel.append(Value.of(map.get("protocol_type"),""));
            xmlModel.append("</ProtocolType>");
            xmlModel.append("<StreamType>");
            xmlModel.append(Value.of(map.get("stream_type"),""));
            xmlModel.append("</StreamType>");
            xmlModel.append("<IsEhomeDevice>");
            xmlModel.append(Value.of(map.get("is_ehome_device"),""));
            xmlModel.append("</IsEhomeDevice>");
            xmlModel.append("<DeviceIndexCode>");
            xmlModel.append(Value.of(map.get("device_index_code"),""));
            xmlModel.append("</DeviceIndexCode>");
            xmlModel.append("<VagIp>");
            xmlModel.append(Value.of(map.get("vag_ip"),""));
            xmlModel.append("</VagIp>");
            xmlModel.append("<VagPort>");
            xmlModel.append(Value.of(map.get("vag_port"),""));
            xmlModel.append("</VagPort>");
            xmlModel.append("<CameraIndexCode>");
            xmlModel.append(Value.of(map.get("camera_index_code"),""));
            xmlModel.append("</CameraIndexCode>");
            xmlModel.append("<StreamPu>");
            xmlModel.append(Value.of(map.get("stream_pu"),""));
            xmlModel.append("</StreamPu>");
            xmlModel.append("<StreamCu>");
            xmlModel.append(Value.of(map.get("dtream_cu"),""));
            xmlModel.append("</StreamCu>");
            xmlModel.append("<RegionID>");
            xmlModel.append(Value.of(map.get("region_id"),""));
            xmlModel.append("</RegionID>");
            xmlModel.append("<ControlUnitID>");
            xmlModel.append(Value.of(map.get("control_unit_id"),""));
            xmlModel.append("</ControlUnitID>");
            xmlModel.append("<Cascade>");
            xmlModel.append(Value.of(map.get("cascad"),""));
            xmlModel.append("</Cascade>");
            xmlModel.append("<CascadeCameraIndexcode>");
            xmlModel.append(Value.of(map.get("cascade_camera_index_code"),""));
            xmlModel.append("</CascadeCameraIndexcode>");
            xmlModel.append("<CascadeIasIp>");
            xmlModel.append(Value.of(map.get("cascade_ias_ip"),""));
            xmlModel.append("</CascadeIasIp>");
            xmlModel.append("<CascadeIasPort>");
            xmlModel.append(Value.of(map.get("cascade_ias_port"),""));
            xmlModel.append("</CascadeIasPort>");
            xmlModel.append("<CascadeDeviceType>");
            xmlModel.append(Value.of(map.get("cascad_device_type"),""));
            xmlModel.append("</CascadeDeviceType>");
            xmlModel.append("<StreamServerIP>");
            xmlModel.append(Value.of(map.get("stream_server_ip"),""));
            xmlModel.append("</StreamServerIP>");
            xmlModel.append("<StreamServerPort>");
            xmlModel.append(Value.of(map.get("stream_server_port"),""));
            xmlModel.append("</StreamServerPort>");
            xmlModel.append("<IsUseStreamServer>");
            xmlModel.append(Value.of(map.get("is_use_stream_server"),""));
            xmlModel.append("</IsUseStreamServer>");
            xmlModel.append("<DeviceID>");
            xmlModel.append(Value.of(map.get("device_id"),""));
            xmlModel.append("</DeviceID>");
            xmlModel.append("<UserID>");
            xmlModel.append(Value.of(map.get("user_id"),""));
            xmlModel.append("</UserID>");
            xmlModel.append("<PagIP>");
            xmlModel.append(Value.of(map.get("pag_ip"),""));
            xmlModel.append("</PagIP>");
            xmlModel.append("<PagPort>");
            xmlModel.append(Value.of(map.get("pag_port"),""));
            xmlModel.append("</PagPort>");
            xmlModel.append("<UserSession>");
            xmlModel.append(Value.of(map.get("user_session"),""));
            xmlModel.append("</UserSession>");
            xmlModel.append("<UserRightLevel>");
            xmlModel.append(Value.of(map.get("user_right_level"),""));
            xmlModel.append("</UserRightLevel>");
            xmlModel.append("<LinkedMode>");
            xmlModel.append(Value.of(map.get("linked_mode"),""));
            xmlModel.append("</LinkedMode>");
            xmlModel.append("<UseStreamServerCascade>");
            xmlModel.append(Value.of(map.get("use_stream_server_cascade"),""));
            xmlModel.append("</UseStreamServerCascade>");
            xmlModel.append("<CascadeStreamRtspPath>");
            xmlModel.append(Value.of(map.get("cascade_stream_rtsp_path"),""));
            xmlModel.append("</CascadeStreamRtspPath>");
            xmlModel.append("<PtzControl>");
            xmlModel.append(Value.of(map.get("ptz_control"),""));
            xmlModel.append("</PtzControl>");
            xmlModel.append("<SnapPicture>");
            xmlModel.append(Value.of(map.get("snap_pricture"),""));
            xmlModel.append("</SnapPicture>");
            xmlModel.append("<RecordVideo>");
            xmlModel.append(Value.of(map.get("record_video"),""));
            xmlModel.append("</RecordVideo>");
            xmlModel.append("<ThreeDPosition>");
            xmlModel.append(Value.of(map.get("three_d_position"),""));
            xmlModel.append("</ThreeDPosition>");
            xmlModel.append("<DigitalZoom>");
            xmlModel.append(Value.of(map.get("digital_zoom"),""));
            xmlModel.append("</DigitalZoom>");
            xmlModel.append("<ptzConfigPrivilege>");
            xmlModel.append(Value.of(map.get("ptz_config_privilege"),""));
            xmlModel.append("</ptzConfigPrivilege>");
            xmlModel.append("<videoConfigPrivilege>");
            xmlModel.append(Value.of(map.get("video_config_privilege"),""));
            xmlModel.append("</videoConfigPrivilege>");
            xmlModel.append("</Parament>");

            String param = xmlModel.toString();
            String lat = Value.of(map.get("lat"),"");
            String lng = Value.of(map.get("lon"),"");
            String[] strs = {param,lat,lng};
            result.add(strs);
        }
        return result;
    }

    @Scheduled(fixedRate = 30*1000)
    public void userOnlineHeartbeat(){
        log.debug("user online heart beat");
        if(StringUtils.isNotBlank(sessionId)){
            String xml = "<?xml version=\"1.0\" ?>" +
                    "<requestInfo>" +
                    "<sessionId> "+sessionId+" </sessionId>" +
                    "</requestInfo>";
            thirdInfoService.userOnlineHeartbeat(xml);
        }
    }

    @Override
    public String getPreviewParam(String sessionId, String cameraId) {
        if (sessionId == null) {
            sessionId = this.sessionId;
        }
        if (sessionId == null) {
            sessionId = userLogin();
        }

        Map<String,Object> root = new HashMap<>();

        Map<String,Object> requestInfo = new HashMap<>();

        Map<String,Object> session = new HashMap<>();
        Map<String,Object> previewParam = new HashMap<>();

        Map<String,Object> camera = new HashMap<>();
        Map<String,Object> netZoneId = new HashMap<>();

        session.put("value", sessionId);

        camera.put("value", cameraId);
        netZoneId.put("value", 0);

        previewParam.put("cameraId", camera);
        previewParam.put("netZoneID", netZoneId);

        requestInfo.put("sessionId", session);
        requestInfo.put("previewParam", previewParam);

        root.put("requestInfo", requestInfo);

        String xmlStr = XMLUtils.map2Xml(root);

        String resultStr = thirdInfoService.getPreviewParam(xmlStr);
        //TODO 临时
//        resultStr = "<?xml version=\"1.0\"?>"+
//                "<Parament>"+
//                "<MatrixCode/>"+
//                "<MonitorID>0</MonitorID>"+
//                "<CameraID>1673</CameraID>"+
//                "<CameraName>29王江泾海事所</CameraName>"+
//                "<DeviceIP>172.20.71.55</DeviceIP>"+
//                "<DevicePort>8000</DevicePort>"+
//                "<DeviceType>0</DeviceType>"+
//                "<MulitIp/>"+
//                "<User>admin</User>"+
//                "<Password>12345</Password>"+
//                "<ChannelNum>0</ChannelNum>"+
//                "<ProtocolType>0</ProtocolType>"+
//                "<StreamType>0</StreamType>"+
//                "<Transmits/>"+
//                "<UseCisco>0</UseCisco>"+
//                "<RegionID>207</RegionID>"+
//                "<ControlUnitID>1</ControlUnitID>"+
//                "<StreamServerIP>10.100.70.201</StreamServerIP>"+
//                "<StreamServerPort>554</StreamServerPort>"+
//                "<IsUseStreamServer>1</IsUseStreamServer>"+
//                "<DeviceID>13</DeviceID>"+
//                "<UserID>1</UserID>"+
//                "<PagIP>10.100.70.201</PagIP>"+
//                "<PagPort>7302</PagPort>"+
//                "<CameraIndexCode>16090109031310232130</CameraIndexCode>"+
//                "<UserSession>admin</UserSession>"+
//                "<UserRightLevel>10000</UserRightLevel>"+
//                "<LinkedMode>0</LinkedMode>"+
//                "<UseStreamServerCascade>0</UseStreamServerCascade>"+
//                "<CascadeStreamRtspPath/>"+
//                "<BindAudio>0</BindAudio>"+
//                "<isWebservice>1</isWebservice></Parament>";
//        resultStr = resultStr.replaceAll("\"","\\\\\"");
        return resultStr;
    }

    @Override
    public List<String> queryParamentByIds(Integer[] cameraIds) {
        List<Parament> parament = paramentDao.queryParamentByIds(cameraIds);
        List<String> result = new ArrayList<>();
        for (Parament p : parament) {
            result.add(p.toXML2());
        }
        return result;
    }

    @Override
    public boolean updateCameraInfo() {
        boolean result = false;
        long start = System.currentTimeMillis();
        for (int i = 1; i < totalPage; i++) {
            String sessionId = userLogin();
            if (i % 4 == 0) {
                System.out.printf("====================%d========%d============", i, totalPage);
            }
            List<XMLObject> list = getAllCamera(sessionId, i, 100);
            List<Object> paraments = new ArrayList<>();
            String str = null;
            int j = 0;
            try {
                for (; j < list.size(); j++) {
                    XMLObject cameraInfo = list.get(j);
                    String cameraId = String.valueOf((cameraInfo.getXMLObject("cameraId")).get("value"));
                    str = getPreviewParam(sessionId, cameraId);
                    paraments.add(new Parament(str));
                }
                paramentDao.saveOrUpdateAll(paraments);
                result = true;
            } catch (Exception e) {
                log.error("发生异常：{}xml:{}index:{}",e.getMessage(),str,i);
            }
        }
        log.info("user time {} ms",(System.currentTimeMillis() - start));
        return result;
    }

    public static void main(String[] args) {
        CamerServiceImpl service = new CamerServiceImpl();
        service.getControlCenter();
        service.getAllRegion();
    }

    private void getControlCenter() {
        userLogin();
        System.out.println(sessionId);
        Integer parentUnitId = Value.of(userInfo.getXMLObject("controlUnitId").get("value"),new Integer(0));
        System.out.println(parentUnitId);
        String resquest = "<?xml version=\"1.0\" ?>" +
                "<requestInfo>" +
                "<sessionId> "+"admin"+" </sessionId>" +
                "<commonPageReq>" +
                "<currentPage> 1 </currentPage>" +
                "<pageSize> 10 </pageSize>   " +
                "</commonPageReq>" +
                "<dataReq>" +
                "<parentUnitId> "+parentUnitId+" </parentUnitId>" +
                "<isFirstSubUnit> 2 </isFirstSubUnit>" +
                "</dataReq>" +
                "</requestInfo>";
        String str = thirdInfoService.getAllControlCenterForList(resquest);
        System.out.println(str);

    }

    private void getAllRegion(){
        
        String request = "<?xml version=\"1.0\" ?>" +
                "<requestInfo>" +
                "<sessionId> admin </sessionId>" +
                "<commonPageReq>" +
                "<currentPage> 1 </currentPage>" +
                "<pageSize> 10 </pageSize>   " +
                "</commonPageReq>" +
                "<dataReq>" +
                "<controlUnitId> 0 </controlUnitId>" +
                "</dataReq>" +
                "</requestInfo>";
        String strs = thirdInfoService.getAllRegionInfoForList(request);
        System.out.println(strs);
    }

}
