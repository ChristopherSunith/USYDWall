package com.samsonjabin.uwall.get;

import com.parse.ParseFile;


public class News {
    private String content;
    private String username;
    private String timestamp;
    private String objectid;
    private Integer views;
    private ParseFile image;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTimestamp() {

        return timestamp;
    }
    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }
    public Integer getViews() {
        return views;
    }
    public void setViews(Integer views) {
        this.views = views;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public ParseFile getImage() {
        return image;
    }
    public ParseFile getDPImage() {
        return image;
    }
    public void setDPImage(ParseFile image) {
        this.image = image;
    }
    public void setImage(ParseFile image) {
        this.image = image;
    }

}