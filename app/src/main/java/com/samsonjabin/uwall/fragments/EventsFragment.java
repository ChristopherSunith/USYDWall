package com.samsonjabin.uwall.fragments;

import android.app.ProgressDialog;
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
import com.samsonjabin.uwall.adapters.EventsListAdapter;
import com.samsonjabin.uwall.adapters.NewsListAdapter;
import com.samsonjabin.uwall.get.Events;
import com.samsonjabin.uwall.get.News;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AravindRaj on 26-03-2015.
 */
public class EventsFragment extends Fragment {

    public String pcu;
    public String admin = "Aravind Raj C";
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    EventsListAdapter adapter;
    private List<Events> eventslist = null;
    // Set the limit of objects to show
    private int limit = 20;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.events_layout, container, false);
        listview = (ListView) v.findViewById(R.id.events_list);
        pcu = ParseUser.getCurrentUser().get("name").toString();
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
            mProgressDialog.setTitle("vitly");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            eventslist = new ArrayList<Events>();
            try {
                // Locate the class table name in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Events");
                query.orderByDescending("createdAt");
                // Set the limit of objects to show
                query.setLimit(limit);
                ob = query.find();
                for (ParseObject num : ob) {
                    Events map = new Events();
                    map.setEventname((String) num.get("event_name"));
                    map.sethostname((String) num.get("clubname"));
                    map.setViews((Integer) num.get("views"));
                    map.setVenue((String) num.get("venue"));
                    map.setDesc((String) num.get("content"));
                    map.setDate((String) num.get("date"));
                    map.setTime((String) num.get("time"));
                    map.setEvent_banner((ParseFile) num.get("event_banner"));
                    map.setObjectid((String) num.getObjectId());
                    //SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                    //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                    //map.setTimestamp(formatter.format(num.getCreatedAt()));
                    //map.setImage((ParseFile) num.get("ImageFile"));
                    eventslist.add(map);
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
            adapter = new EventsListAdapter(getActivity(),eventslist);
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
                eventslist = new ArrayList<Events>();
                try {
                    // Locate the class table name in Parse.com
                    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                            "Events");
                    query.orderByDescending("createdAt");
                    // Set the limit of objects to show
                    query.setLimit(limit);
                    ob = query.find();
                    for (ParseObject num : ob) {
                        Events map = new Events();
                        map.setEventname((String) num.get("event_name"));
                        map.sethostname((String) num.get("clubname"));
                        map.setViews((Integer) num.get("views"));
                        map.setVenue((String) num.get("venue"));
                        map.setDesc((String) num.get("content"));
                        map.setDate((String) num.get("date"));
                        map.setObjectid((String) num.getObjectId());
                        map.setTime((String) num.get("time"));
                        map.setEvent_banner((ParseFile) num.get("event_banner"));
                        map.setContact((String) num.get("contactno"));
                        //SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a , yyyy-MMM-dd ");
                        //dateCreatedText.setText(formatter.format(currentUser.getCreatedAt()));
                        //map.setTimestamp(formatter.format(num.getCreatedAt()));
                        //map.setImage((ParseFile) num.get("ImageFile"));
                        eventslist.add(map);
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
                adapter = new EventsListAdapter(getActivity(), eventslist);
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
        if (pcu.equals(admin)){
        inflater.inflate(R.menu.menu_events, menu);}
        else {inflater.inflate(R.menu.menu_user, menu); }
        super.onCreateOptionsMenu(menu, inflater);
    }


}
