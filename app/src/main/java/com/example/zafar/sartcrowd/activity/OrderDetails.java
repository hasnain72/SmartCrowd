package com.example.zafar.sartcrowd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.other.CustomListOrderDetails;

import org.w3c.dom.Text;

public class OrderDetails extends AppCompatActivity {

    TextView order_detail_date;
    TextView order_detail_price;
    TextView order_detail_soldBy;
    ImageView customer;
    TextView order_detail_customer_city;
    TextView order_detail_customers;
    ListView list;
    String[] tittle_order_detail_product = {
            "New Zealanad Apple",
            "Banana",
            "Sundar Khawani",
            "Mandarins"
    } ;
    String[] price_order_detail_product = {
            "198",
            "200",
            "140",
            "99"
    } ;
    Integer[] images_order_detail_product = {
            R.drawable.apple_product,
            R.drawable.apple_product,
            R.drawable.apple_product,
            R.drawable.apple_product

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Order #1076");

        order_detail_date = (TextView) findViewById(R.id.order_detail_date);
        order_detail_price = (TextView) findViewById(R.id.order_detail_price);
        order_detail_soldBy = (TextView) findViewById(R.id.order_detail_sold_by);
        order_detail_customers = (TextView) findViewById(R.id.order_detail_customers);
        order_detail_customer_city = (TextView) findViewById(R.id.order_detail_customer_city);
        customer = (ImageView) findViewById(R.id.customer_avatar_order_detail);

//        Main Section
        order_detail_date.setText("Today 9:24 AM");
        order_detail_price.setText("Rs." + "589" + "/-");
        order_detail_soldBy.setText("Sold by " + "Charles Gay");

//        Customer Section
        customer.setImageResource(R.drawable.customer2);
        order_detail_customers.setText("Susie Queue");
        order_detail_customer_city.setText("Torronto , Ontario");

//        Products details
        CustomListOrderDetails adapter = new
                CustomListOrderDetails(this, tittle_order_detail_product , price_order_detail_product, images_order_detail_product);

        list=(ListView) findViewById(R.id.list_order_details);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }
}
