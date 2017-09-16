package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 2016/11/11.
 */
public class CruiseLogEN {
    private int id;
    private String title;
    private String content;
    Date recordtime;
    String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonIgnore
    CruiseRecordEN cruiseRecordEN;

    Set<CruiseFileEN> cruiseFileENs=new HashSet<>();

    public Set<CruiseFileEN> getCruiseFileENs() {
        return cruiseFileENs;
    }

    public void setCruiseFileENs(Set<CruiseFileEN> cruiseFileENs) {
        this.cruiseFileENs = cruiseFileENs;
    }

    public CruiseRecordEN getCruiseRecordEN() {
        return cruiseRecordEN;
    }

    public void setCruiseRecordEN(CruiseRecordEN cruiseRecordEN) {
        this.cruiseRecordEN = cruiseRecordEN;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CruiseLogEN that = (CruiseLogEN) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
