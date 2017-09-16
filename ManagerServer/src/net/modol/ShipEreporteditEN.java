package net.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/12/20.
 */
public class ShipEreporteditEN
{
    private int id;
    Date uptime;
    Date porttime;
    CommonPortEN portEN;

    public CommonPortEN getPortEN() {
        return portEN;
    }

    public void setPortEN(CommonPortEN portEN) {
        this.portEN = portEN;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public Date getPorttime() {

        return porttime;
    }

    public void setPorttime(Date porttime) {
        this.porttime = porttime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipEreporteditEN that = (ShipEreporteditEN) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
