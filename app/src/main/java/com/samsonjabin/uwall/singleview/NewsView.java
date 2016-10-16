package com.samsonjabin.uwall.singleview;



import com.samsonjabin.uwall.MainActivity;
import com.samsonjabin.uwall.comment.NewsComment;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.samsonjabin.uwall.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_LONG;

public class NewsView extends ActionBarActivity {
    // Declare Variables
    public String admin = "allaccess",club="club";
    TextView content_tv;
    TextView username_tv;
    TextView timestamp_tv;
    TextView views_tv;
    ParseImageView dp_iv,con_iv;
    CircleImageView dp_cir;
    EditText com;
    Button com_btn;
    String content;
    String views_s;
    public String username,pcu;
    String timestamp;
    String obje;
    String comment_st,commenter;
    Integer views;
    ListView comment_list;
    List<ParseObject> pob;
    private int limit = 20;



    private Toolbar mToolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pcu = ParseUser.getCurrentUser().get("access").toString();

        setContentView(R.layout.newsview_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        //getSupportActionBar().setTitle("");
        mToolbar.getBackground().setAlpha(0);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back_white);
        mToolbar.inflateMenu(R.menu.menu_news);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewsView.this, MainActivity.class));

            }
        });


        // Retrieve data from ListViewAdapter on click event
        Intent i = getIntent();
        // Get the results
        content = i.getStringExtra("content");
        username = i.getStringExtra("username");
        timestamp = i.getStringExtra("timestamp");
        views = i.getIntExtra("views", 0);
        views_s = String.valueOf(views);
        obje = i.getStringExtra("obj");
        // Locate the TextView in singleitemview.xml
        content_tv = (TextView) findViewById(R.id.content_single_news);
        username_tv = (TextView) findViewById(R.id.username_single_news);
        timestamp_tv = (TextView) findViewById(R.id.timestamp_single_news);
        views_tv = (TextView) findViewById(R.id.views_single_news);
        //dp_iv = (ParseImageView) findViewById(R.id.newsview_dp);
        dp_cir = (CircleImageView) findViewById(R.id.newsview_dp);
        con_iv = (ParseImageView) findViewById(R.id.content_image_news_view);
        comment_list = (ListView) findViewById(R.id.news_comment_list);
        com = (EditText) findViewById(R.id.comment);
        com_btn = (Button) findViewById(R.id.com_btn);
        com_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_st = com.getText().toString();
                commenter = ParseUser.getCurrentUser().getUsername().toString();
                ParseUser pcu = ParseUser.getCurrentUser();
                ParseFile dp = pcu.getParseFile("profilePic");
                dp.saveInBackground();
                ParseObject com_test = new ParseObject("NewsComments");
                com_test.put("comment", comment_st);
                com_test.put("dp_name", "commenter_dp.jpg");
                com_test.put("ImageFile", dp);
                com_test.put("commeter", pcu.getUsername().toString());
                com_test.put("postId", obje);
                com_test.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(NewsView.this, "Done", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(NewsView.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });


        ParseQuery<ParseObject> dp_query = ParseQuery.getQuery("News");

        dp_query.getInBackground(obje, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    ParseFile dp_file = (ParseFile) parseObject.get("Dp_file");
                    dp_file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            Bitmap bmp = BitmapFactory
                                    .decodeByteArray(data, 0, data.length);

                            dp_cir.setImageBitmap(bmp);
                        }
                    });
                } else {
                    Toast.makeText(NewsView.this, "Dp not available", LENGTH_LONG).show();
                }
            }
        });

        ParseQuery<ParseObject> content_query = ParseQuery.getQuery("News");

        content_query.getInBackground(obje, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                ParseFile dp_file = (ParseFile) parseObject.get("ImageFile");
                dp_file.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        Bitmap bmp = BitmapFactory
                                .decodeByteArray(data, 0, data.length);

                        con_iv.setImageBitmap(bmp);
                    }
                });
            }
        });


        ParseQuery<ParseObject> query = ParseQuery.getQuery("News");

// Retrieve the object by id
        query.getInBackground(obje, new GetCallback<ParseObject>() {
            public void done(ParseObject views, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    views.increment("views", 1);
                    views.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(NewsView.this, "Save success", LENGTH_LONG).show();
                            } else {
                                Toast.makeText(NewsView.this, "Save error", LENGTH_LONG).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(NewsView.this, "Query error", LENGTH_LONG).show();
                }
            }
        });


        // Set the results into TextView
        content_tv.setText(content);
        username_tv.setText(username);
        timestamp_tv.setText(timestamp);
        views_tv.setText(views_s);

    }

    public void NewsComment (MenuItem item){
        Intent myIntent = new Intent(NewsView.this, NewsComment.class);
        myIntent.putExtra("obj", obje); //Optional parameters
        NewsView.this.startActivity(myIntent);
    }

    public void NewsDelete (MenuItem item){

        ParseObject.createWithoutData("News", obje).deleteEventually(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Toast.makeText(NewsView.this,e.getMessage().toString(),LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(NewsView.this,"Deleted",LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (pcu.equals(admin)){
            getMenuInflater().inflate(R.menu.menu_news_admin, menu);}
        else {//getMenuInflater().inflate(R.menu.menu_news, menu);
         }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(NewsView.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));

        }
        return super.onKeyDown(keyCode, event);
    }

    }