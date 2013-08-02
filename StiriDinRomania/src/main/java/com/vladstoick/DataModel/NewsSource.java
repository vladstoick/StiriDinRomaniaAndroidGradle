package com.vladstoick.DataModel;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.otto.Subscribe;
import com.vladstoick.OttoBus.BusProvider;
import com.vladstoick.OttoBus.NewsSourceFeedLoaded;
import com.vladstoick.stiridinromania.StiriApp;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vlad on 7/19/13.
 */
public class NewsSource implements Parcelable {
    public static class DataDownloaded {
        public String url;
        ArrayList<NewsItem> items;
        public DataDownloaded(ArrayList<NewsItem> items, String url)
        {
            this.items = items;
            this.url = url;
        }
    }
    String url;
    public static String BASE_URL="http://37.139.8.146:3000/?url=";
    public static String TAG_RSSLINK = "url";
    private String rssLink;
    public static String TAG_TITLE = "title";
    private String title;
    public static String TAG_DESCRIPTION = "description";
    private String description;
    public ArrayList<NewsItem> news;
    public static String TAG_ID = "id";
    private int id;
    private int groupId;
    private int numberOfUnreadNews;
    public NewsSource(String rssLink, String title, String description, int id) {
        this.rssLink = rssLink;
        this.title = title;
        this.description = description;
        this.id = id;
        news = new ArrayList<NewsItem>();

    }
    public NewsSource(Cursor cursor)
    {
        this.id = cursor.getInt(0);
        this.title = cursor.getString(1);
        this.description = cursor.getString(2);
        this.rssLink = cursor.getString(3);
        this.groupId = cursor.getInt(4);
        this.numberOfUnreadNews = cursor.getInt(5);
    }
    public void loadFeed(StiriApp app)
    {
        BusProvider.getInstance().register(this);
    }
    public int getNumberOfUnreadNews() {
        return numberOfUnreadNews;
    }

    public void setNumberOfUnreadNews(int numberOfUnreadNews) {
        this.numberOfUnreadNews = numberOfUnreadNews;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRssLink() {

        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    //PARCELABLE
    public static final Parcelable.Creator<NewsSource> CREATOR
            = new Parcelable.Creator<NewsSource>() {
        public NewsSource createFromParcel(Parcel in) {
            return new NewsSource(in);
        }

        public NewsSource[] newArray(int size) {
            return new NewsSource[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rssLink);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeTypedList(news);
        dest.writeInt(groupId);
        dest.writeInt(numberOfUnreadNews);
    }

    private NewsSource(Parcel in) {
        news = new ArrayList<NewsItem>();
        this.rssLink = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        in.readTypedList(news, NewsItem.CREATOR);
        this.groupId = in.readInt();
        this.numberOfUnreadNews = in.readInt();
    }

}
