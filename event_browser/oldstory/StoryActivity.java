package com.example.jimmycrms.storyfinder.oldstory;

import android.support.v4.app.Fragment;

import android.content.Context;
import android.content.Intent;

import java.util.UUID;


public class StoryActivity extends SingleFragmentActivity {


    private static final String EXTRA_STORY_ID =
            "com.example.jimmycrms.android.StoryFinder.story_id"; //need help understanding this

    public static Intent newIntent(Context packageContext, UUID storyId) {
        Intent intent = new Intent(packageContext, StoryActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID storyID = (UUID) getIntent()
                .getSerializableExtra(EXTRA_STORY_ID);
        return StoryFragment.newInstance(storyID);
    }



    //finish editing story, needs pic; storyFragment; StoryListFrag
    //still need singleton class see CrimeLab, implement search in that class
    //create xml files, referenced in CrimeListFragment, CrimeFragment, Single
    //create new activity, layout that opens on click
}
