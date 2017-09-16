package net.ghpublic.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/7/8.
 */
public class CommonVersionEN {
    private Integer id;
    private Integer versionNum;
    private String verstionDec;
    private Date uptime;
    private String address;

    String appType;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    private String log;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public String getVerstionDec() {
        return verstionDec;
    }

    public void setVerstionDec(String verstionDec) {
        this.verstionDec = verstionDec;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonVersionEN that = (CommonVersionEN) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (versionNum != null ? !versionNum.equals(that.versionNum) : that.versionNum != null) return false;
        if (verstionDec != null ? !verstionDec.equals(that.verstionDec) : that.verstionDec != null) return false;
        if (uptime != null ? !uptime.equals(that.uptime) : that.uptime != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (versionNum != null ? versionNum.hashCode() : 0);
        result = 31 * result + (verstionDec != null ? verstionDec.hashCode() : 0);
        result = 31 * result + (uptime != null ? uptime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
