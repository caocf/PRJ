package net.ghpublic.modol;

/**
 * Created by Admin on 2016/10/26.
 */
public class CommonGoodsunitEN {
    private int id;
    private String unitname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonGoodsunitEN that = (CommonGoodsunitEN) o;

        if (id != that.id) return false;
        if (unitname != null ? !unitname.equals(that.unitname) : that.unitname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (unitname != null ? unitname.hashCode() : 0);
        return result;
    }
}
