package com.samsonjabin.uwall.adapters;

/**
 * Created by AravindRaj on 18-03-2015.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.get.News;
import com.samsonjabin.uwall.singleview.NewsView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_LONG;

public class NewsListAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<News> newslist = null;
    private ArrayList<News> arraylist;


    public NewsListAdapter(Context context, List<News> newslist) {
        this.mContext = context;
        this.newslist = newslist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<News>();
        this.arraylist.addAll(newslist);
    }

    public class ViewHolder {
        TextView content;
        TextView username;
        TextView timestamp;
        TextView ob;
    }

    @Override
    public int getCount() {
        return newslist.size();
    }

    @Override
    public News getItem(int position) {
        return newslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
           view = inflater.inflate(R.layout.news_list_item, null);
            // Locate the TextView in listview_item.xml
            holder.content = (TextView) view.findViewById(R.id.content_news_item);
            holder.username = (TextView) view.findViewById(R.id.uname_news_item);
            holder.timestamp = (TextView) view.findViewById(R.id.timestamp);



            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextView
        String obj = newslist.get(position).getObjectid().toString();
        holder.content.setText(newslist.get(position).getContent());
        holder.username.setText(newslist.get(position).getUsername());
        holder.timestamp.setText(newslist.get(position).getTimestamp());
        final CircleImageView dp_imageview = (CircleImageView) view.findViewById(R.id.news_author_dp);
        ParseQuery<ParseObject> dp_query = ParseQuery.getQuery("News");

        dp_query.getInBackground(obj, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    ParseFile dp_file = (ParseFile) parseObject.get("Dp_file");
                    dp_file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e==null) {
                                Bitmap bmp = BitmapFactory
                                        .decodeByteArray(data, 0, data.length);

                                dp_imageview.setImageBitmap(bmp);
                            }

                        }
                    });
                }
            }
        });


        final ParseImageView imageView = (ParseImageView) view.findViewById(R.id.imageView_news);
        imageView.setParseFile(newslist.get(position).getImage());
        //imageView.setPlaceholder()
        imageView.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                //Bitmap bmp = BitmapFactory.decodeResource(getResources(), image.getId());
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //byte[] byteArray = stream.toByteArray();
            }
        });

        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
               Intent intent = new Intent(mContext, NewsView.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                // Pass all data number
                intent.putExtra("content", (newslist.get(position).getContent()));
                intent.putExtra("username", (newslist.get(position).getUsername()));
                intent.putExtra("timestamp",(newslist.get(position).getTimestamp()));
                intent.putExtra("obj",(newslist.get(position).getObjectid()));
                intent.putExtra("views",newslist.get(position).getViews());
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}