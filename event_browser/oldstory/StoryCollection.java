package com.example.jimmycrms.storyfinder.oldstory;

import android.content.Context;

import com.example.jimmycrms.storyfinder.JsonController;
import com.example.jimmycrms.storyfinder.Meetup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StoryCollection {
    private static StoryCollection sStoryCollection;
    private ArrayList<Story> mStories;

    JsonController controller;


    public static StoryCollection get (Context context){
        if (sStoryCollection == null) {
            sStoryCollection = new StoryCollection(context);
        }
        return sStoryCollection;
    }

    private StoryCollection (Context context){
        mStories = new ArrayList<>();

        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<Meetup> meetups) {
                        if(meetups.size() > 0) {
                            for(int i = 0; i < meetups.size(); i++){
                                Story meetup = new Story();
                                meetup.setName(meetups.get(i).getName());
                                meetup.setDescription(meetups.get(i).getDescription());
                                mStories.add(meetup);
                            }

                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                        for (int i = 1; i < 6 ; i++){
                            Story story = new Story();
                            story.setName("Story #" + i);

                            if(i == 1){
                                story.setDescription("Bob had a good day.");
                            }

                            if(i == 2){
                                story.setDescription("John sets his house on fire.");
                            }

                            if(i == 3){
                                story.setDescription("Mary had a huge lamb");
                            }

                            if(i == 4){
                                story.setDescription("Amy fought a bear.");
                            }

                            if(i == 5){
                                story.setDescription("Steve fell off his bike.");
                            }

                            mStories.add(story);
                        }

                    }
                });

        /*
        for (int i = 1; i < 6 ; i++){
            Story story = new Story();
            story.setName("Story #" + i);

            if(i == 1){
                story.setDescription("Bob had a good day.");
            }

            if(i == 2){
                story.setDescription("John sets his house on fire.");
            }

            if(i == 3){
                story.setDescription("Mary had a huge lamb");
            }

            if(i == 4){
                story.setDescription("Amy fought a bear.");
            }

            if(i == 5){
                story.setDescription("Steve fell off his bike.");
            }

            mStories.add(story);
        }
        */
    }


    public ArrayList<Story> getStories() {
        return mStories;
    }


    public Story getStory (UUID id){
        for (Story story : mStories){
            if (story.getId().equals(id)){
                return story;
            }
        }
        return null;
    }
}
