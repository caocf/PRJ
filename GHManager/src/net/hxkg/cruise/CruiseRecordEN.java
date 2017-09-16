package net.hxkg.cruise;

import java.io.Serializable;



/**
 * Created by Admin on 2016/7/23.
 */
public class CruiseRecordEN implements Serializable
{
    private int id;
    private String member;
    private String area;
    private String period;
    private String miles;
    private String dir;
    private String toolsString;

    public String gettoolsString() {
        return toolsString;
    }

    public void settoolsString(String toolsString) {
        this.toolsString = toolsString;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CruiseRecordEN that = (CruiseRecordEN) o;

        if (id != that.id) return false;
        if (member != null ? !member.equals(that.member) : that.member != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (miles != null ? !miles.equals(that.miles) : that.miles != null) return false;
        if (dir != null ? !dir.equals(that.dir) : that.dir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (miles != null ? miles.hashCode() : 0);
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
}
