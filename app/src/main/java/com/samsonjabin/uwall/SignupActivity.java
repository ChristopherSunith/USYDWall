package com.samsonjabin.uwall;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;


public class SignupActivity extends Activity {

    private EditText username;
    private EditText pass;
    private EditText cpass;
    private EditText regno;
    private RadioGroup sex;
    private EditText sch;
    private EditText mailid;
    private RadioGroup category;
    private RadioButton t;
    private Button signup,login;
    private TextView textview;
    String MALE="MALE", FEMALE="FEMALE", userid;
    String STUD="stud_cat", FAC="FACULTY", ST="STAFF";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        setContentView(R.layout.activity_signup);

        // Set up the signup form.
        username = (EditText) findViewById(R.id.name);
        regno = (EditText) findViewById(R.id.regno);
        sch = (EditText) findViewById(R.id.sch);
        mailid = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        cpass = (EditText) findViewById(R.id.cpass);
        signup = (Button) findViewById(R.id.signup);
        //sex=(RadioGroup) findViewById(R.id.gender);
        category = (RadioGroup) findViewById(R.id.category);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        // Set up the submit button click handler
        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int selectedcat = category.getCheckedRadioButtonId();
                final String user_category = ((RadioButton) findViewById(selectedcat)).getText().toString();


                // Validate the sign up data
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(username)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.username));
                }
                if (isEmpty(regno)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.u_regno));
                }
                if (isEmpty(mailid)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.u_mailid));
                }
                if (isEmpty(sch)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.u_sch));
                }
                if (isEmpty(pass)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.u_pass));
                }
                if (isEmpty(cpass)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.u_cpass));
                }
                if (!isMatching(pass, cpass)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(
                            R.string.error_mismatched_passwords));
                }
                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(SignupActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }


                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(SignupActivity.this);
                dlg.setTitle("Please wait");
                dlg.setMessage("Signing up..");
                dlg.show();

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.user);
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                final ParseFile file = new ParseFile(username.getText().toString() + ".png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            userid = mailid.getText().toString()+"@uni.sydney.edu.au";

                            // Set up a new Parse user
                            ParseUser user = new ParseUser();
                            user.setUsername(userid);
                            user.setPassword(pass.getText().toString());
                            user.setEmail(mailid.getText().append("@uni.sydney.edu.au").toString());
                            user.put("name", username.getText().toString());
                            user.put("regNo", regno.getText().toString());
                            user.put("school",sch.getText().toString());
                            user.put("profilePic",file);
                            user.put("access","null");
                            user.put("category",user_category);


                            // Call the Parse signup method
                            user.signUpInBackground(new SignUpCallback() {

                                @Override
                                public void done(ParseException e) {
                                    dlg.dismiss();
                                    if (e != null) {
                                        // Show the error message
                                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        // Start an intent for the dispatch activity
                                        Intent intent = new Intent(SignupActivity.this, DispatchActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });


                        }
                    }
                });

            }

        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }

    }
}




