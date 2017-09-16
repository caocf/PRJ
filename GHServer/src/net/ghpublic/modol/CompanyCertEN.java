package net.ghpublic.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Admin on 2016/6/24.
 */
public class CompanyCertEN {
    private Integer id;
    private String dir;
    private CompanyBaseEN companyBaseEN=new CompanyBaseEN();
    @JsonIgnore
    public CompanyBaseEN getCompanyBaseEN() {
        return companyBaseEN;
    }

    public void setCompanyBaseEN(CompanyBaseEN companyBaseEN) {
        this.companyBaseEN = companyBaseEN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

        CompanyCertEN that = (CompanyCertEN) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dir != null ? !dir.equals(that.dir) : that.dir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
}
