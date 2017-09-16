package net.modol;

/**
 * Created by Admin on 2016/11/22.
 */
public class UserGroupEN {
    private int id;
    private String groupname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupEN that = (UserGroupEN) o;

        if (id != that.id) return false;
        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (groupname != null ? groupname.hashCode() : 0);
        return result;
    }
}
