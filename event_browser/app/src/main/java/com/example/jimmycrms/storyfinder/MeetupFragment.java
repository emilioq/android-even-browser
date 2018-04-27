package com.example.jimmycrms.storyfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;



public class MeetupFragment extends Fragment {

    private static final String ARG_MEETUP_ID = "meetup_id";

    private Meetup meetup;
    private TextView mName;
    private TextView mDescription;
    private ImageView mImage;

    String name;
    String description;
    String urlStr;
    URL url;
    Bitmap bmp;

    private Button locationButton;
    private LocationManager lm;
    private LocationListener locationListener;




    public static MeetupFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putSerializable(ARG_MEETUP_ID, meetupId);

        MeetupFragment fragment = new MeetupFragment();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //UUID meetupId = (UUID) getArguments().getSerializable(ARG_MEETUP_ID);
        Intent intent = getActivity().getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            name = b.getString("name");
            description = b.getString("description");
            urlStr = b.getString("url");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mName = (TextView) v.findViewById(R.id.fragment_story_name);
        mName.setText(name);

        mDescription = (TextView) v.findViewById(R.id.fragment_story_description);
        mDescription.setText(description);

        //urlStr.replace("\\", "%5C");

        urlStr = "http://www.telegraph.co.uk/content/dam/news/2016/08/23/106598324PandawaveNEWS-large_trans++eo_i_u9APj8RuoebjoAHt0k9u7HhRJvuo-ZLenGRumA.jpg";
        DownloadImageTask dit = new DownloadImageTask(mImage);
        mImage = (ImageView) v.findViewById(R.id.image);
        dit.onPostExecute(dit.doInBackground(urlStr));
        //ImageLoader imageLoader = imageSingleton.getInstance(App.getContext()).getImageLoader();
        //mImage.setImageUrl(url, imageLoader);





        /*
        locationButton = (Button) v.findViewById(R.id.location);
        lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getContext(),"Longitude: " + location.getLongitude() + ", Latitude: " + location.getLatitude(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionGranted = ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if(permissionGranted){
                    lm.requestLocationUpdates("gps", 0,0,locationListener);
                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                }
            }
        });
        */

        locationButton = (Button) v.findViewById(R.id.locationButton);
        lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(getContext(),"Longitude: " + location.getLongitude() + ", Latitude: " + location.getLatitude(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permissionGranted = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if(permissionGranted){
                    //lm.requestLocationUpdates("gps", 0,0,locationListener);
                    lm.requestSingleUpdate("gps", locationListener, Looper.myLooper());
                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                }
            }
        });








        return v;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                if(e.getMessage() != null) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                bmImage.setImageBitmap(result);
            }
        }
    }


}