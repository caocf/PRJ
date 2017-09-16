package net.ghpublic.modol;

/**
 * Created by Admin on 2016/12/28.
 */
public class CommonUnitEN {
    private int id;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonUnitEN that = (CommonUnitEN) o;

        if (id != that.id) return false;
        if (unit != null ? !unit.equals(that.unit) : that.unit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }
}
