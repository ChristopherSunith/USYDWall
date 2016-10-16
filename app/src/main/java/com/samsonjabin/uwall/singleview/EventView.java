package com.samsonjabin.uwall.singleview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.samsonjabin.uwall.MainActivity;
import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.fragments.EventsFragment;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import static android.widget.Toast.LENGTH_LONG;


public class EventView extends ActionBarActivity {

    public String admin = "Samson Jabin D";
    TextView clubname , eventname, desc, date, venue, views, time,cont;
    private String clubnames,evntname,descs,dates,venuest,time_st,obje,con;
    private Integer viewss;
    private Toolbar mToolbar;

    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String uname = ParseUser.getCurrentUser().getUsername().toString();

        if (uname.equals(admin)){
            getMenuInflater().inflate(R.menu.menu_main, menu);}
        else {getMenuInflater().inflate(R.menu.menu_main, menu);}
        return super.onCreateOptionsMenu(menu);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventsview_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setTitle("Events");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventView.this, "jbnbn",LENGTH_LONG);
            }
        });
        Intent i = getIntent();
        clubnames = i.getStringExtra("clubname");
        evntname = i.getStringExtra("eventname");
        descs = i.getStringExtra("content");
        dates = i.getStringExtra("date");
        venuest = i.getStringExtra("venue");
        viewss = i.getIntExtra("views",0);
        obje = i.getStringExtra("obj");
        time_st = i.getStringExtra("time");
        con = i.getStringExtra("con");

        clubname = (TextView) findViewById(R.id.event_view_hostname);
        eventname = (TextView) findViewById(R.id.event_view_eventname);
        desc = (TextView) findViewById(R.id.event_view_desc);
        date = (TextView) findViewById(R.id.event_view_date);
        venue = (TextView) findViewById(R.id.event_view_venue);
        views = (TextView) findViewById(R.id.event_view_views);
        time = (TextView) findViewById(R.id.event_view_time);
        cont = (TextView) findViewById(R.id.contactno_single_events);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");

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
                                Toast.makeText(EventView.this, "Save success", LENGTH_LONG).show();
                            } else {
                                Toast.makeText(EventView.this, "Save error", LENGTH_LONG).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(EventView.this, "Views error", LENGTH_LONG).show();
                }
            }
        });

        clubname.setText(clubnames);
        eventname.setText(evntname);
        desc.setText(evntname);
        date.setText(dates);
        venue.setText(venuest);
        views.setText(viewss.toString()+" views");
        time.setText(time_st);
        cont.setText(cont.getText().toString());
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+cont.getText().toString()));
                startActivity(phoneIntent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            startActivity(new Intent(EventView.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
