package net.hxkg.channel;



import java.util.Date;

/**
 * CHdHdaojcxx entity. @author MyEclipse Persistence Tools
 */

public class CHdHdaojcxx implements java.io.Serializable, Comparable<CHdHdaojcxx> {

    // Fields

    private Integer id;

    private String hdbh;

    private String hdmc;

    private Integer sfqshd;

    private Integer sssjbh;

    private Integer sfjjx;

    private Integer sfgg;

    private String bz;

    private Date createtime; 
    private Date updatetime;


    private int hduancnt;
    private String hduansjbh;

    public int getHduancnt() {
        return hduancnt;
    }

    public void setHduancnt(int hduancnt) {
        this.hduancnt = hduancnt;
    }

    public String getHduansjbh() {
        return hduansjbh;
    }

    public void setHduansjbh(String hduansjbh) {
        this.hduansjbh = hduansjbh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHdbh() {
        return hdbh;
    }

    public void setHdbh(String hdbh) {
        this.hdbh = hdbh;
    }

    public String getHdmc() {
        return hdmc;
    }

    public void setHdmc(String hdmc) {
        this.hdmc = hdmc;
    }

    public Integer getSfqshd() {
        return sfqshd;
    }

    public void setSfqshd(Integer sfqshd) {
        this.sfqshd = sfqshd;
    }

    public Integer getSssjbh() {
        return sssjbh;
    }

    public void setSssjbh(Integer sssjbh) {
        this.sssjbh = sssjbh;
    }

    public Integer getSfjjx() {
        return sfjjx;
    }

    public void setSfjjx(Integer sfjjx) {
        this.sfjjx = sfjjx;
    }

    public Integer getSfgg() {
        return sfgg;
    }

    public void setSfgg(Integer sfgg) {
        this.sfgg = sfgg;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public CHdHdaojcxx() {
    }

    public CHdHdaojcxx(String hdbh, String hdmc, Integer sfqshd, Integer sssjbh, Integer sfjjx, Integer sfgg, String bz, Date createtime, Date updatetime) {
        this.hdbh = hdbh;
        this.hdmc = hdmc;
        this.sfqshd = sfqshd;
        this.sssjbh = sssjbh;
        this.sfjjx = sfjjx;
        this.sfgg = sfgg;
        this.bz = bz;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    public CHdHdaojcxx(Date updatetime, Integer id, String hdbh, String hdmc, Integer sfqshd, Integer sssjbh, Integer sfjjx, Integer sfgg, String bz, Date createtime) {
        this.updatetime = updatetime;
        this.id = id;
        this.hdbh = hdbh;
        this.hdmc = hdmc;
        this.sfqshd = sfqshd;
        this.sssjbh = sssjbh;
        this.sfjjx = sfjjx;
        this.sfgg = sfgg;
        this.bz = bz;
        this.createtime = createtime;
    }

    @Override
    public int compareTo(CHdHdaojcxx hd) {
        int val= this.getSfgg()-hd.getSfgg();
        if (val!=0){
            return val;
        }
        return  this.getCreatetime().compareTo(hd.getCreatetime());
    }
}