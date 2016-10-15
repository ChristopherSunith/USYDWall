package com.samsonjabin.uwall.comment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.get.Comment;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by AravindRaj on 05-04-2015.
 */
public class NewsComment extends ActionBarActivity {


    ListView listview;
    private Toolbar mToolbar;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    NewsCommentAdapter adapter;
    private List<Comment> comment_list = null;
    // Set the limit of objects to show
    private int limit = 20;
    public String com_obj;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list_comment);
        listview = (ListView) findViewById(R.id.news_comment_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Comments - News");
        mToolbar.setNavigationIcon(R.drawable.ic_action_back_white);
        /**mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewsComment.this, "Navigation", Toast.LENGTH_SHORT).show();
               // finishActivity();
            }
        });*/
        Intent intent = getIntent();
        com_obj = intent.getStringExtra("obj");
        new RemoteDataTask().execute();


    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(NewsComment.this);
            // Set progressdialog title
            mProgressDialog.setTitle("vitly");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading comment...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            comment_list = new ArrayList<Comment>();
            try {
                // Locate the class table name in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "NewsComments");
                query.whereContains("postId", com_obj);
                query.orderByDescending("createdAt");
                // Set the limit of objects to show
                query.setLimit(limit);
                ob = query.find();
                for (ParseObject num : ob) {
                    Comment map = new Comment();
                    map.setComment((String) num.get("comment"));
                    map.setCommeter((String) num.get("commeter"));
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                    //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                    map.setTimestamp(formatter.format(num.getCreatedAt()));
                    map.setImage((ParseFile) num.get("ImageFile"));
                    comment_list.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the ListView in listview.xml
            // Pass the results into ListViewAdapter.java

            adapter = new NewsCommentAdapter(NewsComment.this,comment_list);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            // Create an OnScrollListener
            listview.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view,
                                                 int scrollState) { // TODO Auto-generated method stub
                    int threshold = 1;
                    int count = listview.getCount();

                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (listview.getLastVisiblePosition() >= count
                                - threshold) {
                            // Execute LoadMoreDataTask AsyncTask
                            new LoadMoreDataTask().execute();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub

                }

            });
        }

        private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressdialog
                mProgressDialog = new ProgressDialog(NewsComment.this);
                // Set progressdialog title
                mProgressDialog.setTitle("vitly");
                // Set progressdialog message
                mProgressDialog.setMessage("Loading more...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Create the array
                comment_list = new ArrayList<Comment>();
                try {
                    // Locate the class table name in Parse.com
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                            "NewsComments");
                    query.whereContains("postId", com_obj);
                    query.orderByDescending("createdAt");
                    // Set the limit of objects to show
                    query.setLimit(limit);
                    ob = query.find();
                    for (ParseObject num : ob) {
                        Comment map = new Comment();
                        map.setComment((String) num.get("comment"));
                        map.setCommeter((String) num.get("commeter"));
                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                        //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                        map.setTimestamp(formatter.format(num.getCreatedAt()));
                        map.setImage((ParseFile) num.get("ImageFile"));
                        comment_list.add(map);
                    }
                } catch (ParseException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Locate listview last item
                int position = listview.getLastVisiblePosition();
                // Pass the results into ListViewAdapter.java
                adapter = new NewsCommentAdapter(NewsComment.this, comment_list);
                // Binds the Adapter to the ListView
                listview.setAdapter(adapter);
                // Show the latest retrived results on the top
                listview.setSelectionFromTop(position, position);
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }

    }
}


