package com.samsonjabin.uwall.newupload;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.samsonjabin.uwall.MainActivity;
import com.samsonjabin.uwall.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.app.TimePickerDialog.*;


public class NewEvents extends ActionBarActivity {
    int yr,day,month;
    private ImageButton date;
    private EditText clubname,eventname,venue,desc,con;
    private TextView date_view,time_view;
    private String event_date;
    private Button post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_event);
        clubname = (EditText) findViewById(R.id.post_clubname);
        eventname = (EditText) findViewById(R.id.post_event_name);
        venue = (EditText) findViewById(R.id.venue);
        desc = (EditText) findViewById(R.id.des);
        Calendar today = Calendar.getInstance();
        yr= today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day= today.get(Calendar.DAY_OF_MONTH);
        date=(ImageButton) findViewById(R.id.date_btn);
        date_view=(TextView) findViewById(R.id.date_view);
        time_view = (TextView) findViewById(R.id.time_view);
        post= (Button) findViewById(R.id.done);
        con = (EditText) findViewById(R.id.con);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dpd = new DatePickerDialog(NewEvents.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                date_view.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                event_date =  dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;

                            }
                        }, yr, month, day);
                dpd.show();
            }
        });






        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dlg = new ProgressDialog(NewEvents.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Posting, USYDWall");
                dlg.show();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.codeplay);
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);
                byte[] data = stream.toByteArray();
                ParseFile file = new ParseFile("image"+".jpg", data);
                file.saveInBackground();

                ParseObject postObject = new ParseObject("Events");
                String username = ParseUser.getCurrentUser().get("name").toString();
                postObject.put("content", desc.getText().toString());
                postObject.put("clubname", clubname.getText().toString());
                postObject.put("event_name", eventname.getText().toString());
                postObject.put("date", event_date);
                postObject.put("venue", venue.getText().toString());
                postObject.put("username", username);
                postObject.put("banner", file);
                postObject.put("contactno",con.getText().toString());
                postObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            Toast.makeText(NewEvents.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(NewEvents.this, "Posted successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(NewEvents.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));

                        }
                    }
                });
            }



        });




    }
}
