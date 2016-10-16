package com.samsonjabin.uwall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



public class Dp extends Activity {
    private ImageView dp_avatar;
    private static final int RESULT_LOAD_IMAGE = 1;
    private Button upload;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser pcu = ParseUser.getCurrentUser();
        setContentView(R.layout.dp_upload);
        dp_avatar = (ImageView) findViewById(R.id.dp_iv);
        upload = (Button) findViewById(R.id.button);
        username = ParseUser.getCurrentUser().toString();

        final String picturePath = PreferenceManager.getDefaultSharedPreferences(this).getString("picturePath", "");
        if (!picturePath.equals("")) {
            ImageView imageView = (ImageView) findViewById(R.id.image_preview);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }

        dp_avatar.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

            ImageView imageView = (ImageView) findViewById(R.id.dp_iv);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {

                    upload.setEnabled(false);
                    upload.setBackgroundColor(getResources().getColor(R.color.button_material_dark));
                    final ProgressDialog dlg = new ProgressDialog(Dp.this);
                    dlg.setTitle("Please wait.");
                    dlg.setMessage("USYDWall");
                    dlg.show();
                    // Locate the image in res >
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    //Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream);

                    Object image = null;
                    try {
                        String path = null;
                        image = readInFile(path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    byte[] data = stream.toByteArray();
                    // Create the ParseFile
                    ParseFile file = new ParseFile(username+"avatar_file"+".jpg", data);
                    // Upload the image into Parse Cloud
                    file.saveInBackground();

                    // Create a New Class called "ImageUpload" in Parse


                    ParseUser pcu = ParseUser.getCurrentUser();
                    // Create a column named "ImageName" and set the string
                    pcu.put("DpImagename", username + "avatar_name" + ".jpg");


                    // Create a column named "ImageFile" and insert the image
                    pcu.put("profilePic", file);

                    // Create the class and the columns
                    pcu.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                dlg.dismiss();
                                Toast.makeText(Dp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                dlg.dismiss();
                                Toast.makeText(Dp.this, "Profile Picture updated!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Dp.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP));


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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //Intent intent = new Intent(VerifyMail.this, SignupActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("EXIT", true);
            //startActivity(intent);
            startActivity(new Intent(Dp.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
        }
        return super.onKeyDown(keyCode, event);
    }


}
