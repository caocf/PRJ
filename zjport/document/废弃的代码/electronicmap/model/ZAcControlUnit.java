package com.zjport.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Will on 2016/11/4 10:18.
 */
public class ZAcControlUnit implements Serializable{

    private static final long serialVersionUID = -6728171834161720568L;
    private Integer controlUnitId;
    private String indexCode;
    private String name;
    private Integer parentId;
    private String parentTree;
    private Integer unitLevel;
    private Integer defaultRegionId;
    private Integer useStreamServer;
    private Integer streamServerId;
    private String description;
    private Integer sequenceIdx;
    private Integer operatorId;
    private Date createTime;
    private Date updateTime;
    private Integer cmsCascadeId;
    private Integer cmsCascadeType;
    private Integer shareLevel;
    private String sequenceSort;
    private Integer useStreamStrictPath;
    private String cascadeIndex;
    private Integer controlOrder;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentTree() {
        return parentTree;
    }

    public void setParentTree(String parentTree) {
        this.parentTree = parentTree;
    }

    public Integer getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(Integer unitLevel) {
        this.unitLevel = unitLevel;
    }

    public Integer getDefaultRegionId() {
        return defaultRegionId;
    }

    public void setDefaultRegionId(Integer defaultRegionId) {
        this.defaultRegionId = defaultRegionId;
    }

    public Integer getUseStreamServer() {
        return useStreamServer;
    }

    public void setUseStreamServer(Integer useStreamServer) {
        this.useStreamServer = useStreamServer;
    }

    public Integer getStreamServerId() {
        return streamServerId;
    }

    public void setStreamServerId(Integer streamServerId) {
        this.streamServerId = streamServerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getCmsCascadeId() {
        return cmsCascadeId;
    }

    public void setCmsCascadeId(Integer cmsCascadeId) {
        this.cmsCascadeId = cmsCascadeId;
    }

    public Integer getCmsCascadeType() {
        return cmsCascadeType;
    }

    public void setCmsCascadeType(Integer cmsCascadeType) {
        this.cmsCascadeType = cmsCascadeType;
    }

    public Integer getShareLevel() {
        return shareLevel;
    }

    public void setShareLevel(Integer shareLevel) {
        this.shareLevel = shareLevel;
    }

    public String getSequenceSort() {
        return sequenceSort;
    }

    public void setSequenceSort(String sequenceSort) {
        this.sequenceSort = sequenceSort;
    }

    public Integer getUseStreamStrictPath() {
        return useStreamStrictPath;
    }

    public void setUseStreamStrictPath(Integer useStreamStrictPath) {
        this.useStreamStrictPath = useStreamStrictPath;
    }

    public String getCascadeIndex() {
        return cascadeIndex;
    }

    public void setCascadeIndex(String cascadeIndex) {
        this.cascadeIndex = cascadeIndex;
    }

    public Integer getControlOrder() {
        return controlOrder;
    }

    public void setControlOrder(Integer controlOrder) {
        this.controlOrder = controlOrder;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ZAcControlUnit that = (ZAcControlUnit) object;

        if (controlUnitId != null ? !controlUnitId.equals(that.controlUnitId) : that.controlUnitId != null)
            return false;
        if (indexCode != null ? !indexCode.equals(that.indexCode) : that.indexCode != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (parentTree != null ? !parentTree.equals(that.parentTree) : that.parentTree != null) return false;
        if (unitLevel != null ? !unitLevel.equals(that.unitLevel) : that.unitLevel != null) return false;
        if (defaultRegionId != null ? !defaultRegionId.equals(that.defaultRegionId) : that.defaultRegionId != null)
            return false;
        if (useStreamServer != null ? !useStreamServer.equals(that.useStreamServer) : that.useStreamServer != null)
            return false;
        if (streamServerId != null ? !streamServerId.equals(that.streamServerId) : that.streamServerId != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (sequenceIdx != null ? !sequenceIdx.equals(that.sequenceIdx) : that.sequenceIdx != null) return false;
        if (operatorId != null ? !operatorId.equals(that.operatorId) : that.operatorId != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
        if (cmsCascadeId != null ? !cmsCascadeId.equals(that.cmsCascadeId) : that.cmsCascadeId != null) return false;
        if (cmsCascadeType != null ? !cmsCascadeType.equals(that.cmsCascadeType) : that.cmsCascadeType != null)
            return false;
        if (shareLevel != null ? !shareLevel.equals(that.shareLevel) : that.shareLevel != null) return false;
        if (sequenceSort != null ? !sequenceSort.equals(that.sequenceSort) : that.sequenceSort != null) return false;
        if (useStreamStrictPath != null ? !useStreamStrictPath.equals(that.useStreamStrictPath) : that.useStreamStrictPath != null)
            return false;
        if (cascadeIndex != null ? !cascadeIndex.equals(that.cascadeIndex) : that.cascadeIndex != null) return false;
        if (controlOrder != null ? !controlOrder.equals(that.controlOrder) : that.controlOrder != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = controlUnitId != null ? controlUnitId.hashCode() : 0;
        result = 31 * result + (indexCode != null ? indexCode.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parentTree != null ? parentTree.hashCode() : 0);
        result = 31 * result + (unitLevel != null ? unitLevel.hashCode() : 0);
        result = 31 * result + (defaultRegionId != null ? defaultRegionId.hashCode() : 0);
        result = 31 * result + (useStreamServer != null ? useStreamServer.hashCode() : 0);
        result = 31 * result + (streamServerId != null ? streamServerId.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (sequenceIdx != null ? sequenceIdx.hashCode() : 0);
        result = 31 * result + (operatorId != null ? operatorId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (cmsCascadeId != null ? cmsCascadeId.hashCode() : 0);
        result = 31 * result + (cmsCascadeType != null ? cmsCascadeType.hashCode() : 0);
        result = 31 * result + (shareLevel != null ? shareLevel.hashCode() : 0);
        result = 31 * result + (sequenceSort != null ? sequenceSort.hashCode() : 0);
        result = 31 * result + (useStreamStrictPath != null ? useStreamStrictPath.hashCode() : 0);
        result = 31 * result + (cascadeIndex != null ? cascadeIndex.hashCode() : 0);
        result = 31 * result + (controlOrder != null ? controlOrder.hashCode() : 0);
        return result;
    }
}
