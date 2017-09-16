package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by Admin on 2016/7/16.
 */
public class LawBaseEN
{
    private int id;
    private String firstman;
    private String secman;
    private String target;
    private String reason;
    private String detail;
    private String location;
    private String checker;
    private String status;
    private Date sumbdate;
    private String descr;
    private String isllegal;
    Double lat;
    Double lon;
    LawTypeEN typeEN;
    String issimple;

    public String getIssimple() {
        return issimple;
    }

    public void setIssimple(String issimple) {
        this.issimple = issimple;
    }

    public LawTypeEN getTypeEN() {
        return typeEN;
    }

    public void setTypeEN(LawTypeEN typeEN) {
        this.typeEN = typeEN;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {

        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getIsllegal() {
        return isllegal;
    }

    public void setIsllegal(String isllegal) {
        this.isllegal = isllegal;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getSumbdate() {
        return sumbdate;
    }

    public void setSumbdate(Date sumbdate) {
        this.sumbdate = sumbdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LawBaseEN lawBaseEN = (LawBaseEN) o;

        if (id != lawBaseEN.id) return false;
        if (firstman != null ? !firstman.equals(lawBaseEN.firstman) : lawBaseEN.firstman != null) return false;
        if (secman != null ? !secman.equals(lawBaseEN.secman) : lawBaseEN.secman != null) return false;
        if (target != null ? !target.equals(lawBaseEN.target) : lawBaseEN.target != null) return false;
        if (reason != null ? !reason.equals(lawBaseEN.reason) : lawBaseEN.reason != null) return false;
        if (detail != null ? !detail.equals(lawBaseEN.detail) : lawBaseEN.detail != null) return false;
        if (location != null ? !location.equals(lawBaseEN.location) : lawBaseEN.location != null) return false;
        if (checker != null ? !checker.equals(lawBaseEN.checker) : lawBaseEN.checker != null) return false;
        if (status != null ? !status.equals(lawBaseEN.status) : lawBaseEN.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstman != null ? firstman.hashCode() : 0);
        result = 31 * result + (secman != null ? secman.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (checker != null ? checker.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
