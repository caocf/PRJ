package net.modol;

/**
 * Created by Admin on 2016/7/7.
 */
public class UserBaseEN
{
    private Integer id;
    private String name;
    private String psw;
    private String dep;
    private String tel;
    private String level;
    private String pid;
    private String haschildren;
    private String expand;
    private UserRoleEN userRoleEN;

    public UserRoleEN getUserRoleEN() {
        return userRoleEN;
    }

    public void setUserRoleEN(UserRoleEN userRoleEN) {
        this.userRoleEN = userRoleEN;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHaschildren() {
        return haschildren;
    }

    public void setHaschildren(String haschildren) {
        this.haschildren = haschildren;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBaseEN that = (UserBaseEN) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (psw != null ? !psw.equals(that.psw) : that.psw != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (psw != null ? psw.hashCode() : 0);
        return result;
    }
}
