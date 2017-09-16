package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/9/6.
 */
@Entity
@Table(name = "t_knowledge_base_structure", schema = "zjport", catalog = "")
public class TKnowledgeBaseStructure {
    private int stStructureId;
    private String stStructureName;
    private Integer stParentStructureId;
    private String stStructureDescribe;

    @Id
    @Column(name = "ST_STRUCTURE_ID")
    public int getStStructureId() {
        return stStructureId;
    }

    public void setStStructureId(int stStructureId) {
        this.stStructureId = stStructureId;
    }

    @Basic
    @Column(name = "ST_STRUCTURE_NAME")
    public String getStStructureName() {
        return stStructureName;
    }

    public void setStStructureName(String stStructureName) {
        this.stStructureName = stStructureName;
    }

    @Basic
    @Column(name = "ST_PARENT_STRUCTURE_ID")
    public Integer getStParentStructureId() {
        return stParentStructureId;
    }

    public void setStParentStructureId(Integer stParentStructureId) {
        this.stParentStructureId = stParentStructureId;
    }

    @Basic
    @Column(name = "ST_STRUCTURE_DESCRIBE")
    public String getStStructureDescribe() {
        return stStructureDescribe;
    }

    public void setStStructureDescribe(String stStructureDescribe) {
        this.stStructureDescribe = stStructureDescribe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TKnowledgeBaseStructure that = (TKnowledgeBaseStructure) o;

        if (stStructureId != that.stStructureId) return false;
        if (stStructureName != null ? !stStructureName.equals(that.stStructureName) : that.stStructureName != null)
            return false;
        if (stParentStructureId != null ? !stParentStructureId.equals(that.stParentStructureId) : that.stParentStructureId != null)
            return false;
        if (stStructureDescribe != null ? !stStructureDescribe.equals(that.stStructureDescribe) : that.stStructureDescribe != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stStructureId;
        result = 31 * result + (stStructureName != null ? stStructureName.hashCode() : 0);
        result = 31 * result + (stParentStructureId != null ? stParentStructureId.hashCode() : 0);
        result = 31 * result + (stStructureDescribe != null ? stStructureDescribe.hashCode() : 0);
        return result;
    }
}
