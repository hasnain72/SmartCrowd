package com.example.zafar.sartcrowd.other;

import android.app.Activity;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.zafar.sartcrowd.Model.Store;
import com.example.zafar.sartcrowd.R;

import java.util.ArrayList;

public class CustomListStore extends ArrayAdapter<Store>{

    private final Activity context;
    ArrayList<Store> store;

    public CustomListStore(Activity context,  ArrayList<Store> store) {
        super(context, R.layout.list_single, store);
        this.context = context;
        this.store = store;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_map_store, null, true);

        TextView STitle = (TextView) rowView.findViewById(R.id.s_title);
        TextView SReview = (TextView) rowView.findViewById(R.id.s_review);
        TextView SStore = (TextView) rowView.findViewById(R.id.s_type);
        TextView STiming = (TextView) rowView.findViewById(R.id.s_timing);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.myRatingBar);
        ratingBar.setFocusable(false);
        ratingBar.setFocusableInTouchMode(false);


        STitle.setText(store.get(position).getTitle());
        SReview.setText("5 reviews");
        SStore.setText("Convenience Store");
        STiming.setText("7:00 Am - 12:00Am");
        ratingBar.setRating(Float.parseFloat("5"));

        return rowView;
    }
}