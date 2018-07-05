package com.example.engit.newsappst1;

import java.util.List;

public class News {
    private String title;
    private String sectionName;
    private String date;
    private String url;
    private String authorName;

    public News(String title, String sectionName, String authorName, String date, String url) {
        this.title = title;
        this.sectionName = sectionName;
        this.date = date;
        this.url = url;
        this.authorName = authorName;
    }

    List<News> newsList = QueryUtils.fetchEarthquakeData(url);



    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
