package net.ghpublic.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 2016/4/28.
 */
public class ShipEN {
    private int shipid;
    private String shipname;
    private String regnumber;
    private PublicUserEN publicUserEN=new PublicUserEN();//many to one

    public PublicuserStatusEN getShipstatus() {
        return shipstatus;
    }

    public void setShipstatus(PublicuserStatusEN shipstatus) {
        this.shipstatus = shipstatus;
    }

    private PublicuserStatusEN shipstatus;
    Date binddate;
    Set<ShipcertEN> shipcertENs=new HashSet<>();

    public Set<ShipcertEN> getShipcertENs() {
        return shipcertENs;
    }

    public void setShipcertENs(Set<ShipcertEN> shipcertENs) {
        this.shipcertENs = shipcertENs;
    }

    public Date getBinddate() {
        return binddate;
    }

    public void setBinddate(Date binddate) {
        this.binddate = binddate;
    }

    public int getShipid() {
        return shipid;
    }

    public void setShipid(int shipid) {
        this.shipid = shipid;
    }
    //@JsonIgnore
    public PublicUserEN getPublicUserEN() {
        return publicUserEN;
    }

    public void setPublicUserEN(PublicUserEN publicUserEN) {
        this.publicUserEN = publicUserEN;
    }


    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipEN shipEN = (ShipEN) o;

        if (shipid != shipEN.shipid) return false;
        if (shipname != null ? !shipname.equals(shipEN.shipname) : shipEN.shipname != null) return false;
        if (regnumber != null ? !regnumber.equals(shipEN.regnumber) : shipEN.regnumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shipid;
        result = 31 * result + (shipname != null ? shipname.hashCode() : 0);
        result = 31 * result + (regnumber != null ? regnumber.hashCode() : 0);
        return result;
    }
}
