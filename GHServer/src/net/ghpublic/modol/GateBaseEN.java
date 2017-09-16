package net.ghpublic.modol;

/**
 * Created by Admin on 2016/7/18.
 */
public class GateBaseEN {
    private int id;
    private String order;
    private String level1;
    private String level2;
    private String shipnum;
    private String shipname;

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getShipnum() {
        return shipnum;
    }

    public void setShipnum(String shipnum) {
        this.shipnum = shipnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GateBaseEN that = (GateBaseEN) o;

        if (id != that.id) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (level1 != null ? !level1.equals(that.level1) : that.level1 != null) return false;
        if (level2 != null ? !level2.equals(that.level2) : that.level2 != null) return false;
        if (shipnum != null ? !shipnum.equals(that.shipnum) : that.shipnum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (level1 != null ? level1.hashCode() : 0);
        result = 31 * result + (level2 != null ? level2.hashCode() : 0);
        result = 31 * result + (shipnum != null ? shipnum.hashCode() : 0);
        return result;
    }
}
