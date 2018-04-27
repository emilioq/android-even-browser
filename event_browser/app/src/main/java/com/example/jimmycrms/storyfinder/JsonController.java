package com.example.jimmycrms.storyfinder;

/**
 * Created by vaxa on 12/13/2016.
 */

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.example.android.app.App;
//import com.example.csc413_volley_template.model.Movie;
//import com.example.csc413_volley_template.request.JsonRequest;
//import com.example.csc413_volley_template.volley.VolleySingleton;

import java.util.List;

/*
 * Created by abhijit on 12/2/16.
 */


public class JsonController {

    private final int TAG = 100;

    private OnResponseListener responseListener;


    public JsonController(OnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void sendRequest(String query){

        // Request Method
        int method = Request.Method.GET;

        // Url with GET parameters
        String url = "https://api.meetup.com/find/groups?&sign=true&photo-host=public&text=" + Uri.encode(query) +  "&page=20&key=1fa424d713b05248482d3131622c";

        // Create new request using JsonRequest
        JsonRequest request
                = new JsonRequest(method, url, new Response.Listener<List<Meetup>>() {
                    @Override
                    public void onResponse(List<Meetup> meetups) {
                        responseListener.onSuccess(meetups);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onFailure(error.getMessage());
                    }
                }
        );


        request.setTag(TAG);

        imageSingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    public void cancelAllRequests() {
        imageSingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    public interface OnResponseListener {
        void onSuccess(List<Meetup> meetups);
        void onFailure(String errorMessage);
    }

}