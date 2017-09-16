package net.ghpublic.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.xml.internal.ws.policy.jaxws.SafePolicyReader;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 2016/6/24.
 */
public class CompanyBaseEN {
    private int id;
    private String name;
    String regnumber;
    Date binddate;

    PublicuserStatusEN statusEN;
    Set<CompanyCertEN> companyCertEN=new HashSet<>();

    public Set<CompanyCertEN> getCompanyCertEN() {
        return companyCertEN;
    }

    public void setCompanyCertEN(Set<CompanyCertEN> companyCertEN) {
        this.companyCertEN = companyCertEN;
    }

    String business;
    String contactname;
    String tel;

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getBinddate() {
        return binddate;
    }

    public void setBinddate(Date binddate) {
        this.binddate = binddate;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    public PublicuserStatusEN getStatusEN() {
        return statusEN;
    }

    public void setStatusEN(PublicuserStatusEN statusEN) {
        this.statusEN = statusEN;
    }

    private PublicUserEN userEN=new PublicUserEN();

    public PublicUserEN getUserEN() {
        return userEN;
    }

    public void setUserEN(PublicUserEN userEN) {
        this.userEN = userEN;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyBaseEN that = (CompanyBaseEN) o;


        if (name != null ? !name.equals(that.name) : that.name != null) return false;


        return true;
    }

}
