package com.proj4.rakshithramesh.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rakshithramesh on 4/5/18.
 */

public class DropClassAdapter extends BaseAdapter {

    DropButtonClickListener dropButtonClickListener;
    private Context context;
    private ArrayList<CourseListModel> items;

    public DropClassAdapter(Context context, ArrayList<CourseListModel> items) {
        this.context = context;
        this.dropButtonClickListener = (DropButtonClickListener) context;
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
                    inflate(R.layout.list_view_class_item, parent, false);
        }

        // get current item to be displayed
        CourseListModel currentItem = (CourseListModel) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.classTitleValue);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.departmentTitleValue);
        TextView textViewInstructor = (TextView)
                convertView.findViewById(R.id.instructorTitleValue);
        TextView textViewItemStartTime = (TextView)
                convertView.findViewById(R.id.startTimeTitleValue);
        TextView textViewItemEndTime = (TextView)
                convertView.findViewById(R.id.endTimeTitleValue);
        Button dropButton = convertView.findViewById(R.id.dropButton);
        dropButton.setOnClickListener(view -> {
            dropButtonClickListener.dropClass(position);
        });


        //sets the text for items from the current item object
        textViewItemName.setText(currentItem.getTitle());
        textViewItemDescription.setText(currentItem.getDepartment());
        textViewInstructor.setText(currentItem.getInstructor());
        textViewItemStartTime.setText(currentItem.getStartTime());
        textViewItemEndTime.setText(currentItem.getEndTime());

        // returns the view for the current row
        return convertView;
    }

    public interface DropButtonClickListener {
        void dropClass(Integer position);
    }
}
