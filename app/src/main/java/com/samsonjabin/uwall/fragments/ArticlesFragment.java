package com.samsonjabin.uwall.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.adapters.ArticlesListAdapter;
import com.samsonjabin.uwall.adapters.NewsListAdapter;
import com.samsonjabin.uwall.get.Articles;
import com.samsonjabin.uwall.get.News;
import com.samsonjabin.uwall.newupload.NewArticle;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ArticlesFragment extends Fragment {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ArticlesListAdapter adapter;
    private List<Articles> articleslist = null;
    // Set the limit of objects to show
    private int limit = 20;
    public String pcu;
    public String admin = "Samson Jabin D";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.article_layout, container, false);
        listview = (ListView) v.findViewById(R.id.articlelist);
        pcu = ParseUser.getCurrentUser().getUsername().toString();
        new RemoteDataTask().execute();
        return v;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("USYDWall");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            articleslist = new ArrayList<Articles>();
            try {
                // Locate the class table name in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Articles");
                query.orderByDescending("createdAt");
                // Set the limit of objects to show
                query.setLimit(limit);
                ob = query.find();
                for (ParseObject num : ob) {
                    Articles map = new Articles();
                    map.setTitle((String) num.get("article_title"));
                    map.setArticle((String) num.get("content"));
                    map.setUname((String) num.get("author"));
                    map.setViews((Integer) num.get("views"));
                    map.setObjectid((String) num.getObjectId());
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                    //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                    map.setTimestamp(formatter.format(num.getCreatedAt()));
                    map.setAvatar((ParseFile) num.get("profilePic"));
                    articleslist.add(map);
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
            adapter = new ArticlesListAdapter(getActivity(), articleslist);
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
                mProgressDialog = new ProgressDialog(getActivity());
                // Set progressdialog title
                mProgressDialog.setTitle("USYDWall");
                // Set progressdialog message
                mProgressDialog.setMessage("Loading more...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Create the array
                articleslist = new ArrayList<Articles>();
                try {
                    // Locate the class table name in Parse.com
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                            "Articles");
                    query.orderByDescending("createdAt");
                    // Set the limit of objects to show
                    query.setLimit(limit);
                    ob = query.find();
                    for (ParseObject num : ob) {
                        Articles map = new Articles();
                        map.setTitle((String) num.get("article_title"));
                        map.setArticle((String) num.get("content"));
                        map.setUname((String) num.get("author"));
                        map.setViews((Integer) num.get("views"));
                        map.setObjectid((String) num.getObjectId());
                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                        //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                        map.setTimestamp(formatter.format(num.getCreatedAt()));
                        map.setAvatar((ParseFile) num.get("profilePic"));
                        articleslist.add(map);
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
                adapter = new ArticlesListAdapter(getActivity(), articleslist);
                // Binds the Adapter to the ListView
                listview.setAdapter(adapter);
                // Show the latest retrived results on the top
                listview.setSelectionFromTop(position, position);
                // Close the progressdialog
                mProgressDialog.dismiss();
            }
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            inflater.inflate(R.menu.menu_articles, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


}
