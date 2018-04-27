package com.example.jimmycrms.storyfinder.oldstory;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;


import android.media.MediaPlayer;

import com.android.volley.toolbox.NetworkImageView;


import com.example.jimmycrms.storyfinder.JsonController;
import com.example.jimmycrms.storyfinder.R;


public class StoryListFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private StoryAdapter mStoryAdapter;
    private SearchView mSV;

    private Button locationButton;
    private LocationManager lm;
    private LocationListener locationListener;

    JsonController controller;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_list, container, false);
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.recycler_view);
        mSV = (SearchView) view.findViewById(R.id.mSearch);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        locationButton = (Button) view.findViewById(R.id.location);
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

        mSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mStoryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mStoryAdapter.getFilter().filter(newText);

                return false;
            }
        });


        /*
        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<Meetup> meetups) {
                        if(meetups.size() > 0) {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mRecyclerView.invalidate();
                            updateUI();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        //Toast.makeText(App.getContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }

        });
        */





        updateUI();
        return view;
    }

    private void updateUI() {
        StoryCollection c = StoryCollection.get(getActivity());
        List<Story> stories = c.getStories();

        if (mStoryAdapter == null){
            mStoryAdapter = new StoryAdapter(getContext(),stories); //added StoryAdapter
            mRecyclerView.setAdapter(mStoryAdapter);
        }else{
            mStoryAdapter.notifyDataSetChanged();
        }
    }

    private class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mDescriptionTextView;
        private NetworkImageView image;


        private Story mStory;

        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.list_item_story_name_text_view);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.list_item_story_description_text_view);
            this.image = (NetworkImageView) itemView.findViewById(R.id.image1);

        }

        public void bindStory(Story story) {
            mStory = story;
            mNameTextView.setText(mStory.getName());
            mDescriptionTextView.setText(mStory.getDescription());

        }

        @Override
        public void onClick(View v) {
            Intent intent = StoryActivity.newIntent(getActivity(), mStory.getId());
            startActivity(intent);

            Context c = getContext();
            final MediaPlayer mp = MediaPlayer.create(c, R.raw.sound1);
            mp.start();
        }
    }


    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> implements Filterable{
        Context c;
        private List<Story> mStories;
        private List<Story> filters;
        SearchFilter filter;

        public StoryAdapter(Context c, List<Story> stories) {
            this.c = c;
            mStories = stories;
            this.filters = mStories;
        }

        @Override
        public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_story, parent, false);
            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(StoryHolder holder, int position) {
            Story card = mStories.get(position);
            holder.bindStory(card);
        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }

        @Override
        public Filter getFilter(){
            if (filter== null){
                filter = new SearchFilter (filters, this);
            }
            return filter;
        }
    }

    private class SearchFilter extends Filter{
        StoryAdapter adapter;
        List<Story> filters;
        private SearchFilter(List<Story> filters, StoryAdapter adapter){
            this.adapter = adapter;
            this.filters = filters;
        }

        @Override
        protected FilterResults performFiltering (CharSequence arg){
            FilterResults res = new FilterResults();
            if(arg != null && arg.length() > 0) {
                arg = arg.toString().toUpperCase();

                List<Story> filteredStories = new ArrayList<>();

                for (int i = 0; i < filters.size(); i++) {
                    if (filters.get(i).getName().toUpperCase().contains(arg)) {
                        //add player to filtered players
                        filteredStories.add(filters.get(i));
                    }
                }
                res.count = filteredStories.size();
                res.values = filteredStories;
            }else{
                res.count = filters.size();
                res.values = filters;
            }
            return res;
        }
        @Override
        protected void publishResults(CharSequence arg, FilterResults res){
            adapter.mStories = (List<Story>) res.values;
            adapter.notifyDataSetChanged();
        }
    }







}







