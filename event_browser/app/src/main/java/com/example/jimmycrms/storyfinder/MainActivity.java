package com.example.jimmycrms.storyfinder;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RecyclerViewAdapter.OnClickListener {

    private RecyclerViewAdapter adapter;
    JsonController controller;

    TextView textView;
    RecyclerView recyclerView;

    private Button locationButton;
    private LocationManager lm;
    private LocationListener locationListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = (TextView) findViewById(R.id.tvEmptyRecyclerView);
        textView.setText("Search for Meetups!");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(new ArrayList<Meetup>());
        adapter.setListener(this);

        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<Meetup> meetups) {
                        if(meetups.size() > 0) {
                            textView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.invalidate();
                            adapter.updateDataSet(meetups);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Failed to retrieve data");
                        Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(query);
            return false;
        } else {
            Toast.makeText(MainActivity.this, "Must provide more than one character", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Must provide more than one character to search");
            return true;
        }
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(newText);
        } else if(newText.equals("")) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        return true;
    }


    @Override
    public void onCardClick(Meetup meetup) {
        //Toast.makeText(this, meetup.getName() + " clicked", Toast.LENGTH_SHORT).show();

        Meetup m = meetup;
        //Intent intent = MeetupActivity.newIntent(MainActivity.this, meetup.getUUID());
        Intent intent = new Intent(MainActivity.this, MeetupActivity.class);
        Bundle b = new Bundle();
        b.putString("name", meetup.getName());
        b.putString("description", meetup.getDescription());
        b.putString("url", meetup.getUrlkey());
        intent.putExtras(b);

        startActivity(intent);
        //finish();

        //Context c = getContext();
        //final MediaPlayer mp = MediaPlayer.create(c, R.raw.sound1);
        //mp.start();

    }

    @Override
    public void onImageClick(Meetup meetup) {
        //Toast.makeText(this, meetup.getName() + " image clicked", Toast.LENGTH_SHORT).show();
    }
}