package net.ghpublic.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/4/7.
 */
public class CommonAdviceEN
{
    private int id;
    private String cotent;
    private String contact;
    private String city;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {

        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    Date time;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCotent() {
        return cotent;
    }

    public void setCotent(String cotent) {
        this.cotent = cotent;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
