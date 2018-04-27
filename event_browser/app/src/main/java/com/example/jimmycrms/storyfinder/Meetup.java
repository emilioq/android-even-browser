package com.example.jimmycrms.storyfinder;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;



public class Meetup {

    private String urlkey;
    private String members;
    private String name;
    private String description;
    private String id;
    private String updated;
    private UUID mId;


    public static List<Meetup> parseJson(String str) throws JSONException{
        List<Meetup> meetups = new ArrayList<>();
        // Check if the JSONObject has object with key "Search"
       //if(jsonObject.has("")){
            // Get JSONArray from JSONObject
            JSONArray jsonArray = new JSONArray(str);
            for(int i = 0; i < jsonArray.length(); i++){
                meetups.add(new Meetup(jsonArray.getJSONObject(i)));
            }
        //}

        return meetups;
    }

    private Meetup(JSONObject jsonObject) throws JSONException {
        if(jsonObject.has("photo")){
            JSONObject photo = jsonObject.getJSONObject("photo_link");
            this.setUrlkey(photo.getString("photo_link"));
        }
        if(jsonObject.has("members")) this.setMembers(jsonObject.getString("members"));
        if(jsonObject.has("name")) this.setName(jsonObject.getString("name"));
        if(jsonObject.has("description")) this.setDescription(jsonObject.getString("description"));
        if(jsonObject.has("id")) this.setId(jsonObject.getString("id"));
        //if(jsonObject.has("updated")) this.setUpdated(jsonObject.getString("updated"));
        mId = UUID.randomUUID();
    }


    public UUID getUUID() {
        return mId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description =(description);
    }

    public String getUrlkey() {
        return urlkey;
    }

    public void setUrlkey(String urlkey) {
        this.urlkey = urlkey;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }



}