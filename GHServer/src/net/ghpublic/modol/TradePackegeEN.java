package net.ghpublic.modol;

/**
 * Created by Admin on 2016/11/8.
 */
public class TradePackegeEN {
    private int id;
    private String packege;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackege() {
        return packege;
    }

    public void setPackege(String packege) {
        this.packege = packege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradePackegeEN that = (TradePackegeEN) o;

        if (id != that.id) return false;
        if (packege != null ? !packege.equals(that.packege) : that.packege != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (packege != null ? packege.hashCode() : 0);
        return result;
    }
}
