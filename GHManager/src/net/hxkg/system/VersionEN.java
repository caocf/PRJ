package net.hxkg.system;



import java.util.Date;

/**
 * Created by Admin on 2016/7/19.
 */
public class VersionEN {
    private int id;
    private int num;
    private String desc;
    private String uptime;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
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
        if (desc != null ? !desc.equals(versionEN.desc) : versionEN.desc != null) return false;
        if (uptime != null ? !uptime.equals(versionEN.uptime) : versionEN.uptime != null) return false;
        if (address != null ? !address.equals(versionEN.address) : versionEN.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (uptime != null ? uptime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
