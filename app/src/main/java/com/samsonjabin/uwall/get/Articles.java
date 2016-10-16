package com.samsonjabin.uwall.get;

import com.parse.ParseFile;

public class Articles {

    private String uname,article,title,timestamp,obj;
    private ParseFile avatar;
    private Integer views;

    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getArticle() {
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ParseFile getAvatar() {
        return avatar;
    }
    public void setAvatar(ParseFile avatar) {
        this.avatar = avatar;
    }

    public Integer getViews() {
        return views;
    }
    public void setViews(Integer views) {
        this.views = views;
    }

    public void setObjectid(String obj) {this.obj=obj;
    }

    public String getObjectId(){return obj;}
}
