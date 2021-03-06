package com.example.ashiagrawal.nytsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by ashiagrawal on 6/20/16.
 */
@Parcel
public class Article {

    String webUrl;
    String headline;
    String thumbnail;

    public Article () {

    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Article (JSONObject jsonObject, String type) {
        try {
            if (type == "search") {
                this.webUrl = jsonObject.getString("web_url");
                this.headline = jsonObject.getJSONObject("headline").getString("main");
                JSONArray multimedia = jsonObject.getJSONArray("multimedia");
                if (multimedia.length() > 0) {
                    JSONObject multimediaJson = multimedia.getJSONObject(0);
                    this.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
                } else {
                    this.thumbnail = "http://www.rakuten.co.uk/assets/noimage_96x96.gif";
                }
            } else if (type == "browse"){
                this.webUrl = jsonObject.getString("url");
                this.headline = jsonObject.getString("title");
                JSONArray multimedia = jsonObject.getJSONArray("multimedia");
                if (multimedia.length() > 0) {
                    JSONObject multimediaJson = multimedia.getJSONObject(0);
                    if (type == "search") this.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
                    else if (type == "browse") this.thumbnail = multimediaJson.getString("url");
                }
            }
        } catch (JSONException e){

        }
    }

    public static ArrayList<Article> fromJSONArray(JSONArray array, String type){
        ArrayList<Article> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            try {
                results.add(new Article(array.getJSONObject(i), type));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
