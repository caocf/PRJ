package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Admin on 2016/7/16.
 */
public class LawEvidencesEN {
    private int id;
    private String dir;
    String signedpdf;

    public String getSignedpdf() {
        return signedpdf;
    }

    public void setSignedpdf(String signedpdf) {
        this.signedpdf = signedpdf;
    }

    //@JsonIgnore
    private LawBaseEN lawBaseEN=new LawBaseEN();

    public LawBaseEN getLawBaseEN() {
        return lawBaseEN;
    }

    public void setLawBaseEN(LawBaseEN lawBaseEN) {
        this.lawBaseEN = lawBaseEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        LawEvidencesEN that = (LawEvidencesEN) o;

        if (id != that.id) return false;
        if (dir != null ? !dir.equals(that.dir) : that.dir != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        return result;
    }
}
