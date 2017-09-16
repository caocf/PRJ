package net.ghpublic.modol;

/**
 * Created by Admin on 2016/5/3.
 */
//@JsonIgnoreProperties(value={"hibernateLazyInitializer"})

public class PublicUserTypeEN
{
    private int id;
    private String typename;
    private String bindclass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getBindclass() {
        return bindclass;
    }

    public void setBindclass(String bindclass) {
        this.bindclass = bindclass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicUserTypeEN that = (PublicUserTypeEN) o;

        if (id != that.id) return false;
        if (typename != null ? !typename.equals(that.typename) : that.typename != null) return false;
        if (bindclass != null ? !bindclass.equals(that.bindclass) : that.bindclass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (typename != null ? typename.hashCode() : 0);
        result = 31 * result + (bindclass != null ? bindclass.hashCode() : 0);
        return result;
    }
}
