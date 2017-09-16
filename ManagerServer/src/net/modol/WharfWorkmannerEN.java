package net.modol;

/**
 * Created by Admin on 2016/11/8.
 */
public class WharfWorkmannerEN {
    private int id;
    private String workmanner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkmanner() {
        return workmanner;
    }

    public void setWorkmanner(String workmanner) {
        this.workmanner = workmanner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WharfWorkmannerEN that = (WharfWorkmannerEN) o;

        if (id != that.id) return false;
        if (workmanner != null ? !workmanner.equals(that.workmanner) : that.workmanner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (workmanner != null ? workmanner.hashCode() : 0);
        return result;
    }
}
