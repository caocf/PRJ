package net.ghpublic.modol;

/**
 * Created by Admin on 2016/10/25.
 */
public class PublicuserRegionEN {
    private int id;
    private String regionname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicuserRegionEN that = (PublicuserRegionEN) o;

        if (id != that.id) return false;
        if (regionname != null ? !regionname.equals(that.regionname) : that.regionname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (regionname != null ? regionname.hashCode() : 0);
        return result;
    }
}
