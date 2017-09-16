package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Created by Admin on 2016/8/29.
 */
public class ScheduleBaseEN {
    private int id;
    private String name;
    private Date scheduletime;
    private String location;
    private String content;
    private String type;
    private JcxxYhEN user;
    private String remind;
    private String remindtype;

    public String getRemindtype() {
        return remindtype;
    }

    public void setRemindtype(String remindtype) {
        this.remindtype = remindtype;
    }

    public String getRemind() {

        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    @JsonIgnore
    public JcxxYhEN getUser() {
        return user;
    }

    public void setUser(JcxxYhEN user) {
        this.user = user;
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

    public Date getScheduletime() {
        return scheduletime;
    }

    public void setScheduletime(Date scheduletime) {
        this.scheduletime = scheduletime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleBaseEN that = (ScheduleBaseEN) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (scheduletime != null ? !scheduletime.equals(that.scheduletime) : that.scheduletime != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (scheduletime != null ? scheduletime.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
