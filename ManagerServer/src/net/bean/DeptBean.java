package net.bean;

import java.util.List;

public class DeptBean {
    private String ID;
    private String ZZJGMC;
    private String SJZZJG;
    private Integer ZZJGLB;
    private Integer WZH;
    private Integer isleaf;
//    private List<DeptBean> dept;

    public DeptBean() {
    }

    public DeptBean(String ID, String ZZJGMC, String SJZZJG, Integer ZZJGLB, Integer WZH) {
        this.ID = ID;
        this.ZZJGMC = ZZJGMC;
        this.SJZZJG = SJZZJG;
        this.ZZJGLB = ZZJGLB;
        this.WZH = WZH;
    }

/*    public DeptBean(String ID, String ZZJGMC, String SJZZJG, Integer ZZJGLB, Integer WZH, List<DeptBean> dept) {
        this.ID = ID;
        this.ZZJGMC = ZZJGMC;
        this.SJZZJG = SJZZJG;
        this.ZZJGLB = ZZJGLB;
        this.WZH = WZH;
        this.dept = dept;
    }*/

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getZZJGMC() {
        return ZZJGMC;
    }

    public void setZZJGMC(String ZZJGMC) {
        this.ZZJGMC = ZZJGMC;
    }

    public String getSJZZJG() {
        return SJZZJG;
    }

    public void setSJZZJG(String SJZZJG) {
        this.SJZZJG = SJZZJG;
    }

    public Integer getZZJGLB() {
        return ZZJGLB;
    }

    public void setZZJGLB(Integer ZZJGLB) {
        this.ZZJGLB = ZZJGLB;
    }

    public Integer getWZH() {
        return WZH;
    }

    public void setWZH(Integer WZH) {
        this.WZH = WZH;
    }

    public Integer getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Integer isleaf) {
        this.isleaf = isleaf;
    }
/*    public List<DeptBean> getDept() {
        return dept;
    }

    public void setDept(List<DeptBean> dept) {
        this.dept = dept;
    }*/
}
