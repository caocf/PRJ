package net.ghpublic.modol;

/**
 * Created by Admin on 2016/10/26.
 */
public class CommonPortEN {
    private int id;
    private String portname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonPortEN that = (CommonPortEN) o;

        if (id != that.id) return false;
        if (portname != null ? !portname.equals(that.portname) : that.portname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (portname != null ? portname.hashCode() : 0);
        return result;
    }
}
