package com.example.zafar.sartcrowd.other;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zafar.sartcrowd.R;

public class CustomListOrderDetails extends ArrayAdapter<String> {

    private final Activity context;

    private final String[] title;
    private final String[] price;
    private final Integer[] images;
    public CustomListOrderDetails(Activity context,
                                  String[] title ,String[] price, Integer[] images ) {
        super(context, R.layout.list_single_order_details, title);
        this.context = context;
        this.title = title;
        this.price = price;
        this.images = images;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_order_details, null, true);

        TextView titles = (TextView) rowView.findViewById(R.id.product_title);
        TextView prices = (TextView) rowView.findViewById(R.id.product_price);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.product_avatar);

        titles.setText(title[position]);
        prices.setText("Rs."+price[position]+"/-");
        imageView.setImageResource(images[position]);

        return rowView;
    }
}