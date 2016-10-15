package com.samsonjabin.uwall.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samsonjabin.uwall.R;
import com.samsonjabin.uwall.get.Articles;
import com.samsonjabin.uwall.singleview.ArticlesView;
import com.samsonjabin.uwall.singleview.EventView;

import java.util.ArrayList;
import java.util.List;


public class ArticlesListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<Articles> articleslist = null;
    private ArrayList<Articles> arraylist;

    public ArticlesListAdapter(Context context, List<Articles> articleslist) {
        mContext= context;
        this.articleslist= articleslist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(articleslist);

    }

    public class ViewHolder {
        TextView title;
        TextView username;
        TextView timestamp;
    }

    @Override
    public int getCount() {
        return articleslist.size();
    }

    @Override
    public Articles getItem(int position) {
        return articleslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.articles_item, null);
            // Locate the TextView in listview_item.xml
            holder.title = (TextView) convertView.findViewById(R.id.article_item_title);
            holder.username = (TextView) convertView.findViewById(R.id.article_item_uname);
            holder.timestamp = (TextView) convertView.findViewById(R.id.article_item_timestamp);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Set the results into TextView
        String obj = articleslist.get(position).getObjectId().toString();
        holder.title.setText(articleslist.get(position).getTitle());
        holder.username.setText(articleslist.get(position).getUname());
        holder.timestamp.setText(articleslist.get(position).getTimestamp());


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, ArticlesView.class);
                // Pass all data number
                intent.putExtra("content", (articleslist.get(position).getArticle()));
                intent.putExtra("uname",(articleslist.get(position).getUname()));
                intent.putExtra("timestamp",(articleslist.get(position).getTimestamp()));
                intent.putExtra("obj",(articleslist.get(position).getObjectId()));
                intent.putExtra("views",articleslist.get(position).getViews());
                intent.putExtra("title",articleslist.get(position).getTitle());
                // Start SingleItemView Class

                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
