package framework.modol;

import java.util.List;

/**
 * Created by Admin on 2016/11/14.
 */
public class DataList
{
    int pages;
    List<?> list;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public DataList()
    {

    }
    public DataList(List<?> list)
    {
        this.list=list;
    }
    public DataList(int pages,List<?> list)
    {
        this.pages=pages;
        this.list=list;
    }

}
