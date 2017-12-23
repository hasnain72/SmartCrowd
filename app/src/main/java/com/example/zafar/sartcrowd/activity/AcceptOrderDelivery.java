package com.example.zafar.sartcrowd.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.Model.Order;
import com.example.zafar.sartcrowd.Model.User;
import com.example.zafar.sartcrowd.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AcceptOrderDelivery extends AppCompatActivity {
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_order_delivery);

        getSupportActionBar().setTitle("Accept Order Delivery");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#25496D")));


        order_id = getIntent().getStringExtra("order");

        final TextView NoOfProducts = (TextView) findViewById(R.id.total_products_accepting);
        final TextView OrderPrice = (TextView) findViewById(R.id.total_price_accepting);
        final TextView OrderDiscount = (TextView) findViewById(R.id.total_discount_accepting);
        final TextView OrderDeliveryBoy = (TextView) findViewById(R.id.resource_p_accepting);

        Button AcceptDelivery = (Button) findViewById(R.id.accept_delivery);
        Button RejectDelivery = (Button) findViewById(R.id.reject_delivery);

//        Showing Dialog
        final ProgressDialog Dialog = new ProgressDialog(this);
        Dialog.setCanceledOnTouchOutside(false);
        Dialog.setMessage("Loading Details");
        Dialog.show();

//        Loading Data From Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference OrderTableDataReference = databaseReference.child("order");
        Query query = OrderTableDataReference.orderByChild("order_id").equalTo(order_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);

                    NoOfProducts.setText(order.getNo_of_products());
                    OrderPrice.setText("Rs."+order.getTotal_price()+"/-");
                    OrderDiscount.setText("Rs."+order.getDiscount()+"/-");
                    OrderDeliveryBoy.setText(order.getResource_pid());
                }
                Dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        AcceptDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AcceptOrderDelivery.this, "Accepted", Toast.LENGTH_SHORT).show();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference OrderTableDataReference = databaseReference.child("order");
                OrderTableDataReference.child(order_id).child("resource_pid").setValue("0");
                OrderTableDataReference.child(order_id).child("order_status").setValue("2");

                finish();
            }
        });

        RejectDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AcceptOrderDelivery.this, "Rejected", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Toast.makeText(this, order_id, Toast.LENGTH_SHORT).show();
    }
}
