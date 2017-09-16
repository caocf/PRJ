package net.hxkg.simple;

import java.io.Serializable;

public class SimpleModel implements Serializable
{
	private String pid;
    private String firstman;
    private String secman;
    private String target;
    private String reason;
    private String detail;
    private String location;
    private String checker;
    private String status;
    private String sumbdate;
    private String isllegal;
    private String showdate;
    
   
    
    public Double lat,lon;
    public int typeid; 
    public int baseID;
    public String typename;
    public String penaltyString;
   
    public String getShowdate() {
        return showdate;
    }

    public void setShowdate(String showdate) {
        this.showdate = showdate;
    }
    
    public String getIsllegal() {
        return isllegal;
    }

    public void setIsllegal(String isllegal) {
        this.isllegal = isllegal;
    }

    public String getSumbdate() {
        return sumbdate;
    }

    public void setSumbdate(String sumbdate) {
        this.sumbdate = sumbdate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFirstman() {
        return firstman;
    }

    public void setFirstman(String firstman) {
        this.firstman = firstman;
    }

    public String getSecman() {
        return secman;
    }

    public void setSecman(String secman) {
        this.secman = secman;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
