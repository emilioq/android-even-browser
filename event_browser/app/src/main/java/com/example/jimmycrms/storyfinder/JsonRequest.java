package com.example.jimmycrms.storyfinder;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.example.csc413_volley_template.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.List;

public class JsonRequest extends Request<List<Meetup>> {

    // Success listener implemented in controller
    private Response.Listener<List<Meetup>> successListener;

    public JsonRequest( int method,
                        String url,
                        Response.Listener<List<Meetup>> successListener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<List<Meetup>> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        List<Meetup> meetups;
        JSONObject jsonObject;
        Log.i(this.getClass().getName(), jsonString);
        try {
            //JSONArray jsonArray = new JSONArray(jsonString);
            //jsonObject = jsonArray.getJSONObject(0);
            meetups = Meetup.parseJson(jsonString);
        }
        catch (JSONException e) {
            e.printStackTrace();

            return Response.error(new VolleyError("Failed to process the request"));
        }

        return Response.success(meetups, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<Meetup> meetups) {
        successListener.onResponse(meetups);
    }
}