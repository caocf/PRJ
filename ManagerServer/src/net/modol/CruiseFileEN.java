package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Admin on 2016/11/11.
 */
public class CruiseFileEN {
    private int id;
    private String dir;
    @JsonIgnore
    CruiseLogEN cruiseLogEN;

    public CruiseLogEN getCruiseLogEN() {
        return cruiseLogEN;
    }

    public void setCruiseLogEN(CruiseLogEN cruiseLogEN) {
        this.cruiseLogEN = cruiseLogEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        CruiseFileEN that = (CruiseFileEN) o;

        if (id != that.id) return false;
        if (dir != null ? !dir.equals(that.dir) : that.dir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
}
