package com.samsonjabin.uwall.newupload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.samsonjabin.uwall.MainActivity;
import com.samsonjabin.uwall.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by AravindRaj on 04-05-2015.
 */
public class NewPost extends ActionBarActivity {

    EditText con;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
        con = (EditText) findViewById(R.id.new_post_content);
        post = (Button) findViewById(R.id.post_button);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(NewPost.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Posting, vitly");
                dlg.show();
                ParseObject postObject = new ParseObject("Post");
                String username = ParseUser.getCurrentUser().get("name").toString();
                postObject.put("content", con.getText().toString());
                postObject.put("username", username);
                postObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e!=null)
                        {
                            Toast.makeText(NewPost.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(NewPost.this,"Posted Successfully!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(NewPost.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    }
                });
            }
        });

    }
}
