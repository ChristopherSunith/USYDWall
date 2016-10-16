package com.samsonjabin.uwall.singleview;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.samsonjabin.uwall.R;

public class PostView extends ActionBarActivity {
    TextView content_tv;
    TextView username_tv;
    TextView timestamp_tv;
    String content;
    String username;
    String timestamp;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postview_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        getSupportActionBar().setTitle("Post");
        //mToolbar.getBackground().setAlpha(0);

        // Retrieve data from ListViewAdapter on click event
        Intent i = getIntent();
        // Get the results
        content = i.getStringExtra("content");
        username = i.getStringExtra("username");
        timestamp = i.getStringExtra("timestamp");

        // Locate the TextView in singleitemview.xml
        content_tv = (TextView) findViewById(R.id.content_single_post);
        username_tv = (TextView) findViewById(R.id.username_single_post);
        timestamp_tv = (TextView) findViewById(R.id.timestamp_single_post);
        // Set the results into TextView
        content_tv.setText(content);
        username_tv.setText(username);
        timestamp_tv.setText(timestamp);

    }
}
