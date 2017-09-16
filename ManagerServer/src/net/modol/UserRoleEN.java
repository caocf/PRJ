package net.modol;

import java.util.Set;

/**
 * Created by Admin on 2016/8/30.
 */
public class UserRoleEN {
    private int id;
    private String role;





    private Set<UserPermissionEN> permissionENs;
    public void setPermissionENs(Set<UserPermissionEN> permissionENs) {
        this.permissionENs = permissionENs;
    }
    public Set<UserPermissionEN> getPermissionENs() {
        return permissionENs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoleEN that = (UserRoleEN) o;

        if (id != that.id) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
