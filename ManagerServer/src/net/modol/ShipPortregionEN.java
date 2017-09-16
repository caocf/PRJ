package net.modol;

/**
 * Created by Admin on 2016/11/2.
 */
public class ShipPortregionEN {
    private int id;
    private String portregion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPortregion() {
        return portregion;
    }

    public void setPortregion(String portregion) {
        this.portregion = portregion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipPortregionEN that = (ShipPortregionEN) o;

        if (id != that.id) return false;
        if (portregion != null ? !portregion.equals(that.portregion) : that.portregion != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (portregion != null ? portregion.hashCode() : 0);
        return result;
    }
}
