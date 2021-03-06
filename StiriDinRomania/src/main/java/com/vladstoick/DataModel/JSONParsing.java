package com.vladstoick.DataModel;

import android.content.SharedPreferences;

import com.google.gson.JsonObject;
import com.vladstoick.DataModel.NewsGroup;
import com.vladstoick.DataModel.NewsItem;
import com.vladstoick.DataModel.NewsSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vlad on 7/19/13.
 */
public class JSONParsing {
    public static ArrayList<NewsGroup> parseNewsDataSource(String response) {
        ArrayList<NewsGroup> newsDataSource = new ArrayList<NewsGroup>();

        try {
            JSONArray responseJA = new JSONArray(response);

            for (int groupIterator = 0; groupIterator < responseJA.length(); groupIterator++) {
                JSONObject groupJO = responseJA.getJSONObject(groupIterator);
                NewsGroup ng = new NewsGroup(groupJO.getString(NewsGroup.TAG_TITLE),
                        groupJO.getInt(NewsGroup.TAG_ID));
                JSONArray feedsJA = groupJO.getJSONArray(NewsGroup.TAG_NEWSOURCES);
                ng.setNoFeeds(feedsJA.length());
                for (int feedIterator = 0; feedIterator < feedsJA.length(); feedIterator++) {
                    JSONObject feedJO = feedsJA.getJSONObject(feedIterator);
                    NewsSource ns = new NewsSource(feedJO.getString(NewsSource.TAG_RSSLINK),
                            feedJO.getString(NewsSource.TAG_TITLE),
                            feedJO.getInt(NewsSource.TAG_ID));
                    ns.setGroupId(ng.getId());
                    ng.newsSources.add(ns);
                }
                newsDataSource.add(ng);


            }
            return newsDataSource;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<NewsItem> parseFeed(JSONArray newsJArray) {
        try {

            ArrayList<NewsItem> newsItems = new ArrayList<NewsItem>();
            for (int i = 0; i < newsJArray.length(); i++) {
                JSONObject jo = newsJArray.getJSONObject(i);
                NewsItem ni = new NewsItem(jo.getString(NewsItem.TAG_TITLE),
                        jo.getString(NewsItem.TAG_DESCRIPTION),
                        jo.getString(NewsItem.TAG_URLLINK),
                        jo.getLong(NewsItem.TAG_DATE),
                        jo.getInt(NewsItem.TAG_ID));
                newsItems.add(ni);
            }
            return newsItems;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<NewsItem>();
        }

    }

    public static int parseAddNewsGroupResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt(NewsGroup.TAG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void parseAddNewsSource(NewsSource ns,String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            ns.setId(jsonObject.getInt("id"));
            ns.setTitle(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void parseServerLogin(JSONObject jsonObject, SharedPreferences.Editor editor) {
        try {
            int userId = jsonObject.getInt("id");
            String token = jsonObject.getString("key");
            editor.putString("key",token);
            editor.putInt("user_id", userId);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<NewsItem> parseSearchResults(JSONObject jsonObject) {
        ArrayList<NewsItem> results = new ArrayList<NewsItem>();
        try {
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("docs");
            for(int i=0; i<resultsArray.length(); i++){
                JSONObject newsItem = resultsArray.getJSONObject(i);
                NewsItem ni = new NewsItem(newsItem.getString("title"),
                        newsItem.getString("content"),
                        newsItem.getString("url"),0,0);
                results.add(ni);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<Integer> parseUnreadIds(JSONObject jsonObject){
        try {
            ArrayList<Integer> result = new ArrayList<Integer>();
            JSONArray articles = jsonObject.getJSONArray("articles");
            for(int i=0; i <articles.length();i++){
                result.add(articles.getInt(i));
            }
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static ArrayList<NewsSource> parseFeeds(JSONObject jsonObject){
        try{
            JSONArray feeds = jsonObject.getJSONArray("feeds");
            ArrayList<NewsSource> results = new ArrayList<NewsSource>();
            for(int i=0; i< feeds.length(); i++){
                JSONObject feed = feeds.getJSONObject(i);
                NewsSource ns = new NewsSource(feed.getString("url"),feed.getString("title")
                        ,feed.getInt("id"));
                ns.category = feed.getString("category");
                results.add(ns);
            }
            return results;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<NewsSource>();
        }
    }

}
