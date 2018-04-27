package com.example.jimmycrms.storyfinder;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import android.text.Html;


import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meetup> meetupList;
    private OnClickListener listener;

    public RecyclerViewAdapter(List<Meetup> meetupList) {
        this.meetupList = meetupList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meetup_card_layout, parent, false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Meetup meetup = meetupList.get(position);

        CardViewHolder cardViewHolder = (CardViewHolder) holder;
        cardViewHolder.setName(meetup.getName());
        cardViewHolder.setDescription(meetup.getDescription());
        cardViewHolder.setImageUrl(meetup.getUrlkey());
        if(listener!=null) {
            cardViewHolder.bindClickListener(listener, meetup);
        }
    }

    @Override
    public int getItemCount() {
        return meetupList.size();
    }

    public void updateDataSet(List<Meetup> modelList) {
        this.meetupList.clear();
        this.meetupList.addAll(modelList);
        notifyDataSetChanged();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onCardClick(Meetup meetup);
        void onImageClick(Meetup meetup);
    }

    private class CardViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView name;
        private TextView description;
        private NetworkImageView image;


        CardViewHolder(View view) {
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.card_view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.description = (TextView) view.findViewById(R.id.description);
            this.image = (NetworkImageView) view.findViewById(R.id.image1);
        }

        void setName(String name) {
            String t = name;
            this.name.setText(t);
        }

        void setDescription(String description) {
            String y = description;
            this.description.setText(y);
        }

        void setImageUrl(String urlkey) {
            ImageLoader imageLoader = imageSingleton.getInstance(App.getContext()).getImageLoader();
            this.image.setImageUrl(urlkey, imageLoader);
        }

        void bindClickListener(final OnClickListener listener, final Meetup meetup){
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCardClick(meetup);
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onImageClick(meetup);
                }
            });
        }
    }
}