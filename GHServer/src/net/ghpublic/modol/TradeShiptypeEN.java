package net.ghpublic.modol;

/**
 * Created by Admin on 2016/11/7.
 */
public class TradeShiptypeEN {
    private int id;
    private String shiptype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShiptype() {
        return shiptype;
    }

    public void setShiptype(String shiptype) {
        this.shiptype = shiptype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeShiptypeEN that = (TradeShiptypeEN) o;

        if (id != that.id) return false;
        if (shiptype != null ? !shiptype.equals(that.shiptype) : that.shiptype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shiptype != null ? shiptype.hashCode() : 0);
        return result;
    }
}
