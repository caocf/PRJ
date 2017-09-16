package net.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/7/19.
 */
public class VersionEN {
    private int id;
    private int versionNum;
    private String verstionDec;
    private Date uptime;
    private String address;
    private String log;

    public int getId() {
        return id;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public String getVerstionDec() {
        return verstionDec;
    }

    public void setVerstionDec(String verstionDec) {
        this.verstionDec = verstionDec;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setId(int id) {
        this.id = id;
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

        VersionEN versionEN = (VersionEN) o;

        if (id != versionEN.id) return false;
        if (uptime != null ? !uptime.equals(versionEN.uptime) : versionEN.uptime != null) return false;
        if (address != null ? !address.equals(versionEN.address) : versionEN.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (uptime != null ? uptime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
