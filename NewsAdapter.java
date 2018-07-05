package com.example.engit.newsappst1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    private final String DATE_SEPATATOR = "T";

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        News currentNews = (News) getItem(position);

        TextView titleNewsTextView = convertView.findViewById(R.id.titleNewsTextView);
        titleNewsTextView.setText(currentNews.getTitle());

        TextView sectionNameTextView = convertView.findViewById(R.id.sectionNameTextView);
        sectionNameTextView.setText(currentNews.getSectionName());

        TextView authorNameTextView = convertView.findViewById(R.id.authorNameTextView);
        authorNameTextView.setText(currentNews.getAuthorName());

        TextView dateViewTextView = convertView.findViewById(R.id.dateTextView);
        String dateOriginal = currentNews.getDate();
        String[] parts = dateOriginal.split(DATE_SEPATATOR);
        String dateOnly = parts[0];
        dateViewTextView.setText(dateOnly);
        return convertView;
    }
}
