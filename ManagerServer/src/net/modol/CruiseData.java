package net.modol;

/**
 * Created by Admin on 2016/8/11.
 */
public class CruiseData
{
    Double toatlmiles;
    int evidnum;
    int times;

    public CruiseData()
    {

    }
    public CruiseData(int toatlmiles,int evidnum,int times)
    {
        this.evidnum=evidnum;
        this.times=times;
    }

    public Double getToatlmiles() {
        return toatlmiles;
    }

    public void setToatlmiles(Double toatlmiles) {
        this.toatlmiles = toatlmiles;
    }

    public int getEvidnum() {
        return evidnum;
    }

    public void setEvidnum(int evidnum) {
        this.evidnum = evidnum;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
