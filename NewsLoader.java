package com.example.engit.newsappst1;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.widget.ListView;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader {

    private static final String LOG_TAG = MainActivity.class.getName();
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        if (url == null) {
            return null;
        }
        List<News> newsList = QueryUtils.fetchEarthquakeData(url);
        return newsList;
    }
}
