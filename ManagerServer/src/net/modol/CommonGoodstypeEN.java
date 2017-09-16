package net.modol;

/**
 * Created by Admin on 2016/11/2.
 */
public class CommonGoodstypeEN {
    private int id;
    private String typname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypname() {
        return typname;
    }

    public void setTypname(String typname) {
        this.typname = typname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonGoodstypeEN that = (CommonGoodstypeEN) o;

        if (id != that.id) return false;
        if (typname != null ? !typname.equals(that.typname) : that.typname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typname != null ? typname.hashCode() : 0);
        return result;
    }
}
