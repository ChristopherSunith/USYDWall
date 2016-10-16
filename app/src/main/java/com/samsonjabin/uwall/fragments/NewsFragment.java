package com.samsonjabin.uwall.fragments;


import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.samsonjabin.uwall.adapters.NewsListAdapter;
import com.samsonjabin.uwall.get.News;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.samsonjabin.uwall.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ProgressBar mProgressBar;
    NewsListAdapter adapter;
    SwipeRefreshLayout news_swipe;
    private List<News> newslist = null;
    // Set the limit of objects to show
    private int limit = 20;
    public String pcu;
    public String admin = "allaccess";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.news_layout, container, false);
        listview = (ListView) v.findViewById(R.id.news_list);
        news_swipe = (SwipeRefreshLayout) v.findViewById(R.id.news_swipe);
        mProgressBar = (ProgressBar) v.findViewById(R.id.load);
        pcu = ParseUser.getCurrentUser().get("access").toString();
        news_swipe.setOnRefreshListener(this);
        news_swipe.setColorSchemeColors(R.color.myPrimaryColor,R.color.myPrimaryDarkColor,R.color.myAccentColor);
        new RemoteDataTask().execute();
        return v;

    }

    @Override
    public void onRefresh() {
        new RemoteDataTask().execute();
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            //mProgressDialog.setTitle("USYDWall");
            // Set progressdialog message
            //mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //mProgressDialog.show();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            newslist = new ArrayList<News>();
            try {
                // Locate the class table name in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "News");
                query.orderByDescending("createdAt");
                // Set the limit of objects to show
                query.setLimit(limit);
                ob = query.find();
                for (ParseObject num : ob) {
                    News map = new News();
                    map.setContent((String) num.get("news"));
                    map.setUsername((String) num.get("username"));
                    map.setViews((Integer) num.get("views"));
                    map.setObjectid((String) num.getObjectId());
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                    //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                    map.setTimestamp(formatter.format(num.getCreatedAt()));
                    map.setDPImage((ParseFile) num.get("profilePic"));
                    map.setImage((ParseFile) num.get("ImageFile"));
                    newslist.add(map);
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
            adapter = new NewsListAdapter(getActivity(), newslist);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            //mProgressDialog.dismiss();
            mProgressBar.setVisibility(View.INVISIBLE);
            news_swipe.setRefreshing(false);
            // Create an OnScrollListener
            listview.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view,
                                                 int scrollState) { // TODO Auto-generated method stub
                    int threshold = 1;
                    int count = listview.getCount();

                        if (listview.getLastVisiblePosition() >= count
                                - threshold) {
                            // Execute LoadMoreDataTask AsyncTask
                            new LoadMoreDataTask().execute();

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
                //mProgressDialog = new ProgressDialog(getActivity());
                // Set progressdialog title
                //mProgressDialog.setTitle("USYDWall");
                // Set progressdialog message
                //mProgressDialog.setMessage("Loading more...");
                //mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                //mProgressDialog.show();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                // Create the array
                newslist = new ArrayList<News>();
                try {
                    // Locate the class table name in Parse.com
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                            "News");
                    query.orderByDescending("createdAt");
                    // Set the limit of objects to show
                    query.setLimit(limit);
                    ob = query.find();
                    for (ParseObject num : ob) {
                        News map = new News();
                        map.setContent((String) num.get("news"));
                        map.setUsername((String) num.get("username"));
                        map.setViews((Integer) num.get("views"));
                        map.setObjectid((String) num.getObjectId());
                        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                        //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                        map.setTimestamp(formatter.format(num.getCreatedAt()));
                        map.setDPImage((ParseFile) num.get("profilePic"));
                        map.setImage((ParseFile) num.get("ImageFile"));
                        newslist.add(map);
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
                adapter = new NewsListAdapter(getActivity(), newslist);
                // Binds the Adapter to the ListView
                listview.setAdapter(adapter);
                // Show the latest retrived results on the top
                listview.setSelectionFromTop(position, position);
                // Close the progressdialog
                news_swipe.setRefreshing(false);
                mProgressBar.setVisibility(View.INVISIBLE);
                //mProgressDialog.dismiss();

            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (pcu.equals(admin)){
            inflater.inflate(R.menu.menu_main, menu);}
        else {inflater.inflate(R.menu.menu_user, menu); }
        super.onCreateOptionsMenu(menu, inflater);
    }
}


