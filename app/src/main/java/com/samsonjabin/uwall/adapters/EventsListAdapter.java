package com.samsonjabin.uwall.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.get.Events;
import com.samsonjabin.uwall.get.News;
import com.samsonjabin.uwall.singleview.EventView;
import com.samsonjabin.uwall.singleview.NewsView;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AravindRaj on 05-04-2015.
 */
public class EventsListAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Events> eventslist = null;
    private ArrayList<Events> arraylist;


    public EventsListAdapter(Context context, List<Events> eventslist) {
        mContext = context;
        this.eventslist = eventslist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Events>();
        this.arraylist.addAll(eventslist);
    }

    public class ViewHolder {
        TextView content;
        TextView username;
        TextView timestamp;
        ParseImageView banner;
    }

    @Override
    public int getCount() {
        return eventslist.size();
    }

    @Override
    public Events getItem(int position) {
        return eventslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.events_list_item, null);
            // Locate the TextView in listview_item.xml
            holder.content = (TextView) view.findViewById(R.id.event_name);
            holder.username = (TextView) view.findViewById(R.id.event_host_name);
            holder.timestamp = (TextView) view.findViewById(R.id.event_date);
            holder.banner = (ParseImageView) view.findViewById(R.id.event_banner);



            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextView
        String obj = eventslist.get(position).getObjectId().toString();
        holder.content.setText(eventslist.get(position).getEventname());
        holder.username.setText(eventslist.get(position).gethostname());
        holder.timestamp.setText(eventslist.get(position).getDate());
        holder.banner.setParseFile(eventslist.get(position).getEvent_banner());
        final CircleImageView dp_imageview = (CircleImageView) view.findViewById(R.id.news_author_dp);
        /**ParseQuery<ParseObject> dp_query = ParseQuery.getQuery("Events");

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
        });*/


        final ParseImageView imageView = (ParseImageView) view.findViewById(R.id.imageView_news);
        //imageView.setParseFile(eventslist.get(position).getImage());
        //imageView.setPlaceholder()
        //imageView.loadInBackground(new GetDataCallback() {
           // @Override
            //public void done(byte[] bytes, ParseException e) {
                //Bitmap bmp = BitmapFactory.decodeResource(getResources(), image.getId());
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //byte[] byteArray = stream.toByteArray();
           // }
        //});

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, EventView.class);
                // Pass all data number
                intent.putExtra("content", (eventslist.get(position).getDesc()));
                intent.putExtra("clubname", (eventslist.get(position).gethostname()));
                intent.putExtra("eventname",(eventslist.get(position).getEventname()));
                intent.putExtra("date",(eventslist.get(position).getDate()));
                intent.putExtra("timestamp",(eventslist.get(position).getTimestamp()));
                intent.putExtra("obj",(eventslist.get(position).getObjectId()));
                intent.putExtra("venue",(eventslist.get(position).getVenue()));
                intent.putExtra("views", eventslist.get(position).getViews());
                intent.putExtra("time",eventslist.get(position).getTime());
                intent.putExtra("con", eventslist.get(position).getContact());
                // Start SingleItemView Class

                mContext.startActivity(intent);
            }
        });

        return view;
    }


}
