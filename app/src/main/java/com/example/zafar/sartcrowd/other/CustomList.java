package com.example.zafar.sartcrowd.other;

        import android.app.Activity;
        import android.graphics.BitmapFactory;
        import android.util.Base64;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.example.zafar.sartcrowd.Database.Database;
        import com.example.zafar.sartcrowd.Model.Cart;
        import com.example.zafar.sartcrowd.R;
        import com.example.zafar.sartcrowd.Model.Product;
        import com.example.zafar.sartcrowd.activity.MainActivity;
        import com.example.zafar.sartcrowd.fragments.TabFragment1;

        import java.util.ArrayList;
        import java.util.List;

public class CustomList extends ArrayAdapter<Product>{

    private final Activity context;
    ArrayList<Product> products = new ArrayList<Product>();



    public CustomList(Activity context, ArrayList<Product> products) {
        super(context, R.layout.list_single, products);
        this.context = context;
        this.products = products;


    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        Product single_product = products.get(position);

        TextView fresho = (TextView) rowView.findViewById(R.id.p_fresho);
        TextView product_title = (TextView) rowView.findViewById(R.id.p_title);
        TextView product_price= (TextView) rowView.findViewById(R.id.p_price);
        Button prod_add_btn = (Button) rowView.findViewById(R.id.add_btn);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        fresho.setText(single_product.getProduct_name());
        product_title.setText("1 kg");
        product_price.setText("Rs."+single_product.getPrice()+"/-");

        prod_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "I Clicked "+ products.get(position).getProduct_id(), Toast.LENGTH_SHORT).show();
                new Database(context.getBaseContext()).addToCart(new Cart(
                        products.get(position).getProduct_id(),
                        products.get(position).getProduct_name(),
                        "1",
                        products.get(position).getPrice(),
                        "0"
                ));

                ((MainActivity) context).initializeCountDrawer();
                Toast.makeText(context, "Added to Checkout Page", Toast.LENGTH_SHORT).show();
            }
        });

// Decoding Image
        String image[] = single_product.getImage().split("!@");
        Glide.with(context).load(image[0]).dontAnimate().into(imageView);


        // Decoding Image
//        String encodedDataString = single_product.getImage();
//        byte[] imageAsBytes = Base64.decode(encodedDataString.getBytes(), 0);
//        imageView.setImageBitmap(BitmapFactory.decodeByteArray(
//                imageAsBytes, 0, imageAsBytes.length));
//        imageView.setImageResource(R.drawable.apple_product);
        return rowView;
    }


}