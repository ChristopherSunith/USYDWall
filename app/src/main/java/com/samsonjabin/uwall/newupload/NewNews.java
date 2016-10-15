package com.samsonjabin.uwall.newupload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.samsonjabin.uwall.MainActivity;
import com.samsonjabin.uwall.R;
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by AravindRaj on 19-03-2015.
 */
public class NewNews extends Activity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private Button upload,pick;
    private EditText desc;
    private String pcu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_news);
        upload = (Button) findViewById(R.id.upload_button);
        pick = (Button) findViewById(R.id.pick_button);
        desc =(EditText) findViewById(R.id.image_caption);

        final String picturePath = PreferenceManager.getDefaultSharedPreferences(this).getString("picturePath", "");
        if (!picturePath.equals("")) {
            ImageView imageView = (ImageView) findViewById(R.id.image_preview);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        pick = (Button) findViewById(R.id.pick_button);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            cursor.close();

            final ImageView imageView = (ImageView) findViewById(R.id.image_preview);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    final ProgressDialog dlg = new ProgressDialog(NewNews.this);
                    dlg.setTitle("Please wait.");
                    dlg.setMessage("vitly");
                    dlg.show();
                    // Locate the image
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);

                    Object image = null;
                    try {
                        String path = null;
                        image = readInFile(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                    ParseUser user = ParseUser.getCurrentUser();
                    pcu = ParseUser.getCurrentUser().get("name").toString();
                    /**ParseFile dp_image = pcu.getParseFile("profilePic");
                    dp_image.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                           ParseFile dp_file = new ParseFile("post_author"+".jpg",bytes);
                            dp_file.saveInBackground();
                        }
                    } );*/

                    byte[] data = stream.toByteArray();
                    // Create the ParseFile
                    ParseFile file = new ParseFile("image"+".jpg", data);
                    // Upload the image into Parse Cloud
                    file.saveInBackground();
                    ParseFile dp= user.getParseFile("profilePic");
                    dp.saveInBackground();


                    // Create a New Class called "ImageUpload" in Parse
                    ParseObject imgupload = new ParseObject("News");

                    // Create a column named "ImageName" and set the string
                    imgupload.put("Image", "test.jpg");


                    // Create a column named "ImageFile" and insert the image
                    imgupload.put("ImageFile", file);
                    imgupload.put("news",desc.getText().toString());
                    imgupload.put("views",0);
                    imgupload.put("author_avatar","author_avatar.jpg");
                    imgupload.put("Dp_file",dp);
                    imgupload.put("username",pcu);

                    // Create the class and the columns
                    imgupload.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null)
                            {
                                dlg.dismiss();
                                Toast.makeText(NewNews.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            else {
                                dlg.dismiss();
                                Toast.makeText(NewNews.this,"Done.",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(NewNews.this, MainActivity.class));



                            }
                        }
                    });



                }
            });



        }

    }

    private byte[] readInFile(String path) throws IOException {
        // TODO Auto-generated method stub
        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(
                file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;
        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }
        input_stream.close();
        return buffer.toByteArray();

    }


}