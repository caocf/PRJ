package net.ghpublic.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangzedong on 2016/4/22.
 */
public class PublicUserEN
{
    private int userid;
    private String username;
    private String password;
    private String tel;
    private PublicUserTypeEN usertype=new PublicUserTypeEN();//many to one
    private PublicuserRegionEN region;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnore
    private Set<ShipEN> ships=new HashSet<>();

    public Set<ShipEN> getShips() {
        return ships;
    }

    public void setShips(Set<ShipEN> ships) {
        this.ships = ships;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    //@JsonIgnore
    //@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    public PublicUserTypeEN getUsertype() {
        return usertype;
    }

    public void setUsertype(PublicUserTypeEN usertype) {
        this.usertype = usertype;
    }

    public PublicuserRegionEN getRegion() {
        return region;
    }

    public void setRegion(PublicuserRegionEN region) {
        this.region = region;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    private Date registertime;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
