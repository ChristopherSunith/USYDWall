package com.samsonjabin.uwall;

import android.app.Application;

import com.parse.Parse;


public class Initialize extends Application {
    public void onCreate() {
        Parse.initialize(this, "3N9cToLyOFybHH7ZLEBVMAZsRYTVFLdAf7I4AWU0", "Jq19zhcI0eTViqKHEXa8G1XbnjyWuNIw58bw25ZE");
    }
}
