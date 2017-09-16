package net.modol;

/**
 * Created by Admin on 2016/11/2.
 */
public class ShipReporttypeEN {
    private int id;
    private String reporttype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipReporttypeEN that = (ShipReporttypeEN) o;

        if (id != that.id) return false;
        if (reporttype != null ? !reporttype.equals(that.reporttype) : that.reporttype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (reporttype != null ? reporttype.hashCode() : 0);
        return result;
    }
}
