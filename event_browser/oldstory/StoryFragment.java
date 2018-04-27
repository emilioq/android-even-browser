package com.example.jimmycrms.storyfinder.oldstory;

import android.support.v4.app.Fragment;
import java.util.UUID;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jimmycrms.storyfinder.R;


public class StoryFragment extends Fragment {

    private static final String ARG_STORY_ID = "story_id";

    private Story mStory;
    //private EditText description name
    private TextView mName;
    private TextView mDescription;


    public static StoryFragment newInstance(UUID storyId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyId);

        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID storyId = (UUID) getArguments().getSerializable(ARG_STORY_ID);
        mStory = StoryCollection.get(getActivity()).getStory(storyId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        mName = (TextView) v.findViewById(R.id.fragment_story_name);
        mName.setText(mStory.getName());

        mDescription = (TextView) v.findViewById(R.id.fragment_story_description);
        mDescription.setText(mStory.getDescription());

        /*
        mTitle.setText(mStory.getName());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStory.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        */

        return v;
    }


}
