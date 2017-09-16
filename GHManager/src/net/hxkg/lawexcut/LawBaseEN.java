package net.hxkg.lawexcut;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LawBaseEN  implements Serializable
{
    public static String CHECKING="待审核";
    public static String CHECKED="已审核";
	private static final long serialVersionUID = 1L;
	private int id;
    private String firstman;
    private String secman;
    private String target;
    private String reason;
    private String detail;
    private String location;
    private String checker;
    private String status;
    private Date sumbdate;
    private String isllegal;
    private String showdate;
    
    public Double lat,lon;
    public int typeid; 
    public String typename;
    public int localstatus;
    public String commitdate;
    
    public List<File> filelist=new ArrayList<>();
   
    public String getShowdate() {
        return showdate;
    }

    public void setShowdate(String showdate) {
        this.showdate = showdate;
    }
    
    public String getIsllegal() {
        return isllegal;
    }

    public void setIsllegal(String isllegal) {
        this.isllegal = isllegal;
    }

    public Date getSumbdate() {
        return sumbdate;
    }

    public void setSumbdate(Date sumbdate) {
        this.sumbdate = sumbdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstman() {
        return firstman;
    }

    public void setFirstman(String firstman) {
        this.firstman = firstman;
    }

    public String getSecman() {
        return secman;
    }

    public void setSecman(String secman) {
        this.secman = secman;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LawBaseEN lawBaseEN = (LawBaseEN) o;

        if (id != lawBaseEN.id) return false;
        if (firstman != null ? !firstman.equals(lawBaseEN.firstman) : lawBaseEN.firstman != null) return false;
        if (secman != null ? !secman.equals(lawBaseEN.secman) : lawBaseEN.secman != null) return false;
        if (target != null ? !target.equals(lawBaseEN.target) : lawBaseEN.target != null) return false;
        if (reason != null ? !reason.equals(lawBaseEN.reason) : lawBaseEN.reason != null) return false;
        if (detail != null ? !detail.equals(lawBaseEN.detail) : lawBaseEN.detail != null) return false;
        if (location != null ? !location.equals(lawBaseEN.location) : lawBaseEN.location != null) return false;
        if (checker != null ? !checker.equals(lawBaseEN.checker) : lawBaseEN.checker != null) return false;
        if (status != null ? !status.equals(lawBaseEN.status) : lawBaseEN.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstman != null ? firstman.hashCode() : 0);
        result = 31 * result + (secman != null ? secman.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (checker != null ? checker.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
