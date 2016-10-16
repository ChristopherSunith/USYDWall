package com.samsonjabin.uwall.get;

import com.parse.ParseFile;


public class Comment {

    private String comment;
    private String commeter;
    private String timestamp;
    private ParseFile image;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommeter() {
        return commeter;
    }

    public void setCommeter(String commeter) {
        this.commeter = commeter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ParseFile getImage() {
        return image;
    }

    public void setImage(ParseFile image) {
        this.image = image;
    }
}
