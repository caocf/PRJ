package net.modol;

/**
 * Created by Admin on 2016/11/23.
 */
public class LawSignedpdfEN {
    private int id;
    private String dir;
    String unsigneddir;

    public String getUnsigneddir() {
        return unsigneddir;
    }

    public void setUnsigneddir(String unsigneddir) {
        this.unsigneddir = unsigneddir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LawSignedpdfEN that = (LawSignedpdfEN) o;

        if (id != that.id) return false;
        if (dir != null ? !dir.equals(that.dir) : that.dir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
}
