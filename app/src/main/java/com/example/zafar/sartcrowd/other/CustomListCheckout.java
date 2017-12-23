package com.example.zafar.sartcrowd.other;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.Cart;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.fragments.CheckoutFragment;

import java.util.ArrayList;

public class CustomListCheckout extends ArrayAdapter<Cart> {

    private final Activity context;
    private final ArrayList<Cart> carts;

    public CustomListCheckout(Activity context , ArrayList<Cart> carts) {
        super(context, R.layout.list_single_checkout, carts);
        this.context = context;
        this.carts = carts;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_checkout, null, true);

        TextView fresho = (TextView) rowView.findViewById(R.id.p_fresho);
        TextView product_title = (TextView) rowView.findViewById(R.id.p_title);
        TextView product_price= (TextView) rowView.findViewById(R.id.p_price);

        TextView quantity = (TextView) rowView.findViewById(R.id.quantity);
        ImageButton add_button = (ImageButton) rowView.findViewById(R.id.add_image_button);
        ImageButton reduce_button = (ImageButton) rowView.findViewById(R.id.reduce_image_button);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        fresho.setText(carts.get(position).getProductName());
        product_title.setText("1kg");
        product_price.setText("Rs."+carts.get(position).getPrice()+"/-");
        quantity.setText(carts.get(position).getQuantity());

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     //           Toast.makeText(context, carts.get(position).getProductName(), Toast.LENGTH_SHORT).show();

                new Database(context.getBaseContext()).addToCart(new Cart(
                        carts.get(position).getProductId(),
                        carts.get(position).getProductName(),
                        "1",
                        carts.get(position).getPrice(),
                        carts.get(position).getDiscount()
                ));

                CheckoutFragment fragment = new CheckoutFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });

        reduce_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      //          Toast.makeText(context, carts.get(position).getProductName(), Toast.LENGTH_SHORT).show();
                new Database(context.getBaseContext()).removeFromCart(new Cart(
                        carts.get(position).getProductId()
                ));

                CheckoutFragment fragment = new CheckoutFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });


        imageView.setImageResource(R.drawable.apple_product);
        return rowView;
    }
}