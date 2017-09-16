package net.ghpublic.modol;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 2016/5/6.
 */
public class NewsEN
{
    private int id;
    private String title;
    private String content;
    private Date updatetime;
    private CommonRegionEN region=new CommonRegionEN();
    private NewsTypeEN newstype=new NewsTypeEN();
    private String filedir;
    private String imdir;

    public String getImdir() {
        return imdir;
    }

    public void setImdir(String imdir) {
        this.imdir = imdir;
    }

    public String getFiledir() {
        return filedir;
    }

    public void setFiledir(String filedir) {
        this.filedir = filedir;
    }

    private Set<NewsCommentEN> comments=new HashSet<NewsCommentEN>();

    public Set<NewsCommentEN> getComments() {
        return comments;
    }

    public void setComments(Set<NewsCommentEN> comments) {
        this.comments = comments;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }



    public CommonRegionEN getRegion() {
        return region;
    }

    public void setRegion(CommonRegionEN region) {
        this.region = region;
    }

    public NewsTypeEN getNewstype() {
        return newstype;
    }

    public void setNewstype(NewsTypeEN newstype) {
        this.newstype = newstype;
    }

}
