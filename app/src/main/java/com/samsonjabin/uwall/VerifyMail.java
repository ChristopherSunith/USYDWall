package com.samsonjabin.uwall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RefreshCallback;





public class VerifyMail extends Activity {

    private TextView text4;
    private ImageView veri;
    String truee = "true";
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        text4 = (TextView) findViewById(R.id.textView4);
        veri = (ImageView) findViewById(R.id.imageView);
        spinner = (ProgressBar)findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);

        final String userid = ParseUser.getCurrentUser().getObjectId();
        spinner.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.getInBackground(userid, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    String test = object.get("emailVerified").toString();
                    if (test == truee) {
                        startActivity(new Intent(VerifyMail.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
                    } else {
                        setContentView(R.layout.verify_mail);
                        //String test = object.get("emailVerified").toString();
                        text4.setText("Verify your account \n and get back to us. \n Close the app and re-open it");
                        veri.setImageResource(R.drawable.verify);
                    }
                } else {

                    Toast.makeText(VerifyMail.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

        /**refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat();

                String userid = ParseUser.getCurrentUser().getObjectId();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                query.getInBackground(userid , new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        if (e == null) {
                            ParseUser user = ParseUser.getCurrentUser();
                            emailVerified = user.getBoolean("emailVerified");

                            if (emailVerified == true) {

                                Toast.makeText(VerifyMail.this,"If "+ emailVerified.toString(),Toast.LENGTH_LONG).show();
                                startActivity(new Intent(VerifyMail.this, Dp.class));
                            }
                            else {
                                Toast.makeText(VerifyMail.this,emailVerified.toString(),Toast.LENGTH_LONG).show();
                            }
                        }

                        else {
                            Toast.makeText(VerifyMail.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });*/









    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
