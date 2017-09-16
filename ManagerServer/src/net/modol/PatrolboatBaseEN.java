package net.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/8/10.
 */
public class PatrolboatBaseEN {
    private int id;
    private String name;
    private String dep;
    private String level;
    private String pid;
    private String haschildren;
    private String member;
    private String shiplength;
    private String buildtime;
    private String childrenum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
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

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getShiplength() {
        return shiplength;
    }

    public void setShiplength(String shiplength) {
        this.shiplength = shiplength;
    }

    public String getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(String buildtime) {
        this.buildtime = buildtime;
    }

    public String getChildrenum() {
        return childrenum;
    }

    public void setChildrenum(String childrenum) {
        this.childrenum = childrenum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatrolboatBaseEN that = (PatrolboatBaseEN) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dep != null ? !dep.equals(that.dep) : that.dep != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (haschildren != null ? !haschildren.equals(that.haschildren) : that.haschildren != null) return false;
        if (member != null ? !member.equals(that.member) : that.member != null) return false;
        if (shiplength != null ? !shiplength.equals(that.shiplength) : that.shiplength != null) return false;
        if (buildtime != null ? !buildtime.equals(that.buildtime) : that.buildtime != null) return false;
        if (childrenum != null ? !childrenum.equals(that.childrenum) : that.childrenum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dep != null ? dep.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (haschildren != null ? haschildren.hashCode() : 0);
        result = 31 * result + (member != null ? member.hashCode() : 0);
        result = 31 * result + (shiplength != null ? shiplength.hashCode() : 0);
        result = 31 * result + (buildtime != null ? buildtime.hashCode() : 0);
        result = 31 * result + (childrenum != null ? childrenum.hashCode() : 0);
        return result;
    }
}
