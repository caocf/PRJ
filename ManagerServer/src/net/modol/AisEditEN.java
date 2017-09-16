package net.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/11/24.
 */
public class AisEditEN {
    private String shipnameid;
    private String bh;
    private String dir;
    AisStatusEN status=new AisStatusEN();
    Date committime;
    String opinion;

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getCommittime() {
        return committime;
    }

    public void setCommittime(Date committime) {
        this.committime = committime;
    }

    public AisStatusEN getStatus() {
        return status;
    }

    public void setStatus(AisStatusEN status) {
        this.status = status;
    }

    public String getShipnameid() {
        return shipnameid;
    }

    public void setShipnameid(String shipnameid) {
        this.shipnameid = shipnameid;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AisEditEN aisEditEN = (AisEditEN) o;

        if (shipnameid != null ? !shipnameid.equals(aisEditEN.shipnameid) : aisEditEN.shipnameid != null) return false;
        if (bh != null ? !bh.equals(aisEditEN.bh) : aisEditEN.bh != null) return false;
        if (dir != null ? !dir.equals(aisEditEN.dir) : aisEditEN.dir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shipnameid != null ? shipnameid.hashCode() : 0;
        result = 31 * result + (bh != null ? bh.hashCode() : 0);
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
}
