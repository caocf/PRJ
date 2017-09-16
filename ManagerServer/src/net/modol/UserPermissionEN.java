package net.modol;

import java.util.Set;

/**
 * Created by Admin on 2016/8/30.
 */
public class UserPermissionEN {
    private int id;
    private String permissionname;
    private String name;
    //UserGroupEN group;
int group;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


   // private Set<UserRoleEN> userRoleENs;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPermissionEN that = (UserPermissionEN) o;

        if (id != that.id) return false;
        if (permissionname != null ? !permissionname.equals(that.permissionname) : that.permissionname != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (permissionname != null ? permissionname.hashCode() : 0);
        return result;
    }
}
