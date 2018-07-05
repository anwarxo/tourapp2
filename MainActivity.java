package com.example.engit.newsappst1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public  class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {


    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int NEWS_LOADER_ID = 1;

    private static final String GARDIAN_REQUEST_URL ="http://content.guardianapis.com/search?";
    private NewsAdapter adapter;
    private TextView emptyTextView;
    private ProgressBar loadingIndecator;

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new NewsAdapter(this, new ArrayList<News>());
        ListView newslistView = findViewById(R.id.list);

        emptyTextView = findViewById(R.id.emptyState);
        loadingIndecator = findViewById(R.id.loading_indicator);

        newslistView.setEmptyView(emptyTextView);
        newslistView.setAdapter(adapter);

        if (isNetworkConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            loadingIndecator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.NO_INTERNET_CONNECTION);
        }
        newslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews = (News) adapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent webSiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(webSiteIntent);
            }
        });

    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String minNews = sharedPreferences.getString(getString(R.string.settings_min_news_key), getString(R.string.settings_min_news_default));
        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));
        String section = sharedPreferences.getString(getString(R.string.settings_section_news_key), getString(R.string.settings_section_news_default));

        Uri baseUri = Uri.parse(GARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "8d0335fb-a585-44a0-a958-a68ef63898f0");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", minNews);
        uriBuilder.appendQueryParameter("order-by", orderBy);

        if (!section.equals(getString(R.string.settings_section_news_default))) {
            uriBuilder.appendQueryParameter("section", section);
        }

        return new NewsLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        loadingIndecator.setVisibility(View.GONE);
        adapter.clear();
        emptyTextView.setText((R.string.no_news));
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.clear();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
