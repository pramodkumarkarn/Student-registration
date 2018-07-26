package com.proj4.rakshithramesh.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rakshithramesh on 4/2/18.
 */

public class MajorListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MajorListModel> items;

    public MajorListAdapter(Context context, ArrayList<MajorListModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_view_row_item, parent, false);
        }

        // get current item to be displayed
        MajorListModel currentItem = (MajorListModel) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.row_item_title_name);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.row_item_college);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getTitle());
        textViewItemDescription.setText(currentItem.getCollege());

        // returns the view for the current row
        return convertView;

    }
}
