package com.samsonjabin.uwall.adapters;

import android.widget.BaseAdapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



;import com.samsonjabin.uwall.R;


public class DrawerListAdapter extends BaseAdapter {

    String titles[], subtitle[];
    int img_resources[];
    private static LayoutInflater inflater=null;


    public DrawerListAdapter(Context context, String title[], int img[]){
        this.titles = title;
        this.img_resources = img;
        this.subtitle = subtitle;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;

        //Check if the view is empty
        if(vi==null)
            vi = inflater.inflate(R.layout.drawer_list_item, viewGroup, false);

        TextView lbl_drawer = (TextView) vi.findViewById(R.id.txt_drawer);
        ImageView img_drawer = (ImageView) vi.findViewById(R.id.img_drawer);

        lbl_drawer.setText(titles[i]);
        img_drawer.setImageResource(img_resources[i]);

        return vi;
    }
}
