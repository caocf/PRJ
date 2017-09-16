package com.zjport.model;

import javax.persistence.*;

/**
 * Created by TWQ on 2016/8/22.
 */
@Entity
@Table(name = "t_board", schema = "zjport", catalog = "")
public class TBoard {
    private int stBoardId;
    private String stBoardName;
    private String stBoardIp;
    private String stBoardDescribe;
    private String stBoardManager;

    @Id
    @Column(name = "ST_BOARD_ID")
    public int getStBoardId() {
        return stBoardId;
    }

    public void setStBoardId(int stBoardId) {
        this.stBoardId = stBoardId;
    }

    @Basic
    @Column(name = "ST_BOARD_NAME")
    public String getStBoardName() {
        return stBoardName;
    }

    public void setStBoardName(String stBoardName) {
        this.stBoardName = stBoardName;
    }

    @Basic
    @Column(name = "ST_BOARD_IP")
    public String getStBoardIp() {
        return stBoardIp;
    }

    public void setStBoardIp(String stBoardIp) {
        this.stBoardIp = stBoardIp;
    }

    @Basic
    @Column(name = "ST_BOARD_DESCRIBE")
    public String getStBoardDescribe() {
        return stBoardDescribe;
    }

    public void setStBoardDescribe(String stBoardDescribe) {
        this.stBoardDescribe = stBoardDescribe;
    }

    @Basic
    @Column(name = "ST_BOARD_MANAGER")
    public String getStBoardManager() {
        return stBoardManager;
    }

    public void setStBoardManager(String stBoardManager) {
        this.stBoardManager = stBoardManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TBoard tBoard = (TBoard) o;

        if (stBoardId != tBoard.stBoardId) return false;
        if (stBoardName != null ? !stBoardName.equals(tBoard.stBoardName) : tBoard.stBoardName != null) return false;
        if (stBoardIp != null ? !stBoardIp.equals(tBoard.stBoardIp) : tBoard.stBoardIp != null) return false;
        if (stBoardDescribe != null ? !stBoardDescribe.equals(tBoard.stBoardDescribe) : tBoard.stBoardDescribe != null)
            return false;
        if (stBoardManager != null ? !stBoardManager.equals(tBoard.stBoardManager) : tBoard.stBoardManager != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = stBoardId;
        result = 31 * result + (stBoardName != null ? stBoardName.hashCode() : 0);
        result = 31 * result + (stBoardIp != null ? stBoardIp.hashCode() : 0);
        result = 31 * result + (stBoardDescribe != null ? stBoardDescribe.hashCode() : 0);
        result = 31 * result + (stBoardManager != null ? stBoardManager.hashCode() : 0);
        return result;
    }
}
