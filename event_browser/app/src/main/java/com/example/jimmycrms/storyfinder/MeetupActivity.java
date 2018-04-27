package com.example.jimmycrms.storyfinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;




public class MeetupActivity extends SingleFragmentActivity {

    private static final String EXTRA_MEETUP_ID =
            "com.example.jimmycrms.android.StoryFinder.meetup_id";

    String name;
    String description;
    private TextView mName;
    private TextView mDescription;



    /*
    public static Intent newIntent(Context packageContext, UUID meetupId) {
        Intent intent = new Intent(packageContext, MeetupActivity.class);
        intent.putExtra(EXTRA_MEETUP_ID, meetupId);
        return intent;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_story);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            name = b.getString("name");
            description = b.getString("description");
        }
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mName = (TextView) v.findViewById(R.id.fragment_story_name);
        mName.setText(name);

        mDescription = (TextView) v.findViewById(R.id.fragment_story_description);
        mDescription.setText(description);

        return v;
    }
    */



    @Override
    protected Fragment createFragment(){
        //UUID meetupID = (UUID) getIntent().getSerializableExtra(EXTRA_MEETUP_ID);
        return MeetupFragment.newInstance();
    }


}
