package com.zjport.model;

import com.zjport.util.Value;
import com.zjport.util.XMLObject;
import com.zjport.util.XMLUtils;

import java.util.List;

/**
 * Created by Will on 2016/10/13 13:09.
 */
public class Camera {
    private Integer cameraId;
    private String indexCode;
    private String name;
    private Integer deviceId;
    private Integer regionId;
    private Integer controlUnitId;
    private Integer chanNum;
    private Integer cameraType;
    private Integer streamType;
    private String latitude;
    private String longitude;
    private Integer sequenceIdx;
    private String caseCadeIndex;
    private String recLactionList;
    private String privilegeCodeList;

    public Camera(){

    }

    public Camera(String xmlStr){
        this(XMLUtils.xml2Map(xmlStr));
    }

    public Camera(XMLObject cameraMap){
        Integer nullInteger = null;
        String nullString = null;
        this.cameraId = Value.of(cameraMap.getXMLObject("cameraId").get("value"),nullInteger);
        this.indexCode = Value.of(cameraMap.getXMLObject("indexCode").get("value"),nullString);
        this.name = Value.of(cameraMap.getXMLObject("name").get("value"),nullString);
        this.deviceId = Value.of(cameraMap.getXMLObject("deviceId").get("value"),nullInteger);
        this.regionId = Value.of(cameraMap.getXMLObject("regionId").get("value"),nullInteger);
        this.controlUnitId = Value.of(cameraMap.getXMLObject("controlUnitId").get("value"),nullInteger);
        this.chanNum = Value.of(cameraMap.getXMLObject("chanNum").get("value"),nullInteger);
        this.cameraType = Value.of(cameraMap.getXMLObject("cameraType").get("value"),nullInteger);
        this.streamType = Value.of(cameraMap.getXMLObject("streamType").get("value"),nullInteger);
        this.latitude = Value.of(cameraMap.getXMLObject("latitude").get("value"),nullString);
        this.longitude = Value.of(cameraMap.getXMLObject("longitude").get("value"),nullString);
        this.sequenceIdx = Value.of(cameraMap.getXMLObject("sequenceIdx").get("value"),nullInteger);
        this.caseCadeIndex = Value.of(cameraMap.getXMLObject("caseCadeIndex").get("value"),nullString);
        Object rec = cameraMap.get("recLactionList");
        String recStr = "";
        if(rec instanceof List){
            StringBuffer recStrBuffer = new StringBuffer();
            List list = (List) rec;
            recStrBuffer.append("[");
            for ( Object o: list ) {
                XMLObject map = (XMLObject) o;
                String str = Value.of(map.getXMLObject("recLacation").get("value"),nullString);
                recStrBuffer.append(str + " , ");
            }
            recStr = recStrBuffer.substring(0,recStr.length() - 1) +"]";
        }else if(rec instanceof XMLObject){
            XMLObject map = (XMLObject) rec;
            String str = Value.of(map.getXMLObject("recLacation").get("value"),nullString);
            recStr = "["+str+"]";

        }
        this.recLactionList = recStr;
        Object privileg = cameraMap.get("privilegeCodeList");
        String privilegStr = "";
        if(privileg instanceof List){
            StringBuffer recStrBuffer = new StringBuffer();
            List list = (List) privileg;
            recStrBuffer.append("[");
            for ( Object o: list ) {
                XMLObject map = (XMLObject) o;
                String str = Value.of(map.getXMLObject("privilegeCode").get("value"),nullString);
                recStrBuffer.append(str + " , ");
            }
            privilegStr = recStrBuffer.substring(0,recStr.length() - 1) +"]";
        }else if(privileg instanceof XMLObject){
            XMLObject map = (XMLObject) privileg;
            String str = Value.of(map.getXMLObject("privilegeCode").get("value"),nullString);
            privilegStr = "["+str+"]";

        }
        this.privilegeCodeList = privilegStr;

    }

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getControlUnitId() {
        return controlUnitId;
    }

    public void setControlUnitId(Integer controlUnitId) {
        this.controlUnitId = controlUnitId;
    }

    public Integer getChanNum() {
        return chanNum;
    }

    public void setChanNum(Integer chanNum) {
        this.chanNum = chanNum;
    }

    public Integer getCameraType() {
        return cameraType;
    }

    public void setCameraType(Integer cameraType) {
        this.cameraType = cameraType;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getSequenceIdx() {
        return sequenceIdx;
    }

    public void setSequenceIdx(Integer sequenceIdx) {
        this.sequenceIdx = sequenceIdx;
    }

    public String getCaseCadeIndex() {
        return caseCadeIndex;
    }

    public void setCaseCadeIndex(String caseCadeIndex) {
        this.caseCadeIndex = caseCadeIndex;
    }

    public String getRecLactionList() {
        return recLactionList;
    }

    public void setRecLactionList(String recLactionList) {
        this.recLactionList = recLactionList;
    }

    public String getPrivilegeCodeList() {
        return privilegeCodeList;
    }

    public void setPrivilegeCodeList(String privilegeCodeList) {
        this.privilegeCodeList = privilegeCodeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camera tCamera = (Camera) o;

        if (cameraId != null ? !cameraId.equals(tCamera.cameraId) : tCamera.cameraId != null) return false;
        if (indexCode != null ? !indexCode.equals(tCamera.indexCode) : tCamera.indexCode != null) return false;
        if (name != null ? !name.equals(tCamera.name) : tCamera.name != null) return false;
        if (deviceId != null ? !deviceId.equals(tCamera.deviceId) : tCamera.deviceId != null) return false;
        if (regionId != null ? !regionId.equals(tCamera.regionId) : tCamera.regionId != null) return false;
        if (controlUnitId != null ? !controlUnitId.equals(tCamera.controlUnitId) : tCamera.controlUnitId != null)
            return false;
        if (chanNum != null ? !chanNum.equals(tCamera.chanNum) : tCamera.chanNum != null) return false;
        if (cameraType != null ? !cameraType.equals(tCamera.cameraType) : tCamera.cameraType != null) return false;
        if (streamType != null ? !streamType.equals(tCamera.streamType) : tCamera.streamType != null) return false;
        if (latitude != null ? !latitude.equals(tCamera.latitude) : tCamera.latitude != null) return false;
        if (longitude != null ? !longitude.equals(tCamera.longitude) : tCamera.longitude != null) return false;
        if (sequenceIdx != null ? !sequenceIdx.equals(tCamera.sequenceIdx) : tCamera.sequenceIdx != null) return false;
        if (caseCadeIndex != null ? !caseCadeIndex.equals(tCamera.caseCadeIndex) : tCamera.caseCadeIndex != null)
            return false;
        if (recLactionList != null ? !recLactionList.equals(tCamera.recLactionList) : tCamera.recLactionList != null)
            return false;
        if (privilegeCodeList != null ? !privilegeCodeList.equals(tCamera.privilegeCodeList) : tCamera.privilegeCodeList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cameraId != null ? cameraId.hashCode() : 0;
        result = 31 * result + (indexCode != null ? indexCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        result = 31 * result + (controlUnitId != null ? controlUnitId.hashCode() : 0);
        result = 31 * result + (chanNum != null ? chanNum.hashCode() : 0);
        result = 31 * result + (cameraType != null ? cameraType.hashCode() : 0);
        result = 31 * result + (streamType != null ? streamType.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (sequenceIdx != null ? sequenceIdx.hashCode() : 0);
        result = 31 * result + (caseCadeIndex != null ? caseCadeIndex.hashCode() : 0);
        result = 31 * result + (recLactionList != null ? recLactionList.hashCode() : 0);
        result = 31 * result + (privilegeCodeList != null ? privilegeCodeList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "cameraId=" + cameraId +
                ", indexCode='" + indexCode + '\'' +
                ", name='" + name + '\'' +
                ", deviceId=" + deviceId +
                ", regionId=" + regionId +
                ", controlUnitId=" + controlUnitId +
                ", chanNum=" + chanNum +
                ", cameraType=" + cameraType +
                ", streamType=" + streamType +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", sequenceIdx=" + sequenceIdx +
                ", caseCadeIndex='" + caseCadeIndex + '\'' +
                ", recLactionList='" + recLactionList + '\'' +
                ", privilegeCodeList='" + privilegeCodeList + '\'' +
                '}';
    }
}
