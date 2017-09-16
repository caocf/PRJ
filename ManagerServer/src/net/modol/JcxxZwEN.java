package net.modol;

/**
 * Created by Admin on 2016/9/9.
 */
public class JcxxZwEN {
    private String id;
    private String zwmc;
    private String ssdwid;
    private String ssdwmc;
    private Integer wzh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZwmc() {
        return zwmc;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc;
    }

    public String getSsdwid() {
        return ssdwid;
    }

    public void setSsdwid(String ssdwid) {
        this.ssdwid = ssdwid;
    }

    public String getSsdwmc() {
        return ssdwmc;
    }

    public void setSsdwmc(String ssdwmc) {
        this.ssdwmc = ssdwmc;
    }

    public Integer getWzh() {
        return wzh;
    }

    public void setWzh(Integer wzh) {
        this.wzh = wzh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JcxxZwEN jcxxZwEN = (JcxxZwEN) o;

        if (id != null ? !id.equals(jcxxZwEN.id) : jcxxZwEN.id != null) return false;
        if (zwmc != null ? !zwmc.equals(jcxxZwEN.zwmc) : jcxxZwEN.zwmc != null) return false;
        if (ssdwid != null ? !ssdwid.equals(jcxxZwEN.ssdwid) : jcxxZwEN.ssdwid != null) return false;
        if (ssdwmc != null ? !ssdwmc.equals(jcxxZwEN.ssdwmc) : jcxxZwEN.ssdwmc != null) return false;
        if (wzh != null ? !wzh.equals(jcxxZwEN.wzh) : jcxxZwEN.wzh != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (zwmc != null ? zwmc.hashCode() : 0);
        result = 31 * result + (ssdwid != null ? ssdwid.hashCode() : 0);
        result = 31 * result + (ssdwmc != null ? ssdwmc.hashCode() : 0);
        result = 31 * result + (wzh != null ? wzh.hashCode() : 0);
        return result;
    }
}
