package com.samsonjabin.uwall.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.get.News;
import com.samsonjabin.uwall.get.Post;
import com.samsonjabin.uwall.singleview.NewsView;
import com.samsonjabin.uwall.singleview.PostView;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends BaseAdapter {



    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Post> postlist = null;
    private ArrayList<Post> arraylist;
    protected int count;

    public PostListAdapter(Context context, List<Post> postlist) {
        mContext = context;
        this.postlist = postlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Post>();
        this.arraylist.addAll(postlist);
    }

    public class ViewHolder {
        TextView content;
        TextView username;
        TextView timestamp;

    }

    @Override
    public int getCount() {
        return postlist.size();
    }

    @Override
    public Post getItem(int position) {
        return postlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.post_list_item, null);
            // Locate the TextView in listview_item.xml
            holder.content = (TextView) view.findViewById(R.id.content_post_item);
            holder.username = (TextView) view.findViewById(R.id.uname_post_item);
            holder.timestamp = (TextView) view.findViewById(R.id.post_timestamp);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextView
        holder.content.setText(postlist.get(position).getContent());
        holder.username.setText(postlist.get(position).getUsername());
        holder.timestamp.setText(postlist.get(position).getTimestamp());
        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, PostView.class);
                // Pass all data number
                intent.putExtra("content", (postlist.get(position).getContent()));
                intent.putExtra("username", (postlist.get(position).getUsername()));
                intent.putExtra("timestamp",(postlist.get(position).getTimestamp()));
                // Start SingleItemView Class

                mContext.startActivity(intent);
            }
        });

        return view;
    }
}
