package net.modol;

/**
 * Created by Admin on 2016/10/26.
 */
public class CommonDangerrankEN {
    private int id;
    private String rankname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRankname() {
        return rankname;
    }

    public void setRankname(String rankname) {
        this.rankname = rankname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonDangerrankEN that = (CommonDangerrankEN) o;

        if (id != that.id) return false;
        if (rankname != null ? !rankname.equals(that.rankname) : that.rankname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (rankname != null ? rankname.hashCode() : 0);
        return result;
    }
}
