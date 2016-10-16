package com.samsonjabin.uwall.get;

import android.support.v4.app.Fragment;


public class Post extends Fragment {

    private String content;
    private String username;
    private String timestamp;

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
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
