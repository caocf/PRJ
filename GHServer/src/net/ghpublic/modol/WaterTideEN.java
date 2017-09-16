package net.ghpublic.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/8/8.
 */
public class WaterTideEN {
    private int id;
    private String port;
    private Date freshdate;
    private String standard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Date getFreshdate() {
        return freshdate;
    }

    public void setFreshdate(Date freshdate) {
        this.freshdate = freshdate;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaterTideEN that = (WaterTideEN) o;

        if (id != that.id) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (freshdate != null ? !freshdate.equals(that.freshdate) : that.freshdate != null) return false;
        if (standard != null ? !standard.equals(that.standard) : that.standard != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (freshdate != null ? freshdate.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        return result;
    }
}
