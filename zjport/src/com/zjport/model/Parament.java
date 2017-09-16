package com.zjport.model;

import com.zjport.util.Value;
import com.zjport.util.XMLObject;
import com.zjport.util.XMLUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Will on 2016/10/11 16:10.
 */
public class Parament {
    private String matrixCode;
    private Integer monitorId;
    private Integer cameraId;
    private String cameraName;
    private String deviceIp;
    private Integer devicePort;
    private String deviceType;
    private String user;
    private String password;
    private Integer channalNum;
    private Integer protocolType;
    private Integer streamType;
    private Integer isEhomeDevice;
    private String deviceIndexCode;
    private String vagIp;
    private Integer vagPort;
    private String cameraIndexCode;
    private String streamPu;
    private String streamCu;
    private Integer regionId;
    private String controlUnitId;
    private String cascad;
    private String cascadeCameraIndexCode;
    private String cascadeIasIp;
    private Integer cascadeIasPort;
    private Integer cascadDeviceType;
    private String streamServerIp;
    private Integer streamServerPort;
    private Integer isUseStreamServer;
    private Integer deviceId;
    private Integer userId;
    private String pagIp;
    private Integer pagPort;
    private String userSession;
    private String userRightLevel;
    private Integer linkedMode;
    private Integer useStreamServerCascade;
    private String cascadeStreamRtspPath;
    private String ptzControl;
    private String snapPricture;
    private String recordVideo;
    private String threeDPosition;
    private String digitalZoom;
    private String ptzConfigPrivilege;
    private String videoConfigPrivilege;

    public Parament() {

    }

    public Parament(String xmlStr) {
        this(XMLUtils.xml2Map(xmlStr));
    }

    public Parament(XMLObject mapBean) {
        String def_Str = null;
        Integer def_integer = null;
        XMLObject map = mapBean.getXMLObject("Parament");
        this.matrixCode = Value.of( map.getXMLObject("MatrixCode").get("value"), def_Str);
        this.monitorId = Value.of( map.getXMLObject("MonitorID").get("value"),def_integer);
        this.cameraId = Value.of( map.getXMLObject("CameraID").get("value"),def_integer);
        this.cameraName = Value.of( map.getXMLObject("CameraName").get("value"), def_Str);
        this.deviceIp = Value.of( map.getXMLObject("DeviceIP").get("value"), def_Str);
        this.devicePort = Value.of( map.getXMLObject("DevicePort").get("value"),def_integer);
        this.deviceType = Value.of( map.getXMLObject("DeviceType").get("value"), def_Str);
        this.user = Value.of( map.getXMLObject("User").get("value"), def_Str);
        this.password = Value.of( map.getXMLObject("Password").get("value"), def_Str);
        this.channalNum = Value.of( map.getXMLObject("ChannelNum").get("value"),def_integer);
        this.protocolType = Value.of( map.getXMLObject("ProtocolType").get("value"),def_integer);
        this.streamType = Value.of( map.getXMLObject("StreamType").get("value"),def_integer);
        this.isEhomeDevice = Value.of( map.getXMLObject("IsEhomeDevice").get("value"),def_integer);
        this.deviceIndexCode = Value.of( map.getXMLObject("DeviceIndexCode").get("value"), def_Str);
        this.vagIp = Value.of( map.getXMLObject("VagIp").get("value"), def_Str);
        this.vagPort = Value.of( map.getXMLObject("VagPort").get("value"),def_integer);
        this.cameraIndexCode = Value.of( map.getXMLObject("CameraIndexCode").get("value"), def_Str);
        this.streamPu = Value.of( map.getXMLObject("StreamPu").get("value"), def_Str);
        this.streamCu = Value.of( map.getXMLObject("StreamCu").get("value"), def_Str);
        this.regionId = Value.of( map.getXMLObject("RegionID").get("value"),def_integer);
        this.controlUnitId = Value.of( map.getXMLObject("ControlUnitID").get("value"), def_Str);
        this.cascad = Value.of( map.getXMLObject("Cascade").get("value"), def_Str);
        this.cascadeCameraIndexCode = Value.of( map.getXMLObject("CascadeCameraIndexcode").get("value"), def_Str);
        this.cascadeIasIp = Value.of( map.getXMLObject("CascadeIasIp").get("value"), def_Str);
        this.cascadeIasPort = Value.of( map.getXMLObject("CascadeIasPort").get("value"),def_integer);
        this.cascadDeviceType = Value.of( map.getXMLObject("CascadeDeviceType").get("value"),def_integer);
        this.streamServerIp = Value.of( map.getXMLObject("StreamServerIP").get("value"), def_Str);
        this.streamServerPort = Value.of( map.getXMLObject("StreamServerPort").get("value"),def_integer);
        this.isUseStreamServer = Value.of( map.getXMLObject("IsUseStreamServer").get("value"),def_integer);
        this.deviceId = Value.of( map.getXMLObject("DeviceID").get("value"),def_integer);
        this.userId = Value.of( map.getXMLObject("UserID").get("value"),def_integer);
        this.pagIp = Value.of( map.getXMLObject("PagIP").get("value"), def_Str);
        this.pagPort = Value.of( map.getXMLObject("PagPort").get("value"),def_integer);
        this.userSession = Value.of( map.getXMLObject("UserSession").get("value"), def_Str);
        this.userRightLevel = Value.of( map.getXMLObject("UserRightLevel").get("value"), def_Str);
        this.linkedMode = Value.of( map.getXMLObject("LinkedMode").get("value"), def_integer);
        this.useStreamServerCascade = Value.of( map.getXMLObject("UseStreamServerCascade").get("value"),def_integer);
        this.cascadeStreamRtspPath = Value.of( map.getXMLObject("CascadeStreamRtspPath").get("value"), def_Str);
        this.ptzControl = Value.of( map.getXMLObject("PtzControl").get("value"), def_Str);
        this.snapPricture = Value.of( map.getXMLObject("SnapPicture").get("value"), def_Str);
        this.recordVideo = Value.of( map.getXMLObject("RecordVideo").get("value"), def_Str);
        this.threeDPosition = Value.of( map.getXMLObject("ThreeDPosition").get("value"), def_Str);
        this.digitalZoom = Value.of( map.getXMLObject("DigitalZoom").get("value"), def_Str);
        this.ptzConfigPrivilege = Value.of( map.getXMLObject("ptzConfigPrivilege").get("value"), def_Str);
        this.videoConfigPrivilege = Value.of( map.getXMLObject("videoConfigPrivilege").get("value"), def_Str);
    }

    public String getMatrixCode() {
        return matrixCode;
    }

    public Parament setMatrixCode(String matrixCode) {
        this.matrixCode = matrixCode;
        return this;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public Parament setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
        return this;
    }

    public Integer getCameraId() {
        return cameraId;
    }

    public Parament setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
        return this;
    }

    public String getCameraName() {
        return cameraName;
    }

    public Parament setCameraName(String cameraName) {
        this.cameraName = cameraName;
        return this;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public Parament setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
        return this;
    }

    public Integer getDevicePort() {
        return devicePort;
    }

    public Parament setDevicePort(Integer devicePort) {
        this.devicePort = devicePort;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public Parament setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Parament setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Parament setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getChannalNum() {
        return channalNum;
    }

    public Parament setChannalNum(Integer channalNum) {
        this.channalNum = channalNum;
        return this;
    }

    public Integer getProtocolType() {
        return protocolType;
    }

    public Parament setProtocolType(Integer protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public Parament setStreamType(Integer streamType) {
        this.streamType = streamType;
        return this;
    }

    public Integer getIsEhomeDevice() {
        return isEhomeDevice;
    }

    public Parament setIsEhomeDevice(Integer isEhomeDevice) {
        this.isEhomeDevice = isEhomeDevice;
        return this;
    }

    public String getDeviceIndexCode() {
        return deviceIndexCode;
    }

    public Parament setDeviceIndexCode(String deviceIndexCode) {
        this.deviceIndexCode = deviceIndexCode;
        return this;
    }

    public String getVagIp() {
        return vagIp;
    }

    public Parament setVagIp(String vagIp) {
        this.vagIp = vagIp;
        return this;
    }

    public Integer getVagPort() {
        return vagPort;
    }

    public Parament setVagPort(Integer vagPort) {
        this.vagPort = vagPort;
        return this;
    }

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public Parament setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
        return this;
    }

    public String getStreamPu() {
        return streamPu;
    }

    public Parament setStreamPu(String streamPu) {
        this.streamPu = streamPu;
        return this;
    }

    public String getStreamCu() {
        return streamCu;
    }

    public Parament setStreamCu(String dtreamCu) {
        this.streamCu = dtreamCu;
        return this;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public Parament setRegionId(Integer regionId) {
        this.regionId = regionId;
        return this;
    }

    public String getControlUnitId() {
        return controlUnitId;
    }

    public Parament setControlUnitId(String controlUnitId) {
        this.controlUnitId = controlUnitId;
        return this;
    }

    public String getCascad() {
        return cascad;
    }

    public Parament setCascad(String cascad) {
        this.cascad = cascad;
        return this;
    }

    public String getCascadeCameraIndexCode() {
        return cascadeCameraIndexCode;
    }

    public Parament setCascadeCameraIndexCode(String cascadeCameraIndexCode) {
        this.cascadeCameraIndexCode = cascadeCameraIndexCode;
        return this;
    }

    public String getCascadeIasIp() {
        return cascadeIasIp;
    }

    public Parament setCascadeIasIp(String cascadeIasIp) {
        this.cascadeIasIp = cascadeIasIp;
        return this;
    }

    public Integer getCascadeIasPort() {
        return cascadeIasPort;
    }

    public Parament setCascadeIasPort(Integer cascadeIasPort) {
        this.cascadeIasPort = cascadeIasPort;
        return this;
    }

    public Integer getCascadDeviceType() {
        return cascadDeviceType;
    }

    public Parament setCascadDeviceType(Integer cascadDeviceType) {
        this.cascadDeviceType = cascadDeviceType;
        return this;
    }

    public String getStreamServerIp() {
        return streamServerIp;
    }

    public Parament setStreamServerIp(String streamServerIp) {
        this.streamServerIp = streamServerIp;
        return this;
    }

    public Integer getStreamServerPort() {
        return streamServerPort;
    }

    public Parament setStreamServerPort(Integer streamServerPort) {
        this.streamServerPort = streamServerPort;
        return this;
    }

    public Integer getIsUseStreamServer() {
        return isUseStreamServer;
    }

    public Parament setIsUseStreamServer(Integer isUseStreamServer) {
        this.isUseStreamServer = isUseStreamServer;
        return this;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public Parament setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Parament setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getPagIp() {
        return pagIp;
    }

    public Parament setPagIp(String pagIp) {
        this.pagIp = pagIp;
        return this;
    }

    public Integer getPagPort() {
        return pagPort;
    }

    public Parament setPagPort(Integer pagPort) {
        this.pagPort = pagPort;
        return this;
    }

    public String getUserSession() {
        return userSession;
    }

    public Parament setUserSession(String userSession) {
        this.userSession = userSession;
        return this;
    }

    public String getUserRightLevel() {
        return userRightLevel;
    }

    public Parament setUserRightLevel(String userRightLevel) {
        this.userRightLevel = userRightLevel;
        return this;
    }

    public Integer getLinkedMode() {
        return linkedMode;
    }

    public Parament setLinkedMode(Integer linkedMode) {
        this.linkedMode = linkedMode;
        return this;
    }

    public Integer getUseStreamServerCascade() {
        return useStreamServerCascade;
    }

    public Parament setUseStreamServerCascade(Integer useStreamServerCascade) {
        this.useStreamServerCascade = useStreamServerCascade;
        return this;
    }

    public String getCascadeStreamRtspPath() {
        return cascadeStreamRtspPath;
    }

    public Parament setCascadeStreamRtspPath(String cascadeStreamRtspPath) {
        this.cascadeStreamRtspPath = cascadeStreamRtspPath;
        return this;
    }

    public String getPtzControl() {
        return ptzControl;
    }

    public Parament setPtzControl(String ptzControl) {
        this.ptzControl = ptzControl;
        return this;
    }

    public String getSnapPricture() {
        return snapPricture;
    }

    public Parament setSnapPricture(String snapPricture) {
        this.snapPricture = snapPricture;
        return this;
    }

    public String getRecordVideo() {
        return recordVideo;
    }

    public Parament setRecordVideo(String recordVideo) {
        this.recordVideo = recordVideo;
        return this;
    }

    public String getThreeDPosition() {
        return threeDPosition;
    }

    public Parament setThreeDPosition(String threeDPosition) {
        this.threeDPosition = threeDPosition;
        return this;
    }

    public String getDigitalZoom() {
        return digitalZoom;
    }

    public Parament setDigitalZoom(String digitalZoom) {
        this.digitalZoom = digitalZoom;
        return this;
    }

    public String getPtzConfigPrivilege() {
        return ptzConfigPrivilege;
    }

    public Parament setPtzConfigPrivilege(String ptzConfigPrivilege) {
        this.ptzConfigPrivilege = ptzConfigPrivilege;
        return this;
    }

    public String getVideoConfigPrivilege() {
        return videoConfigPrivilege;
    }

    public Parament setVideoConfigPrivilege(String videoConfigPrivilege) {
        this.videoConfigPrivilege = videoConfigPrivilege;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parament that = (Parament) o;

        if (matrixCode != null ? !matrixCode.equals(that.matrixCode) : that.matrixCode != null) return false;
        if (monitorId != null ? !monitorId.equals(that.monitorId) : that.monitorId != null) return false;
        if (cameraId != null ? !cameraId.equals(that.cameraId) : that.cameraId != null) return false;
        if (cameraName != null ? !cameraName.equals(that.cameraName) : that.cameraName != null) return false;
        if (deviceIp != null ? !deviceIp.equals(that.deviceIp) : that.deviceIp != null) return false;
        if (devicePort != null ? !devicePort.equals(that.devicePort) : that.devicePort != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (channalNum != null ? !channalNum.equals(that.channalNum) : that.channalNum != null) return false;
        if (protocolType != null ? !protocolType.equals(that.protocolType) : that.protocolType != null) return false;
        if (streamType != null ? !streamType.equals(that.streamType) : that.streamType != null) return false;
        if (isEhomeDevice != null ? !isEhomeDevice.equals(that.isEhomeDevice) : that.isEhomeDevice != null)
            return false;
        if (deviceIndexCode != null ? !deviceIndexCode.equals(that.deviceIndexCode) : that.deviceIndexCode != null)
            return false;
        if (vagIp != null ? !vagIp.equals(that.vagIp) : that.vagIp != null) return false;
        if (vagPort != null ? !vagPort.equals(that.vagPort) : that.vagPort != null) return false;
        if (cameraIndexCode != null ? !cameraIndexCode.equals(that.cameraIndexCode) : that.cameraIndexCode != null)
            return false;
        if (streamPu != null ? !streamPu.equals(that.streamPu) : that.streamPu != null) return false;
        if (streamCu != null ? !streamCu.equals(that.streamCu) : that.streamCu != null) return false;
        if (regionId != null ? !regionId.equals(that.regionId) : that.regionId != null) return false;
        if (controlUnitId != null ? !controlUnitId.equals(that.controlUnitId) : that.controlUnitId != null)
            return false;
        if (cascad != null ? !cascad.equals(that.cascad) : that.cascad != null) return false;
        if (cascadeCameraIndexCode != null ? !cascadeCameraIndexCode.equals(that.cascadeCameraIndexCode) : that.cascadeCameraIndexCode != null)
            return false;
        if (cascadeIasIp != null ? !cascadeIasIp.equals(that.cascadeIasIp) : that.cascadeIasIp != null) return false;
        if (cascadeIasPort != null ? !cascadeIasPort.equals(that.cascadeIasPort) : that.cascadeIasPort != null)
            return false;
        if (cascadDeviceType != null ? !cascadDeviceType.equals(that.cascadDeviceType) : that.cascadDeviceType != null)
            return false;
        if (streamServerIp != null ? !streamServerIp.equals(that.streamServerIp) : that.streamServerIp != null)
            return false;
        if (streamServerPort != null ? !streamServerPort.equals(that.streamServerPort) : that.streamServerPort != null)
            return false;
        if (isUseStreamServer != null ? !isUseStreamServer.equals(that.isUseStreamServer) : that.isUseStreamServer != null)
            return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (pagIp != null ? !pagIp.equals(that.pagIp) : that.pagIp != null) return false;
        if (pagPort != null ? !pagPort.equals(that.pagPort) : that.pagPort != null) return false;
        if (userSession != null ? !userSession.equals(that.userSession) : that.userSession != null) return false;
        if (userRightLevel != null ? !userRightLevel.equals(that.userRightLevel) : that.userRightLevel != null)
            return false;
        if (linkedMode != null ? !linkedMode.equals(that.linkedMode) : that.linkedMode != null) return false;
        if (useStreamServerCascade != null ? !useStreamServerCascade.equals(that.useStreamServerCascade) : that.useStreamServerCascade != null)
            return false;
        if (cascadeStreamRtspPath != null ? !cascadeStreamRtspPath.equals(that.cascadeStreamRtspPath) : that.cascadeStreamRtspPath != null)
            return false;
        if (ptzControl != null ? !ptzControl.equals(that.ptzControl) : that.ptzControl != null) return false;
        if (snapPricture != null ? !snapPricture.equals(that.snapPricture) : that.snapPricture != null) return false;
        if (recordVideo != null ? !recordVideo.equals(that.recordVideo) : that.recordVideo != null) return false;
        if (threeDPosition != null ? !threeDPosition.equals(that.threeDPosition) : that.threeDPosition != null)
            return false;
        if (digitalZoom != null ? !digitalZoom.equals(that.digitalZoom) : that.digitalZoom != null) return false;
        if (ptzConfigPrivilege != null ? !ptzConfigPrivilege.equals(that.ptzConfigPrivilege) : that.ptzConfigPrivilege != null)
            return false;
        if (videoConfigPrivilege != null ? !videoConfigPrivilege.equals(that.videoConfigPrivilege) : that.videoConfigPrivilege != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = matrixCode != null ? matrixCode.hashCode() : 0;
        result = 31 * result + (monitorId != null ? monitorId.hashCode() : 0);
        result = 31 * result + (cameraId != null ? cameraId.hashCode() : 0);
        result = 31 * result + (cameraName != null ? cameraName.hashCode() : 0);
        result = 31 * result + (deviceIp != null ? deviceIp.hashCode() : 0);
        result = 31 * result + (devicePort != null ? devicePort.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (channalNum != null ? channalNum.hashCode() : 0);
        result = 31 * result + (protocolType != null ? protocolType.hashCode() : 0);
        result = 31 * result + (streamType != null ? streamType.hashCode() : 0);
        result = 31 * result + (isEhomeDevice != null ? isEhomeDevice.hashCode() : 0);
        result = 31 * result + (deviceIndexCode != null ? deviceIndexCode.hashCode() : 0);
        result = 31 * result + (vagIp != null ? vagIp.hashCode() : 0);
        result = 31 * result + (vagPort != null ? vagPort.hashCode() : 0);
        result = 31 * result + (cameraIndexCode != null ? cameraIndexCode.hashCode() : 0);
        result = 31 * result + (streamPu != null ? streamPu.hashCode() : 0);
        result = 31 * result + (streamCu != null ? streamCu.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        result = 31 * result + (controlUnitId != null ? controlUnitId.hashCode() : 0);
        result = 31 * result + (cascad != null ? cascad.hashCode() : 0);
        result = 31 * result + (cascadeCameraIndexCode != null ? cascadeCameraIndexCode.hashCode() : 0);
        result = 31 * result + (cascadeIasIp != null ? cascadeIasIp.hashCode() : 0);
        result = 31 * result + (cascadeIasPort != null ? cascadeIasPort.hashCode() : 0);
        result = 31 * result + (cascadDeviceType != null ? cascadDeviceType.hashCode() : 0);
        result = 31 * result + (streamServerIp != null ? streamServerIp.hashCode() : 0);
        result = 31 * result + (streamServerPort != null ? streamServerPort.hashCode() : 0);
        result = 31 * result + (isUseStreamServer != null ? isUseStreamServer.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (pagIp != null ? pagIp.hashCode() : 0);
        result = 31 * result + (pagPort != null ? pagPort.hashCode() : 0);
        result = 31 * result + (userSession != null ? userSession.hashCode() : 0);
        result = 31 * result + (userRightLevel != null ? userRightLevel.hashCode() : 0);
        result = 31 * result + (linkedMode != null ? linkedMode.hashCode() : 0);
        result = 31 * result + (useStreamServerCascade != null ? useStreamServerCascade.hashCode() : 0);
        result = 31 * result + (cascadeStreamRtspPath != null ? cascadeStreamRtspPath.hashCode() : 0);
        result = 31 * result + (ptzControl != null ? ptzControl.hashCode() : 0);
        result = 31 * result + (snapPricture != null ? snapPricture.hashCode() : 0);
        result = 31 * result + (recordVideo != null ? recordVideo.hashCode() : 0);
        result = 31 * result + (threeDPosition != null ? threeDPosition.hashCode() : 0);
        result = 31 * result + (digitalZoom != null ? digitalZoom.hashCode() : 0);
        result = 31 * result + (ptzConfigPrivilege != null ? ptzConfigPrivilege.hashCode() : 0);
        result = 31 * result + (videoConfigPrivilege != null ? videoConfigPrivilege.hashCode() : 0);
        return result;
    }

    public String toXML() {
        Map root = new HashMap();

        Map parament = new HashMap();

        Map matrixCode = new HashMap();
        matrixCode.put("value", this.matrixCode);
        parament.put("MatrixCode", matrixCode);

        Map monitorId = new HashMap();
        monitorId.put("value", this.monitorId);
        parament.put("MonitorID", monitorId);

        Map cameraId = new HashMap();
        cameraId.put("value", this.cameraId);
        parament.put("CameraID", cameraId);

        Map cameraName = new HashMap();
        cameraName.put("value", this.cameraName);
        parament.put("CameraName", cameraName);

        Map deviceIp = new HashMap();
        deviceIp.put("value", this.deviceIp);
        parament.put("DeviceIP", deviceIp);

        Map devicePort = new HashMap();
        devicePort.put("value", this.devicePort);
        parament.put("DevicePort", devicePort);

        Map deviceType = new HashMap();
        deviceType.put("value", this.deviceType);
        parament.put("DeviceType", deviceType);

        Map user = new HashMap();
        user.put("value", this.user);
        parament.put("User", user);

        Map password = new HashMap();
        password.put("value", this.password);
        parament.put("Password", password);

        Map channalNum = new HashMap();
        channalNum.put("value", this.channalNum);
        parament.put("ChannelNum", channalNum);

        Map protocolType = new HashMap();
        protocolType.put("value", this.protocolType);
        parament.put("ProtocolType", protocolType);

        Map streamType = new HashMap();
        streamType.put("value", this.streamType);
        parament.put("StreamType", streamType);

//        Map isEhomeDevice = new HashMap();
//        isEhomeDevice.put("value",this.isEhomeDevice);
//        parament.put("IsEhomeDevice",isEhomeDevice);
//
//        Map deviceIndexCode = new HashMap();
//        deviceIndexCode.put("value",this.deviceIndexCode);
//        parament.put("DeviceIndexCode",deviceIndexCode);
//
//        Map vagIp = new HashMap();
//        vagIp.put("value",this.vagIp);
//        parament.put("VagIp",vagIp);
//
//        Map vagPort = new HashMap();
//        vagPort.put("value",this.vagPort);
//        parament.put("VagPort",vagPort);
//
        Map cameraIndexCode = new HashMap();
        cameraIndexCode.put("value", this.cameraIndexCode);
        parament.put("CameraIndexCode", cameraIndexCode);
//
//        Map streamPu = new HashMap();
//        streamPu.put("value",this.streamPu);
//        parament.put("StreamPu",streamPu);
//
//        Map streamCu = new HashMap();
//        streamCu.put("value",this.streamCu);
//        parament.put("DtreamCu",streamCu);

        Map regionId = new HashMap();
        regionId.put("value", this.regionId);
        parament.put("RegionID", regionId);

        Map controlUnitId = new HashMap();
        controlUnitId.put("value", this.controlUnitId);
        parament.put("ControlUnitID", controlUnitId);

//        Map cascad = new HashMap();
//        cascad.put("value",this.cascad);
//        parament.put("Cascade",cascad);
//
//        Map cascadeCameraIndexCode = new HashMap();
//        cascadeCameraIndexCode.put("value",this.cascadeCameraIndexCode);
//        parament.put("CascadeCameraIndexcode",cascadeCameraIndexCode);
//
//        Map cascadeIasIp = new HashMap();
//        cascadeIasIp.put("value",this.cascadeIasIp);
//        parament.put("CascadeIasIp",cascadeIasIp);
//
//        Map cascadeIasPort = new HashMap();
//        cascadeIasPort.put("value",this.cascadeIasPort);
//        parament.put("CascadeIasPort",cascadeIasPort);
//
//        Map cascadDeviceType = new HashMap();
//        cascadDeviceType.put("value",this.cascadDeviceType);
//        parament.put("CascadeDeviceType",cascadDeviceType);

        Map streamServerIp = new HashMap();
        streamServerIp.put("value", this.streamServerIp);
        parament.put("StreamServerIP", streamServerIp);

        Map streamServerPort = new HashMap();
        streamServerPort.put("value", this.streamServerPort);
        parament.put("StreamServerPort", streamServerPort);

        Map isUseStreamServer = new HashMap();
        isUseStreamServer.put("value", this.isUseStreamServer);
        parament.put("IsUseStreamServer", isUseStreamServer);

        Map DeviceId = new HashMap();
        DeviceId.put("value", this.deviceId);
        parament.put("DeviceID", DeviceId);

        Map userId = new HashMap();
        userId.put("value", this.userId);
        parament.put("UserID", userId);

        Map pagIp = new HashMap();
        pagIp.put("value", this.pagIp);
        parament.put("PagIP", pagIp);

        Map pagPort = new HashMap();
        pagPort.put("value", this.pagPort);
        parament.put("PagPort", pagPort);

        Map userSession = new HashMap();
        userSession.put("value", this.userSession);
        parament.put("UserSession", userSession);

        Map userRightLevel = new HashMap();
        userRightLevel.put("value", this.userRightLevel);
        parament.put("UserRightLevel", userRightLevel);

        Map linkedMode = new HashMap();
        linkedMode.put("value", this.linkedMode);
        parament.put("LinkedMode", linkedMode);

        Map useStreamServerCascade = new HashMap();
        useStreamServerCascade.put("value", this.useStreamServerCascade);
        parament.put("UseStreamServerCascade", useStreamServerCascade);

        Map cascadeStreamRtspPath = new HashMap();
        cascadeStreamRtspPath.put("value", this.cascadeStreamRtspPath);
        parament.put("CascadeStreamRtspPath", cascadeStreamRtspPath);

//        Map ptzControl = new HashMap();
//        ptzControl.put("value",this.ptzControl);
//        parament.put("PtzControl",ptzControl);
//
//        Map snapPricture = new HashMap();
//        snapPricture.put("value",this.snapPricture);
//        parament.put("SnapPicture",snapPricture);
//
//        Map recordVideo = new HashMap();
//        recordVideo.put("value",this.recordVideo);
//        parament.put("RecordVideo",recordVideo);
//
//        Map threeDPosition = new HashMap();
//        threeDPosition.put("value",this.threeDPosition);
//        parament.put("ThreeDPosition",threeDPosition);
//
//        Map digitalZoom = new HashMap();
//        digitalZoom.put("value",this.digitalZoom);
//        parament.put("DigitalZoom",digitalZoom);
//
//        Map ptzConfigPrivilege = new HashMap();
//        ptzConfigPrivilege.put("value",this.ptzConfigPrivilege);
//        parament.put("ptzConfigPrivilege",ptzConfigPrivilege);
//
//        Map videoConfigPrivilege = new HashMap();
//        videoConfigPrivilege.put("value",this.videoConfigPrivilege);
//        parament.put("videoConfigPrivilege",videoConfigPrivilege);

        root.put("Parament", parament);
        String result = XMLUtils.map2Xml(root);
        return result.replaceAll("\n", "");
    }

    public String toXML2() {
//        String xmlModel = "<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>"+
//                "<Parament>"+
//                "<MatrixCode>"+this.matrixCode+"</MatrixCode>"+
//                "<MonitorID>"+this.monitorId+"</MonitorID>"+
//                "<CameraID>"+this.cameraId+"</CameraID>"+
//                "<CameraName>"+this.cameraName+"</CameraName>"+
//                "<DeviceIP>"+this.deviceIp+"</DeviceIP>"+
//                "<DevicePort>"+this.devicePort+"</DevicePort>"+
//                "<DeviceType>"+this.deviceType+"</DeviceType>"+
//                "<User>"+this.user+"</User>"+
//                "<Password>"+this.password+"</Password>"+
//                "<ChannelNum>"+this.channalNum+"</ChannelNum>"+
//                "<ProtocolType>"+this.protocolType+"</ProtocolType>"+
//                "<StreamType>"+this.streamType+"</StreamType>"+
//                "<RegionID>"+this.regionId+"</RegionID>"+
//                "<ControlUnitID>"+this.controlUnitId+"</ControlUnitID>"+
//                "<StreamServerIP>"+this.streamServerIp+"</StreamServerIP>"+
//                "<StreamServerPort>"+this.streamServerPort+"</StreamServerPort>"+
//                "<IsUseStreamServer>"+this.isUseStreamServer+"</IsUseStreamServer>"+
//                "<DeviceID>"+this.deviceId+"</DeviceID>"+
//                "<UserID>"+this.userId+"</UserID>"+
//                "<PagIP>"+this.pagIp+"</PagIP>"+
//                "<PagPort>"+this.pagIp+"</PagPort>"+
//                "<CameraIndexCode>"+this.cameraIndexCode+"</CameraIndexCode>"+
//                "<UserSession>"+this.userSession+"</UserSession>"+
//                "<UserRightLevel>"+this.userRightLevel+"</UserRightLevel>"+
//                "<LinkedMode>"+this.linkedMode+"</LinkedMode>"+
//                "<UseStreamServerCascade>"+this.useStreamServerCascade+"</UseStreamServerCascade>"+
//                "<CascadeStreamRtspPath>"+this.cascadeStreamRtspPath+"</CascadeStreamRtspPath>"+
//                "</Parament>";
        StringBuffer xmlModel = new StringBuffer();
        xmlModel.append("<?xml version=\\\"1.0\\\"?>");
        xmlModel.append("<Parament>");
        xmlModel.append("<MatrixCode>");
        xmlModel.append(Value.of(this.matrixCode,""));
        xmlModel.append("</MatrixCode>");
        xmlModel.append(" <MonitorID>");
        xmlModel.append(Value.of(this.monitorId,""));
        xmlModel.append("</MonitorID>");
        xmlModel.append("<CameraID>");
        xmlModel.append(Value.of(this.cameraId,""));
        xmlModel.append("</CameraID>");
        xmlModel.append("<CameraName>");
        xmlModel.append(Value.of(this.cameraName,""));
        xmlModel.append("</CameraName>");
        xmlModel.append("<DeviceIP>");
        xmlModel.append(Value.of(this.deviceIp,""));
        xmlModel.append("</DeviceIP>");
        xmlModel.append("<DevicePort>");
        xmlModel.append(Value.of(this.devicePort,""));
        xmlModel.append("</DevicePort>");
        xmlModel.append("<DeviceType>");
        xmlModel.append(Value.of(this.deviceType,""));
        xmlModel.append("</DeviceType>");
        xmlModel.append("<User>");
        xmlModel.append(Value.of(this.user,""));
        xmlModel.append("</User>");
        xmlModel.append("<Password>");
        xmlModel.append(Value.of(this.password,""));
        xmlModel.append("</Password>");
        xmlModel.append("<ChannelNum>");
        xmlModel.append(Value.of(this.channalNum,""));
        xmlModel.append("</ChannelNum>");
        xmlModel.append("<ProtocolType>");
        xmlModel.append(Value.of(this.protocolType,""));
        xmlModel.append("</ProtocolType>");
        xmlModel.append("<StreamType>");
        xmlModel.append(Value.of(this.streamType,""));
        xmlModel.append("</StreamType>");
        xmlModel.append("<IsEhomeDevice>");
        xmlModel.append(Value.of(this.isEhomeDevice,""));
        xmlModel.append("</IsEhomeDevice>");
        xmlModel.append("<DeviceIndexCode>");
        xmlModel.append(Value.of(this.deviceIndexCode,""));
        xmlModel.append("</DeviceIndexCode>");
        xmlModel.append("<VagIp>");
        xmlModel.append(Value.of(this.vagIp,""));
        xmlModel.append("</VagIp>");
        xmlModel.append("<VagPort>");
        xmlModel.append(Value.of(this.vagPort,""));
        xmlModel.append("</VagPort>");
        xmlModel.append("<CameraIndexCode>");
        xmlModel.append(Value.of(this.cameraIndexCode,""));
        xmlModel.append("</CameraIndexCode>");
        xmlModel.append("<StreamPu>");
        xmlModel.append(Value.of(this.streamPu,""));
        xmlModel.append("</StreamPu>");
        xmlModel.append("<StreamCu>");
        xmlModel.append(Value.of(this.streamCu,""));
        xmlModel.append("</StreamCu>");
        xmlModel.append("<RegionID>");
        xmlModel.append(Value.of(this.regionId,""));
        xmlModel.append("</RegionID>");
        xmlModel.append("<ControlUnitID>");
        xmlModel.append(Value.of(this.controlUnitId,""));
        xmlModel.append("</ControlUnitID>");
        xmlModel.append("<Cascade>");
        xmlModel.append(Value.of(this.cascad,""));
        xmlModel.append("</Cascade>");
        xmlModel.append("<CascadeCameraIndexcode>");
        xmlModel.append(Value.of(this.cascadeCameraIndexCode,""));
        xmlModel.append("</CascadeCameraIndexcode>");
        xmlModel.append("<CascadeIasIp>");
        xmlModel.append(Value.of(this.cascadeIasIp,""));
        xmlModel.append("</CascadeIasIp>");
        xmlModel.append("<CascadeIasPort>");
        xmlModel.append(Value.of(this.cascadeIasPort,""));
        xmlModel.append("</CascadeIasPort>");
        xmlModel.append("<CascadeDeviceType>");
        xmlModel.append(Value.of(this.cascadDeviceType,""));
        xmlModel.append("</CascadeDeviceType>");
        xmlModel.append("<StreamServerIP>");
        xmlModel.append(Value.of(this.streamServerIp,""));
        xmlModel.append("</StreamServerIP>");
        xmlModel.append("<StreamServerPort>");
        xmlModel.append(Value.of(this.streamServerPort,""));
        xmlModel.append("</StreamServerPort>");
        xmlModel.append("<IsUseStreamServer>");
        xmlModel.append(Value.of(this.isUseStreamServer,""));
        xmlModel.append("</IsUseStreamServer>");
        xmlModel.append("<DeviceID>");
        xmlModel.append(Value.of(this.deviceId,""));
        xmlModel.append("</DeviceID>");
        xmlModel.append("<UserID>");
        xmlModel.append(Value.of(this.userId,""));
        xmlModel.append("</UserID>");
        xmlModel.append("<PagIP>");
        xmlModel.append(Value.of(this.pagIp,""));
        xmlModel.append("</PagIP>");
        xmlModel.append("<PagPort>");
        xmlModel.append(Value.of(this.pagPort,""));
        xmlModel.append("</PagPort>");
        xmlModel.append("<UserSession>");
        xmlModel.append(Value.of(this.userSession,""));
        xmlModel.append("</UserSession>");
        xmlModel.append("<UserRightLevel>");
        xmlModel.append(Value.of(this.userRightLevel,""));
        xmlModel.append("</UserRightLevel>");
        xmlModel.append("<LinkedMode>");
        xmlModel.append(Value.of(this.linkedMode,""));
        xmlModel.append("</LinkedMode>");
        xmlModel.append("<UseStreamServerCascade>");
        xmlModel.append(Value.of(this.useStreamServerCascade,""));
        xmlModel.append("</UseStreamServerCascade>");
        xmlModel.append("<CascadeStreamRtspPath>");
        xmlModel.append(Value.of(this.cascadeStreamRtspPath,""));
        xmlModel.append("</CascadeStreamRtspPath>");
        xmlModel.append("<PtzControl>");
        xmlModel.append(Value.of(this.ptzControl,""));
        xmlModel.append("</PtzControl>");
        xmlModel.append("<SnapPicture>");
        xmlModel.append(Value.of(this.snapPricture,""));
        xmlModel.append("</SnapPicture>");
        xmlModel.append("<RecordVideo>");
        xmlModel.append(Value.of(this.recordVideo,""));
        xmlModel.append("</RecordVideo>");
        xmlModel.append("<ThreeDPosition>");
        xmlModel.append(Value.of(this.threeDPosition,""));
        xmlModel.append("</ThreeDPosition>");
        xmlModel.append("<DigitalZoom>");
        xmlModel.append(Value.of(this.digitalZoom,""));
        xmlModel.append("</DigitalZoom>");
        xmlModel.append("<ptzConfigPrivilege>");
        xmlModel.append(Value.of(this.ptzConfigPrivilege,""));
        xmlModel.append("</ptzConfigPrivilege>");
        xmlModel.append("<videoConfigPrivilege>");
        xmlModel.append(Value.of(this.videoConfigPrivilege,""));
        xmlModel.append("</videoConfigPrivilege>");
        xmlModel.append("</Parament>");


        return xmlModel.toString();
    }

    @Override
    public String toString() {
        return "Parament{" +
                "matrixCode='" + matrixCode + '\'' +
                ", monitorId=" + monitorId +
                ", cameraId=" + cameraId +
                ", cameraName='" + cameraName + '\'' +
                ", deviceIp='" + deviceIp + '\'' +
                ", devicePort=" + devicePort +
                ", deviceType='" + deviceType + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", channalNum=" + channalNum +
                ", protocolType=" + protocolType +
                ", streamType=" + streamType +
                ", isEhomeDevice=" + isEhomeDevice +
                ", deviceIndexCode='" + deviceIndexCode + '\'' +
                ", vagIp='" + vagIp + '\'' +
                ", vagPort=" + vagPort +
                ", cameraIndexCode='" + cameraIndexCode + '\'' +
                ", streamPu='" + streamPu + '\'' +
                ", streamCu='" + streamCu + '\'' +
                ", regionId=" + regionId +
                ", controlUnitId='" + controlUnitId + '\'' +
                ", cascad='" + cascad + '\'' +
                ", cascadeCameraIndexCode='" + cascadeCameraIndexCode + '\'' +
                ", cascadeIasIp='" + cascadeIasIp + '\'' +
                ", cascadeIasPort=" + cascadeIasPort +
                ", cascadDeviceType=" + cascadDeviceType +
                ", streamServerIp='" + streamServerIp + '\'' +
                ", streamServerPort=" + streamServerPort +
                ", isUseStreamServer=" + isUseStreamServer +
                ", deviceId=" + deviceId +
                ", userId=" + userId +
                ", pagIp='" + pagIp + '\'' +
                ", pagPort=" + pagPort +
                ", userSession='" + userSession + '\'' +
                ", userRightLevel='" + userRightLevel + '\'' +
                ", linkedMode=" + linkedMode +
                ", useStreamServerCascade=" + useStreamServerCascade +
                ", cascadeStreamRtspPath='" + cascadeStreamRtspPath + '\'' +
                ", ptzControl='" + ptzControl + '\'' +
                ", snapPricture='" + snapPricture + '\'' +
                ", recordVideo='" + recordVideo + '\'' +
                ", threeDPosition='" + threeDPosition + '\'' +
                ", digitalZoom='" + digitalZoom + '\'' +
                ", ptzConfigPrivilege='" + ptzConfigPrivilege + '\'' +
                ", videoConfigPrivilege='" + videoConfigPrivilege + '\'' +
                '}';
    }

    public static void main(String[] args) {
    }
}
