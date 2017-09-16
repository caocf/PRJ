package net.ghpublic.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Admin on 2016/4/28.
 */
public class ShipcertEN {
    private String gppc;
    private String con;
    private String acirs;
    private String soc;
    private String wtl;
    private int id;
    private ShipEN shipEN=new ShipEN();
    @JsonIgnore
    public ShipEN getShipEN() {
        return shipEN;
    }

    public void setShipEN(ShipEN shipEN) {
        this.shipEN = shipEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGppc() {
        return gppc;
    }

    public void setGppc(String gppc) {
        this.gppc = gppc;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getAcirs() {
        return acirs;
    }

    public void setAcirs(String acirs) {
        this.acirs = acirs;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getWtl() {
        return wtl;
    }

    public void setWtl(String wtl) {
        this.wtl = wtl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipcertEN that = (ShipcertEN) o;

        if (gppc != null ? !gppc.equals(that.gppc) : that.gppc != null) return false;
        if (con != null ? !con.equals(that.con) : that.con != null) return false;
        if (acirs != null ? !acirs.equals(that.acirs) : that.acirs != null) return false;
        if (soc != null ? !soc.equals(that.soc) : that.soc != null) return false;
        if (wtl != null ? !wtl.equals(that.wtl) : that.wtl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gppc != null ? gppc.hashCode() : 0;
        result = 31 * result + (con != null ? con.hashCode() : 0);
        result = 31 * result + (acirs != null ? acirs.hashCode() : 0);
        result = 31 * result + (soc != null ? soc.hashCode() : 0);
        result = 31 * result + (wtl != null ? wtl.hashCode() : 0);
        return result;
    }
}
