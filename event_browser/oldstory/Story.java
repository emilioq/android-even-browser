package com.example.jimmycrms.storyfinder.oldstory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.jimmycrms.storyfinder.App;
import com.example.jimmycrms.storyfinder.imageSingleton;

import java.util.UUID;

public class Story {
    private UUID mId;
    private String mName;
    private String mDescription;
    private NetworkImageView image;

    // insert picture field here     ?? Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.Screen1.png, R.drawable.Screen2.png);



    public Story() {
        mId = UUID.randomUUID();
    }

    public String getDescription() {
        return mDescription;
    }

    public UUID getId() {
        return mId;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    void setImageUrl(String imageUrl) {
        ImageLoader imageLoader = imageSingleton.getInstance(App.getContext()).getImageLoader();
        this.image.setImageUrl(imageUrl, imageLoader);
    }

}
