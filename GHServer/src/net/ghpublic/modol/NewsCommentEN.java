package net.ghpublic.modol;

import java.util.Date;

/**
 * Created by Admin on 2016/5/6.
 */
public class NewsCommentEN {
    private int id;
    private String content;
    private Date sumbtime;
    private int newsid;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSumbtime() {
        return sumbtime;
    }

    public void setSumbtime(Date sumbtime) {
        this.sumbtime = sumbtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsCommentEN that = (NewsCommentEN) o;

        if (id != that.id) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (sumbtime != null ? !sumbtime.equals(that.sumbtime) : that.sumbtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sumbtime != null ? sumbtime.hashCode() : 0);
        return result;
    }
}
