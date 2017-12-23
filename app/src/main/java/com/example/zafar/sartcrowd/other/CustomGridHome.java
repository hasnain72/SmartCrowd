package com.example.zafar.sartcrowd.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zafar.sartcrowd.Model.Category;
import com.example.zafar.sartcrowd.R;

import java.util.ArrayList;


public class CustomGridHome extends ArrayAdapter<Category> {
    private Context mContext;
    ArrayList<Category> category;

    public CustomGridHome(Activity c, ArrayList<Category> category) {
        super(c, R.layout.grid_single_home , category);
        mContext = c;
        this.category = category;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_single_home, null);
        } else {
            grid = (View) convertView;
            Log.d("Lulu" , "Else");
        }

            TextView textView = (TextView) grid.findViewById(R.id.grid_product_title);
            TextView textViewPrice = (TextView) grid.findViewById(R.id.grid_product_items);
            LinearLayout linearLayout = (LinearLayout) grid.findViewById(R.id.grid_product_item);
          //  linearLayout.setBackground(Imageid[position]);
            textView.setText(category.get(position).getCat_name());
            textViewPrice.setText(category.get(position).getCat_desc());



        return grid;
    }
}
