package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Admin on 2016/9/9.
 */
public class JcxxYhEN {
    private String id;
    private String zh;
    private String xm;

    private String mm;
    private String zw;
    private String dw;
    private String bm;
    private String js;
    private String sjzg;
    private String yx;
    private String bgdh;
    private String sjhm;
    private String xnwh;
    private String zt;
    private Integer wzh;
    private String sjzzjgidlb;
    private String jdid;
    private String bmmc;
    private UserRoleEN userRoleEN;
    String personal;

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public UserRoleEN getUserRoleEN() {
        return userRoleEN;
    }

    public void setUserRoleEN(UserRoleEN userRoleEN) {
        this.userRoleEN = userRoleEN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }
    @JsonIgnore
    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getSjzg() {
        return sjzg;
    }

    public void setSjzg(String sjzg) {
        this.sjzg = sjzg;
    }

    public String getYx() {
        return yx;
    }

    public void setYx(String yx) {
        this.yx = yx;
    }

    public String getBgdh() {
        return bgdh;
    }

    public void setBgdh(String bgdh) {
        this.bgdh = bgdh;
    }

    public String getSjhm() {
        return sjhm;
    }

    public void setSjhm(String sjhm) {
        this.sjhm = sjhm;
    }

    public String getXnwh() {
        return xnwh;
    }

    public void setXnwh(String xnwh) {
        this.xnwh = xnwh;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Integer getWzh() {
        return wzh;
    }

    public void setWzh(Integer wzh) {
        this.wzh = wzh;
    }

    public String getSjzzjgidlb() {
        return sjzzjgidlb;
    }

    public void setSjzzjgidlb(String sjzzjgidlb) {
        this.sjzzjgidlb = sjzzjgidlb;
    }

    public String getJdid() {
        return jdid;
    }

    public void setJdid(String jdid) {
        this.jdid = jdid;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JcxxYhEN jcxxYhEN = (JcxxYhEN) o;

        if (id != null ? !id.equals(jcxxYhEN.id) : jcxxYhEN.id != null) return false;
        if (zh != null ? !zh.equals(jcxxYhEN.zh) : jcxxYhEN.zh != null) return false;
        if (xm != null ? !xm.equals(jcxxYhEN.xm) : jcxxYhEN.xm != null) return false;
        if (mm != null ? !mm.equals(jcxxYhEN.mm) : jcxxYhEN.mm != null) return false;
        if (zw != null ? !zw.equals(jcxxYhEN.zw) : jcxxYhEN.zw != null) return false;
        if (dw != null ? !dw.equals(jcxxYhEN.dw) : jcxxYhEN.dw != null) return false;
        if (bm != null ? !bm.equals(jcxxYhEN.bm) : jcxxYhEN.bm != null) return false;
        if (js != null ? !js.equals(jcxxYhEN.js) : jcxxYhEN.js != null) return false;
        if (sjzg != null ? !sjzg.equals(jcxxYhEN.sjzg) : jcxxYhEN.sjzg != null) return false;
        if (yx != null ? !yx.equals(jcxxYhEN.yx) : jcxxYhEN.yx != null) return false;
        if (bgdh != null ? !bgdh.equals(jcxxYhEN.bgdh) : jcxxYhEN.bgdh != null) return false;
        if (sjhm != null ? !sjhm.equals(jcxxYhEN.sjhm) : jcxxYhEN.sjhm != null) return false;
        if (xnwh != null ? !xnwh.equals(jcxxYhEN.xnwh) : jcxxYhEN.xnwh != null) return false;
        if (zt != null ? !zt.equals(jcxxYhEN.zt) : jcxxYhEN.zt != null) return false;
        if (wzh != null ? !wzh.equals(jcxxYhEN.wzh) : jcxxYhEN.wzh != null) return false;
        if (sjzzjgidlb != null ? !sjzzjgidlb.equals(jcxxYhEN.sjzzjgidlb) : jcxxYhEN.sjzzjgidlb != null) return false;
        if (jdid != null ? !jdid.equals(jcxxYhEN.jdid) : jcxxYhEN.jdid != null) return false;
        if (bmmc != null ? !bmmc.equals(jcxxYhEN.bmmc) : jcxxYhEN.bmmc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (zh != null ? zh.hashCode() : 0);
        result = 31 * result + (xm != null ? xm.hashCode() : 0);
        result = 31 * result + (mm != null ? mm.hashCode() : 0);
        result = 31 * result + (zw != null ? zw.hashCode() : 0);
        result = 31 * result + (dw != null ? dw.hashCode() : 0);
        result = 31 * result + (bm != null ? bm.hashCode() : 0);
        result = 31 * result + (js != null ? js.hashCode() : 0);
        result = 31 * result + (sjzg != null ? sjzg.hashCode() : 0);
        result = 31 * result + (yx != null ? yx.hashCode() : 0);
        result = 31 * result + (bgdh != null ? bgdh.hashCode() : 0);
        result = 31 * result + (sjhm != null ? sjhm.hashCode() : 0);
        result = 31 * result + (xnwh != null ? xnwh.hashCode() : 0);
        result = 31 * result + (zt != null ? zt.hashCode() : 0);
        result = 31 * result + (wzh != null ? wzh.hashCode() : 0);
        result = 31 * result + (sjzzjgidlb != null ? sjzzjgidlb.hashCode() : 0);
        result = 31 * result + (jdid != null ? jdid.hashCode() : 0);
        result = 31 * result + (bmmc != null ? bmmc.hashCode() : 0);
        return result;
    }
}
