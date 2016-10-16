package com.samsonjabin.uwall.singleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.samsonjabin.uwall.R;

public class ArticlesView extends ActionBarActivity {
    TextView title,author,article,timestamp;
    String content,title_st,time,author_st;

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_view);
        title = (TextView) findViewById(R.id.articles_view_title);
        author = (TextView) findViewById(R.id.articles_view_author);
        article = (TextView) findViewById(R.id.article_view_content);
        timestamp= (TextView) findViewById(R.id.article_view_time);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        Intent i = getIntent();
        // Get the results
        content = i.getStringExtra("content");
        author_st = i.getStringExtra("uname");
        time = i.getStringExtra("timestamp");
        title_st =i.getStringExtra("title");

        title.setText(title_st);
        author.setText(author_st);
        timestamp.setText(time);
        article.setText(content);

    }
}
