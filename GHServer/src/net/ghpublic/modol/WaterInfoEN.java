package net.ghpublic.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/5/9.
 */
public class WaterInfoEN {
    private int id;
    private Date updatetime;
    private String whatchpoint;
    private Double swsz;
    private Double syl;
    private Double ryl;
    private Double cjjx;
    private Double cwj;
    private CommonAreaEN region;

    public CommonAreaEN getRegion() {
        return region;
    }

    public void setRegion(CommonAreaEN region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }


    public double getSwsz() {
        return swsz;
    }

    public void setSwsz(double swsz) {
        this.swsz = swsz;
    }

    public Double getSyl() {
        return syl;
    }

    public void setSyl(Double syl) {
        this.syl = syl;
    }

    public Double getRyl() {
        return ryl;
    }

    public void setRyl(Double ryl) {
        this.ryl = ryl;
    }

    public Double getCjjx() {
        return cjjx;
    }

    public void setCjjx(Double cjjx) {
        this.cjjx = cjjx;
    }

    public Double getCwj() {
        return cwj;
    }

    public void setCwj(Double cwj) {
        this.cwj = cwj;
    }


    public String getWhatchpoint() {
        return whatchpoint;
    }

    public void setWhatchpoint(String whatchpoint) {
        this.whatchpoint = whatchpoint;
    }

    public void setSwsz(Double swsz) {
        this.swsz = swsz;
    }
}
