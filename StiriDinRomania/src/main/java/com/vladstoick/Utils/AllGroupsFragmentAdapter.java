package com.vladstoick.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vladstoick.DataModel.NewsGroup;
import com.vladstoick.stiridinromania.R;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by vlad on 7/19/13.
 */
public class AllGroupsFragmentAdapter extends BaseAdapter {
    static class RowHolder {
        @InjectView(R.id.groupTitle)
        TextView mTitle;
        @InjectView(R.id.numberOfGroups)
        TextView mNumberOfGroups;

        public RowHolder(View view) {
            Views.inject(this, view);
        }
    }

    private final Context context;
    private ArrayList<NewsGroup> data;

    public AllGroupsFragmentAdapter(ArrayList<NewsGroup> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public NewsGroup getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowHolder holder;
        final NewsGroup ng = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_row_allgroups, parent, false);
            holder = new RowHolder(row);
            row.setTag(holder);
        } else {
            holder = (RowHolder) row.getTag();
        }
        holder.mTitle.setText(ng.getTitle());

        int noGroups = ng.getNoFeeds();
        String noGroupsString;
        if (noGroups == 1)
            noGroupsString = noGroups + " " + context.getString(R.string.feed).toLowerCase();
        else
            noGroupsString = noGroups + " " + context.getString(R.string.feeds).toLowerCase();
        holder.mNumberOfGroups.setText(noGroupsString);
        return row;
    }
}