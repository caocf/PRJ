package net.modol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.star.view.SelectionType;

import java.util.Set;

/**
 * Created by Admin on 2016/7/27.
 */
public class LawPenaltyEN {
    private int id;
    private String depend;
    private String item1;
    private String item2;
    private String item3;
    private String suggest;
    private String number;
    Set<LawSignedpdfEN> signedpdfENSet;

    public Set<LawSignedpdfEN> getSignedpdfENSet() {
        return signedpdfENSet;
    }

    public void setSignedpdfENSet(Set<LawSignedpdfEN> signedpdfENSet) {
        this.signedpdfENSet = signedpdfENSet;
    }

    // @JsonIgnore
    private LawBaseEN lawEvidencesEN;

    public LawBaseEN getLawEvidencesEN() {
        return lawEvidencesEN;
    }

    public void setLawEvidencesEN(LawBaseEN lawEvidencesEN) {
        this.lawEvidencesEN = lawEvidencesEN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepend() {
        return depend;
    }

    public void setDepend(String depend) {
        this.depend = depend;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LawPenaltyEN that = (LawPenaltyEN) o;

        if (id != that.id) return false;
        if (depend != null ? !depend.equals(that.depend) : that.depend != null) return false;
        if (item1 != null ? !item1.equals(that.item1) : that.item1 != null) return false;
        if (item2 != null ? !item2.equals(that.item2) : that.item2 != null) return false;
        if (item3 != null ? !item3.equals(that.item3) : that.item3 != null) return false;
        if (suggest != null ? !suggest.equals(that.suggest) : that.suggest != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (depend != null ? depend.hashCode() : 0);
        result = 31 * result + (item1 != null ? item1.hashCode() : 0);
        result = 31 * result + (item2 != null ? item2.hashCode() : 0);
        result = 31 * result + (item3 != null ? item3.hashCode() : 0);
        result = 31 * result + (suggest != null ? suggest.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
