package net.modol;

/**
 * Created by Admin on 2016/9/23.
 */
public class Duty
{
    String orgname;
    public int leave=0;
    public int jb=0;
    public int cc=0;

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public int getLeave() {
        return leave;
    }

    public void setLeave(int leave) {
        this.leave = leave;
    }

    public int getJb() {
        return jb;
    }

    public void setJb(int jb) {
        this.jb = jb;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }
}
