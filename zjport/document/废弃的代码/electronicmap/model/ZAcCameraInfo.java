package com.zjport.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by Will on 2016/11/4 10:18.
 */
public class ZAcCameraInfo implements Serializable{

    private static final long serialVersionUID = 5644903025767050606L;
    private Integer cameraId;
    private Integer deviceId;
    private Integer powerChannelId;
    private Integer controlUnitId;
    private String indexCode;
    private String name;
    private Integer channelNo;
    private Integer cameraType;
    private Integer vrmServerId;
    private Integer recordLocationSet;
    private Integer recordMode;
    private Integer streamType;
    private Integer streamLinkType;
    private Integer shareFlag;
    private String matrixCode;
    private Integer pixel;
    private Integer ptzType;
    private Integer isBindAudio;
    private Integer isDisplay;
    private Integer mainBitRate;
    private Integer mainRateType;
    private String streamUrl;
    private Integer sequenceIdx;
    private Integer operatorId;
    private Date createTime;
    private Date updateTime;
    private Integer regionId;
    private String latitude;
    private String longitude;
    private BigInteger isShowMap;
    private String gisName;
    private String cascadeIndex;
    private String localCascadeIndex;
    private BigInteger channelType;
    private Integer manufacturerType;
    private Integer state;
    private String flag;

    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getPowerChannelId() {
        return powerChannelId;
    }

    public void setPowerChannelId(Integer powerChannelId) {
        this.powerChannelId = powerChannelId;
    }

    public Integer getControlUnitId() {
        return controlUnitId;
    }

    public void setControlUnitId(Integer controlUnitId) {
        this.controlUnitId = controlUnitId;
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

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public Integer getCameraType() {
        return cameraType;
    }

    public void setCameraType(Integer cameraType) {
        this.cameraType = cameraType;
    }

    public Integer getVrmServerId() {
        return vrmServerId;
    }

    public void setVrmServerId(Integer vrmServerId) {
        this.vrmServerId = vrmServerId;
    }

    public Integer getRecordLocationSet() {
        return recordLocationSet;
    }

    public void setRecordLocationSet(Integer recordLocationSet) {
        this.recordLocationSet = recordLocationSet;
    }

    public Integer getRecordMode() {
        return recordMode;
    }

    public void setRecordMode(Integer recordMode) {
        this.recordMode = recordMode;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    public Integer getStreamLinkType() {
        return streamLinkType;
    }

    public void setStreamLinkType(Integer streamLinkType) {
        this.streamLinkType = streamLinkType;
    }

    public Integer getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(Integer shareFlag) {
        this.shareFlag = shareFlag;
    }

    public String getMatrixCode() {
        return matrixCode;
    }

    public void setMatrixCode(String matrixCode) {
        this.matrixCode = matrixCode;
    }

    public Integer getPixel() {
        return pixel;
    }

    public void setPixel(Integer pixel) {
        this.pixel = pixel;
    }

    public Integer getPtzType() {
        return ptzType;
    }

    public void setPtzType(Integer ptzType) {
        this.ptzType = ptzType;
    }

    public Integer getIsBindAudio() {
        return isBindAudio;
    }

    public void setIsBindAudio(Integer isBindAudio) {
        this.isBindAudio = isBindAudio;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getMainBitRate() {
        return mainBitRate;
    }

    public void setMainBitRate(Integer mainBitRate) {
        this.mainBitRate = mainBitRate;
    }

    public Integer getMainRateType() {
        return mainRateType;
    }

    public void setMainRateType(Integer mainRateType) {
        this.mainRateType = mainRateType;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public Integer getSequenceIdx() {
        return sequenceIdx;
    }

    public void setSequenceIdx(Integer sequenceIdx) {
        this.sequenceIdx = sequenceIdx;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
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

    public BigInteger getIsShowMap() {
        return isShowMap;
    }

    public void setIsShowMap(BigInteger isShowMap) {
        this.isShowMap = isShowMap;
    }

    public String getGisName() {
        return gisName;
    }

    public void setGisName(String gisName) {
        this.gisName = gisName;
    }

    public String getCascadeIndex() {
        return cascadeIndex;
    }

    public void setCascadeIndex(String cascadeIndex) {
        this.cascadeIndex = cascadeIndex;
    }

    public String getLocalCascadeIndex() {
        return localCascadeIndex;
    }

    public void setLocalCascadeIndex(String localCascadeIndex) {
        this.localCascadeIndex = localCascadeIndex;
    }

    public BigInteger getChannelType() {
        return channelType;
    }

    public void setChannelType(BigInteger channelType) {
        this.channelType = channelType;
    }

    public Integer getManufacturerType() {
        return manufacturerType;
    }

    public void setManufacturerType(Integer manufacturerType) {
        this.manufacturerType = manufacturerType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ZAcCameraInfo that = (ZAcCameraInfo) object;

        if (cameraId != null ? !cameraId.equals(that.cameraId) : that.cameraId != null) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (powerChannelId != null ? !powerChannelId.equals(that.powerChannelId) : that.powerChannelId != null)
            return false;
        if (controlUnitId != null ? !controlUnitId.equals(that.controlUnitId) : that.controlUnitId != null)
            return false;
        if (indexCode != null ? !indexCode.equals(that.indexCode) : that.indexCode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (channelNo != null ? !channelNo.equals(that.channelNo) : that.channelNo != null) return false;
        if (cameraType != null ? !cameraType.equals(that.cameraType) : that.cameraType != null) return false;
        if (vrmServerId != null ? !vrmServerId.equals(that.vrmServerId) : that.vrmServerId != null) return false;
        if (recordLocationSet != null ? !recordLocationSet.equals(that.recordLocationSet) : that.recordLocationSet != null)
            return false;
        if (recordMode != null ? !recordMode.equals(that.recordMode) : that.recordMode != null) return false;
        if (streamType != null ? !streamType.equals(that.streamType) : that.streamType != null) return false;
        if (streamLinkType != null ? !streamLinkType.equals(that.streamLinkType) : that.streamLinkType != null)
            return false;
        if (shareFlag != null ? !shareFlag.equals(that.shareFlag) : that.shareFlag != null) return false;
        if (matrixCode != null ? !matrixCode.equals(that.matrixCode) : that.matrixCode != null) return false;
        if (pixel != null ? !pixel.equals(that.pixel) : that.pixel != null) return false;
        if (ptzType != null ? !ptzType.equals(that.ptzType) : that.ptzType != null) return false;
        if (isBindAudio != null ? !isBindAudio.equals(that.isBindAudio) : that.isBindAudio != null) return false;
        if (isDisplay != null ? !isDisplay.equals(that.isDisplay) : that.isDisplay != null) return false;
        if (mainBitRate != null ? !mainBitRate.equals(that.mainBitRate) : that.mainBitRate != null) return false;
        if (mainRateType != null ? !mainRateType.equals(that.mainRateType) : that.mainRateType != null) return false;
        if (streamUrl != null ? !streamUrl.equals(that.streamUrl) : that.streamUrl != null) return false;
        if (sequenceIdx != null ? !sequenceIdx.equals(that.sequenceIdx) : that.sequenceIdx != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (regionId != null ? !regionId.equals(that.regionId) : that.regionId != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (isShowMap != null ? !isShowMap.equals(that.isShowMap) : that.isShowMap != null) return false;
        if (gisName != null ? !gisName.equals(that.gisName) : that.gisName != null) return false;
        if (cascadeIndex != null ? !cascadeIndex.equals(that.cascadeIndex) : that.cascadeIndex != null) return false;
        if (localCascadeIndex != null ? !localCascadeIndex.equals(that.localCascadeIndex) : that.localCascadeIndex != null)
            return false;
        if (channelType != null ? !channelType.equals(that.channelType) : that.channelType != null) return false;
        if (manufacturerType != null ? !manufacturerType.equals(that.manufacturerType) : that.manufacturerType != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (flag != null ? !flag.equals(that.flag) : that.flag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cameraId != null ? cameraId.hashCode() : 0;
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (powerChannelId != null ? powerChannelId.hashCode() : 0);
        result = 31 * result + (controlUnitId != null ? controlUnitId.hashCode() : 0);
        result = 31 * result + (indexCode != null ? indexCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (channelNo != null ? channelNo.hashCode() : 0);
        result = 31 * result + (cameraType != null ? cameraType.hashCode() : 0);
        result = 31 * result + (vrmServerId != null ? vrmServerId.hashCode() : 0);
        result = 31 * result + (recordLocationSet != null ? recordLocationSet.hashCode() : 0);
        result = 31 * result + (recordMode != null ? recordMode.hashCode() : 0);
        result = 31 * result + (streamType != null ? streamType.hashCode() : 0);
        result = 31 * result + (streamLinkType != null ? streamLinkType.hashCode() : 0);
        result = 31 * result + (shareFlag != null ? shareFlag.hashCode() : 0);
        result = 31 * result + (matrixCode != null ? matrixCode.hashCode() : 0);
        result = 31 * result + (pixel != null ? pixel.hashCode() : 0);
        result = 31 * result + (ptzType != null ? ptzType.hashCode() : 0);
        result = 31 * result + (isBindAudio != null ? isBindAudio.hashCode() : 0);
        result = 31 * result + (isDisplay != null ? isDisplay.hashCode() : 0);
        result = 31 * result + (mainBitRate != null ? mainBitRate.hashCode() : 0);
        result = 31 * result + (mainRateType != null ? mainRateType.hashCode() : 0);
        result = 31 * result + (streamUrl != null ? streamUrl.hashCode() : 0);
        result = 31 * result + (sequenceIdx != null ? sequenceIdx.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (isShowMap != null ? isShowMap.hashCode() : 0);
        result = 31 * result + (gisName != null ? gisName.hashCode() : 0);
        result = 31 * result + (cascadeIndex != null ? cascadeIndex.hashCode() : 0);
        result = 31 * result + (localCascadeIndex != null ? localCascadeIndex.hashCode() : 0);
        result = 31 * result + (channelType != null ? channelType.hashCode() : 0);
        result = 31 * result + (manufacturerType != null ? manufacturerType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        return result;
    }
}
