package net.modol;

/**
 * Created by Admin on 2016/9/9.
 */
public class JcxxZzjgEN {
    private String id;
    private String zzjgmc;
    private String zzjgbm;
    private String zzjgdm;
    private String zzjglb;
    private String zzym;
    private String sjzzjg;
    private String sjzzjgidlb;
    private Integer wzh;
    private String sfck;
    private String sfjd;
    private String zzjgjc;
    private String ssqy;
    private String wsdwbh;
    private String adcode;

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZzjgmc() {
        return zzjgmc;
    }

    public void setZzjgmc(String zzjgmc) {
        this.zzjgmc = zzjgmc;
    }

    public String getZzjgbm() {
        return zzjgbm;
    }

    public void setZzjgbm(String zzjgbm) {
        this.zzjgbm = zzjgbm;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public String getZzjglb() {
        return zzjglb;
    }

    public void setZzjglb(String zzjglb) {
        this.zzjglb = zzjglb;
    }

    public String getZzym() {
        return zzym;
    }

    public void setZzym(String zzym) {
        this.zzym = zzym;
    }

    public String getSjzzjg() {
        return sjzzjg;
    }

    public void setSjzzjg(String sjzzjg) {
        this.sjzzjg = sjzzjg;
    }

    public String getSjzzjgidlb() {
        return sjzzjgidlb;
    }

    public void setSjzzjgidlb(String sjzzjgidlb) {
        this.sjzzjgidlb = sjzzjgidlb;
    }

    public Integer getWzh() {
        return wzh;
    }

    public void setWzh(Integer wzh) {
        this.wzh = wzh;
    }

    public String getSfck() {
        return sfck;
    }

    public void setSfck(String sfck) {
        this.sfck = sfck;
    }

    public String getSfjd() {
        return sfjd;
    }

    public void setSfjd(String sfjd) {
        this.sfjd = sfjd;
    }

    public String getZzjgjc() {
        return zzjgjc;
    }

    public void setZzjgjc(String zzjgjc) {
        this.zzjgjc = zzjgjc;
    }

    public String getSsqy() {
        return ssqy;
    }

    public void setSsqy(String ssqy) {
        this.ssqy = ssqy;
    }

    public String getWsdwbh() {
        return wsdwbh;
    }

    public void setWsdwbh(String wsdwbh) {
        this.wsdwbh = wsdwbh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JcxxZzjgEN that = (JcxxZzjgEN) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (zzjgmc != null ? !zzjgmc.equals(that.zzjgmc) : that.zzjgmc != null) return false;
        if (zzjgbm != null ? !zzjgbm.equals(that.zzjgbm) : that.zzjgbm != null) return false;
        if (zzjgdm != null ? !zzjgdm.equals(that.zzjgdm) : that.zzjgdm != null) return false;
        if (zzjglb != null ? !zzjglb.equals(that.zzjglb) : that.zzjglb != null) return false;
        if (zzym != null ? !zzym.equals(that.zzym) : that.zzym != null) return false;
        if (sjzzjg != null ? !sjzzjg.equals(that.sjzzjg) : that.sjzzjg != null) return false;
        if (sjzzjgidlb != null ? !sjzzjgidlb.equals(that.sjzzjgidlb) : that.sjzzjgidlb != null) return false;
        if (wzh != null ? !wzh.equals(that.wzh) : that.wzh != null) return false;
        if (sfck != null ? !sfck.equals(that.sfck) : that.sfck != null) return false;
        if (sfjd != null ? !sfjd.equals(that.sfjd) : that.sfjd != null) return false;
        if (zzjgjc != null ? !zzjgjc.equals(that.zzjgjc) : that.zzjgjc != null) return false;
        if (ssqy != null ? !ssqy.equals(that.ssqy) : that.ssqy != null) return false;
        if (wsdwbh != null ? !wsdwbh.equals(that.wsdwbh) : that.wsdwbh != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (zzjgmc != null ? zzjgmc.hashCode() : 0);
        result = 31 * result + (zzjgbm != null ? zzjgbm.hashCode() : 0);
        result = 31 * result + (zzjgdm != null ? zzjgdm.hashCode() : 0);
        result = 31 * result + (zzjglb != null ? zzjglb.hashCode() : 0);
        result = 31 * result + (zzym != null ? zzym.hashCode() : 0);
        result = 31 * result + (sjzzjg != null ? sjzzjg.hashCode() : 0);
        result = 31 * result + (sjzzjgidlb != null ? sjzzjgidlb.hashCode() : 0);
        result = 31 * result + (wzh != null ? wzh.hashCode() : 0);
        result = 31 * result + (sfck != null ? sfck.hashCode() : 0);
        result = 31 * result + (sfjd != null ? sfjd.hashCode() : 0);
        result = 31 * result + (zzjgjc != null ? zzjgjc.hashCode() : 0);
        result = 31 * result + (ssqy != null ? ssqy.hashCode() : 0);
        result = 31 * result + (wsdwbh != null ? wsdwbh.hashCode() : 0);
        return result;
    }
}
