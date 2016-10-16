package com.samsonjabin.uwall.newupload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.samsonjabin.uwall.MainActivity;
import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.fragments.ArticlesFragment;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class NewArticle extends ActionBarActivity {
    EditText title, content;
    Button post;
    String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_article);
        title = (EditText) findViewById(R.id.new_article_title);
        content = (EditText) findViewById(R.id.new_article_content);
        post = (Button) findViewById(R.id.post_article);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(NewArticle.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Posting, USYDWall");
                dlg.show();
                ParseUser pcu = ParseUser.getCurrentUser();
                ParseFile dp= pcu.getParseFile("profilePic");
                dp.saveInBackground();
                ParseObject postObject = new ParseObject("Articles");
                String username = ParseUser.getCurrentUser().get("name").toString();
                postObject.put("content", content.getText().toString());
                postObject.put("author", username);
                postObject.put("article_title", title.getText().toString());
                //postObject.put("author_avatar","author_avatar.jpg");
                //postObject.put("avatarFile",dp);
                postObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            Toast.makeText(NewArticle.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(NewArticle.this, "Posted successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(NewArticle.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));

                        }
                    }
                });
            }
        });
    }
}
