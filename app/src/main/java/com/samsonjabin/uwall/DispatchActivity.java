package com.samsonjabin.uwall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class DispatchActivity extends Activity {

    String truee = "true";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProgressBar spinner = (ProgressBar) findViewById(R.id.spinner);
        setContentView(R.layout.splash);
//        spinner.setVisibility(View.GONE);


        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {


            // Start an intent for the logged in activity
            final String userid = ParseUser.getCurrentUser().getObjectId();
            //spinner.setVisibility(View.VISIBLE);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
            query.getInBackground(userid, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {

                        String test = object.get("emailVerified").toString();
                        if (test == truee) {
                            startActivity(new Intent(DispatchActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
                        } else {
                            setContentView(R.layout.verify_mail);
                            //String test = object.get("emailVerified").toString();

                        }
                    } else {

                        Toast.makeText(DispatchActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        else
        {
            startActivity(new Intent(DispatchActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    }


