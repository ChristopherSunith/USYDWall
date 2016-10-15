package com.samsonjabin.uwall.comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.get.Comment;
import com.parse.ParseImageView;

import java.util.ArrayList;
import java.util.List;

public class NewsCommentAdapter  extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Comment> comment_list = null;
    private ArrayList<Comment> arraylist;


    public NewsCommentAdapter(Context context, List<Comment> comment_list) {
        mContext = context;
        this.comment_list = comment_list;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Comment>();
        this.arraylist.addAll(comment_list);
    }

    public class ViewHolder {
        TextView comment;
        TextView username;
        TextView timestamp;
        ParseImageView dp;
    }

    @Override
    public int getCount() {
        return comment_list.size();
    }

    @Override
    public Comment getItem(int position) {
        return comment_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.news_view_comment, null);
            // Locate the TextView in listview_item.xml
            holder.comment = (TextView) view.findViewById(R.id.comment_comment);
            holder.username = (TextView) view.findViewById(R.id.uname_comment_item);
            holder.timestamp = (TextView) view.findViewById(R.id.timestamp);
            holder.dp = (ParseImageView) view.findViewById(R.id.comment_author_dp);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextView
        holder.comment.setText(comment_list.get(position).getComment());
        holder.username.setText(comment_list.get(position).getCommeter());
        holder.timestamp.setText(comment_list.get(position).getTimestamp());
        final ParseImageView dp_imageview = (ParseImageView) view.findViewById(R.id.comment_author_dp);
        dp_imageview.setParseFile(comment_list.get(position).getImage());
        dp_imageview.loadInBackground();


        return view;
    }
}
